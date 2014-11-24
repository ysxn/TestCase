package com.android_05_10_pixel;

import android.app.Activity;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;

import java.lang.Thread;
import android.util.Log;
public class gameview extends View implements Runnable{

	int BACKWIDTH;
	int BACKHEIGHT;
	short[] buf1;
	short[] buf2;
	
	int[] Bitmap1;
	int[] Bitmap2;
	
	int setx = 0;
	int sety = 0;
	
    public gameview(Context context)
    {
    	super(context);

    	Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.p1);
    	BACKWIDTH = image.getWidth();
    	BACKHEIGHT = image.getHeight();
    	
    	buf1 = new short[BACKWIDTH*BACKHEIGHT];
    	buf2 = new short[BACKWIDTH*BACKHEIGHT];
    	Bitmap1 = new int[BACKWIDTH*BACKHEIGHT];
    	Bitmap2 = new int[BACKWIDTH*BACKHEIGHT];
    	
    	image.getPixels(Bitmap1, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT);
    	
    	new Thread(this).start();
    }
    
    void DropStone(int x, int y, int stonesize, int stoneweight)
    {
    	if (x - stonesize < 0 || y - stonesize < 0 || x - stonesize > BACKWIDTH || y - stonesize > BACKHEIGHT)
    	{
    		//Log.i("zhuyawen", "x = " + Integer.toString(x) +" y = " +Integer.toString(y) + " stonesize = " + Integer.toString(stonesize) + 
    		//		" BACKWIDTH = " + Integer.toString(BACKWIDTH) +" BACKHEIGHT = " +Integer.toString(BACKHEIGHT));
    		return;
    	}
    	for (int posx = x - stonesize;posx < x + stonesize; posx++)
    	{
    		for(int posy = x - stonesize;posy < x + stonesize; posy++)
    		{
    			if ((posx - x)*(posx - x) + (posy - y)*(posy - y) < stonesize*stonesize)
    			{
    				buf1[BACKWIDTH*posy + posx] = (short) -stoneweight;
    				//Log.i("buf1[BACKWIDTH*posy + posx] = ", Short.toString(buf1[BACKWIDTH*posy + posx]) + Integer.toString(posx) + Integer.toString(posy));
    			}
    		}
    	}
    	//Log.i("zhuyawen after", "x = " + Integer.toString(x) +" y = " +Integer.toString(y) + " stonesize = " + Integer.toString(stonesize) + 
		//		" BACKWIDTH = " + Integer.toString(BACKWIDTH) +" BACKHEIGHT = " +Integer.toString(BACKHEIGHT));
		
    }
    
    void RippleSpread()
    {
    	for(int i = BACKWIDTH; i < BACKWIDTH*BACKHEIGHT - BACKWIDTH; i++)
    	{
    		buf2[i] = (short) (((buf1[i-1] + buf1[i+1] +buf1[i-BACKWIDTH] +buf1[i+BACKWIDTH]) >>1) - buf2[i]);
    		buf2[i] -=buf2[i] >> 5;
    	}
    	short[] ptemp = buf1;
    	buf1 = buf2;
    	buf2 = ptemp;
    }
    
    void render()
    {
    	int xoff, yoff;
    	int k = BACKWIDTH;
    	for(int i = 1; i< BACKHEIGHT -1; i++)
    	{
    		for(int j = 0;j < BACKWIDTH; j++)
    		{
    			xoff = buf1[k - 1] - buf1[k + 1];
    			yoff = buf1[k - BACKWIDTH] - buf1[k + BACKWIDTH];
    			if ((i + yoff) < 0)
    			{
    				k++;
    				continue;
    			}
    			if ((i + yoff) > BACKHEIGHT)
    			{
    				k++;
    				continue;
    			}
    			if ((j + xoff) < 0)
    			{
    				k++;
    				continue;
    			}
    			if ((j + xoff) > BACKWIDTH)
    			{
    				k++;
    				continue;
    			}
    			
    			int pos1, pos2;
    			pos1 = BACKWIDTH * (i+yoff) + (j+xoff);
    			pos2 = BACKWIDTH*i +j;
    			Bitmap2[pos2++] = Bitmap1[pos1++];
    			k++;
    		}
    	}
    }
    public void onDraw(Canvas canvas)
    {
    	super.onDraw(canvas);
    	//drawBitmap(int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, Paint paint) 
    	//Legacy version of drawBitmap(int[] colors, ...) that took ints for x,y 
    	canvas.drawBitmap(Bitmap2, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT, false, null);

    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
    	
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		//DropStone(35, 110, 10, 5);
    		//DropStone(setx, sety, 30, 5);
    		DropStone((int)event.getX(), (int)event.getY(), 30, 10);
    		//Log.i("zhuyawen", "event.getX() = " + Float.toString(event.getX()) +" event.getY() = " +Float.toString(event.getY())
    		//		+ " event.getXPrecision() = " + Float.toString(event.getXPrecision()) + " event.getYPrecision() = " + Float.toString(event.getYPrecision()) 
    		//		+" event.getRawX() = " +Float.toString(event.getRawX()) + " event.getRawY() = " +Float.toString(event.getRawY()));
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
			RippleSpread();
			render();
			//Log.i("zhuyawen", "run");
			postInvalidate();
		}
	}

}