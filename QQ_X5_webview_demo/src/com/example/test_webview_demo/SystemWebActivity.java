package com.example.test_webview_demo;

import java.util.Properties;

import com.example.test_webview_demo.utils.SystemWebView;
import com.example.test_webview_demo.utils.X5WebView;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.QbSdk;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.ViewConfiguration;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class SystemWebActivity extends Activity{
	
	

	
	private SystemWebView webView;
//	private static final String mHomeUrl = "http://app.html5.qq.com/navi/index";
	private static final String mHomeUrl = "file:///android_asset/webpage/SysJavaToJs.html";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen_sys_web);
		webView=(SystemWebView) findViewById(R.id.full_sys_web_webview);
		webView.loadUrl(mHomeUrl);
//		webView.loadUrl("http://lol.qq.com/m/act/a20150319lolapp/exp_3.htm?iVideoId=14642");
		webView.addJavascriptInterface(new Object(){
			
			@JavascriptInterface
			public void alertMsg(String msg){
				Toast.makeText(SystemWebActivity.this,msg, Toast.LENGTH_SHORT).show();
				
			}
		
		},"Android");
		
	}
	
	

}
