package com.android.demo.toast_textview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class toast_textview extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ������������*/
		setContentView(R.layout.main);
		/* ������Toast����*/
		Toast showImageToast = new Toast(this);
		/*�½�TextView����*/
		TextView text=new TextView(this);
		/*����TextView����*/
		text.setText("��ʾ��Toast�е�TextView");
		/* ����Toast�ϵ�View--(TextView) */
		showImageToast.setView(text);
		/* ����Toast��ʾʱ��*/
		showImageToast.setDuration(Toast.LENGTH_LONG);
		/* ��ʾToast */
		showImageToast.show();
	}
}