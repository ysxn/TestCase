
package com.codezyw.backuprestore;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileListAdapter extends ArrayAdapter<File> {

    public FileListAdapter(Context context, int Resource, List<File> objects) {
        super(context, Resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        File file = getItem(position);
        if (position == 0) {
            view.setText("��ǰĿ¼:" + file.getAbsolutePath());
        } else if (position == 1 && !isRoot()) {
            view.setText("������һ��Ŀ¼");
        } else {
            view.setText(file.getName());
        }
        return view;
    }

    public boolean isRoot() {
        return getItem(0).getParent() == null;
    }
}
