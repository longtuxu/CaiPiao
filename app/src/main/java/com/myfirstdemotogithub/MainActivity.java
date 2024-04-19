package com.myfirstdemotogithub;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myfirstdemotogithub.file.StringToFileSaver;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

//练习
public class MainActivity extends Activity implements OnClickListener
{

    private TextView s, d, q, p, k, selectTicketTx, ss, dd, qq, pp, kk;
    private Button saveBtn;
    private ClipboardManager cm;
    private String copyContent, copyBallToFileStr;
    String sameNumberStr;
    String fileContent;


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
        saveBtn = findViewById(R.id.saveButton);

        s.setOnClickListener(this);
        d.setOnClickListener(this);
        q.setOnClickListener(this);
        p.setOnClickListener(this);
        k.setOnClickListener(this);
        selectTicketTx.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        ss = findViewById(R.id.ss);
        dd = findViewById(R.id.dd);
        qq = findViewById(R.id.qq);
        pp = findViewById(R.id.pp);
        kk = findViewById(R.id.kk);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            copyBallToFileStr = copyPPText;
            saveFile();
        } else if (selectTicketTx == v)
        {
            // 今天开奖
            String selectTicket = new SelcetTicket().selectTicket();
            Toast.makeText(getApplicationContext(), selectTicket, Toast.LENGTH_SHORT).show();
        } else if (saveBtn == v)
        {
            readTextFromInternalStorage();
            compareTheWinBallNum();

        }
        copySelect();
    }

    private void copySelect()
    {
        cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyContent);
        copyContent = "";
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveFile()
    {
        new StringToFileSaver().saveStringToFile(this, copyBallToFileStr);
    }

    //开奖数据与file字符串对比，得出中出的号码个数
    private void compareTheWinBallNum()
    {

        new Thread(new Runnable()
        {
            Set<Integer> set2;

            @Override
            public void run()
            {
                try
                {
                    // 使用Jsoup连接到指定的网址并获取页面内容
                    Document doc = Jsoup.connect("https://kaijiang.500.com/kl8.shtml").get();

                    // 选择页面中彩票结果的元素，并打印出来
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    Set<Integer> set1 = new HashSet<>();
                    set2 = new HashSet<>();

                    // 构建数字集合
                    for (String numStr : lotteryResultsStr.split(" "))
                    {
                        set1.add(Integer.parseInt(numStr)); // 将字符串分割为数字并添加到集合中
                    }
                    for (String numStr : fileContent.split("、"))
                    {
                        set2.add(Integer.parseInt(numStr)); // 同样将第二个字符串中的数字添加到集合中
                    }

                    // 求出集合的交集
                    Set<Integer> intersection = new HashSet<>(set1);
                    intersection.retainAll(set2);

                    // 打印出交集元素的个数
                    sameNumberStr = String.valueOf(intersection.size());
                    new Handler(Looper.getMainLooper()).post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 更新UI上的数据
                            Toast.makeText(getApplicationContext(), (set2.size()) + "中" + sameNumberStr, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e)
                {
                    // 捕获和处理IO异常
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void readTextFromInternalStorage()
    {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "MyApp");
        String fileName = "my_data.txt";

        File file = new File(directory, fileName);
        StringBuilder content = new StringBuilder();

        if (file.exists())
        { // 检查文件是否存在
            try
            {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = br.readLine()) != null)
                {
                    content.append(line);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else
        {
            Log.e("FileRead", "The file does not exist");
        }

        if (content != null && content.length() > 0)
        { // 检查content是否非空
            fileContent = content.toString();
        } else
        {
            Log.e("FileRead", "The file content is null or empty");
        }
    }


}
