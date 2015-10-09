
package com.codezyw.common;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

public class DownloadService extends Service implements Handler.Callback {
    private static final String TAG = "zyw";
    public static final boolean DEBUG = true;
    public static final int MSG_STOP_SERVICE_BY_SELF = 110;
    public static final int BUFFER_SIZE = 4096;
    public static final String URL = "download_url";
    public static final String MD5 = "download_MD5";
    public static final String SILENT = "download_silent";
    public static final String WIFI_LIMIT = "download_only_wifi";
    public static final String CMD = "cmd";
    public static final String START_DOWNLOAD = "start_download";
    public static final String INSTALL_APK = "install";
    public static final int MSG_START_DOWNLOAD = 0x1000;
    public static final int MSG_TRY_CONTINUE_DOWNLOAD = 0x1001;

    private boolean isDownloadStarted = false;
    private Handler mServiceMainHandler;
    private HandlerThread mHandlerThread;
    private Handler mWorkHandler;
    private Context mContext = DownloadService.this;
    private boolean mIsNetConnected = false;
    private boolean mIsWifi = false;
    private boolean mDownloadSilent = false;
    private boolean mWifiLimit = true;
    private int mAlreadyWrite;
    private int mTotalByte;
    private HttpURLConnection httpConn;
    private NotificationManager mNm;
    private Notification mNotification;
    private String mNotificationTag = "DownloadService";
    private int mNotificationId = DownloadService.this.hashCode();

    public static String mUrl;
    public static String mMd5;
    public static String mSaveDirectory;
    private BroadcastReceiver mNetStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetStatus();
        }
    };

    @Override
    public void onCreate() {
        if (DEBUG) {
            Log.i(TAG, "DownloadService onCreate");
        }
        super.onCreate();
        mSaveDirectory = FileHelper.getSdcardRootDir(mContext, "download");
        Log.i(TAG, "DownloadService onCreate mSaveDirectory=" + mSaveDirectory);
        mTotalByte = DownloadHelper.getInstance(mContext).getInt(Constant.APK_DOWNLOAD_TOTAL, 0);
        mAlreadyWrite = DownloadHelper.getInstance(mContext).getInt(Constant.APK_DOWNLOAD_PROGRESS, 0);
        mUrl = DownloadHelper.getInstance(mContext).getString(Constant.APK_DOWNLOAD_URL, "");
        mMd5 = DownloadHelper.getInstance(mContext).getString(Constant.APK_DOWNLOAD_MD5, "");
        DownloadHelper.getInstance(mContext).close();
        mServiceMainHandler = new Handler(this);
        mHandlerThread = new HandlerThread("DownloadService_thread");
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_START_DOWNLOAD: {
                        Intent i = (Intent) msg.obj;
                        if (i != null) {
                            String url = i.getStringExtra(URL);
                            String md5 = i.getStringExtra(MD5);
                            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(md5)) {
                                mTotalByte = 0;
                                mAlreadyWrite = 0;
                                mUrl = url;
                                mMd5 = md5;
                                DownloadHelper.getInstance(mContext).putInt(Constant.APK_DOWNLOAD_TOTAL, 0);
                                DownloadHelper.getInstance(mContext).putInt(Constant.APK_DOWNLOAD_PROGRESS, 0);
                                DownloadHelper.getInstance(mContext).putString(Constant.APK_DOWNLOAD_URL, mUrl);
                                DownloadHelper.getInstance(mContext).putString(Constant.APK_DOWNLOAD_MD5, mMd5);
                                DownloadHelper.getInstance(mContext).close();
                            }
                            mWorkHandler.sendEmptyMessageDelayed(MSG_TRY_CONTINUE_DOWNLOAD, 1000);
                        }
                        break;
                    }
                    case MSG_TRY_CONTINUE_DOWNLOAD: {
                        try {
                            mWorkHandler.removeMessages(MSG_TRY_CONTINUE_DOWNLOAD);
                            downloadFile(mUrl, mSaveDirectory);
                        } catch (Exception e) {
                            if (httpConn != null) {
                                httpConn.disconnect();
                                httpConn = null;
                            }
                            e.printStackTrace();
                            if (isNetOk()) {
                                mWorkHandler.sendEmptyMessageDelayed(MSG_TRY_CONTINUE_DOWNLOAD, 1000);
                            }
                        }
                        DownloadHelper.getInstance(mContext).putInt(Constant.APK_DOWNLOAD_PROGRESS, mAlreadyWrite);
                        DownloadHelper.getInstance(mContext).close();
                        if (isDownloadComplete()) {
                            mWorkHandler.removeMessages(MSG_TRY_CONTINUE_DOWNLOAD);
                            showDialog(mContext, TransparentActivity.DIALOG_BULLET_DOWNLOAD_OK);
                            updateDownloadNotification(mAlreadyWrite, mTotalByte, "下载完成");
                        } else {
                            checkNetStatus();
                            if (mWifiLimit && !mIsWifi && mIsNetConnected) {
                                showDialog(mContext, TransparentActivity.DIALOG_CONFIRM_NOT_WIFI);
                            }
                            updateDownloadNotification(mAlreadyWrite, mTotalByte, "下载暂停");
                        }
                        break;
                    }

                    default:
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetStatusReceiver, intentFilter);
        mNm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cmd = intent.getStringExtra(CMD);
        if (DEBUG) {
            Log.i(TAG, "DownloadService onStartCommand CMD=" + cmd + " flags=" + flags + " , startId=" + startId);
        }

        if (START_DOWNLOAD.equals(cmd)) {
            if (isDownloadComplete()) {
                showDialog(mContext, TransparentActivity.DIALOG_BULLET_DOWNLOAD_OK);
            } else {
                mDownloadSilent = intent.getBooleanExtra(SILENT, false);
                mWifiLimit = intent.getBooleanExtra(WIFI_LIMIT, true);
                if (mDownloadSilent) {
                    mNm.cancel(mNotificationTag, mNotificationId);
                }
                if (!isDownloadStarted) {
                    isDownloadStarted = true;
                    Message msg = mWorkHandler.obtainMessage(MSG_START_DOWNLOAD, intent);
                    mWorkHandler.sendMessageDelayed(msg, 30000);
                } else if (isNetOk()) {
                    mWorkHandler.sendEmptyMessageDelayed(MSG_TRY_CONTINUE_DOWNLOAD, 30000);
                }
            }
        } else if (INSTALL_APK.equals(cmd)) {
            installApk();
        } else {
            if (isDownloadComplete()) {
                showDialog(mContext, TransparentActivity.DIALOG_BULLET_DOWNLOAD_OK);
            }
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) {
            Log.i(TAG, "DownloadService onDestroy");
        }
        if (httpConn != null) {
            httpConn.disconnect();
            httpConn = null;
        }
        mHandlerThread.quit();
        unregisterReceiver(mNetStatusReceiver);
        this.stopForeground(true);
        mNm.cancel(mNotificationTag, mNotificationId);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_STOP_SERVICE_BY_SELF:
                Log.i(TAG, "finish DownloadService.");
                stopSelf();
                break;
            default:
                Log.w(TAG, "un-handled message.");
                return false;
        }
        return true;
    }

    private boolean isNetOk() {
        return (mWifiLimit && mIsWifi) || (!mWifiLimit && mIsNetConnected);
    }

    private void checkNetStatus() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = cm.getActiveNetworkInfo();
        if (activeInfo == null) {
            mIsNetConnected = false;
            mIsWifi = false;
            return;
        }
        mIsNetConnected = activeInfo.isConnected();
        if (!mIsNetConnected) {
            mIsWifi = false;
        } else if (activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            mIsWifi = true;
            Log.i(TAG, "checkNetStatus mIsWifi=" + mIsWifi);
            mWorkHandler.sendEmptyMessageDelayed(MSG_TRY_CONTINUE_DOWNLOAD, 15000);
        } else {
            mIsWifi = false;
        }
    }

    private boolean isDownloadComplete() {
        boolean r = false;
        String why = "";
        if (mAlreadyWrite == mTotalByte && mTotalByte > 0) {
            if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mMd5) && !TextUtils.isEmpty(getSavePath())) {
                File file = new File(getSavePath());
                try {
                    if (file.exists()) {
                        String md5 = MD5Util.getFileMD5String(file);
                        Log.i(TAG, "md5=" + md5 + ",mMd5=" + mMd5);
                        if (mMd5.equalsIgnoreCase(md5)) {
                            r = true;
                            why = "md5 OK";
                        } else if (file != null) {
                            why = "md5 not equal";
                            file.delete();
                        }
                    } else {
                        why = "file not exists";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    r = false;
                    why = "Exception=" + e.toString();
                }
                if (!r) {
                    mTotalByte = 0;
                    mAlreadyWrite = 0;
                    DownloadHelper.getInstance(mContext).putInt(Constant.APK_DOWNLOAD_TOTAL, 0);
                    DownloadHelper.getInstance(mContext).putInt(Constant.APK_DOWNLOAD_PROGRESS, 0);
                    DownloadHelper.getInstance(mContext).close();
                    mWorkHandler.sendEmptyMessage(MSG_TRY_CONTINUE_DOWNLOAD);
                }
            }
        } else {
            why = "byte error write=" + mAlreadyWrite + " total=" + mTotalByte;
        }
        Log.i(TAG, "isDownloadComplete=" + r + " why=" + why);
        return r;
    }

    public void downloadFile(String fileURL, String saveDir) throws Exception {
        Log.i(TAG, "downloadFile check url=" + mUrl + ",md5=" + mMd5);
        Log.i(TAG, "downloadFile check isNetOk=" + isNetOk() + "  httpConn=" + httpConn + " write=" + mAlreadyWrite + " total=" + mTotalByte);
        Log.i(TAG, "downloadFile check mIsWifi=" + mIsWifi + " mWifiLimit=" + mWifiLimit + " mIsNetConnected=" + mIsNetConnected);
        String saveFilePath = getSavePath();
        Log.i(TAG, "downloadFile check fileName = " + saveFilePath);
        if (TextUtils.isEmpty(mUrl) || TextUtils.isEmpty(mMd5) || httpConn != null || !isNetOk() || TextUtils.isEmpty(saveFilePath) || isDownloadComplete()) {
            return;
        }

        URL url = new URL(fileURL);
        httpConn = (HttpURLConnection) url.openConnection();
        if (mAlreadyWrite > 0) {
            httpConn.addRequestProperty("Range", "bytes=" + mAlreadyWrite + "-");
        }
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if ((responseCode == HttpURLConnection.HTTP_OK && mAlreadyWrite == 0) || (responseCode == HttpURLConnection.HTTP_PARTIAL && mAlreadyWrite > 0)) {
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            Log.i(TAG, "downloadFile Content-Type = " + contentType);
            Log.i(TAG, "downloadFile Content-Length = " + contentLength);
            if (mAlreadyWrite == 0) {
                if (mTotalByte > 0 && mTotalByte != contentLength) {
                    // 服务器大小变化了
                    android.util.Log.i(TAG, "mTotalByte != contentLength");
                }
                mTotalByte = contentLength;
                DownloadHelper.getInstance(mContext).putInt(Constant.APK_DOWNLOAD_TOTAL, mTotalByte);
                DownloadHelper.getInstance(mContext).close();
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            // opens an output stream to save into file
            RandomAccessFile osavedfile = new RandomAccessFile(saveFilePath, "rw");
            Log.i(TAG, "osavedfile.length = " + osavedfile.length());
            if (osavedfile.length() != mAlreadyWrite) {
                // 本地已经保存的部分文件不符合
                android.util.Log.i(TAG, "osavedfile.length() != mAlreadyWrite");
            }
            osavedfile.seek(mAlreadyWrite);

            int bytesRead = -1;

            byte[] buffer = new byte[BUFFER_SIZE];
            long time = SystemClock.elapsedRealtime();
            while (isNetOk() && (bytesRead = inputStream.read(buffer)) != -1) {
                osavedfile.write(buffer, 0, bytesRead);
                mAlreadyWrite += bytesRead;
                if (SystemClock.elapsedRealtime() > time + 1500) {
                    time = SystemClock.elapsedRealtime();
                    Log.i(TAG, "download write= " + mAlreadyWrite + " Total=" + mTotalByte + " Content-Length = " + contentLength);
                    updateDownloadNotification(mAlreadyWrite, mTotalByte, "下载中");
                }
            }
            Log.i(TAG, "write file bytes= " + mAlreadyWrite + " sTotalByte=" + mTotalByte + " Content-Length = " + contentLength);

            osavedfile.close();
            inputStream.close();

            Log.i(TAG, "downloadFile write file bytes= " + mAlreadyWrite + " mTotalByte=" + mTotalByte + " Content-Length = " + contentLength);
        } else {
            Log.i(TAG, "downloadFile No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
        httpConn = null;
    }

    @SuppressWarnings("deprecation")
    private void updateDownloadNotification(int progress, int total, String content) {
        if (mDownloadSilent) {
            return;
        }
        int updatePercent = 0;
        if (total > 0) {
            if (total == progress) {
                updatePercent = 100;
            } else {
                updatePercent = (int) (100f * progress / total);
            }
        }
        if (mNotification == null) {
            mNotification = new Notification();
            mNotification.icon = android.R.drawable.stat_sys_download;
            mNotification.tickerText = "下载";
            Intent i = new Intent(this, DownloadService.class);
            i.putExtra(DownloadService.CMD, DownloadService.INSTALL_APK);
            PendingIntent contentIntent = PendingIntent.getService(this, 0, i, 0);
            mNotification.contentIntent = contentIntent;
        }
        mNotification.setLatestEventInfo(mContext, "下载", "下载进度: " + updatePercent + "%", mNotification.contentIntent);
        // mNotification.contentView = new RemoteViews(getPackageName(),
        // R.layout.download_notification);
        // mNotification.contentView.setImageViewResource(R.id.update_notification_icon,
        // R.drawable.icon);
        // mNotification.contentView.setProgressBar(R.id.update_notification_progress,
        // 100, updatePercent, false);
        // mNotification.contentView.setTextViewText(R.id.update_notification_text,
        // content);
        // mNotification.contentView.setTextViewText(R.id.progress_text,
        // updatePercent + "%");

        // mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNm.notify(mNotificationTag, mNotificationId, mNotification);

    }

    private void showDialog(Context context, int type) {
        if (context == null) {
            return;
        }
        if (mDownloadSilent && type != TransparentActivity.DIALOG_BULLET_DOWNLOAD_OK) {
            return;
        }
        String title = "下载";
        String message = "\n" + "下载完成." + "\n";
        if (type == TransparentActivity.DIALOG_CONFIRM_NOT_WIFI) {
            message = "当前没有连接WIFI网络, 下载需求较大流量， 是否继续下载?";
        }
        Intent i = new Intent();
        Bundle bd = new Bundle();
        bd.putInt(TransparentActivity.BUNDLE_KEY_DIALOG_ID, type);
        bd.putString(TransparentActivity.BUNDLE_TITLE, title);
        bd.putString(TransparentActivity.BUNDLE_MESSAGE, message);
        bd.putString(TransparentActivity.SAVE_PATH, getSavePath());
        i.setClass(context, TransparentActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(bd);
        context.startActivity(i);
        if (type == TransparentActivity.DIALOG_BULLET_DOWNLOAD_OK) {
            mWorkHandler.removeMessages(MSG_TRY_CONTINUE_DOWNLOAD);
            mServiceMainHandler.sendEmptyMessage(MSG_STOP_SERVICE_BY_SELF);
        }
        Log.i(TAG, "showDialog type=" + type);
    }

    private static String getSavePath() {
        String savePath = null;
        if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mSaveDirectory)) {
            // extracts file name from URL
            String fileName = mUrl.substring(mUrl.lastIndexOf("/") + 1, mUrl.length());
            savePath = mSaveDirectory + File.separator + fileName;
        }
        return savePath;
    }

    private void installApk() {
        if (isDownloadComplete()) {
            FileHelper.installApk(this, getSavePath());
            mWorkHandler.removeMessages(MSG_TRY_CONTINUE_DOWNLOAD);
            mServiceMainHandler.sendEmptyMessage(MSG_STOP_SERVICE_BY_SELF);
        }
    }

    public static void tryInstall(Context context, String updateUrl) {
        if (context != null) {
            Intent i = new Intent(context, DownloadService.class);
            i.putExtra(DownloadService.CMD, DownloadService.INSTALL_APK);
            context.startService(i);
        }
    }

    /**
     * @param context
     * @param updateUrl
     * @param silent 是否不显示通知栏进度
     * @param wifiLimit 是否只能在wifi下更新
     * @return
     */
    public static boolean startDownloadService(Context context, String updateUrl, boolean silent, boolean wifiLimit) {
        boolean r = false;
        if (context != null && !TextUtils.isEmpty(updateUrl)) {
            Intent i = new Intent(context, DownloadService.class);
            i.putExtra(DownloadService.CMD, DownloadService.START_DOWNLOAD);
            String paraUrl = updateUrl;
            String md5 = null;
            int md5Suffix = updateUrl.indexOf("?MD5=");
            if (md5Suffix != -1) {
                md5 = updateUrl.substring(md5Suffix + 5);
                paraUrl = updateUrl.substring(0, md5Suffix);
            }
            if (!TextUtils.isEmpty(paraUrl) && !TextUtils.isEmpty(md5)) {
                Log.i(TAG, "startDownloadService");
                i.putExtra(DownloadService.SILENT, silent);
                i.putExtra(DownloadService.URL, paraUrl);
                i.putExtra(DownloadService.MD5, md5);
                i.putExtra(DownloadService.WIFI_LIMIT, wifiLimit);
                mUrl = paraUrl;
                mMd5 = md5;
                DownloadHelper.getInstance(context).putString(Constant.APK_DOWNLOAD_URL, mUrl);
                DownloadHelper.getInstance(context).putString(Constant.APK_DOWNLOAD_MD5, mMd5);
                DownloadHelper.getInstance(context).close();
                context.startService(i);
                r = true;
            }
        }
        Log.i(TAG, "startDownloadService return=" + r + " updateUrl=" + updateUrl + " silent=" + silent + " wifiLimit=" + wifiLimit);
        return r;
    }
}
