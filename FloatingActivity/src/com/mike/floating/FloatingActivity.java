package com.mike.floating;

import android.app.Activity;
import android.os.Bundle;

public class FloatingActivity extends Activity {

	private FloatingView mFloatingView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mFloatingView = new FloatingView(this);
		mFloatingView.show();
	}

	@Override
	protected void onDestroy() {
		if (null != mFloatingView) {
			mFloatingView.release();
		}
		super.onDestroy();
	}
}