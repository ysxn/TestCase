
package com.codezyw.backuprestore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.codezyw.common.AppBackupFragment;
import com.codezyw.common.BaseFragmentActivity;
import com.codezyw.common.BaseLoginFragment;
import com.codezyw.common.BottomMenuLayout;
import com.codezyw.common.Constant;
import com.codezyw.common.CrashHelper;
import com.codezyw.common.HttpPostAsyncTask.PostListener;
import com.codezyw.common.JsonHelper;
import com.codezyw.common.SPAsync;
import com.codezyw.common.UIHelper;

public class MainActivity extends BaseFragmentActivity {
    private AppBackupFragment mAppBackupFragment;
    private BaseLoginFragment mBaseLoginFragment;
    private ExplorerFragment mFileBrowser;
    private ApkListFragment mApkBrowser;
    private BottomMenuLayout mBottomMenuLayout;
    private long mBackPressed = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mAppBackupFragment = AppBackupFragment.newInstance(savedInstanceState);
        mBaseLoginFragment = BaseLoginFragment.newInstance(savedInstanceState);
        mFileBrowser = ExplorerFragment.newInstance(savedInstanceState);
        mApkBrowser = ApkListFragment.newInstance(savedInstanceState);
        mBottomMenuLayout = (BottomMenuLayout) findViewById(R.id.bottom_menu);
        mBottomMenuLayout.setFrameId(R.id.fragment);
        mBottomMenuLayout.addMenu(0, "备份", 0, mAppBackupFragment);
        mBottomMenuLayout.addMenu(0, "搜索", 1, mApkBrowser);
        mBottomMenuLayout.addMenu(0, "查看", 2, mFileBrowser);
        mBottomMenuLayout.addMenu(0, "登录", 3, mBaseLoginFragment);
        int tab = 0;
        if (savedInstanceState != null) {
            tab = savedInstanceState.getInt(Constant.SAVE_FRAGMENT_INDEX, 0);
        }
        mBottomMenuLayout.switchFragment(tab);

        String log = SPAsync.getString(this, CrashHelper.SERVER_TAG, "");
        if (!TextUtils.isEmpty(log)) {
            CrashHelper.getInstance().uploadServer(MainActivity.this, log, new PostListener() {

                @Override
                public void onProgressUpdate(int progress) {
                    UIHelper.updateProgressDialog(100, progress);
                }

                @Override
                public void onPreExecute() {
                    UIHelper.showProgressDialog(MainActivity.this, "上传崩溃日志", "上传中......", ProgressDialog.STYLE_HORIZONTAL, false, 100);
                }

                @Override
                public void onPostExecute(String result) {
                    UIHelper.dismissProgressDialog();
                    UIHelper.showDialog(MainActivity.this, "上传崩溃日志结束", result);
                    if (JsonHelper.parseServerResult(MainActivity.this, result)) {
                        SPAsync.setString(MainActivity.this, CrashHelper.SERVER_TAG, "");
                    }
                }

                @Override
                public void onCancelled(String result) {
                    UIHelper.dismissProgressDialog();
                    UIHelper.showDialog(MainActivity.this, "上传崩溃日志被取消", result);
                    if (JsonHelper.parseServerResult(MainActivity.this, result)) {
                        SPAsync.setString(MainActivity.this, CrashHelper.SERVER_TAG, "");
                    }
                }
            });
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.SAVE_FRAGMENT_INDEX, mBottomMenuLayout.getCurrentTabId());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mBottomMenuLayout.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        long previous = mBackPressed;
        mBackPressed = SystemClock.elapsedRealtime();
        if (mBackPressed - previous < 3500) {
            finish();
        } else {
            Toast.makeText(this, "再按一下返回键退出应用.", Toast.LENGTH_SHORT).show();
        }
    }
}
