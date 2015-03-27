package com.mediatek.mtklogger.taglog;

import android.util.SparseArray;
import java.io.File;
import java.text.DecimalFormat;

public class LogInformation
{
  private static final SparseArray<Long> LOG_COMPRESS_RATIO_CHANGE = new SparseArray();
  private static final SparseArray<Double> LOG_COMPRESS_RATIO_MAX;
  private static final SparseArray<Double> LOG_COMPRESS_RATIO_MIN;
  private File mLogFile;
  private int mLogFilesCount;
  private long mLogSize;
  private int mLogType;
  private long mSaveSpace;
  private long mTagLogSize;
  
  static
  {
    LOG_COMPRESS_RATIO_CHANGE.put(2, Long.valueOf(10485760L));
    LOG_COMPRESS_RATIO_CHANGE.put(1, Long.valueOf(10485760L));
    LOG_COMPRESS_RATIO_CHANGE.put(4, Long.valueOf(52428800L));
    LOG_COMPRESS_RATIO_MAX = new SparseArray();
    LOG_COMPRESS_RATIO_MAX.put(2, Double.valueOf(0.8D));
    LOG_COMPRESS_RATIO_MAX.put(1, Double.valueOf(0.8D));
    LOG_COMPRESS_RATIO_MAX.put(4, Double.valueOf(0.8D));
    LOG_COMPRESS_RATIO_MIN = new SparseArray();
    LOG_COMPRESS_RATIO_MIN.put(2, Double.valueOf(0.3D));
    LOG_COMPRESS_RATIO_MIN.put(1, Double.valueOf(0.2D));
    LOG_COMPRESS_RATIO_MIN.put(4, Double.valueOf(0.3D));
  }
  
  public LogInformation(int paramInt, File paramFile)
  {
    this.mLogType = paramInt;
    this.mLogFile = paramFile;
    this.mLogFilesCount = TagLogUtils.getFolderFilesCount(paramFile.getAbsolutePath());
    this.mLogSize = TagLogUtils.getFolderOrFileSize(paramFile.getAbsolutePath());
    this.mTagLogSize = calculateTagLogSize();
    this.mSaveSpace = (this.mLogSize - this.mTagLogSize);
  }
  
  private long calculateTagLogSize()
  {
    if (this.mLogSize <= ((Long)LOG_COMPRESS_RATIO_CHANGE.get(this.mLogType)).longValue()) {
      return Long.parseLong(new DecimalFormat("0").format(this.mLogSize * ((Double)LOG_COMPRESS_RATIO_MAX.get(this.mLogType)).doubleValue()));
    }
    return Long.parseLong(new DecimalFormat("0").format(this.mLogSize * ((Double)LOG_COMPRESS_RATIO_MIN.get(this.mLogType)).doubleValue()));
  }
  
  public File getLogFile()
  {
    return this.mLogFile;
  }
  
  public int getLogFilesCount()
  {
    return this.mLogFilesCount;
  }
  
  public long getLogSize()
  {
    return this.mLogSize;
  }
  
  public int getLogType()
  {
    return this.mLogType;
  }
  
  public long getSaveSpace()
  {
    return this.mSaveSpace;
  }
  
  public long getTagLogSize()
  {
    return this.mTagLogSize;
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.taglog.LogInformation
 * JD-Core Version:    0.7.0.1
 */