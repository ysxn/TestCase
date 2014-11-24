package com.android_example_04_11_datetime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Button;
import java.util.Calendar;
import android.view.View;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

public class android_example_04_11_datetime extends Activity {
	TextView m_TextView;
	DatePicker m_DatePicker;
	TimePicker m_TimePicker;
	Button m_dpButton;
	Button m_tpButton;
	Calendar	c;
    private int mYear; 
    private int mMonth; 
    private int mDay; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        c = Calendar.getInstance();
        m_TextView = (TextView) findViewById(R.id.TextView01);
        m_dpButton = (Button) findViewById(R.id.Button1);
        m_tpButton = (Button) findViewById(R.id.Button2);
        
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        m_DatePicker = (DatePicker) findViewById(R.id.DatePicker01);
        m_DatePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 
        		c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
        	public void onDateChanged(DatePicker view, int year, int monthOfYear, 
        			int dayOfMonth)
        	{
        		c.set(year, monthOfYear, dayOfMonth, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
				//c.set(year, monthOfYear, dayOfMonth);
        	}
        });
        
        m_TimePicker = (TimePicker) findViewById(R.id.TimePicker01);
        m_TimePicker.setIs24HourView(true);
        m_TimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
        	public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
        	{
        		//c.set(hourOfDay, minute);
        		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 
		        		c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
        	}
        });
        
        m_dpButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		new DatePickerDialog(android_example_04_11_datetime.this, 
        				new DatePickerDialog.OnDateSetListener ()
        		{
        			public void onDateSet(DatePicker view, int year, int monthOfYear, 
                			int dayOfMonth)
        			{
        				c.set(year, monthOfYear, dayOfMonth, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        				mYear = year;
        				mMonth = monthOfYear;
        				mDay = dayOfMonth;
        				//c.set(year, monthOfYear, dayOfMonth);
        			}
        		}, mYear, mMonth, 
        		mDay).show();
        		//c.get(Calendar.YEAR), c.get(Calendar.MONTH), 
        		//c.get(Calendar.DAY_OF_MONTH)).show();
        	}
        });
        
        m_tpButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		new TimePickerDialog(android_example_04_11_datetime.this, 
        				new TimePickerDialog.OnTimeSetListener ()
        		{
        			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        			{
        				c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 
        		        		c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
        			}
        		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), 
        		true).show();
        	}
        }); 
    }
}