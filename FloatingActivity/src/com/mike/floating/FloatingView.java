
package com.mike.floating;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mike.floating.service.RunningStateService;

/*
 * @author wangxin
 * 
 * @date June 24, 2013
 */

public class FloatingView extends LinearLayout implements OnClickListener {
    private static final String TAG = "zyw";

    private static final int MOVE_DISTANCE_MIN = 10;

    private float mTouchStartX;
    private float mTouchStartY;
    private float mRawX;
    private float mRawY;

    private float mRawStartX = 0;
    private float mRawStartY = 0;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private int mLastWindowHeight = 0;
    private View mContentView = null;

    private Button mBtnToFullscreen;
    private Button mBtnToMini;
    private Button mBtnToStardard;

    private boolean mIsShowing = false;
    private HomeReceiver mHomeReceiver;

    private int mScreenHeight;
    private int mStatusBarHeight;

    public FloatingView(Context context) {
        super(context);
        init();
    }

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initScreenWidthHeihgt();
        initWindowLayoutParams();
        prepareForAddView();
        prepareForServiceReveiver();
    }

    /**
	 * 
	 */
    @SuppressWarnings("deprecation")
    private void prepareForAddView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mContentView = inflater.inflate(R.layout.test, null);
        mContentView.setOnClickListener(this);
        mBtnToFullscreen = (Button) mContentView
                .findViewById(R.id.btn_fullscreen);
        mBtnToMini = (Button) mContentView.findViewById(R.id.btn_mini);
        mBtnToFullscreen.setOnClickListener(this);
        mBtnToMini.setOnClickListener(this);
        mBtnToStardard = (Button) mContentView.findViewById(R.id.btn_stardard);
        mBtnToStardard.setOnClickListener(this);
        addView(mContentView, LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        mWindowManager.addView(this, mWindowLayoutParams);
    }

    private void initScreenWidthHeihgt() {
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager = (WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        Activity activity = (Activity) getContext();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        mStatusBarHeight = rect.top;

        Log.d(TAG, "initScreenWidthHeihgt mScreenHeight = " + mScreenHeight);
        Log.d(TAG, "initScreenWidthHeihgt mStatusBarHeight = " + mStatusBarHeight);
    }

    @SuppressWarnings("deprecation")
    private void initWindowLayoutParams() {
        mWindowLayoutParams = ((FloatingApplication) getContext()
                .getApplicationContext()).getWindowManagerLayoutParams();

        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mWindowLayoutParams.format = PixelFormat.RGBA_8888;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        mWindowLayoutParams.x = 0;
        mWindowLayoutParams.y = 0;

        //Note: the following parameters is the key for width and height of display window
        mWindowLayoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mLastWindowHeight = mWindowLayoutParams.height;
    }

    private void prepareForServiceReveiver() {
        getContext().startService(
                new Intent(getContext(), RunningStateService.class));
        mHomeReceiver = new HomeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(RunningStateService.ACTION_STATE_HOME);
        filter.addAction(RunningStateService.ACTION_STATE_INNER);
        filter.addAction(RunningStateService.ACTION_STATE_OTHER);
        getContext().getApplicationContext().registerReceiver(mHomeReceiver,
                filter);
    }

    public void show() {
        if (null != mContentView && !mIsShowing) {
            Log.d(TAG, "show()");
            mWindowLayoutParams.height = mLastWindowHeight;
            mWindowManager.updateViewLayout(this, mWindowLayoutParams);
            mContentView.setVisibility(View.VISIBLE);
            mIsShowing = true;
        }
    }

    public void hide() {
        if (null != mContentView && mIsShowing) {
            Log.d(TAG, "hide()");
            mWindowLayoutParams.height = 0;
            mWindowManager.updateViewLayout(this, mWindowLayoutParams);
            mContentView.setVisibility(View.GONE);
            mIsShowing = false;
        }
    }

    public void release() {
        if (null != mWindowManager && null != mContentView) {
            Log.d(TAG, "release()");
            mWindowManager.removeView(this);
            mIsShowing = false;
            getContext().getApplicationContext().unregisterReceiver(
                    mHomeReceiver);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        mRawX = event.getRawX();
        mRawY = event.getRawY() - mStatusBarHeight; // remove the height of notification bar
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                mRawStartX = mRawX;
                mRawStartY = mRawY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isNeedUpdateViewPosition()) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedUpdateViewPosition()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    private boolean isNeedUpdateViewPosition() {
        return Math.abs(mRawX - mRawStartX) > MOVE_DISTANCE_MIN || Math.abs(mRawY - mRawStartY) > MOVE_DISTANCE_MIN;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() ");
        mRawX = event.getRawX();
        mRawY = event.getRawY() - mStatusBarHeight;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                updateViewPosition();
                break;
        }
        return true;
    }

    private void updateViewPosition() {
        mWindowLayoutParams.x = (int) (mRawX - mTouchStartX);
        mWindowLayoutParams.y = (int) (mRawY - mTouchStartY);
        mWindowManager.updateViewLayout(this, mWindowLayoutParams);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen:
                mWindowLayoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
                mWindowLayoutParams.height = WindowManager.LayoutParams.FILL_PARENT;
                mLastWindowHeight = mWindowLayoutParams.height;
                mWindowManager.updateViewLayout(this, mWindowLayoutParams);
                break;
            case R.id.btn_mini:
                mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                mWindowLayoutParams.x = 0;
                mLastWindowHeight = mWindowLayoutParams.height;
                mWindowManager.updateViewLayout(this, mWindowLayoutParams);
                break;
            case R.id.btn_stardard:
                mWindowLayoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
                mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                mWindowLayoutParams.x = 0;
                mLastWindowHeight = mWindowLayoutParams.height;
                mWindowManager.updateViewLayout(this, mWindowLayoutParams);
                break;
        }
    }

    class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "HomeReceiver onReceive()  action = " + action);
            if (RunningStateService.ACTION_STATE_HOME.equals(action)) {
                hide();
            } else if (RunningStateService.ACTION_STATE_INNER.equals(action)) {
                show();
            } else if (RunningStateService.ACTION_STATE_OTHER.equals(action)) {
                hide();
            }
        }

    }

}
