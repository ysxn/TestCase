
package com.codezyw.freeparking;

import android.content.Context;

public class SettingManager {
    private static SettingManager sSettingManager;
    private Context mContext;

    public SettingManager() {
    }

    public static SettingManager getInstance() {
        if (sSettingManager == null) {
            synchronized (SettingManager.class) {
                if (sSettingManager == null) {
                    sSettingManager = new SettingManager();
                }
            }
        }
        return sSettingManager;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
