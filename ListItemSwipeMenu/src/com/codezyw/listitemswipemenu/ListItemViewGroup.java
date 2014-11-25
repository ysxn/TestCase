
package com.codezyw.listitemswipemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

public class ListItemViewGroup extends ViewGroup {

    private String TAG = "zyw";
    private boolean DEBUG = true;
    private Context mContext;
    private LayoutInflater mInflater;
    private Scroller mScroller;
    private View mLeftMenu;
    private View mRightMenu;
    private View mListItem;
    private int mPosition = -1;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mLeftMenuWidth = 0;
    private int mLeftMenuHeight = 0;
    private int mRightMenuWidth = 0;
    private int mRightMenuHeight = 0;
    private OnLeftMenuClickListener mOnLeftMenuClickListener = null;
    private OnRightMenuClickListener mOnRightMenuClickListener = null;
    private OnMenuClickListener mOnMenuClickListener = null;
    
    public interface OnLeftMenuClickListener {
        void onLeftMenuClicked(ListItemViewGroup listItemViewGroup);
    }
    
    public interface OnRightMenuClickListener {
        void onRightMenuClicked(ListItemViewGroup listItemViewGroup);
    }
    
    public interface OnMenuClickListener {
        void onMenuClicked(ListItemViewGroup listItemViewGroup);
    }

    public ListItemViewGroup(Context context) {
        this(context, null);
    }

    public ListItemViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListItemViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListItemViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mScroller = new Scroller(context, new LinearInterpolator());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        if (mListItem != null) {
            mListItem.layout(0, 0, mWidth, mHeight);
        }
        if (mLeftMenu != null && (mLeftMenu.getVisibility() != View.GONE)) {
            mLeftMenu.layout(-mLeftMenuWidth, 0, 0, mHeight);
        }
        if (mRightMenu != null && (mRightMenu.getVisibility() != View.GONE)) {
            mRightMenu.layout(mWidth, 0, mWidth + mRightMenuWidth, mHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mListItem != null) {
            mListItem.measure(widthMeasureSpec, heightMeasureSpec);
            mWidth = mListItem.getMeasuredWidth();
            mHeight = mListItem.getMeasuredHeight();
            ViewGroup.LayoutParams layoutParams;
            if (mLeftMenu != null && (mLeftMenu.getVisibility() != View.GONE)) {
                layoutParams = mLeftMenu.getLayoutParams();
                mLeftMenu.measure(
                        MeasureSpec.makeMeasureSpec(layoutParams.width, layoutParams.width > 0 ? MeasureSpec.EXACTLY : MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
                mLeftMenuWidth = mLeftMenu.getMeasuredWidth();
                mLeftMenuHeight = mLeftMenu.getMeasuredHeight();
            }
            if (mRightMenu != null && (mRightMenu.getVisibility() != View.GONE)) {
                layoutParams = mRightMenu.getLayoutParams();
                mRightMenu.measure(
                        MeasureSpec.makeMeasureSpec(layoutParams.width, layoutParams.width > 0 ? MeasureSpec.EXACTLY : MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
                mRightMenuWidth = mRightMenu.getMeasuredWidth();
                mRightMenuHeight = mRightMenu.getMeasuredHeight();
            }
            setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec),
                    resolveSize(mHeight, heightMeasureSpec));
            if (DEBUG) {
                Log.i(TAG,
                        ">>>>mLeftMenu=" + mLeftMenuWidth + "-" + mLeftMenuHeight + ", mRightMenu="
                                + mRightMenuWidth + "-" + mRightMenuHeight + ", mListItem="
                                + mListItem.getMeasuredWidth() + "-"
                                + mListItem.getMeasuredHeight());
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    
    @Override
    public final void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
    
    public void scrollSmoothTo (int finalX, int finalY) {
        int startX = getScrollX();
        if (DEBUG) {
            Log.i(TAG, ">>>>>scrollSmoothTo , finalX="+finalX+", startX="+startX);
        }
        mScroller.startScroll(startX, 0, finalX - startX, 0, 100);
        postInvalidate();
    }

    public void setListItemView(View v) {
        mListItem = v;
        addView(mListItem);
    }

    public void setListItemView(int resid) {
        mListItem = mInflater.inflate(resid, this);
    }

    public void setLeftMenu(View v) {
        mLeftMenu = v;
        addView(mLeftMenu);
    }

    public void setLeftMenu(int resid) {
        mLeftMenu = mInflater.inflate(resid, this);
    }

    public void setRightMenu(View v) {
        mRightMenu = v;
        addView(mRightMenu);
    }

    public void setRightMenu(int resid) {
        mRightMenu = mInflater.inflate(resid, this);
    }
    
    public int getRightMenuWidth() {
        return mRightMenuWidth;
    }
    
    public int getLeftMenuWidth() {
        return mLeftMenuWidth;
    }
    
    public void setLeftMenuClickListener(OnLeftMenuClickListener l) {
        mOnLeftMenuClickListener = l;
        if (mLeftMenu != null && mOnLeftMenuClickListener != null) {
            mLeftMenu.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (mOnMenuClickListener != null) {
                        mOnMenuClickListener.onMenuClicked(ListItemViewGroup.this);
                    }
                    if (mOnLeftMenuClickListener != null) {
                        mOnLeftMenuClickListener.onLeftMenuClicked(ListItemViewGroup.this);
                    }
                }
            });
        } else {
            mLeftMenu.setOnClickListener(null);
        }
    }
    
    public void setRightMenuClickListener(OnRightMenuClickListener l) {
        mOnRightMenuClickListener = l;
        if (mRightMenu != null && mOnRightMenuClickListener != null) {
            mRightMenu.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (mOnMenuClickListener != null) {
                        mOnMenuClickListener.onMenuClicked(ListItemViewGroup.this);
                    }
                    if (mOnRightMenuClickListener != null) {
                        mOnRightMenuClickListener.onRightMenuClicked(ListItemViewGroup.this);
                    }
                }
            });
        } else {
            mRightMenu.setOnClickListener(null);
        }
    }
    
    public void setMenuClickListener(OnMenuClickListener l) {
        mOnMenuClickListener = l;
    }

    public String dumpLog() {
        return "para:" + mPosition;
    }
    
    public void setDebugEnable(boolean isTrue) {
        DEBUG = isTrue;
    }
}
