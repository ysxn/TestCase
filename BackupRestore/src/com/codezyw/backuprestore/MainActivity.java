
package com.codezyw.backuprestore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.file_filter) {
            showEditDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog(final Context c) {
        View v = LayoutInflater.from(c).inflate(R.layout.rename_fingerprint, null);
        final EditText et = (EditText) v.findViewById(R.id.title);
        new AlertDialog.Builder(c).setTitle(R.string.suffix).setView(v).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String title = et.getText().toString();
                Intent i = new Intent(c, FilterBrowser.class);
                i.putExtra(Constant.SUFFFIX, title);
                i.putExtra(Constant.DIRECTORY, ""/* mDirectory */);
                c.startActivity(i);
                finish();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setCancelable(false).create().show();
    }
}
