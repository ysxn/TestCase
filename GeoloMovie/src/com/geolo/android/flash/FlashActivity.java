package com.geolo.android.flash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.geolo.android.FileBrowser;
import com.geolo.android.R;

public class FlashActivity extends Activity{

	private WebView mWebView;
	private Button playButton,pauseButton,rewindButton,exitButton,fileButton;
	private ProgressBar mProgressBarHorizontal;
	private final static int PROGRESSBARSIZE = 0x0000;
	private final static int FLASH_START = 0x0001;
	private String flashName ;
	private boolean stopThread = false;
	private ProgressDialog mProgressDialog;
	private int mSize = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.main);

		getWindow().setFeatureInt(
                Window.FEATURE_INDETERMINATE_PROGRESS,
                Window.PROGRESS_INDETERMINATE_ON);
        getWindow().setFeatureInt(
                Window.FEATURE_INDETERMINATE_PROGRESS,
                Window.PROGRESS_VISIBILITY_ON);
        
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Flash动画正在加载,请稍等......");
		mProgressDialog.show();

		Intent intent = this.getIntent();
		String fileName = intent.getStringExtra("fileName");
		if(fileName != null && !fileName.equals("")){
			flashName = "file://"+fileName;
			//flashName = "javascript:setFlashPath(flashName)";
		}else{
			flashName = "file:///android_asset/sample/flash.swf";
		}
		Log.d(this.getClass().getName(), flashName);		

		mWebView = (WebView)findViewById(R.id.webView01); 
		mProgressBarHorizontal = (ProgressBar)findViewById(R.id.progress_horizontal);
		//this.setProgress(mProgressBarHorizontal.getProgress() * 100);
		this.setProgress(0);
		//this.setSecondaryProgress(mProgressBarHorizontal.getSecondaryProgress() * 100);
		playButton = (Button)findViewById(R.id.playButton);
		pauseButton = (Button)findViewById(R.id.pauseButton);
		rewindButton = (Button)findViewById(R.id.rewindButton);
		exitButton = (Button)findViewById(R.id.exitButton);
		fileButton = (Button)findViewById(R.id.fileButton);
		playButton.setOnClickListener(buttonListener);
		pauseButton.setOnClickListener(buttonListener);
		rewindButton.setOnClickListener(buttonListener);
		exitButton.setOnClickListener(buttonListener);
		fileButton.setOnClickListener(buttonListener);
		mWebView.getSettings().setJavaScriptEnabled(true);  
		//mWebView.getSettings().setPluginsEnabled(true);
		mWebView.getSettings().setPluginState(PluginState.ON);
		mWebView.setWebChromeClient(new WebChromeClient()); 
		mWebView.addJavascriptInterface(new CallJava(), "CallJava");
		mWebView.loadUrl("file:///android_asset/sample/index.html"); 
		//mWebView.loadUrl("javascript:setFlashPath('"+flashName+"')"); 
		startThread();
	}

	Button.OnClickListener buttonListener = new Button.OnClickListener() {	
		public void onClick(View v) {
			int buttonID = v.getId();
			switch (buttonID) {
			case R.id.playButton:
				mWebView.loadUrl("javascript:Play()");
				//showFlashProgress(5);
				mWebView.loadUrl("javascript:showcount()");
				break;
			case R.id.pauseButton:
				mWebView.loadUrl("javascript:Pause()");
				break;
			case R.id.rewindButton:
				//mWebView.loadUrl(flashName);
				try {
					mWebView.loadUrl("about:blank");
					mWebView.loadUrl("file:///android_asset/sample/index.html"); 
					Thread.sleep(1000);
					mWebView.loadUrl("javascript:setFlashPath('"+flashName+"')"); 
				} catch (InterruptedException e) {
					Log.e(this.getClass().getName(), "Flash Rewind error: ", e);
				}
				break;
			case R.id.fileButton:
				Intent intent = new Intent();
				intent.setClass(FlashActivity.this, FileBrowser.class);
				startActivity(intent);
				stopThread = true;
				FlashActivity.this.finish();
				break;
			case R.id.exitButton:
				quitDialog();
				break;
			default:
				break;
			}
		}
	};

	public void showFlashProgress(float progressSize){
		int size = (int)progressSize;
		//Toast.makeText(this, size+"", Toast.LENGTH_SHORT).show();
		mSize = size;
		mProgressBarHorizontal.setProgress(size);
	}

	private void quitDialog(){
		new AlertDialog.Builder(this)
		.setMessage("没胆就不要退出")
		.setPositiveButton("比你有胆", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				stopThread = true;
				FlashActivity.this.finish();
			}
		})
		.setNegativeButton("怕你了", null)
		.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			quitDialog();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onPause(){
		super.onPause();
		mWebView.pauseTimers();
		if(isFinishing()){
			mWebView.loadUrl("about:blank");
			setContentView(new FrameLayout(this));
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		mWebView.resumeTimers();
	}

	private final class CallJava{
		public void consoleFlashProgress(float  progressSize){
			showFlashProgress(progressSize);
		}
	}

	private void startThread(){
		//通过线程来改变ProgressBar的值
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(500);
					Message message = new Message();
					message.what = FlashActivity.FLASH_START;
					FlashActivity.this.myMessageHandler.sendMessage(message);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
				}


				while(!stopThread && !Thread.currentThread().isInterrupted()){
					try {
						Thread.sleep(500);
						Message message2 = new Message();
						message2.what = FlashActivity.PROGRESSBARSIZE;
						FlashActivity.this.myMessageHandler.sendMessage(message2);
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}).start();
	}

	Handler myMessageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FlashActivity.PROGRESSBARSIZE:
			    Log.d(this.getClass().getName(),"refresh progressbar,"+"Start flash : "+flashName+",mSize="+mSize);
				mWebView.loadUrl("javascript:showcount()");
				break;
			case FlashActivity.FLASH_START:
				mWebView.loadUrl("javascript:setFlashPath('"+flashName+"')"); 
				Log.d(this.getClass().getName(),"Start flash : "+flashName+",mSize="+mSize);
				mProgressDialog.dismiss();
				FlashActivity.this.getWindow().setFeatureInt(
		                Window.FEATURE_INDETERMINATE_PROGRESS,
		                Window.PROGRESS_INDETERMINATE_ON);
				FlashActivity.this.getWindow().setFeatureInt(
	                    Window.FEATURE_INDETERMINATE_PROGRESS,
	                    Window.PROGRESS_VISIBILITY_OFF);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
}