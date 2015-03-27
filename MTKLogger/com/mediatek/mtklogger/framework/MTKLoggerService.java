package com.mediatek.mtklogger.framework;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;
import com.mediatek.mtklogger.IMTKLoggerManager.Stub;
import com.mediatek.mtklogger.taglog.TagLogManager;
import com.mediatek.mtklogger.utils.Utils;
import java.util.Iterator;
import java.util.Set;

public class MTKLoggerService
  extends Service
{
  private static final String TAG = "MTKLogger/MTKLoggerService";
  public static boolean isInIPOShutdown = false;
  private static boolean isStarted = false;
  private static boolean isWaitingSDReady = false;
  private static NotificationManager mNM = null;
  private String failReasonStr = "";
  public IMTKLoggerManager.Stub mBind = new IMTKLoggerManager.Stub()
  {
    public boolean clearLog()
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->clearLog()");
      return true;
    }
    
    public int getCurrentRunningStage()
    {
      return MTKLoggerService.this.getGlobalRunningStage();
    }
    
    public int getLogRunningStatus(int paramAnonymousInt)
    {
      return MTKLoggerService.this.getLogInstanceRunningStatus(paramAnonymousInt);
    }
    
    public boolean runCommand(String paramAnonymousString)
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->runCommand(), command=" + paramAnonymousString);
      LogInstance localLogInstance = MTKLoggerService.this.getLogInstance(4);
      if (localLogInstance == null)
      {
        Utils.loge("MTKLogger/MTKLoggerService", "Fail to get shell command handler.");
        return false;
      }
      if (localLogInstance.mHandler != null)
      {
        localLogInstance.mHandler.obtainMessage(5, paramAnonymousString).sendToTarget();
        return true;
      }
      Utils.loge("MTKLogger/MTKLoggerService", "When runCommand(), fail to get log instance handler of network log.");
      return false;
    }
    
    public boolean setAutoStart(int paramAnonymousInt, boolean paramAnonymousBoolean)
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->setAutoStart(), logType=" + paramAnonymousInt + ", autoStart?" + paramAnonymousBoolean);
      LogInstance localLogInstance = MTKLoggerService.this.getLogInstance(paramAnonymousInt);
      if (localLogInstance == null)
      {
        Utils.loge("MTKLogger/MTKLoggerService", "Fail to get log instance for config auto start value.");
        return false;
      }
      if (localLogInstance.mHandler != null)
      {
        LogInstance.LogHandler localLogHandler = localLogInstance.mHandler;
        StringBuilder localStringBuilder = new StringBuilder().append("autostart=");
        if (paramAnonymousBoolean) {}
        for (String str = "1";; str = "0")
        {
          localLogHandler.obtainMessage(7, str).sendToTarget();
          return true;
        }
      }
      Utils.loge("MTKLogger/MTKLoggerService", "When setAutoStart(), fail to get log instance handler  of log [" + paramAnonymousInt + "].");
      return false;
    }
    
    public boolean setLogSize(int paramAnonymousInt1, int paramAnonymousInt2)
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->setLogSize(), logType=" + paramAnonymousInt1 + ", size=" + paramAnonymousInt2);
      LogInstance localLogInstance = MTKLoggerService.this.getLogInstance(paramAnonymousInt1);
      if (localLogInstance == null)
      {
        Utils.loge("MTKLogger/MTKLoggerService", "Fail to get log instance for config log size.");
        return false;
      }
      if (localLogInstance.mHandler != null)
      {
        localLogInstance.mHandler.obtainMessage(7, "logsize=" + paramAnonymousInt2).sendToTarget();
        return true;
      }
      Utils.loge("MTKLogger/MTKLoggerService", "When setLogSize(), fail to get log instance handler  of log [" + paramAnonymousInt1 + "].");
      return false;
    }
    
    public boolean setSubLogEnableState(int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean)
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->setSubLogEnableState(), logType=" + paramAnonymousInt1 + ", subType=" + paramAnonymousInt2 + ", enable?" + paramAnonymousBoolean);
      LogInstance localLogInstance = MTKLoggerService.this.getLogInstance(paramAnonymousInt1);
      if (localLogInstance == null)
      {
        Utils.loge("MTKLogger/MTKLoggerService", "Fail to get log instance for config sub log enable state.");
        return false;
      }
      if (localLogInstance.mHandler != null)
      {
        LogInstance.LogHandler localLogHandler = localLogInstance.mHandler;
        int i = 0;
        if (paramAnonymousBoolean) {
          i = 1;
        }
        localLogHandler.obtainMessage(7, paramAnonymousInt2, i, "sublog_").sendToTarget();
        return true;
      }
      Utils.loge("MTKLogger/MTKLoggerService", "When setSubLogEnableState(), fail to get log instance handler  of log [" + paramAnonymousInt1 + "].");
      return false;
    }
    
    public boolean startLog(int paramAnonymousInt)
    {
      return MTKLoggerService.this.startRecording(paramAnonymousInt, null);
    }
    
    public boolean stopCommand()
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->stopCommand()");
      LogInstance localLogInstance = MTKLoggerService.this.getLogInstance(4);
      if (localLogInstance == null)
      {
        Utils.loge("MTKLogger/MTKLoggerService", "Fail to get shell command handler.");
        return false;
      }
      if (localLogInstance.mHandler != null)
      {
        localLogInstance.mHandler.obtainMessage(6).sendToTarget();
        return true;
      }
      Utils.loge("MTKLogger/MTKLoggerService", "When stopCommand(), fail to get log instance handler of network log.");
      return false;
    }
    
    public boolean stopLog(int paramAnonymousInt)
    {
      return MTKLoggerService.this.stopRecording(paramAnonymousInt, null);
    }
    
    public boolean tagLog()
    {
      Utils.logd("MTKLogger/MTKLoggerService", "-->tagLog()");
      MTKLoggerService.this.beginTagLog();
      return true;
    }
  };
  private int mCachedStartStopCmd = 0;
  private int mCurrentAffectedLogType = 0;
  private String mCurrentLogPathType = null;
  private String mCurrentRecordingLogPath = null;
  private int mCurrentRunningStatus = 0;
  private SharedPreferences mDefaultSharedPreferences = null;
  private int mGlobalRunningStage = 0;
  private IntentFilter mIPOIntentFilter;
  private BroadcastReceiver mIPOReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      Utils.logd("MTKLogger/MTKLoggerService", " mIPOReceiver intent action: " + str);
      if ("android.intent.action.ACTION_SHUTDOWN_IPO".equals(str))
      {
        Utils.logd("MTKLogger/MTKLoggerService", "Get IPO_SHUTDOWN event!");
        MTKLoggerService.isInIPOShutdown = true;
      }
    }
  };
  private boolean mLogFolderMonitorThreadStopFlag = true;
  private SparseArray<LogInstance> mLogInstanceMap = new SparseArray();
  LogFolderMonitor mMonitorLogFolderThread = null;
  private Handler mNativeStateHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      int i = paramAnonymousMessage.what;
      Utils.logd("MTKLogger/MTKLoggerService", " mStartStopRespHandler receive message, what=" + i + ", arg1=" + paramAnonymousMessage.arg1 + ", arg2=" + paramAnonymousMessage.arg2);
      if (i == 2)
      {
        Utils.logw("MTKLogger/MTKLoggerService", "At boot up/IPO time, waiting SD card time out.");
        MTKLoggerService.this.mSharedPreferences.edit().remove("waiting_sd_ready_reason").commit();
        MTKLoggerService.access$602(false);
        MTKLoggerService.this.changeLogRunningStatus(false, "sd_timeout");
        return;
      }
      if ((i > 1000) || (i == 1))
      {
        MTKLoggerService.this.handleLogStateChangeMsg(this, paramAnonymousMessage);
        return;
      }
      if (i == 3)
      {
        int j = paramAnonymousMessage.arg1;
        MTKLoggerService.this.handleGlobalRunningStageChange(j);
        return;
      }
      Utils.loge("MTKLogger/MTKLoggerService", "Unknown message");
    }
  };
  private IntentFilter mPhoneStorageIntentFilter = null;
  private int mRemainingStorage = 0;
  private IntentFilter mSDStatusIntentFilter = null;
  private String mServiceStartType = null;
  private SharedPreferences mSharedPreferences = null;
  private BroadcastReceiver mStorageStatusReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      Utils.logd("MTKLogger/MTKLoggerService", "Storage status changed, action=" + str1 + ", current logPathType=" + MTKLoggerService.this.mCurrentLogPathType);
      if (("/mnt/sdcard2".equals(MTKLoggerService.this.mCurrentLogPathType)) || ("/mnt/sdcard".equals(MTKLoggerService.this.mCurrentLogPathType)))
      {
        localUri = paramAnonymousIntent.getData();
        str2 = null;
        if (localUri != null) {
          str2 = localUri.getPath();
        }
        str3 = Utils.getCurrentLogPath(MTKLoggerService.this);
        bool = MTKLoggerService.this.isAnyLogRunning();
        Utils.logd("MTKLogger/MTKLoggerService", "AffectedPath=" + str2 + ", new got currentLogPath=" + str3 + ", isAnyLogRuning?" + bool + ", cached recording log path=" + MTKLoggerService.this.mCurrentRecordingLogPath);
        if ((bool) && (MTKLoggerService.this.mCurrentRecordingLogPath != null)) {
          str3 = MTKLoggerService.this.mCurrentRecordingLogPath;
        }
        if ((str2 == null) || (!str2.equals(str3))) {
          Utils.logi("MTKLogger/MTKLoggerService", "SD card change was not happend in current log path, ignore.");
        }
      }
      while (!"android.intent.action.DEVICE_STORAGE_LOW".equals(str1))
      {
        Uri localUri;
        String str2;
        String str3;
        boolean bool;
        return;
        if (("android.intent.action.MEDIA_BAD_REMOVAL".equals(str1)) || ("android.intent.action.MEDIA_EJECT".equals(str1)) || ("android.intent.action.MEDIA_REMOVED".equals(str1)) || ("android.intent.action.MEDIA_UNMOUNTED".equals(str1)))
        {
          if (MTKLoggerService.isInIPOShutdown)
          {
            Utils.logi("MTKLogger/MTKLoggerService", "Device is in IPO shut down phase, mark reason as ipo_shutdown.");
            MTKLoggerService.this.changeLogRunningStatus(false, "ipo_shutdown");
            Utils.logi("MTKLogger/MTKLoggerService", "Send ipo_shutdown to native, stop service now.");
            MTKLoggerService.this.stopSelf();
            return;
          }
          MTKLoggerService.this.changeLogRunningStatus(false, "storage_full_or_lost");
          return;
        }
        if ("android.intent.action.MEDIA_MOUNTED".equals(str1))
        {
          MTKLoggerService.this.mNativeStateHandler.removeMessages(2);
          String str4 = MTKLoggerService.this.mSharedPreferences.getString("waiting_sd_ready_reason", "");
          MTKLoggerService.this.mSharedPreferences.edit().remove("waiting_sd_ready_reason").commit();
          Utils.logv("MTKLogger/MTKLoggerService", "Got storage mounted event, isWaitingSDReady=" + MTKLoggerService.isWaitingSDReady + ", waitSDStatuStr=" + str4);
          if (MTKLoggerService.isWaitingSDReady)
          {
            MTKLoggerService.access$602(false);
            Utils.logd("MTKLogger/MTKLoggerService", "Got storage mounted event, cached waitSDStatuStr=" + str4);
            MTKLoggerService.this.changeLogRunningStatus(true, str4);
            return;
          }
          MTKLoggerService.this.changeLogRunningStatus(true, "storage_recovery");
          return;
        }
        Utils.loge("MTKLogger/MTKLoggerService", "Unsupported broadcast action for SD card. action=" + str1);
        return;
      }
      Utils.logw("MTKLogger/MTKLoggerService", "Phone storage is low now. What should I do? ");
      MTKLoggerService.this.changeLogRunningStatus(false, "storage_full_or_lost");
    }
  };
  private BroadcastReceiver mUserSwitchReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      int i = paramAnonymousIntent.getIntExtra("android.intent.extra.user_handle", -1);
      Utils.loge("MTKLogger/MTKLoggerService", "Monitor a user switch event. New user id = " + i);
      if (i == Utils.USER_ID)
      {
        Utils.logi("MTKLogger/MTKLoggerService", "Switch current user to fore ground, sync info with native now");
        MTKLoggerService.this.updateLogStatus();
        return;
      }
      Utils.logi("MTKLogger/MTKLoggerService", "Current user is set to background, just ignore and let me free some time, thank you.");
    }
  };
  
  private boolean beginTagLog()
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->beginTagLog()");
    Intent localIntent = new Intent();
    localIntent.putExtra("path", "SaveLogManually");
    TagLogManager.getInstance(this).beginTag(localIntent);
    return true;
  }
  
  private void changeLogRunningStatus(boolean paramBoolean, String paramString)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->changeLogRunningStatus(), enable?" + paramBoolean + ", reason=[" + paramString + "]");
    if (this.mSharedPreferences == null) {
      Utils.loge("MTKLogger/MTKLoggerService", "SharedPreference instance is null");
    }
    int i;
    label439:
    label572:
    label576:
    do
    {
      do
      {
        return;
        i = 0;
        if (!paramBoolean) {
          break;
        }
        Iterator localIterator2;
        if (("boot".equals(paramString)) || ("ipo".equals(paramString))) {
          localIterator2 = Utils.LOG_TYPE_SET.iterator();
        }
        while (localIterator2.hasNext())
        {
          Integer localInteger2 = (Integer)localIterator2.next();
          if ((true == this.mDefaultSharedPreferences.getBoolean((String)Utils.KEY_START_AUTOMATIC_MAP.get(localInteger2.intValue()), ((Boolean)Utils.DEFAULT_CONFIG_LOG_AUTO_START_MAP.get(localInteger2.intValue())).booleanValue())) && (this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger2.intValue()), 0) == 0))
          {
            i += localInteger2.intValue();
            continue;
            boolean bool = "storage_recovery".equals(paramString);
            i = 0;
            if (bool)
            {
              Iterator localIterator3 = Utils.LOG_TYPE_SET.iterator();
              while (localIterator3.hasNext())
              {
                Integer localInteger3 = (Integer)localIterator3.next();
                if ((this.mSharedPreferences.getBoolean((String)Utils.KEY_NEED_RECOVER_RUNNING_MAP.get(localInteger3.intValue()), false)) && (this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger3.intValue()), 0) == 0))
                {
                  i += localInteger3.intValue();
                  this.mSharedPreferences.edit().putBoolean((String)Utils.KEY_NEED_RECOVER_RUNNING_MAP.get(localInteger3.intValue()), false).apply();
                }
              }
            }
          }
        }
        Utils.logv("MTKLogger/MTKLoggerService", " affectedLog=" + i);
      } while (i <= 0);
      startRecording(i, paramString);
      return;
      Iterator localIterator1 = Utils.LOG_TYPE_SET.iterator();
      while (localIterator1.hasNext())
      {
        Integer localInteger1 = (Integer)localIterator1.next();
        int j;
        if (1 == this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger1.intValue()), 0))
        {
          j = 1;
          if (true != this.mDefaultSharedPreferences.getBoolean((String)Utils.KEY_START_AUTOMATIC_MAP.get(localInteger1.intValue()), ((Boolean)Utils.DEFAULT_CONFIG_LOG_AUTO_START_MAP.get(localInteger1.intValue())).booleanValue())) {
            break label572;
          }
        }
        for (int k = 1;; k = 0)
        {
          if ((j == 0) && ((k == 0) || (!"sd_timeout".equals(paramString)))) {
            break label576;
          }
          i += localInteger1.intValue();
          if ((j == 0) || (!"storage_full_or_lost".equals(paramString))) {
            break;
          }
          this.mSharedPreferences.edit().putBoolean((String)Utils.KEY_NEED_RECOVER_RUNNING_MAP.get(localInteger1.intValue()), true).apply();
          break;
          j = 0;
          break label439;
        }
      }
      Utils.logv("MTKLogger/MTKLoggerService", " affectedLog=" + i);
    } while (i <= 0);
    stopRecording(i, paramString);
  }
  
  private void checkRemainingStorage()
  {
    int i = Utils.getAvailableStorageSize(Utils.getCurrentLogPath(this));
    if ((i < 30) && ((this.mRemainingStorage == 0) || (this.mRemainingStorage >= 30)))
    {
      Utils.logi("MTKLogger/MTKLoggerService", "Remaining log storage drop below water level, give a notification now");
      if (mNM == null) {
        mNM = (NotificationManager)getSystemService("notification");
      }
      Utils.logd("MTKLogger/MTKLoggerService", "Log storage drop down below water level, give out a notification");
      Notification localNotification = new Notification();
      localNotification.icon = 2130837510;
      localNotification.tickerText = getText(2131165193);
      Intent localIntent = new Intent();
      localIntent.setComponent(new ComponentName("com.mediatek.mtklogger", "com.mediatek.mtklogger.MainActivity"));
      localIntent.setFlags(536870912);
      localIntent.putExtra("reason_start", "low_storage");
      PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, localIntent, 0);
      localNotification.setLatestEventInfo(this, getText(2131165193), getText(2131165194), localPendingIntent);
      mNM.notify(2130837510, localNotification);
    }
    for (;;)
    {
      this.mRemainingStorage = i;
      return;
      if ((this.mRemainingStorage > 0) && (this.mRemainingStorage < 30) && (i >= 30))
      {
        if (mNM == null) {
          mNM = (NotificationManager)getSystemService("notification");
        }
        Utils.logd("MTKLogger/MTKLoggerService", "Log storage resume upto water level, clear former notification");
        mNM.cancel(2130837510);
      }
    }
  }
  
  private void dealWithAdbCommand(int paramInt, String paramString)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->dealWithAdbCommand(), logTypeCluster=" + paramInt + ", command=" + paramString);
    if ("start".equals(paramString)) {
      startRecording(paramInt, "adb");
    }
    for (;;)
    {
      return;
      if ("stop".equals(paramString))
      {
        stopRecording(paramInt, paramString);
        return;
      }
      Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        if ((i & paramInt) != 0)
        {
          LogInstance localLogInstance = getLogInstance(i);
          if (localLogInstance != null)
          {
            Utils.logd("MTKLogger/MTKLoggerService", "Send adb command [" + paramString + "] to log " + i);
            LogInstance.LogHandler localLogHandler = localLogInstance.mHandler;
            if (localLogHandler != null) {
              localLogHandler.obtainMessage(8, paramString).sendToTarget();
            } else {
              Utils.loge("MTKLogger/MTKLoggerService", "When dealWithAdbCommand(), fail to get log instance handler of log [" + i + "]");
            }
          }
          else
          {
            Utils.loge("MTKLogger/MTKLoggerService", "Fail to get log instance(" + i + ") when dealing with adb command");
          }
        }
      }
    }
  }
  
  private int getGlobalRunningStage()
  {
    int i = this.mGlobalRunningStage;
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      LogInstance localLogInstance = getLogInstance(((Integer)localIterator.next()).intValue());
      if (localLogInstance != null)
      {
        int j = localLogInstance.getGlobalRunningStage();
        if (j > i) {
          i = j;
        }
      }
    }
    Utils.logd("MTKLogger/MTKLoggerService", "<--getGlobalRunningStage(), current stage=" + i);
    return i;
  }
  
  private LogInstance getLogInstance(int paramInt)
  {
    if (this.mLogInstanceMap.indexOfKey(paramInt) < 0)
    {
      LogInstance localLogInstance = LogInstance.getInstance(paramInt, this, this.mNativeStateHandler);
      if (localLogInstance != null) {
        this.mLogInstanceMap.put(paramInt, localLogInstance);
      }
    }
    return (LogInstance)this.mLogInstanceMap.get(paramInt);
  }
  
  private void handleGlobalRunningStageChange(int paramInt)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->handleGlobalRunningStageChange(), stageEvent=" + paramInt + ", 1:start; 2:stop; 3:polling; 4:polling done.");
    new Intent("").putExtra("stage_event", paramInt);
  }
  
  private void handleLogStateChangeMsg(Handler paramHandler, Message paramMessage)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->handleLogStateChangeMsg(), mCachedStartStopCmd = " + this.mCachedStartStopCmd);
    int i = paramMessage.what;
    if (i > 1000)
    {
      int m = i - 1000;
      if ((m & this.mCachedStartStopCmd) != 0)
      {
        this.mCachedStartStopCmd = (m ^ this.mCachedStartStopCmd);
        this.failReasonStr = (this.failReasonStr + m + ":" + "9" + ";");
        if (this.mCachedStartStopCmd == 0)
        {
          Utils.logi("MTKLogger/MTKLoggerService", " Notify other modules current state with broadcast. mCurrentAffectedLogType=" + this.mCurrentAffectedLogType + ", currentRunningStatus=" + this.mCurrentRunningStatus + ", failReasonStr=[" + this.failReasonStr + "]");
          Intent localIntent = new Intent("com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED");
          localIntent.putExtra("affected_log_type", this.mCurrentAffectedLogType);
          localIntent.putExtra("log_new_state", this.mCurrentRunningStatus);
          if (!TextUtils.isEmpty(this.failReasonStr)) {
            localIntent.putExtra("fail_reason", this.failReasonStr);
          }
          sendBroadcast(localIntent);
          updateStartRecordingTime();
          updateLogFolderMonitor();
          if (!isAnyLogRunning()) {
            break label553;
          }
        }
      }
    }
    label520:
    label553:
    for (this.mCurrentRecordingLogPath = Utils.getCurrentLogPath(this);; this.mCurrentRecordingLogPath = null)
    {
      this.mCurrentAffectedLogType = 0;
      this.mCurrentRunningStatus = 0;
      this.failReasonStr = "";
      this.mGlobalRunningStage = 0;
      return;
      Utils.logw("MTKLogger/MTKLoggerService", "Attention: timeout message should have been removed.");
      break;
      if (i == 1)
      {
        int j = paramMessage.arg1;
        int k = paramMessage.arg2;
        Object localObject = paramMessage.obj;
        Utils.logd("MTKLogger/MTKLoggerService", " MSG_LOG_STATE_CHANGED, logType=" + j + ", result=" + k + ", reason=[" + localObject + "]");
        if ((localObject != null) && (!"".equals((String)localObject))) {
          this.failReasonStr = (this.failReasonStr + j + ":" + (String)localObject + ";");
        }
        if ((j & this.mCachedStartStopCmd) != 0)
        {
          Utils.logd("MTKLogger/MTKLoggerService", "Still waiting start/stop command response, mark it as finished.");
          this.mCachedStartStopCmd = (j ^ this.mCachedStartStopCmd);
          paramHandler.removeMessages(j + 1000);
        }
        for (;;)
        {
          if (k <= 0) {
            break label520;
          }
          this.mCurrentRunningStatus = (j | this.mCurrentRunningStatus);
          break;
          Utils.logw("MTKLogger/MTKLoggerService", "No cached start/stop command for this log, treat it as self change");
          this.mCurrentAffectedLogType = (j | this.mCurrentAffectedLogType);
        }
        if ((j & this.mCurrentRunningStatus) == 0) {
          break;
        }
        this.mCurrentRunningStatus = (j ^ this.mCurrentRunningStatus);
        break;
      }
      Utils.loge("MTKLogger/MTKLoggerService", "Unknown message");
      return;
    }
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
    Utils.logv("MTKLogger/MTKLoggerService", "<--isAnyLogRunning()? " + bool2);
    return bool2;
  }
  
  private boolean isStorageReady()
  {
    if ("/data".equals(this.mCurrentLogPathType)) {
      Utils.logv("MTKLogger/MTKLoggerService", "For phone internal storage, assume it's already ready");
    }
    while ("mounted".equals(Utils.getCurrentVolumeState(this))) {
      return true;
    }
    return false;
  }
  
  private void updateLogFolderMonitor()
  {
    boolean bool = isAnyLogRunning();
    Utils.logd("MTKLogger/MTKLoggerService", "-->updateLogFolderMonitor(), isLogRunning=" + bool + ", mLogFolderMonitorThreadStopFlag=" + this.mLogFolderMonitorThreadStopFlag);
    if ((bool) && (this.mLogFolderMonitorThreadStopFlag))
    {
      this.mMonitorLogFolderThread = new LogFolderMonitor();
      this.mMonitorLogFolderThread.start();
      this.mLogFolderMonitorThreadStopFlag = false;
      Utils.logv("MTKLogger/MTKLoggerService", "Log is running, so start monitor log folder");
    }
    while ((bool) || (this.mLogFolderMonitorThreadStopFlag)) {
      return;
    }
    Utils.logv("MTKLogger/MTKLoggerService", "Log is stopped, so need to stop log folder monitor if any exist.");
    this.mLogFolderMonitorThreadStopFlag = true;
    if (this.mMonitorLogFolderThread != null)
    {
      this.mMonitorLogFolderThread.interrupt();
      this.mMonitorLogFolderThread = null;
    }
    if (mNM == null) {
      mNM = (NotificationManager)getSystemService("notification");
    }
    mNM.cancel(2130837510);
    this.mRemainingStorage = 0;
  }
  
  private void updateLogStatus()
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->updateLogStatus()");
    LogInstance localLogInstance1 = new LogInstance(this);
    boolean bool = TextUtils.isEmpty(this.mSharedPreferences.getString("md_assert_file_path", ""));
    int i = 0;
    if (!bool)
    {
      Utils.logw("MTKLogger/MTKLoggerService", " Modem assert file path is not null, need to re-show assert dialog");
      i = 1;
    }
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      String str = SystemProperties.get((String)Utils.KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP.get(localInteger.intValue()), "0");
      Utils.logi("MTKLogger/MTKLoggerService", " Native log(" + localInteger + ") running status=" + str);
      if ("1".equals(str))
      {
        this.mSharedPreferences.edit().putInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 1).commit();
        localLogInstance1.updateStatusBar(localInteger.intValue(), ((Integer)Utils.KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP.get(localInteger.intValue())).intValue(), true);
        LogInstance localLogInstance3 = getLogInstance(localInteger.intValue());
        if (localLogInstance3.mHandler != null) {
          localLogInstance3.mHandler.obtainMessage(9).sendToTarget();
        } else {
          Utils.loge("MTKLogger/MTKLoggerService", "When updateLogStatus(), fail to get log instance handler of log [" + localInteger + "]");
        }
      }
      else
      {
        this.mSharedPreferences.edit().putInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0).commit();
        localLogInstance1.updateStatusBar(localInteger.intValue(), ((Integer)Utils.KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP.get(localInteger.intValue())).intValue(), false);
        if ((localInteger.intValue() == 2) && (i != 0))
        {
          LogInstance localLogInstance2 = getLogInstance(localInteger.intValue());
          if (localLogInstance2.mHandler != null) {
            localLogInstance2.mHandler.obtainMessage(9).sendToTarget();
          } else {
            Utils.loge("MTKLogger/MTKLoggerService", "When try to reconnect to modem log, fail to get log instance handler");
          }
        }
      }
    }
    LogReceiver.bootupFlag = true;
    if (isAnyLogRunning()) {}
    for (this.mCurrentRecordingLogPath = Utils.getCurrentLogPath(this);; this.mCurrentRecordingLogPath = null)
    {
      updateLogFolderMonitor();
      updateStartRecordingTime();
      sendBroadcast(new Intent("com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"));
      return;
    }
  }
  
  private void updateStartRecordingTime()
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->updateStartRecordingTime(), mCurrentRunningStatus=" + this.mCurrentRunningStatus);
    if (this.mCurrentRunningStatus == 0) {
      if (!isAnyLogRunning()) {
        this.mSharedPreferences.edit().putLong("begin_recording_time", 0L).commit();
      }
    }
    while (this.mSharedPreferences.getLong("begin_recording_time", 0L) > 0L) {
      return;
    }
    Utils.logi("MTKLogger/MTKLoggerService", "Former log start time is 0, set to current system time.");
    this.mSharedPreferences.edit().putLong("begin_recording_time", System.currentTimeMillis()).putInt("system_time_reset", 0).commit();
  }
  
  public int getLogInstanceRunningStatus(int paramInt)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->getLogInstanceRunningStatus(), logType=" + paramInt);
    LogInstance localLogInstance = getLogInstance(paramInt);
    int i = -1;
    if (localLogInstance != null) {
      i = localLogInstance.getLogRunningStatus();
    }
    Utils.logd("MTKLogger/MTKLoggerService", "<--getLogInstanceRunningStatus(), status=" + i);
    return i;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    Utils.logi("MTKLogger/MTKLoggerService", "-->onBind()");
    return this.mBind;
  }
  
  public void onCreate()
  {
    Utils.logi("MTKLogger/MTKLoggerService", "-->onCreate()");
    super.onCreate();
    this.mSharedPreferences = getSharedPreferences("log_settings", 0);
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.mSDStatusIntentFilter = new IntentFilter();
    this.mSDStatusIntentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
    this.mSDStatusIntentFilter.addAction("android.intent.action.MEDIA_EJECT");
    this.mSDStatusIntentFilter.addAction("android.intent.action.MEDIA_REMOVED");
    this.mSDStatusIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
    this.mSDStatusIntentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
    this.mSDStatusIntentFilter.addDataScheme("file");
    registerReceiver(this.mStorageStatusReceiver, this.mSDStatusIntentFilter);
    this.mPhoneStorageIntentFilter = new IntentFilter();
    this.mPhoneStorageIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
    this.mPhoneStorageIntentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
    registerReceiver(this.mStorageStatusReceiver, this.mPhoneStorageIntentFilter);
    this.mIPOIntentFilter = new IntentFilter();
    this.mIPOIntentFilter.addAction("android.intent.action.ACTION_SHUTDOWN_IPO");
    registerReceiver(this.mIPOReceiver, this.mIPOIntentFilter);
    this.mCurrentLogPathType = Utils.getLogPathType();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.USER_SWITCHED");
    registerReceiver(this.mUserSwitchReceiver, localIntentFilter);
    LogReceiver.canKillSelf = false;
  }
  
  public void onDestroy()
  {
    Utils.logi("MTKLogger/MTKLoggerService", "-->onDestroy()");
    if ("true".equalsIgnoreCase(SystemProperties.get("ro.monkey", "false")))
    {
      Utils.logi("MTKLogger/MTKLoggerService", "Monkey is running, MTKLoggerService destroy failed!");
      return;
    }
    unregisterReceiver(this.mStorageStatusReceiver);
    unregisterReceiver(this.mIPOReceiver);
    unregisterReceiver(this.mUserSwitchReceiver);
    this.mLogFolderMonitorThreadStopFlag = true;
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->onStartCommand(), isStarted=" + isStarted + ", old mServiceStartType=" + this.mServiceStartType);
    if (paramIntent != null)
    {
      this.mServiceStartType = paramIntent.getStringExtra("startup_type");
      Utils.logd("MTKLogger/MTKLoggerService", " new mServiceStartType=" + this.mServiceStartType);
      if (!isStarted)
      {
        Utils.logi("MTKLogger/MTKLoggerService", "MTKLoggerService.onStartCommand mServiceStartType=" + this.mServiceStartType + ", thread name = " + Thread.currentThread().getName());
        updateLogStatus();
        if ((!"boot".equals(this.mServiceStartType)) && (!"ipo".equals(this.mServiceStartType))) {
          break label405;
        }
        isInIPOShutdown = false;
        String str1 = SystemProperties.get("debug.mtk.aee.db", "");
        if (((TextUtils.isEmpty(str1)) || (!str1.startsWith("2:"))) && (1 == this.mSharedPreferences.getInt("mobilelog_enable", 0))) {
          this.mSharedPreferences.edit().putInt("mobilelog_enable", 0).commit();
        }
        if (!isStorageReady()) {
          break label336;
        }
        isWaitingSDReady = false;
        changeLogRunningStatus(true, this.mServiceStartType);
        label255:
        isStarted = true;
        Utils.logd("MTKLogger/MTKLoggerService", "Service is first started, check whether need to resume TagLog process");
        TagLogManager.getInstance(this).resumeTag();
      }
      if ((isWaitingSDReady) || (!"adb".equals(this.mServiceStartType))) {
        break label537;
      }
      dealWithAdbCommand(paramIntent.getIntExtra("cmd_target", 0), paramIntent.getStringExtra("cmd_name"));
    }
    for (;;)
    {
      return super.onStartCommand(paramIntent, paramInt1, paramInt2);
      Utils.loge("MTKLogger/MTKLoggerService", "intent == null, maybe this service is restarted by system.");
      this.mServiceStartType = null;
      break;
      label336:
      Utils.logi("MTKLogger/MTKLoggerService", "At bootup/IPO time, SD is not ready yet, wait.");
      isWaitingSDReady = true;
      Utils.logw("MTKLogger/MTKLoggerService", "Storage is not ready yet, waiting for mounted signal.");
      this.mNativeStateHandler.sendMessageDelayed(this.mNativeStateHandler.obtainMessage(2), 40000L);
      this.mSharedPreferences.edit().putString("waiting_sd_ready_reason", this.mServiceStartType).commit();
      break label255;
      label405:
      String str2 = this.mSharedPreferences.getString("waiting_sd_ready_reason", "");
      Utils.logd("MTKLogger/MTKLoggerService", "At service first init time(maybe killed by system), waitSDStatuStr=" + str2);
      if (TextUtils.isEmpty(str2)) {
        break label255;
      }
      isWaitingSDReady = true;
      if (isStorageReady())
      {
        Utils.logd("MTKLogger/MTKLoggerService", "At service restarted time, SD is already ready, continue boot flow");
        this.mSharedPreferences.edit().remove("waiting_sd_ready_reason").commit();
        changeLogRunningStatus(true, str2);
        break label255;
      }
      Utils.logw("MTKLogger/MTKLoggerService", "At service restarted time, SD is still not ready, keep waiting");
      this.mNativeStateHandler.sendMessageDelayed(this.mNativeStateHandler.obtainMessage(2), 40000L);
      break label255;
      label537:
      if ("update".equals(this.mServiceStartType))
      {
        Utils.logd("MTKLogger/MTKLoggerService", "Modem restart finished, update log running status now.");
        this.mCurrentRunningStatus = (0x2 | this.mCurrentRunningStatus);
        updateLogStatus();
      }
      else if ("exception_happen".equals(this.mServiceStartType))
      {
        Utils.logd("MTKLogger/MTKLoggerService", "Got exception happens message, begin to tag log now.");
        TagLogManager.getInstance(this).beginTag(paramIntent);
      }
    }
  }
  
  public boolean startRecording(int paramInt, String paramString)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->startRecording(), logTypeCluster=" + paramInt + ", reason=" + paramString);
    boolean bool1 = false;
    Utils.logi("MTKLogger/MTKLoggerService", "MTKLoggerService.startRecording() thread name = " + Thread.currentThread().getName());
    if (this.mCachedStartStopCmd != 0)
    {
      Utils.loge("MTKLogger/MTKLoggerService", "Server is busy dealing former command, wait till it is free please");
      return false;
    }
    if (isWaitingSDReady)
    {
      Utils.loge("MTKLogger/MTKLoggerService", "Server is waiting for SD ready, wait till it is OK please");
      return false;
    }
    this.mCurrentAffectedLogType = paramInt;
    this.mCachedStartStopCmd = paramInt;
    this.mCurrentRunningStatus = 0;
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      if ((i & this.mCachedStartStopCmd) != 0)
      {
        LogInstance localLogInstance = getLogInstance(i);
        if (localLogInstance != null)
        {
          LogInstance.LogHandler localLogHandler = localLogInstance.mHandler;
          if (localLogHandler != null)
          {
            this.mNativeStateHandler.sendMessageDelayed(this.mNativeStateHandler.obtainMessage(i + 1000), 20000L);
            double d = Math.log(i) / Math.log(2.0D);
            Utils.logv("MTKLogger/MTKLoggerService", "When start recording, for log [" + i + "], delay index=" + d);
            boolean bool2 = "storage_recovery".equals(paramString);
            int j = 0;
            if (bool2)
            {
              Utils.logd("MTKLogger/MTKLoggerService", "For storage recovery event, wait more time for its ready");
              j = 100;
            }
            localLogHandler.sendMessageDelayed(localLogHandler.obtainMessage(1, paramString), j + 300 * (int)d);
            bool1 = true;
          }
          else
          {
            Utils.loge("MTKLogger/MTKLoggerService", "When startRecording(), fail to get log instance handler  of log [" + i + "].");
            this.mNativeStateHandler.obtainMessage(1, i, 0, "4").sendToTarget();
          }
        }
        else
        {
          Utils.loge("MTKLogger/MTKLoggerService", "Fail to get log instance of type: " + i);
          this.mNativeStateHandler.obtainMessage(1, i, 0, "6").sendToTarget();
        }
      }
    }
    if (bool1)
    {
      handleGlobalRunningStageChange(1);
      this.mGlobalRunningStage = 1;
    }
    return bool1;
  }
  
  public boolean stopRecording(int paramInt, String paramString)
  {
    Utils.logd("MTKLogger/MTKLoggerService", "-->stopRecording(), logTypeCluster=" + paramInt + ", reason=" + paramString);
    boolean bool = false;
    if (this.mCachedStartStopCmd != 0)
    {
      Utils.loge("MTKLogger/MTKLoggerService", "Server is busy dealing former command, wait till it's free please");
      return false;
    }
    this.mCurrentAffectedLogType = paramInt;
    this.mCachedStartStopCmd = paramInt;
    this.mCurrentRunningStatus = 0;
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      if ((i & this.mCachedStartStopCmd) != 0)
      {
        LogInstance localLogInstance = getLogInstance(i);
        if (localLogInstance != null)
        {
          LogInstance.LogHandler localLogHandler = localLogInstance.mHandler;
          if (localLogHandler != null)
          {
            this.mNativeStateHandler.sendMessageDelayed(this.mNativeStateHandler.obtainMessage(i + 1000), 20000L);
            localLogHandler.obtainMessage(3, paramString).sendToTarget();
            bool = true;
          }
          else
          {
            Utils.loge("MTKLogger/MTKLoggerService", "When stopRecording(), fail to get log instance handler  of log [" + i + "].");
            this.mNativeStateHandler.obtainMessage(1, i, 0, "4").sendToTarget();
          }
        }
        else
        {
          Utils.logw("MTKLogger/MTKLoggerService", "Fail to get log instance of logtype " + i);
          this.mNativeStateHandler.obtainMessage(1, i, 0, "6").sendToTarget();
        }
      }
    }
    if (bool)
    {
      handleGlobalRunningStageChange(2);
      this.mGlobalRunningStage = 2;
    }
    return bool;
  }
  
  class LogFolderMonitor
    extends Thread
  {
    LogFolderMonitor() {}
    
    public void run()
    {
      Utils.logd("MTKLogger/MTKLoggerService", "Begin to monitor log folder status...");
      while (!MTKLoggerService.this.mLogFolderMonitorThreadStopFlag)
      {
        if (MTKLoggerService.this.isStorageReady())
        {
          MTKLoggerService.this.checkRemainingStorage();
          Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
          while (localIterator.hasNext())
          {
            Integer localInteger = (Integer)localIterator.next();
            if (MTKLoggerService.this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0) == 1)
            {
              LogInstance localLogInstance = MTKLoggerService.this.getLogInstance(localInteger.intValue());
              if (localLogInstance != null) {
                localLogInstance.checkLogFolder();
              } else {
                Utils.loge("MTKLogger/MTKLoggerService", "Fail to get log instance when checking log folder.");
              }
            }
          }
        }
        try
        {
          Thread.sleep(60000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          Utils.loge("MTKLogger/MTKLoggerService", "Waiting check log folder been interrupted.", localInterruptedException);
        }
      }
      Utils.logd("MTKLogger/MTKLoggerService", "End monitor log folder status.");
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.MTKLoggerService
 * JD-Core Version:    0.7.0.1
 */