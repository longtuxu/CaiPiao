package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 排列五随机
 */
public class PaiLieWu
{
    Random random = new Random();
    List<String> ballList = new ArrayList<>();

    public String setBallText()
    {
        for (int i = 1; i <= 5; i++)
        {
            int index = random.nextInt(10);
            ballList.add(String.valueOf(index));
        }
        return ballList.toString();
    }
}
