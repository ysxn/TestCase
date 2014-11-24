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
		/* 设置主屏布局*/
		setContentView(R.layout.main);
		/* 创建新Toast对象*/
		Toast showImageToast = new Toast(this);
		// /*新建Button对象*/
		Button button = new Button(this);
		button.setText("OK");
		/* 设置Toast上的View--(Button) */
		showImageToast.setView(button);
		/* 设置Toast显示时间*/
		showImageToast.setDuration(Toast.LENGTH_LONG);
		/* 显示Toast */
		showImageToast.show();
	}
}