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
	/*���������������(��ť��༭����)*/
	private Button mButton;
	private EditText mEditText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/*ͨ��findViewById()ȡ�ö���*/
		mButton=(Button)findViewById(R.id.Button_Send);
		mEditText=(EditText)findViewById(R.id.EditText_Wish);
		/*����onClickListener ��Button ��������onClick �¼�*/
		mButton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*�����ַ���������ȡ���û������EditText �ַ���*/
				Editable Str;
				Str=mEditText.getText();
				/*ʹ��CharSequence��getString()������XML�л�ȡString*/
				CharSequence string2=getString(R.string.yourWish);
				CharSequence string3=getString(R.string.hasSend);
				/*ʹ��ϵͳ��׼��makeText()��ʽ������Toast ��Ϣ*/
				Toast.makeText( toast_christmas.this,string2+Str.toString()+string3,Toast.LENGTH_LONG).show();
				/*���EditText*/
				mEditText.setText("");
			}
		});
	}
}