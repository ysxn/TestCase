
package com.widget;

import com.transformer.FadeTransformer;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

public class ViewPagerWithIndicator extends ViewPager {
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    
    private Context mContext;
    private DotIndicator mDotIndicator = null;
    private int mCurrentPagerReallyIndex = 0;
    
    public ViewPagerWithIndicator(Context context) {
        super(context);
        mContext = context;
        initPageTransformer(context);
    }

    public ViewPagerWithIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPageTransformer(context);
    }
    
    @Override
    public void setCurrentItem(int i) {
        super.setCurrentItem(i);
    }
    
    private void initPageTransformer(Context context) {
        setPageTransformer(true, new FadeTransformer());
        //mPager.setPageTransformer(true, new DepthPageTransformer());
        //mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }
    
    public void setDotIndicator(DotIndicator d) {
        mDotIndicator = d;
    }
    
    public void onPageScrollStateChangedEvent(int i) {
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>>>onPageScrollStateChangedEvent stat="+i+" , mCurrentPager="+mCurrentPagerReallyIndex);
        }
    }

    public void onPageSelectedEvent(int i) {
        mCurrentPagerReallyIndex = i % ((DefaultPagerAdapter) getAdapter()).getReallyCount();
        mDotIndicator.setCurrentItem(mCurrentPagerReallyIndex);
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>>>onPageSelectedEvent mCurrentPager="+mCurrentPagerReallyIndex+", int = "+i);
        }
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Invalidate the adapter's data set since children may have been added during inflation.
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }
    
    /**
     * test
     * @return
     */
    public String dumpLog() {
        return "para:";
    }

    /**
     * set log enable
     * @param isTrue
     */
    public void setDebugEnable(boolean isTrue) {
        DEBUG = isTrue;
    }
}
