package com.myfirstdemotogithub.ReadFileStrCompareToLotteryData;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import com.myfirstdemotogithub.prize.TwoTonePrize;
import com.myfirstdemotogithub.tools.CustomToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 读取双色球文件，比对开奖数据
 */
public class ReadTwoToneCompareData
{
    String fileContent;
    int sameElementsCount = 0;
    String lastNumberCount;

    public void readTwoToneCompareData(final Context context)
    {
        //读取保存的文件字符串号码
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "双色球");
        String fileName = "双色球保存.txt";

        File file = new File(directory, fileName);
        StringBuilder content = new StringBuilder();
        try
        {
            if (!file.exists())
            {
                CustomToast.show(context, "请先点标题选号", 500);
            } else
            {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = br.readLine()) != null)
                {
                    content.append(line);
                }
                if (content != null && content.length() > 0)
                { // 检查content是否非空
                    fileContent = content.toString();
                }

                //从开奖网站获取数据，比对中奖情况
                new Thread(new Runnable()
                {
                    Set<Integer> fileSet;

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run()
                    {
                        try
                        {
                            // 使用Jsoup连接到指定的网址并获取页面内容
                            Document doc = Jsoup.connect("https://kaijiang.500.com/ssq.shtml").get();

                            // 选择页面中彩票结果的元素，并打印出来
                            Elements lotteryResults = doc.select("div.ball_box01");
                            String lotteryResultsStr = lotteryResults.text();
                            Set<Integer> openSet = new LinkedHashSet<>();
                            fileSet = new LinkedHashSet<>();

                            // 构建数字集合
                            for (String numStr : lotteryResultsStr.split(" "))
                            {
                                openSet.add(Integer.parseInt(numStr)); // 开奖数据用空格截取后添加到openList数组中
                            }
                            for (String numStr : fileContent.split("[、+]"))
                            {
                                fileSet.add(Integer.parseInt(numStr)); // 文件数据用“、”截取后添加到fileStrList中
                            }

                            List<Integer> openSetFirstSix = new ArrayList<>(openSet).subList(0, Math.min(openSet.size(), 6));
                            List<Integer> fileSetFirstSix = new ArrayList<>(fileSet).subList(0, Math.min(fileSet.size(), 6));


                            for (Integer openNum : openSetFirstSix)
                            {
                                for (Integer fileNum : fileSetFirstSix)
                                {
                                    if (openNum.equals(fileNum))
                                    {
                                        sameElementsCount++;
                                        break; // 遇到相同的元素后，跳过当前外层循环的剩余部分
                                    }
                                }
                            }
                            // 获取openSet的最后一个元素
                            List<Integer> lastOpenSetElementAsList = new ArrayList<>(openSet);
                            int LastOpenElement = lastOpenSetElementAsList.get(lastOpenSetElementAsList.size() - 1);

                            // 获取fileSet的最后一个元素
                            List<Integer> lastFileSetElementAsList = new ArrayList<>(fileSet);
                            int lastFileElement = lastFileSetElementAsList.get(lastFileSetElementAsList.size() - 1);

                            if (LastOpenElement == lastFileElement)
                            {
                                lastNumberCount = "1";
                            } else
                            {
                                lastNumberCount = "0";
                            }

                            new Handler(Looper.getMainLooper()).post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    // 更新UI上的数据
                                    TwoTonePrize twoTonePrize = new TwoTonePrize();
                                    String prize = twoTonePrize.checkPrizeLevel(sameElementsCount, Integer.valueOf(lastNumberCount));
                                    CustomToast.show(context, sameElementsCount + "+" + lastNumberCount + "  " + prize, 500);
                                }
                            });

                        } catch (IOException e)
                        {
                            // 捕获和处理IO异常
                        }
                    }
                }).start();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
