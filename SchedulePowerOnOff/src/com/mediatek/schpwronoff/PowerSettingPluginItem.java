package com.mediatek.schpwronoff;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.settings.pluginItem.AbstractSettingItem;
import com.lenovo.settings.pluginItem.BaseSettingFragment;
import android.util.Log;


public class PowerSettingPluginItem extends AbstractSettingItem {
	private static final String PREF_KEY = "power_setting";

	public PowerSettingPluginItem(Context context) {
		super(context);

	}

	@Override
	public Fragment getFragment() {
        Log.d("Lrk","getFragment");
        return null;
	}

	@Override
	public Intent getIntent() {
        Log.d("Lrk","getIntent");
		Intent intent = new Intent();
		intent.setAction("com.android.settings.SCHEDULE_POWER_ON_OFF_SETTING");
		return intent;
	}

	@Override
	public String getKey() {
		Log.d("Lrk","getKey");
		return PREF_KEY;
	}

	@Override
	public String getParentKey() {
		Log.d("Lrk","getParentKey");
		return "base_section";
	}

	@Override
	public float getPosition() {
		Log.d("Lrk","getPosition");
		return 2.4f;
	}

	@Override
	public Preference getPreference(Context context) {
        Log.d("Lrk","getPreference");
		Preference prf = new Preference(context);
		prf.setKey(PREF_KEY);
		prf.setLayoutResource(R.layout.preference_header_item_rom20);
		prf.setIcon(getDrawable(R.drawable.ic_settings_timely_power_on_off));
		prf.setTitle(getText(R.string.schedule_power_on_off_settings_title));
		return prf;

	}
}
