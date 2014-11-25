
package com.codezyw.listitemswipemenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

public class ListItemViewGroup extends ViewGroup {

    private String TAG = "zyw";
    private boolean DEBUG = false;
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
    private float mDensity = 1;
    private boolean mLeftMenuHide = false;
    private boolean mRightMenuHide = false;
    
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
        mDensity = getResources().getDisplayMetrics().density;
        
        TextView leftMenu = new TextView(mContext);
        Drawable dLeft = mContext.getDrawable(R.drawable.listviewslidemenu_ic_list_slidemenu_top);
        dLeft.setBounds(0, 0, dLeft.getIntrinsicWidth(), dLeft.getIntrinsicHeight());
        leftMenu.setCompoundDrawables(dLeft, null, null, null);
        leftMenu.setCompoundDrawablePadding((int) (16*mDensity));
        leftMenu.setPadding((int) (16*mDensity), 0, (int) (16*mDensity), 0);
        leftMenu.setGravity(Gravity.CENTER);
        leftMenu.setTextColor(0xfffafafa);
        leftMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        /*
        RippleDrawable rdLeft = new RippleDrawable(ColorStateList.valueOf(Color.GREEN), null, new ColorDrawable(Color.WHITE));
        TypedValue outValue = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        final int selectableItemBackground =  outValue.resourceId;
        */
        
        leftMenu.setBackgroundResource(R.drawable.ripple_left_menu);
        leftMenu.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        if (mLeftMenu != null) {
            removeView(mLeftMenu);
        }
        mLeftMenu = leftMenu;
        addView(mLeftMenu);
        
        TextView rightMenu = new TextView(mContext);
        Drawable dRight = mContext.getDrawable(R.drawable.listviewslidemenu_ic_list_slidemenu_delete);
        dRight.setBounds(0, 0, dRight.getIntrinsicWidth(), dRight.getIntrinsicHeight());
        rightMenu.setCompoundDrawables(null, null, dRight, null);
        rightMenu.setCompoundDrawablePadding((int) (16*mDensity));
        rightMenu.setPadding((int) (16*mDensity), 0, (int) (16*mDensity), 0);
        rightMenu.setGravity(Gravity.CENTER);
        rightMenu.setTextColor(0xfffafafa);
        rightMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        rightMenu.setBackgroundResource(R.drawable.ripple_right_menu);
        rightMenu.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        //rightMenu.setLayoutParams(new LayoutParams((int) (60 * mDensity), (int) (60 * mDensity)));
        if (mRightMenu != null) {
            removeView(mRightMenu);
        }
        mRightMenu = rightMenu;
        addView(mRightMenu);
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
        if (mListItem != null) {
            removeView(mListItem);
        }
        mListItem = v;
        addView(mListItem);
    }

    public void setListItemView(int resid) {
        if (mListItem != null) {
            removeView(mListItem);
        }
        mListItem = mInflater.inflate(resid, this);
    }

    public void setLeftMenu(View v) {
        if (mLeftMenu != null) {
            removeView(mLeftMenu);
        }
        mLeftMenu = v;
        addView(mLeftMenu);
    }

    public void setLeftMenu(int resid) {
        if (mLeftMenu != null) {
            removeView(mLeftMenu);
        }
        mLeftMenu = mInflater.inflate(resid, this);
    }

    public void setRightMenu(View v) {
        if (mRightMenu != null) {
            removeView(mRightMenu);
        }
        mRightMenu = v;
        addView(mRightMenu);
    }

    public void setRightMenu(int resid) {
        if (mRightMenu != null) {
            removeView(mRightMenu);
        }
        mRightMenu = mInflater.inflate(resid, this);
    }
    
    public int getRightMenuWidth() {
        return mRightMenuWidth;
    }
    
    public int getLeftMenuWidth() {
        return mLeftMenuWidth;
    }
    
    public View getRightMenu() {
        return mRightMenu;
    }
    
    public View getLeftMenu() {
        return mLeftMenu;
    }
    
    public boolean isRightMenuHide() {
        return mRightMenuHide;
    }
    
    public boolean isLeftMenuHide() {
        return mLeftMenuHide;
    }
    
    public void setRightMenuHide(boolean isTrue) {
//        if (mRightMenu != null) {
////            mRightMenu.setVisibility(isTrue ? View.GONE : View.VISIBLE);
////            requestLayout();
////            postInvalidate();
//            if (isTrue) {
//                removeView(mRightMenu);
//            } else {
//                addView(mRightMenu);
//            }
//        }
        mRightMenuHide = isTrue;
    }
    
    public void setLeftMenuHide(boolean isTrue) {
//        if (mLeftMenu != null) {
//            if (isTrue) {
//                removeView(mLeftMenu);
//            } else {
//                addView(mLeftMenu);
//            }
//        }
        mLeftMenuHide = isTrue;
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
