
package com.codezyw.common;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Build;

/**
 * hack to com.android.internal.widget.LockPatternUtils
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class LockPatternUtils {
    @SuppressWarnings("unused")
    private boolean DEBUG = true;

    @SuppressWarnings("unused")
    private final String TAG = "LockPatternUtils";

    private Context mContext;

    public LockPatternUtils(Context c) {
        mContext = c;
    }

    public int getActivePasswordQuality() {

        String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";
        try {
            Class<?> lockPatternUtilsClass = Class.forName(LOCK_PATTERN_UTILS);
            Object lockPatternUtils = lockPatternUtilsClass.getConstructor(Context.class).newInstance(mContext);
            Method method = lockPatternUtilsClass.getMethod("getActivePasswordQuality");
            int lockProtectionLevel = Integer.valueOf(String.valueOf(method.invoke(lockPatternUtils)));
            return lockProtectionLevel;
            // Then check if lockProtectionLevel ==
            // DevicePolicyManager.TheConstantForWhicheverLevelOfProtectionYouWantToEnforce,
            // and return true if the check passes, false if it fails
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED;
    }

    public int getKeyguardStoredPasswordQuality() {
        String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";

        try {
            Class<?> lockPatternUtilsClass = Class.forName(LOCK_PATTERN_UTILS);
            Object lockPatternUtils = lockPatternUtilsClass.getConstructor(Context.class).newInstance(mContext);
            Method method = lockPatternUtilsClass.getMethod("getKeyguardStoredPasswordQuality");
            int lockProtectionLevel = Integer.valueOf(String.valueOf(method.invoke(lockPatternUtils)));
            return lockProtectionLevel;
            // Then check if lockProtectionLevel ==
            // DevicePolicyManager.TheConstantForWhicheverLevelOfProtectionYouWantToEnforce,
            // and return true if the check passes, false if it fails
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED;
    }

    public boolean isLockPatternEnabled() {
        String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";

        try {
            Class<?> lockPatternUtilsClass = Class.forName(LOCK_PATTERN_UTILS);
            Object lockPatternUtils = lockPatternUtilsClass.getConstructor(Context.class).newInstance(mContext);
            Method method = lockPatternUtilsClass.getMethod("isLockPatternEnabled");
            Boolean isLockPatternEnabled = Boolean.valueOf(String.valueOf(method.invoke(lockPatternUtils)));
            return isLockPatternEnabled;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean savedPatternExists() {
        String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";

        try {
            Class<?> lockPatternUtilsClass = Class.forName(LOCK_PATTERN_UTILS);
            Object lockPatternUtils = lockPatternUtilsClass.getConstructor(Context.class).newInstance(mContext);
            Method method = lockPatternUtilsClass.getMethod("savedPatternExists");
            Boolean savedPatternExists = Boolean.valueOf(String.valueOf(method.invoke(lockPatternUtils)));
            return savedPatternExists;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean isLockPasswordEnabled() {
        String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";

        try {
            Class<?> lockPatternUtilsClass = Class.forName(LOCK_PATTERN_UTILS);
            Object lockPatternUtils = lockPatternUtilsClass.getConstructor(Context.class).newInstance(mContext);
            Method method = lockPatternUtilsClass.getMethod("isLockPasswordEnabled");
            Boolean isLockPasswordEnabled = Boolean.valueOf(String.valueOf(method.invoke(lockPatternUtils)));
            return isLockPasswordEnabled;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
