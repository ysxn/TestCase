
package com.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerWithIndicator extends ViewPager {
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    
    //private List<ImageView> mImageViews = null;
    private HashMap<Integer, ImageView> mImageViews = new HashMap<Integer, ImageView>();
    private List<Drawable> mDrawables = null;
    private int[] mImageResIds = {};
    private Context mContext;
    private DotIndicator mDotIndicator = null;
    private SamplePagerAdapter mSamplePagerAdapter = null;
    private boolean mUseResIds = true;
    private int mCurrentPager = 1;
    
    public ViewPagerWithIndicator(Context context) {
        super(context);
        mContext = context;
        initViewPager(context);
    }

    public ViewPagerWithIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViewPager(context);
    }
    
    private void initViewPager(Context context) {
        mDrawables = new ArrayList<Drawable>();
    }
    
    public void setDotIndicator(DotIndicator d) {
        mDotIndicator = d;
        mUseResIds = false;
        mDrawables.clear();
        mImageViews.clear();
        // 添加viewpager多出的两个view 
        newImageView(2);
    }
    
    public void newImageView(int count) {
        for (int i : mImageResIds) {
            ImageView imageView = new ImageView(mContext);  
            ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(  
                    ViewGroup.LayoutParams.WRAP_CONTENT,  
                    ViewGroup.LayoutParams.WRAP_CONTENT);  
            imageView.setLayoutParams(viewPagerImageViewParams);  
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mImageViews.put(mImageResIds[i], imageView);
        }
    }
    
    public void clearImageViews() {
        ImageView imageView;
        for (int i : mImageResIds) {
            imageView = mImageViews.get(i);
            imageView.setImageBitmap(null);
        }
    }
    
    public void setDotIndicatorAndResIds(DotIndicator d, int[] resIds) {
        mDotIndicator = d;
        if (resIds == null || mDotIndicator == null) {
            Log.e(TAG, ">>>>>setDotIndicatorAndResIds NULL error!!!");
            return;
        }
        mUseResIds = true;
        this.removeAllViews();
        clearImageViews();
        mImageViews.clear();
        updatePagerAdapter();
        mImageResIds = resIds;
        mDotIndicator.setTotalItems(mImageResIds.length);
        // 添加viewpager多出的两个view  
        int length = mImageResIds.length;  
        newImageView(length);
        mDrawables.clear();
        updatePagerAdapter();
    }
    
    public boolean isUseResIds() {
        return mUseResIds;
    }
    
    public void addDrawable(Drawable d) {
        if (mUseResIds) {
            Log.e(TAG, ">>>>>addDrawable mode is mUseResIds!!!");
            return;
        }
        if (mDotIndicator == null || d == null) {
            Log.e(TAG, ">>>>>addDrawable NULL error!!!");
            return;
        }
        if (mDrawables.contains(d)) {
            Log.e(TAG, ">>>>>addDrawable duplicate!!!");
            return;
        }
        mUseResIds = false;
        mDrawables.add(d);
        newImageView(1);
        mDotIndicator.setTotalItems(mDrawables.size());
        updatePagerAdapter();
    }
    
    public void removeDrawable(Drawable d) {
        if (mUseResIds) {
            Log.e(TAG, ">>>>>removeDrawable mode is mUseResIds!!!");
            return;
        }
        if (mDotIndicator == null || d == null || mImageViews.size() <= 0) {
            Log.e(TAG, ">>>>>removeDrawable NULL error!!!");
            return;
        }
        if (!mDrawables.contains(d)) {
            Log.e(TAG, ">>>>>removeDrawable not exist!!!");
            return;
        }
        mUseResIds = false;
        mDrawables.remove(d);
        mImageViews.remove(0);
        mDotIndicator.setTotalItems(mDrawables.size());
        updatePagerAdapter();
    }
    
    public void updatePagerAdapter() {
        if (mImageResIds == null || mDotIndicator == null) {
            Log.e(TAG, ">>>>>initPagerAdapter NULL error!!!");
            return;
        }
        if (mSamplePagerAdapter == null) {
            mSamplePagerAdapter = new SamplePagerAdapter(mContext);
            this.setAdapter(mSamplePagerAdapter);
        }
        mSamplePagerAdapter.notifyDataSetChanged();
    }

    public void onPageScrollStateChangedEvent(int i) {
        // TODO Auto-generated method stub
        String stat = "STATE_IDLE";
        switch (i) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                stat = "STATE_DRAGGING";
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                stat = "STATE_SETTLING";
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                //this.setCurrentItem(mCurrentPager, false);
                //this.postInvalidate();
                break;

            default:
                break;
        }
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>>>onPageScrollStateChangedEvent stat="+stat+" , mCurrentPager="+mCurrentPager);
        }
    }

    public void onPageSelectedEvent(int i) {
        // TODO Auto-generated method stub
        int pageIndex = i;  
        
        if (i == 0) {  
            // 当视图在第一个时，将页面号设置为图片的最后一张。  
            //pageIndex = mUseResIds ? mImageResIds.length : mDrawables.size();
        } else if (i == (mUseResIds ? mImageResIds.length + 1 : mDrawables.size() + 1)) {  
            // 当视图在最后一个是,将页面号设置为图片的第一张。  
            //pageIndex = 1;  
        }
        mCurrentPager = pageIndex;
        mDotIndicator.setCurrentItem(pageIndex);
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>>>onPageSelectedEvent i="+i+", pageIndex="+pageIndex);
        }
        if (i != pageIndex) {
            //this.setCurrentItem(pageIndex, false);
            return;  
        } 
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Invalidate the adapter's data set since children may have been added during inflation.
        if (getAdapter() == mSamplePagerAdapter && mSamplePagerAdapter != null) {
            mSamplePagerAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {
        private Context mContext;
        
        public SamplePagerAdapter(Context c) {
            // TODO Auto-generated constructor stub
            mContext = c;
        }

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return mImageViews.size()+2;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewHolder holder;
//            if (position == 0) {
//                //Drawable d = mContext.getResources().getDrawable(mImageResIds[mImageResIds.length - 1]);
//                //Log.i(TAG, ">>>>"+d.getBounds()+", w="+d.getIntrinsicWidth()+", h="+d.getIntrinsicHeight());
//                //d.setBounds(0, 0, 100, 100);
//                if (mUseResIds) {
//                    mImageViews.get(mImageResIds[mImageResIds.length - 1]).setImageResource(mImageResIds[mImageResIds.length - 1]);
//                } else {
//                    mImageViews.get(position).setImageDrawable(mDrawables.get(mDrawables.size() - 1));
//                }
//            } else if (position == (mImageViews.size() - 1)) {
//                if (mUseResIds) {
//                    mImageViews.get(mImageResIds[0]).setImageResource(mImageResIds[0]);
//                } else {
//                    mImageViews.get(position).setImageDrawable(mDrawables.get(0));
//                }
//            } else {
//                if (mUseResIds) {
//                    mImageViews.get(mImageResIds[position]).setImageResource(mImageResIds[position - 1]);
//                } else {
//                    mImageViews.get(position).setImageDrawable(mDrawables.get(position - 1));
//                }
//            }
            
            //container.addView(mImageViews.get(mImageResIds[position]));  
            if (DEBUG) {
                Log.i(TAG, "instantiateItem() [position: " + position + "]");
            }
            Object tmp = mImageViews.get(position).getTag();
            if (tmp != null && tmp instanceof ViewHolder) {
                holder = (ViewHolder) tmp;
            } else {
                holder = new ViewHolder();
            }
            holder.position = position;
            mImageViews.get(position).setTag(holder);
            return mImageViews.get(position);
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;//container.getChildAt(position);//mImageViews.get(position);
            if (DEBUG) {
                Log.i(TAG, "destroyItem() [position: " + position + "]"
                        +", count="+container.getChildCount()
                        +", isViewPager="+(container instanceof ViewPager));
            }
            ViewHolder holder;
            Object tmp = view.getTag();
            if (tmp != null && tmp instanceof ViewHolder) {
                holder = (ViewHolder) tmp;
                Log.i(TAG, ">>>>>>>destroyItem position="+holder.position);
            }
            container.removeView(view);
            //avoid memory leak
            view.setImageBitmap(null);
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
