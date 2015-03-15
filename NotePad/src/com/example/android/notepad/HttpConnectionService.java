
package com.example.android.notepad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class HttpConnectionService extends Service implements Handler.Callback {

    private static final String TAG = "zyw";
    public static final boolean DEBUG = true;
    
    private static final int MSG_STOP_SERVICE_BY_TIMEOUT = 110;
    private static final long DELAY_STOP_SERVICE_BY_TIMEOUT = 10000L;//10 s

    private boolean isServiceRunning = false;
    private Handler mServiceMainHandler;
    //Job queen design
    
    private HttpHelper mHttpHelper;
    public static final String NOTE_ID = "note_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "pass";
    public static final String TITLE = "note_title";
    public static final String CONTENT = "note_content";
    public static final String CMD = "cmd";
    public static final String FETCH = "fetch";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    //public static final String LOGIN = "login";
    
    public static final String FETCH_CMD_DELETE = "delete_note";
    public static final String FETCH_CMD_UPDATE = "update_note";
    public static final String FETCH_CMD_INSERT = "insert_note";
    public static final String FETCH_CMD_FETCH = "fetch_note";

    public static final int MSG_HTTP_POST_RESULT = HttpHelper.MSG_SHOW_RESULT;
    public static final String RESULT_DATA = HttpHelper.RESULT_DATA;
    public static final String IS_ERROR = HttpHelper.IS_ERROR;
    
    public static final int MSG_FETCH = 0x1000;
    public static final int MSG_DELETE = 0x1001;
    public static final int MSG_UPDATE = 0x1002;
    public static final int MSG_LOGIN = 0x1003;
    
    private final static Object mHttpLock = new Object();
    
    private HandlerThread mHandlerThread;
    private Handler mWorkHandler;
    private Context mContext = HttpConnectionService.this;
    private ProgressDialog mProgressDialog = null;
    
    public static FetchBean sFetchBean = null;
    public static String sEmail = null;
    public static String sPass = null;
    private DataBase mDataBase = new DataBase(this);
    
    ContentObserver mContentObserver = null;
    private static final String[] READ_NOTE_PROJECTION = new String[] {
        NotePad.Notes._ID,               // Projection position 0, the note's id
        NotePad.Notes.COLUMN_NAME_NOTE,  // Projection position 1, the note's content
        NotePad.Notes.COLUMN_NAME_TITLE, // Projection position 2, the note's title
        NotePad.Notes.COLUMN_NAME_CREATE_DATE, // Projection position 3,
        NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, // Projection position 4,
    };
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置格式 
    
    private boolean mIsNetConnected = false;
    private boolean mIsWifi = false;
    private BroadcastReceiver mNetStatusReceiver = new BroadcastReceiver() {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            checkNetStatus();
        }
    };
    
    @Override
    public void onCreate() {
        if (DEBUG)
            Log.v(TAG, "service onCreate");
        super.onCreate();
        mServiceMainHandler = new Handler(this);
        mHttpHelper = new HttpHelper();
        mHandlerThread = new HandlerThread("handler_thread");
        mHandlerThread.start();
        sEmail = mDataBase.getEmail(0);
        sPass = mDataBase.getPassword(0);
        mWorkHandler = new Handler(mHandlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_LOGIN: 
                    case MSG_FETCH: {
                        showProgressDialog("正在下载服务器数据......","");
                        Intent intent = (Intent) msg.obj;
                        if (intent == null) {
                            Toast.makeText(mContext, "MSG_FETCH intent is null error!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        final String username = intent.getStringExtra(EMAIL);
                        final String pass = intent.getStringExtra(PASSWORD);
                        
                        final String httpUrl = "http://php.codezyw.com/fetch_note_android.php";
                        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                        postParams.add(new BasicNameValuePair(EMAIL, username));
                        postParams.add(new BasicNameValuePair(PASSWORD, pass));
                        synchronized (mHttpLock) {
                            mHttpHelper.methodHttpPost(httpUrl, postParams, username, pass);
                        }
                        break;
                    }
                    case MSG_UPDATE: {
                        Bundle bundle = (Bundle) msg.obj;
                        if (bundle != null) {
                            showProgressDialog("正在更新服务器数据......","");
                            int id = bundle.getInt(NOTE_ID);
                            String title = bundle.getString(TITLE);
                            String content = bundle.getString(CONTENT);
                            final String httpUrl = "http://php.codezyw.com/update_note_android.php";
                            final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                            postParams.add(new BasicNameValuePair(EMAIL, sEmail));
                            postParams.add(new BasicNameValuePair(PASSWORD, sPass));
                            postParams.add(new BasicNameValuePair(TITLE, title));
                            postParams.add(new BasicNameValuePair(CONTENT, content));
                            postParams.add(new BasicNameValuePair(NOTE_ID, Integer.toString(id)));
                            synchronized (mHttpLock) {
                                mHttpHelper.methodHttpPost(httpUrl, postParams, sEmail, sPass);
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
                            phpParams.add(new BasicNameValuePair(EMAIL, sEmail));
                            phpParams.add(new BasicNameValuePair(PASSWORD, sPass));
                            phpParams.add(new BasicNameValuePair(NOTE_ID, Integer.toString(id)));
                            synchronized (mHttpLock) {
                                mHttpHelper.methodHttpPost(phpString, phpParams, sEmail, sPass);
                            }
                        }
                        break;
                    }
                    case MSG_HTTP_POST_RESULT: {
                        Bundle bundle = (Bundle) msg.obj;
                        if (bundle != null) {
                            boolean error = bundle.getBoolean(IS_ERROR, true);
                            String result_data = bundle.getString(RESULT_DATA, "");
                            Log.i(TAG, "after http post result_data = "+result_data);
                            if (!error) {
                                sFetchBean = ParseJSONHelper.parseFetchBeanByJSONSingle(result_data);
                                if (sFetchBean != null) {
                                    Log.i(TAG, "sFetchBean = "+sFetchBean.dump());
                                    if (sFetchBean.getLogin_result()) {
                                        String email = bundle.getString(EMAIL, "");
                                        String password = bundle.getString(PASSWORD, "");
                                        mDataBase.addId(0, email, password);
                                        SharedPreferenceUtils.setPrefString(mContext, SharedPreferenceUtils.KEY_USERNAME, email);
                                        SharedPreferenceUtils.setPrefString(mContext, SharedPreferenceUtils.KEY_PASSWORD, password);
                                        if (sEmail == null || sPass == null || sEmail.isEmpty() || sPass.isEmpty()) {
                                            Intent i = new Intent(mContext, NotesList.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                    |Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            mContext.startActivity(i);
                                        }
                                        sEmail = email;
                                        sPass = password;
                                        
                                        if (FETCH_CMD_FETCH.equals(sFetchBean.getFetch_cmd())) {
                                            if (sFetchBean.getFetch_result()) {
                                                List<NoteBean> list = sFetchBean.getResult_data();
                                                Toast.makeText(mContext, "登录成功,下载服务器数据成功!", Toast.LENGTH_SHORT).show();
                                                if (list !=null) {
                                                    getContentResolver().unregisterContentObserver(mContentObserver);
                                                    syncServerToLocal(list);
                                                    
                                                    getContentResolver().registerContentObserver(NotePad.Notes.CONTENT_URI, true, mContentObserver);
                                                    dumpLocalDataBase();
                                                }
                                            } else {
                                                showDialog("登录成功,但是下载服务器数据失败!", sFetchBean.getError_info());
                                            }
                                            dismissProgressDialog();
                                        } else if (FETCH_CMD_INSERT.equals(sFetchBean.getFetch_cmd())) {
                                            dismissProgressDialog();
                                            if (sFetchBean.getFetch_result()) {
                                                Toast.makeText(mContext, "登录成功,插入服务器数据成功!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                showDialog("登录成功,但是插入服务器数据失败!", sFetchBean.getError_info());
                                            }
                                        } else if (FETCH_CMD_UPDATE.equals(sFetchBean.getFetch_cmd())) {
                                            dismissProgressDialog();
                                            if (sFetchBean.getFetch_result()) {
                                                Toast.makeText(mContext, "登录成功,更新服务器数据成功!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                showDialog("登录成功,但是更新服务器数据失败!", sFetchBean.getError_info());
                                            }
                                        } else if (FETCH_CMD_DELETE.equals(sFetchBean.getFetch_cmd())) {
                                            dismissProgressDialog();
                                            if (sFetchBean.getFetch_result()) {
                                                Toast.makeText(mContext, "登录成功,删除服务器数据成功!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                showDialog("登录成功,但是删除服务器数据失败!", sFetchBean.getError_info());
                                            }
                                        }
                                    } else {
                                        showDialog("登录失败!", "");
                                    }
                                }
                            } else {
                                showDialog("网络错误!", result_data);
                            }
                        }
                        dismissProgressDialog();
                        break;
                    }
                    default:
                        break;
                }
            }
        };

        mHttpHelper.setMsgHandler(mWorkHandler);
        mContentObserver = new ContentObserver(mWorkHandler) {
            @Override
            public void onChange(boolean selfChange) {
                Log.i(TAG, ">>>>>onChange");
            }
            
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.i(TAG, ">>>>>onChangeWithUri="+uri);
                syncLocalToServer(uri);
            }
        };
        getContentResolver().registerContentObserver(NotePad.Notes.CONTENT_URI, true, mContentObserver);
        Notification notification = new Notification();
        this.startForeground(0x200, notification);
        
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetStatusReceiver, intentFilter);
    }

    private Long convertDate(String str) {
        if (str == null || str.isEmpty()) {
            return Long.valueOf(System.currentTimeMillis());//0L;
        }
        try {
            Date date = mFormat.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(System.currentTimeMillis());
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
                    Log.i(TAG, ">>>>dumpLocalDataBase = id="+id+"; title="+title+"; content="+content
                            +"; create="+mFormat.format(new Date(create))
                            +"; modify="+mFormat.format(new Date(modify)));
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
                    Log.i(TAG, ">>>>syncLocalToServer id="+id+"; title="+title+"; content="+content);
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
            values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, convertDate(n.getCreate_date()));
            values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, convertDate(n.getModify_date()));
            values.put(NotePad.Notes.COLUMN_NAME_TITLE, n.getNote_title());
            values.put(NotePad.Notes.COLUMN_NAME_NOTE, n.getNote_content());
            values.put(NotePad.Notes._ID, n.getNote_id());
            Uri uri = getContentResolver().insert(NotePad.Notes.CONTENT_URI, values);
            Log.i(TAG, ">>>>syncServerToLocal uri = "+uri+"; note_id"+n.getNote_id());
            if (uri == null) {
                //insert failed, try update
                uri = ContentUris.withAppendedId(NotePad.Notes.CONTENT_ID_URI_BASE, n.getNote_id());
                if (checkServerModify(uri, n.getModify_date())) {
                    getContentResolver().update(
                            uri,    // The URI for the record to update.
                            values,  // The map of column names and new values to apply to them.
                            null,    // No selection criteria are used, so no where columns are necessary.
                            null     // No where columns are used, so no where arguments are necessary.
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
        long serverTime = convertDate(serverModifyTime);
        try {
            cursor = getContentResolver().query(uri, READ_NOTE_PROJECTION, null, null, NotePad.Notes.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotePad.Notes._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_NOTE));
                long create = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
                long modify = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
                Log.i(TAG, ">>>>checkServerModify = id="+id+"; title="+title+"; content="+content
                        +"; create="+mFormat.format(new Date(create))
                        +"; modify="+mFormat.format(new Date(modify))
                        +"; serverTime="+mFormat.format(new Date(serverTime)));
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
        String cmd = intent.getStringExtra(CMD);
        if (DEBUG)
            Log.v(TAG, "onStartCommand CMD="+cmd+" flags="+flags+" , startId="+startId);

        sEmail = mDataBase.getEmail(0);
        sPass = mDataBase.getPassword(0);
        if (cmd != null && FETCH.equals(cmd)) {
            Message msg = mWorkHandler.obtainMessage(MSG_FETCH, intent);
            msg.sendToTarget();
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        if (DEBUG)
            Log.v(TAG, "service onDestroy");
        mHandlerThread.quit();
        getContentResolver().unregisterContentObserver(mContentObserver);
        unregisterReceiver(mNetStatusReceiver);
        this.stopForeground(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        switch (msg.what) {
            case MSG_STOP_SERVICE_BY_TIMEOUT:
                Log.i(TAG, "MSG_STOP_SERVICE_BY_TIMEOUT.");
                stopSelf();
                break;
            default:
                Log.w(TAG, "un-handled message.");
                return false;
        }
        return true;
    }
    
    private void checkNetStatus() {
        // TODO Auto-generated method stub
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = cm.getActiveNetworkInfo();
        if (activeInfo == null) {
            mIsNetConnected = false;
            mIsWifi = false;
            Toast.makeText(this, "当前没有可用网络连接!", Toast.LENGTH_LONG).show();
            return;
        }
        mIsNetConnected = activeInfo.isConnected();
        mIsWifi = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
        if (!mIsNetConnected) {
            Toast.makeText(this, "当前没有可用网络连接!", Toast.LENGTH_LONG).show();
        } else if (mIsNetConnected && !mIsWifi) {
            //Toast.makeText(this, "当前不是WiFi连接,注意流量使用!", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, R.string.is_wifi, Toast.LENGTH_LONG).show();
        }
    }
    
    private void showDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(mContext).setTitle(title).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        //android.permission.SYSTEM_ALERT_WINDOW 
        //允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
        ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        ad.setCancelable(false);
        ad.show();
    }
    
    private void showProgressDialog(String title, String message) {
        Log.i(TAG, "showProgressDialog ==" + mProgressDialog);
        dismissProgressDialog();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        //android.permission.SYSTEM_ALERT_WINDOW 
        //允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
        mProgressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mProgressDialog.show();
    }
    
    private void dismissProgressDialog() {
        Log.i(TAG, "dismissProgressDialog ==" + mProgressDialog);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
