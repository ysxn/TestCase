package com.android.trafficmeter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 *	A helper class to manage database.
 * @author archermind
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME ="trafficmeter.db";
	private static final int DATABASE_VERSION = 2;
	private static DatabaseHelper databaseHelper;

	public static  DatabaseHelper getInstance(Context context) {
		if(databaseHelper == null) {
			databaseHelper = new DatabaseHelper(context);
		}
		return databaseHelper;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlDaily = "CREATE TABLE " + DailyColumns.TABLE_NAME + " ("
						+ DailyColumns._ID + " INTEGER PRIMARY KEY,"
						+ DailyColumns.INTERFACE + " TEXT,"
						+ DailyColumns.NETWORKTYPE + " TEXT,"
						+ DailyColumns.DAY + " DATE,"
						+ DailyColumns.LAST_RX + " LONG,"
						+ DailyColumns.LAST_TX + " LONG,"
						+ DailyColumns.RX + " LONG,"
						+ DailyColumns.TX + " LONG,"
						+ DailyColumns.DELTA_RX + " LONG,"
						+ DailyColumns.DELTA_TX + " LONG,"
						+ DailyColumns.STARTED_AT + " DATETIME,"
						+ DailyColumns.ENDED_AT + " DATETIME)";
//		String sqlCounter = "CREATE TABLE " + CounterColumns.TABLE_NAME + "("
//						+ CounterColumns._ID + " INTEGER PRIMARY KEY,"
//						+ CounterColumns.PNAME + " TEXT,"
//						+ CounterColumns.LAST_RX + " LONG,"
//						+ CounterColumns.LAST_TX + " LONG,"
//						+ CounterColumns.LAST_UPDATE + " DATETIME)";
		db.execSQL(sqlDaily);
//		db.execSQL(sqlCounter);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(DATABASE_VERSION ==1) {
			db.execSQL("DROP TABLE IF EXISTS " + DailyColumns.TABLE_NAME);
//			db.execSQL("DROP TABLE IF EXISTS " + CounterColumns.TABLE_NAME);
			onCreate(db);
		}
	}

//	public static final class CounterColumns implements BaseColumns {
//		public static final String TABLE_NAME = "counter";
//		public static final String PNAME = "pname";
//		public static final String LAST_RX = "last_rx";
//		public static final String LAST_TX = "last_tx";
//		public static final String LAST_UPDATE = "last_update";
//	}

	public static final class DailyColumns implements BaseColumns {
		public static final String TABLE_NAME = "daily";
		public static final String INTERFACE = "interface";
		public static final String NETWORKTYPE = "networktype";
		public static final String DAY = "day";
		public static final String LAST_RX = "last_rx";
		public static final String LAST_TX = "last_tx";
		public static final String RX = "rx";
		public static final String TX = "tx";
		public static final String DELTA_RX = "delta_rx";
		public static final String DELTA_TX = "delta_tx";
		public static final String STARTED_AT = "started_at";
		public static final String ENDED_AT = "ended_at";
	}

}
