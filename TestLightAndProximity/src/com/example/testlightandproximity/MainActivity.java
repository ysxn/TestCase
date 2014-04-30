
package com.example.testlightandproximity;

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
 * frameworks\base\core\res\res\values\config.xml里面config_autoBrightnessLevels这个
 * ,还有对应这个autoBrightnessLcdBacklightValues
 * 代码是这个frameworks\base\services\java\com
 * \android\server\power\DisplayPowerController.java
 * 关系是这个config_autoBrightnessLevels 范围是0~10240，单位是流明。
 * autoBrightnessLcdBacklightValues这个是给LCD背光，范围0~255。 驱动那边的.als_value
 * 正好和config_autoBrightnessLevels 是同一个单位。
 * 
 * @author zhuyw1
 */
public class MainActivity extends Activity implements SensorEventListener {

    public static final long GB_IN_BYTES = 1073741824L;
    public static final long KB_IN_BYTES = 1024L;
    public static final long MB_IN_BYTES = 1048576L;
    private static final int REQUEST_UPDATE_DATA = 299;
    private float mAmbientLightLevel = 0.0F;

    private long mCurrentTime = SystemClock.elapsedRealtime();
    private Sensor mGSensor;
    private TextView mTextViewGSensorName;

    private Sensor mLightSensor;
    private float mProximitySensorData = 0.0F;

    private Sensor mProximitySensor;

    private long mRxAll = TrafficStats.getTotalRxBytes();
    private SensorManager mSensorManager;
    private TextView mTextViewLightSensorName;
    private TextView mTextViewProximitySensorName;
    private TextView mTextViewProximityData;
    private TextView mTextViewLightSensorData;
    private TextView mTextViewLightMode;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);

                    if (mTextViewLightMode != null) {
                        boolean isAutoBrightness = Settings.System.getInt(
                                getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS_MODE,
                                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

                        int brightness = Settings.System.getInt(
                                getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS, -1);

                        mTextViewLightMode.setText("当前是否自动背光 = "
                                + isAutoBrightness + ",自动背光模式下本数值恒定, 背光亮度(0~255)= " + brightness);

                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 300L);
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

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mTextViewLightSensorName = ((TextView) findViewById(R.id.light_sensor_name));
        mTextViewProximitySensorName = ((TextView) findViewById(R.id.proximity_sensor_name));
        mTextViewProximityData = ((TextView) findViewById(R.id.proximity_data));
        mTextViewLightSensorData = ((TextView) findViewById(R.id.light_data));
        mTextViewLightMode = ((TextView) findViewById(R.id.light_mode));

        if (mLightSensor == null)
        {
            mTextViewLightSensorName.setText("没有光传感器!");
        } else {
            mTextViewLightSensorName.setText("光传感器型号:"+mLightSensor.getName());
        }

        if (mProximitySensor == null) {
            mTextViewProximitySensorName.setText("没有接近传感器!");
        } else {
            mTextViewProximitySensorName.setText("接近传感器型号:"+mProximitySensor.getName());
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

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        Log.e("zyw", ">>>" + localDisplayMetrics.toString() + ", densityDpi="
                + localDisplayMetrics.densityDpi);
        Toast.makeText(
                this,
                ">>>" + localDisplayMetrics.toString() + ", densityDpi="
                        + localDisplayMetrics.densityDpi, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mSensorManager.unregisterListener(this, mLightSensor);
        mSensorManager.unregisterListener(this, mProximitySensor);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent paramSensorEvent) {
        // TODO Auto-generated method stub
        /**
         * 勒克斯（Lux，通常简写为lx）是一个标识照度的国际单位制单位，1流明每平方米面积，就是1勒克斯。 其单位换算是 1勒克斯 = 1
         * 流明/平方米 = 1 坎德拉·球面度/平方米（1 lx = 1 lm/m2= 1 cd·sr·m–2）。
         */
        if (paramSensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            mAmbientLightLevel = paramSensorEvent.values[0];
            /**
             * Sensor.TYPE_LIGHT: values[0]: Ambient light level in SI lux units
             */
            mTextViewLightSensorData.setText("光传感器读数 Ambient Light Level : "
                    + mAmbientLightLevel + "勒克斯(单位SI lux units)"
                    + "; MaximumRange=" + mLightSensor.getMaximumRange() + "; Resolution="
                    + mLightSensor.getResolution() + "; the power in mA used by ="
                    + mLightSensor.getPower());
        } else if (paramSensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            /**
             * Sensor.TYPE_PROXIMITY: values[0]: Proximity sensor distance
             * measured in centimeters Note: Some proximity sensors only support
             * a binary near or far measurement. In this case, the sensor should
             * report its maximum range value in the far state and a lesser
             * value in the near state.
             */
            mProximitySensorData = paramSensorEvent.values[0];
            mTextViewProximityData.setText("接近传感器读数 : " + mProximitySensorData
                    + " 厘米" + "; MaximumRange=" + mProximitySensor.getMaximumRange()
                    + "; Resolution="
                    + mProximitySensor.getResolution() + "; the power in mA used by ="
                    + mProximitySensor.getPower());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

}
