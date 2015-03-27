package com.mediatek.mtklogger.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MDLoggerClearLog
{
  private static final String LOG_FOLDER_BEGIN = "MDLog";
  private static final String TAG = "MTKLogger/MDLoggerClearLog";
  private static MDLoggerClearLog mInstance = null;
  private ArrayList<String> eeFolderArray = new ArrayList();
  private ArrayList<String> folderTreeListArray = new ArrayList();
  private ArrayList<String> mASTL1Array = new ArrayList();
  private ArrayList<String> mDAKArray = new ArrayList();
  private ArrayList<String> mDMDSPMLTArray = new ArrayList();
  private ArrayList<String> mL1Array = new ArrayList();
  private long mLogNeedToBeClearSize;
  private ArrayList<String> mMD2GMLTArray = new ArrayList();
  private String mMdlogPath;
  private ArrayList<String> mNormalContainBin = new ArrayList();
  private ArrayList<String> mPSArray = new ArrayList();
  private ArrayList<String> mRunningArray = new ArrayList();
  private int mTotalLogSizeLimit;
  private ArrayList<String> normalFolderArray = new ArrayList();
  private ArrayList<String> otherFolderArray = new ArrayList();
  private long otherFolderSize;
  private String sd_mdlog_root = null;
  private String strStartWithString = null;
  
  private long calculateFolderSize(File paramFile)
  {
    long l = paramFile.length();
    if (paramFile.isDirectory())
    {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile != null)
      {
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++) {
          l += getFileSize(arrayOfFile[j]);
        }
      }
    }
    return l;
  }
  
  private boolean checkAndRemoveRunningFolder()
  {
    int i = 1;
    int j = 4;
    if (this.mPSArray.size() > 3)
    {
      i++;
      j += this.mPSArray.size();
    }
    if (this.mL1Array.size() > 3)
    {
      i++;
      j += this.mL1Array.size();
    }
    if (this.mDAKArray.size() > 3)
    {
      i++;
      j += this.mDAKArray.size();
    }
    if (this.mDMDSPMLTArray.size() > 3)
    {
      i++;
      j += this.mDMDSPMLTArray.size();
    }
    if (this.mMD2GMLTArray.size() > 3)
    {
      i++;
      j += this.mMD2GMLTArray.size();
    }
    if (this.mASTL1Array.size() > 3)
    {
      i++;
      j += this.mASTL1Array.size();
    }
    Utils.logd("MTKLogger/MDLoggerClearLog", "nTotalFile =  " + j + " nCount =  " + i);
    int k = j / i;
    if (removeLogByProtectFileNum(k)) {}
    while ((k > 4) && (removeLogByProtectFileNum(3))) {
      return true;
    }
    return false;
  }
  
  private boolean checkDeleteSizeIfEnough()
  {
    if ((this.otherFolderSize > 10485760L) && (this.otherFolderSize / 1024L / 1024L >= this.mLogNeedToBeClearSize))
    {
      Utils.logi("MTKLogger/MDLoggerClearLog", "Clean Running folder in mdlog is " + this.otherFolderSize);
      Utils.logi("MTKLogger/MDLoggerClearLog", "Array size after RemoveRunning Folder ");
      Utils.logd("MTKLogger/MDLoggerClearLog", "mASTL1Array size: " + this.mASTL1Array.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mDAKArray size: " + this.mDAKArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mDMDSPMLTArray size: " + this.mDMDSPMLTArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mMD2GMLTArray size: " + this.mMD2GMLTArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mPSArray size: " + this.mPSArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mL1Array size: " + this.mL1Array.size());
      return true;
    }
    return false;
  }
  
  private boolean checkIfCanDeleteNormalFolder(String paramString)
  {
    int i = this.folderTreeListArray.size();
    if (this.folderTreeListArray.contains(paramString))
    {
      if (((String)this.folderTreeListArray.get(i - 1)).equalsIgnoreCase(paramString))
      {
        if (!this.mRunningArray.contains(paramString))
        {
          this.mRunningArray.add(paramString);
          Utils.logi("MTKLogger/MDLoggerClearLog", "Find running path: " + paramString);
        }
        return false;
      }
      return true;
    }
    return true;
  }
  
  private boolean checkIfClearSDLog(String paramString)
  {
    this.otherFolderArray.clear();
    this.eeFolderArray.clear();
    this.normalFolderArray.clear();
    this.mRunningArray.clear();
    this.mNormalContainBin.clear();
    File localFile;
    try
    {
      this.mMdlogPath = (this.sd_mdlog_root + File.separator + "mtklog" + File.separator + paramString);
      localFile = new File(this.mMdlogPath);
      if (!localFile.exists())
      {
        Utils.loge("MTKLogger/MDLoggerClearLog", "The mdlog folder doesn't exist");
        return false;
      }
      if (!localFile.canExecute())
      {
        Utils.loge("MTKLogger/MDLoggerClearLog", "The mdlog folder can not execute.");
        return false;
      }
    }
    catch (Exception localException)
    {
      Utils.loge("MTKLogger/MDLoggerClearLog", "Exception: Failed to get the SD card status");
      localException.printStackTrace();
      Utils.loge("MTKLogger/MDLoggerClearLog", "The SD Card is not available");
      return false;
    }
    this.strStartWithString = localFile.getAbsolutePath();
    this.strStartWithString = (this.strStartWithString + File.separator + "MDLog");
    long l = calculateFolderSize(localFile) / 1024L / 1024L;
    Utils.logd("MTKLogger/MDLoggerClearLog", "Limit size is: " + this.mTotalLogSizeLimit + "M. " + this.mMdlogPath + " mdlog foler block size is " + l + "M");
    this.mLogNeedToBeClearSize = (l - this.mTotalLogSizeLimit);
    if (l < -10 + this.mTotalLogSizeLimit)
    {
      Utils.logd("MTKLogger/MDLoggerClearLog", "The Mdlogger need not to clear log.");
      return false;
    }
    Utils.logd("MTKLogger/MDLoggerClearLog", "The Mdlogger need clear log if less 10M below limitation.");
    return true;
  }
  
  private long checkOtherFolderInMDFolder(File paramFile)
  {
    long l = paramFile.length();
    if (!paramFile.isDirectory()) {
      return 0L;
    }
    for (File localFile : paramFile.listFiles()) {
      if (localFile.isDirectory())
      {
        l += clearLogs(localFile);
        localFile.delete();
        Utils.logd("MTKLogger/MDLoggerClearLog", "Clear file: " + localFile.getAbsolutePath());
      }
    }
    return l;
  }
  
  private boolean checkRemoveFileOneByOne()
  {
    int i = 1;
    int j = 4;
    if (this.mPSArray.size() > 3)
    {
      i++;
      j += this.mPSArray.size();
    }
    if (this.mL1Array.size() > 3)
    {
      i++;
      j += this.mL1Array.size();
    }
    if (this.mDAKArray.size() > 3)
    {
      i++;
      j += this.mDAKArray.size();
    }
    if (this.mDMDSPMLTArray.size() > 3)
    {
      i++;
      j += this.mDMDSPMLTArray.size();
    }
    if (this.mMD2GMLTArray.size() > 3)
    {
      i++;
      j += this.mMD2GMLTArray.size();
    }
    if (this.mASTL1Array.size() > 3)
    {
      i++;
      j += this.mASTL1Array.size();
    }
    Utils.logd("MTKLogger/MDLoggerClearLog", "nTotalFile =  " + j + " nCount =  " + i);
    int k = j / i;
    if (removeLogByProtectFileNum(k)) {
      return true;
    }
    do
    {
      int m = k;
      k = m - 1;
      if (m <= 0) {
        break;
      }
    } while (!removeLogByProtectFileNum(k));
    return true;
    return false;
  }
  
  private long clearLogs(File paramFile)
  {
    long l = paramFile.length();
    if (!paramFile.isDirectory())
    {
      paramFile.delete();
      Utils.logd("MTKLogger/MDLoggerClearLog", "Clear file: " + paramFile.getAbsolutePath());
      return l;
    }
    File[] arrayOfFile = paramFile.listFiles();
    int i = arrayOfFile.length;
    int j = 0;
    if (j < i)
    {
      File localFile = arrayOfFile[j];
      if (localFile.isFile())
      {
        l += localFile.length();
        localFile.delete();
      }
      for (;;)
      {
        Utils.logd("MTKLogger/MDLoggerClearLog", "Clear file: " + localFile.getAbsolutePath());
        j++;
        break;
        if (localFile.isDirectory())
        {
          l += clearLogs(localFile);
          localFile.delete();
        }
      }
    }
    paramFile.delete();
    Utils.logd("MTKLogger/MDLoggerClearLog", "Clear file: " + paramFile.getAbsolutePath());
    return l;
  }
  
  private long clearNormalLogWithConfirm(File paramFile)
  {
    long l = paramFile.length();
    if (!paramFile.isDirectory())
    {
      paramFile.delete();
      Utils.logd("MTKLogger/MDLoggerClearLog", "confirm clear !isDirectory file: " + paramFile.getAbsolutePath());
      return l;
    }
    File[] arrayOfFile = paramFile.listFiles();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = arrayOfFile.length;
    int k = 0;
    if (k < j)
    {
      File localFile2 = arrayOfFile[k];
      if (localFile2.isFile()) {
        if (localFile2.getName().endsWith(".bin"))
        {
          i = 1;
          Utils.logi("MTKLogger/MDLoggerClearLog", "Find bin: " + localFile2.getAbsolutePath());
          if (!this.mNormalContainBin.contains(paramFile.getAbsolutePath())) {
            this.mNormalContainBin.add(paramFile.getAbsolutePath());
          }
        }
      }
      for (;;)
      {
        k++;
        break;
        if (localFile2.getName().startsWith("BPLGUInfo"))
        {
          localArrayList.add(localFile2.getAbsolutePath());
        }
        else
        {
          l += localFile2.length();
          localFile2.delete();
          Utils.logd("MTKLogger/MDLoggerClearLog", "confirm clear file: " + localFile2.getAbsolutePath());
          continue;
          if (localFile2.isDirectory())
          {
            l += clearLogs(localFile2);
            localFile2.delete();
            Utils.logd("MTKLogger/MDLoggerClearLog", "confirm clear file: " + localFile2.getAbsolutePath());
          }
        }
      }
    }
    if (i == 0)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        File localFile1 = new File((String)localIterator.next());
        l += localFile1.length();
        localFile1.delete();
      }
      Utils.logd("MTKLogger/MDLoggerClearLog", "confirm clear file: " + paramFile.getAbsolutePath());
      paramFile.delete();
    }
    return l;
  }
  
  private long clearNormalLogs(File paramFile)
  {
    long l = paramFile.length();
    if (!paramFile.isDirectory())
    {
      paramFile.delete();
      Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Normal file: " + paramFile.getAbsolutePath());
      return l;
    }
    File[] arrayOfFile = paramFile.listFiles();
    int i = arrayOfFile.length;
    int k;
    for (int j = 0;; j++)
    {
      k = 0;
      int m = 0;
      File localFile2;
      if (j < i)
      {
        localFile2 = arrayOfFile[j];
        if (!localFile2.isFile()) {
          break label255;
        }
        if (!localFile2.getName().endsWith(".bin")) {
          break label155;
        }
        k = 1;
        boolean bool2 = this.mNormalContainBin.contains(paramFile.getAbsolutePath());
        m = 0;
        if (!bool2) {
          this.mNormalContainBin.add(paramFile.getAbsolutePath());
        }
      }
      for (;;)
      {
        if ((m == 0) && (k == 0)) {
          break label313;
        }
        return l - paramFile.length();
        label155:
        if (!localFile2.getName().endsWith(".dmp.dmp")) {
          break;
        }
        m = 1;
        String str = paramFile.getAbsolutePath();
        boolean bool1 = this.mRunningArray.contains(str);
        k = 0;
        if (!bool1)
        {
          this.mRunningArray.add(str);
          Utils.logi("MTKLogger/MDLoggerClearLog", "Arraysize= " + this.mRunningArray.size() + " Find running path: " + str);
          k = 0;
        }
      }
      label255:
      if (localFile2.isDirectory())
      {
        l += clearLogs(localFile2);
        localFile2.delete();
        Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Normal file: " + localFile2.getAbsolutePath());
      }
    }
    label313:
    if (k == 0)
    {
      int n = arrayOfFile.length;
      int i1 = 0;
      if (i1 < n)
      {
        File localFile1 = arrayOfFile[i1];
        if (localFile1.isFile())
        {
          l += localFile1.length();
          localFile1.delete();
          Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Normal file: " + localFile1.getAbsolutePath());
        }
        for (;;)
        {
          i1++;
          break;
          if (localFile1.isDirectory())
          {
            l += clearLogs(localFile1);
            localFile1.delete();
            Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Normal file: " + localFile1.getAbsolutePath());
          }
        }
      }
      paramFile.delete();
      Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Normal file: " + paramFile.getAbsolutePath());
    }
    return l;
  }
  
  private long getFileSize(File paramFile)
  {
    long l = paramFile.length();
    if (paramFile.isDirectory())
    {
      saveDirToArray(paramFile);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramFile);
      while (!localArrayList.isEmpty())
      {
        File[] arrayOfFile = ((File)localArrayList.get(0)).listFiles();
        if (arrayOfFile != null)
        {
          int i = arrayOfFile.length;
          for (int j = 0; j < i; j++)
          {
            File localFile = arrayOfFile[j];
            if (localFile.isDirectory())
            {
              saveDirToArray(localFile);
              localArrayList.add(localFile);
            }
            l += localFile.length();
          }
        }
        localArrayList.remove(0);
      }
    }
    return l;
  }
  
  public static MDLoggerClearLog getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new MDLoggerClearLog();
      }
      MDLoggerClearLog localMDLoggerClearLog = mInstance;
      return localMDLoggerClearLog;
    }
    finally {}
  }
  
  private boolean readRootFolderTreeList(String paramString, boolean paramBoolean)
  {
    String str1 = paramString + File.separator + "file_tree.txt";
    Utils.logi("MTKLogger/MDLoggerClearLog", "tree file path: " + str1);
    File localFile = new File(str1);
    if (paramBoolean) {
      this.folderTreeListArray.clear();
    }
    for (;;)
    {
      try
      {
        localBufferedReader = new BufferedReader(new FileReader(localFile));
        try
        {
          String str2 = localBufferedReader.readLine();
          if (str2 == null) {
            continue;
          }
          str3 = str2.replace("\r", "").replaceAll(".dmp.dmp", ".dmp").replaceAll(".bin.bin", ".bin");
          if (str3.length() <= 0) {
            continue;
          }
          if (!paramBoolean) {
            continue;
          }
          if (this.folderTreeListArray.contains(str3)) {
            continue;
          }
          this.folderTreeListArray.add(str3);
          continue;
          localIOException1.printStackTrace();
        }
        catch (IOException localIOException1) {}
      }
      catch (IOException localIOException2)
      {
        BufferedReader localBufferedReader;
        String str3;
        continue;
      }
      return false;
      this.mPSArray.clear();
      this.mL1Array.clear();
      this.mDAKArray.clear();
      this.mDMDSPMLTArray.clear();
      this.mMD2GMLTArray.clear();
      this.mASTL1Array.clear();
      continue;
      if (str3.contains(".dmp")) {
        if (str3.contains("MDLog_PS")) {
          this.mPSArray.add(str3);
        } else if (str3.contains("MDLog_DAK")) {
          this.mDAKArray.add(str3);
        } else if (str3.contains("MDLog_L1")) {
          this.mL1Array.add(str3);
        } else if (str3.contains("MDLog_ASTL1")) {
          this.mASTL1Array.add(str3);
        } else if (str3.contains("MDLog_MD2GMLT")) {
          this.mMD2GMLTArray.add(str3);
        } else if (str3.contains("MDLog_DMDSPMLT")) {
          this.mDMDSPMLTArray.add(str3);
        }
      }
    }
    localBufferedReader.close();
    if (paramBoolean) {
      Utils.logd("MTKLogger/MDLoggerClearLog", "Top Tree File: " + this.folderTreeListArray.size());
    }
    for (;;)
    {
      return true;
      Utils.logd("MTKLogger/MDLoggerClearLog", "mASTL1Array size: " + this.mASTL1Array.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mPSArray size: " + this.mPSArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mL1Array size: " + this.mL1Array.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mDAKArray size: " + this.mDAKArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mDMDSPMLTArray size: " + this.mDMDSPMLTArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mMD2GMLTArray size: " + this.mMD2GMLTArray.size());
    }
  }
  
  private boolean removeLogByProtectFileNum(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    int i = 1;
    if (paramInt == 0) {
      i = 0;
    }
    int j = this.mL1Array.size();
    int k = 0;
    String str2;
    if (j > paramInt)
    {
      str2 = (String)this.mL1Array.get(i);
      File localFile6 = new File(str2);
      k = 1;
      if (localFile6.exists())
      {
        this.otherFolderSize += localFile6.length();
        localFile6.delete();
        Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Running file: " + localFile6.getAbsolutePath());
        if (!checkDeleteSizeIfEnough()) {
          break label149;
        }
      }
    }
    label121:
    String str1;
    label149:
    do
    {
      do
      {
        return true;
        Utils.loge("MTKLogger/MDLoggerClearLog", "File Not exist: " + str2);
        this.mL1Array.remove(i);
        if (this.mDAKArray.size() <= paramInt) {
          break label265;
        }
        File localFile1 = new File((String)this.mDAKArray.get(i));
        k = 1;
        if (!localFile1.exists()) {
          break;
        }
        this.otherFolderSize += localFile1.length();
        localFile1.delete();
        Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Running file: " + localFile1.getAbsolutePath());
      } while (checkDeleteSizeIfEnough());
      this.mDAKArray.remove(i);
      if (this.mPSArray.size() <= paramInt) {
        break;
      }
      str1 = (String)this.mPSArray.get(i);
      File localFile5 = new File(str1);
      if (!localFile5.exists()) {
        break label899;
      }
      this.otherFolderSize += localFile5.length();
      localFile5.delete();
      Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Running file: " + localFile5.getAbsolutePath());
    } while (checkDeleteSizeIfEnough());
    for (;;)
    {
      label265:
      k = 1;
      this.mPSArray.remove(i);
      if (this.mDMDSPMLTArray.size() > paramInt)
      {
        File localFile2 = new File((String)this.mDMDSPMLTArray.get(i));
        k = 1;
        if (localFile2.exists())
        {
          this.otherFolderSize += localFile2.length();
          localFile2.delete();
          Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Running file: " + localFile2.getAbsolutePath());
          if (checkDeleteSizeIfEnough()) {
            break label121;
          }
        }
        this.mDMDSPMLTArray.remove(i);
      }
      if (this.mMD2GMLTArray.size() > paramInt)
      {
        File localFile3 = new File((String)this.mMD2GMLTArray.get(i));
        k = 1;
        if (localFile3.exists())
        {
          this.otherFolderSize += localFile3.length();
          localFile3.delete();
          Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Running file: " + localFile3.getAbsolutePath());
          if (checkDeleteSizeIfEnough()) {
            break label121;
          }
        }
        this.mMD2GMLTArray.remove(i);
      }
      if (this.mASTL1Array.size() > paramInt)
      {
        File localFile4 = new File((String)this.mASTL1Array.get(i));
        k = 1;
        if (localFile4.exists())
        {
          this.otherFolderSize += localFile4.length();
          localFile4.delete();
          Utils.logd("MTKLogger/MDLoggerClearLog", "Clear Running file: " + localFile4.getAbsolutePath());
          if (checkDeleteSizeIfEnough()) {
            break label121;
          }
        }
        this.mASTL1Array.remove(i);
      }
      if (k != 0) {
        break;
      }
      Utils.logi("MTKLogger/MDLoggerClearLog", "No more log find in running folder");
      Utils.logi("MTKLogger/MDLoggerClearLog", "Array size after RemoveRunning Folder ");
      Utils.logd("MTKLogger/MDLoggerClearLog", "mASTL1Array size: " + this.mASTL1Array.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mDAKArray size: " + this.mDAKArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mDMDSPMLTArray size: " + this.mDMDSPMLTArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mMD2GMLTArray size: " + this.mMD2GMLTArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mPSArray size: " + this.mPSArray.size());
      Utils.logd("MTKLogger/MDLoggerClearLog", "mL1Array size: " + this.mL1Array.size());
      return false;
      label899:
      Utils.loge("MTKLogger/MDLoggerClearLog", "File Not exist: " + str1);
    }
  }
  
  private void saveDirToArray(File paramFile)
  {
    if (!paramFile.isDirectory()) {
      return;
    }
    String str = paramFile.getAbsolutePath();
    if (str.startsWith(this.strStartWithString))
    {
      if (str.contains("EE"))
      {
        this.eeFolderArray.add(str);
        return;
      }
      this.normalFolderArray.add(str);
      return;
    }
    this.otherFolderArray.add(str);
  }
  
  public void checkAndClearLog(int paramInt, String paramString1, String paramString2)
  {
    this.mTotalLogSizeLimit = paramInt;
    this.sd_mdlog_root = paramString1;
    if (!checkIfClearSDLog(paramString2)) {}
    do
    {
      return;
      Iterator localIterator6;
      while (!localIterator6.hasNext())
      {
        Utils.logd("MTKLogger/MDLoggerClearLog", "other folder =" + this.otherFolderArray.size());
        Utils.logd("MTKLogger/MDLoggerClearLog", "normal Folder =" + this.normalFolderArray.size());
        Utils.logd("MTKLogger/MDLoggerClearLog", "ee Folder =" + this.eeFolderArray.size());
        Collections.sort(this.otherFolderArray, new UtilsSortString(1));
        this.otherFolderSize = 0L;
        int i = this.otherFolderArray.size();
        for (int j = 0; j < i; j++)
        {
          File localFile1 = new File((String)this.otherFolderArray.get(j));
          this.otherFolderSize += clearLogs(localFile1);
        }
        Utils.logd("MTKLogger/MDLoggerClearLog", "other folde remove file total size: " + this.otherFolderSize / 1024L + "K");
        if ((this.otherFolderSize > 10485760L) && (this.otherFolderSize / 1024L / 1024L >= this.mLogNeedToBeClearSize))
        {
          Utils.logi("MTKLogger/MDLoggerClearLog", "Clean other folder in mdlog is " + this.otherFolderSize);
          return;
        }
        readRootFolderTreeList(this.mMdlogPath, true);
        Collections.sort(this.eeFolderArray, new UtilsSortString(1));
        Iterator localIterator1 = this.eeFolderArray.iterator();
        while (localIterator1.hasNext())
        {
          File localFile2 = new File((String)localIterator1.next());
          this.otherFolderSize += checkOtherFolderInMDFolder(localFile2);
          if ((this.otherFolderSize > 10485760L) && (this.otherFolderSize / 1024L / 1024L >= this.mLogNeedToBeClearSize))
          {
            Utils.logi("MTKLogger/MDLoggerClearLog", "Clean other folder in EE mdlog is  " + this.otherFolderSize);
            return;
          }
        }
        Collections.sort(this.normalFolderArray, new UtilsSortString(1));
        Iterator localIterator2 = this.normalFolderArray.iterator();
        while (localIterator2.hasNext())
        {
          File localFile3 = new File((String)localIterator2.next());
          this.otherFolderSize += checkOtherFolderInMDFolder(localFile3);
          if ((this.otherFolderSize > 10485760L) && (this.otherFolderSize / 1024L / 1024L >= this.mLogNeedToBeClearSize))
          {
            Utils.logi("MTKLogger/MDLoggerClearLog", "Clean other folder in normal mdlog is  " + this.otherFolderSize);
            return;
          }
        }
        if (this.normalFolderArray.size() > 0)
        {
          Iterator localIterator8 = this.normalFolderArray.iterator();
          for (;;)
          {
            if (localIterator8.hasNext())
            {
              String str = (String)localIterator8.next();
              if (this.folderTreeListArray.isEmpty())
              {
                if (readRootFolderTreeList(str, false))
                {
                  if (checkRemoveFileOneByOne()) {
                    break;
                  }
                  File localFile8 = new File(str);
                  this.otherFolderSize += clearLogs(localFile8);
                  if ((this.otherFolderSize <= 10485760L) || (this.otherFolderSize / 1024L / 1024L < this.mLogNeedToBeClearSize)) {
                    continue;
                  }
                  Utils.logi("MTKLogger/MDLoggerClearLog", "Clean bin in normal mdlog is  " + this.otherFolderSize);
                  return;
                }
                File localFile7 = new File(str);
                this.otherFolderSize += clearNormalLogs(localFile7);
                if ((this.otherFolderSize <= 10485760L) || (this.otherFolderSize / 1024L / 1024L < this.mLogNeedToBeClearSize)) {
                  continue;
                }
                Utils.logi("MTKLogger/MDLoggerClearLog", "Clean normal log is  " + this.otherFolderSize);
                return;
              }
              if (checkIfCanDeleteNormalFolder(str))
              {
                Utils.logi("MTKLogger/MDLoggerClearLog", "Clean Noram log file one by one");
                readRootFolderTreeList(str, false);
                if (checkRemoveFileOneByOne()) {
                  break;
                }
                File localFile6 = new File(str);
                this.otherFolderSize += clearLogs(localFile6);
                if ((this.otherFolderSize > 10485760L) && (this.otherFolderSize / 1024L / 1024L >= this.mLogNeedToBeClearSize))
                {
                  Utils.logi("MTKLogger/MDLoggerClearLog", "Clean bin in normal mdlog is  " + this.otherFolderSize);
                  return;
                }
              }
            }
          }
        }
        if (this.mRunningArray.size() == 1)
        {
          Utils.logi("MTKLogger/MDLoggerClearLog", "Clean running log");
          Iterator localIterator7 = this.mRunningArray.iterator();
          while (localIterator7.hasNext())
          {
            readRootFolderTreeList((String)localIterator7.next(), false);
            if (checkAndRemoveRunningFolder()) {
              return;
            }
          }
        }
        Utils.logi("MTKLogger/MDLoggerClearLog", "Clean Noram bin file one bye one");
        Iterator localIterator3 = this.mNormalContainBin.iterator();
        while (localIterator3.hasNext())
        {
          readRootFolderTreeList((String)localIterator3.next(), false);
          if (checkRemoveFileOneByOne()) {
            return;
          }
        }
        Utils.logi("MTKLogger/MDLoggerClearLog", "Clean Noram bin folder");
        Iterator localIterator4 = this.mNormalContainBin.iterator();
        while (localIterator4.hasNext())
        {
          File localFile4 = new File((String)localIterator4.next());
          this.otherFolderSize += clearLogs(localFile4);
          if ((this.otherFolderSize > 10485760L) && (this.otherFolderSize / 1024L / 1024L >= this.mLogNeedToBeClearSize))
          {
            Utils.logi("MTKLogger/MDLoggerClearLog", "Clean bin in normal mdlog is  " + this.otherFolderSize);
            return;
          }
        }
        Utils.logi("MTKLogger/MDLoggerClearLog", "Clean EE dump file one bye one");
        Iterator localIterator5 = this.eeFolderArray.iterator();
        while (localIterator5.hasNext())
        {
          readRootFolderTreeList((String)localIterator5.next(), false);
          if (checkRemoveFileOneByOne()) {
            return;
          }
        }
        Utils.logi("MTKLogger/MDLoggerClearLog", "Clean EE Folder");
        localIterator6 = this.eeFolderArray.iterator();
      }
      File localFile5 = new File((String)localIterator6.next());
      this.otherFolderSize += clearLogs(localFile5);
    } while ((this.otherFolderSize <= 10485760L) || (this.otherFolderSize / 1024L / 1024L < this.mLogNeedToBeClearSize));
    Utils.logi("MTKLogger/MDLoggerClearLog", "Clean bin in EE mdlog is  " + this.otherFolderSize);
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.utils.MDLoggerClearLog
 * JD-Core Version:    0.7.0.1
 */