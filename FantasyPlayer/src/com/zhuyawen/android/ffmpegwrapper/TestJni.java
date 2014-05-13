package com.zhuyawen.android.ffmpegwrapper;

public class TestJni
{
    public static boolean forceShutdown;
    public static Object lockingObject;
    public static boolean opened = false;

    static
    {
        forceShutdown = false;
        lockingObject = new Object();
        System.loadLibrary("testjni");
        _init();
    }

    public static int _getInt()
    {
        synchronized (lockingObject)
        {
          return getInt();
        }
    }

    public static void _init()
    {
        synchronized (lockingObject)
        {
          init();
          return;
        }
    }

    private static native void init();

    private static native int getInt();
}