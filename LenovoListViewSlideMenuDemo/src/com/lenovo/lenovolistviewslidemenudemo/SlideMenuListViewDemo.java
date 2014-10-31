package com.lenovo.lenovolistviewslidemenudemo;

import lenovo.view.LenovoSlideMenuGroupView;
import lenovo.widget.LenovoSlidemenuListViewListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SlideMenuListViewDemo extends Activity {
	private static String TAG = "SlideSectionListViewDemo";
	private ListView mListView;
	private LenovoSlidemenuListViewListener mLenovoSlidemenuListViewListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slidesection);
		
		mListView = (ListView)this.findViewById(R.id.listView);
		mListView.setAdapter(new EfficientAdapter(this));
		
		mLenovoSlidemenuListViewListener = new LenovoSlidemenuListViewListener(mListView, 
				new LenovoSlidemenuListViewListener.OnSlideMenuClickListener() {
					
					@Override
					public void onClick(ListView parent, View view, int position) {
						// TODO Auto-generated method stub
						Log.e(TAG, "####onClick:"+position);
						Toast.makeText(SlideMenuListViewDemo.this, "onClick["+position+"]", 200)
            			.show();
					}
				});
		mListView.setOnTouchListener(mLenovoSlidemenuListViewListener);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //Log.e(TAG, "#####onItemClick:"+position);
            }
        });
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//Log.e(TAG, "#####setOnItemLongClickListener:"+arg2);
				mLenovoSlidemenuListViewListener.abortSlide();
				return false;
			}
			
		});

		mListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {       

			@Override
			public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {       
				conMenu.setHeaderTitle("ContextMenu");       
				conMenu.add(0, 0, 0, "Test only!");
			}   
		});       
	}
	
	private class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap mIcon1;
        private boolean mTwoSlideMenuOneSide = false;

        public EfficientAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
            // Icons bound to the rows.
            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_demo_1);
            mTwoSlideMenuOneSide = getApplicationContext().getResources().getBoolean(R.bool.use_two_slide_menu_item_one_side);

        }
        
        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return CALL_DATA.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {               
                LenovoSlideMenuGroupView sectionView = (LenovoSlideMenuGroupView)mInflater.inflate(R.layout.list_item_slide_menu, null);
                View itemView = mInflater.inflate(R.layout.list_item_icon_text_tip, null);
                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.position = position;
                holder.text = (TextView) itemView.findViewById(R.id.text);
                holder.icon = (ImageView) itemView.findViewById(R.id.icon);
                itemView.setTag(holder);
                sectionView.setTag(holder);
                sectionView.setContentView(itemView);
                
                convertView = sectionView;
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
                // We should update the position so that the position is continuous.
                holder.position = position;
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(CALL_DATA[position][0]);
            holder.icon.setImageBitmap(mIcon1);
            ((LenovoSlideMenuGroupView)convertView).setMenuItem(mTwoSlideMenuOneSide);
            
            return convertView;
        }

        class ViewHolder {
        	int position;
            TextView text;
            ImageView icon;
        }
        
        private final String[][] CALL_DATA = {
            {"联想热线01", "4008188818"},
            {"联想热线02", "4008188818"},
            {"联想热线03", "4008188818"},
            {"联想热线04", "4008188818"},
            {"联想热线05", "4008188818"},
            {"联想热线06", "4008188818"},
            {"联想热线07", "4008188818"},
            {"联想热线08", "4008188818"},
            {"联想热线09", "4008188818"},
            {"联想热线10", "4008188818"},
            {"联想热线11", "4008188818"},
            {"联想热线12", "4008188818"},
            {"联想热线13", "4008188818"},
            {"联想热线14", "4008188818"},
            {"联想热线15", "4008188818"},
            {"联想热线16", "4008188818"},
            {"联想热线17", "4008188818"},
            {"联想热线18", "4008188818"},
            {"联想热线19", "4008188818"},
            {"联想热线20", "4008188818"},
            {"联想热线21", "4008188818"},
            {"联想热线22", "4008188818"},
            {"联想热线23", "4008188818"},
            {"联想热线24", "4008188818"},
            {"联想热线25", "4008188818"},
            {"联想热线26", "4008188818"},
            {"联想热线27", "4008188818"},
            {"联想热线28", "4008188818"},
            {"联想热线29", "4008188818"},
            {"联想热线30", "4008188818"},
        };
        
    }
}
