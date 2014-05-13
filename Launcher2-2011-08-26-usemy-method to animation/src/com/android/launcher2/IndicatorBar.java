package com.android.launcher2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.android.launcher.R;

public class IndicatorBar extends View {
    private int mDownRadius = 1050;
    private int mIndicatorWidth = 0;
    private int mIndicatorHeight = 0;
    private float mXPos = 0f;
    private float mYPos = 0f;
    private float mXTotal = 0f;
    private Drawable mIndicator = null;

    public IndicatorBar(Context context) {
    	this(context, null);
    }
    
    public IndicatorBar(Context context, AttributeSet attrs) {
    	this(context, attrs, 0);
    }
    
    public IndicatorBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorBar, defStyle, 0);
        mDownRadius = a.getInt(R.styleable.IndicatorBar_indicatorRadius, 1050);
        a.recycle();
        mIndicator = getResources().getDrawable(R.drawable.zoom_bar_indicator);
        mIndicatorWidth = mIndicator.getIntrinsicWidth();
        mIndicatorHeight = mIndicator.getIntrinsicHeight();
        mIndicator.setBounds(0, 0, mIndicatorWidth, mIndicatorHeight);
        mXTotal = getResources().getDisplayMetrics().widthPixels;
    }

    public void setAlphaHtc(int currentScreen) {
        switch (currentScreen) {
            case 0:
                mXPos = -mXTotal/2 + mIndicatorWidth/2;
                break;
            case 1:
                mXPos = -mXTotal/4;
                break;
            case 2:
                mXPos = 0;
                break;
            case 3:
                mXPos = mXTotal/4;
                break;
            case 4:
                mXPos = mXTotal/2 - mIndicatorWidth/2;
                break;
            default:
                mXPos = 0;
                break;
        }
        
        invalidate();
    }
    
    public void setAlphaHtc(int currentScreen, float mScrollX) {
        mXPos = (mScrollX - mXTotal*2)/4*(mXTotal/2 - mIndicatorWidth/2)/mXTotal*2;
        System.out.println();
        if (mXPos <= -mXTotal/2 + mIndicatorWidth/2) {
            mXPos = -mXTotal/2 + mIndicatorWidth/2;
        } else if (mXPos >= mXTotal/2 - mIndicatorWidth/2) {
            mXPos = mXTotal/2 - mIndicatorWidth/2;
        }
        invalidate();
    }
    
    @Override 
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
        mPaint.setAlpha(125);
        canvas.save();
        
        canvas.translate(getWidth()/ 2, mDownRadius -50);
        canvas.drawCircle(0, 0, mDownRadius - 58, mPaint);
        canvas.restore();
        
        canvas.save();
        canvas.translate(getWidth()/ 2, mDownRadius + 12);
        // x^2 + y^2 = r^2 --> y = sqrt(r^2 - x^2); --> r = 640
        
        mYPos = (float) Math.sqrt(mDownRadius * mDownRadius - mXPos * mXPos);
        canvas.translate(- mIndicatorWidth / 2,  - mIndicatorHeight / 2);
        canvas.translate(mXPos, -mYPos);
        double delta = Math.acos(mXPos / mDownRadius) * 180 / Math.PI;
        canvas.rotate(90 - (float) delta, mIndicatorWidth / 2, mIndicatorHeight / 2);
        mIndicator.draw(canvas);

        canvas.restore();
    }
}
