package com.acfun;

import com.acfun.utils.Defined;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	
	private WebView webview;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_layout);
		path = getIntent().getStringExtra(Defined.INTENT_EXTRA_VIEW_ARTICLE_URL);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setSupportZoom(true);
		
		webview.setWebChromeClient(new WebChromeClient());
		webview.getSettings().setPluginState(PluginState.ON);
		webview.loadUrl(path);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webview.loadUrl("about:blank");
	}
}
