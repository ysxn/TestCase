
package com.codezyw.common;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

import com.codezyw.common.AppScanAsyncTask.ApkData;
import com.codezyw.common.ThreadPoolHelper.ThreadBaseRunnable;
import com.codezyw.common.ThreadPoolHelper.ThreadCallBackData;

public class AppScanAsyncTask extends NamedAsyncTask<File, Integer, List<ApkData>> {

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
            return true;
        }
    };
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(6, 30, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private List<ApkData> mApkList = new ArrayList<ApkData>();
    private PostListener mPostListener;
    private PackageManager mPackageManager;
    private List<ApplicationInfo> mApps;
    private List<PackageInfo> mPackages;
    private Activity mActivity;

    public static interface PostListener {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute(List<ApkData> result);

        void onCancelled(List<ApkData> result);
    }

    public AppScanAsyncTask() {

    }

    public AppScanAsyncTask(Activity activity) {
        this(activity, null);
    }

    public AppScanAsyncTask(Activity activity, PostListener listener) {
        mPostListener = listener;
        mActivity = activity;
        mPackageManager = activity.getPackageManager();

        mApps = mPackageManager.getInstalledApplications(0);
        if (mApps == null) {
            mApps = new ArrayList<ApplicationInfo>();
        }
        mPackages = mPackageManager.getInstalledPackages(0);
        if (mPackages == null) {
            mPackages = new ArrayList<PackageInfo>();
        }
    }

    @Override
    protected void onPreExecute() {
        if (mPostListener != null) {
            mPostListener.onPreExecute();
        }
        UIHelper.showProgressDialog(mActivity, "正在扫描APK安装包:", "", ProgressDialog.STYLE_SPINNER, true, 100);
    }

    @Override
    protected List<ApkData> doInBackground(File... params) {
        if (params == null || params.length <= 0) {
            return null;
        }
        scanApkFileByPath(params[0]);
        return mApkList;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (mPostListener != null) {
            mPostListener.onProgressUpdate(progress[0]);
        }
        UIHelper.updateProgressDialogText("正在扫描APK安装包:", "已经扫描到 " + progress[0] + " 个APK安装包.");
    }

    @Override
    protected void onPostExecute(List<ApkData> r) {
        if (mPostListener != null) {
            mPostListener.onPostExecute(mApkList);
        }
    }

    @Override
    protected void onCancelled(List<ApkData> r) {
        if (mPostListener != null) {
            mPostListener.onCancelled(mApkList);
        }
    }

    private void scanApkFileByPath(File folder) {
        if (isCancelled()) {
            return;
        }
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e("zyw", "scanApkFileByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                if (!file.isDirectory() && file.getName().endsWith(".apk")) {
                    // file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
                    ApkData fdData = new ApkData();
                    fdData.mFile = file;
                    threadPool.execute(new ThreadRunnable(fdData));
                    mApkList.add(fdData);
                    publishProgress(mApkList.size());
                } else if (file.isDirectory()) {
                    scanApkFileByPath(file);
                }
            }
        }
    }

    private int checkInstallStat(ApkData fd) {
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
            r = r + s.toCharsString();
        }
        return r;
    }

    public class ThreadRunnable extends ThreadBaseRunnable {
        private ApkData fdData;
        @SuppressWarnings("unused")
        private Handler handler;

        public ThreadRunnable(final ApkData fileData) {
            this.fdData = fileData;
        }

        public ThreadRunnable(final ApkData fileData, final Handler handler) {
            this.fdData = fileData;
            this.handler = handler;
        }

        @Override
        public void run() {
            File file = fdData.mFile;
            fdData.mDrawable = FileHelper.getUninatllApkInfo(mActivity, file.getAbsolutePath(), file);
            fdData.mPi = FileHelper.getPackageInfo(mActivity, file.getAbsolutePath());
            fdData.mAi = (fdData.mPi != null) ? fdData.mPi.applicationInfo : null;
            fdData.mInstalled = checkInstallStat(fdData);
            fdData.mCert = getSignatures(fdData.mPi.signatures);
        }
    }

    public class ApkData extends ThreadCallBackData {
        public int mInstalled = Constant.NONE;
        public File mFile;
        public Drawable mDrawable;
        public PackageInfo mPi;
        public ApplicationInfo mAi;
        public String mVersion = "";
        public String mCert = "";
    }
}
