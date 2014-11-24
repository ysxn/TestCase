package com.android_exercise;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Toast;

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
	//private Button edit = null;
	//
	private OnClickListener save_listener = null;
	private OnClickListener cancel_listener = null;
	private OnClickListener preview_listener = null;
	//private OnClickListener edit_listener = null;
	//
	private EditText input_name = null;
	private EditText input_job = null;
	private EditText input_company = null;
	private EditText input_myself = null;
	
	private ContentValues values = null;
	
	private static final int requestCode_preview = 0;
	private static final int requestCode_edit = 1;
	
	private DisplayMetrics dm = null;
	private int screenheight = 0;
	private int screenwidth = 0;
    //private RelativeLayout mlayout1 = null;
    //private ImageView imageview1 = null;
    //private EditText edittext1 = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*
        mlayout1 = new RelativeLayout(this);//
        //mlayout1 = (LinearLayout) findViewById(R.id.LinearLayout01);
        //mlayout1.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.cardsetbg));
        mlayout1.setBackgroundResource(R.drawable.cardsetbg);
        imageview1 = new ImageView(this);
        imageview1.setImageResource(R.drawable.face01);
        edittext1 = new EditText(this);
        edittext1.
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT
        		,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mlayout1.addView(imageview1, params);
        mlayout1.addView(edittext1, params);
        */
        //setContentView(mlayout1);
        setContentView(R.layout.main);
        
        
        input_name = (EditText) findViewById(R.id.EditText01);
        input_job = (EditText) findViewById(R.id.EditText02);
        input_company = (EditText) findViewById(R.id.EditText03);
        input_myself = (EditText) findViewById(R.id.EditText04);
        save = (ImageButton) findViewById(R.id.ImageButton03);
        cancel = (ImageButton) findViewById(R.id.ImageButton04);
        preview = (ImageButton) findViewById(R.id.ImageButton05);
        //edit = (Button) findViewById(R.id.Button04);
        
        
        
        values = new ContentValues();
        dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenheight = dm.heightPixels;
        screenwidth = dm.widthPixels;
        
        
        
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
				
				Intent i = new Intent();
				i.setClass(Android_exercise_main_activity.this, Android_exercise_preview_activity.class);
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
        
        /*
        edit_listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent();
				i.setClass(Android_exercise_main_activity.this, Android_exercise_edit_activity.class);
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
        */
        
        save.setOnClickListener(save_listener);
        cancel.setOnClickListener(cancel_listener);
        preview.setOnClickListener(preview_listener);
        //edit.setOnClickListener(edit_listener);
        
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        if (requestCode == requestCode_preview) 
        {
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
    
    private void showtips(String info)
    {
    	Toast toast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	toast.show();
    }
}