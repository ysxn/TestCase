
package com.example.testjarlistviewslidemenu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.lenovo.component.listviewslidemenu.LenovoListViewSlideMenuAdapter;
import com.lenovo.component.listviewslidemenu.LenovoListViewSlideMenuGroupView;
import com.lenovo.component.listviewslidemenu.LenovoListViewSlideMenuListener;
import com.lenovo.internal.R;

public class SlideMenuListViewDemoActivity extends Activity {
    private static String TAG = "zyw";
    private ListView mListView;
    private LenovoListViewSlideMenuListener mLenovoSlidemenuListViewListener;
    private LenovoListViewSlideMenuAdapter mLenovoListViewSlideMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewslidemenu_demo_activity);

        
        mListView = (ListView) this.findViewById(R.id.listviewslidemenu_listView);
        mLenovoListViewSlideMenuAdapter = new CustomAdapter(this);
        mListView.setAdapter(mLenovoListViewSlideMenuAdapter);

        mLenovoSlidemenuListViewListener = new LenovoListViewSlideMenuListener(mListView,
                new LenovoListViewSlideMenuListener.OnSlideMenuClickListener() {

                    @Override
                    public void onClick(ListView parent, View view, int position) {
                        // TODO Auto-generated method stub
                        Log.e(TAG, "####onSlideMenuClick:" + position+",view="+view);
                        Toast.makeText(SlideMenuListViewDemoActivity.this,
                                "onSlideMenuClick[" + position + "]", 200)
                                .show();
                    }
                });
        mListView.setOnTouchListener(mLenovoSlidemenuListViewListener);
        mListView.setOnScrollListener(new OnScrollListener() {
            
            @Override
            public void onScrollStateChanged(AbsListView list, int state) {
                
                Log.i(TAG, ">>>>>OnScrollListener onScrollStateChanged state="+state);
            }
            
            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                Log.i(TAG, ">>>>>OnScrollListener onScroll arg1="+arg1+",arg2="+arg2+",arg3="+arg3);
            }
        });
    }

    private class CustomAdapter extends LenovoListViewSlideMenuAdapter {
        private Context mContext;

        public CustomAdapter(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            mContext = context;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return CALL_DATA.length;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                LenovoListViewSlideMenuGroupView sectionView = (LenovoListViewSlideMenuGroupView) super
                        .getView(position, convertView, parent);
                //for example, list item is a text view:
                TextView customView = new TextView(mContext);
                customView.setGravity(Gravity.CENTER);
                customView.setMinHeight(100);
                customView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                // Creates a ViewHolder and store references to the two children
                // views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.position = position;
                holder.text = customView;
                customView.setTag(holder);
                sectionView.setTag(holder);
                ((LenovoListViewSlideMenuGroupView) sectionView).setContentView(customView);

                convertView = sectionView;

            } else {
                holder = (ViewHolder) convertView.getTag();
                // We should update the position so that the position is
                // continuous.
                holder.position = position;
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(CALL_DATA[position][0]);
            holder.tag = CALL_DATA[position][0];
            ((LenovoListViewSlideMenuGroupView) convertView).setHideLeftSlideMenu(false);
            ((LenovoListViewSlideMenuGroupView) convertView).setHideRightSlideMenu(false);
            
            //show example for call interface:
            
            boolean test = true;
            
            if (test) {
                ((LenovoListViewSlideMenuGroupView) convertView).getSlideMenuLeftSideText().setText("Left "+position);
                ((LenovoListViewSlideMenuGroupView) convertView).getSlideMenuRightSideText().setText("Right "+position);
                ((LenovoListViewSlideMenuGroupView) convertView).getSlideMenuLeftSideIcon().setImageDrawable(mContext.getResources().getDrawable(R.drawable.listviewslidemenu_ic_list_slidemenu_canceltop));
                ((LenovoListViewSlideMenuGroupView) convertView).getSlideMenuRightSideIcon().setImageDrawable(mContext.getResources().getDrawable(R.drawable.listviewslidemenu_ic_list_slidemenu_canceltop));
            }
            

            return convertView;
        }

        class ViewHolder {
            int position;
            TextView text;
            String tag;
        }
    }

    private final String[][] CALL_DATA = {
            {
                    "联想热线01", "4008188818"
            },
            {
                    "联想热线02", "4008188818"
            },
            {
                    "联想热线03", "4008188818"
            },
            {
                    "联想热线04", "4008188818"
            },
            {
                    "联想热线05", "4008188818"
            },
            {
                    "联想热线06", "4008188818"
            },
            {
                    "联想热线07", "4008188818"
            },
            {
                    "联想热线08", "4008188818"
            },
            {
                    "联想热线09", "4008188818"
            },
            {
                    "联想热线10", "4008188818"
            },
            {
                    "联想热线11", "4008188818"
            },
            {
                    "联想热线12", "4008188818"
            },
            {
                    "联想热线13", "4008188818"
            },
            {
                    "联想热线14", "4008188818"
            },
            {
                    "联想热线15", "4008188818"
            },
            {
                    "联想热线16", "4008188818"
            },
            {
                    "联想热线17", "4008188818"
            },
            {
                    "联想热线18", "4008188818"
            },
            {
                    "联想热线19", "4008188818"
            },
            {
                    "联想热线20", "4008188818"
            },
            {
                    "联想热线21", "4008188818"
            },
            {
                    "联想热线22", "4008188818"
            },
            {
                    "联想热线23", "4008188818"
            },
            {
                    "联想热线24", "4008188818"
            },
            {
                    "联想热线25", "4008188818"
            },
            {
                    "联想热线26", "4008188818"
            },
            {
                    "联想热线27", "4008188818"
            },
            {
                    "联想热线28", "4008188818"
            },
            {
                    "联想热线29", "4008188818"
            },
            {
                    "联想热线30", "4008188818"
            },
    };

}
