package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FilterBrowser extends ListActivity {
    private final String TAG = "zyw";
    
    private static String mSuffix = "";

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
        	return true;
        }
    };

    private FilterBrowserAdapter mFileListAdapter;
    
    private ProgressDialog mProgressDialog;
    
    private ListView mListView;
    
    private List<File> mFilesList = new ArrayList<File>();

    private static final int REQUEST_UPDATE_PROGRESS = 299;

    private final int REQUEST_UPDATE_DATA = 0x123;

    private final int REQUEST_DISMISS_PROGRESS = 0x122;



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
                case REQUEST_UPDATE_PROGRESS: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        int count = msg.arg1;
                        String fileName = (String) msg.obj;
                        mProgressDialog.setMessage("已经扫描到 " + count + " 个"+mSuffix+"文件：" + fileName);
                    }
                }
                    break;
                case REQUEST_DISMISS_PROGRESS: {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    mFileListAdapter = new FilterBrowserAdapter(FilterBrowser.this, android.R.layout.simple_list_item_1, mFilesList);
                    setListAdapter(mFileListAdapter);
                    Toast.makeText(FilterBrowser.this, "已经扫描到 " + mFilesList.size() +" 个"+mSuffix+"文件：",
                            Toast.LENGTH_LONG).show();
                }
                    break;
            }
        }
    };

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mListView = getListView();
        Intent i = getIntent();
        mSuffix = i.getStringExtra("suffix");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        mProgressDialog.setTitle("正在扫描"+mSuffix+"文件"+"中...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        final File sdcard = android.os.Environment.getExternalStorageDirectory();
        Log.i(TAG, "sdcard=" + sdcard);
        new Thread() {
            public void run() {
            	setListAdapterByPath(sdcard);
                mHandler.sendEmptyMessage(REQUEST_DISMISS_PROGRESS);
            };
        }.start();

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            	File file = (File) mFileListAdapter.getItem(position);
                Toast.makeText(FilterBrowser.this, "路径："+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
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
		return super.onOptionsItemSelected(item);
	}

    private void setListAdapterByPath(File folder) {
        Log.i(TAG, "setListAdapterByPath folder=" + folder);
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e(TAG, "setListAdapterByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                if (!file.isDirectory() && file.getName().endsWith(mSuffix)) {
                    // file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
                    mFilesList.add(file);
                    mHandler.sendMessage(mHandler.obtainMessage(REQUEST_UPDATE_PROGRESS,
                            mFilesList.size(), 0, file.getName()));
                } else if (file.isDirectory()) {
                	setListAdapterByPath(file);
                }
            }
        }
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
            if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingImage))) {
                intent = OpenFiles.getImageFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingWebText))) {
                intent = OpenFiles.getHtmlFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingPackage))) {
                intent = OpenFiles.getApkFileIntent(f);
                startActivity(intent);

            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingAudio))) {
                intent = OpenFiles.getAudioFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingVideo))) {
                intent = OpenFiles.getVideoFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingText))) {
                intent = OpenFiles.getTextFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingPdf))) {
                intent = OpenFiles.getPdfFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingWord))) {
                intent = OpenFiles.getWordFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingExcel))) {
                intent = OpenFiles.getExcelFileIntent(f);
                startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName,
                    getResources().getStringArray(R.array.fileEndingPPT))) {
                intent = OpenFiles.getPPTFileIntent(f);
                startActivity(intent);
            } else {
//                showMessage("无法打开，请安装相应的软件！");
                try {
                    intent = OpenFiles.getTextFileIntent(f);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    
                }
            }
        }
    }
    
    private void showEditDialog(final Context c) {
        View v = LayoutInflater.from(c).inflate(R.layout.rename_fingerprint, null);
        final EditText et = (EditText) v.findViewById(R.id.title);
        new AlertDialog.Builder(c)
        		.setTitle("输入过滤后缀名：")
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String title = et.getText().toString();
                        Intent i = new Intent(c, FilterBrowser.class);
                        i.putExtra("suffix", title);
                        c.startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
    }
}
