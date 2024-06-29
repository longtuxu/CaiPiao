package com.caipiao.ReadPasteStrCompareToLotteryData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.caipiao.prize.SevenStarColorPrize;
import com.caipiao.tools.CustomToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PastePrizeClaimSevenStarData {

    String sameNumberStr;
    int top6 = 0;
    int lastNumberNum = 0;

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable() {
            Set<Integer> set2;

            @Override
            public void run() {
                try {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/qxc.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    final List<Integer> openList = new ArrayList<>();
                    final List<Integer> fileStrList = new ArrayList<>();

                    // 构建开奖数据数组和文件数据数组
                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openList.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                    }
                    for (String numStr : editedText.split("、")) {
                        fileStrList.add(Integer.parseInt(numStr)); // 文件数据用“、”截取后添加到fileStrList中
                    }
                    // 求出前6位中出的个数和最后一位中出的个数

                    int minLength = Math.min(openList.size(), fileStrList.size());
                    // 前6位置比对
                    for (int i = 0; i < minLength - 1; i++) {
                        if (openList.get(i).equals(fileStrList.get(i))) {
                            top6++;
                        }
                    }
                    // 最后一位比对

                    if (openList.get(minLength - 1).equals(fileStrList.get(minLength - 1))) {
                        lastNumberNum++;
                    }
                    sameNumberStr = String.valueOf(top6) + " + " + String.valueOf(lastNumberNum);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新UI上的数据
                            SevenStarColorPrize sevenStarColorPrize = new SevenStarColorPrize();
                            String prize = sevenStarColorPrize.checkPrizeLevel(top6, lastNumberNum);
                            CustomToast.show(context, sameNumberStr + " " + prize, 800);
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

