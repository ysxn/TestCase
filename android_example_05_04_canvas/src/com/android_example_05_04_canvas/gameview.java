package com.android_example_05_04_canvas;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.*;
import android.view.*;
import android.content.*;
import android.graphics.*;
import java.lang.Thread;

public class gameview extends View implements Runnable{
    int miCount = 0;
    int y = 0;
    private Paint mPaint = null;
    public gameview(Context context)
    {
    	super(context);
    	mPaint = new Paint();
    	new Thread(this).start();
    }
    
    public void onDraw(Canvas canvas)
    {
    	if (miCount < 100)
    	{
    		miCount++;
    	}
    	else
    	{
    		miCount = 0;
    	}
    	super.onDraw(canvas);
    	mPaint.setAntiAlias(true);
    	canvas.drawColor(Color.CYAN);
    	canvas.clipRect(100, 100, 150, 150);
    	canvas.save();
    	switch(miCount%4)
    	{
    	case 0:
    		mPaint.setColor(Color.BLUE);
    		canvas.rotate(45.0f);
    		break;
    	case 1:
    		mPaint.setColor(Color.GREEN);
    		canvas.rotate(145.0f);
    		break;
    	case 2:
    		mPaint.setColor(Color.RED);
    		canvas.rotate(245.0f);
    		break;
    	case 3:
    		mPaint.setColor(Color.YELLOW);
    		canvas.rotate(345.0f);
    		break;
    	default:
    		mPaint.setColor(Color.WHITE);
    		canvas.rotate(0.0f);
    		break;
    	}

    	
    	canvas.drawRect(new Rect(100, 100, 150, 150), mPaint);
    	canvas.restore();
    	//canvas.drawRect((320-80)/2, y, (320-80)/2+80, y+40, mPaint);
    	
    }
    
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
			postInvalidate();
		}
	}
}