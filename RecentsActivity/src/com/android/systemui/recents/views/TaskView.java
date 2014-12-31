/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.android.systemui.recents.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import com.example.recentsactivity.R;
import com.android.systemui.recents.AlternateRecentsComponent;
import com.android.systemui.recents.Constants;
import com.android.systemui.recents.RecentsConfiguration;
import com.android.systemui.recents.model.RecentsTaskLoader;
import com.android.systemui.recents.model.Task;

/* A task view */
public class TaskView extends FrameLayout implements Task.TaskCallbacks,
        View.OnClickListener, View.OnLongClickListener {

    /** The TaskView callbacks */
    interface TaskViewCallbacks {
        public void onTaskViewAppIconClicked(TaskView tv);
        public void onTaskViewAppInfoClicked(TaskView tv);
        public void onTaskViewClicked(TaskView tv, Task task, boolean lockToTask);
        public void onTaskViewDismissed(TaskView tv);
        public void onTaskViewClipStateChanged(TaskView tv);
        public void onTaskViewFullScreenTransitionCompleted();
        public void onTaskViewFocusChanged(TaskView tv, boolean focused);
    }

    RecentsConfiguration mConfig;

    float mTaskProgress;
    ObjectAnimator mTaskProgressAnimator;
    ObjectAnimator mDimAnimator;
    float mMaxDimScale;
    int mDim;
    AccelerateInterpolator mDimInterpolator = new AccelerateInterpolator(1f);
    PorterDuffColorFilter mDimColorFilter = new PorterDuffColorFilter(0, PorterDuff.Mode.MULTIPLY);

    Task mTask;
    boolean mTaskDataLoaded;
    boolean mIsFocused;
    boolean mFocusAnimationsEnabled;
    boolean mIsFullScreenView;
    boolean mClipViewInStack;
    AnimateableViewBounds mViewBounds;
    Paint mLayerPaint = new Paint();

    View mContent;
    TaskViewCallbacks mCb;

    // Optimizations
    ValueAnimator.AnimatorUpdateListener mUpdateDimListener =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setTaskProgress((Float) animation.getAnimatedValue());
                }
            };


    public TaskView(Context context) {
        this(context, null);
    }

    public TaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TaskView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mConfig = RecentsConfiguration.getInstance();
        mMaxDimScale = mConfig.taskStackMaxDim / 255f;
        mClipViewInStack = true;
        mViewBounds = new AnimateableViewBounds(this, mConfig.taskViewRoundedCornerRadiusPx);
        setTaskProgress(getTaskProgress());
        setDim(getDim());
        if (mConfig.fakeShadows) {
            setBackground(new FakeShadowDrawable(context.getResources(), mConfig));
        }
        setOutlineProvider(mViewBounds);
    }

    /** Set callback */
    void setCallbacks(TaskViewCallbacks cb) {
        mCb = cb;
    }

    /** Gets the task */
    Task getTask() {
        return mTask;
    }

    /** Returns the view bounds. */
    AnimateableViewBounds getViewBounds() {
        return mViewBounds;
    }

    @Override
    protected void onFinishInflate() {
        // Bind the views
        mContent = findViewById(R.id.task_view_content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int widthWithoutPadding = width - mPaddingLeft - mPaddingRight;
        int heightWithoutPadding = height - mPaddingTop - mPaddingBottom;

        // Measure the content
        mContent.measure(MeasureSpec.makeMeasureSpec(widthWithoutPadding, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(widthWithoutPadding, MeasureSpec.EXACTLY));

        setMeasuredDimension(width, height);
        invalidateOutline();
    }

    /** Synchronizes this view's properties with the task's transform */
    void updateViewPropertiesToTaskTransform(TaskViewTransform toTransform, int duration) {
        updateViewPropertiesToTaskTransform(toTransform, duration, null);
    }

    void updateViewPropertiesToTaskTransform(TaskViewTransform toTransform, int duration,
                                             ValueAnimator.AnimatorUpdateListener updateCallback) {
        // If we are a full screen view, then only update the Z to keep it in order
        // XXX: Also update/animate the dim as well
        if (mIsFullScreenView) {
            if (!mConfig.fakeShadows &&
                    toTransform.hasTranslationZChangedFrom(getTranslationZ())) {
                setTranslationZ(toTransform.translationZ);
            }
            return;
        }

        // Apply the transform
        toTransform.applyToTaskView(this, duration, mConfig.fastOutSlowInInterpolator, false,
                !mConfig.fakeShadows, updateCallback);

        // Update the task progress
        if (mTaskProgressAnimator != null) {
            mTaskProgressAnimator.removeAllListeners();
            mTaskProgressAnimator.cancel();
        }
        if (duration <= 0) {
            setTaskProgress(toTransform.p);
        } else {
            mTaskProgressAnimator = ObjectAnimator.ofFloat(this, "taskProgress", toTransform.p);
            mTaskProgressAnimator.setDuration(duration);
            mTaskProgressAnimator.addUpdateListener(mUpdateDimListener);
            mTaskProgressAnimator.start();
        }
    }

    /** Resets this view's properties */
    void resetViewProperties() {
        setDim(0);
        TaskViewTransform.reset(this);
    }

    /**
     * When we are un/filtering, this method will set up the transform that we are animating to,
     * in order to hide the task.
     */
    void prepareTaskTransformForFilterTaskHidden(TaskViewTransform toTransform) {
        // Fade the view out and slide it away
        toTransform.alpha = 0f;
        toTransform.translationY += 200;
        toTransform.translationZ = 0;
    }

    /**
     * When we are un/filtering, this method will setup the transform that we are animating from,
     * in order to show the task.
     */
    void prepareTaskTransformForFilterTaskVisible(TaskViewTransform fromTransform) {
        // Fade the view in
        fromTransform.alpha = 0f;
    }

    /** Prepares this task view for the enter-recents animations.  This is called earlier in the
     * first layout because the actual animation into recents may take a long time. */
    void prepareEnterRecentsAnimation(boolean isTaskViewLaunchTargetTask,
                                             boolean occludesLaunchTarget, int offscreenY) {
        int initialDim = getDim();
        if (mConfig.launchedFromAppWithScreenshot) {
            if (isTaskViewLaunchTargetTask) {

            } else {
                // Don't do anything for the side views when animating in
            }

        } else if (mConfig.launchedFromAppWithThumbnail) {
            if (isTaskViewLaunchTargetTask) {
                // Set the dim to 0 so we can animate it in
                initialDim = 0;
            } else if (occludesLaunchTarget) {
                // Move the task view off screen (below) so we can animate it in
                setTranslationY(offscreenY);
            }

        } else if (mConfig.launchedFromHome) {
            // Move the task view off screen (below) so we can animate it in
            setTranslationY(offscreenY);
            setTranslationZ(0);
            setScaleX(1f);
            setScaleY(1f);
        }
        // Apply the current dim
        setDim(initialDim);
    }

    /** Animates this task view as it enters recents */
    void startEnterRecentsAnimation(final ViewAnimation.TaskViewEnterContext ctx) {
        final TaskViewTransform transform = ctx.currentTaskTransform;
        int startDelay = 0;

        if (mConfig.launchedFromAppWithScreenshot) {
            if (mTask.isLaunchTarget) {
                Rect taskRect = ctx.currentTaskRect;
                int duration = mConfig.taskViewEnterFromHomeDuration * 10;
                int windowInsetTop = mConfig.systemInsets.top; // XXX: Should be for the window
                float taskScale = ((float) taskRect.width() / getMeasuredWidth()) * transform.scale;
                float scaledYOffset = ((1f - taskScale) * getMeasuredHeight()) / 2;
                float scaledWindowInsetTop = (int) (taskScale * windowInsetTop);
                float scaledTranslationY = taskRect.top + transform.translationY -
                        (scaledWindowInsetTop + scaledYOffset);
                startDelay = mConfig.taskViewEnterFromHomeStaggerDelay;

                // Animate the top clip
                mViewBounds.animateClipTop(windowInsetTop, duration,
                        new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int y = (Integer) animation.getAnimatedValue();
                    }
                });
                // Animate the bottom or right clip
                int size = Math.round((taskRect.width() / taskScale));
                if (mConfig.hasHorizontalLayout()) {
                    mViewBounds.animateClipRight(getMeasuredWidth() - size, duration);
                } else {
                    mViewBounds.animateClipBottom(getMeasuredHeight() - (windowInsetTop + size), duration);
                }
                // Animate the task bar of the first task view
                animate()
                        .scaleX(taskScale)
                        .scaleY(taskScale)
                        .translationY(scaledTranslationY)
                        .setDuration(duration)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                setIsFullScreen(false);
                                requestLayout();

                                // Reset the clip
                                mViewBounds.setClipTop(0);
                                mViewBounds.setClipBottom(0);
                                mViewBounds.setClipRight(0);
                                // Animate the footer into view (if it is the front most task)
                                animateFooterVisibility(true, mConfig.taskBarEnterAnimDuration);

                                // Unbind the thumbnail from the screenshot
                                RecentsTaskLoader.getInstance().loadTaskData(mTask);
                                // Recycle the full screen screenshot
                                AlternateRecentsComponent.consumeLastScreenshot();

                                mCb.onTaskViewFullScreenTransitionCompleted();

                                // Decrement the post animation trigger
                                ctx.postAnimationTrigger.decrement();
                            }
                        })
                        .start();
            } else {
                // Animate the footer into view
                animateFooterVisibility(true, 0);
            }
            ctx.postAnimationTrigger.increment();

        } else if (mConfig.launchedFromAppWithThumbnail) {
            if (mTask.isLaunchTarget) {
                // Animate the dim/overlay
                if (Constants.DebugFlags.App.EnableThumbnailAlphaOnFrontmost) {

                } else {
                    // Immediately start the dim animation
                    animateDimToProgress(mConfig.taskBarEnterAnimDelay,
                            mConfig.taskBarEnterAnimDuration,
                            ctx.postAnimationTrigger.decrementOnAnimationEnd());
                }
                ctx.postAnimationTrigger.increment();

                // Animate the footer into view
                animateFooterVisibility(true, mConfig.taskBarEnterAnimDuration);

            } else {
                // Animate the task up if it was occluding the launch target
                if (ctx.currentTaskOccludesLaunchTarget) {
                    setTranslationY(transform.translationY + mConfig.taskViewAffiliateGroupEnterOffsetPx);
                    setAlpha(0f);
                    animate().alpha(1f)
                            .translationY(transform.translationY)
                            .setStartDelay(mConfig.taskBarEnterAnimDelay)
                            .setUpdateListener(null)
                            .setInterpolator(mConfig.fastOutSlowInInterpolator)
                            .setDuration(mConfig.taskViewEnterFromHomeDuration)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    // Decrement the post animation trigger
                                    ctx.postAnimationTrigger.decrement();
                                }
                            })
                            .start();
                    ctx.postAnimationTrigger.increment();
                }
            }
            startDelay = mConfig.taskBarEnterAnimDelay;

        } else if (mConfig.launchedFromHome) {
            // Animate the tasks up
            int frontIndex = (ctx.currentStackViewCount - ctx.currentStackViewIndex - 1);
            int delay = mConfig.taskViewEnterFromHomeDelay +
                    frontIndex * mConfig.taskViewEnterFromHomeStaggerDelay;

            setScaleX(transform.scale);
            setScaleY(transform.scale);
            if (!mConfig.fakeShadows) {
                animate().translationZ(transform.translationZ);
            }
            animate()
                    .translationY(transform.translationY)
                    .setStartDelay(delay)
                    .setUpdateListener(ctx.updateListener)
                    .setInterpolator(mConfig.quintOutInterpolator)
                    .setDuration(mConfig.taskViewEnterFromHomeDuration +
                            frontIndex * mConfig.taskViewEnterFromHomeStaggerDelay)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            // Decrement the post animation trigger
                            ctx.postAnimationTrigger.decrement();
                        }
                    })
                    .start();
            ctx.postAnimationTrigger.increment();

            // Animate the footer into view
            animateFooterVisibility(true, mConfig.taskViewEnterFromHomeDuration);
            startDelay = delay;

        } else {
            // Animate the footer into view
            animateFooterVisibility(true, 0);
        }

        // Enable the focus animations from this point onwards so that they aren't affected by the
        // window transitions
        postDelayed(new Runnable() {
            @Override
            public void run() {
                enableFocusAnimations();
            }
        }, (startDelay / 2));
    }

    /** Animates this task view as it leaves recents by pressing home. */
    void startExitToHomeAnimation(ViewAnimation.TaskViewExitContext ctx) {
        animate()
                .translationY(ctx.offscreenTranslationY)
                .setStartDelay(0)
                .setUpdateListener(null)
                .setInterpolator(mConfig.fastOutLinearInInterpolator)
                .setDuration(mConfig.taskViewExitToHomeDuration)
                .withEndAction(ctx.postAnimationTrigger.decrementAsRunnable())
                .start();
        ctx.postAnimationTrigger.increment();
    }

    /** Animates this task view as it exits recents */
    void startLaunchTaskAnimation(final Runnable postAnimRunnable, boolean isLaunchingTask,
            boolean occludesLaunchTarget, boolean lockToTask) {
        if (isLaunchingTask) {

            // Animate the dim
            if (mDim > 0) {
                ObjectAnimator anim = ObjectAnimator.ofInt(this, "dim", 0);
                anim.setDuration(mConfig.taskBarExitAnimDuration);
                anim.setInterpolator(mConfig.fastOutLinearInInterpolator);
                anim.start();
            }

        } else {
            // If this is another view in the task grouping and is in front of the launch task,
            // animate it away first
            if (occludesLaunchTarget) {
                animate().alpha(0f)
                    .translationY(getTranslationY() + mConfig.taskViewAffiliateGroupEnterOffsetPx)
                    .setStartDelay(0)
                    .setUpdateListener(null)
                    .setInterpolator(mConfig.fastOutLinearInInterpolator)
                    .setDuration(mConfig.taskBarExitAnimDuration)
                    .start();
            }
        }
    }

    /** Animates the deletion of this task view */
    void startDeleteTaskAnimation(final Runnable r) {
        // Disabling clipping with the stack while the view is animating away
        setClipViewInStack(false);

        animate().translationX(mConfig.taskViewRemoveAnimTranslationXPx)
            .alpha(0f)
            .setStartDelay(0)
            .setUpdateListener(null)
            .setInterpolator(mConfig.fastOutSlowInInterpolator)
            .setDuration(mConfig.taskViewRemoveAnimDuration)
            .withEndAction(new Runnable() {
                @Override
                public void run() {
                    // We just throw this into a runnable because starting a view property
                    // animation using layers can cause inconsisten results if we try and
                    // update the layers while the animation is running.  In some cases,
                    // the runnabled passed in may start an animation which also uses layers
                    // so we defer all this by posting this.
                    r.run();

                    // Re-enable clipping with the stack (we will reuse this view)
                    setClipViewInStack(true);
                }
            })
            .start();
    }

    /** Animates this task view if the user does not interact with the stack after a certain time. */
    void startNoUserInteractionAnimation() {

    }

    /** Mark this task view that the user does has not interacted with the stack after a certain time. */
    void setNoUserInteractionState() {

    }

    /** Dismisses this task. */
    void dismissTask() {
        // Animate out the view and call the callback
        final TaskView tv = this;
        startDeleteTaskAnimation(new Runnable() {
            @Override
            public void run() {
                mCb.onTaskViewDismissed(tv);
            }
        });
        // Hide the footer
        animateFooterVisibility(false, mConfig.taskViewRemoveAnimDuration);
    }

    /** Sets whether this task view is full screen or not. */
    void setIsFullScreen(boolean isFullscreen) {
        mIsFullScreenView = isFullscreen;

        if (isFullscreen) {
            // If we are full screen, then disable the bottom outline clip for the footer
            mViewBounds.setOutlineClipBottom(0);
        }
    }

    /** Returns whether this task view should currently be drawn as a full screen view. */
    boolean isFullScreenView() {
        return mIsFullScreenView;
    }

    /**
     * Returns whether this view should be clipped, or any views below should clip against this
     * view.
     */
    boolean shouldClipViewInStack() {
        return mClipViewInStack && !mIsFullScreenView && (getVisibility() == View.VISIBLE);
    }

    /** Sets whether this view should be clipped, or clipped against. */
    void setClipViewInStack(boolean clip) {
        if (clip != mClipViewInStack) {
            mClipViewInStack = clip;
            mCb.onTaskViewClipStateChanged(this);
        }
    }

    /** Gets the max footer height. */
    public int getMaxFooterHeight() {
        return 0;
    }

    /** Animates the footer into and out of view. */
    void animateFooterVisibility(boolean visible, int duration) {
        // Hide the footer if we are a full screen view
        if (mIsFullScreenView) return;
        // Hide the footer if the current task can not be locked to
        if (!mTask.lockToTaskEnabled || !mTask.lockToThisTask) return;
    }

    /** Sets the current task progress. */
    public void setTaskProgress(float p) {
        mTaskProgress = p;
        mViewBounds.setAlpha(p);
        updateDimFromTaskProgress();
    }

    /** Returns the current task progress. */
    public float getTaskProgress() {
        return mTaskProgress;
    }

    /** Returns the current dim. */
    public void setDim(int dim) {
        mDim = dim;
        if (mDimAnimator != null) {
            mDimAnimator.removeAllListeners();
            mDimAnimator.cancel();
        }
        if (mConfig.useHardwareLayers) {
            // Defer setting hardware layers if we have not yet measured, or there is no dim to draw
            if (getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
                if (mDimAnimator != null) {
                    mDimAnimator.removeAllListeners();
                    mDimAnimator.cancel();
                }

                int inverse = 255 - mDim;
                mDimColorFilter = new PorterDuffColorFilter(Color.argb(0xFF, inverse, inverse, inverse), PorterDuff.Mode.MULTIPLY);
                //.setColor(Color.argb(0xFF, inverse, inverse, inverse));
                mLayerPaint.setColorFilter(mDimColorFilter);
                mContent.setLayerType(LAYER_TYPE_HARDWARE, mLayerPaint);
            }
        } else {
            float dimAlpha = mDim / 255.0f;
        }
    }

    /** Returns the current dim. */
    public int getDim() {
        return mDim;
    }

    /** Animates the dim to the task progress. */
    void animateDimToProgress(int delay, int duration, Animator.AnimatorListener postAnimRunnable) {
        // Animate the dim into view as well
        int toDim = getDimFromTaskProgress();
        if (toDim != getDim()) {
            ObjectAnimator anim = ObjectAnimator.ofInt(TaskView.this, "dim", toDim);
            anim.setStartDelay(delay);
            anim.setDuration(duration);
            if (postAnimRunnable != null) {
                anim.addListener(postAnimRunnable);
            }
            anim.start();
        }
    }

    /** Compute the dim as a function of the scale of this view. */
    int getDimFromTaskProgress() {
        float dim = mMaxDimScale * mDimInterpolator.getInterpolation(1f - mTaskProgress);
        return (int) (dim * 255);
    }

    /** Update the dim as a function of the scale of this view. */
    void updateDimFromTaskProgress() {
        setDim(getDimFromTaskProgress());
    }

    /**** View focus state ****/

    /**
     * Sets the focused task explicitly. We need a separate flag because requestFocus() won't happen
     * if the view is not currently visible, or we are in touch state (where we still want to keep
     * track of focus).
     */
    public void setFocusedTask() {
        mIsFocused = true;

        // Call the callback
        mCb.onTaskViewFocusChanged(this, true);
        // Workaround, we don't always want it focusable in touch mode, but we want the first task
        // to be focused after the enter-recents animation, which can be triggered from either touch
        // or keyboard
        setFocusableInTouchMode(true);
        requestFocus();
        setFocusableInTouchMode(false);
        invalidate();
    }

    /**
     * Unsets the focused task explicitly.
     */
    void unsetFocusedTask() {
        mIsFocused = false;

        // Call the callback
        mCb.onTaskViewFocusChanged(this, false);
        invalidate();
    }

    /**
     * Updates the explicitly focused state when the view focus changes.
     */
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!gainFocus) {
            unsetFocusedTask();
        }
    }

    /**
     * Returns whether we have explicitly been focused.
     */
    public boolean isFocusedTask() {
        return mIsFocused || isFocused();
    }

    /** Enables all focus animations. */
    void enableFocusAnimations() {
        boolean wasFocusAnimationsEnabled = mFocusAnimationsEnabled;
        mFocusAnimationsEnabled = true;
        if (mIsFocused && !wasFocusAnimationsEnabled) {

        }
    }

    /**** TaskCallbacks Implementation ****/

    /** Binds this task view to the task */
    public void onTaskBound(Task t) {
        mTask = t;
        mTask.setCallbacks(this);
        if (getMeasuredWidth() == 0) {
            // If we haven't yet measured, we should just set the footer height with any animation
            animateFooterVisibility(t.lockToThisTask, 0);
        } else {
            animateFooterVisibility(t.lockToThisTask, mConfig.taskViewLockToAppLongAnimDuration);
        }
    }

    @Override
    public void onTaskDataLoaded() {

        mTaskDataLoaded = true;
    }

    @Override
    public void onTaskDataUnloaded() {

        mTaskDataLoaded = false;
    }

    /** Enables/disables handling touch on this task view. */
    void setTouchEnabled(boolean enabled) {
        setOnClickListener(enabled ? this : null);
    }

    /**** View.OnClickListener Implementation ****/

    @Override
     public void onClick(final View v) {
        final TaskView tv = this;
        final boolean delayViewClick = (v != this);
        if (delayViewClick) {
            // We purposely post the handler delayed to allow for the touch feedback to draw
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Constants.DebugFlags.App.EnableTaskFiltering) {
                        mCb.onTaskViewAppIconClicked(tv);
                    }
                }
            }, 125);
        } else {

        }
    }

    /**** View.OnLongClickListener Implementation ****/

    @Override
    public boolean onLongClick(View v) {

        return false;
    }
}
