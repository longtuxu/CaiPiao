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

    public void pastePrizeClaimTwoToneData(Context context, String editedText) {
        new Thread(() -> {
            try {
                Document doc = Jsoup.connect("https://kaijiang.500.com/dlt.shtml").get();
                Elements balls = doc.select("div.ball_box01 li.ball_red, div.ball_box01 li.ball_blue");

                // 解析开奖号码（自动补零处理）
                List<Integer> openFront = new ArrayList<>();
                List<Integer> openBack = new ArrayList<>();
                for (int i = 0; i < balls.size(); i++) {
                    String num = balls.get(i).text();
                    int parsedNum = Integer.parseInt(num);
                    if (i < 5) {
                        openFront.add(parsedNum);
                    } else {
                        openBack.add(parsedNum);
                    }
                }

                // 解析用户输入（兼容3、03、+前后空格等格式）
                String[] parts = editedText.split("\\+");
                if (parts.length != 2) {
                    showError(context, "格式错误：必须包含+分隔符");
                    return;
                }

                List<Integer> userFront = parseNumbers(parts[0]);
                List<Integer> userBack = parseNumbers(parts[1]);

                // 格式验证
                if (userFront.size() != 5 || userBack.size() != 2) {
                    showError(context, "号码数量错误：\n前区需5个，后区需2个");
                    return;
                }

                // 计算匹配数
                int frontMatch = countMatches(userFront, openFront);
                int backMatch = countMatches(userBack, openBack);

                // 显示结果
                new Handler(Looper.getMainLooper()).post(() -> {
                    String prize = new SuperLottoPrize().checkPrizeLevel(frontMatch, backMatch);
                    CustomToast.show(context,
                            String.format("前区中%d个 后区中%d个\n%s",
                            frontMatch, backMatch, prize),
                            1500);
                });

            } catch (Exception e) {
                showError(context, "数据获取失败：" + e.getMessage());
            }
        }).start();
    }

    private List<Integer> parseNumbers(String input) {
        List<Integer> numbers = new ArrayList<>();
        String[] rawNumbers = input.trim().split("[、\\s,，]+"); // 兼容所有分隔符
        for (String numStr : rawNumbers) {
            try {
                numbers.add(Integer.parseInt(numStr.replaceAll("[^0-9]", "")));
            } catch (NumberFormatException e) {
                // 忽略非法字符
            }
        }
        return numbers;
    }

    private int countMatches(List<Integer> user, List<Integer> official) {
        int count = 0;
        for (Integer num : user) {
            if (official.contains(num)) {
                count++;
            }
        }
        return count;
    }

    private void showError(Context context, String message) {
        new Handler(Looper.getMainLooper()).post(() ->
            CustomToast.show(context, message, 1500));
    }
}


