
package com.example.menuanimation;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button mGo = null;

    private ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGo = (Button) findViewById(R.id.go_to_holo);
        mObjectAnimator = new ObjectAnimator();
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                android.util.Log.i("zyw", ">>>>>>");
            }
        });
        ((ValueAnimator)mObjectAnimator).getAnimatedFraction();
        ((ValueAnimator)mObjectAnimator).getAnimatedValue();
        ((ValueAnimator)mObjectAnimator).getCurrentPlayTime();
        mGo.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, HoloActivity.class);
                //i.setFlags(Intent.);
                startActivity(i);
            }
        });
        Thread.dumpStack();
        android.util.Log.i("", "");
        getClass().getName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
