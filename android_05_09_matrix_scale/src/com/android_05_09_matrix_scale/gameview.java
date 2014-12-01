package com.android_05_09_matrix_scale;

import android.app.Activity;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;

import java.lang.Thread;

public class gameview extends View implements Runnable{

    private Paint mPaint = null;
    Bitmap mBitQQ = null;
    Bitmap mBitDeatTop = null;
    int miDTX = 0;
    float degrees = 1.0f;
    Matrix mMatrix = new Matrix();
    
    int mBitQQwidth = 0;
    int mBitQQheight = 0;
    
    public gameview(Context context)
    {
    	super(context);
    	mPaint = new Paint();
    	miDTX = 0;
    	mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.p1)).getBitmap();
    	mBitDeatTop = ((BitmapDrawable) getResources().getDrawable(R.drawable.p2)).getBitmap();
    	mBitQQwidth = mBitQQ.getWidth();
    	mBitQQheight = mBitQQ.getHeight();
    	
    	new Thread(this).start();
    }
    
    public void onDraw(Canvas canvas)
    {
		canvas.drawColor(Color.BLACK);
		mMatrix.reset();
		mMatrix.postScale(degrees, degrees);
		
		Bitmap mBitQQ2 = Bitmap.createBitmap(mBitQQ, 0, 0, mBitQQwidth, mBitQQheight, mMatrix, true);
		gameview.drawImage(canvas, mBitQQ2, 0, 0);
		mBitQQ2 = null;
		/*
		gameview.drawImage(canvas, mBitQQ, 0, 0);
		gameview.drawImage(canvas, mBitDeatTop, miDTX, mBitQQ.getHeight(),
				mBitDeatTop.getWidth(), mBitDeatTop.getHeight()/2, 0, 0);
    	*/
    }
    
	private static void drawImage(Canvas canvas, Bitmap bitmap, int x,
			int y, int w, int h, int j, int k) {
		// TODO Auto-generated method stub
		Rect src = new Rect();
		Rect dst = new Rect();
		src.left = j;
		src.top = k;
		src.right = j + w;
		src.bottom = k + h;
		
		dst.left = x;
		dst.top = y;
		dst.right = x + w;
		dst.bottom = y + h;
		canvas.drawBitmap(bitmap, src, dst, null);
		
		src = null;
		dst = null;
		
	}

	private static void drawImage(Canvas canvas, Bitmap bitmap, int left, int top) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bitmap, left, top, null);
	}

    public boolean onTouchEvent(MotionEvent event)
    {
    	
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		degrees = 1.0f;
    		break;
    	}
    	
		return true;
    	
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