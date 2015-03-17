package com.example.testlaunchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Second extends Activity {

	private static Activity mLast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setTitle("Second");
		Button next = (Button)findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mLast != null) {
					Intent i = new Intent(Second.this, Third.class);
					i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(i);
				} else {
					Intent i = new Intent(Second.this, Third.class);
					startActivity(i);
					Third.setActivity(Second.this);
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mLast != null) {
			mLast.finish();
			mLast = null;
		}
		finish();
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
	
	public static void setActivity(Activity a) {
		mLast = a;
	}
}
