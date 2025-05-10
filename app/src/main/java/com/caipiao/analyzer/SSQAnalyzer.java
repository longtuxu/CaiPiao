package com.caipiao.analyzer;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;

/**
 * 双色球智能分析器
 */
public class SSQAnalyzer {

    /**
     * 读取最近 N 期历史数据
     *
     * @param filename    数据文件路径
     * @param recentCount 要读取的历史期数
     * @return iorist<String>> 每期红球和蓝球列表
     * @throws IOException 文件读取失败时抛出
     */
    public static List<List<String>> readRecentHistoricalData(String filename, int recentCount) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null && lineCount < recentCount) {
                String[] parts = line.split("\\+");
                if (parts.length >= 2) {
                    String redStr = parts[0];
                    String blueStr = parts[1];
                    List<String> numbers = new ArrayList<>();
                    Collections.addAll(numbers, redStr.split("、"));
                    numbers.add(blueStr);
                    data.add(0, numbers); // 插入到最前
                }
                lineCount++;
            }
        }
        return data.subList(0, Math.min(recentCount, data.size()));
    }

    /**
     * 计算红球出现频率
     *
     * @param historicalData 历史开奖数据
     * @return 红球号码 -> 出现次数
     */
    public static Map<Integer, Integer> calculateRedBallFrequency(List<List<String>> historicalData) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (List<String> draw : historicalData) {
            for (int i = 0; i < 6; i++) {
                try {
                    int num = Integer.parseInt(draw.get(i));
                    if (num >= 1 && num <= 33) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
                        }
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return frequency;
    }

    /**
     * 获取热号（高频出现）
     *
     * @param frequency 号码频率映射
     * @param n         返回数量
     * @return 热号列表
     */
    public static List<Integer> getTopNumbers(Map<Integer, Integer> frequency, int n) {
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(frequency.entrySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            entries.sort((a, b) -> b.getValue() - a.getValue());
        }

        List<Integer> topNumbers = new ArrayList<>();
        for (int i = 0; i < Math.min(n, entries.size()); i++) {
            topNumbers.add(entries.get(i).getKey());
        }
        return topNumbers;
    }

    /**
     * 获取冷号（低频出现）
     *
     * @param frequency 号码频率映射
     * @param n         返回数量
     * @return 冷号列表
     */
    public static List<Integer> getBottomNumbers(Map<Integer, Integer> frequency, int n) {
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(frequency.entrySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            entries.sort(Map.Entry.comparingByValue());
        }

        List<Integer> bottomNumbers = new ArrayList<>();
        for (int i = 0; i < Math.min(n, entries.size()); i++) {
            bottomNumbers.add(entries.get(i).getKey());
        }
        return bottomNumbers;
    }

    /**
     * 获取剩余号码（未被热/冷号包含的）
     */
    public static List<Integer> getRemainingNumbers(Map<Integer, Integer> frequency, List<Integer> hot, List<Integer> cold) {
        Set<Integer> all = new HashSet<>();
        for (int i = 1; i <= 33; i++) all.add(i);

        all.removeAll(new HashSet<>(hot));
        all.removeAll(new HashSet<>(cold));

        return new ArrayList<>(all);
    }

    /**
     * 使用加权策略打分（频率+连续+遗漏）
     *
     * @param historicalData 历史数据
     * @return 分数表
     */
    public static Map<Integer, Double> calculateWeightedScore(@NonNull List<List<String>> historicalData) {
        Map<Integer, Integer> freq = new HashMap<>();
        Map<Integer, Integer> consecutive = new HashMap<>();
        Map<Integer, Integer> missing = new HashMap<>();

        for (int i = 1; i <= 33; i++) {
            freq.put(i, 0);
            consecutive.put(i, 0);
            missing.put(i, 0);
        }

        // 统计频率
        for (List<String> draw : historicalData) {
            for (int i = 0; i < 6; i++) {
                try {
                    int num = Integer.parseInt(draw.get(i));
                    if (num >= 1 && num <= 33) {
                        freq.put(num, freq.get(num) + 1);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        // 统计连续出现
        for (int i = 0; i < historicalData.size() - 1; i++) {
            List<String> curr = historicalData.get(i);
            List<String> next = historicalData.get(i + 1);
            for (String s : next) {
                try {
                    int num = Integer.parseInt(s);
                    if (curr.contains(s)) {
                        consecutive.put(num, consecutive.get(num) + 1);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        // 统计当前是否在最近几期中出现
        for (int num = 1; num <= 33; num++) {
            boolean appearedRecently = false;
            for (int i = 0; i < Math.min(5, historicalData.size()); i++) {
                List<String> draw = historicalData.get(i);
                for (int j = 0; j < 6; j++) {
                    try {
                        if (Integer.parseInt(draw.get(j)) == num) {
                            appearedRecently = true;
                            break;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
            missing.put(num, appearedRecently ? 0 : 1);
        }

        // 综合得分
        Map<Integer, Double> scoreMap = new HashMap<>();
        int maxFreq = Collections.max(freq.values());

        for (int num = 1; num <= 33; num++) {
            double score = (double) freq.get(num) / maxFreq * 0.4;
            score += consecutive.get(num) * 0.3;
            score += (1 - missing.get(num)) * 0.3;
            scoreMap.put(num, score);
        }

        return scoreMap;
    }

    /**
     * 抽样函数（不修改原列表）
     *
     * @param list 输入号码列表
     * @param k    抽取数量
     * @param random 随机数生成器
     * @return 抽样结果
     */
    private static List<Integer> sample(@NonNull List<Integer> list, int k, @NonNull Random random) {
        if (k > list.size()) {
            throw new IllegalArgumentException("样本大小超过可用范围");
        }
        List<Integer> copy = new ArrayList<>(list);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(copy.remove(random.nextInt(copy.size())));
        }
        return result;
    }

    /**
     * 打乱数组
     */
    private static void shuffleArray(@NonNull Integer[] array, @NonNull Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    /**
     * 格式化数字（去掉前导零）
     */
    private static String formatNumber(int number) {
        return String.valueOf(number).replaceFirst("^0+", "");
    }

    /**
     * 生成一组双色球号码（热号5个 + 冷号1个）
     *
     * @param historicalData 历史数据
     * @param numCombinations 生成组合数量
     * @return 格式化字符串列表
     */
    public static List<String> getBall(List<List<String>> historicalData, int numCombinations) {
        List<String> results = new ArrayList<>();
        Map<Integer, Double> scores = calculateWeightedScore(historicalData);

        List<Integer> sortedByScore = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sortedByScore = scores.entrySet().stream()
                    .sorted(Collections.reverseOrder(Comparator.comparingDouble(Map.Entry::getValue)))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        List<Integer> hotRedNumbers = sortedByScore.subList(0, Math.min(10, sortedByScore.size())); // 前10为热池
        List<Integer> coldPool = sortedByScore.subList(Math.max(25, sortedByScore.size() - 5), sortedByScore.size()); // 后5为冷池

        Random random = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? ThreadLocalRandom.current() : new Random();

        for (int i = 0; i < numCombinations; i++) {
            List<Integer> selectedRed = new ArrayList<>();
            selectedRed.addAll(sample(hotRedNumbers, 5, random)); // 从热池选5个
            selectedRed.addAll(sample(coldPool, 1, random));   // 从冷池选1个

            Collections.sort(selectedRed);

            int blueBall = random.nextInt(16) + 1;

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                sb.append(formatNumber(selectedRed.get(j)));
                if (j < 5) sb.append("、");
            }
            sb.append("+").append(formatNumber(blueBall));

            results.add(sb.toString());
        }

        return results;
    }

    /**
     * 异步分析彩票并返回结果（用于UI更新）
     *
     * @param context 上下文
     * @param filename 文件路径
     * @param callback 结果回调
     */
    public static void analyzeAndGenerate(final Context context, final String filename, final int combinations, final AnalysisCallback callback) {
        new Thread(() -> {
            try {
                List<List<String>> data = readRecentHistoricalData(filename, 12);
                List<String> ballList = getBall(data, combinations);

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(ballList));
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    /**
     * 回调接口
     */
    public interface AnalysisCallback {
        void onSuccess(List<String> result);
        void onError(String error);
    }

    /**
     * 避免选择生日类号码（1~31）
     */
    public static List<Integer> avoidBirthdayNumbers(@NonNull List<Integer> candidates) {
        List<Integer> filtered = new ArrayList<>();
        for (int num : candidates) {
            if (num < 1 || num > 31) {
                filtered.add(num);
            }
        }
        if (filtered.isEmpty()) return candidates; // 至少保留一个
        return filtered;
    }

}
