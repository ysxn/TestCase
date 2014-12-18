
package com.widget;

import java.util.ArrayList;
import java.util.List;

import com.example.simpleviewpager.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 参考类未使用
 * 
 * @author Administrator
 */
public class DotIndicator extends LinearLayout {
    private boolean DEBUG = true;
    private static final String TAG = "SlideDotIndicator";

    private Drawable mIndicatorDotNormal;
    private Drawable mIndicatorDotSelected;

    private List<DotIndicatorItem> mDotViews;

    // 当前选中的项
    private int mCurrentItem = -1;

    // 中数量
    private int mTotalItems = -1;

    private Context mContext;

    public DotIndicator(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public DotIndicator(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        setFocusable(false);
        mIndicatorDotNormal = this.getResources().getDrawable(R.drawable.base_indicator_dot_normal);
        mIndicatorDotSelected = this.getResources().getDrawable(
                R.drawable.base_indicator_dot_selected);
        mDotViews = new ArrayList<DotIndicatorItem>();
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public int getTotalItems() {
        return this.mTotalItems;
    }

    /**
     * set current index, range is [0,total -1]
     * @param currentIndex
     */
    public void setCurrentItem(int currentIndex) {
        if (DEBUG) {
            Log.i(TAG, ">>>>setCurrentItem : "+currentIndex);
        }
        this.mCurrentItem = currentIndex;
        for (int j =0;j < mDotViews.size(); j++) {
            mDotViews.get(j).setImageDrawable(mIndicatorDotNormal);
        }
        mDotViews.get(currentIndex).setImageDrawable(mIndicatorDotSelected);
    }

    public void setTotalItems(int total) {
        this.mTotalItems = total;
        this.removeAllViews();
        mDotViews.clear();
        if (DEBUG) {
            Log.i(TAG, ">>>>>>setTotalItems ="+total);
        }

        for (int i = 0; i < mTotalItems; i++) {
            DotIndicatorItem dot = new DotIndicatorItem(mContext);
            ViewGroup.LayoutParams dotImageViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dot.setLayoutParams(dotImageViewParams);
            dot.setScaleType(ImageView.ScaleType.CENTER);
            dot.setImageDrawable(mIndicatorDotNormal);
            dot.setPadding(4, 0, 4, 0);
            this.addView(dot);
            mDotViews.add(dot);
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
