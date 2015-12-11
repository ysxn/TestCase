
package com.codezyw.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 扩展了OnPageChangeListener, 支持多个OnPageChangeListener
 * <p>
 * Layout manager that allows the user to flip left and right through pages of
 * data. You supply an implementation of a {@link PagerAdapter} to generate the
 * pages that the view shows.
 * <p>
 * Note this class is currently under early design and development. The API will
 * likely change in later updates of the compatibility library, requiring
 * changes to the source code of apps when they are compiled against the newer
 * version.
 * </p>
 * <p>
 * ViewPager is most often used in conjunction with {@link android.app.Fragment}
 * , which is a convenient way to supply and manage the lifecycle of each page.
 * There are standard adapters implemented for using fragments with the
 * ViewPager, which cover the most common use cases. These are
 * {@link android.support.v4.app.FragmentPagerAdapter} and
 * {@link android.support.v4.app.FragmentStatePagerAdapter}; each of these
 * classes have simple code showing how to build a full user interface with
 * them.
 * <p>
 * For more information about how to use ViewPager, read <a href="{@docRoot}
 * training/implementing-navigation/lateral.html">Creating Swipe Views with
 * Tabs</a>.
 * </p>
 * <p>
 * Below is a more complicated example of ViewPager, using it in conjunction
 * with {@link android.app.ActionBar} tabs. You can find other examples of using
 * ViewPager in the API 4+ Support Demos and API 13+ Support Demos sample code.
 * {@sample
 * development/samples/Support13Demos/src/com/example/android/supportv13/app/
 * ActionBarTabsPager.java complete}
 */
public class ViewPagerEx extends ViewPager {

    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private OnPageChangeListener mInternalPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(position);
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageSelected(position);
                    }
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrolled(position, offset, offsetPixels);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrollStateChanged(state);
                    }
                }
            }
        }
    };

    public ViewPagerEx(Context context) {
        super(context);
        init();
    }

    public ViewPagerEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(mInternalPageChangeListener);
    }

    /**
     * Set the currently selected page. If the ViewPager has already been
     * through its first layout with its current adapter there will be a smooth
     * animated transition between the current item and the specified item.
     * 
     * <pre>
     * mPopulatePending = false;
     * setCurrentItemInternal(item, !mFirstLayout, false);
     * </pre>
     * 
     * @param item Item index to select
     */
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    /**
     * Set the currently selected page.
     * 
     * <pre>
     * mPopulatePending = false;
     * setCurrentItemInternal(item, smoothScroll, false);
     * </pre>
     * 
     * @param item Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to
     *            transition immediately
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * <pre>
     * return mCurItem;
     * </pre>
     */
    @Override
    public int getCurrentItem() {
        return super.getCurrentItem();
    }

    /**
     * 外部传递的OnPageChangeListener，区别于{@link #init()}
     * 里面的mInternalPageChangeListener
     * 
     * @see mInternalPageChangeListener
     * @param listener
     */
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    /**
     * Set a {@link PageTransformer} that will be called for each attached page
     * whenever the scroll position is changed. This allows the application to
     * apply custom property transformations to each page, overriding the
     * default sliding look and feel.
     * <p>
     * <em>Note:</em> Prior to Android 3.0 the property animation APIs did not
     * exist. As a result, setting a PageTransformer prior to Android 3.0 (API
     * 11) will have no effect.
     * </p>
     * 
     * <pre>
     * if (Build.VERSION.SDK_INT &gt;= 11) {
     *     final boolean hasTransformer = transformer != null;
     *     final boolean needsPopulate = hasTransformer != (mPageTransformer != null);
     *     mPageTransformer = transformer;
     *     setChildrenDrawingOrderEnabledCompat(hasTransformer);
     *     if (hasTransformer) {
     *         mDrawingOrder = reverseDrawingOrder ? DRAW_ORDER_REVERSE : DRAW_ORDER_FORWARD;
     *     } else {
     *         mDrawingOrder = DRAW_ORDER_DEFAULT;
     *     }
     *     if (needsPopulate)
     *         populate();
     * }
     * </pre>
     * 
     * @param reverseDrawingOrder true if the supplied PageTransformer requires
     *            page views to be drawn from last to first instead of first to
     *            last.
     * @param transformer PageTransformer that will modify each page's animation
     *            properties
     */
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * 外部传递的可以有多个OnPageChangeListener
     * <p>
     * Add a listener that will be invoked whenever the page changes or is
     * incrementally scrolled. See {@link OnPageChangeListener}.
     * <p>
     * Components that add a listener should take care to remove it when
     * finished. Other components that take ownership of a view may call
     * {@link #clearOnPageChangeListeners()} to remove all attached listeners.
     * </p>
     * 
     * @param listener listener to add
     */
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<OnPageChangeListener>();
        }
        mOnPageChangeListeners.add(listener);
    }

    /**
     * Remove a listener that was previously added via
     * {@link #addOnPageChangeListener(OnPageChangeListener)}.
     * 
     * @param listener listener to remove
     */
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    /**
     * Remove all listeners that are notified of any changes in scroll state or
     * position.
     */
    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }
}
