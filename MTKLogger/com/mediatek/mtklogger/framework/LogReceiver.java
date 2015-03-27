package com.mediatek.mtklogger.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.Toast;
import com.mediatek.mtklogger.exceptionreporter.ExceptionReportManager;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class LogReceiver
  extends BroadcastReceiver
{
  private static final String ACTION_CALIBRATION_DATA = "android.intent.action.DOWNLOAD_CALIBRATION_DATA";
  private static final int DELAY_KILL_SELF = 10000;
  private static final int MSG_KILL_SELF = 1;
  private static final String TAG = "MTKLogger/LogReceiver";
  public static boolean bootupFlag = false;
  public static boolean canKillSelf = true;
  private static Handler mKillProcessCmdHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1)
      {
        Utils.logi("MTKLogger/LogReceiver", "Get a self-kill command. Need to kill me now");
        if (LogReceiver.canKillSelf) {
          Process.killProcess(Process.myPid());
        }
      }
      else
      {
        return;
      }
      Utils.logi("MTKLogger/LogReceiver", "But Log service was started already, maybe user enter UI.Do not kill self any more.");
    }
  };
  private SharedPreferences mDefaultSharedPreferences = null;
  private SharedPreferences mSharedPreferences = null;
  
  private void checkProcess()
  {
    boolean bool = needStartLogAtBootTime();
    Utils.logd("MTKLogger/LogReceiver", "-->checkProcess(), needAutoStart=" + bool + ", canKillSelf=" + canKillSelf);
    if ((!bool) && (canKillSelf))
    {
      Utils.logi("MTKLogger/LogReceiver", "MTKLogger process should not startup, send a kill message to kill it.");
      mKillProcessCmdHandler.removeMessages(1);
      mKillProcessCmdHandler.sendMessageDelayed(mKillProcessCmdHandler.obtainMessage(1), 10000L);
    }
  }
  
  private void checkRebootIssue(Context paramContext)
  {
    Utils.logd("MTKLogger/LogReceiver", "-->checkRebootIssue()");
    String str = SystemProperties.get("debug.mtk.aee.db", "");
    if (!TextUtils.isEmpty(str))
    {
      Utils.logw("MTKLogger/LogReceiver", "After reboot completed, detect an exception, notify Exception Reporter");
      mKillProcessCmdHandler.removeMessages(1);
      reportDb2ExceptionReporter(paramContext, str);
      return;
    }
    Utils.logv("MTKLogger/LogReceiver", "Normal boot completed event.");
  }
  
  private void handleSystemTimeChanged()
  {
    Utils.logd("MTKLogger/LogReceiver", "-->handleSystemTimeChanged()");
    if (isAnyLogRunning())
    {
      long l = System.currentTimeMillis();
      Utils.logd("MTKLogger/LogReceiver", "Log is running, need to notify user this log start time change event.currentTime=" + l);
      this.mSharedPreferences.edit().putInt("system_time_reset", 1).putLong("begin_recording_time", l).commit();
      Utils.logv("MTKLogger/LogReceiver", "Reset log starting time and store to shanred preference finished.");
    }
  }
  
  private void initLogStatus()
  {
    Utils.logd("MTKLogger/LogReceiver", "-->initLogStatus()");
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      if (1 == this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0))
      {
        Utils.logw("MTKLogger/LogReceiver", "Boot up, set " + (String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()) + " to stopped");
        this.mSharedPreferences.edit().putInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0).commit();
      }
      if (this.mSharedPreferences.getBoolean((String)Utils.KEY_NEED_RECOVER_RUNNING_MAP.get(localInteger.intValue()), false)) {
        this.mSharedPreferences.edit().putBoolean((String)Utils.KEY_NEED_RECOVER_RUNNING_MAP.get(localInteger.intValue()), false).commit();
      }
    }
    this.mSharedPreferences.edit().putLong("begin_recording_time", 0L).commit();
    this.mSharedPreferences.edit().putBoolean("hasStartedDebugMode", false).commit();
    this.mSharedPreferences.edit().putInt("system_time_reset", 0).commit();
    this.mSharedPreferences.edit().remove("md_assert_file_path").remove("waiting_sd_ready_reason").commit();
  }
  
  private boolean isAnyLogRunning()
  {
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    Integer localInteger;
    do
    {
      boolean bool1 = localIterator.hasNext();
      bool2 = false;
      if (!bool1) {
        break;
      }
      localInteger = (Integer)localIterator.next();
    } while (1 != this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0));
    boolean bool2 = true;
    Utils.logv("MTKLogger/LogReceiver", "<--isAnyLogRunning()? " + bool2);
    return bool2;
  }
  
  private boolean needStartLogAtBootTime()
  {
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    Integer localInteger;
    do
    {
      boolean bool1 = localIterator.hasNext();
      bool2 = false;
      if (!bool1) {
        break;
      }
      localInteger = (Integer)localIterator.next();
    } while (true != this.mDefaultSharedPreferences.getBoolean((String)Utils.KEY_START_AUTOMATIC_MAP.get(localInteger.intValue()), ((Boolean)Utils.DEFAULT_CONFIG_LOG_AUTO_START_MAP.get(localInteger.intValue())).booleanValue()));
    boolean bool2 = true;
    Utils.logd("MTKLogger/LogReceiver", "-->needStartLogAtBootTime(), needStart=" + bool2);
    return bool2;
  }
  
  private void reportDb2ExceptionReporter(Context paramContext, String paramString)
  {
    Utils.logd("MTKLogger/LogReceiver", "-->reportDb2ExceptionReporter(), dbPathStr=" + paramString);
    Toast.makeText(paramContext, "Detect a reboot exception:[" + paramString + "]", 0).show();
    String str1 = paramString.substring(paramString.indexOf("db."));
    String str2 = paramString.substring(1 + paramString.indexOf(":")) + File.separator;
    String str3 = str1 + ".dbg";
    Intent localIntent = new Intent("com.mediatek.mtklogger.MTKLoggerService");
    localIntent.putExtra("startup_type", "exception_happen");
    localIntent.putExtra("from_reboot", "FROM_REBOOT");
    localIntent.putExtra("path", str2);
    localIntent.putExtra("db_filename", str3);
    localIntent.putExtra("zz_filename", "ZZ_INTERNAL");
    Utils.logd("MTKLogger/LogReceiver", "Report reboot exception to Exception Reporter module, path=" + str2 + ", dbName=" + str3 + ", zzName=" + "ZZ_INTERNAL");
    paramContext.startService(localIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = paramIntent.getAction();
    Utils.logi("MTKLogger/LogReceiver", " -->onReceive(), action=" + str1);
    this.mSharedPreferences = paramContext.getSharedPreferences("log_settings", 0);
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    Intent localIntent1;
    if (("android.intent.action.BOOT_COMPLETED".equals(str1)) || ("android.intent.action.ACTION_BOOT_IPO".equals(str1)))
    {
      new LogConfig(paramContext).checkConfig();
      if (needStartLogAtBootTime())
      {
        if (bootupFlag)
        {
          Utils.logi("MTKLogger/LogReceiver", "Service already be started by boot event, ignore this event.");
          return;
        }
        bootupFlag = true;
        initLogStatus();
        localIntent1 = new Intent("com.mediatek.mtklogger.MTKLoggerService");
        if ("android.intent.action.BOOT_COMPLETED".equals(str1))
        {
          localIntent1.putExtra("startup_type", "boot");
          mKillProcessCmdHandler.removeMessages(1);
          paramContext.startService(localIntent1);
          label159:
          if ("android.intent.action.BOOT_COMPLETED".equals(str1)) {
            checkRebootIssue(paramContext);
          }
        }
      }
    }
    for (;;)
    {
      Utils.logv("MTKLogger/LogReceiver", " OnReceive function exit.");
      return;
      if (!"android.intent.action.ACTION_BOOT_IPO".equals(str1)) {
        break;
      }
      localIntent1.putExtra("startup_type", "ipo");
      break;
      initLogStatus();
      Utils.logi("MTKLogger/LogReceiver", "Do not need to start any log at boot time, send out self kill message.");
      mKillProcessCmdHandler.removeMessages(1);
      mKillProcessCmdHandler.sendMessageDelayed(mKillProcessCmdHandler.obtainMessage(1), 10000L);
      break label159;
      if ("android.intent.action.ACTION_SHUTDOWN_IPO".equals(str1))
      {
        Utils.logd("MTKLogger/LogReceiver", "Receive IPO shutdown event in LogReceiver, notify to service for safety, since service may already been killed by system.");
        MTKLoggerService.isInIPOShutdown = true;
      }
      else if ("com.mediatek.mtklogger.ADB_CMD".equals(str1))
      {
        Intent localIntent2 = new Intent("com.mediatek.mtklogger.MTKLoggerService");
        int i = paramIntent.getIntExtra("cmd_target", 0);
        String str2 = paramIntent.getStringExtra("cmd_name");
        Utils.logd("MTKLogger/LogReceiver", "Receive adb command, logCluster=" + i + ", command=" + str2);
        if ((this.mDefaultSharedPreferences != null) && ("1".equals(this.mDefaultSharedPreferences.getString("log_mode", "2"))) && ("force_modem_assert".equals(str2)))
        {
          Utils.logw("MTKLogger/LogReceiver", "In USB mode, force modem assert command is not supported");
          checkProcess();
          return;
        }
        if ("switch_taglog".equals(str2))
        {
          int j = paramIntent.getIntExtra("cmd_target", -1);
          Utils.logd("MTKLogger/LogReceiver", "Receive a Taglog configuration broadcast, target value=" + j);
          if (1 == j) {
            this.mSharedPreferences.edit().putBoolean("tagLogEnable", true).commit();
          }
          for (;;)
          {
            checkProcess();
            return;
            if (j == 0) {
              this.mSharedPreferences.edit().putBoolean("tagLogEnable", false).commit();
            } else {
              Utils.logw("MTKLogger/LogReceiver", "Configure taglog value invalid: " + j);
            }
          }
        }
        localIntent2.putExtra("startup_type", "adb");
        localIntent2.putExtra("cmd_target", i);
        localIntent2.putExtra("cmd_name", str2);
        mKillProcessCmdHandler.removeMessages(1);
        paramContext.startService(localIntent2);
      }
      else if ("com.mediatek.mdlogger.AUTOSTART_COMPLETE".equals(str1))
      {
        if (bootupFlag)
        {
          Intent localIntent4 = new Intent("com.mediatek.mtklogger.MTKLoggerService");
          localIntent4.putExtra("startup_type", "update");
          mKillProcessCmdHandler.removeMessages(1);
          paramContext.startService(localIntent4);
        }
        else
        {
          Utils.logw("MTKLogger/LogReceiver", "Modem reset come before boot completed(IPO), just ignore this event, and send out self kill message.");
          mKillProcessCmdHandler.removeMessages(1);
          mKillProcessCmdHandler.sendMessageDelayed(mKillProcessCmdHandler.obtainMessage(1), 10000L);
        }
      }
      else if ("android.intent.action.DOWNLOAD_CALIBRATION_DATA".equals(str1))
      {
        boolean bool2 = paramIntent.getBooleanExtra("calibrationData", false);
        Utils.logd("MTKLogger/LogReceiver", "Get calibration info from modem. Calibrated?" + bool2);
        this.mSharedPreferences.edit().putBoolean("calibrationData", bool2).commit();
        if (!needStartLogAtBootTime())
        {
          Utils.logi("MTKLogger/LogReceiver", "Do not need to start any log when get calibration data, send out self kill message.");
          mKillProcessCmdHandler.removeMessages(1);
          mKillProcessCmdHandler.sendMessageDelayed(mKillProcessCmdHandler.obtainMessage(1), 10000L);
        }
      }
      else if ("com.mediatek.log2server.EXCEPTION_HAPPEND".equals(str1))
      {
        String str3 = Build.TYPE;
        boolean bool1 = false;
        if (str3 != null)
        {
          Utils.logd("MTKLogger/LogReceiver", "Build type :" + str3);
          if (!str3.trim().equalsIgnoreCase("user")) {
            break label960;
          }
        }
        Intent localIntent3;
        Bundle localBundle;
        label960:
        for (bool1 = this.mSharedPreferences.getBoolean("tagLogEnable", false);; bool1 = this.mSharedPreferences.getBoolean("tagLogEnable", true))
        {
          Utils.logd("MTKLogger/LogReceiver", "Receive exception happens broadcast, isTagLogEnabled?" + bool1);
          if (!bool1) {
            break label996;
          }
          localIntent3 = new Intent("com.mediatek.mtklogger.MTKLoggerService");
          localIntent3.putExtra("startup_type", "exception_happen");
          localBundle = paramIntent.getExtras();
          if (localBundle != null) {
            break;
          }
          Utils.loge("MTKLogger/LogReceiver", "--> intent.getExtras() == null");
          checkProcess();
          return;
        }
        localIntent3.putExtras(localBundle);
        paramContext.startService(localIntent3);
        continue;
        label996:
        checkProcess();
      }
      else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(str1))
      {
        Utils.loge("MTKLogger/LogReceiver", "start ExceptionReportManager");
        if (!ExceptionReportManager.getInstance(paramContext).beginException(paramIntent)) {
          Utils.logi("MTKLogger/LogReceiver", "Connectivity status changed, but log2server have nothing to report.");
        } else {
          checkProcess();
        }
      }
      else if ("android.intent.action.TIME_SET".equals(str1))
      {
        handleSystemTimeChanged();
        checkProcess();
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.LogReceiver
 * JD-Core Version:    0.7.0.1
 */