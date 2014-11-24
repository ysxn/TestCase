package com.android.demo.button;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class demobutton extends Activity {
    /** Called when the activity is first created. */
    TextView show;
    Button press;
	@Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //
        show = (TextView)findViewById(R.id.show_TextView);
        
        show.setText("Hi, Pre!");
        
        press = (Button)findViewById(R.id.Click_Button);
        press.setOnClickListener(callback_button);

        
    }
    private OnClickListener callback_button = new OnClickListener()
    {
	    public void onClick(View arg0)
	    {
	    	show.setText("Hi, Google Android!");
	    }
    };
}