
package com.example.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BaseView(this));
    }

    class BaseView extends View {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public BaseView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.save();
            int width = getWidth();
            int height = getHeight();
            mPaint.setColor(Color.LTGRAY);
            canvas.drawRect(0, 0, 100, 100, mPaint);
            mPaint.setColor(Color.YELLOW);
            canvas.drawRect(100, 0, 200, 100, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawRect(0, 100, 100, 200, mPaint);
            mPaint.setColor(Color.BLUE);
            int size = 28;
            mPaint.setTextSize(size);
            mPaint.setTextAlign(Paint.Align.LEFT);
            int wordWidth = (int) mPaint.measureText("R");
            int ascent = (int) mPaint.ascent();
            int descent = (int) mPaint.descent();
            int fontMetrics_ascent = (int) mPaint.getFontMetrics().ascent;
            int fontMetrics_descent = (int) mPaint.getFontMetrics().descent;
            int fontMetrics_top = (int) mPaint.getFontMetrics().top;
            int fontMetrics_bottom = (int) mPaint.getFontMetrics().bottom;
            int fontMetrics_leading = (int) mPaint.getFontMetrics().leading;
            canvas.drawText("R", 100, 100, mPaint);
            int startY = 200;
            int startX = 0;
            int lineHeight = descent - ascent;
            canvas.drawText("{Q TextSize:" + size + " ascent:" + ascent + " descent:" + descent + " wordWidth:" + wordWidth, startX, startY, mPaint);
            canvas.drawText("fontMetrics{", startX, startY + lineHeight * 1, mPaint);
            canvas.drawText("    ascent:" + fontMetrics_ascent, startX, startY + lineHeight * 2, mPaint);
            canvas.drawText("    descent:" + fontMetrics_descent, startX, startY + lineHeight * 3, mPaint);
            canvas.drawText("    top:" + fontMetrics_top, startX, startY + lineHeight * 4, mPaint);
            canvas.drawText("    bottom:" + fontMetrics_bottom, startX, startY + lineHeight * 5, mPaint);
            canvas.drawText("    leading:" + fontMetrics_leading, startX, startY + lineHeight * 6, mPaint);
            canvas.drawText("}", startX, startY + lineHeight * 7, mPaint);
            canvas.restore();
        }
    }
}
