
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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
    
    private List<FileData> mFilesList = new ArrayList<FileData>();
    
    private PackageManager mPm;
    
    private List<ApplicationInfo> mApps;
    
    private List<PackageInfo> mPackages;
    

    private static String mSuffix = "";
    private static String mDirectory = "";

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
                        mProgressDialog.setMessage("已经扫描到 " + count + " 个APK安装包：" + fileName);
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
                    Toast.makeText(ApkBrowser.this, "已经扫描到 " + mFilesList.size() + " 个APK安装包：",
                            Toast.LENGTH_LONG).show();
                }
                    break;
            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mListView = getListView();
        Intent i = getIntent();
        mSuffix = i.getStringExtra(Constant.SUFFFIX);
        mDirectory = i.getStringExtra(Constant.DIRECTORY);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        mProgressDialog.setTitle("正在扫描APK安装包中...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mPm = getPackageManager();

        if (mDirectory != null && !mDirectory.isEmpty()) {
            final File sdcard = new File(mDirectory);//android.os.Environment.getExternalStorageDirectory();
            Log.i(TAG, "sdcard=" + sdcard);
            new Thread() {
                public void run() {
                	// Retrieve all known applications.
            		mApps = mPm
            				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
            						| PackageManager.GET_DISABLED_COMPONENTS);
            		if (mApps == null) {
            			mApps = new ArrayList<ApplicationInfo>();
            		}
            		mPackages = mPm.getInstalledPackages(PackageManager.GET_DISABLED_COMPONENTS);
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
            		mApps = mPm
            				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
            						| PackageManager.GET_DISABLED_COMPONENTS);
            		if (mApps == null) {
            			mApps = new ArrayList<ApplicationInfo>();
            		}
            		mPackages = mPm.getInstalledPackages(PackageManager.GET_DISABLED_COMPONENTS);
            		if (mPackages == null) {
            			mPackages = new ArrayList<PackageInfo>();
            		}
                    scanApkFileByPath(sdcard);
                    mHandler.sendEmptyMessage(REQUEST_DISMISS_PROGRESS);
                };
            }.start();
        }
        
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            	FileData fileData = (FileData) view.getTag();
                Toast.makeText(ApkBrowser.this, "签名："+fileData.mCert, Toast.LENGTH_LONG).show();
                return true;
            }
        });

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
                    fdData.mPi = mUtil.getPackageInfo(ApkBrowser.this, file.getAbsolutePath());
                    fdData.mAi = (fdData.mPi != null) ? fdData.mPi.applicationInfo : null;
                    fdData.mInstalled = checkInstallStat(fdData);
                    fdData.mCert = getSignatures(fdData.mPi.signatures);
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
    
    private int checkInstallStat(FileData fd) {
    	fd.mInstalled = Constant.NONE;
    	fd.mVersion = "安装状态：未安装";
    	if (fd.mAi == null || fd.mPi == null || fd.mPi.packageName == null || fd.mPi.versionName == null) {
        	return fd.mInstalled;
    	}
    	for (PackageInfo pi :mPackages) {
    		if (fd.mAi.packageName.equals(pi.packageName)) {
    			if (fd.mPi.versionCode == pi.versionCode
        				&& fd.mPi.versionName.equals(pi.versionName)) {
    				fd.mInstalled = Constant.SAME;
    				fd.mVersion = "安装状态：已安装:"+pi.versionName+" : "+pi.versionCode;
    			} else {
    				fd.mInstalled = Constant.SAME;
    				fd.mVersion = "安装状态：已安装:"+pi.versionName+" : "+pi.versionCode
    						+"->"
    						+fd.mPi.versionName+" : "+fd.mPi.versionCode;
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
    		Log.i("zyw", ">>>"+new String(s.toChars()));
    		Log.i("zyw", ">>>"+new String(s.toChars()));
    		r = r + s.toCharsString();
    	}
    	return r;
    }
}
