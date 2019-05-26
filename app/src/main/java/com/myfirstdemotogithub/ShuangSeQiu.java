package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 双色球随机
 */
public class ShuangSeQiu
{
    Random random = new Random();
    List<String> redBallList = new ArrayList<>();
    List<String> sixRedBallList = new ArrayList<>();
    List<String> blueBallList = new ArrayList<>();
    String blueBallStr;

    // 获取33个红球数组
    public List<String> getRedBallList()
    {

        for (int i = 1; i <= 33; i++)
        {
            if (i < 10)
            {
                redBallList.add("0" + String.valueOf(i));
            } else
            {
                redBallList.add(String.valueOf(i));
            }
        }
        return redBallList;
    }

    // 获取6个红球数组
    public List<String> getSixRedBallList()
    {
        for (int i = 1; i <= 6; i++)
        {
            int index = random.nextInt(redBallList.size());
            sixRedBallList.add(redBallList.get(index));
            redBallList.remove(redBallList.get(index));
        }
        return sixRedBallList;
    }

    //获取一个蓝球
    public String getBlueBall()
    {

        for (int i = 1; i <= 16; i++)
        {
            if (i < 10)
            {
                blueBallList.add("0" + String.valueOf(i));
            } else
            {
                blueBallList.add(String.valueOf(i));
            }
        }
        blueBallStr = blueBallList.get(random.nextInt(16));
        return blueBallStr;
    }


    public String setShuangSeQiuText()
    {
        String result ="";
        for (int i = 0; i <4 ; i++)
        {
            sixRedBallList.clear();
            blueBallStr="";
            getRedBallList();
            getSixRedBallList();
            getBlueBall();
            String singleResult = sixRedBallList.toString() + " + " + blueBallStr+ "\n";
            result =result + singleResult;
        }
        return result;
    }
}
