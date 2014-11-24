package com.android_example_05_01_gameviews;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.*;
import android.view.*;
import android.content.*;
import android.graphics.*;


public class gameview extends View {
    int miCount = 0;
    int y = 0;
    
    public gameview(Context context)
    {
    	super(context);
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
    	
    	Paint mPaint = new Paint();
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
    	
    	canvas.drawRect((320-80)/2, y, (320-80)/2+80, y+40, mPaint);
    	
    }
}