package com.caipiao.tools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

public class ClipboardReceiver
{
    public void checkClipboardContent(Context context)
    {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0)
        {
            CharSequence pasteData = clip.getItemAt(0).getText();
            if (pasteData != null)
            {
                String pasteContent = pasteData.toString();
                // 进行内容比对
                compareContent(pasteContent);
            }
        } else
        {
            Log.d("ClipboardCheck", "Clipboard is empty.");
        }
    }

    private void compareContent(String contentToCompare)
    {
        String targetContent = "your_target_string_here";
        if (contentToCompare.equals(targetContent))
        {
            // 内容匹配，执行相应操作
            Log.d("ClipboardCheck", "Content matched!");
        } else
        {
            // 内容不匹配
            Log.d("ClipboardCheck", "Content not matched.");
        }
    }
}

