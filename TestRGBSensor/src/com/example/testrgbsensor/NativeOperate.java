package com.example.testrgbsensor;

import android.graphics.Bitmap;

public class NativeOperate
{
    public static boolean forceShutdown;
    public static Object lockingObject;
    private static boolean opened = false;

    static
    {
        forceShutdown = false;
        lockingObject = new Object();
        System.loadLibrary("operate_jni");
        init();
    }

    public static void init()
    {
        synchronized (lockingObject)
        {
          native_init();
          return;
        }
    }

    public static String getVersion()
    {
        synchronized (lockingObject)
        {
          return native_getVersion();
        }
    }
    
    public static int getData()
    {
        synchronized (lockingObject)
        {
          return native_getData();
        }
    }
    
    private static native void native_init();
    
    private static native int native_getData();
    
    private static native String native_getVersion();

}