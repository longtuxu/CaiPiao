package com.myfirstdemotogithub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 大乐透随机
 */
public class SuperLotto
{
    BallUtils ballUtils = new BallUtils();

    // 调用方法显示数据
    public String setSuperLottoStr()
    {
        // 选红球
        String redBallStr = ballUtils.getBallListStr(35, 5);
        // 选蓝球
        String blueBallStr = ballUtils.getBallListStr(12, 2);
        // 蓝球+红球
        String copyContent = redBallStr + " + " + blueBallStr;
        return copyContent;
    }
}
