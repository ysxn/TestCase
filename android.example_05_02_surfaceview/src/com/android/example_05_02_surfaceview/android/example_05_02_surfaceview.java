package com.android.example_05_02_surfaceview.android;

import com.android.example_05_02_surfaceview.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;

public class example_05_02_surfaceview extends Activity {

	private static final int REFRESH = 0x000001;
	private GameSurfaceView mGameview = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGameview = new GameSurfaceView(this);
        
        setContentView(mGameview);
        
        //new Thread(new GameThread()).start();
    }

    public boolean onTouchEvent(MotionEvent event)
    {
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		mGameview.y +=90;
    		break;
    	}
		return false;
    	
    }
    
    public boolean onKeyDown(int KeyCode, KeyEvent event)
    {
    	return true;
    }
    
    public boolean onKeyUp(int KeyCode, KeyEvent event)
    {
    	switch(KeyCode)
    	{
    	case KeyEvent.KEYCODE_DPAD_UP:
    		mGameview.y -=30;
    		break;
    	case KeyEvent.KEYCODE_DPAD_DOWN:
    		mGameview.y +=30;
    		break;
    	}
    	
    	return false;
    }
    
    public boolean onKeyMultiple(int KeyCode, KeyEvent event)
    {
    	return true;
    }
}

