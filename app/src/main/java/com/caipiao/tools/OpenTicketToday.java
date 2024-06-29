package com.caipiao.tools;


import java.util.Calendar;

/**
 * 今日开奖的彩票
 *
 * 点击今日开奖，显示今日开奖的彩票
 */
public class OpenTicketToday
{

    public String openTicketToday()
    {
        String openTicketStr = null;
        // 获取今天是星期几
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 根据星期几执行相应操作
        if (dayOfWeek == Calendar.MONDAY)
        {
            openTicketStr = "大乐透 + 快乐8";
        } else if (dayOfWeek == Calendar.TUESDAY)
        {
            openTicketStr = "双色球 + 七星彩 + 快乐8";
        } else if (dayOfWeek == Calendar.WEDNESDAY)
        {
            openTicketStr = "大乐透 + 快乐8";
        } else if (dayOfWeek == Calendar.THURSDAY)
        {
            openTicketStr = "双色球 + 快乐8";
        } else if (dayOfWeek == Calendar.FRIDAY)
        {
            openTicketStr = "七星彩 + 快乐8";
        } else if (dayOfWeek == Calendar.SATURDAY)
        {
            openTicketStr = "大乐透 + 快乐8";
        } else if (dayOfWeek == Calendar.SUNDAY)
        {
            openTicketStr = "双色球 + 七星彩 + 快乐8";
        }
        return openTicketStr;
    }


}