
package com.codezyw.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class GestureView extends View {
    public static final int LEFT = 3;// 3;//2;
    public static final int RIGHT = 4;// 3;2;//
    public static final int DOWN = 1;// 4;5;//
    public static final int UP = 2;// 5;4;//
    int IDLE_COLOR = Color.GRAY;
    int WORK_COLOR = Color.GREEN;
    int STROKE_WIDTH = 30;
    Paint mPaint;
    int mLevel = 0;
    int mDirection;
    int mState;// 0 IDLE 1 WORK
    static final int STATE_IDLE = 0;
    static final int STATE_WORKING = 1;
    static final int TEXT_SIZE = 200;

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setTextSize(TEXT_SIZE);

        mPaint.setStrokeWidth(STROKE_WIDTH);
    }

    void reset() {
        mState = STATE_IDLE;
        mDirection = 0;
        mLevel = 0;
        this.invalidate();
    }

    void setDirection(int dir) {
        mDirection = dir;
        invalidate();
    }

    void setState(int state) {
        mState = state;
        invalidate();
    }

    public int getState() {
        return mState;
    }

    void setScale(int scale) {
        mLevel = scale;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        float r = Math.min(width, height) / 5;
        int centerX = width / 2;
        int centerY = height / 2;
        if (mState == STATE_IDLE) {
            mPaint.setColor(IDLE_COLOR);
            canvas.drawCircle(centerX, centerY, r, mPaint);
            return;
        }

        if (mLevel >= 5) {
            r += r * (mLevel - 5) / 6;
        }
        // if (mLevel == MainActivity.NEAR) {
        // r -= r * 1 / 6;
        // } else if (mLevel == MainActivity.FAR) {
        // r += r * 1 / 6;
        // }
        mPaint.setColor(WORK_COLOR);

        canvas.drawCircle(centerX, centerY, r, mPaint);

        mPaint.setColor(Color.RED);
        if (mLevel > 0) {
            canvas.drawText(Integer.toString(mLevel), centerX - TEXT_SIZE / 3, centerY + TEXT_SIZE
                    / 3, mPaint);
        }
        switch (mDirection) {
            case UP:
                canvas.drawLine(centerX, centerY, centerX, centerY - r, mPaint);
                break;
            case DOWN:
                canvas.drawLine(centerX, centerY, centerX, centerY + r, mPaint);
                break;
            case LEFT:
                canvas.drawLine(centerX, centerY, centerX - r, centerY, mPaint);
                break;
            case RIGHT:
                canvas.drawLine(centerX, centerY, centerX + r, centerY, mPaint);
                break;
        }
    }

}
