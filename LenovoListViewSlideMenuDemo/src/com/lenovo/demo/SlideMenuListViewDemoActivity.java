
package com.lenovo.demo;

import com.lenovo.component.listviewslidemenu.LenovoListViewSlideMenuListenerMaterial;
import com.lenovo.component.listviewslidemenu.LenovoListViewSlideMenuGroupViewMaterial;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.internal.R;

public class SlideMenuListViewDemoActivity extends Activity {
    private final String TAG = "zyw";
    private boolean DEBUG = true;
    private float mDensity = 1;

    private ListView mListView;
    private DemoAdapter mDemoAdapter;
    private LenovoListViewSlideMenuListenerMaterial mSwipeTouchListener;
    private final static int NUM_ITEM = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewslidemenu_demo_activity);

        mDensity = getResources().getDisplayMetrics().density;
        mListView = (ListView) findViewById(R.id.listviewslidemenu_listView);
        mDemoAdapter = new DemoAdapter(SlideMenuListViewDemoActivity.this);
        mListView.setAdapter(mDemoAdapter);
        mSwipeTouchListener = new LenovoListViewSlideMenuListenerMaterial(this, mListView);
        mListView.setOnTouchListener(mSwipeTouchListener);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onScrollStateChanged , scrollState=" + scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                // TODO Auto-generated method stub
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onScroll , firstVisibleItem=" + firstVisibleItem
                            + ", visibleItemCount=" + visibleItemCount + ", totalItemCount="
                            + totalItemCount);
                }
            }
        });

        mListView.setOnItemClickListener(new AbsListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onItemClick  , position=" + position + ", id=" + id);
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (DEBUG) {
                    Log.i(TAG, ">>>>>onItemLongClick , position=" + position + ", id=" + id);
                }
                return true;
            }
        });

    }

    private class DemoAdapter extends BaseAdapter {
        private Context mContext;

        public DemoAdapter(Context context) {
            // TODO Auto-generated constructor stub
            mContext = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return NUM_ITEM;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = new LenovoListViewSlideMenuGroupViewMaterial(mContext);
                TextView listItem = new TextView(mContext);
                listItem.setGravity(Gravity.CENTER);
                listItem.setMinHeight((int) (60 * mDensity));
                listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT));
                holder.listItem = listItem;
                ((LenovoListViewSlideMenuGroupViewMaterial) convertView).setListItemView(listItem);

                holder.leftMenu = (TextView) ((LenovoListViewSlideMenuGroupViewMaterial) convertView).getLeftMenu();

                holder.rightMenu = (TextView) ((LenovoListViewSlideMenuGroupViewMaterial) convertView).getRightMenu();

                convertView.setTag(holder);
                ((LenovoListViewSlideMenuGroupViewMaterial) convertView)
                        .setLeftMenuClickListener(new LenovoListViewSlideMenuGroupViewMaterial.OnLeftMenuClickListener() {

                            @Override
                            public void onLeftMenuClicked(LenovoListViewSlideMenuGroupViewMaterial listItemViewGroup) {
                                // TODO Auto-generated method stub
                                if (DEBUG) {
                                    Log.i(TAG, ">>>>>>>leftMenu onClick : "
                                            + ((ViewHolder) listItemViewGroup.getTag()).text);
                                    Toast.makeText(mContext, "leftMenu onClick : "+((ViewHolder) listItemViewGroup.getTag()).text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                ((LenovoListViewSlideMenuGroupViewMaterial) convertView)
                        .setRightMenuClickListener(new LenovoListViewSlideMenuGroupViewMaterial.OnRightMenuClickListener() {

                            @Override
                            public void onRightMenuClicked(LenovoListViewSlideMenuGroupViewMaterial listItemViewGroup) {
                                // TODO Auto-generated method stub
                                if (DEBUG) {
                                    Log.i(TAG, ">>>>>>>rightMenu onClick : "
                                            + ((ViewHolder) listItemViewGroup.getTag()).text);
                                    Toast.makeText(mContext, "rightMenu onClick : "+((ViewHolder) listItemViewGroup.getTag()).text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.position = position;
            holder.listItem.setText("List Item " + position);
            holder.leftMenu.setText("MenuL  " + position);
            holder.rightMenu.setText("MenuR  " + position);
            ((LenovoListViewSlideMenuGroupViewMaterial) convertView).setLeftMenuHide(false);
            ((LenovoListViewSlideMenuGroupViewMaterial) convertView).setRightMenuHide(false);
            if (position % 2 == 0) {
                ((LenovoListViewSlideMenuGroupViewMaterial) convertView).setLeftMenuHide(true);
            } else {
                ((LenovoListViewSlideMenuGroupViewMaterial) convertView).setRightMenuHide(true);
            }
            View v = ((LenovoListViewSlideMenuGroupViewMaterial) convertView).getLeftMenu();
            ((TextView)v).setText("Test Left "+position);
            Drawable dLeft = mContext.getDrawable(R.drawable.listviewslidemenu_ic_list_slidemenu_canceltop);
            dLeft.setBounds(0, 0, dLeft.getIntrinsicWidth(), dLeft.getIntrinsicHeight());
            ((TextView)v).setCompoundDrawables(dLeft, null, null, null);

            holder.text = "List Item " + position;
            return convertView;
        }

    }

    private class ViewHolder {
        public int position;
        public String text;
        public TextView listItem;
        public TextView leftMenu;
        public TextView rightMenu;
    }
}
