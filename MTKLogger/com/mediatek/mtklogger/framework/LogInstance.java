package com.mediatek.mtklogger.framework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LogInstance
{
  private static final int INTERNAL_UPDATE_DURATION = 1000;
  public static final int MSG_ADB_CMD = 8;
  public static final int MSG_ALIVE = 23;
  public static final int MSG_CHECK = 4;
  public static final int MSG_CONFIG = 7;
  public static final int MSG_CONN_FAIL = 21;
  public static final int MSG_DIE = 22;
  public static final int MSG_INIT = 1;
  public static final int MSG_RESTORE_CONN = 9;
  public static final int MSG_SD_ALMOST_FULL = 24;
  public static final int MSG_SPECIAL_BASE = 50;
  public static final int MSG_START = 2;
  public static final int MSG_START_SHELL_CMD = 5;
  public static final int MSG_STOP = 3;
  public static final int MSG_STOP_SHELL_CMD = 6;
  public static final int MSG_UNKNOWN = 0;
  private static final HashMap<Integer, Integer> PENDING_OFF_NOTIFICATION_MAP = new HashMap();
  private static final HashMap<Integer, Integer> PENDING_ON_NOTIFICATION_MAP;
  public static final String PREFIX_CONFIG_AUTO_START = "autostart=";
  public static final String PREFIX_CONFIG_LOG_SIZE = "logsize=";
  public static final String PREFIX_CONFIG_SUB_LOG = "sublog_";
  protected static final HashMap<Integer, Integer> RUNNING_NOTIFICATION_MAP;
  private static final String TAG = "MTKLogger/LogInstance";
  protected static Context mContext;
  private static NotificationManager mNM = null;
  private static Handler notificationHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage != null) {
        for (;;)
        {
          String str3;
          String str4;
          synchronized (LogInstance.RUNNING_NOTIFICATION_MAP)
          {
            int i = paramAnonymousMessage.what;
            if (LogInstance.RUNNING_NOTIFICATION_MAP.size() <= 0)
            {
              LogInstance.showLogStatusInNotificationBar(false, null, null, i, null);
              LogInstance.PENDING_ON_NOTIFICATION_MAP.clear();
              LogInstance.PENDING_OFF_NOTIFICATION_MAP.clear();
              return;
            }
            String str1 = LogInstance.mContext.getString(2131165188);
            String str2 = LogInstance.getLogStateDescStr(LogInstance.RUNNING_NOTIFICATION_MAP, 2131165189, 2131165190);
            str3 = LogInstance.getLogStateDescStr(LogInstance.PENDING_ON_NOTIFICATION_MAP, 2131165189, 2131165190);
            str4 = LogInstance.getLogStateDescStr(LogInstance.PENDING_OFF_NOTIFICATION_MAP, 2131165191, 2131165192);
            if ((!TextUtils.isEmpty(str3)) && (!TextUtils.isEmpty(str4)))
            {
              localObject2 = str3 + ", " + str4;
              LogInstance.showLogStatusInNotificationBar(true, str1, str2, i, (String)localObject2);
            }
          }
          String str5 = str3 + str4;
          Object localObject2 = str5;
        }
      }
    }
  };
  protected LogHandler mHandler;
  protected LogConnection mLogConnection;
  protected SharedPreferences mSharedPreferences;
  
  static
  {
    RUNNING_NOTIFICATION_MAP = new HashMap();
    PENDING_ON_NOTIFICATION_MAP = new HashMap();
  }
  
  public LogInstance(Context paramContext)
  {
    mContext = paramContext;
    this.mSharedPreferences = paramContext.getSharedPreferences("log_settings", 0);
  }
  
  private int getAvailableLogMemorySize()
  {
    String str = Utils.getLogPathType();
    if (("/mnt/sdcard".equals(str)) || ("/mnt/sdcard2".equals(str))) {
      return Utils.getAvailableStorageSize(Utils.getCurrentLogPath(mContext));
    }
    if ("/data".equals(str)) {
      return Utils.getAvailableStorageSize(Environment.getDataDirectory().getPath());
    }
    Utils.loge("MTKLogger/LogInstance", "Unknown log path type: " + str);
    return 0;
  }
  
  public static LogInstance getInstance(int paramInt, Context paramContext, Handler paramHandler)
  {
    switch (paramInt)
    {
    case 3: 
    default: 
      Utils.loge("MTKLogger/LogInstance", "Unsported tag instance type[" + paramInt + "] till now.");
      return null;
    case 4: 
      return new NetworkLog(paramContext, paramHandler);
    case 1: 
      return new MobileLog(paramContext, paramHandler);
    }
    return new MultiModemLog(paramContext, paramHandler);
  }
  
  private static String getLogStateDescStr(Map<Integer, Integer> paramMap, int paramInt1, int paramInt2)
  {
    if ((paramMap == null) || (paramMap.size() == 0)) {
      return "";
    }
    if (paramMap.size() == 1) {}
    String str2;
    for (String str1 = mContext.getString(paramInt1);; str1 = mContext.getString(paramInt2))
    {
      str2 = "";
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        if (!"".equals(str2)) {
          str2 = str2 + ", ";
        }
        str2 = str2 + mContext.getString(((Integer)paramMap.get(localInteger)).intValue());
      }
    }
    return str2 + " " + str1;
  }
  
  public static boolean initLogConnection(LogConnection paramLogConnection)
  {
    Utils.logd("MTKLogger/LogInstance", "-->initLogConnection() with parameter");
    if (paramLogConnection == null)
    {
      Utils.loge("MTKLogger/LogInstance", "LogConnection is null");
      return false;
    }
    int i = 8;
    while (i != 0) {
      try
      {
        if (!paramLogConnection.connect())
        {
          Thread.sleep(500L);
          Utils.loge("MTKLogger/LogInstance", "Connection retry");
          i--;
        }
      }
      catch (Exception localException)
      {
        Utils.loge("MTKLogger/LogInstance", "thread " + localException.toString());
        return false;
      }
    }
    boolean bool = false;
    if (i > 0) {
      bool = true;
    }
    return bool;
  }
  
  public static void showLogStatusInNotificationBar(boolean paramBoolean, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    Utils.logd("MTKLogger/LogInstance", "-->showLogStatusInNotificationBar(), enable?" + paramBoolean + ", title=" + paramString1 + ", summary=" + paramString2 + ", tickerText=" + paramString3 + ", icon=" + paramInt);
    if (mNM == null) {
      mNM = (NotificationManager)mContext.getSystemService("notification");
    }
    if (paramBoolean)
    {
      localNotification = new Notification(paramInt, paramString3, System.currentTimeMillis());
      localNotification.flags = (0x20 | localNotification.flags);
      localIntent = new Intent();
      localIntent.setComponent(new ComponentName("com.mediatek.mtklogger", "com.mediatek.mtklogger.MainActivity"));
      localIntent.setFlags(536870912);
      if (mContext.getPackageManager().resolveActivity(localIntent, 0) != null)
      {
        localPendingIntent = PendingIntent.getActivity(mContext, 0, localIntent, 0);
        localNotification.setLatestEventInfo(mContext, paramString1, paramString2, localPendingIntent);
        if (mNM != null) {
          mNM.notify(paramInt, localNotification);
        }
      }
    }
    while (mNM == null) {
      for (;;)
      {
        Notification localNotification;
        Intent localIntent;
        return;
        Utils.loge("MTKLogger/LogInstance", "Could not find MTKLogger settings page.");
        PendingIntent localPendingIntent = null;
      }
    }
    mNM.cancel(paramInt);
  }
  
  public void checkLogFolder() {}
  
  public int getGlobalRunningStage()
  {
    return 0;
  }
  
  public int getLogRunningStatus()
  {
    return -1;
  }
  
  public int getLogStorageState()
  {
    String str1 = Utils.getLogPathType();
    Utils.logd("MTKLogger/LogInstance", "-->getStorageState(), currentLogPathType=" + str1);
    if (("/mnt/sdcard".equals(str1)) || ("/mnt/sdcard2".equals(str1)))
    {
      String str2 = Utils.getCurrentVolumeState(mContext);
      if (!"mounted".equals(str2))
      {
        Utils.logw("MTKLogger/LogInstance", "Log storage is not ready yet, state=" + str2);
        return -1;
      }
    }
    int i = getAvailableLogMemorySize();
    if (i < 10)
    {
      Utils.logw("MTKLogger/LogInstance", "Not enough storage for log, current available value=" + i + "MB");
      return -2;
    }
    Utils.logd("MTKLogger/LogInstance", "<--getStorageState(), storage is ready for log capture.");
    return 1;
  }
  
  public boolean initLogConnection()
  {
    return initLogConnection(this.mLogConnection);
  }
  
  public void updateStatusBar(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Utils.logd("MTKLogger/LogInstance", "-->updateStatusBar(), logType=" + paramInt1 + ", titleRes=" + paramInt2 + ", enable=" + paramBoolean);
    localHashMap = RUNNING_NOTIFICATION_MAP;
    if (paramBoolean) {}
    for (;;)
    {
      try
      {
        RUNNING_NOTIFICATION_MAP.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
        PENDING_ON_NOTIFICATION_MAP.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
        PENDING_OFF_NOTIFICATION_MAP.remove(Integer.valueOf(paramInt1));
        if (notificationHandler.hasMessages(2130837517))
        {
          Utils.logw("MTKLogger/LogInstance", "Too frequent status bar update request, slow down.");
          notificationHandler.removeMessages(2130837517);
        }
        notificationHandler.sendEmptyMessageDelayed(2130837517, 1000L);
        return;
      }
      finally {}
      RUNNING_NOTIFICATION_MAP.remove(Integer.valueOf(paramInt1));
      PENDING_OFF_NOTIFICATION_MAP.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      PENDING_ON_NOTIFICATION_MAP.remove(Integer.valueOf(paramInt1));
    }
  }
  
  class LogHandler
    extends Handler
  {
    LogHandler() {}
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.LogInstance
 * JD-Core Version:    0.7.0.1
 */