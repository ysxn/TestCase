package com.android_example_04_17_gallery;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

public class android_example_04_17_gallery extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery g = (Gallery) findViewById(R.id.Gallery01);
        
        g.setAdapter(new ImageAdapter(this));
        g.setBackgroundColor(android.R.color.black);
        
        
        g.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        	{
        		Toast.makeText(android_example_04_17_gallery.this, "you choose " + (position+1) + " pic.",
        				Toast.LENGTH_SHORT).show();
        	}
        });
        
    }
}