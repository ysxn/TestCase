package com.android_example_05_01_gameviews;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.lang.Thread;



public class android_example_05_01_gameviews extends Activity {

	private static final int REFRESH = 0x000001;
	private gameview mGameview = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGameview = new gameview(this);
        
        setContentView(mGameview);
        
        new Thread(new GameThread()).start();
    }
    
    Handler myHandler = new Handler(){
    	public void handleMessage(Message msg)
    	{
    		switch (msg.what)
    		{
    		case android_example_05_01_gameviews.REFRESH:
    			mGameview.invalidate();
    			break;
    		}
    		super.handleMessage(msg);
    	}
    };
    
    class GameThread implements Runnable
    {

		
		public void run() {
			// TODO Auto-generated method stub
			while(!Thread.currentThread().isInterrupted())
			{
				Message msg = new Message();
				msg.what = android_example_05_01_gameviews.REFRESH;
				android_example_05_01_gameviews.this.myHandler.sendMessage(msg);
				try
				{
					Thread.sleep(100);
					
				}
				catch(InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}
		}

    }
    /*
    class GameThread implements Runnable
    {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!Thread.currentThread().isInterrupted())
			{

				try
				{
					Thread.sleep(1000);
					
				}
				catch(InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
				mGameview.postInvalidate();
			}
		}

    }*/
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
    		mGameview.y -=3;
    		break;
    	case KeyEvent.KEYCODE_DPAD_DOWN:
    		mGameview.y +=3;
    		break;
    	}
    	
    	return false;
    }
    
    public boolean onKeyMultiple(int KeyCode, KeyEvent event)
    {
    	return true;
    }
}