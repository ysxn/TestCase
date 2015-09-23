
package com.codezyw.common;

import android.app.Activity;
import android.os.Bundle;

public class BaseActicity extends Activity {

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
