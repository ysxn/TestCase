
package com.example.android.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPreferenceUtils {
    private final String TAG = "zyw";
    private boolean DEBUG = true;
    public static final String PREFERENCES = "com.example.android.notepad";
    
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    
    public static final String KEY_FINGERPRINT_0 = "fingerprint_enroll_0";
    public static final String KEY_FINGERPRINT_1 = "fingerprint_enroll_1";
    public static final String KEY_FINGERPRINT_2 = "fingerprint_enroll_2";
    public static final String KEY_FINGERPRINT_3 = "fingerprint_enroll_3";
    public static final String KEY_FINGERPRINT_4 = "fingerprint_enroll_4";
    public static final String KEY_PREFIX = "fingerprint_enroll_";
    public static final String KEY_SUFFIX_ORDER = "_order";
    public static final String KEY_SUFFIX_TITLE = "_title";
    public static final String KEY_SUFFIX_FINGER_ID = "_fingerId";
    
    public static String getPrefString(Context context, String key, final String defaultValue) {
        final SharedPreferences settings = context.getApplicationContext().getSharedPreferences(PREFERENCES,
                0);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key, final String value) {
        final SharedPreferences settings = context.getApplicationContext().getSharedPreferences(PREFERENCES,
                0);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getPrefBoolean(Context context, final String key,
            final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }

    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key, final float value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(Context context, final String key, final long value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).commit();
    }

    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context, final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }
}
