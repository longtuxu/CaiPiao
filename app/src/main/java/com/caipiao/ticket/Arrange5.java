package com.caipiao.ticket;

import java.util.Random;

/**
 * 排列五随机
 */

public class Arrange5
{
    public String getArrange5Str()
    {
        int[] numbers = new int[5];
        Random rand = new Random();
        for (int i = 0; i < 5; i++)
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
        String ballListStr = sb.toString();
        return ballListStr;
    }
}

