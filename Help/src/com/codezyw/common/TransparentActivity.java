
package com.codezyw.common;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.FrameLayout;

public class TransparentActivity extends BaseActicity {
    public final static int DIALOG_BULLET_DOWNLOAD_OK = 0x1001;
    public final static int DIALOG_CONFIRM_NOT_WIFI = 0x1002;
    public final static String BUNDLE_SAVE_PATH = "save_path";
    public final static String BUNDLE_DIALOG_ID = "dialog_id";
    public static final String BUNDLE_TITLE = Constant.BUNDLE_TITLE;
    public static final String BUNDLE_MESSAGE = Constant.BUNDLE_MESSAGE;
    private String mTitle;
    private String mMessage;
    private int mType;
    private String mSavePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        FrameLayout content = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(lp);
        content.setBackgroundColor(Color.TRANSPARENT);
        setContentView(content);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        Bundle bd = intent.getExtras();
        if (bd == null) {
            finish();
            return;
        }
        mTitle = bd.getString(BUNDLE_TITLE);
        mMessage = bd.getString(BUNDLE_MESSAGE);
        mType = bd.getInt(BUNDLE_DIALOG_ID);
        mSavePath = bd.getString(BUNDLE_SAVE_PATH);
        if (mTitle == null) {
            mTitle = "";
        }
        if (mMessage == null) {
            mMessage = "";
        }
        if (savedInstanceState == null) {
            showDialog(0);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        String mPositive = getString(android.R.string.ok);
        String mNegative = getString(android.R.string.cancel);
        if (mType == DIALOG_BULLET_DOWNLOAD_OK) {
            mPositive = "立即安装";
            mMessage = mMessage + " 可以安装新版本上海免费停车";
        }
        AlertDialog d = new AlertDialog.Builder(TransparentActivity.this)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(mPositive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        handle(true);
                    }
                })
                .setNegativeButton(mNegative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        handle(false);
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        handle(false);
                    }
                }).create();
        d.setCancelable(true);
        d.setCanceledOnTouchOutside(true);
        return d;
    }

    private void handle(boolean confirm) {
        if (confirm) {
            if (mType == DIALOG_BULLET_DOWNLOAD_OK && !TextUtils.isEmpty(mSavePath)) {
                File file = new File(mSavePath);
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }
        finish();
    }
}
