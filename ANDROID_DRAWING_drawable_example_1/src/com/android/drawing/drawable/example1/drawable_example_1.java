package com.android.drawing.drawable.example1;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class drawable_example_1 extends Activity {
    /** Called when the activity is first created. */
	LinearLayout mLinearLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        mLinearLayout = new LinearLayout(this);
        ImageView i = new ImageView(this);
        i.setAdjustViewBounds(true);
        i.setLayoutParams(new Gallery.LayoutParams(
        									LayoutParams.WRAP_CONTENT, 
        									LayoutParams.WRAP_CONTENT)
        );
        mLinearLayout.addView(i);
        setContentView(mLinearLayout);
        Resources res = getResources();
        TransitionDrawable transition = 
        	(TransitionDrawable) res.getDrawable(R.drawable.expand_collapse);
        i.setImageDrawable(transition);
        transition.startTransition(10000);
    }
    
}