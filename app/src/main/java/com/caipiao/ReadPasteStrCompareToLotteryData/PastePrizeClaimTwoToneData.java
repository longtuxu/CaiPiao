package com.caipiao.ReadPasteStrCompareToLotteryData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.caipiao.prize.TwoTonePrize;
import com.caipiao.tools.CustomToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PastePrizeClaimTwoToneData {
    int redRightCount = 0;
    String blueRightCount;

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable() {
            Set<Integer> editedBlue = new HashSet<>();
            Set<Integer> editedRed = new HashSet<>();

            @Override
            public void run() {
                try {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/ssq.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    List<Integer> openSet = new ArrayList<>();
                    editedBlue = new LinkedHashSet<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openSet.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                    }


                    /**
                     * 红球
                     */
                    // 按 + 号分割
                    String[] red = editedText.split("\\+", 2); // 限制分割次数为2，只分割一次

                    if (red.length > 0) {
                        // 获取 + 号前面的部分
                        String beforePlus = red[0];
                        // 去掉顿号
                        String cleanedText = beforePlus.replace("、", ",");
                        // 分割数字
                        String[] numbers = cleanedText.split(",");

                        for (String numStr : numbers) {
                            if (!numStr.trim().isEmpty()) {
                                editedRed.add(Integer.parseInt(numStr.trim()));
                            }
                        }
                    }

                    List<Integer> openSetFirstSix = new ArrayList<>(openSet).subList(0, Math.min(openSet.size(), 6));

                    for (Integer openNum : openSetFirstSix) {
                        for (Integer fileNum : editedRed) {
                            if (openNum.equals(fileNum)) {
                                redRightCount++;
                                break; // 遇到相同的元素后，跳过当前外层循环的剩余部分
                            }
                        }
                    }

                    /**
                     * 蓝球
                     */
                    // 提取 + 号后面的数字
                    String[] blue = editedText.split("\\+");
                    if (blue.length > 1) {
                        String numStr = blue[1].trim();
                        if (!numStr.isEmpty() && !numStr.equals("0")) {
                            editedBlue.add(Integer.parseInt(numStr));
                        }
                    }

                    // 获取openSet的最后一个元素
                    List<Integer> lastOpenSetElementAsList = new ArrayList<>(openSet);
                    int LastOpenElement = lastOpenSetElementAsList.get(lastOpenSetElementAsList.size() - 1);

                    // 获取editedBlue的最后一个元素
                    int lastFileElement = new ArrayList<>(editedBlue).get(0);

                    if (LastOpenElement == lastFileElement) {
                        blueRightCount = "1";
                    } else {
                        blueRightCount = "0";
                    }


                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新UI上的数据
                            TwoTonePrize twoTonePrize = new TwoTonePrize();
                            String prize = twoTonePrize.checkPrizeLevel(redRightCount, Integer.valueOf(blueRightCount));
                            CustomToast.show(context, redRightCount + "+" + blueRightCount + "  " + prize, 800);
                        }
                    });

                } catch (IOException e) {
                    // 捕获和处理IO异常
                }
            }
        }).start();
    }
}

