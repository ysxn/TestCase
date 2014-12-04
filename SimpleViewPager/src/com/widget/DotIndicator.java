
package com.widget;

import java.util.ArrayList;
import java.util.List;

import com.example.simpleviewpager.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 参考类未使用
 * 
 * @author Administrator
 */
public class DotIndicator extends LinearLayout implements
        Animation.AnimationListener {
    private static final String TAG = "SlideDotIndicator";
    // 是否显示数量
    private boolean mIsShowNum = false;

    private Drawable mIndicatorDotNormal;
    private Drawable mIndicatorDotSelected;

    private List<DotIndicatorItem> mDotViews;
    // 可见时间
    private int VISIBLE_TIME = 300;
    // 动画
    private Animation mAnimation;
    // 自动隐藏的线程
    private Runnable mAutoHide = new Runnable() {
        public void run() {
            if (mAnimation == null) {
                mAnimation = AnimationUtils.loadAnimation(
                        getContext(), R.anim.fade_out_fast);
                mAnimation.setAnimationListener(DotIndicator.this);
            }
            startAnimation(mAnimation);
        }
    };
    // 当前选中的项
    private int mCurrentItem = -1;

    private Handler mHandler = new Handler();
    // 中数量
    private int mTotalItems = -1;
    // 可见项
    private int mVisibleTime = -1;

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

    public void move(float paramFloat) {
    }

    @Override
    public void onAnimationEnd(Animation paramAnimation) {
        setVisibility(4);
    }

    @Override
    public void onAnimationRepeat(Animation paramAnimation) {
    }

    @Override
    public void onAnimationStart(Animation paramAnimation) {
    }

    // 设置当前选项
    public void onSetCurrentItem() {
        // 返回子节点的数量
        int i = getChildCount();
        for (int k = 0; k < i; k++) {
            DotIndicatorItem item = (DotIndicatorItem) getChildAt(k);
            // 如果不显示数量那么重置数量
            if (this.mIsShowNum) {
                item.resetNum();
            }
            // 过渡--->获取控件上的图片//重置表示只显示第一层图片
            ((TransitionDrawable) item.getDrawable()).resetTransition();
        }
        // 获取当前选项
        int j = getCurrentItem();
        if ((j < 0) || (j >= i)) {
            return;
        }
        DotIndicatorItem item1 = (DotIndicatorItem) getChildAt(j);
        if (this.mIsShowNum)
            item1.setNum(j);
        ((TransitionDrawable) item1.getDrawable()).startTransition(50);
    }

    public void onSetTotalItems() {
        // 从父类中分离所有的视图
        detachAllViewsFromParent();
        int i = getTotalItems();
        LayoutInflater localLayoutInflater = null;
        if (i > 0) {
            localLayoutInflater = LayoutInflater.from(getContext());
        }
        for (int j = 0; j < i; ++j) {
            DotIndicatorItem item = (DotIndicatorItem)
                    localLayoutInflater.inflate(2130903072, null);
            ((TransitionDrawable) item.getDrawable())
                    .setCrossFadeEnabled(true);
            addView(item, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    public void setAutoHide(boolean paramBoolean) {
        if (!paramBoolean) {
            this.mVisibleTime = -1;
            setVisibility(View.VISIBLE);// 设置可见
        } else {
            this.mVisibleTime = this.VISIBLE_TIME;
            setVisibility(View.INVISIBLE);// 设置隐藏
        }
    }

    public void setCurrentItem(int paramInt) {
        this.mCurrentItem = paramInt;
        for (int j =0;j < mDotViews.size(); j++) {
            mDotViews.get(j).setImageDrawable(mIndicatorDotNormal);
        }
        mDotViews.get(paramInt-1).setImageDrawable(mIndicatorDotSelected);
        /*
        if ((paramInt < 0) || (paramInt >= this.mTotalItems)
                || (paramInt == this.mCurrentItem))
            return;
        this.mCurrentItem = paramInt;
        onSetCurrentItem();
        this.mHandler.removeCallbacks(this.mAutoHide);
        if (this.mVisibleTime <= 0)
            return;
        this.mHandler.postDelayed(this.mAutoHide, this.mVisibleTime);
        */
    }

    public void setTotalItems(int total) {
        this.mTotalItems = total;

        for (int i = 0; i < mTotalItems; i++) {
            DotIndicatorItem dot = new DotIndicatorItem(mContext);
            ViewGroup.LayoutParams dotImageViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dot.setLayoutParams(dotImageViewParams);
            dot.setScaleType(ImageView.ScaleType.CENTER);
            // mIndicatorDotNormal.set
            dot.setImageDrawable(mIndicatorDotNormal);
            dot.setMaxHeight(8);
            dot.setMaxWidth(8);
            dot.setPadding(4, 0, 4, 0);
            this.addView(dot);
            mDotViews.add(dot);
        }

        /*
        if (total != this.mTotalItems) {
            this.mTotalItems = total;
            onSetTotalItems();
            this.mHandler.removeCallbacks(this.mAutoHide);
            if (this.mVisibleTime > 0)
                this.mHandler.postDelayed(this.mAutoHide, this.mVisibleTime);
        }
        this.mCurrentItem = -1;
        */

    }

}
