package com.mediatek.mtklogger.framework;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.LocalSocketAddress.Namespace;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Window;
import android.widget.Toast;
import com.mediatek.mtklogger.utils.MDLoggerClearLog;
import com.mediatek.mtklogger.utils.Utils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MultiModemLog
  extends LogInstance
{
  private static final String ADB_COMMAND_AUTO_TEST_START_MDLOGGER = "auto_test_start_mdlogger";
  private static final String ADB_COMMAND_EXIT = "exit";
  public static final String ADB_COMMAND_FORCE_MODEM_ASSERT = "force_modem_assert";
  private static final String ADB_COMMAND_PAUSE = "pause";
  private static final String ADB_COMMAND_RESUME = "resume";
  private static final String COMMAND_ISPAUSED = "ispaused";
  private static final String COMMAND_PAUSE = "pause";
  private static final String COMMAND_POLLING = "polling";
  private static final String COMMAND_RESET = "resetmd";
  private static final String COMMAND_RESUME = "resume";
  private static final String COMMAND_SETAUTO = "setauto,";
  private static final String COMMAND_START = "start,";
  private static final String COMMAND_STOP = "stop";
  public static final String LOG_FOLDER_NAME_MD1 = "mdlog";
  public static final String LOG_FOLDER_NAME_MD2 = "dualmdlog";
  private static final int MSG_BEGIN_DUMP = 51;
  private static final int MSG_BEGIN_RESET = 52;
  private static final int MSG_DISMISS_RESET_DIALOG = 54;
  private static final int MSG_MEMORY_DUMP_FINISH = 71;
  private static final int MSG_MEMORY_DUMP_START = 70;
  private static final int MSG_NO_LOGGING_FILE = 72;
  private static final int MSG_QUERY_PAUSE_STATUS = 76;
  private static final int MSG_SDCARD_FULL = 75;
  private static final int MSG_SEND_FILTER_FAIL = 73;
  private static final int MSG_SHOW_RESET_DIALOG = 53;
  private static final int MSG_WRITE_FILE_FAIL = 74;
  private static final String RESPONSE_FAIL_SEND_FILTER = "FAIL_SENDFILTER";
  private static final String RESPONSE_FAIL_WRITE_FILE = "FAIL_WRITEFILE";
  private static final String RESPONSE_FINISH_MEMORY_DUMP = "MEMORYDUMP_DONE";
  private static final String RESPONSE_LOGGING_FILE_NOTEXIST = "LOGFILE_NOTEXIST";
  private static final String RESPONSE_SDCARD_FULL = "SDCARD_FULL";
  private static final String RESPONSE_START_MEMORY_DUMP = "MEMORYDUMP_START";
  public static final String SOCKET_NAME_MD1 = "com.mediatek.mdlogger.socket";
  public static final String SOCKET_NAME_MD2 = "com.mediatek.dualmdlogger.socket";
  private static final String TAG = "MTKLogger/MultiModemLog";
  private Uri alertRingUri = null;
  private Ringtone assertRingtone = null;
  private boolean bConnected = false;
  private boolean isModemResetDialogShowing = false;
  private Handler mCmdResHandler = null;
  private int mCurrentStage = 0;
  private int mCurrentStatus = -1;
  private SharedPreferences mDefaultSharedPreferences;
  private Handler mMessageHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Utils.logd("MTKLogger/MultiModemLog", " MyHandler handleMessage --> start " + paramAnonymousMessage.what);
      switch (paramAnonymousMessage.what)
      {
      default: 
        Utils.logw("MTKLogger/MultiModemLog", "Not supported message: " + paramAnonymousMessage.what);
        return;
      }
      MultiModemLog.this.dismissResetModemDialog();
    }
  };
  private ModemManager mModemManager = null;
  private volatile boolean mMonitorThreadRunning = false;
  private volatile boolean mMonitorThreadStop = false;
  private ProgressDialog mResetModemDialog;
  private int mResetTimeout = 0;
  private Timer mTimer;
  Object modemLogLock = new Object();
  Object waitNextClearLogCheck = new Object();
  
  public MultiModemLog(Context paramContext, Handler paramHandler)
  {
    super(paramContext);
    new ModemLogThread().start();
    synchronized (this.modemLogLock)
    {
      try
      {
        this.modemLogLock.wait(500L);
        this.mCmdResHandler = paramHandler;
        this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          Utils.logi("MTKLogger/MultiModemLog", "Wait modem log sub thread initialization, but was interrupted");
        }
      }
    }
  }
  
  private void dealWithADBCommand(String paramString)
  {
    if ("exit".equalsIgnoreCase(paramString)) {
      if ((this.bConnected) && (this.mModemManager.sendCmd("stop")))
      {
        this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
        notifyServiceStatus(0, "");
      }
    }
    do
    {
      return;
      if ("pause".equalsIgnoreCase(paramString))
      {
        this.mHandler.sendEmptyMessage(3);
        return;
      }
      if ("resume".equalsIgnoreCase(paramString))
      {
        this.mHandler.sendEmptyMessage(1);
        return;
      }
      if ("force_modem_assert".equalsIgnoreCase(paramString))
      {
        this.mHandler.sendEmptyMessage(51);
        return;
      }
    } while ((!"auto_test_start_mdlogger".equalsIgnoreCase(paramString)) || (!getCurrentMode().equals("2")));
    this.mHandler.sendEmptyMessage(1);
  }
  
  private void dismissResetModemDialog()
  {
    int i = 1;
    this.mResetTimeout = (1000 + this.mResetTimeout);
    if (this.mResetTimeout >= 10000)
    {
      Utils.logw("MTKLogger/MultiModemLog", "Reset modem timeout!");
      this.mResetTimeout = 0;
      if (this.mTimer != null)
      {
        this.mTimer.cancel();
        this.mTimer = null;
      }
      if (this.mResetModemDialog != null)
      {
        this.mResetModemDialog.cancel();
        this.mResetModemDialog = null;
      }
      return;
    }
    if (i == this.mSharedPreferences.getInt("modemlog_enable", i)) {}
    for (;;)
    {
      Utils.loge("MTKLogger/MultiModemLog", "dismissResetModemDialog()-> isResetDone ? " + i);
      if (i == 0) {
        break;
      }
      this.mResetTimeout = 0;
      if (this.mTimer != null)
      {
        this.mTimer.cancel();
        this.mTimer = null;
      }
      if (this.mResetModemDialog == null) {
        break;
      }
      this.mResetModemDialog.cancel();
      this.mResetModemDialog = null;
      return;
      int j = 0;
    }
  }
  
  private String getCurrentMode()
  {
    String str = "2";
    if (this.mDefaultSharedPreferences != null) {
      str = this.mDefaultSharedPreferences.getString("log_mode", "2");
    }
    return str;
  }
  
  private void notifyServiceStatus(int paramInt, String paramString)
  {
    Utils.logi("MTKLogger/MultiModemLog", "-->notifyServiceStatus(), status=" + paramInt + ",  reason=[" + paramString + "]");
    if (1 == paramInt)
    {
      if (this.mSharedPreferences != null) {
        this.mSharedPreferences.edit().putInt("modemlog_enable", 1).commit();
      }
      updateStatusBar(2, 2131165187, true);
    }
    for (;;)
    {
      this.mCmdResHandler.obtainMessage(1, 2, paramInt, paramString).sendToTarget();
      return;
      if (this.mSharedPreferences != null)
      {
        this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
        this.mCurrentStatus = 0;
      }
      updateStatusBar(2, 2131165187, false);
    }
  }
  
  private void runMonitoringLogSizeThread()
  {
    Utils.logd("MTKLogger/MultiModemLog", "-->runMonitoringLogSizeThread()");
    if (this.mMonitorThreadRunning)
    {
      this.mMonitorThreadStop = false;
      Utils.logd("MTKLogger/MultiModemLog", "Already running, just return.");
      return;
    }
    Utils.logd("MTKLogger/MultiModemLog", "Initialize running flag for clear log checking thread.");
    this.mMonitorThreadStop = false;
    this.mMonitorThreadRunning = true;
    new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: ldc 26
        //   2: ldc 28
        //   4: invokestatic 34	com/mediatek/mtklogger/utils/Utils:logd	(Ljava/lang/String;Ljava/lang/String;)V
        //   7: aload_0
        //   8: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   11: invokestatic 38	com/mediatek/mtklogger/framework/MultiModemLog:access$1700	(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/content/SharedPreferences;
        //   14: ldc 40
        //   16: sipush 1000
        //   19: invokestatic 46	java/lang/String:valueOf	(I)Ljava/lang/String;
        //   22: invokeinterface 52 3 0
        //   27: astore_1
        //   28: aload_1
        //   29: invokestatic 58	java/lang/Integer:parseInt	(Ljava/lang/String;)I
        //   32: istore 14
        //   34: iload 14
        //   36: istore_3
        //   37: aload_0
        //   38: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   41: getfield 62	com/mediatek/mtklogger/framework/MultiModemLog:mSharedPreferences	Landroid/content/SharedPreferences;
        //   44: ldc 64
        //   46: iconst_0
        //   47: invokeinterface 68 3 0
        //   52: istore 4
        //   54: iload 4
        //   56: ifle +50 -> 106
        //   59: iload 4
        //   61: sipush 1000
        //   64: imul
        //   65: i2l
        //   66: lstore 10
        //   68: lload 10
        //   70: invokestatic 74	java/lang/Thread:sleep	(J)V
        //   73: aload_0
        //   74: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   77: getfield 62	com/mediatek/mtklogger/framework/MultiModemLog:mSharedPreferences	Landroid/content/SharedPreferences;
        //   80: invokeinterface 78 1 0
        //   85: ldc 64
        //   87: iconst_0
        //   88: invokeinterface 84 3 0
        //   93: invokeinterface 88 1 0
        //   98: pop
        //   99: ldc 26
        //   101: ldc 90
        //   103: invokestatic 93	com/mediatek/mtklogger/utils/Utils:logi	(Ljava/lang/String;Ljava/lang/String;)V
        //   106: aload_0
        //   107: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   110: invokestatic 97	com/mediatek/mtklogger/framework/MultiModemLog:access$900	(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z
        //   113: ifne +129 -> 242
        //   116: getstatic 103	com/mediatek/mtklogger/framework/LogInstance:mContext	Landroid/content/Context;
        //   119: invokestatic 107	com/mediatek/mtklogger/utils/Utils:getCurrentLogPath	(Landroid/content/Context;)Ljava/lang/String;
        //   122: astore 6
        //   124: aload_0
        //   125: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   128: invokestatic 111	com/mediatek/mtklogger/framework/MultiModemLog:access$000	(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
        //   131: ifnull +104 -> 235
        //   134: aload_0
        //   135: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   138: invokestatic 111	com/mediatek/mtklogger/framework/MultiModemLog:access$000	(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
        //   141: iload_3
        //   142: aload 6
        //   144: invokestatic 117	com/mediatek/mtklogger/framework/MultiModemLog$ModemManager:access$1800	(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;ILjava/lang/String;)V
        //   147: aload_0
        //   148: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   151: getfield 121	com/mediatek/mtklogger/framework/MultiModemLog:waitNextClearLogCheck	Ljava/lang/Object;
        //   154: astore 7
        //   156: aload 7
        //   158: monitorenter
        //   159: aload_0
        //   160: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   163: getfield 121	com/mediatek/mtklogger/framework/MultiModemLog:waitNextClearLogCheck	Ljava/lang/Object;
        //   166: ldc2_w 122
        //   169: invokevirtual 126	java/lang/Object:wait	(J)V
        //   172: aload 7
        //   174: monitorexit
        //   175: goto -69 -> 106
        //   178: astore 9
        //   180: aload 7
        //   182: monitorexit
        //   183: aload 9
        //   185: athrow
        //   186: astore_2
        //   187: ldc 26
        //   189: new 128	java/lang/StringBuilder
        //   192: dup
        //   193: invokespecial 129	java/lang/StringBuilder:<init>	()V
        //   196: ldc 131
        //   198: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   201: aload_1
        //   202: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   205: ldc 137
        //   207: invokevirtual 135	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   210: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   213: invokestatic 34	com/mediatek/mtklogger/utils/Utils:logd	(Ljava/lang/String;Ljava/lang/String;)V
        //   216: sipush 1000
        //   219: istore_3
        //   220: goto -183 -> 37
        //   223: astore 12
        //   225: ldc 26
        //   227: ldc 143
        //   229: invokestatic 146	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
        //   232: goto -126 -> 106
        //   235: ldc 26
        //   237: ldc 148
        //   239: invokestatic 146	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
        //   242: aload_0
        //   243: getfield 17	com/mediatek/mtklogger/framework/MultiModemLog$1:this$0	Lcom/mediatek/mtklogger/framework/MultiModemLog;
        //   246: iconst_0
        //   247: invokestatic 152	com/mediatek/mtklogger/framework/MultiModemLog:access$1902	(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z
        //   250: pop
        //   251: ldc 26
        //   253: ldc 154
        //   255: invokestatic 93	com/mediatek/mtklogger/utils/Utils:logi	(Ljava/lang/String;Ljava/lang/String;)V
        //   258: return
        //   259: astore 8
        //   261: ldc 26
        //   263: ldc 156
        //   265: invokestatic 159	com/mediatek/mtklogger/utils/Utils:logw	(Ljava/lang/String;Ljava/lang/String;)V
        //   268: goto -96 -> 172
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	271	0	this	1
        //   27	175	1	str1	String
        //   186	1	2	localNumberFormatException	NumberFormatException
        //   36	184	3	i	int
        //   52	13	4	j	int
        //   122	21	6	str2	String
        //   259	1	8	localInterruptedException1	InterruptedException
        //   178	6	9	localObject2	Object
        //   66	3	10	l	long
        //   223	1	12	localInterruptedException2	InterruptedException
        //   32	3	14	k	int
        // Exception table:
        //   from	to	target	type
        //   159	172	178	finally
        //   172	175	178	finally
        //   180	183	178	finally
        //   261	268	178	finally
        //   28	34	186	java/lang/NumberFormatException
        //   68	106	223	java/lang/InterruptedException
        //   159	172	259	java/lang/InterruptedException
      }
    }).start();
  }
  
  private void showMemoryDumpDoneDialog(final int paramInt, String paramString)
  {
    Utils.logd("MTKLogger/MultiModemLog", "-->showMemoryDumpDone(), logFolderPath=" + paramString + ", isModemResetDialogShowing=" + this.isModemResetDialogShowing);
    if (this.isModemResetDialogShowing)
    {
      Utils.logd("MTKLogger/MultiModemLog", "Modem reset dialog is already showing, just return");
      return;
    }
    if (this.alertRingUri == null) {
      this.alertRingUri = RingtoneManager.getDefaultUri(4);
    }
    if (this.alertRingUri != null)
    {
      Utils.logd("MTKLogger/MultiModemLog", "Play the ringtone");
      if (this.assertRingtone == null) {
        this.assertRingtone = RingtoneManager.getRingtone(mContext, this.alertRingUri);
      }
      if (this.assertRingtone != null) {
        this.assertRingtone.play();
      }
    }
    Utils.logi("MTKLogger/MultiModemLog", "Show memory dump done dialog.");
    String str = mContext.getText(2131165303).toString() + paramString;
    AlertDialog localAlertDialog = new AlertDialog.Builder(mContext).setTitle(mContext.getText(2131165304).toString()).setMessage(str).setPositiveButton(17039379, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Utils.logd("MTKLogger/MultiModemLog", "Click OK in memory dump done dialog");
        MultiModemLog.this.mHandler.obtainMessage(52, paramInt, 0).sendToTarget();
        if (MultiModemLog.this.assertRingtone != null) {
          MultiModemLog.this.assertRingtone.stop();
        }
        Utils.logd("MTKLogger/MultiModemLog", "After confirm, no need to show reset dialog next time");
        MultiModemLog.this.mSharedPreferences.edit().remove("md_assert_file_path").commit();
        MultiModemLog.access$2102(MultiModemLog.this, false);
      }
    }).create();
    localAlertDialog.getWindow().setType(2003);
    localAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        Utils.logd("MTKLogger/MultiModemLog", "Press cancel in memory dump done dialog");
        MultiModemLog.this.mHandler.obtainMessage(52, paramInt, 0).sendToTarget();
        if (MultiModemLog.this.assertRingtone != null) {
          MultiModemLog.this.assertRingtone.stop();
        }
        Utils.logd("MTKLogger/MultiModemLog", "After cancel, no need to show reset dialog next time");
        MultiModemLog.this.mSharedPreferences.edit().remove("md_assert_file_path").commit();
        MultiModemLog.access$2102(MultiModemLog.this, false);
      }
    });
    this.isModemResetDialogShowing = true;
    localAlertDialog.show();
  }
  
  private void showResetModemDialog()
  {
    this.mResetModemDialog = new ProgressDialog(mContext);
    this.mResetModemDialog.setTitle(mContext.getText(2131165305).toString());
    this.mResetModemDialog.setMessage(mContext.getText(2131165306).toString());
    this.mResetModemDialog.setCancelable(false);
    this.mResetModemDialog.getWindow().setType(2003);
    this.mResetModemDialog.show();
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    this.mTimer = new Timer(true);
    this.mTimer.schedule(new TimerTask()
    {
      public void run()
      {
        MultiModemLog.this.mMessageHandler.obtainMessage(54).sendToTarget();
      }
    }, 1000L, 1000L);
  }
  
  public int getGlobalRunningStage()
  {
    return this.mCurrentStage;
  }
  
  public int getLogRunningStatus()
  {
    return this.mCurrentStatus;
  }
  
  class ModemLogConnection
    extends LogConnection
  {
    private int mConnType = 0;
    
    public ModemLogConnection(int paramInt, String paramString, Handler paramHandler)
    {
      super(paramString, LocalSocketAddress.Namespace.ABSTRACT, paramHandler);
      this.mConnType = paramInt;
    }
    
    public void dealWithResponse(byte[] paramArrayOfByte, Handler paramHandler)
    {
      super.dealWithResponse(paramArrayOfByte, paramHandler);
      String str = new String(paramArrayOfByte);
      Utils.logi("MTKLogger/MultiModemLog", "-->dealWithResponse(), resp=" + new String(paramArrayOfByte));
      if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      {
        Utils.logw("MTKLogger/MultiModemLog", "Get an empty response from native, ignore it.");
        return;
      }
      Object localObject = null;
      int i;
      if (str.startsWith("MEMORYDUMP_START")) {
        i = 70;
      }
      for (;;)
      {
        Message localMessage = paramHandler.obtainMessage(i);
        localMessage.arg1 = this.mConnType;
        if (!TextUtils.isEmpty((CharSequence)localObject)) {
          localMessage.obj = localObject;
        }
        localMessage.sendToTarget();
        return;
        if (str.startsWith("MEMORYDUMP_DONE"))
        {
          i = 71;
          int k = 1 + "MEMORYDUMP_DONE".length();
          int m = -1 + str.length();
          if (k < m) {}
          for (localObject = str.substring(k, m);; localObject = null)
          {
            Utils.logd("MTKLogger/MultiModemLog", "Try to save modem assert file name to file, file name=" + (String)localObject);
            MultiModemLog.this.mSharedPreferences.edit().putString("md_assert_file_path", this.mConnType + ";" + (String)localObject).commit();
            Utils.logd("MTKLogger/MultiModemLog", "Save modem assert file name to file done");
            break;
            Utils.loge("MTKLogger/MultiModemLog", "Invalid dump done message from native.");
          }
        }
        if (str.startsWith("LOGFILE_NOTEXIST"))
        {
          i = 72;
          localObject = null;
        }
        else if (str.startsWith("FAIL_SENDFILTER"))
        {
          i = 73;
          localObject = null;
        }
        else if (str.startsWith("FAIL_WRITEFILE"))
        {
          i = 74;
          localObject = null;
        }
        else if (str.startsWith("SDCARD_FULL"))
        {
          i = 75;
          localObject = null;
        }
        else
        {
          boolean bool = str.startsWith("ispaused");
          localObject = null;
          i = 0;
          if (bool)
          {
            i = 76;
            int j = 1 + "ispaused".length();
            if (j < str.length())
            {
              localObject = str.substring(j, j + 1);
            }
            else
            {
              Utils.loge("MTKLogger/MultiModemLog", "Invalid puase status response from native.");
              localObject = null;
            }
          }
        }
      }
    }
  }
  
  class ModemLogHandler
    extends LogInstance.LogHandler
  {
    ModemLogHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      Utils.logi("MTKLogger/MultiModemLog", "Handle receive message, what=" + paramMessage.what + ", msg.arg1=[" + paramMessage.arg1 + "]");
      if (!MultiModemLog.this.bConnected)
      {
        if (MultiModemLog.this.mModemManager == null) {
          MultiModemLog.access$002(MultiModemLog.this, new MultiModemLog.ModemManager(MultiModemLog.this, MultiModemLog.this.mHandler));
        }
        MultiModemLog.access$102(MultiModemLog.this, MultiModemLog.ModemManager.access$200(MultiModemLog.this.mModemManager));
        MultiModemLog.access$302(MultiModemLog.this, -1);
      }
      Object localObject1 = paramMessage.obj;
      String str1 = null;
      if (localObject1 != null)
      {
        boolean bool2 = localObject1 instanceof String;
        str1 = null;
        if (bool2) {
          str1 = (String)localObject1;
        }
      }
      String[] arrayOfString;
      if (MultiModemLog.this.bConnected)
      {
        String str7 = MultiModemLog.this.mSharedPreferences.getString("md_assert_file_path", "");
        if (!TextUtils.isEmpty(str7))
        {
          arrayOfString = str7.split(";");
          if (arrayOfString.length == 2) {
            Utils.logi("MTKLogger/MultiModemLog", "The former modem reset dialog was killed abnormally, re-show it, assert file path=" + str7);
          }
        }
      }
      String str2;
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                try
                {
                  MultiModemLog.this.showMemoryDumpDoneDialog(Integer.parseInt(arrayOfString[0]), arrayOfString[1]);
                  switch (paramMessage.what)
                  {
                  default: 
                    Utils.logw("MTKLogger/MultiModemLog", "Not supported message: " + paramMessage.what);
                    return;
                  }
                }
                catch (NumberFormatException localNumberFormatException)
                {
                  for (;;)
                  {
                    Utils.loge("MTKLogger/MultiModemLog", "Cached modem assert file format invalid");
                    continue;
                    if (!MultiModemLog.this.bConnected)
                    {
                      Utils.loge("MTKLogger/MultiModemLog", "Fail to establish connection to native layer.");
                      if ((MultiModemLog.this.mSharedPreferences != null) && (1 == MultiModemLog.this.mSharedPreferences.getInt("modemlog_enable", 1))) {
                        MultiModemLog.this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
                      }
                      MultiModemLog.this.notifyServiceStatus(0, "1");
                      return;
                    }
                    int j = MultiModemLog.this.getLogStorageState();
                    String str6 = MultiModemLog.this.getCurrentMode();
                    if ((!"1".equals(str6)) && (j != 1))
                    {
                      Utils.loge("MTKLogger/MultiModemLog", "Log storage is not ready yet.");
                      if ((MultiModemLog.this.mSharedPreferences != null) && (1 == MultiModemLog.this.mSharedPreferences.getInt("modemlog_enable", 1))) {
                        MultiModemLog.this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
                      }
                      if (j == -1)
                      {
                        MultiModemLog.this.notifyServiceStatus(0, "2");
                        return;
                      }
                      if (j == -2) {
                        MultiModemLog.this.notifyServiceStatus(0, "3");
                      }
                    }
                    else
                    {
                      try
                      {
                        Thread.sleep(200L);
                        MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "start," + str6);
                        Thread.sleep(500L);
                        if (MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "resume"))
                        {
                          MultiModemLog.this.notifyServiceStatus(1, "");
                          MultiModemLog.this.runMonitoringLogSizeThread();
                          return;
                        }
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
                  if (MultiModemLog.this.bConnected)
                  {
                    Utils.logd("MTKLogger/MultiModemLog", "Receive stop command, stop reason=" + str1);
                    if ("ipo_shutdown".equals(str1))
                    {
                      String str5 = SystemProperties.get("sys.ipo.pwrdncap", "-1");
                      Utils.logv("MTKLogger/MultiModemLog", "IPO flag for modem log is " + str5);
                      if (("1".equals(str5)) || ("3".equals(str5)))
                      {
                        Utils.logi("MTKLogger/MultiModemLog", "According to IPO flag, do not need to stop modem log when IPO shutdown");
                        return;
                      }
                    }
                    boolean bool1 = MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "pause");
                    MultiModemLog.access$902(MultiModemLog.this, true);
                    synchronized (MultiModemLog.this.waitNextClearLogCheck)
                    {
                      MultiModemLog.this.waitNextClearLogCheck.notifyAll();
                      if (!bool1)
                      {
                        Utils.loge("MTKLogger/MultiModemLog", "Send stop command to native layer fail, maybe connection has already be lost.");
                        MultiModemLog.this.notifyServiceStatus(0, "4");
                        return;
                      }
                    }
                    String str4 = "";
                    if ("sd_timeout".equals(str1)) {
                      str4 = "11";
                    }
                    MultiModemLog.this.notifyServiceStatus(0, str4);
                    return;
                  }
                  Utils.logw("MTKLogger/MultiModemLog", "Have not connected to native layer, just ignore this command");
                  MultiModemLog.this.notifyServiceStatus(0, "1");
                  return;
                }
                if (MultiModemLog.this.bConnected)
                {
                  if (!TextUtils.isEmpty(str1))
                  {
                    Utils.logd("MTKLogger/MultiModemLog", "Receive config command, parameter=" + str1);
                    if (str1.startsWith("autostart="))
                    {
                      if (str1.endsWith("1"))
                      {
                        MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "setauto," + MultiModemLog.this.getCurrentMode());
                        return;
                      }
                      MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "setauto,0");
                      return;
                    }
                    Utils.logw("MTKLogger/MultiModemLog", "Unsupported config command");
                    return;
                  }
                  Utils.loge("MTKLogger/MultiModemLog", "Receive config command, but parameter is null");
                  return;
                }
                Utils.logw("MTKLogger/MultiModemLog", "Fail to config native parameter because of lost connection.");
                return;
                if (!MultiModemLog.this.bConnected)
                {
                  Utils.logw("MTKLogger/MultiModemLog", "Reconnect to native layer fail! Mark log status as stopped.");
                  MultiModemLog.this.notifyServiceStatus(0, "1");
                  return;
                }
                MultiModemLog.this.runMonitoringLogSizeThread();
                Utils.logd("MTKLogger/MultiModemLog", "Send query pause status command to modem.");
                MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "ispaused");
                return;
                Utils.logd("MTKLogger/MultiModemLog", "Receive adb command[" + str1 + "]");
              } while (TextUtils.isEmpty(str1));
              MultiModemLog.this.dealWithADBCommand(str1);
              return;
              MultiModemLog.access$102(MultiModemLog.this, false);
              MultiModemLog.access$902(MultiModemLog.this, true);
              synchronized (MultiModemLog.this.waitNextClearLogCheck)
              {
                MultiModemLog.this.waitNextClearLogCheck.notifyAll();
                Utils.logw("MTKLogger/MultiModemLog", "Modemlog [" + paramMessage.arg1 + "] lost, do not stop other instance, just update status as stopped");
                if (MultiModemLog.this.mModemManager != null)
                {
                  MultiModemLog.ModemManager.access$1100(MultiModemLog.this.mModemManager);
                  MultiModemLog.access$002(MultiModemLog.this, null);
                }
                MultiModemLog.this.notifyServiceStatus(0, "5");
                return;
              }
              if (MultiModemLog.this.bConnected)
              {
                if (1 == MultiModemLog.this.mSharedPreferences.getInt("modemlog_enable", 1))
                {
                  MultiModemLog.ModemManager.access$1200(MultiModemLog.this.mModemManager, "polling");
                  return;
                }
                Utils.logw("MTKLogger/MultiModemLog", "Modem log is stopped, no dumping is allowed.");
                return;
              }
              Utils.loge("MTKLogger/MultiModemLog", "Lost connection to native, so ignore polling command.");
              return;
              if (MultiModemLog.this.bConnected)
              {
                MultiModemLog.ModemManager.access$1300(MultiModemLog.this.mModemManager, paramMessage.arg1, "resetmd");
                MultiModemLog.this.showResetModemDialog();
                return;
              }
              Utils.loge("MTKLogger/MultiModemLog", "Lost connection to native, reset command will have no effect.");
              return;
              MultiModemLog.access$1502(MultiModemLog.this, 3);
              MultiModemLog.this.mCmdResHandler.obtainMessage(3, Integer.valueOf(MultiModemLog.this.mCurrentStage)).sendToTarget();
              return;
              MultiModemLog.access$1502(MultiModemLog.this, 0);
              MultiModemLog.this.mCmdResHandler.obtainMessage(3, Integer.valueOf(MultiModemLog.this.mCurrentStage)).sendToTarget();
              Object localObject3 = paramMessage.obj;
              String str3 = "";
              if ((localObject3 != null) && ((localObject3 instanceof String)))
              {
                str3 = (String)localObject3;
                MultiModemLog.this.mSharedPreferences.edit().putString("modem_exception_path", str3).commit();
              }
              MultiModemLog.this.showMemoryDumpDoneDialog(paramMessage.arg1, str3);
              return;
              Toast.makeText(LogInstance.mContext, "Log file is lost", 1).show();
            } while ((!MultiModemLog.this.bConnected) || (!MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "pause")));
            MultiModemLog.this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
            MultiModemLog.this.notifyServiceStatus(0, "");
            return;
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(LogInstance.mContext);
            localBuilder.setTitle(2131165307).setMessage(2131165308);
            DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
            };
            localBuilder.setPositiveButton(17039379, local1);
            AlertDialog localAlertDialog = localBuilder.create();
            localAlertDialog.getWindow().setType(2003);
            localAlertDialog.show();
            return;
            Toast.makeText(LogInstance.mContext, "Failed to write log file", 1).show();
          } while ((!MultiModemLog.this.bConnected) || (!MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "pause")));
          MultiModemLog.this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
          MultiModemLog.this.notifyServiceStatus(0, "");
          return;
        } while ((!MultiModemLog.this.bConnected) || (!MultiModemLog.ModemManager.access$700(MultiModemLog.this.mModemManager, "pause")));
        MultiModemLog.this.mSharedPreferences.edit().putInt("modemlog_enable", 0).commit();
        MultiModemLog.this.notifyServiceStatus(0, "3");
        return;
        int i = paramMessage.arg1;
        Object localObject2 = paramMessage.obj;
        if (MultiModemLog.this.mCurrentStatus == -1) {
          MultiModemLog.access$302(MultiModemLog.this, 0);
        }
        if ("0".equals(localObject2)) {
          MultiModemLog.access$376(MultiModemLog.this, i);
        }
        for (;;)
        {
          Utils.logd("MTKLogger/MultiModemLog", "Query MD pause status return, modemIndex=" + i + ", new pause status=" + localObject2 + ", mCurrentStatus=" + MultiModemLog.this.mCurrentStatus);
          str2 = SystemProperties.get((String)Utils.KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP.get(2), "0");
          if ((!"0".equals(str2)) || (MultiModemLog.this.mCurrentStatus <= 0)) {
            break;
          }
          Utils.logw("MTKLogger/MultiModemLog", "Modem log runing status from system property is 0, but new query value is 1, update this to user.");
          MultiModemLog.this.notifyServiceStatus(1, "");
          return;
          if ("1".equals(localObject2))
          {
            MultiModemLog.access$376(MultiModemLog.this, i);
            MultiModemLog.access$380(MultiModemLog.this, i);
          }
          else
          {
            Utils.loge("MTKLogger/MultiModemLog", "Invalid pause status value.");
          }
        }
      } while ((!"1".equals(str2)) || (MultiModemLog.this.mCurrentStatus != 0));
      Utils.logw("MTKLogger/MultiModemLog", "Modem log runing status from system property is 1, but new query value is 0, update this to user.");
      MultiModemLog.this.notifyServiceStatus(0, "");
    }
  }
  
  class ModemLogThread
    extends Thread
  {
    ModemLogThread() {}
    
    public void run()
    {
      Looper.prepare();
      MultiModemLog.this.mHandler = new MultiModemLog.ModemLogHandler(MultiModemLog.this);
      Utils.logd("MTKLogger/MultiModemLog", "Begin to construct ModemManager with ModemLog handler.");
      MultiModemLog.access$002(MultiModemLog.this, new MultiModemLog.ModemManager(MultiModemLog.this, MultiModemLog.this.mHandler));
      synchronized (MultiModemLog.this.modemLogLock)
      {
        MultiModemLog.this.modemLogLock.notify();
        Looper.loop();
        return;
      }
    }
  }
  
  class ModemManager
  {
    private Set<Integer> MODEM_KEY_SET = new HashSet();
    private SparseArray<MultiModemLog.ModemLogConnection> connectionMap = new SparseArray();
    private SparseArray<String> logFolderMap = new SparseArray();
    
    public ModemManager(Handler paramHandler)
    {
      Utils.logv("MTKLogger/MultiModemLog", "Modem1 is enabled");
      this.MODEM_KEY_SET.add(Integer.valueOf(1));
      this.connectionMap.put(1, new MultiModemLog.ModemLogConnection(MultiModemLog.this, 1, "com.mediatek.mdlogger.socket", paramHandler));
      this.logFolderMap.put(1, "mdlog");
    }
    
    private boolean initLogConnection()
    {
      boolean bool = false;
      Iterator localIterator = this.MODEM_KEY_SET.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        if (LogInstance.initLogConnection((LogConnection)this.connectionMap.get(i))) {
          bool = true;
        } else {
          Utils.loge("MTKLogger/MultiModemLog/ModemManager", "Fail to init connection for modem [" + i + "]");
        }
      }
      Utils.logd("MTKLogger/MultiModemLog/ModemManager", "-->initLogConnection(), connected=" + bool);
      return bool;
    }
    
    private void recycleLogFolder(int paramInt, String paramString)
    {
      Iterator localIterator = this.MODEM_KEY_SET.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        if (((LogConnection)this.connectionMap.get(i)).isConnected())
        {
          String str = (String)this.logFolderMap.get(i);
          MDLoggerClearLog.getInstance().checkAndClearLog(paramInt, paramString, str);
        }
      }
    }
    
    private boolean sendCmd(int paramInt, String paramString)
    {
      LogConnection localLogConnection = (LogConnection)this.connectionMap.get(paramInt);
      if ((localLogConnection == null) || (!localLogConnection.isConnected()))
      {
        Utils.loge("MTKLogger/MultiModemLog", "Send command to instance [" + paramInt + "] fail, instance have not be initialized or already lost connection.");
        return false;
      }
      boolean bool1 = localLogConnection.sendCmd(paramString);
      boolean bool2 = false;
      if (bool1) {
        bool2 = true;
      }
      Utils.logd("MTKLogger/MultiModemLog/ModemManager", "-->sendCmd(), instanceIndex=" + paramInt + "cmd=" + paramString + ", result=" + bool2);
      return bool2;
    }
    
    private boolean sendCmd(String paramString)
    {
      boolean bool = false;
      if ("resume".equals(paramString)) {
        MultiModemLog.access$302(MultiModemLog.this, 0);
      }
      Iterator localIterator = this.MODEM_KEY_SET.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        LogConnection localLogConnection = (LogConnection)this.connectionMap.get(i);
        if ((localLogConnection.isConnected()) && (localLogConnection.sendCmd(paramString)))
        {
          bool = true;
          if ("resume".equals(paramString))
          {
            Utils.logd("MTKLogger/MultiModemLog", "Send resume command to MD" + i + ", mark it as running");
            MultiModemLog.access$376(MultiModemLog.this, i);
          }
        }
      }
      Utils.logd("MTKLogger/MultiModemLog/ModemManager", "<--sendCmd(), cmd=" + paramString + ", result=" + bool);
      return bool;
    }
    
    private boolean sendCmdToOneInstance(String paramString)
    {
      Iterator localIterator = this.MODEM_KEY_SET.iterator();
      LogConnection localLogConnection;
      do
      {
        boolean bool1 = localIterator.hasNext();
        bool2 = false;
        if (!bool1) {
          break;
        }
        int i = ((Integer)localIterator.next()).intValue();
        localLogConnection = (LogConnection)this.connectionMap.get(i);
      } while ((!localLogConnection.isConnected()) || (!localLogConnection.sendCmd(paramString)));
      boolean bool2 = true;
      Utils.logd("MTKLogger/MultiModemLog/ModemManager", "-->sendCmdToOneInstance(), cmd=" + paramString + ", result=" + bool2);
      return bool2;
    }
    
    private void stop()
    {
      Utils.logi("MTKLogger/MultiModemLog/ModemManager", "-->stop()");
      Iterator localIterator = this.MODEM_KEY_SET.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        ((LogConnection)this.connectionMap.get(i)).stop();
      }
    }
    
    private void termOtherInstance(int paramInt)
    {
      Utils.logi("MTKLogger/MultiModemLog/ModemManager", "-->termOtherInstance(), selfType=" + paramInt);
      Iterator localIterator = this.MODEM_KEY_SET.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        LogConnection localLogConnection = (LogConnection)this.connectionMap.get(i);
        if ((localLogConnection.isConnected()) && (i != paramInt)) {
          localLogConnection.sendCmd("pause");
        }
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.MultiModemLog
 * JD-Core Version:    0.7.0.1
 */