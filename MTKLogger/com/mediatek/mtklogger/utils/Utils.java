package com.mediatek.mtklogger.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Utils
{
  public static final String ACTION_ADB_CMD = "com.mediatek.mtklogger.ADB_CMD";
  public static final String ACTION_EXP_HAPPENED = "com.mediatek.log2server.EXCEPTION_HAPPEND";
  public static final String ACTION_IPO_BOOT = "android.intent.action.ACTION_BOOT_IPO";
  public static final String ACTION_IPO_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN_IPO";
  public static final String ACTION_LOG_STATE_CHANGED = "com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED";
  public static final String ACTION_MDLOGGER_RESTART_DONE = "com.mediatek.mdlogger.AUTOSTART_COMPLETE";
  public static final String ACTION_REMAINING_STORAGE_LOW = "com.mediatek.mdlogger.REMAINING_STORAGE_LOW";
  public static final String ACTION_RUNNING_STAGE_CHANGED = "";
  public static final String ACTION_START_SERVICE = "com.mediatek.mtklogger.MTKLoggerService";
  public static final String ACTION_TAGLOG_TO_LOG2SERVER = "com.mediatek.syslogger.taglog";
  public static final String ADB_COMMAND_START = "start";
  public static final String ADB_COMMAND_STOP = "stop";
  public static final String ADB_COMMAND_SWITCH_TAGLOG = "switch_taglog";
  public static final String AEE_EXP_PATH = "aee_exp";
  public static final float ANDROID_VERSION_NUMBER = 0.0F;
  public static final String BROADCAST_KEY_MDLOG_PATH = "ModemLogPath";
  public static final String BROADCAST_KEY_MOBILELOG_PATH = "MobileLogPath";
  public static final String BROADCAST_KEY_NETLOG_PATH = "NetLogPath";
  public static final String BROADCAST_KEY_TAGLOG_RESULT = "TaglogResult";
  public static final String BROADCAST_VAL_LOGTOOL_STOPPED = "LogToolStopped";
  public static final String BROADCAST_VAL_TAGLOG_CANCEL = "Cancel";
  public static final String BROADCAST_VAL_TAGLOG_FAILED = "Failed";
  public static final String BROADCAST_VAL_TAGLOG_SUCCESS = "Successful";
  public static final int CHECK_CMD_DURATION = 10000;
  public static final String CONFIG_FILE_NAME = "log_settings";
  public static final String CUSTOMIZE_CONFIG_FILE = "/system/etc/mtklog-config.prop";
  public static final int DEFAULT_ADB_CMD_TARGET = 0;
  public static final SparseArray<Boolean> DEFAULT_CONFIG_LOG_AUTO_START_MAP;
  public static final SparseArray<Integer> DEFAULT_CONFIG_LOG_SIZE_MAP;
  public static final String DEFAULT_LOG_PATH_TYPE = "/mnt/sdcard";
  public static final int DEFAULT_LOG_SIZE = 200;
  public static final int DEFAULT_STORAGE_WATER_LEVEL = 30;
  public static final boolean DEFAULT_VALUE_NEED_RECOVER_RUNNING = false;
  public static final int DEFAULT_VALUE_SYSTME_TIME_CHANGE = 0;
  public static final String DUAL_MODEM_LOG_PATH = "dualmdlog";
  public static final int DURATION_CHECK_LOG_FOLDER = 60000;
  public static final int DURATION_START_EACH_LOG = 300;
  public static final int DURATION_WAIT_LOG_INSTANCE_READY = 500;
  public static final String EXTRA_ADB_CMD_NAME = "cmd_name";
  public static final String EXTRA_ADB_CMD_TARGET = "cmd_target";
  public static final String EXTRA_AFFECTED_LOG_TYPE = "affected_log_type";
  public static final String EXTRA_FAIL_REASON = "fail_reason";
  public static final String EXTRA_KEY_EXP_FROM_REBOOT = "from_reboot";
  public static final String EXTRA_KEY_EXP_NAME = "db_filename";
  public static final String EXTRA_KEY_EXP_PATH = "path";
  public static final String EXTRA_KEY_EXP_ZZ = "zz_filename";
  public static final String EXTRA_KEY_FROM_MAIN_ACTIVITY = "extra_key_from_main_activity";
  public static final String EXTRA_KEY_FROM_TAGLOG = "extra_key_from_taglog";
  public static final String EXTRA_LOG_NEW_STATE = "log_new_state";
  public static final String EXTRA_NEW_USER_ID = "android.intent.extra.user_handle";
  public static final String EXTRA_REMAINING_STORAGE = "remaining_storage";
  public static final String EXTRA_RUNNING_STAGE_CHANGE_EVENT = "stage_event";
  public static final String EXTRA_SERVICE_STARTUP_TYPE = "startup_type";
  public static final String EXTRA_VALUE_FROM_REBOOT = "FROM_REBOOT";
  public static final Map<String, Integer> FAIL_REASON_DETAIL_MAP;
  public static final String FILTER_FILE = "catcher_filter.bin";
  public static final String FLAG_REBOOT_ISSUE_SYSTEM_CARSH = "2:";
  public static final String KEY_BEGIN_RECORDING_TIME = "begin_recording_time";
  public static final SparseArray<String> KEY_CONFIG_LOG_AUTO_START_MAP;
  public static final SparseArray<String> KEY_CONFIG_LOG_SIZE_MAP;
  public static final String KEY_CONFIG_TAGLOG_ENABLED = "com.mediatek.log.taglog.enabled";
  public static final String KEY_LOG2SERVER_SWITCH = "log2server_dialog_show";
  public static final SparseArray<String> KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP;
  public static final SparseArray<String> KEY_LOG_SIZE_MAP;
  public static final String KEY_LOG_SIZE_NETWORK = "networklog_logsize";
  public static final SparseArray<Integer> KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP;
  public static final String KEY_MODEM_ASSERT_FILE_PATH = "md_assert_file_path";
  public static final String KEY_MODEM_EXCEPTION_PATH = "modem_exception_path";
  public static final String KEY_MODEM_MODE = "log_mode";
  public static final SparseArray<String> KEY_NEED_RECOVER_RUNNING_MAP;
  public static final String KEY_REBOOT_EXCEPTION_DB = "debug.mtk.aee.db";
  public static final SparseArray<String> KEY_START_AUTOMATIC_MAP;
  public static final String KEY_START_AUTOMATIC_MOBILE = "mobilelog_autostart";
  public static final String KEY_START_AUTOMATIC_MODEM = "modemlog_autostart";
  public static final String KEY_START_AUTOMATIC_NETWORK = "networklog_autostart";
  public static final String KEY_START_UI_REASON = "reason_start";
  public static final SparseArray<String> KEY_STATUS_MAP;
  public static final String KEY_STATUS_MOBILE = "mobilelog_enable";
  public static final String KEY_STATUS_MODEM = "modemlog_enable";
  public static final String KEY_STATUS_NETWORK = "networklog_enable";
  public static final String KEY_SYSTEM_PROPERTY_LOG_PATH_TYPE;
  public static final String KEY_SYSTEM_PROPERTY_NETLOG_RUNNING_FLAG;
  public static final String KEY_SYSTEM_PROPERTY_NETLOG_SAVING_PATH;
  public static final String KEY_SYSTME_TIME_CHANGE_FLAG = "system_time_reset";
  public static final String KEY_TAGGING_DB = "tagging_db";
  public static final String KEY_TAGGING_DEST = "tagging_dest";
  public static final SparseArray<String> KEY_TAGGING_LOG_MAP;
  public static final String KEY_TAGGING_MOBILE = "tagging_mobile";
  public static final String KEY_TAGGING_MODEM = "tagging_modem";
  public static final String KEY_TAGGING_NETWORK = "tagging_network";
  public static final String KEY_TAG_LOG_COMPRESSING = "tag_log_compressing";
  public static final String KEY_TAG_LOG_ONGOING = "tag_log_ongoing";
  public static final String KEY_WAITING_SD_READY_REASON = "waiting_sd_ready_reason";
  public static final SparseArray<Integer> LOG_NAME_MAP;
  public static final SparseArray<String> LOG_PATH_MAP;
  public static final String LOG_PATH_TYPE_EXTERNAL_SD = "/mnt/sdcard2";
  public static final String LOG_PATH_TYPE_INTERNAL_SD = "/mnt/sdcard";
  public static final String LOG_PATH_TYPE_PHONE = "/data";
  public static final int LOG_PHONE_STORAGE = 2131165210;
  public static final String LOG_PHONE_STORAGE_CMD = "Log2emmc";
  public static final String LOG_PHONE_STORAGE_KEY = "1";
  public static final int LOG_RUNNING_STATUS_MD1 = 1;
  public static final int LOG_RUNNING_STATUS_MD1_MD2 = 3;
  public static final int LOG_RUNNING_STATUS_MD2 = 2;
  public static final int LOG_RUNNING_STATUS_STOPPED = 0;
  public static final int LOG_RUNNING_STATUS_UNKNOWN = -1;
  public static final int LOG_SD_CARD = 2131165211;
  public static final String LOG_SD_CARD_CMD = "Log2sd";
  public static final String LOG_SD_CARD_KEY = "2";
  public static final int LOG_SIZE_MODE1_SIZE = 1000;
  public static final String LOG_TREE_FILE = "file_tree.txt";
  public static final int LOG_TYPE_MOBILE = 1;
  public static final int LOG_TYPE_MODEM = 2;
  public static final int LOG_TYPE_NETWORK = 4;
  public static final Set<Integer> LOG_TYPE_SET;
  public static final String MANUAL_SAVE_LOG = "SaveLogManually";
  public static final String MOBILE_LOG_PATH = "mobilelog";
  public static final String MODEM_LOG_PATH = "mdlog";
  public static final String MODEM_MODE_IDLE = "0";
  public static final String MODEM_MODE_SD = "2";
  public static final String MODEM_MODE_USB = "1";
  public static final int MSG_CMD_TIMEOUT_BASE = 1000;
  public static final int MSG_LOG_STATE_CHANGED = 1;
  public static final int MSG_RUNNING_STAGE_CHANGE = 3;
  public static final int MSG_SD_TIMEOUT = 2;
  public static final String MTKLOG_PATH = "/mtklog/";
  public static final String NETWORK_LOG_PATH = "netlog";
  public static final String NETWORK_STATE = "state";
  public static final String NETWORK_STATE_MOBILE = "mobile";
  public static final String NETWORK_STATE_NONE = "none";
  public static final String NETWORK_STATE_WIFI = "wifi";
  public static final String PROP_MONKEY = "ro.monkey";
  public static final String REASON_CMD_TIMEOUT = "9";
  public static final String REASON_COMMON = "12";
  public static final String REASON_DAEMON_DIE = "5";
  public static final String REASON_DAEMON_UNKNOWN = "1";
  public static final String REASON_LOG_FOLDER_CREATE_FAIL = "10";
  public static final String REASON_LOG_FOLDER_DELETED = "8";
  public static final String REASON_SEND_CMD_FAIL = "4";
  public static final String REASON_STORAGE_FULL = "3";
  public static final String REASON_STORAGE_NOT_READY = "2";
  public static final String REASON_STORAGE_UNAVAILABLE = "7";
  public static final String REASON_UNSUPPORTED_LOG = "6";
  public static final String REASON_WAIT_SD_TIMEOUT = "11";
  public static final int RESERVED_STORAGE_SIZE = 10;
  public static final int RUNNING_STAGE_IDLE = 0;
  public static final int RUNNING_STAGE_POLLING_LOG = 3;
  public static final int RUNNING_STAGE_STARTING_LOG = 1;
  public static final int RUNNING_STAGE_STOPPING_LOG = 2;
  public static final String SDCARD_SIZE = "sdcardSize";
  public static final int SD_LACK_OF_SPACE = 402;
  public static final int SD_NORMAL = 401;
  public static final int SD_NOT_EXIST = 403;
  public static final int SD_NOT_WRITABLE = 404;
  public static final int SD_TIMEOUT = 40000;
  public static final String SERVICE_SHUTDOWN_TYPE_BAD_STORAGE = "storage_full_or_lost";
  public static final String SERVICE_SHUTDOWN_TYPE_IPO = "ipo_shutdown";
  public static final String SERVICE_SHUTDOWN_TYPE_SD_TIMEOUT = "sd_timeout";
  public static final String SERVICE_STARTUP_TYPE_ADB = "adb";
  public static final String SERVICE_STARTUP_TYPE_BOOT = "boot";
  public static final String SERVICE_STARTUP_TYPE_EXCEPTION_HAPPEN = "exception_happen";
  public static final String SERVICE_STARTUP_TYPE_IPO = "ipo";
  public static final String SERVICE_STARTUP_TYPE_STORAGE_RECOVERY = "storage_recovery";
  public static final String SERVICE_STARTUP_TYPE_UPDATE = "update";
  public static final String SERVICE_STARTUP_TYPE_USER = "user";
  public static final String SETTINGS_HAS_STARTED_DEBUG_MODE = "hasStartedDebugMode";
  public static final String SETTINGS_IS_SWITCH_CHECKED = "isSwitchChecked";
  public static final int STAGE_EVENT_POLLING_DONE = 4;
  public static final int STAGE_EVENT_START_LOG = 1;
  public static final int STAGE_EVENT_START_POLLING = 3;
  public static final int STAGE_EVENT_STOP_LOG = 2;
  public static final String START_CMD_PREFIX = "runshell_command_start_";
  public static final String START_UI_REASON_LOW_STORAGE = "low_storage";
  public static final String STOP_CMD_PREFIX = "runshell_command_stop_";
  public static final int STORAGE_STATE_FULL = -2;
  public static final int STORAGE_STATE_NOT_READY = -1;
  public static final int STORAGE_STATE_OK = 1;
  public static final String TAG = "MTKLogger";
  public static final int TAGLOG_CONFIG_VALUE_DISABLE = 0;
  public static final int TAGLOG_CONFIG_VALUE_ENABLE = 1;
  public static final int TAGLOG_CONFIG_VALUE_INVALID = -1;
  public static final String TAG_ASTL1 = "MDLog_ASTL1";
  public static final String TAG_DAK = "MDLog_DAK";
  public static final String TAG_DMDSPMLT = "MDLog_DMDSPMLT";
  public static final String TAG_L1 = "MDLog_L1";
  public static final String TAG_LOG_ENABLE = "tagLogEnable";
  public static final String TAG_LOG_PATH = "taglog";
  public static final String TAG_MD2GMLT = "MDLog_MD2GMLT";
  public static final String TAG_PS = "MDLog_PS";
  public static final int TIMEOUT_CMD = 20000;
  public static final int USER_ID = 0;
  public static final int USER_ID_OWNER = 0;
  public static final int USER_ID_UNDEFINED = -1;
  public static final long VALUE_BEGIN_RECORDING_TIME_DEFAULT = 0L;
  public static final String VALUE_LOG_RUNNING_STATUS_IN_SYSPROP_OFF = "0";
  public static final String VALUE_LOG_RUNNING_STATUS_IN_SYSPROP_ON = "1";
  public static final boolean VALUE_START_AUTOMATIC_DEFAULT = false;
  public static final boolean VALUE_START_AUTOMATIC_OFF = false;
  public static final boolean VALUE_START_AUTOMATIC_ON = true;
  public static final int VALUE_STATUS_DEFAULT = 0;
  public static final int VALUE_STATUS_RUNNING = 1;
  public static final int VALUE_STATUS_STOPPED = 0;
  public static final int ZZ_INTERNAL_LENGTH = 10;
  private static StorageManager mStorageManager;
  
  static
  {
    str = Build.VERSION.RELEASE;
    logi("MTKLogger/Utils", "Andriod version string = " + str);
    int i = str.indexOf(".");
    if (i > 0)
    {
      int j = str.indexOf(".", i + 1);
      if (j > 0)
      {
        str = str.substring(0, j);
        logi("MTKLogger/Utils", "Formated version number = " + str);
      }
    }
    try
    {
      float f2 = Float.parseFloat(str);
      f1 = f2;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        float f1;
        logw("MTKLogger/Utils", "Fail to parse version number directly, just use the first character");
        if (str.startsWith("4."))
        {
          f1 = 4.0F;
        }
        else
        {
          f1 = 2.3F;
          continue;
          KEY_SYSTEM_PROPERTY_LOG_PATH_TYPE = "persist.radio.log2sd.path";
          KEY_SYSTEM_PROPERTY_NETLOG_SAVING_PATH = "persist.radio.writtingpath";
          KEY_SYSTEM_PROPERTY_NETLOG_RUNNING_FLAG = "persist.radio.netlog.Running";
        }
      }
    }
    ANDROID_VERSION_NUMBER = f1;
    logi("MTKLogger/Utils", "Android version number=" + ANDROID_VERSION_NUMBER);
    if (ANDROID_VERSION_NUMBER >= 4.199D)
    {
      KEY_SYSTEM_PROPERTY_LOG_PATH_TYPE = "persist.mtklog.log2sd.path";
      KEY_SYSTEM_PROPERTY_NETLOG_SAVING_PATH = "debug.netlog.writtingpath";
      KEY_SYSTEM_PROPERTY_NETLOG_RUNNING_FLAG = "debug.mtklog.netlog.Running";
      LOG_TYPE_SET = new HashSet();
      LOG_TYPE_SET.add(Integer.valueOf(1));
      LOG_TYPE_SET.add(Integer.valueOf(2));
      LOG_TYPE_SET.add(Integer.valueOf(4));
      LOG_NAME_MAP = new SparseArray();
      LOG_NAME_MAP.put(1, Integer.valueOf(2131165196));
      LOG_NAME_MAP.put(2, Integer.valueOf(2131165197));
      LOG_NAME_MAP.put(4, Integer.valueOf(2131165195));
      KEY_STATUS_MAP = new SparseArray();
      KEY_STATUS_MAP.put(4, "networklog_enable");
      KEY_STATUS_MAP.put(1, "mobilelog_enable");
      KEY_STATUS_MAP.put(2, "modemlog_enable");
      KEY_START_AUTOMATIC_MAP = new SparseArray();
      KEY_START_AUTOMATIC_MAP.put(1, "mobilelog_autostart");
      KEY_START_AUTOMATIC_MAP.put(2, "modemlog_autostart");
      KEY_START_AUTOMATIC_MAP.put(4, "networklog_autostart");
      KEY_LOG_SIZE_MAP = new SparseArray();
      KEY_LOG_SIZE_MAP.put(1, "mobilelog_logsize");
      KEY_LOG_SIZE_MAP.put(2, "modemlog_logsize");
      KEY_LOG_SIZE_MAP.put(4, "networklog_logsize");
      KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP = new SparseArray();
      KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP.put(4, KEY_SYSTEM_PROPERTY_NETLOG_RUNNING_FLAG);
      KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP.put(1, "debug.MB.running");
      KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP.put(2, "debug.mdlogger.Running");
      KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP = new SparseArray();
      KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP.put(4, Integer.valueOf(2131165185));
      KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP.put(1, Integer.valueOf(2131165186));
      KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP.put(2, Integer.valueOf(2131165187));
      KEY_NEED_RECOVER_RUNNING_MAP = new SparseArray();
      KEY_NEED_RECOVER_RUNNING_MAP.put(1, "need_recovery_mobile");
      KEY_NEED_RECOVER_RUNNING_MAP.put(2, "need_recovery_modem");
      KEY_NEED_RECOVER_RUNNING_MAP.put(4, "need_recovery_network");
      LOG_PATH_MAP = new SparseArray();
      LOG_PATH_MAP.put(4, "netlog");
      LOG_PATH_MAP.put(1, "mobilelog");
      LOG_PATH_MAP.put(2, "mdlog");
      FAIL_REASON_DETAIL_MAP = new HashMap();
      FAIL_REASON_DETAIL_MAP.put("1", Integer.valueOf(2131165274));
      FAIL_REASON_DETAIL_MAP.put("2", Integer.valueOf(2131165275));
      FAIL_REASON_DETAIL_MAP.put("3", Integer.valueOf(2131165276));
      FAIL_REASON_DETAIL_MAP.put("4", Integer.valueOf(2131165277));
      FAIL_REASON_DETAIL_MAP.put("5", Integer.valueOf(2131165278));
      FAIL_REASON_DETAIL_MAP.put("6", Integer.valueOf(2131165279));
      FAIL_REASON_DETAIL_MAP.put("7", Integer.valueOf(2131165280));
      FAIL_REASON_DETAIL_MAP.put("8", Integer.valueOf(2131165281));
      FAIL_REASON_DETAIL_MAP.put("9", Integer.valueOf(2131165282));
      FAIL_REASON_DETAIL_MAP.put("10", Integer.valueOf(2131165283));
      FAIL_REASON_DETAIL_MAP.put("11", Integer.valueOf(2131165284));
      FAIL_REASON_DETAIL_MAP.put("12", Integer.valueOf(2131165285));
      KEY_CONFIG_LOG_AUTO_START_MAP = new SparseArray();
      KEY_CONFIG_LOG_AUTO_START_MAP.put(1, "com.mediatek.log.mobile.enabled");
      KEY_CONFIG_LOG_AUTO_START_MAP.put(2, "com.mediatek.log.modem.enabled");
      KEY_CONFIG_LOG_AUTO_START_MAP.put(4, "com.mediatek.log.net.enabled");
      KEY_CONFIG_LOG_SIZE_MAP = new SparseArray();
      KEY_CONFIG_LOG_SIZE_MAP.put(1, "com.mediatek.log.mobile.maxsize");
      KEY_CONFIG_LOG_SIZE_MAP.put(2, "com.mediatek.log.modem.maxsize");
      KEY_CONFIG_LOG_SIZE_MAP.put(4, "com.mediatek.log.net.maxsize");
      DEFAULT_CONFIG_LOG_AUTO_START_MAP = new SparseArray();
      DEFAULT_CONFIG_LOG_AUTO_START_MAP.put(1, Boolean.valueOf(true));
      DEFAULT_CONFIG_LOG_AUTO_START_MAP.put(2, Boolean.valueOf(true));
      DEFAULT_CONFIG_LOG_AUTO_START_MAP.put(4, Boolean.valueOf(true));
      DEFAULT_CONFIG_LOG_SIZE_MAP = new SparseArray();
      DEFAULT_CONFIG_LOG_SIZE_MAP.put(1, Integer.valueOf(300));
      DEFAULT_CONFIG_LOG_SIZE_MAP.put(2, Integer.valueOf(600));
      DEFAULT_CONFIG_LOG_SIZE_MAP.put(4, Integer.valueOf(200));
      KEY_TAGGING_LOG_MAP = new SparseArray();
      KEY_TAGGING_LOG_MAP.put(1, "tagging_mobile");
      KEY_TAGGING_LOG_MAP.put(2, "tagging_modem");
      KEY_TAGGING_LOG_MAP.put(4, "tagging_network");
      USER_ID = UserHandle.myUserId();
      mStorageManager = null;
      return;
    }
  }
  
  public static boolean checkLogStarted(SharedPreferences paramSharedPreferences)
  {
    Iterator localIterator = LOG_TYPE_SET.iterator();
    Integer localInteger;
    do
    {
      boolean bool1 = localIterator.hasNext();
      bool2 = false;
      if (!bool1) {
        break;
      }
      localInteger = (Integer)localIterator.next();
    } while (1 != paramSharedPreferences.getInt((String)KEY_STATUS_MAP.get(localInteger.intValue()), 0));
    boolean bool2 = true;
    return bool2;
  }
  
  public static void deleteFolder(File paramFile)
  {
    File[] arrayOfFile;
    if (paramFile.exists())
    {
      arrayOfFile = paramFile.listFiles();
      if (arrayOfFile != null) {
        break label56;
      }
      paramFile.delete();
      logi("MTKLogger/Utils", "Delete file :" + paramFile.getPath());
    }
    for (;;)
    {
      paramFile.delete();
      return;
      label56:
      for (int i = 0; i < arrayOfFile.length; i++) {
        deleteFolder(arrayOfFile[i]);
      }
    }
  }
  
  public static int getAvailableStorageSize(String paramString)
  {
    int i = 1;
    for (;;)
    {
      if (i <= 3)
      {
        try
        {
          StatFs localStatFs = new StatFs(paramString);
          int j = (int)(localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() / 1048576L);
          Log.d("MTKLogger", "-->getAvailableStorageSize(), path=" + paramString + ", size=" + j + "MB");
          return j;
        }
        catch (Exception localException)
        {
          Log.w("MTKLogger", "Fail to get storage info from [" + paramString + "] by StatFs, try again(index=" + i + ").", localException);
        }
        try
        {
          Thread.sleep(200L);
          i++;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }
    Log.e("MTKLogger", "-->getAvailableStorageSize(), fail to get it by StatFs, unknown exception happen.");
    return 0;
  }
  
  public static String getCurrentLogPath(Context paramContext)
  {
    return getLogPath(paramContext, getLogPathType());
  }
  
  public static String getCurrentVolumeState(Context paramContext)
  {
    if (mStorageManager == null) {
      mStorageManager = (StorageManager)paramContext.getSystemService("storage");
    }
    return getVolumeState(paramContext, getCurrentLogPath(paramContext));
  }
  
  public static long getFileSize(String paramString)
  {
    long l = 0L;
    File localFile1 = new File(paramString);
    if ((localFile1 == null) || (!localFile1.exists())) {
      return 0L;
    }
    if (!localFile1.isDirectory())
    {
      l = localFile1.length();
      return l;
    }
    File[] arrayOfFile = localFile1.listFiles();
    if ((arrayOfFile == null) || (arrayOfFile.length == 0))
    {
      loge("MTKLogger", "Loop folder [" + paramString + "] get a null/empty list");
      return 0L;
    }
    int i = arrayOfFile.length;
    int j = 0;
    label97:
    File localFile2;
    if (j < i)
    {
      localFile2 = arrayOfFile[j];
      if (localFile2 != null) {
        break label122;
      }
    }
    for (;;)
    {
      j++;
      break label97;
      break;
      label122:
      l += getFileSize(localFile2.getAbsolutePath());
    }
  }
  
  public static String getLogPath(Context paramContext, String paramString)
  {
    if (mStorageManager == null) {
      mStorageManager = (StorageManager)paramContext.getSystemService("storage");
    }
    String str;
    if ("/mnt/sdcard".equals(paramString)) {
      str = mStorageManager.getInternalStoragePathForLogger();
    }
    for (;;)
    {
      if (str == null)
      {
        loge("MTKLogger/Utils", "Fail to get detail log path string for type: " + paramString + ", return empty to avoid NullPointerException.");
        str = "";
      }
      return str;
      if ("/mnt/sdcard2".equals(paramString))
      {
        str = mStorageManager.getExternalStoragePath();
      }
      else if ("/data".equals(paramString))
      {
        str = "/data";
      }
      else
      {
        loge("MTKLogger/Utils", "Unsupported log path type: " + paramString);
        str = null;
      }
    }
  }
  
  /* Error */
  public static String getLogPathType()
  {
    // Byte code:
    //   0: getstatic 556	com/mediatek/mtklogger/utils/Utils:KEY_SYSTEM_PROPERTY_LOG_PATH_TYPE	Ljava/lang/String;
    //   3: invokestatic 880	android/os/SystemProperties:get	(Ljava/lang/String;)Ljava/lang/String;
    //   6: astore_0
    //   7: ldc_w 501
    //   10: new 503	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 506	java/lang/StringBuilder:<init>	()V
    //   17: ldc_w 882
    //   20: invokevirtual 512	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: getstatic 556	com/mediatek/mtklogger/utils/Utils:KEY_SYSTEM_PROPERTY_LOG_PATH_TYPE	Ljava/lang/String;
    //   26: invokevirtual 512	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: ldc_w 884
    //   32: invokevirtual 512	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: aload_0
    //   36: invokevirtual 512	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: invokevirtual 516	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   42: invokestatic 887	com/mediatek/mtklogger/utils/Utils:logv	(Ljava/lang/String;Ljava/lang/String;)V
    //   45: aload_0
    //   46: invokestatic 893	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   49: ifeq +48 -> 97
    //   52: new 895	java/util/Properties
    //   55: dup
    //   56: invokespecial 896	java/util/Properties:<init>	()V
    //   59: astore_1
    //   60: aconst_null
    //   61: astore_2
    //   62: new 898	java/io/FileInputStream
    //   65: dup
    //   66: ldc 83
    //   68: invokespecial 899	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   71: astore_3
    //   72: aload_1
    //   73: aload_3
    //   74: invokevirtual 903	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   77: aload_1
    //   78: getstatic 556	com/mediatek/mtklogger/utils/Utils:KEY_SYSTEM_PROPERTY_LOG_PATH_TYPE	Ljava/lang/String;
    //   81: invokevirtual 906	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   84: astore 8
    //   86: aload 8
    //   88: astore_0
    //   89: aload_3
    //   90: ifnull +7 -> 97
    //   93: aload_3
    //   94: invokevirtual 909	java/io/FileInputStream:close	()V
    //   97: aload_0
    //   98: invokestatic 893	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   101: ifeq +6 -> 107
    //   104: ldc 93
    //   106: astore_0
    //   107: aload_0
    //   108: areturn
    //   109: astore 9
    //   111: ldc_w 501
    //   114: ldc_w 911
    //   117: invokestatic 853	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   120: goto -23 -> 97
    //   123: astore 4
    //   125: ldc_w 501
    //   128: new 503	java/lang/StringBuilder
    //   131: dup
    //   132: invokespecial 506	java/lang/StringBuilder:<init>	()V
    //   135: ldc_w 913
    //   138: invokevirtual 512	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: aload 4
    //   143: invokevirtual 914	java/io/IOException:toString	()Ljava/lang/String;
    //   146: invokevirtual 512	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: invokevirtual 516	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   152: invokestatic 853	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   155: aload_2
    //   156: ifnull -59 -> 97
    //   159: aload_2
    //   160: invokevirtual 909	java/io/FileInputStream:close	()V
    //   163: goto -66 -> 97
    //   166: astore 7
    //   168: ldc_w 501
    //   171: ldc_w 911
    //   174: invokestatic 853	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   177: goto -80 -> 97
    //   180: astore 5
    //   182: aload_2
    //   183: ifnull +7 -> 190
    //   186: aload_2
    //   187: invokevirtual 909	java/io/FileInputStream:close	()V
    //   190: aload 5
    //   192: athrow
    //   193: astore 6
    //   195: ldc_w 501
    //   198: ldc_w 911
    //   201: invokestatic 853	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   204: goto -14 -> 190
    //   207: astore 5
    //   209: aload_3
    //   210: astore_2
    //   211: goto -29 -> 182
    //   214: astore 4
    //   216: aload_3
    //   217: astore_2
    //   218: goto -93 -> 125
    // Local variable table:
    //   start	length	slot	name	signature
    //   6	102	0	localObject1	Object
    //   59	19	1	localProperties	java.util.Properties
    //   61	157	2	localObject2	Object
    //   71	146	3	localFileInputStream	java.io.FileInputStream
    //   123	19	4	localIOException1	java.io.IOException
    //   214	1	4	localIOException2	java.io.IOException
    //   180	11	5	localObject3	Object
    //   207	1	5	localObject4	Object
    //   193	1	6	localIOException3	java.io.IOException
    //   166	1	7	localIOException4	java.io.IOException
    //   84	3	8	str	String
    //   109	1	9	localIOException5	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   93	97	109	java/io/IOException
    //   62	72	123	java/io/IOException
    //   159	163	166	java/io/IOException
    //   62	72	180	finally
    //   125	155	180	finally
    //   186	190	193	java/io/IOException
    //   72	86	207	finally
    //   72	86	214	java/io/IOException
  }
  
  public static String getVolumeState(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      logw("MTKLogger/Utils", "Empty pathString when cal getVolumnState");
      return "Unknown";
    }
    try
    {
      Method localMethod2 = StorageManager.class.getDeclaredMethod("getVolumeState", new Class[] { String.class });
      if (localMethod2 != null)
      {
        if (mStorageManager == null) {
          mStorageManager = (StorageManager)paramContext.getSystemService("storage");
        }
        String str2 = (String)localMethod2.invoke(mStorageManager, new Object[] { paramString });
        return str2;
      }
    }
    catch (Exception localException1)
    {
      logv("MTKLogger/Utils", "Fail to access StorageManager.getVolumnState(). No such method.");
      try
      {
        Method localMethod1 = Environment.class.getDeclaredMethod("getStorageState", new Class[] { String.class });
        if (localMethod1 != null)
        {
          String str1 = (String)localMethod1.invoke(null, new Object[] { paramString });
          return str1;
        }
      }
      catch (Exception localException2)
      {
        loge("MTKLogger/Utils", "Fail to access Environment.getStorageState(). No such method.", localException2);
      }
    }
    return "Unknown";
  }
  
  public static void logd(String paramString1, String paramString2)
  {
    Log.d(paramString1, paramString2);
  }
  
  public static void loge(String paramString1, String paramString2)
  {
    Log.e(paramString1, paramString2);
  }
  
  public static void loge(String paramString1, String paramString2, Throwable paramThrowable)
  {
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void logi(String paramString1, String paramString2)
  {
    Log.i(paramString1, paramString2);
  }
  
  public static void logv(String paramString1, String paramString2)
  {
    Log.v(paramString1, paramString2);
  }
  
  public static void logw(String paramString1, String paramString2)
  {
    Log.w(paramString1, paramString2);
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.utils.Utils
 * JD-Core Version:    0.7.0.1
 */