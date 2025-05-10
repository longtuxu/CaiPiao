package com.caipiao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimArrange5Data;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimHappy8Data;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimSevenStarData;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimSuperLottoData;
import com.caipiao.ReadPasteStrCompareToLotteryData.PastePrizeClaimTwoToneData;
import com.caipiao.tools.CustomToast;
import com.myfirstdemotogithub.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 *  粘贴兑奖--主页
 *
 *  粘贴检测买的彩票中奖情况
 */
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
                if (!prizeCode.isEmpty()) {
                    String resultEditTextStr = deleteEditTextNotNeedLine(prizeCode);
                    if (resultEditTextStr != null && !resultEditTextStr.isEmpty()) {
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
                            CustomToast.show(getApplicationContext(), "请在第一行输入彩种名称，第二行号码以 、和 + 连接", 800);
                        }
                    } else {
                        CustomToast.show(getApplicationContext(), "格式错误：\n\n第一行仅输入彩种名称\n\n号码行以 、和 + 连接，不含中文", 800);
                    }
                }
            }
        });
    }

    /**
     * 删除EditText中不需要的行
     */
    private String deleteEditTextNotNeedLine(String editTextStr) {
        List<String> result = extractNumberLines(editTextStr);
        if (result.size() != 0) {
            return result.get(0);
        }
        return "";
    }

    /**
     * 从给定的文本中提取以数字开头的行。
     *
     * @param text 包含多行文本的字符串
     * @return 只包含以数字开头的行的列表
     */
// 修改 extractNumberLines 方法
public static List<String> extractNumberLines(String text) {
    List<String> numberLines = new ArrayList<>();
    String[] lines = text.split("\\n");

    // 新正则表达式：支持带+号的号码行
    Pattern pattern = Pattern.compile("^(\\d+[、\\s+])+\\d+$");

    for (String line : lines) {
        if (pattern.matcher(line.replaceAll("[^0-9、+\\s]", "")).find()) {
            numberLines.add(line.trim().replaceAll("，", ",")); // 统一中文逗号
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.onTouchEvent(event);
    }

}
