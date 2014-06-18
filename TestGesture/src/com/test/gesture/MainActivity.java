
package com.test.gesture;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Excel�������Ƕ�AvagoSensor�����󣬵���ĿǰAvago�޷�ȫ�����㡣��״�ǣ� 1�� ��������4������ 2��
 * Z������ṩ9�������⣨Ŀǰֻ֧��6����Avago�����޸�Ϊ9����
 * 
 * @author zhuyw1
 */
public class MainActivity extends Activity implements SensorEventListener {

    /*
     * k7�϶�����LEFT = 3��RIGHT = 2��DOWN = 5��UP = 4��NEAR = 6��FAR = 15 x2��Ŀ���Ƕ�����LEFT
     * = 3��RIGHT = 4��DOWN = 1��UP = 2��NEAR = 5��FAR = 6
     */
    public static final int LEFT = 3;// 3;//2;
    public static final int RIGHT = 4;// 3;2;//
    public static final int DOWN = 1;// 4;5;//
    public static final int UP = 2;// 5;4;//

    public static final int NEAR = 5;// 6;6;//
    public static final int FAR = 6;// 7;15;//

    static final String TAG = "zyw";

    // static final int MSG_RESET = 1;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��  ");
    private String mSensorBrief = "";
    private int mSensorData[] = {
            0, 0, 0
    };
    SensorManager mSm;
    Sensor mSensor;
    /**
     * k7����33171014������x2��Ŀ�϶���Ϊ33171011
     */
    static final int GESTURE_SENSOR = 33171011;// 33171014;//
                                               // Sensor.TYPE_ROTATION_VECTOR;//
                                               // 33171011;
    GestureView mGestureView;
    private TextView mTextViewSensorData;
    private static final int REQUEST_UPDATE_DATA = 299;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    if (mTextViewSensorData != null) {
                        mTextViewSensorData.setText("��ǰʱ�䣺" + date.format(System.currentTimeMillis()) + ". \n"
                                + mSensorBrief
                                + ": update gesture data:" + "\n"
                                + ";ԭʼ���ݣ�e.values[0]=" + mSensorData[0] + ", e.values[1]=" + mSensorData[1]
                                + ", e.values[2]=" + mSensorData[2] + "\n"
                                + "����e.values[0]���洫��");
                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureView = (GestureView) this.findViewById(R.id.gestureView1);
        mTextViewSensorData = (TextView) this.findViewById(R.id.data);
        mSm = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        List<Sensor> ss = mSm.getSensorList(GESTURE_SENSOR);
        if (ss == null || ss.size() == 0) {
            Toast.makeText(this, "��ȡ���ƴ�����Ϊ�գ����鴫����id�Ƿ�Ϊ��" + GESTURE_SENSOR,
                    Toast.LENGTH_LONG)
                    .show();
            finish();
            return;
        }
        Toast.makeText(this,
                "��ȡ���ƴ�����ok��getSensorList size=" + ss.size() + ",������idΪ��" + GESTURE_SENSOR,
                Toast.LENGTH_LONG)
                .show();
        StringBuilder sb = new StringBuilder();
        sb.append("getSensorList,size=" + ss.size() + ",������Type_idΪ=" + GESTURE_SENSOR + "; ");
        Sensor s;
        for (int i = 0; i < ss.size(); i++) {
            s = ss.get(i);
            sb.append("(i=" + i + ",name=" + s.getName() + ") ");
        }
        sb.append("\n");
        mSensorBrief = sb.toString();
        mTextViewSensorData.setText(mSensorBrief);

        mSensor = ss.get(0);
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // Log.i(TAG, "onResume");
        mSm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // Log.i(TAG, "onPause");
        mSm.unregisterListener(this);
    }

    final void handle(int data) {
        switch (data) {
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                if (mGestureView.getState() == GestureView.STATE_IDLE) {
                    mGestureView.setState(GestureView.STATE_WORKING);
                }
                mGestureView.setDirection(data);
                Log.i(TAG, "PENDING :" + dirString(data));
                return;
            case NEAR:
            case FAR:
                if (mGestureView.getState() == GestureView.STATE_IDLE) {
                    mGestureView.setState(GestureView.STATE_WORKING);
                }
                mGestureView.setScale(data);
                Log.i(TAG, "Proximity : " + dirString(data));
                break;
            default:
                if (mGestureView.getState() == GestureView.STATE_IDLE) {
                    mGestureView.setState(GestureView.STATE_WORKING);
                }
                mGestureView.setScale(data);
                Log.i(TAG, "default : " + dirString(data));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        // TODO Auto-generated method stub
        /**
         * k7������e.values[0]���洫�ݣ� ����x2��e.values[0]���洫��
         */
        mSensorData[0] = (int) e.values[0];
        mSensorData[1] = (int) e.values[1];
        mSensorData[2] = (int) e.values[2];
        //Log.i(TAG, "data:" + data);
        
        handle(mSensorData[0]);
    }

    final String dirString(int dir) {
        switch (dir) {
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case LEFT:
                return "LEFT";
            case RIGHT:
                return "RIGHT";
            case NEAR:
                return "NEAR";
            case FAR:
                return "FAR";
        }
        return Integer.toString(dir);
    }

}
