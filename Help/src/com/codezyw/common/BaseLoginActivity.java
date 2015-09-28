
package com.codezyw.common;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

public class BaseLoginActivity extends BaseFragmentActivity {
    private BaseLoginFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentView = new FrameLayout(this);
        contentView.setId(FRAMELAYOUT_ID);
        setContentView(contentView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment != null && fragment.isAdded()) {
            ft.hide(fragment);
            ft.remove(fragment);
        }
        fragment = BaseLoginFragment.newInstance(savedInstanceState);
        // ft.replace(FRAMELAYOUT_ID, fragment).commitAllowingStateLoss();
        if (!fragment.isAdded()) {
            ft.add(FRAMELAYOUT_ID, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commitAllowingStateLoss();
    }
}
