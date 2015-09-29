
package com.codezyw.backuprestore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.codezyw.common.BaseListFragment;
import com.codezyw.common.ColorHelper;
import com.codezyw.common.Constant;
import com.codezyw.common.FileIOHelper;
import com.codezyw.common.OpenFileHelper;
import com.codezyw.common.ShapeHelper;
import com.codezyw.common.UnitHelper;

/**
 * 文件浏览器
 */
public class ExplorerFragment extends BaseListFragment {
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

    /**
     * 必须有无参构造函数，否则影响状态恢复
     */
    public ExplorerFragment() {

    }

    public static ExplorerFragment newInstance(Bundle bundle) {
        ExplorerFragment f = new ExplorerFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable
    Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDirectory = android.os.Environment.getExternalStorageDirectory();
        Log.i(TAG, "sdcard=" + mDirectory);
        setListAdapterByPath(mDirectory);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mFileListAdapter.isRoot()) {
            setListAdapterByPath((File) mFileListAdapter.getItem(1));
            return true;
        }
        return false;
    }

    private void setListAdapterByPath(File folder) {
        mDirectory = folder;
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
                    if (fileA.isDirectory() && fileB.isFile()) {
                        return -1;
                    } else if (fileA.isFile() && fileB.isDirectory()) {
                        return 1;
                    }
                    return fileA.getName().toUpperCase(Locale.getDefault()).compareTo(fileB.getName().toUpperCase(Locale.getDefault()));
                }
            });
        }
        filesList.add(0, folder);
        if (folder.getParentFile() != null) {
            filesList.add(1, folder.getParentFile());
        }
        mFileListAdapter = new FileListAdapter(getActivity(), android.R.layout.simple_list_item_1, filesList);
        setListAdapter(mFileListAdapter);
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
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.file_filter:
                showInputDialog(getActivity(), mDirectory.getAbsolutePath());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 搜索文件后缀名
     * 
     * @param c
     * @param path
     */
    public void showInputDialog(final Context c, final String path) {
        final LinearLayout contentView = new LinearLayout(c);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(lp);
        contentView.setGravity(Gravity.CENTER);
        contentView.setPadding((int) UnitHelper.dp2px(c, 10), (int) UnitHelper.dp2px(c, 20), (int) UnitHelper.dp2px(c, 10), (int) UnitHelper.dp2px(c, 20));

        final EditText input = new EditText(c);
        input.setSingleLine(true);
        LinearLayout.LayoutParams lpEditText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        contentView.addView(input, lpEditText);
        final AlertDialog alertDialog = new AlertDialog.Builder(c).setTitle("搜索文件后缀名:").setView(contentView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String suffix = input.getText().toString().trim();
                        if (!TextUtils.isEmpty(suffix)) {
                            Intent i = new Intent(c, SearchActivity.class);
                            i.putExtra(Constant.SUFFFIX, suffix);
                            i.putExtra(Constant.DIRECTORY, path);
                            c.startActivity(i);
                        }
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public class FileListAdapter extends ArrayAdapter<File> {
        private FileIOHelper mUtil;

        public FileListAdapter(Context context, int Resource, List<File> objects) {
            super(context, Resource, objects);
            mUtil = new FileIOHelper(context);
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
                view.setTextColor(ColorHelper.WHITE);
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
            }
            return view;
        }

        public boolean isRoot() {
            return getItem(0).getParent() == null;
        }
    }
}
