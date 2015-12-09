
package com.codezyw.freeparking;

import com.baidu.mapapi.SDKInitializer;
import com.codezyw.common.CrashApplication;

public class MyApplication extends CrashApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }

}
