
package com.codezyw.backuprestore;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.codezyw.common.BackupAppFragment;
import com.codezyw.common.BaseFragmentActivity;
import com.codezyw.common.BaseLoginFragment;
import com.codezyw.common.BottomMenuLayout;
import com.codezyw.common.CrashHelper;
import com.codezyw.common.SPAsync;

public class MainActivity extends BaseFragmentActivity {
    private BackupAppFragment mBackupAppFragment;
    private BaseLoginFragment mBaseLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        BottomMenuLayout menu = (BottomMenuLayout) findViewById(R.id.bottom_menu);
        menu.addMenu(0, "备份");
        menu.addMenu(0, "搜索");
        menu.addMenu(0, "查看");
        menu.addMenu(0, "登录");
        menu.setMenuOnClickListener(3, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (mBaseLoginFragment != null && mBaseLoginFragment.isAdded()) {
                    ft.hide(mBaseLoginFragment);
                    ft.remove(mBaseLoginFragment);
                } else {
                    mBaseLoginFragment = BaseLoginFragment.newInstance(null);
                }
                if (!mBaseLoginFragment.isAdded()) {
                    ft.add(R.id.fragment, mBaseLoginFragment);
                } else {
                    ft.show(mBaseLoginFragment);
                }
                ft.commitAllowingStateLoss();
            }
        });
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mBackupAppFragment != null && mBackupAppFragment.isAdded()) {
            ft.hide(mBackupAppFragment);
            ft.remove(mBackupAppFragment);
        }
        mBackupAppFragment = BackupAppFragment.newInstance(savedInstanceState);
        if (!mBackupAppFragment.isAdded()) {
            ft.add(R.id.fragment, mBackupAppFragment);
        } else {
            ft.show(mBackupAppFragment);
        }
        ft.commitAllowingStateLoss();
        final String log = SPAsync.getString(this, "crash", "");
        if (!TextUtils.isEmpty(log)) {
            CrashHelper.getInstance().uploadServer(MainActivity.this, log);
        }
    }
}
