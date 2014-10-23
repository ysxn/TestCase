
package com.example.testmediaprofile;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final String TAG = "zyw";
    private final boolean DEBUG = true;

    private TextView mTips;
    private TextView mData;
    private EditText mCameraIdEdit;
    private EditText mQualityEdit;
    private Button mPause;

    private static final int REQUEST_UPDATE_DATA = 299;
    private int mCameraId = 0;
    private int mQuality = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    String sC = (String) msg.obj;
                    mData.setText(sC);
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

        mCameraIdEdit = (EditText) findViewById(R.id.cameraid);
        mQualityEdit = (EditText) findViewById(R.id.quality);
        mTips = (TextView) findViewById(R.id.tips);
        mTips.setVisibility(View.GONE);
        mData = (TextView) findViewById(R.id.data);
        Button exit = (Button) findViewById(R.id.button_get);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                boolean numOK = true;
                String s = mCameraIdEdit.getText().toString();
                try {
                    mCameraId = Integer.valueOf(s);
//                    if (mCameraId != 0 && mCameraId != 1) {
//                        numOK =false;
//                        Toast.makeText(MainActivity.this, "数字为空或者非法输入！！！", Toast.LENGTH_LONG).show();
//                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "数字为空或者非法输入！！！", Toast.LENGTH_LONG).show();
                    numOK =false;
                }
                s = mQualityEdit.getText().toString();
                try {
                    mQuality = Integer.valueOf(s);
//                    if (mQuality < 0 || mQuality > 23) {
//                        numOK =false;
//                        Toast.makeText(MainActivity.this, "数字为空或者非法输入！！！", Toast.LENGTH_LONG).show();
//                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "数字为空或者非法输入！！！", Toast.LENGTH_LONG).show();
                    numOK =false;
                }
                if (numOK) {
                    getMediaProfile();
                }
            }
        });

    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        Log.i(TAG, ">>>>>>>>>>>>>>newConfig="+newConfig);
        Thread.dumpStack();
        super.onConfigurationChanged(newConfig);
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
        super.onDestroy();
    }

    private void getMediaProfile() {
        boolean has = CamcorderProfile.hasProfile(mCameraId, mQuality);
        CamcorderProfile c = null;
        String sc1 = "";
        if (mCameraId == 0) {
            sc1 = ">>>>>>hasProfile(" + "后置摄像头" + ", 质量为 " + mQuality + ")==" + has;
        } else if (mCameraId == 1) {
            sc1 = ">>>>>>hasProfile(" + "前置摄像头" + ", 质量为 " + mQuality + ")==" + has;
        } else {
            sc1 = ">>>>>>hasProfile(" + mCameraId + ", " + mQuality + ")==" + has;
        }
        
        String sc2 = "";
        if (has) {
            try {
            c = CamcorderProfile.get(mCameraId, mQuality);
            sc2 = "\n"
                    + "duration=" + c.duration
                    + "\n"
                    + "quality=" + c.quality
                    + "\n"
                    + "fileFormat=" + c.fileFormat
                    + "\n"
                    + "videoCodec=" + c.videoCodec
                    + "\n"
                    + "videoBitRate=" + c.videoBitRate
                    + "\n"
                    + "videoFrameRate=" + c.videoFrameRate
                    + "\n"
                    + "videoFrameWidth=" + c.videoFrameWidth
                    + "\n"
                    + "videoFrameHeight=" + c.videoFrameHeight
                    + "\n"
                    + "audioCodec=" + c.audioCodec
                    + "\n"
                    + "audioBitRate=" + c.audioBitRate
                    + "\n"
                    + "audioSampleRate=" + c.audioSampleRate
                    + "\n"
                    + "audioChannels=" + c.audioChannels;
            } catch (IllegalArgumentException e) {
                sc2 = "\n"
                        +"Unsupported quality level "+mQuality;
            }
            
        }
        Message msg = new Message();
        msg.what = REQUEST_UPDATE_DATA;
        msg.obj = sc1 + sc2;
        mHandler.sendMessage(msg);

    }
}
