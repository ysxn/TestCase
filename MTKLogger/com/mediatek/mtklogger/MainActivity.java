package com.mediatek.mtklogger;

import android.app.Activity;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mediatek.mtklogger.framework.MTKLoggerManager;
import com.mediatek.mtklogger.settings.SettingsActivity;
import com.mediatek.mtklogger.taglog.TagLogUtils;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity
  extends Activity
{
  private static final int ALPHA_FULL = 255;
  private static final int ALPHA_GRAY = 75;
  private static final int IS_LOG_START = 1;
  private static final int IS_LOG_STOP = 0;
  private static final int MESSAGE_DELAY_TIME = 400;
  private static final int MSG_MONITOR_SDCARD_BAR = 3;
  private static final int MSG_SET_AUTO_START = 4;
  private static final int MSG_TIMER = 1;
  private static final int MSG_WAITING_DIALOG_TIMER = 2;
  private static final int SDCARD_RATIO_BAR_TIMER = 30000;
  private static final int START_STOP_TIMER = 60000;
  private static final String TAG = "MTKLogger/MainActivity";
  private static final int TIMER_PERIOD = 1000;
  private ImageButton mClearLogImageButton;
  private SharedPreferences mDefaultSharedPreferences;
  private float mFreeStorageSize;
  private TextView mFreeStorageText;
  private SparseArray<ImageView> mLogImageViews = new SparseArray();
  private SparseArray<TextView> mLogTextViews = new SparseArray();
  private MTKLoggerManager mManager = null;
  private Handler mMessageHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      int i = 1;
      if (paramAnonymousMessage.what == i)
      {
        MainActivity.access$514(MainActivity.this, 1L);
        MainActivity.this.mTimeText.setText(MainActivity.this.calculateTimer());
      }
      do
      {
        return;
        if (paramAnonymousMessage.what == 2)
        {
          Utils.logd("MTKLogger/MainActivity", "mMessageHandler->handleMessage() msg.what == MSG_WAITING_DIALOG_TIMER");
          MainActivity.this.stopWaitingDialog();
          return;
        }
        if (paramAnonymousMessage.what == 3)
        {
          Utils.logd("MTKLogger/MainActivity", "mMessageHandler->handleMessage() msg.what == MSG_MONITOR_SDCARD_BAR");
          MainActivity.this.refreshSdcardBar();
          return;
        }
      } while (paramAnonymousMessage.what != 4);
      MainActivity localMainActivity = MainActivity.this;
      if (paramAnonymousMessage.arg1 == i) {}
      for (;;)
      {
        localMainActivity.updateLogAutoStart(i);
        return;
        int j = 0;
      }
    }
  };
  private ImageView mMobileLogImage;
  private TextView mMobileLogText;
  private ImageView mModemLogImage;
  private TextView mModemLogText;
  private Timer mMonitorTimer;
  private ImageView mNetworkLogImage;
  private TextView mNetworkLogText;
  private String mSDCardPathStr;
  private String mSavePathStr;
  private TextView mSavePathText;
  private LinearColorBar mSdcardRatioBar;
  private BroadcastReceiver mServiceReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      if (str1.equals("com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"))
      {
        Utils.logd("MTKLogger/MainActivity", "ACTION_LOG_STATE_CHANGED");
        MainActivity.this.stopWaitingDialog();
        String str2 = paramAnonymousIntent.getStringExtra("fail_reason");
        if ((str2 != null) && (!"".equals(str2)))
        {
          Utils.logd("MTKLogger/MainActivity", "ACTION_LOG_STATE_CHANGED : failReason = " + str2);
          Toast.makeText(MainActivity.this, MainActivity.this.analyzeReason(str2), 0).show();
        }
        MainActivity.this.updateUI();
      }
      do
      {
        return;
        if (str1.equals("stage_event"))
        {
          Utils.logd("MTKLogger/MainActivity", "EXTRA_RUNNING_STAGE_CHANGE_EVENT");
          MainActivity.this.changeWaitingDialog();
          return;
        }
      } while (!str1.equals("extra_key_from_taglog"));
      Utils.logd("MTKLogger/MainActivity", "EXTRA_KEY_FROM_TAGLOG");
      MainActivity.this.finish();
    }
  };
  private ImageButton mSettingsImageButton;
  private MenuItem mSettingsMenuItem;
  private SharedPreferences mSharedPreferences;
  private ToggleButton mStartStopToggleButton;
  private TextView mStorageChartLabelText;
  private BroadcastReceiver mStorageStatusReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Utils.logd("MTKLogger/MainActivity", "Storage status changed, update UI now");
      MainActivity.this.mMessageHandler.sendEmptyMessage(3);
      MainActivity.this.updateUI();
    }
  };
  private ImageButton mTagImageButton;
  private long mTimeMillisecond = 0L;
  private TextView mTimeText;
  private Timer mTimer;
  private float mUsedStorageSize;
  private TextView mUsedStorageText;
  private ProgressDialog mWaitingDialog;
  private Timer mWaitingDialogTimer;
  
  private void alertLowStorageDialog()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this).setTitle(2131165213);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(30);
    localBuilder.setMessage(getString(2131165214, arrayOfObject)).setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    }).show();
  }
  
  private String analyzeReason(String paramString)
  {
    String str1 = "";
    int i = 0;
    label30:
    int k;
    int n;
    for (;;)
    {
      int j;
      if (i < paramString.length())
      {
        j = paramString.indexOf(":", i);
        if (j != -1) {
          break label30;
        }
      }
      do
      {
        do
        {
          return str1;
          try
          {
            int i3 = Integer.parseInt(paramString.substring(i, j));
            k = i3;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            for (;;)
            {
              int m;
              String str2;
              int i2;
              k = -1;
            }
          }
        } while (!Utils.LOG_TYPE_SET.contains(Integer.valueOf(k)));
        m = j + 1;
        n = paramString.indexOf(";", m);
      } while (n == -1);
      str2 = paramString.substring(m, n);
      try
      {
        i2 = ((Integer)Utils.FAIL_REASON_DETAIL_MAP.get(str2)).intValue();
        i1 = i2;
      }
      catch (NullPointerException localNullPointerException)
      {
        int i1;
        for (;;)
        {
          i1 = -1;
        }
        str1 = str1 + getString(((Integer)Utils.LOG_NAME_MAP.get(k)).intValue()) + " : " + getString(i1) + "\n";
        i = n + 1;
      }
      if (i1 == -1)
      {
        (n + 1);
        return str1;
      }
    }
  }
  
  private String calculateTimer()
  {
    int i = (int)this.mTimeMillisecond / 3600;
    if (i > 9999)
    {
      Utils.loge("MTKLogger/MainActivity", "There is something wrong with time record! The hour is " + i);
      this.mTimeMillisecond = 0L;
      Utils.loge("MTKLogger/MainActivity", "There is something wrong with time record!");
    }
    long l;
    String str1;
    label132:
    StringBuilder localStringBuilder2;
    if ((i >= 10) && (i <= 99))
    {
      this.mTimeText.setTextSize(70.0F);
      int j = (int)this.mTimeMillisecond / 60 % 60;
      l = this.mTimeMillisecond % 60L;
      StringBuilder localStringBuilder1 = new StringBuilder().append("").append(i).append(":");
      if (j >= 10) {
        break label237;
      }
      str1 = "0";
      localStringBuilder2 = localStringBuilder1.append(str1).append(j).append(":");
      if (l >= 10L) {
        break label244;
      }
    }
    label237:
    label244:
    for (String str2 = "0";; str2 = "")
    {
      return str2 + l;
      if ((i >= 100) && (i <= 999))
      {
        this.mTimeText.setTextSize(60.0F);
        break;
      }
      if (i >= 1000)
      {
        this.mTimeText.setTextSize(50.0F);
        break;
      }
      this.mTimeText.setTextSize(80.0F);
      break;
      str1 = "";
      break label132;
    }
  }
  
  private void changeWaitingDialog()
  {
    int i = this.mManager.getCurrentRunningStage();
    Utils.logd("MTKLogger/MainActivity", "changeWaitingDialog() -> currentRunningStage is " + i);
    if (i == 0) {
      stopWaitingDialog();
    }
    for (;;)
    {
      return;
      String str1 = "";
      String str2 = "";
      if (i == 1)
      {
        str1 = getString(2131165215);
        str2 = getString(2131165216);
      }
      while (this.mWaitingDialog == null)
      {
        this.mWaitingDialog = ProgressDialog.show(this, str1, str2, true, false);
        if (this.mWaitingDialogTimer != null)
        {
          this.mWaitingDialogTimer.cancel();
          this.mWaitingDialogTimer = null;
        }
        this.mWaitingDialogTimer = new Timer(true);
        this.mWaitingDialogTimer.schedule(new TimerTask()
        {
          public void run()
          {
            MainActivity.this.mMessageHandler.sendEmptyMessage(2);
          }
        }, 60000L, 60000L);
        return;
        if (i == 2)
        {
          str1 = getString(2131165217);
          str2 = getString(2131165218);
        }
        else if (i == 3)
        {
          str1 = getString(2131165219);
          str2 = getString(2131165220);
        }
      }
    }
  }
  
  private boolean checkPath()
  {
    boolean bool = true;
    if (!new File(this.mSDCardPathStr).exists())
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mSDCardPathStr;
      Toast.makeText(this, getString(2131165212, arrayOfObject), 0).show();
      bool = false;
    }
    String str = Utils.getVolumeState(this, this.mSDCardPathStr);
    Utils.logd("MTKLogger/MainActivity", "-->checkPath(), path=" + this.mSDCardPathStr + ", exist?" + bool + ", volumeState=" + str);
    return (bool) && ("mounted".equals(str));
  }
  
  private void checkTaglogCompressing()
  {
    if (this.mSharedPreferences.getBoolean("tag_log_compressing", false))
    {
      Utils.logd("MTKLogger/MainActivity", "checkTaglogCompressing() ? true");
      Intent localIntent = new Intent();
      localIntent.setAction("com.mediatek.log2server.EXCEPTION_HAPPEND");
      localIntent.putExtra("extra_key_from_main_activity", true);
      sendBroadcast(localIntent);
    }
  }
  
  private void clearLogs()
  {
    if (!checkPath()) {
      return;
    }
    Intent localIntent = new Intent(this, LogFolderListActivity.class);
    localIntent.putExtra("rootpath", this.mSavePathStr);
    if (this.mStartStopToggleButton.isChecked())
    {
      Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        int i;
        label105:
        int k;
        if (1 == this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger.intValue()), 0))
        {
          i = 1;
          if (i == 0) {
            break label347;
          }
          if (localInteger.intValue() != 2) {
            break label361;
          }
          int j = this.mManager.getModemLogRunningDetailStatus();
          Utils.logd("MTKLogger/MainActivity", "modemLogRunningDetailStatus : " + j);
          if ((j != 1) && (j != 3)) {
            break label349;
          }
          k = 1;
          label169:
          if (k != 0)
          {
            Utils.logd("MTKLogger/MainActivity", "Modem one is running!");
            File localFile3 = TagLogUtils.getCurrentLogFolderFromPath(this.mSavePathStr + (String)Utils.LOG_PATH_MAP.get(localInteger.intValue()));
            if ((localFile3 != null) && (localFile3.exists())) {
              localIntent.putExtra((String)LogFolderListActivity.EXTRA_FILTER_FILES_KEY.get(localInteger.intValue()), localFile3.getAbsolutePath());
            }
          }
          if ((j != 2) && (j != 3)) {
            break label355;
          }
        }
        label347:
        label349:
        label355:
        for (int m = 1;; m = 0)
        {
          if (m == 0) {
            break label359;
          }
          Utils.logd("MTKLogger/MainActivity", "DualModem is running!");
          File localFile2 = TagLogUtils.getCurrentLogFolderFromPath(this.mSavePathStr + "dualmdlog");
          if ((localFile2 == null) || (!localFile2.exists())) {
            break;
          }
          localIntent.putExtra("filterDualModemFile", localFile2.getAbsolutePath());
          break;
          i = 0;
          break label105;
          break;
          k = 0;
          break label169;
        }
        label359:
        continue;
        label361:
        File localFile1 = TagLogUtils.getCurrentLogFolderFromPath(this.mSavePathStr + (String)Utils.LOG_PATH_MAP.get(localInteger.intValue()));
        if ((localFile1 != null) && (localFile1.exists())) {
          localIntent.putExtra((String)LogFolderListActivity.EXTRA_FILTER_FILES_KEY.get(localInteger.intValue()), localFile1.getAbsolutePath());
        }
      }
    }
    startActivity(localIntent);
    this.mMessageHandler.sendEmptyMessage(3);
  }
  
  private void findViews()
  {
    this.mSettingsImageButton = ((ImageButton)findViewById(2131296289));
    this.mModemLogImage = ((ImageView)findViewById(2131296294));
    this.mModemLogText = ((TextView)findViewById(2131296295));
    this.mLogImageViews.put(2, this.mModemLogImage);
    this.mLogTextViews.put(2, this.mModemLogText);
    this.mMobileLogImage = ((ImageView)findViewById(2131296292));
    this.mMobileLogText = ((TextView)findViewById(2131296293));
    this.mLogImageViews.put(1, this.mMobileLogImage);
    this.mLogTextViews.put(1, this.mMobileLogText);
    this.mNetworkLogImage = ((ImageView)findViewById(2131296296));
    this.mNetworkLogText = ((TextView)findViewById(2131296297));
    this.mLogImageViews.put(4, this.mNetworkLogImage);
    this.mLogTextViews.put(4, this.mNetworkLogText);
    this.mTimeText = ((TextView)findViewById(2131296291));
    this.mSavePathText = ((TextView)findViewById(2131296298));
    this.mSdcardRatioBar = ((LinearColorBar)findViewById(2131296300));
    this.mStorageChartLabelText = ((TextView)findViewById(2131296302));
    this.mUsedStorageText = ((TextView)findViewById(2131296301));
    this.mFreeStorageText = ((TextView)findViewById(2131296303));
    this.mTagImageButton = ((ImageButton)findViewById(2131296305));
    this.mStartStopToggleButton = ((ToggleButton)findViewById(2131296307));
    this.mClearLogImageButton = ((ImageButton)findViewById(2131296306));
  }
  
  private void initViews()
  {
    this.mManager = new MTKLoggerManager(this);
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.mSharedPreferences = getSharedPreferences("log_settings", 0);
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      TextView localTextView = (TextView)this.mLogTextViews.get(localInteger.intValue());
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = getString(((Integer)Utils.LOG_NAME_MAP.get(localInteger.intValue())).intValue());
      localTextView.setText(getString(2131165207, arrayOfObject));
    }
    String str = getIntent().getStringExtra("reason_start");
    if ((str != null) && ("low_storage".equals(str)) && (Utils.checkLogStarted(this.mSharedPreferences))) {
      alertLowStorageDialog();
    }
  }
  
  private boolean isAutoTest()
  {
    String str = SystemProperties.get("ro.monkey", "false");
    Utils.logi("MTKLogger/MainActivity", "isAutoTest()-> Monkey running status is " + str);
    return "true".equalsIgnoreCase(str);
  }
  
  private void monitorSdcardRatioBar()
  {
    if (this.mMonitorTimer != null) {
      return;
    }
    this.mMonitorTimer = new Timer(true);
    this.mMonitorTimer.schedule(new TimerTask()
    {
      public void run()
      {
        MainActivity.this.mMessageHandler.sendEmptyMessage(3);
      }
    }, 0L, 30000L);
  }
  
  private void refreshSdcardBar()
  {
    Utils.logd("MTKLogger/MainActivity", "-->refreshSdcardBar()");
    if (!checkPath())
    {
      Utils.logd("MTKLogger/MainActivity", "Selected log path is unavailable, reset storage info");
      this.mSdcardRatioBar.setGradientPaint(0.0F, 0.0F);
      this.mSdcardRatioBar.setRatios(0.0F, 0.0F, 0.0F);
      this.mUsedStorageText.setText("0M " + getString(2131165204));
      this.mFreeStorageText.setText("0M " + getString(2131165205));
      return;
    }
    StatFs localStatFs = new StatFs(this.mSDCardPathStr);
    int i = localStatFs.getBlockSize() / 1024;
    this.mFreeStorageSize = (i * localStatFs.getAvailableBlocks());
    this.mUsedStorageSize = (i * localStatFs.getBlockCount() - this.mFreeStorageSize);
    Utils.logd("MTKLogger/MainActivity", " mSDCardPathStr=" + this.mSDCardPathStr + ", free size=" + this.mFreeStorageSize + ", used size=" + this.mUsedStorageSize);
    float f = this.mUsedStorageSize / (this.mFreeStorageSize + this.mUsedStorageSize);
    this.mSdcardRatioBar.setShowingGreen(false);
    long l = Utils.getFileSize(this.mSavePathStr);
    if (l <= 1024L) {
      l = 1024L;
    }
    this.mSdcardRatioBar.setGradientPaint(f - (float)(l / 1024L) / (this.mFreeStorageSize + this.mUsedStorageSize), f);
    this.mSdcardRatioBar.setRatios(0.0F, f, 1.0F - f);
    this.mUsedStorageText.setText((int)(this.mUsedStorageSize / 1024.0F) + "M " + getString(2131165204));
    this.mFreeStorageText.setText((int)(this.mFreeStorageSize / 1024.0F) + "M " + getString(2131165205));
  }
  
  private void removeManualTitle()
  {
    ((LinearLayout)findViewById(2131296285)).removeView(findViewById(2131296286));
  }
  
  private void setButtonStatus(boolean paramBoolean)
  {
    if (this.mSettingsMenuItem != null) {
      this.mSettingsMenuItem.setEnabled(paramBoolean);
    }
    this.mTagImageButton.setEnabled(paramBoolean);
    this.mStartStopToggleButton.setEnabled(paramBoolean);
    this.mClearLogImageButton.setEnabled(paramBoolean);
  }
  
  private void setListeners()
  {
    if (this.mSettingsImageButton != null) {
      this.mSettingsImageButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Utils.logd("MTKLogger/MainActivity", "Settings button is clicked!");
          Intent localIntent = new Intent(MainActivity.this, SettingsActivity.class);
          MainActivity.this.startActivity(localIntent);
        }
      });
    }
    this.mStartStopToggleButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        int i = 1;
        Utils.logd("MTKLogger/MainActivity", "StartStopToggleButton button is clicked!");
        ToggleButton localToggleButton;
        boolean bool;
        int k;
        if ((paramAnonymousView instanceof ToggleButton))
        {
          localToggleButton = (ToggleButton)paramAnonymousView;
          bool = localToggleButton.isChecked();
          Utils.logd("MTKLogger/MainActivity", "StartStopToggleButton button is checked ? " + bool);
          k = 0;
          if (!bool) {
            break label382;
          }
          Iterator localIterator2 = Utils.LOG_TYPE_SET.iterator();
          if (localIterator2.hasNext())
          {
            Integer localInteger2 = (Integer)localIterator2.next();
            if (MainActivity.this.mDefaultSharedPreferences.getBoolean((String)SettingsActivity.KEY_LOG_SWITCH_MAP.get(localInteger2.intValue()), i)) {}
            for (int i8 = localInteger2.intValue();; i8 = 0)
            {
              k |= i8;
              break;
            }
          }
          if ((k & 0x1) != 0)
          {
            if ((0 == 0) && (!MainActivity.this.mDefaultSharedPreferences.getBoolean("mobilelog_androidlog", i))) {
              break label297;
            }
            int i2 = i;
            if ((i2 == 0) && (!MainActivity.this.mDefaultSharedPreferences.getBoolean("mobilelog_kernellog", i))) {
              break label303;
            }
            int i4 = i;
            label203:
            if ((i4 == 0) && (!MainActivity.this.mDefaultSharedPreferences.getBoolean("mobilelog_btlog", i))) {
              break label309;
            }
            int i6 = i;
            label229:
            if (i6 == 0)
            {
              k--;
              Toast.makeText(MainActivity.this, MainActivity.this.getString(2131165201), i).show();
            }
          }
          if (k != 0) {
            break label321;
          }
          if (bool) {
            break label315;
          }
        }
        label297:
        label303:
        label309:
        label315:
        int i1;
        for (int n = i;; i1 = 0)
        {
          localToggleButton.setChecked(n);
          Toast.makeText(MainActivity.this, MainActivity.this.getString(2131165202), 0).show();
          return;
          int i3 = 0;
          break;
          int i5 = 0;
          break label203;
          int i7 = 0;
          break label229;
        }
        label321:
        MainActivity.this.mManager.startLog(k);
        MainActivity.this.changeWaitingDialog();
        Handler localHandler1 = MainActivity.this.mMessageHandler;
        Handler localHandler2 = MainActivity.this.mMessageHandler;
        if (bool) {}
        for (;;)
        {
          localHandler1.sendMessageDelayed(localHandler2.obtainMessage(4, i, 0), 400L);
          return;
          label382:
          Iterator localIterator1 = Utils.LOG_TYPE_SET.iterator();
          if (localIterator1.hasNext())
          {
            Integer localInteger1 = (Integer)localIterator1.next();
            if (i == MainActivity.this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger1.intValue()), i)) {}
            for (int m = localInteger1.intValue();; m = 0)
            {
              k |= m;
              break;
            }
          }
          MainActivity.this.mManager.stopLog(k);
          break;
          int j = 0;
        }
      }
    });
    this.mClearLogImageButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Utils.logd("MTKLogger/MainActivity", "ClearLogs button is clicked!");
        MainActivity.this.clearLogs();
      }
    });
    this.mTagImageButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Utils.logd("MTKLogger/MainActivity", "Taglog button is clicked!");
        MainActivity.this.tagLogs();
      }
    });
  }
  
  private void startTimer()
  {
    long l = this.mSharedPreferences.getLong("begin_recording_time", 0L);
    if (l == 0L) {}
    for (this.mTimeMillisecond = 0L;; this.mTimeMillisecond = ((System.currentTimeMillis() - l) / 1000L))
    {
      int i = this.mSharedPreferences.getInt("system_time_reset", 0);
      if (i != 0)
      {
        this.mSharedPreferences.edit().putInt("system_time_reset", 0).commit();
        Toast.makeText(this, 2131165327, 1).show();
      }
      Utils.logv("MTKLogger/MainActivity", "-->startTimer(), startTime=" + l + ", timeResetFlag=" + i);
      this.mTimeText.setText(calculateTimer());
      stopTimer();
      this.mTimer = new Timer(true);
      this.mTimer.schedule(new TimerTask()
      {
        public void run()
        {
          MainActivity.this.mMessageHandler.sendEmptyMessage(1);
        }
      }, 1000L, 1000L);
      return;
    }
  }
  
  private void stopTimer()
  {
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
  }
  
  private void stopWaitingDialog()
  {
    if (this.mWaitingDialog != null)
    {
      this.mWaitingDialog.cancel();
      this.mWaitingDialog = null;
    }
    if (this.mWaitingDialogTimer != null)
    {
      this.mWaitingDialogTimer.cancel();
      this.mWaitingDialogTimer = null;
    }
  }
  
  private void tagLogs()
  {
    if (!this.mStartStopToggleButton.isChecked()) {
      return;
    }
    this.mManager.tagLog();
  }
  
  private void updateLogAutoStart(boolean paramBoolean)
  {
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    for (;;)
    {
      Integer localInteger;
      if (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        if (!this.mDefaultSharedPreferences.getBoolean((String)SettingsActivity.KEY_LOG_SWITCH_MAP.get(localInteger.intValue()), true)) {
          continue;
        }
        Utils.logd("MTKLogger/MainActivity", "Log(" + localInteger + ") is setAutoStart ? " + paramBoolean);
        if (this.mManager != null) {}
      }
      else
      {
        return;
      }
      this.mManager.setAutoStart(localInteger.intValue(), paramBoolean);
      this.mDefaultSharedPreferences.edit().putBoolean((String)SettingsActivity.KEY_LOG_AUTOSTART_MAP.get(localInteger.intValue()), paramBoolean).commit();
    }
  }
  
  private void updateUI()
  {
    Utils.logd("MTKLogger/MainActivity", "-->updateUI(), Update UI Start");
    this.mSDCardPathStr = Utils.getCurrentLogPath(this);
    Utils.logd("MTKLogger/MainActivity", " mSDCardPathStr=" + this.mSDCardPathStr);
    this.mSavePathStr = (this.mSDCardPathStr + "/mtklog/");
    this.mSavePathText.setText(getString(2131165203) + ": " + this.mSavePathStr);
    TextView localTextView1 = this.mStorageChartLabelText;
    int i;
    if ("/mnt/sdcard2".equals(Utils.getLogPathType()))
    {
      i = 2131165211;
      localTextView1.setText(i);
      if (!isAutoTest()) {
        break label157;
      }
      setButtonStatus(false);
    }
    label1035:
    for (;;)
    {
      return;
      i = 2131165210;
      break;
      label157:
      setButtonStatus(true);
      changeWaitingDialog();
      boolean bool1 = Utils.checkLogStarted(this.mSharedPreferences);
      this.mStartStopToggleButton.setChecked(bool1);
      label200:
      Integer localInteger2;
      if (bool1)
      {
        startTimer();
        Iterator localIterator1 = Utils.LOG_TYPE_SET.iterator();
        if (!localIterator1.hasNext()) {
          break label615;
        }
        localInteger2 = (Integer)localIterator1.next();
        if (1 != this.mSharedPreferences.getInt((String)Utils.KEY_STATUS_MAP.get(localInteger2.intValue()), 0)) {
          break label397;
        }
      }
      label397:
      for (int k = 1;; k = 0)
      {
        if ((!bool1) || (k == 0)) {
          break label403;
        }
        ((ImageView)this.mLogImageViews.get(localInteger2.intValue())).setImageResource(2130837516);
        TextView localTextView4 = (TextView)this.mLogTextViews.get(localInteger2.intValue());
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = getString(((Integer)Utils.LOG_NAME_MAP.get(localInteger2.intValue())).intValue());
        localTextView4.setText(getString(2131165206, arrayOfObject2));
        ((ImageView)this.mLogImageViews.get(localInteger2.intValue())).setAlpha(255);
        ((TextView)this.mLogTextViews.get(localInteger2.intValue())).setAlpha(255.0F);
        break label200;
        stopTimer();
        break;
      }
      label403:
      boolean bool4 = this.mDefaultSharedPreferences.getBoolean((String)SettingsActivity.KEY_LOG_SWITCH_MAP.get(localInteger2.intValue()), true);
      ((ImageView)this.mLogImageViews.get(localInteger2.intValue())).setImageResource(2130837515);
      TextView localTextView2 = (TextView)this.mLogTextViews.get(localInteger2.intValue());
      int m;
      label477:
      int n;
      label548:
      TextView localTextView3;
      if (bool4)
      {
        m = 2131165207;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = getString(((Integer)Utils.LOG_NAME_MAP.get(localInteger2.intValue())).intValue());
        localTextView2.setText(getString(m, arrayOfObject1));
        ImageView localImageView = (ImageView)this.mLogImageViews.get(localInteger2.intValue());
        if (!bool4) {
          break label600;
        }
        n = 255;
        localImageView.setAlpha(n);
        localTextView3 = (TextView)this.mLogTextViews.get(localInteger2.intValue());
        if (!bool4) {
          break label607;
        }
      }
      label600:
      label607:
      for (float f = 255.0F;; f = 75.0F)
      {
        localTextView3.setAlpha(f);
        break;
        m = 2131165208;
        break label477;
        n = 75;
        break label548;
      }
      label615:
      String str = Build.TYPE;
      boolean bool2 = false;
      if (str != null)
      {
        Utils.logd("MTKLogger/MainActivity", "Build type :" + str);
        if (str.trim().equalsIgnoreCase("user")) {
          bool2 = this.mSharedPreferences.getBoolean("tagLogEnable", false);
        }
      }
      else
      {
        if ((!bool1) || (!bool2)) {
          break label997;
        }
        this.mTagImageButton.setEnabled(true);
        this.mTagImageButton.setAlpha(255);
        label710:
        Iterator localIterator2 = Utils.LOG_TYPE_SET.iterator();
        File localFile3;
        do
        {
          boolean bool3 = localIterator2.hasNext();
          j = 0;
          if (!bool3) {
            break;
          }
          Integer localInteger1 = (Integer)localIterator2.next();
          localFile3 = new File(this.mSavePathStr + (String)Utils.LOG_PATH_MAP.get(localInteger1.intValue()));
        } while ((!localFile3.exists()) || (localFile3.listFiles().length <= 0));
        int j = 1;
        File localFile1 = new File(this.mSavePathStr + "dualmdlog");
        if ((localFile1.exists()) && (localFile1.listFiles().length > 0)) {
          j = 1;
        }
        File localFile2 = new File(this.mSavePathStr + "taglog");
        if ((localFile2.exists()) && (localFile2.listFiles().length > 0)) {
          j = 1;
        }
        if (j == 0) {
          break label1017;
        }
        this.mClearLogImageButton.setEnabled(true);
        this.mClearLogImageButton.setAlpha(255);
      }
      for (;;)
      {
        if (TextUtils.isEmpty(this.mSharedPreferences.getString("waiting_sd_ready_reason", ""))) {
          break label1035;
        }
        Utils.logi("MTKLogger/MainActivity", "Still waiting SD ready");
        Toast.makeText(this, 2131165286, 1).show();
        return;
        bool2 = this.mSharedPreferences.getBoolean("tagLogEnable", true);
        break;
        label997:
        this.mTagImageButton.setEnabled(false);
        this.mTagImageButton.setAlpha(75);
        break label710;
        label1017:
        this.mClearLogImageButton.setEnabled(false);
        this.mClearLogImageButton.setAlpha(75);
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    Utils.logd("MTKLogger/MainActivity", "onCreate");
    super.onCreate(paramBundle);
    setContentView(2130903048);
    if (Utils.ANDROID_VERSION_NUMBER > 3.999D) {
      removeManualTitle();
    }
    findViews();
    initViews();
    setListeners();
    updateUI();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    if (Utils.ANDROID_VERSION_NUMBER < 3.999D) {
      return true;
    }
    this.mSettingsMenuItem = paramMenu.add(getString(2131165200));
    this.mSettingsMenuItem.setShowAsAction(2);
    this.mSettingsMenuItem.setIcon(2130837512);
    if (isAutoTest())
    {
      this.mSettingsMenuItem.setEnabled(false);
      return true;
    }
    this.mSettingsMenuItem.setEnabled(true);
    return true;
  }
  
  protected void onDestroy()
  {
    Utils.logd("MTKLogger/MainActivity", "onDestroy");
    if (this.mManager != null)
    {
      this.mManager.free();
      this.mManager = null;
    }
    stopWaitingDialog();
    super.onDestroy();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (Utils.ANDROID_VERSION_NUMBER < 3.999D) {
      return true;
    }
    if ((Utils.USER_ID != -1) && (Utils.USER_ID != 0))
    {
      Utils.logi("MTKLogger/MainActivity", "In multi user case, only device owner can change log configuration");
      Toast.makeText(this, 2131165234, 0).show();
      return true;
    }
    Utils.logd("MTKLogger/MainActivity", "SettingsActivity open!");
    startActivity(new Intent(this, SettingsActivity.class));
    return true;
  }
  
  protected void onPause()
  {
    Utils.logd("MTKLogger/MainActivity", "onPause");
    super.onPause();
    if (this.mMonitorTimer != null)
    {
      this.mMonitorTimer.cancel();
      this.mMonitorTimer = null;
    }
    stopTimer();
    unregisterReceiver(this.mServiceReceiver);
    unregisterReceiver(this.mStorageStatusReceiver);
  }
  
  protected void onResume()
  {
    Utils.logd("MTKLogger/MainActivity", "onResume");
    IntentFilter localIntentFilter1 = new IntentFilter();
    localIntentFilter1.addAction("com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED");
    localIntentFilter1.addAction("stage_event");
    localIntentFilter1.addAction("extra_key_from_taglog");
    registerReceiver(this.mServiceReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter();
    localIntentFilter2.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
    localIntentFilter2.addAction("android.intent.action.MEDIA_EJECT");
    localIntentFilter2.addAction("android.intent.action.MEDIA_REMOVED");
    localIntentFilter2.addAction("android.intent.action.MEDIA_UNMOUNTED");
    localIntentFilter2.addAction("android.intent.action.MEDIA_MOUNTED");
    localIntentFilter2.addDataScheme("file");
    registerReceiver(this.mStorageStatusReceiver, localIntentFilter2);
    checkTaglogCompressing();
    updateUI();
    monitorSdcardRatioBar();
    super.onResume();
  }
  
  protected void onStop()
  {
    Utils.logd("MTKLogger/MainActivity", "onStop");
    super.onStop();
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.MainActivity
 * JD-Core Version:    0.7.0.1
 */