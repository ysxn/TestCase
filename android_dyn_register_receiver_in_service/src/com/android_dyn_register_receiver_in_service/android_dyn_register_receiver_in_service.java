package com.android_dyn_register_receiver_in_service;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.content.*;

import java.util.Calendar;

public class android_dyn_register_receiver_in_service extends AppWidgetProvider {
	public static final String MY_SERVICE_STRING = "special_service_my";
    /** Called when the activity is first created. */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
    	Log.i("zhuyawen : android_160examples_4_28_widget_time", " onUpdate");
    	Intent intent = new Intent(context, UpdateService.class);
    	context.startService(intent);
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    	Log.i("zhuyawen : appwidget", " onDeleted");
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            
        }
    }

    @Override
    public void onEnabled(Context context) {
    	Log.i("zhuyawen : appwidget", " onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
    	Log.i("zhuyawen : appwidget", " onDisabled");
    	Intent intent = new Intent(context, UpdateService.class);
    	context.stopService(intent);
    }
	public static class mBR extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("zhuyawen mBR; onReceive", "onReceive onReceive");
	        ComponentName name = new ComponentName("com.android_dyn_register_receiver_in_service", 
    		"com.android_dyn_register_receiver_in_service.android_dyn_register_receiver_in_service");
	        AppWidgetManager gm = AppWidgetManager.getInstance(context);
	        android_dyn_register_receiver_in_service.updateAppWidget(context, gm, name);
		}
	}
	
    public static class UpdateService extends Service
    {
    	private Handler obj = new Handler();
    	
		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
    	
		private Runnable mtasks = new Runnable()
		{

			
			public void run() {
				// TODO Auto-generated method stub
				Log.i("zhuyawen ; Runnable mtasks ;", "Runnable mtasks ; Runnable mtasks");
				Intent i = new Intent(MY_SERVICE_STRING);
				sendBroadcast(i);
				obj.postDelayed(mtasks, 1000);
			}
			
		};
		
		public void onStart(Intent intent, int startId)
		{
			super.onStart(intent, startId);
			RemoteViews updateview = new RemoteViews(this.getPackageName(), R.layout.appwidget_provider);
			updateview.setTextViewText(R.id.appwidget_provider, "zhuyawen: new" + Long.toString(SystemClock.elapsedRealtime()));
			ComponentName thisWidget = new ComponentName(this, android_dyn_register_receiver_in_service.class);
			Log.i("zhuyawen : UpdateService", " onStart: " + thisWidget.toString());
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, updateview);
		}

		public void onCreate()
		{
			Log.i("zhuyawen : UpdateService", " onCreate");
			obj.postDelayed(mtasks, 1000);
			super.onCreate();
		}
		
		public void onDestroy()
		{
			Log.i("zhuyawen : UpdateService", " onDestroy");
			obj.removeCallbacks(mtasks);
			super.onDestroy();
		}
    }

	public static void updateAppWidget(Context context, AppWidgetManager gm,
			ComponentName name) {
		// TODO Auto-generated method stub
		RemoteViews updateview = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider);
		updateview.setTextViewText(R.id.appwidget_provider, "zhuyawen: updateAppWidget" + Long.toString(SystemClock.elapsedRealtime()));
		ComponentName thisWidget = new ComponentName(context, android_dyn_register_receiver_in_service.class);
		Log.i("zhuyawen : updateAppWidget", " updateAppWidget: " + thisWidget.toString());
		gm.updateAppWidget(thisWidget, updateview);
	}
}