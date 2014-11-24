package com.android_example_04_09;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;

public class android_example_04_09 extends Activity {
	private static final String[] m_Countries = {"O style", 
		"A style", "B style", "AB style", "other style"}; 
	private TextView	m_TextView;
	private Spinner		m_Spinner;
	private ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        m_TextView = (TextView) findViewById(R.id.TextView1);
    	m_Spinner = (Spinner) findViewById(R.id.Spinner1);
    	
    	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
    			m_Countries);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	m_Spinner.setAdapter(adapter);
    	
    	m_Spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
    		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    		{
    			m_TextView.setText("your blood is :" + m_Countries[arg2]);
    			arg0.setVisibility(View.VISIBLE);
    		}
    		
    		public void onNothingSelected(AdapterView<?> arg0)
    		{
    			
    		}
    	});
    }
}