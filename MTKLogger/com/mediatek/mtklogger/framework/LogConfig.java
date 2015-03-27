package com.mediatek.mtklogger.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.SparseArray;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class LogConfig
{
  private static final String TAG = "MTKLogger/LogConfig";
  private static String mConfigFilePathRoot = "/data/data/com.mediatek.mtklogger/";
  private static String mConfigFileSuffix = "shared_prefs/com.mediatek.mtklogger_preferences.xml";
  private File mConfigFile = null;
  private Context mContext = null;
  
  public LogConfig(Context paramContext)
  {
    this.mContext = paramContext;
    String str = this.mContext.getFilesDir().getAbsolutePath();
    if ((!TextUtils.isEmpty(str)) && (str.contains("files"))) {
      mConfigFilePathRoot = str.substring(0, str.indexOf("files"));
    }
    Utils.logi("MTKLogger/LogConfig", "APK private file root path = " + mConfigFilePathRoot);
  }
  
  /* Error */
  private void initConfig()
  {
    // Byte code:
    //   0: ldc 8
    //   2: ldc 90
    //   4: invokestatic 93	com/mediatek/mtklogger/utils/Utils:logd	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: new 40	java/io/File
    //   10: dup
    //   11: ldc 95
    //   13: invokespecial 98	java/io/File:<init>	(Ljava/lang/String;)V
    //   16: invokevirtual 102	java/io/File:exists	()Z
    //   19: ifeq +483 -> 502
    //   22: aload_0
    //   23: getfield 32	com/mediatek/mtklogger/framework/LogConfig:mContext	Landroid/content/Context;
    //   26: invokestatic 108	android/preference/PreferenceManager:getDefaultSharedPreferences	(Landroid/content/Context;)Landroid/content/SharedPreferences;
    //   29: invokeinterface 114 1 0
    //   34: astore_1
    //   35: new 116	java/util/Properties
    //   38: dup
    //   39: invokespecial 117	java/util/Properties:<init>	()V
    //   42: astore_2
    //   43: aconst_null
    //   44: astore_3
    //   45: new 119	java/io/FileInputStream
    //   48: dup
    //   49: ldc 95
    //   51: invokespecial 120	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   54: astore 4
    //   56: aload_2
    //   57: aload 4
    //   59: invokevirtual 124	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   62: getstatic 128	com/mediatek/mtklogger/utils/Utils:LOG_TYPE_SET	Ljava/util/Set;
    //   65: invokeinterface 134 1 0
    //   70: astore 9
    //   72: aload 9
    //   74: invokeinterface 139 1 0
    //   79: ifeq +302 -> 381
    //   82: aload 9
    //   84: invokeinterface 143 1 0
    //   89: checkcast 145	java/lang/Integer
    //   92: invokevirtual 149	java/lang/Integer:intValue	()I
    //   95: istore 14
    //   97: getstatic 153	com/mediatek/mtklogger/utils/Utils:DEFAULT_CONFIG_LOG_AUTO_START_MAP	Landroid/util/SparseArray;
    //   100: iload 14
    //   102: invokevirtual 159	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   105: checkcast 161	java/lang/Boolean
    //   108: invokevirtual 164	java/lang/Boolean:booleanValue	()Z
    //   111: istore 15
    //   113: getstatic 167	com/mediatek/mtklogger/utils/Utils:DEFAULT_CONFIG_LOG_SIZE_MAP	Landroid/util/SparseArray;
    //   116: iload 14
    //   118: invokevirtual 159	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   121: checkcast 145	java/lang/Integer
    //   124: invokevirtual 149	java/lang/Integer:intValue	()I
    //   127: istore 16
    //   129: aload_2
    //   130: getstatic 170	com/mediatek/mtklogger/utils/Utils:KEY_CONFIG_LOG_AUTO_START_MAP	Landroid/util/SparseArray;
    //   133: iload 14
    //   135: invokevirtual 159	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   138: checkcast 54	java/lang/String
    //   141: invokevirtual 174	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   144: astore 17
    //   146: aload_2
    //   147: getstatic 177	com/mediatek/mtklogger/utils/Utils:KEY_CONFIG_LOG_SIZE_MAP	Landroid/util/SparseArray;
    //   150: iload 14
    //   152: invokevirtual 159	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   155: checkcast 54	java/lang/String
    //   158: invokevirtual 174	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   161: astore 18
    //   163: aload 17
    //   165: ifnull +14 -> 179
    //   168: aload 17
    //   170: invokestatic 181	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   173: istore 19
    //   175: iload 19
    //   177: istore 15
    //   179: aload 18
    //   181: ifnull +14 -> 195
    //   184: aload 18
    //   186: invokestatic 184	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   189: istore 22
    //   191: iload 22
    //   193: istore 16
    //   195: ldc 8
    //   197: new 67	java/lang/StringBuilder
    //   200: dup
    //   201: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   204: ldc 186
    //   206: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: iload 14
    //   211: invokevirtual 189	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   214: ldc 191
    //   216: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: iload 15
    //   221: invokevirtual 194	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   224: ldc 196
    //   226: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: iload 16
    //   231: invokevirtual 189	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   234: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   237: invokestatic 93	com/mediatek/mtklogger/utils/Utils:logd	(Ljava/lang/String;Ljava/lang/String;)V
    //   240: aload_1
    //   241: getstatic 199	com/mediatek/mtklogger/utils/Utils:KEY_START_AUTOMATIC_MAP	Landroid/util/SparseArray;
    //   244: iload 14
    //   246: invokevirtual 159	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   249: checkcast 54	java/lang/String
    //   252: iload 15
    //   254: invokeinterface 205 3 0
    //   259: getstatic 208	com/mediatek/mtklogger/utils/Utils:KEY_LOG_SIZE_MAP	Landroid/util/SparseArray;
    //   262: iload 14
    //   264: invokevirtual 159	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   267: checkcast 54	java/lang/String
    //   270: iload 16
    //   272: invokestatic 212	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   275: invokeinterface 216 3 0
    //   280: pop
    //   281: goto -209 -> 72
    //   284: astore 7
    //   286: aload 4
    //   288: astore_3
    //   289: ldc 8
    //   291: new 67	java/lang/StringBuilder
    //   294: dup
    //   295: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   298: ldc 218
    //   300: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: aload 7
    //   305: invokevirtual 219	java/io/IOException:toString	()Ljava/lang/String;
    //   308: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   314: invokestatic 222	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   317: aload_0
    //   318: invokespecial 225	com/mediatek/mtklogger/framework/LogConfig:initDefaultConfig	()V
    //   321: aload_3
    //   322: ifnull +7 -> 329
    //   325: aload_3
    //   326: invokevirtual 228	java/io/FileInputStream:close	()V
    //   329: return
    //   330: astore 21
    //   332: ldc 8
    //   334: new 67	java/lang/StringBuilder
    //   337: dup
    //   338: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   341: ldc 230
    //   343: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   346: iload 14
    //   348: invokevirtual 189	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   351: ldc 232
    //   353: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   359: invokestatic 235	com/mediatek/mtklogger/utils/Utils:logw	(Ljava/lang/String;Ljava/lang/String;)V
    //   362: goto -167 -> 195
    //   365: astore 5
    //   367: aload 4
    //   369: astore_3
    //   370: aload_3
    //   371: ifnull +7 -> 378
    //   374: aload_3
    //   375: invokevirtual 228	java/io/FileInputStream:close	()V
    //   378: aload 5
    //   380: athrow
    //   381: aload_2
    //   382: ldc 237
    //   384: invokevirtual 174	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   387: astore 10
    //   389: ldc 8
    //   391: new 67	java/lang/StringBuilder
    //   394: dup
    //   395: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   398: ldc 239
    //   400: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   403: aload 10
    //   405: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   408: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   411: invokestatic 93	com/mediatek/mtklogger/utils/Utils:logd	(Ljava/lang/String;Ljava/lang/String;)V
    //   414: aload 10
    //   416: ifnull +36 -> 452
    //   419: aload_0
    //   420: getfield 32	com/mediatek/mtklogger/framework/LogConfig:mContext	Landroid/content/Context;
    //   423: ldc 241
    //   425: iconst_0
    //   426: invokevirtual 245	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   429: invokeinterface 114 1 0
    //   434: ldc 247
    //   436: aload 10
    //   438: invokestatic 181	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   441: invokeinterface 205 3 0
    //   446: invokeinterface 250 1 0
    //   451: pop
    //   452: aload_1
    //   453: invokeinterface 250 1 0
    //   458: pop
    //   459: aload 4
    //   461: ifnull +65 -> 526
    //   464: aload 4
    //   466: invokevirtual 228	java/io/FileInputStream:close	()V
    //   469: return
    //   470: astore 13
    //   472: ldc 8
    //   474: ldc 252
    //   476: invokestatic 222	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   479: return
    //   480: astore 8
    //   482: ldc 8
    //   484: ldc 252
    //   486: invokestatic 222	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   489: return
    //   490: astore 6
    //   492: ldc 8
    //   494: ldc 252
    //   496: invokestatic 222	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   499: goto -121 -> 378
    //   502: ldc 8
    //   504: ldc 254
    //   506: invokestatic 235	com/mediatek/mtklogger/utils/Utils:logw	(Ljava/lang/String;Ljava/lang/String;)V
    //   509: aload_0
    //   510: invokespecial 225	com/mediatek/mtklogger/framework/LogConfig:initDefaultConfig	()V
    //   513: return
    //   514: astore 5
    //   516: goto -146 -> 370
    //   519: astore 7
    //   521: aconst_null
    //   522: astore_3
    //   523: goto -234 -> 289
    //   526: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	527	0	this	LogConfig
    //   34	419	1	localEditor	SharedPreferences.Editor
    //   42	340	2	localProperties	java.util.Properties
    //   44	479	3	localObject1	Object
    //   54	411	4	localFileInputStream	java.io.FileInputStream
    //   365	14	5	localObject2	Object
    //   514	1	5	localObject3	Object
    //   490	1	6	localIOException1	java.io.IOException
    //   284	20	7	localIOException2	java.io.IOException
    //   519	1	7	localIOException3	java.io.IOException
    //   480	1	8	localIOException4	java.io.IOException
    //   70	13	9	localIterator	Iterator
    //   387	50	10	str1	String
    //   470	1	13	localIOException5	java.io.IOException
    //   95	252	14	i	int
    //   111	142	15	bool1	boolean
    //   127	144	16	j	int
    //   144	25	17	str2	String
    //   161	24	18	str3	String
    //   173	3	19	bool2	boolean
    //   330	1	21	localNumberFormatException	java.lang.NumberFormatException
    //   189	3	22	k	int
    // Exception table:
    //   from	to	target	type
    //   56	72	284	java/io/IOException
    //   72	163	284	java/io/IOException
    //   168	175	284	java/io/IOException
    //   184	191	284	java/io/IOException
    //   195	281	284	java/io/IOException
    //   332	362	284	java/io/IOException
    //   381	414	284	java/io/IOException
    //   419	452	284	java/io/IOException
    //   452	459	284	java/io/IOException
    //   184	191	330	java/lang/NumberFormatException
    //   56	72	365	finally
    //   72	163	365	finally
    //   168	175	365	finally
    //   184	191	365	finally
    //   195	281	365	finally
    //   332	362	365	finally
    //   381	414	365	finally
    //   419	452	365	finally
    //   452	459	365	finally
    //   464	469	470	java/io/IOException
    //   325	329	480	java/io/IOException
    //   374	378	490	java/io/IOException
    //   45	56	514	finally
    //   289	321	514	finally
    //   45	56	519	java/io/IOException
  }
  
  private void initDefaultConfig()
  {
    Utils.logw("MTKLogger/LogConfig", "-->initDefaultConfig()");
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
    Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      boolean bool = ((Boolean)Utils.DEFAULT_CONFIG_LOG_AUTO_START_MAP.get(i)).booleanValue();
      int j = ((Integer)Utils.DEFAULT_CONFIG_LOG_SIZE_MAP.get(i)).intValue();
      localEditor.putBoolean((String)Utils.KEY_START_AUTOMATIC_MAP.get(i), bool).putString((String)Utils.KEY_LOG_SIZE_MAP.get(i), String.valueOf(j));
    }
    localEditor.commit();
  }
  
  public void checkConfig()
  {
    Utils.logd("MTKLogger/LogConfig", "-->checkConfig()");
    this.mConfigFile = new File(mConfigFilePathRoot + mConfigFileSuffix);
    if ((this.mConfigFile == null) || (!this.mConfigFile.exists()))
    {
      Utils.logi("MTKLogger/LogConfig", "Config file has not been initialized, create it now");
      initConfig();
      return;
    }
    Utils.logd("MTKLogger/LogConfig", " configuration file already OK.");
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.LogConfig
 * JD-Core Version:    0.7.0.1
 */