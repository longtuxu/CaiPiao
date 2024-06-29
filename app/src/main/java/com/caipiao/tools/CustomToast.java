package com.caipiao.tools;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.caipiao.R;

/**
 * 自定义Toast吐司，设置时长
 */
public class CustomToast
{

    public static void show(Context context, String message, int durationMillis)
    {
        // 获取LayoutInflater服务
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 根据自定义布局文件生成视图对象
        View layout = inflater.inflate(R.layout.toast_custom, null);

        // 设置Toast文本内容
        TextView textViewMessage = layout.findViewById(R.id.text_view_message);
        textViewMessage.setText(message);

        // 这里可以根据需要设置ImageView的资源

        // 创建Toast实例
        Toast toast = new Toast(context);
        // 设置Toast的显示位置（默认是Gravity.BOTTOM）
        toast.setGravity(Gravity.BOTTOM, 0, 180);
        // 设置Toast持续时间
        toast.setDuration(durationMillis); // 注意：这里直接使用duration参数，不再需要CountDownTimer
        // 设置自定义视图
        toast.setView(layout);
        // 显示Toast
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


