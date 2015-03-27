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

public class MobileLogSettings
  extends PreferenceActivity
  implements Preference.OnPreferenceChangeListener
{
  public static final String KEY_MB_ANDROID_LOG = "mobilelog_androidlog";
  public static final String KEY_MB_BT_LOG = "mobilelog_btlog";
  public static final String KEY_MB_CATEGORY = "mobilelog_category";
  public static final String KEY_MB_KERNEL_LOG = "mobilelog_kernellog";
  public static final String KEY_MB_LOGSIZE = "mobilelog_logsize";
  public static final String KEY_MB_PREFERENCE_SCREEN = "mobilelog_preference_screen";
  private static final int LIMIT_LOG_SIZE = 100;
  private final String TAG = "MTKLogger/MobileLogSettings";
  private OptionalActionBarSwitch mBarSwitch;
  private SharedPreferences mDefaultSharedPreferences;
  private MTKLoggerManager mManager = null;
  private CheckBoxPreference mMbAndroidLog;
  private CheckBoxPreference mMbAutoStartPre;
  private CheckBoxPreference mMbBluetoothLog;
  private CheckBoxPreference mMbKernelLog;
  private EditTextPreference mMbLogSizeLimitPre;
  private int mSdcardSize;
  private SharedPreferences mSharedPreferences;
  
  private void findViews()
  {
    this.mMbAndroidLog = ((CheckBoxPreference)findPreference("mobilelog_androidlog"));
    this.mMbKernelLog = ((CheckBoxPreference)findPreference("mobilelog_kernellog"));
    this.mMbBluetoothLog = ((CheckBoxPreference)findPreference("mobilelog_btlog"));
    this.mMbAutoStartPre = ((CheckBoxPreference)findPreference("mobilelog_autostart"));
    if (Utils.ANDROID_VERSION_NUMBER > 3.999D)
    {
      this.mMbLogSizeLimitPre = ((EditTextPreference)findPreference("mobilelog_logsize"));
      return;
    }
    PreferenceScreen localPreferenceScreen = (PreferenceScreen)findPreference("mobilelog_preference_screen");
    localPreferenceScreen.removePreference(findPreference("mobilelog_logsize"));
    this.mMbLogSizeLimitPre = new EditTextPreference(this)
    {
      protected void onPrepareDialogBuilder(AlertDialog.Builder paramAnonymousBuilder)
      {
        paramAnonymousBuilder.setInverseBackgroundForced(true);
        super.onPrepareDialogBuilder(paramAnonymousBuilder);
      }
    };
    this.mMbLogSizeLimitPre.setKey("mobilelog_logsize");
    this.mMbLogSizeLimitPre.setTitle(2131165243);
    this.mMbLogSizeLimitPre.setDialogTitle(2131165243);
    this.mMbLogSizeLimitPre.setSummary(2131165244);
    this.mMbLogSizeLimitPre.setDefaultValue("300");
    localPreferenceScreen.addPreference(this.mMbLogSizeLimitPre);
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
    Utils.logd("MTKLogger/MobileLogSettings", "initViews()");
    this.mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    this.mSharedPreferences = getSharedPreferences("log_settings", 0);
    this.mManager = new MTKLoggerManager(this);
    boolean bool1 = this.mDefaultSharedPreferences.getBoolean("mobilelog_switch", false);
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
      this.mMbLogSizeLimitPre.getEditText().setInputType(2);
      this.mSdcardSize = getIntent().getIntExtra("sdcardSize", 100);
      arrayOfObject = new Object[4];
      arrayOfObject[0] = getString(2131165196);
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
      this.mMbLogSizeLimitPre.setDialogMessage(getString(2131165245, arrayOfObject));
      return;
      bool3 = false;
      break;
      bool4 = false;
      break label96;
    }
  }
  
  private void removeManualTitle()
  {
    ((PreferenceScreen)findPreference("mobilelog_preference_screen")).removePreference(findPreference("mobilelog_category"));
  }
  
  private void setAllPreferencesEnable(boolean paramBoolean)
  {
    this.mMbAndroidLog.setEnabled(paramBoolean);
    this.mMbKernelLog.setEnabled(paramBoolean);
    this.mMbBluetoothLog.setEnabled(paramBoolean);
    this.mMbLogSizeLimitPre.setEnabled(paramBoolean);
  }
  
  private void setListeners()
  {
    Utils.logd("MTKLogger/MobileLogSettings", "setListeners()");
    this.mMbAndroidLog.setOnPreferenceChangeListener(this);
    this.mMbKernelLog.setOnPreferenceChangeListener(this);
    this.mMbBluetoothLog.setOnPreferenceChangeListener(this);
    this.mMbLogSizeLimitPre.setOnPreferenceChangeListener(this);
    this.mMbAutoStartPre.setOnPreferenceChangeListener(this);
    this.mBarSwitch.setOnCheckedChangeListener(new LogSwitchListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        MobileLogSettings.this.mDefaultSharedPreferences.edit().putBoolean("mobilelog_switch", paramAnonymousBoolean).commit();
        MobileLogSettings.this.setAllPreferencesEnable(paramAnonymousBoolean);
      }
      
      public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
      {
        boolean bool = Boolean.parseBoolean(paramAnonymousObject.toString());
        MobileLogSettings.this.mDefaultSharedPreferences.edit().putBoolean("mobilelog_switch", Boolean.parseBoolean(paramAnonymousObject.toString())).commit();
        MobileLogSettings.this.setAllPreferencesEnable(bool);
        return true;
      }
    });
    this.mMbLogSizeLimitPre.getEditText().addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        Dialog localDialog = MobileLogSettings.this.mMbLogSizeLimitPre.getDialog();
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
            if ((i >= 100) && (i <= MobileLogSettings.this.mSdcardSize))
            {
              bool = true;
              ((AlertDialog)localDialog).getButton(-1).setEnabled(bool);
              return;
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Utils.loge("MTKLogger/MobileLogSettings", "Integer.parseInt(" + String.valueOf(paramAnonymousCharSequence) + ") is error!");
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
    addPreferencesFromResource(2130903049);
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
      this.mBarSwitch = new OptionalActionBarSwitch(this, 1);
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
    Utils.logd("MTKLogger/MobileLogSettings", "Preference Change Key : " + paramPreference.getKey() + " newValue : " + paramObject);
    if (paramPreference.getKey().equals("mobilelog_androidlog")) {
      this.mManager.setMobileSubLogEnableState(1, Boolean.parseBoolean(paramObject.toString()));
    }
    do
    {
      return true;
      if (paramPreference.getKey().equals("mobilelog_kernellog"))
      {
        this.mManager.setMobileSubLogEnableState(2, Boolean.parseBoolean(paramObject.toString()));
        return true;
      }
      if (paramPreference.getKey().equals("mobilelog_btlog"))
      {
        this.mManager.setMobileSubLogEnableState(3, Boolean.parseBoolean(paramObject.toString()));
        return true;
      }
      if (paramPreference.getKey().equals("mobilelog_logsize"))
      {
        this.mManager.setLogSize(1, getIntByObj(paramObject));
        return true;
      }
    } while (!paramPreference.getKey().equals("mobilelog_autostart"));
    this.mManager.setAutoStart(1, Boolean.parseBoolean(paramObject.toString()));
    return true;
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.settings.MobileLogSettings
 * JD-Core Version:    0.7.0.1
 */