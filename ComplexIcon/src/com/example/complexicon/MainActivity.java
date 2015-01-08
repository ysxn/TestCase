
package com.example.complexicon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.widget.ViewAnimation;

//import com.amulyakhare.td.sample.MainActivityNew;

public class MainActivity extends Activity {
    private String TAG = "zyw";
    private boolean DEBUG = true;
    private ImageView mButton = null;
    private ImageView mViewBehind = null;
    private ImageView mViewUpper = null;
    private ImageView mViewMiddle = null;
    private Animation mAnimation = null;
    private ViewPropertyAnimator mViewPropertyAnimator = null;

    private float mDownX;
    private float mDownY;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mViewBehind = (ImageView) findViewById(R.id.imgBehind);
        mViewUpper = (ImageView) findViewById(R.id.imgUpper);
        mViewMiddle = (ImageView) findViewById(R.id.imgMiddle);
        //mViewMiddle.setImageDrawable(new ColorDrawable(Color.RED));
        //mViewMiddle.setElevation(20);
        //mViewMiddle.setRotationX(90);
        mViewMiddle.setRotationX(0);
        mViewMiddle.setRotationY(-90);
        mViewBehind.getDrawable().setDither(true);
        mViewUpper.getDrawable().setDither(true);
        mViewMiddle.getDrawable().setDither(true);
        //showToast("mViewBehind="+mViewBehind.getElevation()+" , mViewUpper="+mViewUpper.getElevation());
        mButton = (ImageView) findViewById(R.id.imgPlay);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mAnimation = new ViewAnimation(windowManager.getDefaultDisplay()
                .getWidth() / 2,
                windowManager.getDefaultDisplay().getHeight() / 2);
        mAnimation.setInterpolator(new LinearInterpolator());
        
        mButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //doAnimate1(mViewUpper);
                //doAnimate2(mViewBehind);
                Intent i = new Intent(MainActivity.this, com.amulyakhare.td.sample.MainActivityNew.class);
                startActivity(i);
            }
        });
    }
    
    public void doAnimate1(final View view) {
        mViewPropertyAnimator = view.animate();
        mViewPropertyAnimator.cancel();
        //mViewPropertyAnimator.alpha(0);
        //mViewPropertyAnimator.z(120);
        mViewPropertyAnimator.rotationY(360);
        mViewPropertyAnimator.setStartDelay(0);
        mViewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            
            @Override
            public void onAnimationStart(Animator animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animator animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(1.0f);
                view.setRotationY(0);
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {
                view.setRotationY(0);
            }
        });
        mViewPropertyAnimator.setDuration(3000);
        mViewPropertyAnimator.start();

        //mView.setAnimation(mAnimation);
        //mViewBehind.startAnimation(mAnimation);
    }
    

    public void doAnimate2(final View view) {
        mViewPropertyAnimator = view.animate();
        mViewPropertyAnimator.cancel();
        //mViewPropertyAnimator.alpha(0);
        //mViewPropertyAnimator.z(120);
        mViewPropertyAnimator.rotationY(360);
        mViewPropertyAnimator.setStartDelay(0);
        mViewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            
            @Override
            public void onAnimationStart(Animator animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animator animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setRotationY(0);
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {
                view.setRotationY(0);
            }
        });
        mViewPropertyAnimator.setDuration(3000);
        mViewPropertyAnimator.start();

        //mView.setAnimation(mAnimation);
        //mViewBehind.startAnimation(mAnimation);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        final int action = event.getAction();
        
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getRawX() - mDownX);
                int deltaY = (int) (event.getRawY() - mDownY);
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onTouch ACTION_MOVE"
                            +", touch=("+event.getRawX()+","+event.getRawY()+")"
                            +", mDown=("+mDownX+","+mDownY+")"
                            +", delta=("+deltaX+","+deltaY+")");
                }

                mViewBehind.setRotationX(-deltaY);
                mViewBehind.setRotationY(deltaX);
                mViewUpper.setRotationX(-deltaY);
                mViewUpper.setRotationY(deltaX);
                mViewMiddle.setRotationX(-deltaY);
                mViewMiddle.setRotationY(deltaX-90);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mViewBehind.setRotationX(0);
                mViewBehind.setRotationY(0);
                mViewUpper.setRotationX(0);
                mViewUpper.setRotationY(0);
                mViewMiddle.setRotationX(0);
                mViewMiddle.setRotationY(-90);
                showToast("mViewBehind="+mViewBehind.getElevation()
                        +" , mViewUpper="+mViewUpper.getElevation()
                        +" , mViewMiddle="+mViewMiddle.getElevation());
                break;

            default:
                break;
        }
        return true;
    }
    
    private void showToast(String text) {
        if (DEBUG) {
            Log.i(TAG, ">>>>>>log : "+text);
        }
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
