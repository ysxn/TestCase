package com.android_exercise;

import com.android_exercise.calling_card.calling_card_contactors;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;


public class calling_card_Provider extends ContentProvider {

    private static final String TAG = "calling_card_Provider";

    private static final String DATABASE_NAME = "calling_card.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CONTACTORS_TABLE_NAME = "calling_card_contactors";

    private static HashMap<String, String> sCallingCardMap;

    private static final int CALLINGCARD_CONTACTORS = 1;
    private static final int CALLINGCARD_CONTACTOR_ID = 2;

    private static final UriMatcher sUriMatcher;
    
    private static final String CREATE_TABLE = "CREATE TABLE " + CONTACTORS_TABLE_NAME + " ("
                    + calling_card_contactors._ID + " INTEGER PRIMARY KEY,"
                    + calling_card_contactors.NAME + " TEXT,"
                    + calling_card_contactors.JOB + " TEXT,"
                    + calling_card_contactors.COMPANY + " TEXT,"
                    + calling_card_contactors.MYSELF + " TEXT,"
                    + calling_card_contactors.CREATED_DATE + " INTEGER,"
                    + calling_card_contactors.MODIFIED_DATE + " INTEGER"
                    + ");";
                    
    private static class DatabaseHelper extends SQLiteOpenHelper {

 	   DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS calling_card_contactors");
            onCreate(db);
        }
    
    }
    
    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
        case CALLINGCARD_CONTACTORS:
            qb.setTables(CONTACTORS_TABLE_NAME);
            qb.setProjectionMap(sCallingCardMap);
            break;

        case CALLINGCARD_CONTACTOR_ID:
            qb.setTables(CONTACTORS_TABLE_NAME);
            qb.setProjectionMap(sCallingCardMap);
            qb.appendWhere(calling_card_contactors._ID + "=" + uri.getPathSegments().get(1));
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = calling_card.calling_card_contactors.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }
    
        @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case CALLINGCARD_CONTACTORS:
            return calling_card_contactors.CONTENT_TYPE;

        case CALLINGCARD_CONTACTOR_ID:
            return calling_card_contactors.CONTENT_ITEM_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != CALLINGCARD_CONTACTORS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis());

        // Make sure that the fields are all set
        if (values.containsKey(calling_card.calling_card_contactors.CREATED_DATE) == false) {
            values.put(calling_card.calling_card_contactors.CREATED_DATE, now);
        }

        if (values.containsKey(calling_card.calling_card_contactors.MODIFIED_DATE) == false) {
            values.put(calling_card.calling_card_contactors.MODIFIED_DATE, now);
        }

        if (values.containsKey(calling_card.calling_card_contactors.NAME) == false) {
            Resources r = Resources.getSystem();
            values.put(calling_card.calling_card_contactors.NAME, r.getString(android.R.string.unknownName));
        }

        if (values.containsKey(calling_card.calling_card_contactors.JOB) == false) {
            values.put(calling_card.calling_card_contactors.JOB, "");
        }

		if (values.containsKey(calling_card.calling_card_contactors.COMPANY) == false) {
            values.put(calling_card.calling_card_contactors.COMPANY, "");
        }
        
        if (values.containsKey(calling_card.calling_card_contactors.MYSELF) == false) {
            values.put(calling_card.calling_card_contactors.MYSELF, "");
        }
        
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(CONTACTORS_TABLE_NAME, calling_card_contactors.NAME, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(calling_card.calling_card_contactors.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case CALLINGCARD_CONTACTORS:
            count = db.delete(CONTACTORS_TABLE_NAME, where, whereArgs);
            break;

        case CALLINGCARD_CONTACTOR_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.delete(CONTACTORS_TABLE_NAME, calling_card_contactors._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case CALLINGCARD_CONTACTORS:
            count = db.update(CONTACTORS_TABLE_NAME, values, where, whereArgs);
            break;

        case CALLINGCARD_CONTACTOR_ID:
            String noteId = uri.getPathSegments().get(1);
            count = db.update(CONTACTORS_TABLE_NAME, values, calling_card_contactors._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(calling_card.AUTHORITY, "calling_card_contactors", CALLINGCARD_CONTACTORS);
        sUriMatcher.addURI(calling_card.AUTHORITY, "calling_card_contactors/#", CALLINGCARD_CONTACTOR_ID);

        sCallingCardMap = new HashMap<String, String>();
        sCallingCardMap.put(calling_card_contactors._ID, calling_card_contactors._ID);
        sCallingCardMap.put(calling_card_contactors._COUNT, calling_card_contactors._COUNT);
        sCallingCardMap.put(calling_card_contactors.NAME, calling_card_contactors.NAME);
        sCallingCardMap.put(calling_card_contactors.JOB, calling_card_contactors.JOB);
        sCallingCardMap.put(calling_card_contactors.COMPANY, calling_card_contactors.COMPANY);
        sCallingCardMap.put(calling_card_contactors.MYSELF, calling_card_contactors.MYSELF);
        sCallingCardMap.put(calling_card_contactors.CREATED_DATE, calling_card_contactors.CREATED_DATE);
        sCallingCardMap.put(calling_card_contactors.MODIFIED_DATE, calling_card_contactors.MODIFIED_DATE);
    }
}