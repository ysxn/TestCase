package com.mediatek.mtklogger.exceptionreporter;

import android.content.Context;
import android.content.Intent;
import com.mediatek.mtklogger.utils.Utils;

public class ExceptionReportManager
{
  private static final String TAG = "MTKLogger/MockExceptionReporterManager";
  private static ExceptionReportManager instance = null;
  
  private ExceptionReportManager(Context paramContext)
  {
    Utils.logd("MTKLogger/MockExceptionReporterManager", "<init>, empty implementation");
  }
  
  public static ExceptionReportManager getInstance(Context paramContext)
  {
    if (instance == null) {
      instance = new ExceptionReportManager(paramContext);
    }
    return instance;
  }
  
  public static boolean runExceptionHistory(Context paramContext)
  {
    return false;
  }
  
  public boolean beginException(Intent paramIntent)
  {
    Utils.logw("MTKLogger/MockExceptionReporterManager", "-->beginException(), this should not happened at any time. Attentions!");
    return false;
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.exceptionreporter.ExceptionReportManager
 * JD-Core Version:    0.7.0.1
 */