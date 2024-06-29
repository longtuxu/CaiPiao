package com.caipiao.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * amount个球随机选择size个球
 */
public class BallUtils
{
    // 从1到amount个数中选择size个数，升序
    public String getBallListStr(int amount, int size)
    {
        ArrayList<Integer> allBalls = new ArrayList<>();
        for (int i = 1; i <= amount; i++)
        {
            allBalls.add(i);
        }
        // 打乱顺序
        Collections.shuffle(allBalls);
        // 遍历所有球数
        ArrayList<Integer> selectedBalls = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            selectedBalls.add(allBalls.get(i));
        }

        // 对选取的红球进行升序排序
        Collections.sort(selectedBalls);

        //  把结果数组转化成需要的字符串
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < selectedBalls.size(); j++)
        {
            sb.append(selectedBalls.get(j));
            if (j < selectedBalls.size() - 1)
            {
                // 在除了最后一个元素之外的元素后面加上逗号和空格
                sb.append("、");
            }
        }
        String ballListStr = sb.toString();
        return ballListStr;
    }

    // 从fistNum到lastNum随机选择一个数字
    public Integer getRandom(int fistNum, int lastNum)
    {
        Random random = new Random();
        int selectedNumber = random.nextInt(lastNum - fistNum + 1) + fistNum;
        return selectedNumber;
    }

}
