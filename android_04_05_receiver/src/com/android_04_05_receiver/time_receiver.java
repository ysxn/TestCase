package com.android_04_05_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class time_receiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("zhuyawen ; time_receiver", " ");
		android_04_05_receiver.settextbytime(Long.toString(SystemClock.elapsedRealtime()));
	}
	
}