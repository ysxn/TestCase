package com.android.trafficmeter.activity;


import java.util.Calendar;

import com.android.trafficmeter.R;
import com.android.trafficmeter.TrafficConst;
import com.android.trafficmeter.TrafficHelper;
import com.android.trafficmeter.TrafficMeterApplication;
import com.android.trafficmeter.service.TrafficService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.TabHost;

/**
 *
 *	An activity that contains and runs multiple embedded activities.
 * @author archermind
 *
 */
public class TrafficActivity extends TabActivity {
	private MobileActivity mobileActivity = null;
	private WifiActivity wifiActivity = null;
	private int mYear;
	private int mMonth;
	private int mDay;
	private TrafficMeterApplication app = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (TrafficMeterApplication)getApplication();
        final TabHost tabhost= getTabHost();
        tabhost.addTab(tabhost.newTabSpec("tab1")
        		.setIndicator(getString(R.string.wifi), getResources().getDrawable(R.drawable.wifi))
        		.setContent(new Intent(this,WifiActivity.class)));

        tabhost.addTab(tabhost.newTabSpec("tab2")
        		.setIndicator(getString(R.string.mobile), getResources().getDrawable(R.drawable.mobile))
        		.setContent(new Intent(this,MobileActivity.class)));

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        startService();
    }
    public void startService() {
		Intent intent = new Intent(this, TrafficService.class);
		startService(intent);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item = menu.add(TrafficConst.MENU_GROUP,
				TrafficConst.MENU_HELP, TrafficConst.MENU_HELP_ORDER,
				R.string.menu_help_title);
		item.setIcon(android.R.drawable.ic_menu_help);
		item = menu.add(TrafficConst.MENU_GROUP, TrafficConst.MENU_ABOUT,
				TrafficConst.MENU_ABOUT_ORDER, R.string.menu_about_title);
		item.setIcon(android.R.drawable.ic_menu_info_details);
		item = menu.add(TrafficConst.MENU_GROUP, TrafficConst.MENU_REFRESH,
				TrafficConst.MENU_REFRESH_ORDER, R.string.menu_refresh_title);
		item.setIcon(android.R.drawable.ic_menu_rotate);
		return true;
	}

	/**
	 * This hook is called whenever an item in your options menu is selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case TrafficConst.MENU_HELP:
			showDialog(TrafficConst.DIALOG_MENU_HELP);
			break;
		case TrafficConst.MENU_ABOUT:
			showDialog(TrafficConst.DIALOG_MENU_ABOUT);
			break;
		case TrafficConst.MENU_REFRESH:
			SharedPreferences prefs = app.getAdapter(SharedPreferences.class);
		    String startDay = prefs.getString(TrafficConst.STARTDAY, TrafficHelper.getDate(Calendar.getInstance()));
		    BaseTabActivity baseTabActivity = (BaseTabActivity)getCurrentActivity();
		    baseTabActivity.setStartDay(startDay);
			if (TrafficConst.MOBILEACTIVITY.equals(getCurrentActivity()
					.getLocalClassName())) {
				if (mobileActivity == null) {
					mobileActivity = (MobileActivity) getCurrentActivity();
				}
				mobileActivity.fillAdapter();
				mobileActivity.modelChanged();
				mobileActivity.animateList();
			}
			if (TrafficConst.WIFIACTIVITY.equals(getCurrentActivity()
					.getLocalClassName())) {
				if (wifiActivity == null) {
					wifiActivity = (WifiActivity) getCurrentActivity();
				}
				wifiActivity.fillAdapter();
				wifiActivity.modelChanged();
			}
			if (TrafficConst.DEBUG) {
				Log.d(TrafficConst.TAG,
						"TrafficMeter :onOptionsItemSelected className:"
								+ getCurrentActivity().getLocalClassName());
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * display the dialog after you created is
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TrafficConst.DIALOG_MENU_HELP: {
			WebView helpView = new WebView(this);
			helpView.loadUrl("file:///android_asset/help.html");
			helpView.setBackgroundColor(0);
			return new AlertDialog.Builder(this).setTitle(
					R.string.menu_help_title).setIcon(
					android.R.drawable.ic_menu_help).setPositiveButton(
					R.string.close, null).setView(helpView).create();
		}
		case TrafficConst.DIALOG_MENU_ABOUT: {
			WebView aboutView = new WebView(this);
			aboutView.loadUrl("file:///android_asset/about.html");
			aboutView.setBackgroundColor(0);
			return new AlertDialog.Builder(this).setTitle(
					R.string.menu_about_title).setIcon(
					android.R.drawable.ic_menu_info_details).setPositiveButton(
					R.string.close, null).setView(aboutView).create();
		}
		case TrafficConst.DIALOG_DAY: {
			DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					String startDay = year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth;
					if (TrafficConst.DEBUG) {
						Log.d(TrafficConst.TAG, "TrafficMeter :DIALOG_DAY:"
								+ startDay);
					}
				}

			};
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		default:
			break;
		}
		return super.onCreateDialog(id);
	}



}