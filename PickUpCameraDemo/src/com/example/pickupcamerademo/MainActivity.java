
package com.example.pickupcamerademo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private final String TAG = "zyw";
    private final boolean DEBUG = true;
    public static final int TYPE_SENSOR_HUB_AIR_MOUSE = 33171001;
    public static final int TYPE_SENSOR_HUB_SNAP = 33171002;
    public static final int TYPE_SENSOR_HUB_SHAKE = 33171003;
    public static final int TYPE_SENSOR_HUB_TAP = 33171004;
    public static final int TYPE_SENSOR_HUB_FLIP = 33171005;
    public static final int TYPE_SENSOR_HUB_TWIST = 33171006;
    public static final int TYPE_SENSOR_HUB_TILT = 33171007;
    public static final int TYPE_SENSOR_HUB_PDR_HOLD = 33171008;
    public static final int TYPE_SENSOR_HUB_FALLING = 33171009;
    public static final int TYPE_SENSOR_HUB_RGB = 33171013;
    public static final int TYPE_SENSOR_HUB_PROXIMITY_GESTURE = 33171011;
    public static final int TYPE_SENSOR_HUB_CONTEXT_AWARENESS = 33171012;
    public static final int TYPE_SENSOR_HUB_SCREEN_ON = 33171010;

    private static final int REQUEST_UPDATE_DATA = 299;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");
    private String mSensorBrief = "";

    private float mSensorAcceData[] = {
            0, 0, 0, 0
    };
    private float mSensorProximityData[] = {
            0, 0, 0, 0
    };
    private float mSensorGyroData[] = {
            0, 0, 0, 0
    };

    SensorManager mSm;
    Sensor mSensorAirMouse;
    Sensor mSensorSnap;
    Sensor mSensorShake;
    Sensor mSensorTap;
    Sensor mSensorFlip;
    Sensor mSensorTwist;
    Sensor mSensorTilt;
    Sensor mSensorPDRHold;
    Sensor mSensorFalling;
    Sensor mSensorRGB;
    Sensor mSensorGesture;
    Sensor mSensorContextAwareness;
    Sensor mSensorScreenOn;

    Sensor mSensorAcce;
    Sensor mSensorGyro;
    Sensor mSensorOrien;
    Sensor mSensorProximity;

    private TextView mTips;
    private TextView mData;

    private final int REQUEST_CAPTURE = 0x2341;

    private boolean mFlagStartMonitor = false;
    private boolean mFlagProximityNear = false;
    private boolean mFlagActivityResume = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    if (mData != null) {
                        mData.setText(">>>>>Proximity : " + mSensorProximityData[0]
                                + ",   mFlagProximityNear="
                                + mFlagProximityNear + "\n"
                                + ">>>>>acce the x-axis:" + mSensorAcceData[0] + "\n"
                                + ">>>>>acce the y-axis:" + mSensorAcceData[1] + "\n"
                                + ">>>>>acce the y-axis:" + mSensorAcceData[2] + "\n"
                                + "mFlagStartMonitor=" + mFlagStartMonitor + "\n"
                                + ">>>>>Angular speed around the x-axis:" + mSensorGyroData[0]
                                + "\n"
                                + ">>>>>Angular speed around the y-axis:" + mSensorGyroData[1]
                                + "\n"
                                + ">>>>>Angular speed around the z-axis:" + mSensorGyroData[2]
                                + "\n"
                                + "mFlagActivityResume=" + mFlagActivityResume);
                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTips = (TextView) findViewById(R.id.sensor_tips);
        mData = (TextView) findViewById(R.id.sensor_data);
        Button b = (Button) findViewById(R.id.button_exit);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        StringBuilder sb = new StringBuilder();

        mSm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorAcce = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyro = mSm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorOrien = mSm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorProximity = mSm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mSensorBrief = sb.toString();
        // mTips.setText(mSensorBrief);
        mTips.setText("距离传感器被挡住代表手机在裤子口袋或包里面,这时候也是初始情况之一。 或者手机水平放在桌面上静止不动，这也是初始情况之一。， \n当用户拿起手机横屏对准前方， 立即启动后Camera。 \n如果是拿起后竖屏，本应该打开前camera，目前DEMO只能打开后camera，因为标准api不能指定前后camera，除非camera应用修改定制过。");
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        AlarmAlertWakeLock.acquireScreenCpuWakeLock(this);
        mFlagActivityResume = true;
        if (mSensorAcce != null) {
            mSm.registerListener(this, mSensorAcce, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorProximity != null) {
            mSm.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorGyro != null) {
            mSm.registerListener(this, mSensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mFlagActivityResume = false;
        super.onPause();
        mSm.unregisterListener(this);
        AlarmAlertWakeLock.releaseCpuLock();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSensorAcceData[0] = event.values[0];
            mSensorAcceData[1] = event.values[1];
            mSensorAcceData[2] = event.values[2];
            if (mFlagActivityResume && !mFlagStartMonitor
                    && (mFlagProximityNear || (Math.abs(mSensorAcceData[0]) <= 1
                            && Math.abs(mSensorAcceData[1]) <= 1
                            && mSensorAcceData[2] >= 9))) {
                // 距离传感器被挡住代表手机在裤子口袋或包里面,这时候也是初始情况之一。
                // 手机水平放在桌面上静止不动，这也是初始情况之一。
                mFlagStartMonitor = true;
            } else if (mFlagActivityResume && !mFlagProximityNear && mFlagStartMonitor
                    && Math.abs(mSensorAcceData[0]) >= 8
                    && Math.abs(mSensorAcceData[1]) <= 3 && Math.abs(mSensorAcceData[2]) <= 3) {
                mFlagStartMonitor = false;
                launchBackCamera();
            } else if (mFlagActivityResume && !mFlagProximityNear && mFlagStartMonitor
                    && Math.abs(mSensorAcceData[0]) <= 3
                    && mSensorAcceData[1] >= 8
                    && Math.abs(mSensorAcceData[2]) <= 4) {
                mFlagStartMonitor = false;
                launchFrontCamera();
            }
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            mSensorGyroData[0] = Math.abs(event.values[0]) > 0.01f ? event.values[0] : 0f;
            mSensorGyroData[1] = Math.abs(event.values[1]) > 0.01f ? event.values[1] : 0f;
            mSensorGyroData[2] = Math.abs(event.values[2]) > 0.01f ? event.values[2] : 0f;
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            mSensorProximityData[0] = event.values[0];
            mFlagProximityNear = mSensorProximityData[0] == 0f;
        }

        Log.i("zzzz", ">>>>>acce == (" + mSensorAcceData[0]
                + ", "
                + mSensorAcceData[1]
                + ", "
                + mSensorAcceData[2]
                + ")   >>>>>Angular speed around the x-axis:" + mSensorGyroData[0]
                + ", y-axis:" + mSensorGyroData[1]
                + ", z-axis:" + mSensorGyroData[2]);
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    private void launchBackCamera() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        // | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        // | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void launchFrontCamera() {
        Intent intent = new Intent();
        // intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
        // | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        // | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void launchCameraForResult() {
        String imgPath = "/sdcard/test/img.jpg";
        File vFile = new File(imgPath);

        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile(); // new
                                                   // File(vFile.getParent());
            vDirPath.mkdirs();
        }

        Uri uri = Uri.fromFile(vFile);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
        startActivityForResult(i, REQUEST_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    // iViewPic.setImageURI(Uri.fromFile(new File(imgPath)));
                    // 假设不传参数MediaStore.EXTRA_OUTPUT的情况下，onActivityResult函数在resultCode为RESULT_OK的情况下，data返回的参数是经过实际拍摄照片经过缩放的图像数据，可以通过类似如下方法来打印缩放图像的尺寸
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    Log.d(TAG, "bmp width:" + bmp.getWidth() + ", height:" + bmp.getHeight());
                }
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        // super.onConfigurationChanged(newConfig);
    }

}
