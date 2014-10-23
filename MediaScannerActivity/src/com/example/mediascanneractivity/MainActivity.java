
package com.example.mediascanneractivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    static final String INTERNAL_VOLUME = "internal";
    static final String EXTERNAL_VOLUME = "external";
    private Button mScanButton;
    private TextView mState;

    BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (mState == null) {
                return;
            }
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
                mState.setText(R.string.scan_finish);
            } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)) {
                mState.setText(R.string.scan_start);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mScanButton = (Button) findViewById(R.id.button_scan);
        mState = (TextView) findViewById(R.id.tips);
        mScanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //MainActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                //        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                MediaScannerConnection.scanFile(MainActivity.this, new String[] {
                        Environment.getExternalStorageDirectory()
                            .getPath()
                }, null, null);
                
                Intent SECURE_CAMERA_INTENT = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE);
                SECURE_CAMERA_INTENT.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                MainActivity.this.startActivity(SECURE_CAMERA_INTENT);
            }
        });
        /*
        boolean has = CamcorderProfile.hasProfile(0, 7);
        CamcorderProfile c = CamcorderProfile.get(0, 7);
        Log.i("zyw", ">>>>>>hasProfile(0, 7)=="+has);
        Log.i("zyw", ">>>>>>get(0, 7)=="
                +"\n"
                +"duration="+c.duration
                +"\n"
                +"quality="+c.quality
                +"\n"
                +"fileFormat="+c.fileFormat
                +"\n"
                +"videoCodec="+c.videoCodec
                +"\n"
                +"videoBitRate="+c.videoBitRate
                +"\n"
                +"videoFrameRate="+c.videoFrameRate
                +"\n"
                +"videoFrameWidth="+c.videoFrameWidth
                +"\n"
                +"videoFrameHeight="+c.videoFrameHeight
                +"\n"
                +"audioCodec="+c.audioCodec
                +"\n"
                +"audioBitRate="+c.audioBitRate
                +"\n"
                +"audioSampleRate="+c.audioSampleRate
                +"\n"
                +"audioChannels="+c.audioChannels);
                */
        AudioManager as = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        String soundPath = Settings.Global.getString(this.getApplicationContext().getContentResolver(), "unlock_sound"/*Settings.Global.UNLOCK_SOUND*/);
        Log.i("zyw", "soundPath=="+soundPath);
        //09-24 10:49:31.390: I/zyw(16179): soundPath==/system/media/audio/ui/Unlock.ogg
        DisplayToast(soundPath);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        Log.i("zhuyawen",dm.toString());
        Log.i("zhuyawen", ">>>>>>>>>>>>>>>>>"
                +", metrics"+this.getResources().getDisplayMetrics().toString()
                +" , screenDensity = "+this.getResources().getDisplayMetrics().densityDpi);
        Toast.makeText(this, ">>>>>>>>>>>>>>>>>"
                +", metrics"+this.getResources().getDisplayMetrics().toString()
                +" , screenDensity = "+this.getResources().getDisplayMetrics().densityDpi, Toast.LENGTH_LONG).show();

    }
    
    private void DisplayToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addDataScheme("file");
        this.registerReceiver(mScanReceiver, intentFilter);
    }

}
/*
private void bindMediaScanner() {
    if (!mMediaScannerConnecting) {
        Intent intent = new Intent();
        intent.setClassName("com.android.providers.media",
                "com.android.providers.media.MediaScannerService");
        mMediaScannerConnecting = true;
        bindService(intent, mMediaScannerConnection, BIND_AUTO_CREATE); // 启动MediaScannerService对下载的文件进行扫描
    }
}
*/
