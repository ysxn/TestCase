
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
 * RGB ������K7�Ϻ�⣨r��=650���̹⣨g��=750�����⣨b��=845��ɫ�£�ct��=4655
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

    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");
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
     * k7����33171013������x2��Ŀ�϶���Ϊ33171013
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
            Toast.makeText(this, "��ȡRGB������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + RGB_SENSOR,
                    Toast.LENGTH_LONG)
                    .show();
            finish();
            return;
        }
        Toast.makeText(this,
                "��ȡRGB������ok��getSensorList size=" + ss.size() + ",������idΪ��" + RGB_SENSOR,
                Toast.LENGTH_LONG)
                .show();
        StringBuilder sb = new StringBuilder();
        sb.append("getSensorList,size=" + ss.size() + ",������Type_idΪ=" + RGB_SENSOR + "; ");
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
            mTextViewFrontRGBSensorName.setText("û��ǰRGB������!");
        } else {
            mTextViewFrontRGBSensorName.setText("RGB�������ͺ�:" + mFrontRGBSensor.getName());
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
            mTextViewFrontRGBSensorData.setText("��ǰʱ�䣺" + date.format(System.currentTimeMillis())
                    + ". \n" + mSensorBrief+"\n"
                    + ": onSensorChanged RGB data: \n"
                    + "���(r)=" + red + "\n"
                    + "�̹�(g)=" + green + "\n"
                    + "����(b)=" + blue + "\n"
                    + "ɫ��(ct)=" + ct+"\n");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

}
