
package com.codezyw.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetManager {
    private boolean mIsNetConnected = false;

    private boolean mIsWifi = false;

    private BroadcastReceiver mNetStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetStatus(context);
        }
    };

    public void onCreate(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mNetStatusReceiver, intentFilter);
    }

    public void onDestroy(Context context) {
        context.unregisterReceiver(mNetStatusReceiver);
    }

    public void checkNetStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = cm.getActiveNetworkInfo();
        if (activeInfo == null) {
            mIsNetConnected = false;
            mIsWifi = false;
            UIHelper.showToast(context, "当前没有可用网络连接!");
            return;
        }
        mIsNetConnected = activeInfo.isConnected();
        mIsWifi = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
        if (!mIsNetConnected) {
            UIHelper.showToast(context, "当前没有可用网络连接!");
        } else if (mIsNetConnected && !mIsWifi) {
            // UIHelper.showToast(this, "当前不是WiFi连接,注意流量使用!");
        } else {
            // UIHelper.showToast(this, R.string.is_wifi);
        }
    }

    public boolean hasNet() {
        return mIsNetConnected;
    }

    public boolean hasWifi() {
        return mIsWifi;
    }
}
