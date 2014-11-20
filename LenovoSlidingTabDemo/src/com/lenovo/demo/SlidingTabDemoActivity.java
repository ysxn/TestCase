
package com.lenovo.demo;

import android.os.Bundle;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.lenovo.component.slidingtab.*;
import com.lenovo.internal.R;

public class SlidingTabDemoActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private String mDemoTitles[] = new String[]{
            "类别", "首页", "热门付费", "热门免费"
            , "创收最高", "畅销付费新品", "热门免费新品", "上升最快"
            , "热门游戏", "热门音乐", "热门网络", "热门招聘"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidingtab_activity_sample);

        mViewPager = (ViewPager) findViewById(R.id.slidingtab_viewpager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingtab_sliding_tabs);
        mViewPager.setAdapter(new DemoViewPagerAdapter(getSupportFragmentManager(), mDemoTitles));
        
        mSlidingTabLayout.setTabViewTextViewColorResId(R.color.slidingtab_tab_textview_color);
        mSlidingTabLayout.setViewPager(mViewPager);
        //mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
        //    @Override
        //    public int getIndicatorColor(int position) {
        //        return Color.BLUE;
        //    }
        //});
    }
    
    private class DemoViewPagerAdapter extends FragmentPagerAdapter {

        private String demoTitles[] ;

        public DemoViewPagerAdapter(FragmentManager fragmentManager, String[] titles2) {
            super(fragmentManager);
            demoTitles=titles2;
        }

        @Override
        public Fragment getItem(int position) {
            return DemoFragment.newInstance(position);
        }

        public CharSequence getPageTitle(int position) {
            return demoTitles[position];
        }

        @Override
        public int getCount() {
            return demoTitles != null ? demoTitles.length : 0;
        }

    }

}
