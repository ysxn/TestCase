package com.mediatek.mtklogger;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

public class LinearColorBar
  extends LinearLayout
{
  private static final String TAG = "MTKLogger/LinearColorBar";
  final Paint mColorGradientPaint = new Paint();
  final Path mColorPath = new Path();
  final Paint mEdgeGradientPaint = new Paint();
  final Path mEdgePath = new Path();
  private float mGreenRatio;
  float mInterestingLeftRadio;
  float mInterestingRightRadio;
  private int mLeftColor = -16737844;
  int mLineWidth;
  private int mMiddleColor = -16737844;
  final Paint mPaint = new Paint();
  final Rect mRect = new Rect();
  private float mRedRatio;
  private int mRightColor = -7829368;
  private boolean mShowingGreen;
  private float mYellowRatio;
  
  public LinearColorBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setWillNotDraw(false);
    this.mPaint.setStyle(Paint.Style.FILL);
    this.mColorGradientPaint.setStyle(Paint.Style.FILL);
    this.mColorGradientPaint.setAntiAlias(true);
    this.mEdgeGradientPaint.setStyle(Paint.Style.STROKE);
    if (getResources().getDisplayMetrics().densityDpi >= 240) {}
    for (int i = 2;; i = 1)
    {
      this.mLineWidth = i;
      this.mEdgeGradientPaint.setStrokeWidth(this.mLineWidth);
      this.mEdgeGradientPaint.setAntiAlias(true);
      return;
    }
  }
  
  private void updateIndicator()
  {
    int i = getPaddingTop() - getPaddingBottom();
    if (i < 0) {
      i = 0;
    }
    this.mRect.top = i;
    this.mRect.bottom = getHeight();
    if (this.mShowingGreen) {
      this.mColorGradientPaint.setShader(new LinearGradient(0.0F, 0.0F, 0.0F, i - 2, 0xFFFFFF & this.mRightColor, this.mRightColor, Shader.TileMode.CLAMP));
    }
    for (;;)
    {
      this.mEdgeGradientPaint.setShader(new LinearGradient(0.0F, 0.0F, 0.0F, i / 2, 10526880, -6250336, Shader.TileMode.CLAMP));
      return;
      this.mColorGradientPaint.setShader(new LinearGradient(0.0F, 0.0F, 0.0F, i - 2, 0xFFFFFF & this.mMiddleColor, this.mMiddleColor, Shader.TileMode.CLAMP));
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = 0 + (int)(i * this.mRedRatio);
    int k = j + (int)(i * this.mYellowRatio);
    this.mColorPath.reset();
    this.mEdgePath.reset();
    if (this.mInterestingLeftRadio < this.mInterestingRightRadio)
    {
      int i1 = (int)(i * this.mInterestingLeftRadio);
      int i2 = (int)(i * this.mInterestingRightRadio);
      int i3 = this.mRect.top;
      this.mColorPath.moveTo(i1, this.mRect.top);
      this.mColorPath.cubicTo(i1, 0.0F, -2.0F, i3, -2.0F, 0.0F);
      this.mColorPath.lineTo(-1 + (i + 2), 0.0F);
      this.mColorPath.cubicTo(-1 + (i + 2), i3, i2, 0.0F, i2, this.mRect.top);
      this.mColorPath.close();
      float f = 0.5F + this.mLineWidth;
      this.mEdgePath.moveTo(-2.0F + f, 0.0F);
      this.mEdgePath.cubicTo(-2.0F + f, i3, f + i1, 0.0F, f + i1, this.mRect.top);
      this.mEdgePath.moveTo(-1 + (i + 2) - f, 0.0F);
      this.mEdgePath.cubicTo(-1 + (i + 2) - f, i3, i2 - f, 0.0F, i2 - f, this.mRect.top);
    }
    if (!this.mEdgePath.isEmpty())
    {
      paramCanvas.drawPath(this.mEdgePath, this.mEdgeGradientPaint);
      paramCanvas.drawPath(this.mColorPath, this.mColorGradientPaint);
    }
    int m = 0;
    if (j < 0)
    {
      this.mRect.left = 0;
      this.mRect.right = j;
      this.mPaint.setColor(this.mLeftColor);
      paramCanvas.drawRect(this.mRect, this.mPaint);
      i -= j - 0;
      m = j;
    }
    if (m < k)
    {
      this.mRect.left = m;
      this.mRect.right = k;
      this.mPaint.setColor(this.mMiddleColor);
      paramCanvas.drawRect(this.mRect, this.mPaint);
      i -= k - m;
      m = k;
    }
    int n = m + i;
    if (m < n)
    {
      this.mRect.left = m;
      this.mRect.right = n;
      this.mPaint.setColor(this.mRightColor);
      paramCanvas.drawRect(this.mRect, this.mPaint);
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    updateIndicator();
  }
  
  public void setGradientPaint(float paramFloat1, float paramFloat2)
  {
    this.mInterestingLeftRadio = paramFloat1;
    this.mInterestingRightRadio = paramFloat2;
  }
  
  public void setRatios(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mRedRatio = paramFloat1;
    this.mYellowRatio = paramFloat2;
    this.mGreenRatio = paramFloat3;
    invalidate();
  }
  
  public void setShowingGreen(boolean paramBoolean)
  {
    if (this.mShowingGreen != paramBoolean)
    {
      this.mShowingGreen = paramBoolean;
      invalidate();
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.LinearColorBar
 * JD-Core Version:    0.7.0.1
 */