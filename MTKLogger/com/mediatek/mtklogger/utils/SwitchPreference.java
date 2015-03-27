package com.mediatek.mtklogger.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SwitchPreference
  extends Preference
{
  private boolean isChecked = false;
  private CheckBox mCheckBox = null;
  private Context mContext;
  
  public SwitchPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
    this.mContext = paramContext;
    setLayoutResource(2130903056);
    if (isPersistent()) {
      this.isChecked = PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean(getKey(), true);
    }
  }
  
  private void callOnStateChangeListener(boolean paramBoolean)
  {
    setChecked(paramBoolean);
    callChangeListener(Boolean.valueOf(paramBoolean));
  }
  
  public boolean isChecked()
  {
    return this.isChecked;
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.mCheckBox = ((CheckBox)paramView.findViewById(2131296318));
    this.mCheckBox.setChecked(this.isChecked);
    if (isPersistent()) {
      persistBoolean(this.isChecked);
    }
    this.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        SwitchPreference.this.callOnStateChangeListener(paramAnonymousBoolean);
      }
    });
  }
  
  protected void onClick()
  {
    super.onClick();
    if (!isChecked()) {}
    for (boolean bool = true; !callChangeListener(Boolean.valueOf(bool)); bool = false) {
      return;
    }
    setChecked(bool);
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.isChecked = paramBoolean;
    notifyChanged();
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.utils.SwitchPreference
 * JD-Core Version:    0.7.0.1
 */