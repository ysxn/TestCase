
package com.example.webview;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codezyw.common.FileHelper;

public class MainActivity extends Activity {
    private LinearLayout mRootView;
    private WebView mWebView;
    private Button mGo;
    private EditText mInput;
    private Context mContext;
    private final static String IMAGE_MIME_TYPE = "image/*";
    private final static String VIDEO_MIME_TYPE = "video/*";
    private final static String AUDIO_MIME_TYPE = "audio/*";
    public final static int FILECHOOSER_RESULTCODE = 0x998;
    public ValueCallback<Uri> mUploadMessage;
    public Uri mCapturedMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = new LinearLayout(this);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout tmp = new LinearLayout(this);
        tmp.setOrientation(LinearLayout.HORIZONTAL);
        mWebView = new WebView(this);
        mGo = new Button(this);
        mGo.setText("GO");
        mInput = new EditText(this);
        mInput.setText("http://www.baidu.com");

        tmp.addView(mInput, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        tmp.addView(mGo, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mRootView.addView(tmp, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mRootView.addView(mWebView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(mRootView);
        mContext = this;
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String originalUrl) {
                Log.i("zyw", "originalUrl=" + originalUrl);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.cancel();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String mtTitle) {
                super.onReceivedTitle(view, mtTitle);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, android.webkit.GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }

            // For Android 3.0-
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, IMAGE_MIME_TYPE);

            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                openFileChooser(uploadMsg, IMAGE_MIME_TYPE, "*");
            }

            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                android.util.Log.i("GUH", "openFileChooser uploadMsg=" + uploadMsg + " acceptType=" + acceptType + " capture=" + capture);
                if (mUploadMessage != null) {
                    return;
                }
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // if (IMAGE_MIME_TYPE.equals(acceptType) &&
                // ("*".equals(capture) || "camera".equals(capture))) {
                if (IMAGE_MIME_TYPE.equals(acceptType)) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mCapturedMedia = createTempFileContentUri(".jpg");
                    android.util.Log.i("GUH", "openFileChooser mCapturedMedia=" + mCapturedMedia);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedMedia);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    ((Activity) mContext).startActivityForResult(intent, FILECHOOSER_RESULTCODE);
                } else if (VIDEO_MIME_TYPE.equals(acceptType)) {
                    intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    ((Activity) mContext).startActivityForResult(intent, FILECHOOSER_RESULTCODE);
                } else if (AUDIO_MIME_TYPE.equals(acceptType)) {
                    intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    ((Activity) mContext).startActivityForResult(intent, FILECHOOSER_RESULTCODE);
                } else {
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
                }
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setDomStorageEnabled(true);
        mGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mInput != null) {
                    String url = mInput.getText().toString().trim();
                    mWebView.loadUrl(url);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private Uri createTempFileContentUri(String suffix) {
        try {
            File mediaPath = new File(FileHelper.getSdcardRootDir(mContext, "download"), "captured_media");
            android.util.Log.i("GUH", "createTempFileContentUri=" + mediaPath.getAbsolutePath());
            if (!mediaPath.exists() && !mediaPath.mkdir()) {
                mediaPath = new File("/sdcard", "captured_media");
                if (!mediaPath.exists() && !mediaPath.mkdir()) {
                    return null;
                }
            }
            File mediaFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), suffix, mediaPath);
            return Uri.fromFile(mediaFile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
