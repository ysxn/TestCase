package com.codezyw.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 带背景的文字 
 * 
 */
public class ColorTextView extends TextView {
	final static int DEFAULT_BORDER_COLOR = 0x00000000;
	private int mBorderColor;
	private Paint mTextPaint;
	private String mText;
	private Point mTextPoint;

	public ColorTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mBorderColor = DEFAULT_BORDER_COLOR;

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextSize(getTextSize());
		mTextPaint.setColor(0xff000000);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mTextPoint = new Point();
	}

	public void setDisplayText(String text) {
		mText = text;
		if (!TextUtils.isEmpty(mText)) {
			mTextPaint.measureText(mText);
		}
		measurePoint(getWidth(), getHeight());
	}
	
	public void setDisplayTextColor(int color) {
		mTextPaint.setColor(color);
	}
	
	public void setDisplayTextSize(float size) {
		mTextPaint.setTextSize(size);
		measurePoint(getWidth(), getHeight());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		measurePoint(w, h);
	}

	private void measurePoint(int w, int h) {
		mTextPoint.x = w / 2;
		if ("*".equals(mText)) {
			mTextPoint.y = (int) (h + mTextPaint.descent());
		} else {
			mTextPoint.y = (int) (h / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2) + 1;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (TextUtils.isEmpty(mText)) {
			return;
		}
		if (mBorderColor != DEFAULT_BORDER_COLOR) {
			canvas.drawColor(mBorderColor);
		}
		canvas.drawText(mText, mTextPoint.x, mTextPoint.y, mTextPaint);
	}

}
