package com.android.demo.menu_01;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class menu_01 extends Activity {
    /** Called when the activity is first created. */
	public static final int ADD_ID = Menu.FIRST;
	public static final int DELETE_ID = Menu.FIRST + 1;
	public static final int EXIT_ID = Menu.FIRST + 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0, ADD_ID, 0, R.string.menu_add);
		menu.add(0, DELETE_ID, 1, R.string.menu_delete);
		menu.add(0, EXIT_ID, 2, R.string.menu_exit);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId())
		{
		case ADD_ID: 
			setTitle("Insert...");
			break;
		case DELETE_ID:
			setTitle("Delete...");
			break;
		case EXIT_ID:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	

}