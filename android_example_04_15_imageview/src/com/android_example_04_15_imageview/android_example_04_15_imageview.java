package com.android_example_04_15_imageview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.ImageView;



public class android_example_04_15_imageview extends Activity {
	ImageView imageview;
	TextView textview;
	int image_alpha = 255;
	
	Handler mHandler = new Handler();
	boolean isrung = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        isrung = true;
        
        imageview = (ImageView)findViewById(R.id.ImageView01);
        textview = (TextView)findViewById(R.id.TextView01);
        
        //imageview.setImageDrawable(getResources().getDrawable(android.R.drawable.radiobutton_off_background));
        imageview.setImageResource(R.drawable.test);
        imageview.setAlpha(image_alpha);
        
        new Thread(new Runnable(){
        	public void run()
        	{
        		while(isrung)
        		{
	        		try
	        		{
	        			Thread.sleep(200);
	        			updateAlpha();
	        		}
	        		catch (InterruptedException e)
	        		{
	        			e.printStackTrace();
	        		}
        		}
        	}


        }).start();
        
        mHandler = new Handler()
        {
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		imageview.setAlpha(image_alpha);
        		textview.setText("now alpha is : " + Integer.toString(image_alpha));
        		imageview.invalidate();
        	}
        };
    }
	public void updateAlpha() {
		// TODO Auto-generated method stub
		if (image_alpha - 7 >= 0)
		{
			image_alpha -= 7;
			
		}
		else
		{
			image_alpha = 255;
			//isrung = false;
		}
		mHandler.sendMessage(mHandler.obtainMessage());
	}
}