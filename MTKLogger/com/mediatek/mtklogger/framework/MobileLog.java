package com.mediatek.mtklogger.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import com.mediatek.mtklogger.utils.Utils;

public class MobileLog
  extends LogInstance
{
  private static final String COMMAND_BOOT = "copy";
  private static final String COMMAND_CHECK = "check";
  private static final String COMMAND_DIE = "die";
  private static final String COMMAND_IPO = "IPO_BOOT";
  private static final String COMMAND_IPO_SHUTDOWN = "IPO_SHUTDOWN";
  private static final String COMMAND_START = "start";
  private static final String COMMAND_STOP = "stop";
  public static final SparseArray<String> KEY_SUB_LOG_TYPE_MAP = new SparseArray();
  public static final String SOCKET_NAME = "mobilelogd";
  public static final int SUB_LOG_TYPE_ANDROID = 1;
  public static final int SUB_LOG_TYPE_BT = 3;
  public static final int SUB_LOG_TYPE_KERNEL = 2;
  private static final String TAG = "MTKLogger/MobileLog";
  private boolean bConnected = false;
  private Handler mCmdResHandler = null;
  Object mobileLogLock = new Object();
  
  static
  {
    KEY_SUB_LOG_TYPE_MAP.put(1, "AndroidLog");
    KEY_SUB_LOG_TYPE_MAP.put(2, "KernelLog");
    KEY_SUB_LOG_TYPE_MAP.put(3, "BTLog");
  }
  
  public MobileLog(Context paramContext, Handler paramHandler)
  {
    super(paramContext);
    new MobileLogThread().start();
    synchronized (this.mobileLogLock)
    {
      try
      {
        this.mobileLogLock.wait(500L);
        this.mCmdResHandler = paramHandler;
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          Utils.logi("MTKLogger/MobileLog", "Wait modem log sub thread initialization, but was interrupted");
        }
      }
    }
  }
  
  private void notifyServiceStatus(int paramInt, String paramString)
  {
    Utils.logi("MTKLogger/MobileLog", "-->notifyServiceStatus(), status=" + paramInt + ",  reason=[" + paramString + "]");
    if (1 == paramInt)
    {
      if (this.mSharedPreferences != null) {
        this.mSharedPreferences.edit().putInt("mobilelog_enable", 1).commit();
      }
      updateStatusBar(1, 2131165186, true);
    }
    for (;;)
    {
      this.mCmdResHandler.obtainMessage(1, 1, paramInt, paramString).sendToTarget();
      return;
      if (this.mSharedPreferences != null) {
        this.mSharedPreferences.edit().putInt("mobilelog_enable", 0).commit();
      }
      updateStatusBar(1, 2131165186, false);
    }
  }
  
  class MobileLogConnection
    extends LogConnection
  {
    public MobileLogConnection(String paramString, Handler paramHandler)
    {
      super(paramHandler);
    }
    
    public void dealWithResponse(byte[] paramArrayOfByte, Handler paramHandler)
    {
      super.dealWithResponse(paramArrayOfByte, paramHandler);
      Utils.logi("MTKLogger/MobileLog", "-->dealWithResponse(), resp=" + new String(paramArrayOfByte));
      if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      {
        Utils.logw("MTKLogger/MobileLog", "Get an empty response from native, ignore it.");
        return;
      }
      int i;
      switch (paramArrayOfByte[0])
      {
      default: 
        i = 23;
        Utils.logd("MTKLogger/MobileLog", "kmsgcat || logcat alive. recv unkown bytes");
      }
      for (;;)
      {
        paramHandler.sendEmptyMessage(i);
        return;
        i = 22;
        Utils.loge("MTKLogger/MobileLog", "kmsgcat || logcat die");
        continue;
        i = 23;
        continue;
        i = 24;
        Utils.logd("MTKLogger/MobileLog", "kmsgcat || sd card is almost full");
      }
    }
  }
  
  class MobileLogHandler
    extends LogInstance.LogHandler
  {
    MobileLogHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      Utils.logi("MTKLogger/MobileLog", "Handle receive message, what=" + paramMessage.what);
      if (!MobileLog.this.bConnected)
      {
        if (MobileLog.this.mLogConnection == null) {
          MobileLog.this.mLogConnection = new MobileLog.MobileLogConnection(MobileLog.this, "mobilelogd", MobileLog.this.mHandler);
        }
        MobileLog.access$002(MobileLog.this, MobileLog.this.initLogConnection());
      }
      Object localObject = paramMessage.obj;
      String str1 = null;
      if (localObject != null)
      {
        boolean bool3 = localObject instanceof String;
        str1 = null;
        if (bool3) {
          str1 = (String)localObject;
        }
      }
      switch (paramMessage.what)
      {
      default: 
        Utils.logw("MTKLogger/MobileLog", "Not supported message: " + paramMessage.what);
      case 1: 
      case 3: 
      case 4: 
      case 7: 
      case 8: 
      case 9: 
        do
        {
          int k;
          do
          {
            return;
            if (!MobileLog.this.bConnected)
            {
              Utils.loge("MTKLogger/MobileLog", "Fail to establish connection to native layer.");
              if ((MobileLog.this.mSharedPreferences != null) && (1 == MobileLog.this.mSharedPreferences.getInt("mobilelog_enable", 1))) {
                MobileLog.this.mSharedPreferences.edit().putInt("mobilelog_enable", 0).commit();
              }
              MobileLog.this.notifyServiceStatus(0, "1");
              return;
            }
            k = MobileLog.this.getLogStorageState();
            if (k == 1) {
              break;
            }
            Utils.loge("MTKLogger/MobileLog", "Log storage is not ready yet.");
            if ((MobileLog.this.mSharedPreferences != null) && (1 == MobileLog.this.mSharedPreferences.getInt("mobilelog_enable", 1))) {
              MobileLog.this.mSharedPreferences.edit().putInt("mobilelog_enable", 0).commit();
            }
            Utils.logi("MTKLogger/MobileLog", "Going to start mobile log, but SD card not ready yet, status=" + k + ", just send out a stop command to native.");
            MobileLog.this.mLogConnection.sendCmd("stop");
            if (k == -1)
            {
              MobileLog.this.notifyServiceStatus(0, "2");
              return;
            }
          } while (k != -2);
          MobileLog.this.notifyServiceStatus(0, "3");
          return;
          boolean bool2;
          if (!TextUtils.isEmpty(str1))
          {
            Utils.logi("MTKLogger/MobileLog", "Mobile log initialization reason = " + str1);
            if ("boot".equals(str1)) {
              bool2 = MobileLog.this.mLogConnection.sendCmd("copy");
            }
          }
          while (bool2)
          {
            sendEmptyMessageDelayed(4, 10000L);
            MobileLog.this.notifyServiceStatus(1, "");
            return;
            if ("ipo".equals(str1))
            {
              bool2 = MobileLog.this.mLogConnection.sendCmd("IPO_BOOT");
            }
            else
            {
              Utils.logw("MTKLogger/MobileLog", "Unsupported initialization reason, ignore it.");
              bool2 = MobileLog.this.mLogConnection.sendCmd("start");
              continue;
              Utils.logd("MTKLogger/MobileLog", "No valid start up reason was found when init. Just start up.");
              bool2 = MobileLog.this.mLogConnection.sendCmd("start");
            }
          }
          Utils.loge("MTKLogger/MobileLog", "Send start command to native layer fail, maybe connection has already be lost.");
          MobileLog.this.notifyServiceStatus(0, "4");
          return;
          if (MobileLog.this.bConnected)
          {
            boolean bool1;
            if (!TextUtils.isEmpty(str1))
            {
              Utils.logi("MTKLogger/MobileLog", "Mobile log stop reason = " + str1);
              if ("ipo_shutdown".equals(str1))
              {
                bool1 = MobileLog.this.mLogConnection.sendCmd("IPO_SHUTDOWN");
                MobileLog.this.mLogConnection.stop();
                MobileLog.this.mLogConnection = null;
                MobileLog.access$002(MobileLog.this, false);
                if (bool1) {
                  break label800;
                }
                Utils.loge("MTKLogger/MobileLog", "Send stop command to native layer fail, maybe connection has already be lost.");
                MobileLog.this.notifyServiceStatus(0, "4");
              }
            }
            for (;;)
            {
              removeMessages(4);
              return;
              Utils.logw("MTKLogger/MobileLog", "Unsupported stop reason, ignore it.");
              bool1 = MobileLog.this.mLogConnection.sendCmd("stop");
              break;
              Utils.logd("MTKLogger/MobileLog", "Normally stop mobile log with stop command");
              bool1 = MobileLog.this.mLogConnection.sendCmd("stop");
              break;
              String str3 = "";
              if ("sd_timeout".equals(str1)) {
                str3 = "11";
              }
              MobileLog.this.notifyServiceStatus(0, str3);
            }
          }
          Utils.logw("MTKLogger/MobileLog", "Have not connected to native layer, just ignore this command");
          MobileLog.this.notifyServiceStatus(0, "1");
          return;
          if (MobileLog.this.bConnected)
          {
            Utils.logd("MTKLogger/MobileLog", "Receive a check command. Ignore it.");
            return;
          }
          Utils.logw("MTKLogger/MobileLog", "lost connection to native layer, stop check.");
          removeMessages(4);
          return;
          if (MobileLog.this.bConnected)
          {
            if (!TextUtils.isEmpty(str1))
            {
              Utils.logd("MTKLogger/MobileLog", "Receive config command, parameter=" + str1);
              if ((str1.startsWith("logsize=")) || (str1.startsWith("autostart=")))
              {
                MobileLog.this.mLogConnection.sendCmd(str1);
                return;
              }
              if (str1.startsWith("sublog_"))
              {
                int i = paramMessage.arg1;
                int j = paramMessage.arg2;
                Utils.logv("MTKLogger/MobileLog", "Try to set mobile sub log enable state, subType=" + i + ", enable?" + j);
                String str2 = (String)MobileLog.KEY_SUB_LOG_TYPE_MAP.get(i);
                if (str2 == null)
                {
                  Utils.loge("MTKLogger/MobileLog", "Unsupported sub mobile log type");
                  return;
                }
                MobileLog.this.mLogConnection.sendCmd("sublog_" + str2 + "=" + j);
                return;
              }
              Utils.logw("MTKLogger/MobileLog", "Unsupported config command");
              return;
            }
            Utils.loge("MTKLogger/MobileLog", "Receive config command, but parameter is null");
            return;
          }
          Utils.logw("MTKLogger/MobileLog", "Fail to config native parameter because of lost connection.");
          return;
          Utils.logd("MTKLogger/MobileLog", "Receive adb command[" + str1 + "]");
          return;
        } while (MobileLog.this.bConnected);
        Utils.logw("MTKLogger/MobileLog", "Reconnect to native layer fail! Mark log status as stopped.");
        MobileLog.this.notifyServiceStatus(0, "1");
        return;
      case 22: 
        label800:
        if (MobileLog.this.mLogConnection != null)
        {
          MobileLog.this.mLogConnection.stop();
          MobileLog.this.mLogConnection = null;
        }
        removeMessages(4);
        MobileLog.access$002(MobileLog.this, false);
        MobileLog.this.notifyServiceStatus(0, "5");
        return;
      }
      if (MobileLog.this.mSharedPreferences.getInt("mobilelog_enable", 0) != 1)
      {
        MobileLog.this.notifyServiceStatus(1, "");
        return;
      }
      Utils.logv("MTKLogger/MobileLog", "Still running, ignore alive message.");
    }
  }
  
  class MobileLogThread
    extends Thread
  {
    MobileLogThread() {}
    
    public void run()
    {
      Looper.prepare();
      MobileLog.this.mHandler = new MobileLog.MobileLogHandler(MobileLog.this);
      Utils.logd("MTKLogger/MobileLog", "Begin to construct MobileLogConnection instance with MobileLog handler.");
      MobileLog.this.mLogConnection = new MobileLog.MobileLogConnection(MobileLog.this, "mobilelogd", MobileLog.this.mHandler);
      synchronized (MobileLog.this.mobileLogLock)
      {
        MobileLog.this.mobileLogLock.notify();
        Looper.loop();
        return;
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.MobileLog
 * JD-Core Version:    0.7.0.1
 */