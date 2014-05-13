package com.acfun;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.acfun.url.WebUrl;
import com.acfun.utils.Debug;
import com.acfun.utils.Defined;
import com.avfun.parser.Htmlparser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PageAnimation extends ListActivity implements OnClickListener {
    private static final boolean DEBUG = Debug.PAGEANIMATION_DEBUG;
    private static final String TAG = "PageAnimationActivity";
    
    private TextView mTextViewMainpageMenu = null;
    private TextView mTextViewRefreshMenu = null;
    private TextView mTextViewForwardMenu = null;
    private TextView mTextViewBackMenu = null;
    private TextView mActivityTitle = null;
    
    private ListView mListView = null;
    private ArrayList<String> mTitle = new ArrayList<String>();
    private ArrayList<String> mDescription = new ArrayList<String>();
    private ArrayList<String> mTime = new ArrayList<String>();
    private ArrayList<String> mComments = new ArrayList<String>();
    private ArrayList<String> mViews = new ArrayList<String>();
    private ArrayList<String> mHref = new ArrayList<String>();
    
    private ArrayList<String> mTmpTitle = new ArrayList<String>();
    private ArrayList<String> mTmpDescription = new ArrayList<String>();
    private ArrayList<String> mTmpTime = new ArrayList<String>();
    private ArrayList<String> mTmpComments = new ArrayList<String>();
    private ArrayList<String> mTmpViews = new ArrayList<String>();
    private ArrayList<String> mTmpHref = new ArrayList<String>();
    
    private int mCurrentPage = 1;
    private String mCurrentUrl = WebUrl.ACFUN_ANIMATION_URL;
    
    
    private final int DATA_REFRESH = 0x1000;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DATA_REFRESH:
                    mHandler.removeMessages(DATA_REFRESH);
                    
                    mTitle.clear();
                    mDescription.clear();
                    mTime.clear();
                    mViews.clear();
                    mComments.clear();
                    mHref.clear();
                    
                    mTitle.addAll(mTmpTitle);
                    mDescription.addAll(mTmpDescription);
                    mTime.addAll(mTmpTime);
                    mViews.addAll(mTmpViews);
                    mComments.addAll(mTmpComments);
                    mHref.addAll(mTmpHref);
                    
                    ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
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
        setContentView(R.layout.article);
        mActivityTitle = (TextView) findViewById(R.id.title_textview);
        mActivityTitle.setText(getString(R.string.animation)+"   第 "+mCurrentPage+" 页");
        
        mListView = getListView();
        mListView.setAdapter(new MyListAdapter(this));
        
        mTextViewMainpageMenu = (TextView) findViewById(R.id.menu_mainpage);
        mTextViewMainpageMenu.setOnClickListener(this);
        mTextViewRefreshMenu = (TextView) findViewById(R.id.menu_refresh);
        mTextViewRefreshMenu.setOnClickListener(this);
        mTextViewForwardMenu = (TextView) findViewById(R.id.menu_forward);
        mTextViewForwardMenu.setOnClickListener(this);
        mTextViewBackMenu = (TextView) findViewById(R.id.menu_back);
        mTextViewBackMenu.setOnClickListener(this);
        
        parseAnimation(mCurrentUrl);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        if (DEBUG) Log.i(TAG, ">>>>>>>>>position="+position+";id="+id);
        startActivity(new Intent(Defined.ACTION_ACTIVITY_VIEWVIDEO)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
        .putExtra(Defined.INTENT_EXTRA_VIEW_ARTICLE_URL, WebUrl.ACFUN_URL+mHref.get(position))
        .putExtra(Defined.INTENT_EXTRA_VIEW_ARTICLE_TITLE, mTitle.get(position)));
    }
    
    private void parseAnimation(final String acfunUrl) {
        // TODO Auto-generated method stub
        if (DEBUG) Log.i(TAG, ">>>>>>>>parseAnimation");
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mTmpTitle.clear();
                mTmpDescription.clear();
                mTmpTime.clear();
                mTmpViews.clear();
                mTmpComments.clear();
                mTmpHref.clear();
                Connection connection = Jsoup.connect(acfunUrl);
                try {
                    Document document = connection.get();
                    Elements elements = document.getElementsByAttributeValueStarting("class", "item-list-area");
                    for (Element element : elements) {
                        if (DEBUG) Log.i(TAG,">>>>>>>>>>>>>>element.toString="+element.toString()+"::"+element.getElementsByAttributeValueStarting("class","title").first().attr("href"));
                        mTmpTitle.add(element.getElementsByAttributeValueStarting("class","title").first().text());
                        mTmpDescription.add(element.getElementsByAttributeValueStarting("class","desc").first().text());
                        mTmpTime.add(element.getElementsByAttributeValueStarting("class","title").first().attr("title").replace("发布于 ", ""));
                        mTmpViews.add(element.getElementsByAttributeValueStarting("class","views").first().text());
                        mTmpComments.add(element.getElementsByAttributeValueStarting("class","comments").first().text());
                        mTmpHref.add(element.getElementsByAttributeValueStarting("class","title").first().attr("href"));
                    }
                    mHandler.sendEmptyMessageDelayed(DATA_REFRESH , 100);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
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
                
            case R.id.menu_refresh:
                parseAnimation(mCurrentUrl);
                break;
                
            case R.id.menu_back:
                if (mCurrentPage == 1) {
                    
                } else if (mCurrentPage == 2) {
                    mCurrentPage = 1;
                    mCurrentUrl = WebUrl.ACFUN_ANIMATION_URL;
                    parseAnimation(WebUrl.ACFUN_ANIMATION_URL);
                } else {
                    mCurrentPage--;
                    //mCurrentUrl.replace("", replacement);
                    mCurrentUrl = "http://www.acfun.tv/v/list1/index_"+mCurrentPage+".htm";
                    parseAnimation(mCurrentUrl);
                }
                mActivityTitle.setText(getString(R.string.animation)+"   第 "+mCurrentPage+" 页");
                break;
                
            case R.id.menu_forward:
                mCurrentPage++;
                //mCurrentUrl.replace("", replacement);
                mCurrentUrl = "http://www.acfun.tv/v/list1/index_"+mCurrentPage+".htm";
                parseAnimation(mCurrentUrl);
                mActivityTitle.setText(getString(R.string.animation)+"   第 "+mCurrentPage+" 页");
                break;
                
            default:
                break;
        }
    }
    
    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;

        public MyListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return mTitle.size();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;//!mStrings[position].startsWith("-");
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView title;
            TextView description;
            TextView time;
            TextView views;
            TextView comments;

            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.listitem, parent, false);
            }
            title = (TextView) convertView.findViewById(R.id.title);
            description = (TextView) convertView.findViewById(R.id.description);
            time = (TextView) convertView.findViewById(R.id.time);
            views = (TextView) convertView.findViewById(R.id.views);
            comments = (TextView) convertView.findViewById(R.id.comments);
            title.setText(mTitle.get(position));
            description.setText(mDescription.get(position));
            time.setText("日期: "+mTime.get(position));
            views.setText("浏览:"+mViews.get(position));
            comments.setText("评论: "+mComments.get(position));
            return convertView;
        }

    }
    
}
