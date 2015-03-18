package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private boolean DEBUG = true;

	private String TAG = "zyw";

	private PackageManager mPm;

	private List<ApplicationInfo> mApps;

	private ProgressDialog mProgressDialog;

	private Button mBackUp;

	private Button mSeeapk;

	private Button mFiles;

	private int mTotal = 0;

	private final int REQUEST_UPDATE_DATA = 0x123;

	private final int REQUEST_DISMISS_PROGRESS = 0x122;

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
				if (mProgressDialog != null && mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBackUp = (Button) findViewById(R.id.button_backup);
		mSeeapk = (Button) findViewById(R.id.button_seeapk);
		mFiles = (Button) findViewById(R.id.button_files);

		mPm = this.getPackageManager();

		mProgressDialog = new ProgressDialog(MainActivity.this);
		mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
		mProgressDialog.setTitle("正在备份中...");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setMax(0);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setProgress(0);

		mBackUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mProgressDialog.show();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						initApp();
					}
				}).start();
			}
		});

		mSeeapk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ApkBrowser.class);
				startActivity(i);
			}
		});

		mFiles.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, FileBrowser.class);
				startActivity(i);
			}
		});
	}

	private void initApp() {
		// Retrieve all known applications.
		mApps = mPm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_DISABLED_COMPONENTS);
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
			if (app.sourceDir != null && app.sourceDir.startsWith("/data/app/")) {
				progress++;
				String label = (String) app.loadLabel(mPm);
				if (DEBUG) {
					Log.i(TAG, ">>>>>>>>>>app=" + app.sourceDir);
					Log.i(TAG, ">>>>>>>>>>app=" + label);
					Log.i(TAG, ">>>>>>>>>>app=" + app.packageName);
				}
				mHandler.sendMessage(mHandler.obtainMessage(
						REQUEST_UPDATE_DATA, progress, 0, label));
				try {
					if (backupApp(app.packageName, app.sourceDir, label)) {
						
					} else {
						Toast.makeText(MainActivity.this, "备份失败:"+label, Toast.LENGTH_SHORT).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Log.i(TAG, ">>>>>>>>>>finish!!!!!");
		mHandler.sendMessage(mHandler.obtainMessage(REQUEST_DISMISS_PROGRESS));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		super.onDestroy();
	}

	private boolean backupApp(String packageName, String sourceDir, String label)
			throws IOException {
		if (DEBUG) {
			Log.i(TAG, ">>>>>>>>>>packageName=" + packageName);
		}
		File in = new File(sourceDir);// File("/data/app/" + packageName +
										// ".apk");
		if (in == null || !in.exists() || !in.canRead()) {
			Log.i(TAG, ">>>>>>>>>>packageName : " + packageName
					+ " file can not save.");
			return false;
		}
		File dir = new File(Environment.getExternalStorageDirectory()
				+ "/BackupApp");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File out = new File(Environment.getExternalStorageDirectory()
				+ "/BackupApp/" + label + ".apk");

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
		boolean ok = false;
		if (inMd5 != null && outMd5 != null && inMd5.equals(outMd5)) {
			ok = true;
		}
		Log.i(TAG, ">>>>>>>>>>备份结果 " + label + ": "+ok);
		return ok;
	}
}
