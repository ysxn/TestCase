package com.codezyw.backuprestore;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

public class ApkListAdapter extends BaseAdapter {

	private final Context mContext;

	private final LayoutInflater mInflater;

	private List<FileData> mApkList;

	private Util mUtil;

	public ApkListAdapter(Context c) {
		mContext = c;
		mUtil = new Util(c);
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<FileData> list) {
		mApkList = list;
	}

	public int getCount() {
		return mApkList.size();
	}

	public Object getItem(int position) {
		return mApkList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new SlideMenuGroup(mContext);
			View listItem = (View) mInflater.inflate(R.layout.list_item,
					parent, false);
			// android.R.layout.simple_list_item_2, parent, false);
			((SlideMenuGroup) convertView).setListItemView(listItem);
		}
		View listItem = ((SlideMenuGroup) convertView).getListItemView();
		TextView textView1 = (TextView) listItem
				.findViewById(android.R.id.text1);
		TextView textView2 = (TextView) listItem
				.findViewById(android.R.id.text2);
		ImageView icon = (ImageView) listItem.findViewById(R.id.icon);
		FileData file = mApkList.get(position);
		textView1.setText(file.mFile.getName());

		long b = file.mFile.length();
		if (b > 1024 * 1024) {
			textView2.setText(file.mFile.getAbsolutePath() + "\n文件大小：" + b
					/ 1024 / 1024 + "MB" + "\n创建时间："
					+ mUtil.convetTime(file.mFile.lastModified())+"\n"+file.mVersion);
		} else {
			textView2.setText(file.mFile.getAbsolutePath() + "\n文件大小：" + b
					/ 1024 + "KB" + "\n创建时间："
					+ mUtil.convetTime(file.mFile.lastModified())+"\n"+file.mVersion);
		}
		if (file.mDrawable != null) {
			icon.setImageDrawable(file.mDrawable);
		}
		convertView.setTag(file);
		TextView menuTextView = (TextView) ((SlideMenuGroup) convertView)
				.getRightMenu();
		menuTextView.setText("删除");
		((SlideMenuGroup) convertView).setLeftMenuHide(true);
		((SlideMenuGroup) convertView)
				.setLeftMenuClickListener(new SlideMenuGroup.OnLeftMenuClickListener() {

					@Override
					public void onLeftMenuClicked(
							SlideMenuGroup listItemViewGroup) {

					}
				});
		((SlideMenuGroup) convertView)
				.setRightMenuClickListener(new SlideMenuGroup.OnRightMenuClickListener() {

					@Override
					public void onRightMenuClicked(
							SlideMenuGroup listItemViewGroup) {
						FileData fdData = (FileData) listItemViewGroup.getTag();
						Toast.makeText(
								mContext,
								"删除安装包：" + fdData.mFile.getName() + "\n路径："
										+ fdData.mFile.getAbsolutePath(),
								Toast.LENGTH_LONG).show();

						fdData.mFile.delete();
						mApkList.remove(fdData);
						ApkListAdapter.this.notifyDataSetChanged();
					}
				});
		return convertView;
	}
}
