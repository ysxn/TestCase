
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
 * RGB 读数在K7上红光（r）=650，绿光（g）=750，蓝光（b）=845，色温（ct）=4655 RGB Light
 * Sensor（颜色/光敏传感器）：测量光源的红、绿、蓝、白光的强度。典型应用：三星Adapt Display，也就是应用程序自适应屏幕；
 * <p>
 * 色温是表示光源光谱质量最通用的指标
 * 。一般用Tc表示。色温是按绝对黑体来定义的，光源的辐射在可见区和绝对黑体的辐射完全相同时，此时黑体的温度就称此光源的色温。
 * 低色温光源的特征是能量分布中，红辐射相对说要多些
 * ，通常称为“暖光”；色温提高后，能量分布中，蓝辐射的比例增加，通常称为“冷光”。一些常用光源的色温为：标准烛光为1930K
 * （开尔文温度单位）；钨丝灯为2760
 * -2900K；荧光灯为3000K；闪光灯为3800K；中午阳光为5600K；电子闪光灯为6000K；蓝天为12000-18000K。
 * <p>
 * 光源颜色
 * <p>
 * 光源的颜色常用色温这一概念来表示。光源发射光的颜色与黑体在某一温度下辐射光色相同时，黑体的温度称为该光源的色温。在黑体辐射中，随着温度不同，
 * 光的颜色各不相同，黑体呈现由红——橙红——黄——黄白——白——蓝白的渐变过程。某个光源所发射的光的颜色，看起来与黑体在某一个温度下所发射的光颜色相同时，
 * 黑体的这个温度称为该光源的色温。“黑体”的温度越高，光谱中蓝色的成份则越多，而红色的成份则越少。例如，白炽灯的光色是暖白色，其色温表示为2700K，
 * 而日光色荧光灯的色温表示方法则是6000K。
 * 某些放电光源，它发射光的颜色与黑体在各种温度下所发射的光颜色都不完全相同。所以在这种情况下用“相关色温”的概念
 * 。光源所发射的光的颜色与黑体在某一温度下发射的光的颜色最接近时，黑体的温度就称为该光源的相关色温。 光源色温不同，光色也不同，带来的感觉也不相同：
 * <p>
 * <3300K 温暖（带红的白色） 稳重、温暖
 * <p>
 * 3000－5000K 中间（白色） 爽快
 * <p>
 * >5000K 清凉型（带蓝的白色） 冷
 * <p>
 * 色温与亮度：高色温光源照射下，如亮度不高则给人们有一种阴冷的气氛；低色温光源照射下，亮度过高会给人们有一种闷热感觉。光色的对比：
 * 在同一空间使用两种光色差很大的光源，其对比将会出现层次效果，光色对比大时，在获得亮度层次的同时，又可获得光色的层次。
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

    private TextView mTextViewNativeOperate;

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

                    if (mTextViewNativeOperate != null) {
                        mTextViewNativeOperate.setText("native int=" + NativeOperate.getData());
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
        mTextViewNativeOperate = ((TextView) findViewById(R.id.native_operate));

        List<Sensor> ss = mSensorManager.getSensorList(RGB_SENSOR);
        if (ss == null || ss.size() == 0) {
            Toast.makeText(this, "获取RGB传感器为空，请检查传感器id是否为：" + RGB_SENSOR,
                    Toast.LENGTH_LONG)
                    .show();
            // finish();
            // return;
        } else {
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
        }

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
                    + ". \n" + mSensorBrief + "\n"
                    + ": onSensorChanged RGB data: \n"
                    + "红光(r)=" + red + "\n"
                    + "绿光(g)=" + green + "\n"
                    + "蓝光(b)=" + blue + "\n"
                    + "色温(ct)=" + ct + "\n");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

}
