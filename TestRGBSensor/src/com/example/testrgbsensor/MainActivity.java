
package com.example.testrgbsensor;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * RGB 读数在K7上红光（r）=650，绿光（g）=750，蓝光（b）=845，色温（ct）=4655
 * 
 * @author zhuyw1
 */
public class MainActivity extends Activity implements SensorEventListener {

    private static final int REQUEST_UPDATE_DATA = 299;

    private long mCurrentTime = SystemClock.elapsedRealtime();

    private SensorManager mSensorManager;
    private TextView mTextViewFrontRGBSensorName;
    private TextView mTextViewBackRGBSensorName;
    private TextView mTextViewFrontRGBSensorData;
    private TextView mTextViewBackRGBSensorData;

    private Sensor mFrontRGBSensor;
    private Sensor mBackRGBSensor;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");
    private String mSensorBrief = "";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);

                    if (mTextViewFrontRGBSensorData != null) {

                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 300L);
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * k7上是33171013，我们x2项目上定义为33171013
     */
    static final int RGB_SENSOR = 33171013;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mTextViewFrontRGBSensorName = ((TextView) findViewById(R.id.front_rgb_sensor_name));
        mTextViewFrontRGBSensorData = ((TextView) findViewById(R.id.front_rgb_data));

        List<Sensor> ss = mSensorManager.getSensorList(RGB_SENSOR);
        if (ss == null || ss.size() == 0) {
            Toast.makeText(this, "获取RGB传感器为空，请检查传感器id是否为：" + RGB_SENSOR,
                    Toast.LENGTH_LONG)
                    .show();
            finish();
            return;
        }
        Toast.makeText(this,
                "获取RGB传感器ok，getSensorList size=" + ss.size() + ",传感器id为：" + RGB_SENSOR,
                Toast.LENGTH_LONG)
                .show();
        StringBuilder sb = new StringBuilder();
        sb.append("getSensorList,size=" + ss.size() + ",传感器Type_id为=" + RGB_SENSOR + "; ");
        Sensor s;
        for (int i = 0; i < ss.size(); i++) {
            s = ss.get(i);
            sb.append("(i=" + i + ",name=" + s.getName() + ") ");
        }
        sb.append("\n");
        mSensorBrief = sb.toString();

        mFrontRGBSensor = ss.get(0);

        if (mFrontRGBSensor == null)
        {
            mTextViewFrontRGBSensorName.setText("没有前RGB传感器!");
        } else {
            mTextViewFrontRGBSensorName.setText("RGB传感器型号:" + mFrontRGBSensor.getName());
        }

        Button exit = (Button) findViewById(R.id.button_exit);
        if (exit != null) {
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 300L);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mSensorManager.registerListener(this, mFrontRGBSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mSensorManager.unregisterListener(this, mFrontRGBSensor);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        // TODO Auto-generated method stub
        int red = (int) e.values[0];
        int green = (int) e.values[1];
        int blue = (int) e.values[2];
        int ct = (int) e.values[3];
        // Log.i(TAG, "data:" + data);
        if (mTextViewFrontRGBSensorData != null) {
            mTextViewFrontRGBSensorData.setText("当前时间：" + date.format(System.currentTimeMillis())
                    + ". \n" + mSensorBrief+"\n"
                    + ": onSensorChanged RGB data: \n"
                    + "红光(r)=" + red + "\n"
                    + "绿光(g)=" + green + "\n"
                    + "蓝光(b)=" + blue + "\n"
                    + "色温(ct)=" + ct+"\n");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

}
