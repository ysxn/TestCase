
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.codezyw.common.OpenFileHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

;
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

    private File mDirectory;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mDirectory = android.os.Environment.getExternalStorageDirectory();
        Log.i(TAG, "sdcard=" + mDirectory);
        setListAdapterByPath(mDirectory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.file_filter) {
            showEditDialog(this);
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
        mDirectory = folder;
        List<File> filesList = new ArrayList<File>();

        Log.i(TAG, "setListAdapterByPath folder=" + folder);
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
                    if (fileA.isDirectory() && fileB.isFile()) {
                        return -1;
                    } else if (fileA.isFile() && fileB.isDirectory()) {
                        return 1;
                    }
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
        } else {
            OpenFileHelper.tryOpenFile(this, file);
        }
    }

    private void showEditDialog(final Context c) {
        View v = LayoutInflater.from(c).inflate(R.layout.rename_fingerprint, null);
        final EditText et = (EditText) v.findViewById(R.id.title);
        new AlertDialog.Builder(c).setTitle(R.string.suffix).setView(v).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String title = et.getText().toString();
                if ("apk".equalsIgnoreCase(title)) {
                    Intent i = new Intent(c, ApkBrowser.class);
                    i.putExtra(Constant.SUFFFIX, title);
                    i.putExtra(Constant.DIRECTORY, mDirectory.getAbsolutePath());
                    c.startActivity(i);
                } else if (title != null && !title.isEmpty()) {
                    Intent i = new Intent(c, FilterBrowser.class);
                    i.putExtra(Constant.SUFFFIX, title);
                    i.putExtra(Constant.DIRECTORY, mDirectory.getAbsolutePath());
                    c.startActivity(i);
                }
                finish();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setCancelable(false).create().show();
    }
}
