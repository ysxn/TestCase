package com.zhuyawen.getmetrics;

import junit.framework.Test;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class GetMetricsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        Log.i("zhuyawen",dm.toString());
        Log.i("zhuyawen", ">>>>>>>>>>>>>>>>>"
                +", metrics"+this.getResources().getDisplayMetrics().toString()
                +" , screenDensity = "+this.getResources().getDisplayMetrics().densityDpi);
        Toast.makeText(this, ">>>>>>>>>>>>>>>>>"
                +", metrics"+this.getResources().getDisplayMetrics().toString()
                +" , screenDensity = "+this.getResources().getDisplayMetrics().densityDpi, Toast.LENGTH_LONG).show();
        String s = "xxx";
        Log.i("zhuyawen", "before test s="+s);
        test(s);
        Log.i("zhuyawen", "after test s="+s);
    }
    
    private void test(String c) {
        c = "asd";
    }
}