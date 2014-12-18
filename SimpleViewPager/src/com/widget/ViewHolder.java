package com.widget;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

public class ViewHolder {
    public static enum ImageType {
        TYPE_INVALID, TYPE_RESID, TYPE_DRAWABLE, TYPE_URI
    };
    public int position = -1;
    public String title = null;
    public ImageView image = null;
    public Uri uri = null;
    public Drawable drawable = null;
    public int resId = -1;
    public ImageType mImageType = ImageType.TYPE_INVALID;
}