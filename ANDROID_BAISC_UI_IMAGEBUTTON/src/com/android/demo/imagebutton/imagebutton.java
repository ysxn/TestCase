package com.android.demo.imagebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class imagebutton extends Activity {
    /** Called when the activity is first created. */
	//声明三个对象变量（图片按钮，普通按钮，TextView）
	private ImageButton mImageButton;
	private Button mbutton;
	private TextView mTextView1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //
        mImageButton = (ImageButton)findViewById(R.id.image_Button);
    	mbutton = (Button)findViewById(R.id.normal_Button);
    	mTextView1 = (TextView)findViewById(R.id.show_TextView);
    	
    	//设置图片按钮的onFocus事件
    	mImageButton.setOnFocusChangeListener(callback_imagebutton_onfocus);
    	//设置图片按钮的loseFocus事件
    	//mImageButton.setLostFocusChangeListener(callback_imagebutton_losefocus);
    	//设置图片按钮的click事件
    	mImageButton.setOnClickListener(callback_imagebutton_onclick);
    	//设置normal按钮的click事件
    	mbutton.setOnClickListener(callback_normalbutton_onclick);
    	//
    	
    	
    }
    //设置图片按钮的onFocus事件
    private OnFocusChangeListener callback_imagebutton_onfocus = new OnFocusChangeListener(){

		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (hasFocus == true){
				mTextView1.setText(R.string.onfocus);
				mImageButton.setImageResource(R.drawable.onfocusimage);
			}else{
				mTextView1.setText(R.string.lostfocus);
				mImageButton.setImageResource(R.drawable.losefocusimage);
			}
		}
    	
    };
    //设置图片按钮的click事件
    private OnClickListener callback_imagebutton_onclick = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			mTextView1.setText(R.string.onclick);
			mImageButton.setImageResource(R.drawable.clickimage);
		}
    	
    };
    //设置normal按钮的click事件
    private OnClickListener callback_normalbutton_onclick = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			mTextView1.setText(R.string.lostfocus);
			mImageButton.setImageResource(R.drawable.losefocusimage);
		}
    	
    };
    
    
    //
    
    
}