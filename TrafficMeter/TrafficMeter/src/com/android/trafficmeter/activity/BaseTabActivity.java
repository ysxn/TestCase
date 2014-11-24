package com.android.trafficmeter.activity;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.android.trafficmeter.DailyModel;
import com.android.trafficmeter.R;
import com.android.trafficmeter.TrafficConst;
import com.android.trafficmeter.TrafficHelper;
import com.android.trafficmeter.TrafficMeterApplication;
import com.android.trafficmeter.DailyModel.StatisticsInfo;
import com.android.trafficmeter.DatabaseHelper.DailyColumns;

/**
 *	A base tab activity that performance some base actions.
 * @author archermind
 *
 */
public abstract class BaseTabActivity extends Activity implements OnItemClickListener{
	protected String mInterfase ;
	protected ListView listView = null;
	protected String[] currentDates;
	protected TrafficAdapter trafficAdapter;
	protected ArrayList<DailyModel.StatisticsInfo> statisticsList;
	protected String startDay;
	protected long started ;

	protected int mYear;
	protected int mMonth;
	protected int mDay;
	protected String dateType = null;
	protected TrafficMeterApplication app = null;
	private Cursor mCursor;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInterface();
        statisticsList = new ArrayList<DailyModel.StatisticsInfo>();
        app = (TrafficMeterApplication)getApplication();
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.statistics);
        listView.setOnItemClickListener(this);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        registerForContextMenu(listView);
    }

	@Override
	protected void onDestroy() {
		unregisterForContextMenu(listView);
		currentDates = null;
		statisticsList = null;
		trafficAdapter = null;
		listView = null;
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "BaseTabActivity :onPause time:"+(System.currentTimeMillis()-started));
		}
		super.onPause();
		started = System.currentTimeMillis();
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "BaseTabActivity :list clear time:"+(System.currentTimeMillis()-started));
			started = System.currentTimeMillis();

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = app.getAdapter(SharedPreferences.class);
       startDay = prefs.getString(TrafficConst.STARTDAY, TrafficHelper.getDate(Calendar.getInstance()));
		started=System.currentTimeMillis();
		listView.setAdapter(fillAdapter());
		animateList();
		if(TrafficConst.DEBUG) {
			Log.d(TrafficConst.TAG, "BaseTabActivity :fillAdapter time:"+(System.currentTimeMillis()-started));
			started = System.currentTimeMillis();

		}
	}

	protected TrafficAdapter fillAdapter(){
		statisticsList.clear();
		TrafficAdapter adapter = prepareAdapter();
		return adapter;
	}
	protected abstract TrafficAdapter prepareAdapter();
	protected abstract void setInterface();
	public String getInterface() {
		return mInterfase;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if(TrafficConst.DIALOG_DETAIL == id) {
			AlertDialog alert = (AlertDialog) dialog;
			if (alert==null) return ;
			ListView mListView = alert.getListView();
			mCursor = getCursor(dateType);
	        startManagingCursor(mCursor);
	        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.detail, mCursor,
	        		new String[] {DailyColumns.DAY,DailyColumns.DELTA_TX,DailyColumns.DELTA_RX,},
	        		new int[]{R.id.day,R.id.dayUp,R.id.dayDown});
	        mListView.setAdapter(adapter);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case TrafficConst.DIALOG_DAY: {
			DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					String tempMonth = monthOfYear>10?""+(monthOfYear+1):"0"+(monthOfYear+1);
					String tempday = dayOfMonth>10?""+dayOfMonth:"0"+dayOfMonth;
					String startDay = year+"-"+tempMonth+"-"+tempday;
					SharedPreferences prefs = app.getAdapter(SharedPreferences.class);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString(TrafficConst.STARTDAY, startDay);
					editor.commit();
					if(TrafficConst.DEBUG) {
						Log.d(TrafficConst.TAG, "TrafficMeter :DIALOG_DAY:"+startDay);
					}
				}

			};
			return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
		}
		case TrafficConst.DIALOG_DETAIL: {
//			return new DetailDailog(this,app,dateType);
			return new AlertDialog.Builder(this).setItems(0, null).create();
		}
		case TrafficConst.DIALOG_COUNTERTYPE: {
			DialogInterface.OnClickListener buttonClick = new DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					if(which>=0) {
						String counterType = TrafficMeterApplication.COUNTER_TYPES[which].toString();
						TrafficHelper.toast(getApplicationContext(), counterType);
						String today = TrafficHelper.getDate(Calendar.getInstance());
						if(TrafficConst.DAY.equals(counterType)) {
							String[] currentDates = TrafficHelper.getBeginAndEndDayOfThisDay(getApplicationContext(), System.currentTimeMillis());
							DailyModel.StatisticsInfo monthStats = DailyModel.findMonthTraffic(getApplicationContext(), getInterface(), currentDates, TrafficConst.DAY);
							statisticsList.add(monthStats);
							modelChanged();
						} else if(TrafficConst.WEEK.equals(counterType)) {
							String[] currentDates = TrafficHelper.getBeginAndEndDayOfThisWeek(System.currentTimeMillis());
							DailyModel.StatisticsInfo monthStats = DailyModel.findMonthTraffic(getApplicationContext(), getInterface(), currentDates, TrafficConst.WEEK);
							statisticsList.add(monthStats);
							modelChanged();
						} else	if(TrafficConst.MONTH.equals(counterType)) {
							String[] currentDates = TrafficHelper.getBeginAndEndDayOfThisMonth(getApplicationContext(), System.currentTimeMillis());
							DailyModel.StatisticsInfo monthStats = DailyModel.findMonthTraffic(getApplicationContext(), getInterface(), currentDates, TrafficConst.MONTH);
							statisticsList.add(monthStats);
							modelChanged();
						} else if(TrafficConst.TOTAL.equals(counterType)) {
							DailyModel.StatisticsInfo allStats = DailyModel.findTotalTraffic(getApplicationContext(), getInterface(), startDay, TrafficConst.TOTAL);
							statisticsList.add(allStats);
							modelChanged();
						} else if(TrafficConst.YESTERDAY.equals(counterType)) {
							String[] currentDates = TrafficHelper.getBeginAndEndDayOfOtherDay(getApplicationContext(), today, -1);
							DailyModel.StatisticsInfo dayStats = DailyModel.findMonthTraffic(getApplicationContext(), getInterface(), currentDates, TrafficConst.YESTERDAY);
							statisticsList.add(dayStats);
							modelChanged();
						} else if(TrafficConst.LAST_WEEK.equals(counterType)) {
							String[] currentDates = TrafficHelper.getBeginAndEndDayOfOtherWeek(today, -1);
							DailyModel.StatisticsInfo dayStats = DailyModel.findMonthTraffic(getApplicationContext(), getInterface(), currentDates, TrafficConst.LAST_WEEK);
							statisticsList.add(dayStats);
							modelChanged();
						} else if(TrafficConst.LAST_MONTH.equals(counterType)) {
							String[] currentDates = TrafficHelper.getBeginAndEndDayOfOtherMonth(getApplicationContext(), today, -1);
							DailyModel.StatisticsInfo dayStats = DailyModel.findMonthTraffic(getApplicationContext(), getInterface(), currentDates, TrafficConst.LAST_MONTH);
							statisticsList.add(dayStats);
							modelChanged();
						}

					}
					else{
						if(which == DialogInterface.BUTTON_NEGATIVE) {
							TrafficHelper.toast(getApplicationContext(), R.string.alert_dialog_cancel);
						}
					}
					dialog.dismiss();
				}

			};
			return new AlertDialog.Builder(this).setSingleChoiceItems(
					TrafficMeterApplication.COUNTER_TYPES, 1, buttonClick).setNegativeButton(R.string.alert_dialog_cancel,buttonClick).create();
		}
		default:break;
	}
		return super.onCreateDialog(id);
	}

	/**
	 *	called when a context menu for the view is about to be shown.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		 menu.setHeaderTitle(R.string.counter);
		 menu.add(TrafficConst.CONTEXTMENU_GROUP, TrafficConst.CONTEXTMENU_COUNTER_DAY, TrafficConst.CONTEXTMENU_COUNTER_DAY_ORDER, R.string.menucontext_startDay);
		 menu.add(TrafficConst.CONTEXTMENU_GROUP, TrafficConst.CONTEXTMENU_GRAPH, TrafficConst.CONTEXTMENU_GRAPH_ORDER, R.string.menucontext_graph);
		 menu.add(TrafficConst.CONTEXTMENU_GROUP, TrafficConst.CONTEXTMENU_COUNTER_ADD, TrafficConst.CONTEXTMENU_COUNTER_ADD_ORDER, R.string.menucontext_counter_add);
		 menu.add(TrafficConst.CONTEXTMENU_GROUP, TrafficConst.CONTEXTMENU_COUNTER_REMOVE, TrafficConst.CONTEXTMENU_COUNTER_REMOVE_ORDER, R.string.menucontext_counter_remove);
	}

	/**
	 * This hook is called whenever an item in a context menu is selected.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo adapterContextMenuInfo= (AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId()) {
		case TrafficConst.CONTEXTMENU_COUNTER_DAY: showDialog(TrafficConst.DIALOG_DAY);return true;
		case TrafficConst.CONTEXTMENU_GRAPH: {
			Intent intent = new Intent(this,BarGraphActivity.class);
			intent.putExtra(TrafficConst.INTENT_EXTRA_INTERFACE, getInterface());
			startActivity(intent);
			return true;
		}
		case TrafficConst.CONTEXTMENU_COUNTER_ADD:{
			showDialog(TrafficConst.DIALOG_COUNTERTYPE);
			return true;
		}
		case TrafficConst.CONTEXTMENU_COUNTER_REMOVE: {
			statisticsList.remove(adapterContextMenuInfo.position);
			modelChanged();
			TrafficHelper.toast(this,R.string.menucontext_counter_remove);
			return true;
		}
		}
		return false;
	}

	/**
	 * This hook is called whenever an item in a view is selected.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		dateType = null;
		StatisticsInfo stats= (StatisticsInfo)statisticsList.get(position);
		if(TrafficConst.WEEK.equals(stats.statstype)) {
			dateType = TrafficConst.WEEK;
		} else if(TrafficConst.MONTH.equals(stats.statstype)) {
			dateType = TrafficConst.MONTH;
		} else if(TrafficConst.TOTAL.equals(stats.statstype)) {
			dateType = TrafficConst.TOTAL;
		}
		if(dateType != null) {
			Intent intent = new Intent(this,DetailActivity.class);
			intent.putExtra(TrafficConst.INTENT_EXTRA_DATE_TYPE, dateType);
			intent.putExtra(TrafficConst.INTENT_EXTRA_INTERFACE, getInterface());
			startActivity(intent);
//			showDialog(TrafficConst.DIALOG_DETAIL);
		}

	}
	public Cursor getCursor(String dateType) {
		if(TrafficConst.WEEK.equals(dateType)) {
			currentDates = TrafficHelper.getBeginAndEndDayOfThisWeek(System.currentTimeMillis());
		} else if(TrafficConst.MONTH.equals(dateType)) {
			currentDates = TrafficHelper.getBeginAndEndDayOfThisMonth(this, System.currentTimeMillis());
		} else if(TrafficConst.TOTAL.equals(dateType)) {
		String date = TrafficHelper.getDate(Calendar.getInstance());
		currentDates[0]=startDay;
		currentDates[1]=date;
		}
		mCursor= DailyModel.findMonthTraffic(this, TrafficConst.MOBILE, currentDates);
		return mCursor;
	}

	/**
	 * add some animates for ListView
	 */
	public void animateList() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f,1.0f);
		animation.setDuration(150);
		set.addAnimation(animation);
		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(150);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set,0.5f);
		listView.setLayoutAnimation(controller);
	}

	/**
	 * Runs the specified action on the UI thread to notice the message of model's changed
	 */
	public void modelChanged() {
		runOnUiThread(new Runnable() {
			public void run() {
				trafficAdapter.notifyDataSetChanged();
			}

		}) ;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

}
