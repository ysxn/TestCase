/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.swipebackdemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
//import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class SwipeBackLayout extends FrameLayout {
    private boolean DEBUG = true;
    private String TAG = "zyw";
    /**
     * Minimum velocity that will be detected as a fling
     */
    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    /**
     * Set a default color to use for the scrim that obscures primary content while a
     * drawer is open.
     * 
     * color Color to use in 0xAARRGGBB format.
     */
    private static final int DEFAULT_SCRIM_COLOR = 0xff000000;

    /**
     * Default threshold of scroll
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;

    /**
     * unit is pixel , 10px.
     * If we want do animation for activity swipe out .likely finish the activity, extra move distance is this .
     * used like this : mContentView.getWidth + OVERSCROLL_DISTANCE
     */
    private static final int OVERSCROLL_DISTANCE = 10;

    /**
     * INT array for setting swipe direction   
     * ViewDragHelper.EDGE_LEFT, ViewDragHelper.EDGE_RIGHT, ViewDragHelper.EDGE_BOTTOM, ViewDragHelper.EDGE_ALL
     */
    private static final int[] EDGE_FLAGS = {
            ViewDragHelper.EDGE_LEFT, ViewDragHelper.EDGE_RIGHT, ViewDragHelper.EDGE_BOTTOM, ViewDragHelper.EDGE_ALL
    };

    /**
     * current direction for detected swipe direction   
     */
    public int mEdgeFlag = 0;

    /**
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     */
    private float mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;

    /**
     * the associated Activity by this 
     */
    private Activity mActivity;

    /**
     * boolean indicate weather Swipe Back function Enable or not
     */
    private boolean mEnable = false;

    /**
     * the origin child of DecorView at(0).
     * replace DecorView's child at(0) by this SwipeBackLayout, which this SwipeBackLayout will contain the above mentioned child.
     */
    private View mContentView;

    /**
     * helper to handle touch event
     */
    private ViewDragHelper mDragHelper;

    /**
     * in ViewDragHelper.Callback function onViewPositionChanged() set this value.
     * this is the origin DecorView's first child : mContentView drag compute result
     */
    private float mScrollPercent;

    /**
     * in ViewDragHelper.Callback function onViewPositionChanged() will update this value.
     * this is the origin DecorView's first child
     */
    private int mContentLeft;

    /**
     * in ViewDragHelper.Callback function onViewPositionChanged() will update this value.
     * this is the origin DecorView's first child
     */
    private int mContentTop;

    /**
     * this value is compute like this : = 1 - mScrollPercent.
     * Above means when drag percent approach 100%, blank area's alpha effect will also approach to 0
     * @see # mScrollPercent
     */
    private float mScrimOpacity;

    /**
     * Set a default color to use for the scrim that obscures primary content while a
     * drawer is open.
     * 
     * color Color to use in 0xAARRGGBB format.
     */
    private int mScrimColor = DEFAULT_SCRIM_COLOR;

    /**
     * indicate onLayout is calling.
     * Overrides: onLayout(...) in FrameLayout
     *  protected void onLayout (boolean changed, int left, int top, int right, int bottom) 
     *  Added in API level 1
     *  Called from layout when this view should assign a size and position to each of its children. Derived classes with children should override this method and call layout on each of their children. 
     */
    private boolean mInLayout;

    /**
     * Edge being dragged, this is set by running motion.
     * Unlike mEdgeFlag, mEdgeFlag is stable.
     * @see # mEdgeFlag
     */
    public int mTrackingEdge = 0;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());

        mDragHelper.setEdgeSize(50);
        mEdgeFlag = EDGE_FLAGS[3];
        
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        mDragHelper.setMinVelocity(minVel);

    }

    /**
     * Set up contentView which will be moved by user gesture
     * 
     * @param view
     */
    private void setContentView(View view) {
        mContentView = view;
    }

    /**
     * set the boolean indicator weather Swipe Back function Enable or not
     * @param enable
     */
    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        try {
            return mDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            // FIXME: handle exception
            // issues #9
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        try {
            mDragHelper.processTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            // FIXME: handle exception
            // issues #9
            return false;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        if (mContentView != null)
            mContentView.layout(mContentLeft, mContentTop,
                    mContentLeft + mContentView.getMeasuredWidth(),
                    mContentTop + mContentView.getMeasuredHeight());
        mInLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        /**
         * whether this is the origin DecorView's first child
         */
        final boolean drawContent = child == mContentView;

        boolean ret = super.drawChild(canvas, child, drawingTime);
        /**
         * alpha animation for draw blank area
         */
        /*
        if (mScrimOpacity > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawScrim(canvas, child);
        }
        */
        return ret;
    }

    /**
     * this is used to draw alpha animation for when dragging the origin Content View.
     * the blank area beside he origin Content View will draw alpha color by mScrimColor.
     * alpha effect is control by mScrimOpacity
     * @param canvas
     * @param child
     * @see # mScrimColor
     * @see # mScrimOpacity
     */
    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        if (DEBUG) {
            android.util.Log.i(TAG, ">>>>>>>>>drawScrim baseAlpha="+baseAlpha+" mScrimOpacity="+mScrimOpacity);
        }
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);

        if ((mTrackingEdge & ViewDragHelper.EDGE_LEFT) != 0) {
            canvas.clipRect(0, 0, child.getLeft(), getHeight());
        } else if ((mTrackingEdge & ViewDragHelper.EDGE_RIGHT) != 0) {
            canvas.clipRect(child.getRight(), 0, getRight(), getHeight());
        } else if ((mTrackingEdge & ViewDragHelper.EDGE_BOTTOM) != 0) {
            canvas.clipRect(child.getLeft(), child.getBottom(), getRight(), getHeight());
        }
        canvas.drawColor(color);
    }

    /**
     * replace DecorView's child at(0) by this SwipeBackLayout, which this SwipeBackLayout will contain the above mentioned child.
     * @param activity
     */
    public void attachToActivity(Activity activity) {
        if (!mEnable) {
            return;
        }
        if (activity == null) {
            mEnable = false;
            return;
        }
        WindowManager.LayoutParams windowParams = activity.getWindow().getAttributes();
        android.util.Log.i(TAG,">>>attachToActivity windowParams="+windowParams.toString());
        if (windowParams.width != -1 || windowParams.height != -1) {
            mEnable = false;
            return;
        }
        mActivity = activity;
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //mActivity.getWindow().getDecorView().setBackgroundDrawable(new ColorDrawable(0));
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[] {
            android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
        if (mDragHelper.continueSettling(true)) {
            SwipeBackLayout.this.postInvalidateOnAnimation();
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View view, int i) {
            boolean ret = mDragHelper.isEdgeTouched(mEdgeFlag, i);
            if (ret) {
                if (mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, i)) {
                    mTrackingEdge = ViewDragHelper.EDGE_LEFT;
                } else if (mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_RIGHT, i)) {
                    mTrackingEdge = ViewDragHelper.EDGE_RIGHT;
                } else if (mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_BOTTOM, i)) {
                    mTrackingEdge = ViewDragHelper.EDGE_BOTTOM;
                }
            }
            return ret;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if ((mTrackingEdge & ViewDragHelper.EDGE_LEFT) != 0) {
                mScrollPercent = Math.abs((float) left
                        / (mContentView.getWidth()));
            } else if ((mTrackingEdge & ViewDragHelper.EDGE_RIGHT) != 0) {
                mScrollPercent = Math.abs((float) left
                        / (mContentView.getWidth()));
            } else if ((mTrackingEdge & ViewDragHelper.EDGE_BOTTOM) != 0) {
                mScrollPercent = Math.abs((float) top
                        / (mContentView.getHeight()));
            }
            android.util.Log.i(TAG, ">>>>change left="+left+",top="+top+",dx="+dx+",dy="+dy+",mScrollPercent="+mScrollPercent);
            mContentLeft = left;
            mContentTop = top;
            invalidate();

            if (mScrollPercent >= 1 && mActivity != null && !mActivity.isFinishing()) {
                    android.util.Log.i(TAG, ">>>>onBackPressed");
                    mActivity.finish();
            }
        }

        @Override
        public void onViewReleased(View releasedChild) {
            if (releasedChild == null) {
                return;
            }
            final int childWidth = releasedChild.getWidth();
            final int childHeight = releasedChild.getHeight();

            int left = 0, top = 0;
            if ((mTrackingEdge & ViewDragHelper.EDGE_LEFT) != 0) {
                left = mScrollPercent > mScrollThreshold ? childWidth
                        + OVERSCROLL_DISTANCE : 0;
            } else if ((mTrackingEdge & ViewDragHelper.EDGE_RIGHT) != 0) {
                left = mScrollPercent > mScrollThreshold ? -(childWidth
                        + OVERSCROLL_DISTANCE) : 0;
            } else if ((mTrackingEdge & ViewDragHelper.EDGE_BOTTOM) != 0) {
                top = mScrollPercent > mScrollThreshold ? -(childHeight
                        + OVERSCROLL_DISTANCE) : 0;
            }
            
            mDragHelper.releaseCapturedViewAt(left, top);
            invalidate();
        }
    }
}