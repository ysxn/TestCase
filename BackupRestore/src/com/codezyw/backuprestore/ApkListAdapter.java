
package com.codezyw.backuprestore;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codezyw.common.FileIOHelper;
import com.codezyw.common.SlideMenuGroup;
import com.codezyw.common.UIHelper;
import com.codezyw.common.UnitHelper;

public class ApkListAdapter extends BaseAdapter {

    private final Context mContext;

    private final LayoutInflater mInflater;

    private List<FileData> mApkList;

    private FileIOHelper mUtil;

    public ApkListAdapter(Context c) {
        mContext = c;
        mUtil = new FileIOHelper(c);
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<FileData> list) {
        mApkList = list;
    }

    public int getCount() {
        if (mApkList == null) {
            return 0;
        }
        return mApkList.size();
    }

    public Object getItem(int position) {
        if (mApkList == null) {
            return 0;
        }
        return mApkList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new SlideMenuGroup(mContext);
            View listItem = (View) mInflater.inflate(R.layout.list_item, parent, false);
            // android.R.layout.simple_list_item_2, parent, false);
            ((SlideMenuGroup) convertView).setListItemView(listItem);
        }
        View listItem = ((SlideMenuGroup) convertView).getListItemView();
        TextView textView1 = (TextView) listItem.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) listItem.findViewById(android.R.id.text2);
        ImageView icon = (ImageView) listItem.findViewById(R.id.icon);
        FileData file = mApkList.get(position);
        textView1.setText(file.mFile.getName());
        StringBuilder sb = new StringBuilder();
        sb.append(file.mFile.getAbsolutePath()).append("\n");
        long b = file.mFile.length();
        sb.append("文件大小 : ").append(UnitHelper.byteToHumanNumber(b)).append("\n")
                .append("修改时间 : ").append(mUtil.convetTime(file.mFile.lastModified())).append("\n")
                .append(file.mVersion);
        textView2.setText(sb.toString());
        if (file.mDrawable != null) {
            icon.setImageDrawable(file.mDrawable);
        }
        convertView.setTag(file);
        TextView menuTextView = (TextView) ((SlideMenuGroup) convertView).getRightMenu();
        menuTextView.setText("删除");
        ((SlideMenuGroup) convertView).setLeftMenuHide(true);
        ((SlideMenuGroup) convertView).setLeftMenuClickListener(new SlideMenuGroup.OnLeftMenuClickListener() {
            @Override
            public void onLeftMenuClicked(SlideMenuGroup listItemViewGroup) {
            }
        });
        ((SlideMenuGroup) convertView).setRightMenuClickListener(new SlideMenuGroup.OnRightMenuClickListener() {
            @Override
            public void onRightMenuClicked(SlideMenuGroup listItemViewGroup) {
                FileData fdData = (FileData) listItemViewGroup.getTag();
                boolean ok = fdData.mFile.delete();
                if (ok) {
                    mApkList.remove(fdData);
                    ApkListAdapter.this.notifyDataSetChanged();
                    StringBuilder sb = new StringBuilder();
                    sb.append("成功删除安装包：")
                            .append(fdData.mFile.getName())
                            .append("\n路径：")
                            .append(fdData.mFile.getAbsolutePath());
                    UIHelper.showToast(mContext, sb.toString());
                }
            }
        });
        return convertView;
    }
}
