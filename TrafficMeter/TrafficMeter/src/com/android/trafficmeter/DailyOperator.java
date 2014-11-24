package com.android.trafficmeter;

import java.util.Calendar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *	Describes the logic of the daily operator from network
 *
 * @author archermind
 *
 */
public class DailyOperator {
	private static String mlastUpdate;
	public long startedReceived = 0;
	public long startedSent = 0;
	public long deltaReceived = 0;
	public long deltaSent = 0;
	private static final int DAY_OF_YEAR = Calendar.DAY_OF_YEAR;

	/**
	 *	get the bytes from network or DB,store it
	 * @param context
	 * @param isBootCompleted  The isBootCompleted is true when the device is loader ,else is false
	 */
	public void trackData(Context context,boolean isBootCompleted) {
		Calendar now = Calendar.getInstance();
		String curday = TrafficHelper.getDate(now);
		ConnectivityManager connMng = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = connMng.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnected()) {
			long[] data = TrafficHelper.getNetworkBytes(context);
			String interfase = getInterface(netinfo);
			if(isBootCompleted){
				DailyModel lastModel= DailyModel.findPreviousDayTraffic(context,interfase,curday);
				if(lastModel.getRx()>0) data[0]+=lastModel.getRx();
				if(lastModel.getTx()>0) data[1]+=lastModel.getTx();
			}
			if (isNewDay(curday)) {
				if(data[0]>0 && data[1]>0) {
					String previousDay = TrafficHelper.getPreviousDay(System.currentTimeMillis());
					if(!isBootCompleted) {
						DailyModel lastModel= DailyModel.findPreviousDayTraffic(context,interfase,previousDay);
						if(lastModel.getRx()>0) data[0]=lastModel.getRx();
						if(lastModel.getTx()>0) data[1]=lastModel.getTx();
					}
					DailyModel dailymodel= new DailyModel();
					dailymodel.setInterfase(interfase);
					dailymodel.setDay(curday);
					dailymodel.setLastRx(data[0]);
					dailymodel.setLastTx(data[1]);
					String startedAt = TrafficHelper.getDateTime(now);
					dailymodel.setStartedAt(startedAt);
					dailymodel.create(context);
					startedReceived = data[0];
					startedSent = data[1];
					mlastUpdate = curday;
				}
			} else {
				if(data[0]>0 && data[1]>0) {
					DailyModel dailymodel= new DailyModel();
					dailymodel.setInterfase(interfase);
					dailymodel.setRx(data[0]);
					dailymodel.setTx(data[1]);
					deltaReceived = data[0]-startedReceived;
					deltaSent = data[1]-startedSent;
					dailymodel.setDeltaRx(deltaReceived);
					dailymodel.setDeltaTx(deltaSent);
					String endedAt = TrafficHelper.getDateTime(now);
					dailymodel.setEndedAt(endedAt);
					dailymodel.setDay(curday);
					dailymodel.update(context);
					mlastUpdate = curday;
				}
			}


		}
	}

	/**
	 *	get the type of network
	 * @param netinfo NetworkInfo
	 * @return String
	 */

	public String getInterface(NetworkInfo netinfo) {
		String interfase = "";
		if (ConnectivityManager.TYPE_WIFI == netinfo.getType())
			interfase = TrafficConst.WIFI;
		if (ConnectivityManager.TYPE_MOBILE == netinfo.getType())
			interfase = TrafficConst.MOBILE;
		return interfase;
	}

	/**
	 * judge it whether it is a new day.return true when it's a new day,else return false
	 * @param now
	 * @return boolean
	 */
	public boolean isNewDay(String now) {
		if (mlastUpdate == null) {
			return true;
		} else {
			Calendar nowCalendar = TrafficHelper.parseDate(now);
			Calendar lastCalendar = TrafficHelper.parseDate(mlastUpdate);
			return lastCalendar.get(DAY_OF_YEAR) != nowCalendar.get(DAY_OF_YEAR);
		}
	}

	public static String getMlastUpdate() {
		return mlastUpdate;
	}

	public static void setMlastUpdate(String lastUpdate) {
		mlastUpdate = lastUpdate;
	}

}
