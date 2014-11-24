package com.android.demo.toast_imageiew;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class toast_imageiew extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*设置主屏布局*/
		setContentView(R.layout.main);
		/*创建新Toast对象*/
		Toast showImageToast=new Toast(this);
		/*创建新ImageView对象*/
		ImageView imageView=new ImageView(this);
		/*从资源中获取图片*/
		imageView.setImageResource(R.drawable.argon);
		/*设置Toast上的View--(ImageView)*/
		showImageToast.setView(imageView);
		/*设置Toast显示时间*/
		showImageToast.setDuration(Toast.LENGTH_LONG);
		/*显示Toast*/
		showImageToast.show();
	}
}