package com.acfun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.acfun.url.WebUrl;
import com.acfun.utils.Debug;
import com.acfun.utils.Defined;
import com.avfun.parser.Htmlparser;

import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends Activity implements OnClickListener {
    private static final boolean DEBUG = Debug.HOMEPAGE_DEBUG;
    private static final String TAG = "HomepageActivity";
    
    private static Htmlparser mHtmlparser = new Htmlparser();
    
    private Button mButtonAnimation = null;
    private Button mButtonGame = null;
    private Button mButtonMusic = null;
    private Button mButtonMovie = null;
    private Button mButtonCartoon = null;
    private Button mButtonEntertainment = null;
    private Button mButtonArticle = null;
    private Button mButtonRanking = null;
    
    private TextView mTextViewMainpageMenu = null;
    private TextView mTextViewRefreshMenu = null;
    private TextView mTextViewFavoriteMenu = null;
    private TextView mTextViewAboutMenu = null;
    
    private boolean mIsNetConnected = false;
    private boolean mIsWifi = false;
    private BroadcastReceiver mNetStatusReceiver = new BroadcastReceiver() {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            checkNetStatus();
        }
    };
    private final int MEMORY_CHECK = 0x1000;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MEMORY_CHECK:
                    mHandler.removeMessages(MEMORY_CHECK);
                    checkMemory();
                    mHandler.sendEmptyMessageDelayed(MEMORY_CHECK, 60000);
                    break;

                default:
                    break;
            }
        };
    };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        TextView title = (TextView) findViewById(R.id.title_textview);
        title.setText(R.string.mainpage);
        mButtonAnimation = (Button) findViewById(R.id.animation);
        mButtonAnimation.setOnClickListener(this);
        mButtonGame = (Button) findViewById(R.id.game);
        mButtonGame.setOnClickListener(this);
        mButtonMusic = (Button) findViewById(R.id.music);
        mButtonMusic.setOnClickListener(this);
        mButtonMovie = (Button) findViewById(R.id.movie);
        mButtonMovie.setOnClickListener(this);
        mButtonCartoon = (Button) findViewById(R.id.cartoon);
        mButtonCartoon.setOnClickListener(this);
        mButtonEntertainment = (Button) findViewById(R.id.entertainment);
        mButtonEntertainment.setOnClickListener(this);
        mButtonArticle = (Button) findViewById(R.id.article);
        mButtonArticle.setOnClickListener(this);
        mButtonRanking = (Button) findViewById(R.id.ranking);
        mButtonRanking.setOnClickListener(this);
        mButtonRanking.setVisibility(View.INVISIBLE);
        
        mTextViewMainpageMenu = (TextView) findViewById(R.id.menu_mainpage);
        mTextViewMainpageMenu.setOnClickListener(this);
        mTextViewRefreshMenu = (TextView) findViewById(R.id.menu_refresh);
        mTextViewRefreshMenu.setOnClickListener(this);
        mTextViewFavoriteMenu = (TextView) findViewById(R.id.menu_favorite);
        mTextViewFavoriteMenu.setOnClickListener(this);
        mTextViewAboutMenu = (TextView) findViewById(R.id.menu_about);
        mTextViewAboutMenu.setOnClickListener(this);
        
        if (DEBUG) {
            Log.i(TAG,">>>>>>>>"+WebUrl.ACFUN_URL);
            //mHandler.sendEmptyMessageDelayed(MEMORY_CHECK, 1000);
        }
        //mHtmlparser.parseHome(HomeUrl.ACFUN_URL);
        
    }
    
    private ActivityManager mActivityManager = null;
    private int[] mPids = {-1};
    private android.os.Debug.MemoryInfo[] mInfos = null;
    private MemoryInfo mOutInfo = new MemoryInfo();
    protected void checkMemory() {
        // TODO Auto-generated method stub
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
        }
        if (mPids[0] == -1) {
            mPids[0] = android.os.Process.myPid();
        }
        mInfos = mActivityManager.getProcessMemoryInfo(mPids);
        java.lang.Process m_process = null;
        try {
            m_process = Runtime.getRuntime().exec("/system/bin/top -n 1 -m 20 -s rss");
            //final StringBuilder sbread = new StringBuilder();
            if (m_process != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(m_process.getInputStream()), 8192);
                String s = null;
                while ((s =bufferedReader.readLine()) != null) {
                    if(s.endsWith("com.acfun") || s.endsWith("Name")) Log.i(TAG,">>>>>>>>>>>"+s);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mActivityManager.getMemoryInfo(mOutInfo);
        String out = "Total used pss : "+mInfos[0].getTotalPss()+" Kb."+"  avail memory : " + mOutInfo.availMem/1024+" Kb";
        Toast.makeText(this, out, Toast.LENGTH_LONG).show();
    }

    private void registerForCheckNetStatus() {
        // TODO Auto-generated method stub
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetStatusReceiver, intentFilter);
    }

    private void checkNetStatus() {
        // TODO Auto-generated method stub
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = cm.getActiveNetworkInfo();
        if (activeInfo == null) {
            mIsNetConnected = false;
            mIsWifi = false;
            Toast.makeText(this, R.string.no_connect, Toast.LENGTH_LONG).show();
            return;
        }
        mIsNetConnected = activeInfo.isConnected();
        mIsWifi = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
        if (!mIsNetConnected) {
            Toast.makeText(this, R.string.no_connect, Toast.LENGTH_LONG).show();
        } else if (mIsNetConnected && !mIsWifi) {
            Toast.makeText(this, R.string.no_wifi, Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, R.string.is_wifi, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        checkNetStatus();
        //registerForCheckNetStatus();
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //unregisterReceiver(mNetStatusReceiver);
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (DEBUG) Log.i(TAG,">>>>>>>>>>>>>>>>>>>onDestroy");
        mHandler.removeMessages(MEMORY_CHECK);
        //android.os.Process.killProcess(Process.myPid());
    }
    
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this)
        .setTitle(R.string.exit)
        .setPositiveButton(android.R.string.ok, 
            new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    workBeforefinish();
                    finish();
                }
            })
        .setNegativeButton(android.R.string.cancel, 
            new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    
                }
            })
        .show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
    }
    
    protected void workBeforefinish() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(MEMORY_CHECK);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.menu_about:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_ABOUT)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.article:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGEARTICLE)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            case R.id.entertainment:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGEENTERTAINMENT)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            case R.id.animation:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGEANIMATION)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            case R.id.movie:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGEMOVIE)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            case R.id.music:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGEMUSIC)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            case R.id.game:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGEGAME)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            case R.id.cartoon:
                startActivity(new Intent(Defined.ACTION_ACTIVITY_PAGECARTOON)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
                
            default:
                break;
        }
    }
}