package com.android.demo.alertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

public class alertdialog extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ������������*/
		setContentView(R.layout.main);
		/*�½�һ��AlertDialog.Builder����*/
		AlertDialog.Builder my_ADialog =new AlertDialog.Builder(this);
		/*���ñ���*/
		my_ADialog.setTitle("Android ��ʾ");
		/*������ʾ��Ϣ*/
		my_ADialog.setMessage("AlertDialog.Builder��ʾ�Ի�����Ϣ!!");
		/*��ʾ*/
		my_ADialog.show();
	}
}