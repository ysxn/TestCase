
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

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
    

    private static final int REQUEST_UPDATE_DATA = 299;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);
                    if (mFileListAdapter != null) {
                        mFileListAdapter.notifyDataSetChanged();
                    }
                }
                break;
            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        File sdcard = android.os.Environment.getExternalStorageDirectory();
        Log.i(TAG, "sdcard="+sdcard);
        setListAdapterByPath(sdcard);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mFileListAdapter.isRoot()) {
            setListAdapterByPath((File) mFileListAdapter.getItem(1));
            /*
             * Intent intent = new Intent(); intent.setClass(FileBrowser.this,
             * FlashActivity.class); startActivity(intent);
             */
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setListAdapterByPath(File folder) {
        List<File> filesList = new ArrayList<File>();

        Log.i(TAG, "setListAdapterByPath folder="+folder);
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
                    
                    return fileA.getName().toUpperCase().compareTo(fileB.getName().toUpperCase());
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
//            intent.setClass(FileBrowser.this, MovieActivity.class);
            //intent.putExtra("fileName", file.getAbsolutePath().replace("/mnt", ""));
            intent.setData(Uri.parse(file.getAbsolutePath()));
            startActivity(intent);
            //FileBrowser.this.finish();
        }
    }
}
