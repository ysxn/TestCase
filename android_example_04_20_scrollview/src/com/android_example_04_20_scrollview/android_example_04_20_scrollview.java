package com.android_example_04_20_scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;


/**
 * Demonstrates wrapping a layout in a ScrollView.
 *
 */
public class android_example_04_20_scrollview extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_2);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        for (int i = 2; i < 64; i++) {
            TextView textView = new TextView(this);
            textView.setText("Text View " + i);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layout.addView(textView, p);

            Button buttonView = new Button(this);
            buttonView.setText("Button " + i);
            layout.addView(buttonView, p);
        }
    }
}
