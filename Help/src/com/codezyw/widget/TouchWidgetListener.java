
package com.codezyw.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

public class TouchWidgetListener implements View.OnTouchListener {
    private String TAG = "zyw";
    private boolean DEBUG = true;

    public enum SWIPE_STATE {
        SWIPE_STAT_IDLE, SWIPE_STAT_DRAGGING, SWIPE_STAT_SHOW_MENU
    }

    private Context mContext;
    private ListView mListView;
    private ViewGroup mView;
    private float mDownX;
    private float mDownY;
    private SWIPE_STATE mSwipeStatus = SWIPE_STATE.SWIPE_STAT_IDLE;
    private ViewGroup mTouchDownViewGroup = null;
    private int mTouchSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private VelocityTracker mVelocityTracker;
    private boolean mSlideEnabled = true;

    public interface ScrollListener {
        void scrollSmoothTo(int finalX, int finalY);
    }

    public TouchWidgetListener(Context context, ViewGroup view) {
        mContext = context;
        mView = view;
        ViewConfiguration vc = ViewConfiguration.get(mContext);
        mTouchSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        if (DEBUG) {
            Log.i(TAG, ">>>>>mMinFlingVelocity=" + mMinFlingVelocity + ", mMaxFlingVelocity=" + mMaxFlingVelocity + " mTouchSlop=" + mTouchSlop);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!mSlideEnabled) {
            if (mTouchDownViewGroup != null) {
                scrollBack(true);
            }
            return false;
        }
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_DOWN, touch=" + event.getRawX() + "+" + event.getRawY() + ", mSwipeStatus=" + mSwipeStatus);
                }
                if (mSwipeStatus == SWIPE_STATE.SWIPE_STAT_IDLE) {
                    mDownX = event.getRawX();
                    mDownY = event.getRawY();
                    if (mView != null) {
                        mTouchDownViewGroup = mView;
                        if (mVelocityTracker != null) {
                            mVelocityTracker.recycle();
                            mVelocityTracker = null;
                        }
                        if (mVelocityTracker == null) {
                            mVelocityTracker = VelocityTracker.obtain();
                        }
                        mVelocityTracker.addMovement(event);
                    }
                    return true;
                } else if (mSwipeStatus == SWIPE_STATE.SWIPE_STAT_DRAGGING) {
                    scrollBack(true);
                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mDownX - event.getRawX();
                float dy = mDownY - event.getRawY();
                if (mSwipeStatus == SWIPE_STATE.SWIPE_STAT_IDLE && mTouchDownViewGroup != null) {
                    if (Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop) {
                        mSwipeStatus = SWIPE_STATE.SWIPE_STAT_DRAGGING;
                    }
                }
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_MOVE, touch=" + event.getRawX() + "+" + event.getRawY() + " diff={" + dx + "," + dy + "}, mSwipeStatus="
                            + mSwipeStatus);
                }
                if (mSwipeStatus == SWIPE_STATE.SWIPE_STAT_DRAGGING) {

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();

                    int deltaX = (int) (mDownX - event.getRawX());
                    if (DEBUG) {
                        Log.i(TAG, ">>>>>onTouch ACTION_MOVE, touch=" + event.getRawX() + ", mDownX=" + mDownX);
                    }
                    if (deltaX > 0) {
                        // swipe to left direction
                        deltaX = Math.min(deltaX, mTouchDownViewGroup.getMeasuredWidth());
                    } else {
                        // swipe to right direction
                        deltaX = Math.max(deltaX, -mTouchDownViewGroup.getMeasuredWidth());
                    }
                    mTouchDownViewGroup.scrollTo(deltaX, 0);
                    if (mVelocityTracker != null) {
                        mVelocityTracker.addMovement(event);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_UP, touch=" + event.getRawX() + "+" + event.getRawY() + ", mSwipeStatus=" + mSwipeStatus);
                }
                if (mSwipeStatus == SWIPE_STATE.SWIPE_STAT_DRAGGING) {
                    boolean velocityEnoughToFling = false;
                    int v_x = 0;
                    int v_y = 0;
                    int deltaX = (int) (mDownX - event.getRawX());
                    if (mVelocityTracker != null) {
                        mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                        v_x = (int) mVelocityTracker.getXVelocity();
                        v_y = (int) mVelocityTracker.getYVelocity();
                        if (Math.abs(v_y) < Math.abs(v_x) && Math.abs(v_x) > mMinFlingVelocity) {
                            velocityEnoughToFling = true;
                        }
                        if (DEBUG) {
                            Log.i(TAG, ">>>>>onTouch ACTION_UP, Velocity=" + v_x + "+" + v_y);
                        }
                    }

                    if (deltaX > 0 && (deltaX > (mTouchDownViewGroup.getMeasuredWidth() / 3) || (velocityEnoughToFling && v_x < 0))) {
                        // swipe to left direction
                        deltaX = mTouchDownViewGroup.getMeasuredWidth();
                        ((ScrollListener) mTouchDownViewGroup).scrollSmoothTo(deltaX, 0);
                        mSwipeStatus = SWIPE_STATE.SWIPE_STAT_SHOW_MENU;
                    } else if (deltaX < 0 && (deltaX < (-mTouchDownViewGroup.getMeasuredWidth() / 3) || (velocityEnoughToFling && v_x > 0))) {
                        // swipe to right direction
                        deltaX = -mTouchDownViewGroup.getMeasuredWidth();
                        ((ScrollListener) mTouchDownViewGroup).scrollSmoothTo(deltaX, 0);
                        mSwipeStatus = SWIPE_STATE.SWIPE_STAT_SHOW_MENU;
                    } else {
                        scrollBack();
                    }
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * scrollBack menu view, default is scroll smoothly
     */
    private void scrollBack() {
        scrollBack(false);
    }

    /**
     * scrollBack menu view
     * 
     * @param quickly
     */
    private void scrollBack(boolean quickly) {
        if (mTouchDownViewGroup != null) {
            if (quickly) {
                mTouchDownViewGroup.scrollTo(0, 0);
            } else {
                ((ScrollListener) mTouchDownViewGroup).scrollSmoothTo(0, 0);
            }
            mSwipeStatus = SWIPE_STATE.SWIPE_STAT_IDLE;
            mTouchDownViewGroup = null;
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
        } else {
            Log.e(TAG, ">>>>>scrollBack  NULL pointer");
        }
    }

    public SWIPE_STATE getmSwipeStatus() {
        return mSwipeStatus;
    }

    private View getFocusChild(int x, int y) {
        View child = null;
        Rect rect = new Rect();
        int[] listViewCoords = new int[2];
        mListView.getLocationOnScreen(listViewCoords);
        int view_x = (int) x - listViewCoords[0];
        int view_y = (int) y - listViewCoords[1];
        int childCount = mListView.getChildCount();

        for (int i = 0; i < childCount; i++) {
            child = mListView.getChildAt(i);
            child.getHitRect(rect);
            if (rect.contains(view_x, view_y)) {
                // if (child instanceof
                // LenovoListViewSlideMenuGroupViewMaterial) {
                // return child;
                // }
            }
        }
        return null;
    }

    /**
     * set Slide Function Enabled or not
     * 
     * @param isTrue
     */
    public void setSlideFunctionEnable(boolean isTrue) {
        if (mTouchDownViewGroup != null) {
            scrollBack(true);
        }
        mSlideEnabled = isTrue;
        if (DEBUG) {
            Log.i(TAG, ">>>>>mSlideEnabled=" + mSlideEnabled);
        }
    }

    /**
     * get Slide Function Enabled or not
     * 
     * @return
     */
    public boolean getSlideFunctionEnable() {
        return mSlideEnabled;
    }
}
