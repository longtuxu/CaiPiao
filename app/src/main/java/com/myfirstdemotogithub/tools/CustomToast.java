package com.myfirstdemotogithub.tools;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class CustomToast
{

    public static void show(Context context, String message, int durationMillis)
    {
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();

        // 创建并启动自定义定时器，关闭Toast
        new CountDownTimer(durationMillis, 1000)
        { // 参数：总时长，间隔
            @Override
            public void onTick(long millisUntilFinished)
            {
            }

            @Override
            public void onFinish()
            {
                toast.cancel(); // 在指定时长后取消Toast显示
            }
        }.start();
    }
}


