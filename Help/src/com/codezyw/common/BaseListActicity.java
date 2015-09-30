
package com.codezyw.common;


import android.app.ListActivity;
import android.os.Bundle;

public class BaseListActicity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitHelper.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitHelper.getInstance().delActivity(this);
    }
}
