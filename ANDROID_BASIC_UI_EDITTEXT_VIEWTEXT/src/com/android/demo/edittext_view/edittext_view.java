package com.android.demo.edittext_view;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class edittext_view extends Activity {
    /** Called when the activity is first created. */
	private TextView mTextView01;
	private EditText mEditText01;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mTextView01 = (TextView)findViewById(R.id.myTextView);
        
        mEditText01 = (EditText)findViewById(R.id.myEditText);
        
        mEditText01.setOnKeyListener(callback_edittext);
    }
    
    //
    private OnKeyListener callback_edittext = new OnKeyListener(){

		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			mTextView01.setText(mEditText01.getText().toString());
			return false;
		}
    };
}