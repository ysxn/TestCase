
package com.android_05_07_example_bitmap;

import android.view.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;

import java.lang.Thread;

public class GameView extends View implements Runnable {

    private Paint mPaint = null;
    Bitmap mBitQQ = null;
    Bitmap mBitDeatTop = null;
    int miDTX = 0;

    public GameView(Context context) {
        super(context);
        mPaint = new Paint();
        miDTX = 0;
        mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.p1)).getBitmap();
        mBitDeatTop = ((BitmapDrawable) getResources().getDrawable(R.drawable.p2)).getBitmap();
        new Thread(this).start();
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        GameView.drawImage(canvas, mBitQQ, 0, 0);
        GameView.drawImage(canvas, mBitDeatTop, miDTX, mBitQQ.getHeight(),
                mBitDeatTop.getWidth(), mBitDeatTop.getHeight() / 2, 0, 0);

    }

    private static void drawImage(Canvas canvas, Bitmap bitmap,
            int x, int y, int w, int h, int left, int top) {
        Rect src = new Rect();
        Rect dst = new Rect();
        src.left = left;
        src.top = top;
        src.right = left + w;
        src.bottom = top + h;

        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;
        canvas.drawBitmap(bitmap, src, dst, null);

        src = null;
        dst = null;

    }

    private static void drawImage(Canvas canvas, Bitmap bitmap, int left, int top) {
        canvas.drawBitmap(bitmap, left, top, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                miDTX = 0;
                break;
        }

        return true;

    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            postInvalidate();
        }
    }

}
