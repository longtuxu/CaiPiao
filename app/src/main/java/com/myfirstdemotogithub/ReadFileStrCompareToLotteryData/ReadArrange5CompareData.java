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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
读取排列5文件，比对开奖数据
*/
public class ReadArrange5CompareData
{
    String fileContent;

    String sameNumberStr;

    public void readArrange5CompareData(final Context context)
    {
        //读取保存的文件字符串号码
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "排列5");
        String fileName = "排列5保存.txt";

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
            Set<Integer> fileList;

            @Override
            public void run()
            {
                try
                {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/plw.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    final List<Integer> openList = new ArrayList<>();
                    final List<Integer> fileList = new ArrayList<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" "))
                    {
                        openList.add(Integer.parseInt(numStr));
                    }
                    for (String numStr : fileContent.split("、"))
                    {
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
                    new Handler(Looper.getMainLooper()).post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 更新UI上的数据
                            Toast.makeText(context,   "5 中 " + sameNumberStr, Toast.LENGTH_SHORT).show();
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
