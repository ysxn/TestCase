package com.mediatek.mtklogger.taglog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.mediatek.mtklogger.LogFolderListActivity;
import com.mediatek.mtklogger.exceptionreporter.ExceptionReportManager;
import com.mediatek.mtklogger.framework.MTKLoggerService;
import com.mediatek.mtklogger.utils.ExceptionInfo;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class TagLogManager
{
  private static final int COMPRESS_SPEED = 1;
  private static final int DIALOG_ALL_LOGTOOL_STOPED = 302;
  private static final int DIALOG_INPUT = 301;
  private static final int DIALOG_LACK_OF_SDSPACE = 303;
  private static final int DIALOG_START_PROGRESS = 306;
  private static final int DIALOG_ZIP_LOG_FAIL = 305;
  private static final int EVENT_ALL_LOGTOOL_STOPED = 205;
  private static final int EVENT_CHECK_INPUTDIALOG_TIMEOUT = 209;
  private static final int EVENT_CREATE_INPUTDIALOG = 203;
  private static final int EVENT_ZIP_LOG_FAIL = 207;
  private static final int EVENT_ZIP_LOG_SUCCESS = 206;
  private static final int INPUT_TIMEOUT = 120000;
  private static final SparseArray<String> LOGPATHKEY;
  private static final int MONITOR_TIMER = 200;
  private static final int STOPPED_TIMEOUT = 16000;
  private static final String TAG = "MTKLogger/TagLogManager";
  private static final int WAIT_MODEM_INTENT = 1000;
  private static TagLogManager instance = null;
  private static boolean isTagingLog = false;
  private Context mContext = null;
  private List<LogInformation> mCurrentLogFolderList;
  private Bundle mDataFromExtras;
  private String mDbPathFromAee;
  private SharedPreferences mDefaultSharedPreferences;
  private AlertDialog mDialog;
  private boolean mIgnoreMdLog = false;
  private boolean mIsFromMainActivity = false;
  private boolean mIsFromReboot = false;
  private boolean mIsInputDialogClicked;
  private boolean mIsModemExp;
  private boolean mIsTagInputNull = false;
  private boolean mIsTaglogClicked = false;
  private boolean mIsWaitingLogStateChange = false;
  private SparseArray<String> mLogPathInTagLog = new SparseArray();
  private MTKLoggerService mLogService = null;
  private BroadcastReceiver mLogStateReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if ("com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED".equals(paramAnonymousIntent.getAction()))
      {
        Utils.logd("MTKLogger/TagLogManager", "Log state changed, mIsWaitingLogStateChange?" + TagLogManager.this.mIsWaitingLogStateChange);
        TagLogManager.access$002(TagLogManager.this, false);
      }
    }
  };
  private SparseArray<Boolean> mLogToolStatus = new SparseArray();
  private boolean mManualSaveLog = false;
  private Timer mMonitorTimer;
  long mNeedMoreSpace = 0L;
  private ProgressDialog mProgressDialog;
  private SharedPreferences mSharedPreferences;
  private String mTag = "";
  private String mTagLogResult;
  private int mTotalFilesCount = 0;
  private UIHandler mUIHandler;
  
  static
  {
    LOGPATHKEY = new SparseArray();
    LOGPATHKEY.put(2, "ModemLogPath");
    LOGPATHKEY.put(1, "MobileLogPath");
    LOGPATHKEY.put(4, "NetLogPath");
  }
  
  private TagLogManager(Context paramContext)
  {
    Utils.logv("MTKLogger/TagLogManager", "<init>");
    this.mContext = paramContext;
    init();
  }
  
  private void createDialog(int paramInt)
  {
    AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this.mContext);
    switch (paramInt)
    {
    }
    for (;;)
    {
      this.mDialog = localBuilder1.create();
      this.mDialog.setCancelable(false);
      this.mDialog.getWindow().setType(2003);
      this.mDialog.show();
      return;
      this.mIsInputDialogClicked = false;
      final EditText localEditText = new EditText(this.mContext);
      localEditText.setCustomSelectionActionModeCallback(new ActionMode.Callback()
      {
        public boolean onActionItemClicked(ActionMode paramAnonymousActionMode, MenuItem paramAnonymousMenuItem)
        {
          return false;
        }
        
        public boolean onCreateActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
        {
          return false;
        }
        
        public void onDestroyActionMode(ActionMode paramAnonymousActionMode) {}
        
        public boolean onPrepareActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
        {
          return false;
        }
      });
      localEditText.setLongClickable(false);
      localEditText.setTextIsSelectable(false);
      localEditText.setKeyListener(new NumberKeyListener()
      {
        protected char[] getAcceptedChars()
        {
          return new char[] { 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 95, 32, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };
        }
        
        public int getInputType()
        {
          return 8192;
        }
      });
      String str = this.mDataFromExtras.getString("taglogInputName");
      if (str == null) {
        str = "";
      }
      localEditText.setText(str);
      localBuilder1.setTitle(2131165288).setMessage(2131165292).setView(localEditText).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if (TagLogManager.this.mIsTaglogClicked) {
            return;
          }
          TagLogManager.access$3002(TagLogManager.this, true);
          ((InputMethodManager)TagLogManager.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(localEditText.getWindowToken(), 0);
          TagLogManager.access$1302(TagLogManager.this, true);
          TagLogManager.access$2602(TagLogManager.this, localEditText.getText().toString().trim());
          if (TagLogManager.this.mTag.equals("")) {
            TagLogManager.access$3102(TagLogManager.this, true);
          }
          Utils.logi("MTKLogger/TagLogManager", "Input tag: " + TagLogManager.this.mTag);
          ZipManager.initZippedFilesCount();
          TagLogManager.this.mUIHandler.sendEmptyMessage(306);
          new TagLogManager.TagLogThread(TagLogManager.this, null).start();
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.access$3002(TagLogManager.this, false);
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          TagLogManager.access$1302(TagLogManager.this, true);
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.access$1402(TagLogManager.this, "Cancel");
          TagLogManager.this.deInit(true);
        }
      });
      continue;
      localBuilder1.setTitle(2131165288).setMessage(2131165293).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
          while (localIterator.hasNext())
          {
            Integer localInteger = (Integer)localIterator.next();
            TagLogManager.this.mLogToolStatus.put(localInteger.intValue(), Boolean.valueOf(true));
          }
          boolean bool = TagLogManager.this.mIgnoreMdLog;
          TagLogManager.access$2102(TagLogManager.this, false);
          TagLogManager.this.startOrStopAllLogTool(true);
          TagLogManager.access$2102(TagLogManager.this, bool);
          TagLogManager.this.deInit(true);
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.this.deInit(true);
        }
      });
      continue;
      AlertDialog.Builder localBuilder2 = localBuilder1.setTitle(2131165290);
      Context localContext = this.mContext;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new DecimalFormat(".00").format((float)this.mNeedMoreSpace / 1024.0F / 1024.0F).toString();
      localBuilder2.setMessage(localContext.getString(2131165291, arrayOfObject)).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          Intent localIntent = new Intent(TagLogManager.this.mContext, LogFolderListActivity.class);
          localIntent.putExtra("rootpath", Utils.getCurrentLogPath(TagLogManager.this.mContext) + "/mtklog/");
          localIntent.putExtra("fromWhere", "fromTagLog");
          localIntent.putExtra("path", TagLogManager.this.mDataFromExtras.getString("path"));
          if (TagLogManager.this.mIsTagInputNull) {}
          for (String str = "";; str = TagLogManager.this.mTag)
          {
            localIntent.putExtra("taglogInputName", str);
            Iterator localIterator = TagLogManager.this.mCurrentLogFolderList.iterator();
            while (localIterator.hasNext())
            {
              LogInformation localLogInformation = (LogInformation)localIterator.next();
              localIntent.putExtra((String)LogFolderListActivity.EXTRA_FILTER_FILES_KEY.get(localLogInformation.getLogType()), localLogInformation.getLogFile().getAbsolutePath());
            }
          }
          localIntent.setFlags(268435456);
          TagLogManager.this.mContext.startActivity(localIntent);
          TagLogManager.access$1402(TagLogManager.this, "Failed");
          TagLogManager.this.deInit(false);
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.this.deInit(true);
        }
      });
      continue;
      localBuilder1.setTitle(2131165289).setMessage(2131165294).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.access$1402(TagLogManager.this, "Failed");
          TagLogManager.this.deInit(true);
        }
      });
      continue;
      localBuilder1.setTitle(2131165289).setMessage(2131165295).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.this.deInit(true);
        }
      });
      continue;
      localBuilder1.setTitle(2131165289).setMessage(2131165296).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          TagLogManager.this.deInit(true);
        }
      });
    }
  }
  
  private void createProgressDialog()
  {
    int i = 0;
    while (this.mTotalFilesCount == 0)
    {
      i += 50;
      if (i >= 10000) {}
      try
      {
        Utils.loge("MTKLogger/TagLogManager", "Create progress dialog failed! The total files count calculated error!");
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      Thread.sleep(50L);
    }
    if ((this.mProgressDialog == null) && (this.mUIHandler != null))
    {
      this.mProgressDialog = new ProgressDialog(this.mContext);
      if (this.mProgressDialog == null) {
        break label283;
      }
      this.mProgressDialog.setProgressStyle(0);
      this.mProgressDialog.setTitle(2131165288);
      this.mProgressDialog.setMessage(this.mContext.getResources().getText(2131165297));
      this.mProgressDialog.setCancelable(false);
      this.mProgressDialog.setProgressStyle(1);
      this.mProgressDialog.setButton(-1, this.mContext.getResources().getText(2131165298), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Utils.logd("MTKLogger/TagLogManager", "Run in Background button is clicked!");
          Intent localIntent = new Intent("extra_key_from_taglog");
          TagLogManager.this.mContext.sendBroadcast(localIntent);
          TagLogManager.this.dismissProgressDialog();
        }
      });
      this.mProgressDialog.getWindow().setType(2003);
      if ((this.mManualSaveLog) || (this.mIsFromMainActivity))
      {
        Utils.logd("MTKLogger/TagLogManager", "mManualSaveLog?" + this.mManualSaveLog + "; mIsFromMainActivity?" + this.mIsFromMainActivity);
        this.mProgressDialog.show();
      }
      this.mProgressDialog.setMax(this.mTotalFilesCount);
      this.mProgressDialog.setProgress(ZipManager.getZippedFilesCount());
      monitorProgressDialogBar();
    }
    while ((!this.mManualSaveLog) && (!this.mIsFromMainActivity))
    {
      dismissProgressDialog();
      return;
      label283:
      Utils.loge("MTKLogger/TagLogManager", "new mProgressDialog failed");
    }
  }
  
  private void deInit(boolean paramBoolean)
  {
    Utils.logd("MTKLogger/TagLogManager", "-->deInit()");
    this.mContext.unregisterReceiver(this.mLogStateReceiver);
    if ((this.mDialog != null) && (this.mDialog.isShowing()))
    {
      this.mDialog.dismiss();
      this.mDialog = null;
    }
    dismissProgressDialog();
    instance = null;
    this.mUIHandler = null;
    if ((paramBoolean) && (!this.mManualSaveLog))
    {
      notifyToLog2Server();
      return;
    }
    Utils.logd("MTKLogger/TagLogManager", "<-- Not need to notify Log2Server, mManualSaveLog=" + this.mManualSaveLog);
  }
  
  private void dismissProgressDialog()
  {
    if (this.mProgressDialog != null)
    {
      this.mProgressDialog.dismiss();
      this.mProgressDialog = null;
    }
  }
  
  private void forgetCachedTaggingLogFolder()
  {
    Utils.logd("MTKLogger/TagLogManager", "-->forgetCachedTaggingLogFolder()");
    SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      localEditor.remove((String)Utils.KEY_TAGGING_LOG_MAP.get(i));
    }
    localEditor.remove("tagging_dest").remove("tagging_db").commit();
  }
  
  public static TagLogManager getInstance(Context paramContext)
  {
    if (instance == null) {
      instance = new TagLogManager(paramContext);
    }
    return instance;
  }
  
  private SparseArray<String> getLogPath()
  {
    SparseArray localSparseArray = new SparseArray();
    String str1 = Utils.getCurrentLogPath(this.mContext) + "/mtklog/";
    if (!new File(str1).exists()) {}
    for (;;)
    {
      return localSparseArray;
      Utils.logi("MTKLogger/TagLogManager", "getMTKLogPath :" + str1);
      Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        String str2 = str1 + (String)Utils.LOG_PATH_MAP.get(localInteger.intValue());
        localSparseArray.put(localInteger.intValue(), str2);
      }
    }
  }
  
  private void init()
  {
    Utils.logd("MTKLogger/TagLogManager", "-->init()");
    this.mContext.setTheme(16973934);
    isTagingLog = false;
    this.mUIHandler = new UIHandler(null);
    this.mSharedPreferences = this.mContext.getSharedPreferences("log_settings", 0);
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    String[] arrayOfString = this.mContext.getResources().getStringArray(2131099655);
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      this.mIgnoreMdLog = arrayOfString[0].equals(this.mDefaultSharedPreferences.getString("log_mode", this.mContext.getString(2131165302)));
    }
    Utils.logd("MTKLogger/TagLogManager", "mIgnoreMdLog?" + this.mIgnoreMdLog);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED");
    this.mContext.registerReceiver(this.mLogStateReceiver, localIntentFilter);
    if ((this.mContext instanceof MTKLoggerService))
    {
      this.mLogService = ((MTKLoggerService)this.mContext);
      return;
    }
    Utils.loge("MTKLogger/TagLogManager", "Wrong parameter to contruct TagLogManager");
  }
  
  private void initToolStatus()
  {
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    if (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      SparseArray localSparseArray = this.mLogToolStatus;
      int i = localInteger.intValue();
      if (1 == this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0)) {}
      for (boolean bool = true;; bool = false)
      {
        localSparseArray.put(i, Boolean.valueOf(bool));
        break;
      }
    }
  }
  
  private void monitorProgressDialogBar()
  {
    if (this.mMonitorTimer != null) {
      return;
    }
    this.mMonitorTimer = new Timer(true);
    this.mMonitorTimer.schedule(new TimerTask()
    {
      public void run()
      {
        if (TagLogManager.this.mProgressDialog == null)
        {
          TagLogManager.this.mMonitorTimer.cancel();
          TagLogManager.access$2902(TagLogManager.this, null);
          return;
        }
        TagLogManager.this.mProgressDialog.setProgress(ZipManager.getZippedFilesCount());
      }
    }, 0L, 200L);
  }
  
  private void notifyToLog2Server()
  {
    Utils.logd("MTKLogger/TagLogManager", "-->notifyToLog2Server()");
    Intent localIntent = new Intent();
    localIntent.setAction("com.mediatek.syslogger.taglog");
    Iterator localIterator;
    if (this.mTagLogResult != null)
    {
      localIntent.putExtra("TaglogResult", this.mTagLogResult);
      Utils.logd("MTKLogger/TagLogManager", "TaglogResult = " + this.mTagLogResult);
      if (this.mLogPathInTagLog != null) {
        localIterator = Utils.LOG_TYPE_SET.iterator();
      }
    }
    else
    {
      for (;;)
      {
        if (!localIterator.hasNext()) {
          break label278;
        }
        Integer localInteger = (Integer)localIterator.next();
        if (this.mLogPathInTagLog.get(localInteger.intValue()) != null)
        {
          localIntent.putExtra((String)LOGPATHKEY.get(localInteger.intValue()), (String)this.mLogPathInTagLog.get(localInteger.intValue()));
          Utils.logd("MTKLogger/TagLogManager", (String)LOGPATHKEY.get(localInteger.intValue()) + " = " + (String)this.mLogPathInTagLog.get(localInteger.intValue()));
          continue;
          Utils.loge("MTKLogger/TagLogManager", "mTagLogResult is null!");
          break;
        }
        Utils.loge("MTKLogger/TagLogManager", "mLogPathInTagLog[" + localInteger + "]" + "= null!");
      }
    }
    Utils.loge("MTKLogger/TagLogManager", "mLogPathInTagLog is null");
    label278:
    if (this.mDataFromExtras != null) {
      localIntent.putExtras(this.mDataFromExtras);
    }
    for (;;)
    {
      ExceptionReportManager.getInstance(this.mContext).beginException(localIntent);
      Utils.logd("MTKLogger/TagLogManager", "send intent to Log2Server");
      return;
      Utils.loge("MTKLogger/TagLogManager", "Data From Aee is null");
    }
  }
  
  private void rememberCurrentTaggingLogFolder(String paramString)
  {
    Utils.logd("MTKLogger/TagLogManager", "-->rememberCurrentTaggingLogFolder()");
    SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
    Iterator localIterator = this.mCurrentLogFolderList.iterator();
    while (localIterator.hasNext())
    {
      LogInformation localLogInformation = (LogInformation)localIterator.next();
      localEditor.putString((String)Utils.KEY_TAGGING_LOG_MAP.get(localLogInformation.getLogType()), localLogInformation.getLogFile().getAbsolutePath());
    }
    localEditor.putString("tagging_dest", paramString).putString("tagging_db", this.mDbPathFromAee).commit();
  }
  
  private void startOrStopAllLogTool(boolean paramBoolean)
  {
    Utils.logd("MTKLogger/TagLogManager", "startOrStopAllLogTool() -> isStart?" + paramBoolean);
    int i = 0;
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      if ((localInteger.intValue() != 2) || ((!this.mIsModemExp) && (!this.mIgnoreMdLog)))
      {
        if (((Boolean)this.mLogToolStatus.get(localInteger.intValue(), Boolean.valueOf(false))).booleanValue()) {}
        for (int j = localInteger.intValue();; j = 0)
        {
          i |= j;
          break;
        }
      }
    }
    if (i == 0) {
      return;
    }
    if (paramBoolean) {}
    for (boolean bool = this.mLogService.startRecording(i, null);; bool = this.mLogService.stopRecording(i, null))
    {
      Utils.logd("MTKLogger/TagLogManager", "startOrStopAllLogTool() -> result?" + bool);
      if (!bool) {
        break;
      }
      this.mIsWaitingLogStateChange = true;
      return;
    }
    Utils.loge("MTKLogger/TagLogManager", "-->startOrStopAllLogTool(), isStart=" + paramBoolean + ", operation fail!");
  }
  
  private void tagSelectedLogFolder(String paramString)
  {
    Utils.logd("MTKLogger/TagLogManager", "-->tagSelectedLogFolder(), targetFolderName=" + paramString);
    this.mSharedPreferences.edit().putBoolean("tag_log_compressing", true).commit();
    this.mLogPathInTagLog = zipAllLogAndDelete(this.mCurrentLogFolderList, paramString);
    Iterator localIterator1 = Utils.LOG_TYPE_SET.iterator();
    while (localIterator1.hasNext())
    {
      Integer localInteger = (Integer)localIterator1.next();
      if (this.mLogPathInTagLog.get(localInteger.intValue()) == null) {
        Utils.logd("MTKLogger/TagLogManager", "mLogPathInTagLog[" + localInteger + "]" + "= null");
      } else {
        Utils.logd("MTKLogger/TagLogManager", "mLogPathInTagLog[" + localInteger + "]" + "=" + (String)this.mLogPathInTagLog.get(localInteger.intValue()));
      }
    }
    if (!this.mManualSaveLog) {
      TagLogUtils.writeFolderToTagFolder(this.mDbPathFromAee, paramString);
    }
    File localFile = new File(paramString + File.separator + "checksop.txt");
    if (localFile != null) {
      if (localFile.exists()) {
        localFile.delete();
      }
    }
    int i;
    try
    {
      localFile.createNewFile();
      TagLogUtils.writeCheckSOPToFile(this.mContext, localFile);
      i = 1;
      Iterator localIterator2 = this.mCurrentLogFolderList.iterator();
      while (localIterator2.hasNext())
      {
        LogInformation localLogInformation = (LogInformation)localIterator2.next();
        if (this.mLogPathInTagLog.get(localLogInformation.getLogType()) == null)
        {
          Utils.loge("MTKLogger/TagLogManager", "mLogPathInTagLog[" + localLogInformation.getLogType() + "]" + "= null");
          i = 0;
        }
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
    if (i != 0) {
      this.mUIHandler.sendEmptyMessage(206);
    }
    for (;;)
    {
      this.mSharedPreferences.edit().putBoolean("tag_log_compressing", false).commit();
      forgetCachedTaggingLogFolder();
      return;
      this.mUIHandler.sendEmptyMessage(207);
    }
  }
  
  private SparseArray<String> zipAllLogAndDelete(List<LogInformation> paramList, String paramString)
  {
    SparseArray localSparseArray = new SparseArray();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      LogInformation localLogInformation = (LogInformation)localIterator.next();
      String str = zipLogAndDelete(localLogInformation, paramString);
      if (str != null)
      {
        localSparseArray.put(localLogInformation.getLogType(), str);
        Utils.logi("MTKLogger/TagLogManager", "currentTaglogPaths[" + localLogInformation.getLogType() + "]" + "=" + str);
      }
    }
    return localSparseArray;
  }
  
  private String zipLogAndDelete(LogInformation paramLogInformation, String paramString)
  {
    File localFile1 = paramLogInformation.getLogFile();
    if (localFile1 == null)
    {
      Utils.loge("MTKLogger/TagLogManager", "Needed log folder path is null!!");
      return null;
    }
    Utils.logd("MTKLogger/TagLogManager", "-->zipLog() Folder : " + localFile1.getName());
    Object localObject = null;
    if (localFile1 != null)
    {
      boolean bool = "dualmdlog".equals(localFile1.getParentFile().getName());
      StringBuilder localStringBuilder = new StringBuilder().append(paramString).append(File.separator);
      if (bool) {}
      String str2;
      for (String str1 = "Dual";; str1 = "")
      {
        str2 = str1 + localFile1.getName() + ".zip";
        Utils.logi("MTKLogger/TagLogManager", "targetLogFileName :" + str2);
        File localFile2 = new File(str2);
        if ((localFile2 != null) && (localFile2.exists()) && (localFile2.isFile()))
        {
          Utils.logw("MTKLogger/TagLogManager", "target taglog file [" + str2 + "] already exist, delete it first");
          localFile2.delete();
        }
        if (ZipManager.zipFileOrFolder(localFile1.getAbsolutePath(), str2)) {
          break;
        }
        Utils.loge("MTKLogger/TagLogManager", "Fail to zip log folder: " + localFile1.getAbsolutePath());
        File localFile3 = new File(str2);
        if (localFile3.exists()) {
          localFile3.delete();
        }
        return null;
      }
      Utils.logi("MTKLogger/TagLogManager", "Zip log success, target log file=" + str2);
      localObject = str2;
      if ((this.mIsModemExp) && (paramLogInformation.getLogType() == 2))
      {
        Utils.logd("MTKLogger/TagLogManager", "It is a ModemExp and not delete primary log folder");
        return localObject;
      }
      Utils.deleteFolder(localFile1);
    }
    Utils.logd("MTKLogger/TagLogManager", "<--zipLog(), zipResultPath=" + localObject);
    return localObject;
  }
  
  public void beginTag(Intent paramIntent)
  {
    Utils.logd("MTKLogger/TagLogManager", "-->beginTag()");
    this.mIsFromMainActivity = false;
    if (paramIntent.getBooleanExtra("extra_key_from_main_activity", false))
    {
      if (isTagingLog)
      {
        Utils.loge("MTKLogger/TagLogManager", "EXTRA_KEY_FROM_MAIN_ACTIVITY");
        if (this.mUIHandler != null)
        {
          this.mIsFromMainActivity = true;
          this.mUIHandler.sendEmptyMessage(306);
        }
        return;
      }
      this.mSharedPreferences.edit().putBoolean("tag_log_compressing", false).commit();
      return;
    }
    if (isTagingLog)
    {
      Utils.logw("MTKLogger/TagLogManager", "TagLog is already on process, reject new request!");
      Toast.makeText(this.mContext, 2131165299, 0).show();
      return;
    }
    isTagingLog = true;
    this.mDataFromExtras = paramIntent.getExtras();
    this.mUIHandler.sendEmptyMessage(203);
  }
  
  public void resumeTag()
  {
    boolean bool = this.mSharedPreferences.getBoolean("tag_log_compressing", false);
    Utils.logd("MTKLogger/TagLogManager", "-->resumeTag(), isLastTaggingNotFinished=" + bool);
    if (bool)
    {
      isTagingLog = true;
      String str1 = this.mSharedPreferences.getString("tagging_dest", "");
      String str2 = this.mSharedPreferences.getString("tagging_db", "");
      if (TextUtils.isEmpty(str1))
      {
        Utils.logw("MTKLogger/TagLogManager", "Last tagging flag is not null, but can not find target folder, ignore");
        return;
      }
      File localFile1 = new File(str1);
      if ((localFile1 == null) || (!localFile1.exists()))
      {
        Utils.logw("MTKLogger/TagLogManager", "Last tagging target folder does not exist, ignore");
        return;
      }
      Iterator localIterator1;
      if (TextUtils.isEmpty(str2))
      {
        Utils.logd("MTKLogger/TagLogManager", "At resume time, find no db file, treat this as user trigger event");
        this.mManualSaveLog = true;
        this.mCurrentLogFolderList = new ArrayList();
        localIterator1 = Utils.LOG_TYPE_SET.iterator();
      }
      for (;;)
      {
        if (!localIterator1.hasNext()) {
          break label380;
        }
        int i = ((Integer)localIterator1.next()).intValue();
        String str3 = this.mSharedPreferences.getString((String)Utils.KEY_TAGGING_LOG_MAP.get(i), "");
        if (!TextUtils.isEmpty(str3))
        {
          File localFile2 = new File(str3);
          if ((localFile2 != null) && (localFile2.exists()))
          {
            Utils.logd("MTKLogger/TagLogManager", "Add not finished log folder: " + str3);
            this.mCurrentLogFolderList.add(new LogInformation(i, localFile2));
            continue;
            this.mManualSaveLog = false;
            this.mDbPathFromAee = str2;
            break;
          }
          Utils.logd("MTKLogger/TagLogManager", "Log folder [" + str3 + "] not exist, maybe already tag finish.");
          continue;
        }
        Utils.logd("MTKLogger/TagLogManager", "Log folder for type [" + i + "] is empty or null.");
      }
      label380:
      if (this.mCurrentLogFolderList.size() > 0)
      {
        this.mTotalFilesCount = 0;
        Iterator localIterator2 = this.mCurrentLogFolderList.iterator();
        while (localIterator2.hasNext())
        {
          LogInformation localLogInformation = (LogInformation)localIterator2.next();
          this.mTotalFilesCount += localLogInformation.getLogFilesCount();
        }
        Utils.logd("MTKLogger/TagLogManager", "Total taglog files count after resumeTag is : " + this.mTotalFilesCount);
        if (this.mProgressDialog != null) {
          this.mProgressDialog.setMax(this.mTotalFilesCount);
        }
        new ResumeTagThread(str1).start();
        return;
      }
      Utils.logw("MTKLogger/TagLogManager", "From taglog flag, need to resume tagging, but find no log folder that need tag, ignore");
      forgetCachedTaggingLogFolder();
      return;
    }
    deInit(false);
  }
  
  private class ResumeTagThread
    extends Thread
  {
    String targetFolder = null;
    
    public ResumeTagThread(String paramString)
    {
      this.targetFolder = paramString;
    }
    
    public void run()
    {
      Utils.logd("MTKLogger/TagLogManager", " -->Begin ResumeTagThread, targetFolder=" + this.targetFolder);
      TagLogManager.this.tagSelectedLogFolder(this.targetFolder);
      Utils.logd("MTKLogger/TagLogManager", " <--ResumeTagThread end, resume taglog finished.");
    }
  }
  
  private class TagLogThread
    extends Thread
  {
    private TagLogThread() {}
    
    public void run()
    {
      Utils.logi("MTKLogger/TagLogManager", "-->begin tag log compressing thread");
      TagLogManager.this.mSharedPreferences.edit().putBoolean("tag_log_compressing", true).commit();
      String str1 = Utils.getCurrentLogPath(TagLogManager.this.mContext);
      int i = TagLogUtils.checkSdCardSpace(str1);
      if (i != 401)
      {
        TagLogManager.this.mUIHandler.sendEmptyMessage(i);
        TagLogManager.this.mSharedPreferences.edit().putBoolean("tag_log_compressing", false).commit();
        return;
      }
      SparseArray localSparseArray = TagLogManager.this.getLogPath();
      TagLogManager.access$2002(TagLogManager.this, new ArrayList());
      if (TagLogManager.this.mIsModemExp)
      {
        String str4 = TagLogManager.this.mSharedPreferences.getString("modem_exception_path", "");
        int i6;
        if ((str4 != null) && (!"".equals(str4))) {
          i6 = 1;
        }
        for (;;)
        {
          int i7 = 0;
          label168:
          if (i6 == 0) {
            try
            {
              Thread.sleep(1000L);
              i7++;
              if (i7 == 5)
              {
                Utils.logd("MTKLogger/TagLogManager", "Modem Log is not Ready , wait for 5s");
                i7 = 0;
              }
              str4 = TagLogManager.this.mSharedPreferences.getString("modem_exception_path", "");
              if (str4 != null)
              {
                boolean bool = "".equals(str4);
                if (bool) {}
              }
              for (i6 = 1;; i6 = 0)
              {
                break label168;
                i6 = 0;
                break;
              }
            }
            catch (InterruptedException localInterruptedException3)
            {
              Utils.loge("MTKLogger/TagLogManager", "Catch InterruptedException");
            }
          }
        }
        TagLogManager.this.mSharedPreferences.edit().putString("modem_exception_path", "").commit();
        Utils.logd("MTKLogger/TagLogManager", "MODEM_EXCEPTION_PATH : " + str4);
        if (!TagLogManager.this.mIgnoreMdLog)
        {
          List localList4 = TagLogManager.this.mCurrentLogFolderList;
          File localFile4 = new File(str4);
          localList4.add(new LogInformation(2, localFile4));
        }
      }
      TagLogManager.this.initToolStatus();
      Iterator localIterator1 = Utils.LOG_TYPE_SET.iterator();
      while (localIterator1.hasNext())
      {
        Integer localInteger2 = (Integer)localIterator1.next();
        if (((Boolean)TagLogManager.this.mLogToolStatus.get(localInteger2.intValue())).booleanValue())
        {
          Utils.logd("MTKLogger/TagLogManager", "Type of " + localInteger2 + " log is running!");
          if (localInteger2.intValue() == 2)
          {
            if ((!TagLogManager.this.mIsModemExp) && (!TagLogManager.this.mIgnoreMdLog))
            {
              Utils.logd("MTKLogger/TagLogManager", "Modem log is running!");
              int i1 = TagLogManager.this.mLogService.getLogInstanceRunningStatus(2);
              Utils.logd("MTKLogger/TagLogManager", "modemLogRunningDetailStatus : " + i1);
              int i2 = 0;
              label535:
              if (i1 == -1) {}
              for (;;)
              {
                try
                {
                  Thread.sleep(500L);
                  int i5 = TagLogManager.this.mLogService.getLogInstanceRunningStatus(2);
                  i1 = i5;
                  i2++;
                  if (i2 != 10) {
                    break label535;
                  }
                  Utils.logd("MTKLogger/TagLogManager", "After wait for " + 0.5D * i2 + "s, modemLogRunningDetailStatus : " + i1);
                  if ((i1 != 1) && (i1 != 3)) {
                    break label850;
                  }
                  i3 = 1;
                  if (i3 != 0)
                  {
                    Utils.logd("MTKLogger/TagLogManager", "Modem one is running!");
                    File localFile3 = TagLogUtils.getCurrentLogFolderFromPath((String)localSparseArray.get(localInteger2.intValue()), TagLogManager.this.mIsFromReboot);
                    if ((localFile3 != null) && (localFile3.exists()))
                    {
                      List localList3 = TagLogManager.this.mCurrentLogFolderList;
                      LogInformation localLogInformation4 = new LogInformation(localInteger2.intValue(), localFile3);
                      localList3.add(localLogInformation4);
                    }
                  }
                  if ((i1 != 2) && (i1 != 3)) {
                    break label856;
                  }
                  i4 = 1;
                  if (i4 == 0) {
                    break;
                  }
                  Utils.logd("MTKLogger/TagLogManager", "Modem two is running!");
                  File localFile2 = TagLogUtils.getCurrentLogFolderFromPath(Utils.getCurrentLogPath(TagLogManager.this.mContext) + "/mtklog/" + "dualmdlog", TagLogManager.this.mIsFromReboot);
                  if ((localFile2 == null) || (!localFile2.exists())) {
                    break;
                  }
                  List localList2 = TagLogManager.this.mCurrentLogFolderList;
                  LogInformation localLogInformation3 = new LogInformation(localInteger2.intValue(), localFile2);
                  localList2.add(localLogInformation3);
                }
                catch (InterruptedException localInterruptedException2)
                {
                  Utils.loge("MTKLogger/TagLogManager", "Catch InterruptedException");
                }
                break label535;
                label850:
                int i3 = 0;
                continue;
                label856:
                int i4 = 0;
              }
            }
          }
          else
          {
            File localFile1 = TagLogUtils.getCurrentLogFolderFromPath((String)localSparseArray.get(localInteger2.intValue()), TagLogManager.this.mIsFromReboot);
            if ((localFile1 != null) && (localFile1.exists()))
            {
              List localList1 = TagLogManager.this.mCurrentLogFolderList;
              LogInformation localLogInformation2 = new LogInformation(localInteger2.intValue(), localFile1);
              localList1.add(localLogInformation2);
            }
          }
        }
      }
      TagLogManager.access$2302(TagLogManager.this, 0);
      Iterator localIterator2 = TagLogManager.this.mCurrentLogFolderList.iterator();
      while (localIterator2.hasNext())
      {
        LogInformation localLogInformation1 = (LogInformation)localIterator2.next();
        TagLogManager.access$2312(TagLogManager.this, localLogInformation1.getLogFilesCount());
      }
      Utils.logd("MTKLogger/TagLogManager", "Total taglog files count is : " + TagLogManager.this.mTotalFilesCount);
      if (TagLogManager.this.mProgressDialog != null) {
        TagLogManager.this.mProgressDialog.setMax(TagLogManager.this.mTotalFilesCount);
      }
      StatFs localStatFs = new StatFs(str1);
      int j = localStatFs.getAvailableBlocks();
      int k = localStatFs.getBlockSize();
      long l1 = j * k;
      TagLogManager.this.mNeedMoreSpace = (TagLogUtils.getTagLogNeededSize(TagLogManager.this.mCurrentLogFolderList) - l1);
      if (TagLogManager.this.mNeedMoreSpace > 0L)
      {
        TagLogManager.this.mUIHandler.sendEmptyMessage(402);
        TagLogManager.this.mSharedPreferences.edit().putBoolean("tag_log_compressing", false).commit();
        return;
      }
      if (!TagLogManager.this.mIsFromReboot) {
        TagLogManager.this.startOrStopAllLogTool(false);
      }
      long l2 = 0L;
      Iterator localIterator3 = TagLogManager.this.mCurrentLogFolderList.iterator();
      while (localIterator3.hasNext()) {
        l2 += ((LogInformation)localIterator3.next()).getLogSize();
      }
      TagLogManager.this.mSharedPreferences.edit().putInt("tag_log_ongoing", (int)(l2 / 1024L / 1024L / 1L)).commit();
      if (!TagLogManager.this.mIsFromReboot)
      {
        int m = 0;
        int n = 0;
        for (;;)
        {
          if (m == 0)
          {
            n += 500;
            if (n >= 16000)
            {
              Utils.loge("MTKLogger/TagLogManager", "Waiting log stopped timeout");
              TagLogManager.this.mSharedPreferences.edit().putBoolean("tag_log_compressing", false).commit();
              return;
            }
            try
            {
              Thread.sleep(500L);
              if (!TagLogManager.this.mIsWaitingLogStateChange)
              {
                Iterator localIterator4 = Utils.LOG_TYPE_SET.iterator();
                do
                {
                  if (!localIterator4.hasNext()) {
                    break;
                  }
                  localInteger1 = (Integer)localIterator4.next();
                  if (1 != TagLogManager.this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger1.intValue()), 0)) {
                    break label1494;
                  }
                } while ((localInteger1.intValue() == 2) && ((TagLogManager.this.mIsModemExp) || (TagLogManager.this.mIgnoreMdLog)));
                Utils.logi("MTKLogger/TagLogManager", "The type " + localInteger1 + " log is not stopped!");
                m = 0;
              }
            }
            catch (InterruptedException localInterruptedException1)
            {
              for (;;)
              {
                Integer localInteger1;
                localInterruptedException1.printStackTrace();
                continue;
                label1494:
                String str3 = SystemProperties.get((String)Utils.KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP.get(localInteger1.intValue()), "0");
                Utils.logi("MTKLogger/TagLogManager", " Native log(" + localInteger1 + ") running status=" + str3);
                if ("1".equals(str3))
                {
                  m = 0;
                  break;
                }
                Utils.logi("MTKLogger/TagLogManager", "The type " + localInteger1 + " log is stopped!");
                m = 1;
              }
            }
          }
        }
        TagLogManager.this.startOrStopAllLogTool(true);
      }
      String str2 = TagLogUtils.createTagLogFolder(str1, TagLogManager.this.mTag);
      TagLogManager.this.rememberCurrentTaggingLogFolder(str2);
      TagLogManager.this.tagSelectedLogFolder(str2);
      TagLogManager.this.mSharedPreferences.edit().putBoolean("tag_log_compressing", false).commit();
      Utils.logi("MTKLogger/TagLogManager", "<--tag log compressing thread end");
    }
  }
  
  private class UIHandler
    extends Handler
  {
    private UIHandler() {}
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      Utils.logd("MTKLogger/TagLogManager", " UIHandler handleMessage, what=" + paramMessage.what + ", thread=" + Thread.currentThread().getName());
      switch (paramMessage.what)
      {
      }
      for (;;)
      {
        Utils.logd("MTKLogger/TagLogManager", " MyHandler handleMessage --> end " + paramMessage.what);
        return;
        String str1 = TagLogManager.this.mDataFromExtras.getString("path");
        if (str1 == null)
        {
          Utils.loge("MTKLogger/TagLogManager", "params are not valid! exp_path is null");
          TagLogManager.this.deInit(false);
          return;
        }
        if ("SaveLogManually".equalsIgnoreCase(str1))
        {
          TagLogManager.access$402(TagLogManager.this, true);
          if (!TagLogManager.this.mIsFromReboot) {}
        }
        try
        {
          for (;;)
          {
            Thread.sleep(5000L);
            TagLogManager.this.initToolStatus();
            i = 0;
            Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
            for (;;)
            {
              if (localIterator.hasNext())
              {
                Integer localInteger = (Integer)localIterator.next();
                if (((Boolean)TagLogManager.this.mLogToolStatus.get(localInteger.intValue(), Boolean.valueOf(false))).booleanValue())
                {
                  j = localInteger.intValue();
                  i |= j;
                  continue;
                  String str2 = TagLogManager.this.mDataFromExtras.getString("from_reboot");
                  Utils.logd("MTKLogger/TagLogManager", "isFromReboot ? " + str2);
                  TagLogManager.access$402(TagLogManager.this, false);
                  String str3 = TagLogManager.this.mDataFromExtras.getString("db_filename");
                  String str4 = TagLogManager.this.mDataFromExtras.getString("zz_filename");
                  if ((str3 == null) || (str4 == null))
                  {
                    Utils.logd("MTKLogger/TagLogManager", "params are not valid! exp_file name is null");
                    TagLogManager.this.deInit(false);
                    return;
                  }
                  TagLogManager.access$502(TagLogManager.this, str1);
                  String str5 = str1 + str4;
                  ExceptionInfo localExceptionInfo = new ExceptionInfo();
                  String str6 = str1 + str3;
                  Utils.loge("MTKLogger/TagLogManager", "exp_path: " + str1);
                  Utils.loge("MTKLogger/TagLogManager", "exp_file: " + str6);
                  localExceptionInfo.setmPath(str6);
                  try
                  {
                    localExceptionInfo.initFieldsFromZZ(str5);
                    String str7 = localExceptionInfo.getmType();
                    String str8 = localExceptionInfo.getmDiscription();
                    String str9 = localExceptionInfo.getmProcess();
                    if (str7 != null) {
                      Utils.logi("MTKLogger/TagLogManager", "exp_info.getmType(): " + str7);
                    }
                    if (str8 != null) {
                      Utils.logi("MTKLogger/TagLogManager", "exp_info.getmDiscription(): " + str8);
                    }
                    if (str9 != null) {
                      Utils.logi("MTKLogger/TagLogManager", "exp_info.getmProcess(): " + str9);
                    }
                    if ((str7 != null) && (str7.endsWith("Externel (EE)")))
                    {
                      Utils.logi("MTKLogger/TagLogManager", "expType == External (EE) " + str7);
                      TagLogManager.access$602(TagLogManager.this, true);
                    }
                    if ((str7 == null) || (!str7.endsWith("Kernel (KE)"))) {
                      break;
                    }
                    Utils.logi("MTKLogger/TagLogManager", "expType == Kernel (KE) " + str7);
                    TagLogManager.access$702(TagLogManager.this, true);
                  }
                  catch (IOException localIOException)
                  {
                    Utils.loge("MTKLogger/TagLogManager", "fail to init exception info:" + localIOException.getMessage());
                    TagLogManager.this.deInit(false);
                    return;
                  }
                }
              }
            }
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          int i;
          for (;;)
          {
            localInterruptedException.printStackTrace();
            continue;
            int j = 0;
          }
          if ((i == 0) && (!TagLogManager.this.mIsFromReboot))
          {
            TagLogManager.this.createDialog(302);
            return;
          }
          if (!TagLogManager.this.mManualSaveLog)
          {
            ZipManager.initZippedFilesCount();
            TagLogManager.this.mUIHandler.sendEmptyMessage(306);
            new TagLogManager.TagLogThread(TagLogManager.this, null).start();
            continue;
          }
          TagLogManager.this.createDialog(301);
          TagLogManager.this.mUIHandler.sendEmptyMessageDelayed(209, 120000L);
        }
        continue;
        if (!TagLogManager.this.mIsInputDialogClicked)
        {
          Utils.logw("MTKLogger/TagLogManager", "time out");
          TagLogManager.access$1402(TagLogManager.this, "Cancel");
          TagLogManager.this.deInit(true);
          continue;
          TagLogManager.this.createDialog(303);
          continue;
          TagLogManager.this.dismissProgressDialog();
          TagLogManager.this.createDialog(403);
          continue;
          TagLogManager.this.dismissProgressDialog();
          TagLogManager.this.createDialog(404);
          continue;
          TagLogManager.this.createDialog(302);
          continue;
          TagLogManager.this.dismissProgressDialog();
          TagLogManager.access$1402(TagLogManager.this, "Successful");
          Toast.makeText(TagLogManager.this.mContext, 2131165300, 0).show();
          TagLogManager.this.deInit(true);
          continue;
          TagLogManager.this.dismissProgressDialog();
          TagLogManager.this.createDialog(305);
          continue;
          TagLogManager.this.createProgressDialog();
        }
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.taglog.TagLogManager
 * JD-Core Version:    0.7.0.1
 */