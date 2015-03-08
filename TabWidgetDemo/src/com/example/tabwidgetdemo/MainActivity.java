
package com.example.tabwidgetdemo;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class MainActivity extends Activity implements OnPageChangeListener, OnTabChangeListener {
    private static final String TAG_TAB1 = "tab 1";
    private static final String TAG_TAB2 = "tab 2";
    private static final String TAG_TAB3 = "tab 3";
    private static final int TAB_HOST_ID = android.R.id.tabhost;
    private ViewPager mViewPager;
    private TabHost mTabHost;
    private ListView v1;
    private ListView v2;
    private ListView v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTabHost = (TabHost) findViewById(TAB_HOST_ID);
        mTabHost.setup();
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setOnPageChangeListener(this);

        mTabHost.setOnTabChangedListener(this);

        TabSpec tabSpec1 = mTabHost.newTabSpec(TAG_TAB1);
        tabSpec1.setIndicator(TAG_TAB1);
        tabSpec1.setContent(new MyTabContentFactoryView());
        mTabHost.addTab(tabSpec1);

        TabSpec tabSpec2 = mTabHost.newTabSpec(TAG_TAB2);
        tabSpec2.setIndicator(TAG_TAB2);
        tabSpec2.setContent(new MyTabContentFactoryView());
        mTabHost.addTab(tabSpec2);
        
        TabSpec tabSpec3 = mTabHost.newTabSpec(TAG_TAB3);
        tabSpec3.setIndicator(TAG_TAB3);
        tabSpec3.setContent(new MyTabContentFactoryView());
        mTabHost.addTab(tabSpec3);

        v1 = new ListView(this);
        v1.setAdapter

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeViewAt(0);// 删除页卡
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
                View v = new View(MainActivity.this);
                container.addView(v);// 添加页卡
                return v;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;// 官方提示这样写
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 3;
            }
        });
        
        mTabHost.setCurrentTab(2);
        
        
        
        
    }

    private void DisplayToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
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

    private class MyTabContentFactoryView implements TabHost.TabContentFactory {
        public View createTabContent(String tag) {
            final View v = new View(MainActivity.this);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetpixels) {
        // TODO Auto-generated method stub
        Log.i("zyw", ">>>>>>>onPageScrolled position="+position
                +", positionOffset="+positionOffset
                +", positionOffsetpixels="+positionOffsetpixels
                +", count="+mTabHost.getTabWidget().getChildCount());
        setTabSliderCoord(position, positionOffset, positionOffsetpixels);
    }

    private void setTabSliderCoord(int position, float positionOffset, int positionOffsetpixels) {
        // TODO Auto-generated method stub
        try {
            Class<?> aops = Class.forName("android.widget.TabHost");
            Method method = aops.getMethod("setTabCoordScale", new Class<?>[] {
                    int.class, float.class
            });
            method.invoke(mTabHost, position, positionOffset);
        } catch (Exception e) {
            Log.i("zyw", ">>>>>>>setTabSliderCoord error!!!");
        }
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        // hack focus
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
        Log.i("zyw", ">>>>>>onPageSelected, position=" + position);
    }

    @Override
    public void onTabChanged(String tabId) {
        // TODO Auto-generated method stub
        int position = mTabHost.getCurrentTab();
        Log.i("zyw", ">>>>>>>onTabChanged, position=" + position+", tabId="+tabId
                +", c="+mViewPager.getCurrentItem());
        if (position != mViewPager.getCurrentItem()) {
            mViewPager.setCurrentItem(position, true);
        }
        
    }

}
