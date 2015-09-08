
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
                    result = ImageHelper.scaleBitmap(originalBitmap, SCALED_SCREENSHOT_MAX_HEIGHT_WIDTH, SCALED_SCREENSHOT_MAX_HEIGHT_HEIGHT);
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
