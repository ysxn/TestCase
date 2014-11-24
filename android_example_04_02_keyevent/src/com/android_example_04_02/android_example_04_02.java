package com.android_example_04_02;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class android_example_04_02 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	KeyEvent key = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
    	return super.onKeyDown(key.getKeyCode(), key);
    }
}