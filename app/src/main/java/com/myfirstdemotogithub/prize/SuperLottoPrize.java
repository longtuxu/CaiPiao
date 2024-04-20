package com.myfirstdemotogithub.prize;

/**
 * 大乐透奖项
 */
public class SuperLottoPrize
{
    public String checkPrizeLevel(int topFive, int lastTwo)
    {
        if (topFive == 5 && lastTwo == 2)
        {
            return "一等奖"; // 一等奖
        } else if (topFive == 5 && lastTwo == 1)
        {
            return "二等奖"; // 二等奖
        } else if (topFive == 5)
        {
            return "三等奖 1万元"; // 三等奖
        } else if (topFive == 4 && lastTwo == 2)
        {
            return "四等奖 3000元"; // 四等奖
        } else if (topFive == 4 && lastTwo == 1)
        {
            return "五等奖 300元"; // 五等奖
        } else if (topFive == 4 && lastTwo == 2)
        {
            return "六等奖 200元"; // 六等奖
        } else if (topFive == 4)
        {
            return "七等奖 100元"; // 七等奖
        } else if (topFive == 3 && lastTwo == 1 || topFive == 2 && lastTwo == 2)
        {
            return "八等奖 15元"; // 八等奖
        } else if (topFive == 3 || topFive == 1 && lastTwo == 2 || topFive == 2 && lastTwo == 1 || lastTwo == 2)
        {
            return "九等奖 5元"; // 八等奖
        } else
        {
            return "未中奖"; // 未中奖
        }
    }
}
