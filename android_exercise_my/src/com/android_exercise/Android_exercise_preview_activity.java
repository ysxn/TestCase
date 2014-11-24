package com.android_exercise;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.*;
import android.view.View.OnClickListener;


public class Android_exercise_preview_activity extends Activity {
	//
	private String name = null;
	private String job = null;
	private String company = null;
	private String myself = null;
	//
	//private Button prev = null;
	private ImageButton go_back = null;
	//private Button delete = null;
	//private Button next = null;
	//
	//private OnClickListener prev_listener = null;
	private OnClickListener go_back_listener = null;
	//private OnClickListener delete_listener = null;
	//private OnClickListener next_listener = null;
	//
	private EditText input_name = null;
	private EditText input_job = null;
	private EditText input_company = null;
	private EditText input_myself = null;
	

	
	private Intent intent;
	private Bundle extras;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        
        input_name = (EditText) findViewById(R.id.name);
        input_job = (EditText) findViewById(R.id.job);
        input_company = (EditText) findViewById(R.id.company);
        input_myself = (EditText) findViewById(R.id.myself);
        
        go_back = (ImageButton) findViewById(R.id.ImageButton05);
        
        intent = this.getIntent();
        extras = intent.getExtras();
        

    	input_name.setText(extras.getString("name"));
    	input_job.setText(extras.getString("job"));
    	input_company.setText(extras.getString("company"));
    	input_myself.setText(extras.getString("myself"));

        
        
        
        go_back_listener = new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Intent i = new Intent();
				//i.setClass(Android_exercise_preview_activity.this, Android_exercise_main_activity.class);
				//startActivity(i);
				/*
				extras.putString("name", input_name.getText().toString());
				extras.putString("job", input_job.getText().toString());
				extras.putString("company", input_company.getText().toString());
				extras.putString("myself", input_myself.getText().toString());
				intent.putExtras(extras);
				*/
				setResult(RESULT_OK, intent);
				
				finish();
			}
        	
        };
        
        go_back.setOnClickListener(go_back_listener);
      
        
    }
    
    
}