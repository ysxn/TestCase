package com.android.trafficmeter.activity;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.trafficmeter.DailyModel;
import com.android.trafficmeter.R;
import com.android.trafficmeter.TrafficConst;
import com.android.trafficmeter.TrafficHelper;
import com.android.trafficmeter.TrafficMeterApplication;
import com.android.trafficmeter.DatabaseHelper.DailyColumns;

/**
 * An activity that contains the base detail information
 * @author archermind
 *
 */
public class DetailActivity extends ListActivity {

	protected ArrayList<DailyModel.StatisticsInfo> statisticsList;
	protected String[] currentDates=new String[2];
	protected String startDay;
	protected ListView listView = null;
	private Cursor mCursor;
	private String mInterface;
	private DetailAdapter detailAdapter;
	private String dateType;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInterface = getIntent().getStringExtra(TrafficConst.INTENT_EXTRA_INTERFACE);
        setTitle(mInterface);
        dateType = getIntent().getStringExtra(TrafficConst.INTENT_EXTRA_DATE_TYPE);
        TrafficMeterApplication app = (TrafficMeterApplication)getApplication();
        SharedPreferences prefs = app.getAdapter(SharedPreferences.class);
        startDay = prefs.getString(TrafficConst.STARTDAY, TrafficHelper.getDate(Calendar.getInstance()));
	}



	@Override
	protected void onResume() {
		if(mCursor!=null) {
			stopManagingCursor(mCursor);
		}
		 mCursor = getCursor(dateType);
        startManagingCursor(mCursor);
        detailAdapter = new DetailAdapter(this,mCursor);
        setListAdapter(detailAdapter);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		statisticsList = null;
		currentDates = null;
		startDay = null;
		listView = null;
		stopManagingCursor(mCursor);
		super.onDestroy();
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
		mCursor= DailyModel.findMonthTraffic(this, mInterface, currentDates);
		return mCursor;
	}

	public class DetailAdapter extends CursorAdapter{

		public DetailAdapter(Context context, Cursor c) {
			super(context, c);
			notifyDataSetChanged();
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			if(cursor.getCount() <= 0) {
				TextView textView = (TextView)view;
				textView.setText(getResources().getString(R.string.no_data));
			} else {
				String mday = cursor.getString(cursor.getColumnIndex(DailyColumns.DAY));
				long up = cursor.getLong(cursor.getColumnIndex(DailyColumns.DELTA_TX));
				long down = cursor.getLong(cursor.getColumnIndex(DailyColumns.DELTA_RX));
				TextView dayView = (TextView)view.findViewById(R.id.day);
				dayView.setText(mday);
				TextView dayUpView = (TextView)view.findViewById(R.id.dayUp);
				dayUpView.setText(TrafficHelper.convertBytes(up));
				TextView dayDownView = (TextView)view.findViewById(R.id.dayDown);
				dayDownView.setText(TrafficHelper.convertBytes(down));
				TextView dayTotalView = (TextView)view.findViewById(R.id.dayTotal);
				dayTotalView.setText(TrafficHelper.convertBytes(up+down));
			}
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = null;
			if(cursor.getCount() <= 0) {
				TextView textView = new TextView(context);
				textView.setText(getResources().getString(R.string.no_data));
				view = textView;
			} else {
				view = getLayoutInflater().inflate(R.layout.detail, null);
			}
			return view;
		}

	}
}
