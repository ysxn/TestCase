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
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.mediatek.mtklogger.framework.MTKLoggerManager;
import com.mediatek.mtklogger.utils.Utils;

public class NetworkLogSettings
  extends PreferenceActivity
  implements Preference.OnPreferenceChangeListener
{
  public static final String KEY_NT_CATEGORY = "networklog_category";
  public static final String KEY_NT_LOGSIZE = "networklog_logsize";
  public static final String KEY_NT_PREFERENCE_SCREEN = "networklog_preference_screen";
  private static final int LIMIT_LOG_SIZE = 100;
  private final String TAG = "MTKLogger/NetworkLogSettings";
  private OptionalActionBarSwitch mBarSwitch;
  private SharedPreferences mDefaultSharedPreferences;
  private MTKLoggerManager mManager = null;
  private CheckBoxPreference mNtAutoStartPre;
  private EditTextPreference mNtLogSizeLimitPre;
  private int mSdcardSize;
  private SharedPreferences mSharedPreferences;
  
  private void findViews()
  {
    if (Utils.ANDROID_VERSION_NUMBER > 3.999D) {
      this.mNtLogSizeLimitPre = ((EditTextPreference)findPreference("networklog_logsize"));
    }
    for (;;)
    {
      this.mNtAutoStartPre = ((CheckBoxPreference)findPreference("networklog_autostart"));
      return;
      PreferenceScreen localPreferenceScreen = (PreferenceScreen)findPreference("networklog_preference_screen");
      localPreferenceScreen.removePreference(findPreference("networklog_logsize"));
      this.mNtLogSizeLimitPre = new EditTextPreference(this)
      {
        protected void onPrepareDialogBuilder(AlertDialog.Builder paramAnonymousBuilder)
        {
          paramAnonymousBuilder.setInverseBackgroundForced(true);
          super.onPrepareDialogBuilder(paramAnonymousBuilder);
        }
      };
      this.mNtLogSizeLimitPre.setKey("networklog_logsize");
      this.mNtLogSizeLimitPre.setTitle(2131165243);
      this.mNtLogSizeLimitPre.setDialogTitle(2131165243);
      this.mNtLogSizeLimitPre.setSummary(2131165244);
      this.mNtLogSizeLimitPre.setDefaultValue("200");
      localPreferenceScreen.addPreference(this.mNtLogSizeLimitPre);
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
    Utils.logd("MTKLogger/NetworkLogSettings", "initViews()");
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.mSharedPreferences = getSharedPreferences("log_settings", 0);
    this.mManager = new MTKLoggerManager(this);
    boolean bool1 = this.mDefaultSharedPreferences.getBoolean("networklog_switch", false);
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
        break label211;
      }
      bool4 = true;
      setAllPreferencesEnable(bool4);
      this.mNtLogSizeLimitPre.getEditText().setInputType(2);
      this.mSdcardSize = getIntent().getIntExtra("sdcardSize", 100);
      arrayOfObject = new Object[4];
      arrayOfObject[0] = getString(2131165195);
      arrayOfObject[1] = Integer.valueOf(100);
      arrayOfObject[2] = Integer.valueOf(this.mSdcardSize);
      if (!"/mnt/sdcard2".equals(Utils.getLogPathType())) {
        break label217;
      }
    }
    label211:
    label217:
    for (int i = 2131165211;; i = 2131165210)
    {
      arrayOfObject[3] = getString(i);
      this.mNtLogSizeLimitPre.setDialogMessage(getString(2131165245, arrayOfObject));
      return;
      bool3 = false;
      break;
      bool4 = false;
      break label96;
    }
  }
  
  private void removeManualTitle()
  {
    ((PreferenceScreen)findPreference("networklog_preference_screen")).removePreference(findPreference("networklog_category"));
  }
  
  private void setAllPreferencesEnable(boolean paramBoolean)
  {
    this.mNtLogSizeLimitPre.setEnabled(paramBoolean);
  }
  
  private void setListeners()
  {
    Utils.logd("MTKLogger/NetworkLogSettings", "setListeners()");
    this.mNtLogSizeLimitPre.setOnPreferenceChangeListener(this);
    this.mNtAutoStartPre.setOnPreferenceChangeListener(this);
    this.mBarSwitch.setOnCheckedChangeListener(new LogSwitchListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        NetworkLogSettings.this.mDefaultSharedPreferences.edit().putBoolean("networklog_switch", paramAnonymousBoolean).commit();
        NetworkLogSettings.this.setAllPreferencesEnable(paramAnonymousBoolean);
      }
      
      public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
      {
        boolean bool = Boolean.parseBoolean(paramAnonymousObject.toString());
        NetworkLogSettings.this.mDefaultSharedPreferences.edit().putBoolean("networklog_switch", Boolean.parseBoolean(paramAnonymousObject.toString())).commit();
        NetworkLogSettings.this.setAllPreferencesEnable(bool);
        return true;
      }
    });
    this.mNtLogSizeLimitPre.getEditText().addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        Dialog localDialog = NetworkLogSettings.this.mNtLogSizeLimitPre.getDialog();
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
            if ((i >= 100) && (i <= NetworkLogSettings.this.mSdcardSize))
            {
              bool = true;
              ((AlertDialog)localDialog).getButton(-1).setEnabled(bool);
              return;
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Utils.loge("MTKLogger/NetworkLogSettings", "Integer.parseInt(" + String.valueOf(paramAnonymousCharSequence) + ") is error!");
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
    addPreferencesFromResource(2130903052);
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
      this.mBarSwitch = new OptionalActionBarSwitch(this, 4);
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
    Utils.logd("MTKLogger/NetworkLogSettings", "Preference Change Key : " + paramPreference.getKey() + " newValue : " + paramObject);
    if (paramPreference.getKey().equals("networklog_logsize")) {
      this.mManager.setLogSize(4, getIntByObj(paramObject));
    }
    for (;;)
    {
      return true;
      if (paramPreference.getKey().equals("networklog_autostart")) {
        this.mManager.setAutoStart(4, Boolean.parseBoolean(paramObject.toString()));
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.settings.NetworkLogSettings
 * JD-Core Version:    0.7.0.1
 */