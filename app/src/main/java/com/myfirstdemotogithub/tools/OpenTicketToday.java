package com.myfirstdemotogithub.tools;


import java.util.Calendar;

/**
 * 今日开奖的彩票
 */
public class OpenTicketToday
{

    public String openTicketToday()
    {
        String openTicketStr;
        // 获取今天是星期几
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 根据星期几执行相应操作
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.SATURDAY)
        {
            // 如果是星期一，输出"大乐透"
            openTicketStr = "大乐透 + 快乐8";
        } else if (dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.SUNDAY)
        {
            // 其他情况
            openTicketStr = "双色球 + 快乐8";
        } else
        {
            openTicketStr = "排列5 + 七星彩";
        }
        return openTicketStr;
    }

}