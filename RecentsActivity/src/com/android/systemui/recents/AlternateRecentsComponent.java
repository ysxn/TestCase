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

package com.android.systemui.recents;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import com.example.recentsactivity.R;
import com.android.systemui.recents.misc.Console;
import com.android.systemui.recents.model.RecentsTaskLoader;
import com.android.systemui.recents.model.Task;
import com.android.systemui.recents.model.TaskStack;
import com.android.systemui.recents.views.TaskStackView;
import com.android.systemui.recents.views.TaskStackViewLayoutAlgorithm;
import com.android.systemui.recents.views.TaskViewTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/** A proxy implementation for the recents component */
public class AlternateRecentsComponent {

    final public static String EXTRA_FROM_HOME = "recents.triggeredOverHome";
    final public static String EXTRA_FROM_SEARCH_HOME = "recents.triggeredOverSearchHome";
    final public static String EXTRA_FROM_APP_THUMBNAIL = "recents.animatingWithThumbnail";
    final public static String EXTRA_FROM_APP_FULL_SCREENSHOT = "recents.thumbnail";
    final public static String EXTRA_FROM_TASK_ID = "recents.activeTaskId";
    final public static String EXTRA_TRIGGERED_FROM_ALT_TAB = "recents.triggeredFromAltTab";
    final public static String EXTRA_TRIGGERED_FROM_HOME_KEY = "recents.triggeredFromHomeKey";

    final public static String ACTION_START_ENTER_ANIMATION = "action_start_enter_animation";
    final public static String ACTION_TOGGLE_RECENTS_ACTIVITY = "action_toggle_recents_activity";
    final public static String ACTION_HIDE_RECENTS_ACTIVITY = "action_hide_recents_activity";

    final static int sMinToggleDelay = 350;

    final static String sToggleRecentsAction = "com.android.systemui.recents.SHOW_RECENTS";
    final static String sRecentsPackage = "com.android.systemui";
    final static String sRecentsActivity = "com.android.systemui.recents.RecentsActivity";

    static Bitmap sLastScreenshot;

    Context mContext;
    LayoutInflater mInflater;
    Handler mHandler;
    boolean mBootCompleted;
    boolean mStartAnimationTriggered;

    // Task launching
    RecentsConfiguration mConfig;
    Rect mWindowRect = new Rect();
    Rect mTaskStackBounds = new Rect();
    Rect mSystemInsets = new Rect();
    TaskViewTransform mTmpTransform = new TaskViewTransform();
    int mStatusBarHeight;
    int mNavBarHeight;
    int mNavBarWidth;

    // Header (for transition)
    TaskStackView mDummyStackView;

    // Variables to keep track of if we need to start recents after binding
    View mStatusBarView;
    boolean mTriggeredFromAltTab;
    long mLastToggleTime;

    public AlternateRecentsComponent(Context context) {
        RecentsTaskLoader.initialize(context);
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mHandler = new Handler();
        mTaskStackBounds = new Rect();
    }

    public void onStart() {
        // Initialize some static datastructures
        TaskStackViewLayoutAlgorithm.initializeCurve();

    }

    public void onBootCompleted() {
        mBootCompleted = true;
    }

    /** Shows the recents */
    public void onShowRecents(boolean triggeredFromAltTab, View statusBarView) {
        mStatusBarView = statusBarView;
        mTriggeredFromAltTab = triggeredFromAltTab;

        try {
            startRecentsActivity();
        } catch (ActivityNotFoundException e) {
            Console.logRawError("Failed to launch RecentAppsIntent", e);
        }
    }

    /** Toggles the alternate recents activity */
    public void onToggleRecents(View statusBarView) {
        mStatusBarView = statusBarView;
        mTriggeredFromAltTab = false;

        try {
            toggleRecentsActivity();
        } catch (ActivityNotFoundException e) {
            Console.logRawError("Failed to launch RecentAppsIntent", e);
        }
    }

    public void onPreloadRecents() {
        // Do nothing
    }

    public void onCancelPreloadingRecents() {
        // Do nothing
    }

    public void onConfigurationChanged(Configuration newConfig) {
        sLastScreenshot = null;
    }

    /** Gets the top task. */
    ActivityManager.RunningTaskInfo getTopMostTask() {

        return null;
    }

    /** Toggles the recents activity */
    void toggleRecentsActivity() {
        // If the user has toggled it too quickly, then just eat up the event here (it's better than
        // showing a janky screenshot).
        // NOTE: Ideally, the screenshot mechanism would take the window transform into account
        if (System.currentTimeMillis() - mLastToggleTime < sMinToggleDelay) {
            return;
        }

        // If Recents is the front most activity, then we should just communicate with it directly
        // to launch the first task or dismiss itself
        ActivityManager.RunningTaskInfo topTask = getTopMostTask();
        AtomicBoolean isTopTaskHome = new AtomicBoolean();

        // Otherwise, start the recents activity
        startRecentsActivity(topTask, isTopTaskHome.get());

    }

    /** Starts the recents activity if it is not already running */
    void startRecentsActivity() {
        // Check if the top task is in the home stack, and start the recents activity
        ActivityManager.RunningTaskInfo topTask = getTopMostTask();
        AtomicBoolean isTopTaskHome = new AtomicBoolean();
        startRecentsActivity(topTask, isTopTaskHome.get());
        
    }

    /**
     * Creates the activity options for a unknown state->recents transition.
     */
    ActivityOptions getUnknownTransitionActivityOptions() {
        mStartAnimationTriggered = false;
        return ActivityOptions.makeCustomAnimation(mContext,
                R.anim.recents_from_unknown_enter,
                R.anim.recents_from_unknown_exit);
    }

    /** Returns the transition rect for the given task id. */
    TaskViewTransform getThumbnailTransitionTransform(int runningTaskId, boolean isTopTaskHome,
                                                      Task runningTaskOut) {
        // Get the stack of tasks that we are animating into
        RecentsTaskLoader loader = RecentsTaskLoader.getInstance();
        TaskStack stack = loader.getTaskStack(mContext.getResources(),
                runningTaskId, -1, false, isTopTaskHome, null, null);
        if (stack.getTaskCount() == 0) {
            return null;
        }

        // Find the running task in the TaskStack
        Task task = null;
        ArrayList<Task> tasks = stack.getTasks();
        if (runningTaskId != -1) {
            // Otherwise, try and find the task with the
            int taskCount = tasks.size();
            for (int i = taskCount - 1; i >= 0; i--) {
                Task t = tasks.get(i);
                if (t.key.id == runningTaskId) {
                    task = t;
                    runningTaskOut.copyFrom(t);
                    break;
                }
            }
        }
        if (task == null) {
            // If no task is specified or we can not find the task just use the front most one
            task = tasks.get(tasks.size() - 1);
        }

        // Get the transform for the running task
        mDummyStackView.updateMinMaxScrollForStack(stack, mTriggeredFromAltTab, isTopTaskHome);
        mDummyStackView.getScroller().setStackScrollToInitialState();
        mTmpTransform = mDummyStackView.getStackAlgorithm().getStackTransform(task,
                mDummyStackView.getScroller().getStackScroll(), mTmpTransform, null);
        return mTmpTransform;
    }

    /** Starts the recents activity */
    void startRecentsActivity(ActivityManager.RunningTaskInfo topTask, boolean isTopTaskHome) {
        // If Recents is not the front-most activity and we should animate into it.  If
        // the activity at the root of the top task stack in the home stack, then we just do a
        // simple transition.  Otherwise, we animate to the rects defined by the Recents service,
        // which can differ depending on the number of items in the list
        List<ActivityManager.RecentTaskInfo> recentTasks = new ArrayList<ActivityManager.RecentTaskInfo>();

        // Otherwise we do the normal fade from an unknown source
        ActivityOptions opts = getUnknownTransitionActivityOptions();
        startAlternateRecentsActivity(topTask, opts, null);
        
        mLastToggleTime = System.currentTimeMillis();
    }

    /** Starts the recents activity */
    void startAlternateRecentsActivity(ActivityManager.RunningTaskInfo topTask,
                                       ActivityOptions opts, String extraFlag) {
        Intent intent = new Intent(sToggleRecentsAction);
        intent.setClassName(sRecentsPackage, sRecentsActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        if (extraFlag != null) {
            intent.putExtra(extraFlag, true);
        }
        intent.putExtra(EXTRA_TRIGGERED_FROM_ALT_TAB, mTriggeredFromAltTab);
        intent.putExtra(EXTRA_FROM_TASK_ID, (topTask != null) ? topTask.id : -1);
        if (opts != null) {
            mContext.startActivity(intent, opts.toBundle()/*, UserHandle.CURRENT*/);
        } else {
            mContext.startActivity(intent/*, UserHandle.CURRENT*/);
        }
    }

    /** Returns the last screenshot taken, this will be called by the RecentsActivity. */
    public static Bitmap getLastScreenshot() {
        return sLastScreenshot;
    }

    /** Recycles the last screenshot taken, this will be called by the RecentsActivity. */
    public static void consumeLastScreenshot() {
        if (sLastScreenshot != null) {
            sLastScreenshot.recycle();
            sLastScreenshot = null;
        }
    }

}
