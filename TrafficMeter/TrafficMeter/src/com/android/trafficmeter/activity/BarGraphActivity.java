package com.android.trafficmeter.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ViewFlipper;

import com.android.trafficmeter.DailyModel;
import com.android.trafficmeter.R;
import com.android.trafficmeter.TrafficConst;
import com.android.trafficmeter.TrafficHelper;
import com.android.trafficmeter.DatabaseHelper.DailyColumns;

/**
 * An activity for show the bar graph
 * @author archermind
 *
 */
public class BarGraphActivity extends Activity {

	private ViewFlipper viewFlipper;
	private GraphWork graphWorker;
	private String mInterface;
	private Cursor mCursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInterface = getIntent().getStringExtra(TrafficConst.INTENT_EXTRA_INTERFACE);
		setContentView(R.layout.graph);
		viewFlipper = (ViewFlipper) findViewById(R.id.graph);

		if(mCursor != null) {
			stopManagingCursor(mCursor);
		}
		String[] currentDates = TrafficHelper.getBeginAndEndDayOfThisMonth(this, System.currentTimeMillis());
		mCursor = DailyModel.findMonthTraffic(this, mInterface, currentDates);
		startManagingCursor(mCursor);


		graphWorker= new GraphWork();
		graphWorker.execute(1);
	}

	@Override
	protected void onDestroy() {
		if(mCursor != null) {
			stopManagingCursor(mCursor);
		}
		if (graphWorker != null) {
			graphWorker.cancel(true);
			graphWorker = null;
		}
		super.onDestroy();
	}

	private class GraphWork extends AsyncTask<Integer, Void, GraphicalView> {
		@Override
		protected GraphicalView doInBackground(Integer... params) {
			XYMultipleSeriesDataset dataset = buildBarDataSet() ;
			XYMultipleSeriesRenderer renderer =  buildBarRender();
			XYSeries series = dataset.getSeriesAt(0);
			double diff = series.getMaxX()-series.getMinX();
			diff/=15;
			renderer.setXAxisMin(series.getMinX()-diff);
			renderer.setXAxisMax(series.getMaxX()+diff);
			renderer.setYAxisMin(0);
			return ChartFactory.getTimeBarChartView(BarGraphActivity.this, dataset, renderer, Type.DEFAULT, "d");
		}

		@Override
		protected void onPostExecute(GraphicalView result) {
			viewFlipper.addView(result);
			viewFlipper.setDisplayedChild(1);
		}

	}

	private XYMultipleSeriesDataset buildBarDataSet() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		TimeSeries rx = new TimeSeries(getString(R.string.graphUp));
		TimeSeries tx = new TimeSeries(getString(R.string.graphDown));
		Cursor c = mCursor;
		for (int i = 0; i < c.getCount(); i++) {
			c.moveToNext();
			String mday = c.getString(c.getColumnIndex(DailyColumns.DAY));
			long up = c.getLong(c.getColumnIndex(DailyColumns.DELTA_TX));
			long down = c.getLong(c.getColumnIndex(DailyColumns.DELTA_RX));
			Calendar day = TrafficHelper.parseDate(mday);
			day.set(Calendar.SECOND, 0);
			day.set(Calendar.MINUTE, 0);
			day.set(Calendar.HOUR, 0);
			rx.add(day.getTime(), down);

			tx.add(day.getTime(), up);
		}
//		Date date1 =getDate("2010-11-24");
//		Date date2 =getDate("2010-11-26");
//		Date date3 =getDate("2010-11-28");
//		Date date4 =getDate("2010-11-30");
//		rx.add(date1, 100);
//		rx.add(date2, 200);
//		rx.add(date3, 150);
//		rx.add(date4, 100);
//
//		tx.add(date1, 150);
//		tx.add(date2, 100);
//		tx.add(date3, 350);
//		tx.add(date4, 600);
		dataset.addSeries(rx);
		dataset.addSeries(tx);
		return dataset;
	}

	private XYMultipleSeriesRenderer buildBarRender() {
		XYMultipleSeriesRenderer render = new XYMultipleSeriesRenderer();
		SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
//		ssr.setColor(Color.rgb(185, 190, 221));
		ssr.setColor(Color.MAGENTA);
		render.addSeriesRenderer(ssr);
		ssr = new SimpleSeriesRenderer();
//		ssr.setColor(Color.rgb(138, 148, 198));
		ssr.setColor(Color.CYAN);
		render.addSeriesRenderer(ssr);

		render.setYTitle(getString(R.string.graphYTitle));
		render.setXLabels(15);
		return render;

	}

	public Date getDate(String date) {
		Date mDate = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		try {
			mDate=sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mDate;
	}
}
