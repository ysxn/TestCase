
package com.example.testctscase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {
    private final String TAG = "zyw";
    private final boolean DEBUG = true;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");

    SensorManager mSensorManager;

    private TextView mTips;
    private TextView mData;
    private Button mPause;
    private Button mResume;
    
    private static final int REQUEST_UPDATE_DATA = 299;
    private static final int REQUEST_UPDATE_DATA_ERROR = 399;
    private static final int REQUEST_UPDATE_DATA_MAIN = 699;
    private boolean mFlagTest = true;
    
    private Thread mThread;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    String sC = (String) msg.obj;
                    mData.setText(date.format(System.currentTimeMillis())+">>>"+sC+"\n"+mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA_ERROR: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA_ERROR);
                    String error = (String) msg.obj;
                    mData.setText(date.format(System.currentTimeMillis())+">>>"+error+"\n"+mData.getText());
                    Log.e("cts", ">>>>>>>>5����û�������ϱ��ˣ�Sensor HUB���ܳ����⣬�����Զ�������ע���ṩmtklog��������������");
                    mFlagTest = false;
                    updateText("\n \n");
                    updateText("����EXIT�˳�Ӧ�ã����mtklog�ṩ����������������");
                }
                    break;
                case REQUEST_UPDATE_DATA_MAIN: {
                    mTips.setText("��ǰʱ�䣺"+date.format(System.currentTimeMillis()));
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_MAIN, 1000);
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
        Button exit = (Button) findViewById(R.id.button_exit);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mFlagTest = false;
                finish();
            }
        });
        
        mPause = (Button) findViewById(R.id.button_pause);
        mResume = (Button) findViewById(R.id.button_resume);
        mPause.setVisibility(View.INVISIBLE);
        mPause.setEnabled(false);
        mResume.setEnabled(true);
        mPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mFlagTest = false;
                mPause.setEnabled(false);
                mResume.setEnabled(true);
            }
        });
        mResume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPause.setEnabled(true);
                mResume.setEnabled(false);
                mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Log.e("cts", ">>>>>>>>�ȶ��Բ��Կ�ʼ��");
                            updateText(">>>>>>>>�ȶ��Բ��Կ�ʼ��");
                            testBatchAndFlush();
                            mHandler.removeMessages(REQUEST_UPDATE_DATA_ERROR);
                            Log.e("cts", ">>>>>>>>�ȶ��Բ�������������");
                            updateText(">>>>>>>>�ȶ��Բ�������������");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.e("cts", ">>>>>>>>�ȶ��Բ����쳣������"+"\n \n"+e.toString());
                            updateText(">>>>>>>>�ȶ��Բ����쳣������"+"\n \n"+e.toString());
                        }
                    }
                };
                mThread.start();
                
            }
        });
        try {
            setUpSensors();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA_MAIN, 1000);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mFlagTest = false;
        mHandler.removeMessages(REQUEST_UPDATE_DATA_MAIN);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        // super.onConfigurationChanged(newConfig);
    }
    
    private ArrayList<Sensor> mContinuousSensorList;
    
    private void setUpSensors() throws Exception {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mContinuousSensorList = new ArrayList<Sensor>();
        Log.e("cts", ">>>>>>>>CTS : Start search continuous sensor");
        //updateText(">>>>>>>>CTS : Start search continuous sensor");
        for (int i = Sensor.TYPE_ACCELEROMETER; i <= Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR; ++i) {
            Sensor sensor = mSensorManager.getDefaultSensor(i);
            // Skip all non-continuous mode sensors.
            if (sensor == null || Sensor.TYPE_SIGNIFICANT_MOTION == i ||
                Sensor.TYPE_STEP_COUNTER == i || Sensor.TYPE_STEP_DETECTOR == i ||
                Sensor.TYPE_LIGHT == i || Sensor.TYPE_PROXIMITY == i ||
                Sensor.TYPE_AMBIENT_TEMPERATURE == i) {
                continue;
            }
            mContinuousSensorList.add(sensor);
            Log.e("cts", ">>>>>>>>CTS : add sensor="+sensor.toString());
            //updateText(">>>>>>>>CTS : add sensor="+sensor.toString());
        }
        
        Log.e("cts", "\n \n");
        //updateText("\n \n");
        Log.e("cts", ">>>>>>>>CTS : Finish search continuous sensor");
        //updateText(">>>>>>>>CTS : Finish search continuous sensor");
        
        Log.e("cts", ">>>>>>>>�ȶ��Բ��� : ���� START");
        updateText("�ο�CTS������Ŀ����SensorTest�Ĵ��룬cts�����Ǽ�����������ϱ���������REPORTING_MODE_CONTINUOUS��Sensor��ÿ��Sensor�����յ�25���ϱ����ݺ���жϸ�Sensor�������������޸����߼����������߼���������������ѭ������ʱ5������Ϊ���Ź���һ������5����û�������ϱ����ͽ�����ʾ����LOG��");
        
        updateText("\n \n");
        updateText(">>>>>>>>��ǵÿ�ʼǰ��mtklog������ ��ʼ�󱾲���������ʱ�����У�Ҫ�ֶ�������������EXIT");
        
        updateText("\n \n");
        updateText(">>>>>>>> �ȶ��Բ��� : ���� START");
        updateText("\n \n");
    }
    
    // Register for updates from each continuous mode sensor, wait for 25 events, call flush and
    // wait for flushCompleteEvent before unregistering for the sensor.
    public void testBatchAndFlush() throws Exception {
        while(mFlagTest) {
            for (Sensor sensor : mContinuousSensorList) {
                if (!mFlagTest) {
                    return;
                }
                final CountDownLatch eventReceived = new CountDownLatch(25);
                final CountDownLatch flushReceived = new CountDownLatch(1);
                SensorEventListener2 listener = new SensorEventListener2() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        Log.e("cts", ">>>>>>>>onSensorChanged");
                        //updateText(">>>>>>>>onSensorChanged");
                        mHandler.removeMessages(REQUEST_UPDATE_DATA_ERROR);
                        postWatchDog();
                        eventReceived.countDown();
                    }
    
                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
    
                    @Override
                    public void onFlushCompleted(Sensor sensor) {
                        Log.e("cts", ">>>>>>>>onFlushCompleted");
                        //updateText(">>>>>>>>onFlushCompleted");
                        mHandler.removeMessages(REQUEST_UPDATE_DATA_ERROR);
                        postWatchDog();
                        flushReceived.countDown();
                    }
                };
                
                boolean result = mSensorManager.registerListener(listener, sensor,
                                                SensorManager.SENSOR_DELAY_NORMAL, 10000000);
                Log.e("cts", ">>>>>>>>start �ȶ��Բ��� for sensor="+sensor.toString());
                //updateText(">>>>>>>>start �ȶ��Բ��� for sensor="+sensor.toString());
                if (result) {
                    Log.i("cts", "current registerListener pass");
                    //updateText("current registerListener pass");
                } else {
                    Log.i("cts", "current registerListener fail");
                    //updateText("current registerListener fail");
                }
                // Wait for 25 events and call flush.
                if (!mFlagTest) {
                    mSensorManager.unregisterListener(listener);
                    return;
                }
                postWatchDog();
                if (!mFlagTest) {
                    mSensorManager.unregisterListener(listener);
                    return;
                }
                eventReceived.await();
                result = mSensorManager.flush(listener);
                if (result) {
                    Log.i("cts", "current mSensorManager.flush ok");
                    //updateText("current mSensorManager.flush ok");
                } else {
                    Log.i("cts", "current mSensorManager.flush fail");
                    //updateText("current mSensorManager.flush fail");
                }
                if (!mFlagTest) {
                    mSensorManager.unregisterListener(listener);
                    return;
                }
                postWatchDog();
                if (!mFlagTest) {
                    mSensorManager.unregisterListener(listener);
                    return;
                }
                flushReceived.await();
                mSensorManager.unregisterListener(listener);
                Log.e("cts", ">>>>>>>>success end �ȶ��Բ��� for sensor="+sensor.toString());
                //updateText(">>>>>>>>success end �ȶ��Բ��� for sensor="+sensor.toString());
            }
        }
    }
    
    private void updateText(String data) {
        Message msg = mHandler.obtainMessage(REQUEST_UPDATE_DATA);
        msg.obj = data;
        mHandler.sendMessageDelayed(msg, 0);
    }
    
    private void postWatchDog() {
        Message msg = mHandler.obtainMessage(REQUEST_UPDATE_DATA_ERROR);
        msg.obj = ">>>>>>>>5����û�������ϱ��ˣ�Sensor HUB���ܳ����⣬�����Զ�������ע���ṩmtklog��������������";
        mHandler.sendMessageDelayed(msg, 300000);
    }

}
