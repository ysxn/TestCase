package com.android_04_05_receiver;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class android_04_05_receiver extends Activity {
	private static TextView tv = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.TextView01);
        
    }
    
    public static void settextbytime(String text)
    {
    	tv.setText(text);
    }
}