package com.android.demo.toast_button;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class toast_button extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ������������*/
		setContentView(R.layout.main);
		/* ������Toast����*/
		Toast showImageToast = new Toast(this);
		// /*�½�Button����*/
		Button button = new Button(this);
		button.setText("OK");
		/* ����Toast�ϵ�View--(Button) */
		showImageToast.setView(button);
		/* ����Toast��ʾʱ��*/
		showImageToast.setDuration(Toast.LENGTH_LONG);
		/* ��ʾToast */
		showImageToast.show();
	}
}