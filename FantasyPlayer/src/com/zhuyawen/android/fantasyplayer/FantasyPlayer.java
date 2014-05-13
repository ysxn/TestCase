package com.zhuyawen.android.fantasyplayer;

import com.zhuyawen.android.ffmpegwrapper.TestJni;
import com.zhuyawen.android.utils.DebugUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class FantasyPlayer extends Activity{
    public static final String TAG = DebugUtil.FANTASYPLAYER_TAG;
    public static final boolean DEBUG = DebugUtil.LOG_ENABLED_FANTASYPLAYER;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TestJni._init();
        if (DEBUG) {
            for (int i = 0 ; i < 10 ; i++) {
                Log.i(TAG,"i="+TestJni._getInt());
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}