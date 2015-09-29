
package com.codezyw.backuprestore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.codezyw.common.AppBackupFragment;
import com.codezyw.common.BaseFragmentActivity;
import com.codezyw.common.BaseLoginFragment;
import com.codezyw.common.BottomMenuLayout;
import com.codezyw.common.CrashHelper;
import com.codezyw.common.SPAsync;

public class MainActivity extends BaseFragmentActivity {
    private AppBackupFragment mAppBackupFragment;
    private BaseLoginFragment mBaseLoginFragment;
    private ExplorerFragment mFileBrowser;
    private ApkListFragment mApkBrowser;
    private BottomMenuLayout mBottomMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        String log = SPAsync.getString(this, "crash", "");
        if (!TextUtils.isEmpty(log)) {
            CrashHelper.getInstance().uploadServer(MainActivity.this, log);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mBottomMenuLayout.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
