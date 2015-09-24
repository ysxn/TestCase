
package com.codezyw.backuprestore;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codezyw.common.FileIOHelper;
import com.codezyw.common.UnitHelper;

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
        StringBuilder sb = new StringBuilder();
        sb.append(file.getName()).append("\n");
        if (file.isDirectory()) {
            sb.append("修改时间 : ").append(mUtil.convetTime(file.lastModified()));
            view.setText(sb.toString());
        } else {
            long b = file.length();
            sb.append("文件大小 : ").append(UnitHelper.byteToHumanNumber(b)).append("\n").append("修改时间 : ").append(mUtil.convetTime(file.lastModified()));
            view.setText(sb.toString());
        }
        return view;
    }

    public boolean isRoot() {
        return getItem(0).getParent() == null;
    }
}
