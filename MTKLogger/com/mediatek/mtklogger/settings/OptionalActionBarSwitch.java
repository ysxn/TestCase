package com.mediatek.mtklogger.settings;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class OptionalActionBarSwitch
{
  private TextView mActionBarTextView;
  private Activity mActivity;
  private Switch mBarSwitch;
  
  public OptionalActionBarSwitch(Activity paramActivity, int paramInt)
  {
    this.mActivity = paramActivity;
    View localView = ((LayoutInflater)paramActivity.getSystemService("layout_inflater")).inflate(2130903051, null);
    ImageButton localImageButton = (ImageButton)localView.findViewById(2131296308);
    this.mActionBarTextView = ((TextView)localView.findViewById(2131296309));
    TextView localTextView = this.mActionBarTextView;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    localTextView.setText(paramActivity.getString(2131165267, arrayOfObject));
    localImageButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        OptionalActionBarSwitch.this.mActivity.finish();
      }
    });
    paramActivity.getActionBar().setDisplayOptions(16, 26);
    paramActivity.getActionBar().setCustomView(localView);
  }
  
  public OptionalActionBarSwitch(PreferenceActivity paramPreferenceActivity)
  {
    this.mBarSwitch = new Switch(paramPreferenceActivity);
    if ((paramPreferenceActivity.onIsHidingHeaders()) || (!paramPreferenceActivity.onIsMultiPane()))
    {
      this.mBarSwitch.setPadding(0, 0, 16, 0);
      paramPreferenceActivity.getActionBar().setDisplayOptions(16, 16);
      paramPreferenceActivity.getActionBar().setCustomView(this.mBarSwitch, new ActionBar.LayoutParams(-2, -2, 21));
    }
  }
  
  public void setChecked(boolean paramBoolean)
  {
    if (this.mBarSwitch != null) {
      this.mBarSwitch.setChecked(paramBoolean);
    }
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    if (this.mBarSwitch != null) {
      this.mBarSwitch.setEnabled(paramBoolean);
    }
  }
  
  public void setOnCheckedChangeListener(LogSwitchListener paramLogSwitchListener)
  {
    if (this.mBarSwitch != null) {
      this.mBarSwitch.setOnCheckedChangeListener(paramLogSwitchListener);
    }
  }
  
  public void updateTitle(int paramInt)
  {
    if (this.mActionBarTextView != null)
    {
      TextView localTextView = this.mActionBarTextView;
      Activity localActivity = this.mActivity;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      localTextView.setText(localActivity.getString(2131165267, arrayOfObject));
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.settings.OptionalActionBarSwitch
 * JD-Core Version:    0.7.0.1
 */