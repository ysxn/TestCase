package com.android.trafficmeter;

import java.util.ArrayList;
import java.util.Calendar;

import com.android.trafficmeter.DatabaseHelper.DailyColumns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * The class to perform some action to manage database.
 * @author archermind
 *
 */
public class DailyModel {

	private long id;
	private String interfase;
	private String networktype;
	private String day;
	private long lastTx = 0;  //the first sent bytes of this day
	private long lastRx = 0; //the first received bytes of this day
	private long tx = 0;  // the current sent bytes of this day
	private long rx = 0;  // the current received bytes of this day
	private long deltaTx = 0;  // the sent bytes of this day
	private long deltaRx = 0;  // the received bytes of this day
	private String startedAt;
	private String endedAt;
	private String[] dates;

	private final static String nullColumnHack = "_id";
	private final static String[] columns = new String[] { "_id", "interface", "networktype", "day", "last_rx", "last_tx", "rx", "tx","delta_rx","delta_tx", "started_at", "ended_at" };

	public synchronized static ArrayList<DailyModel> findByMonth(SQLiteDatabase db, String[] dates, String count){
		String selection = "day between ? and ?";
		String[] selectionArgs = new String[] { dates[0], dates[1] };
		String groupBy = null;
		String having = null;
		String orderBy = "id desc";
		String limit = count;

		return fillData(db, selection, selectionArgs, groupBy, having, orderBy, limit);
	}

	public synchronized static ArrayList<DailyModel> load(SQLiteDatabase db,int amount) {
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = "id desc";
		String limit = null;
		if (amount != 0) limit = Integer.toString(amount);
		return fillData(db,selection,selectionArgs,groupBy,having,orderBy,limit);
	}

	public synchronized static ArrayList<DailyModel> load(Context context,int amount) {

		DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = "id desc";
		String limit = null;
		if (amount != 0) limit = Integer.toString(amount);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ArrayList<DailyModel> list =fillData(db,selection,selectionArgs,groupBy,having,orderBy,limit);
		db.close();
		return list;
	}

	public  synchronized static ArrayList<DailyModel> fillData(SQLiteDatabase db, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
		ArrayList<DailyModel> list = new ArrayList<DailyModel>();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DailyColumns.TABLE_NAME);
		Cursor c = qb.query(db, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		c.moveToFirst();
		for(int i = 0; i<c.getCount(); i++) {
			DailyModel model = new DailyModel();
			model.setId(c.getInt(c.getColumnIndex(DailyColumns._ID)));
			model.setInterfase(c.getString(c.getColumnIndex(DailyColumns.INTERFACE)));
			model.setDay(c.getString(c.getColumnIndex(DailyColumns.DAY)));
			model.setLastRx(c.getLong(c.getColumnIndex(DailyColumns.LAST_RX)));
			model.setLastTx(c.getLong(c.getColumnIndex(DailyColumns.LAST_TX)));
			model.setRx(c.getLong(c.getColumnIndex(DailyColumns.RX)));
			model.setTx(c.getLong(c.getColumnIndex(DailyColumns.TX)));
			model.setStartedAt(c.getString(c.getColumnIndex(DailyColumns.STARTED_AT)));
			model.setEndedAt(c.getString(c.getColumnIndex(DailyColumns.ENDED_AT)));
			list.add(model);
			c.moveToNext();
		}
		c.close();
		return list;
	}

	public synchronized long create(Context context) {
		long id = 0;
		DatabaseHelper helper = DatabaseHelper.getInstance(context);
		SQLiteDatabase db = null;

		ContentValues values = new ContentValues();
		values.put(DailyColumns.INTERFACE, interfase);
		values.put(DailyColumns.NETWORKTYPE, networktype);
		if(day == null) {
			Calendar c = Calendar.getInstance();
			day = TrafficHelper.getDate(c);
		}
		values.put(DailyColumns.DAY, day);
		values.put(DailyColumns.LAST_RX, lastRx);
		values.put(DailyColumns.LAST_TX, lastTx);
		values.put("started_at", startedAt);
		try {
			db = helper.getWritableDatabase();
			id = db.insert(DailyColumns.TABLE_NAME, nullColumnHack, values);
		} catch (Exception e) {
		} finally {
			try {
				if(db != null) db.close(); db = null;
			} catch(Exception e) {
			}
		}
		return id;
	}

	public synchronized void update(Context context) {
		DatabaseHelper helper = DatabaseHelper.getInstance(context);
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
//			values.put("interface", interfase);
			values.put(DailyColumns.NETWORKTYPE, networktype);
			values.put(DailyColumns.RX, rx);
			values.put(DailyColumns.TX, tx);
			values.put(DailyColumns.DELTA_RX, deltaRx);
			values.put(DailyColumns.DELTA_TX, deltaTx);
			values.put("ended_at", endedAt);
			String where = "interface='"+ interfase+"' and day='"+day+"'";
			db.update(DailyColumns.TABLE_NAME, values, where, null);
		} catch (Exception e) {
			e.fillInStackTrace();
		} finally {
			try {
				if(db != null) db.close(); db = null;
			} catch(Exception e) {
			}
		}
	}

	public synchronized static DailyModel findPreviousDayTraffic(Context context, String interfase, String date) {
		DailyModel  dailyModel= new DailyModel();
		DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
		String[] columns = new String[] { "interface", "rx", "tx","delta_rx","delta_tx" };
		String selection = "interface = ? AND day <= ? ";
		String[] selectionArgs = new String[] { interfase, date};
		String groupBy = null;
		String having = null;
		String orderBy = "day DESC";
		String limit = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DailyColumns.TABLE_NAME);
		Cursor c = qb.query(db,columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		c.moveToFirst();
		if(c.getCount() > 0) {
			dailyModel.setInterfase(c.getString(0));
			dailyModel.setRx(c.getLong(1));
			dailyModel.setTx(c.getLong(2));
			dailyModel.setDeltaRx(c.getLong(2));
			dailyModel.setDeltaTx(c.getLong(3));;
		}
		c.close();
		db.close();
		return dailyModel;
	}

	public synchronized static Cursor findMonthTraffic(Context context, String interfase, String[] dates) {
//		ArrayList<DailyModel> list = new ArrayList<DailyModel>();
		DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
//		String[] columns = new String[] { "interface", "delta_rx as down", "delta_tx as up" };
		String selection = "interface = ? AND day between ? and ?";
		String[] selectionArgs = new String[] { interfase, dates[0], dates[1] };
		String groupBy = null;
		String having = null;
		String orderBy = "day DESC";
		String limit = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DailyColumns.TABLE_NAME);
		Cursor c = qb.query(db,columns, selection, selectionArgs, groupBy, having, orderBy, limit);
//		c.moveToFirst();
//		if(c.getCount() > 0) {
//			DailyModel model = new DailyModel();
//			model.setId(c.getInt(c.getColumnIndex(DailyColumns._ID)));
//			model.setInterfase(c.getString(c.getColumnIndex(DailyColumns.INTERFACE)));
//			model.setDay(c.getString(c.getColumnIndex(DailyColumns.DAY)));
//			model.setLastRx(c.getLong(c.getColumnIndex(DailyColumns.LAST_RX)));
//			model.setLastTx(c.getLong(c.getColumnIndex(DailyColumns.LAST_TX)));
//			model.setRx(c.getLong(c.getColumnIndex(DailyColumns.RX)));
//			model.setTx(c.getLong(c.getColumnIndex(DailyColumns.TX)));
//			model.setStartedAt(c.getString(c.getColumnIndex(DailyColumns.STARTED_AT)));
//			model.setEndedAt(c.getString(c.getColumnIndex(DailyColumns.ENDED_AT)));
//			list.add(model);
//			c.moveToNext();
//		}
//		c.close();
//		db.close();
		return c;
	}

	public synchronized static StatisticsInfo findMonthTraffic(Context context, String interfase, String[] dates,String statsType) {
		StatisticsInfo  statisticsInfo= new StatisticsInfo();
		DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
		String[] columns = new String[] { "interface", "sum(delta_rx) as down", "sum(delta_tx) as up" };
		String selection = "interface = ? AND day between ? and ?";
		String[] selectionArgs = new String[] { interfase, dates[0], dates[1] };
		String groupBy = "interface";
		String having = null;
		String orderBy = null;
		String limit = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DailyColumns.TABLE_NAME);
		Cursor c = qb.query(db,columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		c.moveToFirst();
		if(c.getCount() > 0) {
			statisticsInfo.interfase =c.getString(0);
			statisticsInfo.down=c.getLong(1);
			statisticsInfo.up=c.getLong(2);
			statisticsInfo.all =c.getLong(1)+c.getLong(2);
		}
		c.close();
		db.close();
		statisticsInfo.statstype =statsType;
		return statisticsInfo;
	}

	public synchronized static StatisticsInfo findTotalTraffic(Context context, String interfase, String date,String statsType) {
		StatisticsInfo  statisticsInfo= new StatisticsInfo();
		DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
		String[] columns = new String[] { "interface", "sum(delta_rx) as down", "sum(delta_tx) as up" };
		String selection = "interface = ? AND day >= ? ";
		String[] selectionArgs = new String[] { interfase, date};
		String groupBy = "interface";
		String having = null;
		String orderBy = null;
		String limit = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DailyColumns.TABLE_NAME);
		Cursor c = qb.query(db,columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		c.moveToFirst();
		if(c.getCount() > 0) {
			statisticsInfo.interfase =c.getString(0);
			statisticsInfo.down=c.getLong(1);
			statisticsInfo.up=c.getLong(2);
			statisticsInfo.all =c.getLong(1)+c.getLong(2);
		}
		c.close();
		db.close();
		statisticsInfo.statstype =statsType;
		return statisticsInfo;
	}

	@Override
	public boolean equals(Object o) {
		return this.id == ((DailyModel)o).getId();
	}
	@Override
	public int hashCode() {
		return (this.startedAt+this.endedAt).hashCode();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInterfase() {
		return interfase;
	}
	public void setInterfase(String interfase) {
		this.interfase = interfase;
	}
	public String getNetworktype() {
		return networktype;
	}
	public void setNetworktype(String networktype) {
		this.networktype = networktype;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public long getLastTx() {
		return lastTx;
	}
	public void setLastTx(long lastTx) {
		this.lastTx = lastTx;
	}
	public long getLastRx() {
		return lastRx;
	}
	public void setLastRx(long lastRx) {
		this.lastRx = lastRx;
	}
	public long getTx() {
		return tx;
	}
	public void setTx(long tx) {
		this.tx = tx;
	}
	public long getRx() {
		return rx;
	}
	public void setRx(long rx) {
		this.rx = rx;
	}
	public String getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}
	public String getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(String endedAt) {
		this.endedAt = endedAt;
	}
	public String[] getDates() {
		return dates;
	}
	public void setDates(String[] dates) {
		this.dates = dates;
	}

	public long getDeltaTx() {
		return deltaTx;
	}

	public void setDeltaTx(long deltaTx) {
		this.deltaTx = deltaTx;
	}

	public long getDeltaRx() {
		return deltaRx;
	}

	public void setDeltaRx(long deltaRx) {
		this.deltaRx = deltaRx;
	}

	public static class StatisticsInfo {
		public String interfase;
    	public String statstype;
       public long up;
       public long down;
       public long all;
    }
}
