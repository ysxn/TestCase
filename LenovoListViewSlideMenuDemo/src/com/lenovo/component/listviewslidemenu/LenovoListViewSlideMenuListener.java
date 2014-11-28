
package com.lenovo.component.listviewslidemenu;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * @deprecated This API has not ripple effect.  Do not use. Should use LenovoListViewSlideMenuListenerMaterial
 * @author zhuyw1
 *
 */
public class LenovoListViewSlideMenuListener implements View.OnTouchListener {
    private static final String TAG = "LenovoSlidemenuListViewListener";
    private static boolean DEBUG = false;
    private ListView mListView;
    private OnSlideMenuClickListener mOnSlideMenuClickListener;
    private LenovoListViewSlideMenuGroupView mDownView;
    private LenovoListViewSlideMenuGroupView mSelectView;
    private VelocityTracker mVelocityTracker;
    private View mMenuItemView;

    private int mScrollState;
    private int mTouchSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;

    private float mDownX;
    private float mDownY;
    private float mTouchX;
    private float mTouchY;

    private boolean bSliding;
    private boolean bIntercepTouch;
    private boolean bMenuTouch;

    public LenovoListViewSlideMenuListener(ListView listView, OnSlideMenuClickListener listener) {
        mListView = listView;
        mOnSlideMenuClickListener = listener;
        ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mScrollState = LenovoListViewSlideMenuGroupView.SLIDE_STATE_IDLE;
    }

    public interface OnSlideMenuClickListener {
        void onClick(ListView parent, View view, int position);
    }

    private void registerOnScrollListener() {
        if (mDownView == null) {
            return;
        }

        mDownView
                .registerSlideSectionListener(new LenovoListViewSlideMenuGroupView.OnSlideSectionListener() {

                    @Override
                    public void onSlideStateChanged(LenovoListViewSlideMenuGroupView view, int state) {
                        if (DEBUG) {
                            Log.i(TAG, "####onSlideStateChanged state:"+state);
                        }
                        // TODO Auto-generated method stub
                        mScrollState = state;
                        if (mScrollState == LenovoListViewSlideMenuGroupView.SLIDE_STATE_SETTING) {
                            mSelectView = view;
                        } else {
                            mSelectView = null;
                        }
                    }
                });
    }

    public void abortSlide() {
        // bForceAbort = true;
        if (mScrollState == LenovoListViewSlideMenuGroupView.SLIDE_STATE_SETTING) {
            if (mMenuItemView != null
                    && mMenuItemView.isSelected()) {
                mMenuItemView.setSelected(false);
            }
            mSelectView.resetState();
        }

        mDownView = null;
        bSliding = false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mTouchX = event.getRawX();
        mTouchY = event.getRawY();

        if (DEBUG) {
            Log.i(TAG,"####onTouch:"+event.getAction()+", mScrollState:"+mScrollState);
        }

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (!bIntercepTouch
                        && (mScrollState == LenovoListViewSlideMenuGroupView.SLIDE_STATE_SCROLLING)) {
                    bIntercepTouch = true;
                }

                if (!bIntercepTouch
                        && (mScrollState == LenovoListViewSlideMenuGroupView.SLIDE_STATE_SETTING)) {

                    bIntercepTouch = true;
                    mMenuItemView = mSelectView.getTouchMenu((int) mTouchX, (int) mTouchY);
                    if (mMenuItemView == null) {
                        mSelectView.cancelScroll();
                    } else {
                        bMenuTouch = true;
                        mMenuItemView.setSelected(true);
                    }
                }

                if (bIntercepTouch) {
                    return true;
                }

                View downView = getFocusChild((int) mTouchX,
                        (int) mTouchY);
                if (downView != null && downView instanceof LenovoListViewSlideMenuGroupView) {
                    mDownView = (LenovoListViewSlideMenuGroupView) downView;
                }
                if (mDownView != null) {
                    // mDownPosition = mListView.getPositionForView(mDownView);
                    if (DEBUG) {
                        Log.i(TAG, "#####mDownView:" + mDownView);
                    }
                    mDownX = mTouchX;
                    mDownY = mTouchY;
                    if (mVelocityTracker == null) {
                        mVelocityTracker = VelocityTracker.obtain();
                    }
                    mVelocityTracker.addMovement(event);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaX_move = mTouchX - mDownX;
                float deltaY_move = mTouchY - mDownY;

                if (bIntercepTouch) {
                    if (bMenuTouch
                            && mScrollState == LenovoListViewSlideMenuGroupView.SLIDE_STATE_SETTING) {
                        if (mMenuItemView != null
                                && (mMenuItemView != mSelectView.getTouchMenu((int) mTouchX,
                                        (int) mTouchY))) {
                            mMenuItemView.setSelected(false);
                        }
                    }
                    return true;
                }

                if (mDownView == null
                        || mVelocityTracker == null) {
                    break;
                }

                mVelocityTracker.addMovement(event);

                if (!bSliding
                        && Math.abs(deltaY_move) > mTouchSlop) {
                    mDownView = null;
                    break;
                }

                if (!bSliding
                        && Math.abs(deltaX_move) > mTouchSlop) {
                    bSliding = true;
                }

                if (bSliding) {
                    if (DEBUG) {
                        Log.i(TAG, "####touchScroll");
                    }
                    mListView.requestDisallowInterceptTouchEvent(true);

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mListView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();

                    mDownView.touchScroll((int) deltaX_move);
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                boolean bIntercepInternel = false;
                if (bIntercepTouch || bSliding) {
                    if (bIntercepTouch
                            && bMenuTouch
                            && (mScrollState == LenovoListViewSlideMenuGroupView.SLIDE_STATE_SETTING)) {
                        if (mMenuItemView != null
                                && (mMenuItemView.isSelected())) {
                            mMenuItemView.setSelected(false);
                            if (DEBUG) {
                                Log.i(TAG, "#####mMenuItemView click:mSelectView="+mSelectView);
                            }
                            if (mOnSlideMenuClickListener != null) {
                                int position = ((Integer) mMenuItemView.getTag()).intValue();
                                if (DEBUG) {
                                    Log.i(TAG, "#####mMenuItemView click: mSelectView="+mSelectView);
                                }
                                mOnSlideMenuClickListener.onClick(mListView, mSelectView, position);
                            }
                            mSelectView.resetState();
                        }
                    }
                    bIntercepInternel = true;
                }

                if (!bIntercepTouch
                        && bSliding
                        && mDownView != null
                        && mVelocityTracker != null) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                    int v_x = (int) mVelocityTracker.getXVelocity();
                    int v_y = (int) mVelocityTracker.getYVelocity();
                    registerOnScrollListener();
                    mDownView.touchCancelScroll(v_x, v_y);
                    bIntercepInternel = true;
                }

                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                bIntercepTouch = false;
                bMenuTouch = false;
                mDownView = null;
                bSliding = false;

                if (bIntercepInternel) {
                    return true;
                }
                break;

            default:
                break;
        }

        return false;
    }

    private View getFocusChild(int x, int y) {
        View child = null;
        Rect rect = new Rect();
        int[] listViewCoords = new int[2];
        mListView.getLocationOnScreen(listViewCoords);
        int view_x = (int) x - listViewCoords[0];
        int view_y = (int) y - listViewCoords[1];
        int childCount = mListView.getChildCount();
        if (DEBUG) {
            Log.i(TAG, "####childCount:"+childCount);
        }

        for (int i = 0; i < childCount; i++) {
            child = mListView.getChildAt(i);
            child.getHitRect(rect);
            if (rect.contains(view_x, view_y)) {
                if (DEBUG) {
                    Log.i(TAG, "####Focus: "+i);
                }
                if (child instanceof LenovoListViewSlideMenuGroupView) {
                    return child;
                }
            }
        }

        return null;
    }
    
    public void setDebugLog(boolean isTrue) {
        DEBUG = isTrue;
    }
}
