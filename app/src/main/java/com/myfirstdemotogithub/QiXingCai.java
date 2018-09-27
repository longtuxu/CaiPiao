package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QiXingCai
{
    Random random = new Random();
    List<String> ballList = new ArrayList<>();

    public String setBallText()
    {
        for (int i = 1; i <= 7; i++)
        {
            int index = random.nextInt(10);
            ballList.add(String.valueOf(index));
        }
        return ballList.toString();
    }
}
