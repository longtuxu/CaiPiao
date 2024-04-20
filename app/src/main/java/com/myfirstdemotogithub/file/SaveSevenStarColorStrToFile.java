package com.myfirstdemotogithub.file;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.myfirstdemotogithub.tools.CustomToast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 保存数据到七星彩文件中
 */
public class SaveSevenStarColorStrToFile
{

    private static final int PERMISSION_REQUEST_WRITE_STORAGE = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveSevenStarColorStrToFile(Context context, String dataToSave)
    {
        // 检查是否具有写入外部存储的权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WRITE_STORAGE);
            return; // 等待用户授权
        }

        // 定义文件路径和名称
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "七星彩");
        String fileName = "七星彩保存.txt";

        try
        {
            // 确保目录存在
            if (!directory.exists() && !directory.mkdirs())
            {
                CustomToast.show(context, "目录不存在", 500);
                return;
            }

            File file = new File(directory, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                writer.write(dataToSave);
                writer.newLine();
                writer.flush();

                CustomToast.show(context, "已保存", 500);
            } catch (IOException e)
            {
                CustomToast.show(context, "保存失败: " + e.getMessage(), 500);
            }
        } catch (Exception e)
        {
            CustomToast.show(context, "报错: " + e.getMessage(), 500);
        }
    }
}
