package com.myfirstdemotogithub.ReadFileStrCompareToLotteryData;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 读取七星彩文件，比对开奖数据
 */
public class ReadSevenStarColorCompareData
{
    String fileContent;

    String sameNumberStr;

    public void readSevenStarColorCompareData(final Context context)
    {
        //读取保存的文件字符串号码
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "七星彩");
        String fileName = "七星彩保存.txt";

        File file = new File(directory, fileName);
        StringBuilder content = new StringBuilder();
        try
        {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null)
            {
                content.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        if (content != null && content.length() > 0)
        { // 检查content是否非空
            fileContent = content.toString();
        }
        //从开奖网站获取数据，比对中奖情况
        new Thread(new Runnable()
        {
            Set<Integer> set2;

            @Override
            public void run()
            {
                try
                {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/qxc.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    final List<Integer> openList = new ArrayList<>();
                    final List<Integer> fileStrList = new ArrayList<>();

                    // 构建开奖数据数组和文件数据数组
                    for (String numStr : lotteryResultsStr.split(" "))
                    {
                        openList.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                    }
                    for (String numStr : fileContent.split("、"))
                    {
                        fileStrList.add(Integer.parseInt(numStr)); // 文件数据用“、”截取后添加到fileStrList中
                    }
                    // 求出前6位中出的个数和最后一位中出的个数
                    int top6 = 0;
                    int minLength = Math.min(openList.size(), fileStrList.size());
                    // 前6位置比对
                    for (int i = 0; i < minLength - 1; i++)
                    {
                        if (openList.get(i).equals(fileStrList.get(i)))
                        {
                            top6++;
                        }
                    }
                    // 最后一位比对
                    int lastNumberNum = 0;
                    if (openList.get(minLength - 1).equals(fileStrList.get(minLength - 1)))
                    {
                        lastNumberNum++;
                    }

                    sameNumberStr = String.valueOf(top6) + " + " + String.valueOf(lastNumberNum);
                    new Handler(Looper.getMainLooper()).post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 更新UI上的数据
                            Toast.makeText(context, "中" + sameNumberStr, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e)
                {
                    // 捕获和处理IO异常
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
