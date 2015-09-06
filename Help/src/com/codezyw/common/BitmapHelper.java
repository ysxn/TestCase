
package com.codezyw.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;

public class BitmapHelper {

    public static final int SCALED_SCREENSHOT_MAX_HEIGHT_WIDTH = 720;

    public static final int SCALED_SCREENSHOT_MAX_HEIGHT_HEIGHT = 1280;

    public static Bitmap getShot(Activity activity) {
        final Window activityWindow = activity.getWindow();
        final View decorView = activityWindow != null ? activityWindow.getDecorView() : null;
        final View rootView = decorView != null ? decorView.getRootView() : null;
        if (rootView != null) {
            boolean flag = rootView.isDrawingCacheEnabled();
            rootView.setDrawingCacheEnabled(true);
            final Bitmap drawingCache = rootView.getDrawingCache();
            // Null check to avoid NPE discovered from monkey crash:
            if (drawingCache != null) {
                try {
                    final Bitmap originalBitmap = drawingCache.copy(Bitmap.Config.ARGB_8888, false);
                    return originalBitmap;
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
            rootView.setDrawingCacheEnabled(flag);
        }
        return null;
    }

    public static Bitmap getReducedSizeBitmap(Activity activity) {
        final Window activityWindow = activity.getWindow();
        final View decorView = activityWindow != null ? activityWindow.getDecorView() : null;
        final View rootView = decorView != null ? decorView.getRootView() : null;
        if (rootView != null) {
            boolean flag = rootView.isDrawingCacheEnabled();
            rootView.setDrawingCacheEnabled(true);
            final Bitmap drawingCache = rootView.getDrawingCache();
            Bitmap result = null;
            // Null check to avoid NPE discovered from monkey crash:
            if (drawingCache != null) {
                try {
                    final Bitmap originalBitmap = drawingCache.copy(Bitmap.Config.ARGB_8888, false);
                    double originalHeight = originalBitmap.getHeight();
                    double originalWidth = originalBitmap.getWidth();
                    int newHeight = SCALED_SCREENSHOT_MAX_HEIGHT_WIDTH;
                    int newWidth = SCALED_SCREENSHOT_MAX_HEIGHT_WIDTH;
                    double scaleX, scaleY;
                    scaleX = newWidth / originalWidth;
                    scaleY = newHeight / originalHeight;
                    final double scale = Math.min(scaleX, scaleY);
                    newWidth = (int) Math.round(originalWidth * scale);
                    newHeight = (int) Math.round(originalHeight * scale);
                    result = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                    if (result != originalBitmap) {
                        originalBitmap.recycle();
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
            rootView.setDrawingCacheEnabled(flag);
            return result;
        }
        return null;
    }
}
