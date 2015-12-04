
package com.bigkoo.convenientbanner;

import java.lang.reflect.Field;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.bigkoo.convenientbanner.transforms.ABaseTransformer;

/**
 * 页面翻转控件，极方便的广告栏 支持无限循环，自动翻页，翻页特效
 */
public class FlipBanner<T> extends LinearLayout {
    private ViewHolderCreator holderCreator;
    private List<T> mDatas;
    private LoopPageAdapter mLoopPageAdapter;
    private RelativeLayout mRootView;
    private LoopViewPager mLoopViewPager;
    private LoopIndicator mLoopIndicator;
    private long autoTurningTime;
    private boolean mFlipEnable = false;
    private boolean mFlipStart = false;
    private boolean manualPageable = true;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public enum Transformer {
        DefaultTransformer("DefaultTransformer"), AccordionTransformer(
                "AccordionTransformer"), BackgroundToForegroundTransformer(
                "BackgroundToForegroundTransformer"), CubeInTransformer(
                "CubeInTransformer"), CubeOutTransformer("CubeOutTransformer"), DepthPageTransformer(
                "DepthPageTransformer"), FlipHorizontalTransformer(
                "FlipHorizontalTransformer"), FlipVerticalTransformer(
                "FlipVerticalTransformer"), ForegroundToBackgroundTransformer(
                "ForegroundToBackgroundTransformer"), RotateDownTransformer(
                "RotateDownTransformer"), RotateUpTransformer(
                "RotateUpTransformer"), StackTransformer("StackTransformer"), TabletTransformer(
                "TabletTransformer"), ZoomInTransformer("ZoomInTransformer"), ZoomOutSlideTransformer(
                "ZoomOutSlideTransformer"), ZoomOutTranformer(
                "ZoomOutTranformer");

        private final String CLASS_NAME;

        /**
         * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
         * 
         * @param className
         */
        Transformer(String className) {
            this.CLASS_NAME = className;
        }

        public String getClassName() {
            return CLASS_NAME;
        }
    }

    private Handler timeHandler = new Handler();
    private Runnable adSwitchTask = new Runnable() {
        @Override
        public void run() {
            if (mLoopViewPager != null && mFlipEnable) {
                int page = mLoopViewPager.getCurrentItem() + 1;
                mLoopViewPager.setCurrentItem(page);
                timeHandler.postDelayed(adSwitchTask, autoTurningTime);
            }
        }
    };

    public FlipBanner(Context context) {
        this(context, null);
    }

    public FlipBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private void init(Context context) {
        mRootView = new RelativeLayout(context);

        mLoopViewPager = new LoopViewPager(context);
        RelativeLayout.LayoutParams pagerLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mRootView.addView(mLoopViewPager, pagerLp);

        mLoopIndicator = new LoopIndicator(context);
        mRootView.addView(mLoopIndicator);

        addView(mRootView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        initViewPagerScroll();
    }

    public FlipBanner setViewPagerData(ViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        this.holderCreator = holderCreator;
        mLoopPageAdapter = new LoopPageAdapter(holderCreator, mDatas);
        mLoopViewPager.setAdapter(mLoopPageAdapter);
        mLoopViewPager.setBoundaryCaching(true);
        mLoopIndicator.updatePageIndicator(mDatas != null ? mDatas.size() : 0);
        return this;
    }

    /**
     * 通知数据变化
     */
    public void notifyDataSetChanged() {
        mLoopViewPager.getAdapter().notifyDataSetChanged();
        mLoopIndicator.updatePageIndicator(mDatas != null ? mDatas.size() : 0);
    }

    /**
     * 设置底部指示器图标
     * 
     * @param selected
     * @param other
     */
    public void setPageIndicator(int selected, int other) {
        mLoopIndicator.setPageIndicator(selected, other, mLoopViewPager);
    }

    /**
     * 设置底部指示器是否可见
     * 
     * @param visible
     */
    public void setPagerIndicatorVisible(boolean visible) {
        mLoopIndicator.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 指示器
     */
    public LoopIndicator getPageIndicator() {
        return mLoopIndicator;
    }

    public LoopViewPager getViewPager() {
        return mLoopViewPager;
    }

    /***
     * 是否开启了翻页
     * 
     * @return
     */
    public boolean isEnableFlip() {
        return mFlipEnable;
    }

    /***
     * 开始翻页
     * 
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public FlipBanner startFlipping(long autoTurningTime) {
        // 如果是正在翻页的话先停掉
        if (mFlipEnable) {
            stopFlipping();
        }
        // 设置可以翻页并开启翻页
        mFlipStart = true;
        this.autoTurningTime = autoTurningTime;
        mFlipEnable = true;
        timeHandler.postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    public void stopFlipping() {
        mFlipEnable = false;
        timeHandler.removeCallbacks(adSwitchTask);
    }

    /**
     * 自定义翻页动画效果
     * 
     * @param transformer
     * @return
     */
    public FlipBanner setPageTransformer(PageTransformer transformer) {
        mLoopViewPager.setPageTransformer(true, transformer);
        return this;
    }

    /**
     * 自定义翻页动画效果，使用已存在的效果
     * 
     * @param transformer
     * @return
     */
    public FlipBanner setPageTransformer(Transformer transformer) {
        try {
            mLoopViewPager
                    .setPageTransformer(
                            true,
                            (PageTransformer) Class.forName(
                                    ABaseTransformer.class.getPackage().getName()
                                            + "." + transformer.getClassName())
                                    .newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    mLoopViewPager.getContext());
            // scroller.setScrollDuration(1500);
            mScroller.set(mLoopViewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return mLoopViewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        mLoopViewPager.setCanScroll(manualPageable);
    }

    /**
     * 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 开始翻页
            if (mFlipStart) {
                startFlipping(autoTurningTime);
            }
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (mFlipStart) {
                stopFlipping();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public class ViewPagerScroller extends Scroller {
        /**
         * 滑动速度,值越大滑动越慢，滑动太快会使3d效果不明显
         * <p>
         * super.DEFAULT_DURATION = 250;
         */
        private int mScrollDuration = 1200;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator,
                boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        public int getScrollDuration() {
            return mScrollDuration;
        }

        public void setScrollDuration(int scrollDuration) {
            this.mScrollDuration = scrollDuration;
        }
    }
}
