package com.mediatek.mtklogger.debugtool;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.mediatek.xlog.Xlog;

public class DebugToolboxActivity
  extends PreferenceActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener
{
  private static final String TAG = "MTKLogger/Debugutils";
  private static final String sAEE_BUILD_INFO = "ro.aee.build.info";
  private static final String sAEE_MODE = "persist.mtk.aee.mode";
  private static final String[] sAEE_MODE_STRING = { "", "MediatekEngineer", "MediatekUser", "CustomerEngineer", "CustomerUser" };
  private static final String[] sDAL_OPTION_STRING = { "EnableDAL", "DisableDAL" };
  private static final String sDAL_SETTING = "persist.mtk.aee.dal";
  private static final String sRO_BUILD_TYPE = "ro.build.type";
  PreferenceScreen cleanData;
  PreferenceScreen clearDAL;
  private AEEControlService mBoundService;
  private ServiceConnection mConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      DebugToolboxActivity.access$002(DebugToolboxActivity.this, ((AEEControlService.LocalBinder)paramAnonymousIBinder).getService());
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      DebugToolboxActivity.access$002(DebugToolboxActivity.this, null);
    }
  };
  private boolean mIsBound;
  ListPreference pref_aeemode;
  ListPreference pref_daloption;
  
  private static String currentAEEMode()
  {
    int i = 4;
    int j = 1;
    String str1 = getProperty("persist.mtk.aee.mode");
    if ((str1 != null) && (!str1.isEmpty()))
    {
      int k = Integer.valueOf(str1).intValue();
      if ((k >= j) && (k <= i)) {
        return sAEE_MODE_STRING[k];
      }
    }
    String str2 = getProperty("ro.aee.build.info");
    String str3 = getProperty("ro.build.type");
    Xlog.i("MTKLogger/Debugutils", "[ro.build.type]" + str2 + "[ro.aee.build.info]" + str3);
    if (str2.equals("mtk")) {
      i = 2;
    }
    if (str3.equals("eng")) {}
    for (;;)
    {
      return sAEE_MODE_STRING[(i - j)];
      j = 0;
    }
  }
  
  private static String currentDalOption()
  {
    String str = getProperty("persist.mtk.aee.dal");
    if ((str != null) && (!str.isEmpty()) && (Integer.valueOf(str).intValue() == 0)) {
      return sDAL_OPTION_STRING[1];
    }
    return sDAL_OPTION_STRING[0];
  }
  
  private static String getProperty(String paramString)
  {
    return SystemProperties.get(paramString);
  }
  
  void doBindService()
  {
    bindService(new Intent(this, AEEControlService.class), this.mConnection, 1);
    this.mIsBound = true;
  }
  
  void doUnbindService()
  {
    if (this.mIsBound)
    {
      unbindService(this.mConnection);
      this.mIsBound = false;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2130968578);
    this.pref_aeemode = ((ListPreference)getPreferenceScreen().findPreference(getString(2131165311)));
    String str1 = getProperty("ro.aee.build.info");
    if ((str1 == null) || (!str1.equals("mtk")))
    {
      this.pref_aeemode.setEntries(2131099652);
      this.pref_aeemode.setEntryValues(2131099653);
    }
    String str2 = currentAEEMode();
    this.pref_aeemode.setValue(str2);
    this.pref_aeemode.setSummary(str2);
    this.pref_daloption = ((ListPreference)getPreferenceScreen().findPreference(getString(2131165315)));
    String str3 = currentDalOption();
    this.pref_daloption.setValue(str3);
    this.pref_daloption.setSummary(str3);
    this.clearDAL = ((PreferenceScreen)getPreferenceScreen().findPreference(getString(2131165319)));
    this.clearDAL.setOnPreferenceClickListener(this);
    this.cleanData = ((PreferenceScreen)getPreferenceScreen().findPreference(getString(2131165324)));
    this.cleanData.setOnPreferenceClickListener(this);
    String str4 = getProperty("ro.build.type");
    if ((str4 == null) || (!str4.equals("eng"))) {
      this.cleanData.setEnabled(false);
    }
    PreferenceCategory localPreferenceCategory = (PreferenceCategory)getPreferenceScreen().findPreference(getString(2131165323));
    getPreferenceScreen().removePreference(localPreferenceCategory);
    doBindService();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    doUnbindService();
  }
  
  protected void onPause()
  {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
  
  public boolean onPreferenceClick(Preference paramPreference)
  {
    if (paramPreference.equals(this.clearDAL)) {
      try
      {
        this.mBoundService.clearDAL();
        return true;
      }
      catch (NullPointerException localNullPointerException2)
      {
        Xlog.e("MTKLogger/Debugutils", "AEE Service is not started");
      }
    }
    for (;;)
    {
      return false;
      if (paramPreference.equals(this.cleanData)) {
        try
        {
          this.mBoundService.cleanData();
          return true;
        }
        catch (NullPointerException localNullPointerException1)
        {
          Xlog.e("MTKLogger/Debugutils", "AEE Service is not started");
        }
      }
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    String str3;
    if (paramString.equals(getString(2131165311)))
    {
      str3 = paramSharedPreferences.getString(paramString, null);
      if (str3 == null) {
        Xlog.e("MTKLogger/Debugutils", "AEE working mode is set to NULL.");
      }
    }
    String str1;
    for (;;)
    {
      return;
      try
      {
        this.mBoundService.changeAEEMode(str3);
        String str4 = currentAEEMode();
        if (!str4.equals(str3))
        {
          this.pref_aeemode.setValue(str4);
          String str5 = "Change debug level [" + str4 + "-->" + str3 + "] too fequent, please retry later.";
          Xlog.e("MTKLogger/Debugutils", str5);
          Toast.makeText(this, str5, 0).show();
        }
        this.pref_aeemode.setSummary(str4);
        if (paramString.equals(getString(2131165315)))
        {
          str1 = paramSharedPreferences.getString(paramString, null);
          if (str1 == null)
          {
            Xlog.d("MTKLogger/Debugutils", "DAL setting mode is set to NULL.");
            return;
          }
        }
      }
      catch (NullPointerException localNullPointerException2)
      {
        for (;;)
        {
          Xlog.e("MTKLogger/Debugutils", "AEE Service is not started");
        }
      }
    }
    try
    {
      this.mBoundService.dalSetting(str1);
      String str2 = currentDalOption();
      if (!str2.equals(str1)) {
        this.pref_daloption.setValue(str2);
      }
      this.pref_daloption.setSummary(str2);
      return;
    }
    catch (NullPointerException localNullPointerException1)
    {
      for (;;)
      {
        Xlog.e("MTKLogger/Debugutils", "DAL setting error");
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.debugtool.DebugToolboxActivity
 * JD-Core Version:    0.7.0.1
 */