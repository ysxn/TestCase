
package com.codezyw.backuprestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.codezyw.common.BaseFragment;
import com.codezyw.common.BaseFragmentActivity;
import com.codezyw.common.Constant;

public class SearchActivity extends BaseFragmentActivity {
    private BaseFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentView = new FrameLayout(this);
        contentView.setId(FRAMELAYOUT_ID);
        setContentView(contentView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Intent i = getIntent();
        Bundle bd = new Bundle();
        bd.putString(Constant.SUFFFIX, i.getStringExtra(Constant.SUFFFIX));
        bd.putString(Constant.DIRECTORY, i.getStringExtra(Constant.DIRECTORY));

        if ("apk".equalsIgnoreCase(i.getStringExtra(Constant.SUFFFIX))) {
            fragment = ApkListFragment.newInstance(bd);
        } else {
            fragment = SearchFragment.newInstance(bd);
        }
        // ft.replace(FRAMELAYOUT_ID, fragment).commitAllowingStateLoss();
        if (!fragment.isAdded()) {
            ft.add(FRAMELAYOUT_ID, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commitAllowingStateLoss();
    }
}
