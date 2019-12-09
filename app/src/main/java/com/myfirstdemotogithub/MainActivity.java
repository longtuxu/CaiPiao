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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//练习
public class MainActivity extends Activity implements OnClickListener
{

    private TextView s, d, q, p, m, ss, dd, qq, pp, num;
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
        m = findViewById(R.id.pay);

        s.setOnClickListener(this);
        d.setOnClickListener(this);
        q.setOnClickListener(this);
        p.setOnClickListener(this);
        m.setOnClickListener(this);

        ss = findViewById(R.id.ss);
        dd = findViewById(R.id.dd);
        qq = findViewById(R.id.qq);
        pp = findViewById(R.id.pp);
        num = findViewById(R.id.num);

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
            copyContent = ss.getText().toString() + "\n";
        } else if (d == v)
        {
            dd.setText(new DaLeTou().setDaletouText());
            copyContent = "大乐透\n" + dd.getText().toString() + "\n";
        } else if (q == v)
        {
            qq.setText(new QiXingCai().setBallText());
            copyContent = "七星彩\n" + qq.getText().toString() + "\n";
        } else if (p == v)
        {
            pp.setText(new PaiLieWu().setBallText());
            copyContent = "排列五\n" + pp.getText().toString() + "\n";
        } else if (m == v)
        {
            String toast = new PayHowNum().setBallText();
            if (toast.equals("0"))
            {
                Toast.makeText(getApplicationContext(), "不买", Toast.LENGTH_SHORT).show();
                num.setText(null);
            } else
            {
                num.setText(toast);
            }
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
            String result = "";
            for (int j = 0; j < 4; j++)
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
                String singleResult = list1.toString() + " + " + blue + "\n\n";
                result = result + singleResult;
            }

            copyContent = result;
            String textStr = list.toString() + "  \n\n" + result;
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
