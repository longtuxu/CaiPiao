package com.myfirstdemotogithub;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.myfirstdemotogithub.ReadFileStrCompareToLotteryData.ReadArrange5CompareData;
import com.myfirstdemotogithub.ReadFileStrCompareToLotteryData.ReadHappy8CompareData;
import com.myfirstdemotogithub.ReadFileStrCompareToLotteryData.ReadSevenStarColorCompareData;
import com.myfirstdemotogithub.ReadFileStrCompareToLotteryData.ReadSuperLottoCompareData;
import com.myfirstdemotogithub.ReadFileStrCompareToLotteryData.ReadTwoToneCompareData;
import com.myfirstdemotogithub.file.SaveArrange5StrToFile;
import com.myfirstdemotogithub.file.SaveHappy8StrToFile;
import com.myfirstdemotogithub.file.SaveSevenStarColorStrToFile;
import com.myfirstdemotogithub.file.SaveSuperLottoStrToFile;
import com.myfirstdemotogithub.file.SaveTwoToneStrToFile;
import com.myfirstdemotogithub.ticket.Arrange5;
import com.myfirstdemotogithub.ticket.Happy8;
import com.myfirstdemotogithub.ticket.SevenStarColor;
import com.myfirstdemotogithub.ticket.SuperLotto;
import com.myfirstdemotogithub.ticket.TwoTone;
import com.myfirstdemotogithub.tools.CustomToast;
import com.myfirstdemotogithub.tools.OpenTicketToday;

import androidx.annotation.RequiresApi;

/**
 *彩票单注多买一注都是侮辱智商。
 *只有参与和不参与，不存在买多概率就大的可能性,一种彩票只买一注就是顶级参与了。
 *让你随机一天，不用钱，你也随机不到中四等奖以上的单注。
 * 总结：一种彩票单注只买一注
 *
 * 忠告：假如你对彩票有瘾，并且对今天的开奖非常期待。不妨安装此应用，点上一天随机，你就会明白什么叫概率。
 *       你的幻想将毫无意义，甚至自己都有点想笑。
 */

public class MainActivity extends Activity implements OnClickListener
{

    private TextView tv_twotone, tv_superlotto, tv_sevenstarcolor, tv_arrange5, tv_happy8, tv_opentickettoday, tv_show_twotone, tv_show_superlotto, tv_show_sevenstarcolor, tv_show_arrange5, tv_show_happy8;
    private Button save_happy8_btn, save_twotone_btn, save_superlotto_btn, save_sevenstarcolor_btn, save_arrange5_btn;
    private ClipboardManager cm;
    private String copyContent, copyBallToFileStr;

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
        tv_twotone = findViewById(R.id.tv_twotone);
        tv_superlotto = findViewById(R.id.tv_superlotto);
        tv_sevenstarcolor = findViewById(R.id.tv_sevenstarcolor);
        tv_arrange5 = findViewById(R.id.tv_arrange5);
        tv_happy8 = findViewById(R.id.tv_happy8);
        tv_opentickettoday = findViewById(R.id.tv_opentickettoday);
        save_happy8_btn = findViewById(R.id.save_happy8_btn);
        save_twotone_btn = findViewById(R.id.save_twotone_btn);
        save_superlotto_btn = findViewById(R.id.save_superlotto_btn);
        save_sevenstarcolor_btn = findViewById(R.id.save_sevenstarcolor_btn);
        save_arrange5_btn = findViewById(R.id.save_arrange5_btn);

        tv_twotone.setOnClickListener(this);
        tv_superlotto.setOnClickListener(this);
        tv_sevenstarcolor.setOnClickListener(this);
        tv_arrange5.setOnClickListener(this);
        tv_happy8.setOnClickListener(this);
        tv_opentickettoday.setOnClickListener(this);
        save_happy8_btn.setOnClickListener(this);
        save_twotone_btn.setOnClickListener(this);
        save_superlotto_btn.setOnClickListener(this);
        save_sevenstarcolor_btn.setOnClickListener(this);
        save_arrange5_btn.setOnClickListener(this);

        tv_show_twotone = findViewById(R.id.tv_show_twotone);
        tv_show_superlotto = findViewById(R.id.tv_show_superlotto);
        tv_show_sevenstarcolor = findViewById(R.id.tv_show_sevenstarcolor);
        tv_show_arrange5 = findViewById(R.id.tv_show_arrange5);
        tv_show_happy8 = findViewById(R.id.tv_show_happy8);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v)
    {
        copyContent = "";
        if (tv_twotone == v)
        {
            String copyTwoToneText = new TwoTone().getTwoToneStr();
            tv_show_twotone.setText(copyTwoToneText);
            copyContent = "双色球\n\n" + copyTwoToneText + "\n";
            copyBallToFileStr = copyTwoToneText;
            SaveTwoToneStrToFile();
        } else if (tv_superlotto == v)
        {
            String copySuperLottoText = new SuperLotto().setSuperLottoStr();
            tv_show_superlotto.setText(copySuperLottoText);
            copyContent = "超级大乐透\n\n" + copySuperLottoText + "\n";
            copyBallToFileStr = copySuperLottoText;
            saveSuperLottoStrToFile();
        } else if (tv_sevenstarcolor == v)
        {
            String copySevenStarColorText = new SevenStarColor().getSevenStarColorStr();
            tv_show_sevenstarcolor.setText(copySevenStarColorText);
            copyContent = "七星彩\n\n" + copySevenStarColorText + "\n";
            copyBallToFileStr = copySevenStarColorText;
            saveSevenStarColorStrToFile();
        } else if (tv_arrange5 == v)
        {
            String copyArrange5Text = new Arrange5().getArrange5Str();
            tv_show_arrange5.setText(copyArrange5Text);
            copyContent = "排列五\n\n" + copyArrange5Text + "\n";
            copyBallToFileStr = copyArrange5Text;
            saveArrange5StrToFile();
        } else if (tv_happy8 == v)
        {
            Happy8 happy8 = new Happy8();
            String copyHappy8Text = happy8.getHappy8Str();
            tv_show_happy8.setText(copyHappy8Text);
            copyContent = "快乐8 【选" + happy8.getChineseNumStr() + "】" + "\n\n" + copyHappy8Text + "\n";
            copyBallToFileStr = copyHappy8Text;
            SaveHappy8StrToFile();
        } else if (tv_opentickettoday == v)
        {
            // 今天开奖
            String openTicketToday = new OpenTicketToday().openTicketToday();
            CustomToast.show(getApplicationContext(), openTicketToday, 500);
        } else if (save_happy8_btn == v)
        {
            // 快乐8比对
            new ReadHappy8CompareData().readHappy8CompareData(this);
        } else if (save_twotone_btn == v)
        {
            // 双色球比对
            new ReadTwoToneCompareData().readTwoToneCompareData(this);
        } else if (save_superlotto_btn == v)
        {
            // 大乐透比对
            new ReadSuperLottoCompareData().readSuperLottoCompareData(this);
        } else if (save_sevenstarcolor_btn == v)
        {
            // 七星彩比对
            new ReadSevenStarColorCompareData().readSevenStarColorCompareData(this);
        } else if (save_arrange5_btn == v)
        {
            // 排列5比对
            new ReadArrange5CompareData().readArrange5CompareData(this);
        }
        copySelect();
    }

    private void copySelect()
    {
        cm = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyContent);
        copyContent = "";
    }

    //保存快乐8
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void SaveHappy8StrToFile()
    {
        new SaveHappy8StrToFile().saveHappy8StrToFile(this, copyBallToFileStr);
    }

    // 保存双色球
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void SaveTwoToneStrToFile()
    {
        new SaveTwoToneStrToFile().saveTwoToneStrToFile(this, copyBallToFileStr);
    }

    // 保存大乐透
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveSuperLottoStrToFile()
    {
        new SaveSuperLottoStrToFile().saveSuperLottoStrToFile(this, copyBallToFileStr);
    }

    // 保存七星彩
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveSevenStarColorStrToFile()
    {
        new SaveSevenStarColorStrToFile().saveSevenStarColorStrToFile(this, copyBallToFileStr);
    }

    // 保存排列5
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveArrange5StrToFile()
    {
        new SaveArrange5StrToFile().saveArrange5StrToFile(this, copyBallToFileStr);
    }
}
