package com.caipiao;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.caipiao.ReadFileStrCompareToLotteryData.ReadArrange5CompareData;
import com.caipiao.ReadFileStrCompareToLotteryData.ReadHappy8CompareData;
import com.caipiao.ReadFileStrCompareToLotteryData.ReadSevenStarColorCompareData;
import com.caipiao.ReadFileStrCompareToLotteryData.ReadSuperLottoCompareData;
import com.caipiao.ReadFileStrCompareToLotteryData.ReadTwoToneCompareData;
import com.caipiao.activity.PastePrizeClaimActivity;
import com.caipiao.analyzer.SSQAnalyzer;
import com.caipiao.file.SaveArrange5StrToFile;
import com.caipiao.file.SaveHappy8StrToFile;
import com.caipiao.file.SaveSevenStarColorStrToFile;
import com.caipiao.file.SaveSuperLottoStrToFile;
import com.caipiao.file.SaveTwoToneStrToFile;
import com.caipiao.ticket.Arrange5;
import com.caipiao.ticket.Happy8;
import com.caipiao.ticket.SevenStarColor;
import com.caipiao.ticket.SuperLotto;
import com.caipiao.ticket.TwoTone;
import com.caipiao.tools.CustomToast;
import com.caipiao.tools.OpenTicketToday;
import com.myfirstdemotogithub.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

/**
 * 彩票单注多买一注都是侮辱智商。
 * 只有参与和不参与，不存在买多概率就大的可能性,一种彩票只买一注就是顶级参与了。
 * 让你随机一天，不用钱，你也随机不到中四等奖以上的单注。
 * 总结：一种彩票单注只买一注
 * <p>
 * 忠告：假如你对彩票有瘾，并且对今天的开奖非常期待。不妨安装此应用，点上一天随机，你就会明白什么叫概率。
 * 你的幻想将毫无意义，甚至自己都有点想笑。
 */

public class MainActivity extends Activity implements OnClickListener {

    private TextView tv_twotone, tv_superlotto, tv_sevenstarcolor, tv_arrange5, tv_happy8, tv_opentickettoday, tv_show_twotone, tv_show_superlotto, tv_show_sevenstarcolor, tv_show_arrange5, tv_show_happy8;
    private Button prizeClaim_happy8_btn, prizeClaim_twotone_btn, prizeClaim_superlotto_btn,
            prizeClaim_sevenstarcolor_btn, prizeClaim_arrange5_btn, prizeClaim_Paste_btn;
    private ClipboardManager cm;
    private String copyContent, copyBallToFileStr;
    LinearLayout linearLayout;
    private Random random = new Random();
    private int lastNumber = -1;

    private TextView resultTextView;
    private Button fetchButton;
    private Button analyzeButton;
    private List<List<String>> historicalData = new ArrayList<>();
    private final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        linearLayout = findViewById(R.id.ll_main);
        initUI();

    }

    private void initUI() {
        tv_twotone = findViewById(R.id.tv_twotone);
        tv_superlotto = findViewById(R.id.tv_superlotto);
        tv_sevenstarcolor = findViewById(R.id.tv_sevenstarcolor);
        tv_arrange5 = findViewById(R.id.tv_arrange5);
        tv_happy8 = findViewById(R.id.tv_happy8);
        tv_opentickettoday = findViewById(R.id.tv_opentickettoday);
        prizeClaim_happy8_btn = findViewById(R.id.prizeClaim_happy8_btn);
        prizeClaim_twotone_btn = findViewById(R.id.prizeClaim_twotone_btn);
        prizeClaim_superlotto_btn = findViewById(R.id.prizeClaim_superlotto_btn);
        prizeClaim_sevenstarcolor_btn = findViewById(R.id.prizeClaim_sevenstarcolor_btn);
        prizeClaim_arrange5_btn = findViewById(R.id.prizeClaim_arrange5_btn);
        prizeClaim_Paste_btn = findViewById(R.id.prizeClaim_Paste_btn);
        resultTextView = findViewById(R.id.resultTextView);
        fetchButton = findViewById(R.id.fetchButton);
        analyzeButton = findViewById(R.id.analyzeButton);

        tv_twotone.setOnClickListener(this);
        tv_superlotto.setOnClickListener(this);
        tv_sevenstarcolor.setOnClickListener(this);
        tv_arrange5.setOnClickListener(this);
        tv_happy8.setOnClickListener(this);
        tv_opentickettoday.setOnClickListener(this);
        prizeClaim_happy8_btn.setOnClickListener(this);
        prizeClaim_twotone_btn.setOnClickListener(this);
        prizeClaim_superlotto_btn.setOnClickListener(this);
        prizeClaim_sevenstarcolor_btn.setOnClickListener(this);
        prizeClaim_arrange5_btn.setOnClickListener(this);
        prizeClaim_Paste_btn.setOnClickListener(this);

        tv_show_twotone = findViewById(R.id.tv_show_twotone);
        tv_show_superlotto = findViewById(R.id.tv_show_superlotto);
        tv_show_sevenstarcolor = findViewById(R.id.tv_show_sevenstarcolor);
        tv_show_arrange5 = findViewById(R.id.tv_show_arrange5);
        tv_show_happy8 = findViewById(R.id.tv_show_happy8);


        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLotteryData();
            }
        });

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analyzeLotteryData();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }


    private void fetchLotteryData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://kaijiang.500.com/ssq.shtml").get();
                    Elements lotteryResults = doc.select("div.ball_box01");
                    String lotteryResultsStr = lotteryResults.text();
                    List<String> openSet = new ArrayList<>();
                    LinkedHashSet<String> fileSet = new LinkedHashSet<>();

                    for (String numStr : lotteryResultsStr.split(" ")) {
                        openSet.add(numStr);
                    }

                    File directory = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        directory = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOCUMENTS), "双色球");
                    }
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    File file = new File(directory, "分析双色球.txt");
//                    FileWriter writer = new FileWriter(file, true);

                    String newContent = formatLotteryResult(openSet); // 假设 openSet 是你已经定义好的数据
                    try {
                        // 1. 读取现有内容
                        StringBuilder existingContent = new StringBuilder();
                        if (file.exists()) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    existingContent.append(line).append("\n");
                                }
                            }
                        }

                        // 2. 构建新的内容
                        StringBuilder newFileContent = new StringBuilder();
                        newFileContent.append(newContent).append("\n").append(existingContent);

                        // 3. 写入新的内容

                        try (FileWriter writer = new FileWriter(file)) {
                            writer.write(newFileContent.toString());
                        }

                        System.out.println("内容已成功写入文件的第一行");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTextView.setText("数据已保存到 " + file.getAbsolutePath());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTextView.setText("数据获取失败，请检查网络连接");
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 格式化彩票结果
     *
     * @param numbers 彩票号码列表
     * @return 格式化的彩票结果字符串
     */
    private String formatLotteryResult(List<String> numbers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(formatNumber(Integer.parseInt(numbers.get(i)))).append("、");
        }
        sb.deleteCharAt(sb.length() - 1); // 移除最后一个“、”
        sb.append("+").append(formatNumber(Integer.parseInt(numbers.get(6))));
        return sb.toString();
    }

    /**
     * 格式化号码，去掉前导0
     *
     * @param number 号码
     * @return 格式化的号码字符串
     */
    private String formatNumber(int number) {
        return String.valueOf(number).replaceFirst("^0+", "");
    }


    private void analyzeLotteryData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File directory = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    directory = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOCUMENTS), "双色球");
                }
                File file = new File(directory, "分析双色球.txt");

                if (file.exists()) {
                    try {
                        historicalData = SSQAnalyzer.readRecentHistoricalData(file.getAbsolutePath(), 12);
                        List<String> ballStr = SSQAnalyzer.getBall(historicalData, 1);

                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < ballStr.size(); i++) {
                            result.append("双色球 \n\n").append(ballStr.get(i)).append("\n\n\n");
                        }

                        runOnUiThread(() -> resultTextView.setText(result.toString()));
                        copyContent = result.toString();
                        copySelect();

                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText("数据读取失败，请检查文件");
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTextView.setText("文件不存在，请先获取数据");
                        }
                    });
                }
            }
        }).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        copyContent = "";
        if (tv_twotone == v) {
            String copyTwoToneText = new TwoTone().getTwoToneStr();
            tv_show_twotone.setText(copyTwoToneText);
            copyContent = "双色球\n\n" + copyTwoToneText + "\n";
            copyBallToFileStr = copyTwoToneText;
            SaveTwoToneStrToFile();
        } else if (tv_superlotto == v) {
            String copySuperLottoText = new SuperLotto().setSuperLottoStr();
            tv_show_superlotto.setText(copySuperLottoText);
            copyContent = "超级大乐透\n\n" + copySuperLottoText + "\n";
            copyBallToFileStr = copySuperLottoText;
            saveSuperLottoStrToFile();
        } else if (tv_sevenstarcolor == v) {
            String copySevenStarColorText = new SevenStarColor().getSevenStarColorStr();
            tv_show_sevenstarcolor.setText(copySevenStarColorText);
            copyContent = "七星彩\n\n" + copySevenStarColorText + "\n";
            copyBallToFileStr = copySevenStarColorText;
            saveSevenStarColorStrToFile();
        } else if (tv_arrange5 == v) {
            String copyArrange5Text = new Arrange5().getArrange5Str();
            tv_show_arrange5.setText(copyArrange5Text);
            copyContent = "排列五\n\n" + copyArrange5Text + "\n";
            copyBallToFileStr = copyArrange5Text;
            saveArrange5StrToFile();
        } else if (tv_happy8 == v) {
            Happy8 happy8 = new Happy8();
            String copyHappy8Text = happy8.getHappy8Str();
            tv_show_happy8.setText(copyHappy8Text);
            copyContent = "快乐8 【选" + happy8.getChineseNumStr() + "】" + "\n\n" + copyHappy8Text + "\n";
            copyBallToFileStr = copyHappy8Text;
            SaveHappy8StrToFile();
        } else if (tv_opentickettoday == v) {
            // 今天开奖
            String openTicketToday = new OpenTicketToday().openTicketToday();
            CustomToast.show(getApplicationContext(), openTicketToday, 800);
            changeBackgroundPhoto();
        } else if (prizeClaim_happy8_btn == v) {
            // 快乐8比对
            new ReadHappy8CompareData().readHappy8CompareData(this);
        } else if (prizeClaim_twotone_btn == v) {
            // 双色球比对
            new ReadTwoToneCompareData().readTwoToneCompareData(this);
        } else if (prizeClaim_superlotto_btn == v) {
            // 大乐透比对
            new ReadSuperLottoCompareData().readSuperLottoCompareData(this);
        } else if (prizeClaim_sevenstarcolor_btn == v) {
            // 七星彩比对
            new ReadSevenStarColorCompareData().readSevenStarColorCompareData(this);
        } else if (prizeClaim_arrange5_btn == v) {
            // 排列5比对
            new ReadArrange5CompareData().readArrange5CompareData(this);
        } else if (prizeClaim_Paste_btn == v) {
            // 跳转到新的粘贴兑奖 Activity
            Intent intent = new Intent(this, PastePrizeClaimActivity.class);
            startActivity(intent);

        }
        copySelect();
    }

    private void copySelect() {
        cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyContent);
        copyContent = "";
    }

    //保存快乐8
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void SaveHappy8StrToFile() {
        new SaveHappy8StrToFile().saveHappy8StrToFile(this, copyBallToFileStr);
    }

    // 保存双色球
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void SaveTwoToneStrToFile() {
        new SaveTwoToneStrToFile().saveTwoToneStrToFile(this, copyBallToFileStr);
    }

    // 保存大乐透
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveSuperLottoStrToFile() {
        new SaveSuperLottoStrToFile().saveSuperLottoStrToFile(this, copyBallToFileStr);
    }

    // 保存七星彩
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveSevenStarColorStrToFile() {
        new SaveSevenStarColorStrToFile().saveSevenStarColorStrToFile(this, copyBallToFileStr);
    }

    // 保存排列5
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveArrange5StrToFile() {
        new SaveArrange5StrToFile().saveArrange5StrToFile(this, copyBallToFileStr);
    }

    private void changeBackgroundPhoto() {
        int newNumber = getNextUniqueRandom();
        // 使用switch-case结构替换if-else，使代码更清晰
        switch (newNumber) {
            case 0:
                linearLayout.setBackgroundResource(R.drawable.background1);
                break;
            case 1:
                linearLayout.setBackgroundResource(R.drawable.background2);
                break;
            default:
                // 这里通常不会执行，但作为一个良好的编程习惯，处理意外情况
                break;
        }


    }

    /**
     * 生成一个与上一次选择不同的0到2之间的随机数。
     *
     * @return 新的随机数。
     */
    private int getNextUniqueRandom() {
        int nextNumber;
        do {
            nextNumber = random.nextInt(3); // 生成0、1、2中的一个数
        } while (nextNumber == lastNumber); // 如果和上次一样，就重新生成
        /**
         * do 关键字开始循环体。
         * 大括号 {} 包围的循环体代码，这部分代码会先被执行。
         * while ( 条件表达式 ); 判断是否继续循环。
         * 如果条件表达式为真（true），则循环体再次执行；如果为假（false），则循环结束。
         */
        lastNumber = nextNumber; // 更新上一次选择的数
        return nextNumber;
    }
}
