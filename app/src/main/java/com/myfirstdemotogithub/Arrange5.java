package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 排列五
 */
public class Arrange5
{
    Random random = new Random();
    List<String> ballList = new ArrayList<>();

    public String getArrange5Str()
    {
        for (int i = 1; i <= 5; i++)
        {
            int index = random.nextInt(10);
            ballList.add(String.valueOf(index));
        }
        return ballList.toString();
    }
}
