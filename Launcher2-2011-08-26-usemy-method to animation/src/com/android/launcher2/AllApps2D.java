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

package com.android.launcher2;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Interpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;

import com.android.launcher.R;

public class AllApps2D
        extends RelativeLayout
        implements AllAppsView,
                   AdapterView.OnItemClickListener,
                   AdapterView.OnItemLongClickListener,
                   View.OnKeyListener,
                   DragSource {

    private static final String TAG = "Launcher.AllApps2D";
    private static final boolean DEBUG = false;

    private Launcher mLauncher;
    private DragController mDragController;

    private GridView mGrid;
    private ViewGroup mAnimationGrid;
    private AllApps2D mAllApps2D;
    private AppTextView mAppTextView;

    /** The animation that morphs the search widget to the search dialog. */
    private ToParentOriginAnimation mMorphAnimation;

    /** The animation that morphs the search widget back to its normal position. */
    private FromParentOriginAnimation mUnmorphAnimation;

    private boolean mShadeAllApp = false;

    private ArrayList<ApplicationInfo> mAllAppsList = new ArrayList<ApplicationInfo>();

    // preserve compatibility with 3D all apps:
    //    0.0 -> hidden
    //    1.0 -> shown and opaque
    //    intermediate values -> partially shown & partially opaque
    private float mZoom;

    private AppsAdapter mAppsAdapter;

    // ------------------------------------------------------------
    
    public static class HomeButton extends ImageButton {
        public HomeButton(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        @Override
        public View focusSearch(int direction) {
            if (direction == FOCUS_UP) return super.focusSearch(direction);
            return null;
        }
    }

    public static class AppTextView extends TextView {
        private GridView mGrid;
        private ViewGroup mAnimationGrid;
        private AllApps2D mAllApps2D;
        private Launcher mLauncher;
        private int mLastMotionY;
        private boolean mLastMotionUp = false;
    
        public AppTextView(Context context) {
            this(context, null);
        }

        public AppTextView(Context context,
                        AttributeSet attrs) {
            this(context, attrs, com.android.internal.R.attr.textViewStyle);
        }

        public AppTextView(Context context,
                        AttributeSet attrs,
                        int defStyle) {
            super(context, attrs, defStyle);
        }

        public void setGridView(GridView grid) {
            mGrid = grid;
        }

        public void setViewGroup(ViewGroup grid) {
            mAnimationGrid = grid;
        }

        public void setAllApps2D(AllApps2D grid) {
            mAllApps2D = grid;
        }

        public void setLauncher(Launcher launcher) {
            mLauncher = launcher;
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onStartTrackingTouch(event);
                    break;
                    
                case MotionEvent.ACTION_MOVE:
                    trackTouchEvent(event);
                    break;
                    
                case MotionEvent.ACTION_UP:
                    onStopTrackingTouch(event);
                    break;
                    
                case MotionEvent.ACTION_CANCEL:
                    onStopTrackingTouch(event);
                    break;
            }
            return true;
        }

        private void trackTouchEvent(MotionEvent event) {
            int paddingTop = (int)event.getY() - mLastMotionY;
            mLastMotionY = (int)event.getY();
            if (paddingTop != 0) {
                mAllApps2D.setBackgroundColor(Color.TRANSPARENT);
                mLauncher.shadeAllApps(true);
                //mAnimationGrid.setPadding(0, paddingTop > 0 ? paddingTop : 0 , 0, 0);
                System.out.println(">>>>>>>>>>>111111111>>>>y="+event.getY() 
                    +",,>>>mLastMotionY=="+mLastMotionY+", paddingTop="+paddingTop
                    +",  getTop=="+mAnimationGrid.getTop());
                if ((mAnimationGrid.getTop() + paddingTop) < 0) {
                    mAnimationGrid.offsetTopAndBottom(0 - mAnimationGrid.getTop());
                } else {
                    mAnimationGrid.offsetTopAndBottom(paddingTop);
                }
                System.out.println(">>>>>>>>>>>22222222>>>>y="+event.getY() 
                    +",,>>>mLastMotionY=="+mLastMotionY+", paddingTop="+paddingTop
                    +",  getTop=="+mAnimationGrid.getTop());
                mAnimationGrid.invalidate();
                mLastMotionUp = (paddingTop < 0);
            }
        }

        /**
         * This is called when the user has started touching this widget.
         */
        void onStartTrackingTouch(MotionEvent event) {
            mLastMotionY = (int)event.getY();
        }

        /**
         * This is called when the user either releases his touch or the touch is
         * canceled.
         */
        void onStopTrackingTouch(MotionEvent event) {
            if (mLastMotionUp) {
                mAllApps2D.shadeAllApps(true);
                mLauncher.showAllApps(true);
            } else {
                mLauncher.closeAllApps(true);
            }
            mLastMotionY = 0;
        }
    }

    public class AppsAdapter extends ArrayAdapter<ApplicationInfo> {
        private final LayoutInflater mInflater;

        public AppsAdapter(Context context, ArrayList<ApplicationInfo> apps) {
            super(context, 0, apps);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ApplicationInfo info = getItem(position);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.application_boxed, parent, false);
            }

//            if (!info.filtered) {
//                info.icon = Utilities.createIconThumbnail(info.icon, getContext());
//                info.filtered = true;
//            }

            final TextView textView = (TextView) convertView;
            if (DEBUG) {
                Log.d(TAG, "icon bitmap = " + info.iconBitmap 
                    + " density = " + info.iconBitmap.getDensity());
            }
            info.iconBitmap.setDensity(Bitmap.DENSITY_NONE);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(info.iconBitmap), null, null);
            textView.setText(info.title);

            return convertView;
        }
    }

    public AllApps2D(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVisibility(View.GONE);
        setSoundEffectsEnabled(false);

        mAppsAdapter = new AppsAdapter(getContext(), mAllAppsList);
        mAppsAdapter.setNotifyOnChange(false);
        mAllApps2D = this;


        Interpolator fadein = new DecelerateInterpolator();
        mMorphAnimation = new ToParentOriginAnimation(0, 0, 800, 0);
        mMorphAnimation.setInterpolator(fadein);
        mMorphAnimation.setDuration(400);
        mMorphAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                atAnimationEnd();
            }
            public void onAnimationRepeat(Animation animation) { }
            public void onAnimationStart(Animation animation) { }
        });

        Interpolator fadeout = new AccelerateInterpolator();
        mUnmorphAnimation = new FromParentOriginAnimation(0, 0, 0, 800);
        mUnmorphAnimation.setInterpolator(fadeout);
        mUnmorphAnimation.setDuration(400);
        mUnmorphAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                atAnimationEnd();
            }
            public void onAnimationRepeat(Animation animation) { }
            public void onAnimationStart(Animation animation) { }
        });
    }

    @Override
    protected void onFinishInflate() {
        setBackgroundColor(Color.BLACK);

        try {
            mGrid = (GridView)findViewWithTag("all_apps_2d_grid");
            if (mGrid == null) throw new Resources.NotFoundException();
            mGrid.setOnItemClickListener(this);
            mGrid.setOnItemLongClickListener(this);
            mGrid.setBackgroundColor(Color.BLACK);
            mGrid.setCacheColorHint(Color.BLACK);

            mAnimationGrid = (ViewGroup) findViewById(R.id.all_apps_2d_grid_cluster);

            mAppTextView = (AppTextView) findViewById(R.id.all_apps_foot_header_text);
            mAppTextView.setAllApps2D(mAllApps2D);
            mAppTextView.setGridView(mGrid);
            mAppTextView.setViewGroup(mAnimationGrid);
            

            ImageButton homeButton = (ImageButton) findViewWithTag("all_apps_2d_home");
            if (homeButton == null) throw new Resources.NotFoundException();
            homeButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mLauncher.closeAllApps(true);
                    }
                });
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find necessary layout elements for AllApps2D");
        }

        setOnKeyListener(this);
    }

    public AllApps2D(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public void setLauncher(Launcher launcher) {
        mLauncher = launcher;
        mAppTextView.setLauncher(mLauncher);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (!isVisible()) return false;

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mLauncher.closeAllApps(true);
                break;
            default:
                return false;
        }

        return true;
    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
        mLauncher.startActivitySafely(app.intent, app);
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (!view.isInTouchMode()) {
            return false;
        }

        ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
        app = new ApplicationInfo(app);

        mDragController.startDrag(view, this, app, DragController.DRAG_ACTION_COPY);
        mLauncher.closeAllApps(true);

        return true;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, android.graphics.Rect prev) {
        if (gainFocus) {
            mGrid.requestFocus();
        }
    }

    public void setDragController(DragController dragger) {
        mDragController = dragger;
    }

    public void onDropCompleted(View target, boolean success) {
    }

    public void shadeAllApps(boolean shadeAllApp) {
        mShadeAllApp = shadeAllApp;
    }
    
    /**
     * Zoom to the specifed level.
     *
     * @param zoom [0..1] 0 is hidden, 1 is open
     */
    public void zoom(float zoom, boolean animate) {
//        Log.d(TAG, "zooming " + ((zoom == 1.0) ? "open" : "closed"));
        cancelLongPress();

        mZoom = zoom;

        if (isVisible()) {
            getParent().bringChildToFront(this);
            setVisibility(View.VISIBLE);
            mGrid.setAdapter(mAppsAdapter);
            if (animate) {
                //startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.all_apps_2d_fade_in));
                ToParentOriginAnimation morphAnimation = null;
                if (mShadeAllApp) {
                    Interpolator fadein = new DecelerateInterpolator();
                    morphAnimation = new ToParentOriginAnimation(0, 0, mAnimationGrid.getTop(), 0);
                    morphAnimation.setInterpolator(fadein);
                    morphAnimation.setDuration(400);
                    morphAnimation.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationEnd(Animation animation) {
                            atAnimationEnd();
                        }
                        public void onAnimationRepeat(Animation animation) { }
                        public void onAnimationStart(Animation animation) { }
                    });
                }
                mAnimationGrid.startAnimation(mShadeAllApp ? morphAnimation : mMorphAnimation);
                mShadeAllApp = false;
            } else {
                onAnimationEnd();
            }
        } else {
            if (animate) {
                setBackgroundColor(Color.TRANSPARENT);
                //startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.all_apps_2d_fade_out));
                mAnimationGrid.startAnimation(mUnmorphAnimation);
            } else {
                onAnimationEnd();
            }
        }
    }

    public void atAnimationEnd() {
        onAnimationEnd();
    }
    
    protected void onAnimationEnd() {
        if (!isVisible()) {
            setVisibility(View.GONE);
            mGrid.setAdapter(null);
            mZoom = 0.0f;
        } else {
            mZoom = 1.0f;
        }

        mLauncher.zoomed(mZoom);
    }

    public boolean isVisible() {
        return mZoom > 0.001f;
    }

    @Override
    public boolean isOpaque() {
        return mZoom > 0.999f;
    }

    public void setApps(ArrayList<ApplicationInfo> list) {
        mAllAppsList.clear();
        addApps(list);
    }

    public void addApps(ArrayList<ApplicationInfo> list) {
//        Log.d(TAG, "addApps: " + list.size() + " apps: " + list.toString());

        final int N = list.size();

        for (int i=0; i<N; i++) {
            final ApplicationInfo item = list.get(i);
            int index = Collections.binarySearch(mAllAppsList, item,
                    LauncherModel.APP_NAME_COMPARATOR);
            if (index < 0) {
                index = -(index+1);
            }
            mAllAppsList.add(index, item);
        }
        mAppsAdapter.notifyDataSetChanged();
    }

    public void removeApps(ArrayList<ApplicationInfo> list) {
        final int N = list.size();
        for (int i=0; i<N; i++) {
            final ApplicationInfo item = list.get(i);
            int index = findAppByComponent(mAllAppsList, item);
            if (index >= 0) {
                mAllAppsList.remove(index);
            } else {
                Log.w(TAG, "couldn't find a match for item \"" + item + "\"");
                // Try to recover.  This should keep us from crashing for now.
            }
        }
        mAppsAdapter.notifyDataSetChanged();
    }

    public void updateApps(ArrayList<ApplicationInfo> list) {
        // Just remove and add, because they may need to be re-sorted.
        removeApps(list);
        addApps(list);
    }

    private static int findAppByComponent(ArrayList<ApplicationInfo> list, ApplicationInfo item) {
        ComponentName component = item.intent.getComponent();
        final int N = list.size();
        for (int i=0; i<N; i++) {
            ApplicationInfo x = list.get(i);
            if (x.intent.getComponent().equals(component)) {
                return i;
            }
        }
        return -1;
    }

    public void dumpState() {
        ApplicationInfo.dumpApplicationInfoList(TAG, "mAllAppsList", mAllAppsList);
    }
    
    public void surrender() {
    }

    /** 
     * Moves the view to the top left corner of its parent.
     */
    private class ToParentOriginAnimation extends Animation {
        private float mFromXDelta;
        private float mToXDelta;
        private float mFromYDelta;
        private float mToYDelta;
        
        public ToParentOriginAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
            mFromXDelta = fromXDelta;
            mToXDelta = toXDelta;
            mFromYDelta = fromYDelta;
            mToYDelta = toYDelta;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float dx = mFromXDelta;
            float dy = mFromYDelta;
            if (mFromXDelta != mToXDelta) {
                dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
            }
            if (mFromYDelta != mToYDelta) {
                dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
            }
            t.getMatrix().setTranslate(dx, dy);
        }
    }

    /** 
     * Moves the view from the top left corner of its parent.
     */
    private class FromParentOriginAnimation extends Animation {
        private float mFromXDelta;
        private float mToXDelta;
        private float mFromYDelta;
        private float mToYDelta;
        
        public FromParentOriginAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
            mFromXDelta = fromXDelta;
            mToXDelta = toXDelta;
            mFromYDelta = fromYDelta;
            mToYDelta = toYDelta;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float dx = mFromXDelta;
            float dy = mFromYDelta;
            if (mFromXDelta != mToXDelta) {
                dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
            }
            if (mFromYDelta != mToYDelta) {
                dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
            }
            t.getMatrix().setTranslate(dx, dy);
        }
    }
}


