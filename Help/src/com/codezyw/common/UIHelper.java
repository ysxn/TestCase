
package com.codezyw.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class UIHelper {

    private static AlertDialog mAlertDialog = null;

    private static ProgressDialog mProgressDialog = null;

    /**
     * 拨打电话
     * 
     * @param phoneNumber
     * @return
     */
    public static Intent getDialIntent(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return dialIntent;
    }

    /**
     * 拨打电话
     * 
     * @param phoneNumber
     * @return
     */
    public static Intent getCallIntent(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return callIntent;
    }

    // 标题栏隐藏
    public static void hideTitle(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    // 状态栏隐藏（全屏）
    public static void hideStatusBar(Activity activity) {
        // 标题栏隐藏
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        // 获得窗口对象
        Window myWindow = activity.getWindow();
        // 设置Flag标示
        myWindow.setFlags(flag, flag);
    }

    /**
     * 消除可以scroll的view阴影效果
     * 
     * @param view
     */
    public static void disableScrollShadow(View view) {
        view.setOverScrollMode(View.OVER_SCROLL_NEVER);
        view.setHorizontalFadingEdgeEnabled(false);
        view.setVerticalFadingEdgeEnabled(false);
    }

    public static void showDialog(Context context, String title, String message) {
        if (context == null) {
            return;
        }
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        mAlertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        // android.permission.SYSTEM_ALERT_WINDOW
        // 允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
        if (context instanceof Activity) {
        } else {
            mAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        mAlertDialog.setCancelable(false);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    public static void showProgressDialog(Context context, String title, String message, int style, boolean isIndeterminate, int max) {
        if (context == null) {
            return;
        }
        dismissProgressDialog();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(style);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(isIndeterminate);
        mProgressDialog.setMax(max);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        // android.permission.SYSTEM_ALERT_WINDOW
        // 允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
        if (context instanceof Activity) {
        } else {
            mProgressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        mProgressDialog.show();
    }

    public static void showAlertDialog(Context context, String title, String message, int style, boolean isIndeterminate, int max) {
        if (context == null) {
            return;
        }
        AlertDialog mAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_DeviceDefault_Dialog))
                .setView(new TextView(context)).create();
        mAlertDialog.setMessage("MESSAGE");
        mAlertDialog.setTitle("TITLE");
        mAlertDialog.setCancelable(true);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        // android.permission.SYSTEM_ALERT_WINDOW
        // 允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层
        if (context instanceof Activity) {
        } else {
            mAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        mAlertDialog.show();
    }

    public static void updateProgressDialog(int progress) {
        if (mProgressDialog != null && mProgressDialog.isShowing() && !mProgressDialog.isIndeterminate()) {
            mProgressDialog.setProgress(progress);
        }
    }

    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public static void showToast(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showPopup(Activity context, String title, String message) {
        if (context == null || context.isFinishing() || context.getWindow() == null || context.getWindow().getDecorView() == null) {
            return;
        }
        LayoutInflater factory = LayoutInflater.from(context);
        LinearLayout parent = new LinearLayout(context);
        parent.setOrientation(LinearLayout.VERTICAL);
        TextView titleTextView = new TextView(context);
        TextView messageTextView = new TextView(context);
        titleTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        titleTextView.setText(title);
        messageTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        messageTextView.setText(message);
        parent.addView(titleTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.addView(messageTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final PopupWindow popupWindow = new PopupWindow(parent, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    public static int measureTextHeight(Paint paint) {
        return (int) (paint.descent() - paint.ascent());
    }

    public static int measureTextWidth(Paint paint, String s) {
        return (int) paint.measureText(s);
    }

    /**
     * 解开键盘锁
     * 
     * @param mKeyguardLock
     */
    public static void disableKeyguardLock(KeyguardLock mKeyguardLock) {
        mKeyguardLock.disableKeyguard();
    }

    /**
     * 恢复屏幕锁定
     * 
     * @param mKeyguardLock
     */
    public static void enableKeyguardLock(KeyguardLock mKeyguardLock) {
        mKeyguardLock.reenableKeyguard();
    }

    /**
     * 初始化键盘锁，可以锁定或解开键盘锁
     * 
     * @param context
     * @return
     */
    public static KeyguardLock getKeyguardLock(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock mKeyguardLock = mKeyguardManager.newKeyguardLock("zhuyawen");
        // 判断键盘锁定返回值mKeyguardManager.isKeyguardLocked和disableKeyguard的调用没有关系
        return mKeyguardLock;
    }

    /**
     * 监听屏幕亮屏和灭屏
     */
    public static BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            }
        }
    };

    /**
     * 监听屏幕亮屏和灭屏
     */
    public static void registerScreenEvent(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        context.registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * 解除监听屏幕亮屏和灭屏
     */
    public static void unregisterScreenEvent(Context context) {
        context.unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * WindowManager添加新的view来控制亮屏和解锁
     * 
     * @param context
     */
    public static void unLockScreen(Context context) {
        // 亮屏
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View mGlobalView = new LinearLayout(context);
        WindowManager.LayoutParams mGlobalParams = new WindowManager.LayoutParams();
        mGlobalParams.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mGlobalView.setVisibility(View.GONE);
        mWindowManager.addView(mGlobalView, mGlobalParams);
        mGlobalView.setVisibility(View.VISIBLE);
        mWindowManager.updateViewLayout(mGlobalView, mGlobalParams);
        // mPowerManager.wakeUp(SystemClock.uptimeMillis());
    }

    /**
     * 灭屏
     * 
     * @param context
     * @param mGlobalView
     */
    public static void LockScreen(Context context, View mGlobalView) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mGlobalView.setVisibility(View.GONE);
        mWindowManager.removeView(mGlobalView);
        WindowManager.LayoutParams mGlobalParams = (LayoutParams) mGlobalView.getLayoutParams();
        mWindowManager.updateViewLayout(mGlobalView, mGlobalParams);
        // mPowerManager.goToSleep(SystemClock.uptimeMillis());
    }

    /**
     * <pre>
     *    &lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
     *    &lt;selector xmlns:android=&quot;http://schemas.android.com/apk/res/android&quot;&gt;
     * 
     *        &lt;item android:state_pressed=&quot;true&quot;&gt;&lt;shape android:shape=&quot;rectangle&quot;&gt;
     * 
     *                &lt;!-- 填充颜色 --&gt;
     *                &lt;solid android:color=&quot;#ffefefef&quot;&gt;&lt;/solid&gt;
     * 
     *                &lt;!-- 线的宽度，颜色灰色 --&gt;
     *                &lt;stroke android:width=&quot;1dp&quot; android:color=&quot;#ffefefef&quot;&gt;&lt;/stroke&gt;
     * 
     *                &lt;!-- 矩形的圆角半径 --&gt;
     *                &lt;corners 
     *                    android:topLeftRadius=&quot;0dp&quot;
     *                    android:topRightRadius=&quot;0dp&quot;
     *                    android:bottomLeftRadius=&quot;5dp&quot;
     *                    android:bottomRightRadius=&quot;5dp&quot; /&gt;
     *            &lt;/shape&gt;&lt;/item&gt;
     *        &lt;item android:state_focused=&quot;true&quot;&gt;&lt;shape android:shape=&quot;rectangle&quot;&gt;
     * 
     *                &lt;!-- 填充颜色 --&gt;
     *                &lt;solid android:color=&quot;#ffefefef&quot;&gt;&lt;/solid&gt;
     * 
     *                &lt;!-- 线的宽度，颜色灰色 --&gt;
     *                &lt;stroke android:width=&quot;1dp&quot; android:color=&quot;#ffefefef&quot;&gt;&lt;/stroke&gt;
     * 
     *                &lt;!-- 矩形的圆角半径 --&gt;
     *                &lt;corners 
     *                    android:topLeftRadius=&quot;0dp&quot;
     *                    android:topRightRadius=&quot;0dp&quot;
     *                    android:bottomLeftRadius=&quot;5dp&quot;
     *                    android:bottomRightRadius=&quot;5dp&quot; /&gt;
     *            &lt;/shape&gt;&lt;/item&gt;
     *        &lt;item&gt;&lt;shape android:shape=&quot;rectangle&quot;&gt;
     * 
     *                &lt;!-- 填充颜色 --&gt;
     *                &lt;solid android:color=&quot;#ffffffff&quot;&gt;&lt;/solid&gt;
     * 
     *                &lt;!-- 线的宽度，颜色灰色 --&gt;
     *                &lt;stroke android:width=&quot;1dp&quot; android:color=&quot;#ffffffff&quot;&gt;&lt;/stroke&gt;
     * 
     *                &lt;!-- 矩形的圆角半径 --&gt;
     *                &lt;corners 
     *                    android:topLeftRadius=&quot;0dp&quot;
     *                    android:topRightRadius=&quot;0dp&quot;
     *                    android:bottomLeftRadius=&quot;5dp&quot;
     *                    android:bottomRightRadius=&quot;5dp&quot; /&gt;
     *            &lt;/shape&gt;&lt;/item&gt;
     * 
     * &lt;/selector&gt;
     * </pre>
     */
    public static void getXmlShape() {

    }

    /**
     * <pre>
     *    &lt;style name=&quot;Theme_dialog_Transparent&quot; parent=&quot;@android:Theme.Holo.Light.Dialog&quot;&gt;
     *        &lt;item name=&quot;android:windowIsTranslucent&quot;&gt;true&lt;/item&gt;
     *        &lt;item name=&quot;android:windowBackground&quot;&gt;@android:color/transparent&lt;/item&gt;
     *        &lt;item name=&quot;android:windowContentOverlay&quot;&gt;@null&lt;/item&gt;
     *        &lt;item name=&quot;android:windowNoTitle&quot;&gt;true&lt;/item&gt;
     *    &lt;/style&gt;
     * </pre>
     */
    public static void getXmlDialogStyle() {

    }

    /**
     * 获取屏幕窗口显示属性
     * 
     * @param activity
     * @see DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.i("zyw", ">>>" + metrics.toString() + ", densityDpi=" + metrics.densityDpi);
        Toast.makeText(activity, ">>>" + metrics.toString() + ", densityDpi=" + metrics.densityDpi, Toast.LENGTH_LONG).show();
        return metrics;
    }

    /**
     * 获取屏幕窗口宽度
     * 
     * @param activity
     * @see DisplayMetrics
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕窗口高度
     * 
     * @param activity
     * @see DisplayMetrics
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取触摸位置原始坐标x
     */
    public static int getMotionEventX(MotionEvent event) {
        return (int) event.getRawX();
    }

    /**
     * 获取触摸位置原始坐标y，此时的坐标没有相对系统状态栏高度调整过，会比实际位置偏下
     */
    public static int getMotionEventY(MotionEvent event) {
        return (int) event.getRawY();
    }

    /**
     * 获取触摸位置坐标x
     */
    public static int getMotionEventAdjustX(MotionEvent event) {
        return (int) event.getX();
    }

    /**
     * 获取触摸位置坐标y，此时的坐标相对系统状态栏高度调整过
     */
    public static int getMotionEventAdjustY(MotionEvent event) {
        return (int) event.getY();
    }
}
