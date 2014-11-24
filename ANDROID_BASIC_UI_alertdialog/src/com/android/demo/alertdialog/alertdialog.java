package com.android.demo.alertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

public class alertdialog extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置主屏布局*/
		setContentView(R.layout.main);
		/*新建一个AlertDialog.Builder对象*/
		AlertDialog.Builder my_ADialog =new AlertDialog.Builder(this);
		/*设置标题*/
		my_ADialog.setTitle("Android 提示");
		/*设置显示消息*/
		my_ADialog.setMessage("AlertDialog.Builder提示对话框消息!!");
		/*显示*/
		my_ADialog.show();
	}
}