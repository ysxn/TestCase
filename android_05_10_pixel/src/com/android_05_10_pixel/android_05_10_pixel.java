package com.android_05_10_pixel;



import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class android_05_10_pixel extends Activity {
	private static final int REFRESH = 0x000001;
	private gameview mGameview = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameview = new gameview(this);
        setContentView(mGameview);
    }
    

    
    public boolean onKeyDown(int KeyCode, KeyEvent event)
    {
    	return true;
    }
    
    public boolean onKeyUp(int KeyCode, KeyEvent event)
    {
    	switch(KeyCode)
    	{
    	case KeyEvent.KEYCODE_DPAD_LEFT:
    		mGameview.setx = 35;
    		mGameview.sety = 110;
    		break;
    	case KeyEvent.KEYCODE_DPAD_RIGHT:
    		//mGameview.setx += 10;
    		//mGameview.sety += 10;
    		break;
    	}
    	
    	return true;
    }
    
    public boolean onKeyMultiple(int KeyCode, KeyEvent event)
    {
    	return true;
    }
}