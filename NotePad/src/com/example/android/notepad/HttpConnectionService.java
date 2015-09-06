
package com.example.android.notepad;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.codezyw.common.DbHelper;
import com.codezyw.common.HttpHelper;
import com.codezyw.common.NetManager;
import com.codezyw.common.TimeHelper;
import com.codezyw.common.UIHelper;

import android.app.Notification;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class HttpConnectionService extends Service implements Handler.Callback {

    private static final String TAG = "zyw";

    public static final boolean DEBUG = true;

    private static final int MSG_STOP_SERVICE_BY_TIMEOUT = 110;

    private boolean isServiceRunning = false;

    private Handler mServiceMainHandler;

    // Job queen design

    public static final String NOTE_ID = "note_id";

    public static final String EMAIL = "email";

    public static final String PASSWORD = "pass";

    public static final String TITLE = "note_title";

    public static final String CONTENT = "note_content";

    public static final String CMD_KEY = "cmd";

    public static final String FETCH_CMD_DELETE = "delete_note";

    public static final String FETCH_CMD_UPDATE = "update_note";

    public static final String FETCH_CMD_INSERT = "insert_note";

    public static final String FETCH_CMD_FETCH = "fetch_note";

    public static final int MSG_FETCH = 0x1000;

    public static final int MSG_DELETE = 0x1001;

    public static final int MSG_UPDATE = 0x1002;

    private final static Object mHttpLock = new Object();

    private HandlerThread mHandlerThread;

    private Handler mWorkHandler;

    private Context mContext = HttpConnectionService.this;
    
    private NetManager mNetManager = new NetManager();

    public static FetchBean sFetchBean = null;

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
        mServiceMainHandler = new Handler(this);
        mHandlerThread = new HandlerThread("handler_thread");
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_FETCH: {
                        Intent intent = (Intent) msg.obj;
                        if (intent == null) {
                            UIHelper.showToast(mContext, "MSG_FETCH intent is null error!");
                            return;
                        }
                        UIHelper.showProgressDialog(mContext, "正在下载服务器数据......", "", ProgressDialog.STYLE_SPINNER, true, 100);
                        final String httpUrl = "http://php.codezyw.com/fetch_note_android.php";
                        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                        postParams.add(new BasicNameValuePair(EMAIL, DbHelper.getInstance(mContext)
                                .getString(EMAIL, "")));
                        postParams.add(new BasicNameValuePair(PASSWORD, DbHelper.getInstance(
                                mContext).getString(PASSWORD, "")));
                        synchronized (mHttpLock) {
                            parseServerResult(HttpHelper.httpPost(httpUrl, postParams));
                        }
                        break;
                    }
                    case MSG_UPDATE: {
                        Bundle bundle = (Bundle) msg.obj;
                        if (bundle != null) {
                            UIHelper.showProgressDialog(mContext, "正在更新服务器数据......", "", ProgressDialog.STYLE_SPINNER, true, 100);
                            int id = bundle.getInt(NOTE_ID);
                            String title = bundle.getString(TITLE);
                            String content = bundle.getString(CONTENT);
                            final String httpUrl = "http://php.codezyw.com/update_note_android.php";
                            final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                            postParams.add(new BasicNameValuePair(EMAIL, DbHelper.getInstance(
                                    mContext).getString(EMAIL, "")));
                            postParams.add(new BasicNameValuePair(PASSWORD, DbHelper.getInstance(
                                    mContext).getString(PASSWORD, "")));
                            postParams.add(new BasicNameValuePair(TITLE, title));
                            postParams.add(new BasicNameValuePair(CONTENT, content));
                            postParams.add(new BasicNameValuePair(NOTE_ID, Integer.toString(id)));
                            synchronized (mHttpLock) {
                                parseServerResult(HttpHelper.httpPost(httpUrl, postParams));
                            }
                        }
                        break;
                    }
                    case MSG_DELETE: {
                        Bundle bundle = (Bundle) msg.obj;
                        if (bundle != null) {
                            int id = bundle.getInt(NOTE_ID);
                            final String phpString = "http://php.codezyw.com/delete_note_android.php";
                            final List<NameValuePair> phpParams = new ArrayList<NameValuePair>();
                            phpParams.add(new BasicNameValuePair(EMAIL, DbHelper.getInstance(
                                    mContext).getString(EMAIL, "")));
                            phpParams.add(new BasicNameValuePair(PASSWORD, DbHelper.getInstance(
                                    mContext).getString(PASSWORD, "")));
                            phpParams.add(new BasicNameValuePair(NOTE_ID, Integer.toString(id)));
                            synchronized (mHttpLock) {
                                parseServerResult(HttpHelper.httpPost(phpString, phpParams));
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
        getContentResolver().registerContentObserver(NotePad.Notes.CONTENT_URI, true,
                mContentObserver);
        Notification notification = new Notification();
        this.startForeground(0x200, notification);
        mNetManager.onCreate(mContext);
    }

    private void parseServerResult(String result_data) {
        Log.i(TAG, "after http post result_data = " + result_data);
        if (!TextUtils.isEmpty(result_data)) {
            sFetchBean = ParseJSONHelper.parseFetchBeanByJSONSingle(result_data);
            if (sFetchBean != null) {
                Log.i(TAG, "start dump sFetchBean = " + sFetchBean.dump()
                        + " \n end dump sFetchBean");
                if (sFetchBean.getLogin_result()) {
                    if (FETCH_CMD_FETCH.equals(sFetchBean.getFetch_cmd())) {
                        if (sFetchBean.getFetch_result()) {
                            List<NoteBean> list = sFetchBean.getResult_data();
                            UIHelper.showToast(mContext, "登录成功,下载服务器数据成功!");
                            if (list != null) {
                                // 避免错误的无限同步循环
                                getContentResolver().unregisterContentObserver(mContentObserver);
                                syncServerToLocal(list);
                                getContentResolver().registerContentObserver(
                                        NotePad.Notes.CONTENT_URI, true, mContentObserver);
                                dumpLocalDataBase();
                                UIHelper.dismissProgressDialog();
                            }
                        } else {
                            UIHelper.dismissProgressDialog();
                            UIHelper.showDialog(mContext, "登录成功,但是下载服务器数据失败!",
                                    sFetchBean.getError_info());
                        }
                    } else if (FETCH_CMD_INSERT.equals(sFetchBean.getFetch_cmd())) {
                        UIHelper.dismissProgressDialog();
                        if (sFetchBean.getFetch_result()) {
                            UIHelper.showToast(mContext, "登录成功,插入服务器数据成功!");
                        } else {
                            UIHelper.showDialog(mContext, "登录成功,但是插入服务器数据失败!",
                                    sFetchBean.getError_info());
                        }
                    } else if (FETCH_CMD_UPDATE.equals(sFetchBean.getFetch_cmd())) {
                        UIHelper.dismissProgressDialog();
                        if (sFetchBean.getFetch_result()) {
                            UIHelper.showToast(mContext, "登录成功,更新服务器数据成功!");
                        } else {
                            UIHelper.showDialog(mContext, "登录成功,但是更新服务器数据失败!",
                                    sFetchBean.getError_info());
                        }
                    } else if (FETCH_CMD_DELETE.equals(sFetchBean.getFetch_cmd())) {
                        UIHelper.dismissProgressDialog();
                        if (sFetchBean.getFetch_result()) {
                            UIHelper.showToast(mContext, "登录成功,删除服务器数据成功!");
                        } else {
                            UIHelper.showDialog(mContext, "登录成功,但是删除服务器数据失败!",
                                    sFetchBean.getError_info());
                        }
                    }
                } else {
                    UIHelper.dismissProgressDialog();
                    UIHelper.showDialog(mContext, "登录失败!", result_data);
                }
            } else {
                UIHelper.dismissProgressDialog();
                UIHelper.showDialog(mContext, "JSON错误!", result_data);
            }
        } else {
            UIHelper.dismissProgressDialog();
            UIHelper.showDialog(mContext, "网络错误!", result_data);
        }
        UIHelper.dismissProgressDialog();
    }

    private void dumpLocalDataBase() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(NotePad.Notes.CONTENT_URI, READ_NOTE_PROJECTION,
                    null, null, NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                    String title = cursor.getString(cursor
                            .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                    String content = cursor.getString(cursor
                            .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                    long create = cursor.getLong(cursor
                            .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
                    long modify = cursor.getLong(cursor
                            .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
                    Log.i(TAG, ">>>>dumpLocalDataBase = id=" + id + "; title=" + title
                            + "; content=" + content + "; create=" + TimeHelper.getTime(create)
                            + "; modify=" + TimeHelper.getTime(modify));
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
            cursor = getContentResolver().query(uri, READ_NOTE_PROJECTION, null, null,
                    NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                    String title = cursor.getString(cursor
                            .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                    String content = cursor.getString(cursor
                            .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                    Log.i(TAG, ">>>>syncLocalToServer id=" + id + "; title=" + title + "; content="
                            + content);
                    Bundle bundle = new Bundle();
                    bundle.putInt(NOTE_ID, id);
                    bundle.putString(TITLE, title);
                    bundle.putString(CONTENT, content);
                    mWorkHandler.obtainMessage(MSG_UPDATE, bundle).sendToTarget();
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

    private void syncServerToLocal(List<NoteBean> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (NoteBean n : list) {
            ContentValues values = new ContentValues();
            values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE,
                    TimeHelper.getTimeInLong(n.getCreate_date()));
            values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
                    TimeHelper.getTimeInLong(n.getModify_date()));
            values.put(NotePad.Notes.COLUMN_NAME_TITLE, n.getNote_title());
            values.put(NotePad.Notes.COLUMN_NAME_NOTE, n.getNote_content());
            values.put(NotePad.Notes._ID, n.getNote_id());
            Uri uri = getContentResolver().insert(NotePad.Notes.CONTENT_URI, values);
            Log.i(TAG, ">>>>syncServerToLocal uri = " + uri + "; note_id" + n.getNote_id());
            if (uri == null) {
                // insert failed, try update
                uri = ContentUris.withAppendedId(NotePad.Notes.CONTENT_ID_URI_BASE, n.getNote_id());
                if (checkServerModify(uri, n.getModify_date())) {
                    getContentResolver().update(uri, // The URI for the record
                                                     // to update.
                            values, // The map of column names and new values to
                                    // apply to them.
                            null, // No selection criteria are used, so no where
                                  // columns are necessary.
                            null // No where columns are used, so no where
                                 // arguments are necessary.
                            );
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
            cursor = getContentResolver().query(uri, READ_NOTE_PROJECTION, null, null,
                    NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                String title = cursor.getString(cursor
                        .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor
                        .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                long create = cursor.getLong(cursor
                        .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
                long modify = cursor.getLong(cursor
                        .getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
                Log.i(TAG,
                        ">>>>checkServerModify = id=" + id + "; title=" + title + "; content="
                                + content + "; create=" + TimeHelper.getTime(create) + "; modify="
                                + TimeHelper.getTime(modify) + "; serverTime="
                                + TimeHelper.getTime(serverTime));
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
        int cmd = intent.getIntExtra(CMD_KEY, 0);
        if (DEBUG)
            Log.v(TAG, "onStartCommand CMD=" + cmd + " flags=" + flags + " , startId=" + startId);
        if (MSG_FETCH == cmd) {
            Message msg = mWorkHandler.obtainMessage(MSG_FETCH, intent);
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
