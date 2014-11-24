package com.android.demo.menu_02;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class menu02 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button ok = (Button)findViewById(R.id.ok);
        Button exit = (Button)findViewById(R.id.exit);
        ok.setOnClickListener(callback_button);
        exit.setOnClickListener(callback_button);
        
    }
    
    private OnClickListener callback_button = new OnClickListener()
    {
	    public void onClick(View arg0)
	    {
	    	switch(arg0.getId())
	    	{
	    	case R.id.ok:
	    		setTitle("this is OK button");
	    		break;
	    	case R.id.exit:
	    		finish();
	    		break;
	    	default:
	    		break;
	    	
	    	}
	    }
    };
    
    

    
}