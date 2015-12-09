
package com.codezyw.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.codezyw.common.HttpPostAsyncTask.PostListener;

/**
 * 自定义系统的Crash捕捉类，用Toast替换系统的对话框 将软件版本信息，设备信息，出错信息保存在sd卡中，你可以上传到服务器中
 * 
 * @author xiaanming <a
 *         href="http://blog.csdn.net/xiaanming/article/details/9344703"
 *         >http://blog.csdn.net/xiaanming/article/details/9344703</a>
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CrashHelper implements UncaughtExceptionHandler {
    /**
     * 保存错误日志到SharedPreferences
     */
    public static final String SERVER_TAG = "crash";
    private static final String TAG = "Activity";
    private Context mContext;
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
    /**
     * 静态代码块跟静态变量都是类加载时进行初始化的（同等条件下，初始化顺序由书写顺序决定
     */
    private static CrashHelper mInstance = new CrashHelper();

    private CrashHelper() {
    }

    /**
     * 单例模式，保证只有一个CustomCrashHandler实例存在
     * 
     * @return
     */
    public static CrashHelper getInstance() {
        return mInstance;
    }

    /**
     * 异常发生时，系统回调的函数，我们在这里处理一些操作
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 将一些信息保存到SDcard中
        savaInfoToSD(mContext, ex);

        // 提示用户程序即将退出
        showToast(mContext, "很抱歉，程序遭遇异常，即将退出！");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // android.os.Process.killProcess(android.os.Process.myPid());
        // System.exit(1);

        // 完美退出程序方法
        ExitHelper.getInstance().exit();
    }

    /**
     * 为我们的应用程序设置自定义Crash处理
     */
    public void setCustomCrashHanler(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 显示提示信息，需要在线程中显示Toast
     * 
     * @param context
     * @param msg
     */
    private void showToast(final Context context, final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }

    /**
     * 上传错误log
     */
    public void uploadServer(final Activity context, final String exception, final PostListener postListener) {
        final String httpUrl = HttpHelper.CRASH_REPORT_URL;
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(JsonHelper.TITLE, "Crash log-" + TimeHelper.getTime(System.currentTimeMillis())));
        postParams.add(new BasicNameValuePair(JsonHelper.CONTENT, exception));
        // HttpHelper.asyncSSLHttpPost(httpUrl, context, postParams,
        // postListener);
        HttpHelper.asyncHttpPost(httpUrl, context, postParams, postListener);
    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * 
     * @param context
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (mPackageInfo != null) {
            map.put("versionName", mPackageInfo.versionName);
            map.put("versionCode", "" + mPackageInfo.versionCode);
            map.put("firstInstallTime", "" + paserTime(mPackageInfo.firstInstallTime));
            map.put("lastUpdateTime", "" + paserTime(mPackageInfo.lastUpdateTime));
            map.put("BOARD", "" + Build.BOARD);
            map.put("BRAND", "" + Build.BRAND);
            map.put("CPU_ABI", "" + Build.CPU_ABI);
            map.put("CPU_ABI2", "" + Build.CPU_ABI2);
            map.put("DEVICE", "" + Build.DEVICE);
            map.put("DISPLAY", "" + Build.DISPLAY);
            map.put("FINGERPRINT", "" + Build.FINGERPRINT);
            map.put("HARDWARE", "" + Build.HARDWARE);
            map.put("HOST", "" + Build.HOST);
            map.put("ID", "" + Build.ID);
            map.put("MANUFACTURER", "" + Build.MANUFACTURER);
            map.put("MODEL", "" + Build.MODEL);
            map.put("PRODUCT", "" + Build.PRODUCT);
            map.put("TAGS", "" + Build.TAGS);
            map.put("TYPE", "" + Build.TYPE);
            map.put("USER", "" + Build.USER);
            map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
            map.put("IMEI", "" + DeviceHelper.getInstance().getIMEI());
            map.put("IMSI", "" + DeviceHelper.getInstance().getIMSI());
            map.put("Serial", "" + DeviceHelper.getSerialNumberBuild());
        }
        return map;
    }

    /**
     * 获取系统未捕捉的错误信息
     * 
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();
        Log.e(TAG, mStringWriter.toString());
        return mStringWriter.toString();
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     * 
     * @param context
     * @param ex
     * @return
     */
    private String savaInfoToSD(Context context, Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        sb.append(obtainExceptionInfo(ex));
        String exception = sb.toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(SDCARD_ROOT + File.separator + "crash" + File.separator);
            if (!dir.exists()) {
                dir.mkdir();
            }
            FileOutputStream fos = null;
            try {
                fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".log";
                fos = new FileOutputStream(fileName);
                fos.write(exception.getBytes());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SPAsync.setString(mContext, CrashHelper.SERVER_TAG, exception);
        return fileName;
    }

    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
     * 
     * @param milliseconds
     * @return
     */
    private String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));
        return times;
    }
}
