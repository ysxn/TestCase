/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codezyw.common;

//import com.android.internal.widget.LockPatternUtils;

import android.app.Activity;
import android.app.Fragment;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;

/**
 * helper for start confirm UI
 */
public final class ChooseLockSettingsHelper {
    @SuppressWarnings("unused")
    private boolean DEBUG = true;

    @SuppressWarnings("unused")
    private final String TAG = "ChooseLockSettingsHelper";

    static final String EXTRA_KEY_TYPE = "type";

    static final String EXTRA_KEY_PASSWORD = "password";

    /**
     * Names of {@link CharSequence} fields within the originating
     * {@link Intent} that are used to configure the keyguard confirmation
     * view's labeling. The view will use the system-defined resource strings
     * for any labels that the caller does not supply.
     */
    public static final String PACKAGE = "com.android.settings";

    public static final String HEADER_TEXT = PACKAGE + ".ConfirmLockPattern.header";

    public static final String FOOTER_TEXT = PACKAGE + ".ConfirmLockPattern.footer";

    public static final String HEADER_WRONG_TEXT = PACKAGE + ".ConfirmLockPattern.header_wrong";

    public static final String FOOTER_WRONG_TEXT = PACKAGE + ".ConfirmLockPattern.footer_wrong";

    private LockPatternUtils mLockPatternUtils;

    private Activity mActivity;

    private Fragment mFragment;

    public ChooseLockSettingsHelper(Activity activity) {
        mActivity = activity;
        mLockPatternUtils = new LockPatternUtils(activity);
    }

    public ChooseLockSettingsHelper(Activity activity, Fragment fragment) {
        this(activity);
        mFragment = fragment;
    }

    public LockPatternUtils utils() {
        return mLockPatternUtils;
    }

    /**
     * If a pattern, password or PIN exists, prompt the user before allowing
     * them to change it.
     * 
     * @param message optional message to display about the action about to be
     *            done
     * @param details optional detail message to display
     * @return true if one exists and we launched an activity to confirm it
     * @see #onActivityResult(int, int, android.content.Intent)
     */
    public boolean launchConfirmationActivity(int request, CharSequence message, CharSequence details) {
        return launchConfirmationActivity(request, message, details, false);
    }

    /**
     * If a pattern, password or PIN exists, prompt the user before allowing
     * them to change it.
     * 
     * @param message optional message to display about the action about to be
     *            done
     * @param details optional detail message to display
     * @param returnCredentials if true, put credentials into intent. Note that
     *            if this is true, this can only be called internally.
     * @return true if one exists and we launched an activity to confirm it
     * @see #onActivityResult(int, int, android.content.Intent)
     */
    public boolean launchConfirmationActivity(int request, CharSequence message, CharSequence details, boolean returnCredentials) {
        boolean launched = false;
        switch (mLockPatternUtils.getKeyguardStoredPasswordQuality()) {
            case DevicePolicyManager.PASSWORD_QUALITY_SOMETHING:
                launched = confirmPattern(request, message, details, returnCredentials);
                break;
            case DevicePolicyManager.PASSWORD_QUALITY_NUMERIC:
                // case DevicePolicyManager.PASSWORD_QUALITY_NUMERIC_COMPLEX:
            case DevicePolicyManager.PASSWORD_QUALITY_ALPHABETIC:
            case DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC:
            case DevicePolicyManager.PASSWORD_QUALITY_COMPLEX:
                // TODO: update UI layout for ConfirmPassword to show message
                // and details
                launched = confirmPassword(request, message, returnCredentials);
                break;
        }
        return launched;
    }

    /**
     * Launch screen to confirm the existing lock pattern.
     * 
     * @param message shown in header of ConfirmLockPattern if not null
     * @param details shown in footer of ConfirmLockPattern if not null
     * @param returnCredentials if true, put credentials into intent.
     * @see #onActivityResult(int, int, android.content.Intent)
     * @return true if we launched an activity to confirm pattern
     */
    private boolean confirmPattern(int request, CharSequence message, CharSequence details, boolean returnCredentials) {
        if (!mLockPatternUtils.isLockPatternEnabled() || !mLockPatternUtils.savedPatternExists()) {
            return false;
        }
        final Intent intent = new Intent();
        // supply header and footer text in the intent
        intent.putExtra(HEADER_TEXT, message);
        intent.putExtra(FOOTER_TEXT, details);
        intent.setClassName("com.android.settings", "com.android.settings.ConfirmLockPattern");
        try {
            if (mFragment != null) {
                mFragment.startActivityForResult(intent, request);
            } else {
                mActivity.startActivityForResult(intent, request);
            }
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Launch screen to confirm the existing lock password.
     * 
     * @param message shown in header of ConfirmLockPassword if not null
     * @param returnCredentials if true, put credentials into intent.
     * @see #onActivityResult(int, int, android.content.Intent)
     * @return true if we launched an activity to confirm password
     */
    private boolean confirmPassword(int request, CharSequence message, boolean returnCredentials) {
        if (!mLockPatternUtils.isLockPasswordEnabled())
            return false;
        final Intent intent = new Intent();
        // supply header text in the intent
        intent.putExtra(HEADER_TEXT, message);
        intent.setClassName("com.android.settings", "com.android.settings.ConfirmLockPassword");
        try {
            if (mFragment != null) {
                mFragment.startActivityForResult(intent, request);
            } else {
                mActivity.startActivityForResult(intent, request);
            }
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
