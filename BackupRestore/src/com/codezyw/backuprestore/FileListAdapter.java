
package com.codezyw.backuprestore;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codezyw.common.ColorHelper;
import com.codezyw.common.FileIOHelper;
import com.codezyw.common.ShapeHelper;
import com.codezyw.common.UnitHelper;

public class FileListAdapter extends ArrayAdapter<File> {
    private FileIOHelper mUtil;

    public FileListAdapter(Context context, int Resource, List<File> objects) {
        super(context, Resource, objects);
        mUtil = new FileIOHelper(context);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1 && !isRoot()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        File file = getItem(position);
        if (getItemViewType(position) == 0) {
            view.setText("当前目录:" + file.getAbsolutePath());
            view.setBackground(ShapeHelper.createRectShape(ColorHelper.GREEN));
            view.setTextColor(ColorHelper.BLACK);
        } else if (getItemViewType(position) == 1) {
            view.setText("返回上一个目录");
            view.getPaint().setFakeBoldText(true);
            view.setTextColor(ColorHelper.RED);
        } else {
            view.getPaint().setFakeBoldText(false);
            view.setTextColor(ColorHelper.WHITE);
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
        }
        return view;
    }

    public boolean isRoot() {
        return getItem(0).getParent() == null;
    }
}
