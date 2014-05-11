
package com.example.testhallic;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zhuyw1
 */
public class MainActivity extends Activity {

    private TextView mTextViewHallName;

    private TextView mTextViewHallTips;

    private TextView mTextViewHallData;

    private ImageView mHallAnimation;

    private Button mExit;

    private static final float NS2S = 1.0f / 1000000000.0f;

    private final float[] deltaRotationVector = new float[4];

    private static final int REQUEST_UPDATE_DATA = 299;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");

    private String mSensorBrief = "";

    private int mSensorData = 0;

    private final static String sHallPath = "/dev/sys/class/switch/hall/";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    if (mTextViewHallData != null) {
                        mTextViewHallData.setText("当前时间：" + date.format(System.currentTimeMillis())
                                + "\n" + "当前 Hall IC读数=" + mSensorData + "\n");
                    }
                    if (mHallAnimation != null) {
                        if (mSensorData == 1) {
                            mHallAnimation.setBackgroundDrawable(new ColorDrawable(Color.GREEN));
                        } else {
                            mHallAnimation.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                        }
                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                    break;
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
        setContentView(R.layout.activity_main);

        mTextViewHallName = (TextView) findViewById(R.id.hall_sensor_name);
        mTextViewHallTips = (TextView) findViewById(R.id.hall_sensor_tips);
        mTextViewHallData = (TextView) findViewById(R.id.hall_sensor_data);

        mTextViewHallName.setVisibility(View.GONE);
        mTextViewHallTips.setText("Hall设备节点路径="+sHallPath);
        mHallAnimation = (ImageView) findViewById(R.id.animation_image);

        mHallAnimation.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        mExit = (Button) findViewById(R.id.button_exit);
        if (mExit != null) {
            mExit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

}
