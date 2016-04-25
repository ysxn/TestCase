package com.example.test_webview_demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.HttpAuthHandler;
import android.webkit.WebIconDatabase;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.test_webview_demo.ImageResultActivity;
import com.example.test_webview_demo.R;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.FindListener;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.WebViewTransport;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebHistoryItem;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebViewDatabase;

public class X5WebView  extends WebView{
	
	
	///////////////////////////////////////////////////
	//add private object
	
	
	//////////////////////////////////////////////////
	//file chooser result code
	public static final int FILE_CHOOSER=0;
	private String resourceUrl="";
	private WebView smallWebView;
	private static boolean isSmallWebViewDisplayed = false;
	private Map<String,Object> mJsBridges;

	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		
		
		WebStorage webStorage = WebStorage.getInstance();
		// TODO Auto-generated constructor stub
		WebSettings webSetting=this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		this.getView().setClickable(true);
		this.getView().setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		

		//WebClient settings
		this.setWebViewClient(new WebViewClient(){
			
			/**
			 * 防止加载网页时调起系统浏览器
			 */
			public boolean shouldOverrideUrlLoading(WebView view,String url){
					view.loadUrl(url);
					return true;	
//				return false;
				}
			public void onReceivedHttpAuthRequest(WebView webview, com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host, String realm) {
				boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
				Log.i("yuanhaizhou", "useHttpAuthUsernamePassword is"+flag);
				Log.i("yuanhaizhou", "HttpAuth host is" +host);
				Log.i("yuanhaizhou", "HttpAuth realm is" + realm);
				
				}
			@Override
			public void onDetectedBlankScreen(String arg0, int arg1) {
				// TODO Auto-generated method stub
				super.onDetectedBlankScreen(arg0, arg1);
			}
			
			
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView arg0,
					String arg1) {
				// TODO Auto-generated method stub
				return super.shouldInterceptRequest(arg0, arg1);
			}
			
			
		});
		
		
		//webchromeclient settings
		this.setWebChromeClient(new WebChromeClient(){
			View myVideoView;
			View myNormalView;
			CustomViewCallback callback;
			
			
			
			///////////////////////////////////////////////////////////
			//
			/**
			 * 全屏播放配置
			 */
			@Override
			public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
				// TODO Auto-generated method stub
				
					FrameLayout normalView=(FrameLayout) ((Activity)getContext()).findViewById(R.id.web_filechooser);
					ViewGroup viewGroup=(ViewGroup) normalView.getParent();
					viewGroup.removeView(normalView);
					viewGroup.addView(view);
					myVideoView=view;
					myNormalView=normalView;
					callback=customViewCallback;
			}
			@Override
			public void onHideCustomView() {
				// TODO Auto-generated method stub
				if(callback!=null){
					callback.onCustomViewHidden();
					callback=null;
				}
				if(myVideoView!=null){
					ViewGroup viewGroup=(ViewGroup) myVideoView.getParent();
					viewGroup.removeView(myVideoView);
					viewGroup.addView(myNormalView);
				}
			}
			@Override
			public void onProgressChanged(WebView arg0, int arg1) {
				// TODO Auto-generated method stub
				super.onProgressChanged(arg0, arg1);
			}
			@Override
			public void openFileChooser(ValueCallback<Uri> uploadFile,
					String acceptType, String captureType) {
				Log.i("ChromeClient","openFileChooser enter");
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				((Activity)(X5WebView.this.getContext())).startActivityForResult(
						Intent.createChooser(
								i,"choose files"),X5WebView.FILE_CHOOSER);
				super.openFileChooser(uploadFile,acceptType,captureType);
			}
			
			
			@Override
			public void onShowCustomView(View arg0, int arg1,
					CustomViewCallback arg2) {
				// TODO Auto-generated method stub
				CustomViewCallback callback=new CustomViewCallback() {
					
					@Override
					public void onCustomViewHidden() {
						// TODO Auto-generated method stub
						Log.i("yuanhaizhou", "video view hidden");
					}
				};
				super.onShowCustomView(arg0, arg1, arg2);
			}
			
			/**
			 * webview 的窗口转移
			 */
			@Override
			public boolean onCreateWindow(WebView arg0, boolean arg1,
					boolean arg2, Message msg) {
				// TODO Auto-generated method stub
				Log.i("yuanhaihzou", "onCreateWindow happend!!");
				if(X5WebView.isSmallWebViewDisplayed==true){
					
					WebView.WebViewTransport webViewTransport =(WebView.WebViewTransport) msg.obj;
					WebView webView = new WebView(X5WebView.this.getContext()){
					
						protected void onDraw(Canvas canvas) {
							super.onDraw(canvas);
							Paint paint=new Paint();
							paint.setColor(Color.GREEN);
							paint.setTextSize(15);
							canvas.drawText("新建窗口", 10, 10, paint);
						};
					};
					webView.setWebViewClient(new WebViewClient(){
						public boolean shouldOverrideUrlLoading(WebView arg0, String arg1) {
							arg0.loadUrl(arg1);
							return true;
						};
					});
					FrameLayout.LayoutParams lp= new LayoutParams(400, 600);
					lp.gravity = Gravity.CENTER_HORIZONTAL |Gravity.CENTER_VERTICAL;
					X5WebView.this.addView(webView, lp);
					
					webViewTransport.setWebView(webView);
					msg.sendToTarget();	
				}
				return true;
			}
			
			@Override
			public boolean onJsAlert(WebView arg0, String arg1, String arg2,
					JsResult arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(getContext());
				builder.setTitle("X5内核");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.show();
				arg3.confirm();
//				return true;
				Log.i("yuanhaizhou", "setX5webview = null");
				return super.onJsAlert(null,"www.baidu.com","aa",arg3);
			}
			
			/**
			 *对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
			 */
			@Override
			public boolean onJsPrompt(WebView arg0, String arg1, String arg2,
					String arg3, JsPromptResult arg4) {
				// TODO Auto-generated method stub
				//在这里可以判定js传过来的数据，用于调起android native 方法
				if(X5WebView.this.isMsgPrompt(arg1)){
					if(X5WebView.this.onJsPrompt(arg2, arg3)){
						return true;
					}else{
						return false;
					}
				}
				return super.onJsPrompt(arg0, arg1, arg2, arg3, arg4);
			}
			
			@Override
			public void onReceivedTitle(WebView arg0, final String arg1) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(arg0, arg1);
				Log.i("yuanhaizhou", "webpage title is "+ arg1);
				
			}
		});
		
		
	}
				
		
		
	
	
	
	
	/////////////////////////////////////////////////////////////////////
	//绘制webview的标记
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime)
	{
		boolean ret = super.drawChild(canvas, child, drawingTime);
		canvas.save();
		Paint paint = new Paint();
		paint.setColor(0x7fff0000);
		paint.setTextSize(24.f);
		paint.setAntiAlias(true);
		if(getX5WebViewExtension() != null){
			//Log.d(TAG, "drawChild--X5 Core");
			canvas.drawText(this.getContext().getPackageName()+"-pid:" +android.os.Process.myPid(), 10, 50, paint);
			canvas.drawText("X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10, 100, paint);
		}
		else{
			//Log.d(TAG, "drawChild--Sys Core");
			canvas.drawText(this.getContext().getPackageName()+"-pid:" +android.os.Process.myPid(), 10, 50, paint);
			canvas.drawText("Sys Core", 10, 100, paint);
		}
		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
		canvas.drawText(Build.MODEL, 10, 200, paint);
		canvas.restore();
		return ret;
	}
	
	

	
	public X5WebView(Context arg0){
		super(arg0);
		setBackgroundColor(85621);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev);
	}
	
	
	public static void setSmallWebViewEnabled(boolean enabled){
		isSmallWebViewDisplayed=enabled;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
//		Log.i("yuanhaizhou","webview scroll y is" +this.getView().getScrollY());
//		Log.i("yuanhaizhou","real webview scroll y is" + this.getScrollY());
//		Log.i("yuanhaizhou","webview webscroll y is" + this.getWebScrollY());
		Log.i("yuanhaizhou","webview webscroll y is" + this.getWebScrollY());
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
	public void addJavascriptBridge(SecurityJsBridgeBundle jsBridgeBundle){
		if(this.mJsBridges == null){
			this.mJsBridges = new HashMap<String,Object>(5);
		}
		
		if(jsBridgeBundle!=null){
			String tag = SecurityJsBridgeBundle.BLOCK + jsBridgeBundle.getJsBlockName() 
					+"-"
					+SecurityJsBridgeBundle.METHOD + jsBridgeBundle.getMethodName();
			this.mJsBridges.put(tag, jsBridgeBundle);
		}
	}
	
	/**
	 * 当webchromeClient收到 web的prompt请求后进行拦截判断，用于调起本地android方法
	 * @param methodName 方法名称
	 * @param blockName 区块名称
	 * @return true ：调用成功 ； false ：调用失败
	 */
	private boolean onJsPrompt(String methodName,String blockName){
		String tag = SecurityJsBridgeBundle.BLOCK + blockName
				+"-"
				+SecurityJsBridgeBundle.METHOD + methodName;
		
		if(this.mJsBridges!=null && this.mJsBridges.containsKey(tag)){
			((SecurityJsBridgeBundle)this.mJsBridges.get(tag)).onCallMethod();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判定当前的prompt消息是否为用于调用native方法的消息
	 * @param msg 消息名称
	 * @return true 属于prompt消息方法的调用
	 */
	private boolean isMsgPrompt(String msg){
		if(msg!=null&& msg.startsWith(SecurityJsBridgeBundle.PROMPT_START_OFFSET)){
			return true;
		}else{
			return false;
		}
	}

	
	
	
	
	
	


	
	

	
	

}
