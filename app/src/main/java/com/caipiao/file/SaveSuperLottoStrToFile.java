package com.caipiao.file;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.caipiao.tools.CustomToast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 保存数据到大乐透文件中
 */
public class SaveSuperLottoStrToFile
{

    private static final int PERMISSION_REQUEST_WRITE_STORAGE = 1;


    public void saveSuperLottoStrToFile(Context context, String dataToSave)
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
                Environment.DIRECTORY_DOCUMENTS), "大乐透");
        String fileName = "大乐透保存.txt";

        try
        {
            // 确保目录存在
            if (!directory.exists() && !directory.mkdirs())
            {
                CustomToast.show(context, "目录不存在", 800);
                return;
            }

            File file = new File(directory, fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                writer.write(dataToSave);
                writer.newLine();
                writer.flush();

                CustomToast.show(context, "已保存", 800);
            } catch (IOException e)
            {
                CustomToast.show(context, "保存失败: " + e.getMessage(), 800);
            }
        } catch (Exception e)
        {
            CustomToast.show(context, "报错: " + e.getMessage(), 800);
        }
    }
}
