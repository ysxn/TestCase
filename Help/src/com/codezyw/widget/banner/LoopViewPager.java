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

package com.codezyw.widget.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.codezyw.common.ViewPagerEx;

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
public class LoopViewPager extends ViewPagerEx {
    private boolean mCanScrollByTouch = true;

    public LoopViewPager(Context context) {
        super(context);
        init();
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
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
     * A PagerAdapter wrapper responsible for providing a proper page to
     * LoopViewPager <br>
     * This class shouldn't be used directly
     */
    @Deprecated
    private static class PagerAdapterWrapper extends PagerAdapter {

        private PagerAdapter mAdapter;

        private SparseArray<PageHolder> mToDestroy = new SparseArray<PageHolder>();

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
            mToDestroy = new SparseArray<PageHolder>();
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
                PageHolder toDestroy = mToDestroy.get(position);
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
                mToDestroy.put(position, new PageHolder(container, realPosition,
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
        static class PageHolder {
            ViewGroup container;
            int position;
            Object object;

            public PageHolder(ViewGroup container, int position, Object object) {
                this.container = container;
                this.position = position;
                this.object = object;
            }
        }

    }
}
