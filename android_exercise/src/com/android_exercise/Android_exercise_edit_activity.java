package com.android_exercise;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;


public class Android_exercise_edit_activity extends Activity {
	//
	private String name = null;
	private String job = null;
	private String company = null;
	private String myself = null;
	//
	private Button prev = null;
	private Button go_back = null;
	private Button delete = null;
	private Button next = null;
	private Button update = null;
	//
	private OnClickListener prev_listener = null;
	private OnClickListener go_back_listener = null;
	private OnClickListener delete_listener = null;
	private OnClickListener next_listener = null;
	private OnClickListener update_listener = null;
	//
	private EditText input_name = null;
	private EditText input_job = null;
	private EditText input_company = null;
	private EditText input_myself = null;
	
	private TextView contactir_id = null;
	private ContentValues values = null;
	
	private String columns[] = new String[] {
			calling_card.calling_card_contactors._ID,
			calling_card.calling_card_contactors.NAME,
			calling_card.calling_card_contactors.JOB,
			calling_card.calling_card_contactors.COMPANY,
			calling_card.calling_card_contactors.MYSELF
	};
	private Cursor c = null;
	
	private Intent intent;
	private Bundle extras;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        
        input_name = (EditText) findViewById(R.id.name);
        input_job = (EditText) findViewById(R.id.job);
        input_company = (EditText) findViewById(R.id.company);
        input_myself = (EditText) findViewById(R.id.myself);
        prev = (Button) findViewById(R.id.Button01);
        delete = (Button) findViewById(R.id.Button02);
        go_back = (Button) findViewById(R.id.Button03);
        next = (Button) findViewById(R.id.Button04);
        update = (Button) findViewById(R.id.Button05);
        
        contactir_id = (TextView) findViewById(R.id.contactor_ID);
        	
        values = new ContentValues();
        
        intent = this.getIntent();
        extras = intent.getExtras();
        
        getcursor_forall();
        
        
        prev_listener = new OnClickListener(){


			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(c.moveToPrevious())
				{
					updatedisplay();
				}
				else
				{
			        if (c.moveToFirst())
			        {
			        	updatedisplay();
			        }
				}
			}

        	
        };
        	
        delete_listener = new OnClickListener(){

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//delete the table
				//getContentResolver().delete(calling_card.calling_card_contactors.CONTENT_URI, null, null);
				//delete the contactor
				if (c.getCount() <= 0)
				{
					return;
				}
				
				Uri uri = ContentUris.withAppendedId(calling_card.calling_card_contactors.CONTENT_URI, 
						c.getLong(c.getColumnIndex(calling_card.calling_card_contactors._ID)));
				
				getContentResolver().delete(uri, null, null);
				getcursor_forall();
				/*
				values.put(calling_card.calling_card_contactors.NAME, "hello");
				values.put(calling_card.calling_card_contactors.JOB, "FAE");
				values.put(calling_card.calling_card_contactors.COMPANY, "huawei");
				values.put(calling_card.calling_card_contactors.MYSELF, "music");
				getContentResolver().insert(calling_card.calling_card_contactors.CONTENT_URI, values);
				values.clear();
				*/
			}
        	
        };
        
        update_listener = new OnClickListener(){

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (c.getCount() <= 0)
				{
					//Log.i("zhuyawen", "update_listener is_cursor_empty");
					return;
				}
				
				int edit_rowid = c.getPosition();
				Uri uri = ContentUris.withAppendedId(calling_card.calling_card_contactors.CONTENT_URI, 
						c.getLong(c.getColumnIndex(calling_card.calling_card_contactors._ID)));
				values.put(calling_card.calling_card_contactors.NAME, input_name.getText().toString());
				values.put(calling_card.calling_card_contactors.JOB, input_job.getText().toString());
				values.put(calling_card.calling_card_contactors.COMPANY, input_company.getText().toString());
				values.put(calling_card.calling_card_contactors.MYSELF, input_myself.getText().toString());
				
				getContentResolver().update(uri, values, null, null);
				values.clear();
				getcursor_forall(edit_rowid);
				
			}
        	
        };
        
        go_back_listener = new OnClickListener(){

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent();
				//i.setClass(Android_exercise_edit_activity.this, Android_exercise_main_activity.class);
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
        
        next_listener = new OnClickListener(){

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(c.moveToNext())
				{
					updatedisplay();
				}
				else
				{
			        if (c.moveToLast())
			        {
			        	updatedisplay();
			        }
				}
			}
        	
        };
        
        prev.setOnClickListener(prev_listener);
        delete.setOnClickListener(delete_listener);
        go_back.setOnClickListener(go_back_listener);
        next.setOnClickListener(next_listener);
        update.setOnClickListener(update_listener);
        
    }
    
    private void updatedisplay()
    {
		name = c.getString(c.getColumnIndex(calling_card.calling_card_contactors.NAME));
    	job = c.getString(c.getColumnIndex(calling_card.calling_card_contactors.JOB));;
    	company = c.getString(c.getColumnIndex(calling_card.calling_card_contactors.COMPANY));;
    	myself = c.getString(c.getColumnIndex(calling_card.calling_card_contactors.MYSELF));;
    	//int id = c.getInt(c.getColumnIndex(calling_card.calling_card_contactors._ID));
    	//int count = c.getInt(c.getColumnIndex(calling_card.calling_card_contactors._COUNT));
    	
    	input_name.setText(name);
    	input_job.setText(job);
    	input_company.setText(company);
    	input_myself.setText(myself);
    	//contactir_id.setText(Integer.toString(id) + "/" + Integer.toString(c.getCount()));
    	//contactir_id.setText(c.getString(c.getColumnIndex(calling_card.calling_card_contactors._ID)) + "/" + Integer.toString(c.getCount()));
    	contactir_id.setText(Integer.toString(c.getPosition() + 1) + "/" + Integer.toString(c.getCount()));
    }
    
    private void getcursor_forall()
    {
    	c = managedQuery(calling_card.calling_card_contactors.CONTENT_URI, 
        		columns, 
        		null, 
        		null, 
        		calling_card.calling_card_contactors._ID + " ASC");
        if (c.moveToFirst())
        {
        	updatedisplay();
        	
        }
        else
        {
        	
        	contactir_id.setText("0/0");
        	input_name.setText(extras.getString("name"));
        	input_job.setText(extras.getString("job"));
        	input_company.setText(extras.getString("company"));
        	input_myself.setText(extras.getString("myself"));
        }
    }
    
    private void getcursor_forall(int position)
    {
    	c = managedQuery(calling_card.calling_card_contactors.CONTENT_URI, 
        		columns, 
        		null, 
        		null, 
        		calling_card.calling_card_contactors._ID + " ASC");
        if (c.moveToFirst())
        {
        	c.moveToPosition(position);
        	//Log.i("zhuyawen ", "position = " + Integer.toString(position));
        	updatedisplay();
        	
        }
        else
        {
        	
        	contactir_id.setText("0/0");
        	input_name.setText(extras.getString("name"));
        	input_job.setText(extras.getString("job"));
        	input_company.setText(extras.getString("company"));
        	input_myself.setText(extras.getString("myself"));
        }
    }
}