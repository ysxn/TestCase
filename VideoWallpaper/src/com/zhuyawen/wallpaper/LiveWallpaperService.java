
package com.zhuyawen.wallpaper;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class LiveWallpaperService extends WallpaperService {
    public static final String TAG = "VideoWallpaperActivity";

    public static final boolean DEBUG = false;

    public static final String PREFERENCES = "com.zhuyawen.wallpaper.LiveWallpaperSettings";

    public static final String PREFERENCE_BGCOLOR = "preference_bgcolor";

    public static final String PREFERENCE_FIXSCREEN = "preference_fixscreen";

    public static final String PREFERENCE_SELECTFILE = "preference_selectfile";

    public static String mFileName = " ";

    private Object mLockObject = new Object();

    private final int FIX_SCREEN = 3;

    private final int FIX_WIDTH = 2;

    private final int FIX_HEIGHT = 1;

    private final int FIX_VIDEO = 0;

    private int mFixScreen = FIX_WIDTH;

    private Bitmap mBitmap = null;

    private float mTimeBase = 0f;

    private long mCurrentPts = 0l;

    private int mTargetWidth = 0;

    private int mTargetHeight = 0;

    private int mScreenWidth = 0;

    private int mScreenHeight = 0;

    private Rect mRect = new Rect();

    private int mBgColor = Color.WHITE;

    @Override
    public Engine onCreateEngine() {
        return new WallPaperEngine();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        synchronized (mLockObject) {
            if (WrapFFmpeg.isFileOpen()) {
                WrapFFmpeg.exit();
            }
            if (mBitmap != null) {
                mBitmap.recycle();
                mBitmap = null;
            }
        }
        if (DEBUG)
            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>out onDestroy," + Thread.currentThread().getId());

    }

    public class WallPaperEngine extends Engine implements
            SharedPreferences.OnSharedPreferenceChangeListener {

        private SharedPreferences prefs;

        private RenderThread mRenderThread = null;

        private long mCurrentTime = 0;

        public WallPaperEngine() {
            prefs = LiveWallpaperService.this.getSharedPreferences(PREFERENCES, 0);
            prefs.registerOnSharedPreferenceChangeListener(this);
            setBgColor();
            setFixScreen();
        }

        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>onSharedPreferenceChanged,key=" + key + ", value="
                        + Integer.parseInt(prefs.getString(key, "8")));
            setBgColor();
            setFixScreen();
            // mFixHow = prefs.getString(key, "null");
            // painting.setRadius(Integer.parseInt(prefs.getString(
            // PREFERENCE_RADIUS, "10")));
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>onCreate," + Thread.currentThread().getId());
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>onDestroy," + Thread.currentThread().getId());
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (DEBUG)
                Log.i(TAG,
                        ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onVisibilityChanged, visible=" + visible
                                + " ," + Thread.currentThread().getId() + " ,"
                                + WallPaperEngine.this.hashCode());
            if (visible) {
                openFile();
            } else {
                mRenderThread.pausePainting();
            }
        }

        private void openFile() {
            if (" ".equals(mFileName))
                return;
            synchronized (mLockObject) {
                if (WrapFFmpeg.isFileOpen()) {
                    WrapFFmpeg.exit();
                }
                int ret = WrapFFmpeg.openFile(mFileName, mScreenWidth, mScreenHeight, mFixScreen);
                if (ret < 0) {
                    Toast.makeText(LiveWallpaperService.this, "打开文件错误!", Toast.LENGTH_LONG);
                    return;
                }
                if (mBitmap != null) {
                    mBitmap.recycle();
                    mBitmap = null;
                }
                mTargetWidth = WrapFFmpeg.getTargetWidth();
                mTargetHeight = WrapFFmpeg.getTargetHeight();
                mRect.set((mScreenWidth - mTargetWidth) / 2, (mScreenHeight - mTargetHeight) / 2,
                        (mScreenWidth + mTargetWidth) / 2, (mScreenHeight + mTargetHeight) / 2);
                mBitmap = Bitmap.createBitmap(mTargetWidth, mTargetHeight, Bitmap.Config.ARGB_8888);
                mTimeBase = 1000.0f / WrapFFmpeg.getTimeBase();

                mRenderThread.setDirty();
                mRenderThread.setDirty();
                mRenderThread.setDirty();
                mRenderThread.setDirty();
                mCurrentPts = 0;
                mRenderThread.resumePainting();
                if (DEBUG)
                    Log.i(TAG, ">>>>>>>>>>>>mTimeBase=" + mTimeBase + "," + mTargetWidth + ","
                            + mTargetHeight + " , mRect =" + mRect.toString());
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onSurfaceChanged,"
                        + Thread.currentThread().getId());
            super.onSurfaceChanged(holder, format, width, height);
            mScreenWidth = width;
            mScreenHeight = height;

        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>surfaceCreated,"
                        + Thread.currentThread().getId());
            mRenderThread = new RenderThread(holder);
            mRenderThread.start();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>surfaceDestroyed begin");
            if (mRenderThread != null && mRenderThread.isAlive()) {
                mRenderThread.exitRunning();
                boolean retry = true;
                while (retry) {
                    try {
                        mRenderThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>surfaceDestroyed end");
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // synchronized (mLockObject) {
                // mRenderThread.mAllowRendering =
                // !mRenderThread.mAllowRendering;
                // }
            }
            super.onTouchEvent(event);
        }

        private void setBgColor() {
            int color = Integer.parseInt(prefs.getString(PREFERENCE_BGCOLOR, "6"));
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>...color=" + color);
            switch (color) {
                case 1:
                    mBgColor = Color.BLACK;
                    break;
                case 2:
                    mBgColor = Color.BLUE;
                    break;
                case 3:
                    mBgColor = Color.CYAN;
                    break;
                case 4:
                    mBgColor = Color.DKGRAY;
                    break;
                case 5:
                    mBgColor = Color.GRAY;
                    break;
                case 6:
                    mBgColor = Color.GREEN;
                    break;
                case 7:
                    mBgColor = Color.LTGRAY;
                    break;
                case 8:
                    mBgColor = Color.MAGENTA;
                    break;
                case 9:
                    mBgColor = Color.RED;
                    break;
                case 10:
                    mBgColor = Color.TRANSPARENT;
                    break;
                case 11:
                    mBgColor = Color.WHITE;
                    break;
                case 12:
                    mBgColor = Color.YELLOW;
                    break;
                default:
                    mBgColor = Color.BLACK;
                    break;
            }
        }

        private void setFixScreen() {
            int fixscreen = Integer.parseInt(prefs.getString(PREFERENCE_FIXSCREEN, "2"));
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>>>>>...fixscreen=" + fixscreen);
            mFixScreen = fixscreen;
        }

        private class RenderThread extends Thread {
            private boolean mAllowRendering = false;

            private boolean mAllowRunning = true;

            private Rect rect = null;

            private SurfaceHolder mSurfaceHolder = null;

            private int ret = -1;

            public RenderThread(SurfaceHolder Holder) {
                // TODO Auto-generated constructor stub
                mSurfaceHolder = Holder;
            }

            public void exitRunning() {
                // TODO Auto-generated method stub
                this.mAllowRunning = false;
                // 运行和等待的控制必须获取Thread的锁
                synchronized (this) {
                    this.notify();
                }
            }

            public void resumePainting() {
                // TODO Auto-generated method stub
                this.mAllowRendering = true;
                // 运行和等待的控制必须获取Thread的锁
                synchronized (this) {
                    this.notify();
                }
            }

            public void pausePainting() {
                // TODO Auto-generated method stub
                this.mAllowRendering = false;
                // 运行和等待的控制必须获取Thread的锁
                synchronized (this) {
                    this.notify();
                }
            }

            public Rect setDirty() {
                rect = new Rect(mRect);
                if (DEBUG)
                    Log.i(TAG, "before >>>>>>>>>>>>>>>>" + rect.toString());
                Canvas canvas = mSurfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(mBgColor);
                } else {
                    Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>canvas == null");
                }
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                if (DEBUG)
                    Log.i(TAG, "after >>>>>>>>>>>>>>>>" + rect.toString());
                return rect;
            }

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (DEBUG)
                    Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>render thread start,"
                            + Thread.currentThread().getId());
                while (mAllowRunning) {
                    // 运行和等待的控制必须获取Thread的锁
                    synchronized (this) {
                        if (!mAllowRunning) {
                            break;
                        }
                        if (!mAllowRendering) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            continue;
                        }
                    }
                    // 处理资源必须获取资源锁
                    synchronized (mLockObject) {
                        if (DEBUG)
                            Log.i(TAG, "decode frame start,mAllowRendering=" + mAllowRendering
                                    + ",mAllowRunning=" + mAllowRunning + ",id="
                                    + Thread.currentThread().getId());
                        ret = WrapFFmpeg.drawFrame(mBitmap);

                        if (ret == 0) {
                            WrapFFmpeg.seekTo(0);
                        }
                        mCurrentPts = WrapFFmpeg.getPts();
                        if (DEBUG)
                            Log.i(TAG, "decode ret = " + ret + ", mCurrentPts=" + mCurrentPts);
                        if (DEBUG)
                            Log.i(TAG, "decode frame end,mAllowRendering=" + mAllowRendering
                                    + ",mAllowRunning=" + mAllowRunning + ",id="
                                    + Thread.currentThread().getId());
                    }
                    // 睡眠不要锁,避免死锁
                    if (ret == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    // 睡眠不要锁,避免死锁
                    if (mCurrentPts == 0) {
                        mCurrentTime = SystemClock.elapsedRealtime();
                    } else if (mCurrentPts > 0) {
                        long diff = SystemClock.elapsedRealtime() - mCurrentTime;
                        long now = (long) (mCurrentPts * mTimeBase);
                        if (diff < now) {
                            try {
                                Thread.sleep(now - diff);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    // 运行和等待的控制必须获取Thread的锁
                    synchronized (this) {
                        if (!mAllowRunning) {
                            break;
                        }
                        if (!mAllowRendering) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            continue;
                        }
                    }
                    // 处理资源必须获取资源锁
                    synchronized (mLockObject) {
                        if (DEBUG)
                            Log.i(TAG, "pianting surface start,mAllowRendering=" + mAllowRendering
                                    + ",mAllowRunning=" + mAllowRunning + ",id="
                                    + Thread.currentThread().getId());
                        Canvas canvas = mSurfaceHolder.lockCanvas(mRect);
                        if (canvas == null) {
                            // if (DEBUG)
                            // Log.i(TAG,
                            // ">>>>>>>>>>>>>>>>>>>>>>>>canvas == null");
                        } else {
                            canvas.save();
                            canvas.drawBitmap(mBitmap, null, mRect, null);
                            canvas.restore();
                            mSurfaceHolder.unlockCanvasAndPost(canvas);
                            // if (DEBUG)
                            // Log.i(TAG,
                            // ">>>>>>>>>>>>>>>>>>>>>>>>canvas !!== null, canvas density = "
                            // + canvas.getDensity() + " , mBitmap density = "
                            // + mBitmap.getDensity() + " , mRect ="
                            // + mRect.toString() + " , " + mScreenHeight +
                            // " , "
                            // + mScreenWidth + " , " + mTargetHeight + " , "
                            // + mTargetWidth);
                        }
                        if (DEBUG)
                            Log.i(TAG, "pianting surface end,mAllowRendering=" + mAllowRendering
                                    + ",mAllowRunning=" + mAllowRunning + ",id="
                                    + Thread.currentThread().getId());
                    }
                }
                if (DEBUG)
                    Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>render thread exit,id="
                            + Thread.currentThread().getId());
            }

        }

    }

}
