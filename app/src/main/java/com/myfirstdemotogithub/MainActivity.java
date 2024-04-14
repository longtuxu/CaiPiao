package com.myfirstdemotogithub;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//练习
public class MainActivity extends Activity implements OnClickListener
{

    private TextView s, d, q, p, k, selectTicketTx, ss, dd, qq, pp, kk, todayTicket;
    private ClipboardManager cm;
    private String copyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initUI();
    }

    private void initUI()
    {
        s = findViewById(R.id.s);
        d = findViewById(R.id.d);
        q = findViewById(R.id.q);
        p = findViewById(R.id.p);
        k = findViewById(R.id.k);
        selectTicketTx = findViewById(R.id.selectTicket);

        s.setOnClickListener(this);
        d.setOnClickListener(this);
        q.setOnClickListener(this);
        p.setOnClickListener(this);
        k.setOnClickListener(this);
        selectTicketTx.setOnClickListener(this);

        ss = findViewById(R.id.ss);
        dd = findViewById(R.id.dd);
        qq = findViewById(R.id.qq);
        pp = findViewById(R.id.pp);
        kk = findViewById(R.id.kk);
        todayTicket = findViewById(R.id.todayTicket);

    }

    @Override
    public void onClick(View v)
    {
        copyContent = "";
        if (s == v)
        {
            String copySSText = new TwoTone().getTwoToneStr();
            ss.setText(copySSText);
            copyContent = "双色球\n\n" + copySSText + "\n";
        } else if (d == v)
        {
            String copyDDText = new SuperLotto().setSuperLottoStr();
            dd.setText(copyDDText);
            copyContent = "超级大乐透\n\n" + copyDDText + "\n";
        } else if (q == v)
        {
            String copyQQText = new SevenStarColor().getSevenStarColorStr();
            qq.setText(copyQQText);
            copyContent = "七星彩\n\n" + copyQQText + "\n";
        } else if (p == v)
        {
            String copyPPText = new Arrange5().getArrange5Str();
            pp.setText(copyPPText);
            copyContent = "排列五\n\n" + copyPPText + "\n";
        } else if (k == v)
        {
            Happy8 happy8 = new Happy8();
            String copyPPText = happy8.setBallStr();
            kk.setText(copyPPText);
            copyContent = "快乐8 【选" + happy8.getChineseNumStr() + "】" + "\n\n" + copyPPText + "\n";
        } else if (selectTicketTx == v)
        {
            // 今天开奖
            String selectTicket = new SelcetTicket().selectTicket();
            Toast.makeText(getApplicationContext(), selectTicket, Toast.LENGTH_SHORT).show();
        }
        copySelect();
    }

    private void copySelect()
    {
        cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyContent);
        copyContent = "";
    }
}
