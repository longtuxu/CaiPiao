package com.caipiao.prize;

/**
 * 排列5奖项
 */
public class Arrange5Prize
{
    public String checkPrizeLevel(int bingoCount)
    {
        if (bingoCount == 5)
        {
            return "一等奖 10万元"; // 一等奖
        } else
        {
            return "未中奖"; // 未中奖
        }
    }
}
