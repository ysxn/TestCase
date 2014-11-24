package com.android.trafficmeter.activity;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class DetailDailog extends AlertDialog {

	protected ArrayList<DailyModel.StatisticsInfo> statisticsList;
	protected String[] currentDates=new String[2];
	protected String startDay;
	protected ListView listView = null;
	private Cursor mCursor;

	protected DetailDailog(Context context,TrafficMeterApplication app,String dateType) {
		super(context);
//		mView = getLayoutInflater().inflate(R.layout.detail, null);
//		setView(mView);
//		String dateType = getIntent().getStringExtra(TrafficConst.INTENT_EXTRA_DATE_TYPE);
//        TrafficMeterApplication app = (TrafficMeterApplication)getApplication();
        SharedPreferences prefs = app.getAdapter(SharedPreferences.class);
        startDay = prefs.getString(TrafficConst.STARTDAY, TrafficHelper.getDate(Calendar.getInstance()));
        mCursor = getCursor(context,dateType);
//        startManagingCursor(mCursor);
        listView = getListView();
//        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.detail, mCursor,
//        		new String[] {DailyColumns.DAY,DailyColumns.DELTA_TX,DailyColumns.DELTA_RX,},
//        		new int[]{R.id.day,R.id.dayUp,R.id.dayDown});
        DetailAdapter detailAdapter = new DetailAdapter(context,mCursor);
        listView.setAdapter(detailAdapter);
	}

	public Cursor getCursor(Context context,String dateType) {
		if(TrafficConst.WEEK.equals(dateType)) {
			currentDates = TrafficHelper.getBeginAndEndDayOfThisWeek(System.currentTimeMillis());
//			cursor= DailyModel.findMonthTraffic(this, TrafficConst.MOBILE, currentDates);
//			statisticsList.add(weekStats);
		} else if(TrafficConst.MONTH.equals(dateType)) {
			currentDates = TrafficHelper.getBeginAndEndDayOfThisMonth(context, System.currentTimeMillis());
//			cursor= DailyModel.findMonthTraffic(this, TrafficConst.MOBILE, currentDates, TrafficConst.MONTH);
//			statisticsList.add(monthStats);
		} else if(TrafficConst.TOTAL.equals(dateType)) {
		String date = TrafficHelper.getDate(Calendar.getInstance());
		currentDates[0]=startDay;
		currentDates[1]=date;
//			DailyModel.StatisticsInfo allStats = DailyModel.findTotalTraffic(this, TrafficConst.MOBILE, startDay, TrafficConst.TOTAL);
//			statisticsList.add(allStats);
		}

		mCursor= DailyModel.findMonthTraffic(context, TrafficConst.MOBILE, currentDates);
//		startManagingCursor(mCursor);
		return mCursor;
	}


	public class DetailAdapter extends CursorAdapter{

		public DetailAdapter(Context context, Cursor c) {
			super(context, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToNext();
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
			View view = getLayoutInflater().inflate(R.layout.detail, null);
			return view;
		}

	}
}
