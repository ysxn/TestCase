package com.acfun;

import com.acfun.utils.Debug;
import com.acfun.utils.Defined;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class About extends Activity implements OnClickListener {
    private static final boolean DEBUG = Debug.ABOUT_DEBUG;
    private static final String TAG = "AboutActivity";
    
    private TextView mTextViewMainpageMenu = null;
    private TextView mTextViewRefreshMenu = null;
    private TextView mTextViewFavoriteMenu = null;
    private TextView mTextViewAboutMenu = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView title = (TextView) findViewById(R.id.title_textview);
        title.setText(R.string.about);
        
        mTextViewMainpageMenu = (TextView) findViewById(R.id.menu_mainpage);
        mTextViewMainpageMenu.setOnClickListener(this);
        mTextViewRefreshMenu = (TextView) findViewById(R.id.menu_refresh);
        mTextViewRefreshMenu.setOnClickListener(this);
        mTextViewFavoriteMenu = (TextView) findViewById(R.id.menu_favorite);
        mTextViewFavoriteMenu.setOnClickListener(this);
        mTextViewAboutMenu = (TextView) findViewById(R.id.menu_about);
        mTextViewAboutMenu.setOnClickListener(this);
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (DEBUG) Log.i(TAG,">>>>>>>>>>>>>>>>>>>onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.menu_mainpage:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_MAINPAGE)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;

            default:
                break;
        }
    }
}