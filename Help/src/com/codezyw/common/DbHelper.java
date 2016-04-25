
package com.codezyw.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public class DbHelper extends SQLiteOpenHelper {
    private static volatile DbHelper sDbHelper = null;

    private static final int VERSION = 1;

    private static final String DB_NAME = "database";

    private static final String SETTING_TABLE = "setting";

    private static final String COLUMN_SETTING_KEY = "setting_key";

    private static final String COLUMN_SETTING_VALUE = "setting_value";

    private static final String SQL_CREATE_SETTING = "CREATE TABLE " + SETTING_TABLE + " (" + "_id integer primary key autoincrement," + COLUMN_SETTING_KEY
            + " text," + COLUMN_SETTING_VALUE + " text" + ");";

    public static DbHelper getInstance(Context context) {
        if (sDbHelper == null) {
            synchronized (DbHelper.class) {
                if (sDbHelper == null) {
                    sDbHelper = new DbHelper(context.getApplicationContext());
                }
            }
        }
        return sDbHelper;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SETTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

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
            db = sDbHelper.getWritableDatabase();
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
            db = sDbHelper.getWritableDatabase();
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
            db = sDbHelper.getWritableDatabase();
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
}
