
package com.codezyw.backuprestore;

import java.io.File;
import java.util.List;

import com.codezyw.common.FileIOHelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FilterBrowserAdapter extends ArrayAdapter<File> {
    private FileIOHelper mUtil;

    public FilterBrowserAdapter(Context context, int Resource, List<File> objects) {
        super(context, Resource, objects);
        mUtil = new FileIOHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        File file = getItem(position);
        if (file.isDirectory()) {
            view.setText(file.getName()+"\n    "+mUtil.convetTime(file.lastModified()));
        } else {
            long b = file.length();
            if (b > 1024*1024) {
                view.setText(file.getName() + "\n    " + b / 1024 / 1024 + "MB" +"\n    "+mUtil.convetTime(file.lastModified()));
            } else if (b > 1024) {
                view.setText(file.getName() + "\n    " + b / 1024 + "KB" +"\n    "+mUtil.convetTime(file.lastModified()));
            } else {
                view.setText(file.getName() + "\n    " + b + "Bytes" +"\n    "+mUtil.convetTime(file.lastModified()));
            }
        }
        
        return view;
    }

    public boolean isRoot() {
        return getItem(0).getParent() == null;
    }
}
