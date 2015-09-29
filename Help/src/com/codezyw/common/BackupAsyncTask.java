
package com.codezyw.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.codezyw.common.HttpPostAsyncTask.PostListener;

public class BackupAsyncTask extends NamedAsyncTask<Boolean, Integer, Boolean> {
    private PostListener mPostListener;
    private PackageManager mPackageManager;
    private List<ApplicationInfo> mApps;
    private Activity mActivity;
    private int mTotal = 0;
    private int mProgress = 0;
    private String mCurrent = "";

    public BackupAsyncTask() {

    }

    public BackupAsyncTask(Activity activity) {
        this(activity, null);
    }

    public BackupAsyncTask(Activity activity, PostListener listener) {
        mPostListener = listener;
        mActivity = activity;
        mPackageManager = activity.getPackageManager();
        mApps = mPackageManager.getInstalledApplications(0);
        if (mApps == null) {
            mApps = new ArrayList<ApplicationInfo>();
        }
    }

    @Override
    protected void onPreExecute() {
        if (mPostListener != null) {
            mPostListener.onPreExecute();
        }
        UIHelper.showProgressDialog(mActivity, "正在备份中...", "", ProgressDialog.STYLE_HORIZONTAL
                , false, 0);
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        backupApp();
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (mPostListener != null) {
            mPostListener.onProgressUpdate(progress[0]);
        }
        UIHelper.updateProgressDialog(mTotal, mProgress);
        UIHelper.updateProgressDialogText("正在备份: " + mCurrent, "");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (mPostListener != null) {
            mPostListener.onPostExecute(null);
        }
    }

    @Override
    protected void onCancelled(Boolean result) {
        if (mPostListener != null) {
            mPostListener.onCancelled(null);
        }
    }

    private void backupApp() {
        mTotal = 0;
        mProgress = 0;
        for (ApplicationInfo app : mApps) {
            if (app.sourceDir != null && app.sourceDir.startsWith("/data/app/")) {
                mTotal++;
            }
        }
        for (ApplicationInfo app : mApps) {
            if (isCancelled() || !UIHelper.isProgressShowing()) {
                return;
            }
            if (app.sourceDir != null && app.sourceDir.startsWith("/data/app/")) {
                mProgress++;
                String label = (String) app.loadLabel(mPackageManager);
                mCurrent = label;
                publishProgress(mProgress);
                if (!saveApp(app.packageName, app.sourceDir, label)) {
                    Toast.makeText(mActivity, "备份失败:" + label, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean saveApp(String packageName, String sourceDir, String label) {
        boolean ok = false;
        try {
            File in = new File(sourceDir);
            if (in == null || !in.exists() || !in.canRead()) {
                Log.e("zyw", ">>>>>>>>>>packageName : " + packageName + " file can not save.");
                return false;
            }
            File dir = new File(Environment.getExternalStorageDirectory() + "/BackupApp");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File out = new File(Environment.getExternalStorageDirectory() + "/BackupApp/" + label + ".apk");
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                out.createNewFile();
                fis = new FileInputStream(in);
                fos = new FileOutputStream(out);
                int count;
                byte[] buffer = new byte[256 * 1024];
                while ((count = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            String inMd5 = MD5Util.getFileMD5String(in);
            String outMd5 = MD5Util.getFileMD5String(out);
            if (inMd5 != null && outMd5 != null && inMd5.equals(outMd5)) {
                ok = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
}
