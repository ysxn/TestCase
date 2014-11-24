package com.list_view1;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class list_view1 extends ListActivity {
//Notice that we don't need to load a layout (at least, not in this case, because we're using the whole screen for our list). Instead, we just call setListAdapter() (which automatically adds a ListView to the ListActivity), and provide it with an ArrayAdapter that binds a simple_list_item_1 layout item to each entry in the COUNTRIES array (added next). The next line of code adds a text filter to the ListView, so that when the user begins typing, the list will filter the entire view to display only the items that match the entry.
	// Create a message handling object as an anonymous class.
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id)
	    {
	        // Display a messagebox.
	        NoteDebug("You've got an event");
	    }
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String []name = new String[]{"Java","C++","C","C#","VB","XML",".NET","J#"};
        ArrayAdapter<String> arrayadapter
        	=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
        
      //设置适配器
        //setListAdapter(arrayadapter);
        getListView().setAdapter(arrayadapter);
       
        
        
        getListView().setOnItemClickListener(mMessageClickedHandler);
        
    }
    
	private void NoteDebug(String string) {
		// TODO Auto-generated method stub
		/*显示Toast提示*/
		Toast.makeText(this,string, Toast.LENGTH_SHORT ).show();
	}
}
