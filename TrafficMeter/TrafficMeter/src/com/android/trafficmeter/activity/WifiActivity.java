package com.android.trafficmeter.activity;

import com.android.trafficmeter.DailyModel;
import com.android.trafficmeter.TrafficConst;
import com.android.trafficmeter.TrafficHelper;

/**
 *	An activity that contains some wifi's information.
 * @author archermind
 *
 */
public class WifiActivity extends BaseTabActivity {
	@Override
	protected TrafficAdapter prepareAdapter() {
		currentDates = TrafficHelper.getBeginAndEndDayOfThisDay(this, System.currentTimeMillis());
		DailyModel.StatisticsInfo dayStats = DailyModel.findMonthTraffic(this, TrafficConst.WIFI, currentDates, TrafficConst.DAY);
		statisticsList.add(dayStats);
		currentDates = TrafficHelper.getBeginAndEndDayOfThisWeek(System.currentTimeMillis());
		DailyModel.StatisticsInfo weekStats = DailyModel.findMonthTraffic(this, TrafficConst.WIFI, currentDates, TrafficConst.WEEK);
		statisticsList.add(weekStats);
		currentDates = TrafficHelper.getBeginAndEndDayOfThisMonth(this, System.currentTimeMillis());
		DailyModel.StatisticsInfo monthStats = DailyModel.findMonthTraffic(this, TrafficConst.WIFI, currentDates, TrafficConst.MONTH);
		statisticsList.add(monthStats);

		DailyModel.StatisticsInfo allStats = DailyModel.findTotalTraffic(this, TrafficConst.WIFI, startDay, TrafficConst.TOTAL);
		statisticsList.add(allStats);

		if(trafficAdapter == null) {
			trafficAdapter = new TrafficAdapter(this);
		}
		trafficAdapter.setMStatisticsInfos(statisticsList);
		return trafficAdapter;
	}

	@Override
	protected void setInterface() {
		this.mInterfase = TrafficConst.WIFI;
	}

}
