
package com.zyw.videofileplayer;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codezyw.common.ColorHelper;
import com.codezyw.common.FileHelper;
import com.codezyw.common.ShapeHelper;
import com.codezyw.common.UnitHelper;

public class FileBrowser extends ListActivity {
    private final String TAG = "zyw";

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
            return true;
        }
    };

    private FileListAdapter mFileListAdapter;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        File sdcard = android.os.Environment.getExternalStorageDirectory();
        Log.i(TAG, "sdcard=" + sdcard);
        setListAdapterByPath(sdcard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.file_filter) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mFileListAdapter.isRoot()) {
            setListAdapterByPath((File) mFileListAdapter.getItem(1));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setListAdapterByPath(File folder) {
        List<File> filesList = new ArrayList<File>();
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e(TAG, "setListAdapterByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                filesList.add(file);
            }
            Collections.sort(filesList, new Comparator<File>() {
                @Override
                public int compare(File fileA, File fileB) {
                    if (fileA.isDirectory() && fileB.isFile())
                        return -1;
                    if (fileA.isFile() && fileB.isDirectory())
                        return 1;

                    return fileA.getName().toUpperCase(Locale.getDefault()).compareTo(fileB.getName().toUpperCase(Locale.getDefault()));
                }
            });
        }
        filesList.add(0, folder);
        if (folder.getParentFile() != null) {
            filesList.add(1, folder.getParentFile());
        }
        mFileListAdapter = new FileListAdapter(this, android.R.layout.simple_list_item_1, filesList);
        setListAdapter(mFileListAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = (File) mFileListAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (file.isDirectory()) {
            setListAdapterByPath(file);
        } else if (file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$")) {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            startActivity(intent);
        } else if (file.getName().matches("^.*?\\.(swf|mp4|3gp)$")) {
            intent.setClass(FileBrowser.this, MovieActivity.class);
            // intent.putExtra("fileName",
            // file.getAbsolutePath().replace("/mnt", ""));
            intent.setData(Uri.parse(file.getAbsolutePath()));
            startActivity(intent);
        } else {
            FileHelper.tryOpenFile(this, file);
        }
    }

    public class FileListAdapter extends ArrayAdapter<File> {

        public FileListAdapter(Context context, int Resource, List<File> objects) {
            super(context, Resource, objects);
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
                view.setTextColor(ColorHelper.BLACK);
                StringBuilder sb = new StringBuilder();
                sb.append(file.getName()).append("\n");
                if (file.isDirectory()) {
                    sb.append("修改时间 : ").append(FileHelper.convetTime(file.lastModified()));
                    view.setText(sb.toString());
                } else {
                    long b = file.length();
                    sb.append("文件大小 : ").append(UnitHelper.byteToHumanNumber(b)).append("\n").append("修改时间 : ")
                            .append(FileHelper.convetTime(file.lastModified()));
                    view.setText(sb.toString());
                }
            }
            return view;
        }

        public boolean isRoot() {
            return getItem(0).getParent() == null;
        }
    }
}
