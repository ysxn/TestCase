package com.android_160examples_4_28_widget_time;

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

public class android_160examples_4_28_widget_time extends AppWidgetProvider {
    /** Called when the activity is first created. */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
    	Log.i("zhuyawen : android_160examples_4_28_widget_time", " onUpdate");
    	Intent intent = new Intent(context, UpdateService.class);
    	context.startService(intent);
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
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
			Log.i("zhuyawen : UpdateService", " onStart");
			super.onStart(intent, startId);
			RemoteViews updateview = new RemoteViews(this.getPackageName(), R.layout.appwidget_provider);
			updateview.setTextViewText(R.id.appwidget_provider, "zhuyawen: " + Long.toString(SystemClock.elapsedRealtime()));
			ComponentName thisWidget = new ComponentName(this, android_160examples_4_28_widget_time.class);
			Log.i("zhuyawen : UpdateService", " ComponentName" + thisWidget.toString());
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, updateview);
		}
    }
}