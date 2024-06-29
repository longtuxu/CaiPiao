package com.caipiao.ReadPasteStrCompareToLotteryData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.caipiao.prize.SuperLottoPrize;
import com.caipiao.tools.CustomToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PastePrizeClaimSuperLottoData {

    int topFiveElementsSameCount = 0;
    int lastTwoElementsSameCount = 0;

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable() {
            Set<Integer> fileSet;

            @Override
            public void run() {
                try {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/dlt.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    Set<Integer> openSet = new LinkedHashSet<>();
                    fileSet = new LinkedHashSet<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openSet.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                    }
                    for (String numStr : editedText.split("[、+\\s]")) {
                        fileSet.add(Integer.parseInt(numStr)); // 文件数据用“、”截取后添加到fileStrList中
                    }

                    List<Integer> openSetFirstSix = new ArrayList<>(openSet).subList(0, Math.min(openSet.size(), 5));
                    List<Integer> fileSetFirstSix = new ArrayList<>(fileSet).subList(0, Math.min(fileSet.size(), 5));

                    for (Integer openNum : openSetFirstSix) {
                        for (Integer fileNum : fileSetFirstSix) {
                            if (openNum.equals(fileNum)) {
                                topFiveElementsSameCount++;
                                break; // 遇到相同的元素后，跳过当前外层循环的剩余部分
                            }
                        }
                    }
                    List<Integer> openSetTopFive = new ArrayList<>(openSet).subList(openSet.size() - 2, openSet.size());
                    List<Integer> fileSetTopFive = new ArrayList<>(fileSet).subList(fileSet.size() - 2, fileSet.size());

                    for (Integer openNum : openSetTopFive) {
                        for (Integer fileNum : fileSetTopFive) {
                            if (openNum.equals(fileNum)) {
                                lastTwoElementsSameCount++;
                                break; // 遇到相同的元素后，跳过当前外层循环的剩余部分
                            }
                        }
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新UI上的数据
                            SuperLottoPrize superLottoPrize = new SuperLottoPrize();
                            String prize = superLottoPrize.checkPrizeLevel(topFiveElementsSameCount, lastTwoElementsSameCount);
                            CustomToast.show(context, topFiveElementsSameCount + " + " + lastTwoElementsSameCount + "  " + prize, 800);
                        }
                    });

                } catch (IOException e) {
                    // 捕获和处理IO异常
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

