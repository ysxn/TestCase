
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codezyw.common.BaseListFragment;
import com.codezyw.common.FileIOHelper;
import com.codezyw.common.SlideMenuListener;
import com.codezyw.common.ThreadPoolHelper.ThreadBaseRunnable;

public class ApkBrowser extends BaseListFragment {
    private final String TAG = "zyw";

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
            return true;
        }
    };

    private FileIOHelper mUtil = new FileIOHelper(getActivity());
    private ApkListAdapter mFileListAdapter;

    private ProgressDialog mProgressDialog;

    private ListView mListView;

    private SlideMenuListener mSwipeTouchListener;

    private List<FileData> mFilesList = new ArrayList<FileData>();

    private PackageManager mPackageManager;

    private List<ApplicationInfo> mApps;

    private List<PackageInfo> mPackages;

    private static String mSuffix = "";
    private static String mDirectory = "";
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(6, 30, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private static final int REQUEST_UPDATE_PROGRESS = 299;

    private final int REQUEST_UPDATE_DATA = 0x123;

    private final int REQUEST_DISMISS_PROGRESS = 0x122;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    if (mFileListAdapter != null) {
                        mFileListAdapter.notifyDataSetChanged();
                        Log.i(TAG, "notifyDataSetChanged");
                    }
                }
                    break;
                case REQUEST_UPDATE_PROGRESS: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        int count = msg.arg1;
                        String fileName = (String) msg.obj;
                        mProgressDialog.setMessage("已经扫描到 " + count + " 个APK安装包：" + fileName);
                    }
                }
                    break;
                case REQUEST_DISMISS_PROGRESS: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mFileListAdapter = new ApkListAdapter(getActivity());
                    mFileListAdapter.setData(mFilesList);
                    setListAdapter(mFileListAdapter);
                    mSwipeTouchListener = new SlideMenuListener(getActivity(), mListView);
                    mListView.setOnTouchListener(mSwipeTouchListener);
                    Toast.makeText(getActivity(), "已经扫描到 " + mFilesList.size() + " 个APK安装包：", Toast.LENGTH_LONG).show();
                }
                    break;
            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mListView = getListView();
        Intent i = getActivity().getIntent();
        mSuffix = i.getStringExtra(Constant.SUFFFIX);
        mDirectory = i.getStringExtra(Constant.DIRECTORY);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        mProgressDialog.setTitle("正在扫描APK安装包中...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mPackageManager = getActivity().getPackageManager();

        if (mDirectory != null && !mDirectory.isEmpty()) {
            final File sdcard = new File(mDirectory);// android.os.Environment.getExternalStorageDirectory();
            Log.i(TAG, "sdcard=" + sdcard);
            new Thread() {
                public void run() {
                    // Retrieve all known applications.
                    mApps = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS);
                    if (mApps == null) {
                        mApps = new ArrayList<ApplicationInfo>();
                    }
                    mPackages = mPackageManager.getInstalledPackages(PackageManager.GET_DISABLED_COMPONENTS);
                    if (mPackages == null) {
                        mPackages = new ArrayList<PackageInfo>();
                    }
                    scanApkFileByPath(sdcard);
                    mHandler.sendEmptyMessage(REQUEST_DISMISS_PROGRESS);
                };
            }.start();
        } else {
            final File sdcard = android.os.Environment.getExternalStorageDirectory();
            Log.i(TAG, "sdcard=" + sdcard);
            new Thread() {
                public void run() {
                    // Retrieve all known applications.
                    mApps = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS);
                    if (mApps == null) {
                        mApps = new ArrayList<ApplicationInfo>();
                    }
                    mPackages = mPackageManager.getInstalledPackages(PackageManager.GET_DISABLED_COMPONENTS);
                    if (mPackages == null) {
                        mPackages = new ArrayList<PackageInfo>();
                    }
                    long startTime = SystemClock.elapsedRealtime();
                    scanApkFileByPath(sdcard);
                    Log.i(TAG, "scanApkFileByPath end=" + (SystemClock.elapsedRealtime() - startTime));
                    mHandler.sendEmptyMessage(REQUEST_DISMISS_PROGRESS);
                };
            }.start();
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FileData fileData = (FileData) view.getTag();
                Toast.makeText(getActivity(), "签名：" + fileData.mCert, Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        threadPool.shutdown();
    }

    private void scanApkFileByPath(File folder) {
        Log.i(TAG, "scanApkFileByPath folder=" + folder);
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e(TAG, "scanApkFileByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                if (!file.isDirectory() && file.getName().endsWith(".apk")) {
                    // file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
                    FileData fdData = new FileData();
                    fdData.mFile = file;
                    threadPool.execute(new ThreadRunnable(fdData, mHandler));
                    mFilesList.add(fdData);
                    mHandler.sendMessage(mHandler.obtainMessage(REQUEST_UPDATE_PROGRESS, mFilesList.size(), 0, file.getName()));
                } else if (file.isDirectory()) {
                    scanApkFileByPath(file);
                }
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FileData fileData = (FileData) v.getTag();
        File file = fileData.mFile;
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (file.isDirectory()) {
            // scanApkFileByPath(file);
        } else if (file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$")) {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            startActivity(intent);
        } else if (file.getName().matches("^.*?\\.(swf|mp4|3gp)$")) {
            // intent.setClass(FileBrowser.this, MovieActivity.class);
            // intent.putExtra("fileName",
            // file.getAbsolutePath().replace("/mnt", ""));
            intent.setData(Uri.parse(file.getAbsolutePath()));
            startActivity(intent);
            // FileBrowser.this.finish();
        } else if (file.getName().endsWith(".apk")) {
            mUtil.installApk(file);
        }
    }

    private int checkInstallStat(FileData fd) {
        fd.mInstalled = Constant.NONE;
        fd.mVersion = "安装状态：未安装";
        if (fd.mAi == null || fd.mPi == null || fd.mPi.packageName == null || fd.mPi.versionName == null) {
            return fd.mInstalled;
        }
        for (PackageInfo pi : mPackages) {
            if (fd.mAi.packageName.equals(pi.packageName)) {
                if (fd.mPi.versionCode == pi.versionCode && fd.mPi.versionName.equals(pi.versionName)) {
                    fd.mInstalled = Constant.SAME;
                    fd.mVersion = "安装状态：已安装:" + pi.versionName + " : " + pi.versionCode;
                } else {
                    fd.mInstalled = Constant.SAME;
                    fd.mVersion = "安装状态：已安装:" + pi.versionName + " : " + pi.versionCode + "->" + fd.mPi.versionName + " : " + fd.mPi.versionCode;
                }
            }
        }
        return fd.mInstalled;
    }

    private String getSignatures(android.content.pm.Signature[] signatures) {
        if (signatures == null || signatures.length <= 0) {
            return "";
        }
        String r = "";
        for (android.content.pm.Signature s : signatures) {
            Log.i("zyw", ">>>" + new String(s.toChars()));
            Log.i("zyw", ">>>" + new String(s.toChars()));
            r = r + s.toCharsString();
        }
        return r;
    }

    private class ThreadRunnable extends ThreadBaseRunnable {
        private FileData fdData;
        private Handler handler;

        public ThreadRunnable(final FileData fileData, final Handler handler) {
            this.fdData = fileData;
            this.handler = handler;
        }

        @Override
        public void run() {
            File file = fdData.mFile;
            fdData.mDrawable = mUtil.getUninatllApkInfo(getActivity(), file.getAbsolutePath(), file);
            fdData.mPi = mUtil.getPackageInfo(getActivity(), file.getAbsolutePath());
            fdData.mAi = (fdData.mPi != null) ? fdData.mPi.applicationInfo : null;
            fdData.mInstalled = checkInstallStat(fdData);
            fdData.mCert = getSignatures(fdData.mPi.signatures);
            handler.removeMessages(REQUEST_UPDATE_DATA);
            handler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 10000);
        }
    }
}
