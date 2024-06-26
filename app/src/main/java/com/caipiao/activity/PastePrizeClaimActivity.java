package com.caipiao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.caipiao.R;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimArrange5Data;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimHappy8Data;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimSevenStarData;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimSuperLottoData;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimTwoToneData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PastePrizeClaimActivity extends Activity {
    String prizeCode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paste_prize_claim);

        EditText editTextCode = findViewById(R.id.editTextCode);






        /**
         * 清空按钮
         * */
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 假设你只有一个需要清空的EditText，根据实际情况调整
                EditText editTextCode = findViewById(R.id.editTextCode);
                editTextCode.setText(""); // 清空EditText的内容
            }
        });

        /**
         * 兑奖按钮
         * */
        Button buttonClaim = findViewById(R.id.buttonClaim);

        // 设置点击监听器
        buttonClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizeCode = editTextCode.getText().toString();
                String resultEditTextStr = deleteEditTextNotNeedLine(prizeCode);
                if (prizeCode.contains("双色球")) {
                    new PastePrizeClaimTwoToneData().pastePrizeClaimTwoToneData(getApplicationContext(), resultEditTextStr);
                } else if (prizeCode.contains("大乐透")) {
                    new PastePrizeClaimSuperLottoData().pastePrizeClaimTwoToneData(getApplicationContext(), resultEditTextStr);
                } else if (prizeCode.contains("快乐8")) {
                    new PastePrizeClaimHappy8Data().pastePrizeClaimTwoToneData(getApplicationContext(), resultEditTextStr);
                } else if (prizeCode.contains("七星彩")) {
                    new PastePrizeClaimSevenStarData().pastePrizeClaimTwoToneData(getApplicationContext(), resultEditTextStr);
                } else if (prizeCode.contains("排列五")) {
                    new PastePrizeClaimArrange5Data().pastePrizeClaimTwoToneData(getApplicationContext(), resultEditTextStr);
                } else {
                    System.out.println("输入的奖品码不符合格式");
                }

                // 示例：简单弹出一个吐司消息显示输入的奖品码
//                CustomToast.show(getApplicationContext(), "已保存", 800);
            }
        });
    }

    /**
     * 删除EditText中不需要的行
     */
    private String deleteEditTextNotNeedLine(String editTextStr) {
        List<String> result = extractNumberLines(editTextStr);
//        String resultStr = "";
//        for (String line : result) {
//            System.out.println(line);
//        }
        return result.get(0);
    }

    /**
     * 从给定的文本中提取以数字开头的行。
     * @param text 包含多行文本的字符串
     * @return 只包含以数字开头的行的列表
     */
    public static List<String> extractNumberLines(String text) {
        List<String> numberLines = new ArrayList<>();
        String[] lines = text.split("\\n"); // 假设文本是以换行符分隔的多行

        // 正则表达式，用于匹配以数字开头的行
        Pattern pattern = Pattern.compile("^[0-9].*");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line.trim()); // 去除前后空白再匹配
            if (matcher.find()) {
                numberLines.add(line.trim()); // 添加匹配到的行，这里trim()是为了去除行首行尾的空白字符
            }
        }

        return numberLines;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 当用户触碰屏幕任何位置时调用此方法
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                // 如果当前焦点在EditText上，则隐藏键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.onTouchEvent(event);
    }

}
