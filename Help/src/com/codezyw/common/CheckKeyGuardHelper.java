
package com.codezyw.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

//import com.android.internal.widget.LockPatternUtils;

/**
 * helper for start check password quality level Dialog and start set new
 * password UI
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class CheckKeyGuardHelper {
    @SuppressWarnings("unused")
    private boolean DEBUG = true;

    @SuppressWarnings("unused")
    private final String TAG = "CheckKeyGuardHelper";

    private Context mContext;

    // This is the minimum acceptable password quality. If the current password
    // quality is
    // lower than this, keystore should not be activated.
    public static final int MIN_PASSWORD_QUALITY = DevicePolicyManager.PASSWORD_QUALITY_SOMETHING;

    public static final String MINIMUM_QUALITY_KEY = "minimum_quality";

    public static final int CONFIRM_KEY_GUARD_REQUEST = 1;

    public CheckKeyGuardHelper(Context c) {
        mContext = c;
    }

    /**
     * Prompt for key guard configuration confirmation.
     */
    private class ConfigureKeyGuardDialog implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
        private boolean mConfigureConfirmed;

        private ConfigureKeyGuardDialog() {
            AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(android.R.string.dialog_alert_title)
                    .setMessage("Prompt for key guard configuration confirmation.").setPositiveButton(android.R.string.ok, this)
                    .setNegativeButton(android.R.string.cancel, this).create();
            dialog.setOnDismissListener(this);
            dialog.show();
        }

        @Override
        public void onClick(DialogInterface dialog, int button) {
            mConfigureConfirmed = (button == DialogInterface.BUTTON_POSITIVE);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if (mConfigureConfirmed) {
                mConfigureConfirmed = false;
                Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
                intent.putExtra(MINIMUM_QUALITY_KEY, MIN_PASSWORD_QUALITY);
                mContext.startActivity(intent);
                return;
            } else {
                ((Activity) mContext).finish();
            }
        }
    }

    /**
     * Make sure the user enters the key guard to set or change the keystore
     * password. This can be used in UNINITIALIZED to set the keystore password
     * or UNLOCKED to change the password (as is the case after unlocking with
     * an old-style password).
     */
    public void ensureKeyGuard() {
        if (!checkKeyGuardQuality()) {
            // key guard not setup, doing so will initialize keystore
            new ConfigureKeyGuardDialog();
            // will return to onResume after Activity
            return;
        }
        // force key guard confirmation
        if (confirmKeyGuard()) {
            // will return confirm result via onActivityResult
            return;
        }
    }

    /**
     * Confirm existing key guard, returning password via onActivityResult.
     */
    private boolean confirmKeyGuard() {
        boolean launched = new ChooseLockSettingsHelper((Activity) mContext).launchConfirmationActivity(CONFIRM_KEY_GUARD_REQUEST, null, null, false);
        return launched;
    }

    /**
     * Returns true if the currently set key guard matches our minimum quality
     * requirements.
     */
    private boolean checkKeyGuardQuality() {
        int quality = new LockPatternUtils(mContext).getActivePasswordQuality();
        return (quality >= MIN_PASSWORD_QUALITY);
    }
}
