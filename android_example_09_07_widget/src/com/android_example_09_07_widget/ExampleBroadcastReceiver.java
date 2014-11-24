package com.android_example_09_07_widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * A BroadcastReceiver that listens for updates for the ExampleAppWidgetProvider.  This
 * BroadcastReceiver starts off disabled, and we only enable it when there is a widget
 * instance created, in order to only receive notifications when we need them.
 */
public class ExampleBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("zhuyawen : ExampleBroadcastReceiver", " onReceive SMS: " + action);
        ComponentName name = new ComponentName("com.android_example_09_07_widget", "com.android_example_09_07_widget.ExampleAppWidgetProvider");
        if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)
                || action.equals(Intent.ACTION_TIME_CHANGED)) {
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            
            ExampleAppWidgetProvider.updateAppWidget(context, gm, name);

        }

        else
        {
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            
            ExampleAppWidgetProvider.updateAppWidget(context, gm, name);
        }
    }

}
