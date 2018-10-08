package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 大乐透随机
 */
public class DaLeTou
{
    Random random = new Random();
    List<String> blueList = new ArrayList<>();
    List<String> redList = new ArrayList<>();
    List<String> twoBlueBallList = new ArrayList<>();
    List<String> fiveRedBallList = new ArrayList<>();

    // 获取蓝球的数组
    public List<String> getBlueList()
    {
        for (int j = 1; j <= 12; j++)
        {
            if (j < 10)
            {
                blueList.add("0" + String.valueOf(j));
            } else
            {
                blueList.add(String.valueOf(j));
            }
        }
        return blueList;
    }

    // 获取蓝球
    public List<String> getTwoBlueBall()
    {
        String firstBlueBall = null, secondBlueBall = null;

        // 获取第一个蓝球
        int index = random.nextInt(blueList.size());
        firstBlueBall = blueList.get(index);
        twoBlueBallList.add(firstBlueBall);
        blueList.remove(firstBlueBall);
        // 获取第二个蓝球
        int resetIndex = random.nextInt(blueList.size());
        secondBlueBall = blueList.get(resetIndex);
        twoBlueBallList.add(secondBlueBall);

        return twoBlueBallList;
    }

    // 获取红球数组
    public List<String> getRedList()
    {
        for (int j = 1; j <= 35; j++)
        {
            if (j < 10)
            {
                redList.add("0" + String.valueOf(j));
            } else
            {
                redList.add(String.valueOf(j));
            }
        }
        return redList;
    }

    // 获取5个红球
    public List<String> getFiveRedBall()
    {
        String redBall;
        for (int i = 1; i <= 5; i++)
        {
            int index = random.nextInt(redList.size());
            redBall = redList.get(index);
            fiveRedBallList.add(redBall);
            redList.remove(redBall);
        }
        return fiveRedBallList;
    }

    // 调用方法显示数据
    public String setDaletouText()
    {
        getBlueList();
        getTwoBlueBall();
        getRedList();
        getFiveRedBall();
        String copyContent = fiveRedBallList.toString() + " + " + twoBlueBallList.toString();
        return copyContent;
    }
}
