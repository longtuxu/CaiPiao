package com.caipiao.prize;

/**
 * 双色球奖项
 */
public class TwoTonePrize
{
    public String checkPrizeLevel(int topFive, int lastTwo)
    {
        if (topFive == 6 && lastTwo == 1)
        {
            return "一等奖"; // 一等奖
        } else if (topFive == 6)
        {
            return "二等奖"; // 二等奖
        } else if (topFive == 5 && lastTwo == 1)
        {
            return "三等奖 3000元"; // 三等奖
        } else if (topFive == 5 && lastTwo == 0 || topFive == 4 && lastTwo == 1)
        {
            return "四等奖 200元"; // 四等奖
        } else if (topFive == 4 && lastTwo == 0 || topFive == 3 && lastTwo == 1)
        {
            return "五等奖 10元"; // 五等奖
        } else if (topFive == 2 && lastTwo == 1 || topFive == 1 && lastTwo == 1 || topFive == 0 && lastTwo == 1)
        {
            return "六等奖 5元"; // 六等奖
        } else
        {
            return "未中奖"; // 未中奖
        }
    }
}
