
package com.example.pickupcamerastepcounter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private final String TAG = "zyw";
    private final boolean DEBUG = true;
    //public static final int TYPE_SENSOR_HUB_AIR_MOUSE = 33171001;
    public static final int TYPE_SENSOR_HUB_SNAP = 33171002;
    public static final int TYPE_SENSOR_HUB_SHAKE = 33171003;
    public static final int TYPE_SENSOR_HUB_TAP = 33171004;
    public static final int TYPE_SENSOR_HUB_FLIP = 33171005;
    public static final int TYPE_SENSOR_HUB_TWIST = 33171006;
    //public static final int TYPE_SENSOR_HUB_TILT = 33171007;
    //public static final int TYPE_SENSOR_HUB_PDR_HOLD = 33171008;
    //public static final int TYPE_SENSOR_HUB_FALLING = 33171009;
    public static final int TYPE_SENSOR_HUB_SCREEN_ON_CAMERA = 33171010;
    public static final int TYPE_SENSOR_HUB_GESTURE = 33171011;
    //public static final int TYPE_SENSOR_HUB_CONTEXT_AWARENESS = 33171012;
    public static final int TYPE_SENSOR_HUB_RGB = 33171013;
    public static final int TYPE_SENSOR_HUB_SCREEN_ON_BYHAND = 33171014;
    public static final int TYPE_SENSOR_HUB_SCREEN_ON_MOTION = 33171015;

    private SimpleDateFormat date = null;//new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");
    
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
    private float mSensorScreenOnCameraData[] = {
            0, 0, 0, 0
    };
    private float mSensorStepCounterData[] = {
            0, 0, 0, 0
    };
    private float mSensorStepDetectorData[] = {
            0, 0, 0, 0
    };
    private float mSensorScreenOnByHandData[] = {
            0, 0, 0, 0
    };
    private float mSensorScreenOnMotionData[] = {
            0, 0, 0, 0
    };
    private float mSensorScreenOnByHandLogData = 0f;
    private float mSensorScreenOnMotionLogData = 0f;
    private float mSensorScreenOnCameraLogData = 0f;
    private boolean mIsScreenOn = true;
    private boolean mTestStep = false;
    private boolean mTestScreenOnByHand = false;
    private boolean mTestPickUp = false;
    private boolean mTestMotion = true;
    private boolean mTestFlip = false;
    private boolean mTestSnap = false;
    private boolean mTestTap = false;

    AudioManager mAudioManager;
    SensorManager mSensorManager;
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
    Sensor mSensorScreenOnCamera;
    Sensor mSensorScreenOnByHand;
    Sensor mSensorScreenOnMotion;

    Sensor mSensorAcce;
    Sensor mSensorGyro;
    Sensor mSensorOrien;
    Sensor mSensorProximity;
    Sensor mSensorStepCounter;
    Sensor mSensorStepDetector;

    private TextView mTips;
    private TextView mData;
    private Button mPause;
    private Button mResume;
    public static final String EXTRAS_CAMERA_FACING = "android.intent.extras.CAMERA_FACING";
    public static final int EXTRAS_CAMERA_FACING_BACK = 0;
    public static final int EXTRAS_CAMERA_FACING_FRONT = 1;
    public static final int SENSOR_DATA_FACING_FRONT = 3;
    public static final int SENSOR_DATA_FACING_BACK = 2;

    private final int REQUEST_CAPTURE_BACK = 0x2341;
    private final int REQUEST_CAPTURE_FRONT = 0x2342;
    private static final String REQUEST_CAPTURE_SAVE_FRONT_IMG = Environment.getExternalStorageDirectory().getPath() + "/pickuptest/front.jpg";
    private static final String REQUEST_CAPTURE_SAVE_BACK_IMG = Environment.getExternalStorageDirectory().getPath() + "/pickuptest/back.jpg";
    
    PowerManager mPowerManager;
    //声明键盘管理器
    KeyguardManager mKeyguardManager = null;    
    //声明键盘锁
    private KeyguardLock mKeyguardLock = null;
    private WindowManager mWindowManager = null;
    private LinearLayout mGlobalView = null;
    private WindowManager.LayoutParams mGlobalParams = null;

    private static final int REQUEST_UPDATE_DATA_STEP_COUNTER = 199;
    private static final int REQUEST_UPDATE_DATA_STEP_DETECTOR = 192;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_CAMERA = 299;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_BYHAND = 399;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_MOTION = 391;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_SNAP = 392;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_TAP = 393;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_FLIP = 394;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA_STEP_DETECTOR: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "Step Detector：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorStepDetectorData[0];
                    mData.setText(sC+"\n"+mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA_STEP_COUNTER: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "Step Counter：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorStepCounterData[0];
                    mData.setText(sC+"\n"+mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_CAMERA: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "体感相机：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnCameraLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    //添加WM新的view来控制亮屏和解锁
                    //挪到service里面
                    if (mSensorScreenOnCameraLogData == 1) {
                        unLockScreen();
                        launchBackCamera();
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_BYHAND: {
                    // mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String step = ">>>>>Step Counter : " + mSensorStepCounterData[0] + "\n"
                            + ">>>>>>Step Detector : " + mSensorStepDetectorData[0] + "\n";
                    String sC = "挥手亮屏：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnByHandLogData;
                    mData.setText(sC + "\n" + mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_MOTION: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "智能亮屏：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnMotionLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    //添加WM新的view来控制亮屏和解锁
                    //挪到service里面
                    if (mSensorScreenOnMotionLogData == 1) {
                        Log.i(TAG, ">>>>>>>>>>>>REQUEST_UPDATE_DATA_SCREEN_ON_MOTION");
                        unLockScreen();
                        //mPm.wakeUp(SystemClock.uptimeMillis());
                        Log.i(TAG, ">>>>>>>>>>>>wake up ok");
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_SNAP: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "SNAP亮屏：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnMotionLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    //添加WM新的view来控制亮屏和解锁
                    //挪到service里面
                    if (mSensorScreenOnMotionLogData == 1) {
                        Log.i(TAG, ">>>>>>>>>>>>REQUEST_UPDATE_DATA_SCREEN_ON_SNAP");
                        unLockScreen();
                        //mPm.wakeUp(SystemClock.uptimeMillis());
                        Log.i(TAG, ">>>>>>>>>>>>wake up ok");
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_TAP: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "TAP亮屏：" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnMotionLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    //添加WM新的view来控制亮屏和解锁
                    //挪到service里面
                    if (mSensorScreenOnMotionLogData == 1) {
                        Log.i(TAG, ">>>>>>>>>>>>REQUEST_UPDATE_DATA_SCREEN_ON_TAP");
                        unLockScreen();
                        //mPm.wakeUp(SystemClock.uptimeMillis());
                        Log.i(TAG, ">>>>>>>>>>>>wake up ok");
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_FLIP: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "FLIP event:" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnMotionLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    Log.i(TAG, ">>>>>>>>>>>>REQUEST_UPDATE_DATA_SCREEN_ON_FLIP data="+mSensorScreenOnMotionLogData+",mIsScreenOn="+mIsScreenOn);
                    //添加WM新的view来控制亮屏和解锁
                    //挪到service里面
                    //data ==1, is flip LCD ,背壳朝上，锁屏。data ==2, is flip LCD朝上，亮屏
                    if (mSensorScreenOnMotionLogData == 1.0) {
                        LockScreen();
                        //mPm.wakeUp(SystemClock.uptimeMillis());
                        Log.i(TAG, ">>>>>>>>>>>>go sleep ok");
                    } else if (mSensorScreenOnMotionLogData == 2.0) {
                        //data ==2, is flip LCD朝上，亮屏
                        //unLockScreen();
                        //Log.i(TAG, ">>>>>>>>>>>>wake up ok");
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
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
                Log.i(TAG, ">>>>>isKeyguardLocked="+mKeyguardManager.isKeyguardLocked());
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
        date = new SimpleDateFormat(getString(R.string.date_format));
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        MainActivity.this.registerReceiver(mBroadcastReceiver, filter);
        
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        mGlobalView = new LinearLayout(this);
        mGlobalParams = new WindowManager.LayoutParams();
        mGlobalParams.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mGlobalView.setVisibility(View.GONE);
        mWindowManager.addView(mGlobalView, mGlobalParams);
        //初始化键盘锁，可以锁定或解开键盘锁
        mKeyguardLock = mKeyguardManager.newKeyguardLock("zhuyawen"); 

        mTips = (TextView) findViewById(R.id.sensor_tips);
        mData = (TextView) findViewById(R.id.sensor_data);
        Button exit = (Button) findViewById(R.id.button_exit);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                disableScreenOnSensor();
                AlarmAlertWakeLock.releaseCpuLock();
                //屏幕锁定
                //mKeyguardLock.reenableKeyguard();
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
                disableScreenOnSensor();
            }
        });
        mResume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                enableScreenOnSensor();
            }
        });

        StringBuilder sb = new StringBuilder();

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorAcce = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorOrien = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        
        List<Sensor> ss;

        if (mTestPickUp) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON_CAMERA);
            if (ss == null || ss.size() == 0) {
                sb.append("获取pick up动作传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_SCREEN_ON_CAMERA);
            } else {
                mSensorScreenOnCamera = ss.get(0);
                sb.append("获取pick up动作传感器OK,size=" + ss.size() + ",传感器Type_id为="
                        + TYPE_SENSOR_HUB_SCREEN_ON_CAMERA + "; MD="+mSensorScreenOnCamera.getMinDelay());
            }
            sb.append("\n");
        }
        
        if (mTestScreenOnByHand) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON_BYHAND);
            if (ss == null || ss.size() == 0) {
                sb.append("获取挥手亮屏动作传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_SCREEN_ON_BYHAND);
            } else {
                mSensorScreenOnByHand = ss.get(0);
                sb.append("获取挥手亮屏动作传感器OK,size=" + ss.size() + ",传感器Type_id为="
                        + TYPE_SENSOR_HUB_SCREEN_ON_BYHAND + "; MD="+mSensorScreenOnByHand.getMinDelay());
            }
            sb.append("\n");
        }
        
        if (mTestMotion) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON_MOTION);
            if (ss == null || ss.size() == 0) {
                sb.append("获取智能亮屏动作传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_SCREEN_ON_MOTION);
            } else {
                mSensorScreenOnMotion = ss.get(0);
                sb.append("获取智能亮屏动作传感器OK,size=" + ss.size() + ",传感器Type_id为="
                        + TYPE_SENSOR_HUB_SCREEN_ON_MOTION + "; MD="+mSensorScreenOnMotion.getMinDelay());
            }
            sb.append("\n");
        }
        
        if (mTestFlip) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_FLIP);
            if (ss == null || ss.size() == 0) {
                sb.append("Get FLIP sensor is NULL，check TYPE ID：" + TYPE_SENSOR_HUB_FLIP);
            } else {
                mSensorFlip = ss.get(0);
                sb.append("Get FLIP sensor is OK,size=" + ss.size() + ",Type_id="
                        + TYPE_SENSOR_HUB_FLIP + "; MD="+mSensorFlip.getMinDelay());
            }
            sb.append("\n");
        }
        
        if (mTestSnap) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_SNAP);
            if (ss == null || ss.size() == 0) {
                sb.append("获取SNAP动作传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_SNAP);
            } else {
                mSensorSnap = ss.get(0);
                sb.append("获取SNAP动作传感器OK,size=" + ss.size() + ",传感器Type_id为="
                        + TYPE_SENSOR_HUB_SNAP + "; MD="+mSensorSnap.getMinDelay());
            }
            sb.append("\n");
        }
        
        if (mTestTap) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_TAP);
            if (ss == null || ss.size() == 0) {
                sb.append("获取TAP动作传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_TAP);
            } else {
                mSensorTap = ss.get(0);
                sb.append("获取TAP动作传感器OK,size=" + ss.size() + ",传感器Type_id为="
                        + TYPE_SENSOR_HUB_TAP + "; MD="+mSensorTap.getMinDelay());
            }
            sb.append("\n");
        }
        mSensorBrief = sb.toString()+"\n";
        if (mTestStep) {
            mSensorBrief = mSensorBrief+"\n"
                    +"1.计步器检测数据始终为1.0，" 
                    +"\n"
                    +"2.计步器计数返回开机后的累计值," 
                    +"\n";
        }
        if (mTestScreenOnByHand) {
            mSensorBrief = mSensorBrief+"\n"
                    +"挥手亮屏说明："
                    +"\n"
                    +"1.挥手亮屏静止于桌面且与水平夹角1~45度" 
                    +"\n"
                    +"2.挥手来回各一次，未覆盖屏幕->覆盖屏幕->0.5秒内离开屏幕->0.5秒内再次覆盖屏幕->0.5秒内再离开屏幕->触发" 
                    +"\n";
        }
        if (mTestPickUp) {
            mSensorBrief = mSensorBrief+"\n"
                    +"体感相机说明：横屏举起对准前方DATA为1就开启后摄像头";
        }
        if (mTestFlip) {
            mSensorBrief = mSensorBrief+"\n"
                    +getString(R.string.flip_tips);
        }
        if (mTestMotion) {
            mSensorBrief = mSensorBrief+"\n\n"
                    +"智能亮屏说明："
                    +"\n"
                    +"1.  在本应用界面灭屏后，按下述动作来触发： " 
                    +"\n"
                    +"2.  手機(由非60~90度)被抬起直立並呈現60~90度(角度變化達45度以上),並固定角度達800 ms. " 
                    +"\n"
                    +"3.  P-sensor沒有被遮蔽. ( P-sensor可由sensor hub 自動開啟. 啟動到讀取正確值,約需要300ms 左右 )" 
                    +"\n";
        }
        mTips.setText(mSensorBrief);
        enableScreenOnSensor();
        
        boolean hasAccelerometer = this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        Log.i(TAG, ">>>>>hasAccelerometer="+hasAccelerometer);
        mAudioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        int i = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
        Log.i(TAG, ">>>>>STREAM_RINGStreamVolume="+i);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //AlarmAlertWakeLock.acquireScreenCpuWakeLock(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        AlarmAlertWakeLock.releaseCpuLock();
    }

    @Override
    protected void onDestroy() {
        mWindowManager.removeView(mGlobalView);
        //广播不能重复解除注册
        MainActivity.this.unregisterReceiver(mBroadcastReceiver);

        disableScreenOnSensor();
        AlarmAlertWakeLock.releaseCpuLock();
        //屏幕锁定
        //mKeyguardLock.reenableKeyguard();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            mSensorStepDetectorData[0] = event.values[0];
            mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_STEP_DETECTOR, 0L);
            Log.i(TAG, ">>>>>STEP_DETECTOR == (" + mSensorStepDetectorData[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            mSensorStepCounterData[0] = event.values[0];
            mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_STEP_COUNTER, 0L);
            Log.i(TAG, ">>>>>STEP_COUNTER == (" + mSensorStepCounterData[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON_CAMERA) {
            mSensorScreenOnCameraData[0] = event.values[0];
            if (mSensorScreenOnCameraData[0] != 0) {
                mSensorScreenOnCameraLogData = mSensorScreenOnCameraData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_CAMERA, 0L);
            }
            Log.i(TAG, ">>>>>TYPE="+TYPE_SENSOR_HUB_SCREEN_ON_CAMERA+",SCREEN_ON == (" + mSensorScreenOnCameraData[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON_BYHAND) {
            mSensorScreenOnByHandData[0] = event.values[0];
            if (mSensorScreenOnByHandData[0] != 0) {
                mSensorScreenOnByHandLogData = mSensorScreenOnByHandData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_BYHAND, 0L);
            }
            Log.i(TAG, ">>>>>SCREEN_ON_BYHAND == (" + event.values[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON_MOTION) {
            mSensorScreenOnMotionData[0] = event.values[0];
            if (mSensorScreenOnMotionData[0] != 0) {
                mSensorScreenOnMotionLogData = mSensorScreenOnMotionData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_MOTION, 0L);
            }
            Log.i(TAG, ">>>>>SCREEN_ON_Motion == (" + event.values[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_FLIP) {
            mSensorScreenOnMotionData[0] = event.values[0];
            if (mSensorScreenOnMotionData[0] != 0) {
                mSensorScreenOnMotionLogData = mSensorScreenOnMotionData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_FLIP, 0L);
            }
            Log.i(TAG, ">>>>>FLIP == (" + event.values[0]
                    + ")"
                    + " TIME:" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_TAP) {
            mSensorScreenOnMotionData[0] = event.values[0];
            if (mSensorScreenOnMotionData[0] != 0) {
                mSensorScreenOnMotionLogData = mSensorScreenOnMotionData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_TAP, 0L);
            }
            Log.i(TAG, ">>>>>TAP == (" + event.values[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SNAP) {
            mSensorScreenOnMotionData[0] = event.values[0];
            if (mSensorScreenOnMotionData[0] != 0) {
                mSensorScreenOnMotionLogData = mSensorScreenOnMotionData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_SNAP, 0L);
            }
            Log.i(TAG, ">>>>>SNAP == (" + event.values[0]
                    + ")"
                    + " 当前时间：" + date.format(System.currentTimeMillis()));
        }
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
        Intent i = new Intent();
        i.setClassName("com.example.pickupcamerastepcounter", "com.example.pickupcamerastepcounter.StatisticPickUpActivity");
        startActivity(i);
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
        Intent i = new Intent();
        i.setClassName("com.example.pickupcamerastepcounter", "com.example.pickupcamerastepcounter.StatisticPickUpActivity");
        startActivity(i);
    }

    private void launchBackCameraForResult() {
        String imgPath = REQUEST_CAPTURE_SAVE_BACK_IMG;//Environment.getExternalStorageDirectory().getPath() + "/pickuptest/back.jpg";
        File vFile = new File(imgPath);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile(); // new File(vFile.getParent());
            if (vDirPath != null) {
                vDirPath.mkdirs();
            }
        }
        Uri uri = Uri.fromFile(vFile);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_BACK);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
        startActivityForResult(i, REQUEST_CAPTURE_BACK);
    }
    
    private void launchFrontCameraForResult() {
        String imgPath = REQUEST_CAPTURE_SAVE_FRONT_IMG;//Environment.getExternalStorageDirectory().getPath() + "/pickuptest/front.jpg";
        File vFile = new File(imgPath);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile(); // new File(vFile.getParent());
            if (vDirPath != null) {
                vDirPath.mkdirs();
            }
        }
        Uri uri = Uri.fromFile(vFile);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(EXTRAS_CAMERA_FACING, EXTRAS_CAMERA_FACING_FRONT);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
        startActivityForResult(i, REQUEST_CAPTURE_FRONT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case REQUEST_CAPTURE_BACK:
                if (resultCode == RESULT_OK) {
                    // iViewPic.setImageURI(Uri.fromFile(new File(imgPath)));
                    // 假设不传参数MediaStore.EXTRA_OUTPUT的情况下，onActivityResult函数在resultCode为RESULT_OK的情况下，data返回的参数是经过实际拍摄照片经过缩放的图像数据，可以通过类似如下方法来打印缩放图像的尺寸
                    //比如Android4.2.2里面PhotoModule.java
                    /**
                     * int orientation = Exif.getOrientation(data);
                     * <p>
                     * Bitmap bitmap = Util.makeBitmap(data, 50 * 1024);
                     * <p>
                     * bitmap = Util.rotate(bitmap, orientation);
                     * <p>
                     * mActivity.setResultEx(Activity.RESULT_OK, new Intent("inline-data").putExtra("data", bitmap));
                     * <p>
                     * mActivity.finish();
                     */
                    Bitmap bmp = null;
                    if (data != null && data.getExtras() != null) {
                        bmp = (Bitmap) data.getExtras().get("data");
                    } else {
                        Log.d(TAG, "REQUEST_CAPTURE_BACK If had pass MediaStore.EXTRA_OUTPUT, result data is null!");
                    }
                    if (bmp != null) {
                        Log.d(TAG, "REQUEST_CAPTURE_BACK bmp width:" + bmp.getWidth() + ", height:" + bmp.getHeight());
                    } else {
                        Log.d(TAG, "REQUEST_CAPTURE_BACK bmp return null");
                    }
                } else {
                    Log.d(TAG, "REQUEST_CAPTURE_BACK resultCode return fail!");
                }
                break;
            case REQUEST_CAPTURE_FRONT:
                if (resultCode == RESULT_OK) {
                    // iViewPic.setImageURI(Uri.fromFile(new File(imgPath)));
                    // 假设不传参数MediaStore.EXTRA_OUTPUT的情况下，onActivityResult函数在resultCode为RESULT_OK的情况下，data返回的参数是经过实际拍摄照片经过缩放的图像数据，可以通过类似如下方法来打印缩放图像的尺寸
                    //比如Android4.2.2里面PhotoModule.java
                    /**
                     * int orientation = Exif.getOrientation(data);
                     * <p>
                     * Bitmap bitmap = Util.makeBitmap(data, 50 * 1024);
                     * <p>
                     * bitmap = Util.rotate(bitmap, orientation);
                     * <p>
                     * mActivity.setResultEx(Activity.RESULT_OK, new Intent("inline-data").putExtra("data", bitmap));
                     * <p>
                     * mActivity.finish();
                     */
                    Bitmap bmp = null;
                    if (data != null && data.getExtras() != null) {
                        bmp = (Bitmap) data.getExtras().get("data");
                    } else {
                        Log.d(TAG, "REQUEST_CAPTURE_FRONT If had pass MediaStore.EXTRA_OUTPUT, result data is null!");
                    }
                    if (bmp != null) {
                        Log.d(TAG, "REQUEST_CAPTURE_FRONT bmp width:" + bmp.getWidth() + ", height:" + bmp.getHeight());
                    } else {
                        Log.d(TAG, "REQUEST_CAPTURE_FRONT bmp return null");
                    }
                } else {
                    Log.d(TAG, "REQUEST_CAPTURE_FRONT resultCode return fail!");
                }
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }
    
    private void enableScreenOnSensor() {
        if (mTestStep) {
            mSensorManager.registerListener(MainActivity.this, mSensorStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(MainActivity.this, mSensorStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mTestScreenOnByHand) {
            mSensorManager.registerListener(MainActivity.this, mSensorScreenOnByHand, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mTestPickUp) {
            mSensorManager.registerListener(MainActivity.this, mSensorScreenOnCamera, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mTestMotion) {
            mSensorManager.registerListener(MainActivity.this, mSensorScreenOnMotion, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if (mTestFlip) {
            mSensorManager.registerListener(MainActivity.this, mSensorFlip, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if (mTestTap) {
            mSensorManager.registerListener(MainActivity.this, mSensorTap, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if (mTestSnap) {
            mSensorManager.registerListener(MainActivity.this, mSensorSnap, SensorManager.SENSOR_DELAY_FASTEST);
        }
        mPause.setEnabled(true);
        mResume.setEnabled(false);
    }
    
    private void disableScreenOnSensor() {
        mSensorManager.unregisterListener(MainActivity.this);
        mPause.setEnabled(false);
        mResume.setEnabled(true);
    }
    
    private void unLockScreen() {
        //亮屏
        if (!mIsScreenOn) {
            AlarmAlertWakeLock.acquireScreenCpuWakeLock(MainActivity.this);
            mGlobalView.setVisibility(View.VISIBLE);
            mWindowManager.updateViewLayout(mGlobalView, mGlobalParams);
        }
        //判断键盘锁定返回值和disableKeyguard的调用没有关系
        Log.i(TAG, ">>>>>isKeyguardLocked="+mKeyguardManager.isKeyguardLocked());
        if (mGlobalView != null) return;
        //解锁
        //禁用显示键盘锁定
        //mKeyguardLock.disableKeyguard(); 
        //判断camera是否已经启动，避免重复开启camera
        //自己修改camera，区分前后
    }
    
    private void LockScreen() {
        //灭屏
        if (mIsScreenOn) {
            AlarmAlertWakeLock.releaseCpuLock();
            mGlobalView.setVisibility(View.GONE);
            mWindowManager.updateViewLayout(mGlobalView, mGlobalParams);
            //mPowerManager.goToSleep(SystemClock.uptimeMillis());
        }
        //判断键盘锁定返回值和disableKeyguard的调用没有关系
        Log.i(TAG, ">>>>>isKeyguardLocked="+mKeyguardManager.isKeyguardLocked());
        if (mGlobalView != null) return;
        //解锁
        //禁用显示键盘锁定
        //mKeyguardLock.disableKeyguard(); 
        //判断camera是否已经启动，避免重复开启camera
        //自己修改camera，区分前后
    }
    
}
