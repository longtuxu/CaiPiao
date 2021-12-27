package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KuaiLeBa
{
    Random random = new Random();
    Random randomSelect = new Random();
    List<String> redList = new ArrayList<>();
    List<String> BallList = new ArrayList<>();
    int randomNumb = 0;

    // 获取80个红球
    public List<String> getRedBall()
    {
        String redBall;
        getAllRedList();
        randomNumb = randomSelect.nextInt(11);
        for (int i = 1; i <= randomNumb; i++)
        {
            int index = random.nextInt(redList.size());
            redBall = redList.get(index);
            BallList.add(redBall);
            redList.remove(redBall);
        }
        return BallList;
    }

    public int getRandomNumb()
    {
        return randomNumb;
    }

    // 获取所有红球数组
    private List<String> getAllRedList()
    {
        for (int j = 1; j <= 80; j++)
        {
            if (j < 10)
            {
                //个位前加0
                redList.add("0" + String.valueOf(j));
            } else
            {
                redList.add(String.valueOf(j));
            }
        }
        return redList;
    }

    public String setBallText()
    {
        String content = getRedBall().toString();
        return content;
    }
}
