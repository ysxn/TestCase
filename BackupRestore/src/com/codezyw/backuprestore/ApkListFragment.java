
package com.codezyw.backuprestore;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codezyw.common.AppScanAsyncTask;
import com.codezyw.common.AppScanAsyncTask.ApkData;
import com.codezyw.common.BaseListFragment;
import com.codezyw.common.Constant;
import com.codezyw.common.FileHelper;
import com.codezyw.common.SlideMenuGroup;
import com.codezyw.common.SlideMenuListener;
import com.codezyw.common.UIHelper;
import com.codezyw.common.UnitHelper;

public class ApkListFragment extends BaseListFragment {
    private final String TAG = "zyw";

    private ApkListAdapter mApkListAdapter;
    private ListView mListView;
    private SlideMenuListener mSwipeTouchListener;
    private List<ApkData> mApkList = new ArrayList<ApkData>();
    private AppScanAsyncTask mAppScanAsyncTask;

    private static String mDirectory = "";
    private static final int MSG_UPDATE_DATA = 0x123;

    @SuppressWarnings("unused")
    private MyHandler mHandler = new MyHandler(getActivity()) {
        @Override
        public void handleMessage(Message msg) {
            Context c = mContext.get();
            if (c == null) {
                mHandler.removeMessages(MSG_UPDATE_DATA);
                return;
            }
            switch (msg.what) {
                case MSG_UPDATE_DATA:
                    mHandler.removeMessages(MSG_UPDATE_DATA);
                    if (mApkListAdapter != null) {
                        mApkListAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    /**
     * 必须有无参构造函数，否则影响状态恢复
     */
    public ApkListFragment() {

    }

    public static ApkListFragment newInstance(Bundle bundle) {
        ApkListFragment f = new ApkListFragment();
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
        mSwipeTouchListener = new SlideMenuListener(getActivity(), mListView);
        mListView.setOnTouchListener(mSwipeTouchListener);
        Bundle bd = getArguments();
        if (bd != null) {
            mDirectory = bd.getString(Constant.DIRECTORY);
        }
        mAppScanAsyncTask = new AppScanAsyncTask(getActivity(), new AppScanAsyncTask.PostListener() {

            @Override
            public void onProgressUpdate(int progress) {
            }

            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute(List<ApkData> result) {
                mApkList = result;
                mApkListAdapter = new ApkListAdapter(getActivity());
                mApkListAdapter.setData(mApkList);
                setListAdapter(mApkListAdapter);
                Toast.makeText(getActivity(), "已经扫描到 " + mApkList.size() + " 个APK安装包", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(List<ApkData> result) {
            }
        });

        if (!TextUtils.isEmpty(mDirectory)) {
            final File sdcard = new File(mDirectory);
            Log.i(TAG, "sdcard=" + sdcard);
            mAppScanAsyncTask.execute(sdcard);
        } else {
            final File sdcard = android.os.Environment.getExternalStorageDirectory();
            Log.i(TAG, "sdcard=" + sdcard);
            mAppScanAsyncTask.execute(sdcard);
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ApkData fileData = (ApkData) view.getTag();
                Toast.makeText(getActivity(), "签名：" + fileData.mCert, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mAppScanAsyncTask != null) {
            mAppScanAsyncTask.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ApkData fileData = (ApkData) v.getTag();
        File file = fileData.mFile;
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (file.isDirectory()) {
            // scanApkFileByPath(file);
        } else if (file.getName().matches("^.*?\\.(jpg|png|bmp|gif)$")) {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            startActivity(intent);
        } else if (file.getName().matches("^.*?\\.(swf|mp4|3gp)$")) {
            // intent.setClass(FileBrowser.this, MovieActivity.class);
            // intent.putExtra("fileName",
            // file.getAbsolutePath().replace("/mnt", ""));
            intent.setData(Uri.parse(file.getAbsolutePath()));
            startActivity(intent);
        } else if (file.getName().endsWith(".apk")) {
            FileHelper.installApk(getActivity(), file);
        }
    }

    public class ApkListAdapter extends BaseAdapter {

        private final Context mContext;
        private final LayoutInflater mInflater;
        private List<ApkData> mApkList;

        public ApkListAdapter(Context c) {
            mContext = c;
            mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<ApkData> list) {
            mApkList = list;
        }

        public int getCount() {
            if (mApkList == null) {
                return 0;
            }
            return mApkList.size();
        }

        public Object getItem(int position) {
            if (mApkList == null) {
                return 0;
            }
            return mApkList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new SlideMenuGroup(mContext);
                View listItem = (View) mInflater.inflate(R.layout.list_item, parent, false);
                // android.R.layout.simple_list_item_2, parent, false);
                ((SlideMenuGroup) convertView).setListItemView(listItem);
            }
            View listItem = ((SlideMenuGroup) convertView).getListItemView();
            TextView textView1 = (TextView) listItem.findViewById(android.R.id.text1);
            TextView textView2 = (TextView) listItem.findViewById(android.R.id.text2);
            ImageView icon = (ImageView) listItem.findViewById(R.id.icon);
            ApkData file = mApkList.get(position);
            textView1.setText(file.mFile.getName());
            StringBuilder sb = new StringBuilder();
            sb.append(file.mFile.getAbsolutePath()).append("\n");
            long b = file.mFile.length();
            sb.append("文件大小 : ").append(UnitHelper.byteToHumanNumber(b)).append("\n")
                    .append("修改时间 : ").append(FileHelper.convetTime(file.mFile.lastModified())).append("\n")
                    .append(file.mVersion);
            textView2.setText(sb.toString());
            if (file.mDrawable != null) {
                icon.setImageDrawable(file.mDrawable);
            } else {
                icon.setImageDrawable(null);
            }
            convertView.setTag(file);
            TextView menuTextView = (TextView) ((SlideMenuGroup) convertView).getRightMenu();
            menuTextView.setText("删除");
            ((SlideMenuGroup) convertView).setLeftMenuHide(true);
            ((SlideMenuGroup) convertView).setRightMenuClickListener(new SlideMenuGroup.OnRightMenuClickListener() {
                @Override
                public void onRightMenuClicked(SlideMenuGroup listItemViewGroup) {
                    ApkData fdData = (ApkData) listItemViewGroup.getTag();
                    boolean ok = fdData.mFile.delete();
                    if (ok) {
                        mApkList.remove(fdData);
                        ApkListAdapter.this.notifyDataSetChanged();
                        StringBuilder sb = new StringBuilder();
                        sb.append("成功删除安装包：")
                                .append(fdData.mFile.getName())
                                .append("\n路径：")
                                .append(fdData.mFile.getAbsolutePath());
                        UIHelper.showToast(mContext, sb.toString());
                    }
                }
            });
            return convertView;
        }
    }

    /**
     * <a href=
     * "http://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler#"
     * >static防止handler泄露</a>
     */
    static class MyHandler extends Handler {
        WeakReference<Context> mContext;

        MyHandler(Context c) {
            mContext = new WeakReference<Context>(c);
        }
    }
}
