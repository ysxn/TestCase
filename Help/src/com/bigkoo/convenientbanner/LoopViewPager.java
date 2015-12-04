/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bigkoo.convenientbanner;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * A ViewPager subclass enabling infinte scrolling of the viewPager elements
 * <p/>
 * When used for paginating views (in opposite to fragments), no code changes
 * should be needed only change xml's from <android.support.v4.view.ViewPager>
 * to <com.imbryk.viewPager.LoopViewPager>
 * <p/>
 * If "blinking" can be seen when paginating to first or last view, simply call
 * seBoundaryCaching( true ), or change DEFAULT_BOUNDARY_CASHING to true
 * <p/>
 * When using a FragmentPagerAdapter or FragmentStatePagerAdapter, additional
 * changes in the adapter must be done. The adapter must be prepared to create 2
 * extra items e.g.:
 * <p/>
 * The original adapter creates 4 items: [0,1,2,3] The modified adapter will
 * have to create 6 items [0,1,2,3,4,5] with mapping
 * realPosition=(position-1)%count [0->3, 1->0, 2->1, 3->2, 4->3, 5->0]
 */
public class LoopViewPager extends ViewPager {
    private static final boolean DEFAULT_BOUNDARY_CASHING = true;
    private PagerAdapterWrapper mPagerAdapterWrapper;
    private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
    private boolean mCanScrollByTouch = true;

    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private OnPageChangeListener mInternalPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mPagerAdapterWrapper.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(realPosition);
                }
                if (mOnPageChangeListeners != null) {
                    for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                        OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                        if (listener != null) {
                            listener.onPageSelected(realPosition);
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            int realPosition = position;
            if (mPagerAdapterWrapper != null) {
                realPosition = mPagerAdapterWrapper.toRealPosition(position);
                if (offset == 0
                        && mPreviousOffset == 0
                        && (position == 0 || position == mPagerAdapterWrapper.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            mPreviousOffset = offset;
            if (mOnPageChangeListener != null) {
                if (realPosition != mPagerAdapterWrapper.getRealCount() - 1) {
                    mOnPageChangeListener.onPageScrolled(realPosition, offset, offsetPixels);
                } else {
                    if (offset > .5) {
                        mOnPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOnPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        if (realPosition != mPagerAdapterWrapper.getRealCount() - 1) {
                            listener.onPageScrolled(realPosition, offset, offsetPixels);
                        } else {
                            if (offset > .5) {
                                listener.onPageScrolled(0, 0, 0);
                            } else {
                                listener.onPageScrolled(realPosition,
                                        0, 0);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mPagerAdapterWrapper != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition = mPagerAdapterWrapper.toRealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE
                        && (position == 0 || position == mPagerAdapterWrapper.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
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

    public LoopViewPager(Context context) {
        super(context);
        init();
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(mInternalPageChangeListener);
    }

    /**
     * 手动触摸影响滚动
     * 
     * @return
     */
    public boolean canScrollByTouch() {
        return mCanScrollByTouch;
    }

    /**
     * 手动触摸影响滚动
     * 
     * @param isCanScroll
     */
    public void setCanScrollByTouch(boolean isCanScroll) {
        this.mCanScrollByTouch = isCanScroll;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCanScrollByTouch) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mCanScrollByTouch) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    /**
     * helper function which may be used when implementing FragmentPagerAdapter
     * 
     * @param position
     * @param count
     * @return (position-1)%count
     */
    public static int toRealPosition(int position, int count) {
        position = position - 1;
        if (position < 0) {
            position += count;
        } else {
            position = position % count;
        }
        return position;
    }

    /**
     * If set to true, the boundary views (i.e. first and last) will never be
     * destroyed This may help to prevent "blinking" of some views
     * 
     * @param flag
     */
    public void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
        if (mPagerAdapterWrapper != null) {
            mPagerAdapterWrapper.setBoundaryCaching(flag);
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mPagerAdapterWrapper = new PagerAdapterWrapper(adapter);
        mPagerAdapterWrapper.setBoundaryCaching(mBoundaryCaching);
        super.setAdapter(mPagerAdapterWrapper);
        setCurrentItem(0, false);
    }

    @Override
    public PagerAdapter getAdapter() {
        return mPagerAdapterWrapper != null ? mPagerAdapterWrapper.getRealAdapter() : mPagerAdapterWrapper;
    }

    @Override
    public int getCurrentItem() {
        return mPagerAdapterWrapper != null ? mPagerAdapterWrapper.toRealPosition(super.getCurrentItem()) : 0;
    }

    /**
     * Set the currently selected page.
     * 
     * @param item Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to
     *            transition immediately
     */
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mPagerAdapterWrapper.toInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    /**
     * Set the currently selected page. If the ViewPager has already been
     * through its first layout with its current adapter there will be a smooth
     * animated transition between the current item and the specified item.
     * 
     * @param item Item index to select
     */
    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
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

    /**
     * A PagerAdapter wrapper responsible for providing a proper page to
     * LoopViewPager <br>
     * This class shouldn't be used directly
     */
    private static class PagerAdapterWrapper extends PagerAdapter {

        private PagerAdapter mAdapter;

        private SparseArray<ToDestroy> mToDestroy = new SparseArray<ToDestroy>();

        private boolean mBoundaryCaching;

        void setBoundaryCaching(boolean flag) {
            mBoundaryCaching = flag;
        }

        PagerAdapterWrapper(PagerAdapter adapter) {
            this.mAdapter = adapter;
            adapter.registerDataSetObserver(new DataSetObserver() {
                public void onChanged() {
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public void notifyDataSetChanged() {
            mToDestroy = new SparseArray<ToDestroy>();
            super.notifyDataSetChanged();
        }

        int toRealPosition(int position) {
            int realCount = getRealCount();
            if (realCount == 0)
                return 0;
            int realPosition = (position - 1) % realCount;
            if (realPosition < 0)
                realPosition += realCount;

            return realPosition;
        }

        public int toInnerPosition(int realPosition) {
            int position = (realPosition + 1);
            return position;
        }

        private int getRealFirstPosition() {
            return 1;
        }

        private int getRealLastPosition() {
            return getRealFirstPosition() + getRealCount() - 1;
        }

        @Override
        public int getCount() {
            return mAdapter.getCount() + 2;
        }

        public int getRealCount() {
            return mAdapter.getCount();
        }

        public PagerAdapter getRealAdapter() {
            return mAdapter;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = (mAdapter instanceof FragmentPagerAdapter || mAdapter instanceof FragmentStatePagerAdapter)
                    ? position
                    : toRealPosition(position);

            if (mBoundaryCaching) {
                ToDestroy toDestroy = mToDestroy.get(position);
                if (toDestroy != null) {
                    mToDestroy.remove(position);
                    return toDestroy.object;
                }
            }
            return mAdapter.instantiateItem(container, realPosition);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int realFirst = getRealFirstPosition();
            int realLast = getRealLastPosition();
            int realPosition = (mAdapter instanceof FragmentPagerAdapter || mAdapter instanceof FragmentStatePagerAdapter)
                    ? position
                    : toRealPosition(position);

            if (mBoundaryCaching && (position == realFirst || position == realLast)) {
                mToDestroy.put(position, new ToDestroy(container, realPosition,
                        object));
            } else {
                mAdapter.destroyItem(container, realPosition, object);
            }
        }

        /*
         * Delegate rest of methods directly to the inner adapter.
         */

        @Override
        public void finishUpdate(ViewGroup container) {
            mAdapter.finishUpdate(container);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mAdapter.isViewFromObject(view, object);
        }

        @Override
        public void restoreState(Parcelable bundle, ClassLoader classLoader) {
            mAdapter.restoreState(bundle, classLoader);
        }

        @Override
        public Parcelable saveState() {
            return mAdapter.saveState();
        }

        @Override
        public void startUpdate(ViewGroup container) {
            mAdapter.startUpdate(container);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mAdapter.setPrimaryItem(container, position, object);
        }

        /*
         * End delegation
         */

        /**
         * Container class for caching the boundary views
         */
        static class ToDestroy {
            ViewGroup container;
            int position;
            Object object;

            public ToDestroy(ViewGroup container, int position, Object object) {
                this.container = container;
                this.position = position;
                this.object = object;
            }
        }

    }
}
