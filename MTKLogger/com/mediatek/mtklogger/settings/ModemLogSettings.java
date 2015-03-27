package com.mediatek.mtklogger.settings;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.mediatek.mtklogger.framework.MTKLoggerManager;
import com.mediatek.mtklogger.utils.Utils;

public class ModemLogSettings
  extends PreferenceActivity
  implements Preference.OnPreferenceChangeListener
{
  public static final String KEY_MD_CATEGORY = "modemlog_category";
  public static final String KEY_MD_LOGSIZE = "modemlog_logsize";
  public static final String KEY_MD_MODE = "log_mode";
  public static final String KEY_MD_PREFERENCE_SCREEN = "modemlog_preference_screen";
  private static final int LIMIT_LOG_SIZE = 600;
  private final String TAG = "MTKLogger/ModemLogSettings";
  private OptionalActionBarSwitch mBarSwitch;
  private SharedPreferences mDefaultSharedPreferences;
  private MTKLoggerManager mManager = null;
  private CheckBoxPreference mMdAutoStartPre;
  private ListPreference mMdLogModeList;
  private EditTextPreference mMdLogSizeLimitPre;
  private int mSdcardSize;
  private SharedPreferences mSharedPreferences;
  
  private void findViews()
  {
    this.mMdLogModeList = ((ListPreference)findPreference("log_mode"));
    if (Utils.ANDROID_VERSION_NUMBER > 3.999D) {
      this.mMdLogSizeLimitPre = ((EditTextPreference)findPreference("modemlog_logsize"));
    }
    for (;;)
    {
      this.mMdAutoStartPre = ((CheckBoxPreference)findPreference("modemlog_autostart"));
      return;
      PreferenceScreen localPreferenceScreen = (PreferenceScreen)findPreference("modemlog_preference_screen");
      localPreferenceScreen.removePreference(findPreference("modemlog_logsize"));
      this.mMdLogSizeLimitPre = new EditTextPreference(this)
      {
        protected void onPrepareDialogBuilder(AlertDialog.Builder paramAnonymousBuilder)
        {
          paramAnonymousBuilder.setInverseBackgroundForced(true);
          super.onPrepareDialogBuilder(paramAnonymousBuilder);
        }
      };
      this.mMdLogSizeLimitPre.setKey("modemlog_logsize");
      this.mMdLogSizeLimitPre.setTitle(2131165243);
      this.mMdLogSizeLimitPre.setDialogTitle(2131165243);
      this.mMdLogSizeLimitPre.setSummary(2131165244);
      this.mMdLogSizeLimitPre.setDefaultValue("600");
      localPreferenceScreen.addPreference(this.mMdLogSizeLimitPre);
    }
  }
  
  private int getIntByObj(Object paramObject)
  {
    try
    {
      int i = Integer.parseInt(paramObject.toString());
      return i;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  private void initViews()
  {
    Utils.logd("MTKLogger/ModemLogSettings", "initViews()");
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.mSharedPreferences = getSharedPreferences("log_settings", 0);
    this.mManager = new MTKLoggerManager(this);
    boolean bool1 = this.mDefaultSharedPreferences.getBoolean("modemlog_switch", false);
    boolean bool2 = Utils.checkLogStarted(this.mSharedPreferences);
    this.mBarSwitch.setChecked(bool1);
    OptionalActionBarSwitch localOptionalActionBarSwitch = this.mBarSwitch;
    boolean bool3;
    boolean bool4;
    label96:
    Object[] arrayOfObject;
    if (!bool2)
    {
      bool3 = true;
      localOptionalActionBarSwitch.setEnabled(bool3);
      if ((!bool1) || (bool2)) {
        break label322;
      }
      bool4 = true;
      setAllPreferencesEnable(bool4);
      String str = this.mDefaultSharedPreferences.getString("log_mode", "");
      if (TextUtils.isEmpty(str))
      {
        str = getString(2131165302);
        Utils.logw("MTKLogger/ModemLogSettings", "No default log mode value stored in default shared preference, try to get it from string res, logModeValue=" + str);
      }
      int i = this.mMdLogModeList.findIndexOfValue(str);
      if (i < 0) {
        Utils.loge("MTKLogger/ModemLogSettings", "Fail to select the given mode, just take the first one.");
      }
      this.mMdLogModeList.setValueIndex(i);
      this.mMdLogModeList.setSummary(this.mMdLogModeList.getEntries()[i]);
      this.mMdLogSizeLimitPre.getEditText().setInputType(2);
      this.mSdcardSize = getIntent().getIntExtra("sdcardSize", 600);
      arrayOfObject = new Object[4];
      arrayOfObject[0] = getString(2131165197);
      arrayOfObject[1] = Integer.valueOf(600);
      arrayOfObject[2] = Integer.valueOf(this.mSdcardSize);
      if (!"/mnt/sdcard2".equals(Utils.getLogPathType())) {
        break label328;
      }
    }
    label322:
    label328:
    for (int j = 2131165211;; j = 2131165210)
    {
      arrayOfObject[3] = getString(j);
      this.mMdLogSizeLimitPre.setDialogMessage(getString(2131165245, arrayOfObject));
      return;
      bool3 = false;
      break;
      bool4 = false;
      break label96;
    }
  }
  
  private void removeManualTitle()
  {
    ((PreferenceScreen)findPreference("modemlog_preference_screen")).removePreference(findPreference("modemlog_category"));
  }
  
  private void setAllPreferencesEnable(boolean paramBoolean)
  {
    this.mMdLogModeList.setEnabled(paramBoolean);
    this.mMdLogSizeLimitPre.setEnabled(paramBoolean);
    this.mMdLogModeList.setSummary(this.mMdLogModeList.getEntry());
  }
  
  private void setListeners()
  {
    Utils.logd("MTKLogger/ModemLogSettings", "setListeners()");
    this.mMdLogModeList.setOnPreferenceChangeListener(this);
    this.mMdLogSizeLimitPre.setOnPreferenceChangeListener(this);
    this.mMdAutoStartPre.setOnPreferenceChangeListener(this);
    this.mBarSwitch.setOnCheckedChangeListener(new LogSwitchListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        ModemLogSettings.this.mDefaultSharedPreferences.edit().putBoolean("modemlog_switch", paramAnonymousBoolean).commit();
        ModemLogSettings.this.setAllPreferencesEnable(paramAnonymousBoolean);
      }
      
      public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
      {
        boolean bool = Boolean.parseBoolean(paramAnonymousObject.toString());
        ModemLogSettings.this.mDefaultSharedPreferences.edit().putBoolean("modemlog_switch", Boolean.parseBoolean(paramAnonymousObject.toString())).commit();
        ModemLogSettings.this.setAllPreferencesEnable(bool);
        return true;
      }
    });
    this.mMdLogSizeLimitPre.getEditText().addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        Dialog localDialog = ModemLogSettings.this.mMdLogSizeLimitPre.getDialog();
        if ((localDialog != null) && ((localDialog instanceof AlertDialog)))
        {
          if ("".equals(String.valueOf(paramAnonymousCharSequence))) {
            ((AlertDialog)localDialog).getButton(-1).setEnabled(false);
          }
        }
        else {
          return;
        }
        for (;;)
        {
          try
          {
            int i = Integer.parseInt(String.valueOf(paramAnonymousCharSequence));
            if ((i >= 600) && (i <= ModemLogSettings.this.mSdcardSize))
            {
              bool = true;
              ((AlertDialog)localDialog).getButton(-1).setEnabled(bool);
              return;
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Utils.loge("MTKLogger/ModemLogSettings", "Integer.parseInt(" + String.valueOf(paramAnonymousCharSequence) + ") is error!");
            ((AlertDialog)localDialog).getButton(-1).setEnabled(false);
            return;
          }
          boolean bool = false;
        }
      }
    });
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130903050);
    if (Utils.ANDROID_VERSION_NUMBER > 3.999D)
    {
      this.mBarSwitch = new OptionalActionBarSwitch(this);
      removeManualTitle();
    }
    for (;;)
    {
      findViews();
      initViews();
      setListeners();
      return;
      this.mBarSwitch = new OptionalActionBarSwitch(this, 2);
    }
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
    Utils.logd("MTKLogger/ModemLogSettings", "Preference Change Key : " + paramPreference.getKey() + " newValue : " + paramObject);
    if (paramPreference.getKey().equals("log_mode"))
    {
      String str = (String)paramObject;
      int i = this.mMdLogModeList.findIndexOfValue(str);
      if (i < 0)
      {
        Utils.loge("MTKLogger/ModemLogSettings", "Fail to select the given mode, ignore.");
        return false;
      }
      this.mMdLogModeList.setSummary(this.mMdLogModeList.getEntries()[i]);
    }
    for (;;)
    {
      return true;
      if (paramPreference.getKey().equals("modemlog_logsize")) {
        this.mManager.setLogSize(2, getIntByObj(paramObject));
      } else if (paramPreference.getKey().equals("modemlog_autostart")) {
        this.mManager.setAutoStart(2, Boolean.parseBoolean(paramObject.toString()));
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.settings.ModemLogSettings
 * JD-Core Version:    0.7.0.1
 */