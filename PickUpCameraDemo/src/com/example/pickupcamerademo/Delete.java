
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

public class Delete extends Activity implements SensorEventListener {
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

    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");
    private String mSensorBrief = "";
    private int mSensorAirMouseData[] = {
            0, 0, 0, 0
    };
    private int mSensorSnapData[] = {
            0, 0, 0, 0
    };
    private int mSensorShakeData[] = {
            0, 0, 0, 0
    };
    private int mSensorTapData[] = {
            0, 0, 0, 0
    };
    private int mSensorFlipData[] = {
            0, 0, 0, 0
    };
    private int mSensorTwistData[] = {
            0, 0, 0, 0
    };
    private int mSensorTiltData[] = {
            0, 0, 0, 0
    };
    private int mSensorPDRHoldData[] = {
            0, 0, 0, 0
    };
    private int mSensorFallingData[] = {
            0, 0, 0, 0
    };
    private int mSensorRGBData[] = {
            0, 0, 0, 0
    };
    private int mSensorGestureData[] = {
            0, 0, 0, 0
    };
    private int mSensorContextAwarenessData[] = {
            0, 0, 0, 0
    };
    private int mSensorScreenOnData[] = {
            0, 0, 0, 0
    };
    private int mSensorAcceData[] = {
            0, 0, 0, 0
    };
    private int mSensorProximityData[] = {
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
//                        mData.setText("AIR_MOUSE����������{" + mSensorAirMouseData[0] + ","
//                                + mSensorAirMouseData[1] + "," + mSensorAirMouseData[2] + ","
//                                + mSensorAirMouseData[3] + "}" + "\n"
//                                + "SNAP����������{" + mSensorSnapData[0] + ","
//                                + mSensorSnapData[1] + "," + mSensorSnapData[2] + ","
//                                + mSensorSnapData[3] + "}" + "\n"
//                                + "SHAKE����������{" + mSensorShakeData[0] + ","
//                                + mSensorShakeData[1] + "," + mSensorShakeData[2] + ","
//                                + mSensorShakeData[3] + "}" + "\n"
//                                + "TAP����������{" + mSensorTapData[0] + ","
//                                + mSensorTapData[1] + "," + mSensorTapData[2] + ","
//                                + mSensorTapData[3] + "}" + "\n"
//                                + "FLIP����������{" + mSensorFlipData[0] + ","
//                                + mSensorFlipData[1] + "," + mSensorFlipData[2] + ","
//                                + mSensorFlipData[3] + "}" + "\n"
//                                + "TWIST����������{" + mSensorTwistData[0] + ","
//                                + mSensorTwistData[1] + "," + mSensorTwistData[2] + ","
//                                + mSensorTwistData[3] + "}" + "\n"
//                                + "TILT����������{" + mSensorTiltData[0] + ","
//                                + mSensorTiltData[1] + "," + mSensorTiltData[2] + ","
//                                + mSensorTiltData[3] + "}" + "\n"
//                                + "PDR_HOLD����������{" + mSensorPDRHoldData[0] + ","
//                                + mSensorPDRHoldData[1] + "," + mSensorPDRHoldData[2] + ","
//                                + mSensorPDRHoldData[3] + "}" + "\n"
//                                + "FALLING����������{" + mSensorFallingData[0] + ","
//                                + mSensorFallingData[1] + "," + mSensorFallingData[2] + ","
//                                + mSensorFallingData[3] + "}" + "\n"
//                                + "RGB����������{" + mSensorRGBData[0] + ","
//                                + mSensorRGBData[1] + "," + mSensorRGBData[2] + ","
//                                + mSensorRGBData[3] + "}" + "\n"
//                                + "���ƴ���������{" + mSensorGestureData[0] + ","
//                                + mSensorGestureData[1] + "," + mSensorGestureData[2] + ","
//                                + mSensorGestureData[3] + "}" + "\n"
//                                + "CONTEXT_AWARENESS����������{" + mSensorContextAwarenessData[0] + ","
//                                + mSensorContextAwarenessData[1] + ","
//                                + mSensorContextAwarenessData[2] + ","
//                                + mSensorContextAwarenessData[3] + "}" + "\n"
//                                + "SCREEN_ON����������{" + mSensorScreenOnData[0] + ","
//                                + mSensorScreenOnData[1] + "," + mSensorScreenOnData[2] + ","
//                                + mSensorScreenOnData[3] + "}" + "\n");
                        mData.setText(">>>>>Proximity : " + mSensorProximityData[0] + ", mFlagProximityNear="
                                + mFlagProximityNear+"\n"
                                +">>>>>acce : " + mSensorAcceData[0] + ", " + mSensorAcceData[1] + ", "
                                + mSensorAcceData[2] + ", mFlagStartMonitor=" + mFlagStartMonitor
                                +", mFlagActivityResume=" + mFlagActivityResume);
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

        List<Sensor> ss;

//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_AIR_MOUSE);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡAIR_MOUSE������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_AIR_MOUSE);
//        } else {
//            mSensorAirMouse = ss.get(0);
//            sb.append("��ȡAIR_MOUSE������OK,size=" + ss.size() + ",������Type_idΪ="
//                    + TYPE_SENSOR_HUB_AIR_MOUSE + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_SNAP);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡSNAP������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_SNAP);
//        } else {
//            mSensorSnap = ss.get(0);
//            sb.append("��ȡSNAP������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_SNAP
//                    + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_SHAKE);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡSHAKE������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_SHAKE);
//        } else {
//            mSensorShake = ss.get(0);
//            sb.append("��ȡSHAKE������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_SHAKE
//                    + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_TAP);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡTAP������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_TAP);
//        } else {
//            mSensorTap = ss.get(0);
//            sb.append("��ȡTAP������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_TAP + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_FLIP);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡFLIP������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_FLIP);
//        } else {
//            mSensorFlip = ss.get(0);
//            sb.append("��ȡFLIP������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_FLIP
//                    + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_TWIST);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡTWIST������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_TWIST);
//        } else {
//            mSensorTwist = ss.get(0);
//            sb.append("��ȡTWIST������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_TWIST
//                    + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_TILT);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡTILT������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_TILT);
//        } else {
//            mSensorTilt = ss.get(0);
//            sb.append("��ȡTILT������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_TILT
//                    + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_PDR_HOLD);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡPDR_HOLD������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_PDR_HOLD);
//        } else {
//            mSensorPDRHold = ss.get(0);
//            sb.append("��ȡPDR_HOLD������OK,size=" + ss.size() + ",������Type_idΪ="
//                    + TYPE_SENSOR_HUB_PDR_HOLD + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_FALLING);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡFALLING������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_FALLING);
//        } else {
//            mSensorFalling = ss.get(0);
//            sb.append("��ȡFALLING������OK,size=" + ss.size() + ",������Type_idΪ="
//                    + TYPE_SENSOR_HUB_FALLING + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_RGB);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡRGB������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_RGB);
//        } else {
//            mSensorRGB = ss.get(0);
//            sb.append("��ȡRGB������OK,size=" + ss.size() + ",������Type_idΪ=" + TYPE_SENSOR_HUB_RGB + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_PROXIMITY_GESTURE);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡ���ƴ�����Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_PROXIMITY_GESTURE);
//        } else {
//            mSensorGesture = ss.get(0);
//            sb.append("��ȡ���ƴ�����OK,size=" + ss.size() + ",������Type_idΪ="
//                    + TYPE_SENSOR_HUB_PROXIMITY_GESTURE + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_CONTEXT_AWARENESS);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡCONTEXT_AWARENESS������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_CONTEXT_AWARENESS);
//        } else {
//            mSensorContextAwareness = ss.get(0);
//            sb.append("��ȡCONTEXT_AWARENESS������OK,size=" + ss.size() + ",������Type_idΪ="
//                    + TYPE_SENSOR_HUB_CONTEXT_AWARENESS + "; ");
//        }
//        sb.append("\n");
//
//        ss = mSm.getSensorList(TYPE_SENSOR_HUB_SCREEN_ON);
//        if (ss == null || ss.size() == 0) {
//            sb.append("��ȡSCREEN_ON������Ϊ�գ����鴫����id�Ƿ�Ϊ��" + TYPE_SENSOR_HUB_SCREEN_ON);
//        } else {
//            mSensorScreenOn = ss.get(0);
//            sb.append("��ȡSCREEN_ON������OK,size=" + ss.size() + ",������Type_idΪ="
//                    + TYPE_SENSOR_HUB_SCREEN_ON + "; ");
//        }
//        sb.append("\n");

        mSensorBrief = sb.toString();
        // mTips.setText(mSensorBrief);
        mTips.setText("���봫��������ס�����ֻ��ڿ��ӿڴ��������,��ʱ��Ҳ�ǳ�ʼ���֮һ�� �����ֻ�ˮƽ���������Ͼ�ֹ��������Ҳ�ǳ�ʼ���֮һ���� \n���û������ֻ�������׼ǰ���� ����������Camera�� \n������������������Ӧ�ô�ǰcamera��ĿǰDEMOֻ�ܴ򿪺�camera����Ϊ��׼api����ָ��ǰ��camera������cameraӦ���޸Ķ��ƹ���");
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        AlarmAlertWakeLock.acquireScreenCpuWakeLock(this);
        mFlagActivityResume = true;
//        if (mSensorAirMouse != null) {
//            mSm.registerListener(this, mSensorAirMouse, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorSnap != null) {
//            mSm.registerListener(this, mSensorSnap, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorShake != null) {
//            mSm.registerListener(this, mSensorShake, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorTap != null) {
//            mSm.registerListener(this, mSensorTap, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorFlip != null) {
//            mSm.registerListener(this, mSensorFlip, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorTwist != null) {
//            mSm.registerListener(this, mSensorTwist, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorTilt != null) {
//            mSm.registerListener(this, mSensorTilt, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorPDRHold != null) {
//            mSm.registerListener(this, mSensorPDRHold, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorFalling != null) {
//            mSm.registerListener(this, mSensorFalling, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorRGB != null) {
//            mSm.registerListener(this, mSensorRGB, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorGesture != null) {
//            mSm.registerListener(this, mSensorGesture, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorContextAwareness != null) {
//            mSm.registerListener(this, mSensorContextAwareness, SensorManager.SENSOR_DELAY_FASTEST);
//        }
//
//        if (mSensorScreenOn != null) {
//            mSm.registerListener(this, mSensorScreenOn, SensorManager.SENSOR_DELAY_FASTEST);
//        }

        if (mSensorAcce != null) {
            mSm.registerListener(this, mSensorAcce, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorProximity != null) {
            mSm.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
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
//        if (event.sensor.getType() == TYPE_SENSOR_HUB_AIR_MOUSE) {
//            if (event.values.length == 4) {
//                mSensorAirMouseData[0] = (int) event.values[0];
//                mSensorAirMouseData[1] = (int) event.values[1];
//                mSensorAirMouseData[2] = (int) event.values[2];
//                mSensorAirMouseData[3] = (int) event.values[3];
//            } else {
//                mSensorAirMouseData[0] = (int) event.values[0];
//                mSensorAirMouseData[1] = (int) event.values[1];
//                mSensorAirMouseData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SNAP) {
//            if (event.values.length == 4) {
//                mSensorSnapData[0] = (int) event.values[0];
//                mSensorSnapData[1] = (int) event.values[1];
//                mSensorSnapData[2] = (int) event.values[2];
//                mSensorSnapData[3] = (int) event.values[3];
//            } else {
//                mSensorSnapData[0] = (int) event.values[0];
//                mSensorSnapData[1] = (int) event.values[1];
//                mSensorSnapData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SHAKE) {
//            if (event.values.length == 4) {
//                mSensorShakeData[0] = (int) event.values[0];
//                mSensorShakeData[1] = (int) event.values[1];
//                mSensorShakeData[2] = (int) event.values[2];
//                mSensorShakeData[3] = (int) event.values[3];
//            } else {
//                mSensorShakeData[0] = (int) event.values[0];
//                mSensorShakeData[1] = (int) event.values[1];
//                mSensorShakeData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_TAP) {
//            if (event.values.length == 4) {
//                mSensorTapData[0] = (int) event.values[0];
//                mSensorTapData[1] = (int) event.values[1];
//                mSensorTapData[2] = (int) event.values[2];
//                mSensorTapData[3] = (int) event.values[3];
//            } else {
//                mSensorTapData[0] = (int) event.values[0];
//                mSensorTapData[1] = (int) event.values[1];
//                mSensorTapData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_FLIP) {
//            if (event.values.length == 4) {
//                mSensorFlipData[0] = (int) event.values[0];
//                mSensorFlipData[1] = (int) event.values[1];
//                mSensorFlipData[2] = (int) event.values[2];
//                mSensorFlipData[3] = (int) event.values[3];
//            } else {
//                mSensorFlipData[0] = (int) event.values[0];
//                mSensorFlipData[1] = (int) event.values[1];
//                mSensorFlipData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_TWIST) {
//            if (event.values.length == 4) {
//                mSensorTwistData[0] = (int) event.values[0];
//                mSensorTwistData[1] = (int) event.values[1];
//                mSensorTwistData[2] = (int) event.values[2];
//                mSensorTwistData[3] = (int) event.values[3];
//            } else {
//                mSensorTwistData[0] = (int) event.values[0];
//                mSensorTwistData[1] = (int) event.values[1];
//                mSensorTwistData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_TILT) {
//            if (event.values.length == 4) {
//                mSensorTiltData[0] = (int) event.values[0];
//                mSensorTiltData[1] = (int) event.values[1];
//                mSensorTiltData[2] = (int) event.values[2];
//                mSensorTiltData[3] = (int) event.values[3];
//            } else {
//                mSensorTiltData[0] = (int) event.values[0];
//                mSensorTiltData[1] = (int) event.values[1];
//                mSensorTiltData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_PDR_HOLD) {
//            if (event.values.length == 4) {
//                mSensorPDRHoldData[0] = (int) event.values[0];
//                mSensorPDRHoldData[1] = (int) event.values[1];
//                mSensorPDRHoldData[2] = (int) event.values[2];
//                mSensorPDRHoldData[3] = (int) event.values[3];
//            } else {
//                mSensorPDRHoldData[0] = (int) event.values[0];
//                mSensorPDRHoldData[1] = (int) event.values[1];
//                mSensorPDRHoldData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_FALLING) {
//            if (event.values.length == 4) {
//                mSensorFallingData[0] = (int) event.values[0];
//                mSensorFallingData[1] = (int) event.values[1];
//                mSensorFallingData[2] = (int) event.values[2];
//                mSensorFallingData[3] = (int) event.values[3];
//            } else {
//                mSensorFallingData[0] = (int) event.values[0];
//                mSensorFallingData[1] = (int) event.values[1];
//                mSensorFallingData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_RGB) {
//            if (event.values.length == 4) {
//                mSensorRGBData[0] = (int) event.values[0];
//                mSensorRGBData[1] = (int) event.values[1];
//                mSensorRGBData[2] = (int) event.values[2];
//                mSensorRGBData[3] = (int) event.values[3];
//            } else {
//                mSensorRGBData[0] = (int) event.values[0];
//                mSensorRGBData[1] = (int) event.values[1];
//                mSensorRGBData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_PROXIMITY_GESTURE) {
//            if (event.values.length == 4) {
//                mSensorGestureData[0] = (int) event.values[0];
//                mSensorGestureData[1] = (int) event.values[1];
//                mSensorGestureData[2] = (int) event.values[2];
//                mSensorGestureData[3] = (int) event.values[3];
//            } else {
//                mSensorGestureData[0] = (int) event.values[0];
//                mSensorGestureData[1] = (int) event.values[1];
//                mSensorGestureData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_CONTEXT_AWARENESS) {
//            if (event.values.length == 4) {
//                mSensorContextAwarenessData[0] = (int) event.values[0];
//                mSensorContextAwarenessData[1] = (int) event.values[1];
//                mSensorContextAwarenessData[2] = (int) event.values[2];
//                mSensorContextAwarenessData[3] = (int) event.values[3];
//            } else {
//                mSensorContextAwarenessData[0] = (int) event.values[0];
//                mSensorContextAwarenessData[1] = (int) event.values[1];
//                mSensorContextAwarenessData[2] = (int) event.values[2];
//            }
//        } else if (event.sensor.getType() == TYPE_SENSOR_HUB_SCREEN_ON) {
//            if (event.values.length == 4) {
//                mSensorScreenOnData[0] = (int) event.values[0];
//                mSensorScreenOnData[1] = (int) event.values[1];
//                mSensorScreenOnData[2] = (int) event.values[2];
//                mSensorScreenOnData[3] = (int) event.values[3];
//            } else {
//                mSensorScreenOnData[0] = (int) event.values[0];
//                mSensorScreenOnData[1] = (int) event.values[1];
//                mSensorScreenOnData[2] = (int) event.values[2];
//            }
//        } else 
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSensorAcceData[0] = (int) event.values[0];
            mSensorAcceData[1] = (int) event.values[1];
            mSensorAcceData[2] = (int) event.values[2];
            if (mFlagActivityResume && !mFlagStartMonitor
                    && (mFlagProximityNear || (Math.abs(mSensorAcceData[0]) <= 1
                            && Math.abs(mSensorAcceData[1]) <= 1
                            && mSensorAcceData[2] >= 9))) {
                // ���봫��������ס�����ֻ��ڿ��ӿڴ��������,��ʱ��Ҳ�ǳ�ʼ���֮һ��
                // �ֻ�ˮƽ���������Ͼ�ֹ��������Ҳ�ǳ�ʼ���֮һ��
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
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            mSensorProximityData[0] = (int) event.values[0];
            mFlagProximityNear = mSensorProximityData[0] == 0;
        }
        
        Log.i(TAG, ">>>>>acce : " + mSensorAcceData[0] + ", " + mSensorAcceData[1] + ", "
                + mSensorAcceData[2] + ", mFlagStartMonitor=" + mFlagStartMonitor);
        Log.i("zzz", ">>>>>Proximity : " + mSensorProximityData[0] + ", mFlagProximityNear="
                + mFlagProximityNear);
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    private void launchBackCamera() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK 
//                | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT 
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void launchFrontCamera() {
        Intent intent = new Intent();
        //intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK 
//                | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT 
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
