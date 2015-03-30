package com.codezyw.backuprestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class LogcatService extends Service implements Handler.Callback {

	private static final String TAG = "zyw";
	public static final boolean DEBUG = true;

	private static final int MSG_STOP_SERVICE_BY_TIMEOUT = 110;
	private static final long DELAY_STOP_SERVICE_BY_TIMEOUT = 10000L;// 10 s

	private boolean isServiceRunning = false;
	private boolean mLogRunning = false;
	private Handler mServiceMainHandler;
	// Job queen design

	public static final String CMD = "cmd";
	public static final String CMD_START = "start_logcat";
	public static final String CMD_STOP = "stop_logcat";

	public static final int MSG_START_LOGCAT = 0x1000;

	private HandlerThread mHandlerThread;
	private Handler mWorkHandler;
	private Context mContext = LogcatService.this;
	private ProgressDialog mProgressDialog = null;

	SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");// 设置格式
	private Process process = null; 

	@Override
	public void onCreate() {
		if (DEBUG)
			Log.v(TAG, "service onCreate");
		super.onCreate();
		mServiceMainHandler = new Handler(this);
		mHandlerThread = new HandlerThread("handler_thread");
		mHandlerThread.start();
		mWorkHandler = new Handler(mHandlerThread.getLooper()) {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_START_LOGCAT: {
//					if (process != null) {
//						return;
//					}
					String path = android.os.Environment
							.getExternalStorageDirectory().getAbsolutePath() + "/autolog";
					File dir = new File(path);
					if (!dir.exists()) {
						dir.mkdirs();
					}
//					List<String> commandList = new ArrayList<String>();  
//			        commandList.add("logcat");  
//			        commandList.add("-f");  
//			        commandList.add(path+File.separator+"logcat_"+mFormat.format(new Date())+".xt");  
//			        commandList.add("-v");  
//			        commandList.add("time");  
//			  
//			        try {
			        	path = path+File.separator+"logcat_"+mFormat.format(new Date())+".ttxt";
//			        	Log.i(TAG, cmd);
//			            process = Runtime.getRuntime().exec("logcat -f  "+cmd);
////			                    commandList.toArray(new String[commandList.size()]));  
//			            process.waitFor();
//			            Log.i(TAG, "finish");
//			        } catch (Exception e) {
//			        	e.printStackTrace();
//			            Log.e(TAG,e.getMessage(), e);  
//			        }
			        
			        final StringBuilder log = new StringBuilder();
			        try {        
			            ArrayList<String> commandLine = new ArrayList<String>();
			            commandLine.add("logcat");
			            commandLine.add("-d");
//			            ArrayList<String> arguments = ((params != null) && (params.length > 0)) ? params[0] : null;
//			            if (null != arguments){
//			                commandLine.addAll(arguments);
//			            }

			            Process process = Runtime.getRuntime().exec(commandLine.toArray(new String[commandLine.size()]));
			            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			            String line;
			            while ((line = bufferedReader.readLine()) != null){ 
			                log.append(line);
			                log.append("\n"); 
			            }
			        } 
			        catch (IOException e){
			                //
			        } 

//			        return log;
					File outFile = new File(path);
					try {
						outFile.createNewFile();
						FileOutputStream out = new FileOutputStream(outFile);
						out.write(log.toString().getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					
//					FileOutputStream out = null;
//					BufferedReader in = null;
//					Process logcatProc = null;
//					try {
//						outFile.createNewFile();
//						out = new FileOutputStream(outFile);
//						logcatProc = Runtime.getRuntime()
//								.exec("logcat -v time");
//						in = new BufferedReader(new InputStreamReader(
//								logcatProc.getInputStream()), 1024);
//						String line = null;
//						mLogRunning = true;
//						while (mLogRunning
//								&& (line = in.readLine()) != null) {
//							if (!mLogRunning) {
//								break;
//							}
//							if (line.length() == 0) {
//								continue;
//							}
//							if (out != null /* && line.contains(mPID) */) {
//								out.write((line + "\n").getBytes());
//							}
//						}

//					} catch (IOException e) {
//						e.printStackTrace();
//					} finally {
//						Log.i(TAG, "finish logcat.");
//						if (logcatProc != null) {
//							logcatProc.destroy();
//							logcatProc = null;
//						}
//						if (in != null) {
//							try {
//								in.close();
//								in = null;
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//						if (out != null) {
//							try {
//								out.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//							out = null;
//						}

//					}
					break;
				}
				default:
					break;
				}
			}
		};
		Notification notification = new Notification();
		this.startForeground(0x200, notification);
		isServiceRunning = true;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String cmd = intent.getStringExtra(CMD);
		if (DEBUG)
			Log.v(TAG, "onStartCommand CMD=" + cmd + " flags=" + flags
					+ " , startId=" + startId);

		if (cmd != null && CMD_START.equals(cmd)) {
			Message msg = mWorkHandler.obtainMessage(MSG_START_LOGCAT);
			msg.sendToTarget();
		} else if (cmd != null && CMD_STOP.equals(cmd)) {
			mLogRunning = false;
			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return Service.START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		if (DEBUG)
			Log.v(TAG, "service onDestroy");
		isServiceRunning = false;
		mLogRunning = false;
		mHandlerThread.quit();
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

	private void showDialog(String title, String message) {
		AlertDialog ad = new AlertDialog.Builder(mContext)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).create();
		// android.permission.SYSTEM_ALERT_WINDOW
		// 允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
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
		// android.permission.SYSTEM_ALERT_WINDOW
		// 允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
		mProgressDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
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
