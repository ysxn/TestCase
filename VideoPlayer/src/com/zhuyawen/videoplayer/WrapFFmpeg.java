package com.zhuyawen.videoplayer;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;

public class WrapFFmpeg
{
    public static boolean forceShutdown;
    public static Object lockingObject;
    private static boolean opened = false;

    static
    {
        forceShutdown = false;
        lockingObject = new Object();
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeg_jni");
        init();
    }

    public static int getTargetHeight()
    {
        synchronized (lockingObject)
        {
          return native_getTargetHeight();
        }
    }
    
    public static int getTargetWidth()
    {
        synchronized (lockingObject)
        {
          return native_getTargetWidth();
        }
    }

    public static void init()
    {
        synchronized (lockingObject)
        {
          native_init();
          return;
        }
    }
    
    public static void exit()
    {
        synchronized (lockingObject)
        {
            native_exit();
            opened = false;
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
    
    public static int openFile(String path, int screenWidth, int screenHeight, int fixWH)
    {
        synchronized (lockingObject)
        {
          int ret = native_openFile(path, screenWidth, screenHeight, fixWH);
          if (ret >= 0) {
              opened = true;
          } else {
              native_exit();
          }
          return ret;
        }
    }
    
    public static int drawFrame(Bitmap bitmap)
    {
        synchronized (lockingObject)
        {
          return native_drawFrame(bitmap);
        }
    }
    
    public static int drawFrameAt(Bitmap bitmap, int sec)
    {
        synchronized (lockingObject)
        {
            return native_drawFrameAt(bitmap, sec);
        }
    }
    
    public static int seekTo(int sec)
    {
        synchronized (lockingObject)
        {
            return native_seekTo(sec);
        }
    }
    
    public static float getTimeBase()
    {
        synchronized (lockingObject)
        {
            return native_getTimeBase();
        }
    }
    
    public static boolean isFileOpen() {
        // TODO Auto-generated method stub
        synchronized (lockingObject)
        {
          return opened;
        }
    }
    
    public static int setDisplay(Surface s) {
        // TODO Auto-generated method stub
        synchronized (lockingObject)
        {
          return native_setDisplay(s);
        }
    }
    
    
    private void postEventFromNative(byte[] audioData) {
//        VideoPlayerActivity.writeToTrack(audioData);
        Log.i("WrapFFmpeg", ">>>>>>>>>>>>>>>>>>>>>>postEventFromNative, audioData.length="+audioData.length);
    }
    
    private static native void native_init();
    
    private static native void native_exit();
    
    private static native int native_openFile(String path, int screenWidth, int screenHeight, int fixWH);
    
    private static native int native_drawFrame(Bitmap bitmap);
    
    private static native int native_drawFrameAt(Bitmap bitmap, int sec);
    
    private static native int native_seekTo(int sec);

    private static native int native_getTargetWidth();
    
    private static native int native_getTargetHeight();
    
    private static native String native_getVersion();
    
    private static native float native_getTimeBase();
    
    private static native int native_setDisplay(Surface s);


}