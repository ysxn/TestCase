
package com.codezyw.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BackupAppFragment extends BaseFragment {
    private boolean DEBUG = true;
    private String TAG = "zyw";

    private PackageManager mPackageManager;
    private List<ApplicationInfo> mApps;
    private ProgressDialog mProgressDialog;
    private View mRootView;
    private Button mButtonBackUp;
    private int mTotal = 0;

    private final int REQUEST_UPDATE_DATA = 0x123;
    private final int REQUEST_DISMISS_PROGRESS = 0x122;

    private boolean mInterruptThread = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        int value = msg.arg1;
                        String label = (String) msg.obj;
                        mProgressDialog.setMax(mTotal);
                        mProgressDialog.setProgress(value);
                        mProgressDialog.setTitle("正在备份中..." + label);
                    }
                }
                    break;
                case REQUEST_DISMISS_PROGRESS: {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }
                    break;
            }
        }
    };

    public static BackupAppFragment newInstance(Bundle bundle) {
        BackupAppFragment f = new BackupAppFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        mRootView = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mRootView.setLayoutParams(lp);
        ((LinearLayout) mRootView).setGravity(Gravity.CENTER);

        mButtonBackUp = new Button(getActivity());
        LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mButtonBackUp.setText("立即备份到/BackupApp");
        ((LinearLayout) mRootView).addView(mButtonBackUp, lpButton);

        mPackageManager = getActivity().getPackageManager();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        mProgressDialog.setTitle("正在备份中...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(0);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(0);
        mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mInterruptThread = true;
            }
        });
        mButtonBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProgressDialog.isShowing()) {
                    return;
                }
                mProgressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mInterruptThread = false;
                        backupApp();
                    }
                }).start();
            }
        });
        return mRootView;
    }

    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }

    private void backupApp() {
        mApps = mPackageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS);
        if (mApps == null) {
            mApps = new ArrayList<ApplicationInfo>();
        }
        mTotal = 0;
        int progress = 0;
        for (ApplicationInfo app : mApps) {
            if (app.sourceDir != null && app.sourceDir.startsWith("/data/app/")) {
                mTotal++;
            }
        }
        for (ApplicationInfo app : mApps) {
            if (!mInterruptThread && app.sourceDir != null && app.sourceDir.startsWith("/data/app/")) {
                progress++;
                String label = (String) app.loadLabel(mPackageManager);
                if (DEBUG) {
                    Log.i(TAG, ">>>>>>>>>>app=" + app.sourceDir);
                    Log.i(TAG, ">>>>>>>>>>app=" + label);
                    Log.i(TAG, ">>>>>>>>>>app=" + app.packageName);
                }
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_UPDATE_DATA, progress, 0, label));
                if (!saveApp(app.packageName, app.sourceDir, label)) {
                    Toast.makeText(getActivity(), "备份失败:" + label, Toast.LENGTH_SHORT).show();
                }
            }
        }
        Log.i(TAG, ">>>>>>>>>>backupApp finish");
        mHandler.sendMessage(mHandler.obtainMessage(REQUEST_DISMISS_PROGRESS));
    }

    private boolean saveApp(String packageName, String sourceDir, String label) {
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>>>>packageName=" + packageName);
        }
        boolean ok = false;
        try {
            File in = new File(sourceDir);
            if (in == null || !in.exists() || !in.canRead()) {
                Log.i(TAG, ">>>>>>>>>>packageName : " + packageName + " file can not save.");
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
        Log.i(TAG, ">>>>>>>>>>备份结果 " + label + ": " + ok);
        return ok;
    }
}
