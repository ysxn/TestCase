package com.mediatek.mtklogger.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.LocalSocketAddress.Namespace;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;

public class NetworkLog
  extends LogInstance
{
  private static final String COMMAND_PHONE_START = "tcpdump_data_start";
  private static final String COMMAND_PHONE_STOP = "tcpdump_data_stop";
  private static final String COMMAND_PHONE_STOP_WITHOUT_PING = "tcpdump_data_stop_noping";
  private static final String COMMAND_SD_CHECK = "tcpdump_sdcard_check";
  private static final String COMMAND_SD_START = "tcpdump_sdcard_start";
  private static final String COMMAND_SD_STOP = "tcpdump_sdcard_stop";
  private static final String COMMAND_SD_STOP_WITHOUT_PING = "tcpdump_sdcard_stop_noping";
  private static final int RESP_MSG_BASE = 32;
  private static final int RESP_TYPE_CHECK = 4;
  private static final int RESP_TYPE_MONITOR = 7;
  private static final int RESP_TYPE_SHELL_START = 5;
  private static final int RESP_TYPE_SHELL_STOP = 6;
  private static final int RESP_TYPE_START = 1;
  private static final int RESP_TYPE_STOP = 2;
  private static final int RESP_TYPE_TAG = 3;
  private static final int RESP_TYPE_UNKNOWN = 8;
  public static final String SOCKET_NAME = "netdiag";
  private static final String STOP_REASON_LOG_FOLDER_LOST = "folder_lost";
  private static final String TAG = "MTKLogger/NetworkLog";
  private boolean bConnected = false;
  private Handler mCmdResHandler = null;
  Object networLogLock = new Object();
  
  public NetworkLog(Context paramContext, Handler paramHandler)
  {
    super(paramContext);
    new NetworkLogThread().start();
    synchronized (this.networLogLock)
    {
      try
      {
        this.networLogLock.wait(500L);
        this.mCmdResHandler = paramHandler;
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          Utils.loge("MTKLogger/NetworkLog", "Wait network log sub thread initialization, but was interrupted");
        }
      }
    }
  }
  
  private void notifyServiceStatus(int paramInt, String paramString)
  {
    Utils.logi("MTKLogger/NetworkLog", "-->notifyServiceStatus(), status=" + paramInt + ",  reason=[" + paramString + "]");
    if (1 == paramInt)
    {
      if (this.mSharedPreferences != null) {
        this.mSharedPreferences.edit().putInt("networklog_enable", 1).commit();
      }
      updateStatusBar(4, 2131165185, true);
    }
    for (;;)
    {
      this.mCmdResHandler.obtainMessage(1, 4, paramInt, paramString).sendToTarget();
      return;
      if (this.mSharedPreferences != null) {
        this.mSharedPreferences.edit().putInt("networklog_enable", 0).commit();
      }
      updateStatusBar(4, 2131165185, false);
    }
  }
  
  public void checkLogFolder()
  {
    super.checkLogFolder();
    String str = SystemProperties.get(Utils.KEY_SYSTEM_PROPERTY_NETLOG_SAVING_PATH);
    File localFile = new File(str);
    if ((this.mSharedPreferences.getInt("networklog_enable", 0) == 1) && (!localFile.exists())) {
      Utils.logw("MTKLogger/NetworkLog", "NetworkLog is running, but could not found log folder(" + str + ") from Java layer, just a remind.");
    }
  }
  
  class NetworkLogConnection
    extends LogConnection
  {
    public NetworkLogConnection(String paramString, Handler paramHandler)
    {
      super(LocalSocketAddress.Namespace.RESERVED, paramHandler);
    }
    
    public void dealWithResponse(byte[] paramArrayOfByte, Handler paramHandler)
    {
      super.dealWithResponse(paramArrayOfByte, paramHandler);
      Utils.logi("MTKLogger/NetworkLog", "-->dealWithResponse(), resp=" + new String(paramArrayOfByte));
      if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 2))
      {
        Utils.logw("MTKLogger/NetworkLog", "Get an invalid response from native, ignore it.");
        return;
      }
      int i;
      String str;
      switch (paramArrayOfByte[0])
      {
      case 104: 
      case 105: 
      case 106: 
      case 108: 
      case 110: 
      case 111: 
      case 113: 
      default: 
        i = 32 + 8;
        Utils.logw("MTKLogger/NetworkLog", "Unknown response type");
        str = null;
        switch (paramArrayOfByte[1])
        {
        default: 
          Utils.loge("MTKLogger/NetworkLog", "Unkonwn response value: " + paramArrayOfByte[0]);
        }
        break;
      }
      for (;;)
      {
        Utils.logd("MTKLogger/NetworkLog", "Response from native type=" + i + ", failReason=" + str);
        if (paramHandler == null) {
          break label364;
        }
        paramHandler.obtainMessage(i, str).sendToTarget();
        return;
        i = 32 + 1;
        break;
        i = 32 + 2;
        break;
        i = 32 + 4;
        break;
        i = 32 + 5;
        break;
        i = 32 + 6;
        break;
        i = 32 + 3;
        break;
        i = 32 + 7;
        break;
        str = "";
        continue;
        str = "12";
        continue;
        str = "10";
        continue;
        str = "3";
        continue;
        str = "8";
      }
      label364:
      Utils.loge("MTKLogger/NetworkLog", "Need to send message[" + i + "], but handler is null");
    }
  }
  
  class NetworkLogHandler
    extends LogInstance.LogHandler
  {
    NetworkLogHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      Utils.logi("MTKLogger/NetworkLog", "Handle receive message, what=" + paramMessage.what);
      if (!NetworkLog.this.bConnected)
      {
        if (NetworkLog.this.mLogConnection == null) {
          NetworkLog.this.mLogConnection = new NetworkLog.NetworkLogConnection(NetworkLog.this, "netdiag", NetworkLog.this.mHandler);
        }
        NetworkLog.access$002(NetworkLog.this, NetworkLog.this.initLogConnection());
      }
      Object localObject1 = paramMessage.obj;
      String str1 = null;
      if (localObject1 != null)
      {
        boolean bool3 = localObject1 instanceof String;
        str1 = null;
        if (bool3) {
          str1 = (String)localObject1;
        }
      }
      int m;
      switch (paramMessage.what)
      {
      default: 
        if (paramMessage.what <= 32) {
          break label1271;
        }
        m = -32 + paramMessage.what;
        String str8 = "";
        Object localObject3 = paramMessage.obj;
        if ((localObject3 != null) && ((localObject3 instanceof String))) {
          str8 = (String)localObject3;
        }
        Utils.logi("MTKLogger/NetworkLog", "Resp type: " + m + ", failReason string: [" + str8 + "]");
        if ((m != 1) && (m != 2) && (m != 4) && (m != 7)) {
          break label1244;
        }
        if (!TextUtils.isEmpty(str8)) {
          NetworkLog.this.notifyServiceStatus(0, str8);
        }
        break;
      }
      label561:
      label828:
      label887:
      do
      {
        do
        {
          for (;;)
          {
            return;
            if (!NetworkLog.this.bConnected)
            {
              Utils.loge("MTKLogger/NetworkLog", "Fail to establish connection to native layer.");
              if ((NetworkLog.this.mSharedPreferences != null) && (1 == NetworkLog.this.mSharedPreferences.getInt("networklog_enable", 1))) {
                NetworkLog.this.mSharedPreferences.edit().putInt("networklog_enable", 0).commit();
              }
              NetworkLog.this.notifyServiceStatus(0, "1");
              return;
            }
            int i = NetworkLog.this.getLogStorageState();
            String str6;
            int j;
            if (i != 1)
            {
              Utils.loge("MTKLogger/NetworkLog", "Log storage is not ready yet.");
              if ((NetworkLog.this.mSharedPreferences != null) && (1 == NetworkLog.this.mSharedPreferences.getInt("networklog_enable", 1))) {
                NetworkLog.this.mSharedPreferences.edit().putInt("networklog_enable", 0).commit();
              }
              if (i == -1)
              {
                NetworkLog.this.notifyServiceStatus(0, "2");
                return;
              }
              if (i == -2) {
                NetworkLog.this.notifyServiceStatus(0, "3");
              }
            }
            else
            {
              str6 = PreferenceManager.getDefaultSharedPreferences(LogInstance.mContext).getString("networklog_logsize", "");
              j = 200;
              if (TextUtils.isEmpty(str6)) {}
            }
            try
            {
              int k = Integer.parseInt(str6);
              if (k > 0) {
                j = k;
              }
            }
            catch (NumberFormatException localNumberFormatException)
            {
              String str7;
              String str3;
              boolean bool1;
              String str4;
              boolean bool2;
              String str5;
              Object localObject2;
              String str2;
              break label561;
            }
            if ("/data".equals(Utils.getLogPathType())) {}
            for (str7 = "tcpdump_data_start_" + j; !NetworkLog.this.mLogConnection.sendCmd(str7); str7 = "tcpdump_sdcard_start_" + j)
            {
              Utils.loge("MTKLogger/NetworkLog", "Send start command to native layer fail, maybe connection has already be lost.");
              NetworkLog.this.notifyServiceStatus(0, "4");
              return;
            }
            continue;
            if (!NetworkLog.this.bConnected) {
              break;
            }
            str3 = Utils.getLogPathType();
            bool1 = PreferenceManager.getDefaultSharedPreferences(LogInstance.mContext).getBoolean("networklog_ping_flag", false);
            Utils.logi("MTKLogger/NetworkLog", "NetworkLog ping at stop time flag = " + bool1);
            if ("/data".equals(str3)) {
              if (bool1)
              {
                str4 = "tcpdump_data_stop";
                if (TextUtils.isEmpty(str1)) {
                  break label927;
                }
                Utils.logi("MTKLogger/NetworkLog", "Network log stop reason = " + str1);
                if ((!"storage_full_or_lost".equals(str1)) && (!"sd_timeout".equals(str1)) && (!"folder_lost".equals(str1))) {
                  break label903;
                }
                bool2 = NetworkLog.this.mLogConnection.sendCmd("tcpdump_sdcard_check");
                if (bool2)
                {
                  Utils.logd("MTKLogger/NetworkLog", "Native will not response to check command, assum it to be successful.");
                  str5 = "7";
                  if (!"folder_lost".equals(str1)) {
                    break label887;
                  }
                  str5 = "8";
                  NetworkLog.this.notifyServiceStatus(0, str5);
                }
              }
            }
            for (;;)
            {
              if (bool2) {
                break label949;
              }
              Utils.loge("MTKLogger/NetworkLog", "Send stop command to native layer fail, maybe connection has already be lost.");
              NetworkLog.this.notifyServiceStatus(0, "4");
              return;
              str4 = "tcpdump_data_stop_noping";
              break;
              if (bool1)
              {
                str4 = "tcpdump_sdcard_stop";
                break;
              }
              str4 = "tcpdump_sdcard_stop_noping";
              break;
              if (!"sd_timeout".equals(str1)) {
                break label828;
              }
              str5 = "11";
              break label828;
              Utils.logw("MTKLogger/NetworkLog", "Unsupported stop reason, ignore it.");
              bool2 = NetworkLog.this.mLogConnection.sendCmd(str4);
              continue;
              Utils.logd("MTKLogger/NetworkLog", "Normally stop network log with stop command");
              bool2 = NetworkLog.this.mLogConnection.sendCmd(str4);
            }
          }
          Utils.logw("MTKLogger/NetworkLog", "Have not connected to native layer, just ignore this command");
          NetworkLog.this.notifyServiceStatus(0, "1");
          return;
          localObject2 = paramMessage.obj;
          if (localObject2 == null)
          {
            Utils.loge("MTKLogger/NetworkLog", "Fail to get start shell command.");
            return;
          }
          str2 = (String)localObject2;
          if (TextUtils.isEmpty(str2))
          {
            Utils.logw("MTKLogger/NetworkLog", "Please give me a not null command to run in shell.");
            return;
          }
          Utils.logd("MTKLogger/NetworkLog", "Send command[" + str2 + "] to shell now.");
          NetworkLog.this.mLogConnection.sendCmd("runshell_command_start_" + str2);
          return;
          Utils.logd("MTKLogger/NetworkLog", "Stop former sent shell command now.");
          NetworkLog.this.mLogConnection.sendCmd("runshell_command_stop_");
          return;
          Utils.logd("MTKLogger/NetworkLog", "Receive adb command[" + str1 + "]");
          return;
        } while (NetworkLog.this.bConnected);
        Utils.logw("MTKLogger/NetworkLog", "Reconnect to native layer fail! Mark log status as stopped.");
        NetworkLog.this.notifyServiceStatus(0, "1");
        return;
        if (NetworkLog.this.mLogConnection != null)
        {
          NetworkLog.this.mLogConnection.stop();
          NetworkLog.this.mLogConnection = null;
        }
        NetworkLog.access$002(NetworkLog.this, false);
        NetworkLog.this.notifyServiceStatus(0, "5");
        return;
        if (m == 1)
        {
          NetworkLog.this.notifyServiceStatus(1, "");
          return;
        }
      } while ((m != 1) && (m != 2));
      label903:
      label927:
      label949:
      NetworkLog.this.notifyServiceStatus(0, "");
      return;
      label1244:
      Utils.logd("MTKLogger/NetworkLog", "Ignore response whose type = " + m);
      return;
      label1271:
      Utils.logw("MTKLogger/NetworkLog", "Current unsupported messge");
    }
  }
  
  class NetworkLogThread
    extends Thread
  {
    NetworkLogThread() {}
    
    public void run()
    {
      Looper.prepare();
      NetworkLog.this.mHandler = new NetworkLog.NetworkLogHandler(NetworkLog.this);
      Utils.logd("MTKLogger/NetworkLog", "Begin to construct NetworkLogConnection instance with NetworkLog handler.");
      NetworkLog.this.mLogConnection = new NetworkLog.NetworkLogConnection(NetworkLog.this, "netdiag", NetworkLog.this.mHandler);
      synchronized (NetworkLog.this.networLogLock)
      {
        NetworkLog.this.networLogLock.notify();
        Looper.loop();
        return;
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.NetworkLog
 * JD-Core Version:    0.7.0.1
 */