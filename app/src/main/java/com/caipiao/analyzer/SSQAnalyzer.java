package com.caipiao.analyzer;

import android.os.Build;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 双色球出球频率分析
 *
 * 从最近12期历史开奖中选号，选热5冷1，生成组合
 */
public class SSQAnalyzer {

    /**
     * 读取最近N次的历史数据
     *
     * @param filename    数据文件名
     * @param recentCount 最近的次数
     * @return 历史数据列表，每个元素是一期开奖号码列表
     */
    public static List<List<String>> readRecentHistoricalData(String filename, int recentCount) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineCount = 0; // 计数器，用于跟踪读取的行数
            while ((line = br.readLine()) != null && lineCount < 12) {
                List<String> numbers = new ArrayList<>();
                for (String numStr : line.split("、")) {
                    numbers.add(numStr);
                }
                data.add(0, numbers); // 将新读取的数据插入到列表的开头
                lineCount++; // 增加计数器
            }
        }

        // 返回最近N次的数据
        return data.subList(0, Math.min(recentCount, data.size()));
    }

    /**
     * 计算红球的出现频率
     *
     * @param data 历史数据
     * @return 红球频率映射，键为号码，值为出现次数
     */
    public static Map<Integer, Integer> calculateRedBallFrequency(List<List<String>> data) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (List<String> draw : data) {
            for (int i = 0; i < 6; i++) { // 前6个数字是红球
                try {
                    int num = Integer.parseInt(draw.get(i));
                    if (num >= 1 && num <= 33) { // 确保号码在有效范围内
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
                        }
                    }
                } catch (NumberFormatException e) {
                    // 忽略无效的号码
                }
            }
        }
        return frequency;
    }

    /**
     * 获取出现频率最高的前N个号码
     *
     * @param frequency 号码频率映射
     * @param n         获取的数量
     * @return 频率最高的号码列表
     */
    public static List<Integer> getTopNumbers(Map<Integer, Integer> frequency, int n) {
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(frequency.entrySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            entries.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());
        }

        List<Integer> topNumbers = new ArrayList<>();
        for (int i = 0; i < Math.min(n, entries.size()); i++) {
            topNumbers.add(entries.get(i).getKey());
        }
        return topNumbers;
    }

    /**
     * 获取出现频率最低的前N个号码
     *
     * @param frequency 号码频率映射
     * @param n         获取的数量
     * @return 频率最低的号码列表
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
     * 获取剩余的号码
     *
     * @param frequency   号码频率映射
     * @param hotNumbers  热号列表
     * @param coldNumbers 冷号列表
     * @return 剩余号码列表
     */
    public static List<Integer> getRemainingNumbers(Map<Integer, Integer> frequency, List<Integer> hotNumbers, List<Integer> coldNumbers) {
        Set<Integer> allNumbers = new HashSet<>();
        for (int i = 1; i <= 33; i++) {
            allNumbers.add(i);
        }

        allNumbers.removeAll(hotNumbers);
        allNumbers.removeAll(coldNumbers);

        return new ArrayList<>(allNumbers);
    }

    /**
     * 生成号码组合
     *
     * @param historicalData  历史数据
     * @param numCombinations 生成的组合数量
     * @return 号码组合列表
     */
    public static List<String> getBall(List<List<String>> historicalData, int numCombinations) {
        try {
            Map<Integer, Integer> redBallFrequency = calculateRedBallFrequency(historicalData);

            List<Integer> hotRedNumbers = getTopNumbers(redBallFrequency, 20);
            List<Integer> coldRedNumbers = getBottomNumbers(redBallFrequency, 5);
            List<Integer> remainingRedNumbers = getRemainingNumbers(redBallFrequency, hotRedNumbers, coldRedNumbers);

            List<String> formattedCombinations = new ArrayList<>();
            Random random = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                random = ThreadLocalRandom.current();
            }
            Integer[] combination = new Integer[6];

            for (int i = 0; i < numCombinations; i++) {
                // 检查列表大小是否足够
                if (hotRedNumbers.size() < 5 || coldRedNumbers.size() < 1 ) {
                    System.out.println("hotRedNumbers size: " + hotRedNumbers.size());
                    System.out.println("coldRedNumbers size: " + coldRedNumbers.size());
                    System.out.println("remainingRedNumbers size: " + remainingRedNumbers.size());
                    throw new IllegalStateException("Not enough numbers to generate combinations");
                }

                sample(random, hotRedNumbers, 5, combination, 0);
                sample(random, coldRedNumbers, 1, combination, 5);
//                sample(random, remainingRedNumbers, 1, combination, 5);

                // 直接随机选择蓝球
                int blueBall= random.nextInt(16) + 1;

                shuffleArray(combination);

                // 格式化组合
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 6; j++) {
                    sb.append(formatNumber(combination[j])).append("、");
                }
                sb.deleteCharAt(sb.length() - 1); // 移除最后一个“、”
                String redBallStr = sb.toString();

                // 1. 解析字符串
                String[] parts = redBallStr.split("、|\\+");
                List<Integer> numbers = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    numbers = Arrays.stream(parts)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                }

                // 2. 排序数字
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    numbers.sort(Integer::compareTo);
                }

                // 3. 重新构建字符串
                String sortedSb = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     sortedSb = numbers.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining("、"));
                }

                StringBuilder stringBuilder = new StringBuilder(sortedSb);
                stringBuilder.append("+").append(blueBall);

                formattedCombinations.add(stringBuilder.toString());
            }

            return formattedCombinations;
        } catch (Exception e) {
            // 记录日志或处理异常
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static void sample(Random random, List<Integer> list, int k, Integer[] result, int offset) {
        if (k > list.size()) {
            throw new IllegalArgumentException("抽取的样本大小超过了列表的实际大小");
        }
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(list.size());
            result[offset + i] = list.remove(index);
        }
    }

    private static void shuffleArray(Integer[] array) {
        Random random = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            random = ThreadLocalRandom.current();
        }
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    /**
     * 格式化号码，去掉前导0
     *
     * @param number 号码
     * @return 格式化的号码字符串
     */
    private static String formatNumber(int number) {
        return String.valueOf(number).replaceFirst("^0+", "");
    }

    /**
     * 从列表中随机抽取k个元素
     *
     * @param random 随机数生成器
     * @param list   输入列表
     * @param k      抽取的数量
     * @return 抽取的元素列表
     */
    private static List<Integer> sample(Random random, List<Integer> list, int k) {
        if (k > list.size()) {
            throw new IllegalArgumentException("抽取的样本大小超过了列表的实际大小");
        }
        List<Integer> result = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(list.size());
            result.add(list.remove(index));
        }
        return result;
    }
}
