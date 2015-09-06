
package com.codezyw.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SharedPreferences
 */
public class SPSync {
    @SuppressWarnings("unused")
    private final String TAG = "SPSync";

    @SuppressWarnings("unused")
    private boolean DEBUG = true;

    public static String getString(Context context, String key, final String defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setString(Context context, final String key, final String value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(Context context, final String key, final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, final String key, final boolean value) {
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

    public static long getLong(Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    public static String getString(String name, Context context, String key, final String defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static void setString(String name, Context context, final String key, final String value) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(String name, Context context, final String key, final boolean defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String name, Context context, final String key, final boolean value) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(String name, Context context, final String key, final int value) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        settings.edit().putInt(key, value).commit();
    }

    public static int getPrefInt(String name, Context context, final String key, final int defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(String name, Context context, final String key, final float value) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getPrefFloat(String name, Context context, final String key, final float defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(String name, Context context, final String key, final long value) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        settings.edit().putLong(key, value).commit();
    }

    public static long getLong(String name, Context context, final String key, final long defaultValue) {
        final SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(String name, Context context) {
        final SharedPreferences p = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean hasKey(String name, Context context, final String key) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE).contains(key);
    }
}
