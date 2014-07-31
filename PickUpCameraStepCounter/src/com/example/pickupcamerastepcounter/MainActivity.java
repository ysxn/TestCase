
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
    public static final int TYPE_SENSOR_HUB_SCREEN_ON_PROXIMITY = 33171014;

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
    private float mSensorStepCounterData[] = {
            0, 0, 0, 0
    };
    private float mSensorStepDetectorData[] = {
            0, 0, 0, 0
    };
    private float mSensorScreenOnProximityData[] = {
            0, 0, 0, 0
    };
    private float mSensorScreenOnProximityLogData = 0f;
    private float mSensorScreenOnLogData = 0f;
    private boolean mIsScreenOn = true;
    private boolean mTestStep = false;
    private boolean mTestScreenOnProximity = false;
    private boolean mTestPickUp = true;

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
    Sensor mSensorScreenOn;
    Sensor mSensorScreenOnProximity;

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
    
    //�������̹�����
    KeyguardManager mKeyguardManager = null;    
    //����������
    private KeyguardLock mKeyguardLock = null;
    private WindowManager mWindowManager = null;
    private LinearLayout mGlobalView = null;
    private WindowManager.LayoutParams mGlobalParams = null;

    private static final int REQUEST_UPDATE_DATA_STEP_COUNTER = 199;
    private static final int REQUEST_UPDATE_DATA_STEP_DETECTOR = 192;
    private static final int REQUEST_UPDATE_DATA = 299;
    private static final int REQUEST_UPDATE_DATA_SCREEN_ON_PROXIMITY = 399;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA_STEP_DETECTOR: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "Step Detector��" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorStepDetectorData[0];
                    mData.setText(sC+"\n"+mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA_STEP_COUNTER: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "Step Counter��" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorStepCounterData[0];
                    mData.setText(sC+"\n"+mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA: {
                    //mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = "��������" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnLogData;
                    mData.setText(sC+"\n"+mData.getText());
                    //���WM�µ�view�����������ͽ���
                    //Ų��service����
                    if (mSensorScreenOnLogData == 1) {
                        unLockScreen();
                        launchBackCamera();
                    }
                    //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
                    break;
                case REQUEST_UPDATE_DATA_SCREEN_ON_PROXIMITY: {
                    // mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String step = ">>>>>Step Counter : " + mSensorStepCounterData[0] + "\n"
                            + ">>>>>>Step Detector : " + mSensorStepDetectorData[0] + "\n";
                    String sC = "����������" + date.format(System.currentTimeMillis())
                            + " DATA=" + mSensorScreenOnProximityLogData;
                    mData.setText(sC + "\n" + mData.getText());
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
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        MainActivity.this.registerReceiver(mBroadcastReceiver, filter);
        
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
        //��ʼ��������������������⿪������
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
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON);
            if (ss == null || ss.size() == 0) {
                sb.append("��ȡpick up����������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_SCREEN_ON);
            } else {
                mSensorScreenOn = ss.get(0);
                sb.append("��ȡpick up����������OK,size=" + ss.size() + ",������Type_idΪ="
                        + TYPE_SENSOR_HUB_SCREEN_ON + "; MD="+mSensorScreenOn.getMinDelay());
            }
            sb.append("\n");
        }
        
        if (mTestScreenOnProximity) {
            ss = mSensorManager.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON_PROXIMITY);
            if (ss == null || ss.size() == 0) {
                sb.append("��ȡ������������������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_SCREEN_ON_PROXIMITY);
            } else {
                mSensorScreenOnProximity = ss.get(0);
                sb.append("��ȡ������������������OK,size=" + ss.size() + ",������Type_idΪ="
                        + TYPE_SENSOR_HUB_SCREEN_ON_PROXIMITY + "; MD="+mSensorScreenOnProximity.getMinDelay());
            }
            sb.append("\n");
        }
        mSensorBrief = sb.toString()+"\n";
        if (mTestStep) {
            mSensorBrief = mSensorBrief+"\n"
                    +"1.�Ʋ����������ʼ��Ϊ1.0��" 
                    +"\n"
                    +"2.�Ʋ����������ؿ�������ۼ�ֵ," 
                    +"\n";
        }
        if (mTestScreenOnProximity) {
            mSensorBrief = mSensorBrief+"\n"
                    +"1.����������ֹ����������ˮƽ�н�1~45��" 
                    +"\n"
                    +"2.�������ظ�һ�Σ�δ������Ļ->������Ļ->0.5�����뿪��Ļ->0.5�����ٴθ�����Ļ->0.5�������뿪��Ļ->����" 
                    +"\n";
        }
        if (mTestPickUp) {
            mSensorBrief = mSensorBrief+"\n"
                    +"���������׼ǰ��DATAΪ1�Ϳ���������ͷ��";
        }
        mTips.setText(mSensorBrief);
        //mTips.setText("���봫��������ס�����ֻ��ڿ��ӿڴ��������,��ʱ��Ҳ�ǳ�ʼ���֮һ�� �����ֻ�ˮƽ���������Ͼ�ֹ��������Ҳ�ǳ�ʼ���֮һ���� \n���û������ֻ�������׼ǰ���� ����������Camera�� \n������������������Ӧ�ô�ǰcamera��ĿǰDEMOֻ�ܴ򿪺�camera����Ϊ��׼api����ָ��ǰ��camera������cameraӦ���޸Ķ��ƹ���");
        //mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
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
        //�㲥�����ظ����ע��
        MainActivity.this.unregisterReceiver(mBroadcastReceiver);

        disableScreenOnSensor();
        AlarmAlertWakeLock.releaseCpuLock();
        //��Ļ����
        mKeyguardLock.reenableKeyguard();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            mSensorStepDetectorData[0] = event.values[0];
            mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_STEP_DETECTOR, 0L);
            Log.i("zzzz", ">>>>>STEP_DETECTOR == (" + mSensorStepDetectorData[0]
                    + ")"
                    + " ��ǰʱ�䣺" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            mSensorStepCounterData[0] = event.values[0];
            mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_STEP_COUNTER, 0L);
            Log.i("zzzz", ">>>>>STEP_COUNTER == (" + mSensorStepCounterData[0]
                    + ")"
                    + " ��ǰʱ�䣺" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON) {
            mSensorScreenOnData[0] = event.values[0];
            if (mSensorScreenOnData[0] != 0) {
                mSensorScreenOnLogData = mSensorScreenOnData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 0L);
            }
            Log.i("yyyy", ">>>>>TYPE="+TYPE_SENSOR_HUB_SCREEN_ON+",SCREEN_ON == (" + mSensorScreenOnData[0]
                    + ")"
                    + " ��ǰʱ�䣺" + date.format(System.currentTimeMillis()));
        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON_PROXIMITY) {
            mSensorScreenOnProximityData[0] = event.values[0];
            if (mSensorScreenOnProximityData[0] != 0) {
                mSensorScreenOnProximityLogData = mSensorScreenOnProximityData[0];
                mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_SCREEN_ON_PROXIMITY, 0L);
            }
            Log.i("zzzz", ">>>>>SCREEN_ON_PROXIMITY == (" + event.values[0]
                    + ")"
                    + " ��ǰʱ�䣺" + date.format(System.currentTimeMillis()));
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
                    // ���費������MediaStore.EXTRA_OUTPUT������£�onActivityResult������resultCodeΪRESULT_OK������£�data���صĲ����Ǿ���ʵ��������Ƭ�������ŵ�ͼ�����ݣ�����ͨ���������·�������ӡ����ͼ��ĳߴ�
                    //����Android4.2.2����PhotoModule.java
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
                    // ���費������MediaStore.EXTRA_OUTPUT������£�onActivityResult������resultCodeΪRESULT_OK������£�data���صĲ����Ǿ���ʵ��������Ƭ�������ŵ�ͼ�����ݣ�����ͨ���������·�������ӡ����ͼ��ĳߴ�
                    //����Android4.2.2����PhotoModule.java
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
        // super.onConfigurationChanged(newConfig);
    }
    
    private void enableScreenOnSensor() {
        if (mTestStep) {
            mSensorManager.registerListener(MainActivity.this, mSensorStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(MainActivity.this, mSensorStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mTestScreenOnProximity) {
            mSensorManager.registerListener(MainActivity.this, mSensorScreenOnProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mTestPickUp) {
            mSensorManager.registerListener(MainActivity.this, mSensorScreenOn, SensorManager.SENSOR_DELAY_NORMAL);
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
        //����
        if (!mIsScreenOn) {
            AlarmAlertWakeLock.acquireScreenCpuWakeLock(MainActivity.this);
            mGlobalView.setVisibility(View.VISIBLE);
            mWindowManager.updateViewLayout(mGlobalView, mGlobalParams);
            
        }
        //�жϼ�����������ֵ��disableKeyguard�ĵ���û�й�ϵ
        Log.i(TAG, ">>>>>isKeyguardLocked="+mKeyguardManager.isKeyguardLocked());
        if (mGlobalView != null) return;
        //����
        //������ʾ��������
        //mKeyguardLock.disableKeyguard(); 
        //�ж�camera�Ƿ��Ѿ������������ظ�����camera
        //�Լ��޸�camera������ǰ��
    }
    
}
