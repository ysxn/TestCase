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
		/* 设置主屏布局*/
		setContentView(R.layout.main);
		/* 创建新Toast对象*/
		Toast showImageToast = new Toast(this);
		/*新建TextView对象*/
		TextView text=new TextView(this);
		/*设置TextView内容*/
		text.setText("显示在Toast中的TextView");
		/* 设置Toast上的View--(TextView) */
		showImageToast.setView(text);
		/* 设置Toast显示时间*/
		showImageToast.setDuration(Toast.LENGTH_LONG);
		/* 显示Toast */
		showImageToast.show();
	}
}