package com.android.demo.toast_christmas;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class toast_christmas extends Activity {
	/** Called when the activity is first created. */
	/*声明两个对象变量(按钮与编辑文字)*/
	private Button mButton;
	private EditText mEditText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/*通过findViewById()取得对象*/
		mButton=(Button)findViewById(R.id.Button_Send);
		mEditText=(EditText)findViewById(R.id.EditText_Wish);
		/*设置onClickListener 给Button 对象聆听onClick 事件*/
		mButton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*声明字符串变量并取得用户输入的EditText 字符串*/
				Editable Str;
				Str=mEditText.getText();
				/*使用CharSequence类getString()方法从XML中获取String*/
				CharSequence string2=getString(R.string.yourWish);
				CharSequence string3=getString(R.string.hasSend);
				/*使用系统标准的makeText()方式来产生Toast 信息*/
				Toast.makeText( toast_christmas.this,string2+Str.toString()+string3,Toast.LENGTH_LONG).show();
				/*清空EditText*/
				mEditText.setText("");
			}
		});
	}
}