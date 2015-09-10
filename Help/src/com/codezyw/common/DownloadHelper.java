
package com.codezyw.common;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class DownloadHelper extends SQLiteOpenHelper {
    private static volatile DownloadHelper sDownloadHelper = null;

    private static final int VERSION = 1;

    private static final String DB_NAME = "download_record";

    // ++++++++++++++++++++<key value>设置表 开始+++++++++++++++++++
    private static final String SETTING_TABLE = "setting";

    public static final String COLUMN_SETTING_KEY = "setting_key";

    public static final String COLUMN_SETTING_VALUE = "setting_value";

    private static final String SQL_CREATE_SETTING = "CREATE TABLE " + SETTING_TABLE + " (" + "_id integer primary key autoincrement," + COLUMN_SETTING_KEY
            + " text," + COLUMN_SETTING_VALUE + " text" + ");";
    // --------------------<key value>设置表 结束----------------------

    // ++++++++++++++++++++下载记录表 开始+++++++++++++++++++
    private static final String RECORD_TABLE = "record";
    public static final String COLUMN_RECORD_URL = "record_url";
    public static final String COLUMN_RECORD_SAVENAME = "record_savename";
    public static final String COLUMN_RECORD_DOWNLOADED_BYTE = "record_downloaded_byte";
    public static final String COLUMN_RECORD_DOWNLOAD_LENGTH = "record_download_length";
    public static final String COLUMN_RECORD_CHECK_CRC = "record_check_crc";

    private static final String SQL_CREATE_DOWNLOAD_RECORD = "CREATE TABLE " + RECORD_TABLE + " (" + "_id integer primary key autoincrement,"
            + COLUMN_RECORD_URL + " text,"
            + COLUMN_RECORD_SAVENAME + " text"
            + COLUMN_RECORD_DOWNLOADED_BYTE + " integer"
            + COLUMN_RECORD_DOWNLOAD_LENGTH + " integer"
            + COLUMN_RECORD_CHECK_CRC + " text"
            + ");";

    // --------------------下载记录表 结束----------------------

    public static DownloadHelper getInstance(Context context) {
        if (sDownloadHelper == null) {
            synchronized (DbHelper.class) {
                if (sDownloadHelper == null) {
                    sDownloadHelper = new DownloadHelper(context);
                }
            }
        }
        return sDownloadHelper;
    }

    private DownloadHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SETTING);
        db.execSQL(SQL_CREATE_DOWNLOAD_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    /*************************************************************
     * 下载记录表操作
     */

    public synchronized void refreshRecord(Record r) {
        if (r == null) {
            return;
        }
        SQLiteDatabase db = null;
        try {
            db = sDownloadHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_RECORD_URL, r.url);
            cv.put(COLUMN_RECORD_SAVENAME, r.savename);
            cv.put(COLUMN_RECORD_DOWNLOADED_BYTE, r.downloaded);
            cv.put(COLUMN_RECORD_DOWNLOAD_LENGTH, r.length);
            cv.put(COLUMN_RECORD_CHECK_CRC, r.crc);

            int update = db.update(RECORD_TABLE, cv, COLUMN_RECORD_URL + "=?", new String[] {
                    r.url
            });
            if (update == 0) {
                db.insert(SETTING_TABLE, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public synchronized ArrayList<Record> queryRecord(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
            String orderBy, String limit) {
        ArrayList<Record> record = new ArrayList<Record>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = sDownloadHelper.getWritableDatabase();
            db.beginTransaction();
            cursor = db.query(RECORD_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            if (cursor == null || !cursor.moveToFirst()) {
                return record;
            }
            do {
                Record r = new Record();
                r.url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECORD_URL));
                r.savename = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECORD_SAVENAME));
                r.downloaded = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECORD_DOWNLOADED_BYTE));
                r.length = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECORD_DOWNLOAD_LENGTH));
                r.crc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECORD_CHECK_CRC));
                record.add(r);
            } while (cursor.moveToNext());
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return record;
    }

    public synchronized int deleteRecord(String whereClause, String[] whereArgs) {
        int affected = 0;
        SQLiteDatabase db = null;
        try {
            db = sDownloadHelper.getWritableDatabase();
            db.beginTransaction();
            affected = db.delete(RECORD_TABLE, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return affected;
    }

    /*************************************************************
     * 设置表操作
     */

    public void putInt(String key, int value) {
        putString(key, Integer.toString(value));
    }

    public int getInt(String key, int value) {
        String r = getString(key, Integer.toString(value));
        try {
            value = Integer.parseInt(r);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void putLong(String key, long value) {
        putString(key, Long.toString(value));
    }

    public long getLong(String key, long value) {
        String r = getString(key, Long.toString(value));
        try {
            value = Long.parseLong(r);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void putShort(String key, short value) {
        putString(key, Long.toString(value));
    }

    public short getShort(String key, short value) {
        String r = getString(key, Short.toString(value));
        try {
            value = Short.parseShort(r);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void putDouble(String key, double value) {
        putString(key, Double.toString(value));
    }

    public double getDouble(String key, double value) {
        String r = getString(key, Double.toString(value));
        try {
            value = Double.parseDouble(r);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void putFloat(String key, float value) {
        putString(key, Float.toString(value));
    }

    public float getFloat(String key, float value) {
        String r = getString(key, Float.toString(value));
        try {
            value = Float.parseFloat(r);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void putBoolean(String key, boolean value) {
        putString(key, Boolean.toString(value));
    }

    public boolean getBoolean(String key, boolean value) {
        String r = getString(key, Boolean.toString(value));
        try {
            value = Boolean.parseBoolean(r);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public synchronized void putString(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        SQLiteDatabase db = null;
        try {
            db = sDownloadHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_SETTING_KEY, key);
            cv.put(COLUMN_SETTING_VALUE, value);
            int update = db.update(SETTING_TABLE, cv, COLUMN_SETTING_KEY + "=?", new String[] {
                    key
            });
            if (update == 0) {
                db.insert(SETTING_TABLE, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public synchronized String getString(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return value;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = sDownloadHelper.getWritableDatabase();
            db.beginTransaction();
            cursor = db.query(SETTING_TABLE, null, COLUMN_SETTING_KEY + "=?", new String[] {
                    key
            }, null, null, null, null);
            if (cursor == null || !cursor.moveToFirst()) {
                return value;
            }
            value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SETTING_VALUE));
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return value;
    }

    public synchronized int delete(String key) {
        int affected = 0;
        if (TextUtils.isEmpty(key)) {
            return 0;
        }
        SQLiteDatabase db = null;
        try {
            db = sDownloadHelper.getWritableDatabase();
            db.beginTransaction();
            affected = db.delete(SETTING_TABLE, COLUMN_SETTING_KEY + "=?", new String[] {
                    key
            });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return affected;
    }

    public static class Record {
        public String url = null;
        public String savename = null;
        public int downloaded = 0;
        public int length = 0;
        public String crc = null;
    }
}
