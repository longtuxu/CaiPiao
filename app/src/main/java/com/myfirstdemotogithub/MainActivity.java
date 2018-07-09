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

    private TextView s, d, q, p, w, ss, dd, qq, pp, ww;
    private ImageButton icon,icon2;
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
        w = findViewById(R.id.w);

        s.setOnClickListener(this);
        d.setOnClickListener(this);
        q.setOnClickListener(this);
        p.setOnClickListener(this);
        w.setOnClickListener(this);

        ss = findViewById(R.id.ss);
        dd = findViewById(R.id.dd);
        qq = findViewById(R.id.qq);
        pp = findViewById(R.id.pp);
        ww = findViewById(R.id.ww);

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
            RadomSelect(1);
        } else if (d == v)
        {
            RadomSelect(2);
        } else if (q == v)
        {
            RadomSelect(3);
        } else if (p == v)
        {
            RadomSelect(4);
        } else if (w == v)
        {
            RadomSelect(5);
        } else if (icon == v)
        {
            RadomSelect(6);
        }else if (icon2 == v)
        {
            linearLayout.setVisibility(View.VISIBLE);
        }else if (get == v)
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

        String [] oneStr = ediStr.split(",");

        List<String> list = new ArrayList<String>();

        for (int i = 0; i <oneStr.length ; i++)
        {
            if(!list.contains(oneStr[i]))
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
            if(!list2.contains(data))
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
                if (list.size() < 10)
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
        // 双色球
        if (type == 1)
        {
            //红球
            ssqRedlist = new ArrayList<>();
            for (int i = 0; i < 20; i++)
            {
                int num = random.nextInt(34);

                if (!ssqRedlist.contains(String.valueOf(num)) && !ssqRedlist.contains("0" + num) && num != 0)
                {
                    if (ssqRedlist.size() < 6)
                    {
                        if (num < 10)
                        {
                            String setNum = "0" + num;
                            ssqRedlist.add(setNum);
                        } else
                        {
                            ssqRedlist.add(String.valueOf(num));
                        }
                    }
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
            copyContent = ssqRedlist.toString() + " + " + blue;
            ss.setText(copyContent);
        }

        // 大乐透
        if (type == 2)
        {
            List<String> BlueList = new ArrayList<>();
            BlueList.add("01");
            BlueList.add("02");
            BlueList.add("03");
            BlueList.add("04");
            BlueList.add("05");
            BlueList.add("06");
            BlueList.add("07");
            BlueList.add("08");
            BlueList.add("09");
            BlueList.add("10");
            BlueList.add("11");
            BlueList.add("12");
            String blue1 = null, blue2 = null;

            int s = random.nextInt(BlueList.size());
            blue1 = BlueList.get(s);
            BlueList.remove(blue1);

            int s2 = random.nextInt(BlueList.size());
            blue2 = BlueList.get(s2);

            List<String> list = new ArrayList<>();
            for (int i = 0; i < 20; i++)
            {
                int num = random.nextInt(36);
                if (!list.contains(String.valueOf(num)) && !list.contains("0" + num) && num != 0)
                {
                    if (list.size() < 5)
                    {
                        if (num < 10)
                        {
                            String setNum = "0" + num;
                            list.add(setNum);
                        } else
                        {
                            list.add(String.valueOf(num));
                        }
                    }
                }
            }
            copyContent = list.toString() + " + " + blue1 + " " + blue2;
            dd.setText(copyContent);
        }

        // 七星彩
        if (type == 3)
        {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 7; i++)
            {
                int num = random.nextInt(10);
                list.add(String.valueOf(num));
            }
            copyContent = list.toString();
            qq.setText(copyContent);
        }

        // 排五
        if (type == 4)
        {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 5; i++)
            {
                int num = random.nextInt(10);
                list.add(String.valueOf(num));
            }
            copyContent = list.toString();
            pp.setText(copyContent);
        }

        // 任五
        if (type == 5)
        {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < 20; i++)
            {
                int num = random.nextInt(12);
                if (!list.contains(String.valueOf(num)) && !list.contains("0" + num) && num != 0)
                {
                    if (list.size() < 5)
                    {
                        list.add(String.valueOf(num));
                    }
                }
            }
            copyContent = list.toString();
            ww.setText(copyContent);
        }

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
