
package com.example.switchanimation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity implements OnItemClickListener {
    private ListView mListView;
    private ListAdapter mListAdapter;
    private int mListItem = 2;
    private boolean DEBUG = true;
    private final String TAG = "zyw";
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mLayoutInflater = LayoutInflater.from(this);
        View v = mLayoutInflater.inflate(R.layout.schpwr_alarm_clock, null);
        setContentView(v);
        mListView = (ListView) v.findViewById(android.R.id.list);
//        mListView = (ListView) findViewById(R.id.list);
        mListAdapter = new ListAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListView.setVerticalScrollBarEnabled(true);
        mListView.setOnItemClickListener(this);
        mListView.setOnCreateContextMenuListener(this);
        registerForContextMenu(mListView);
        
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class ListAdapter extends BaseAdapter {
        private Context mContext;
        public ListAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mListItem;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (DEBUG) Log.i(TAG, "getView position="+position+" ,convertView="+(convertView != null ? convertView.hashCode() : -1));
            if (convertView == null) {
                //ViewGroup vg = new LinearLayout(mContext);
                //vg.setLayoutParams(new LayoutParams(
                //        ViewGroup.LayoutParams.MATCH_PARENT, 150));
                ViewGroup vg = (ViewGroup) mLayoutInflater.inflate(R.layout.item, parent, false);
                Switch sw = (Switch) vg.findViewById(R.id.alarmButton);
                //Switch sw = new Switch(mContext);
                //sw.setFocusable(false);
//                sw.setOnClickListener(new View.OnClickListener() {
//                    
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        boolean is = ((Switch)v).isChecked();
//                        showToast(">>>>>>>isCheck="+is);
//                    }
//                });
                //sw.setLayoutParams(new LayoutParams(
                //        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //vg.addView(sw);
                
                convertView = vg;
            } else {
                
            }
            
            return convertView;
        }
        
    }
    
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        if (DEBUG) Log.i(TAG, s);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Switch sw = (Switch) arg1.findViewById(R.id.alarmButton);
        sw.setChecked(!sw.isChecked());
    }
}
