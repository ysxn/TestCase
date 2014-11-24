package com.android.drawable_example_2;


import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class drawable_example_2 extends Activity {
    /** Called when the activity is first created. */
	private ImageView spaceshipImage;
	private Animation anim;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //main.xml里的ImageView
        spaceshipImage = (ImageView) findViewById(R.id.anim);
        //
        spaceshipImage.setImageDrawable(getResources().getDrawable(R.drawable.spaceshipimage));
        //渐变透明
        //anim = AnimationUtils.loadAnimation(this, R.anim.animation_alpha);
        //位置旋转
        //anim = AnimationUtils.loadAnimation(this, R.anim.animation_rotate);
        //渐变尺寸
        anim = AnimationUtils.loadAnimation(this, R.anim.animation_scale);
        //位置偏移
        //anim = AnimationUtils.loadAnimation(this, R.anim.animation_translate);
        //
        spaceshipImage.startAnimation(anim);
        
    }
}