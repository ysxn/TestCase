package com.zhuyawen.android.videolivewallpaper;

import java.io.File;
import java.io.FileFilter;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import com.ccpcreations.android.VLW.SimpleDecoder;
import com.zhuyawen.android.utils.DebugUtil;

public class VideoLiveWallpaper extends WallpaperService {
    public static final String TAG = DebugUtil.VIDEO_LIVEWALLPAPER_TAG;
    public static final boolean DEBUG = DebugUtil.LOG_ENABLED_VIDEO_LIVEWALLPAPER;
    public static boolean readPreferences = true;
    
    @Override
    public Engine onCreateEngine() {
        // TODO Auto-generated method stub
        return null;//new VLWEngine();
    }
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        if (DEBUG) Log.i(TAG,">>>>>>>>>>>>>>>>>>>>>onLowMemory in VideoLiveWallpaper");
        stopForLowMemory();
        super.onLowMemory();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }
    
    private void stopForLowMemory() {
    
    }
    
//    class VLWEngine extends WallpaperService.Engine {
//        Object bufferLockingObject;
//        ByteBuffer byteBuffer;
//        long curDTS;
//        double debugCurrentFPS;
//        DecimalFormat debugDecimalFormat;
//        boolean debugOn;
//        Paint debugPaint;
//        Path debugPath;
//        boolean delayedRunnableMode;
//        boolean doNotSkip;
//        Bitmap drawingBitmap = null;
//        Matrix drawingMatrix;
//        String fileName;
//        int fileNo;
//        String fileToOpen;
//        float finalXOffset = 0.5F;
//        long fpsStart;
//        int frameCount;
//        int h = -1;
//        boolean keepCentered;
//        int lastFPS;
//        private final Runnable mDrawFrame;
//        final Handler mHandler;
//        int newBufferCreated;
//        float r = 1.0F;
//        boolean randomFile;
//        boolean recursiveDir;
//        int renderingMode;
//        Random rnd;
//        int srcH = 100;
//        int srcW = 100;
//        int srcX = 0;
//        int srcY = 0;
//        float swipingLag;
//        double videoFR;
//        int videoH;
//        double videoR;
//        int videoW;
//        boolean visible;
//        int w = -1;
//        boolean wantedDelayedRunnableMode;
//        WorkingThread wt;
//        float xOffset = 0.5F;
//
//        public VLWEngine()
//        {
//          super();
//          Object localObject = new Object();
//          this.bufferLockingObject = localObject;
//          long l = SystemClock.uptimeMillis();
//          this.fpsStart = l;
//          this.debugCurrentFPS = -1.0D;
//          this.frameCount = 0;
//          this.lastFPS = 0;
//          this.delayedRunnableMode = false;
//          this.curDTS = 65535L;
//          this.fileNo = -1;
//          this.fileToOpen = null;
//          this.newBufferCreated = 0;
//          this.wantedDelayedRunnableMode = false;
//          this.debugOn = false;
//          this.doNotSkip = false;
//          this.keepCentered = false;
//          this.swipingLag = 5.0F;
//          this.fileName = null;
//          this.recursiveDir = true;
//          this.randomFile = true;
//          this.renderingMode = 0;
//          Handler localHandler = new Handler();
//          this.mHandler = localHandler;
//          Runnable local1 = new Runnable()
//          {
//            public void run()
//            {
//              if ((VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode) && (!VideoLiveWallpaper.VLWEngine.this.wantedDelayedRunnableMode))
//              {
//                VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode = false;
//                if (VideoLiveWallpaper.VLWEngine.this.wt.paused)
//                  return;
//                VideoLiveWallpaper.VLWEngine.this.wt.pause(false);
//                return;
//              }
//              if (VideoLiveWallpaper.VLWEngine.this.debugOn)
//                  Log.v("Video Live Wallpaper", "Updating from delayed runnable...");
//              int j = VideoLiveWallpaper.VLWEngine.this.drawFrame();
//              VideoLiveWallpaper.VLWEngine.WorkingThread localWorkingThread1 = VideoLiveWallpaper.VLWEngine.this.wt;
//              double d1 = localWorkingThread1.curTime;
//              double d2 = j;
//              double d3 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//              double d4 = d2 * d3;
//              double d5 = d1 + d4;
//              localWorkingThread1.curTime = d5;
//              double d6 = VideoLiveWallpaper.VLWEngine.this.wt.curTime;
//              double d7 = SystemClock.uptimeMillis();
//              double d8 = d6 - d7;
//              double d9 = j * -5;
//              double d10 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//              double d11 = d9 * d10;
//              if (d8 < d11)
//              {
//                VideoLiveWallpaper.VLWEngine.WorkingThread localWorkingThread2 = VideoLiveWallpaper.VLWEngine.this.wt;
//                double d12 = SystemClock.uptimeMillis();
//                double d13 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                double d14 = d12 + d13;
//                localWorkingThread2.curTime = d14;
//                d8 = 0.0D;
//              }
//              while (true)
//              {
//                if (d8 < 0.0D)
//                  d8 = 0.0D;
//                if (!VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode)
//                  return;
//                if (VideoLiveWallpaper.VLWEngine.this.wt.paused)
//                  return;
//                Handler localHandler = VideoLiveWallpaper.VLWEngine.this.mHandler;
//                Runnable localRunnable = VideoLiveWallpaper.VLWEngine.this.mDrawFrame;
//                long l = (int)(0.5D * d8);
//                boolean bool = localHandler.postDelayed(localRunnable, l);
//                return;
//                double d25;
//                do
//                {
//                  j = VideoLiveWallpaper.VLWEngine.this.skipFrame();
//                  VideoLiveWallpaper.VLWEngine.WorkingThread localWorkingThread3 = VideoLiveWallpaper.VLWEngine.this.wt;
//                  double d15 = localWorkingThread3.curTime;
//                  double d16 = j;
//                  double d17 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                  double d18 = d16 * d17;
//                  double d19 = d15 + d18;
//                  localWorkingThread3.curTime = d19;
//                  double d20 = j;
//                  double d21 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                  double d22 = d20 * d21;
//                  d8 += d22;
//                  double d23 = -j;
//                  double d24 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                  d25 = d23 * d24;
//                }
//                while (d8 < d25);
//              }
//            }
//          };
//          this.mDrawFrame = local1;
//          Random localRandom = new Random();
//          this.rnd = localRandom;
//          Path localPath1 = new Path();
//          this.debugPath = localPath1;
//          Paint localPaint1 = new Paint();
//          this.debugPaint = localPaint1;
//          DecimalFormat localDecimalFormat = new DecimalFormat("#.###");
//          this.debugDecimalFormat = localDecimalFormat;
//          Path localPath2 = this.debugPath;
//          float f1 = this.h >> 1;
//          localPath2.setLastPoint(0.0F, f1);
//          Path localPath3 = this.debugPath;
//          float f2 = this.w;
//          float f3 = this.h >> 1;
//          localPath3.lineTo(f2, f3);
//          this.debugPaint.setColor(-1);
//          this.debugPaint.setTextSize(20.0F);
//          Paint localPaint2 = this.debugPaint;
//          Paint.Align localAlign = Paint.Align.CENTER;
//          localPaint2.setTextAlign(localAlign);
//          tryToOpen();
//          WorkingThread localWorkingThread = new WorkingThread();
//          this.wt = localWorkingThread;
//          this.wt.pause(true);
//          this.wt.start();
//          VideoLiveWallpaper.readPreferences = true;
//        }
//        
//
//        @Override
//        public void onCreate(SurfaceHolder surfaceHolder) {
//            // TODO Auto-generated method stub
//            super.onCreate(surfaceHolder);
//        }
//        
//        @Override
//        public void onDestroy() {
//            // TODO Auto-generated method stub
//            super.onDestroy();
//            this.wt.pause(true);
//            this.wt.stopRunning();
//            try
//            {
//              this.wt.join();
//              label26: {
//                  Handler localHandler = this.mHandler;
//              Runnable localRunnable = this.mDrawFrame;
//              localHandler.removeCallbacks(localRunnable);
//              }
//              return;
//            }
//            catch (InterruptedException localInterruptedException)
//            {
//              break label26;
//            }
//        }
//        
//        @Override
//        public void onVisibilityChanged(boolean visible) {
//            // TODO Auto-generated method stub
//            super.onVisibilityChanged(visible);
//            this.visible = visible;
//            WorkingThread localWorkingThread = this.wt;
//            if (visible);
//            for (boolean bool = false; ; bool = true)
//            {
//              localWorkingThread.pause(bool);
//              return;
//            }
//        }
//        
//        @Override
//        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep,
//                float yOffsetStep, int xPixelOffset, int yPixelOffset) {
//            // TODO Auto-generated method stub
//            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
//            if ((isPreview()) || (this.keepCentered));
//            for (this.xOffset = 0.5F; ; this.xOffset = xOffset)
//            {
//              if (this.xOffset < 0.0F)
//                this.xOffset = 0.0F;
//              if (this.xOffset <= 1.0F)
//                return;
//              this.xOffset = 1.0F;
//              return;
//            }
//        }
//        
//        @Override
//        public void onSurfaceCreated(SurfaceHolder holder) {
//            // TODO Auto-generated method stub
//            super.onSurfaceCreated(holder);
//            createNewBuffer();
//        }
//        
//        @Override
//        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            // TODO Auto-generated method stub
//            super.onSurfaceChanged(holder, format, width, height);
//            this.w = width;
//            this.h = height;
//            float f1 = this.w;
//            float f2 = this.h;
//            float f3 = f1 / f2;
//            this.r = f3;
//            createNewBuffer();
//        }
//        
//        @Override
//        public void onSurfaceDestroyed(SurfaceHolder holder) {
//            // TODO Auto-generated method stub
//            super.onSurfaceDestroyed(holder);
//            this.wt.pause(true);
//        }
//        
//        private void createNewBuffer()
//        {
//          while (true)
//          {
//            synchronized (this.bufferLockingObject)
//            {
//              if (this.videoW <= 0)
//                return;
//              Path localPath1 = new Path();
//              this.debugPath = localPath1;
//              Path localPath2 = this.debugPath;
//              float f1 = this.h >> 1;
//              localPath2.setLastPoint(0.0F, f1);
//              Path localPath3 = this.debugPath;
//              float f2 = this.w;
//              float f3 = this.h >> 1;
//              localPath3.lineTo(f2, f3);
//              if (this.w < 0)
//              {
//                if (this.drawingBitmap == null)
//                  continue;
//                int i = this.drawingBitmap.getWidth();
//                int j = this.videoW;
//                if (i == j)
//                  continue;
//                int k = this.drawingBitmap.getHeight();
//                int m = this.videoH;
//                if (k == m)
//                  continue;
//                if (this.drawingBitmap == null)
//                  continue;
//                this.drawingBitmap.recycle();
//                int n = this.videoW;
//                int i1 = this.videoH;
//                Bitmap.Config localConfig1 = Bitmap.Config.RGB_565;
//                Bitmap localBitmap1 = Bitmap.createBitmap(n, i1, localConfig1);
//                this.drawingBitmap = localBitmap1;
//                int i2 = this.videoW;
//                int i3 = this.videoH;
//                ByteBuffer localByteBuffer1 = ByteBuffer.allocateDirect(i2 * i3 * 2);
//                this.byteBuffer = localByteBuffer1;
//                int i4 = this.videoW;
//                this.srcW = i4;
//                int i5 = this.videoH;
//                this.srcH = i5;
//                this.srcY = 0;
//                this.srcX = 0;
//                Matrix localMatrix1 = new Matrix();
//                this.drawingMatrix = localMatrix1;
//                if (this.renderingMode != 1)
//                  break label716;
//                Matrix localMatrix2 = this.drawingMatrix;
//                float f4 = this.srcW;
//                float f5 = this.srcH;
//                RectF localRectF1 = new RectF(0.0F, 0.0F, f4, f5);
//                float f6 = this.w;
//                float f7 = this.h;
//                RectF localRectF2 = new RectF(0.0F, 0.0F, f6, f7);
//                Matrix.ScaleToFit localScaleToFit1 = Matrix.ScaleToFit.CENTER;
//                boolean bool1 = localMatrix2.setRectToRect(localRectF1, localRectF2, localScaleToFit1);
//                int i6 = this.newBufferCreated + 4;
//                this.newBufferCreated = i6;
//                return;
//              }
//            }
//            if (this.renderingMode == 0)
//            {
//              double d1 = this.r;
//              double d2 = this.videoR;
//              if (d1 < d2)
//              {
//                int i7 = this.videoH;
//                this.srcH = i7;
//                float f8 = this.videoH;
//                float f9 = this.r;
//                int i8 = (int)(f8 * f9);
//                this.srcW = i8;
//              }
//            }
//            while (true)
//            {
//              fixViewingWindow();
//              if (this.drawingBitmap != null)
//              {
//                int i9 = this.drawingBitmap.getWidth();
//                int i10 = this.srcW;
//                if (i9 != i10)
//                {
//                  int i11 = this.drawingBitmap.getHeight();
//                  int i12 = this.srcH;
//                  if (i11 == i12)
//                    break;
//                }
//              }
//              if (this.drawingBitmap != null)
//                this.drawingBitmap.recycle();
//              int i13 = this.srcW;
//              int i14 = this.srcH;
//              Bitmap.Config localConfig2 = Bitmap.Config.RGB_565;
//              Bitmap localBitmap2 = Bitmap.createBitmap(i13, i14, localConfig2);
//              this.drawingBitmap = localBitmap2;
//              int i15 = this.srcW;
//              int i16 = this.srcH;
//              ByteBuffer localByteBuffer2 = ByteBuffer.allocateDirect(i15 * i16 * 2);
//              this.byteBuffer = localByteBuffer2;
//              break;
//              double d3 = this.r;
//              double d4 = this.videoR;
//              if (d3 == d4)
//              {
//                int i17 = this.videoW;
//                this.srcW = i17;
//                int i18 = this.videoH;
//                this.srcH = i18;
//                continue;
//              }
//              int i19 = this.videoW;
//              this.srcW = i19;
//              float f10 = this.videoW;
//              float f11 = this.r;
//              int i20 = (int)(f10 / f11);
//              this.srcH = i20;
//              continue;
//              int i21 = this.videoW;
//              this.srcW = i21;
//              int i22 = this.videoH;
//              this.srcH = i22;
//              this.srcY = 0;
//              this.srcX = 0;
//            }
//            label716: Matrix localMatrix3 = this.drawingMatrix;
//            float f12 = this.srcW;
//            float f13 = this.srcH;
//            RectF localRectF3 = new RectF(0.0F, 0.0F, f12, f13);
//            float f14 = this.w;
//            float f15 = this.h;
//            RectF localRectF4 = new RectF(0.0F, 0.0F, f14, f15);
//            Matrix.ScaleToFit localScaleToFit2 = Matrix.ScaleToFit.FILL;
//            boolean bool2 = localMatrix3.setRectToRect(localRectF3, localRectF4, localScaleToFit2);
//          }
//        }
//        
//        private void fixViewingWindow()
//        {
//          if (this.renderingMode != 0)
//            return;
//          double d1 = this.r;
//          double d2 = this.videoR;
//          if (d1 < d2)
//          {
//            this.srcY = 0;
//            int i = this.videoW;
//            int j = this.srcW;
//            float f1 = i - j;
//            float f2 = this.finalXOffset;
//            int k = (int)(f1 * f2);
//            this.srcX = k;
//            return;
//          }
//          double d3 = this.r;
//          double d4 = this.videoR;
//          if (d3 == d4)
//          {
//            this.srcY = 0;
//            this.srcX = 0;
//            return;
//          }
//          this.srcX = 0;
//          int m = this.videoH;
//          int n = this.srcH;
//          int i1 = (m - n) / 2;
//          this.srcY = i1;
//        }
//
//        public int countFiles(int paramInt, File paramFile)
//        {
//          2 local2 = new FileFilter()
//          {
//            public boolean accept(File paramFile)
//            {
//              if (paramFile.isDirectory());
//              for (int i = 1; ; i = 1)
//              {
//                return i;
//                if (FilePicker.filter != null)
//                  break;
//              }
//              int j = 0;
//              while (true)
//              {
//                int k = FilePicker.filter.length;
//                if (j >= k)
//                {
//                  i = 0;
//                  break;
//                }
//                String str1 = paramFile.getName().toLowerCase();
//                String str2 = FilePicker.filter[j];
//                if (str1.endsWith(str2))
//                {
//                  i = 1;
//                  break;
//                }
//                j += 1;
//              }
//            }
//          };
//          File[] arrayOfFile = paramFile.listFiles(local2);
//          if (arrayOfFile == null);
//          int j;
//          for (int i = paramInt; ; i = paramInt)
//          {
//            return i;
//            j = 0;
//            int k = arrayOfFile.length;
//            if (j < k)
//              break;
//          }
//          if (arrayOfFile[j].isFile())
//            paramInt += 1;
//          while (true)
//          {
//            j += 1;
//            break;
//            if ((!this.recursiveDir) || (!arrayOfFile[j].isDirectory()))
//              continue;
//            File localFile = arrayOfFile[j];
//            paramInt = countFiles(paramInt, localFile);
//          }
//        }
//
//        public int drawFrame()
//        {
//          float f1 = this.xOffset;
//          float f2 = this.finalXOffset;
//          float f3 = f1 - f2;
//          if ((f3 > 0.001D) || (f3 < -0.001D))
//          {
//            float f4 = this.finalXOffset;
//            float f5 = this.swipingLag;
//            float f6 = f3 / f5;
//            float f7 = f4 + f6;
//            this.finalXOffset = f7;
//            fixViewingWindow();
//            if (this.debugOn)
//            {
//              StringBuilder localStringBuilder1 = new StringBuilder("New xOffset: ");
//              float f8 = this.finalXOffset;
//              StringBuilder localStringBuilder2 = localStringBuilder1.append(f8).append("->");
//              float f9 = this.xOffset;
//              String str1 = f9;
//              int i = Log.v("Video Live Wallpaper", str1);
//            }
//          }
//          while (true)
//          {
//            int j;
//            label149: SurfaceHolder localSurfaceHolder;
//            Canvas localCanvas;
//            if (this.videoFR == 0.0D)
//            {
//              j = 0;
//              if (this.debugOn)
//              {
//                int k = this.frameCount + 1;
//                this.frameCount = k;
//                long l1 = SystemClock.uptimeMillis();
//                long l2 = this.fpsStart;
//                if (l1 - l2 >= 1000L)
//                {
//                  long l3 = this.fpsStart + 1000L;
//                  this.fpsStart = l3;
//                  StringBuilder localStringBuilder3 = new StringBuilder("FPS: ");
//                  int m = this.frameCount;
//                  StringBuilder localStringBuilder4 = localStringBuilder3.append(m).append(", required FPS: ");
//                  double d1 = this.videoFR;
//                  double d2 = 1000.0D / d1;
//                  String str2 = d2;
//                  int n = Log.v("Video Live Wallpaper", str2);
//                  int i1 = this.frameCount;
//                  this.lastFPS = i1;
//                  this.frameCount = 0;
//                }
//              }
//              localSurfaceHolder = getSurfaceHolder();
//              localCanvas = null;
//            }
//            try
//            {
//              if (SimpleDecoder.opened)
//              {
//                if (SimpleDecoder._getNextFrame() != 0)
//                  break label526;
//                if (this.debugOn)
//                  int i2 = Log.v("Video Live Wallpaper", "Reopenning a video file...");
//                if (SimpleDecoder.opened)
//                {
//                  SimpleDecoder._close();
//                  SimpleDecoder.opened = false;
//                }
//                tryToOpen();
//                int i3 = this.newBufferCreated + 4;
//                this.newBufferCreated = i3;
//              }
//              while (true)
//              {
//                if (localCanvas != null)
//                  localSurfaceHolder.unlockCanvasAndPost(localCanvas);
//                Handler localHandler = this.mHandler;
//                Runnable localRunnable = this.mDrawFrame;
//                localHandler.removeCallbacks(localRunnable);
//                return j;
//                float f10 = this.finalXOffset;
//                float f11 = this.xOffset;
//                if (f10 == f11)
//                  break;
//                float f12 = this.xOffset;
//                this.finalXOffset = f12;
//                fixViewingWindow();
//                if (!this.debugOn)
//                  break;
//                StringBuilder localStringBuilder5 = new StringBuilder("New xOffset: ");
//                float f13 = this.finalXOffset;
//                StringBuilder localStringBuilder6 = localStringBuilder5.append(f13).append("->");
//                float f14 = this.xOffset;
//                String str3 = f14;
//                int i4 = Log.v("Video Live Wallpaper", str3);
//                break;
//                double d3 = this.videoFR;
//                j = (int)(40.0D / d3);
//                break label149;
//                label526: long l4 = SimpleDecoder._getDTS();
//                int i5 = SimpleDecoder._getDuration();
//                if (this.curDTS < 0L)
//                  this.curDTS = l4;
//                long l5 = this.curDTS;
//                j = (int)(l4 - l5) + i5;
//                long l6 = i5 + l4;
//                this.curDTS = l6;
//                if (this.debugCurrentFPS < 0.0D)
//                {
//                  double d4 = j;
//                  this.debugCurrentFPS = d4;
//                }
//                double d5 = this.debugCurrentFPS * 2.0D / 3.0D;
//                double d6 = j * 1.0D / 3.0D;
//                double d7 = d5 + d6;
//                this.debugCurrentFPS = d7;
//                if (this.debugOn)
//                {
//                  String str4 = "DTS: " + l4 + ", duration: " + i5 + ", required slice count: " + j;
//                  int i6 = Log.v("Video Live Wallpaper", str4);
//                }
//                int i7 = SimpleDecoder._convertFrameData();
//                synchronized (this.bufferLockingObject)
//                {
//                  localCanvas = localSurfaceHolder.lockCanvas();
//                  if (localCanvas != null)
//                  {
//                    ByteBuffer localByteBuffer1 = this.byteBuffer;
//                    int i8 = this.srcX;
//                    int i9 = this.srcY;
//                    int i10 = this.srcX;
//                    int i11 = this.srcW;
//                    int i12 = i10 + i11;
//                    int i13 = this.srcY;
//                    int i14 = this.srcH;
//                    int i15 = i13 + i14;
//                    SimpleDecoder._getDirectFrameData(localByteBuffer1, i8, i9, i12, i15);
//                    Buffer localBuffer = this.byteBuffer.position(0);
//                    Bitmap localBitmap1 = this.drawingBitmap;
//                    ByteBuffer localByteBuffer2 = this.byteBuffer;
//                    localBitmap1.copyPixelsFromBuffer(localByteBuffer2);
//                    if (this.newBufferCreated > 0)
//                    {
//                      localCanvas.drawColor(-16777216);
//                      int i16 = this.newBufferCreated - 1;
//                      this.newBufferCreated = i16;
//                    }
//                    Bitmap localBitmap2 = this.drawingBitmap;
//                    Matrix localMatrix = this.drawingMatrix;
//                    localCanvas.drawBitmap(localBitmap2, localMatrix, null);
//                    if (this.debugOn)
//                    {
//                      Path localPath1 = this.debugPath;
//                      Paint localPaint1 = this.debugPaint;
//                      localCanvas.drawTextOnPath("Debug mode active", localPath1, 0.0F, 0.0F, localPaint1);
//                      String str5 = String.valueOf(this.lastFPS);
//                      StringBuilder localStringBuilder7 = new StringBuilder(str5).append("/");
//                      DecimalFormat localDecimalFormat = this.debugDecimalFormat;
//                      double d8 = this.debugCurrentFPS;
//                      double d9 = 1000.0D / d8;
//                      double d10 = this.videoFR;
//                      double d11 = d9 / d10;
//                      String str6 = localDecimalFormat.format(d11);
//                      String str7 = str6 + " FPS";
//                      Path localPath2 = this.debugPath;
//                      Paint localPaint2 = this.debugPaint;
//                      localCanvas.drawTextOnPath(str7, localPath2, 0.0F, 20.0F, localPaint2);
//                    }
//                  }
//                }
//              }
//            }
//            finally
//            {
//              if (localCanvas != null)
//                localSurfaceHolder.unlockCanvasAndPost(localCanvas);
//            }
//          }
//          throw localObject3;
//        }
//
//        public void drawMessage(String paramString)
//        {
//          SurfaceHolder localSurfaceHolder = getSurfaceHolder();
//          Canvas localCanvas = localSurfaceHolder.lockCanvas();
//          if (localCanvas != null);
//          try
//          {
//            Path localPath1 = new Path();
//            this.debugPath = localPath1;
//            Path localPath2 = this.debugPath;
//            float f1 = this.h >> 1;
//            localPath2.setLastPoint(0.0F, f1);
//            Path localPath3 = this.debugPath;
//            float f2 = this.w;
//            float f3 = this.h >> 1;
//            localPath3.lineTo(f2, f3);
//            localCanvas.drawColor(-16777216);
//            String[] arrayOfString = paramString.split("\n");
//            int i = 0;
//            while (true)
//            {
//              int j = arrayOfString.length;
//              if (i >= j)
//              {
//                if (this.debugOn)
//                  int k = Log.v("Video Live Wallpaper", paramString);
//                if (localCanvas != null)
//                  localSurfaceHolder.unlockCanvasAndPost(localCanvas);
//                Handler localHandler = this.mHandler;
//                Runnable localRunnable = this.mDrawFrame;
//                localHandler.removeCallbacks(localRunnable);
//                return;
//              }
//              String str = arrayOfString[i];
//              Path localPath4 = this.debugPath;
//              int m = arrayOfString.length / 2;
//              float f4 = (i - m) * 20;
//              Paint localPaint = this.debugPaint;
//              localCanvas.drawTextOnPath(str, localPath4, 0.0F, f4, localPaint);
//              i += 1;
//            }
//          }
//          finally
//          {
//            if (localCanvas != null)
//              localSurfaceHolder.unlockCanvasAndPost(localCanvas);
//          }
//          throw localObject;
//        }
//
//        public Object nthFile(int paramInt, File paramFile)
//        {
//          3 local3 = new FileFilter()
//          {
//            public boolean accept(File paramFile)
//            {
//              if (!paramFile.isFile());
//              for (int i = 0; ; i = 1)
//              {
//                return i;
//                if (FilePicker.filter != null)
//                  break;
//              }
//              int j = 0;
//              while (true)
//              {
//                int k = FilePicker.filter.length;
//                if (j >= k)
//                {
//                  i = 0;
//                  break;
//                }
//                String str1 = paramFile.getName().toLowerCase();
//                String str2 = FilePicker.filter[j];
//                if (str1.endsWith(str2))
//                {
//                  i = 1;
//                  break;
//                }
//                j += 1;
//              }
//            }
//          };
//          File[] arrayOfFile = paramFile.listFiles(local3);
//          Object localObject1;
//          if (arrayOfFile == null)
//            localObject1 = new Integer(paramInt);
//          while (true)
//          {
//            return localObject1;
//            4 local4 = new Comparator()
//            {
//              public int compare(File paramFile1, File paramFile2)
//              {
//                String str1 = paramFile1.getName();
//                String str2 = paramFile2.getName();
//                return str1.compareTo(str2);
//              }
//            };
//            Arrays.sort(arrayOfFile, local4);
//            int i = arrayOfFile.length;
//            if (paramInt < i)
//            {
//              if (this.debugOn)
//              {
//                StringBuilder localStringBuilder = new StringBuilder("Next video: ");
//                String str1 = arrayOfFile[paramInt].getAbsolutePath();
//                String str2 = str1;
//                int j = Log.v("Video Live Wallpaper", str2);
//              }
//              localObject1 = arrayOfFile[paramInt].getAbsolutePath();
//              continue;
//            }
//            int k = arrayOfFile.length;
//            paramInt -= k;
//            if (this.recursiveDir)
//              break;
//            localObject1 = new Integer(paramInt);
//          }
//          5 local5 = new FileFilter()
//          {
//            public boolean accept(File paramFile)
//            {
//              return paramFile.isDirectory();
//            }
//          };
//          arrayOfFile = paramFile.listFiles(local5);
//          6 local6 = new Comparator()
//          {
//            public int compare(File paramFile1, File paramFile2)
//            {
//              String str1 = paramFile1.getName();
//              String str2 = paramFile2.getName();
//              return str1.compareTo(str2);
//            }
//          };
//          Arrays.sort(arrayOfFile, local6);
//          int m = 0;
//          while (true)
//          {
//            int n = arrayOfFile.length;
//            if (m >= n)
//            {
//              localObject1 = new Integer(paramInt);
//              break;
//            }
//            File localFile = arrayOfFile[m];
//            Object localObject2 = nthFile(paramInt, localFile);
//            if ((localObject2 instanceof String))
//            {
//              localObject1 = localObject2;
//              break;
//            }
//            paramInt = ((Integer)localObject2).intValue();
//            m += 1;
//          }
//        }
//        
//        public int skipFrame()
//        {
//          int k;
//          if (!this.doNotSkip)
//          {
//            if (SimpleDecoder._getNextFrame() == 0)
//            {
//              if (SimpleDecoder.opened)
//              {
//                SimpleDecoder._close();
//                SimpleDecoder.opened = false;
//              }
//              tryToOpen();
//              int i = SimpleDecoder._getNextFrame();
//            }
//            long l1 = SimpleDecoder._getDTS();
//            int j = SimpleDecoder._getDuration();
//            if (this.curDTS < 0L)
//              this.curDTS = l1;
//            long l2 = this.curDTS;
//            k = (int)(l1 - l2) + j;
//            long l3 = j + l1;
//            this.curDTS = l3;
//          }
//          for (int m = k; ; m = 1)
//            return m;
//        }
//
//        public void tryToOpen()
//        {
//          int i = 0;
//          while (true)
//          {
//            if (!SimpleDecoder.opened);
//            int j;
//            while (true)
//            {
//              if (SimpleDecoder.opened)
//              {
//                SimpleDecoder._close();
//                SimpleDecoder.opened = false;
//              }
//              SimpleDecoder.forceShutdown = false;
//              if (this.fileName != null)
//                break label79;
//              return;
//              j = i + 1;
//              if (i < 50)
//                break;
//              int k = j;
//            }
//            if (j == 2)
//              SimpleDecoder.forceShutdown = true;
//            long l = 100L;
//            try
//            {
//              Thread.sleep(l);
//              i = j;
//            }
//            catch (InterruptedException localInterruptedException)
//            {
//              i = j;
//            }
//          }
//          label79: String str1 = this.fileName;
//          File localFile = new File(str1);
//          if ((localFile.isFile()) && (localFile.exists()))
//          {
//            String str2 = this.fileName;
//            this.fileToOpen = str2;
//            if (SimpleDecoder._open(this.fileToOpen) != 0)
//              return;
//            SimpleDecoder.opened = true;
//            int m = SimpleDecoder._getVideoWidth();
//            this.videoW = m;
//            int n = SimpleDecoder._getVideoHeight();
//            this.videoH = n;
//            double d1 = SimpleDecoder._getVideoFrameRate();
//            this.videoFR = d1;
//            this.curDTS = 65535L;
//            float f1 = this.videoW;
//            float f2 = this.videoH;
//            double d2 = f1 / f2;
//            this.videoR = d2;
//            createNewBuffer();
//            return;
//          }
//          if (!localFile.isDirectory())
//            return;
//          int i1 = -1;
//          if (this.fileNo < 0)
//          {
//            if (!this.randomFile)
//              break label637;
//            i1 = countFiles(0, localFile);
//            if (this.debugOn)
//            {
//              String str3 = "Video File count: " + i1;
//              int i2 = Log.v("Video Live Wallpaper", str3);
//            }
//            if (i1 == 0)
//              return;
//            int i3 = this.rnd.nextInt(i1);
//            this.fileNo = i3;
//            label308: if (this.debugOn)
//            {
//              StringBuilder localStringBuilder1 = new StringBuilder("New fileNo");
//              int i4 = this.fileNo;
//              String str4 = i4;
//              int i5 = Log.v("***********1", str4);
//            }
//          }
//          label396: int i14;
//          label653: label730: label732: for (int i6 = 10; ; i6 = i14)
//          {
//            int i7 = this.fileNo;
//            Object localObject = nthFile(i7, localFile);
//            if ((localObject instanceof String))
//            {
//              String str5 = (String)localObject;
//              this.fileToOpen = str5;
//              if (this.fileToOpen == null)
//              {
//                if (this.debugOn)
//                  int i8 = Log.v("***********1", "File wasn't opened");
//                if (i1 < 0)
//                {
//                  i1 = countFiles(0, localFile);
//                  if (this.debugOn)
//                  {
//                    String str6 = "Video File count: " + i1;
//                    int i9 = Log.v("Video Live Wallpaper", str6);
//                  }
//                }
//                if (i1 == 0)
//                  return;
//              }
//              if (!this.randomFile)
//                break label653;
//              if (i1 < 0)
//              {
//                i1 = countFiles(0, localFile);
//                if (this.debugOn)
//                {
//                  String str7 = "Video File count: " + i1;
//                  int i10 = Log.v("Video Live Wallpaper", str7);
//                }
//              }
//              if (i1 == 0)
//                return;
//              int i11 = this.rnd.nextInt(i1);
//              this.fileNo = i11;
//              if (this.debugOn)
//              {
//                StringBuilder localStringBuilder2 = new StringBuilder("New fileNo");
//                int i12 = this.fileNo;
//                String str8 = i12;
//                int i13 = Log.v("***********2", str8);
//              }
//            }
//            while (true)
//            {
//              if (this.fileToOpen == null)
//              {
//                i14 = i6 + -1;
//                if (i6 > 0)
//                  break label732;
//                int i15 = i14;
//              }
//              if (this.fileToOpen != null)
//                break;
//              return;
//              label637: this.fileNo = 0;
//              break label308;
//              this.fileToOpen = null;
//              break label396;
//              if (this.fileToOpen == null);
//              int i18;
//              for (this.fileNo = 0; ; this.fileNo = i18)
//              {
//                if (!this.debugOn)
//                  break label730;
//                StringBuilder localStringBuilder3 = new StringBuilder("New fileNo");
//                int i16 = this.fileNo;
//                String str9 = i16;
//                int i17 = Log.v("***********3", str9);
//                break;
//                i18 = this.fileNo + 1;
//              }
//            }
//          }
//        }
//
//        class EndOfVideoException extends Exception
//        {
//          private static final long serialVersionUID = 1L;
//
//          EndOfVideoException()
//          {
//          }
//        }
//
//        class WorkingThread extends Thread
//        {
//          public double curTime;
//          boolean finished;
//          Object lockingObject;
//          public boolean paused;
//
//          WorkingThread()
//          {
//            Object localObject = new Object();
//            this.lockingObject = localObject;
//            this.paused = true;
//            this.finished = false;
//          }
//
//          public void pause(boolean paramBoolean)
//          {
//            if (VideoLiveWallpaper.VLWEngine.this.debugOn)
//            {
//              StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
//              StringBuilder localStringBuilder1 = new StringBuilder("Pause set to ").append(paramBoolean).append(" by ");
//              String str1 = arrayOfStackTraceElement[3].getMethodName();
//              StringBuilder localStringBuilder2 = localStringBuilder1.append(str1).append("() [");
//              String str2 = arrayOfStackTraceElement[3].getFileName();
//              StringBuilder localStringBuilder3 = localStringBuilder2.append(str2).append(":");
//              int i = arrayOfStackTraceElement[3].getLineNumber();
//              String str3 = i + "]";
//              int j = Log.v("Video Live Wallpaper", str3);
//            }
//            synchronized (this.lockingObject)
//            {
//              this.paused = paramBoolean;
//              if (paramBoolean)
//                break label284;
//              double d = SystemClock.uptimeMillis();
//              this.curTime = d;
//              VideoLiveWallpaper.VLWEngine localVLWEngine1 = VideoLiveWallpaper.VLWEngine.this;
//              long l = SystemClock.uptimeMillis();
//              localVLWEngine1.fpsStart = l;
//              VideoLiveWallpaper.VLWEngine.this.curDTS = 65535L;
//              if (!isAlive())
//              {
//                VideoLiveWallpaper.readPreferences = true;
//                VideoLiveWallpaper.VLWEngine localVLWEngine2 = VideoLiveWallpaper.VLWEngine.this;
//                VideoLiveWallpaper.VLWEngine localVLWEngine3 = VideoLiveWallpaper.VLWEngine.this;
//                WorkingThread localWorkingThread = new WorkingThread(localVLWEngine3);
//                localVLWEngine2.wt = localWorkingThread;
//                VideoLiveWallpaper.VLWEngine.this.wt.pause(true);
//                VideoLiveWallpaper.VLWEngine.this.wt.start();
//                VideoLiveWallpaper.VLWEngine.this.wt.pause(false);
//                return;
//              }
//            }
//            if (VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode)
//            {
//              VideoLiveWallpaper.VLWEngine.this.mDrawFrame.run();
//              return;
//            }
//            interrupt();
//            return;
//            label284: if (VideoLiveWallpaper.VLWEngine.this.debugOn)
//              int k = Log.v("Video Live Wallpaper", "Going to sleep...");
//            if (!VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode)
//              return;
//            Handler localHandler = VideoLiveWallpaper.VLWEngine.this.mHandler;
//            Runnable localRunnable = VideoLiveWallpaper.VLWEngine.this.mDrawFrame;
//            localHandler.removeCallbacks(localRunnable);
//          }
//
//          public void run()
//          {
//            while (true)
//            {
//              boolean bool1;
//              boolean bool5;
//              String str1;
//              int i;
//              String str2;
//              boolean bool8;
//              long l1;
//              synchronized (this.lockingObject)
//              {
//                if ((!this.finished) && (!SimpleDecoder.forceShutdown))
//                  continue;
//                if (!SimpleDecoder.opened)
//                  return;
//                SimpleDecoder._close();
//                SimpleDecoder.opened = false;
//                return;
//                bool1 = this.paused;
//                if (VideoLiveWallpaper.readPreferences)
//                {
//                  VideoLiveWallpaper.readPreferences = false;
//                  SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(VideoLiveWallpaper.this.getApplicationContext());
//                  VideoLiveWallpaper.VLWEngine localVLWEngine1 = VideoLiveWallpaper.VLWEngine.this;
//                  boolean bool2 = localSharedPreferences.getString("playing_mode", "dr").equals("dr");
//                  localVLWEngine1.wantedDelayedRunnableMode = bool2;
//                  VideoLiveWallpaper.VLWEngine localVLWEngine2 = VideoLiveWallpaper.VLWEngine.this;
//                  boolean bool3 = localSharedPreferences.getBoolean("debug_mode", false);
//                  localVLWEngine2.debugOn = bool3;
//                  VideoLiveWallpaper.VLWEngine localVLWEngine3 = VideoLiveWallpaper.VLWEngine.this;
//                  boolean bool4 = localSharedPreferences.getBoolean("do_not_skip", false);
//                  localVLWEngine3.doNotSkip = bool4;
//                  VideoLiveWallpaper.VLWEngine localVLWEngine4 = VideoLiveWallpaper.VLWEngine.this;
//                  if (!localSharedPreferences.getBoolean("shift_video", true))
//                    break label697;
//                  bool5 = false;
//                  localVLWEngine4.keepCentered = bool5;
//                  VideoLiveWallpaper.VLWEngine localVLWEngine5 = VideoLiveWallpaper.VLWEngine.this;
//                  float f1 = Integer.parseInt(localSharedPreferences.getString("swiping_lag", "v4").substring(1));
//                  localVLWEngine5.swipingLag = f1;
//                  VideoLiveWallpaper.VLWEngine localVLWEngine6 = VideoLiveWallpaper.VLWEngine.this;
//                  boolean bool6 = localSharedPreferences.getBoolean("recursive_dir", true);
//                  localVLWEngine6.recursiveDir = bool6;
//                  VideoLiveWallpaper.VLWEngine localVLWEngine7 = VideoLiveWallpaper.VLWEngine.this;
//                  boolean bool7 = localSharedPreferences.getBoolean("random_file_mode", true);
//                  localVLWEngine7.randomFile = bool7;
//                  str1 = localSharedPreferences.getString("zooming_mode", "cl");
//                  if (!str1.equals("lb"))
//                    break label703;
//                  i = 1;
//                  str2 = localSharedPreferences.getString("video", null);
//                  int j = VideoLiveWallpaper.VLWEngine.this.renderingMode;
//                  if (i == j)
//                    continue;
//                  if (str2 != null)
//                  {
//                    String str3 = VideoLiveWallpaper.VLWEngine.this.fileName;
//                    if (!str2.equals(str3))
//                    {
//                      VideoLiveWallpaper.VLWEngine.this.renderingMode = i;
//                      VideoLiveWallpaper.VLWEngine.this.fileName = str2;
//                      bool8 = this.paused;
//                      if (bool8)
//                        continue;
//                      pause(true);
//                      if (SimpleDecoder.opened)
//                      {
//                        SimpleDecoder.opened = false;
//                        VideoLiveWallpaper.VLWEngine.this.drawMessage("Loading a new video...");
//                        l1 = 500L;
//                      }
//                    }
//                  }
//                }
//              }
//              try
//              {
//                Thread.sleep(l1);
//                label396: SimpleDecoder._close();
//                VideoLiveWallpaper.VLWEngine.this.tryToOpen();
//                if (!bool8)
//                  pause(false);
//                if (VideoLiveWallpaper.VLWEngine.this.debugOn)
//                {
//                  StringBuilder localStringBuilder1 = new StringBuilder("Read settings: ");
//                  boolean bool9 = VideoLiveWallpaper.VLWEngine.this.wantedDelayedRunnableMode;
//                  StringBuilder localStringBuilder2 = localStringBuilder1.append(bool9).append(" ");
//                  boolean bool10 = VideoLiveWallpaper.VLWEngine.this.debugOn;
//                  StringBuilder localStringBuilder3 = localStringBuilder2.append(bool10).append(" ");
//                  boolean bool11 = VideoLiveWallpaper.VLWEngine.this.doNotSkip;
//                  StringBuilder localStringBuilder4 = localStringBuilder3.append(bool11).append(" ");
//                  boolean bool12 = VideoLiveWallpaper.VLWEngine.this.keepCentered;
//                  StringBuilder localStringBuilder5 = localStringBuilder4.append(bool12).append(" ");
//                  float f2 = VideoLiveWallpaper.VLWEngine.this.swipingLag;
//                  StringBuilder localStringBuilder6 = localStringBuilder5.append(f2).append(" ");
//                  boolean bool13 = VideoLiveWallpaper.VLWEngine.this.recursiveDir;
//                  StringBuilder localStringBuilder7 = localStringBuilder6.append(bool13).append(" ");
//                  boolean bool14 = VideoLiveWallpaper.VLWEngine.this.randomFile;
//                  StringBuilder localStringBuilder8 = localStringBuilder7.append(bool14).append(" ");
//                  int k = VideoLiveWallpaper.VLWEngine.this.renderingMode;
//                  String str4 = k + " " + str2;
//                  int m = Log.v("Video Live Wallpaper", str4);
//                }
//                if (!SimpleDecoder.opened)
//                {
//                  VideoLiveWallpaper.VLWEngine.this.tryToOpen();
//                  if (SimpleDecoder.opened)
//                    continue;
//                  try
//                  {
//                    VideoLiveWallpaper.VLWEngine.this.drawMessage("No video found or available.\nYou might want to provide some settings\nor unmount the SD card /\nturn off USB storage.");
//                    Thread.sleep(1000L);
//                  }
//                  catch (InterruptedException localInterruptedException1)
//                  {
//                  }
//                  continue;
//                  localObject2 = finally;
//                  monitorexit;
//                  throw localObject2;
//                  label697: bool5 = true;
//                  continue;
//                  label703: if (str1.equals("st"))
//                  {
//                    i = 2;
//                    continue;
//                  }
//                  i = 0;
//                  continue;
//                }
//                if ((bool1) || (VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode))
//                {
//                  try
//                  {
//                    if (VideoLiveWallpaper.VLWEngine.this.debugOn)
//                      int n = Log.v("Video Live Wallpaper", "Sleeping...");
//                    Thread.sleep(1000L);
//                  }
//                  catch (InterruptedException localInterruptedException2)
//                  {
//                  }
//                  continue;
//                }
//                if ((VideoLiveWallpaper.VLWEngine.this.wantedDelayedRunnableMode) && (!VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode))
//                {
//                  VideoLiveWallpaper.VLWEngine.this.delayedRunnableMode = true;
//                  if (VideoLiveWallpaper.VLWEngine.this.wt.paused)
//                    continue;
//                  VideoLiveWallpaper.VLWEngine.this.wt.pause(false);
//                  continue;
//                }
//                if (VideoLiveWallpaper.VLWEngine.this.debugOn)
//                  int i1 = Log.v("Video Live Wallpaper", "Updating from thread...");
//                int i2 = VideoLiveWallpaper.VLWEngine.this.drawFrame();
//                double d1 = this.curTime;
//                double d2 = i2;
//                double d3 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                double d4 = d2 * d3;
//                double d5 = d1 + d4;
//                this.curTime = d5;
//                double d6 = this.curTime;
//                double d7 = SystemClock.uptimeMillis();
//                double d8 = d6 - d7;
//                double d9 = i2 * -5;
//                double d10 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                double d11 = d9 * d10;
//                if (d8 < d11)
//                {
//                  double d12 = SystemClock.uptimeMillis();
//                  double d13 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                  double d14 = d12 + d13;
//                  this.curTime = d14;
//                  d8 = 0.0D;
//                }
//                while (true)
//                {
//                  if (d8 > 0.0D)
//                    break label1107;
//                  Thread.yield();
//                  break;
//                  double d25;
//                  do
//                  {
//                    i2 = VideoLiveWallpaper.VLWEngine.this.skipFrame();
//                    double d15 = this.curTime;
//                    double d16 = i2;
//                    double d17 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                    double d18 = d16 * d17;
//                    double d19 = d15 + d18;
//                    this.curTime = d19;
//                    double d20 = i2;
//                    double d21 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                    double d22 = d20 * d21;
//                    d8 += d22;
//                    double d23 = -i2;
//                    double d24 = VideoLiveWallpaper.VLWEngine.this.videoFR;
//                    d25 = d23 * d24;
//                  }
//                  while (d8 < d25);
//                }
//                label1107: long l2 = (int)(0.5D * d8);
//                try
//                {
//                  Thread.sleep(l2);
//                }
//                catch (InterruptedException localInterruptedException3)
//                {
//                }
//              }
//              catch (InterruptedException localInterruptedException4)
//              {
//                break label396;
//              }
//            }
//          }
//
//          public void stopRunning()
//          {
//            synchronized (this.lockingObject)
//            {
//              this.finished = true;
//              return;
//            }
//          }
//        }
//    }
}