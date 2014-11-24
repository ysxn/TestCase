package com.android_example_05_04_canvas;



import android.app.Activity;
import android.os.Bundle;

public class android_example_05_04_canvas extends Activity {
	private static final int REFRESH = 0x000001;
	private gameview mGameview = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameview = new gameview(this);
        setContentView(mGameview);
    }
}