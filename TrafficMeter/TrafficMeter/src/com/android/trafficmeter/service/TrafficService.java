package com.android.trafficmeter.service;

import java.util.Calendar;

import com.android.trafficmeter.DailyOperator;
import com.android.trafficmeter.TrafficConst;
import com.android.trafficmeter.TrafficHelper;
import com.android.trafficmeter.TrafficMeterApplication;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

/**
 *	A service designed to load and periodically save the bytes from network
 * @author archermind
 *
 */
public class TrafficService extends Service {

	private boolean isBootCompleted = false;
	private DailyOperator operator = null;
	private TrafficMeterApplication app = null;
	private Handler handler ;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		TrafficMeterApplication app = (TrafficMeterApplication)getApplication();
       SharedPreferences prefs = app.getAdapter(SharedPreferences.class);
       String mlastUpdate = prefs.getString(TrafficConst.CURRENTDAY, null);
		DailyOperator.setMlastUpdate(mlastUpdate);

		operator = new DailyOperator();
		HandlerThread looper = new HandlerThread("Traffic Handler");
		looper.start();
		handler = new Handler(looper.getLooper());
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "TrafficService :onCreate");
		}
	}

	/**
	 * save the status when the service is destroying
	 */
	@Override
	public void onDestroy() {
		handler.post(tranfficRunner);
		app.saveCurrentDay(TrafficHelper.getDate(Calendar.getInstance()));
		isBootCompleted = false;
		super.onDestroy();
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "TrafficService :onDestroy");
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		isBootCompleted = intent.getBooleanExtra("START_SERVICE_FROM_CRASH", false);
		handler.removeCallbacks(tranfficRunner);
		handler.post(tranfficRunner);
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "TrafficService :onStartCommand");
		}
		return START_NOT_STICKY;
	}


	/**
	 *	A thread just for tracking Data
	 */
	private final Runnable tranfficRunner = new Runnable() {

		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				operator.trackData(TrafficService.this,isBootCompleted);
				isBootCompleted = false;
				if(TrafficConst.DEBUG) {
					Log.d(TrafficConst.TAG, "Thread.currentThread() :"+Thread.currentThread().getId());
				}
				try {
                    Thread.sleep(30000);
               } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
               }
			}
		}

	};

}
