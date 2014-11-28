
package com.lenovo.component.listviewslidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.lenovo.internal.R;

/**
 * @deprecated This API has not ripple effect.  Do not use. Should use LenovoListViewSlideMenuGroupViewMaterial
 * @author zhuyw1
 *
 */
public class LenovoListViewSlideMenuGroupView extends ViewGroup {
    public static final int SLIDE_STATE_IDLE = 0;
    public static final int SLIDE_STATE_SCROLLING = 1;
    public static final int SLIDE_STATE_SETTING = 2;
    private static final String TAG = "LenovoSlideSectionGroupView";
    private static boolean DEBUG = false;
    private int mMaxSectionCount;

    private Context mContext;
    private Scroller mScroller;
    private OnSlideSectionListener mCallback;

    private int mScrollTime = 100;
    /**
     * 600
     */
    private int mEnterXVelocity = 600;
    private int mOriginalPositionX;
    private int mAutoScrollThresholdRight;
    private int mAutoScrollThresholdLeft;
    /**
     * positive value
     */
    private int mMaxScrollThresholdRight;
    /**
     * negative value
     */
    private int mMaxScrollThresholdLeft;
    /**
     * 0
     */
    private int mVelocityScrollThreshold;
    private int mTouchSlop;
    private int mMaxFlingVelocity;

    private boolean bAutoScrollState;
    private boolean bEnterLockState;
    private boolean bInitViews;
    private boolean bScrollEnter;
    private boolean bScrollBack;
    private boolean bEnterLeftScroll;
    private boolean bForceAbort;

    private ViewGroup mSlidemenuItemContainerLeft;
    private TextView mSlidemenuItemContainerLeftText;
    private ImageView mSlidemenuItemContainerLeftIcon;

    private ViewGroup mSlidemenuItemContainerRight;
    private TextView mSlidemenuItemContainerRightText;
    private ImageView mSlidemenuItemContainerRightIcon;
    private boolean mHideLeftSlideMenu = false;
    private boolean mHideRightSlideMenu = false;

    /**
     * total 5 childs in this ViewGroup, first one is content = 0
     */
    private final int mContentSectionIdex = 0;
    private int mPosition = -1;

    public LenovoListViewSlideMenuGroupView(Context context) {
        super(context);
        mContext = context;
        init();
        // TODO Auto-generated constructor stub
    }

    public LenovoListViewSlideMenuGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public LenovoListViewSlideMenuGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        initViews();
    }

    public interface OnSlideSectionListener {
        void onSlideStateChanged(LenovoListViewSlideMenuGroupView view, int state);
    }

    public void registerSlideSectionListener(OnSlideSectionListener listener) {
        mCallback = listener;
    }

    private void init() {
        mScroller = new Scroller(mContext, new LinearInterpolator());

        ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        // mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();

        // mVibrator = ((Vibrator)mContext.getSystemService("vibrator"));
    }

    private void initViews() {
        if (!bInitViews) {
            int childCount = getChildCount();
            mMaxSectionCount = childCount + 1;
            for (int position = 0; position < childCount; position++) {
                getChildAt(position).setTag(Integer.valueOf(position));
            }

            mSlidemenuItemContainerLeft = (ViewGroup) findViewById(com.lenovo.internal.R.id.listviewslidemenu_slidemenu_item_container_left);
            mSlidemenuItemContainerLeftText = (TextView)mSlidemenuItemContainerLeft.findViewById(com.lenovo.internal.R.id.listviewslidemenu_slidemenu_item_container_left_text);
            mSlidemenuItemContainerLeftIcon = (ImageView)mSlidemenuItemContainerLeft.findViewById(com.lenovo.internal.R.id.listviewslidemenu_slidemenu_item_container_left_icon);
            mSlidemenuItemContainerRight = (ViewGroup) findViewById(com.lenovo.internal.R.id.listviewslidemenu_slidemenu_item_container_right);
            mSlidemenuItemContainerRightText = (TextView)mSlidemenuItemContainerRight.findViewById(com.lenovo.internal.R.id.listviewslidemenu_slidemenu_item_container_right_text);
            mSlidemenuItemContainerRightIcon = (ImageView)mSlidemenuItemContainerRight.findViewById(com.lenovo.internal.R.id.listviewslidemenu_slidemenu_item_container_right_icon);
            bInitViews = true;
        }
    }

    public void resetState() {
        if (DEBUG) {
            Log.i(TAG, "####resetState, bAutoScrollState: "+bAutoScrollState);
        }

        bScrollEnter = false;
        bScrollBack = false;
        bAutoScrollState = false;
        bEnterLockState = false;

        scrollTo(0, 0);
        if (mCallback != null) {
            mCallback.onSlideStateChanged(this, SLIDE_STATE_IDLE);
        }
    }

    private void scrollBack() {
        int startX = getScrollX();

        bScrollBack = true;
        bAutoScrollState = true;

        if (mCallback != null) {
            mCallback.onSlideStateChanged(this, SLIDE_STATE_SCROLLING);
        }

        mScroller.startScroll(startX, 0, -startX, 0, mScrollTime);

        if (DEBUG) {
            Log.i(TAG, "####scrollBack(), bAutoScrollState:"+bAutoScrollState);
        }
        invalidate();
    }

    private void scorllEnter(boolean bEnterLeft) {
        int startX = getScrollX();

        bScrollEnter = true;
        bAutoScrollState = true;

        if (mCallback != null) {
            mCallback.onSlideStateChanged(this, SLIDE_STATE_SCROLLING);
        }

        if (bEnterLeft) {
            bEnterLeftScroll = true;
            mScroller.startScroll(startX, 0,
                    mMaxScrollThresholdLeft - startX, 0, mScrollTime);
        } else {
            bEnterLeftScroll = false;
            mScroller.startScroll(startX, 0,
                    (Math.abs(mMaxScrollThresholdRight - startX)), 0, mScrollTime);
        }

        if (DEBUG) {
            Log.i(TAG, "####scorllEnter(), bAutoScrollState:"+bAutoScrollState);
        }
        invalidate();
    }

    @Override
    public final void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        } else {
            if (bScrollEnter) {
                // bScrollEnter = false;
                bAutoScrollState = false;
                if (mCallback != null) {
                    mCallback.onSlideStateChanged(this, SLIDE_STATE_SETTING);
                }
            } else if (bScrollBack) {
                bScrollBack = false;
                bAutoScrollState = false;
                if (mCallback != null) {
                    mCallback.onSlideStateChanged(this, SLIDE_STATE_IDLE);
                }
            }
        }
    }

    /*
     * public void abortSlide() { bForceAbort = true; }
     */

    public void touchScroll(int distance_x) {
        int cur_scroll = -distance_x;
        if (cur_scroll < mMaxScrollThresholdLeft) {
            cur_scroll = mMaxScrollThresholdLeft;
        } else if (cur_scroll > mMaxScrollThresholdRight) {
            cur_scroll = mMaxScrollThresholdRight;
        }
        setScrollX(cur_scroll);
    }

    public void touchCancelScroll(int v_x, int v_y) {
        int scrollX = getScrollX();
        boolean bEnter = false;
        boolean bLeft = false;

        if (scrollX > mAutoScrollThresholdRight || scrollX < mAutoScrollThresholdLeft) {
            bEnter = true;
            bLeft = scrollX < 0;
        } else if (Math.abs(scrollX) >= mVelocityScrollThreshold
                && Math.abs(v_y) < Math.abs(v_x)
                && Math.abs(v_x) >= mEnterXVelocity) {
            // Keep the same direction.
            if ((v_x > 0 && scrollX < 0)
                    || (v_x < 0 && scrollX > 0)) {
                bEnter = true;
                bLeft = v_x > 0;
            }
        }
        if (DEBUG) {
            Log.i("zyw", ">>>>>>bLeft="+bLeft+", scrollX="+scrollX);
        }
        // setTranslationX(0);
        // setScrollX(0);
        if (bEnter) {
            scorllEnter(bLeft);
        } else {
            scrollBack();
        }
    }

    public void cancelScroll() {
        bScrollEnter = false;
        scrollBack();
    }

    public View getTouchMenu(int x, int y) {
        int childCount = getChildCount();
        View childView = null;
        int[] view_coords = new int[2];

        if (childCount < mMaxSectionCount) {
            return null;
        }

        for (int index = 1; index < childCount; index++) {
            childView = getChildAt(index);
            if (childView.getVisibility() != View.GONE) {
                childView.getLocationOnScreen(view_coords);
                if (x >= view_coords[0]
                        && x <= (view_coords[0] + childView.getWidth())
                        && y >= view_coords[1]
                        && y <= (view_coords[1] + childView.getHeight()))
                {
                    if (DEBUG) {
                        Log.i(TAG, "####left_section");
                    }
                    return childView;
                }
            }
        }

        return null;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        int viewWidth = 0;
        int viewLeft = 0;
        int viewRight = 0;
        int menuWidthRight = 0;
        int menuWidthLeft = 0;
        int childCount = getChildCount();
        View childView = null;

        if (childCount < mMaxSectionCount) {
            return;
        }

        for (int index = 0; index < childCount; index++) {
            childView = getChildAt(index);
            if (childView.getVisibility() != View.GONE) {
                viewWidth = childView.getMeasuredWidth();
                if (DEBUG) {
                    Log.i(TAG, "####index:"+index+", viewWidth:"+viewWidth);
                }
                if (index == mContentSectionIdex) {
                    mOriginalPositionX = 0;
                    mVelocityScrollThreshold = 0;// viewWidth / 4;
                    viewLeft = mOriginalPositionX;
                    childView.layout(viewLeft, 0, viewLeft + viewWidth, bottom - top);
                    viewLeft += viewWidth;
                } else if (index == 2) {
                    menuWidthRight += viewWidth;
                    childView.layout(viewLeft, 0, viewLeft + viewWidth, bottom - top);
                    viewLeft += viewWidth;
                } else if (index == 1) {
                    menuWidthLeft += viewWidth;
                    childView.layout(viewRight - viewWidth, 0, viewRight, bottom - top);
                    viewRight -= viewWidth;
                }

            }
        }

        mAutoScrollThresholdRight = menuWidthRight / 2;
        mMaxScrollThresholdRight = menuWidthRight;

        mAutoScrollThresholdLeft = -menuWidthLeft / 2;
        mMaxScrollThresholdLeft = -menuWidthLeft;
        if (DEBUG) {
            Log.i("zyw", ">>>>>menuWidthLeft="+menuWidthLeft+", menuWidthRight="+menuWidthRight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        if (DEBUG) {
            Log.i(TAG, "####:getChildCount=" + getChildCount());
        }
        ViewGroup.LayoutParams layoutParams;
        // View left_section = null;
        View content_section = null;
        // View right_section = null;
        int viewHeight = 0;
        int width = 0;
        int height = 0;
        int childCount = getChildCount();

        if (childCount < mMaxSectionCount) {
            return;
        }

        content_section = getChildAt(mContentSectionIdex);
        if (DEBUG) {
            Log.i("zyw", ">>>>>onMeasure, widthMeasureSpec=" + widthMeasureSpec
                + ", heightMeasureSpec=" + heightMeasureSpec);
        }

        if (content_section != null) {
            content_section.measure(widthMeasureSpec, heightMeasureSpec);
        }

        width = MeasureSpec.getSize(widthMeasureSpec);
        if (DEBUG) {
            Log.i(TAG, "####width:"+width);
        }
        if (content_section != null) {
            height = content_section.getMeasuredHeight();
        }

        viewHeight = Math.max(0, height);

        View childView = null;
        for (int index = 1; index < childCount; index++) {
            childView = getChildAt(index);
            if (childView.getVisibility() != View.GONE) {
                layoutParams = childView.getLayoutParams();
                childView.measure(
                        MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                // left_section.measure(MeasureSpec.makeMeasureSpec(width,
                // MeasureSpec.EXACTLY),
                // MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
                viewHeight = Math.max(viewHeight, childView.getMeasuredHeight());
            }
        }
        if (DEBUG) {
            Log.i("zyw", ">>>>>onMeasure, width=" + width
                + ", viewHeight=" + viewHeight);
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(viewHeight, heightMeasureSpec));
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public final void setContentView(View contentView) {
        if (getChildCount() == mMaxSectionCount) {
            removeViewAt(mContentSectionIdex);
        }

        addView(contentView, mContentSectionIdex,
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setHideLeftSlideMenu(boolean isTrue) {
        mHideLeftSlideMenu = isTrue;
        if (mHideLeftSlideMenu) {
            mSlidemenuItemContainerLeft.setVisibility(View.GONE);
        } else {
            mSlidemenuItemContainerLeft.setVisibility(View.VISIBLE);
        }

    }

    public void setHideRightSlideMenu(boolean isTrue) {
        mHideRightSlideMenu = isTrue;
        if (mHideRightSlideMenu) {
            mSlidemenuItemContainerRight.setVisibility(View.GONE);
        } else {
            mSlidemenuItemContainerRight.setVisibility(View.VISIBLE);
        }
    }

    public TextView getSlideMenuLeftSideText() {
        // TODO Auto-generated method stub
        return mSlidemenuItemContainerLeftText;
    }
    
    public ImageView getSlideMenuLeftSideIcon() {
        // TODO Auto-generated method stub
        return mSlidemenuItemContainerLeftIcon;
    }
    
    public TextView getSlideMenuRightSideText() {
        // TODO Auto-generated method stub
        return mSlidemenuItemContainerRightText;
    }
    
    public ImageView getSlideMenuRightSideIcon() {
        // TODO Auto-generated method stub
        return mSlidemenuItemContainerRightIcon;
    }
    
    public ViewGroup getSlideMenuLeftSideViewGroup() {
        // TODO Auto-generated method stub
        return mSlidemenuItemContainerLeft;
    }
    
    public ViewGroup getSlideMenuRightSideViewGroup() {
        // TODO Auto-generated method stub
        return mSlidemenuItemContainerRight;
    }
    
    public void setDebugLog(boolean isTrue) {
        DEBUG = isTrue;
    }
}
