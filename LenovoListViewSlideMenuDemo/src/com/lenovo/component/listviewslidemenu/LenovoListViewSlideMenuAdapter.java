
package com.lenovo.component.listviewslidemenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lenovo.internal.R;

public abstract class LenovoListViewSlideMenuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public LenovoListViewSlideMenuAdapter(Context context) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);

    }

    /**
     * Make a view to hold each row.
     * 
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = (LenovoListViewSlideMenuGroupView) mInflater
                    .inflate(R.layout.listviewslidemenu_list_item_slide_menu, null);
        }
        return convertView;
    }
}
