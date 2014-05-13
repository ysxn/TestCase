
package com.zhuyawen.wallpaper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FileExplorer extends ListActivity implements OnClickListener {
    private String TAG = "FileExplorer";

    private boolean DEBUG = false;

    private String mCurrentDir = "/";

    private File mCurrentFile = null;

    private ListView mListView = null;

    private TextView mClickTextView = null;

    private TextView mCurrenTextView = null;

    private ArrayList<String> mTitle = new ArrayList<String>();

    private ArrayList<String> mSize = new ArrayList<String>();

    private ArrayList<String> mTime = new ArrayList<String>();

    private ArrayList<String> mPath = new ArrayList<String>();

    private ArrayList<Boolean> mIsDir = new ArrayList<Boolean>();

    private ArrayList<Boolean> mCanRead = new ArrayList<Boolean>();

    private Date mDate = new Date();

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView = getListView();
        mListView.setBackgroundColor(Color.BLACK);
        mListView.setAdapter(new MyListAdapter(this));

        mCurrenTextView = (TextView) findViewById(R.id.currentdir);
        mClickTextView = (TextView) findViewById(R.id.click);
        mClickTextView.setOnClickListener(this);
        mCurrenTextView.setText("当前目录 : " + mCurrentDir);
        mClickTextView.setText("点击返回上一层目录");
        mClickTextView.setBackgroundColor(Color.BLUE);

        listCurrentDir();

        if (DEBUG)
            Log.i(TAG, "onCreate mTitle.size()=" + mTitle.size());

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private Context mContext;

        public MyListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            if (DEBUG)
                Log.i(TAG, "mTitle.size()=" + mTitle.size());
            return mTitle.size();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;// !mStrings[position].startsWith("-");
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView title;
            TextView size;
            TextView time;
            ImageView image;

            if (DEBUG)
                Log.i(TAG, "position=" + position);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listitem, parent, false);
            }
            title = (TextView) convertView.findViewById(R.id.title);
            size = (TextView) convertView.findViewById(R.id.size);
            time = (TextView) convertView.findViewById(R.id.time);
            image = (ImageView) convertView.findViewById(R.id.album);
            title.setText(mTitle.get(position));
            size.setText("大小: " + mSize.get(position));
            time.setText("日期: " + mTime.get(position));
            if (mIsDir.get(position)) {
                image.setImageResource(R.drawable.ic_menu_archive);
            } else {
                image.setImageResource(R.drawable.ic_menu_compose);
            }
            if (DEBUG)
                Log.i(TAG, "convertView == " + convertView.hashCode());

            return convertView;
        }

    }

    private void listCurrentDir() {
        mTitle.clear();
        mPath.clear();
        mIsDir.clear();
        mSize.clear();
        mTime.clear();
        mCanRead.clear();
        mCurrenTextView.setText("当前目录 : " + mCurrentDir);
        mCurrentFile = new File(mCurrentDir);
        if (mCurrentFile.getParentFile() != null) {
            mClickTextView.setClickable(true);
        } else {
            mClickTextView.setClickable(false);
        }
        if (mCurrentFile.isDirectory()) {
            File[] files = mCurrentFile.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    mTitle.add(file.getName());
                    mSize.add(String.valueOf(file.length() / 1024) + " Kb");
                    mTime.add(getTimeFromMS(file.lastModified()));
                    mPath.add(file.getPath());
                    mIsDir.add(false);
                    mCanRead.add(file.canRead());
                    if (DEBUG)
                        Log.i(TAG,
                                "onCreate file name=" + file.getName() + ", file path="
                                        + file.getPath() + " , file.canRead=" + file.canRead()
                                        + " , file.isFile=" + file.isFile()
                                        + " , file.isDirectory=" + file.isDirectory());
                } else if (file.isDirectory()) {
                    mTitle.add(file.getName());
                    mSize.add("   ");
                    mTime.add(getTimeFromMS(file.lastModified()));
                    mPath.add(file.getPath());
                    mIsDir.add(true);
                    mCanRead.add(file.canRead());
                    if (DEBUG)
                        Log.i(TAG,
                                "onCreate dir name=" + file.getName() + ", file path="
                                        + file.getPath() + " , file.canRead=" + file.canRead()
                                        + " , file.isFile=" + file.isFile()
                                        + " , file.isDirectory=" + file.isDirectory());
                }

            }
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        if (DEBUG)
            Log.i(TAG,
                    ">>>>>>>>>position=" + position + ";id=" + id + " , address="
                            + mPath.get(position));
        if (mIsDir.get(position) && mCanRead.get(position)) {
            mCurrentDir = mPath.get(position);
            listCurrentDir();
            ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
        } else if (!mIsDir.get(position) && mCanRead.get(position)) {
            setResult(RESULT_OK,
                    new Intent().putExtra(Defined.INTENT_EXTRA_KEY_FILENAME, mPath.get(position)));
            finish();
        }
    }

    private String getTimeFromMS(long milliseconds) {
        mDate.setTime(milliseconds);
        return mSimpleDateFormat.format(mDate);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.click) {
            if (DEBUG)
                Log.i(TAG, ">>>>>>>>..click");
            mCurrentDir = mCurrentFile.getParentFile().getPath();
            listCurrentDir();
            ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
        }
    }

}
