package com.myfirstdemotogithub.ticket;

import com.myfirstdemotogithub.util.BallUtils;

public class Happy8
{
    int selectCount;
    BallUtils ballUtils = new BallUtils();


    public String getChineseNumStr()
    {
        if (selectCount >= 1 && selectCount <= 10)
        {
            String[] chineseNumbers = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
            return chineseNumbers[selectCount];
        } else
        {
            return "未知";
        }
    }

    public String getHappy8Str()
    {
        // 选7到10的随机数
        selectCount = ballUtils.getRandom(7, 10);
        // 选红球
        String redBallStr = ballUtils.getBallListStr(80, selectCount);
        return redBallStr;
    }
}
