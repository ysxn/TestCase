package com.android.demo.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class demointent extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button b1 = (Button)findViewById(R.id.button1);
        
        b1.setOnClickListener(callback_button1);
    }
    //
    private OnClickListener callback_button1 = new OnClickListener(){
    	public void onClick(View v)
    	{
    		//new һ��intent���󣬲�ָ��Ҫ������class
    		Intent intent = new Intent();
    		intent.setClass(demointent.this, SecondActivity.class);
    		//����һ���µ�Activity
    		startActivity(intent);
    		//�ر�ԭ����Activity
    		demointent.this.finish();
    	}
    	   	
    };

    
}