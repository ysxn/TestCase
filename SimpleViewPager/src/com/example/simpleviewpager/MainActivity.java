
package com.example.simpleviewpager;

import java.util.ArrayList;
import java.util.List;

import com.widget.DepthPageTransformer;
import com.widget.DotIndicator;
import com.widget.FadeTransformer;
import com.widget.ViewPagerWithIndicator;
import com.widget.ZoomOutPageTransformer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity  implements OnPageChangeListener {
    private final String TAG = "zyw";
    private static boolean DEBUG = true;
    private ViewPagerWithIndicator mPager;
    private DotIndicator mIndicator;
    private List<Drawable> mDrawables = null;
    private Button mAddPager = null;
    private Button mRemovePager = null;
    
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

        mDrawables = new ArrayList<Drawable>();
        mPager = (ViewPagerWithIndicator) findViewById(R.id.viewpager);
        mIndicator = (DotIndicator) findViewById(R.id.dotindicator);

        //mPager.setDotIndicatorAndResIds(mIndicator, mImageResIds);
        mPager.setDotIndicator(mIndicator);
        mDrawables.add(new ColorDrawable(Color.BLUE));
        mDrawables.add(new ColorDrawable(Color.RED));
        mDrawables.add(new ColorDrawable(Color.GREEN));
        mDrawables.add(new ColorDrawable(Color.YELLOW));
        mDrawables.add(new ColorDrawable(Color.GRAY));
        for (int i = 0; i < mDrawables.size(); i++) {
            mPager.addDrawable(mDrawables.get(i));
        }
        mPager.setOnPageChangeListener(this);
        // 设置viewpager在第二个视图显示  
        mPager.setCurrentItem(1);
        mPager.setPageTransformer(true, new FadeTransformer());
        //mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setCurrentItem(1);
        
        mAddPager = (Button) findViewById(R.id.add_pager);
        mAddPager.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPager.addDrawable(mDrawables.get(mDrawables.size()-1));
            }
        });
        
        mRemovePager = (Button) findViewById(R.id.remove_pager);
        mRemovePager.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPager.removeDrawable(mDrawables.get(mDrawables.size()-1));
            }
        });
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
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        mPager.onPageSelectedEvent(arg0);
    }


}
