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
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 保存数据到双色球文件中
 */
public class SaveTwoToneStrToFile
{

    private static final int PERMISSION_REQUEST_WRITE_STORAGE = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveTwoToneStrToFile(Context context, String dataToSave)
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
                Environment.DIRECTORY_DOCUMENTS), "双色球");
        String fileName = "双色球保存.txt";

        try
        {
            // 确保目录存在
            if (!directory.exists() && !directory.mkdirs())
            {
                Toast.makeText(context, "目录不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            File file = new File(directory, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                writer.write(dataToSave);
                writer.newLine();
                writer.flush();

                Toast.makeText(context, "已保存", Toast.LENGTH_SHORT).show();
            } catch (IOException e)
            {
                Toast.makeText(context, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(context, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
