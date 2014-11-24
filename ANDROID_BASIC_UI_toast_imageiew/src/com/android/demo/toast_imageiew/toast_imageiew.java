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
		/*������������*/
		setContentView(R.layout.main);
		/*������Toast����*/
		Toast showImageToast=new Toast(this);
		/*������ImageView����*/
		ImageView imageView=new ImageView(this);
		/*����Դ�л�ȡͼƬ*/
		imageView.setImageResource(R.drawable.argon);
		/*����Toast�ϵ�View--(ImageView)*/
		showImageToast.setView(imageView);
		/*����Toast��ʾʱ��*/
		showImageToast.setDuration(Toast.LENGTH_LONG);
		/*��ʾToast*/
		showImageToast.show();
	}
}