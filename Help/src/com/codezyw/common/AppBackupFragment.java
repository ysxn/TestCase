
package com.codezyw.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.codezyw.common.HttpPostAsyncTask.PostListener;

public class AppBackupFragment extends BaseFragment {
    private View mRootView;
    private Button mButtonBackUp;
    private BackupAsyncTask mBackupAsyncTast;

    /**
     * 必须有无参构造函数，否则影响状态恢复
     */
    public AppBackupFragment() {
    }

    public static AppBackupFragment newInstance(Bundle bundle) {
        AppBackupFragment f = new AppBackupFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        mRootView = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mRootView.setLayoutParams(lp);
        ((LinearLayout) mRootView).setGravity(Gravity.CENTER);

        mButtonBackUp = new Button(getActivity());
        LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mButtonBackUp.setText("立即备份到/BackupApp");
        ((LinearLayout) mRootView).addView(mButtonBackUp, lpButton);

        mButtonBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackupAsyncTast != null) {
                    return;
                }
                mBackupAsyncTast = new BackupAsyncTask(getActivity(), new PostListener() {

                    @Override
                    public void onProgressUpdate(int progress) {

                    }

                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onPostExecute(String result) {
                        mBackupAsyncTast = null;
                    }

                    @Override
                    public void onCancelled(String result) {
                        mBackupAsyncTast = null;
                    }
                });
                mBackupAsyncTast.execute(true);
            }
        });
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable
    Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        UIHelper.dismissProgressDialog();
        if (mBackupAsyncTast != null) {
            mBackupAsyncTast.cancel(true);
        }
        super.onDestroy();
    }
}
