package com.example.pickupcamerastepcounter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticPickUpActivity extends Activity {
    private final String TAG = "zyw";
    private static final String SHARED_PREFERENCE_KEY_CORRECT = "StatisticPickUp_Correct";
    private static final String SHARED_PREFERENCE_KEY_WRONG = "StatisticPickUp_Wrong";
    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");
    private int mPickUpTriggerCorrect = 0;
    private int mPickUpTriggerWrong = 0;
    private Button mCorrect;
    private Button mWrong;
    private TextView mTips;
    private SharedPreferences mSharedPreferences;
    private static final int REQUEST_QUIT = 299;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_QUIT: {
                    finish();
                }
                default:
                    break;
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_pick_up);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPickUpTriggerCorrect = mSharedPreferences.getInt(SHARED_PREFERENCE_KEY_CORRECT, 0);
        mPickUpTriggerWrong = mSharedPreferences.getInt(SHARED_PREFERENCE_KEY_WRONG, 0);
        mCorrect = (Button) findViewById(R.id.button_correct);
        mWrong = (Button) findViewById(R.id.button_wrong);
        mTips = (TextView) findViewById(R.id.pick_up_tips);
        mCorrect.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPickUpTriggerCorrect++;
                mSharedPreferences.edit().putInt(SHARED_PREFERENCE_KEY_CORRECT, mPickUpTriggerCorrect).commit();
                finish();
            }
        });
        mWrong.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPickUpTriggerWrong++;
                mSharedPreferences.edit().putInt(SHARED_PREFERENCE_KEY_WRONG, mPickUpTriggerWrong).commit();
                finish();
            }
        });
        String text = "������ʱ����-����ͳ��Sensor HUB���������"
                +"\n \n \n \n"
                +"��ȷ��������������="+mPickUpTriggerCorrect
                +"\n"
                +"�󴥷�����������="+mPickUpTriggerWrong
                +"\n"
                +"�󴥸���="+getPercent(mPickUpTriggerWrong, mPickUpTriggerWrong+mPickUpTriggerCorrect)
                +"\n \n \n \n"
                +"��δ���ʱ��:"+date.format(System.currentTimeMillis())
                +"��δ���������������Ҫ�Ľ����";
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
        new AlertDialog.Builder(StatisticPickUpActivity.this)
                .setTitle(R.string.pick_up_statistic_title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        mPickUpTriggerCorrect++;
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        mPickUpTriggerWrong++;
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