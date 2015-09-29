
package com.codezyw.common;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

public class FileScanAsyncTask extends NamedAsyncTask<String, Integer, List<File>> {

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
            return true;
        }
    };
    private List<File> mFileList = new ArrayList<File>();
    private PostListener mPostListener;
    private Activity mActivity;
    private static String mSuffix = "";
    private static String mDirectory = "";

    public static interface PostListener {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute(List<File> result);

        void onCancelled(List<File> result);
    }

    public FileScanAsyncTask() {

    }

    public FileScanAsyncTask(Activity activity) {
        this(activity, null);
    }

    public FileScanAsyncTask(Activity activity, PostListener listener) {
        mPostListener = listener;
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        if (mPostListener != null) {
            mPostListener.onPreExecute();
        }
        UIHelper.showProgressDialog(mActivity, "正在扫描" + mSuffix + "文件中:", "", ProgressDialog.STYLE_SPINNER, true, 100);
    }

    @Override
    protected List<File> doInBackground(String... params) {
        if (params == null || params.length <= 1) {
            return null;
        }
        mDirectory = params[0];
        mSuffix = params[1];
        scanFileByPath(new File(mDirectory));
        return mFileList;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (mPostListener != null) {
            mPostListener.onProgressUpdate(progress[0]);
        }
        UIHelper.updateProgressDialogText("正在扫描" + mSuffix + "文件中:", "已经扫描到 " + progress[0] + " 个" + mSuffix + "文件.");
    }

    @Override
    protected void onPostExecute(List<File> r) {
        if (mPostListener != null) {
            mPostListener.onPostExecute(mFileList);
        }
    }

    @Override
    protected void onCancelled(List<File> r) {
        if (mPostListener != null) {
            mPostListener.onCancelled(mFileList);
        }
    }

    private void scanFileByPath(File folder) {
        if (isCancelled()) {
            return;
        }
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e("zyw", "setListAdapterByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                if (!file.isDirectory() && file.getName().endsWith(mSuffix)) {
                    mFileList.add(file);
                    publishProgress(mFileList.size());
                } else if (file.isDirectory()) {
                    scanFileByPath(file);
                }
            }
        }
    }

}
