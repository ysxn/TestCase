
package com.example.testctsvolume;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.ADJUST_SAME;
import static android.media.AudioManager.FLAG_ALLOW_RINGER_MODES;
import static android.media.AudioManager.MODE_IN_CALL;
import static android.media.AudioManager.MODE_IN_COMMUNICATION;
import static android.media.AudioManager.MODE_NORMAL;
import static android.media.AudioManager.MODE_RINGTONE;
import static android.media.AudioManager.RINGER_MODE_NORMAL;
import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;
import static android.media.AudioManager.STREAM_MUSIC;
import static android.media.AudioManager.USE_DEFAULT_STREAM_TYPE;
import static android.media.AudioManager.VIBRATE_SETTING_OFF;
import static android.media.AudioManager.VIBRATE_SETTING_ON;
import static android.media.AudioManager.VIBRATE_SETTING_ONLY_SILENT;
import static android.media.AudioManager.VIBRATE_TYPE_NOTIFICATION;
import static android.media.AudioManager.VIBRATE_TYPE_RINGER;
import static android.provider.Settings.System.SOUND_EFFECTS_ENABLED;

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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final String TAG = "zyw";
    private final boolean DEBUG = true;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒  ");

    SensorManager mSensorManager;

    private TextView mTips;
    private TextView mData;
    private Button mPause;
    private Button mResume;
    
    private static final int REQUEST_UPDATE_DATA = 299;
    private static final int REQUEST_UPDATE_DATA_ERROR = 399;
    private boolean mFlagTest = true;
    
    private Thread mThread;
    private final static int MP3_TO_PLAY = R.raw.testmp3;
    private final static long TIME_TO_PLAY = 2000;
    private AudioManager mAudioManager;
    private boolean mUseFixedVolume = false;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    String sC = (String) msg.obj;
                    mData.setText(sC+"\n"+mData.getText());
                }
                    break;
                case REQUEST_UPDATE_DATA_ERROR: {
                    String error = (String) msg.obj;
                    mData.setText(error+"\n"+mData.getText());
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
        
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        updateText("mAudioManager.isWiredHeadsetOn()="+mAudioManager.isWiredHeadsetOn());
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
                            Log.e("cts", ">>>>>>>>testVolume测试开始！");
                            updateText(">>>>>>>>testVolume测试开始！");
                           testVolume();
                            mHandler.removeMessages(REQUEST_UPDATE_DATA_ERROR);
                            Log.e("cts", ">>>>>>>>testVolume测试正常结束！");
                            updateText(">>>>>>>>testVolume测试正常结束！");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.e("cts", ">>>>>>>>testVolume测试异常结束！"+"\n \n"+e.toString());
                            updateText(">>>>>>>>testVolume测试异常结束！"+"\n \n"+e.toString());
                        }
                    }
                };
                mThread.start();
                
            }
        });

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
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        // super.onConfigurationChanged(newConfig);
    }
    
    public void testVolume() throws Exception {
        int[] streams = { AudioManager.STREAM_ALARM,
                          AudioManager.STREAM_MUSIC,
                          AudioManager.STREAM_VOICE_CALL,
                          AudioManager.STREAM_RING };

        mAudioManager.adjustVolume(ADJUST_RAISE, 0);
        mAudioManager.adjustSuggestedStreamVolume(
                ADJUST_LOWER, USE_DEFAULT_STREAM_TYPE, 0);
        int maxMusicVolume = mAudioManager.getStreamMaxVolume(STREAM_MUSIC);
        updateText(">>>>>>mAudioManager.getStreamMaxVolume(STREAM_MUSIC)="+maxMusicVolume);

        for (int i = 0; i < streams.length; i++) {
            if (streams[i] == AudioManager.STREAM_ALARM) {
                updateText(">>>>>>start test STREAM_ALARM");
            } else if (streams[i] == AudioManager.STREAM_MUSIC) {
                updateText(">>>>>>start test STREAM_MUSIC");
            } else if (streams[i] == AudioManager.STREAM_VOICE_CALL) {
                updateText(">>>>>>start test STREAM_VOICE_CALL");
            } else if (streams[i] == AudioManager.STREAM_RING) {
                updateText(">>>>>>start test STREAM_RING");
            }
            // set ringer mode to back normal to not interfere with volume tests
            mAudioManager.setRingerMode(RINGER_MODE_NORMAL);

            int maxVolume = mAudioManager.getStreamMaxVolume(streams[i]);
            updateText(">>>>>>mAudioManager.getStreamMaxVolume="+maxVolume);

            mAudioManager.setStreamVolume(streams[i], 1, 0);
            if (mUseFixedVolume) {
                assertEquals(maxVolume, mAudioManager.getStreamVolume(streams[i]));
                continue;
            }
            assertEquals(1, mAudioManager.getStreamVolume(streams[i]));

            if (streams[i] == AudioManager.STREAM_MUSIC && mAudioManager.isWiredHeadsetOn()) {
                // due to new regulations, music sent over a wired headset may be volume limited
                // until the user explicitly increases the limit, so we can't rely on being able
                // to set the volume to getStreamMaxVolume(). Instead, determine the current limit
                // by increasing the volume until it won't go any higher, then use that volume as
                // the maximum for the purposes of this test
                updateText(">>>>>>isWiredHeadsetOn");
                int curvol = 0;
                int prevvol = 0;
                do {
                    prevvol = curvol;
                    mAudioManager.adjustStreamVolume(streams[i], ADJUST_RAISE, 0);
                    curvol = mAudioManager.getStreamVolume(streams[i]);
                } while (curvol != prevvol);
                maxVolume = maxMusicVolume = curvol;
            }
            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
            mAudioManager.adjustStreamVolume(streams[i], ADJUST_RAISE, 0);
            assertEquals(maxVolume, mAudioManager.getStreamVolume(streams[i]));

            mAudioManager.adjustSuggestedStreamVolume(ADJUST_LOWER, streams[i], 0);
            assertEquals(maxVolume - 1, mAudioManager.getStreamVolume(streams[i]));

            // volume lower
            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
            for (int k = maxVolume; k > 0; k--) {
                mAudioManager.adjustStreamVolume(streams[i], ADJUST_LOWER, 0);
                assertEquals(k - 1, mAudioManager.getStreamVolume(streams[i]));
            }

            mAudioManager.adjustStreamVolume(streams[i], ADJUST_SAME, 0);
            // volume raise
            mAudioManager.setStreamVolume(streams[i], 1, 0);
            for (int k = 1; k < maxVolume; k++) {
                mAudioManager.adjustStreamVolume(streams[i], ADJUST_RAISE, 0);
                assertEquals(1 + k, mAudioManager.getStreamVolume(streams[i]));
            }

            // volume same
            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
            for (int k = 0; k < maxVolume; k++) {
                mAudioManager.adjustStreamVolume(streams[i], ADJUST_SAME, 0);
                assertEquals(maxVolume, mAudioManager.getStreamVolume(streams[i]));
            }

            mAudioManager.setStreamVolume(streams[i], maxVolume, 0);
        }

        if (mUseFixedVolume) {
            return;
        }

        // adjust volume
        mAudioManager.adjustVolume(ADJUST_RAISE, 0);

        MediaPlayer mp = MediaPlayer.create(MainActivity.this, MP3_TO_PLAY);
        mp.setAudioStreamType(STREAM_MUSIC);
        mp.setLooping(true);
        mp.start();
        Thread.sleep(TIME_TO_PLAY);
        assertTrue(mAudioManager.isMusicActive());

        // adjust volume as ADJUST_SAME
        for (int k = 0; k < maxMusicVolume; k++) {
            mAudioManager.adjustVolume(ADJUST_SAME, 0);
            assertEquals(maxMusicVolume, mAudioManager.getStreamVolume(STREAM_MUSIC));
        }

        // adjust volume as ADJUST_RAISE
        mAudioManager.setStreamVolume(STREAM_MUSIC, 1, 0);
        for (int k = 0; k < maxMusicVolume - 1; k++) {
            mAudioManager.adjustVolume(ADJUST_RAISE, 0);
            assertEquals(2 + k, mAudioManager.getStreamVolume(STREAM_MUSIC));
        }

        // adjust volume as ADJUST_LOWER
        mAudioManager.setStreamVolume(STREAM_MUSIC, maxMusicVolume, 0);
        maxMusicVolume = mAudioManager.getStreamVolume(STREAM_MUSIC);

        mAudioManager.adjustVolume(ADJUST_LOWER, 0);
        assertEquals(maxMusicVolume - 1, mAudioManager.getStreamVolume(STREAM_MUSIC));
        mp.stop();
        mp.release();
        Thread.sleep(TIME_TO_PLAY);
        assertFalse(mAudioManager.isMusicActive());
    }
    
    private void assertEquals(int volume1, int volume2) throws Exception {
        // TODO Auto-generated method stub
        if (volume1 == volume2) {
            Log.i(TAG, ">>>>>>assertEquals pass! volume1="+volume1+", volume2="+volume2);
            updateText(">>>>>>assertEquals pass! volume1="+volume1+", volume2="+volume2);
        } else {
            Log.i(TAG, ">>>>>>assertEquals fail! volume1="+volume1+", volume2="+volume2);
            updateText(">>>>>>assertEquals fail! volume1="+volume1+", volume2="+volume2);
            Thread.dumpStack();
            throw new Exception(">>>>>>assertEquals fail! volume1="+volume1+", volume2="+volume2);
        }
    }
    
    private void assertFalse(boolean result) throws Exception {
        // TODO Auto-generated method stub
        if (!result) {
            Log.i(TAG, ">>>>>>assertFalse pass! result="+result);
            updateText(">>>>>>assertFalse pass! result="+result);
        } else {
            Log.i(TAG, ">>>>>>assertFalse fail! result="+result);
            updateText(">>>>>>assertFalse fail! result="+result);
            Thread.dumpStack();
            throw new Exception(">>>>>>assertFalse fail! result="+result);
        }
    }
    
    private void assertTrue(boolean result) throws Exception {
        // TODO Auto-generated method stub
        if (result) {
            Log.i(TAG, ">>>>>>assertTrue pass! result="+result);
            updateText(">>>>>>assertTrue pass! result="+result);
        } else {
            Log.i(TAG, ">>>>>>assertTrue fail! result="+result);
            updateText(">>>>>>assertTrue fail! result="+result);
            Thread.dumpStack();
            throw new Exception(">>>>>>assertTrue fail! result="+result);
        }
    }

    private void updateText(String data) {
        Message msg = mHandler.obtainMessage(REQUEST_UPDATE_DATA);
        msg.obj = data;
        mHandler.sendMessageDelayed(msg, 0);
    }
    
    private void postWatchDog() {
        Message msg = mHandler.obtainMessage(REQUEST_UPDATE_DATA_ERROR);
        msg.obj = ">>>>>>>>15秒钟没有数据上报了，Sensor HUB可能出问题，注意提供mtklog分析！！！！！";
        mHandler.sendMessageDelayed(msg, 15000);
    }

}
