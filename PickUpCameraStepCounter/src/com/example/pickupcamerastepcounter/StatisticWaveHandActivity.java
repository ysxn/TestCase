package com.example.pickupcamerastepcounter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticWaveHandActivity extends Activity {
    private final String TAG = "zyw";
    private static final String SHARED_PREFERENCE_KEY_CORRECT = "StatisticWaveHand_Correct";
    private static final String SHARED_PREFERENCE_KEY_WRONG = "StatisticWaveHand_Wrong";
    private SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");
    private int mWaveHandTriggerCorrect = 0;
    private int mWaveHandTriggerWrong = 0;
    private Button mCorrect;
    private Button mWrong;
    private TextView mTips;
    private SharedPreferences mSharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_pick_up);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mWaveHandTriggerCorrect = mSharedPreferences.getInt(SHARED_PREFERENCE_KEY_CORRECT, 0);
        mWaveHandTriggerWrong = mSharedPreferences.getInt(SHARED_PREFERENCE_KEY_WRONG, 0);
        mCorrect = (Button) findViewById(R.id.button_correct);
        mWrong = (Button) findViewById(R.id.button_wrong);
        mTips = (TextView) findViewById(R.id.pick_up_tips);
        mCorrect.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mWaveHandTriggerCorrect++;
                mSharedPreferences.edit().putInt(SHARED_PREFERENCE_KEY_CORRECT, mWaveHandTriggerCorrect).commit();
                finish();
            }
        });
        mWrong.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mWaveHandTriggerWrong++;
                mSharedPreferences.edit().putInt(SHARED_PREFERENCE_KEY_WRONG, mWaveHandTriggerWrong).commit();
                finish();
            }
        });
        String text = "这是临时界面-用来统计Sensor HUB触发情况："
                +"\n \n \n \n"
                +"正确触发挥手亮屏次数="+mWaveHandTriggerCorrect
                +"\n"
                +"误触发挥手亮屏次数="+mWaveHandTriggerWrong
                +"\n"
                +"误触概率="+getPercent(mWaveHandTriggerWrong, mWaveHandTriggerWrong+mWaveHandTriggerCorrect)
                +"\n \n \n \n"
                +"这次触发时间:"+date.format(System.currentTimeMillis())
                +"这次触发挥手亮屏是您想要的结果吗？";
        mTips.setText(text);
        //showDialog(text);
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        // super.onConfigurationChanged(newConfig);
    }
    

    private void showDialog(String msg) {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(StatisticWaveHandActivity.this)
                .setTitle(R.string.pick_up_statistic_title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        mWaveHandTriggerCorrect++;
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        mWaveHandTriggerWrong++;
                        dialog.dismiss();
                    }
                })
                .show();
    }
    
    public String getPercent(int x, int y) {
        String result = "0%";// 接受百分比的值
        if (y <= 0)
            return result;
        double x_double = x * 1.0;
        double y_double = y * 1.0;
        double tempresult = x_double / y_double;
        // NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
        // nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
        DecimalFormat df1 = new DecimalFormat("##%"); // ##.00%
                                                      // 百分比格式，后面不足2位的用0补齐
        // result=nf.format(tempresult);
        result = df1.format(tempresult);
        return result;
    }
}