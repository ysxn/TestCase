package com.android.trafficmeter.service;

import com.android.trafficmeter.TrafficConst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 *	Perform a service after the system has finished booting or when the connecting status is change
 * @author archermind
 *
 */
public class TrafficReceiver extends BroadcastReceiver {

	private static final String CONNECTION_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
	private Intent intent = null;
	@Override
	public void onReceive(Context context, Intent intent) {
		initializeSystem(context, intent);
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "TrafficReceiver :"+intent.getAction());
		}
	}

	/**
	 *	Perform a service
	 * @param context
	 * @param intent
	 */
	public void initializeSystem(Context context,Intent intent) {
		if(CONNECTION_ACTION.equals(intent.getAction())) {
			boolean noConnectivity =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			if(noConnectivity) {
				stopService(context,getIntent(context));
			} else {
				startService(context,getIntent(context));
			}
		} else if(ACTION_BOOT_COMPLETED.equals(intent.getAction())){
			intent = getIntent(context);
			intent.putExtra("BOOT_COMPLETED", true);
			startService(context,getIntent(context));
		}
	}


	private void startService(Context context, Intent intent) {
		context.startService(intent);

	}
	private void stopService(Context context,Intent intent) {
		context.stopService(new Intent(context, TrafficService.class));
	}
	private Intent getIntent(Context context) {
		if(intent == null) {
			intent = new Intent(context, TrafficService.class);
		}
		return intent;
	}

}
