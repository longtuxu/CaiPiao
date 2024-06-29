package com.caipiao.prize;

/**
 * 快乐8奖项
 */
public class Happy8Prize
{
    public String checkPrizeLevel(int total, int sameNumberCount)
    {
        if (total == 10 && sameNumberCount == 10)
        {
            return "500万元"; //选十
        } else if (total == 10 && sameNumberCount == 9)
        {
            return "8000元"; //
        } else if (total == 10 && sameNumberCount == 8)
        {
            return "800元"; //
        } else if (total == 10 && sameNumberCount == 7)
        {
            return "80元"; //
        } else if (total == 10 && sameNumberCount == 6)
        {
            return "5元"; //
        } else if (total == 10 && sameNumberCount == 5)
        {
            return "3元"; //
        } else if (total == 10 && sameNumberCount == 0)
        {
            return "2元"; //
        } else if (total == 9 && sameNumberCount == 9)
        {
            return "30万元"; //选九
        } else if (total == 9 && sameNumberCount == 8)
        {
            return "2000元"; //
        } else if (total == 9 && sameNumberCount == 7)
        {
            return "200元"; //
        } else if (total == 9 && sameNumberCount == 6)
        {
            return "20元"; //
        } else if (total == 9 && sameNumberCount == 5)
        {
            return "5元"; //
        } else if (total == 9 && sameNumberCount == 4)
        {
            return "3元"; //
        } else if (total == 9 && sameNumberCount == 0)
        {
            return "2元"; //
        } else if (total == 8 && sameNumberCount == 8)
        {
            return "5万元"; //选八
        } else if (total == 8 && sameNumberCount == 7)
        {
            return "800元"; //
        } else if (total == 8 && sameNumberCount == 6)
        {
            return "88元"; //
        } else if (total == 8 && sameNumberCount == 5)
        {
            return "10元"; //
        } else if (total == 8 && sameNumberCount == 4)
        {
            return "3元"; //
        } else if (total == 8 && sameNumberCount == 0)
        {
            return "2元"; //
        } else if (total == 7 && sameNumberCount == 7)
        {
            return "1万元"; //选七
        } else if (total == 7 && sameNumberCount == 6)
        {
            return "288元"; //
        } else if (total == 7 && sameNumberCount == 5)
        {
            return "28元"; //
        } else if (total == 7 && sameNumberCount == 4)
        {
            return "4元"; //
        } else if (total == 7 && sameNumberCount == 0)
        {
            return "2元"; //
        } else if (total == 6 && sameNumberCount == 6)
        {
            return "3000元"; //选六
        } else if (total == 6 && sameNumberCount == 5)
        {
            return "30元"; //
        } else if (total == 6 && sameNumberCount == 4)
        {
            return "10元"; //
        } else if (total == 6 && sameNumberCount == 3)
        {
            return "3元"; //
        } else if (total == 5 && sameNumberCount == 5)
        {
            return "1000元"; //选五
        } else if (total == 5 && sameNumberCount == 4)
        {
            return "21元"; //
        } else if (total == 5 && sameNumberCount == 3)
        {
            return "3元"; //
        } else if (total == 4 && sameNumberCount == 4)
        {
            return "100元"; //
        } else if (total == 4 && sameNumberCount == 3)
        {
            return "5元"; //
        } else if (total == 4 && sameNumberCount == 2)
        {
            return "3元"; //
        } else if (total == 3 && sameNumberCount == 3)
        {
            return "53元"; //
        } else if (total == 3 && sameNumberCount == 2)
        {
            return "3元"; //
        } else if (total == 2 && sameNumberCount == 2)
        {
            return "19元"; //
        } else if (total == 1 && sameNumberCount == 1)
        {
            return "4.6元"; //
        } else
        {
            return "未中奖"; // 未中奖
        }
    }
}
