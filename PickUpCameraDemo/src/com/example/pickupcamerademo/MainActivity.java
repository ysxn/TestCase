
package com.example.pickupcamerademo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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

    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");
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
    private float mSensorScreenOnData[] = {
            0, 0, 0, 0
    };
    private float mSensorScreenOnLogData = 0f;
    private boolean mIsScreenOn = true;

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
    private Button mPause;
    private Button mResume;
    public static final String EXTRAS_CAMERA_FACING = "android.intent.extras.CAMERA_FACING";
    public static final int EXTRAS_CAMERA_FACING_BACK = 0;
    public static final int EXTRAS_CAMERA_FACING_FRONT = 1;
    public static final int SENSOR_DATA_FACING_FRONT = 3;
    public static final int SENSOR_DATA_FACING_BACK = 2;

    //private ArrayList<AcceData> mArrayListSensorAcceDataFront = new ArrayList<AcceData>(100);
    //private ArrayList<AcceData> mArrayListSensorAcceDataBack = new ArrayList<AcceData>(100);
    private BufferStack<AcceData> mBufferStack = new BufferStack<AcceData>(20);
    private boolean mFlagReckoning = false;
    private boolean mFlagPreReckoning = false;
    private long mPreReckoningTime = 0L;
    private AcceData mPreReckoningAcceData = null;
    private final int REQUEST_CAPTURE = 0x2341;
    
  //�������̹�����
    KeyguardManager mKeyguardManager = null;    
    //����������
    private KeyguardLock mKeyguardLock = null;

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
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    if (mData != null) {
                        if (false) {
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
                                    // ������z��ת���ĽǶȣ�0��=����:
                                    + ">>>>>azimuth ��λ�ǣ�" + mSensorOrienData[0]
                                    + "\n"
                                    // ��X��ת���ĽǶ� (-180<=pitch<=180),
                                    // ����豸ˮƽ���ã�ǰ�����¸�������:
                                    + ">>>>>pitch ������" + mSensorOrienData[1]
                                    + "\n"
                                    // ��Y��ת��(-90<=roll<=90)�����󷭹�����ֵ:
                                    + ">>>>>roll ��ת��" + mSensorOrienData[2]
                                    + "\n"
                                    + "mFlagActivityResume=" + mFlagActivityResume);
                        }
                    }
                    String sC = "��ǰʱ�䣺" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    //���WM�µ�view�����������ͽ���
                    //Ų��service����
                    if (mSensorScreenOnLogData == SENSOR_DATA_FACING_FRONT) {
                        //����
                        if (!mIsScreenOn) {
                            AlarmAlertWakeLock.acquireScreenCpuWakeLock(MainActivity.this);
                        }
                        //����
                        //������ʾ��������
                        mKeyguardLock.disableKeyguard(); 
                        //�ж�camera�Ƿ��Ѿ������������ظ�����camera
                        
                        //�Լ��޸�camera������ǰ��
                        launchFrontCamera();
                    } else if (mSensorScreenOnLogData == SENSOR_DATA_FACING_BACK) {
                        //����
                        if (!mIsScreenOn) {
                            AlarmAlertWakeLock.acquireScreenCpuWakeLock(MainActivity.this);
                        }
                        //����
                        //������ʾ��������
                        mKeyguardLock.disableKeyguard();
                        
                        //�ж�camera�Ƿ��Ѿ������������ظ�����camera
                        
                        //�Լ��޸�camera������ǰ��
                        launchBackCamera();
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
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
    
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.i(TAG, ">>>>>>>>>>>>ACTION_SCREEN_intent="+intent);
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                Log.i(TAG, ">>>>>>>>>>>>ACTION_SCREEN_OFF");
                mIsScreenOn = false;
            } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                Log.i(TAG, ">>>>>>>>>>>>ACTION_SCREEN_ON");
                mIsScreenOn = true;
                AlarmAlertWakeLock.releaseCpuLock();
            }
        }
        
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        MainActivity.this.registerReceiver(mBroadcastReceiver, filter);
        
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        //��ʼ��������������������⿪������
        mKeyguardLock = mKeyguardManager.newKeyguardLock("zhuyawen"); 

        mTips = (TextView) findViewById(R.id.sensor_tips);
        mData = (TextView) findViewById(R.id.sensor_data);
        Button b = (Button) findViewById(R.id.button_exit);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSm.unregisterListener(MainActivity.this);
                AlarmAlertWakeLock.releaseCpuLock();
                //��Ļ����
                mKeyguardLock.reenableKeyguard();
                finish();
            }
        });
        
        mPause = (Button) findViewById(R.id.button_pause);
        mResume = (Button) findViewById(R.id.button_resume);
        mPause.setEnabled(false);
        mResume.setEnabled(false);
        mPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                mSm.unregisterListener(MainActivity.this);
//                mPause.setEnabled(false);
//                mResume.setEnabled(true);
                launchBackCamera();
            }
        });
        mResume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mSensorScreenOn != null) {
//                    mSm.registerListener(MainActivity.this, mSensorScreenOn, SensorManager.SENSOR_DELAY_NORMAL);
//                    mPause.setEnabled(true);
//                    mResume.setEnabled(false);
                }
                launchFrontCamera();
            }
        });

        StringBuilder sb = new StringBuilder();

        mSm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorAcce = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyro = mSm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorOrien = mSm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorProximity = mSm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        List<Sensor> ss;

        ss = mSm.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON);
        if (ss == null || ss.size() == 0) {
            sb.append("��ȡpick up����������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_SCREEN_ON);
        } else {
            mSensorScreenOn = ss.get(0);
            sb.append("��ȡpick up����������OK,size=" + ss.size() + ",������Type_idΪ="
                    + TYPE_SENSOR_HUB_SCREEN_ON + "; ");
        }
        sb.append("\n");
        
        mSensorBrief = sb.toString()+"\n"
                +"�����������׼ǰ��DATAΪ3�Ϳ���_ǰ����ͷ��" 
                +"\n"
                +"����Ϊ2�Ϳ���_������ͷ," 
                +"\n"
                +"ֵ0Ϊ��ʼֵ��";
        mTips.setText(mSensorBrief);
        //mTips.setText("���봫��������ס�����ֻ��ڿ��ӿڴ��������,��ʱ��Ҳ�ǳ�ʼ���֮һ�� �����ֻ�ˮƽ���������Ͼ�ֹ��������Ҳ�ǳ�ʼ���֮һ���� \n���û������ֻ�������׼ǰ���� ����������Camera�� \n������������������Ӧ�ô�ǰcamera��ĿǰDEMOֻ�ܴ򿪺�camera����Ϊ��׼api����ָ��ǰ��camera������cameraӦ���޸Ķ��ƹ���");
        //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
        if (mSensorScreenOn != null) {
            mSm.registerListener(this, mSensorScreenOn, SensorManager.SENSOR_DELAY_NORMAL);
            mPause.setEnabled(true);
            mResume.setEnabled(false);
        }
        mPause.setEnabled(true);
        mResume.setEnabled(true);
        
        boolean hasAccelerometer = this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        Log.i(TAG, ">>>>>hasAccelerometer="+hasAccelerometer);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //AlarmAlertWakeLock.acquireScreenCpuWakeLock(this);
        mFlagActivityResume = true;
        
        /*
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
        */
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mFlagActivityResume = false;
        super.onPause();
        //AlarmAlertWakeLock.releaseCpuLock();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        //�㲥�����ظ����ע��
        MainActivity.this.unregisterReceiver(mBroadcastReceiver);
        
        
        mSm.unregisterListener(this);
        AlarmAlertWakeLock.releaseCpuLock();
        //��Ļ����
        mKeyguardLock.reenableKeyguard();
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
            mBufferStack.push(data);
            
            if (false && mFlagActivityResume && !mFlagStartMonitor
                    && (mFlagProximityNear || (Math.abs(mSensorAcceData[0]) <= 1
                            && Math.abs(mSensorAcceData[1]) <= 1
                            && mSensorAcceData[2] >= 9))) {
                // ���봫��������ס�����ֻ��ڿ��ӿڴ��������,��ʱ��Ҳ�ǳ�ʼ���֮һ��
                // �ֻ�ˮƽ���������Ͼ�ֹ��������Ҳ�ǳ�ʼ���֮һ��
                mFlagStartMonitor = true;
            } else if (mFlagActivityResume && !mFlagPreReckoning
                    && Math.abs(mSensorAcceData[0]) >= 8
                    && Math.abs(mSensorAcceData[1]) <= 3 && Math.abs(mSensorAcceData[2]) <= 3) {
//                mFlagStartMonitor = false;
//                launchBackCamera();
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
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON) {
            mSensorScreenOnData[0] = event.values[0];
            if (mSensorScreenOnData[0] != 0) {
                mSensorScreenOnLogData = mSensorScreenOnData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 0L);
            }
            Log.i("zzzz", ">>>>>SCREEN_ON == (" + mSensorScreenOnData[0]
                    + ")"
                    + " ��ǰʱ�䣺" + date.format(System.currentTimeMillis()));
        }
    }

    private void reckonLaunchBackCameraHistory() {
        // TODO Auto-generated method stub
        Log.i("zzzz", ">>>>>reckonLaunch <<Back>> CameraHistory start");
        mFlagReckoning = true;
        
        ArrayList<AcceData> mAll = mBufferStack.dump();
        Iterator<AcceData> iterator = mAll.iterator();
        while (iterator.hasNext()) {
            AcceData data = (AcceData) iterator.next();
            Log.i("zzzz",
                  ">>>>>>>AcceData dump time = " + data.time
                          + ", x=" + data.x
                          + ", y=" + data.y
                          + ", z=" + data.z);
        }
        
        boolean checkOk = true;
        int totalLength = mBufferStack.size();
        if (totalLength > 10 && mPreReckoningAcceData != null
              && mPreReckoningTime > 0) {
            int indexOfPreReckoningAcceData = mBufferStack
                  .indexOf(mPreReckoningAcceData);
            if (indexOfPreReckoningAcceData > 0
                    && indexOfPreReckoningAcceData < totalLength) {

                for (int i = indexOfPreReckoningAcceData; i < totalLength; i++) {
                    AcceData checkD = mBufferStack.get(i);
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
                    AcceData check = mBufferStack.get(i);
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
                        AcceData checkUp = mBufferStack.get(i);
                        AcceData checkDown = mBufferStack.get(i-1);
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
                if (checkOk) launchBackCamera();
            }
        }
        
        mPreReckoningAcceData = null;
        mBufferStack.clear();
        mFlagReckoning = false;
        mFlagPreReckoning = false;
        mPreReckoningTime = 0L;
        Log.i("zzzz", ">>>>>reckonLaunch <<Back>> CameraHistory end");
    }

    private void reckonLaunchFrontCameraHistory() {
        Log.i("zzzz", ">>>>>reckonLaunchFrontCameraHistory start");
        mFlagReckoning = true;

        ArrayList<AcceData> mAll = mBufferStack.dump();
        Iterator<AcceData> iterator = mAll.iterator();
        while (iterator.hasNext()) {
            AcceData data = (AcceData) iterator.next();
            Log.i("zzzz",
                  ">>>>>>>AcceData dump time = " + data.time
                          + ", x=" + data.x
                          + ", y=" + data.y
                          + ", z=" + data.z);
        }
        
        boolean checkOk = true;
        int totalLength = mBufferStack.size();
        if (totalLength > 10 && mPreReckoningAcceData != null
                && mPreReckoningTime > 0) {
            int indexOfPreReckoningAcceData = mBufferStack
                    .indexOf(mPreReckoningAcceData);
            if (indexOfPreReckoningAcceData > 0
                    && indexOfPreReckoningAcceData < totalLength) {

                for (int i = indexOfPreReckoningAcceData; i < totalLength; i++) {
                    AcceData checkD = mBufferStack.get(i);
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
                    AcceData check = mBufferStack.get(i);
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
                        AcceData checkUp = mBufferStack.get(i);
                        AcceData checkDown = mBufferStack.get(i-1);
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
                if (checkOk) launchFrontCamera();
            }
        }
        
        
        mPreReckoningAcceData = null;
        mBufferStack.clear();
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
        //intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        intent.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_BACK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void launchFrontCamera() {
        Intent intent = new Intent();
        // intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        intent.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
                    // ���費������MediaStore.EXTRA_OUTPUT������£�onActivityResult������resultCodeΪRESULT_OK������£�data���صĲ����Ǿ���ʵ��������Ƭ�������ŵ�ͼ�����ݣ�����ͨ���������·�������ӡ����ͼ��ĳߴ�
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
