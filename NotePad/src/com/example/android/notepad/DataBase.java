
package com.example.android.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {

    private DbHelper mDbHelper;

    private static final String TABLE = "user";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_EMAIL = "email";
    
    private static final String COLUMN_PASSWORD = "password";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ID + " INTEGER," +
            COLUMN_EMAIL+ " STRING," +
            COLUMN_PASSWORD+ " STRING" +
            ");";

    public DataBase(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public boolean remove(int id) {
        SQLiteDatabase db = null;
        try {
            db = mDbHelper.getWritableDatabase();
            int rowsRemoved = db.delete(TABLE, COLUMN_ID + "=?", new String[] {
                Integer.toString(id)
            });

            return rowsRemoved > 0;
        } finally {
            if (db != null)
                db.close();
        }

    }

    public void addId(int id, String email, String password) {
        SQLiteDatabase db = null;
        try {
            db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, id);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);

            int updated = db.update(TABLE, values, COLUMN_ID + "=?", new String[] {
                Integer.toString(id)
            });

            long status = 0;
            if (updated == 0)
                status = db.insert(TABLE, null, values);
        } finally {
            if (db != null)
                db.close();
        }
    }

    public boolean isEnrolled(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mDbHelper.getReadableDatabase();

            cursor = db.query(TABLE, new String[] {
                COLUMN_ID
            }, COLUMN_ID + "=?", new String[] {
                Integer.toString(id)
            }, null, null, null);

            if (cursor == null)
                return false;

            if (!cursor.moveToFirst())
                return false;

            return true;

        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public String getEmail(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mDbHelper.getReadableDatabase();

            cursor = db.query(TABLE, new String[] {
                    COLUMN_ID, COLUMN_EMAIL
            }, COLUMN_ID + "=?", new String[] {
                Integer.toString(id)
            }, null, null, null);

            if (cursor == null)
                return null;

            if (!cursor.moveToFirst())
                return null;

            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));

            return email;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }
    
    public String getPassword(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mDbHelper.getReadableDatabase();

            cursor = db.query(TABLE, new String[] {
                    COLUMN_ID, COLUMN_PASSWORD
            }, COLUMN_ID + "=?", new String[] {
                Integer.toString(id)
            }, null, null, null);

            if (cursor == null)
                return null;

            if (!cursor.moveToFirst())
                return null;

            String pass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));

            return pass;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public int[] getIds() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mDbHelper.getReadableDatabase();

            cursor = db.query(TABLE, new String[] {
                COLUMN_ID
            }, null, null, null, null, null);

            if (cursor == null)
                return null;

            if (!cursor.moveToFirst())
                return null;

            final int length = cursor.getCount();
            if (length == 0)
                return null;

            int[] result = new int[length];
            int i = 0;
            do {
                result[i++] = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            } while (cursor.moveToNext());

            return result;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    private class DbHelper extends SQLiteOpenHelper {
        private static final int VERSION = 1;

        public DbHelper(Context context) {
            super(context, TABLE, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }
}
