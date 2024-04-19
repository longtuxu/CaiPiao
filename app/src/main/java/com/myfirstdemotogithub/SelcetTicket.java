package com.myfirstdemotogithub;


import java.util.Calendar;

/**
 * 买不买几组
 */
public class SelcetTicket
{

    public String selectTicket()
    {
        String selectTicketStr;
        // 获取今天是星期几
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 根据星期几执行相应操作
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.SATURDAY)
        {
            // 如果是星期一，输出"大乐透"
            selectTicketStr = "大乐透 + 快乐8";
        } else if (dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.SUNDAY)
        {
            // 其他情况
            selectTicketStr = "双色球 + 快乐8";
        } else
        {
            selectTicketStr = "排列5 + 七星彩";
        }
        return selectTicketStr;
    }

}