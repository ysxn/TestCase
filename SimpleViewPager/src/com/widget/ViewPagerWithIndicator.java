
package com.widget;

import java.util.ArrayList;
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
    
    private List<ImageView> mImageViews = null;
    private int[] mImageResIds = {};
    private Context mContext;
    private DotIndicator mDotIndicator = null;
    private SamplePagerAdapter mSamplePagerAdapter;
    
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
        mImageViews = new ArrayList<ImageView>();
    }
    
    public void initPagerAdapter() {
        mSamplePagerAdapter = new SamplePagerAdapter(mContext);
        this.setAdapter(mSamplePagerAdapter);
        mSamplePagerAdapter.notifyDataSetChanged();
    }
    
    public void setDotIndicator(DotIndicator d) {
        mDotIndicator = d;
    }
    
    public void setImageResIds(int[] resIds) {
        if (resIds == null) {
            Log.e(TAG, ">>>>>setImageResIds NULL error!!!");
            return;
        }
        mImageResIds = resIds;
        if (mDotIndicator != null) {
            mDotIndicator.setTotalItems(mImageResIds.length);
        }
        // 添加viewpager多出的两个view  
        int length = mImageResIds.length + 2;  
        for (int i = 0; i < length; i++) {  
            ImageView imageView = new ImageView(mContext);  
            ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(  
                    ViewGroup.LayoutParams.WRAP_CONTENT,  
                    ViewGroup.LayoutParams.WRAP_CONTENT);  
            imageView.setLayoutParams(viewPagerImageViewParams);  
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);  
            mImageViews.add(imageView);
        }
    }

    public void onPageScrollStateChanged(int i) {
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
                break;

            default:
                break;
        }
        Log.i(TAG, ">>>>>>>>>onPageScrollStateChanged stat="+stat);
    }

    public void onPageSelected(int i) {
        // TODO Auto-generated method stub
        int pageIndex = i;  
        
        if (i == 0) {  
            // 当视图在第一个时，将页面号设置为图片的最后一张。  
            pageIndex = mImageResIds.length;  
        } else if (i == mImageResIds.length + 1) {  
            // 当视图在最后一个是,将页面号设置为图片的第一张。  
            pageIndex = 1;  
        }
        mDotIndicator.setCurrentItem(pageIndex);
        if (i != pageIndex) {
            Log.i(TAG, ">>>>>>>>>i="+i+", pageIndex="+pageIndex);
            this.setCurrentItem(pageIndex, false);
            return;  
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
            return mImageViews.size();
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
            
            if (position == 0) {
                Drawable d = mContext.getResources().getDrawable(mImageResIds[mImageResIds.length - 1]);
                Log.i(TAG, ">>>>"+d.getBounds()+", w="+d.getIntrinsicWidth()+", h="+d.getIntrinsicHeight());
                d.setBounds(0, 0, 100, 100);
                mImageViews.get(position).setImageDrawable(d); 
                //mImageViews.get(position).setImageResource(mImageResIds[mImageResIds.length - 1]);  
            } else if (position == (mImageViews.size() - 1)) {  
                mImageViews.get(position).setImageResource(mImageResIds[0]);  
            } else {  
                mImageViews.get(position).setImageResource(mImageResIds[position - 1]);  
            } 
            container.addView(mImageViews.get(position));  
            
            Log.i(TAG, "instantiateItem() [position: " + position + "]");
            return mImageViews.get(position);
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = mImageViews.get(position);  
            container.removeView(view);  
            view.setImageBitmap(null);
            Log.i(TAG, "destroyItem() [position: " + position + "]");
        }

    }
}
