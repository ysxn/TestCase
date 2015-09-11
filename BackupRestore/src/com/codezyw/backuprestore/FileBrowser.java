
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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.codezyw.common.OpenFileHelper;

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
            showEditDialog(this);
            return true;
        }
        if (id == R.id.directory) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        } else {
            tryOpenFile(file);
        }
    }

    private boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private void tryOpenFile(File f) {
        if (f != null && f.isFile()) {
            String fileName = f.getName();
            Intent intent;
            if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingImage))) {
                intent = OpenFileHelper.getImageFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingWebText))) {
                intent = OpenFileHelper.getHtmlFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingPackage))) {
                intent = OpenFileHelper.getApkFileIntent(f);
                startActivity(intent);

            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingAudio))) {
                intent = OpenFileHelper.getAudioFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingVideo))) {
                intent = OpenFileHelper.getVideoFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingText))) {
                intent = OpenFileHelper.getTextFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingPdf))) {
                intent = OpenFileHelper.getPdfFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingWord))) {
                intent = OpenFileHelper.getWordFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingExcel))) {
                intent = OpenFileHelper.getExcelFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, getResources().getStringArray(R.array.fileEndingPPT))) {
                intent = OpenFileHelper.getPPTFileIntent(f);
                startActivity(intent);
            } else {
                // showMessage("无法打开，请安装相应的软件！");
                try {
                    intent = OpenFileHelper.getTextFileIntent(f);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {

                }
            }
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
