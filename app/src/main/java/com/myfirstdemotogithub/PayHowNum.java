package com.myfirstdemotogithub;


import java.util.Random;

/**
 * 买不买几组
 */
public class PayHowNum
{
    Random random = new Random();

    public String setBallText()
    {
        int index = random.nextInt(2);
        if (index == 1)
        {
            int num = random.nextInt(6);
            if (num == 0)
            {
                return null;
            } else
            {
                return String.valueOf(num);
            }

        } else
        {
            return String.valueOf(index);
        }
    }
}