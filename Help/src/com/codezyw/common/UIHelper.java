
package com.codezyw.common;

import android.R.integer;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.FROYO)
public class UIHelper {

    private static AlertDialog mAlertDialog = null;

    private static ProgressDialog mProgressDialog = null;

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
        PopupWindow popupWindow = new PopupWindow(parent, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
