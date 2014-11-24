package com.android_example_05_03_paintcolor;

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
    	mPaint.setAntiAlias(true);
    	
    	canvas.drawRect((320-80)/2, y, (320-80)/2+80, y+40, mPaint);
    	
    }
    
	public void run() {
		// TODO Auto-generated method stub
		while(!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(100);
				
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			postInvalidate();
		}
	}
}