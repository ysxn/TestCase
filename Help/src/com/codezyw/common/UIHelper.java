
package com.codezyw.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.FROYO)
public class UIHelper {

    private static AlertDialog mAlertDialog = null;

    private static ProgressDialog mProgressDialog = null;

    /**
     * 拨打电话
     * @param phoneNumber
     * @return
     */
    public static Intent getDialIntent(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return dialIntent;
    }
    
    /**
     * 拨打电话
     * @param phoneNumber
     * @return
     */
    public static Intent getCallIntent(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return callIntent;
    }
    
    //标题栏隐藏
    public static void hideTitle(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    //状态栏隐藏（全屏）
    public static void hideStatusBar(Activity activity) {
        //标题栏隐藏
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得窗口对象
        Window myWindow = activity.getWindow();
        //设置Flag标示
        myWindow.setFlags(flag, flag);
    }
    
    /**
     * 消除可以scroll的view阴影效果
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
    
//    <style name="Theme_dialog_Transparent" parent="@android:Theme.Holo.Light.Dialog">
//        <item name="android:windowIsTranslucent">true</item>
//        <item name="android:windowBackground">@android:color/transparent</item>
//        <item name="android:windowContentOverlay">@null</item>
//        <item name="android:windowNoTitle">true</item>
//    </style>
    
//    <?xml version="1.0" encoding="utf-8"?>
//    <selector xmlns:android="http://schemas.android.com/apk/res/android">
//
//        <item android:state_pressed="true"><shape android:shape="rectangle">
//
//                <!-- 填充颜色 -->
//                <solid android:color="#ffefefef"></solid>
//
//                <!-- 线的宽度，颜色灰色 -->
//                <stroke android:width="1dp" android:color="#ffefefef"></stroke>
//
//                <!-- 矩形的圆角半径 -->
//                <corners 
//                    android:topLeftRadius="0dp"
//                    android:topRightRadius="0dp"
//                    android:bottomLeftRadius="5dp"
//                    android:bottomRightRadius="5dp" />
//            </shape></item>
//        <item android:state_focused="true"><shape android:shape="rectangle">
//
//                <!-- 填充颜色 -->
//                <solid android:color="#ffefefef"></solid>
//
//                <!-- 线的宽度，颜色灰色 -->
//                <stroke android:width="1dp" android:color="#ffefefef"></stroke>
//
//                <!-- 矩形的圆角半径 -->
//                <corners 
//                    android:topLeftRadius="0dp"
//                    android:topRightRadius="0dp"
//                    android:bottomLeftRadius="5dp"
//                    android:bottomRightRadius="5dp" />
//            </shape></item>
//        <item><shape android:shape="rectangle">
//
//                <!-- 填充颜色 -->
//                <solid android:color="#ffffffff"></solid>
//
//                <!-- 线的宽度，颜色灰色 -->
//                <stroke android:width="1dp" android:color="#ffffffff"></stroke>
//
//                <!-- 矩形的圆角半径 -->
//                <corners 
//                    android:topLeftRadius="0dp"
//                    android:topRightRadius="0dp"
//                    android:bottomLeftRadius="5dp"
//                    android:bottomRightRadius="5dp" />
//            </shape></item>
//
//    </selector>
}
