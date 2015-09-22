
package com.codezyw.backuprestore;

import java.io.File;
import com.codezyw.common.ThreadPoolHelper.ThreadCallBackData;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

public class FileData extends ThreadCallBackData {
    public int mInstalled = Constant.NONE;
    public File mFile;
    public Drawable mDrawable;
    public PackageInfo mPi;
    public ApplicationInfo mAi;
    public String mVersion = "";
    public String mCert = "";
}
