package com.android_example_04_19_grid;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class android_example_04_19_grid extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView g = (GridView) findViewById(R.id.gridview);
        
        g.setAdapter(new ImageAdapter(this));
        
        g.setBackgroundColor(android.R.color.black);
        
        g.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        	{
        		Toast.makeText(android_example_04_19_grid.this, "you choose " + (position+1) + " pic.",
        				Toast.LENGTH_SHORT).show();
        	}
        });
    }
}