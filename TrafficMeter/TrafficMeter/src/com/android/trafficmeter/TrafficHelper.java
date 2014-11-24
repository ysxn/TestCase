package com.android.trafficmeter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
/**
 *	Defined some base function
 * @author archermind
 *
 */
public class TrafficHelper {

	private static final String TAG = "TrafficHelper";
	private static final DateFormat DF_DATATIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final DateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 *	get properties value
	 * @param prop
	 * @return String
	 */
	private static String getProp(String prop) {
		String output = "";
		try {
			Class<?> sp = Class.forName("android.os.SystemProperties");
			Method get = sp.getMethod("get", String.class);
			output = (String)get.invoke(null, prop);
		} catch (Exception e) {
			return output;
		}
		return output;
	}

	/**
	 * try to read file ,if it's true, you can find it and can read,else false;
	 * @param filename
	 * @return
	 */
	public static boolean isUp(String filename) {
		File file = new File(filename);
		if(file.exists()) {
			if(file.canRead()) return true;
		}
		return false;
	}

	/**
	 * read file and return long
	 * @param filename
	 * @return
	 */
	public static long readLong(String filename) {
		long data = 0L;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr,256);
			String line = br.readLine();
			if(line != null) {
				data = Long.parseLong(line);
			}
		} catch (Exception e) {
			return 0L;
		}
		return data;
	}

	/**
	 * get the bytes from net when it's active
	 * @return long[] long[0]=rx; long[1]=tx
	 */
	public static long[] getNetworkBytes(Context context) {
		long[] data = new long[] {0,0}; //rx--Received,tx--translate
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			int networkType = networkInfo.getType();
			if(ConnectivityManager.TYPE_MOBILE == networkType) {
//				rxFile = "/sys/class/net/rmnet0/statistics/rx_bytes";
//				txFile = "/sys/class/net/rmnet0/statistics/tx_bytes";
				data[0] = TrafficStats.getMobileRxBytes();
				data[1] = TrafficStats.getMobileTxBytes();
				return data;
			} else if(ConnectivityManager.TYPE_WIFI == networkType) {
				data = getWifiBytes();
			}
		}

		return data;

	}

	/**
	 * get the bytes from wifi
	 * @return long[] long[0]=rx; long[1]=tx
	 */
	public static long[] getWifiBytes() {
		long[] data = new long[] {0,0}; //rx--Received,tx--translate
		String rxFile = "/sys/class/net/eth0/statistics/rx_bytes";
		String txFile = "/sys/class/net/eth0/statistics/tx_bytes";
		String wifiInterface = getProp("wifi.interface");
		if(wifiInterface == null || "".equals(wifiInterface)) {
			wifiInterface="eth0";
		}
		rxFile = "/sys/class/net/" + wifiInterface + "/statistics/rx_bytes";
		txFile = "/sys/class/net/" + wifiInterface + "/statistics/tx_bytes";
		if(isUp(rxFile)) {
			data[0] = readLong(rxFile);
		}
		if(isUp(txFile)) {
			data[1] = readLong(txFile);
		}
		return data;
	}

	/**
	 *  convert long to string with unit
	 * @param value
	 * @return
	 */
	public static String convertBytes(long value) {
		StringBuilder sb = new StringBuilder();
		if (value < 1024L) {
			sb.append(String.valueOf(value));
			sb.append(" B");
		} else if (value < 1048576L) {
			sb.append(String.format("%.1f", value / 1024.0));
			sb.append(" KB");
		} else if (value < 1073741824L) {
			sb.append(String.format("%.2f", value / 1048576.0));
			sb.append(" MB");
		} else if (value < 1099511627776L) {
			sb.append(String.format("%.3f", value / 1073741824.0));
			sb.append(" GB");
		} else {
			sb.append(String.format("%.4f", value / 1099511627776.0));
			sb.append(" TB");
		}
		return sb.toString();
	}

	/**
	 *	convert long to String
	 * @param duration
	 * @return
	 */
	public static String convertDuration(long duration) {
		String converted = "0 Second";
		long second = 0, min = 0, hour = 0;
		second = duration;
		converted = second + " Seconds";
		if(second > 60) {
			min = (long) Math.floor(second / 60);
			second = second % 60;
			converted = min + " Minutes " + second + " Seconds";
			if(min > 60) {
				hour = (long) Math.floor(min / 60);
				min = min % 60;
				converted = hour + " Hours " + min + " Minutes ";// + second + " Seconds";
			}
		}
		return converted;
	}

	public static String getDateTime(Calendar date) {
		return DF_DATATIME.format(date.getTime());
	}

	public static String getDate(Calendar date) {
		return DF_DATE.format(date.getTime());
	}

	/**
	 * convert the String to Calendar
	 * @param date
	 * @return
	 */
	public static Calendar parseDateTime(String date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(DF_DATATIME.parse(date));
		} catch (ParseException e) {
			Log.e(TAG, "can't parse date",e);
		}
		return c;
	}

	/**
	 * convert the String to Calendar
	 * @param date
	 * @return
	 */
	public static Calendar parseDate(String date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(DF_DATE.parse(date));
		} catch (ParseException e) {
			Log.e(TAG, "can't parse date",e);
		}
		return c;
	}

	public static String[] getBeginAndEndDayOfThisDay(Context context, long today) {
		String[] dates = new String[2];

		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		if (today == 0) {
			cal.setTimeInMillis(System.currentTimeMillis());
		} else {
			cal.setTimeInMillis(today);
		}

		dates[0] = sdf.format(cal.getTime());
		dates[1] = dates[0];
		return dates;
    }

	public static String[] getBeginAndEndDayOfThisWeek( long today) {
		String[] dates = new String[2];
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if(today == 0) {
			cal.setTimeInMillis(System.currentTimeMillis());
		} else {
			cal.setTimeInMillis(today);
		}
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week == 1) {
			week = 7;
		} else if(week ==0) {
			week = 6;
		} else{
			week -=1;
		}

		// 取距离当前日期的周一(日)与当前日期相差的天数
		int count = 0; // count
		count = 7-week; // 取距离当前日期的周日与当前日期相差的天数
		cal.add(Calendar.DAY_OF_WEEK, count);
		String sunday = df.format(cal.getTime());

		Calendar calTemp = Calendar.getInstance();
		if(today == 0) {
			calTemp.setTimeInMillis(System.currentTimeMillis());
		} else {
			calTemp.setTimeInMillis(today);
		}
		count = -week+1;// 取距离当前日期的周一与当前日期相差的天数
		calTemp.add(Calendar.DAY_OF_WEEK, count);
		String monday = df.format(calTemp.getTime());
		dates[0]=monday;
		dates[1]=sunday;
		return dates;
	}

	public static String[] getBeginAndEndDayOfThisMonth(Context context, long today) {
		String[] dates = new String[2];

		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		if (today == 0) {
			cal.setTimeInMillis(System.currentTimeMillis());
		} else {
			cal.setTimeInMillis(today);
		}

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		int startDay = new Integer(pref.getString("start_date", "1")).intValue();
		int nowDay = cal.get(Calendar.DAY_OF_MONTH);

		if(nowDay < startDay) {
			cal.add(Calendar.MONTH, -1);
		}
		cal.set(Calendar.DAY_OF_MONTH, startDay);
		String beginDate = sdf.format(cal.getTime());

//		Calendar startCal = Calendar.getInstance();
//		startCal.setTimeInMillis(cal.getTimeInMillis());
//		startCal.set(Calendar.DAY_OF_MONTH, startDay);
//
//		if(startCal.getTimeInMillis() > cal.getTimeInMillis()) {
//			startCal.add(Calendar.MONTH, -1);
//			cal = startCal;
//		}
//		String beginDate = sdf.format(cal.getTime());

		//the first date of next month
		cal.add(Calendar.MONTH, 1);
		//then yesterday of next month first day
		//is the end day of this month
		cal.add(Calendar.DATE, -1);
		String endDate = sdf.format(cal.getTime());

		dates[0] = beginDate;
		dates[1] = endDate;
		return dates;
	}

	public static String[] getBeginAndEndDayOfOtherDay(Context context, String datePeriod, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");

		String[] dates = datePeriod.split(" - ");
		try {
			Calendar cal = Calendar.getInstance();
			Date date = sdf.parse(dates[0]);
			cal.setTimeInMillis(date.getTime());
			cal.add(Calendar.DAY_OF_MONTH, day);
			dates = getBeginAndEndDayOfThisDay(context, cal.getTimeInMillis());
		} catch (ParseException e) {
			Log.i("TrafficHelper", "Wrong date format for parsing");
		}
		return dates;
	}

	public static String[] getBeginAndEndDayOfOtherWeek( String today,int day) {
		String[] dates = new String[2];
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			Calendar cal = Calendar.getInstance();
			date = df.parse(today);
			if(today == null) {
				cal.setTimeInMillis(System.currentTimeMillis());
			} else {
				cal.setTimeInMillis(date.getTime());
			}
			cal.add(Calendar.WEEK_OF_MONTH, day);
			dates=getBeginAndEndDayOfThisWeek(cal.getTimeInMillis());
		} catch (ParseException e) {
			Log.i("TrafficHelper", "Wrong date format for parsing");
		}
		return dates;
	}

	/**
	 * dataPeriod
	 * @param datePeriod format is 2008-12-01 - 2008-12-31
	 * @param month how much month to given date period, if value is 1
	 *              returned value should be next month date period, if
	 *              value is -1 return the date period of previous month
	 * @return String[] Start date of the month and the end date
	 */
	public static String[] getBeginAndEndDayOfOtherMonth(Context context, String datePeriod, int month) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");

		String[] dates = datePeriod.split(" - ");
		try {
			Calendar cal = Calendar.getInstance();
			Date date = sdf.parse(dates[0]);
			cal.setTimeInMillis(date.getTime());
			cal.add(Calendar.MONTH, month);
			dates = getBeginAndEndDayOfThisMonth(context, cal.getTimeInMillis());
		} catch (ParseException e) {
			Log.i("TrafficHelper", "Wrong date format for parsing");
		}
		return dates;
	}

	/**
	 * get day before today
	 * @param today
	 * @return
	 */
	public static String getPreviousDay(long today) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if(today == 0) {
			cal.setTimeInMillis(System.currentTimeMillis());
		} else {
			cal.setTimeInMillis(today);
		}
		cal.add(Calendar.DAY_OF_WEEK, -1);
		String previousDay = df.format(cal.getTime());
		return previousDay;
	}

	/**
	 * get day before today
	 * @param today
	 * @return
	 */
	public static String getPreviousDay(Calendar today) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(today == null) {
			today = Calendar.getInstance();
		}
		today.add(Calendar.DAY_OF_WEEK, -1);
		String previousDay = df.format(today.getTime());
		System.out.println("preday"+previousDay);
		return previousDay;
	}

	public static void toast(Context context, int message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void toast(Context context, CharSequence message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
