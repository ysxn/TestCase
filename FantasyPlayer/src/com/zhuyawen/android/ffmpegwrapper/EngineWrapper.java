package com.zhuyawen.android.ffmpegwrapper;

public class EngineWrapper
{
    public static boolean forceShutdown;
    public static Object lockingObject;
    public static boolean opened = false;

    static
    {
        forceShutdown = false;
        lockingObject = new Object();
        //System.load("/data/data/com.zhuyawen.android.fantasyplayer/lib/libffmpeg.so");
        //System.load("/data/data/com.zhuyawen.android.fantasyplayer/lib/libsimpledecoder.so");
        _init();
    }

    public static void _close()
    {
        synchronized (lockingObject)
        {
          close();
          return;
        }
    }

    public static int _convertFrameData()
    {
        synchronized (lockingObject)
        {
          int i = convertFrameData();
          return i;
        }
    }

    public static long _getDTS()
    {
        synchronized (lockingObject)
        {
          long l = getDTS();
          return l;
        }
    }

    public static void _getDirectFrameData(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        synchronized (lockingObject)
        {
          getDirectFrameData(paramObject, paramInt1, paramInt2, paramInt3, paramInt4);
          return;
        }
    }

    public static int _getDuration()
    {
        synchronized (lockingObject)
        {
          int i = getDuration();
          return i;
        }
    }

    public static int _getNextFrame()
    {
        synchronized (lockingObject)
        {
          int i = getNextFrame();
          return i;
        }
    }

    public static double _getVideoFrameRate()
    {
        synchronized (lockingObject)
        {
          double d = getVideoFrameRate();
          return d;
        }
    }

    public static int _getVideoHeight()
    {
        synchronized (lockingObject)
        {
          int i = getVideoHeight();
          return i;
        }
    }

    public static int _getVideoWidth()
    {
        synchronized (lockingObject)
        {
          int i = getVideoWidth();
          return i;
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

    public static int _open(String paramString)
    {
        synchronized (lockingObject)
        {
          int i = open(paramString);
          return i;
        }
    }

    private static native void close();

    private static native int convertFrameData();

    private static native long getDTS();

    private static native void getDirectFrameData(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    private static native int getDuration();

    private static native int getNextFrame();

    private static native double getVideoFrameRate();

    private static native int getVideoHeight();

    private static native int getVideoWidth();

    private static native void init();

    private static native int open(String paramString);
}