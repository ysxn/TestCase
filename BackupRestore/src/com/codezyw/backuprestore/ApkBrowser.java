
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ApkBrowser extends ListActivity {
    private final String TAG = "zyw";

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
            return true;
        }
    };

    private Util mUtil = new Util(this);
    private ApkListAdapter mFileListAdapter;

    private ProgressDialog mProgressDialog;
    
    private ListView mListView;

    private SlideMenuListener mSwipeTouchListener;
    
    private List<FileData> mFilesList = new ArrayList<FileData>();;

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
                    }
                }

                    break;
                case REQUEST_UPDATE_PROGRESS: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        int count = msg.arg1;
                        String fileName = (String) msg.obj;
                        mProgressDialog.setMessage("�Ѿ�ɨ�赽 " + count + " ��APK��װ����" + fileName);
                    }
                }
                    break;
                case REQUEST_DISMISS_PROGRESS: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    mFileListAdapter = new ApkListAdapter(ApkBrowser.this);
                    mFileListAdapter.setData(mFilesList);
                    setListAdapter(mFileListAdapter);
                    mSwipeTouchListener = new SlideMenuListener(ApkBrowser.this, mListView);
                    mListView.setOnTouchListener(mSwipeTouchListener);
                    Toast.makeText(ApkBrowser.this, "�Ѿ�ɨ�赽 " + mFilesList.size() + " ��APK��װ����",
                            Toast.LENGTH_LONG).show();
                }
                    break;
            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mListView = getListView();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        mProgressDialog.setTitle("����ɨ��APK��װ����...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        final File sdcard = android.os.Environment.getExternalStorageDirectory();
        Log.i(TAG, "sdcard=" + sdcard);
        new Thread() {
            public void run() {
                scanApkFileByPath(sdcard);
                mHandler.sendEmptyMessage(REQUEST_DISMISS_PROGRESS);
            };
        }.start();

    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
                    fdData.mDrawable = mUtil.getUninatllApkInfo(ApkBrowser.this, file.getAbsolutePath(), file);
                    mFilesList.add(fdData);
                    mHandler.sendMessage(mHandler.obtainMessage(REQUEST_UPDATE_PROGRESS,
                            mFilesList.size(), 0, file.getName()));
                } else if (file.isDirectory()) {
                    scanApkFileByPath(file);
                }
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
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
}