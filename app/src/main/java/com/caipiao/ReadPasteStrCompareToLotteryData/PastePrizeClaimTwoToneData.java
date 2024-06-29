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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PastePrizeClaimTwoToneData {
    int sameElementsCount = 0;
    String lastNumberCount;

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable() {
            Set<Integer> editedStrSet;

            @Override
            public void run() {
                try {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/ssq.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    List<Integer> openSet = new ArrayList<>();
                    editedStrSet = new LinkedHashSet<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openSet.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                    }
                    for (String numStr : editedText.split("[、+]")) {
                        editedStrSet.add(Integer.parseInt(numStr)); // 文件数据用“、”截取后添加到fileStrList中
                    }

                    List<Integer> openSetFirstSix = new ArrayList<>(openSet).subList(0, Math.min(openSet.size(), 6));
                    List<Integer> editedStrSetFirstSix = new ArrayList<>(editedStrSet).subList(0, Math.min(editedStrSet.size(), 6));


                    for (Integer openNum : openSetFirstSix) {
                        for (Integer fileNum : editedStrSetFirstSix) {
                            if (openNum.equals(fileNum)) {
                                sameElementsCount++;
                                break; // 遇到相同的元素后，跳过当前外层循环的剩余部分
                            }
                        }
                    }
                    // 获取openSet的最后一个元素
                    List<Integer> lastOpenSetElementAsList = new ArrayList<>(openSet);
                    int LastOpenElement = lastOpenSetElementAsList.get(lastOpenSetElementAsList.size() - 1);

                    // 获取editedStrSet的最后一个元素
                    List<Integer> lasteditedStrSetElementAsList = new ArrayList<>(editedStrSet);
                    int lastFileElement = lasteditedStrSetElementAsList.get(lasteditedStrSetElementAsList.size() - 1);

                    if (LastOpenElement == lastFileElement) {
                        lastNumberCount = "1";
                    } else {
                        lastNumberCount = "0";
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新UI上的数据
                            TwoTonePrize twoTonePrize = new TwoTonePrize();
                            String prize = twoTonePrize.checkPrizeLevel(sameElementsCount, Integer.valueOf(lastNumberCount));
                            CustomToast.show(context, sameElementsCount + "+" + lastNumberCount + "  " + prize, 800);
                        }
                    });

                } catch (IOException e) {
                    // 捕获和处理IO异常
                }
            }
        }).start();
    }
}

