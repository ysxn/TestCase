
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codezyw.common.BaseListFragment;
import com.codezyw.common.Constant;
import com.codezyw.common.FileIOHelper;
import com.codezyw.common.OpenFileHelper;
import com.codezyw.common.UIHelper;
import com.codezyw.common.UnitHelper;

public class SearchFragment extends BaseListFragment {
    private final String TAG = "zyw";

    private String mTitle = "";
    private static String mSuffix = "";
    private static String mDirectory = "";

    private static final FileFilter FILTER = new FileFilter() {
        public boolean accept(File f) {
            // return f.isDirectory() ||
            // f.getName().matches("^.*?\\.(jpg|png|bmp|gif)$");
            return true;
        }
    };

    private SearchResultAdapter mFileListAdapter;
    private ListView mListView;
    private List<File> mFilesList = new ArrayList<File>();

    private static final int MSG_UPDATE_PROGRESS = 299;
    private static final int MSG_UPDATE_DATA = 0x123;
    private static final int MSG_DISMISS_PROGRESS = 0x122;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_DATA:
                    mHandler.removeMessages(MSG_UPDATE_DATA);
                    if (mFileListAdapter != null) {
                        mFileListAdapter.notifyDataSetChanged();
                    }
                    break;
                case MSG_UPDATE_PROGRESS:
                    int count = msg.arg1;
                    String fileName = (String) msg.obj;
                    UIHelper.updateProgressDialogText(mTitle, "已经扫描到 " + count + " 个" + mSuffix + "文件：" + fileName);
                    break;
                case MSG_DISMISS_PROGRESS:
                    UIHelper.dismissProgressDialog();
                    mFileListAdapter = new SearchResultAdapter(getActivity(), android.R.layout.simple_list_item_1, mFilesList);
                    setListAdapter(mFileListAdapter);
                    Toast.makeText(getActivity(), "已经扫描到 " + mFilesList.size() + " 个" + mSuffix + "文件：", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    /**
     * 必须有无参构造函数，否则影响状态恢复
     */
    public SearchFragment() {

    }

    public static SearchFragment newInstance(Bundle bundle) {
        SearchFragment f = new SearchFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable
    Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = getListView();
        Intent i = getActivity().getIntent();
        mSuffix = i.getStringExtra(Constant.SUFFFIX);
        mDirectory = i.getStringExtra(Constant.DIRECTORY);
        mTitle = "正在扫描" + mSuffix + "文件中...";
        UIHelper.showProgressDialog(getActivity(), mTitle
                , "", ProgressDialog.STYLE_SPINNER, true, 100);

        if (mDirectory != null && !mDirectory.isEmpty()) {
            final File sdcard = new File(mDirectory);
            Log.i(TAG, "sdcard=" + sdcard);
            new Thread() {
                public void run() {
                    setListAdapterByPath(sdcard);
                    mHandler.sendEmptyMessage(MSG_DISMISS_PROGRESS);
                };
            }.start();
        } else {
            final File sdcard = android.os.Environment.getExternalStorageDirectory();
            Log.i(TAG, "sdcard=" + sdcard);
            new Thread() {
                public void run() {
                    setListAdapterByPath(sdcard);
                    mHandler.sendEmptyMessage(MSG_DISMISS_PROGRESS);
                };
            }.start();
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) mFileListAdapter.getItem(position);
                Toast.makeText(getActivity(), "路径：" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    private void setListAdapterByPath(File folder) {
        File[] listFiles = folder.listFiles(FILTER);
        if (listFiles == null) {
            Log.e(TAG, "setListAdapterByPath listFiles == null");
        } else {
            for (File file : listFiles) {
                if (!file.isDirectory() && file.getName().endsWith(mSuffix)) {
                    mFilesList.add(file);
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_PROGRESS, mFilesList.size(), 0, file.getName()));
                } else if (file.isDirectory()) {
                    setListAdapterByPath(file);
                }
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        File file = (File) mFileListAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (file.isDirectory()) {
            setListAdapterByPath(file);
        } else if (file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$")) {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            startActivity(intent);
        } else {
            OpenFileHelper.tryOpenFile(getActivity(), file);
            // getResources().getStringArray(R.array.fileEndingImage);
        }
    }

    public class SearchResultAdapter extends ArrayAdapter<File> {
        private FileIOHelper mUtil;

        public SearchResultAdapter(Context context, int Resource, List<File> objects) {
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
    }
}
