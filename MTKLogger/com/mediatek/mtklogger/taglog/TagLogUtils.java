package com.mediatek.mtklogger.taglog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StatFs;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.mediatek.mtklogger.utils.Utils;
import com.mediatek.telephony.TelephonyManagerEx;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TagLogUtils
{
  private static final long BUFFER_MIN_NEEDED_LOG_SIZE = 2097152L;
  public static final String CALIBRATION_DATA = "calibration_data";
  public static final String CALIBRATION_DATA_KEY = "calibrationData";
  private static final String FILTER_FILE_TREE_TXT = "file_tree.txt";
  private static final String KEY_BASEBAND_VERSION = "gsm.version.baseband";
  private static final String KEY_BUILD_NUMBER = "ro.build.display.id";
  private static final String KEY_BUILD_PRODUCT = "ro.build.product";
  private static final String KEY_BUILD_TYPE = "ro.build.type";
  public static final int PAST_TIME = 10000;
  private static final int PRE_FILE_SIZE = 1024;
  private static final String TAG = "MTKLogger/TagLogUtils";
  public static final long TIMEAWAY = 600000L;
  public static final String ZIP_LOG_SUFFIX = ".zip";
  public static final String ZIP_TAG_LOG_FOLDER = "mtklog/taglog";
  
  public static int checkSdCardSpace(String paramString)
  {
    Utils.logi("MTKLogger/TagLogUtils", "checkSdCardSpace SDPath: " + paramString);
    File localFile = new File(paramString);
    if (paramString.toLowerCase().contains("sdcard"))
    {
      if (!localFile.exists())
      {
        Utils.logi("MTKLogger/TagLogUtils", "The SD Card doesn't exist");
        return 403;
      }
      if (!localFile.canWrite())
      {
        Utils.logi("MTKLogger/TagLogUtils", "The SD Card is not writtable");
        return 404;
      }
    }
    return 401;
  }
  
  public static int checkSdCardSpace(String paramString, String[] paramArrayOfString)
  {
    Utils.logi("MTKLogger/TagLogUtils", "checkSdCardSpace SDPath: " + paramString);
    long l1 = 0L;
    File localFile = new File(paramString);
    if (paramString.toLowerCase().contains("sdcard"))
    {
      if (!localFile.exists())
      {
        Utils.logi("MTKLogger/TagLogUtils", "The SD Card doesn't exist");
        return 403;
      }
      if (!localFile.canWrite())
      {
        Utils.logi("MTKLogger/TagLogUtils", "The SD Card is not writtable");
        return 404;
      }
    }
    StatFs localStatFs = new StatFs(localFile.getPath());
    int i = localStatFs.getAvailableBlocks();
    int j = localStatFs.getBlockSize();
    long l2 = i * j;
    Utils.logi("MTKLogger/TagLogUtils", "availableBlocks: " + i);
    Utils.logi("MTKLogger/TagLogUtils", "blockSize: " + j);
    for (int k = 0; k < paramArrayOfString.length; k++) {
      l1 += getFolderOrFileSize(paramArrayOfString[k]);
    }
    Utils.logd("MTKLogger/TagLogUtils", "sdAvailableSpace is: " + l2 + ", logSize: " + l1);
    if (l2 > l1) {
      return 401;
    }
    return 402;
  }
  
  public static String createTagLogFolder(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(paramString1).append("/").append("mtklog/taglog").append("/").append("TagLog_").append(ZipManager.translateTime2(System.currentTimeMillis()));
    if ((paramString2 == null) || ("".equals(paramString2))) {}
    for (String str1 = "";; str1 = "_" + paramString2)
    {
      String str2 = str1;
      Utils.logi("MTKLogger/TagLogUtils", "createTagLogFolder : " + str2);
      File localFile = new File(str2);
      if (!localFile.exists()) {
        localFile.mkdirs();
      }
      return localFile.getAbsolutePath();
    }
  }
  
  public static String getBuildNumber()
  {
    String str1 = SystemProperties.get("ro.build.display.id");
    String str2 = SystemProperties.get("ro.build.product");
    String str3 = SystemProperties.get("ro.build.type");
    String str4 = SystemProperties.get("gsm.version.baseband");
    StringBuilder localStringBuilder = new StringBuilder("===Build Version Information===");
    localStringBuilder.append("\nBuild Number: " + str1).append("\nThe production is " + str2).append(" with " + str3 + " build").append("\nAnd the baseband version is " + str4 + "\n");
    Log.d("MTKLogger/TagLogUtils", "Build number is " + localStringBuilder.toString());
    return localStringBuilder.toString();
  }
  
  public static File getCurrentLogFolder(File[] paramArrayOfFile, boolean paramBoolean)
  {
    Utils.logd("MTKLogger/TagLogUtils", "-->getCurrentLogFolder()");
    long l1 = 600000L;
    Object localObject = null;
    long l2 = System.currentTimeMillis();
    Utils.logi("MTKLogger/TagLogUtils", "Current time=" + ZipManager.translateTime(l2));
    int i = paramArrayOfFile.length;
    int j = 0;
    if (j < i)
    {
      File localFile2 = paramArrayOfFile[j];
      if ("file_tree.txt".equals(localFile2.getName())) {}
      for (;;)
      {
        j++;
        break;
        long l5 = getFolderLastModifyTime(localFile2);
        if (((!paramBoolean) || (Math.abs(l2 - l5) >= 10000L)) && (Math.abs(l2 - l5) < l1))
        {
          l1 = Math.abs(l2 - l5);
          localObject = localFile2;
        }
      }
    }
    if (localObject != null) {
      Utils.logi("MTKLogger/TagLogUtils", "Selected log folder name=[" + localObject.getName());
    }
    for (;;)
    {
      Utils.logd("MTKLogger/TagLogUtils", "<--getCurrentLogFolder()");
      return localObject;
      Utils.loge("MTKLogger/TagLogUtils", "Could not get needed log folder.");
      long l3 = 0L;
      int k = paramArrayOfFile.length;
      for (int m = 0; m < k; m++)
      {
        File localFile1 = paramArrayOfFile[m];
        long l4 = localFile1.lastModified();
        if ((l4 > l3) && (localFile1.isDirectory()))
        {
          l3 = l4;
          localObject = localFile1;
        }
      }
      if (localObject != null) {
        Utils.logi("MTKLogger/TagLogUtils", "Selected log folder name=[" + localObject.getName() + "], last modified time=" + ZipManager.translateTime(localObject.lastModified()));
      } else {
        Utils.loge("MTKLogger/TagLogUtils", "There is no folder");
      }
    }
  }
  
  public static File getCurrentLogFolderFromPath(String paramString)
  {
    return getCurrentLogFolderFromPath(paramString, false);
  }
  
  public static File getCurrentLogFolderFromPath(String paramString, boolean paramBoolean)
  {
    Utils.logd("MTKLogger/TagLogUtils", "-->get currentLog folder in " + paramString);
    if (paramString == null) {
      return null;
    }
    File localFile = new File(paramString);
    if (!localFile.exists())
    {
      Utils.loge("MTKLogger/TagLogUtils", "getCurrentLogFolder() the folder isn't exist!");
      return null;
    }
    File[] arrayOfFile = localFile.listFiles();
    if (arrayOfFile == null)
    {
      Utils.loge("MTKLogger/TagLogUtils", "No promission to access " + paramString);
      return null;
    }
    if (arrayOfFile.length <= 0)
    {
      Utils.loge("MTKLogger/TagLogUtils", "There is no folder in " + paramString);
      return null;
    }
    Utils.logd("MTKLogger/TagLogUtils", "<--getCurrentLogFolder()");
    return getCurrentLogFolder(arrayOfFile, paramBoolean);
  }
  
  public static int getFolderFilesCount(String paramString)
  {
    int i = 0;
    File localFile = new File(paramString);
    if (!localFile.exists()) {
      return 0;
    }
    if (!localFile.isDirectory()) {
      i = 0 + 1;
    }
    for (;;)
    {
      return i;
      File[] arrayOfFile = localFile.listFiles();
      int j = arrayOfFile.length;
      for (int k = 0; k < j; k++) {
        i += getFolderFilesCount(arrayOfFile[k].getAbsolutePath());
      }
    }
  }
  
  public static long getFolderLastModifyTime(File paramFile)
  {
    long l1 = 0L;
    if ((paramFile == null) || (!paramFile.exists()))
    {
      Utils.logd("MTKLogger/TagLogUtils", "Given file not exist.");
      return l1;
    }
    long l2 = System.currentTimeMillis();
    if (paramFile.isFile())
    {
      Utils.logw("MTKLogger/TagLogUtils", "You should give me a folder. But still can work here.");
      long l4 = paramFile.lastModified();
      if (l2 - l4 > -10000L) {
        l1 = l4;
      }
      return l1;
    }
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
    {
      Utils.loge("MTKLogger/TagLogUtils", "No promission to access ");
      return l1;
    }
    int i = arrayOfFile.length;
    int j = 0;
    label93:
    File localFile;
    if (j < i)
    {
      localFile = arrayOfFile[j];
      if (!localFile.isFile()) {
        break label162;
      }
      l3 = localFile.lastModified();
      if (l2 - l3 >= -10000L) {}
    }
    label162:
    for (long l3 = 0L;; l3 = getFolderLastModifyTime(localFile))
    {
      if (Math.abs(l3 - l2) < Math.abs(l1 - l2)) {
        l1 = l3;
      }
      j++;
      break label93;
      break;
    }
  }
  
  public static long getFolderOrFileSize(String paramString)
  {
    long l = 0L;
    File localFile = new File(paramString);
    if (!localFile.exists()) {
      return 0L;
    }
    if (!localFile.isDirectory()) {
      l = localFile.length();
    }
    for (;;)
    {
      return l;
      File[] arrayOfFile = localFile.listFiles();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++) {
        l += getFolderOrFileSize(arrayOfFile[j].getAbsolutePath());
      }
    }
  }
  
  public static String getIMEI(Context paramContext)
  {
    ((TelephonyManager)paramContext.getSystemService("phone"));
    TelephonyManagerEx localTelephonyManagerEx = TelephonyManagerEx.getDefault();
    String str1 = localTelephonyManagerEx.getDeviceId(0);
    String str2 = localTelephonyManagerEx.getDeviceId(1);
    String str3 = "The device IMEI is " + " SIM1: " + str1 + ", SIM2: " + str2;
    String str4 = str3 + "\n";
    Log.d("MTKLogger/TagLogUtils", "Flashed IMEI? " + str4);
    return str4;
  }
  
  public static String getIVSR(Context paramContext)
  {
    long l = Settings.System.getLong(paramContext.getContentResolver(), "ivsr_setting", 0L);
    String str = "The IVSR is " + String.valueOf(l) + "\n";
    Log.d("MTKLogger/TagLogUtils", "IVSR enable status: " + str);
    return str;
  }
  
  public static long getTagLogNeededSize(List<LogInformation> paramList)
  {
    long l1 = 0L;
    if (paramList.size() == 0) {
      return 0L;
    }
    Collections.sort(paramList, new Comparator()
    {
      public int compare(LogInformation paramAnonymousLogInformation1, LogInformation paramAnonymousLogInformation2)
      {
        long l = paramAnonymousLogInformation1.getTagLogSize() - paramAnonymousLogInformation2.getTagLogSize();
        if (l < 0L) {
          return -1;
        }
        if (l > 0L) {
          return 1;
        }
        return 0;
      }
    });
    long l2 = 0L;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      LogInformation localLogInformation = (LogInformation)localIterator.next();
      if (l2 == 0L)
      {
        l1 = localLogInformation.getTagLogSize();
        l2 = localLogInformation.getSaveSpace();
      }
      else
      {
        if (l2 < localLogInformation.getTagLogSize()) {
          l1 += localLogInformation.getTagLogSize() - l2;
        }
        l2 += localLogInformation.getSaveSpace();
      }
    }
    long l3 = l1 + 2097152L;
    Utils.logi("MTKLogger/TagLogUtils", "getTagLogNeededSize(): " + l3);
    return l3;
  }
  
  public static boolean hasCalibrationData(Context paramContext)
  {
    boolean bool = paramContext.getSharedPreferences("calibration_data", 0).getBoolean("calibrationData", false);
    Log.d("MTKLogger/TagLogUtils", "Calibration data is written? " + bool);
    return bool;
  }
  
  public static boolean writeCheckSOPToFile(Context paramContext, File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.isFile()) || (!paramFile.canWrite())) {
      return false;
    }
    boolean bool = hasCalibrationData(paramContext);
    String str1 = getIVSR(paramContext);
    String str2 = getIMEI(paramContext);
    String str3 = getBuildNumber();
    String str4 = new String("Calibration data is downloaded: " + bool + "\n");
    StringBuffer localStringBuffer = new StringBuffer("Check SOP result:\n");
    localStringBuffer.append(str4).append(str2).append(str1).append(str3);
    try
    {
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(paramFile));
      localOutputStreamWriter.write(localStringBuffer.toString());
      localOutputStreamWriter.flush();
      localOutputStreamWriter.close();
      Log.d("MTKLogger/TagLogUtils", "The check sop string is " + localStringBuffer.toString());
      Log.d("MTKLogger/TagLogUtils", "Write the check sop to file: " + paramFile.getAbsolutePath());
      return true;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      for (;;)
      {
        localFileNotFoundException.printStackTrace();
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
  }
  
  /* Error */
  public static boolean writeDBFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 74	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 77	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: aload_2
    //   10: invokevirtual 92	java/io/File:exists	()Z
    //   13: ifne +13 -> 26
    //   16: ldc 38
    //   18: ldc_w 441
    //   21: invokestatic 231	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   24: iconst_0
    //   25: ireturn
    //   26: aload_2
    //   27: invokevirtual 210	java/io/File:getName	()Ljava/lang/String;
    //   30: astore_3
    //   31: new 74	java/io/File
    //   34: dup
    //   35: new 55	java/lang/StringBuilder
    //   38: dup
    //   39: invokespecial 56	java/lang/StringBuilder:<init>	()V
    //   42: aload_1
    //   43: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: ldc 138
    //   48: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: aload_3
    //   52: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: invokespecial 77	java/io/File:<init>	(Ljava/lang/String;)V
    //   61: astore 4
    //   63: aload 4
    //   65: invokevirtual 92	java/io/File:exists	()Z
    //   68: ifne +9 -> 77
    //   71: aload 4
    //   73: invokevirtual 444	java/io/File:createNewFile	()Z
    //   76: pop
    //   77: ldc 38
    //   79: new 55	java/lang/StringBuilder
    //   82: dup
    //   83: invokespecial 56	java/lang/StringBuilder:<init>	()V
    //   86: ldc_w 446
    //   89: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: aload 4
    //   94: invokevirtual 166	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   97: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: invokestatic 72	com/mediatek/mtklogger/utils/Utils:logi	(Ljava/lang/String;Ljava/lang/String;)V
    //   106: new 448	java/io/FileInputStream
    //   109: dup
    //   110: aload_2
    //   111: invokespecial 449	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   114: astore 5
    //   116: new 413	java/io/FileOutputStream
    //   119: dup
    //   120: aload 4
    //   122: invokespecial 416	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   125: astore 6
    //   127: sipush 1024
    //   130: newarray byte
    //   132: astore 9
    //   134: aload 5
    //   136: aload 9
    //   138: invokevirtual 453	java/io/FileInputStream:read	([B)I
    //   141: istore 10
    //   143: iload 10
    //   145: iconst_m1
    //   146: if_icmpeq +34 -> 180
    //   149: aload 6
    //   151: aload 9
    //   153: iconst_0
    //   154: iload 10
    //   156: invokevirtual 456	java/io/FileOutputStream:write	([BII)V
    //   159: goto -25 -> 134
    //   162: astore 8
    //   164: aload 8
    //   166: invokevirtual 436	java/io/FileNotFoundException:printStackTrace	()V
    //   169: iconst_0
    //   170: ireturn
    //   171: astore 11
    //   173: aload 11
    //   175: invokevirtual 437	java/io/IOException:printStackTrace	()V
    //   178: iconst_0
    //   179: ireturn
    //   180: aload 6
    //   182: invokevirtual 457	java/io/FileOutputStream:flush	()V
    //   185: aload 6
    //   187: invokevirtual 458	java/io/FileOutputStream:close	()V
    //   190: aload 5
    //   192: invokevirtual 459	java/io/FileInputStream:close	()V
    //   195: iconst_1
    //   196: ireturn
    //   197: astore 7
    //   199: aload 7
    //   201: invokevirtual 437	java/io/IOException:printStackTrace	()V
    //   204: iconst_0
    //   205: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	206	0	paramString1	String
    //   0	206	1	paramString2	String
    //   8	103	2	localFile1	File
    //   30	22	3	str	String
    //   61	60	4	localFile2	File
    //   114	77	5	localFileInputStream	java.io.FileInputStream
    //   125	61	6	localFileOutputStream	FileOutputStream
    //   197	3	7	localIOException1	IOException
    //   162	3	8	localFileNotFoundException	FileNotFoundException
    //   132	20	9	arrayOfByte	byte[]
    //   141	14	10	i	int
    //   171	3	11	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   106	134	162	java/io/FileNotFoundException
    //   134	143	162	java/io/FileNotFoundException
    //   149	159	162	java/io/FileNotFoundException
    //   180	195	162	java/io/FileNotFoundException
    //   71	77	171	java/io/IOException
    //   106	134	197	java/io/IOException
    //   134	143	197	java/io/IOException
    //   149	159	197	java/io/IOException
    //   180	195	197	java/io/IOException
  }
  
  public static void writeFolderToTagFolder(String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    if ((localFile1 == null) || (!localFile1.exists())) {}
    for (;;)
    {
      return;
      Utils.logi("MTKLogger/TagLogUtils", "write Log to tag folder" + paramString1 + "--> " + paramString2);
      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile == null)
      {
        String str2 = localFile1.getAbsolutePath();
        Utils.logi("MTKLogger/TagLogUtils", "Log path: " + str2);
        writeDBFile(str2, paramString2);
        return;
      }
      String str1 = localFile1.getName();
      File localFile2 = new File(paramString2 + "/" + str1);
      Utils.logi("MTKLogger/TagLogUtils", "SubFolderNew: " + localFile2);
      if (!localFile2.exists()) {
        localFile2.mkdirs();
      }
      for (int i = 0; i < arrayOfFile.length; i++) {
        writeFolderToTagFolder(arrayOfFile[i].getAbsolutePath(), localFile2.getAbsolutePath());
      }
    }
  }
  
  /* Error */
  public static void writeTagLog(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 74	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 77	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: aload_2
    //   10: invokevirtual 92	java/io/File:exists	()Z
    //   13: ifne +12 -> 25
    //   16: ldc 38
    //   18: ldc_w 441
    //   21: invokestatic 231	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   24: return
    //   25: aload_2
    //   26: invokevirtual 210	java/io/File:getName	()Ljava/lang/String;
    //   29: astore_3
    //   30: new 74	java/io/File
    //   33: dup
    //   34: new 55	java/lang/StringBuilder
    //   37: dup
    //   38: invokespecial 56	java/lang/StringBuilder:<init>	()V
    //   41: aload_1
    //   42: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: ldc 138
    //   47: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: aload_3
    //   51: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   57: invokespecial 77	java/io/File:<init>	(Ljava/lang/String;)V
    //   60: astore 4
    //   62: aload 4
    //   64: invokevirtual 92	java/io/File:exists	()Z
    //   67: ifne +9 -> 76
    //   70: aload 4
    //   72: invokevirtual 444	java/io/File:createNewFile	()Z
    //   75: pop
    //   76: ldc 38
    //   78: new 55	java/lang/StringBuilder
    //   81: dup
    //   82: invokespecial 56	java/lang/StringBuilder:<init>	()V
    //   85: ldc_w 478
    //   88: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: aload 4
    //   93: invokevirtual 166	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   96: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   102: invokestatic 72	com/mediatek/mtklogger/utils/Utils:logi	(Ljava/lang/String;Ljava/lang/String;)V
    //   105: new 480	java/io/BufferedReader
    //   108: dup
    //   109: new 482	java/io/FileReader
    //   112: dup
    //   113: aload_2
    //   114: invokespecial 483	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   117: invokespecial 486	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   120: astore 5
    //   122: new 488	java/io/BufferedWriter
    //   125: dup
    //   126: new 490	java/io/FileWriter
    //   129: dup
    //   130: aload 4
    //   132: invokespecial 491	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   135: invokespecial 494	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   138: astore 6
    //   140: aload 5
    //   142: invokevirtual 497	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   145: astore 9
    //   147: aload 9
    //   149: ifnull +49 -> 198
    //   152: aload 6
    //   154: aload 9
    //   156: iconst_0
    //   157: aload 9
    //   159: invokevirtual 499	java/lang/String:length	()I
    //   162: invokevirtual 502	java/io/BufferedWriter:write	(Ljava/lang/String;II)V
    //   165: aload 5
    //   167: invokevirtual 497	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   170: astore 9
    //   172: aload 6
    //   174: invokevirtual 503	java/io/BufferedWriter:flush	()V
    //   177: goto -30 -> 147
    //   180: astore 8
    //   182: aload 8
    //   184: invokevirtual 436	java/io/FileNotFoundException:printStackTrace	()V
    //   187: return
    //   188: astore 10
    //   190: aload 10
    //   192: invokevirtual 437	java/io/IOException:printStackTrace	()V
    //   195: goto -119 -> 76
    //   198: aload 5
    //   200: invokevirtual 504	java/io/BufferedReader:close	()V
    //   203: aload 6
    //   205: invokevirtual 505	java/io/BufferedWriter:close	()V
    //   208: return
    //   209: astore 7
    //   211: aload 7
    //   213: invokevirtual 437	java/io/IOException:printStackTrace	()V
    //   216: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	217	0	paramString1	String
    //   0	217	1	paramString2	String
    //   8	106	2	localFile1	File
    //   29	22	3	str1	String
    //   60	71	4	localFile2	File
    //   120	79	5	localBufferedReader	java.io.BufferedReader
    //   138	66	6	localBufferedWriter	java.io.BufferedWriter
    //   209	3	7	localIOException1	IOException
    //   180	3	8	localFileNotFoundException	FileNotFoundException
    //   145	26	9	str2	String
    //   188	3	10	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   105	147	180	java/io/FileNotFoundException
    //   152	177	180	java/io/FileNotFoundException
    //   198	208	180	java/io/FileNotFoundException
    //   70	76	188	java/io/IOException
    //   105	147	209	java/io/IOException
    //   152	177	209	java/io/IOException
    //   198	208	209	java/io/IOException
  }
  
  public static void writeTagLogFolder(String paramString1, String paramString2)
  {
    File localFile = new File(paramString1);
    if ((localFile == null) || (!localFile.exists())) {}
    for (;;)
    {
      return;
      Utils.logi("MTKLogger/TagLogUtils", "write Log to tag folder" + paramString1 + "--> " + paramString2);
      File[] arrayOfFile = localFile.listFiles();
      if (arrayOfFile == null)
      {
        String str = localFile.getAbsolutePath();
        Utils.logi("MTKLogger/TagLogUtils", "Log path: " + str);
        writeTagLog(str, paramString2);
        return;
      }
      for (int i = 0; i < arrayOfFile.length; i++) {
        writeTagLogFolder(arrayOfFile[i].getAbsolutePath(), paramString2);
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.taglog.TagLogUtils
 * JD-Core Version:    0.7.0.1
 */