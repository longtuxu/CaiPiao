package com.myfirstdemotogithub.ticket;

import com.myfirstdemotogithub.util.BallUtils;

/**
 * 双色球随机
 */
public class TwoTone

{
    BallUtils ballUtils = new BallUtils();

    //机选两组双色球
    public String getTwoToneStr()
    {
        // 选红球
        String redBallStr = ballUtils.getBallListStr(33, 6);
        // 选蓝球
        String blueBallStr = ballUtils.getBallListStr(16, 1);
        // 蓝球+红球
        String copyContent = redBallStr + "+" + blueBallStr;
        return copyContent;
    }
}
