package com.myfirstdemotogithub.ticket;

import java.util.Random;

/**
 * 七星彩随机
 */
public class SevenStarColor
{
    public String getSevenStarColorStr()
    {
        int[] numbers = new int[6];
        Random rand = new Random();
        for (int i = 0; i < 6; i++)
        {
            numbers[i] = rand.nextInt(10); // 生成 0 到 9 之间的随机数
        }
        //  把结果数组转化成需要的字符串
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < numbers.length; j++)
        {
            sb.append(numbers[j]);
            if (j < numbers.length - 1)
            {
                // 在除了最后一个元素之外的元素后面加上逗号和空格
                sb.append("、");
            }
        }
        String sixBallStr = sb.toString();

        // 末位0-14
        Random random = new Random();
        String lastNumberStr = String.valueOf(random.nextInt(15));
        String ballStr = sixBallStr + "  +  " + lastNumberStr;
        return ballStr;
    }
}
