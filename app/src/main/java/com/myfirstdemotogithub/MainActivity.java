package com.myfirstdemotogithub;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//练习
public class MainActivity extends Activity implements OnClickListener
{

    private TextView s, d, q, p, ss, dd, qq, pp;
    private ImageButton icon, icon2;
    private EditText editText;
    private LinearLayout linearLayout;
    private TextView get;
    private ClipboardManager cm;
    private String copyContent;
    private List<String> list;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideActionBar();
        initUI();
        setInitSsqList();
    }

    private void hideActionBar()
    {
        ActionBar bar = getActionBar();
        if (bar != null)
        {
            bar.hide();
        }
    }

    private void initUI()
    {
        s = findViewById(R.id.s);
        d = findViewById(R.id.d);
        q = findViewById(R.id.q);
        p = findViewById(R.id.p);

        s.setOnClickListener(this);
        d.setOnClickListener(this);
        q.setOnClickListener(this);
        p.setOnClickListener(this);

        ss = findViewById(R.id.ss);
        dd = findViewById(R.id.dd);
        qq = findViewById(R.id.qq);
        pp = findViewById(R.id.pp);

        icon = findViewById(R.id.icon);
        icon.setOnClickListener(this);
        icon2 = findViewById(R.id.icon2);
        icon2.setOnClickListener(this);

        linearLayout = findViewById(R.id.editLinear);
        editText = findViewById(R.id.editext);
        get = findViewById(R.id.get);
        get.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (s == v)
        {
            ss.setText(new ShuangSeQiu().setShuangSeQiuText());
        } else if (d == v)
        {
            dd.setText(new DaLeTou().setDaletouText());
        } else if (q == v)
        {
            qq.setText(new QiXingCai().setBallText());
        } else if (p == v)
        {
            pp.setText(new PaiLieWu().setBallText());
        } else if (icon == v)
        {
            RadomSelect(6);
        } else if (icon2 == v)
        {
            linearLayout.setVisibility(View.VISIBLE);
        } else if (get == v)
        {
            inputDataToRadom();
            copySelect();
        }
        copySelect();
    }

    private void inputDataToRadom()
    {
        String ediStr = editText.getText().toString();
        if (ediStr.contains(" "))
        {
            ediStr.replaceAll(" ", "");
        }
        if (ediStr.contains("，"))
        {
            ediStr.replaceAll("，", ",");
        }
        if (ediStr.contains("'"))
        {
            ediStr.replaceAll("'", ",");
        }
        if (ediStr.contains(",,"))
        {
            ediStr.replaceAll(",,", ",");
        }

        String[] oneStr = ediStr.split(",");

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < oneStr.length; i++)
        {
            if (!list.contains(oneStr[i]))
            {
                list.add(oneStr[i]);
            }
        }

        //红球
        List<String> list2 = new ArrayList<String>();
        for (int i = 0; i < 6; i++)
        {
            random = new Random();
            int num = random.nextInt(list.size());
            String data = list.get(num);
            if (!list2.contains(data))
            {
                list2.add(data);
            } else
            {
                i--;
            }
        }

        //蓝球
        random = new Random();
        List<Integer> list3 = new ArrayList<Integer>();
        for (int i = 0; i < 17; i++)
        {
            list3.add(i);
        }
        list3.remove(0);
        int blue = list3.get(random.nextInt(list3.size()));

        ss.setText(list2.toString() + " + " + blue);
        copyContent = list2.toString() + " + " + blue;
    }

    private void setInitSsqList()
    {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++)
        {
            int data = random.nextInt(34);
            String dataStr = null;
            if (data < 10)
            {
                dataStr = "0" + String.valueOf(data);
            } else
            {
                dataStr = String.valueOf(data);
            }
            if (!list.contains(dataStr) && !"00".equals(dataStr))
            {
                if (list.size() < 11)
                {
                    list.add(dataStr);
                    i++;
                }
            }
        }
    }


    @SuppressLint("NewApi")
    private void RadomSelect(int type)
    {
        random = new Random();
        List<String> ssqRedlist;
        int blue = 0;

        //点双色球图标
        if (type == 6)
        {
            random = new Random();
            List<String> list1 = new ArrayList<String>();
            for (int i = 0; i < 6; i++)
            {
                int num = random.nextInt(list.size());
                String data = list.get(num);
                if (!list1.contains(data))
                {
                    list1.add(data);
                } else
                {
                    i--;
                }
            }

            //蓝球
            List<Integer> blueList = new ArrayList<>();
            for (int i = 0; i < 17; i++)
            {
                blueList.add(i);
            }

            blue = random.nextInt(17);
            if (blue == 0)
            {
                blueList.remove(0);
                blue = random.nextInt(blueList.size());
            }

            copyContent = list1.toString() + " + " + blue;
            String textStr = list.toString() + "  \n\n" + list1.toString() + " + " + blue;
            ss.setText(textStr);

        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void copySelect()
    {
        cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyContent);
    }
}
