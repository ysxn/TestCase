
package com.codezyw.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private volatile boolean mRunning = false;
    private SurfaceHolder mSurfaceHolder = null;
    private int mCount = 0;
    private int x = 0;
    private int y = 0;
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mSurfaceWidth = 0;
    private int mSurfaceHeight = 0;

    public GameSurfaceView(Context context) {
        super(context);
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        mRunning = true;
        mScreenWidth = UIHelper.getScreenWidth((Activity) context);
        mScreenHeight = UIHelper.getScreenHeight((Activity) context);
        x = mScreenWidth / 2;
        y = mScreenHeight / 2;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
        x = mSurfaceWidth / 2;
        y = mSurfaceHeight / 2;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mRunning = false;
    }

    public void run() {
        while (mRunning) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (mSurfaceHolder) {
                Draw();
            }
        }
    }

    private void Draw() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder == null || canvas == null) {
            return;
        }
        if (mCount < 100) {
            mCount++;
        } else {
            mCount = 0;
        }

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        Rect r = new Rect(0, 0, mSurfaceWidth, mSurfaceHeight);
        canvas.drawRect(r, mPaint);
        switch (mCount % 4) {
            case 0:
                mPaint.setColor(Color.BLUE);
                break;
            case 1:
                mPaint.setColor(Color.GREEN);
                break;
            case 2:
                mPaint.setColor(Color.RED);
                break;
            case 3:
                mPaint.setColor(Color.YELLOW);
                break;
            default:
                mPaint.setColor(Color.WHITE);
                break;
        }
        canvas.drawCircle(x, y, 50, mPaint);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

}
