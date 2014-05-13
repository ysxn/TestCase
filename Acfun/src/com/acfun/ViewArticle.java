package com.acfun;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.acfun.utils.Debug;
import com.acfun.utils.Defined;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ViewArticle extends Activity {
    private static final boolean DEBUG = Debug.VIEWARTICLE_DEBUG;
    private static final String TAG = "ViewArticleActivity";
    
    private TextView mActivityTitle = null;
    private TextView mContentView = null;
    
    private String mCurrentUrl = "";
    private String mContentString = "";
    
    private final int DATA_REFRESH = 0x1000;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DATA_REFRESH:
                    mHandler.removeMessages(DATA_REFRESH);
                    mContentView.setText(mContentString);
                    break;

                default:
                    break;
            }
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewarticle);
        mCurrentUrl = this.getIntent().getExtras().getString(Defined.INTENT_EXTRA_VIEW_ARTICLE_URL);
        if (DEBUG) Log.i(TAG, ">>>>>>>>mCurrentUrlString="+mCurrentUrl);
        if (mCurrentUrl == null) {
            this.finish();
        }
        mActivityTitle = (TextView) findViewById(R.id.title_textview);
        mActivityTitle.setText(this.getIntent().getExtras().getString(Defined.INTENT_EXTRA_VIEW_ARTICLE_TITLE));
        mContentView = (TextView) findViewById(R.id.article_content);
        mContentView.setMovementMethod(ScrollingMovementMethod.getInstance());
        
        parseArticleContent(mCurrentUrl);
    }
    

    private void parseArticleContent(final String acfunUrl) {
        // TODO Auto-generated method stub
        if (DEBUG) Log.i(TAG, ">>>>>>>>parseArticleContent");
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Connection connection = Jsoup.connect(acfunUrl);
                try {
                    Document document = connection.get();
                    Elements elements = document.getElementsByAttributeValueStarting("id", "area-player");
                    for (Element element : elements) {
                        if (DEBUG) Log.i(TAG,">>>>>>>>>>>>>>element.toString="+element.toString());

                    }
                    mContentString = elements.first().text();
                    mHandler.sendEmptyMessageDelayed(DATA_REFRESH , 100);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
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
        if (DEBUG) Log.i(TAG,">>>>>>>>>>>>>>>>>>>onDestroy");
    }
    
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }
}