
package com.codezyw.backuprestore;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class SlideMenuListener implements View.OnTouchListener {
    private String TAG = "zyw";
    private boolean DEBUG = false;
    public static final int SWIPE_STAT_IDLE = 0;
    public static final int SWIPE_STAT_PROCESS= 1;
    public static final int SWIPE_STAT_SHOW_MENU = 2;

    private Context mContext;
    private ListView mListView;
    private float mDownX;
    private float mDownY;
    private int mSwipeStatus = SWIPE_STAT_IDLE;
    private SlideMenuGroup mTouchDownListItemViewGroup = null;
    private int mTouchSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private VelocityTracker mVelocityTracker;
    private boolean mSlideEnabled = true;

    public SlideMenuListener(Context context, ListView listView) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mListView = listView;
        ViewConfiguration vc = ViewConfiguration.get(mContext);
        mTouchSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        if (DEBUG) {
            Log.i(TAG, ">>>>>mMinFlingVelocity="+mMinFlingVelocity+", mMaxFlingVelocity="+mMaxFlingVelocity);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        if (!mSlideEnabled) {
            if (mTouchDownListItemViewGroup != null) {
                scrollBack(true);
            }
            return false;
        }
        final int action = event.getAction();
        
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_DOWN, touch="+event.getRawX()+"+"+event.getRawY()+", mSwipeStatus="+mSwipeStatus);
                }
                if (mSwipeStatus == SWIPE_STAT_IDLE ) {
                    mDownX = event.getRawX();
                    mDownY = event.getRawY();
                    View downV = getFocusChild((int)mDownX, (int)mDownY);
                    if (downV != null && downV instanceof SlideMenuGroup) {
                        mTouchDownListItemViewGroup = (SlideMenuGroup) downV;
                        if (mVelocityTracker != null) {
                            mVelocityTracker.recycle();
                            mVelocityTracker = null;
                        }
                        if (mVelocityTracker == null) {
                            mVelocityTracker = VelocityTracker.obtain();
                        }
                        mVelocityTracker.addMovement(event);
                    }
                } else if (mSwipeStatus == SWIPE_STAT_SHOW_MENU) {
                    scrollBack(true);
                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mListView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                    return true;
                } else if (mSwipeStatus == SWIPE_STAT_PROCESS) {
                    scrollBack(true);
                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mListView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_MOVE, touch="+event.getRawX()+"+"+event.getRawY()+", mSwipeStatus="+mSwipeStatus);
                }
                if (mSwipeStatus == SWIPE_STAT_IDLE && mTouchDownListItemViewGroup != null) {
                    if (Math.abs(mDownX - event.getRawX()) > mTouchSlop && Math.abs(mDownY - event.getRawY()) < mTouchSlop) {
                        mSwipeStatus = SWIPE_STAT_PROCESS;
                    }
                }
                
                if (mSwipeStatus == SWIPE_STAT_PROCESS) {
                    mListView.requestDisallowInterceptTouchEvent(true);

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (event.getActionIndex()
                            << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mListView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                    
                    int deltaX = (int) (mDownX - event.getRawX());
                    if (DEBUG) {
                        Log.i(TAG, ">>>>>onTouch ACTION_MOVE, touch="+event.getRawX()+", mDownX="+mDownX);
                    }
                    if (deltaX > 0) {
                        //swipe to left direction
                        deltaX = Math.min(deltaX, mTouchDownListItemViewGroup.getRightMenuWidth());
                        if (mTouchDownListItemViewGroup.isRightMenuHide()) {
                            deltaX = 0;
                        }
                    } else {
                        //swipe to right direction
                        deltaX = Math.max(deltaX, -mTouchDownListItemViewGroup.getLeftMenuWidth());
                        if (mTouchDownListItemViewGroup.isLeftMenuHide()) {
                            deltaX = 0;
                        }
                    }
                    mTouchDownListItemViewGroup.scrollTo(deltaX, 0);
                    if (mVelocityTracker != null) {
                        mVelocityTracker.addMovement(event);
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_UP, touch="+event.getRawX()+"+"+event.getRawY()+", mSwipeStatus="+mSwipeStatus);
                }
                if (mSwipeStatus == SWIPE_STAT_PROCESS) {
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
                            Log.i(TAG, ">>>>>onTouch ACTION_UP, Velocity="+v_x+"+"+v_y);
                        }
                    }
                    
                    if (deltaX > 0 && (deltaX > (mTouchDownListItemViewGroup.getRightMenuWidth() / 2) || (velocityEnoughToFling && v_x < 0))) {
                        //swipe to left direction
                        if (mTouchDownListItemViewGroup.isRightMenuHide()) {
                            scrollBack();
                            break;
                        }
                        deltaX = mTouchDownListItemViewGroup.getRightMenuWidth();
                        mTouchDownListItemViewGroup.scrollSmoothTo(deltaX, 0);
                        mSwipeStatus = SWIPE_STAT_SHOW_MENU;
                        mTouchDownListItemViewGroup.setMenuClickListener(new SlideMenuGroup.OnMenuClickListener() {
                            
                            @Override
                            public void onMenuClicked(SlideMenuGroup listItemViewGroup) {
                                // TODO Auto-generated method stub
                                scrollBack();
                            }
                        });
                    } else if (deltaX < 0 && (deltaX < (-mTouchDownListItemViewGroup.getLeftMenuWidth() / 2) || (velocityEnoughToFling && v_x > 0))) {
                        //swipe to right direction
                        if (mTouchDownListItemViewGroup.isLeftMenuHide()) {
                            scrollBack();
                            break;
                        }
                        deltaX = -mTouchDownListItemViewGroup.getLeftMenuWidth();
                        mTouchDownListItemViewGroup.scrollSmoothTo(deltaX, 0);
                        mSwipeStatus = SWIPE_STAT_SHOW_MENU;
                        mTouchDownListItemViewGroup.setMenuClickListener(new SlideMenuGroup.OnMenuClickListener() {
                            
                            @Override
                            public void onMenuClicked(SlideMenuGroup listItemViewGroup) {
                                // TODO Auto-generated method stub
                                scrollBack();
                            }
                        });
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
        return false;
    }
    
    /**
     * scrollBack menu view, default is scroll smoothly
     */
    private void scrollBack() {
        scrollBack(false);
    }
    
    /**
     * scrollBack menu view
     * @param quickly
     */
    private void scrollBack(boolean quickly) {
        if (mTouchDownListItemViewGroup != null) {
            if (quickly) {
                mTouchDownListItemViewGroup.scrollTo(0, 0);
            } else {
                mTouchDownListItemViewGroup.scrollSmoothTo(0, 0);
            }
            mTouchDownListItemViewGroup.setMenuClickListener(null);
            mSwipeStatus = SWIPE_STAT_IDLE;
            mTouchDownListItemViewGroup = null;
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
        } else {
            Log.e(TAG, ">>>>>scrollBack  NULL pointer");
        }
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
                if (child instanceof SlideMenuGroup) {
                    return child;
                }
            }
        }
        return null;
    }
    
    /**
     * set Slide Function Enabled or not
     * @param isTrue
     */
    public void setSlideFunctionEnable(boolean isTrue) {
        if (mTouchDownListItemViewGroup != null) {
            scrollBack(true);
        }
        mSlideEnabled = isTrue;
        if (DEBUG) {
            Log.i(TAG, ">>>>>mSlideEnabled="+mSlideEnabled);
        }
    }
    
    /**
     * get Slide Function Enabled or not
     * @return
     */
    public boolean getSlideFunctionEnable() {
        return mSlideEnabled;
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
