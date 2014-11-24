package com.android_example_04_19_grid;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.GridView;

public class ImageAdapter extends BaseAdapter {
	private Context mcontext;
	private Integer[] mImageIds =
	{
			R.drawable.p1,
			R.drawable.p2,
			R.drawable.p3,
			R.drawable.p4,
			R.drawable.p5,
			R.drawable.p6,
	};
	
	public ImageAdapter(Context c)
	{
		mcontext = c;
	}
	
	public int getCount()
	{
		return mImageIds.length;
	}
	
	public Object getItem(int position)
	{
		return position;
	}
	
	public long getItemId(int position)
	{
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//ImageView imageview = new ImageView(mcontext);
		//imageview.setImageResource(mImageIds[position]);
		//imageview.setLayoutParams(new Gallery.LayoutParams(120, 120));
		//imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		//imageview.setAdjustViewBounds(true);
		//imageview.setLayoutParams(new Gallery.LayoutParams(
        //        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//imageview.setBackgroundResource(android.R.drawable.picture_frame);
		//return imageview;
		
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mcontext);
            imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mImageIds[position]);

        return imageView;
		
	}
}