
package com.example.android.notepad;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.codezyw.common.HttpDataContentBean;
import com.codezyw.common.HttpHelper;
import com.codezyw.common.JsonHelper;
import com.codezyw.common.NetManager;
import com.codezyw.common.TimeHelper;
import com.codezyw.common.UIHelper;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class HttpConnectionService extends Service implements Handler.Callback {

    private static final String TAG = "zyw";
    public static final boolean DEBUG = true;
    private static final int MSG_STOP_SERVICE_BY_TIMEOUT = 110;
    private final static Object mHttpLock = new Object();
    private HandlerThread mHandlerThread;
    private Handler mWorkHandler;
    private Context mContext = HttpConnectionService.this;
    private NetManager mNetManager = new NetManager();
    ContentObserver mContentObserver = null;

    private static final String[] READ_NOTE_PROJECTION = new String[] {
            NotePad.Notes._ID, // Projection position 0, the note's id
            NotePad.Notes.COLUMN_NAME_NOTE, // Projection position 1, the note's
                                            // content
            NotePad.Notes.COLUMN_NAME_TITLE, // Projection position 2, the
                                             // note's title
            NotePad.Notes.COLUMN_NAME_CREATE_DATE, // Projection position 3,
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, // Projection position
                                                         // 4,
    };

    @Override
    public void onCreate() {
        if (DEBUG)
            Log.v(TAG, "service onCreate");
        super.onCreate();
        new Handler(this);
        mHandlerThread = new HandlerThread("handler_thread");
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case JsonHelper.MSG_FETCH: {
                        Intent intent = (Intent) msg.obj;
                        if (intent == null) {
                            UIHelper.showToast(mContext, "JsonHelper.MSG_FETCH intent is null error!");
                            return;
                        }
                        UIHelper.showToast(mContext, "正在下载服务器数据......");
                        synchronized (mHttpLock) {
                            List<HttpDataContentBean> list = HttpHelper.fetchServer(mContext);
                            if (list != null) {
                                // 避免错误的无限同步循环
                                getContentResolver().unregisterContentObserver(mContentObserver);
                                syncServerToLocal(list);
                                getContentResolver().registerContentObserver(NotePad.Notes.CONTENT_URI, true, mContentObserver);
                                dumpLocalDataBase();
                            }
                        }
                        break;
                    }
                    case JsonHelper.MSG_UPDATE: {
                        Bundle bundle = (Bundle) msg.obj;
                        if (bundle != null) {
                            UIHelper.showToast(mContext, "正在更新服务器数据......");
                            synchronized (mHttpLock) {
                                HttpHelper.updateServer(mContext, bundle);
                            }
                        }
                        break;
                    }
                    case JsonHelper.MSG_DELETE: {
                        Bundle bundle = (Bundle) msg.obj;
                        if (bundle != null) {
                            synchronized (mHttpLock) {
                                HttpHelper.deleteServer(mContext, bundle);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };

        mContentObserver = new ContentObserver(mWorkHandler) {
            @Override
            public void onChange(boolean selfChange) {
                Log.i(TAG, ">>>>>onChange");
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.i(TAG, ">>>>>onChangeWithUri=" + uri);
                syncLocalToServer(uri);
            }
        };
        getContentResolver().registerContentObserver(NotePad.Notes.CONTENT_URI, true, mContentObserver);
        Notification notification = new Notification();
        this.startForeground(0x200, notification);
        mNetManager.onCreate(mContext);
    }

    private void dumpLocalDataBase() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(NotePad.Notes.CONTENT_URI, READ_NOTE_PROJECTION, null, null, NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                    long create = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
                    long modify = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
                    StringBuilder sb = new StringBuilder();
                    sb.append(">>>>>>>>>>>>dumpLocalDataBase start").append("\n");
                    sb.append("id:").append(id).append("\n");
                    sb.append("title:").append("\n").append(title).append("\n");
                    sb.append("content:").append("\n").append(content).append("\n");
                    sb.append("create time:").append("\n").append(TimeHelper.getTime(create)).append("\n");
                    sb.append("modify time:").append("\n").append(TimeHelper.getTime(modify)).append("\n");
                    sb.append(">>>>>>>>>>>>dumpLocalDataBase end");
                    Log.i(TAG, sb.toString());
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                cursor = null;
            }
        }
    }

    private void syncLocalToServer(Uri uri) {
        if (uri == null) {
            return;
        }
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, READ_NOTE_PROJECTION, null, null, NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                    StringBuilder sb = new StringBuilder();
                    sb.append(">>>>>>>>>>>>syncLocalToServer start").append("\n");
                    sb.append("id:").append(id).append("\n");
                    sb.append("title:").append("\n").append(title).append("\n");
                    sb.append("content:").append("\n").append(content).append("\n");
                    sb.append(">>>>>>>>>>>>syncLocalToServer end");
                    Log.i(TAG, sb.toString());
                    Bundle bundle = new Bundle();
                    bundle.putInt(JsonHelper.NOTE_ID, id);
                    bundle.putString(JsonHelper.TITLE, title);
                    bundle.putString(JsonHelper.CONTENT, content);
                    mWorkHandler.obtainMessage(JsonHelper.MSG_UPDATE, bundle).sendToTarget();
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                cursor = null;
            }
        }
    }

    private void syncServerToLocal(List<HttpDataContentBean> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (HttpDataContentBean n : list) {
            ContentValues values = new ContentValues();
            values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, TimeHelper.getTimeInLong(n.getCreate_date()));
            values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, TimeHelper.getTimeInLong(n.getModify_date()));
            values.put(NotePad.Notes.COLUMN_NAME_TITLE, n.getNote_title());
            values.put(NotePad.Notes.COLUMN_NAME_NOTE, n.getNote_content());
            values.put(NotePad.Notes._ID, n.getNote_id());
            Uri uri = getContentResolver().insert(NotePad.Notes.CONTENT_URI, values);
            Log.i(TAG, "syncServerToLocal uri = " + uri + "; note_id" + n.getNote_id());
            if (uri == null) {
                // insert failed, try update
                uri = ContentUris.withAppendedId(NotePad.Notes.CONTENT_ID_URI_BASE, n.getNote_id());
                if (checkServerModify(uri, n.getModify_date())) {
                    /**
                     * <pre>
                     * uri, The URI for the record to update.
                     * values, The map of column names and new values to apply to them.
                     * null,  No selection criteria are used, so no where columns are necessary.
                     * null  No where columns are used, so no where arguments are necessary.
                     * </pre>
                     */
                    getContentResolver().update(uri, values, null, null);
                }
            }
        }
    }

    private boolean checkServerModify(Uri uri, String serverModifyTime) {
        boolean override = true;
        if (uri == null || serverModifyTime == null) {
            return override;
        }
        Cursor cursor = null;
        long serverTime = TimeHelper.getTimeInLong(serverModifyTime);
        try {
            cursor = getContentResolver().query(uri, READ_NOTE_PROJECTION, null, null, NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                long create = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
                long modify = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
                StringBuilder sb = new StringBuilder();
                sb.append(">>>>>>>>>>>>checkServerModify start").append("\n");
                sb.append("id:").append(id).append("\n");
                sb.append("title:").append("\n").append(title).append("\n");
                sb.append("content:").append("\n").append(content).append("\n");
                sb.append("create time:").append("\n").append(TimeHelper.getTime(create)).append("\n");
                sb.append("modify time:").append("\n").append(TimeHelper.getTime(modify)).append("\n");
                sb.append("server time:").append("\n").append(TimeHelper.getTime(serverTime)).append("\n");
                sb.append(">>>>>>>>>>>>checkServerModify end");
                Log.i(TAG, sb.toString());
                if (serverTime < modify) {
                    override = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                cursor = null;
            }
        }
        return override;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int cmd = intent.getIntExtra(JsonHelper.CMD_KEY, 0);
        if (DEBUG)
            Log.v(TAG, "onStartCommand CMD=" + cmd + " flags=" + flags + " , startId=" + startId);
        if (JsonHelper.MSG_FETCH == cmd) {
            Message msg = mWorkHandler.obtainMessage(JsonHelper.MSG_FETCH, intent);
            mWorkHandler.sendMessage(msg);
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        if (DEBUG)
            Log.v(TAG, "service onDestroy");
        mHandlerThread.quit();
        getContentResolver().unregisterContentObserver(mContentObserver);
        mNetManager.onDestroy(mContext);
        this.stopForeground(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_STOP_SERVICE_BY_TIMEOUT:
                Log.i(TAG, "MSG_STOP_SERVICE_BY_TIMEOUT.");
                stopSelf();
                break;
            default:
                return false;
        }
        return true;
    }
}
