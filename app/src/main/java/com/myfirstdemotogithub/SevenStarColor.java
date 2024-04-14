package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 七星彩随机
 */
public class SevenStarColor
{
    Random random = new Random();
    List<String> sixNumber = new ArrayList<>();

    public String getSevenStarColorStr()
    {
//        前6位0-9
        for (int i = 1; i <= 6; i++)
        {
            int index = random.nextInt(10);
            sixNumber.add(String.valueOf(index));
        }
//        末位0-14
        Random random = new Random();
        String lastNumber = String.valueOf(random.nextInt(15));
        String number = sixNumber.toString() + "  +  " + lastNumber;
        return number;
    }
}
