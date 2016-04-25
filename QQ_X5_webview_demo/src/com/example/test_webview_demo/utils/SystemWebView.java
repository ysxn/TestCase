package com.example.test_webview_demo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class SystemWebView extends WebView{
	
	//使用system webview 来验证 X5webview的支持情况


	public SystemWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	public SystemWebView(Context context, AttributeSet attr){
		super(context, attr);
		
		// TODO Auto-generated constructor stub
				WebSettings webSetting=this.getSettings();
				webSetting.setJavaScriptEnabled(true);
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
				webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
				
				
			
				this.setClickable(true);
				this.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				
				
				this.setWebChromeClient(new WebChromeClient(){
					
					@Override
					public boolean onCreateWindow(WebView view,
							boolean isDialog, boolean isUserGesture,
							Message resultMsg) {
						// TODO Auto-generated method stub
						return true;
					}
					
					@Override
					public boolean onJsPrompt(WebView view, String url,
							String message, String defaultValue,
							JsPromptResult result) {
						// TODO Auto-generated method stub
						return super.onJsPrompt(view, url, message, defaultValue, result);
					}
				
				});
				
				this.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view,
						String url) {
					// TODO Auto-generated method stub
					view.loadUrl(url);
					return true;
				}
				});
				
				
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
//		Log.i("yuanhaizhou","real webview scroll y is" + this.getScrollY());
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
		
}
