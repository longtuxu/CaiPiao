package com.caipiao.ReadPasteStrCompareToLotteryData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.caipiao.prize.Arrange5Prize;
import com.caipiao.tools.CustomToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PastePrizeClaimArrange5Data {

    String sameNumberStr;

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/plw.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    final List<Integer> openList = new ArrayList<>();
                    final List<Integer> fileList = new ArrayList<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openList.add(Integer.parseInt(numStr));
                    }
                    for (String numStr : editedText.split("、")) {
                        fileList.add(Integer.parseInt(numStr));
                    }

                    // 按位置顺序比较 List<Integer> openList 和 List<Integer> fileList 对应位置相同的数量
                    int count = 0;
                    int minLength = Math.min(openList.size(), fileList.size());

                    for (int i = 0; i < minLength; i++) {
                        if (openList.get(i).equals(fileList.get(i))) {
                            count++;
                        }
                    }
                    sameNumberStr = String.valueOf(count);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新UI上的数据
                            Arrange5Prize arrange5Prize = new Arrange5Prize();
                            String prize = arrange5Prize.checkPrizeLevel(Integer.valueOf(sameNumberStr));
                            CustomToast.show(context, "5 中 " + sameNumberStr + "  " + prize, 800);
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

