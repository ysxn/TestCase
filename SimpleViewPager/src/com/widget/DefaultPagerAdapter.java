package com.widget;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class DefaultPagerAdapter extends PagerAdapter {
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    
    private Context mContext;
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, ViewHolder> mHashMapPager = new HashMap<Integer, ViewHolder>();
    private int mPagerCountReally = 0;
    
    public DefaultPagerAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>getPageTitle() position = " + position);
        }
        ViewHolder holder = mHashMapPager.get(position);
        return "Item " + holder.title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int reallyPosition = position % mPagerCountReally;
        if (DEBUG) {
            Log.i(TAG, ">>>>>>>instantiateItem() position = " + position+", reallyPosition = "+reallyPosition);
        }

        ViewHolder holder = mHashMapPager.get(reallyPosition);
        
        if (holder.image.getParent() != null) {  
            ((ViewPager) holder.image.getParent()).removeView(holder.image);  
        }
        //((ViewPager) container).addView(holder.image, 0);  

        if (ViewHolder.ImageType.TYPE_RESID == holder.mImageType) {
            holder.image.setImageResource(holder.resId);
        } else if (ViewHolder.ImageType.TYPE_DRAWABLE == holder.mImageType) {
            holder.image.setImageDrawable(holder.drawable);
        } else if (ViewHolder.ImageType.TYPE_URI == holder.mImageType) {
            holder.image.setImageURI(holder.uri);
        }
        container.addView(holder.image);
        return holder.image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mPagerCountReally <= 3) {
            return;
        }
        ImageView view = (ImageView) object;
        container.removeView(view);
        //avoid memory leak
        view.setImageBitmap(null);
        
        int reallyPosition = position % mPagerCountReally;
        ViewHolder holder = mHashMapPager.get(reallyPosition);
        if (DEBUG) {
            Log.i(TAG, ">>>>>destroyItem position: " + position
                    +", reallyPosition="+reallyPosition
                    +", holder="+holder.position
                    +", isSame="+holder.image.equals(view)
                    +", count="+container.getChildCount());
        }
        
    }

    public ImageView createImageView() {
        ImageView imageView = new ImageView(mContext);  
        ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(  
                ViewGroup.LayoutParams.MATCH_PARENT,  
                ViewGroup.LayoutParams.MATCH_PARENT);  
        imageView.setLayoutParams(viewPagerImageViewParams);  
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
    
    public void clearImageBitmap() {
        ImageView imageView;
        for (ViewHolder viewHolder : mHashMapPager.values()) {
            imageView = viewHolder.image;
            imageView.setImageBitmap(null);
        }
    }

    public boolean addImageByRedId(int resId) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mImageType = ViewHolder.ImageType.TYPE_RESID;
        viewHolder.resId = resId;
        viewHolder.position = mPagerCountReally;
        viewHolder.title = ""+mPagerCountReally;
        viewHolder.image = createImageView();
        mHashMapPager.put(mPagerCountReally, viewHolder);
        mPagerCountReally++;
        if (DEBUG) {
            Log.i(TAG, ">>>>mPagerCountReally="+mPagerCountReally+" , addPagerByRedId");
        }
        return true;
    }

    public boolean addImageByUri(Uri uri) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mImageType = ViewHolder.ImageType.TYPE_URI;
        viewHolder.uri = uri;
        viewHolder.position = mPagerCountReally;
        viewHolder.title = ""+mPagerCountReally;
        viewHolder.image = createImageView();
        mHashMapPager.put(mPagerCountReally, viewHolder);
        mPagerCountReally++;
        if (DEBUG) {
            Log.i(TAG, ">>>>mPagerCountReally="+mPagerCountReally+" , addPagerByUri");
        }
        return true;
    }

    public boolean addImageByDrawable(Drawable d) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mImageType = ViewHolder.ImageType.TYPE_DRAWABLE;
        viewHolder.drawable = d;
        viewHolder.position = mPagerCountReally;
        viewHolder.title = ""+mPagerCountReally;
        viewHolder.image = createImageView();
        mHashMapPager.put(mPagerCountReally, viewHolder);
        mPagerCountReally++;
        if (DEBUG) {
            Log.i(TAG, ">>>>mPagerCountReally="+mPagerCountReally+" , addPagerByDrawable");
        }
        return true;
    }
    
    private boolean isContains(Drawable d) {
        for (Map.Entry<Integer, ViewHolder> ent: mHashMapPager.entrySet()) {
            ViewHolder viewHolder = ent.getValue();
            if (viewHolder.mImageType == ViewHolder.ImageType.TYPE_DRAWABLE) {
                if (viewHolder.drawable.equals(d)) {
                    Log.e(TAG, ">>>>>addDrawable duplicate!!!");
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @return the number of image
     */
    public int getReallyCount() {
        return mPagerCountReally;
    }
    
    public ViewHolder getCurrentPagerViewHolder(int i) {
        return mHashMapPager.get(i % mPagerCountReally);
    }

    public int getFirstPagerIndex() {
        //25 * (20000 / 25 / 2) = 10000
        int half = Integer.MAX_VALUE / mPagerCountReally / 2;
        return mPagerCountReally * half;
    }

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