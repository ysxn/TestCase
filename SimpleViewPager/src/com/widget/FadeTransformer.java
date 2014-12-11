
package com.widget;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * view������Ȼ���ǻ����е��Ǹ�view��position������float���ͣ�����ƽʱ����intλ�ã����ǵ�ǰ����״̬��һ����ʾ�����統��������ȫ��ʱ
 * ��position��0�������󻬶���ʹ���ұ߸պ���һ����������Ļʱ��position��1�����ǰһҳ����һҳ����������Ļռһ��ʱ��
 * ǰһҳ��position��-0.5����һҳ��posiotn��0.5�����Ը���position��ֵ���ǾͿ�������������Ҫ��alpha��x/y��Ϣ��
 * 
 * @author zhuyw1
 */
public class FadeTransformer implements PageTransformer {
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    private static float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 0) {
            // [-1,0]
            // Use the default slide transition when
            // moving to the left page
            view.setAlpha(1+position);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (position <= 1) {
            // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);
            // Counteract the default slide transition
            view.setTranslationX(0/*pageWidth * -position*/);
            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                    * (1 - Math.abs(position));
            view.setScaleX(1/*scaleFactor*/);
            view.setScaleY(1/*scaleFactor*/);
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
