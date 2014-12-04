
package com.example.simpleviewpager;

import java.util.ArrayList;
import java.util.List;

import com.widget.DepthPageTransformer;
import com.widget.DotIndicator;
import com.widget.DotIndicatorItem;
import com.widget.FadeTransformer;
import com.widget.ViewPagerWithIndicator;
import com.widget.ZoomOutPageTransformer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity  implements OnPageChangeListener {
    private final String TAG = "zyw";
    private static boolean DEBUG = true;
    private ViewPagerWithIndicator mPager;
    private DotIndicator mIndicator;
    
    private int[] mImageResIds = {
            R.drawable.res_1,
            R.drawable.res_2,
            R.drawable.res_3,
            R.drawable.res_4,
            R.drawable.res_5,
            R.drawable.res_6,
            R.drawable.res_7,
            R.drawable.res_8,
    };
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPagerWithIndicator) findViewById(R.id.viewpager);
        mIndicator = (DotIndicator) findViewById(R.id.dotindicator);

        mPager.setDotIndicator(mIndicator);
        mPager.setImageResIds(mImageResIds);
        mPager.initPagerAdapter();
        mPager.setOnPageChangeListener(this);
        // 设置viewpager在第二个视图显示  
        mPager.setCurrentItem(1);
        mPager.setPageTransformer(true, new FadeTransformer());
        //mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        mPager.onPageScrollStateChanged(arg0);
    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        mPager.onPageSelected(arg0);
    }


}
