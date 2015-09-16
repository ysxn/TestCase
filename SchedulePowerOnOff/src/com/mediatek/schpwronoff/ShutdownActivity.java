/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek Software")
 * have been modified by MediaTek Inc. All revisions are subject to any receiver's
 * applicable license agreements with MediaTek Inc.
 */

package com.mediatek.schpwronoff;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.view.ContextThemeWrapper;
public class ShutdownActivity extends Activity {
    private static final String TAG = "ShutdownActivity";
    public static CountDownTimer sCountDownTimer = null;
    private String mMessage;
    private int mSecondsCountdown;
    private TelephonyManager mTelephonyManager;
    private static final int DIALOG = 1;
    /**Lenovo-sw linrk1 modify beign 2014-12-02 TENMA-1925**/
    private boolean mCallRinging = false ;
     /**Lenovo-sw linrk1 modify end 2014-12-02 TENMA-1925**/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SchPwrWakeLock.acquireCpuWakeLock(this);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        Log.d(TAG, "screen is on ? ----- " + pm.isScreenOn());

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        final int countSeconds = 11;
        final int millisSeconds = 1000;
        if (savedInstanceState == null) {
            mSecondsCountdown = countSeconds;
        } else {
            mSecondsCountdown = savedInstanceState.getInt("lefttime");
            mMessage = savedInstanceState.getString("message");
        }
        sCountDownTimer = new CountDownTimer(mSecondsCountdown * millisSeconds, millisSeconds) {
            @Override
            public void onTick(long millisUntilFinished) {
                mSecondsCountdown = (int) (millisUntilFinished / millisSeconds);
                 /**Lenovo-sw linrk1 modify beign 2014-12-02 TENMA-1925**/
                  if(mTelephonyManager.getCallState() ==TelephonyManager.CALL_STATE_RINGING){
                       mCallRinging = true ;
                        finish();
                        return ;
                    }
                   /**Lenovo-sw linrk1 modify end 2014-12-02 TENMA-1925**/
                if (mSecondsCountdown > 1) { 
                    mMessage = getString(R.string.schpwr_shutdown_message, mSecondsCountdown);
                } else {
                    mMessage = getString(R.string.schpwr_shutdown_message_second, mSecondsCountdown);
                }
                Log.d(TAG, "showDialog time = " + millisUntilFinished / millisSeconds);
                showDialog(DIALOG);
            }

            @Override
            public void onFinish() {
                 /**Lenovo-sw linrk1 modify beign 2014-12-02 TENMA-1925**/
                if (mCallRinging || mTelephonyManager.getCallState() != TelephonyManager.CALL_STATE_IDLE) {
                    Log.d(TAG, "phone is incall, countdown end");
                    isInCall();
                 /**Lenovo-sw linrk1 modify end 2014-12-02 TENMA-1925**/
                    SchPwrWakeLock.releaseCpuWakeLock();
                    finish();
                } else {
                    Log.d(TAG, "count down timer arrived, shutdown phone");
                    /**Lenovo-sw linrk1 modify begin 2014-08-22 FRIESIAN-5315**/
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    /**Lenovo-sw linrk1 modify end 2014-08-22  FRIESIAN-5315**/
                    fireShutDown();
                    sCountDownTimer = null;
                }
            }
        };

        Log.d(TAG, "ShutdonwActivity onCreate");
        if (sCountDownTimer == null) {
            SchPwrWakeLock.releaseCpuWakeLock();
            finish();
        } else {
            sCountDownTimer.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lefttime", mSecondsCountdown);
        outState.putString("message", mMessage);
    }

    private void cancelCountDownTimer() {
        if (sCountDownTimer != null) {
            Log.d(TAG, "cancel sCountDownTimer");
            sCountDownTimer.cancel();
            sCountDownTimer = null;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Log.d(TAG, "onCreateDialog");
        AlertDialog dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setCancelable(false).setIcon(0)
                .setTitle(R.string.power_off).setMessage(mMessage)
                .setPositiveButton(com.android.internal.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelCountDownTimer();
                        fireShutDown();
                    }
                }).setNegativeButton(com.android.internal.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelCountDownTimer();
                        SchPwrWakeLock.releaseCpuWakeLock();
                        finish();
                    }
                }).create();
        if (!getResources().getBoolean(com.android.internal.R.bool.config_sf_slowBlur)) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }
        // Window win = dialog.getWindow();
        // WindowManager.LayoutParams winParams = win.getAttributes();
        // winParams.flags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
        // win.setAttributes(winParams);
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        ((AlertDialog) dialog).setMessage(mMessage);
    }

    private void fireShutDown() {
        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
     /**Lenovo-sw linrk1 modify beign 2014-12-02 TENMA-1925**/
    private void isInCall(){
               NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
               Notification notification = new Notification();
               notification.icon = R.drawable.ic_settings_schpwroff;
               notification.tickerText = this.getString(R.string.schedule_power_off_time_out_summary);
               notification.defaults=Notification.DEFAULT_SOUND;
               notification.audioStreamType= android.media.AudioManager.ADJUST_LOWER;
               notification.flags |= Notification.FLAG_AUTO_CANCEL;
               Intent isCallIntent = new Intent(this,AlarmClock.class);
               PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, isCallIntent,0);
               notification.setLatestEventInfo(this, 
               this.getString(R.string.schedule_power_off_time_out_title),this.getString(R.string.schedule_power_off_time_out_summary), pendingIntent);
               manager.notify(1, notification);
     }
      /**Lenovo-sw linrk1 modify end 2014-12-02 TENMA-1925**/
   
}
