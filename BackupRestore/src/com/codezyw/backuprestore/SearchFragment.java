
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.codezyw.common.FileHelper;
import com.codezyw.common.FileScanAsyncTask;
import com.codezyw.common.UIHelper;
import com.codezyw.common.UnitHelper;

public class SearchFragment extends BaseListFragment {
    @SuppressWarnings("unused")
    private final String TAG = "zyw";
    private static String mSuffix = "";
    private static String mDirectory = "";

    @SuppressWarnings("unused")
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
    private FileScanAsyncTask mFileScanAsyncTask;

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
        Bundle bd = getArguments();
        if (bd != null) {
            mDirectory = bd.getString(Constant.DIRECTORY);
            mSuffix = bd.getString(Constant.SUFFFIX);
        }
        mFileScanAsyncTask = new FileScanAsyncTask(getActivity(), new FileScanAsyncTask.PostListener() {

            @Override
            public void onProgressUpdate(int progress) {
            }

            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute(List<File> result) {
                mFilesList = result;
                mFileListAdapter = new SearchResultAdapter(getActivity(), android.R.layout.simple_list_item_1, mFilesList);
                setListAdapter(mFileListAdapter);
            }

            @Override
            public void onCancelled(List<File> result) {
            }
        });

        if (!TextUtils.isEmpty(mDirectory) && !TextUtils.isEmpty(mSuffix)) {
            mFileScanAsyncTask.execute(mDirectory, mSuffix);
        } else {
            UIHelper.showToast(getActivity(), "参数错误： mDirectory=" + mDirectory + ",mSuffix=" + mSuffix);
            getActivity().finish();
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

    @Override
    public void onDestroy() {
        if (mFileScanAsyncTask != null) {
            mFileScanAsyncTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        File file = (File) mFileListAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$")) {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            startActivity(intent);
        } else {
            FileHelper.tryOpenFile(getActivity(), file);
            // getResources().getStringArray(R.array.fileEndingImage);
        }
    }

    public class SearchResultAdapter extends ArrayAdapter<File> {

        public SearchResultAdapter(Context context, int Resource, List<File> objects) {
            super(context, Resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            File file = getItem(position);
            StringBuilder sb = new StringBuilder();
            sb.append(file.getName()).append("\n");
            if (file.isDirectory()) {
                sb.append("修改时间 : ").append(FileHelper.convetTime(file.lastModified()));
                view.setText(sb.toString());
            } else {
                long b = file.length();
                sb.append("文件大小 : ").append(UnitHelper.byteToHumanNumber(b)).append("\n").append("修改时间 : ").append(FileHelper.convetTime(file.lastModified()));
                view.setText(sb.toString());
            }
            return view;
        }
    }
}
