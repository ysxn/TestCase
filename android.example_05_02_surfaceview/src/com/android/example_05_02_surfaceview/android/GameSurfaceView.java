package com.android.example_05_02_surfaceview.android;

import com.android.example_05_02_surfaceview.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.*;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

	boolean mbloop = false;
	SurfaceHolder mSurfaceHolder = null;
	int miCount = 0;
	int y = 0;
	
	public GameSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);
		mbloop = true;
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}


	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		new Thread(this).start();
	}


	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mbloop = false;
	}


	public void run() {
		// TODO Auto-generated method stub
		while(mbloop)
		{
			try
			{
				Thread.sleep(200);
				
			}
			catch (Exception e)
			{
				
			}
			synchronized(mSurfaceHolder)
			{
				Draw();
			}
		}
	}

	private void Draw() {
		// TODO Auto-generated method stub
		Canvas canvas = mSurfaceHolder.lockCanvas();
		if (mSurfaceHolder == null || canvas == null)
		{
			
			return;
		}
		
    	if (miCount < 100)
    	{
    		miCount++;
    	}
    	else
    	{
    		miCount = 0;
    	}
    	
    	Paint mPaint = new Paint();
    	
    	mPaint.setAntiAlias(true);
    	mPaint.setColor(Color.BLACK);
    	Rect r = new Rect(0, 0, 320, 480);
    	canvas.drawRect(r, mPaint);
    	switch(miCount%4)
    	{
    	case 0:
    		mPaint.setColor(Color.BLUE);
    		break;
    	case 1:
    		mPaint.setColor(Color.GREEN);
    		break;
    	case 2:
    		mPaint.setColor(Color.RED);
    		break;
    	case 3:
    		mPaint.setColor(Color.YELLOW);
    		break;
    	default:
    		mPaint.setColor(Color.WHITE);
    		break;
    	}
    	
    	canvas.drawCircle((320-25)/2, y, 50, mPaint);
    	
    	mSurfaceHolder.unlockCanvasAndPost(canvas);
    	
    	
	}

	
    
}

