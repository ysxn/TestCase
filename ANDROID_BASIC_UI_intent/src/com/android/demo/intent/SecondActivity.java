package com.android.demo.intent;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);
        
        Button b2 = (Button)findViewById(R.id.button2);
        
        b2.setOnClickListener(callback_button2);
    }
    //
    private OnClickListener callback_button2 = new OnClickListener(){
    	public void onClick(View v)
    	{
    		//new 一个intent对象，并指定要启动的class
    		Intent intent = new Intent();
    		intent.setClass(SecondActivity.this, demointent.class);
    		//调用一个新的Activity
    		startActivity(intent);
    		//关闭原来的Activity
    		SecondActivity.this.finish();
    	}
    	   	
    };
}