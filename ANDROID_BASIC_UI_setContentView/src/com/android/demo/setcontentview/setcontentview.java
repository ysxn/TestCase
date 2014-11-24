package com.android.demo.setcontentview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class setcontentview extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动默认布局
        setContentView(R.layout.main);
        //
        Button b1 = (Button)findViewById(R.id.button1);
        //        
		b1.setOnClickListener(callback_button1);
    }
    
    //
    private OnClickListener callback_button1 = new OnClickListener(){
    	public void onClick(View v)
    	{
    		jumpToLayout2();
    	}
    	   	
    };
    
    //public
    public void jumpToLayout2()
    {
        //启动自定义布局
        setContentView(R.layout.mylayout);
        //
        Button b2 = (Button)findViewById(R.id.button2);
        //        
		b2.setOnClickListener(callback_button2);
    	
    }
    //
    private OnClickListener callback_button2 = new OnClickListener(){
    	public void onClick(View v)
    	{
    		jumpToLayout1();
    	}
    	   	
    };
    
    //public
    public void jumpToLayout1()
    {
        //启动自定义布局
        setContentView(R.layout.main);
        //
        Button b1 = (Button)findViewById(R.id.button1);
        //        
		b1.setOnClickListener(callback_button1);
    	
    }
}