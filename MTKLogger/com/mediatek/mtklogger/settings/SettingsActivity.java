package com.mediatek.mtklogger.settings;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.StatFs;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.SparseArray;
import com.mediatek.mtklogger.exceptionreporter.ExceptionReportManager;
import com.mediatek.mtklogger.framework.MTKLoggerManager;
import com.mediatek.mtklogger.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity
  extends PreferenceActivity
  implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener
{
  private static final String ACTION_DUMP = "com.mediatek.logdumper.DUMP_ACTION";
  private static final String EXTRA_TARGET_VALUE = "target_value";
  public static final String KEY_ADVANCED_LOG_STORAGE_LOCATION = "log_storage_location";
  public static final String KEY_ADVANCED_RUN_COMMAND = "run_command";
  public static final String KEY_ADVANCED_RUN_EXCEPTION_HISTORY = "run_exception_history";
  public static final String KEY_ADVANCED_SETTINGS_CATEGORY = "advanced_settings_category";
  public static final String KEY_EXCEPTIONREPORTER_ENABLE = "exceptionreporter_enable";
  public static final SparseArray<String> KEY_LOG_AUTOSTART_MAP;
  public static final SparseArray<String> KEY_LOG_SWITCH_MAP = new SparseArray();
  public static final String KEY_MB_AUTOSTART = "mobilelog_autostart";
  public static final String KEY_MB_SWITCH = "mobilelog_switch";
  public static final String KEY_MD_AUTOSTART = "modemlog_autostart";
  public static final String KEY_MD_SWITCH = "modemlog_switch";
  public static final String KEY_NT_AUTOSTART = "networklog_autostart";
  public static final String KEY_NT_SWITCH = "networklog_switch";
  public static final String KEY_TAGLOG_ENABLE = "taglog_enable";
  public static final String KEY_UI_DEBUG_MODE_ENABLE = "ui_debug_mode_enable";
  private final String TAG = "MTKLogger/SettingsActivity";
  private SharedPreferences mDefaultSharedPreferences;
  PreferenceScreen mExceptionHistory;
  private CheckBoxPreference mExceptionReporterEnable;
  private ListPreference mLogStorageLocationList;
  private MTKLoggerManager mManager = null;
  private SwitchPreference mMbSwitchPre;
  private SwitchPreference mMdSwitchPre;
  private SwitchPreference mNtSwitchPre;
  private EditTextPreference mRunCmdEditText;
  private int mSdcardSize = 0;
  private SharedPreferences mSharedPreferences;
  private CheckBoxPreference mTaglogEnable;
  private CheckBoxPreference mUIDebugModeEnable;
  
  static
  {
    KEY_LOG_SWITCH_MAP.put(1, "mobilelog_switch");
    KEY_LOG_SWITCH_MAP.put(2, "modemlog_switch");
    KEY_LOG_SWITCH_MAP.put(4, "networklog_switch");
    KEY_LOG_AUTOSTART_MAP = new SparseArray();
    KEY_LOG_AUTOSTART_MAP.put(1, "mobilelog_autostart");
    KEY_LOG_AUTOSTART_MAP.put(2, "modemlog_autostart");
    KEY_LOG_AUTOSTART_MAP.put(4, "networklog_autostart");
  }
  
  private void findViews()
  {
    this.mMbSwitchPre = ((SwitchPreference)findPreference("mobilelog_switch"));
    this.mMdSwitchPre = ((SwitchPreference)findPreference("modemlog_switch"));
    this.mNtSwitchPre = ((SwitchPreference)findPreference("networklog_switch"));
    this.mTaglogEnable = ((CheckBoxPreference)findPreference("taglog_enable"));
    this.mExceptionReporterEnable = ((CheckBoxPreference)findPreference("exceptionreporter_enable"));
    this.mUIDebugModeEnable = ((CheckBoxPreference)findPreference("ui_debug_mode_enable"));
    this.mLogStorageLocationList = ((ListPreference)findPreference("log_storage_location"));
    this.mRunCmdEditText = ((EditTextPreference)findPreference("run_command"));
    this.mExceptionHistory = ((PreferenceScreen)getPreferenceScreen().findPreference("run_exception_history"));
  }
  
  private void initViews()
  {
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.mSharedPreferences = getSharedPreferences("log_settings", 0);
    this.mManager = new MTKLoggerManager(this);
    Utils.logd("MTKLogger/SettingsActivity", "Hide log2server_app");
    PreferenceCategory localPreferenceCategory = (PreferenceCategory)findPreference("advanced_settings_category");
    localPreferenceCategory.removePreference(this.mExceptionReporterEnable);
    this.mExceptionReporterEnable = null;
    localPreferenceCategory.removePreference(this.mExceptionHistory);
    this.mExceptionHistory = null;
    String str = Build.TYPE;
    if ((str != null) && (!str.trim().equalsIgnoreCase("eng")))
    {
      Utils.logd("MTKLogger/SettingsActivity", "initViews() BuildType : " + str);
      ((PreferenceCategory)findPreference("advanced_settings_category")).removePreference(this.mUIDebugModeEnable);
      this.mUIDebugModeEnable = null;
      if (!this.mSharedPreferences.getBoolean("tagLogEnable", false))
      {
        this.mTaglogEnable.setChecked(false);
        if (this.mExceptionReporterEnable != null) {
          this.mExceptionReporterEnable.setChecked(false);
        }
        Settings.System.putInt(getContentResolver(), "log2server_dialog_show", 0);
      }
    }
    for (;;)
    {
      setSdcardSize();
      updateUI();
      setLogStorageEntries();
      if (this.mExceptionReporterEnable == null) {
        break label290;
      }
      this.mExceptionReporterEnable.setEnabled(this.mTaglogEnable.isChecked());
      if (this.mExceptionReporterEnable.isEnabled()) {
        break;
      }
      return;
      this.mTaglogEnable.setChecked(this.mSharedPreferences.getBoolean("tagLogEnable", true));
    }
    for (;;)
    {
      try
      {
        CheckBoxPreference localCheckBoxPreference = this.mExceptionReporterEnable;
        if (Settings.System.getInt(getContentResolver(), "log2server_dialog_show") != 1) {
          continue;
        }
        bool2 = true;
        localCheckBoxPreference.setChecked(bool2);
      }
      catch (Settings.SettingNotFoundException localSettingNotFoundException)
      {
        boolean bool2;
        label290:
        PreferenceScreen localPreferenceScreen;
        localSettingNotFoundException.printStackTrace();
        continue;
        boolean bool1 = false;
        continue;
      }
      if (this.mExceptionHistory != null)
      {
        localPreferenceScreen = this.mExceptionHistory;
        if ((!this.mExceptionReporterEnable.isChecked()) || (!this.mTaglogEnable.isChecked())) {
          continue;
        }
        bool1 = true;
        localPreferenceScreen.setEnabled(bool1);
      }
      if (this.mSharedPreferences.getBoolean("hasStartedDebugMode", false)) {
        break;
      }
      this.mSharedPreferences.edit().putBoolean("hasStartedDebugMode", true).commit();
      if (this.mUIDebugModeEnable == null) {
        break;
      }
      this.mUIDebugModeEnable.setChecked(false);
      setUIDebugMode(false);
      return;
      bool2 = false;
    }
  }
  
  private void setListeners()
  {
    this.mMbSwitchPre.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent(SettingsActivity.this, MobileLogSettings.class);
        SwitchPreference localSwitchPreference = SettingsActivity.this.mMbSwitchPre;
        if (!SettingsActivity.this.mMbSwitchPre.isChecked()) {}
        for (boolean bool = true;; bool = false)
        {
          localSwitchPreference.setChecked(bool);
          localIntent.putExtra("isSwitchChecked", SettingsActivity.this.mMbSwitchPre.isChecked());
          SettingsActivity.this.setSdcardSize();
          localIntent.putExtra("sdcardSize", SettingsActivity.this.mSdcardSize);
          SettingsActivity.this.startActivity(localIntent);
          return false;
        }
      }
    });
    this.mMdSwitchPre.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SwitchPreference localSwitchPreference = SettingsActivity.this.mMdSwitchPre;
        if (!SettingsActivity.this.mMdSwitchPre.isChecked()) {}
        for (boolean bool = true;; bool = false)
        {
          localSwitchPreference.setChecked(bool);
          Intent localIntent = new Intent(SettingsActivity.this, ModemLogSettings.class);
          localIntent.putExtra("isSwitchChecked", SettingsActivity.this.mMdSwitchPre.isChecked());
          SettingsActivity.this.setSdcardSize();
          localIntent.putExtra("sdcardSize", SettingsActivity.this.mSdcardSize);
          SettingsActivity.this.startActivity(localIntent);
          return false;
        }
      }
    });
    this.mNtSwitchPre.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent(SettingsActivity.this, NetworkLogSettings.class);
        SwitchPreference localSwitchPreference = SettingsActivity.this.mNtSwitchPre;
        if (!SettingsActivity.this.mNtSwitchPre.isChecked()) {}
        for (boolean bool = true;; bool = false)
        {
          localSwitchPreference.setChecked(bool);
          localIntent.putExtra("isSwitchChecked", SettingsActivity.this.mNtSwitchPre.isChecked());
          SettingsActivity.this.setSdcardSize();
          localIntent.putExtra("sdcardSize", SettingsActivity.this.mSdcardSize);
          SettingsActivity.this.startActivity(localIntent);
          return false;
        }
      }
    });
    this.mTaglogEnable.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingsActivity.this.mSharedPreferences.edit().putBoolean("tagLogEnable", SettingsActivity.this.mTaglogEnable.isChecked()).commit();
        boolean bool;
        if (SettingsActivity.this.mExceptionReporterEnable != null)
        {
          SettingsActivity.this.mExceptionReporterEnable.setEnabled(SettingsActivity.this.mTaglogEnable.isChecked());
          if (SettingsActivity.this.mExceptionHistory != null)
          {
            PreferenceScreen localPreferenceScreen = SettingsActivity.this.mExceptionHistory;
            if ((!SettingsActivity.this.mExceptionReporterEnable.isChecked()) || (!SettingsActivity.this.mTaglogEnable.isChecked())) {
              break label149;
            }
            bool = true;
            localPreferenceScreen.setEnabled(bool);
          }
          if (SettingsActivity.this.mTaglogEnable.isChecked()) {
            break label155;
          }
          Settings.System.putInt(SettingsActivity.this.getContentResolver(), "log2server_dialog_show", 0);
        }
        label149:
        label155:
        while (!SettingsActivity.this.mExceptionReporterEnable.isChecked())
        {
          return true;
          bool = false;
          break;
        }
        Settings.System.putInt(SettingsActivity.this.getContentResolver(), "log2server_dialog_show", 1);
        return true;
      }
    });
    if (this.mExceptionReporterEnable != null) {
      this.mExceptionReporterEnable.setOnPreferenceChangeListener(this);
    }
    if (this.mUIDebugModeEnable != null) {
      this.mUIDebugModeEnable.setOnPreferenceChangeListener(this);
    }
    this.mLogStorageLocationList.setOnPreferenceChangeListener(this);
    this.mRunCmdEditText.setOnPreferenceChangeListener(this);
    if (this.mExceptionHistory != null) {
      this.mExceptionHistory.setOnPreferenceClickListener(this);
    }
  }
  
  private void setLogStorageEntries()
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    if ("mounted".equals(Utils.getVolumeState(this, Utils.getLogPath(this, "/mnt/sdcard"))))
    {
      localArrayList1.add(getString(2131165210));
      localArrayList2.add("1");
    }
    if ("mounted".equals(Utils.getVolumeState(this, Utils.getLogPath(this, "/mnt/sdcard2"))))
    {
      localArrayList1.add(getString(2131165211));
      localArrayList2.add("2");
    }
    if (localArrayList1.size() == 0)
    {
      this.mLogStorageLocationList.setEnabled(false);
      return;
    }
    this.mLogStorageLocationList.setEntries((CharSequence[])localArrayList1.toArray(new CharSequence[localArrayList1.size()]));
    this.mLogStorageLocationList.setEntryValues((CharSequence[])localArrayList2.toArray(new CharSequence[localArrayList2.size()]));
    String str;
    ListPreference localListPreference;
    if ("/mnt/sdcard2".equals(Utils.getLogPathType()))
    {
      str = "2";
      this.mLogStorageLocationList.setValue(str);
      localListPreference = this.mLogStorageLocationList;
      if (!"2".equals(str)) {
        break label232;
      }
    }
    label232:
    for (int i = 2131165211;; i = 2131165210)
    {
      localListPreference.setSummary(i);
      return;
      str = "1";
      break;
    }
  }
  
  private void setSdcardSize()
  {
    try
    {
      StatFs localStatFs = new StatFs(Utils.getCurrentLogPath(this));
      this.mSdcardSize = (localStatFs.getBlockSize() / 1024 * localStatFs.getBlockCount() / 1024);
      return;
    }
    catch (Exception localException)
    {
      Utils.loge("MTKLogger/SettingsActivity", "setSdcardSize() : StatFs error, maybe currentLogPath is invalid");
      this.mSdcardSize = 0;
    }
  }
  
  private void setUIDebugMode(boolean paramBoolean) {}
  
  private void updateUI()
  {
    Utils.logd("MTKLogger/SettingsActivity", "updateUI()");
    boolean bool1 = Utils.checkLogStarted(this.mSharedPreferences);
    SwitchPreference localSwitchPreference1 = this.mMbSwitchPre;
    boolean bool2;
    boolean bool3;
    label45:
    boolean bool4;
    if (!bool1)
    {
      bool2 = true;
      localSwitchPreference1.setEnabled(bool2);
      SwitchPreference localSwitchPreference2 = this.mMdSwitchPre;
      if (bool1) {
        break label171;
      }
      bool3 = true;
      localSwitchPreference2.setEnabled(bool3);
      SwitchPreference localSwitchPreference3 = this.mNtSwitchPre;
      if (bool1) {
        break label177;
      }
      bool4 = true;
      label65:
      localSwitchPreference3.setEnabled(bool4);
      CharSequence[] arrayOfCharSequence = this.mLogStorageLocationList.getEntries();
      if ((arrayOfCharSequence != null) && (arrayOfCharSequence.length != 0)) {
        break label183;
      }
      Utils.logw("MTKLogger/SettingsActivity", "Log storage entry is null or empty, disable storage set item");
      this.mLogStorageLocationList.setEnabled(false);
    }
    for (;;)
    {
      this.mMbSwitchPre.setChecked(this.mDefaultSharedPreferences.getBoolean("mobilelog_switch", true));
      this.mMdSwitchPre.setChecked(this.mDefaultSharedPreferences.getBoolean("modemlog_switch", true));
      this.mNtSwitchPre.setChecked(this.mDefaultSharedPreferences.getBoolean("networklog_switch", true));
      return;
      bool2 = false;
      break;
      label171:
      bool3 = false;
      break label45;
      label177:
      bool4 = false;
      break label65;
      label183:
      ListPreference localListPreference = this.mLogStorageLocationList;
      boolean bool5 = false;
      if (!bool1) {
        bool5 = true;
      }
      localListPreference.setEnabled(bool5);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130968579);
    findViews();
    initViews();
    setListeners();
  }
  
  protected void onDestroy()
  {
    if (this.mManager != null)
    {
      this.mManager.free();
      this.mManager = null;
    }
    super.onDestroy();
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    Utils.logd("MTKLogger/SettingsActivity", "Preference Change Key : " + paramPreference.getKey() + " newValue : " + paramObject);
    int j;
    if (paramPreference.getKey().equals("exceptionreporter_enable"))
    {
      ContentResolver localContentResolver = getContentResolver();
      if (Boolean.parseBoolean(paramObject.toString()))
      {
        j = 1;
        Settings.System.putInt(localContentResolver, "log2server_dialog_show", j);
        if (this.mExceptionHistory != null)
        {
          PreferenceScreen localPreferenceScreen = this.mExceptionHistory;
          boolean bool1 = this.mExceptionReporterEnable.isChecked();
          boolean bool2 = false;
          if (!bool1) {
            bool2 = true;
          }
          localPreferenceScreen.setEnabled(bool2);
        }
      }
    }
    label181:
    label211:
    do
    {
      return true;
      j = 0;
      break;
      if (paramPreference.getKey().equals("ui_debug_mode_enable"))
      {
        setUIDebugMode(Boolean.parseBoolean(paramObject.toString()));
        return true;
      }
      if (paramPreference.getKey().equals("log_storage_location"))
      {
        String str1;
        int i;
        MTKLoggerManager localMTKLoggerManager;
        if ("/mnt/sdcard2".equals(Utils.getLogPathType()))
        {
          str1 = "2";
          if (str1.equals(paramObject.toString())) {
            break label261;
          }
          if (!"2".equals(paramObject.toString())) {
            break label263;
          }
          i = 2131165211;
          paramPreference.setSummary(i);
          localMTKLoggerManager = this.mManager;
          if (!"2".equals(paramObject.toString())) {
            break label271;
          }
        }
        for (String str2 = "Log2sd";; str2 = "Log2emmc")
        {
          localMTKLoggerManager.runCommand(str2);
          setSdcardSize();
          return true;
          str1 = "1";
          break label181;
          break;
          i = 2131165210;
          break label211;
        }
      }
    } while (!paramPreference.getKey().equals("run_command"));
    label261:
    label263:
    label271:
    this.mManager.runCommand(paramObject.toString());
    return true;
  }
  
  public boolean onPreferenceClick(Preference paramPreference)
  {
    if ((!paramPreference.equals(this.mExceptionHistory)) || (!this.mExceptionHistory.isEnabled())) {
      return false;
    }
    return ExceptionReportManager.runExceptionHistory(this);
  }
  
  protected void onResume()
  {
    Utils.logd("MTKLogger/SettingsActivity", "onResume()");
    updateUI();
    super.onResume();
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.settings.SettingsActivity
 * JD-Core Version:    0.7.0.1
 */