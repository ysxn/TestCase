package com.android_exercise;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;

public class List_view extends Activity  implements AdapterView.OnItemClickListener

{
	private ListView listview = null;
	
	private SimpleCursorAdapter cursoradapter = null;
	private Cursor c = null;
	private String projection[] = new String[] {
			calling_card.calling_card_contactors._ID,
			calling_card.calling_card_contactors.NAME
			
	};
	
	private Intent intent = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        
        intent = this.getIntent();
        if (intent.getData() == null) {
            intent.setData(calling_card.calling_card_contactors.CONTENT_URI);
            Log.i("zhuyawen ;List_view ;intent.getData() if", intent.getData().toString());
        }
        else
        {
        	Log.i("zhuyawen ;List_view ;intent.getData() else", intent.getData().toString());
        	
        }

        Log.i("zhuyawen ;List_view ;intent.getData() after", intent.getData().toString());
        
        
        listview = (ListView) findViewById(R.id.ListView01);
        
        c = managedQuery(calling_card.calling_card_contactors.CONTENT_URI,
        		projection,
        		null,
		        null,
		        calling_card.calling_card_contactors._ID + " ASC");
		        
        cursoradapter = new SimpleCursorAdapter(this, 
        		R.layout.listview_item, 
        		c, 
        		new String[] {calling_card.calling_card_contactors._ID, calling_card.calling_card_contactors.NAME}, 
        		new int[] {R.id.TextView01, R.id.TextView02});

        listview.setAdapter(cursoradapter);
        
        
        
		listview.setOnItemClickListener(this);

		
/*
				Intent i = new Intent();
				i.setClass(List_view.this, Android_exercise_edit_activity.class);
				Uri data = ContentUris.withAppendedId(calling_card.calling_card_contactors.CONTENT_URI, 
						c.getLong(c.getColumnIndex(calling_card.calling_card_contactors._ID)));
				i.setData(data);
				startActivity(i);
				finish();
*/

		
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.i("zhuyawen ;List_view ;onItemClick", "int arg2 = " + Integer.toString(arg2) 
				+ " long arg3 = " + Long.toString(arg3)
				);
		Uri data = ContentUris.withAppendedId(calling_card.calling_card_contactors.CONTENT_URI, 
				c.getLong(c.getColumnIndex(calling_card.calling_card_contactors._ID)));
		Intent i = new Intent(Intent.ACTION_EDIT, data);
		//i.setClass(List_view.this, Android_exercise_edit_activity.class);
		//i.setData(data);
		
		Bundle extras = new Bundle();
		extras.putString("name", " ");
		extras.putString("job", " ");
		extras.putString("company", " ");
		extras.putString("myself", " ");
		i.putExtras(extras);
		
		//getContentResolver().delete(data, null, null);
		//return;
		startActivityForResult(i, Android_exercise_main_activity.requestCode_edit);
		//finish();
	}
	

	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    	
        if (requestCode == Android_exercise_main_activity.requestCode_edit)
        {
        	Log.i("zhuawen ;List_view ;onActivityResult","requestCode = requestCode_edit; " + "resultCode = " + Integer.toString(resultCode));
        	switch(resultCode)
            {
            case RESULT_OK:
            	Bundle extras = data.getExtras();
				intent.putExtras(extras);
				
				setResult(RESULT_OK, intent);
            	break;
            	
            default:
            	break;
            }
        }
        else
        {
        	Log.i("zhuawen ;List_view ;onActivityResult","requestCode = none; " + "resultCode = " + Integer.toString(resultCode));
        }

    }
}

