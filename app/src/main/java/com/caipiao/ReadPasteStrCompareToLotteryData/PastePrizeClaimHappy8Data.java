package com.caipiao.ReadPasteStrCompareToLotteryData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.caipiao.prize.Happy8Prize;
import com.caipiao.tools.CustomToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PastePrizeClaimHappy8Data {

    String sameNumberStr;

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable() {
            Set<Integer> fileSet;

            @Override
            public void run() {
                try {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/kl8.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    Set<Integer> openSet = new HashSet<>();
                    fileSet = new HashSet<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openSet.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                    }
                    for (String numStr : editedText.split("、")) {
                        fileSet.add(Integer.parseInt(numStr)); // 文件数据用“、”截取后添加到fileStrList中
                    }

                    // 求出集合的交集
                    Set<Integer> intersection = new HashSet<>(openSet);
                    intersection.retainAll(fileSet);

                    // 打印出交集元素的个数
                    sameNumberStr = String.valueOf(intersection.size());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新UI上的数据
                            int total = fileSet.size();
                            int sameNumberCount = Integer.valueOf(sameNumberStr);
                            Happy8Prize happy8Prize = new Happy8Prize();
                            String prize = happy8Prize.checkPrizeLevel(total, sameNumberCount);
                            String totalStr = String.valueOf(total);
                            CustomToast.show(context, totalStr + "中" + sameNumberStr + "  " + prize, 800);
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

