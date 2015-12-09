
package com.codezyw.common;

import android.app.Application;

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHelper mCustomCrashHandler = CrashHelper.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
        DeviceHelper.getInstance().setContext(getApplicationContext());
    }
}