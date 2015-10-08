
package com.codezyw.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

public class TransparentActivity extends BaseActicity {
    public final static int DIALOG_BULLET_DOWNLOAD_OK = 11;
    public final static int DIALOG_CONFIRM_NOT_WIFI = 12;
    public final static String SAVE_PATH = "save_path";
    public final static String BUNDLE_KEY_DIALOG_ID = "dialog_id";
    public static final String BUNDLE_TITLE = Constant.BUNDLE_TITLE;
    public static final String BUNDLE_MESSAGE = Constant.BUNDLE_MESSAGE;
    private String mTitle;
    private String mMessage;

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
        switch (id) {
            case 0:

                break;

            default:
                break;
        }
        AlertDialog d = new AlertDialog.Builder(TransparentActivity.this)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                }).create();
        d.setCancelable(true);
        d.setCanceledOnTouchOutside(true);
        return d;
    }
}
