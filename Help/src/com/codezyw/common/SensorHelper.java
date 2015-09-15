
package com.codezyw.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.util.Log;

public class SensorHelper {
    private static final String TAG = "SensorHelper";
    public final static String HALL_STATE_PATH = "/sys/class/switch/hall/state";

    public static boolean getHallState() {
        int mSensorData = 0;
        FileReader file = null;
        try {
            char[] buffer = new char[1024];
            file = new FileReader(HALL_STATE_PATH);
            int len = file.read(buffer, 0, 1024);

            /**
             * FileInputStream.jva
             * <p>
             * BufferedInputStream.java
             * <p>
             * BufferedReader.java
             * <p>
             * InputStreamReader.java 这个显示buffer是乱码，最后有"\n"换行符。
             */
            // Log.i(TAG,
            // ">>>>>read state len=="+len+"  buffer={"+buffer.toString()+"}");
            Log.i(TAG, ">>>>>read state len==" + len + "  buffer={"
                    + new String(buffer, 0, len) + "}");
            mSensorData = Integer.valueOf((new String(buffer, 0, len)).trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "FileNotFoundException！ This kernel does not have hall sensor support");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mSensorData > 0 ? true : false;
    }
}
