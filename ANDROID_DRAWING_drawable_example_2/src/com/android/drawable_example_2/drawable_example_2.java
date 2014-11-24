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
        //main.xml���ImageView
        spaceshipImage = (ImageView) findViewById(R.id.anim);
        //
        spaceshipImage.setImageDrawable(getResources().getDrawable(R.drawable.spaceshipimage));
        //����͸��
        //anim = AnimationUtils.loadAnimation(this, R.anim.animation_alpha);
        //λ����ת
        //anim = AnimationUtils.loadAnimation(this, R.anim.animation_rotate);
        //����ߴ�
        anim = AnimationUtils.loadAnimation(this, R.anim.animation_scale);
        //λ��ƫ��
        //anim = AnimationUtils.loadAnimation(this, R.anim.animation_translate);
        //
        spaceshipImage.startAnimation(anim);
        
    }
}