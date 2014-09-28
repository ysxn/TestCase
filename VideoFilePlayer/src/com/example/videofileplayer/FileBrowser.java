
package com.example.videofileplayer;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
        List<File> files = new ArrayList<File>();
        files.add(folder);
        if (folder.getParentFile() != null) {
            files.add(folder.getParentFile());
        }
        Log.i(TAG, "setListAdapterByPath folder="+folder);
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e(TAG, "setListAdapterByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                files.add(file);
            }
        }
        mFileListAdapter = new FileListAdapter(this, android.R.layout.simple_list_item_1, files);
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
        } else if (file.getName().matches("^.*?\\.(swf)$")) {
            intent.setClass(FileBrowser.this, MovieActivity.class);
            intent.putExtra("fileName", file.getAbsolutePath().replace("/mnt", ""));
            startActivity(intent);
            FileBrowser.this.finish();
        }
    }
}
