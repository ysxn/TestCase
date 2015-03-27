package com.mediatek.mtklogger.taglog;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;

public class ZipManager
{
  private static final int PRE_SIZE = 512;
  private static final String TAG = "MTKLogger/ZipManager";
  private static int mZippedFilesCount = 0;
  
  /* Error */
  public static java.util.List<String> getZipContentList(String paramString)
  {
    // Byte code:
    //   0: ldc 11
    //   2: new 27	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   9: ldc 30
    //   11: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_0
    //   15: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   21: invokestatic 44	com/mediatek/mtklogger/utils/Utils:logv	(Ljava/lang/String;Ljava/lang/String;)V
    //   24: new 46	java/util/ArrayList
    //   27: dup
    //   28: invokespecial 47	java/util/ArrayList:<init>	()V
    //   31: astore_1
    //   32: aconst_null
    //   33: astore_2
    //   34: new 49	java/util/zip/ZipInputStream
    //   37: dup
    //   38: new 51	java/io/FileInputStream
    //   41: dup
    //   42: aload_0
    //   43: invokespecial 54	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   46: invokespecial 57	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   49: astore_3
    //   50: aload_3
    //   51: invokevirtual 61	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   54: astore 10
    //   56: aload 10
    //   58: ifnull +117 -> 175
    //   61: aload 10
    //   63: invokevirtual 66	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   66: astore 11
    //   68: aload 10
    //   70: invokevirtual 70	java/util/zip/ZipEntry:getSize	()J
    //   73: lstore 12
    //   75: aload 10
    //   77: invokevirtual 74	java/util/zip/ZipEntry:isDirectory	()Z
    //   80: istore 14
    //   82: new 27	java/lang/StringBuilder
    //   85: dup
    //   86: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   89: aload 11
    //   91: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: ldc 76
    //   96: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: lload 12
    //   101: invokevirtual 79	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   104: ldc 81
    //   106: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: astore 15
    //   111: iload 14
    //   113: ifeq +55 -> 168
    //   116: ldc 83
    //   118: astore 16
    //   120: aload_1
    //   121: aload 15
    //   123: aload 16
    //   125: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: ldc 85
    //   130: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokeinterface 91 2 0
    //   141: pop
    //   142: goto -92 -> 50
    //   145: astore 8
    //   147: aload_3
    //   148: astore_2
    //   149: ldc 11
    //   151: ldc 93
    //   153: aload 8
    //   155: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   158: aload_2
    //   159: ifnull +7 -> 166
    //   162: aload_2
    //   163: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   166: aconst_null
    //   167: areturn
    //   168: ldc 102
    //   170: astore 16
    //   172: goto -52 -> 120
    //   175: aload_3
    //   176: ifnull +101 -> 277
    //   179: aload_3
    //   180: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   183: aload_1
    //   184: areturn
    //   185: astore 18
    //   187: aload 18
    //   189: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   192: aload_1
    //   193: areturn
    //   194: astore 9
    //   196: aload 9
    //   198: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   201: aconst_null
    //   202: areturn
    //   203: astore 4
    //   205: ldc 11
    //   207: ldc 107
    //   209: aload 4
    //   211: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   214: aload_2
    //   215: ifnull -49 -> 166
    //   218: aload_2
    //   219: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   222: aconst_null
    //   223: areturn
    //   224: astore 7
    //   226: aload 7
    //   228: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   231: aconst_null
    //   232: areturn
    //   233: astore 5
    //   235: aload_2
    //   236: ifnull +7 -> 243
    //   239: aload_2
    //   240: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   243: aload 5
    //   245: athrow
    //   246: astore 6
    //   248: aload 6
    //   250: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   253: goto -10 -> 243
    //   256: astore 5
    //   258: aload_3
    //   259: astore_2
    //   260: goto -25 -> 235
    //   263: astore 4
    //   265: aload_3
    //   266: astore_2
    //   267: goto -62 -> 205
    //   270: astore 8
    //   272: aconst_null
    //   273: astore_2
    //   274: goto -125 -> 149
    //   277: aload_1
    //   278: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	279	0	paramString	String
    //   31	247	1	localArrayList	java.util.ArrayList
    //   33	241	2	localObject1	Object
    //   49	217	3	localZipInputStream	java.util.zip.ZipInputStream
    //   203	7	4	localIOException1	java.io.IOException
    //   263	1	4	localIOException2	java.io.IOException
    //   233	11	5	localObject2	Object
    //   256	1	5	localObject3	Object
    //   246	3	6	localIOException3	java.io.IOException
    //   224	3	7	localIOException4	java.io.IOException
    //   145	9	8	localFileNotFoundException1	java.io.FileNotFoundException
    //   270	1	8	localFileNotFoundException2	java.io.FileNotFoundException
    //   194	3	9	localIOException5	java.io.IOException
    //   54	22	10	localZipEntry	java.util.zip.ZipEntry
    //   66	24	11	str1	String
    //   73	27	12	l	long
    //   80	32	14	bool	boolean
    //   109	13	15	localStringBuilder	java.lang.StringBuilder
    //   118	53	16	str2	String
    //   185	3	18	localIOException6	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   50	56	145	java/io/FileNotFoundException
    //   61	111	145	java/io/FileNotFoundException
    //   120	142	145	java/io/FileNotFoundException
    //   179	183	185	java/io/IOException
    //   162	166	194	java/io/IOException
    //   34	50	203	java/io/IOException
    //   218	222	224	java/io/IOException
    //   34	50	233	finally
    //   149	158	233	finally
    //   205	214	233	finally
    //   239	243	246	java/io/IOException
    //   50	56	256	finally
    //   61	111	256	finally
    //   120	142	256	finally
    //   50	56	263	java/io/IOException
    //   61	111	263	java/io/IOException
    //   120	142	263	java/io/IOException
    //   34	50	270	java/io/FileNotFoundException
  }
  
  public static int getZippedFilesCount()
  {
    return mZippedFilesCount;
  }
  
  public static void initZippedFilesCount()
  {
    mZippedFilesCount = 0;
  }
  
  public static String translateTime(long paramLong)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.applyPattern("00");
    localGregorianCalendar.setTime(new Date(paramLong));
    int i = localGregorianCalendar.get(1);
    int j = 1 + localGregorianCalendar.get(2);
    int k = localGregorianCalendar.get(5);
    int m = localGregorianCalendar.get(11);
    int n = localGregorianCalendar.get(12);
    return "" + i + "/" + localDecimalFormat.format(j) + "/" + localDecimalFormat.format(k) + "  " + localDecimalFormat.format(m) + ":" + localDecimalFormat.format(n);
  }
  
  public static String translateTime2(long paramLong)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.applyPattern("00");
    localGregorianCalendar.setTime(new Date(paramLong));
    int i = localGregorianCalendar.get(1);
    int j = 1 + localGregorianCalendar.get(2);
    int k = localGregorianCalendar.get(5);
    int m = localGregorianCalendar.get(11);
    int n = localGregorianCalendar.get(12);
    int i1 = localGregorianCalendar.get(13);
    return "" + i + "_" + localDecimalFormat.format(j) + localDecimalFormat.format(k) + "_" + localDecimalFormat.format(m) + localDecimalFormat.format(n) + localDecimalFormat.format(i1);
  }
  
  /* Error */
  public static void unzipFile(String paramString1, String paramString2, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc 11
    //   2: new 27	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   9: ldc 157
    //   11: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_0
    //   15: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 159
    //   20: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: aload_1
    //   24: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: ldc 161
    //   29: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: iload_2
    //   33: invokevirtual 164	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   36: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: invokestatic 44	com/mediatek/mtklogger/utils/Utils:logv	(Ljava/lang/String;Ljava/lang/String;)V
    //   42: aconst_null
    //   43: astore_3
    //   44: aconst_null
    //   45: astore 4
    //   47: new 166	java/io/File
    //   50: dup
    //   51: aload_1
    //   52: invokespecial 167	java/io/File:<init>	(Ljava/lang/String;)V
    //   55: astore 5
    //   57: aload 5
    //   59: invokevirtual 170	java/io/File:exists	()Z
    //   62: ifeq +37 -> 99
    //   65: ldc 11
    //   67: new 27	java/lang/StringBuilder
    //   70: dup
    //   71: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   74: ldc 172
    //   76: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: aload_1
    //   80: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: ldc 174
    //   85: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: invokestatic 176	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: iload_2
    //   95: ifne +4 -> 99
    //   98: return
    //   99: aload 5
    //   101: invokevirtual 179	java/io/File:mkdirs	()Z
    //   104: pop
    //   105: new 49	java/util/zip/ZipInputStream
    //   108: dup
    //   109: new 51	java/io/FileInputStream
    //   112: dup
    //   113: aload_0
    //   114: invokespecial 54	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   117: invokespecial 57	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   120: astore 7
    //   122: aconst_null
    //   123: astore 8
    //   125: aload 7
    //   127: invokevirtual 61	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   130: astore 18
    //   132: aload 18
    //   134: ifnull +251 -> 385
    //   137: aload 18
    //   139: invokevirtual 66	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   142: astore 19
    //   144: ldc 11
    //   146: new 27	java/lang/StringBuilder
    //   149: dup
    //   150: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   153: ldc 181
    //   155: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: aload 19
    //   160: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokestatic 44	com/mediatek/mtklogger/utils/Utils:logv	(Ljava/lang/String;Ljava/lang/String;)V
    //   169: aload 18
    //   171: invokevirtual 74	java/util/zip/ZipEntry:isDirectory	()Z
    //   174: ifeq +87 -> 261
    //   177: new 166	java/io/File
    //   180: dup
    //   181: new 27	java/lang/StringBuilder
    //   184: dup
    //   185: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   188: aload_1
    //   189: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: getstatic 184	java/io/File:separator	Ljava/lang/String;
    //   195: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: aload 19
    //   200: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   206: invokespecial 167	java/io/File:<init>	(Ljava/lang/String;)V
    //   209: invokevirtual 179	java/io/File:mkdirs	()Z
    //   212: pop
    //   213: goto -88 -> 125
    //   216: astore 15
    //   218: aload 8
    //   220: astore 4
    //   222: aload 7
    //   224: astore_3
    //   225: ldc 11
    //   227: ldc 93
    //   229: aload 15
    //   231: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   234: aload_3
    //   235: ifnull +7 -> 242
    //   238: aload_3
    //   239: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   242: aload 4
    //   244: ifnull -146 -> 98
    //   247: aload 4
    //   249: invokevirtual 187	java/io/FileOutputStream:close	()V
    //   252: return
    //   253: astore 16
    //   255: aload 16
    //   257: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   260: return
    //   261: new 166	java/io/File
    //   264: dup
    //   265: new 27	java/lang/StringBuilder
    //   268: dup
    //   269: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   272: aload_1
    //   273: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: getstatic 184	java/io/File:separator	Ljava/lang/String;
    //   279: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: aload 19
    //   284: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   290: invokespecial 167	java/io/File:<init>	(Ljava/lang/String;)V
    //   293: astore 20
    //   295: aload 20
    //   297: invokevirtual 191	java/io/File:getParentFile	()Ljava/io/File;
    //   300: astore 21
    //   302: aload 21
    //   304: ifnull +9 -> 313
    //   307: aload 21
    //   309: invokevirtual 179	java/io/File:mkdirs	()Z
    //   312: pop
    //   313: aload 20
    //   315: invokevirtual 194	java/io/File:createNewFile	()Z
    //   318: pop
    //   319: new 186	java/io/FileOutputStream
    //   322: dup
    //   323: aload 20
    //   325: invokespecial 197	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   328: astore 4
    //   330: sipush 512
    //   333: newarray byte
    //   335: astore 24
    //   337: aload 7
    //   339: aload 24
    //   341: invokevirtual 201	java/util/zip/ZipInputStream:read	([B)I
    //   344: istore 25
    //   346: iload 25
    //   348: iconst_m1
    //   349: if_icmple +29 -> 378
    //   352: aload 4
    //   354: aload 24
    //   356: iconst_0
    //   357: iload 25
    //   359: invokevirtual 205	java/io/FileOutputStream:write	([BII)V
    //   362: aload 4
    //   364: invokevirtual 208	java/io/FileOutputStream:flush	()V
    //   367: goto -30 -> 337
    //   370: astore 15
    //   372: aload 7
    //   374: astore_3
    //   375: goto -150 -> 225
    //   378: aload 4
    //   380: astore 8
    //   382: goto -257 -> 125
    //   385: aload 7
    //   387: ifnull +8 -> 395
    //   390: aload 7
    //   392: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   395: aload 8
    //   397: ifnull +184 -> 581
    //   400: aload 8
    //   402: invokevirtual 187	java/io/FileOutputStream:close	()V
    //   405: aload 8
    //   407: pop
    //   408: return
    //   409: astore 31
    //   411: aload 31
    //   413: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   416: goto -21 -> 395
    //   419: astore 28
    //   421: aload 28
    //   423: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   426: aload 8
    //   428: pop
    //   429: return
    //   430: astore 17
    //   432: aload 17
    //   434: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   437: goto -195 -> 242
    //   440: astore 9
    //   442: ldc 11
    //   444: ldc 107
    //   446: aload 9
    //   448: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   451: aload_3
    //   452: ifnull +7 -> 459
    //   455: aload_3
    //   456: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   459: aload 4
    //   461: ifnull -363 -> 98
    //   464: aload 4
    //   466: invokevirtual 187	java/io/FileOutputStream:close	()V
    //   469: return
    //   470: astore 13
    //   472: aload 13
    //   474: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   477: return
    //   478: astore 14
    //   480: aload 14
    //   482: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   485: goto -26 -> 459
    //   488: astore 10
    //   490: aload_3
    //   491: ifnull +7 -> 498
    //   494: aload_3
    //   495: invokevirtual 100	java/util/zip/ZipInputStream:close	()V
    //   498: aload 4
    //   500: ifnull +8 -> 508
    //   503: aload 4
    //   505: invokevirtual 187	java/io/FileOutputStream:close	()V
    //   508: aload 10
    //   510: athrow
    //   511: astore 12
    //   513: aload 12
    //   515: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   518: goto -20 -> 498
    //   521: astore 11
    //   523: aload 11
    //   525: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   528: goto -20 -> 508
    //   531: astore 10
    //   533: aload 8
    //   535: astore 4
    //   537: aload 7
    //   539: astore_3
    //   540: goto -50 -> 490
    //   543: astore 10
    //   545: aload 7
    //   547: astore_3
    //   548: goto -58 -> 490
    //   551: astore 9
    //   553: aload 8
    //   555: astore 4
    //   557: aload 7
    //   559: astore_3
    //   560: goto -118 -> 442
    //   563: astore 9
    //   565: aload 7
    //   567: astore_3
    //   568: goto -126 -> 442
    //   571: astore 15
    //   573: aconst_null
    //   574: astore 4
    //   576: aconst_null
    //   577: astore_3
    //   578: goto -353 -> 225
    //   581: aload 8
    //   583: pop
    //   584: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	585	0	paramString1	String
    //   0	585	1	paramString2	String
    //   0	585	2	paramBoolean	boolean
    //   43	535	3	localObject1	Object
    //   45	530	4	localObject2	Object
    //   55	45	5	localFile1	java.io.File
    //   120	446	7	localZipInputStream	java.util.zip.ZipInputStream
    //   123	459	8	localObject3	Object
    //   440	7	9	localIOException1	java.io.IOException
    //   551	1	9	localIOException2	java.io.IOException
    //   563	1	9	localIOException3	java.io.IOException
    //   488	21	10	localObject4	Object
    //   531	1	10	localObject5	Object
    //   543	1	10	localObject6	Object
    //   521	3	11	localIOException4	java.io.IOException
    //   511	3	12	localIOException5	java.io.IOException
    //   470	3	13	localIOException6	java.io.IOException
    //   478	3	14	localIOException7	java.io.IOException
    //   216	14	15	localFileNotFoundException1	java.io.FileNotFoundException
    //   370	1	15	localFileNotFoundException2	java.io.FileNotFoundException
    //   571	1	15	localFileNotFoundException3	java.io.FileNotFoundException
    //   253	3	16	localIOException8	java.io.IOException
    //   430	3	17	localIOException9	java.io.IOException
    //   130	40	18	localZipEntry	java.util.zip.ZipEntry
    //   142	141	19	str	String
    //   293	31	20	localFile2	java.io.File
    //   300	8	21	localFile3	java.io.File
    //   335	20	24	arrayOfByte	byte[]
    //   344	14	25	i	int
    //   419	3	28	localIOException10	java.io.IOException
    //   409	3	31	localIOException11	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   125	132	216	java/io/FileNotFoundException
    //   137	213	216	java/io/FileNotFoundException
    //   261	302	216	java/io/FileNotFoundException
    //   307	313	216	java/io/FileNotFoundException
    //   313	330	216	java/io/FileNotFoundException
    //   247	252	253	java/io/IOException
    //   330	337	370	java/io/FileNotFoundException
    //   337	346	370	java/io/FileNotFoundException
    //   352	367	370	java/io/FileNotFoundException
    //   390	395	409	java/io/IOException
    //   400	405	419	java/io/IOException
    //   238	242	430	java/io/IOException
    //   105	122	440	java/io/IOException
    //   464	469	470	java/io/IOException
    //   455	459	478	java/io/IOException
    //   105	122	488	finally
    //   225	234	488	finally
    //   442	451	488	finally
    //   494	498	511	java/io/IOException
    //   503	508	521	java/io/IOException
    //   125	132	531	finally
    //   137	213	531	finally
    //   261	302	531	finally
    //   307	313	531	finally
    //   313	330	531	finally
    //   330	337	543	finally
    //   337	346	543	finally
    //   352	367	543	finally
    //   125	132	551	java/io/IOException
    //   137	213	551	java/io/IOException
    //   261	302	551	java/io/IOException
    //   307	313	551	java/io/IOException
    //   313	330	551	java/io/IOException
    //   330	337	563	java/io/IOException
    //   337	346	563	java/io/IOException
    //   352	367	563	java/io/IOException
    //   105	122	571	java/io/FileNotFoundException
  }
  
  /* Error */
  public static boolean zipFile(String paramString1, String paramString2, java.util.zip.ZipOutputStream paramZipOutputStream)
  {
    // Byte code:
    //   0: ldc 11
    //   2: new 27	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   9: ldc 212
    //   11: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_0
    //   15: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 214
    //   20: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: aload_1
    //   24: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   30: invokestatic 44	com/mediatek/mtklogger/utils/Utils:logv	(Ljava/lang/String;Ljava/lang/String;)V
    //   33: aload_2
    //   34: ifnonnull +16 -> 50
    //   37: ldc 11
    //   39: ldc 216
    //   41: invokestatic 176	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   44: iconst_0
    //   45: istore 4
    //   47: iload 4
    //   49: ireturn
    //   50: new 166	java/io/File
    //   53: dup
    //   54: new 27	java/lang/StringBuilder
    //   57: dup
    //   58: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   61: aload_0
    //   62: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: getstatic 184	java/io/File:separator	Ljava/lang/String;
    //   68: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: aload_1
    //   72: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   78: invokespecial 167	java/io/File:<init>	(Ljava/lang/String;)V
    //   81: astore_3
    //   82: aload_3
    //   83: invokevirtual 170	java/io/File:exists	()Z
    //   86: ifeq +331 -> 417
    //   89: aload_3
    //   90: invokevirtual 219	java/io/File:isFile	()Z
    //   93: ifeq +194 -> 287
    //   96: aconst_null
    //   97: astore 11
    //   99: new 51	java/io/FileInputStream
    //   102: dup
    //   103: aload_3
    //   104: invokespecial 220	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   107: astore 12
    //   109: aload_2
    //   110: new 63	java/util/zip/ZipEntry
    //   113: dup
    //   114: aload_1
    //   115: invokespecial 221	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
    //   118: invokevirtual 227	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
    //   121: sipush 512
    //   124: newarray byte
    //   126: astore 19
    //   128: aload 12
    //   130: aload 19
    //   132: invokevirtual 228	java/io/FileInputStream:read	([B)I
    //   135: istore 20
    //   137: iload 20
    //   139: iconst_m1
    //   140: if_icmple +54 -> 194
    //   143: aload_2
    //   144: aload 19
    //   146: iconst_0
    //   147: iload 20
    //   149: invokevirtual 229	java/util/zip/ZipOutputStream:write	([BII)V
    //   152: goto -24 -> 128
    //   155: astore 17
    //   157: aload 12
    //   159: astore 11
    //   161: ldc 11
    //   163: ldc 93
    //   165: aload 17
    //   167: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   170: iconst_0
    //   171: istore 4
    //   173: aload 11
    //   175: ifnull -128 -> 47
    //   178: aload 11
    //   180: invokevirtual 230	java/io/FileInputStream:close	()V
    //   183: iconst_0
    //   184: ireturn
    //   185: astore 18
    //   187: aload 18
    //   189: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   192: iconst_0
    //   193: ireturn
    //   194: aload_2
    //   195: invokevirtual 233	java/util/zip/ZipOutputStream:closeEntry	()V
    //   198: iconst_1
    //   199: getstatic 16	com/mediatek/mtklogger/taglog/ZipManager:mZippedFilesCount	I
    //   202: iadd
    //   203: putstatic 16	com/mediatek/mtklogger/taglog/ZipManager:mZippedFilesCount	I
    //   206: aload 12
    //   208: ifnull +269 -> 477
    //   211: aload 12
    //   213: invokevirtual 230	java/io/FileInputStream:close	()V
    //   216: iconst_1
    //   217: ireturn
    //   218: astore 21
    //   220: aload 21
    //   222: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   225: iconst_1
    //   226: ireturn
    //   227: astore 13
    //   229: ldc 11
    //   231: ldc 107
    //   233: aload 13
    //   235: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   238: iconst_0
    //   239: istore 4
    //   241: aload 11
    //   243: ifnull -196 -> 47
    //   246: aload 11
    //   248: invokevirtual 230	java/io/FileInputStream:close	()V
    //   251: iconst_0
    //   252: ireturn
    //   253: astore 16
    //   255: aload 16
    //   257: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   260: iconst_0
    //   261: ireturn
    //   262: astore 14
    //   264: aload 11
    //   266: ifnull +8 -> 274
    //   269: aload 11
    //   271: invokevirtual 230	java/io/FileInputStream:close	()V
    //   274: aload 14
    //   276: athrow
    //   277: astore 15
    //   279: aload 15
    //   281: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   284: goto -10 -> 274
    //   287: iconst_1
    //   288: istore 4
    //   290: aload_3
    //   291: invokevirtual 237	java/io/File:list	()[Ljava/lang/String;
    //   294: astore 5
    //   296: aload 5
    //   298: arraylength
    //   299: ifgt +42 -> 341
    //   302: new 63	java/util/zip/ZipEntry
    //   305: dup
    //   306: new 27	java/lang/StringBuilder
    //   309: dup
    //   310: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   313: aload_1
    //   314: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: getstatic 184	java/io/File:separator	Ljava/lang/String;
    //   320: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   326: invokespecial 221	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
    //   329: astore 6
    //   331: aload_2
    //   332: aload 6
    //   334: invokevirtual 227	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
    //   337: aload_2
    //   338: invokevirtual 233	java/util/zip/ZipOutputStream:closeEntry	()V
    //   341: aload 5
    //   343: arraylength
    //   344: istore 8
    //   346: iconst_0
    //   347: istore 9
    //   349: iload 9
    //   351: iload 8
    //   353: if_icmpge -306 -> 47
    //   356: aload 5
    //   358: iload 9
    //   360: aaload
    //   361: astore 10
    //   363: aload_0
    //   364: new 27	java/lang/StringBuilder
    //   367: dup
    //   368: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   371: aload_1
    //   372: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   375: getstatic 184	java/io/File:separator	Ljava/lang/String;
    //   378: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   381: aload 10
    //   383: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   386: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   389: aload_2
    //   390: invokestatic 239	com/mediatek/mtklogger/taglog/ZipManager:zipFile	(Ljava/lang/String;Ljava/lang/String;Ljava/util/zip/ZipOutputStream;)Z
    //   393: ifne +18 -> 411
    //   396: iconst_0
    //   397: ireturn
    //   398: astore 7
    //   400: aload 7
    //   402: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   405: iconst_0
    //   406: istore 4
    //   408: goto -67 -> 341
    //   411: iinc 9 1
    //   414: goto -65 -> 349
    //   417: ldc 11
    //   419: new 27	java/lang/StringBuilder
    //   422: dup
    //   423: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   426: ldc 241
    //   428: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   431: aload_3
    //   432: invokevirtual 244	java/io/File:getPath	()Ljava/lang/String;
    //   435: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   438: ldc 246
    //   440: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   443: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   446: invokestatic 176	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   449: iconst_0
    //   450: ireturn
    //   451: astore 14
    //   453: aload 12
    //   455: astore 11
    //   457: goto -193 -> 264
    //   460: astore 13
    //   462: aload 12
    //   464: astore 11
    //   466: goto -237 -> 229
    //   469: astore 17
    //   471: aconst_null
    //   472: astore 11
    //   474: goto -313 -> 161
    //   477: iconst_1
    //   478: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	479	0	paramString1	String
    //   0	479	1	paramString2	String
    //   0	479	2	paramZipOutputStream	java.util.zip.ZipOutputStream
    //   81	351	3	localFile	java.io.File
    //   45	362	4	bool	boolean
    //   294	63	5	arrayOfString	String[]
    //   329	4	6	localZipEntry	java.util.zip.ZipEntry
    //   398	3	7	localIOException1	java.io.IOException
    //   344	10	8	i	int
    //   347	65	9	j	int
    //   361	21	10	str	String
    //   97	376	11	localObject1	Object
    //   107	356	12	localFileInputStream	java.io.FileInputStream
    //   227	7	13	localIOException2	java.io.IOException
    //   460	1	13	localIOException3	java.io.IOException
    //   262	13	14	localObject2	Object
    //   451	1	14	localObject3	Object
    //   277	3	15	localIOException4	java.io.IOException
    //   253	3	16	localIOException5	java.io.IOException
    //   155	11	17	localFileNotFoundException1	java.io.FileNotFoundException
    //   469	1	17	localFileNotFoundException2	java.io.FileNotFoundException
    //   185	3	18	localIOException6	java.io.IOException
    //   126	19	19	arrayOfByte	byte[]
    //   135	13	20	k	int
    //   218	3	21	localIOException7	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   109	128	155	java/io/FileNotFoundException
    //   128	137	155	java/io/FileNotFoundException
    //   143	152	155	java/io/FileNotFoundException
    //   194	206	155	java/io/FileNotFoundException
    //   178	183	185	java/io/IOException
    //   211	216	218	java/io/IOException
    //   99	109	227	java/io/IOException
    //   246	251	253	java/io/IOException
    //   99	109	262	finally
    //   161	170	262	finally
    //   229	238	262	finally
    //   269	274	277	java/io/IOException
    //   331	341	398	java/io/IOException
    //   109	128	451	finally
    //   128	137	451	finally
    //   143	152	451	finally
    //   194	206	451	finally
    //   109	128	460	java/io/IOException
    //   128	137	460	java/io/IOException
    //   143	152	460	java/io/IOException
    //   194	206	460	java/io/IOException
    //   99	109	469	java/io/FileNotFoundException
  }
  
  /* Error */
  public static boolean zipFileOrFolder(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: ldc 11
    //   2: new 27	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 28	java/lang/StringBuilder:<init>	()V
    //   9: ldc 250
    //   11: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_0
    //   15: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 252
    //   20: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   26: invokestatic 44	com/mediatek/mtklogger/utils/Utils:logv	(Ljava/lang/String;Ljava/lang/String;)V
    //   29: aconst_null
    //   30: astore_2
    //   31: new 223	java/util/zip/ZipOutputStream
    //   34: dup
    //   35: new 186	java/io/FileOutputStream
    //   38: dup
    //   39: aload_1
    //   40: invokespecial 253	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   43: invokespecial 256	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   46: astore_3
    //   47: new 166	java/io/File
    //   50: dup
    //   51: aload_0
    //   52: invokespecial 167	java/io/File:<init>	(Ljava/lang/String;)V
    //   55: astore 4
    //   57: aload 4
    //   59: invokevirtual 259	java/io/File:getParent	()Ljava/lang/String;
    //   62: aload 4
    //   64: invokevirtual 260	java/io/File:getName	()Ljava/lang/String;
    //   67: aload_3
    //   68: invokestatic 239	com/mediatek/mtklogger/taglog/ZipManager:zipFile	(Ljava/lang/String;Ljava/lang/String;Ljava/util/zip/ZipOutputStream;)Z
    //   71: istore 8
    //   73: aload_3
    //   74: invokevirtual 261	java/util/zip/ZipOutputStream:flush	()V
    //   77: aload_3
    //   78: invokevirtual 264	java/util/zip/ZipOutputStream:finish	()V
    //   81: aload_3
    //   82: invokevirtual 265	java/util/zip/ZipOutputStream:close	()V
    //   85: aload_3
    //   86: ifnull +130 -> 216
    //   89: aload_3
    //   90: invokevirtual 265	java/util/zip/ZipOutputStream:close	()V
    //   93: iload 8
    //   95: ireturn
    //   96: astore 12
    //   98: aload 12
    //   100: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   103: iload 8
    //   105: ireturn
    //   106: astore 5
    //   108: ldc 11
    //   110: ldc 93
    //   112: aload 5
    //   114: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   117: iconst_0
    //   118: istore 8
    //   120: aload_2
    //   121: ifnull -28 -> 93
    //   124: aload_2
    //   125: invokevirtual 265	java/util/zip/ZipOutputStream:close	()V
    //   128: iconst_0
    //   129: ireturn
    //   130: astore 9
    //   132: aload 9
    //   134: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   137: iconst_0
    //   138: ireturn
    //   139: astore 10
    //   141: ldc 11
    //   143: ldc 93
    //   145: aload 10
    //   147: invokestatic 97	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   150: iconst_0
    //   151: istore 8
    //   153: aload_2
    //   154: ifnull -61 -> 93
    //   157: aload_2
    //   158: invokevirtual 265	java/util/zip/ZipOutputStream:close	()V
    //   161: iconst_0
    //   162: ireturn
    //   163: astore 11
    //   165: aload 11
    //   167: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   170: iconst_0
    //   171: ireturn
    //   172: astore 6
    //   174: aload_2
    //   175: ifnull +7 -> 182
    //   178: aload_2
    //   179: invokevirtual 265	java/util/zip/ZipOutputStream:close	()V
    //   182: aload 6
    //   184: athrow
    //   185: astore 7
    //   187: aload 7
    //   189: invokevirtual 105	java/io/IOException:printStackTrace	()V
    //   192: goto -10 -> 182
    //   195: astore 6
    //   197: aload_3
    //   198: astore_2
    //   199: goto -25 -> 174
    //   202: astore 10
    //   204: aload_3
    //   205: astore_2
    //   206: goto -65 -> 141
    //   209: astore 5
    //   211: aload_3
    //   212: astore_2
    //   213: goto -105 -> 108
    //   216: iload 8
    //   218: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	paramString1	String
    //   0	219	1	paramString2	String
    //   30	183	2	localObject1	Object
    //   46	166	3	localZipOutputStream	java.util.zip.ZipOutputStream
    //   55	8	4	localFile	java.io.File
    //   106	7	5	localFileNotFoundException1	java.io.FileNotFoundException
    //   209	1	5	localFileNotFoundException2	java.io.FileNotFoundException
    //   172	11	6	localObject2	Object
    //   195	1	6	localObject3	Object
    //   185	3	7	localIOException1	java.io.IOException
    //   71	146	8	bool	boolean
    //   130	3	9	localIOException2	java.io.IOException
    //   139	7	10	localIOException3	java.io.IOException
    //   202	1	10	localIOException4	java.io.IOException
    //   163	3	11	localIOException5	java.io.IOException
    //   96	3	12	localIOException6	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   89	93	96	java/io/IOException
    //   31	47	106	java/io/FileNotFoundException
    //   124	128	130	java/io/IOException
    //   31	47	139	java/io/IOException
    //   157	161	163	java/io/IOException
    //   31	47	172	finally
    //   108	117	172	finally
    //   141	150	172	finally
    //   178	182	185	java/io/IOException
    //   47	85	195	finally
    //   47	85	202	java/io/IOException
    //   47	85	209	java/io/FileNotFoundException
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.taglog.ZipManager
 * JD-Core Version:    0.7.0.1
 */