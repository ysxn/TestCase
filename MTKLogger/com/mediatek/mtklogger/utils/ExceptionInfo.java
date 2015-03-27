package com.mediatek.mtklogger.utils;

import android.os.Build;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class ExceptionInfo
  implements Serializable
{
  private static final int DISCRIPTION_INDEX = 6;
  private static final int LEVEL_INDEX = 5;
  private static final int PRE_FILE_SIZE = 1024;
  private static final int PROCESS_INDEX = 7;
  private static final String TAG = "Syslog_taglog";
  private static final int TIME_INDEX = 8;
  private static final int TYPE_INDEX = 0;
  private static final long serialVersionUID = 1L;
  private String mBuildVersion;
  private String mDeviceName;
  private String mDiscription;
  private String mLevel;
  private String mPath;
  private String mProcess;
  private String mTime;
  private String mToolVersion = "2.0";
  private String mType;
  
  public ExceptionInfo()
  {
    setmBuildVersion(Build.DISPLAY);
    setmDeviceName(Build.DEVICE);
  }
  
  public String getmBuildVersion()
  {
    return this.mBuildVersion;
  }
  
  public String getmDeviceName()
  {
    return this.mDeviceName;
  }
  
  public String getmDiscription()
  {
    return this.mDiscription;
  }
  
  public String getmLevel()
  {
    return this.mLevel;
  }
  
  public String getmPath()
  {
    return this.mPath;
  }
  
  public String getmProcess()
  {
    return this.mProcess;
  }
  
  public String getmTime()
  {
    return this.mTime;
  }
  
  public String getmToolVersion()
  {
    return this.mToolVersion;
  }
  
  public String getmType()
  {
    return this.mType;
  }
  
  public void initFieldsFromZZ(String paramString)
    throws IOException
  {
    Log.d("Syslog_taglog", "ZZ_INTERNAL's Path:" + paramString);
    File localFile = new File(paramString);
    if (!localFile.exists()) {
      throw new IOException("ZZ_INTERNAL file is not exist!");
    }
    if (!localFile.isFile()) {
      throw new IOException("ZZ_INTERNAL file is not a file!");
    }
    FileInputStream localFileInputStream = new FileInputStream(localFile);
    byte[] arrayOfByte = new byte[1024];
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      int i = localFileInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localStringBuilder.append(new String(arrayOfByte, 0, i));
    }
    localFileInputStream.close();
    String[] arrayOfString = localStringBuilder.toString().split(",");
    if (arrayOfString.length != 10) {
      throw new IOException("fields count in ZZ_INTERNAL file are not 10");
    }
    setmType(arrayOfString[0]);
    setmLevel(arrayOfString[5]);
    setmDiscription(arrayOfString[6]);
    setmProcess(arrayOfString[7]);
    setmTime(arrayOfString[8]);
  }
  
  public void setmBuildVersion(String paramString)
  {
    this.mBuildVersion = paramString;
  }
  
  public void setmDeviceName(String paramString)
  {
    this.mDeviceName = paramString;
  }
  
  public void setmDiscription(String paramString)
  {
    this.mDiscription = paramString;
  }
  
  public void setmLevel(String paramString)
  {
    if (paramString.trim().equals("0"))
    {
      this.mLevel = "FATAL";
      return;
    }
    if (paramString.trim().equals("1"))
    {
      this.mLevel = "EXCEPTION";
      return;
    }
    Log.e("Syslog_taglog", "mLevel is not a valid value:" + paramString);
    this.mLevel = paramString;
  }
  
  public void setmPath(String paramString)
  {
    this.mPath = paramString;
  }
  
  public void setmProcess(String paramString)
  {
    this.mProcess = paramString;
  }
  
  public void setmTime(String paramString)
  {
    this.mTime = paramString;
  }
  
  public void setmToolVersion(String paramString)
  {
    this.mToolVersion = paramString;
  }
  
  public void setmType(String paramString)
  {
    this.mType = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[Device Name]: " + this.mDeviceName + "\n\n");
    localStringBuilder.append("[Build Version]: " + this.mBuildVersion + "\n\n");
    localStringBuilder.append("[Exception Level]: " + this.mLevel + "\n\n");
    localStringBuilder.append("[Exception Class]: " + this.mType + "\n\n");
    localStringBuilder.append("[Exception Type]: " + this.mDiscription + "\n\n");
    localStringBuilder.append("[Process]: " + this.mProcess + "\n\n");
    localStringBuilder.append("[Datetime]: " + this.mTime + "\n");
    return localStringBuilder.toString();
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.utils.ExceptionInfo
 * JD-Core Version:    0.7.0.1
 */