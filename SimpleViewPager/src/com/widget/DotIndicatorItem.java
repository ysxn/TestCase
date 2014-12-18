
package com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 参考类未使用
 * 
 * @author Administrator
 */
public class DotIndicatorItem extends ImageView {
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    private int mCurrentIndex = -1;

    public DotIndicatorItem(Context paramContext) {
        super(paramContext);
    }

    public DotIndicatorItem(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public DotIndicatorItem(Context paramContext,
            AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 在画布上写字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (false) {
            TextPaint paint = new TextPaint(1);
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            String str = String.valueOf(1 + this.mCurrentIndex);
            int k = (width - 3) * str.length();
            paint.setTextSize(k);
            paint.setColor(-16777216);// 设置画笔颜色
            canvas.drawText(str, (width - k / 2) / 2, (height + k) / 2, paint);
        }
    }

    public void resetCurrentIndex() {
        this.mCurrentIndex = -1;
    }

    public void setNum(int currentIndex) {
        this.mCurrentIndex = currentIndex;
    }
    
    /**
     * test
     * @return
     */
    public String dumpLog() {
        return "para:";
    }

    /**
     * set log enable
     * @param isTrue
     */
    public void setDebugEnable(boolean isTrue) {
        DEBUG = isTrue;
    }
}
