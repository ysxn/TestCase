package com.mediatek.mtklogger.settings;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LogSwitchListener
  implements Preference.OnPreferenceChangeListener, CompoundButton.OnCheckedChangeListener
{
  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {}
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    return false;
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.settings.LogSwitchListener
 * JD-Core Version:    0.7.0.1
 */