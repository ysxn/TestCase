/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.musicsss;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
//import com.example.android.apis.R;

/**
 * The configuration screen for the ExampleAppWidgetProvider widget sample.
 */
public class HtcAppWidgetConfigure extends Activity {
    private static final String TAG = "HtcAppWidgetConfigure";

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private int layout_style = 0;
    private static AppWidgetManager gm = null;
    
    public HtcAppWidgetConfigure() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        if (gm == null) {
            gm = AppWidgetManager.getInstance(HtcAppWidgetConfigure.this);
        }
        
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        // Set the view layout resource to use.
        setContentView(R.layout.appwidget_configure);


        // Bind the action for the button.
        findViewById(R.id.button1).setOnClickListener(mOnClickListener1);
        findViewById(R.id.button2).setOnClickListener(mOnClickListener2);

        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

    }

    View.OnClickListener mOnClickListener1 = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = HtcAppWidgetConfigure.this;

            // When the button is clicked, save the string in our prefs and return that they
            // clicked OK.
            layout_style = 1;

            gm.bindAppWidgetId(mAppWidgetId, new ComponentName(context, HtcMediaAppWidgetProvider.class));
            // Send broadcast intent to any running MediaPlaybackService so it can
            // wrap around with an immediate update.
            Intent updateIntent = new Intent(MediaPlaybackService.SERVICECMD);
            updateIntent.putExtra(MediaPlaybackService.CMDNAME,
                    HtcMediaAppWidgetProvider.CMDAPPWIDGETUPDATE);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mAppWidgetId});
            updateIntent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
            context.sendBroadcast(updateIntent);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    View.OnClickListener mOnClickListener2 = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = HtcAppWidgetConfigure.this;

            // When the button is clicked, save the string in our prefs and return that they
            // clicked OK.
            layout_style = 2;

            gm.bindAppWidgetId(mAppWidgetId, new ComponentName(context, HtcMediaAppWidgetProviderSmall.class));
            // Send broadcast intent to any running MediaPlaybackService so it can
            // wrap around with an immediate update.
            Intent updateIntent = new Intent(MediaPlaybackService.SERVICECMD);
            updateIntent.putExtra(MediaPlaybackService.CMDNAME,
                    HtcMediaAppWidgetProviderSmall.CMDAPPWIDGETUPDATE);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mAppWidgetId});
            updateIntent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
            context.sendBroadcast(updateIntent);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

}