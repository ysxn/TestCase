package com.android_160examples_4_28_widget_time_0105;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.content.*;

import java.util.Calendar;

public class android_160examples_4_28_widget_time_0105 extends AppWidgetProvider {
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
    
    public static class UpdateService extends Service
    {

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
    	
		public void onStart(Intent intent, int startId)
		{
			
			super.onStart(intent, startId);
			RemoteViews updateview = new RemoteViews(this.getPackageName(), R.layout.appwidget_provider);
			updateview.setTextViewText(R.id.appwidget_provider, "zhuyawen: " + Long.toString(SystemClock.elapsedRealtime()));
			ComponentName thisWidget = new ComponentName(this, android_160examples_4_28_widget_time_0105.class);
			Log.i("zhuyawen : UpdateService", " onStart: " + thisWidget.toString());
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, updateview);
		}
		
		public void onCreate()
		{
			Log.i("zhuyawen : UpdateService", " onCreate");
			super.onCreate();
			
		}
		
		public void onDestroy()
		{
			Log.i("zhuyawen : UpdateService", " onDestroy");
			super.onDestroy();
			
		}
    }
}