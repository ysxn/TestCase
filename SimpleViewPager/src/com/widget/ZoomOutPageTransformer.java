
package com.widget;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * view理所当然就是滑动中的那个view，position这里是float类型，不是平时理解的int位置，而是当前滑动状态的一个表示，比如当滑动到正全屏时
 * ，position是0，而向左滑动，使得右边刚好有一部被进入屏幕时，position是1，如果前一页和下一页基本各在屏幕占一半时，
 * 前一页的position是-0.5，后一页的posiotn是0.5，所以根据position的值我们就可以自行设置需要的alpha，x/y信息。
 * 
 * @author zhuyw1
 */
public class ZoomOutPageTransformer implements PageTransformer {
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    private static float MIN_SCALE = 0.85f;

    private static float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) {
            // [-1,1]
            // Modify the default slide transition to
            // shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
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
