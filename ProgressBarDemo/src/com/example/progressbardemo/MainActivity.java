
package com.example.progressbardemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ProgressBar mIndeterminateProgressBar;
    private ProgressBar mDeterminateProgressBar;
    private int progress = 0;
    private int seProgress = 200;
    private final int REQUEST_UPDATE_DATA = 0x887;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);

                    if (mDeterminateProgressBar != null) {
                        progress++;
                        seProgress++;
                        if (progress > 1000 || progress < 0) {
                            progress = 0;
                        }
                        if (seProgress > 1000 || seProgress < 0) {
                            seProgress = 200;
                        }
                        mDeterminateProgressBar.setProgress(progress);
                        mDeterminateProgressBar.setSecondaryProgress(seProgress);
                    }
                    mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 10L);
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mIndeterminateProgressBar = (ProgressBar) findViewById(R.id.indeterminate_progressBar);
        mDeterminateProgressBar = (ProgressBar) findViewById(R.id.determinate_progressBar);
        
        ProgressBar progress_small_title = (ProgressBar) findViewById(R.id.progress_small_title);
        progress_small_title.setIndeterminateDrawable(this.getDrawable(R.drawable.progress_m_material));
        progress_small_title.setIndeterminate(true);
        progress_small_title.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 10L);
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
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

}
