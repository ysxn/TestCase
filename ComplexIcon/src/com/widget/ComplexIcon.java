package com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ComplexIcon extends ViewGroup {

    public ComplexIcon(Context context) {
        this(context, null);
    }
    
    public ComplexIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public ComplexIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    
    public ComplexIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        
    }
    
}