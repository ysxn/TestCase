
package com.example.testsettingsensorhub;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    private long mStartDelayTime = 1L;// System.currentTimeMillis();

    //private ArrayList<AcceData> mArrayListSensorAcceDataFront = new ArrayList<AcceData>(100);
    //private ArrayList<AcceData> mArrayListSensorAcceDataBack = new ArrayList<AcceData>(100);
    private BufferStack<AcceData> mBufferStack = new BufferStack<AcceData>(20);
    private int mCount = 0;
    private boolean mExitFlag = false;
    private boolean mStartTestMyBufferFlag = false;

    private final int REQUEST_CAPTURE = 0x2341;
    
    
    private CheckBox mCheckBoxPickup;
    private CheckBox mCheckBoxWave;
    Sensor mSensorAcce;
    Sensor mSensorGyro;
    Sensor mSensorOrien;
    Sensor mSensorProximity;
    Sensor mSensorGesture;
    SensorManager mSm;
    
    private final static String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath()+"/8712.txt"; //"/sdcard/879123.txt";
    private final static String BRIGHTNESS_PATH = "/sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness";
    private final static String I2C_SENSOR_HUB_PATH = "/sys/devices/platform/mt-i2c.3/i2c-3/3-003a/input/input1/enable";


    private static final int REQUEST_UPDATE_DATA = 299;
    private static final int REQUEST_RECKON_DATA = 399;
    private static final int REQUEST_DUMP_DATA = 499;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    FileReader file = null;
                    char[] buffer = new char[1024];
                    int b = -1;
                    try {
                        /**
                         * FileInputStream.jva
                         * <p>
                         * BufferedInputStream.java
                         * <p>
                         * BufferedReader.java
                         * <p>
                         * InputStreamReader.java
                         * 这个显示buffer是乱码，最后有"\n"换行符。
                         */
                        file = new FileReader(I2C_SENSOR_HUB_PATH);
                        int len = file.read(buffer, 0, 1024);
                        b =Integer.valueOf((new String(buffer, 0, len)).trim());
                    } catch (FileNotFoundException e) {
                        Log.e(TAG,
                                "FileNotFoundException！ path="+I2C_SENSOR_HUB_PATH);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    } finally {
                        if (file != null) {
                            try {
                                file.close();
                                file = null;
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    Log.i(TAG, ">>>>>>>>>>>b = "+b+"\n");
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 1000L);
                }
                break;
                case REQUEST_RECKON_DATA: {
                    mHandler.removeMessages(REQUEST_RECKON_DATA);
                    Log.i("zzzz", ">>>>>handler REQUEST_RECKON_DATA");
                }
                break;
                case REQUEST_DUMP_DATA: {
                    mHandler.removeMessages(REQUEST_DUMP_DATA);
                    ArrayList<AcceData> mArrayListData = mBufferStack.dump();
                    
                    Iterator<AcceData> iterator = mArrayListData.iterator();
                    while (iterator.hasNext()) {
                        AcceData data = (AcceData) iterator.next();
                        Log.i("zzzz",
                              ">>>>>>>AcceData dump time = " + data.time
                                      + ", x=" + data.x
                                      + ", y=" + data.y
                                      + ", z=" + data.z);
                    }
                    mArrayListData = null;
                    Log.i("zzzz", ">>>>>handler REQUEST_DUMP_DATA");
                    mHandler.sendEmptyMessageDelayed(REQUEST_DUMP_DATA, 1000L);
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
            } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                Log.i(TAG, ">>>>>>>>>>>>ACTION_SCREEN_ON");
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
        mCheckBoxPickup = (CheckBox) findViewById(R.id.open_pick_up);
        mCheckBoxWave = (CheckBox) findViewById(R.id.open_wave);
        
        mSm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorAcce = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyro = mSm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorOrien = mSm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorProximity = mSm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        List<Sensor> ss;
        ss = mSm.getSensorList(TYPE_SENSOR_HUB_PROXIMITY_GESTURE);
        if (ss == null || ss.size() == 0) {
            Log.i(TAG, "获取手势传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_PROXIMITY_GESTURE);
            Toast.makeText(this, "获取手势传感器为空，请检查传感器id是否为：" + TYPE_SENSOR_HUB_PROXIMITY_GESTURE,
                    Toast.LENGTH_LONG).show();
        } else {
            mSensorGesture = ss.get(0);
            Log.i(TAG, "获取手势传感器OK,size=" + ss.size() + ",传感器Type_id为="
                    + TYPE_SENSOR_HUB_PROXIMITY_GESTURE + "; ");
            Toast.makeText(this, "获取手势传感器OK,size=" + ss.size() + ",传感器Type_id为="
                    + TYPE_SENSOR_HUB_PROXIMITY_GESTURE + "; ", Toast.LENGTH_LONG).show();
        }

        mCheckBoxPickup.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox checkbox = (CheckBox)v;
                if (checkbox.isChecked()) {
                    startPickUp();
                } else {
                    stopPickUp();
                }
            }
        });
        
        mCheckBoxWave.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox checkbox = (CheckBox)v;
                if (checkbox.isChecked()) {
                    startWave();
                } else {
                    stopWave();
                }
            }
        });

        Button b = (Button) findViewById(R.id.button_exit);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        
        Button acceOpen = (Button) findViewById(R.id.acce_open);
        Button acceClose = (Button) findViewById(R.id.acce_close);
        Button gestureOpen = (Button) findViewById(R.id.gesture_open);
        Button gestureClose = (Button) findViewById(R.id.gesture_close);
        acceOpen.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mSensorAcce != null) {
                    mSm.registerListener(MainActivity.this, mSensorAcce,
                            SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });
        
        acceClose.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mSensorAcce != null) {
                    mSm.unregisterListener(MainActivity.this, mSensorAcce);
                }
            }
        });
        
        gestureOpen.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mSensorGesture != null) {
                    mSm.registerListener(MainActivity.this, mSensorGesture,
                            SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });
        
        gestureClose.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mSensorGesture != null) {
                    mSm.unregisterListener(MainActivity.this, mSensorGesture);
                }
            }
        });

        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 1000L);
        if (mStartTestMyBufferFlag) {
            new Thread() {
                @Override
                public void run() {
                    while (mCount < Integer.MAX_VALUE && !mExitFlag) {
                        mCount ++;
                        long time = System.currentTimeMillis();
        
                        AcceData data = new AcceData(time, mCount,
                                mCount,
                                mCount);
                        mBufferStack.push(data);
                    }
                }
            }.start();
            
            mHandler.sendEmptyMessageDelayed(REQUEST_DUMP_DATA, 1000L);
        }
        
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
        
        //AlarmAlertWakeLock.acquireScreenCpuWakeLock(this);
//        if (mSensorAcce != null) {
//            mSm.registerListener(this, mSensorAcce, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//
//        if (mSensorProximity != null) {
//            mSm.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//
//        if (mSensorGyro != null) {
//            mSm.registerListener(this, mSensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//
//        if (mSensorOrien != null) {
//            mSm.registerListener(this, mSensorOrien, SensorManager.SENSOR_DELAY_NORMAL);
//        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mExitFlag = true;

        //AlarmAlertWakeLock.releaseCpuLock();
//        mSm.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        MainActivity.this.unregisterReceiver(mBroadcastReceiver);
    }
    
    protected void stopWave() {
        // TODO Auto-generated method stub
        stopPickUp();
    }

    protected void startWave() {
        // TODO Auto-generated method stub
        startPickUp();
    }

    protected void stopPickUp() {
        // TODO Auto-generated method stub
        writeCmd((int) (Math.random() * 100));
    }
    
    protected void startPickUp() {
        // TODO Auto-generated method stub
        writeCmd((int) (Math.random() * 100));
    }

    protected void writeCmd(int cmd) {
        // TODO Auto-generated method stub
        int i = cmd;
        int brightness = i % 255;
        Log.i(TAG, ">>>>>write i = "+i +", brightness="+brightness);

        execShellCmd(i);
        //Settings.System.putInt(MainActivity.this.getContentResolver(),
        //        Settings.System.SCREEN_BRIGHTNESS, i);
        FileWriter file = null;
        try {
            file = new FileWriter(SDCARD_PATH);
            file.write(String.valueOf(i));
            file.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Log.i(TAG, ">>>>>>>>>>write SDCARD_PATH fail!");
        } finally {
            if (file != null) {
                try {
                    file.close();
                    file= null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        try {
            file = new FileWriter(BRIGHTNESS_PATH);
            Log.i(TAG, ">>>>>>>>>>write BRIGHTNESS_PATH brightness="+brightness);
            file.write(String.valueOf(brightness));
            file.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Log.i(TAG, ">>>>>>>>>>write BRIGHTNESS_PATH  fail!");
        } finally {
            if (file != null) {
                try {
                    file.close();
                    file= null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    /**
     * If device has root manager, it will show dialog to let user authorize this su.
     * Otherwise this su action will fail.
     * @param i
     */
    private void execShellCmd(int i) {
        String cmd1 = "echo " + i
                + " > /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness";
        String cmd2 = "ls -la";
        String suffix = "\n";
        Log.i(TAG, ">>>>>>>>>>exec start, yyyyyyyyyyyy");
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/sh");
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes("cd /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/"+suffix);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("pwd"+suffix);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("ls -la"+suffix);
            dataOutputStream.flush();
//            dataOutputStream.writeBytes("pwd"+suffix);
//            dataOutputStream.flush();
            dataOutputStream.writeBytes("cat brightness"+suffix);
            dataOutputStream.flush();
//            dataOutputStream.writeBytes("su"+suffix);
//            dataOutputStream.flush();
            dataOutputStream.writeBytes("chmod 777 brightness"+suffix);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("ls -la"+suffix);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit"+suffix);
            dataOutputStream.flush();
            dataOutputStream.close();
            try {
                int r = -1;
                if ((r = process.waitFor()) != 0) {
                    Toast.makeText(MainActivity.this,
                            "执行echo失败，请检查权限！return error = " + r, Toast.LENGTH_LONG)
                            .show();
                    Log.i(TAG, ">>>>>>>>>>exec echo fails, return error = " + r);
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>exec result start :"+"\n");
                Log.i(TAG, stringBuffer.toString());
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>exec result end :"+"\n");
                in.close();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "InterruptedException 执行echo失败，请检查权限！", Toast.LENGTH_LONG)
                        .show();
                Log.i(TAG, ">>>>>>>>>>exec echo fails, InterruptedException");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "IOException 执行echo失败，请检查权限！",
                    Toast.LENGTH_LONG)
                    .show();
            Log.i(TAG, ">>>>>>>>>>exec echo fails, IOException");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

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
