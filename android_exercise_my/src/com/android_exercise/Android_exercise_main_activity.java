package com.android_exercise;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;


public class Android_exercise_main_activity extends Activity {
	//
	private String name = null;
	private String job = null;
	private String company = null;
	private String myself = null;
	//
	private ImageButton save = null;
	private ImageButton cancel = null;
	private ImageButton preview = null;
	private ImageButton search = null;
	private ImageButton compose = null;
	//
	private OnClickListener save_listener = null;
	private OnClickListener cancel_listener = null;
	private OnClickListener preview_listener = null;
	private OnClickListener Search_listener = null;
	private OnClickListener compose_listener = null;
	//
	private EditText input_name = null;
	private EditText input_job = null;
	private EditText input_company = null;
	private EditText input_myself = null;
	
	private ContentValues values = null;
	
	static final int requestCode_preview = 0;
	static final int requestCode_edit = 1;
	static final int requestCode_search = 2;
	private DisplayMetrics dm = null;
	private int screenheight = 0;
	private int screenwidth = 0;
	
	private Intent intent = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater factory = LayoutInflater.from(this);
        setContentView(factory.inflate(R.layout.main, null));
        //setContentView(R.layout.main);
        
        input_name = (EditText) findViewById(R.id.EditText01);
        input_job = (EditText) findViewById(R.id.EditText02);
        input_company = (EditText) findViewById(R.id.EditText03);
        input_myself = (EditText) findViewById(R.id.EditText04);
        save = (ImageButton) findViewById(R.id.ImageButtonSave);
        cancel = (ImageButton) findViewById(R.id.ImageButtonCancel);
        preview = (ImageButton) findViewById(R.id.ImageButtonPreview);
        search = (ImageButton) findViewById(R.id.ImageButtonSearch);
        compose = (ImageButton) findViewById(R.id.ImageButtonCompose);
        values = new ContentValues();
        
        dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenheight = dm.heightPixels;
        screenwidth = dm.widthPixels;
        
        intent = this.getIntent();
        if (intent.getData() == null) {
            intent.setData(calling_card.calling_card_contactors.CONTENT_URI);

        }
        else
        {

        }

        save_listener = new OnClickListener(){

	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save_cantactor();
				showtips("save complete!");
			}
        	
        };
        	
        cancel_listener = new OnClickListener(){


			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
        	
        };
        
        preview_listener = new OnClickListener(){


			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Intent.ACTION_VIEW, intent.getData());
				//Intent i = new Intent();
				//i.setClass(Android_exercise_main_activity.this, Android_exercise_preview_activity.class);
				//startActivity(i);
				
				Bundle extras = new Bundle();
				extras.putString("name", input_name.getText().toString());
				extras.putString("job", input_job.getText().toString());
				extras.putString("company", input_company.getText().toString());
				extras.putString("myself", input_myself.getText().toString());
				i.putExtras(extras);
				startActivityForResult(i, requestCode_preview);
				
				
				//finish();
			}
        	
        };
        
        Search_listener = new OnClickListener(){


			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Intent.ACTION_PICK, intent.getData());

				//i.setClass(Android_exercise_main_activity.this, List_view.class);
				
				Bundle extras = new Bundle();
				extras.putString("name", input_name.getText().toString());
				extras.putString("job", input_job.getText().toString());
				extras.putString("company", input_company.getText().toString());
				extras.putString("myself", input_myself.getText().toString());
				i.putExtras(extras);
				startActivityForResult(i, requestCode_search);
				
				//startActivity(i);
				//finish();
			}
        	
        };
        
        compose_listener = new OnClickListener(){


			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Intent.ACTION_EDIT, intent.getData());
				
				//i.setClass(Android_exercise_main_activity.this, Android_exercise_edit_activity.class);
				
				Bundle extras = new Bundle();
				extras.putString("name", input_name.getText().toString());
				extras.putString("job", input_job.getText().toString());
				extras.putString("company", input_company.getText().toString());
				extras.putString("myself", input_myself.getText().toString());
				i.putExtras(extras);
				startActivityForResult(i, requestCode_edit);
				
				//startActivity(i);
				//finish();
			}
        	
        };
        
        save.setOnClickListener(save_listener);
        cancel.setOnClickListener(cancel_listener);
        preview.setOnClickListener(preview_listener);
        search.setOnClickListener(Search_listener);
        compose.setOnClickListener(compose_listener);
        
    }
    
    private void showtips(String info)
    {
    	Toast toast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	toast.show();
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {

        if (requestCode == requestCode_preview) 
        {
        	Log.i("zhuawen :Android_exercise_main_activity :onActivityResult","requestCode = requestCode_preview; " + "resultCode" + Integer.toString(resultCode));
            switch(resultCode)
            {
            case RESULT_OK:
            	Bundle extras = data.getExtras();
            	input_name.setText(extras.getString("name"));
				input_job.setText(extras.getString("job"));
				input_company.setText(extras.getString("company"));
				input_myself.setText(extras.getString("myself"));
            	break;
            	
            default:
            	break;
            }
        }
        else if (requestCode == requestCode_edit)
        {
        	Log.i("zhuawen :Android_exercise_main_activity :onActivityResult","requestCode = requestCode_edit; " + "resultCode" + Integer.toString(resultCode));
        	switch(resultCode)
            {
            case RESULT_OK:
            	Bundle extras = data.getExtras();
            	input_name.setText(extras.getString("name"));
				input_job.setText(extras.getString("job"));
				input_company.setText(extras.getString("company"));
				input_myself.setText(extras.getString("myself"));
            	break;
            	
            default:
            	break;
            }
        }
        else if (requestCode == requestCode_search)
        {
        	Log.i("zhuawen :Android_exercise_main_activity :onActivityResult","requestCode = requestCode_search; " + "resultCode" + Integer.toString(resultCode));
        	switch(resultCode)
            {
            case RESULT_OK:
            	Bundle extras = data.getExtras();
            	input_name.setText(extras.getString("name"));
				input_job.setText(extras.getString("job"));
				input_company.setText(extras.getString("company"));
				input_myself.setText(extras.getString("myself"));
            	break;
            	
            default:
            	break;
            }
        }
        else
        {
        	Log.i("zhuawen :Android_exercise_main_activity :onActivityResult","requestCode = none; " + "resultCode" + Integer.toString(resultCode));
        }

    }
    private void save_cantactor()
    {
		name = input_name.getText().toString();
		job = input_job.getText().toString();
		company = input_company.getText().toString();
		myself = input_myself.getText().toString();
		
		//
		values.put(calling_card.calling_card_contactors.NAME, name);
		values.put(calling_card.calling_card_contactors.JOB, job);
		values.put(calling_card.calling_card_contactors.COMPANY, company);
		values.put(calling_card.calling_card_contactors.MYSELF, myself);
		getContentResolver().insert(calling_card.calling_card_contactors.CONTENT_URI, values);
		values.clear();
		return;
    }
}