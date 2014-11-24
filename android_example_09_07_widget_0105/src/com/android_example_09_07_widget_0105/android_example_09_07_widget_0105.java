package com.android_example_09_07_widget_0105;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Date;


public class android_example_09_07_widget_0105 extends AppWidgetProvider {
    // log tag
    private static final String TAG = "ExampleAppWidgetProvider";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	Log.i("zhuyawen : appwidget", " onUpdate");
    	ComponentName name = new ComponentName("com.android_example_09_07_widget_0105", "com.android_example_09_07_widget_0105.android_example_09_07_widget_0105");
        updateAppWidget(context, appWidgetManager, name);
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
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
    		ComponentName name) {
    	Log.i("zhuyawen : appwidget", " updateAppWidget");

    	SimpleDateFormat f = new SimpleDateFormat("hh:mm:ss");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider);
        //views.setTextViewText(R.id.appwidget_text, "zhuyawen.new:elapsedRealtime:" + Long.toString(SystemClock.elapsedRealtime()));
        views.setTextViewText(R.id.appwidget_text, "zhuyawen.new:elapsedRealtime:" 
        		+ f.format(new Date()));

        appWidgetManager.updateAppWidget(name, views);
    }
}

