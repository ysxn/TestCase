.class public Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;
.super Ljava/lang/Object;
.source "MDLoggerClearLog.java"


# static fields
.field private static final LOG_FOLDER_BEGIN:Ljava/lang/String; = "MDLog"

.field private static final TAG:Ljava/lang/String; = "MTKLogger/MDLoggerClearLog"

.field private static mInstance:Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;


# instance fields
.field private eeFolderArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private folderTreeListArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mASTL1Array:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mDAKArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mDMDSPMLTArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mL1Array:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mLogNeedToBeClearSize:J

.field private mMD2GMLTArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mMdlogPath:Ljava/lang/String;

.field private mNormalContainBin:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mPSArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mRunningArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mTotalLogSizeLimit:I

.field private normalFolderArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private otherFolderArray:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private otherFolderSize:J

.field private sd_mdlog_root:Ljava/lang/String;

.field private strStartWithString:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 54
    const/4 v0, 0x0

    sput-object v0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mInstance:Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;

    return-void
.end method

.method private constructor <init>()V
    .locals 1

    .prologue
    const/4 v0, 0x0

    .line 77
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 53
    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->strStartWithString:Ljava/lang/String;

    .line 55
    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->sd_mdlog_root:Ljava/lang/String;

    .line 56
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    .line 57
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    .line 58
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    .line 59
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    .line 60
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    .line 62
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    .line 65
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    .line 66
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    .line 67
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    .line 68
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    .line 69
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    .line 70
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    .line 78
    return-void
.end method

.method private calculateFolderSize(Ljava/io/File;)J
    .locals 9
    .parameter "root"

    .prologue
    .line 116
    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v5

    .line 118
    .local v5, size:J
    invoke-virtual {p1}, Ljava/io/File;->isDirectory()Z

    move-result v7

    if-eqz v7, :cond_0

    .line 119
    invoke-virtual {p1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    .line 121
    .local v2, files:[Ljava/io/File;
    if-eqz v2, :cond_0

    .line 122
    move-object v0, v2

    .local v0, arr$:[Ljava/io/File;
    array-length v4, v0

    .local v4, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_0
    if-ge v3, v4, :cond_0

    aget-object v1, v0, v3

    .line 123
    .local v1, file:Ljava/io/File;
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->getFileSize(Ljava/io/File;)J

    move-result-wide v7

    add-long/2addr v5, v7

    .line 122
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 127
    .end local v0           #arr$:[Ljava/io/File;
    .end local v1           #file:Ljava/io/File;
    .end local v2           #files:[Ljava/io/File;
    .end local v3           #i$:I
    .end local v4           #len$:I
    :cond_0
    return-wide v5
.end method

.method private checkAndRemoveRunningFolder()Z
    .locals 7

    .prologue
    const/4 v2, 0x1

    const/4 v6, 0x3

    .line 546
    const/4 v0, 0x1

    .line 547
    .local v0, nCount:I
    const/4 v1, 0x4

    .line 548
    .local v1, nTotalFile:I
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-le v3, v6, :cond_0

    .line 549
    add-int/lit8 v0, v0, 0x1

    .line 550
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/2addr v1, v3

    .line 553
    :cond_0
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-le v3, v6, :cond_1

    .line 554
    add-int/lit8 v0, v0, 0x1

    .line 555
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/2addr v1, v3

    .line 558
    :cond_1
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-le v3, v6, :cond_2

    .line 559
    add-int/lit8 v0, v0, 0x1

    .line 560
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/2addr v1, v3

    .line 563
    :cond_2
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-le v3, v6, :cond_3

    .line 564
    add-int/lit8 v0, v0, 0x1

    .line 565
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/2addr v1, v3

    .line 568
    :cond_3
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-le v3, v6, :cond_4

    .line 569
    add-int/lit8 v0, v0, 0x1

    .line 570
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/2addr v1, v3

    .line 573
    :cond_4
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-le v3, v6, :cond_5

    .line 574
    add-int/lit8 v0, v0, 0x1

    .line 575
    iget-object v3, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/2addr v1, v3

    .line 577
    :cond_5
    const-string v3, "MTKLogger/MDLoggerClearLog"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "nTotalFile =  "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " nCount =  "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 578
    div-int/2addr v1, v0

    .line 579
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->removeLogByProtectFileNum(I)Z

    move-result v3

    if-eqz v3, :cond_7

    .line 588
    :cond_6
    :goto_0
    return v2

    .line 583
    :cond_7
    const/4 v3, 0x4

    if-le v1, v3, :cond_8

    .line 584
    invoke-direct {p0, v6}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->removeLogByProtectFileNum(I)Z

    move-result v3

    if-nez v3, :cond_6

    .line 588
    :cond_8
    const/4 v2, 0x0

    goto :goto_0
.end method

.method private checkDeleteSizeIfEnough()Z
    .locals 6

    .prologue
    const-wide/16 v4, 0x400

    .line 479
    iget-wide v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v2, 0xa00000

    cmp-long v0, v0, v2

    if-lez v0, :cond_0

    .line 480
    iget-wide v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    div-long/2addr v0, v4

    div-long/2addr v0, v4

    iget-wide v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v0, v0, v2

    if-ltz v0, :cond_0

    .line 481
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Clean Running folder in mdlog is "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-wide v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 483
    const-string v0, "MTKLogger/MDLoggerClearLog"

    const-string v1, "Array size after RemoveRunning Folder "

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 484
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mASTL1Array size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 486
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mDAKArray size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 487
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mDMDSPMLTArray size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 488
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mMD2GMLTArray size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 489
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mPSArray size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 490
    const-string v0, "MTKLogger/MDLoggerClearLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mL1Array size: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 491
    const/4 v0, 0x1

    .line 495
    :goto_0
    return v0

    :cond_0
    const/4 v0, 0x0

    goto :goto_0
.end method

.method private checkIfCanDeleteNormalFolder(Ljava/lang/String;)Z
    .locals 4
    .parameter "path"

    .prologue
    const/4 v2, 0x1

    .line 800
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 802
    .local v0, nTotal:I
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v1, p1}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 803
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    add-int/lit8 v3, v0, -0x1

    invoke-virtual {v1, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v1, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 804
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v1, p1}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 805
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v1, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 806
    const-string v1, "MTKLogger/MDLoggerClearLog"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Find running path: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 808
    :cond_0
    const/4 v1, 0x0

    .line 814
    :goto_0
    return v1

    :cond_1
    move v1, v2

    .line 810
    goto :goto_0

    :cond_2
    move v1, v2

    .line 814
    goto :goto_0
.end method

.method private checkIfClearSDLog(Ljava/lang/String;)Z
    .locals 9
    .parameter "logFolderName"

    .prologue
    const/4 v4, 0x0

    .line 154
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->clear()V

    .line 155
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->clear()V

    .line 156
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->clear()V

    .line 157
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->clear()V

    .line 158
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->clear()V

    .line 162
    :try_start_0
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->sd_mdlog_root:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    sget-object v6, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "mtklog"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    sget-object v6, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    iput-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMdlogPath:Ljava/lang/String;

    .line 164
    new-instance v1, Ljava/io/File;

    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMdlogPath:Ljava/lang/String;

    invoke-direct {v1, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 165
    .local v1, mdlogFolder:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v5

    if-nez v5, :cond_0

    .line 166
    const-string v5, "MTKLogger/MDLoggerClearLog"

    const-string v6, "The mdlog folder doesn\'t exist"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 193
    .end local v1           #mdlogFolder:Ljava/io/File;
    :goto_0
    return v4

    .line 170
    .restart local v1       #mdlogFolder:Ljava/io/File;
    :cond_0
    invoke-virtual {v1}, Ljava/io/File;->canExecute()Z

    move-result v5

    if-nez v5, :cond_1

    .line 171
    const-string v5, "MTKLogger/MDLoggerClearLog"

    const-string v6, "The mdlog folder can not execute."

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 188
    .end local v1           #mdlogFolder:Ljava/io/File;
    :catch_0
    move-exception v0

    .line 189
    .local v0, e:Ljava/lang/Exception;
    const-string v5, "MTKLogger/MDLoggerClearLog"

    const-string v6, "Exception: Failed to get the SD card status"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 190
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 192
    const-string v5, "MTKLogger/MDLoggerClearLog"

    const-string v6, "The SD Card is not available"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 174
    .end local v0           #e:Ljava/lang/Exception;
    .restart local v1       #mdlogFolder:Ljava/io/File;
    :cond_1
    :try_start_1
    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v5

    iput-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->strStartWithString:Ljava/lang/String;

    .line 175
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->strStartWithString:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    sget-object v6, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "MDLog"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    iput-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->strStartWithString:Ljava/lang/String;

    .line 176
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->calculateFolderSize(Ljava/io/File;)J

    move-result-wide v5

    const-wide/16 v7, 0x400

    div-long/2addr v5, v7

    const-wide/16 v7, 0x400

    div-long v2, v5, v7

    .line 178
    .local v2, ocupySize:J
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Limit size is: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget v7, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mTotalLogSizeLimit:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "M. "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget-object v7, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMdlogPath:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " mdlog foler block size is "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "M"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 180
    iget v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mTotalLogSizeLimit:I

    int-to-long v5, v5

    sub-long v5, v2, v5

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    .line 181
    iget v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mTotalLogSizeLimit:I

    add-int/lit8 v5, v5, -0xa

    int-to-long v5, v5

    cmp-long v5, v2, v5

    if-gez v5, :cond_2

    .line 182
    const-string v5, "MTKLogger/MDLoggerClearLog"

    const-string v6, "The Mdlogger need not to clear log."

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 185
    :cond_2
    const-string v5, "MTKLogger/MDLoggerClearLog"

    const-string v6, "The Mdlogger need clear log if less 10M below limitation."

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    .line 186
    const/4 v4, 0x1

    goto/16 :goto_0
.end method

.method private checkOtherFolderInMDFolder(Ljava/io/File;)J
    .locals 10
    .parameter "dir"

    .prologue
    .line 781
    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v5

    .line 782
    .local v5, mSize:J
    invoke-virtual {p1}, Ljava/io/File;->isDirectory()Z

    move-result v7

    if-nez v7, :cond_0

    .line 783
    const-wide/16 v7, 0x0

    .line 795
    :goto_0
    return-wide v7

    .line 786
    :cond_0
    invoke-virtual {p1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    .line 787
    .local v2, files:[Ljava/io/File;
    move-object v0, v2

    .local v0, arr$:[Ljava/io/File;
    array-length v4, v0

    .local v4, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_1
    if-ge v3, v4, :cond_2

    aget-object v1, v0, v3

    .line 788
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v7

    if-eqz v7, :cond_1

    .line 789
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v7

    add-long/2addr v5, v7

    .line 790
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 791
    const-string v7, "MTKLogger/MDLoggerClearLog"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Clear file: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 787
    :cond_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .end local v1           #file:Ljava/io/File;
    :cond_2
    move-wide v7, v5

    .line 795
    goto :goto_0
.end method

.method private checkRemoveFileOneByOne()Z
    .locals 7

    .prologue
    const/4 v3, 0x1

    const/4 v5, 0x3

    .line 499
    const/4 v0, 0x1

    .line 500
    .local v0, nCount:I
    const/4 v1, 0x4

    .line 501
    .local v1, nTotalFile:I
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-le v4, v5, :cond_0

    .line 502
    add-int/lit8 v0, v0, 0x1

    .line 503
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/2addr v1, v4

    .line 506
    :cond_0
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-le v4, v5, :cond_1

    .line 507
    add-int/lit8 v0, v0, 0x1

    .line 508
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/2addr v1, v4

    .line 511
    :cond_1
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-le v4, v5, :cond_2

    .line 512
    add-int/lit8 v0, v0, 0x1

    .line 513
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/2addr v1, v4

    .line 516
    :cond_2
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-le v4, v5, :cond_3

    .line 517
    add-int/lit8 v0, v0, 0x1

    .line 518
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/2addr v1, v4

    .line 521
    :cond_3
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-le v4, v5, :cond_4

    .line 522
    add-int/lit8 v0, v0, 0x1

    .line 523
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/2addr v1, v4

    .line 526
    :cond_4
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-le v4, v5, :cond_5

    .line 527
    add-int/lit8 v0, v0, 0x1

    .line 528
    iget-object v4, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/2addr v1, v4

    .line 530
    :cond_5
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "nTotalFile =  "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " nCount =  "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 531
    div-int/2addr v1, v0

    .line 532
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->removeLogByProtectFileNum(I)Z

    move-result v4

    if-eqz v4, :cond_6

    .line 541
    :goto_0
    return v3

    :cond_6
    move v2, v1

    .line 535
    .end local v1           #nTotalFile:I
    .local v2, nTotalFile:I
    add-int/lit8 v1, v2, -0x1

    .end local v2           #nTotalFile:I
    .restart local v1       #nTotalFile:I
    if-lez v2, :cond_7

    .line 536
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->removeLogByProtectFileNum(I)Z

    move-result v4

    if-eqz v4, :cond_6

    goto :goto_0

    .line 541
    :cond_7
    const/4 v3, 0x0

    goto :goto_0
.end method

.method private clearLogs(Ljava/io/File;)J
    .locals 12
    .parameter "dir"

    .prologue
    .line 197
    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v5

    .line 198
    .local v5, mSize:J
    invoke-virtual {p1}, Ljava/io/File;->isDirectory()Z

    move-result v9

    if-nez v9, :cond_0

    .line 199
    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    .line 200
    const-string v9, "MTKLogger/MDLoggerClearLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Clear file: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v7, v5

    .line 218
    .end local v5           #mSize:J
    .local v7, mSize:J
    :goto_0
    return-wide v7

    .line 204
    .end local v7           #mSize:J
    .restart local v5       #mSize:J
    :cond_0
    invoke-virtual {p1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    .line 205
    .local v2, files:[Ljava/io/File;
    move-object v0, v2

    .local v0, arr$:[Ljava/io/File;
    array-length v4, v0

    .local v4, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_1
    if-ge v3, v4, :cond_3

    aget-object v1, v0, v3

    .line 207
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->isFile()Z

    move-result v9

    if-eqz v9, :cond_2

    .line 208
    invoke-virtual {v1}, Ljava/io/File;->length()J

    move-result-wide v9

    add-long/2addr v5, v9

    .line 209
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 214
    :cond_1
    :goto_2
    const-string v9, "MTKLogger/MDLoggerClearLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Clear file: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 205
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 210
    :cond_2
    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v9

    if-eqz v9, :cond_1

    .line 211
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v9

    add-long/2addr v5, v9

    .line 212
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    goto :goto_2

    .line 216
    .end local v1           #file:Ljava/io/File;
    :cond_3
    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    .line 217
    const-string v9, "MTKLogger/MDLoggerClearLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Clear file: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v7, v5

    .line 218
    .end local v5           #mSize:J
    .restart local v7       #mSize:J
    goto :goto_0
.end method

.method private clearNormalLogWithConfirm(Ljava/io/File;)J
    .locals 15
    .parameter "dir"

    .prologue
    .line 222
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->length()J

    move-result-wide v6

    .line 223
    .local v6, mSize:J
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->isDirectory()Z

    move-result v12

    if-nez v12, :cond_0

    .line 224
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->delete()Z

    .line 225
    const-string v12, "MTKLogger/MDLoggerClearLog"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "confirm clear !isDirectory file: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v8, v6

    .line 269
    .end local v6           #mSize:J
    .local v8, mSize:J
    :goto_0
    return-wide v8

    .line 228
    .end local v8           #mSize:J
    .restart local v6       #mSize:J
    :cond_0
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    .line 229
    .local v2, files:[Ljava/io/File;
    new-instance v11, Ljava/util/ArrayList;

    invoke-direct {v11}, Ljava/util/ArrayList;-><init>()V

    .line 230
    .local v11, strBPLGUInioArray:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    const/4 v5, 0x0

    .line 231
    .local v5, mFindBin:Z
    move-object v0, v2

    .local v0, arr$:[Ljava/io/File;
    array-length v4, v0

    .local v4, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_1
    if-ge v3, v4, :cond_5

    aget-object v1, v0, v3

    .line 232
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->isFile()Z

    move-result v12

    if-eqz v12, :cond_4

    .line 236
    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v12

    const-string v13, ".bin"

    invoke-virtual {v12, v13}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v12

    if-eqz v12, :cond_2

    .line 237
    const/4 v5, 0x1

    .line 238
    const-string v12, "MTKLogger/MDLoggerClearLog"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Find bin: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 239
    iget-object v12, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v13

    invoke-virtual {v12, v13}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v12

    if-nez v12, :cond_1

    .line 240
    iget-object v12, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v13

    invoke-virtual {v12, v13}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 231
    :cond_1
    :goto_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 242
    :cond_2
    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v12

    const-string v13, "BPLGUInfo"

    invoke-virtual {v12, v13}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-eqz v12, :cond_3

    .line 243
    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_2

    .line 245
    :cond_3
    invoke-virtual {v1}, Ljava/io/File;->length()J

    move-result-wide v12

    add-long/2addr v6, v12

    .line 246
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 247
    const-string v12, "MTKLogger/MDLoggerClearLog"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "confirm clear file: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 250
    :cond_4
    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v12

    if-eqz v12, :cond_1

    .line 251
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v12

    add-long/2addr v6, v12

    .line 252
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 253
    const-string v12, "MTKLogger/MDLoggerClearLog"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "confirm clear file: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 257
    .end local v1           #file:Ljava/io/File;
    :cond_5
    if-nez v5, :cond_7

    .line 259
    invoke-virtual {v11}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .local v3, i$:Ljava/util/Iterator;
    :goto_3
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v12

    if-eqz v12, :cond_6

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Ljava/lang/String;

    .line 260
    .local v10, strBPLGUInio:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v10}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 261
    .restart local v1       #file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->length()J

    move-result-wide v12

    add-long/2addr v6, v12

    .line 262
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    goto :goto_3

    .line 265
    .end local v1           #file:Ljava/io/File;
    .end local v10           #strBPLGUInio:Ljava/lang/String;
    :cond_6
    const-string v12, "MTKLogger/MDLoggerClearLog"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "confirm clear file: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 266
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->delete()Z

    .end local v3           #i$:Ljava/util/Iterator;
    :cond_7
    move-wide v8, v6

    .line 269
    .end local v6           #mSize:J
    .restart local v8       #mSize:J
    goto/16 :goto_0
.end method

.method private clearNormalLogs(Ljava/io/File;)J
    .locals 13
    .parameter "dir"

    .prologue
    .line 273
    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v7

    .line 274
    .local v7, mSize:J
    invoke-virtual {p1}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-nez v10, :cond_0

    .line 275
    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    .line 276
    const-string v10, "MTKLogger/MDLoggerClearLog"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Clear Normal file: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    move-wide v10, v7

    .line 332
    :goto_0
    return-wide v10

    .line 280
    :cond_0
    invoke-virtual {p1}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    .line 281
    .local v2, files:[Ljava/io/File;
    const/4 v5, 0x0

    .line 282
    .local v5, mFindException:Z
    const/4 v6, 0x0

    .line 283
    .local v6, mRunning:Z
    move-object v0, v2

    .local v0, arr$:[Ljava/io/File;
    array-length v4, v0

    .local v4, len$:I
    const/4 v3, 0x0

    .local v3, i$:I
    :goto_1
    if-ge v3, v4, :cond_1

    aget-object v1, v0, v3

    .line 284
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->isFile()Z

    move-result v10

    if-eqz v10, :cond_4

    .line 288
    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v10

    const-string v11, ".bin"

    invoke-virtual {v10, v11}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v10

    if-eqz v10, :cond_3

    .line 289
    const/4 v5, 0x1

    .line 290
    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_1

    .line 291
    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 313
    .end local v1           #file:Ljava/io/File;
    :cond_1
    :goto_2
    if-nez v6, :cond_2

    if-eqz v5, :cond_6

    .line 314
    :cond_2
    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v10

    sub-long v10, v7, v10

    goto :goto_0

    .line 294
    .restart local v1       #file:Ljava/io/File;
    :cond_3
    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v10

    const-string v11, ".dmp.dmp"

    invoke-virtual {v10, v11}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v10

    if-eqz v10, :cond_5

    .line 295
    const/4 v6, 0x1

    .line 297
    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v9

    .line 298
    .local v9, path:Ljava/lang/String;
    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v10, v9}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v10

    if-nez v10, :cond_1

    .line 299
    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v10, v9}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 300
    const-string v10, "MTKLogger/MDLoggerClearLog"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Arraysize= "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    iget-object v12, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v12}, Ljava/util/ArrayList;->size()I

    move-result v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, " Find running path: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 306
    .end local v9           #path:Ljava/lang/String;
    :cond_4
    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-eqz v10, :cond_5

    .line 307
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v7, v10

    .line 308
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 309
    const-string v10, "MTKLogger/MDLoggerClearLog"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Clear Normal file: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 283
    :cond_5
    add-int/lit8 v3, v3, 0x1

    goto/16 :goto_1

    .line 316
    .end local v1           #file:Ljava/io/File;
    :cond_6
    if-nez v5, :cond_a

    .line 317
    move-object v0, v2

    array-length v4, v0

    const/4 v3, 0x0

    :goto_3
    if-ge v3, v4, :cond_9

    aget-object v1, v0, v3

    .line 318
    .restart local v1       #file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->isFile()Z

    move-result v10

    if-eqz v10, :cond_8

    .line 319
    invoke-virtual {v1}, Ljava/io/File;->length()J

    move-result-wide v10

    add-long/2addr v7, v10

    .line 320
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 321
    const-string v10, "MTKLogger/MDLoggerClearLog"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Clear Normal file: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 317
    :cond_7
    :goto_4
    add-int/lit8 v3, v3, 0x1

    goto :goto_3

    .line 322
    :cond_8
    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-eqz v10, :cond_7

    .line 323
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v7, v10

    .line 324
    invoke-virtual {v1}, Ljava/io/File;->delete()Z

    .line 325
    const-string v10, "MTKLogger/MDLoggerClearLog"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Clear Normal file: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_4

    .line 328
    .end local v1           #file:Ljava/io/File;
    :cond_9
    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    .line 329
    const-string v10, "MTKLogger/MDLoggerClearLog"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Clear Normal file: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    :cond_a
    move-wide v10, v7

    .line 332
    goto/16 :goto_0
.end method

.method private getFileSize(Ljava/io/File;)J
    .locals 12
    .parameter "root"

    .prologue
    const/4 v11, 0x0

    .line 89
    invoke-virtual {p1}, Ljava/io/File;->length()J

    move-result-wide v7

    .line 91
    .local v7, size:J
    invoke-virtual {p1}, Ljava/io/File;->isDirectory()Z

    move-result v9

    if-eqz v9, :cond_2

    .line 92
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->saveDirToArray(Ljava/io/File;)V

    .line 93
    new-instance v4, Ljava/util/ArrayList;

    invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V

    .line 94
    .local v4, folderArrayList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/io/File;>;"
    invoke-virtual {v4, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 95
    :goto_0
    invoke-virtual {v4}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v9

    if-nez v9, :cond_2

    .line 96
    invoke-virtual {v4, v11}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/io/File;

    .line 97
    .local v3, folder:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v2

    .line 98
    .local v2, files:[Ljava/io/File;
    if-eqz v2, :cond_1

    .line 99
    move-object v0, v2

    .local v0, arr$:[Ljava/io/File;
    array-length v6, v0

    .local v6, len$:I
    const/4 v5, 0x0

    .local v5, i$:I
    :goto_1
    if-ge v5, v6, :cond_1

    aget-object v1, v0, v5

    .line 100
    .local v1, file:Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->isDirectory()Z

    move-result v9

    if-eqz v9, :cond_0

    .line 101
    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->saveDirToArray(Ljava/io/File;)V

    .line 102
    invoke-virtual {v4, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 104
    :cond_0
    invoke-virtual {v1}, Ljava/io/File;->length()J

    move-result-wide v9

    add-long/2addr v7, v9

    .line 99
    add-int/lit8 v5, v5, 0x1

    goto :goto_1

    .line 107
    .end local v0           #arr$:[Ljava/io/File;
    .end local v1           #file:Ljava/io/File;
    .end local v5           #i$:I
    .end local v6           #len$:I
    :cond_1
    invoke-virtual {v4, v11}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    goto :goto_0

    .line 112
    .end local v2           #files:[Ljava/io/File;
    .end local v3           #folder:Ljava/io/File;
    .end local v4           #folderArrayList:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/io/File;>;"
    :cond_2
    return-wide v7
.end method

.method public static declared-synchronized getInstance()Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;
    .locals 2

    .prologue
    .line 81
    const-class v1, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;

    monitor-enter v1

    :try_start_0
    sget-object v0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mInstance:Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;

    if-nez v0, :cond_0

    .line 82
    new-instance v0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;

    invoke-direct {v0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;-><init>()V

    sput-object v0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mInstance:Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;

    .line 84
    :cond_0
    sget-object v0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mInstance:Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v1

    return-object v0

    .line 81
    :catchall_0
    move-exception v0

    monitor-exit v1

    throw v0
.end method

.method private readRootFolderTreeList(Ljava/lang/String;Z)Z
    .locals 9
    .parameter "path"
    .parameter "mTopFoler"

    .prologue
    .line 819
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    sget-object v7, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "file_tree.txt"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 820
    .local v5, treeFileString:Ljava/lang/String;
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "tree file path: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 822
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 823
    .local v2, logFile:Ljava/io/File;
    const/4 v3, 0x0

    .line 824
    .local v3, reader:Ljava/io/BufferedReader;
    if-eqz p2, :cond_1

    .line 825
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    .line 835
    :goto_0
    :try_start_0
    new-instance v4, Ljava/io/BufferedReader;

    new-instance v6, Ljava/io/FileReader;

    invoke-direct {v6, v2}, Ljava/io/FileReader;-><init>(Ljava/io/File;)V

    invoke-direct {v4, v6}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    .line 836
    .end local v3           #reader:Ljava/io/BufferedReader;
    .local v4, reader:Ljava/io/BufferedReader;
    const/4 v1, 0x0

    .line 837
    .local v1, line:Ljava/lang/String;
    :cond_0
    :goto_1
    :try_start_1
    invoke-virtual {v4}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v1

    if-eqz v1, :cond_8

    .line 838
    const-string v6, "\r"

    const-string v7, ""

    invoke-virtual {v1, v6, v7}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v1

    .line 839
    const-string v6, ".dmp.dmp"

    const-string v7, ".dmp"

    invoke-virtual {v1, v6, v7}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 840
    const-string v6, ".bin.bin"

    const-string v7, ".bin"

    invoke-virtual {v1, v6, v7}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 841
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v6

    if-lez v6, :cond_0

    .line 842
    if-eqz p2, :cond_2

    .line 843
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_0

    .line 844
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_1

    .line 869
    :catch_0
    move-exception v0

    move-object v3, v4

    .line 870
    .end local v1           #line:Ljava/lang/String;
    .end local v4           #reader:Ljava/io/BufferedReader;
    .local v0, e:Ljava/io/IOException;
    .restart local v3       #reader:Ljava/io/BufferedReader;
    :goto_2
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 871
    const/4 v6, 0x0

    .line 884
    .end local v0           #e:Ljava/io/IOException;
    :goto_3
    return v6

    .line 827
    :cond_1
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    .line 828
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    .line 829
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    .line 830
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    .line 831
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    .line 832
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->clear()V

    goto :goto_0

    .line 848
    .end local v3           #reader:Ljava/io/BufferedReader;
    .restart local v1       #line:Ljava/lang/String;
    .restart local v4       #reader:Ljava/io/BufferedReader;
    :cond_2
    :try_start_2
    const-string v6, ".dmp"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_0

    .line 849
    const-string v6, "MDLog_PS"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_3

    .line 850
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_1

    .line 851
    :cond_3
    const-string v6, "MDLog_DAK"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_4

    .line 852
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_1

    .line 853
    :cond_4
    const-string v6, "MDLog_L1"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_5

    .line 854
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto/16 :goto_1

    .line 855
    :cond_5
    const-string v6, "MDLog_ASTL1"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_6

    .line 856
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto/16 :goto_1

    .line 857
    :cond_6
    const-string v6, "MDLog_MD2GMLT"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_7

    .line 858
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto/16 :goto_1

    .line 859
    :cond_7
    const-string v6, "MDLog_DMDSPMLT"

    invoke-virtual {v1, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_0

    .line 860
    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v6, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto/16 :goto_1

    .line 867
    :cond_8
    invoke-virtual {v4}, Ljava/io/BufferedReader;->close()V
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_0

    .line 873
    if-eqz p2, :cond_9

    .line 874
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Top Tree File: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 884
    :goto_4
    const/4 v6, 0x1

    move-object v3, v4

    .end local v4           #reader:Ljava/io/BufferedReader;
    .restart local v3       #reader:Ljava/io/BufferedReader;
    goto/16 :goto_3

    .line 876
    .end local v3           #reader:Ljava/io/BufferedReader;
    .restart local v4       #reader:Ljava/io/BufferedReader;
    :cond_9
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mASTL1Array size: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 877
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mPSArray size: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 878
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mL1Array size: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 879
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mDAKArray size: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 880
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mDMDSPMLTArray size: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 881
    const-string v6, "MTKLogger/MDLoggerClearLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mMD2GMLTArray size: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_4

    .line 869
    .end local v1           #line:Ljava/lang/String;
    .end local v4           #reader:Ljava/io/BufferedReader;
    .restart local v3       #reader:Ljava/io/BufferedReader;
    :catch_1
    move-exception v0

    goto/16 :goto_2
.end method

.method private removeLogByProtectFileNum(I)Z
    .locals 9
    .parameter "nFileRemained"

    .prologue
    const/4 v4, 0x1

    .line 336
    const/4 v3, 0x0

    .line 337
    .local v3, removeFind:Z
    if-gez p1, :cond_0

    .line 338
    const/4 p1, 0x0

    .line 340
    :cond_0
    const/4 v2, 0x1

    .line 341
    .local v2, nSecondOld:I
    if-nez p1, :cond_1

    .line 342
    const/4 v2, 0x0

    .line 347
    :cond_1
    const/4 v3, 0x0

    .line 349
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-le v5, p1, :cond_5

    .line 351
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 352
    .local v1, filePathString:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 353
    .local v0, file:Ljava/io/File;
    const/4 v3, 0x1

    .line 354
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_3

    .line 356
    iget-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v7

    add-long/2addr v5, v7

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 357
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 358
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Clear Running file: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 359
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkDeleteSizeIfEnough()Z

    move-result v5

    if-eqz v5, :cond_4

    .line 475
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_2
    :goto_0
    return v4

    .line 363
    .restart local v0       #file:Ljava/io/File;
    .restart local v1       #filePathString:Ljava/lang/String;
    :cond_3
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "File Not exist: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 366
    :cond_4
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 371
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_5
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-le v5, p1, :cond_7

    .line 372
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 373
    .restart local v1       #filePathString:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 374
    .restart local v0       #file:Ljava/io/File;
    const/4 v3, 0x1

    .line 375
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_6

    .line 377
    iget-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v7

    add-long/2addr v5, v7

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 378
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 379
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Clear Running file: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 380
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkDeleteSizeIfEnough()Z

    move-result v5

    if-nez v5, :cond_2

    .line 385
    :cond_6
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 390
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_7
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-le v5, p1, :cond_8

    .line 391
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 392
    .restart local v1       #filePathString:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 393
    .restart local v0       #file:Ljava/io/File;
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_f

    .line 394
    iget-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v7

    add-long/2addr v5, v7

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 395
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 396
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Clear Running file: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 397
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkDeleteSizeIfEnough()Z

    move-result v5

    if-nez v5, :cond_2

    .line 403
    :goto_1
    const/4 v3, 0x1

    .line 404
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 408
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_8
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-le v5, p1, :cond_a

    .line 409
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 410
    .restart local v1       #filePathString:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 411
    .restart local v0       #file:Ljava/io/File;
    const/4 v3, 0x1

    .line 412
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_9

    .line 414
    iget-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v7

    add-long/2addr v5, v7

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 415
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 416
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Clear Running file: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 417
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkDeleteSizeIfEnough()Z

    move-result v5

    if-nez v5, :cond_2

    .line 422
    :cond_9
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 427
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_a
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-le v5, p1, :cond_c

    .line 428
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 429
    .restart local v1       #filePathString:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 430
    .restart local v0       #file:Ljava/io/File;
    const/4 v3, 0x1

    .line 431
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_b

    .line 433
    iget-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v7

    add-long/2addr v5, v7

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 434
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 435
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Clear Running file: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 436
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkDeleteSizeIfEnough()Z

    move-result v5

    if-nez v5, :cond_2

    .line 441
    :cond_b
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 445
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_c
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-le v5, p1, :cond_e

    .line 446
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 447
    .restart local v1       #filePathString:Ljava/lang/String;
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 448
    .restart local v0       #file:Ljava/io/File;
    const/4 v3, 0x1

    .line 449
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v5

    if-eqz v5, :cond_d

    .line 451
    iget-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v7

    add-long/2addr v5, v7

    iput-wide v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 452
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 453
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Clear Running file: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 454
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkDeleteSizeIfEnough()Z

    move-result v5

    if-nez v5, :cond_2

    .line 459
    :cond_d
    iget-object v5, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 462
    .end local v0           #file:Ljava/io/File;
    .end local v1           #filePathString:Ljava/lang/String;
    :cond_e
    if-nez v3, :cond_1

    .line 463
    const-string v4, "MTKLogger/MDLoggerClearLog"

    const-string v5, "No more log find in running folder"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 467
    const-string v4, "MTKLogger/MDLoggerClearLog"

    const-string v5, "Array size after RemoveRunning Folder "

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 468
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mASTL1Array size: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mASTL1Array:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 470
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mDAKArray size: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDAKArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 471
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mDMDSPMLTArray size: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mDMDSPMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 472
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mMD2GMLTArray size: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMD2GMLTArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 473
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mPSArray size: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mPSArray:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 474
    const-string v4, "MTKLogger/MDLoggerClearLog"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "mL1Array size: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mL1Array:Ljava/util/ArrayList;

    invoke-virtual {v6}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 475
    const/4 v4, 0x0

    goto/16 :goto_0

    .line 401
    .restart local v0       #file:Ljava/io/File;
    .restart local v1       #filePathString:Ljava/lang/String;
    :cond_f
    const-string v5, "MTKLogger/MDLoggerClearLog"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "File Not exist: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1
.end method

.method private saveDirToArray(Ljava/io/File;)V
    .locals 2
    .parameter "file"

    .prologue
    .line 131
    invoke-virtual {p1}, Ljava/io/File;->isDirectory()Z

    move-result v1

    if-nez v1, :cond_0

    .line 144
    :goto_0
    return-void

    .line 134
    :cond_0
    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v0

    .line 135
    .local v0, pathString:Ljava/lang/String;
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->strStartWithString:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 136
    const-string v1, "EE"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 137
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 139
    :cond_1
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 142
    :cond_2
    iget-object v1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0
.end method


# virtual methods
.method public checkAndClearLog(ILjava/lang/String;Ljava/lang/String;)V
    .locals 14
    .parameter "logSizeMode"
    .parameter "sd_path"
    .parameter "logFolderName"

    .prologue
    .line 595
    iput p1, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mTotalLogSizeLimit:I

    .line 596
    move-object/from16 v0, p2

    iput-object v0, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->sd_mdlog_root:Ljava/lang/String;

    .line 597
    move-object/from16 v0, p3

    invoke-direct {p0, v0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkIfClearSDLog(Ljava/lang/String;)Z

    move-result v8

    if-nez v8, :cond_1

    .line 777
    :cond_0
    :goto_0
    return-void

    .line 600
    :cond_1
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "other folder ="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v10}, Ljava/util/ArrayList;->size()I

    move-result v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 601
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "normal Folder ="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v10}, Ljava/util/ArrayList;->size()I

    move-result v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 602
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "ee Folder ="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-object v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v10}, Ljava/util/ArrayList;->size()I

    move-result v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 604
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    new-instance v9, Lcom/mediatek/mtklogger/utils/UtilsSortString;

    const/4 v10, 0x1

    invoke-direct {v9, v10}, Lcom/mediatek/mtklogger/utils/UtilsSortString;-><init>(I)V

    invoke-static {v8, v9}, Ljava/util/Collections;->sort(Ljava/util/List;Ljava/util/Comparator;)V

    .line 605
    const-wide/16 v8, 0x0

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 606
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v3

    .line 607
    .local v3, length:I
    const/4 v5, 0x0

    .line 609
    .local v5, nCount:I
    :goto_1
    if-ge v5, v3, :cond_2

    .line 610
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 611
    .local v6, path:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 612
    .local v1, file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 613
    const/4 v1, 0x0

    .line 614
    add-int/lit8 v5, v5, 0x1

    .line 615
    goto :goto_1

    .line 617
    .end local v1           #file:Ljava/io/File;
    .end local v6           #path:Ljava/lang/String;
    :cond_2
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "other folde remove file total size: "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v12, 0x400

    div-long/2addr v10, v12

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "K"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 618
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_3

    .line 619
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_3

    .line 620
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean other folder in mdlog is "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 624
    :cond_3
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mMdlogPath:Ljava/lang/String;

    const/4 v9, 0x1

    invoke-direct {p0, v8, v9}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->readRootFolderTreeList(Ljava/lang/String;Z)Z

    .line 626
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    new-instance v9, Lcom/mediatek/mtklogger/utils/UtilsSortString;

    const/4 v10, 0x1

    invoke-direct {v9, v10}, Lcom/mediatek/mtklogger/utils/UtilsSortString;-><init>(I)V

    invoke-static {v8, v9}, Ljava/util/Collections;->sort(Ljava/util/List;Ljava/util/Comparator;)V

    .line 627
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :cond_4
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_5

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 628
    .restart local v6       #path:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 629
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkOtherFolderInMDFolder(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 630
    const/4 v1, 0x0

    .line 631
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_4

    .line 632
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_4

    .line 633
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean other folder in EE mdlog is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 639
    .end local v1           #file:Ljava/io/File;
    .end local v6           #path:Ljava/lang/String;
    :cond_5
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    new-instance v9, Lcom/mediatek/mtklogger/utils/UtilsSortString;

    const/4 v10, 0x1

    invoke-direct {v9, v10}, Lcom/mediatek/mtklogger/utils/UtilsSortString;-><init>(I)V

    invoke-static {v8, v9}, Ljava/util/Collections;->sort(Ljava/util/List;Ljava/util/Comparator;)V

    .line 640
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_6
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_7

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 641
    .restart local v6       #path:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 642
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkOtherFolderInMDFolder(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 643
    const/4 v1, 0x0

    .line 644
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_6

    .line 645
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_6

    .line 646
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean other folder in normal mdlog is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 652
    .end local v1           #file:Ljava/io/File;
    .end local v6           #path:Ljava/lang/String;
    :cond_7
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v4

    .line 653
    .local v4, mTotalNoral:I
    if-lez v4, :cond_b

    .line 655
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->normalFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_8
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_b

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 656
    .restart local v6       #path:Ljava/lang/String;
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->folderTreeListArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v8

    if-eqz v8, :cond_a

    .line 657
    const/4 v8, 0x0

    invoke-direct {p0, v6, v8}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->readRootFolderTreeList(Ljava/lang/String;Z)Z

    move-result v8

    if-eqz v8, :cond_9

    .line 658
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkRemoveFileOneByOne()Z

    move-result v8

    if-nez v8, :cond_0

    .line 662
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 663
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 664
    const/4 v1, 0x0

    .line 665
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_8

    .line 666
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_8

    .line 667
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean bin in normal mdlog is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 672
    .end local v1           #file:Ljava/io/File;
    :cond_9
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 673
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearNormalLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 674
    const/4 v1, 0x0

    .line 675
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_8

    .line 676
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_8

    .line 677
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean normal log is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 684
    .end local v1           #file:Ljava/io/File;
    :cond_a
    invoke-direct {p0, v6}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkIfCanDeleteNormalFolder(Ljava/lang/String;)Z

    move-result v8

    if-eqz v8, :cond_8

    .line 685
    const-string v8, "MTKLogger/MDLoggerClearLog"

    const-string v9, "Clean Noram log file one by one"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 686
    const/4 v8, 0x0

    invoke-direct {p0, v6, v8}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->readRootFolderTreeList(Ljava/lang/String;Z)Z

    .line 687
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkRemoveFileOneByOne()Z

    move-result v8

    if-nez v8, :cond_0

    .line 691
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 692
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 693
    const/4 v1, 0x0

    .line 694
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_8

    .line 695
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_8

    .line 696
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean bin in normal mdlog is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 719
    .end local v1           #file:Ljava/io/File;
    .end local v6           #path:Ljava/lang/String;
    :cond_b
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    const/4 v9, 0x1

    if-ne v8, v9, :cond_d

    .line 720
    const-string v8, "MTKLogger/MDLoggerClearLog"

    const-string v9, "Clean running log"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 721
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mRunningArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_c
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_d

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 722
    .local v7, pathString:Ljava/lang/String;
    const/4 v8, 0x0

    invoke-direct {p0, v7, v8}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->readRootFolderTreeList(Ljava/lang/String;Z)Z

    .line 723
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkAndRemoveRunningFolder()Z

    move-result v8

    if-eqz v8, :cond_c

    goto/16 :goto_0

    .line 729
    .end local v7           #pathString:Ljava/lang/String;
    :cond_d
    const-string v8, "MTKLogger/MDLoggerClearLog"

    const-string v9, "Clean Noram bin file one bye one"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 731
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_e
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_f

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 732
    .restart local v7       #pathString:Ljava/lang/String;
    const/4 v8, 0x0

    invoke-direct {p0, v7, v8}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->readRootFolderTreeList(Ljava/lang/String;Z)Z

    .line 733
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkRemoveFileOneByOne()Z

    move-result v8

    if-eqz v8, :cond_e

    goto/16 :goto_0

    .line 739
    .end local v7           #pathString:Ljava/lang/String;
    :cond_f
    const-string v8, "MTKLogger/MDLoggerClearLog"

    const-string v9, "Clean Noram bin folder"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 741
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mNormalContainBin:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_10
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_11

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 742
    .restart local v6       #path:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 743
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 744
    const/4 v1, 0x0

    .line 745
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_10

    .line 746
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_10

    .line 747
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean bin in normal mdlog is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 754
    .end local v1           #file:Ljava/io/File;
    .end local v6           #path:Ljava/lang/String;
    :cond_11
    const-string v8, "MTKLogger/MDLoggerClearLog"

    const-string v9, "Clean EE dump file one bye one"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 756
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_12
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_13

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 757
    .restart local v7       #pathString:Ljava/lang/String;
    const/4 v8, 0x0

    invoke-direct {p0, v7, v8}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->readRootFolderTreeList(Ljava/lang/String;Z)Z

    .line 758
    invoke-direct {p0}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->checkRemoveFileOneByOne()Z

    move-result v8

    if-eqz v8, :cond_12

    goto/16 :goto_0

    .line 763
    .end local v7           #pathString:Ljava/lang/String;
    :cond_13
    const-string v8, "MTKLogger/MDLoggerClearLog"

    const-string v9, "Clean EE Folder"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 765
    iget-object v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->eeFolderArray:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_14
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    .line 766
    .restart local v6       #path:Ljava/lang/String;
    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 767
    .restart local v1       #file:Ljava/io/File;
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->clearLogs(Ljava/io/File;)J

    move-result-wide v10

    add-long/2addr v8, v10

    iput-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    .line 768
    const/4 v1, 0x0

    .line 769
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/32 v10, 0xa00000

    cmp-long v8, v8, v10

    if-lez v8, :cond_14

    .line 770
    iget-wide v8, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    const-wide/16 v10, 0x400

    div-long/2addr v8, v10

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->mLogNeedToBeClearSize:J

    cmp-long v8, v8, v10

    if-ltz v8, :cond_14

    .line 771
    const-string v8, "MTKLogger/MDLoggerClearLog"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Clean bin in EE mdlog is  "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    iget-wide v10, p0, Lcom/mediatek/mtklogger/utils/MDLoggerClearLog;->otherFolderSize:J

    invoke-virtual {v9, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0
.end method
