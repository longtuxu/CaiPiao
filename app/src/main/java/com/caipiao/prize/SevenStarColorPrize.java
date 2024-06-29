package com.caipiao.prize;

/**
 * 七星彩奖项
 */
public class SevenStarColorPrize
{
    public String checkPrizeLevel(int topSix, int lastOne)
    {
        if (topSix == 6 && lastOne == 1)
        {
            return "一等奖"; // 一等奖
        } else if (topSix == 6)
        {
            return "二等奖"; // 二等奖
        } else if (topSix == 5 && lastOne == 1)
        {
            return "三等奖 3000元"; // 三等奖
        } else if (topSix == 5 && lastOne == 0 || topSix == 4 && lastOne == 1)
        {
            return "四等奖 500元"; // 四等奖
        } else if (topSix == 4 && lastOne == 0 || topSix == 3 && lastOne == 1)
        {
            return "五等奖 30元"; // 五等奖
        } else if (topSix == 3 && lastOne == 0 || topSix == 2 && lastOne == 1 || topSix == 1 && lastOne == 1 || topSix == 0 && lastOne == 1)
        {
            return "六等奖 5元"; // 六等奖
        } else
        {
            return "未中奖"; // 未中奖
        }
    }
}
