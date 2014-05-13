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

import android.content.Context;
//import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
//import android.support.v4.view.VelocityTrackerCompat;
import android.view.VelocityTracker;
//import android.support.v4.view.ViewCompat;
import android.view.View;
//import android.support.v4.widget.ScrollerCompat;
import android.widget.Scroller;
import android.view.*;
import android.view.animation.Interpolator;


import java.util.Arrays;

/**
 * ViewDragHelper is a utility class for writing custom ViewGroups. It offers a
 * number of useful operations and state tracking for allowing a user to drag
 * and reposition views within their parent ViewGroup.
 */
public class ViewDragHelper {
    private boolean DEBUG = true;
    private static final String TAG = "zyw";

    /**
     * A null/invalid pointer ID.
     */
    public static final int INVALID_POINTER = -1;

    /**
     * A view is not currently being dragged or animating as a result of a
     * fling/snap.
     */
    public static final int STATE_IDLE = 0;

    /**
     * A view is currently being dragged. The position is currently changing as
     * a result of user input or simulated user input.
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * A view is currently settling into place as a result of a fling or
     * predefined non-interactive motion.
     */
    public static final int STATE_SETTLING = 2;

    /**
     * Edge flag indicating that the left edge should be affected.
     */
    public static final int EDGE_LEFT = 1 << 0;

    /**
     * Edge flag indicating that the right edge should be affected.
     */
    public static final int EDGE_RIGHT = 1 << 1;

    /**
     * Edge flag indicating that the bottom edge should be affected.
     */
    public static final int EDGE_BOTTOM = 1 << 3;

    /**
     * Edge flag set indicating all edges should be affected.
     */
    public static final int EDGE_ALL = EDGE_LEFT | EDGE_RIGHT | EDGE_BOTTOM;

    /**
     * Indicates that a check should occur along the horizontal axis
     */
    public static final int DIRECTION_HORIZONTAL = 1 << 0;

    /**
     * Indicates that a check should occur along the vertical axis
     */
    public static final int DIRECTION_VERTICAL = 1 << 1;

    /**
     * Indicates that a check should occur along all axes
     */
    public static final int DIRECTION_ALL = DIRECTION_HORIZONTAL | DIRECTION_VERTICAL;

    /**
     * unit is dip , 20 dip
     */
    public static final int EDGE_SIZE = 20; // dip

    /**
     * unit is ms , 256 ms
     */
    private static final int BASE_SETTLE_DURATION = 256; // ms

    /**
     * unit is ms , 600 ms
     */
    private static final int MAX_SETTLE_DURATION = 600; // ms

    // Current drag state; idle, dragging or settling
    /**
     * current drag status, may be STATE_IDLE , STATE_DRAGGING, STATE_SETTLING
     * @see #STATE_IDLE
     * @see #STATE_DRAGGING
     * @see #STATE_SETTLING
     */
    private int mDragState;

    // Distance to travel before a drag may begin
    /**
     * The minimum distance in pixels that the user must travel to initiate a drag
     */
    private int mTouchSlop;

    // Last known position/pointer tracking
    /**
     * Set this in tryCaptureViewForDrag, and captureChildView.
     * Reset this in cancel
     * @see #tryCaptureViewForDrag
     * @see #captureChildView
     * @see #cancel
     */
    private int mActivePointerId = INVALID_POINTER;

    /**
     * call from {@link #shouldInterceptTouchEvent(MotionEvent)} or {@link #processTouchEvent(MotionEvent)}, save x, y when MotionEvent.ACTION_DOWN
     * Index is pointerId
     * @see #shouldInterceptTouchEvent(MotionEvent)
     * @see #processTouchEvent(MotionEvent)
     */
    private float[] mInitialMotionX;

    /**
     * call from {@link #shouldInterceptTouchEvent(MotionEvent)} or {@link #processTouchEvent(MotionEvent)}, save x, y when MotionEvent.ACTION_DOWN
     * Index is pointerId
     * @see #shouldInterceptTouchEvent(MotionEvent)
     * @see #processTouchEvent(MotionEvent)
     */
    private float[] mInitialMotionY;

    /**
     * call from {@link #shouldInterceptTouchEvent(MotionEvent)} or {@link #processTouchEvent(MotionEvent)} ,save x, y when MotionEvent.ACTION_MOVE
     * Index is pointerId
     * @see #shouldInterceptTouchEvent(MotionEvent)
     * @see #processTouchEvent(MotionEvent)
     */
    private float[] mLastMotionX;

    /**
     * call from {@link #shouldInterceptTouchEvent(MotionEvent)} or {@link #processTouchEvent(MotionEvent)}, save x, y when MotionEvent.ACTION_MOVE
     * Index is pointerId
     * @see #shouldInterceptTouchEvent(MotionEvent)
     * @see #processTouchEvent(MotionEvent)
     */
    private float[] mLastMotionY;

    /**
     * record Initial touch position (x, y) is outside or can cause a drag sequence
     * @param x
     * @param y
     * @return EDGE_LEFT or EDGE_TOP or EDGE_RIGHT or EDGE_BOTTOM
     * @see #getEdgesTouched
     * @see #EDGE_LEFT
     * @see #EDGE_TOP
     * @see #EDGE_RIGHT
     * @see #EDGE_BOTTOM
     */
    private int[] mInitialEdgesTouched;

    /**
     * 32 bit record every pointer's down happened
     * @see #saveInitialMotion
     */
    private int mPointersDown;

    private VelocityTracker mVelocityTracker;

    private float mMaxVelocity;

    private float mMinVelocity;

    /**
     * this is the size of an edge. This is the range in pixels along the edges
     * of this view that will actively detect edge touches or drags if edge
     * tracking is enabled.
     * 
     * The size of an edge in pixels
     * @see #EDGE_SIZE
     */
    private int mEdgeSize;

    private Scroller mScroller;

    /**
     * call back go to SwipeBackLayout
     */
    private final Callback mCallback;

    /**
     * set this value in {@link #tryCaptureViewForDrag(View, int)}., which is compute by {@link #findTopChildUnder(int, int)}. 
     * reset this value in {@link #setDragState(int)}
     * 
     * default this is the origin child of DecorView at(0) , same as {@link com.example.testmiui.SwipeBackLayout#mContentView }
     */
    private View mCapturedView;

    /**
     * this is likely lock value, set in dispatchViewReleased, used in flingCapturedView and settleCapturedViewAt
     * @see #dispatchViewReleased
     * @see #flingCapturedView
     * @see #settleCapturedViewAt
     */
    private boolean mReleaseInProgress;

    /**
     * passed SwipeBackLayout, which replace activity's DecorView's first child
     */
    private final SwipeBackLayout mParentView;

    /**
     * A Callback is used as a communication channel with the ViewDragHelper
     * back to the parent view using it. <code>on*</code>methods are invoked on
     * siginficant events and several accessor methods are expected to provide
     * the ViewDragHelper with more information about the state of the parent
     * view upon request. The callback also makes decisions governing the range
     * and draggability of child views.
     * 
     * Implement by SwipeBackLayout
     */
    public static abstract class Callback {
        /**
         * Called when the captured view's position changes as the result of a
         * drag or settle.
         * 
         * @param changedView View whose position changed
         * @param left New X coordinate of the left edge of the view
         * @param top New Y coordinate of the top edge of the view
         * @param dx Change in X position from the last call
         * @param dy Change in Y position from the last call
         */
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
        }

        /**
         * Called when the child view is no longer being actively dragged. The
         * fling velocity is also supplied, if relevant. The velocity values may
         * be clamped to system minimums or maximums.
         * <p>
         * Calling code may decide to fling or otherwise release the view to let
         * it settle into place. It should do so using
         * {@link #releaseCapturedViewAt(int, int)}
         * If the Callback
         * invokes one of these methods, the ViewDragHelper will enter
         * {@link #STATE_SETTLING} and the view capture will not fully end until
         * it comes to a complete stop. If neither of these methods is invoked
         * before <code>onViewReleased</code> returns, the view will stop in
         * place and the ViewDragHelper will return to {@link #STATE_IDLE}.
         * </p>
         * 
         * @param releasedChild The captured child view now being released
         */
        public void onViewReleased(View releasedChild) {
        }

        /**
         * Called when the user's input indicates that they want to capture the
         * given child view with the pointer indicated by pointerId. The
         * callback should return true if the user is permitted to drag the
         * given view with the indicated pointer.
         * <p>
         * ViewDragHelper may call this method multiple times for the same view
         * even if the view is already captured; this indicates that a new
         * pointer is trying to take control of the view.
         * </p>
         * 
         * @param child Child the user is attempting to capture
         * @param pointerId ID of the pointer attempting the capture
         * @return true if capture should be allowed, false otherwise
         */
        public abstract boolean tryCaptureView(View child, int pointerId);
    }

    /**
     * Interpolator defining the animation curve for mScroller
     */
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private final Runnable mSetIdleRunnable = new Runnable() {
        public void run() {
            setDragState(STATE_IDLE);
        }
    };

    /**
     * Factory method to create a new ViewDragHelper.
     * 
     * @param forParent Parent view to monitor
     * @param cb Callback to provide information and receive events
     * @return a new ViewDragHelper instance
     */
    public static ViewDragHelper create(SwipeBackLayout forParent, Callback cb) {
        return new ViewDragHelper(forParent.getContext(), forParent, cb);
    }

    /**
     * Factory method to create a new ViewDragHelper.
     * 
     * @param forParent Parent view to monitor
     * @param sensitivity Multiplier for how sensitive the helper should be
     *            about detecting the start of a drag. Larger values are more
     *            sensitive. 1.0f is normal.
     * @param cb Callback to provide information and receive events
     * @return a new ViewDragHelper instance
     */
    public static ViewDragHelper create(SwipeBackLayout forParent, float sensitivity, Callback cb) {
        final ViewDragHelper helper = create(forParent, cb);
        helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
        return helper;
    }

    /**
     * Apps should use ViewDragHelper.create() to get a new instance. This will
     * allow VDH to use internal compatibility implementations for different
     * platform versions.
     * 
     * @param context Context to initialize config-dependent params from
     * @param forParent Parent view to monitor
     */
    private ViewDragHelper(Context context, SwipeBackLayout forParent, Callback cb) {
        if (forParent == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (cb == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }

        mParentView = forParent;
        mCallback = cb;

        final ViewConfiguration vc = ViewConfiguration.get(context);
        final float density = context.getResources().getDisplayMetrics().density;
        mEdgeSize = (int) (EDGE_SIZE * density + 0.5f);

        mTouchSlop = vc.getScaledTouchSlop();
        mMaxVelocity = vc.getScaledMaximumFlingVelocity();
        mMinVelocity = vc.getScaledMinimumFlingVelocity();
        mScroller = new Scroller(context, sInterpolator);
    }

    /**
     * Set the minimum velocity that will be detected as having a magnitude
     * greater than zero in pixels per second. Callback methods accepting a
     * velocity will be clamped appropriately.
     * 
     * @param minVel Minimum velocity to detect
     */
    public void setMinVelocity(float minVel) {
        mMinVelocity = minVel;
    }

    /**
     * Retrieve the current drag state of this helper. This will return one of
     * {@link #STATE_IDLE}, {@link #STATE_DRAGGING} or {@link #STATE_SETTLING}.
     * 
     * @return The current drag state
     */
    public int getViewDragState() {
        return mDragState;
    }

    /**
     * Set the size of an edge. This is the range in pixels along the edges of
     * this view that will actively detect edge touches or drags if edge
     * tracking is enabled.
     * 
     * @param size The size of an edge in pixels
     */
    public void setEdgeSize(int size) {
        mEdgeSize = size;
    }

    /**
     * The result of a call to this method is equivalent to
     * {@link #processTouchEvent(android.view.MotionEvent)} receiving an
     * ACTION_CANCEL event.
     */
    public void cancel() {
        mActivePointerId = INVALID_POINTER;
        clearMotionHistory();

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * {@link #cancel()}, but also abort all motion in progress and snap to the
     * end of any animation.
     */
    public void abort() {
        cancel();
        if (mDragState == STATE_SETTLING) {
            final int oldX = mScroller.getCurrX();
            final int oldY = mScroller.getCurrY();
            mScroller.abortAnimation();
            final int newX = mScroller.getCurrX();
            final int newY = mScroller.getCurrY();
            mCallback.onViewPositionChanged(mCapturedView, newX, newY, newX - oldX, newY - oldY);
        }
        setDragState(STATE_IDLE);
    }

    /**
     * clear value of Arrays for below
     * @see #mInitialMotionX
     * @see #mInitialMotionY
     * @see #mLastMotionX
     * @see #mLastMotionY
     * @see #mInitialEdgesTouched
     * @see #mEdgeDragsInProgress
     * @see #mEdgeDragsLocked
     * @see #mPointersDown
     */
    private void clearMotionHistory() {
        if (mInitialMotionX == null) {
            return;
        }
        Arrays.fill(mInitialMotionX, 0);
        Arrays.fill(mInitialMotionY, 0);
        Arrays.fill(mLastMotionX, 0);
        Arrays.fill(mLastMotionY, 0);
        Arrays.fill(mInitialEdgesTouched, 0);
        mPointersDown = 0;
    }

    /**
     * this is used to check length of arrays for below, if  <= pointerId, extend the arrays.
     * @param pointerId
     * @see #mInitialMotionX
     * @see #mInitialMotionY
     * @see #mLastMotionX
     * @see #mLastMotionY
     * @see #mInitialEdgesTouched
     * @see #mEdgeDragsInProgress
     * @see #mEdgeDragsLocked
     * @see #mPointersDown
     */
    private void ensureMotionHistorySizeForId(int pointerId) {
        if (mInitialMotionX == null || mInitialMotionX.length <= pointerId) {
            float[] imx = new float[pointerId + 1];
            float[] imy = new float[pointerId + 1];
            float[] lmx = new float[pointerId + 1];
            float[] lmy = new float[pointerId + 1];
            int[] iit = new int[pointerId + 1];

            if (mInitialMotionX != null) {
                /**
                 * Copies length elements from the array src, starting at offset srcPos, into the array dst, starting at offset dstPos.
                 */
                System.arraycopy(mInitialMotionX, 0, imx, 0, mInitialMotionX.length);
                System.arraycopy(mInitialMotionY, 0, imy, 0, mInitialMotionY.length);
                System.arraycopy(mLastMotionX, 0, lmx, 0, mLastMotionX.length);
                System.arraycopy(mLastMotionY, 0, lmy, 0, mLastMotionY.length);
                System.arraycopy(mInitialEdgesTouched, 0, iit, 0, mInitialEdgesTouched.length);
            }

            mInitialMotionX = imx;
            mInitialMotionY = imy;
            mLastMotionX = lmx;
            mLastMotionY = lmy;
            mInitialEdgesTouched = iit;
        }
    }

    /**
     * call from shouldInterceptTouchEvent or processTouchEvent, save x, y when MotionEvent.ACTION_DOWN
     * @param x
     * @param y
     * @param pointerId
     * @see #shouldInterceptTouchEvent(MotionEvent)
     * @see #processTouchEvent(MotionEvent)
     */
    private void saveInitialMotion(float x, float y, int pointerId) {
        ensureMotionHistorySizeForId(pointerId);
        mInitialMotionX[pointerId] = mLastMotionX[pointerId] = x;
        mInitialMotionY[pointerId] = mLastMotionY[pointerId] = y;
        mInitialEdgesTouched[pointerId] = getEdgesTouched((int) x, (int) y);
        mPointersDown |= 1 << pointerId;
    }

    /**
     * call from shouldInterceptTouchEvent or processTouchEvent, save x, y when MotionEvent.ACTION_MOVE
     * @param ev
     * @see #shouldInterceptTouchEvent(MotionEvent)
     * @see #processTouchEvent(MotionEvent)
     */
    private void saveLastMotion(MotionEvent ev) {
        final int pointerCount = ev.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            final int pointerId = ev.getPointerId(i);
            final float x = ev.getX(i);
            final float y = ev.getY(i);
            mLastMotionX[pointerId] = x;
            mLastMotionY[pointerId] = y;
        }
    }

    /**
     * Check if the given pointer ID represents a pointer that is currently down
     * (to the best of the ViewDragHelper's knowledge).
     * <p>
     * The state used to report this information is populated by the methods
     * {@link #shouldInterceptTouchEvent(android.view.MotionEvent)} or
     * {@link #processTouchEvent(android.view.MotionEvent)}. If one of these
     * methods has not been called for all relevant MotionEvents to track, the
     * information reported by this method may be stale or incorrect.
     * </p>
     * 
     * @param pointerId pointer ID to check; corresponds to IDs provided by
     *            MotionEvent
     * @return true if the pointer with the given ID is still down
     */
    public boolean isPointerDown(int pointerId) {
        return (mPointersDown & 1 << pointerId) != 0;
    }

    void setDragState(int state) {
        if (mDragState != state) {
            mDragState = state;
            if (state == STATE_IDLE) {
                mCapturedView = null;
            }
        }
    }

    private int computeSettleDuration(View child, int dx, int dy, int xvel, int yvel) {
        xvel = clampMag(xvel, (int) mMinVelocity, (int) mMaxVelocity);
        yvel = clampMag(yvel, (int) mMinVelocity, (int) mMaxVelocity);
        final int absDx = Math.abs(dx);
        final int absDy = Math.abs(dy);
        final int absXVel = Math.abs(xvel);
        final int absYVel = Math.abs(yvel);
        final int addedVel = absXVel + absYVel;
        final int addedDistance = absDx + absDy;

        final float xweight = xvel != 0 ? (float) absXVel / addedVel : (float) absDx
                / addedDistance;
        final float yweight = yvel != 0 ? (float) absYVel / addedVel : (float) absDy
                / addedDistance;

        int xduration = computeAxisDuration(dx, xvel, (mParentView.mEdgeFlag & (ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_RIGHT)));
        int yduration = computeAxisDuration(dy, yvel, (mParentView.mEdgeFlag & ViewDragHelper.EDGE_BOTTOM));

        return (int) (xduration * xweight + yduration * yweight);
    }

    private int computeAxisDuration(int delta, int velocity, int motionRange) {
        if (delta == 0) {
            return 0;
        }

        final int width = mParentView.getWidth();
        final int halfWidth = width / 2;
        float distanceRatio = Math.min(1f, (float) Math.abs(delta) / width);
        
        //start
        distanceRatio -= 0.5f; // center the values about 0.
        distanceRatio *= 0.3f * Math.PI / 2.0f;
        final float distanceInfluenceForSnapDuration = (float) Math.sin(distanceRatio);
        //end
        
        final float distance = halfWidth + halfWidth
                * distanceInfluenceForSnapDuration;

        int duration;
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float range = (float) Math.abs(delta) / motionRange;
            duration = (int) ((range + 1) * BASE_SETTLE_DURATION);
        }
        return Math.min(duration, MAX_SETTLE_DURATION);
    }

    /**
     * Clamp the magnitude of value for absMin and absMax. If the value is below
     * the minimum, it will be clamped to zero. If the value is above the
     * maximum, it will be clamped to the maximum.
     * 
     * @param value Value to clamp
     * @param absMin Absolute value of the minimum significant value to return
     * @param absMax Absolute value of the maximum value to return
     * @return The clamped value with the same sign as <code>value</code>
     */
    private int clampMag(int value, int absMin, int absMax) {
        final int absValue = Math.abs(value);
        if (absValue < absMin)
            return 0;
        if (absValue > absMax)
            return value > 0 ? absMax : -absMax;
        return value;
    }

    /**
     * Move the captured settling view by the appropriate amount for the current
     * time. If <code>continueSettling</code> returns true, the caller should
     * call it again on the next frame to continue.
     * 
     * @param deferCallbacks true if state callbacks should be deferred via
     *            posted message. Set this to true if you are calling this
     *            method from {@link android.view.View#computeScroll()} or
     *            similar methods invoked as part of layout or drawing.
     * @return true if settle is still in progress
     */
    public boolean continueSettling(boolean deferCallbacks) {
        if (mDragState == STATE_SETTLING) {
            boolean keepGoing = mScroller.computeScrollOffset();
            final int x = mScroller.getCurrX();
            final int y = mScroller.getCurrY();
            final int dx = x - mCapturedView.getLeft();
            final int dy = y - mCapturedView.getTop();

            if (dx != 0) {
                mCapturedView.offsetLeftAndRight(dx);
            }
            if (dy != 0) {
                mCapturedView.offsetTopAndBottom(dy);
            }

            if (dx != 0 || dy != 0) {
                mCallback.onViewPositionChanged(mCapturedView, x, y, dx, dy);
            }

            if (keepGoing && x == mScroller.getFinalX() && y == mScroller.getFinalY()) {
                // Close enough. The interpolator/scroller might think we're
                // still moving
                // but the user sure doesn't.
                mScroller.abortAnimation();
                keepGoing = mScroller.isFinished();
            }

            if (!keepGoing) {
                if (deferCallbacks) {
                    mParentView.post(mSetIdleRunnable);
                } else {
                    setDragState(STATE_IDLE);
                }
            }
        }

        return mDragState == STATE_SETTLING;
    }

    /**
     * Like all callback events this must happen on the UI thread, but release
     * involves some extra semantics. During a release (mReleaseInProgress) is
     * the only time it is valid to call {@link #releaseCapturedViewAt(int, int)}
     * or {@link #flingCapturedView(int, int, int, int)}.
     */
    private void dispatchViewReleased() {
        mReleaseInProgress = true;
        mCallback.onViewReleased(mCapturedView);
        mReleaseInProgress = false;

        if (mDragState == STATE_DRAGGING) {
            // onViewReleased didn't call a method that would have changed this.
            // Go idle.
            setDragState(STATE_IDLE);
        }
    }

    /**
     * Settle the captured view at the given (left, top) position. The
     * appropriate velocity from prior motion will be taken into account. If
     * this method returns true, the caller should invoke
     * {@link #continueSettling(boolean)} on each subsequent frame to continue
     * the motion until it returns false. If this method returns false there is
     * no further work to do to complete the movement.
     * 
     * @param finalLeft Settled left edge position for the captured view
     * @param finalTop Settled top edge position for the captured view
     * @return true if animation should continue through
     *         {@link #continueSettling(boolean)} calls
     */
    public boolean releaseCapturedViewAt(int finalLeft, int finalTop) {
        if (!mReleaseInProgress) {
            throw new IllegalStateException("Cannot releaseCapturedViewAt outside of a call to "
                    + "Callback#onViewReleased");
        }

        int xvel = (int) mVelocityTracker.getXVelocity(mActivePointerId);
        int yvel = (int) mVelocityTracker.getYVelocity(mActivePointerId);
        final int startLeft = mCapturedView.getLeft();
        final int startTop = mCapturedView.getTop();
        final int dx = finalLeft - startLeft;
        final int dy = finalTop - startTop;

        if (dx == 0 && dy == 0) {
            // Nothing to do. Send callbacks, be done.
            mScroller.abortAnimation();
            setDragState(STATE_IDLE);
            return false;
        }

        final int duration = computeSettleDuration(mCapturedView, dx, dy, xvel, yvel);
        mScroller.startScroll(startLeft, startTop, dx, dy, duration);

        setDragState(STATE_SETTLING);
        return true;
    }
    
    /**
     * Attempt to capture the view with the given pointer ID. The callback will
     * be involved. This will put us into the "dragging" state. If we've already
     * captured this view with this pointer this method will immediately return
     * true without consulting the callback.
     * 
     * @param toCapture View to capture
     * @param pointerId Pointer to capture with
     * @return true if capture was successful
     */
    boolean tryCaptureViewForDrag(View toCapture, int pointerId) {
        if (toCapture == mCapturedView && mActivePointerId == pointerId) {
            // Already done!
            return true;
        }
        if (toCapture != null && mCallback.tryCaptureView(toCapture, pointerId)) {
            mActivePointerId = pointerId;
            if (toCapture.getParent() != mParentView) {
                throw new IllegalArgumentException("captureChildView: parameter must be a descendant "
                        + "of the ViewDragHelper's tracked parent view (" + mParentView + ")");
            }

            mCapturedView = toCapture;
            mActivePointerId = pointerId;
            setDragState(STATE_DRAGGING);
            return true;
        }
        return false;
    }

    private void dragTo(int left, int top, int dx, int dy) {
        int clampedX = left;
        int clampedY = top;
        final int oldLeft = mCapturedView.getLeft();
        final int oldTop = mCapturedView.getTop();
        int ret = 0;
        if (dx != 0) {
            ret = 0;
            if ((mParentView.mTrackingEdge & ViewDragHelper.EDGE_LEFT) != 0) {
                ret = Math.min(mCapturedView.getWidth(), Math.max(left, 0));
            } else if ((mParentView.mTrackingEdge & ViewDragHelper.EDGE_RIGHT) != 0) {
                ret = Math.min(0, Math.max(left, -mCapturedView.getWidth()));
            }
            clampedX = ret;
            mCapturedView.offsetLeftAndRight(clampedX - oldLeft);
        }
        if (dy != 0) {
            ret = 0;
            if ((mParentView.mTrackingEdge & ViewDragHelper.EDGE_BOTTOM) != 0) {
                ret = Math.min(0, Math.max(top, -mCapturedView.getHeight()));
            }
            clampedY = ret;
            mCapturedView.offsetTopAndBottom(clampedY - oldTop);
        }

        if (dx != 0 || dy != 0) {
            final int clampedDx = clampedX - oldLeft;
            final int clampedDy = clampedY - oldTop;
            mCallback.onViewPositionChanged(mCapturedView, clampedX, clampedY, clampedDx, clampedDy);
        }
    }

    /**
     * Check if this event as provided to the parent view's
     * onInterceptTouchEvent should cause the parent to intercept the touch
     * event stream.
     * 
     * @param ev MotionEvent provided to onInterceptTouchEvent
     * @return true if the parent view should return true from
     *         onInterceptTouchEvent
     */
    public boolean shouldInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            // Reset things for a new event stream, just in case we didn't get
            // the whole previous stream.
            cancel();
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                final int pointerId = ev.getPointerId(0);
                saveInitialMotion(x, y, pointerId);

                final View toCapture = findTopChildUnder((int) x, (int) y);

                // Catch a settling view if possible.
                if (toCapture == mCapturedView && mDragState == STATE_SETTLING) {
                    tryCaptureViewForDrag(toCapture, pointerId);
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                // First to cross a touch slop over a draggable view wins. Also
                // report edge drags.
                final int pointerCount = ev.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    final int pointerId = ev.getPointerId(i);
                    final float x = ev.getX(i);
                    final float y = ev.getY(i);
                    final float dx = x - mInitialMotionX[pointerId];
                    final float dy = y - mInitialMotionY[pointerId];

                    if (mDragState == STATE_DRAGGING) {
                        // Callback might have started an edge drag
                        break;
                    }

                    final View toCapture = findTopChildUnder((int) x, (int) y);
                    if (toCapture != null && checkTouchSlop(toCapture, dx, dy)
                            && tryCaptureViewForDrag(toCapture, pointerId)) {
                        break;
                    }
                }
                saveLastMotion(ev);
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                cancel();
                break;
            }
        }

        return mDragState == STATE_DRAGGING;
    }

    /**
     * Process a touch event received by the parent view. This method will
     * dispatch callback events as needed before returning. The parent view's
     * onTouchEvent implementation should call this.
     * 
     * @param ev The touch event received by the parent view
     */
    public void processTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            // Reset things for a new event stream, just in case we didn't get
            // the whole previous stream.
            cancel();
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                final int pointerId = ev.getPointerId(0);
                final View toCapture = findTopChildUnder((int) x, (int) y);

                saveInitialMotion(x, y, pointerId);

                // Since the parent is already directly processing this touch
                // event,
                // there is no reason to delay for a slop before dragging.
                // Start immediately if possible.
                tryCaptureViewForDrag(toCapture, pointerId);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (mDragState == STATE_DRAGGING) {
                    final int index = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(index);
                    final float y = ev.getY(index);
                    final int idx = (int) (x - mLastMotionX[mActivePointerId]);
                    final int idy = (int) (y - mLastMotionY[mActivePointerId]);

                    dragTo(mCapturedView.getLeft() + idx, mCapturedView.getTop() + idy, idx, idy);

                    saveLastMotion(ev);
                } else {
                    // Check to see if any pointer is now over a draggable view.
                    final int pointerCount = ev.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        final int pointerId = ev.getPointerId(i);
                        final float x = ev.getX(i);
                        final float y = ev.getY(i);
                        final float dx = x - mInitialMotionX[pointerId];
                        final float dy = y - mInitialMotionY[pointerId];

                        if (mDragState == STATE_DRAGGING) {
                            // Callback might have started an edge drag.
                            break;
                        }

                        final View toCapture = findTopChildUnder((int) x, (int) y);
                        if (checkTouchSlop(toCapture, dx, dy)
                                && tryCaptureViewForDrag(toCapture, pointerId)) {
                            break;
                        }
                    }
                    saveLastMotion(ev);
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mDragState == STATE_DRAGGING) {
                    dispatchViewReleased();
                }
                cancel();
                break;
            }
        }
    }

    /**
     * Check if we've crossed a reasonable touch slop for the given child view.
     * If the child cannot be dragged along the horizontal or vertical axis,
     * motion along that axis will not count toward the slop check.
     * 
     * @param child Child to check
     * @param dx Motion since initial position along X axis
     * @param dy Motion since initial position along Y axis
     * @return true if the touch slop has been crossed
     */
    private boolean checkTouchSlop(View child, float dx, float dy) {
        if (child == null) {
            return false;
        }
        if (mParentView == null) {
            return false;
        }
        final boolean checkHorizontal = (mParentView.mEdgeFlag & (ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_RIGHT)) > 0;
        final boolean checkVertical = (mParentView.mEdgeFlag & ViewDragHelper.EDGE_BOTTOM) > 0;

        if (checkHorizontal && checkVertical) {
            return dx * dx + dy * dy > mTouchSlop * mTouchSlop;
        } else if (checkHorizontal) {
            return Math.abs(dx) > mTouchSlop;
        } else if (checkVertical) {
            return Math.abs(dy) > mTouchSlop;
        }
        return false;
    }

    /**
     * Check if any of the edges specified were initially touched by the pointer
     * with the specified ID. If there is no currently active gesture or if
     * there is no pointer with the given ID currently down this method will
     * return false.
     * 
     * @param edges Edges to check for an initial edge touch. See
     *            {@link #EDGE_LEFT}, {@link #EDGE_RIGHT},
     *            {@link #EDGE_BOTTOM} and {@link #EDGE_ALL}
     * @return true if any of the edges specified were initially touched in the
     *         current gesture
     */
    public boolean isEdgeTouched(int edges, int pointerId) {
        return isPointerDown(pointerId) && (mInitialEdgesTouched[pointerId] & edges) != 0;
    }

    /**
     * Find the topmost child under the given point within the parent view's
     * coordinate system. The child order is determined using
     * getOrderedChildIndex(int)
     * 
     * @param x X position to test in the parent's coordinate system
     * @param y Y position to test in the parent's coordinate system
     * @return The topmost child view under (x, y) or null if none found.
     */
    public View findTopChildUnder(int x, int y) {
        final int childCount = mParentView.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            //final View child = mParentView.getChildAt(mCallback.getOrderedChildIndex(i));
            final View child = mParentView.getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight() && y >= child.getTop()
                    && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    /**
     * justify touch position (x, y) is outside or can cause a drag sequence
     * @param x
     * @param y
     * @return EDGE_LEFT or EDGE_TOP or EDGE_RIGHT or EDGE_BOTTOM
     * @see #EDGE_LEFT
     * @see #EDGE_TOP
     * @see #EDGE_RIGHT
     * @see #EDGE_BOTTOM
     */
    private int getEdgesTouched(int x, int y) {
        int result = 0;

        if (x < mParentView.getLeft() + mEdgeSize)
            result |= EDGE_LEFT;
        if (x > mParentView.getRight() - mEdgeSize)
            result |= EDGE_RIGHT;
        if (y > mParentView.getBottom() - mEdgeSize)
            result |= EDGE_BOTTOM;

        return result;
    }
    
}