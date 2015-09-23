package com.android_05_07_example_bitmap;



import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class android_05_07_example_bitmap extends Activity {
	private static final int REFRESH = 0x000001;
	private GameView mGameview = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameview = new GameView(this);
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
    		if (mGameview.miDTX - 30 > 0)
    		{
    			mGameview.miDTX -=30;
    		}
    		break;
    	case KeyEvent.KEYCODE_DPAD_RIGHT:
    		if (mGameview.miDTX + mGameview.mBitDeatTop.getWidth() < 320 - 30)
    		{
    			mGameview.miDTX +=30;
    		}
    		break;
    	}
    	
    	return true;
    }
    
    public boolean onKeyMultiple(int KeyCode, KeyEvent event)
    {
    	return true;
    }
}