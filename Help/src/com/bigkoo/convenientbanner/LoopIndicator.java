
package com.bigkoo.convenientbanner;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.codezyw.common.UnitHelper;

/**
 * 底部指示器, parent View必须是RelativeLayout
 */
public class LoopIndicator extends LinearLayout {
    private int mSelectedIndicatorId = 0;
    private int mOthertIndicatorId = 0;
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private int mPagerCount = 0;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public LoopIndicator(Context context) {
        this(context, null);
    }

    public LoopIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int margin = (int) UnitHelper.dp2px(getContext(), 5);
        lp.leftMargin = margin;
        lp.topMargin = margin;
        lp.rightMargin = margin;
        lp.bottomMargin = margin;
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        setLayoutParams(lp);
        setOrientation(LinearLayout.HORIZONTAL);
        // 设置指示器的方向
        setIndicatorAlign(LoopIndicator.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    /**
     * 底部指示器资源图片
     * 
     * @param page_indicatorId
     */
    public void setIndicatorResource(int selected, int other, ViewPager v) {
        mSelectedIndicatorId = selected;
        mOthertIndicatorId = other;
        v.setOnPageChangeListener(new IndicatorListener());
    }

    public void updateIndicator(int count) {
        mPagerCount = count;
        removeAllViews();
        mPointViews.clear();
        if (mPagerCount <= 0 || mSelectedIndicatorId == 0 || mOthertIndicatorId == 0) {
            return;
        }
        for (int i = 0; i < mPagerCount; i++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty()) {
                pointView.setImageResource(mSelectedIndicatorId);
            } else {
                pointView.setImageResource(mOthertIndicatorId);
            }
            mPointViews.add(pointView);
            addView(pointView);
        }
    }

    /**
     * 指示器的方向
     * 
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中
     *            （RelativeLayout.CENTER_HORIZONTAL），居右
     *            （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public void setIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        setLayoutParams(layoutParams);
    }

    /**
     * 翻页指示器适配器
     */
    public class IndicatorListener implements ViewPager.OnPageChangeListener {

        public IndicatorListener() {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mPointViews.size(); i++) {
                if (position != i) {
                    mPointViews.get(i).setImageResource(mOthertIndicatorId);
                } else {
                    mPointViews.get(position).setImageResource(mSelectedIndicatorId);
                }
            }
        }
    }
}
