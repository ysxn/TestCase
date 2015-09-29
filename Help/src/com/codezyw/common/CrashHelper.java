
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
import android.app.ProgressDialog;
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
@TargetApi(Build.VERSION_CODES.DONUT)
public class CrashHelper implements UncaughtExceptionHandler {
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
    public void uploadServer(final Activity context, final String exception) {
        final String httpUrl = HttpHelper.UPDATE_URL;
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(JsonHelper.ACCOUNT, DbHelper.getInstance(context).getString(JsonHelper.ACCOUNT, "")));
        postParams.add(new BasicNameValuePair(JsonHelper.PASSWORD, DbHelper.getInstance(context).getString(JsonHelper.PASSWORD, "")));
        postParams.add(new BasicNameValuePair(JsonHelper.TITLE, "Crash log"));
        postParams.add(new BasicNameValuePair(JsonHelper.CONTENT, exception));
        postParams.add(new BasicNameValuePair(JsonHelper.NOTE_ID, Integer.toString(9)));
        HttpHelper.asyncHttpPost(httpUrl, context, postParams, new PostListener() {

            @Override
            public void onProgressUpdate(int progress) {
                UIHelper.updateProgressDialog(100, progress);
            }

            @Override
            public void onPreExecute() {
                UIHelper.showProgressDialog(context, "上传崩溃日志", "上传中......", ProgressDialog.STYLE_HORIZONTAL, false, 100);
            }

            @Override
            public void onPostExecute(String result) {
                UIHelper.dismissProgressDialog();
                UIHelper.showDialog(context, "上传崩溃日志结束", result);
            }

            @Override
            public void onCancelled(String result) {
                UIHelper.dismissProgressDialog();
                UIHelper.showDialog(context, "上传崩溃日志被取消", result);
            }
        });
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
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);
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

            try {
                fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".log";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(exception.getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SPAsync.setString(mContext, "crash", exception);
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
