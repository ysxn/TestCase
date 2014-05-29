
package com.example.pickupcamerademo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import android.os.SystemClock;
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

    private SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");
    private String mSensorBrief = "";
    private long mStartDelayTime = 1L;// System.currentTimeMillis();

    private float mSensorAcceData[] = {
            0, 0, 0, 0
    };
    private float mSensorProximityData[] = {
            0, 0, 0, 0
    };
    private float mSensorGyroData[] = {
            0, 0, 0, 0
    };
    private float mSensorOrienData[] = {
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

    private ArrayList<AcceData> mArrayListSensorAcceDataFront = new ArrayList<AcceData>(100);
    private ArrayList<AcceData> mArrayListSensorAcceDataBack = new ArrayList<AcceData>(100);
    private boolean mFlagReckoning = false;
    private boolean mFlagPreReckoning = false;
    private long mPreReckoningTime = 0L;
    private AcceData mPreReckoningAcceData = null;
    private final int REQUEST_CAPTURE = 0x2341;

    private boolean mFlagStartMonitor = false;
    private boolean mFlagProximityNear = false;
    private boolean mFlagActivityResume = false;

    private static final int REQUEST_UPDATE_DATA = 299;
    private static final int REQUEST_RECKON_DATA = 399;

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
                                + ">>>>>acce the z-axis:" + mSensorAcceData[2] + "\n"
                                + "mFlagStartMonitor=" + mFlagStartMonitor + "\n"
                                + ">>>>>Angular speed around the x-axis:" + mSensorGyroData[0]
                                + "\n"
                                + ">>>>>Angular speed around the y-axis:" + mSensorGyroData[1]
                                + "\n"
                                + ">>>>>Angular speed around the z-axis:" + mSensorGyroData[2]
                                + "\n"
                                // 就是绕z轴转动的角度，0度=正北:
                                + ">>>>>azimuth 方位角：" + mSensorOrienData[0]
                                + "\n"
                                // 绕X轴转动的角度 (-180<=pitch<=180),
                                // 如果设备水平放置，前方向下俯就是正:
                                + ">>>>>pitch 仰俯：" + mSensorOrienData[1]
                                + "\n"
                                // 绕Y轴转动(-90<=roll<=90)，向左翻滚是正值:
                                + ">>>>>roll 滚转：" + mSensorOrienData[2]
                                + "\n"
                                + "mFlagActivityResume=" + mFlagActivityResume);
                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
                break;
                case REQUEST_RECKON_DATA: {
                    mHandler.removeMessages(REQUEST_RECKON_DATA);
                    Log.i("zzzz", ">>>>>handler REQUEST_RECKON_DATA");
                    reckonLaunchFrontCameraHistory();
                }
                break;
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

        if (mSensorOrien != null) {
            mSm.registerListener(this, mSensorOrien, SensorManager.SENSOR_DELAY_NORMAL);
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
            if (mFlagReckoning)
                return;
            mSensorAcceData[0] = event.values[0];
            mSensorAcceData[1] = event.values[1];
            mSensorAcceData[2] = event.values[2];
            long time = System.currentTimeMillis();

            AcceData data = new AcceData(time, mSensorAcceData[0],
                    mSensorAcceData[1],
                    mSensorAcceData[2]);
            if (mArrayListSensorAcceDataFront.size() < 20) {
                mArrayListSensorAcceDataFront.add(data);
            } else if (mArrayListSensorAcceDataBack.size() < 20) {
                mArrayListSensorAcceDataBack.add(data);
            } else {
                Log.i("zzzz", ">>>>>swap"
                        +", front size="+mArrayListSensorAcceDataFront.size()
                        +", back  size="+mArrayListSensorAcceDataBack.size());
                if (mArrayListSensorAcceDataFront.get(mArrayListSensorAcceDataFront.size()-1).time > mArrayListSensorAcceDataBack
                        .get(mArrayListSensorAcceDataBack.size()-1).time) {
                    mArrayListSensorAcceDataBack.clear();
                    mArrayListSensorAcceDataBack.add(data);
                } else {
                    mArrayListSensorAcceDataFront.clear();
                    mArrayListSensorAcceDataFront.add(data);
                }
            }
            if (false && mFlagActivityResume && !mFlagStartMonitor
                    && (mFlagProximityNear || (Math.abs(mSensorAcceData[0]) <= 1
                            && Math.abs(mSensorAcceData[1]) <= 1
                            && mSensorAcceData[2] >= 9))) {
                // 距离传感器被挡住代表手机在裤子口袋或包里面,这时候也是初始情况之一。
                // 手机水平放在桌面上静止不动，这也是初始情况之一。
                mFlagStartMonitor = true;
            } else if (mFlagActivityResume && !mFlagPreReckoning
                    && Math.abs(mSensorAcceData[0]) >= 8
                    && Math.abs(mSensorAcceData[1]) <= 3 && Math.abs(mSensorAcceData[2]) <= 3) {
//                mFlagStartMonitor = false;
//                launchBackCamera();
//                mArrayListSensorAcceDataBack.clear();
//                mArrayListSensorAcceDataFront.clear();
                mPreReckoningAcceData = data;
                mPreReckoningTime = time;
                mFlagPreReckoning = true;
                reckonLaunchBackCameraHistory();
            } else if (mFlagActivityResume && !mFlagProximityNear && !mFlagPreReckoning
                    && Math.abs(mSensorAcceData[0]) <= 3
                    && mSensorAcceData[1] >= 8
                    && Math.abs(mSensorAcceData[2]) <= 4) {
                // mFlagStartMonitor = false;

                mPreReckoningAcceData = data;
                mPreReckoningTime = time;
                mFlagPreReckoning = true;
                reckonLaunchFrontCameraHistory();
                //Log.i("zzzz", ">>>>>sendEmptyMessageDelayed REQUEST_RECKON_DATA");
                //mHandler.sendEmptyMessageDelayed(REQUEST_RECKON_DATA, 2000);

            }
            Log.i("zzzz", ">>>>>acce == (" + mSensorAcceData[0]
                    + ", "
                    + mSensorAcceData[1]
                    + ", "
                    + mSensorAcceData[2]
                    + ")"
                    + ", time=" + System.currentTimeMillis());
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            mSensorGyroData[0] = Math.abs(event.values[0]) > 0.01f ? event.values[0] : 0f;
            mSensorGyroData[1] = Math.abs(event.values[1]) > 0.01f ? event.values[1] : 0f;
            mSensorGyroData[2] = Math.abs(event.values[2]) > 0.01f ? event.values[2] : 0f;
            long time = System.currentTimeMillis();
            Log.i("zzzz", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> the x-axis:"
                    + mSensorGyroData[0]
                    + ", y-axis:" + mSensorGyroData[1]
                    + ", z-axis:" + mSensorGyroData[2]
                    + ", time=" + System.currentTimeMillis());
        } else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            mSensorOrienData[0] = event.values[0];
            mSensorOrienData[1] = event.values[1];
            mSensorOrienData[2] = event.values[2];
            long time = System.currentTimeMillis();
            Log.i("zzzz",
                    ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> pitch :"
                            + mSensorOrienData[1]
                            + ", roll :" + mSensorOrienData[2]
                            + ", time=" + System.currentTimeMillis());
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            mSensorProximityData[0] = event.values[0];
            mFlagProximityNear = mSensorProximityData[0] == 0f;
        }

        // Log.i("zzzz", ">>>>>acce == (" + mSensorAcceData[0]
        // + ", "
        // + mSensorAcceData[1]
        // + ", "
        // + mSensorAcceData[2]
        // + ")   >>>>>Angular speed around the x-axis:" + mSensorGyroData[0]
        // + ", y-axis:" + mSensorGyroData[1]
        // + ", z-axis:" + mSensorGyroData[2]);
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    private void reckonLaunchBackCameraHistory() {
        // TODO Auto-generated method stub
        Log.i("zzzz", ">>>>>reckonLaunch <<Back>> CameraHistory start");
        mFlagReckoning = true;
        ArrayList<AcceData> mArrayListSensorAcceData = new ArrayList<AcceData>(100);
        Iterator<AcceData> iteratorFront =
                mArrayListSensorAcceDataFront.iterator();
        Iterator<AcceData> iteratorBack =
                mArrayListSensorAcceDataBack.iterator();
        if (mArrayListSensorAcceDataFront.size() > 0
                && mArrayListSensorAcceDataBack.size() <= 0) {
            while (iteratorFront.hasNext()) {
                AcceData acceData = (AcceData) iteratorFront.next();
                mArrayListSensorAcceData.add(acceData);
                Log.i("zzzz",
                        ">>>>>>>AcceDataFront time = " + acceData.time
                                + ", x=" + acceData.x
                                + ", y=" + acceData.y
                                + ", z=" + acceData.z);
            }
        } else if (mArrayListSensorAcceDataFront.size() <= 0
                && mArrayListSensorAcceDataBack.size() > 0) {
            while (iteratorBack.hasNext()) {
                AcceData acceData = (AcceData) iteratorBack.next();
                mArrayListSensorAcceData.add(acceData);
                Log.i("zzzz",
                        ">>>>>>>AcceDataBack  time = " + acceData.time
                                + ", x=" + acceData.x
                                + ", y=" + acceData.y
                                + ", z=" + acceData.z);
            }
        } else if (mArrayListSensorAcceDataFront.size() > 0
                && mArrayListSensorAcceDataBack.size() > 0) {
            if (mArrayListSensorAcceDataFront.get(mArrayListSensorAcceDataFront.size() - 1).time < mArrayListSensorAcceDataBack
                    .get(mArrayListSensorAcceDataBack.size() - 1).time) {
                while (iteratorFront.hasNext()) {
                    AcceData acceData = (AcceData) iteratorFront.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataFront time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }

                while (iteratorBack.hasNext()) {
                    AcceData acceData = (AcceData) iteratorBack.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataBack  time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }
            } else {
                while (iteratorBack.hasNext()) {
                    AcceData acceData = (AcceData) iteratorBack.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataBack  time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }

                while (iteratorFront.hasNext()) {
                    AcceData acceData = (AcceData) iteratorFront.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataFront time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }
            }
        }

        boolean checkOk = true;
        int totalLength = mArrayListSensorAcceData.size();
        if (totalLength > 10 && mPreReckoningAcceData != null
                && mPreReckoningTime > 0) {
            int indexOfPreReckoningAcceData = mArrayListSensorAcceData
                    .indexOf(mPreReckoningAcceData);
            if (indexOfPreReckoningAcceData > 0
                    && indexOfPreReckoningAcceData < totalLength) {

                for (int i = indexOfPreReckoningAcceData; i < totalLength; i++) {
                    AcceData checkD = mArrayListSensorAcceData.get(i);
                    if (Math.abs(checkD.x) >= 8
                            && Math.abs(checkD.y) <= 3 && Math.abs(checkD.z) <= 3) {
                        Log.i("zzzz",
                                ">>>>>>>AcceData checkOk = true  stable time = " + checkD.time
                                        + ", x=" + checkD.x
                                        + ", y=" + checkD.y
                                        + ", z=" + checkD.z
                                        + ", indexOfPreReckoningAcceData="+indexOfPreReckoningAcceData
                                        + ", totalLength="+totalLength);
                    } else {
                        checkOk = false;
                        Log.i("zzzz",
                                ">>>>>>>AcceData checkOk = false stable time = " + checkD.time
                                        + ", x=" + checkD.x
                                        + ", y=" + checkD.y
                                        + ", z=" + checkD.z
                                        + ", indexOfPreReckoningAcceData="+indexOfPreReckoningAcceData
                                        + ", totalLength="+totalLength);
                    }
                }
                int startIndex = 0;
                AcceData startData = null;
                for (int i = indexOfPreReckoningAcceData; i > 1; i--) {
                    AcceData check = mArrayListSensorAcceData.get(i);
                    if (Math.abs(check.x) <= 1
                            /*&& check.y < 2*/
                            && check.z > 7) {
                        startIndex = i;
                        startData = check;
                        Log.i("zzzz",
                                ">>>>>>>AcceData  find startIndex time = " + check.time
                                        + ", x=" + check.x
                                        + ", y=" + check.y
                                        + ", z=" + check.z
                                        + ", startIndex="+startIndex);
                        break;
                    }
                }
                if (startIndex == 0 || startData == null) {
                    checkOk = false;
                    Log.i("zzzz",
                            ">>>>>>>AcceData  find startIndex time fail");
                } else {
                    for (int i = indexOfPreReckoningAcceData; i > startIndex; i--) {
                        AcceData checkUp = mArrayListSensorAcceData.get(i);
                        AcceData checkDown = mArrayListSensorAcceData.get(i-1);
                        if (/*Math.abs(checkUp.x) <= 3
                                && Math.abs(checkDown.x) <= 3
                                && */checkUp.x >= checkDown.x
                                && (checkUp.z <= checkDown.z || (checkDown.z > 8 && checkUp.z > 8))) {
                            Log.i("zzzz",
                                    ">>>>>>>AcceData checkOk = true  checkDown time = " + checkDown.time
                                            + ", x=" + checkDown.x
                                            + ", y=" + checkDown.y
                                            + ", z=" + checkDown.z);
                        } else {
                            checkOk = false;
                            Log.i("zzzz",
                                    ">>>>>>>AcceData checkOk = false checkDown time = " + checkDown.time
                                            + ", x=" + checkDown.x
                                            + ", y=" + checkDown.y
                                            + ", z=" + checkDown.z);
                        }
                    }
                }
            }
            if (checkOk) launchBackCamera();
        }
        mPreReckoningAcceData = null;
        mArrayListSensorAcceDataBack.clear();
        mArrayListSensorAcceDataFront.clear();
        mFlagReckoning = false;
        mFlagPreReckoning = false;
        mPreReckoningTime = 0L;
        Log.i("zzzz", ">>>>>reckonLaunch <<Back>> CameraHistory end");
    }

    private void reckonLaunchFrontCameraHistory() {
        Log.i("zzzz", ">>>>>reckonLaunchFrontCameraHistory start");
        mFlagReckoning = true;
        ArrayList<AcceData> mArrayListSensorAcceData = new ArrayList<AcceData>(100);
        Iterator<AcceData> iteratorFront =
                mArrayListSensorAcceDataFront.iterator();
        Iterator<AcceData> iteratorBack =
                mArrayListSensorAcceDataBack.iterator();
        if (mArrayListSensorAcceDataFront.size() > 0
                && mArrayListSensorAcceDataBack.size() <= 0) {
            while (iteratorFront.hasNext()) {
                AcceData acceData = (AcceData) iteratorFront.next();
                mArrayListSensorAcceData.add(acceData);
                Log.i("zzzz",
                        ">>>>>>>AcceDataFront time = " + acceData.time
                                + ", x=" + acceData.x
                                + ", y=" + acceData.y
                                + ", z=" + acceData.z);
            }
        } else if (mArrayListSensorAcceDataFront.size() <= 0
                && mArrayListSensorAcceDataBack.size() > 0) {
            while (iteratorBack.hasNext()) {
                AcceData acceData = (AcceData) iteratorBack.next();
                mArrayListSensorAcceData.add(acceData);
                Log.i("zzzz",
                        ">>>>>>>AcceDataBack  time = " + acceData.time
                                + ", x=" + acceData.x
                                + ", y=" + acceData.y
                                + ", z=" + acceData.z);
            }
        } else if (mArrayListSensorAcceDataFront.size() > 0
                && mArrayListSensorAcceDataBack.size() > 0) {
            if (mArrayListSensorAcceDataFront.get(mArrayListSensorAcceDataFront.size() - 1).time < mArrayListSensorAcceDataBack
                    .get(mArrayListSensorAcceDataBack.size() - 1).time) {
                while (iteratorFront.hasNext()) {
                    AcceData acceData = (AcceData) iteratorFront.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataFront time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }

                while (iteratorBack.hasNext()) {
                    AcceData acceData = (AcceData) iteratorBack.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataBack  time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }
            } else {
                while (iteratorBack.hasNext()) {
                    AcceData acceData = (AcceData) iteratorBack.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataBack  time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }

                while (iteratorFront.hasNext()) {
                    AcceData acceData = (AcceData) iteratorFront.next();
                    mArrayListSensorAcceData.add(acceData);
                    Log.i("zzzz",
                            ">>>>>>>AcceDataFront time = " + acceData.time
                                    + ", x=" + acceData.x
                                    + ", y=" + acceData.y
                                    + ", z=" + acceData.z);
                }
            }
        }

        boolean checkOk = true;
        int totalLength = mArrayListSensorAcceData.size();
        if (totalLength > 10 && mPreReckoningAcceData != null
                && mPreReckoningTime > 0) {
            int indexOfPreReckoningAcceData = mArrayListSensorAcceData
                    .indexOf(mPreReckoningAcceData);
            if (indexOfPreReckoningAcceData > 0
                    && indexOfPreReckoningAcceData < totalLength) {

                for (int i = indexOfPreReckoningAcceData; i < totalLength; i++) {
                    AcceData checkD = mArrayListSensorAcceData.get(i);
                    if (Math.abs(checkD.x) <= 3
                            && checkD.y >= 8
                            && Math.abs(checkD.z) <= 4) {
                        Log.i("zzzz",
                                ">>>>>>>AcceData checkOk = true  stable time = " + checkD.time
                                        + ", x=" + checkD.x
                                        + ", y=" + checkD.y
                                        + ", z=" + checkD.z
                                        + ", indexOfPreReckoningAcceData="+indexOfPreReckoningAcceData
                                        + ", totalLength="+totalLength);
                    } else {
                        checkOk = false;
                        Log.i("zzzz",
                                ">>>>>>>AcceData checkOk = false stable time = " + checkD.time
                                        + ", x=" + checkD.x
                                        + ", y=" + checkD.y
                                        + ", z=" + checkD.z
                                        + ", indexOfPreReckoningAcceData="+indexOfPreReckoningAcceData
                                        + ", totalLength="+totalLength);
                    }
                }
                int startIndex = 0;
                AcceData startData = null;
                for (int i = indexOfPreReckoningAcceData; i > 1; i--) {
                    AcceData check = mArrayListSensorAcceData.get(i);
                    if (/*Math.abs(check.x) <= 3
                            && */check.y < 2
                            && check.z > 7) {
                        startIndex = i;
                        startData = check;
                        Log.i("zzzz",
                                ">>>>>>>AcceData  find startIndex time = " + check.time
                                        + ", x=" + check.x
                                        + ", y=" + check.y
                                        + ", z=" + check.z
                                        + ", startIndex="+startIndex);
                        break;
                    }
                }
                if (startIndex == 0 || startData == null) {
                    checkOk = false;
                    Log.i("zzzz",
                            ">>>>>>>AcceData  find startIndex time fail");
                } else {
                    for (int i = indexOfPreReckoningAcceData; i > startIndex; i--) {
                        AcceData checkUp = mArrayListSensorAcceData.get(i);
                        AcceData checkDown = mArrayListSensorAcceData.get(i-1);
                        if (/*Math.abs(checkUp.x) <= 3
                                && Math.abs(checkDown.x) <= 3
                                && */checkUp.y >= checkDown.y
                                && (checkUp.z <= checkDown.z || (checkDown.z > 8 && checkUp.z > 8))) {
                            Log.i("zzzz",
                                    ">>>>>>>AcceData checkOk = true  checkDown time = " + checkDown.time
                                            + ", x=" + checkDown.x
                                            + ", y=" + checkDown.y
                                            + ", z=" + checkDown.z);
                        } else {
                            checkOk = false;
                            Log.i("zzzz",
                                    ">>>>>>>AcceData checkOk = false checkDown time = " + checkDown.time
                                            + ", x=" + checkDown.x
                                            + ", y=" + checkDown.y
                                            + ", z=" + checkDown.z);
                        }
                    }
                }
            }
            if (checkOk) launchFrontCamera();
        }
        mPreReckoningAcceData = null;
        mArrayListSensorAcceDataBack.clear();
        mArrayListSensorAcceDataFront.clear();
        mFlagReckoning = false;
        mFlagPreReckoning = false;
        mPreReckoningTime = 0L;
        Log.i("zzzz", ">>>>>reckonLaunchFrontCameraHistory end");
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

    private class AcceData {

        public AcceData(long l, float f, float g, float h) {
            // TODO Auto-generated constructor stub
            time = l;
            x = f;
            y = g;
            z = h;
        }

        public long time = 0L;
        public float x = 999;
        public float y = 999;
        public float z = 999;
    }

    private class GyroData {

        public GyroData(long l, float f, float g, float h) {
            // TODO Auto-generated constructor stub
            time = l;
            x = f;
            y = g;
            z = h;
        }

        public long time = 0L;
        public float x = 999;
        public float y = 999;
        public float z = 999;
    }

    private class OrienData {

        public OrienData(long l, float f, float g) {
            // TODO Auto-generated constructor stub
            time = l;
            pitch = f;
            roll = g;
        }

        public long time = 0L;
        public float pitch = 999;
        public float roll = 999;
    }

}
