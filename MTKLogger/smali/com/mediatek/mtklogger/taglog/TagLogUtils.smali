.class public Lcom/mediatek/mtklogger/taglog/TagLogUtils;
.super Ljava/lang/Object;
.source "TagLogUtils.java"


# static fields
.field private static final BUFFER_MIN_NEEDED_LOG_SIZE:J = 0x200000L

.field public static final CALIBRATION_DATA:Ljava/lang/String; = "calibration_data"

.field public static final CALIBRATION_DATA_KEY:Ljava/lang/String; = "calibrationData"

.field private static final FILTER_FILE_TREE_TXT:Ljava/lang/String; = "file_tree.txt"

.field private static final KEY_BASEBAND_VERSION:Ljava/lang/String; = "gsm.version.baseband"

.field private static final KEY_BUILD_NUMBER:Ljava/lang/String; = "ro.build.display.id"

.field private static final KEY_BUILD_PRODUCT:Ljava/lang/String; = "ro.build.product"

.field private static final KEY_BUILD_TYPE:Ljava/lang/String; = "ro.build.type"

.field public static final PAST_TIME:I = 0x2710

.field private static final PRE_FILE_SIZE:I = 0x400

.field private static final TAG:Ljava/lang/String; = "MTKLogger/TagLogUtils"

.field public static final TIMEAWAY:J = 0x927c0L

.field public static final ZIP_LOG_SUFFIX:Ljava/lang/String; = ".zip"

.field public static final ZIP_TAG_LOG_FOLDER:Ljava/lang/String; = "mtklog/taglog"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 32
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static checkSdCardSpace(Ljava/lang/String;)I
    .locals 4
    .parameter "sDPath"

    .prologue
    .line 52
    const-string v1, "MTKLogger/TagLogUtils"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "checkSdCardSpace SDPath: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 53
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 54
    .local v0, sdroot:Ljava/io/File;
    invoke-virtual {p0}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v1

    const-string v2, "sdcard"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 55
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_0

    .line 56
    const-string v1, "MTKLogger/TagLogUtils"

    const-string v2, "The SD Card doesn\'t exist"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 57
    const/16 v1, 0x193

    .line 64
    :goto_0
    return v1

    .line 59
    :cond_0
    invoke-virtual {v0}, Ljava/io/File;->canWrite()Z

    move-result v1

    if-nez v1, :cond_1

    .line 60
    const-string v1, "MTKLogger/TagLogUtils"

    const-string v2, "The SD Card is not writtable"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 61
    const/16 v1, 0x194

    goto :goto_0

    .line 64
    :cond_1
    const/16 v1, 0x191

    goto :goto_0
.end method

.method public static checkSdCardSpace(Ljava/lang/String;[Ljava/lang/String;)I
    .locals 16
    .parameter "sDPath"
    .parameter "logToolPath"

    .prologue
    .line 79
    const-string v12, "MTKLogger/TagLogUtils"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "checkSdCardSpace SDPath: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    move-object/from16 v0, p0

    invoke-virtual {v13, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 80
    const-wide/16 v4, 0x0

    .line 81
    .local v4, logSize:J
    new-instance v8, Ljava/io/File;

    move-object/from16 v0, p0

    invoke-direct {v8, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 82
    .local v8, sdroot:Ljava/io/File;
    invoke-virtual/range {p0 .. p0}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v12

    const-string v13, "sdcard"

    invoke-virtual {v12, v13}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v12

    if-eqz v12, :cond_1

    .line 83
    invoke-virtual {v8}, Ljava/io/File;->exists()Z

    move-result v12

    if-nez v12, :cond_0

    .line 84
    const-string v12, "MTKLogger/TagLogUtils"

    const-string v13, "The SD Card doesn\'t exist"

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 85
    const/16 v12, 0x193

    .line 107
    :goto_0
    return v12

    .line 87
    :cond_0
    invoke-virtual {v8}, Ljava/io/File;->canWrite()Z

    move-result v12

    if-nez v12, :cond_1

    .line 88
    const-string v12, "MTKLogger/TagLogUtils"

    const-string v13, "The SD Card is not writtable"

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 89
    const/16 v12, 0x194

    goto :goto_0

    .line 92
    :cond_1
    new-instance v9, Landroid/os/StatFs;

    invoke-virtual {v8}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v12

    invoke-direct {v9, v12}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    .line 93
    .local v9, stat:Landroid/os/StatFs;
    invoke-virtual {v9}, Landroid/os/StatFs;->getAvailableBlocks()I

    move-result v1

    .line 94
    .local v1, availableBlocks:I
    invoke-virtual {v9}, Landroid/os/StatFs;->getBlockSize()I

    move-result v2

    .line 95
    .local v2, blockSize:I
    int-to-long v12, v1

    int-to-long v14, v2

    mul-long v6, v12, v14

    .line 96
    .local v6, sdAvailableSpace:J
    const-string v12, "MTKLogger/TagLogUtils"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "availableBlocks: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 97
    const-string v12, "MTKLogger/TagLogUtils"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "blockSize: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 98
    const/4 v3, 0x0

    .local v3, i:I
    :goto_1
    move-object/from16 v0, p1

    array-length v12, v0

    if-ge v3, v12, :cond_2

    .line 99
    aget-object v12, p1, v3

    invoke-static {v12}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getFolderOrFileSize(Ljava/lang/String;)J

    move-result-wide v10

    .line 104
    .local v10, tempSize:J
    add-long/2addr v4, v10

    .line 98
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 106
    .end local v10           #tempSize:J
    :cond_2
    const-string v12, "MTKLogger/TagLogUtils"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "sdAvailableSpace is: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v6, v7}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v13

    const-string v14, ", logSize: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 107
    cmp-long v12, v6, v4

    if-lez v12, :cond_3

    const/16 v12, 0x191

    goto/16 :goto_0

    :cond_3
    const/16 v12, 0x192

    goto/16 :goto_0
.end method

.method public static createTagLogFolder(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 5
    .parameter "logToolPath"
    .parameter "tag"

    .prologue
    .line 503
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "/"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "mtklog/taglog"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "/"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "TagLog_"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/taglog/ZipManager;->translateTime2(J)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    if-eqz p1, :cond_0

    const-string v2, ""

    invoke-virtual {v2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    :cond_0
    const-string v2, ""

    :goto_0
    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 506
    .local v0, logPath:Ljava/lang/String;
    const-string v2, "MTKLogger/TagLogUtils"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "createTagLogFolder : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 507
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 508
    .local v1, tagLogFolder:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    if-nez v2, :cond_1

    .line 509
    invoke-virtual {v1}, Ljava/io/File;->mkdirs()Z

    .line 511
    :cond_1
    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    return-object v2

    .line 503
    .end local v0           #logPath:Ljava/lang/String;
    .end local v1           #tagLogFolder:Ljava/io/File;
    :cond_2
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "_"

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    goto :goto_0
.end method

.method public static getBuildNumber()Ljava/lang/String;
    .locals 8

    .prologue
    .line 551
    const-string v5, "ro.build.display.id"

    invoke-static {v5}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 552
    .local v1, buildNumberStr:Ljava/lang/String;
    const-string v5, "ro.build.product"

    invoke-static {v5}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 553
    .local v2, buildProduct:Ljava/lang/String;
    const-string v5, "ro.build.type"

    invoke-static {v5}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 554
    .local v3, buildType:Ljava/lang/String;
    const-string v5, "gsm.version.baseband"

    invoke-static {v5}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 555
    .local v0, basebandVersion:Ljava/lang/String;
    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "===Build Version Information==="

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 556
    .local v4, result:Ljava/lang/StringBuilder;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "\nBuild Number: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\nThe production is "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, " with "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " build"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\nAnd the baseband version is "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "\n"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 561
    const-string v5, "MTKLogger/TagLogUtils"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Build number is "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 562
    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    return-object v5
.end method

.method public static getCurrentLogFolder([Ljava/io/File;Z)Ljava/io/File;
    .locals 17
    .parameter "logFolderList"
    .parameter "isFromReboot"

    .prologue
    .line 364
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "-->getCurrentLogFolder()"

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 365
    const-wide/32 v11, 0x927c0

    .line 366
    .local v11, timeAway:J
    const/4 v6, 0x0

    .line 367
    .local v6, neededFile:Ljava/io/File;
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v7

    .line 368
    .local v7, nowTime:J
    const-string v13, "MTKLogger/TagLogUtils"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Current time="

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/taglog/ZipManager;->translateTime(J)Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 369
    move-object/from16 v0, p0

    .local v0, arr$:[Ljava/io/File;
    array-length v3, v0

    .local v3, len$:I
    const/4 v2, 0x0

    .local v2, i$:I
    :goto_0
    if-ge v2, v3, :cond_3

    aget-object v1, v0, v2

    .line 370
    .local v1, file:Ljava/io/File;
    const-string v13, "file_tree.txt"

    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_1

    .line 369
    :cond_0
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 373
    :cond_1
    invoke-static {v1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getFolderLastModifyTime(Ljava/io/File;)J

    move-result-wide v4

    .line 376
    .local v4, modifiedTime:J
    if-eqz p1, :cond_2

    sub-long v13, v7, v4

    invoke-static {v13, v14}, Ljava/lang/Math;->abs(J)J

    move-result-wide v13

    const-wide/16 v15, 0x2710

    cmp-long v13, v13, v15

    if-ltz v13, :cond_0

    .line 379
    :cond_2
    sub-long v13, v7, v4

    invoke-static {v13, v14}, Ljava/lang/Math;->abs(J)J

    move-result-wide v13

    cmp-long v13, v13, v11

    if-gez v13, :cond_0

    .line 380
    sub-long v13, v7, v4

    invoke-static {v13, v14}, Ljava/lang/Math;->abs(J)J

    move-result-wide v11

    .line 381
    move-object v6, v1

    goto :goto_1

    .line 384
    .end local v1           #file:Ljava/io/File;
    .end local v4           #modifiedTime:J
    :cond_3
    if-eqz v6, :cond_4

    .line 385
    const-string v13, "MTKLogger/TagLogUtils"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Selected log folder name=["

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v6}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 404
    :goto_2
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "<--getCurrentLogFolder()"

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 405
    return-object v6

    .line 387
    :cond_4
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "Could not get needed log folder."

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 388
    const-wide/16 v9, 0x0

    .line 389
    .local v9, temp:J
    move-object/from16 v0, p0

    array-length v3, v0

    const/4 v2, 0x0

    :goto_3
    if-ge v2, v3, :cond_6

    aget-object v1, v0, v2

    .line 390
    .restart local v1       #file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->lastModified()J

    move-result-wide v4

    .line 391
    .restart local v4       #modifiedTime:J
    cmp-long v13, v4, v9

    if-lez v13, :cond_5

    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v13

    if-eqz v13, :cond_5

    .line 392
    move-wide v9, v4

    .line 393
    move-object v6, v1

    .line 389
    :cond_5
    add-int/lit8 v2, v2, 0x1

    goto :goto_3

    .line 397
    .end local v1           #file:Ljava/io/File;
    .end local v4           #modifiedTime:J
    :cond_6
    if-eqz v6, :cond_7

    .line 398
    const-string v13, "MTKLogger/TagLogUtils"

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Selected log folder name=["

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v6}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, "], last modified time="

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v6}, Ljava/io/File;->lastModified()J

    move-result-wide v15

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/ZipManager;->translateTime(J)Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 401
    :cond_7
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "There is no folder"

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2
.end method

.method public static getCurrentLogFolderFromPath(Ljava/lang/String;)Ljava/io/File;
    .locals 1
    .parameter "folderPath"

    .prologue
    .line 409
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;Z)Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public static getCurrentLogFolderFromPath(Ljava/lang/String;Z)Ljava/io/File;
    .locals 6
    .parameter "folderPath"
    .parameter "isFromReboot"

    .prologue
    const/4 v2, 0x0

    .line 419
    const-string v3, "MTKLogger/TagLogUtils"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "-->get currentLog folder in "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 421
    if-nez p0, :cond_0

    .line 440
    :goto_0
    return-object v2

    .line 424
    :cond_0
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 425
    .local v0, logFolder:Ljava/io/File;
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v3

    if-nez v3, :cond_1

    .line 426
    const-string v3, "MTKLogger/TagLogUtils"

    const-string v4, "getCurrentLogFolder() the folder isn\'t exist!"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 429
    :cond_1
    invoke-virtual {v0}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v1

    .line 430
    .local v1, logFolderList:[Ljava/io/File;
    if-nez v1, :cond_2

    .line 431
    const-string v3, "MTKLogger/TagLogUtils"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "No promission to access "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 434
    :cond_2
    array-length v3, v1

    if-gtz v3, :cond_3

    .line 435
    const-string v3, "MTKLogger/TagLogUtils"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "There is no folder in "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 439
    :cond_3
    const-string v2, "MTKLogger/TagLogUtils"

    const-string v3, "<--getCurrentLogFolder()"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 440
    invoke-static {v1, p1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolder([Ljava/io/File;Z)Ljava/io/File;

    move-result-object v2

    goto :goto_0
.end method

.method public static getFolderFilesCount(Ljava/lang/String;)I
    .locals 8
    .parameter "filePath"

    .prologue
    .line 284
    const/4 v6, 0x0

    .line 285
    .local v6, size:I
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 286
    .local v2, fileRoot:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_0

    .line 287
    const/4 v7, 0x0

    .line 297
    :goto_0
    return v7

    .line 289
    :cond_0
    invoke-virtual {v2}, Ljava/io/File;->isDirectory()Z

    move-result v7

    if-nez v7, :cond_2

    .line 290
    add-int/lit8 v6, v6, 0x1

    :cond_1
    move v7, v6

    .line 297
    goto :goto_0

    .line 292
    :cond_2
    invoke-virtual {v2}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v3

    .line 293
    .local v3, files:[Ljava/io/File;
    move-object v0, v3

    .local v0, arr$:[Ljava/io/File;
    array-length v5, v0

    .local v5, len$:I
    const/4 v4, 0x0

    .local v4, i$:I
    :goto_1
    if-ge v4, v5, :cond_1

    aget-object v1, v0, v4

    .line 294
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getFolderFilesCount(Ljava/lang/String;)I

    move-result v7

    add-int/2addr v6, v7

    .line 293
    add-int/lit8 v4, v4, 0x1

    goto :goto_1
.end method

.method public static getFolderLastModifyTime(Ljava/io/File;)J
    .locals 17
    .parameter "file"

    .prologue
    .line 453
    const-wide/16 v6, 0x0

    .line 454
    .local v6, result:J
    if-eqz p0, :cond_0

    invoke-virtual/range {p0 .. p0}, Ljava/io/File;->exists()Z

    move-result v13

    if-nez v13, :cond_1

    .line 455
    :cond_0
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "Given file not exist."

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v8, v6

    .line 491
    .end local v6           #result:J
    .local v8, result:J
    :goto_0
    return-wide v8

    .line 458
    .end local v8           #result:J
    .restart local v6       #result:J
    :cond_1
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    .line 459
    .local v1, currentTime:J
    invoke-virtual/range {p0 .. p0}, Ljava/io/File;->isFile()Z

    move-result v13

    if-eqz v13, :cond_3

    .line 460
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "You should give me a folder. But still can work here."

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 461
    invoke-virtual/range {p0 .. p0}, Ljava/io/File;->lastModified()J

    move-result-wide v11

    .line 463
    .local v11, time:J
    sub-long v13, v1, v11

    const-wide/16 v15, -0x2710

    cmp-long v13, v13, v15

    if-lez v13, :cond_2

    .line 465
    move-wide v6, v11

    .end local v11           #time:J
    :cond_2
    move-wide v8, v6

    .line 491
    .end local v6           #result:J
    .restart local v8       #result:J
    goto :goto_0

    .line 468
    .end local v8           #result:J
    .restart local v6       #result:J
    :cond_3
    invoke-virtual/range {p0 .. p0}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v3

    .line 469
    .local v3, fileList:[Ljava/io/File;
    if-nez v3, :cond_4

    .line 470
    const-string v13, "MTKLogger/TagLogUtils"

    const-string v14, "No promission to access "

    invoke-static {v13, v14}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v8, v6

    .line 471
    .end local v6           #result:J
    .restart local v8       #result:J
    goto :goto_0

    .line 473
    .end local v8           #result:J
    .restart local v6       #result:J
    :cond_4
    move-object v0, v3

    .local v0, arr$:[Ljava/io/File;
    array-length v5, v0

    .local v5, len$:I
    const/4 v4, 0x0

    .local v4, i$:I
    :goto_1
    if-ge v4, v5, :cond_2

    aget-object v10, v0, v4

    .line 474
    .local v10, subFile:Ljava/io/File;
    const-wide/16 v11, 0x0

    .line 475
    .restart local v11       #time:J
    invoke-virtual {v10}, Ljava/io/File;->isFile()Z

    move-result v13

    if-eqz v13, :cond_7

    .line 477
    invoke-virtual {v10}, Ljava/io/File;->lastModified()J

    move-result-wide v11

    .line 478
    sub-long v13, v1, v11

    const-wide/16 v15, -0x2710

    cmp-long v13, v13, v15

    if-gez v13, :cond_5

    .line 480
    const-wide/16 v11, 0x0

    .line 485
    :cond_5
    :goto_2
    sub-long v13, v11, v1

    invoke-static {v13, v14}, Ljava/lang/Math;->abs(J)J

    move-result-wide v13

    sub-long v15, v6, v1

    invoke-static/range {v15 .. v16}, Ljava/lang/Math;->abs(J)J

    move-result-wide v15

    cmp-long v13, v13, v15

    if-gez v13, :cond_6

    .line 486
    move-wide v6, v11

    .line 473
    :cond_6
    add-int/lit8 v4, v4, 0x1

    goto :goto_1

    .line 483
    :cond_7
    invoke-static {v10}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getFolderLastModifyTime(Ljava/io/File;)J

    move-result-wide v11

    goto :goto_2
.end method

.method public static getFolderOrFileSize(Ljava/lang/String;)J
    .locals 10
    .parameter "filePath"

    .prologue
    .line 308
    const-wide/16 v6, 0x0

    .line 309
    .local v6, size:J
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 310
    .local v2, fileRoot:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v8

    if-nez v8, :cond_0

    .line 311
    const-wide/16 v8, 0x0

    .line 321
    :goto_0
    return-wide v8

    .line 313
    :cond_0
    invoke-virtual {v2}, Ljava/io/File;->isDirectory()Z

    move-result v8

    if-nez v8, :cond_2

    .line 314
    invoke-virtual {v2}, Ljava/io/File;->length()J

    move-result-wide v6

    :cond_1
    move-wide v8, v6

    .line 321
    goto :goto_0

    .line 316
    :cond_2
    invoke-virtual {v2}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v3

    .line 317
    .local v3, files:[Ljava/io/File;
    move-object v0, v3

    .local v0, arr$:[Ljava/io/File;
    array-length v5, v0

    .local v5, len$:I
    const/4 v4, 0x0

    .local v4, i$:I
    :goto_1
    if-ge v4, v5, :cond_1

    aget-object v1, v0, v4

    .line 318
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getFolderOrFileSize(Ljava/lang/String;)J

    move-result-wide v8

    add-long/2addr v6, v8

    .line 317
    add-int/lit8 v4, v4, 0x1

    goto :goto_1
.end method

.method public static getIMEI(Landroid/content/Context;)Ljava/lang/String;
    .locals 8
    .parameter "context"

    .prologue
    .line 524
    const-string v5, "phone"

    invoke-virtual {p0, v5}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/telephony/TelephonyManager;

    .line 526
    .local v4, telephonyManager:Landroid/telephony/TelephonyManager;
    const-string v0, "The device IMEI is "

    .line 528
    .local v0, imeiStr:Ljava/lang/String;
    invoke-static {}, Lcom/mediatek/telephony/TelephonyManagerEx;->getDefault()Lcom/mediatek/telephony/TelephonyManagerEx;

    move-result-object v1

    .line 529
    .local v1, mTelephonyManager:Lcom/mediatek/telephony/TelephonyManagerEx;
    const/4 v5, 0x0

    invoke-virtual {v1, v5}, Lcom/mediatek/telephony/TelephonyManagerEx;->getDeviceId(I)Ljava/lang/String;

    move-result-object v2

    .line 530
    .local v2, sim1IMEIStr:Ljava/lang/String;
    const/4 v5, 0x1

    invoke-virtual {v1, v5}, Lcom/mediatek/telephony/TelephonyManagerEx;->getDeviceId(I)Ljava/lang/String;

    move-result-object v3

    .line 531
    .local v3, sim2IMEIStr:Ljava/lang/String;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " SIM1: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", SIM2: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 537
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "\n"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 538
    const-string v5, "MTKLogger/TagLogUtils"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Flashed IMEI? "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 539
    return-object v0
.end method

.method public static getIVSR(Landroid/content/Context;)Ljava/lang/String;
    .locals 7
    .parameter "context"

    .prologue
    .line 543
    const-string v3, "ivsr_setting"

    .line 544
    .local v3, keyIVSR:Ljava/lang/String;
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-wide/16 v5, 0x0

    invoke-static {v4, v3, v5, v6}, Landroid/provider/Settings$System;->getLong(Landroid/content/ContentResolver;Ljava/lang/String;J)J

    move-result-wide v0

    .line 545
    .local v0, checked:J
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "The IVSR is "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-static {v0, v1}, Ljava/lang/String;->valueOf(J)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "\n"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 546
    .local v2, ivsr:Ljava/lang/String;
    const-string v4, "MTKLogger/TagLogUtils"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "IVSR enable status: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 547
    return-object v2
.end method

.method public static getTagLogNeededSize(Ljava/util/List;)J
    .locals 10
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List",
            "<",
            "Lcom/mediatek/mtklogger/taglog/LogInformation;",
            ">;)J"
        }
    .end annotation

    .prologue
    .local p0, logSizeInfoList:Ljava/util/List;,"Ljava/util/List<Lcom/mediatek/mtklogger/taglog/LogInformation;>;"
    const-wide/16 v6, 0x0

    .line 325
    const-wide/16 v2, 0x0

    .line 326
    .local v2, minNeededLogSize:J
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v8

    if-nez v8, :cond_0

    .line 353
    :goto_0
    return-wide v6

    .line 329
    :cond_0
    new-instance v8, Lcom/mediatek/mtklogger/taglog/TagLogUtils$1;

    invoke-direct {v8}, Lcom/mediatek/mtklogger/taglog/TagLogUtils$1;-><init>()V

    invoke-static {p0, v8}, Ljava/util/Collections;->sort(Ljava/util/List;Ljava/util/Comparator;)V

    .line 338
    const-wide/16 v4, 0x0

    .line 339
    .local v4, sumSaveSpace:J
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :goto_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_3

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 340
    .local v1, logSizeInfo:Lcom/mediatek/mtklogger/taglog/LogInformation;
    cmp-long v8, v4, v6

    if-nez v8, :cond_1

    .line 342
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getTagLogSize()J

    move-result-wide v2

    .line 343
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getSaveSpace()J

    move-result-wide v4

    .line 344
    goto :goto_1

    .line 346
    :cond_1
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getTagLogSize()J

    move-result-wide v8

    cmp-long v8, v4, v8

    if-gez v8, :cond_2

    .line 347
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getTagLogSize()J

    move-result-wide v8

    sub-long/2addr v8, v4

    add-long/2addr v2, v8

    .line 349
    :cond_2
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getSaveSpace()J

    move-result-wide v8

    add-long/2addr v4, v8

    goto :goto_1

    .line 351
    .end local v1           #logSizeInfo:Lcom/mediatek/mtklogger/taglog/LogInformation;
    :cond_3
    const-wide/32 v6, 0x200000

    add-long/2addr v2, v6

    .line 352
    const-string v6, "MTKLogger/TagLogUtils"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "getTagLogNeededSize(): "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v6, v2

    .line 353
    goto :goto_0
.end method

.method public static hasCalibrationData(Landroid/content/Context;)Z
    .locals 5
    .parameter "context"

    .prologue
    const/4 v3, 0x0

    .line 566
    const-string v2, "calibration_data"

    invoke-virtual {p0, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    .line 568
    .local v1, preferences:Landroid/content/SharedPreferences;
    const-string v2, "calibrationData"

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0

    .line 569
    .local v0, bCalibrated:Z
    const-string v2, "MTKLogger/TagLogUtils"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Calibration data is written? "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 570
    return v0
.end method

.method public static writeCheckSOPToFile(Landroid/content/Context;Ljava/io/File;)Z
    .locals 12
    .parameter "context"
    .parameter "file"

    .prologue
    .line 574
    if-eqz p1, :cond_0

    invoke-virtual {p1}, Ljava/io/File;->exists()Z

    move-result v9

    if-eqz v9, :cond_0

    invoke-virtual {p1}, Ljava/io/File;->isFile()Z

    move-result v9

    if-eqz v9, :cond_0

    invoke-virtual {p1}, Ljava/io/File;->canWrite()Z

    move-result v9

    if-nez v9, :cond_1

    .line 575
    :cond_0
    const/4 v9, 0x0

    .line 598
    :goto_0
    return v9

    .line 577
    :cond_1
    invoke-static {p0}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->hasCalibrationData(Landroid/content/Context;)Z

    move-result v0

    .line 578
    .local v0, bCalibrated:Z
    invoke-static {p0}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getIVSR(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v6

    .line 579
    .local v6, ivsr:Ljava/lang/String;
    invoke-static {p0}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getIMEI(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v5

    .line 580
    .local v5, imei:Ljava/lang/String;
    invoke-static {}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getBuildNumber()Ljava/lang/String;

    move-result-object v1

    .line 581
    .local v1, buildNumber:Ljava/lang/String;
    new-instance v2, Ljava/lang/String;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Calibration data is downloaded: "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "\n"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-direct {v2, v9}, Ljava/lang/String;-><init>(Ljava/lang/String;)V

    .line 582
    .local v2, calibration:Ljava/lang/String;
    new-instance v3, Ljava/lang/StringBuffer;

    const-string v9, "Check SOP result:\n"

    invoke-direct {v3, v9}, Ljava/lang/StringBuffer;-><init>(Ljava/lang/String;)V

    .line 583
    .local v3, content:Ljava/lang/StringBuffer;
    invoke-virtual {v3, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v9

    invoke-virtual {v9, v5}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v9

    invoke-virtual {v9, v6}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v9

    invoke-virtual {v9, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 585
    :try_start_0
    new-instance v7, Ljava/io/FileOutputStream;

    invoke-direct {v7, p1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    .line 586
    .local v7, outStream:Ljava/io/FileOutputStream;
    new-instance v8, Ljava/io/OutputStreamWriter;

    invoke-direct {v8, v7}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;)V

    .line 587
    .local v8, outStreamWriter:Ljava/io/OutputStreamWriter;
    invoke-virtual {v3}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/io/OutputStreamWriter;->write(Ljava/lang/String;)V

    .line 588
    invoke-virtual {v8}, Ljava/io/OutputStreamWriter;->flush()V

    .line 589
    invoke-virtual {v8}, Ljava/io/OutputStreamWriter;->close()V

    .line 590
    const-string v9, "MTKLogger/TagLogUtils"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "The check sop string is "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v3}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 591
    const-string v9, "MTKLogger/TagLogUtils"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Write the check sop to file: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/io/FileNotFoundException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    .line 598
    .end local v7           #outStream:Ljava/io/FileOutputStream;
    .end local v8           #outStreamWriter:Ljava/io/OutputStreamWriter;
    :goto_1
    const/4 v9, 0x1

    goto/16 :goto_0

    .line 592
    :catch_0
    move-exception v4

    .line 593
    .local v4, e:Ljava/io/FileNotFoundException;
    invoke-virtual {v4}, Ljava/io/FileNotFoundException;->printStackTrace()V

    goto :goto_1

    .line 594
    .end local v4           #e:Ljava/io/FileNotFoundException;
    :catch_1
    move-exception v4

    .line 595
    .local v4, e:Ljava/io/IOException;
    invoke-virtual {v4}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1
.end method

.method public static writeDBFile(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 12
    .parameter "path"
    .parameter "newPath"

    .prologue
    const/4 v8, 0x0

    .line 239
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 240
    .local v1, fileIn:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v9

    if-nez v9, :cond_0

    .line 241
    const-string v9, "MTKLogger/TagLogUtils"

    const-string v10, "Log isn\'t exist!"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 273
    :goto_0
    return v8

    .line 244
    :cond_0
    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v2

    .line 245
    .local v2, fileName:Ljava/lang/String;
    new-instance v3, Ljava/io/File;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v9, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "/"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-direct {v3, v9}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 246
    .local v3, fileOut:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v9

    if-nez v9, :cond_1

    .line 248
    :try_start_0
    invoke-virtual {v3}, Ljava/io/File;->createNewFile()Z
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    .line 254
    :cond_1
    const-string v9, "MTKLogger/TagLogUtils"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "create new log file :"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v3}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 256
    :try_start_1
    new-instance v4, Ljava/io/FileInputStream;

    invoke-direct {v4, v1}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    .line 257
    .local v4, fis:Ljava/io/FileInputStream;
    new-instance v5, Ljava/io/FileOutputStream;

    invoke-direct {v5, v3}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    .line 258
    .local v5, fos:Ljava/io/FileOutputStream;
    const/16 v9, 0x400

    new-array v7, v9, [B

    .line 260
    .local v7, temp:[B
    :goto_1
    invoke-virtual {v4, v7}, Ljava/io/FileInputStream;->read([B)I

    move-result v6

    .local v6, len:I
    const/4 v9, -0x1

    if-eq v6, v9, :cond_2

    .line 261
    const/4 v9, 0x0

    invoke-virtual {v5, v7, v9, v6}, Ljava/io/FileOutputStream;->write([BII)V
    :try_end_1
    .catch Ljava/io/FileNotFoundException; {:try_start_1 .. :try_end_1} :catch_0
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_2

    goto :goto_1

    .line 266
    .end local v4           #fis:Ljava/io/FileInputStream;
    .end local v5           #fos:Ljava/io/FileOutputStream;
    .end local v6           #len:I
    .end local v7           #temp:[B
    :catch_0
    move-exception v0

    .line 267
    .local v0, e:Ljava/io/FileNotFoundException;
    invoke-virtual {v0}, Ljava/io/FileNotFoundException;->printStackTrace()V

    goto :goto_0

    .line 249
    .end local v0           #e:Ljava/io/FileNotFoundException;
    :catch_1
    move-exception v0

    .line 250
    .local v0, e:Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_0

    .line 263
    .end local v0           #e:Ljava/io/IOException;
    .restart local v4       #fis:Ljava/io/FileInputStream;
    .restart local v5       #fos:Ljava/io/FileOutputStream;
    .restart local v6       #len:I
    .restart local v7       #temp:[B
    :cond_2
    :try_start_2
    invoke-virtual {v5}, Ljava/io/FileOutputStream;->flush()V

    .line 264
    invoke-virtual {v5}, Ljava/io/FileOutputStream;->close()V

    .line 265
    invoke-virtual {v4}, Ljava/io/FileInputStream;->close()V
    :try_end_2
    .catch Ljava/io/FileNotFoundException; {:try_start_2 .. :try_end_2} :catch_0
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2

    .line 273
    const/4 v8, 0x1

    goto :goto_0

    .line 269
    .end local v4           #fis:Ljava/io/FileInputStream;
    .end local v5           #fos:Ljava/io/FileOutputStream;
    .end local v6           #len:I
    .end local v7           #temp:[B
    :catch_2
    move-exception v0

    .line 270
    .restart local v0       #e:Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_0
.end method

.method public static writeFolderToTagFolder(Ljava/lang/String;Ljava/lang/String;)V
    .locals 9
    .parameter "dbFolderPath"
    .parameter "tagLogFolderPath"

    .prologue
    .line 202
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 204
    .local v2, dir:Ljava/io/File;
    if-eqz v2, :cond_0

    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v6

    if-nez v6, :cond_1

    .line 228
    :cond_0
    :goto_0
    return-void

    .line 207
    :cond_1
    const-string v6, "MTKLogger/TagLogUtils"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "write Log to tag folder"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "--> "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 209
    invoke-virtual {v2}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v3

    .line 210
    .local v3, files:[Ljava/io/File;
    if-nez v3, :cond_2

    .line 212
    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v5

    .line 213
    .local v5, tagLogPath:Ljava/lang/String;
    const-string v6, "MTKLogger/TagLogUtils"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Log path: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 214
    invoke-static {v5, p1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->writeDBFile(Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_0

    .line 218
    .end local v5           #tagLogPath:Ljava/lang/String;
    :cond_2
    invoke-virtual {v2}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v1

    .line 219
    .local v1, dbSubFolderPath:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "/"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v0, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 220
    .local v0, dbSubFolderNew:Ljava/io/File;
    const-string v6, "MTKLogger/TagLogUtils"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "SubFolderNew: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 221
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v6

    if-nez v6, :cond_3

    .line 222
    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    .line 224
    :cond_3
    const/4 v4, 0x0

    .local v4, i:I
    :goto_1
    array-length v6, v3

    if-ge v4, v6, :cond_0

    .line 225
    aget-object v6, v3, v4

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->writeFolderToTagFolder(Ljava/lang/String;Ljava/lang/String;)V

    .line 224
    add-int/lit8 v4, v4, 0x1

    goto :goto_1
.end method

.method public static writeTagLog(Ljava/lang/String;Ljava/lang/String;)V
    .locals 10
    .parameter "path"
    .parameter "newPath"

    .prologue
    .line 130
    new-instance v3, Ljava/io/File;

    invoke-direct {v3, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 131
    .local v3, fileIn:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_0

    .line 132
    const-string v7, "MTKLogger/TagLogUtils"

    const-string v8, "Log isn\'t exist!"

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 162
    :goto_0
    return-void

    .line 135
    :cond_0
    invoke-virtual {v3}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v4

    .line 136
    .local v4, fileName:Ljava/lang/String;
    new-instance v5, Ljava/io/File;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "/"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v5, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 137
    .local v5, fileOut:Ljava/io/File;
    invoke-virtual {v5}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_1

    .line 139
    :try_start_0
    invoke-virtual {v5}, Ljava/io/File;->createNewFile()Z
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    .line 144
    :cond_1
    :goto_1
    const-string v7, "MTKLogger/TagLogUtils"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "create tag log file :"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v5}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 146
    :try_start_1
    new-instance v0, Ljava/io/BufferedReader;

    new-instance v7, Ljava/io/FileReader;

    invoke-direct {v7, v3}, Ljava/io/FileReader;-><init>(Ljava/io/File;)V

    invoke-direct {v0, v7}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 147
    .local v0, br:Ljava/io/BufferedReader;
    new-instance v1, Ljava/io/BufferedWriter;

    new-instance v7, Ljava/io/FileWriter;

    invoke-direct {v7, v5}, Ljava/io/FileWriter;-><init>(Ljava/io/File;)V

    invoke-direct {v1, v7}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    .line 148
    .local v1, bw:Ljava/io/BufferedWriter;
    invoke-virtual {v0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v6

    .line 149
    .local v6, result:Ljava/lang/String;
    :goto_2
    if-eqz v6, :cond_2

    .line 150
    const/4 v7, 0x0

    invoke-virtual {v6}, Ljava/lang/String;->length()I

    move-result v8

    invoke-virtual {v1, v6, v7, v8}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;II)V

    .line 151
    invoke-virtual {v0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v6

    .line 152
    invoke-virtual {v1}, Ljava/io/BufferedWriter;->flush()V
    :try_end_1
    .catch Ljava/io/FileNotFoundException; {:try_start_1 .. :try_end_1} :catch_0
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_2

    goto :goto_2

    .line 157
    .end local v0           #br:Ljava/io/BufferedReader;
    .end local v1           #bw:Ljava/io/BufferedWriter;
    .end local v6           #result:Ljava/lang/String;
    :catch_0
    move-exception v2

    .line 158
    .local v2, e:Ljava/io/FileNotFoundException;
    invoke-virtual {v2}, Ljava/io/FileNotFoundException;->printStackTrace()V

    goto :goto_0

    .line 140
    .end local v2           #e:Ljava/io/FileNotFoundException;
    :catch_1
    move-exception v2

    .line 141
    .local v2, e:Ljava/io/IOException;
    invoke-virtual {v2}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1

    .line 154
    .end local v2           #e:Ljava/io/IOException;
    .restart local v0       #br:Ljava/io/BufferedReader;
    .restart local v1       #bw:Ljava/io/BufferedWriter;
    .restart local v6       #result:Ljava/lang/String;
    :cond_2
    :try_start_2
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V

    .line 155
    invoke-virtual {v1}, Ljava/io/BufferedWriter;->close()V
    :try_end_2
    .catch Ljava/io/FileNotFoundException; {:try_start_2 .. :try_end_2} :catch_0
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2

    goto :goto_0

    .line 159
    .end local v0           #br:Ljava/io/BufferedReader;
    .end local v1           #bw:Ljava/io/BufferedWriter;
    .end local v6           #result:Ljava/lang/String;
    :catch_2
    move-exception v2

    .line 160
    .restart local v2       #e:Ljava/io/IOException;
    invoke-virtual {v2}, Ljava/io/IOException;->printStackTrace()V

    goto/16 :goto_0
.end method

.method public static writeTagLogFolder(Ljava/lang/String;Ljava/lang/String;)V
    .locals 7
    .parameter "folderPath"
    .parameter "newPath"

    .prologue
    .line 171
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 173
    .local v0, dir:Ljava/io/File;
    if-eqz v0, :cond_0

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v4

    if-nez v4, :cond_1

    .line 191
    :cond_0
    :goto_0
    return-void

    .line 176
    :cond_1
    const-string v4, "MTKLogger/TagLogUtils"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "write Log to tag folder"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "--> "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 178
    invoke-virtual {v0}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v1

    .line 179
    .local v1, files:[Ljava/io/File;
    if-nez v1, :cond_2

    .line 181
    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    .line 182
    .local v3, tagLogPath:Ljava/lang/String;
    const-string v4, "MTKLogger/TagLogUtils"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Log path: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 183
    invoke-static {v3, p1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->writeTagLog(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 187
    .end local v3           #tagLogPath:Ljava/lang/String;
    :cond_2
    const/4 v2, 0x0

    .local v2, i:I
    :goto_1
    array-length v4, v1

    if-ge v2, v4, :cond_0

    .line 188
    aget-object v4, v1, v2

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4, p1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->writeTagLogFolder(Ljava/lang/String;Ljava/lang/String;)V

    .line 187
    add-int/lit8 v2, v2, 0x1

    goto :goto_1
.end method
