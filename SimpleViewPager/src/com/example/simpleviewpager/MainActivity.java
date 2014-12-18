
package com.example.simpleviewpager;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.widget.DefaultPagerAdapter;
import com.widget.DotIndicator;
import com.widget.ViewHolder;
import com.widget.ViewPagerWithIndicator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class MainActivity extends Activity  implements OnPageChangeListener {
    private final String TAG = "zyw";
    private static boolean DEBUG = true;
    private ViewPagerWithIndicator mPager;
    private DefaultPagerAdapter mDefaultPagerAdapter = null;
    private DotIndicator mIndicator;
    private List<Drawable> mDrawables = null;
    
    private int[] mImageResIds = {
            R.drawable.res_1,
            R.drawable.res_2,
            R.drawable.res_3,
            R.drawable.res_4,
            R.drawable.res_5,
            R.drawable.res_6,
            //R.drawable.res_7,
            //R.drawable.res_8,
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mPager = (ViewPagerWithIndicator) findViewById(R.id.viewpager);
        mIndicator = (DotIndicator) findViewById(R.id.dotindicator);

        mPager.setDotIndicator(mIndicator);
        mDefaultPagerAdapter = new DefaultPagerAdapter(this);
        for (int i : mImageResIds) {
            mDefaultPagerAdapter.addImageByRedId(i);
        }
        //test drawable
        mDefaultPagerAdapter.addImageByDrawable(new ColorDrawable(Color.BLUE));
        mIndicator.setTotalItems(mDefaultPagerAdapter.getReallyCount());
        mPager.setAdapter(mDefaultPagerAdapter);
        mPager.setOnPageChangeListener(this);

        // 设置viewpager在第1个视图显示  
        mPager.setCurrentItem(mDefaultPagerAdapter.getFirstPagerIndex());
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        mPager.onPageScrollStateChangedEvent(arg0);
    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onPageSelected(int i) {
        // TODO Auto-generated method stub
        mPager.onPageSelectedEvent(i);
        ViewHolder holder = mDefaultPagerAdapter.getCurrentPagerViewHolder(i);
        Log.i(TAG, ">>>>>>>>> title = "+holder.title);
    }
    
    @Deprecated
    private void testDrawable() {
        mDrawables = new ArrayList<Drawable>();
        mDrawables.add(new ColorDrawable(Color.BLUE));
        mDrawables.add(new ColorDrawable(Color.RED));
        mDrawables.add(new ColorDrawable(Color.GREEN));
        mDrawables.add(new ColorDrawable(Color.YELLOW));
        mDrawables.add(new ColorDrawable(Color.GRAY));
    }
}
