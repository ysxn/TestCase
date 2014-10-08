
package com.zyw.videofileplayer;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.VideoView;

import com.zyw.videofileplayer.GestureRecognizer;
import com.zyw.videofileplayer.MovieControllerOverlay;
import com.zyw.videofileplayer.GestureRecognizer.Listener;

public class VideoZoomController
        implements GestureRecognizer.Listener
{
    private static final float MAX_DOUBLETAP_RATIO = 10.0F;
    private static final float MAX_SCALE_RATIO = 10.0F;
    private static final float MAX_SCALE_RATIO_2K_4K = 10.0F;
    private static final float MINI_DOUBLETAP_RATIO = 2.0F;
    private static final float MINI_SCALE_RATIO = 1.0F;
    private static final String TAG = "VideoZoomController";
    private boolean isVideoZoomEnabled;
    private Context mContext;
    private RectF mCurrentDispHitRectF = new RectF();
    private Matrix mCurrentDispMatrix = new Matrix();
    private RectF mCurrentDispRectF = new RectF();
    private int mDefaultPreviewHeight = 0;
    private int mDefaultPreviewWidth = 0;
    private float mDispScaleRatio = 1.0F;
    private float mFocusX;
    private float mFocusY;
    private final GestureRecognizer mGestureRecognizer;
    private float mMaxVideoScale;
    private View mOverlay;
    private int mRootViewWdith;
    private Matrix mScaleMatrix = new Matrix();
    private RectF mTargetRectF = new RectF();
    private int mVideoHeight;
    private View mVideoRoot;
    private float mVideoScaleRatio = 1.0F;
    private ZoomVideoView mVideoView;
    private int mVideoWidth;

    private class LayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            if (!isVideoZoomEnabled) {
                return;
            }
            int rootviewWidth = mVideoView.getRootView().getWidth();
            if (rootviewWidth != mRootViewWdith) {
                Log.i(TAG, " OnGlobalLayoutListener root width  " + rootviewWidth);
                // 1.get current display rect.
                calculateCurrentDisplayRectF();
                Log.i(TAG, " OnGlobalLayoutListener mCurrentDispRectF " + mCurrentDispRectF);
                // 2.correct the position
                int rootviewHeight = mVideoView.getRootView().getHeight();
                // 3.1 calculate default preview size
                // should optimize here, when play a video, default value is
                // always the same.
                calculateDefaultPreviewSize();
                // 3.2 now correct the video position and size by case.
                // case 1: preview width is shorter than default preview width
                if ((mCurrentDispRectF.width() < mDefaultPreviewWidth)
                        && (mCurrentDispRectF.height() < mDefaultPreviewHeight)) {
                    mDispScaleRatio = 1.0f;
                    mVideoView.requestLayout();
                    mVideoRoot.setScrollX(0);
                    mVideoRoot.setScrollY(0);
                    Log.i(TAG, " OnGlobalLayoutListener case 1");
                }
                // case 2: one side is longer than default value, another side
                // is shorter than default value.
                if (((mCurrentDispRectF.width() > mDefaultPreviewWidth)
                        && (mCurrentDispRectF.height() < rootviewHeight))
                        || ((mCurrentDispRectF.width() < rootviewWidth)
                        && (mCurrentDispRectF.height() > mDefaultPreviewHeight))) {
                    // portrait
                    if (rootviewHeight > rootviewWidth) {
                        int offsetX = 0;
                        if (mCurrentDispRectF.left > 0.0F) {
                            offsetX = -Math.round(mCurrentDispRectF.left);
                        } else if (mCurrentDispRectF.right < rootviewWidth) {
                            offsetX = rootviewWidth - Math.round(mCurrentDispRectF.right);
                        }
                        mVideoRoot.setScrollY(0);
                        mVideoRoot.scrollBy(-offsetX, 0);
                        mDispScaleRatio = 1.0F * mCurrentDispRectF.width() / mDefaultPreviewWidth;
                        Log.i(TAG, " OnGlobalLayoutListener case 2.1 mDispScaleRatio "
                                + mDispScaleRatio
                                + " offsetX " + offsetX);
                    } else {
                        // landscape
                        int offsetY = 0;
                        if (mCurrentDispRectF.top > 0.0F) {
                            offsetY = -Math.round(mCurrentDispRectF.top);
                        } else if (mCurrentDispRectF.bottom < rootviewHeight) {
                            offsetY = rootviewWidth - Math.round(mCurrentDispRectF.right);
                        }
                        mVideoRoot.setScrollX(0);
                        mVideoRoot.scrollBy(0, -offsetY);
                        mDispScaleRatio = 1.0F * mCurrentDispRectF.height() / mDefaultPreviewHeight;
                        Log.i(TAG, " OnGlobalLayoutListener case 2.2 mDispScaleRatio "
                                + mDispScaleRatio
                                + " offsetY " + offsetY);
                    }
                }
                // case 3: both side are longer than screen
                if ((mCurrentDispRectF.width() > rootviewWidth)
                        && (mCurrentDispRectF.height() > rootviewHeight)) {
                    // correct x coordinate
                    int offsetX = 0;
                    int offsetY = 0;

                    if (mCurrentDispRectF.left > 0.0F) {
                        offsetX = -Math.round(mCurrentDispRectF.left);
                    } else if (mCurrentDispRectF.top < rootviewWidth) {
                        offsetX = rootviewWidth - Math.round(mCurrentDispRectF.right);
                    }

                    if (mCurrentDispRectF.top > 0) {
                        offsetY = Math.round(mCurrentDispRectF.top);
                    } else if (mCurrentDispRectF.bottom < rootviewHeight) {
                        offsetY = rootviewHeight - Math.round(mCurrentDispRectF.bottom);
                    }

                    mVideoRoot.scrollBy(-offsetX, -offsetY);
                    mDispScaleRatio = 1.0F * mCurrentDispRectF.height() / mDefaultPreviewHeight;
                    Log.i(TAG, " OnGlobalLayoutListener case 3 mDispScaleRatio " + mDispScaleRatio
                            + " offsetX " + offsetX + " offsetY " + offsetY);

                }
                mRootViewWdith = rootviewWidth;
            }
        }
    }

    public VideoZoomController(Context context, View videoroot,
            VideoView videoView, View overlay) {
        mContext = context;
        mVideoView = ((ZoomVideoView) videoView);
        mOverlay = overlay;
        mVideoRoot = videoroot;
        mRootViewWdith = mVideoRoot.getRootView().getWidth();
        mVideoView.setVideoZoomController(this);
        mGestureRecognizer = new GestureRecognizer(mContext, this);
        mVideoRoot.getRootView().setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event)
            {
                Log.i(TAG, ">>>>>>>>onTouch event="+event);
                mGestureRecognizer.onTouchEvent(event);
                return true;
            }
        });
        ViewTreeObserver vto = mVideoRoot.getRootView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new LayoutListener());
    }

    @Override
    public boolean onSingleTapUp(float x, float y) {
        ((MovieControllerOverlay) mOverlay).show();
        return true;
    }

    @Override
    public boolean onDoubleTap(float x, float y) {
        if (!isVideoZoomEnabled) {
            return true;
        }

        if (isAtMinimalScale()) {
            Rect r = new Rect();
            mVideoView.getHitRect(r);
            Log.i(TAG, " onDoubleTap getHitRect " + r);
            float rate = 1.0F * mVideoRoot.getWidth() / r.width();
            rate = Math.max(rate, 1.0F * mVideoRoot.getHeight() / r.height());
            rate = Math.min(rate, MAX_DOUBLETAP_RATIO);
            
            if (rate < MINI_DOUBLETAP_RATIO) {
                rate = MINI_DOUBLETAP_RATIO;
            }
            zoomToPoint(rate, x, y, mVideoRoot);
        } else {
            setScaleRatio(1.0F);
            mVideoRoot.setScrollX(0);
            mVideoRoot.setScrollY(0);
        }
        return true;
    }
    
    @Override
    public void onDown(float x, float y) {
    }

    @Override
    public void onUp() {
    }
    
    private void calculateCurrentDisplayRectF() {
        Rect r = new Rect();
        //1.get current scroll hit rect
        mVideoView.getHitRect(r);
        mCurrentDispHitRectF.set(r);
        //2.get current scroll value
        int scrollX = mVideoRoot.getScrollX();
        int scrollY = mVideoRoot.getScrollY();
        //3.get current disp rect. relative to hit rect.
        mCurrentDispMatrix.reset();
        mCurrentDispMatrix.postTranslate(-scrollX, -scrollY);
        //4.get current display rect
        mCurrentDispMatrix.mapRect(mCurrentDispRectF, mCurrentDispHitRectF);
    }

    private void calculateDefaultPreviewSize() {
        int width = mVideoRoot.getWidth();
        int height = mVideoRoot.getHeight();
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                Log.i(TAG, "image too tall, correcting.");
                height = width * mVideoHeight / mVideoWidth;
            } else if (mVideoWidth * height < width * mVideoHeight) {
                Log.i(TAG, "image too wide, correcting.");
                width = height * mVideoWidth / mVideoHeight;
            }
        }

        mDefaultPreviewWidth = width;
        mDefaultPreviewHeight = height;
        Log.i(TAG, " mDefaultPreviewWidth " + width
                + " mDefaultPreviewHeight " + height);
        mVideoScaleRatio = getVideoScaleRatio(mDefaultPreviewWidth,
                mDefaultPreviewHeight, mVideoWidth, mVideoHeight);
        Log.i(TAG, " calculateDefaultPreviewSize mVideoScaleRatio "
                + mVideoScaleRatio);

    }

    public boolean isAtMinimalScale() {
        return isAlmostEqual(mDispScaleRatio, MINI_SCALE_RATIO);
    }
    
    private static boolean isAlmostEqual(float a, float b) {
        float diff = a - b;
        if (diff < 0.0F)
            diff = -diff;
        return diff < 0.02F;
    }

    private static float getVideoScaleRatio(int defaultDispW, int defaultDispH, int videoW, int videoH) {
        return Math.min(1.0F * defaultDispW / videoW, 1.0F * defaultDispH / videoH);
    }

    public boolean isInZoomState() {
        return getDispScaleRatio() > MINI_SCALE_RATIO;
    }

    private float getDispScaleRatio() {
        Log.i(TAG, " getScaleRatio mDispScaleRatio " + mDispScaleRatio);
        return mDispScaleRatio;
    }

    public int getDispScaleWidth() {
        return Math.round(1.0F * getDispScaleHeight() * mDefaultPreviewWidth / mDefaultPreviewHeight);
    }

    public int getDispScaleHeight() {
        return Math.round(mTargetRectF.height());
    }

    public void resizeToDefaultSize() {
        mDispScaleRatio = 1.0F;
        mVideoRoot.setScrollX(0);
        mVideoRoot.setScrollY(0);
    }

    private void setScaleRatio(float ratio) {
        Log.i(TAG, "setScaleRatio ratio = " + ratio);
        mDispScaleRatio = ratio;
        mVideoView.requestLayout();
    }

    @Override
    public boolean onScaleBegin(float focusX, float focusY) {
        if (!isVideoZoomEnabled) {
            return true;
        }

        Log.i(TAG, "onScaleBegin");
        mScaleMatrix.reset();
        mFocusX = focusX;
        mFocusY = focusY;
        return true;
    }
    
    @Override
    public boolean onScale(float focusX, float focusY, float scale) {
        if (!isVideoZoomEnabled) {
            return true;
        }

        Log.i(TAG, " onScale focusX " + focusX + " focusY " + focusY);
        float accScale = scale * getDispScaleRatio();
        //if accScale is bigger than max, use max value
        float maxScale = mMaxVideoScale / mVideoScaleRatio;
        Log.i(TAG, " onScale maxScale " + maxScale);
        accScale = Math.max(1.0F, Math.min(accScale, maxScale));
        Log.i(TAG, " onScale accScale " + accScale);
        //if current scale ratio is max, return
        if (accScale == getDispScaleRatio()) {
            return true;
        }
        
        if (isAlmostEqual(accScale, 1.0F)) {
            mDispScaleRatio = 1.0F;
            return true;
        }
        //get current display rect
        calculateCurrentDisplayRectF();
        Log.i(TAG, " onScale mCurrentDispRect " + mCurrentDispRectF);
        //calculate the target display rect
        //reset matrix
        mScaleMatrix.reset();
        //calculate the transform matrix, relative to previous display rect
        mScaleMatrix.postScale(scale, scale, mFocusX, mFocusY);
        //focus center should move to new position
        mScaleMatrix.postTranslate(focusX - mFocusX, focusY - mFocusY);
        mScaleMatrix.mapRect(mTargetRectF, mCurrentDispRectF);
        Log.i(TAG, " onScale mTargetRectF " + mTargetRectF);
        
        //calculate x,y coordinate offset value
        //
        float offsetX = mTargetRectF.centerX() - mCurrentDispHitRectF.centerX();
        float offsetY = mTargetRectF.centerY() - mCurrentDispHitRectF.centerY();
        Log.i(TAG, " onScale  offsetX " + offsetX + " offsetY " + offsetY);
        
        //4.
        setScaleRatio(accScale);
        mVideoRoot.scrollTo(-Math.round(offsetX), -Math.round(offsetY));
        mFocusX = focusX;
        mFocusY = focusY;
        return true;
    }
    
    @Override
    public void onScaleEnd() {
        if (!isVideoZoomEnabled) {
            return ;
        }

        Log.i(TAG, "onScaleEnd");
        if (isAtMinimalScale()) {
            mVideoRoot.setScrollX(0);
            mVideoRoot.setScrollY(0);
            mVideoView.requestLayout();
            return;
        }

        //1. get current display rect
        calculateCurrentDisplayRectF();
        Log.i(TAG, " onScaleEnd mCurrentDispRectF " + mCurrentDispRectF);
        
        //2.if video width is smaller than screen width, move the video to center of X coordinate
        if ((mCurrentDispRectF.left > 0.0F)
                || (mCurrentDispRectF.right < mVideoRoot.getWidth())) {
            mVideoRoot.setScrollX(0);
        }
        
        // 3.if video height is smaller than screen height, move the video to
        // center of Y coordinate
        if ((mCurrentDispRectF.top > 0.0F)
                || (mCurrentDispRectF.bottom < mVideoRoot.getHeight())) {
            this.mVideoRoot.setScrollY(0);
        }

        // 4.if video size is smaller than default size, use default size
        // for example, screen rotate
        Log.i(TAG, " onScaleEnd mDefaultPreviewWidth "
                + this.mDefaultPreviewWidth + " mDefaultPreviewHeight "
                + this.mDefaultPreviewHeight);
        if ((mCurrentDispRectF.width() < mDefaultPreviewWidth)
                || (mCurrentDispRectF.height() < mDefaultPreviewHeight)) {
            mDispScaleRatio = 1.0F;
            mVideoView.requestLayout();
            mVideoRoot.setScrollX(0);
            mVideoRoot.setScrollY(0);
        }
    }

    @Override
    public boolean onScroll(float dx, float dy, float totalX, float totalY) {
        if (!isVideoZoomEnabled) {
          return true;
        }
        if ((getDispScaleRatio() > MINI_SCALE_RATIO)) {
            // 1.we want to move x and y distance
            float offsetX = -dx;
            float offsetY = -dy;
            // 2.get current display rect
            calculateCurrentDisplayRectF();

            if (offsetX >= 0.0F) {
                // will move to right, find max offset we can
                float maxOffsetX = 0.0F;
                if (mCurrentDispRectF.left >= 0.0F) {
                    maxOffsetX = 0;
                } else {
                    maxOffsetX = -mCurrentDispRectF.left;
                }

                // max offset is found, correct offsetX value
                if (offsetX > maxOffsetX) {
                    offsetX = maxOffsetX;
                }
            } else {
                // will move to left, find max offset we can
                float maxOffsetX = 0.0F;
                if (mCurrentDispRectF.right <= mVideoRoot.getWidth()) {
                    maxOffsetX = 0;
                } else {
                    maxOffsetX = mVideoRoot.getWidth() - mCurrentDispRectF.right;
                }

                // max offset is found, correct offsetX value
                if (offsetX < maxOffsetX) {
                    offsetX = maxOffsetX;
                }
            }

            //second correct y coordinate
            if (offsetY >= 0.0F) {
                // will move to bottom, find max offset we can
                float maxOffsetY = 0.0F;
                if (mCurrentDispRectF.top >= 0.0F) {
                    maxOffsetY = 0;
                } else {
                    maxOffsetY = -mCurrentDispRectF.top;
                }

                // max offset is found, correct offsetY value
                if (offsetY > maxOffsetY) {
                    offsetY = maxOffsetY;
                }

            } else {
                // will move to top, find max offset we can
                float maxOffsetY = 0.0F;
                if (mCurrentDispRectF.bottom <= mVideoRoot.getHeight()) {
                    maxOffsetY = 0;
                } else {
                    maxOffsetY = mVideoRoot.getHeight() - mCurrentDispRectF.bottom;
                }

                // max offset is found, correct offsetY value
                if (offsetY < maxOffsetY) {
                    offsetY = maxOffsetY;
                }

            }
            // move to right position
            mVideoRoot.scrollBy(-(int) offsetX, -(int) offsetY);
        }
        
        return true;
  }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float paramFloat1, float paramFloat2){
        return false;
    }

    public void setVideoSize(int width, int height) {
        Log.i(TAG, "setVideoSize width " + width + " height " + height);

        if (width > 0 && height > 0) {
            mVideoWidth = width;
            mVideoHeight = height;
            isVideoZoomEnabled = ((mVideoWidth >= 320) && (mVideoHeight >= 240))
                    || ((mVideoWidth >= 240) || (mVideoHeight >= 320));
            isVideoZoomEnabled = true;
            // for streaming , the video size maybe change when playing
            if (!isVideoZoomEnabled && isInZoomState()) {
                setScaleRatio(MINI_SCALE_RATIO);
                mVideoRoot.setScrollX(0);
                mVideoRoot.setScrollY(0);
            }
            if (isVideoZoomEnabled) {
                if ((mVideoWidth >= 1920) && (mVideoHeight >= 1080)
                        || (mVideoWidth >= 1080) && (mVideoHeight >= 1920)) {
                    mMaxVideoScale = MAX_SCALE_RATIO_2K_4K;
                } else {
                    mMaxVideoScale = MAX_SCALE_RATIO;
                }
                calculateDefaultPreviewSize();
            }
        }
    }

    private void zoomToPoint(float scale, float x, float y, final View videoroot) {

        // 1.get current disp hit rect.
        Rect r = new Rect();
        mVideoView.getHitRect(r);
        mCurrentDispHitRectF.set(r);
        // 2.calculate the target display rect
        // reset matrix
        Log.i(TAG, " zoomToPoint mCurrentDispHitRect " + this.mCurrentDispHitRectF);
        mScaleMatrix.reset();
        // calculate the transform matrix, relative to previous disp rect.
        mScaleMatrix.postScale(scale, scale, mCurrentDispHitRectF.centerX(),
                mCurrentDispHitRectF.centerY());
        mScaleMatrix.mapRect(mTargetRectF, mCurrentDispHitRectF);
        Log.i(TAG, " zoomToPoint targetRectF " + mTargetRectF);
        // 3.get clicked position in magnified rect
        float[] screenTouchPos = {
                x, y
        };
        float[] magnifiedTouchPos = {
                0.0F, 0.0F
        };
        mScaleMatrix.mapPoints(magnifiedTouchPos, screenTouchPos);
        Log.i(TAG, " zoomToPoint  screenTouchPos[0] " + screenTouchPos[0]
                + " screenTouchPos[1] " + screenTouchPos[1]);
        Log.i(TAG, " zoomToPoint  magnifiedTouchPos[0] " + magnifiedTouchPos[0]
                + " magnifiedTouchPos[1] " + magnifiedTouchPos[1]);

        // 4.we want to move the magnified touch position to the center of
        // sceen, so we calculate the offsetX and offsetY
        float offsetX = mCurrentDispHitRectF.centerX() - magnifiedTouchPos[0];
        float offsetY = mCurrentDispHitRectF.centerY() - magnifiedTouchPos[1];
        Log.i(TAG, " zoomToPoint offsetX " + offsetX + " offsetY " + offsetY);

        // 5.step 4 cal result may be not right, we will correct it.
        // first we will correct x coordinate.
        if (offsetX >= 0.0F) {
            // will move to right, find max offset we can
            float maxOffsetX = 0.0F;
            if (mTargetRectF.left >= 0.0F) {
                maxOffsetX = 0;
            } else {
                maxOffsetX = -mTargetRectF.left;
            }

            // max offset is found, correct offsetX value
            if (offsetX > maxOffsetX) {
                offsetX = maxOffsetX;
            }
        } else {
            // will move to left, find max offset we can
            float maxOffsetX = 0.0F;
            if (mTargetRectF.right <= videoroot.getWidth()) {
                maxOffsetX = 0;
            } else {
                maxOffsetX = videoroot.getWidth() - mTargetRectF.right;
            }

            // max offset is found, correct offsetX value
            if (offsetX < maxOffsetX) {
                offsetX = maxOffsetX;
            }
        }

        // second correct y coordinate
        if (offsetY >= 0.0F) {
            // will move to bottom, find max offset we can
            float maxOffsetY = 0.0F;
            if (mTargetRectF.top >= 0.0F) {
                maxOffsetY = 0;
            } else {
                maxOffsetY = -mTargetRectF.top;
            }

            // max offset is found, correct offsetY value
            if (offsetY > maxOffsetY) {
                offsetY = maxOffsetY;
            }

        } else {
            // will move to top, find max offset we can
            float maxOffsetY = 0.0F;
            if (mTargetRectF.bottom <= videoroot.getHeight()) {
                maxOffsetY = 0;
            } else {
                maxOffsetY = videoroot.getHeight() - mTargetRectF.bottom;
            }

            // max offset is found, correct offsetY value
            if (offsetY < maxOffsetY) {
                offsetY = maxOffsetY;
            }

        }

        Log.i(TAG, " zoomToPoint after correct offsetX " + offsetX + " offsetY " + offsetY);
        Log.i(TAG, " zoomToPoint videoRoot scrollX " + videoroot.getScrollX() + " scrollY"
                + videoroot.getScrollY());
        // 6.magnified video and move to the right position
        setScaleRatio(scale);
        videoroot.scrollTo(-(int) offsetX, -(int) offsetY);
    }
}
