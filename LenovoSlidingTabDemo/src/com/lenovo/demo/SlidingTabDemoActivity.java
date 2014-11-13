
package com.lenovo.demo;

import android.os.Bundle;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.lenovo.component.slidingtab.*;

public class SlidingTabDemoActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private String mDemoTitles[] = new String[]{
            "Sample Tab 111", "Sample Tab 2", "Sample Tab 333333", "Sample Tab 4"
            , "Sample Tab 5555", "Sample Tab 6", "Sample Tab 77777", "Sample Tab 8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mViewPager.setAdapter(new DemoViewPagerAdapter(getSupportFragmentManager(), mDemoTitles));
        
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.BLUE;
            }
        });
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
