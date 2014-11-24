package com.android.trafficmeter;

import java.util.Calendar;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * The application only for loading base information.
 * @author archermind
 *
 */
public class TrafficMeterApplication extends Application {

	public static CharSequence[] COUNTER_TYPES;
	private SharedPreferences prefs;
	private String date;
	public synchronized <T> T getAdapter(Class<T> clazz) {
		if(SharedPreferences.class == clazz) {
			if(prefs == null) {
				prefs = PreferenceManager.getDefaultSharedPreferences(this);
			}
			return clazz.cast(prefs);
		}
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		COUNTER_TYPES = getResources().getTextArray(R.array.counterTypes);
		prefs = getAdapter(SharedPreferences.class);
		String startday = prefs.getString(TrafficConst.STARTDAY, "");
		date = TrafficHelper.getDate(Calendar.getInstance());
		if("".equals(startday)) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(TrafficConst.STARTDAY, date);
			editor.commit();
		}
//		String mlastUpdate = prefs.getString(TrafficConst.CURRENTDAY, null);
//		DailyOperator.setMlastUpdate(mlastUpdate);
	}


	public void saveCurrentDay(String date) {
		String currentday = prefs.getString(TrafficConst.CURRENTDAY, "");
		if("".equals(currentday)) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(TrafficConst.CURRENTDAY, date);
			editor.commit();
		}
	}
}
