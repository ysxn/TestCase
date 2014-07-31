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
    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");
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
        String text = "������ʱ����-����ͳ��Sensor HUB���������"
                +"\n \n \n \n"
                +"��ȷ����������������="+mWaveHandTriggerCorrect
                +"\n"
                +"�󴥷�������������="+mWaveHandTriggerWrong
                +"\n"
                +"�󴥸���="+getPercent(mWaveHandTriggerWrong, mWaveHandTriggerWrong+mWaveHandTriggerCorrect)
                +"\n \n \n \n"
                +"��δ���ʱ��:"+date.format(System.currentTimeMillis())
                +"��δ�����������������Ҫ�Ľ����";
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
        String result = "0%";// ���ܰٷֱȵ�ֵ
        if (y <= 0)
            return result;
        double x_double = x * 1.0;
        double y_double = y * 1.0;
        double tempresult = x_double / y_double;
        // NumberFormat nf = NumberFormat.getPercentInstance(); ע�͵���Ҳ��һ�ַ���
        // nf.setMinimumFractionDigits( 2 ); ������С�����λ
        DecimalFormat df1 = new DecimalFormat("##%"); // ##.00%
                                                      // �ٷֱȸ�ʽ�����治��2λ����0����
        // result=nf.format(tempresult);
        result = df1.format(tempresult);
        return result;
    }
}