.class public Lcom/mediatek/mtklogger/framework/MTKLoggerService;
.super Landroid/app/Service;
.source "MTKLoggerService.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "MTKLogger/MTKLoggerService"

.field public static isInIPOShutdown:Z

.field private static isStarted:Z

.field private static isWaitingSDReady:Z

.field private static mNM:Landroid/app/NotificationManager;


# instance fields
.field private failReasonStr:Ljava/lang/String;

.field public mBind:Lcom/mediatek/mtklogger/IMTKLoggerManager$Stub;

.field private mCachedStartStopCmd:I

.field private mCurrentAffectedLogType:I

.field private mCurrentLogPathType:Ljava/lang/String;

.field private mCurrentRecordingLogPath:Ljava/lang/String;

.field private mCurrentRunningStatus:I

.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field private mGlobalRunningStage:I

.field private mIPOIntentFilter:Landroid/content/IntentFilter;

.field private mIPOReceiver:Landroid/content/BroadcastReceiver;

.field private mLogFolderMonitorThreadStopFlag:Z

.field private mLogInstanceMap:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Lcom/mediatek/mtklogger/framework/LogInstance;",
            ">;"
        }
    .end annotation
.end field

.field mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

.field private mNativeStateHandler:Landroid/os/Handler;

.field private mPhoneStorageIntentFilter:Landroid/content/IntentFilter;

.field private mRemainingStorage:I

.field private mSDStatusIntentFilter:Landroid/content/IntentFilter;

.field private mServiceStartType:Ljava/lang/String;

.field private mSharedPreferences:Landroid/content/SharedPreferences;

.field private mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

.field private mUserSwitchReceiver:Landroid/content/BroadcastReceiver;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    const/4 v0, 0x0

    .line 52
    sput-boolean v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStarted:Z

    .line 59
    sput-boolean v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    .line 74
    sput-boolean v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isInIPOShutdown:Z

    .line 99
    const/4 v0, 0x0

    sput-object v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    return-void
.end method

.method public constructor <init>()V
    .locals 3

    .prologue
    const/4 v2, 0x0

    const/4 v1, 0x0

    .line 34
    invoke-direct {p0}, Landroid/app/Service;-><init>()V

    .line 41
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogInstanceMap:Landroid/util/SparseArray;

    .line 42
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 47
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 64
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    .line 79
    iput v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mGlobalRunningStage:I

    .line 84
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentLogPathType:Ljava/lang/String;

    .line 89
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRecordingLogPath:Ljava/lang/String;

    .line 96
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    .line 97
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    .line 104
    iput v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    .line 357
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    .line 358
    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mPhoneStorageIntentFilter:Landroid/content/IntentFilter;

    .line 359
    new-instance v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$1;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$1;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    .line 436
    new-instance v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$2;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$2;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mIPOReceiver:Landroid/content/BroadcastReceiver;

    .line 454
    new-instance v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$3;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$3;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mUserSwitchReceiver:Landroid/content/BroadcastReceiver;

    .line 681
    iput v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    .line 693
    iput v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    .line 701
    iput v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    .line 704
    const-string v0, ""

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    .line 710
    new-instance v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$4;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, p0, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$4;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;Landroid/os/Looper;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    .line 912
    new-instance v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mBind:Lcom/mediatek/mtklogger/IMTKLoggerManager$Stub;

    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Ljava/lang/String;
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentLogPathType:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isAnyLogRunning()Z

    move-result v0

    return v0
.end method

.method static synthetic access$1000(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 34
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->checkRemainingStorage()V

    return-void
.end method

.method static synthetic access$1100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)Lcom/mediatek/mtklogger/framework/LogInstance;
    .locals 1
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 34
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1200(Lcom/mediatek/mtklogger/framework/MTKLoggerService;Landroid/os/Handler;Landroid/os/Message;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 34
    invoke-direct {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->handleLogStateChangeMsg(Landroid/os/Handler;Landroid/os/Message;)V

    return-void
.end method

.method static synthetic access$1300(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 34
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->handleGlobalRunningStageChange(I)V

    return-void
.end method

.method static synthetic access$1400(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->beginTagLog()Z

    move-result v0

    return v0
.end method

.method static synthetic access$1500(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)I
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getGlobalRunningStage()I

    move-result v0

    return v0
.end method

.method static synthetic access$200(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Ljava/lang/String;
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRecordingLogPath:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$300(Lcom/mediatek/mtklogger/framework/MTKLoggerService;ZLjava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 34
    invoke-direct {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->changeLogRunningStatus(ZLjava/lang/String;)V

    return-void
.end method

.method static synthetic access$400(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$500(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$600()Z
    .locals 1

    .prologue
    .line 34
    sget-boolean v0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    return v0
.end method

.method static synthetic access$602(Z)Z
    .locals 0
    .parameter "x0"

    .prologue
    .line 34
    sput-boolean p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    return p0
.end method

.method static synthetic access$700(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 34
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateLogStatus()V

    return-void
.end method

.method static synthetic access$800(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    return v0
.end method

.method static synthetic access$900(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 34
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStorageReady()Z

    move-result v0

    return v0
.end method

.method private beginTagLog()Z
    .locals 3

    .prologue
    .line 905
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "-->beginTagLog()"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 906
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 907
    .local v0, intent:Landroid/content/Intent;
    const-string v1, "path"

    const-string v2, "SaveLogManually"

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 908
    invoke-static {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->getInstance(Landroid/content/Context;)Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->beginTag(Landroid/content/Intent;)V

    .line 909
    const/4 v1, 0x1

    return v1
.end method

.method private changeLogRunningStatus(ZLjava/lang/String;)V
    .locals 11
    .parameter "enable"
    .parameter "reason"

    .prologue
    const/4 v7, 0x1

    const/4 v8, 0x0

    .line 474
    const-string v5, "MTKLogger/MTKLoggerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "-->changeLogRunningStatus(), enable?"

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v9, ", reason=["

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v9, "]"

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 475
    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-nez v5, :cond_1

    .line 476
    const-string v5, "MTKLogger/MTKLoggerService"

    const-string v6, "SharedPreference instance is null"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 538
    :cond_0
    :goto_0
    return-void

    .line 480
    :cond_1
    const/4 v0, 0x0

    .line 481
    .local v0, affectedLog:I
    if-eqz p1, :cond_7

    .line 484
    const-string v5, "boot"

    invoke-virtual {v5, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_2

    const-string v5, "ipo"

    invoke-virtual {v5, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_4

    .line 485
    :cond_2
    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v5}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_3
    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_6

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    .line 486
    .local v3, logType:Ljava/lang/Integer;
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_START_AUTOMATIC_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-virtual {v5, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->DEFAULT_CONFIG_LOG_AUTO_START_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v10

    invoke-virtual {v6, v10}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Boolean;

    invoke-virtual {v6}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v6

    invoke-interface {v9, v5, v6}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v5

    if-ne v7, v5, :cond_3

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-virtual {v5, v9}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-interface {v6, v5, v8}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v5

    if-nez v5, :cond_3

    .line 492
    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v5

    add-int/2addr v0, v5

    goto :goto_1

    .line 495
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v3           #logType:Ljava/lang/Integer;
    :cond_4
    const-string v5, "storage_recovery"

    invoke-virtual {v5, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_6

    .line 496
    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v5}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .restart local v1       #i$:Ljava/util/Iterator;
    :cond_5
    :goto_2
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_6

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    .line 497
    .restart local v3       #logType:Ljava/lang/Integer;
    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_NEED_RECOVER_RUNNING_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v5, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-interface {v6, v5, v8}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v5

    if-eqz v5, :cond_5

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v5, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-interface {v6, v5, v8}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v5

    if-nez v5, :cond_5

    .line 502
    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v5

    add-int/2addr v0, v5

    .line 503
    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v5}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_NEED_RECOVER_RUNNING_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v5, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-interface {v6, v5, v8}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v5

    invoke-interface {v5}, Landroid/content/SharedPreferences$Editor;->apply()V

    goto :goto_2

    .line 509
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v3           #logType:Ljava/lang/Integer;
    :cond_6
    const-string v5, "MTKLogger/MTKLoggerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, " affectedLog="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 510
    if-lez v0, :cond_0

    .line 511
    invoke-virtual {p0, v0, p2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->startRecording(ILjava/lang/String;)Z

    goto/16 :goto_0

    .line 514
    :cond_7
    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v5}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .restart local v1       #i$:Ljava/util/Iterator;
    :cond_8
    :goto_3
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_c

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    .line 515
    .restart local v3       #logType:Ljava/lang/Integer;
    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-virtual {v5, v9}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-interface {v6, v5, v8}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v5

    if-ne v7, v5, :cond_a

    move v2, v7

    .line 517
    .local v2, isRunning:Z
    :goto_4
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_START_AUTOMATIC_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-virtual {v5, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->DEFAULT_CONFIG_LOG_AUTO_START_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v10

    invoke-virtual {v6, v10}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Boolean;

    invoke-virtual {v6}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v6

    invoke-interface {v9, v5, v6}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v5

    if-ne v7, v5, :cond_b

    move v4, v7

    .line 521
    .local v4, shouldAutoStarted:Z
    :goto_5
    if-nez v2, :cond_9

    if-eqz v4, :cond_8

    const-string v5, "sd_timeout"

    invoke-virtual {v5, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_8

    .line 523
    :cond_9
    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v5

    add-int/2addr v0, v5

    .line 525
    if-eqz v2, :cond_8

    const-string v5, "storage_full_or_lost"

    invoke-virtual {v5, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_8

    .line 528
    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v5}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->KEY_NEED_RECOVER_RUNNING_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-virtual {v5, v9}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-interface {v6, v5, v7}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v5

    invoke-interface {v5}, Landroid/content/SharedPreferences$Editor;->apply()V

    goto :goto_3

    .end local v2           #isRunning:Z
    .end local v4           #shouldAutoStarted:Z
    :cond_a
    move v2, v8

    .line 515
    goto :goto_4

    .restart local v2       #isRunning:Z
    :cond_b
    move v4, v8

    .line 517
    goto :goto_5

    .line 533
    .end local v2           #isRunning:Z
    .end local v3           #logType:Ljava/lang/Integer;
    :cond_c
    const-string v5, "MTKLogger/MTKLoggerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, " affectedLog="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 534
    if-lez v0, :cond_0

    .line 535
    invoke-virtual {p0, v0, p2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->stopRecording(ILjava/lang/String;)Z

    goto/16 :goto_0
.end method

.method private checkRemainingStorage()V
    .locals 10

    .prologue
    const v9, 0x7f070009

    const/4 v8, 0x0

    const v7, 0x7f020006

    const/16 v5, 0x1e

    .line 602
    invoke-static {p0}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/mediatek/mtklogger/utils/Utils;->getAvailableStorageSize(Ljava/lang/String;)I

    move-result v3

    .line 605
    .local v3, remainingSize:I
    if-ge v3, v5, :cond_3

    iget v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    if-eqz v4, :cond_0

    iget v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    if-lt v4, v5, :cond_3

    .line 607
    :cond_0
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Remaining log storage drop below water level, give a notification now"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 608
    sget-object v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    if-nez v4, :cond_1

    .line 609
    const-string v4, "notification"

    invoke-virtual {p0, v4}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/app/NotificationManager;

    sput-object v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    .line 611
    :cond_1
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Log storage drop down below water level, give out a notification"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 613
    new-instance v1, Landroid/app/Notification;

    invoke-direct {v1}, Landroid/app/Notification;-><init>()V

    .line 614
    .local v1, notification:Landroid/app/Notification;
    iput v7, v1, Landroid/app/Notification;->icon:I

    .line 615
    invoke-virtual {p0, v9}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getText(I)Ljava/lang/CharSequence;

    move-result-object v4

    iput-object v4, v1, Landroid/app/Notification;->tickerText:Ljava/lang/CharSequence;

    .line 616
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 617
    .local v0, backIntent:Landroid/content/Intent;
    new-instance v4, Landroid/content/ComponentName;

    const-string v5, "com.mediatek.mtklogger"

    const-string v6, "com.mediatek.mtklogger.MainActivity"

    invoke-direct {v4, v5, v6}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v4}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 618
    const/high16 v4, 0x2000

    invoke-virtual {v0, v4}, Landroid/content/Intent;->setFlags(I)Landroid/content/Intent;

    .line 619
    const-string v4, "reason_start"

    const-string v5, "low_storage"

    invoke-virtual {v0, v4, v5}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 620
    invoke-static {p0, v8, v0, v8}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v2

    .line 622
    .local v2, pendingIntent:Landroid/app/PendingIntent;
    invoke-virtual {p0, v9}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getText(I)Ljava/lang/CharSequence;

    move-result-object v4

    const v5, 0x7f07000a

    invoke-virtual {p0, v5}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getText(I)Ljava/lang/CharSequence;

    move-result-object v5

    invoke-virtual {v1, p0, v4, v5, v2}, Landroid/app/Notification;->setLatestEventInfo(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V

    .line 624
    sget-object v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    invoke-virtual {v4, v7, v1}, Landroid/app/NotificationManager;->notify(ILandroid/app/Notification;)V

    .line 638
    .end local v0           #backIntent:Landroid/content/Intent;
    .end local v1           #notification:Landroid/app/Notification;
    .end local v2           #pendingIntent:Landroid/app/PendingIntent;
    :cond_2
    :goto_0
    iput v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    .line 639
    return-void

    .line 630
    :cond_3
    iget v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    if-lez v4, :cond_2

    iget v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    if-ge v4, v5, :cond_2

    if-lt v3, v5, :cond_2

    .line 632
    sget-object v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    if-nez v4, :cond_4

    .line 633
    const-string v4, "notification"

    invoke-virtual {p0, v4}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/app/NotificationManager;

    sput-object v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    .line 635
    :cond_4
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Log storage resume upto water level, clear former notification"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 636
    sget-object v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    invoke-virtual {v4, v7}, Landroid/app/NotificationManager;->cancel(I)V

    goto :goto_0
.end method

.method private dealWithAdbCommand(ILjava/lang/String;)V
    .locals 7
    .parameter "logTypeCluster"
    .parameter "command"

    .prologue
    .line 642
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "-->dealWithAdbCommand(), logTypeCluster="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", command="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 643
    const-string v4, "start"

    invoke-virtual {v4, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 644
    const-string v4, "adb"

    invoke-virtual {p0, p1, v4}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->startRecording(ILjava/lang/String;)Z

    .line 669
    :cond_0
    :goto_0
    return-void

    .line 645
    :cond_1
    const-string v4, "stop"

    invoke-virtual {v4, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 646
    invoke-virtual {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->stopRecording(ILjava/lang/String;)Z

    goto :goto_0

    .line 649
    :cond_2
    sget-object v4, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_3
    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Integer;

    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    move-result v3

    .line 650
    .local v3, logType:I
    and-int v4, v3, p1

    if-eqz v4, :cond_3

    .line 654
    invoke-direct {p0, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v2

    .line 655
    .local v2, logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-eqz v2, :cond_5

    .line 656
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Send adb command ["

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "] to log "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 657
    iget-object v0, v2, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    .line 658
    .local v0, handler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
    if-eqz v0, :cond_4

    .line 659
    const/16 v4, 0x8

    invoke-virtual {v0, v4, p2}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v4

    invoke-virtual {v4}, Landroid/os/Message;->sendToTarget()V

    goto :goto_1

    .line 661
    :cond_4
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "When dealWithAdbCommand(), fail to get log instance handler of log ["

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "]"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    .line 665
    .end local v0           #handler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
    :cond_5
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Fail to get log instance("

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ") when dealing with adb command"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1
.end method

.method private getGlobalRunningStage()I
    .locals 9

    .prologue
    .line 830
    iget v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mGlobalRunningStage:I

    .line 831
    .local v3, stage:I
    const/4 v5, 0x0

    .line 832
    .local v5, type:I
    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v6}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Integer;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v2

    .line 833
    .local v2, logType:I
    invoke-direct {p0, v2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v1

    .line 834
    .local v1, logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-eqz v1, :cond_0

    .line 835
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/framework/LogInstance;->getGlobalRunningStage()I

    move-result v4

    .line 836
    .local v4, subStage:I
    if-le v4, v3, :cond_0

    .line 837
    move v5, v2

    .line 838
    move v3, v4

    goto :goto_0

    .line 842
    .end local v1           #logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    .end local v2           #logType:I
    .end local v4           #subStage:I
    :cond_1
    const-string v6, "MTKLogger/MTKLoggerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "<--getGlobalRunningStage(), current stage="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 843
    return v3
.end method

.method private getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;
    .locals 2
    .parameter "logType"

    .prologue
    .line 1171
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogInstanceMap:Landroid/util/SparseArray;

    invoke-virtual {v1, p1}, Landroid/util/SparseArray;->indexOfKey(I)I

    move-result v1

    if-gez v1, :cond_0

    .line 1172
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    invoke-static {p1, p0, v1}, Lcom/mediatek/mtklogger/framework/LogInstance;->getInstance(ILandroid/content/Context;Landroid/os/Handler;)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 1173
    .local v0, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-eqz v0, :cond_0

    .line 1174
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogInstanceMap:Landroid/util/SparseArray;

    invoke-virtual {v1, p1, v0}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 1177
    .end local v0           #instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    :cond_0
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogInstanceMap:Landroid/util/SparseArray;

    invoke-virtual {v1, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/mtklogger/framework/LogInstance;

    return-object v1
.end method

.method private handleGlobalRunningStageChange(I)V
    .locals 4
    .parameter "stageEvent"

    .prologue
    .line 823
    const-string v1, "MTKLogger/MTKLoggerService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "-->handleGlobalRunningStageChange(), stageEvent="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ", 1:start; 2:stop; 3:polling; 4:polling done."

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 825
    new-instance v0, Landroid/content/Intent;

    const-string v1, ""

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 826
    .local v0, intent:Landroid/content/Intent;
    const-string v1, "stage_event"

    invoke-virtual {v0, v1, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 827
    return-void
.end method

.method private handleLogStateChangeMsg(Landroid/os/Handler;Landroid/os/Message;)V
    .locals 9
    .parameter "handler"
    .parameter "msg"

    .prologue
    const/4 v8, 0x0

    .line 744
    const-string v5, "MTKLogger/MTKLoggerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "-->handleLogStateChangeMsg(), mCachedStartStopCmd = "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget v7, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 745
    iget v4, p2, Landroid/os/Message;->what:I

    .line 748
    .local v4, what:I
    const/16 v5, 0x3e8

    if-le v4, v5, :cond_4

    .line 749
    add-int/lit16 v1, v4, -0x3e8

    .line 751
    .local v1, logType:I
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    and-int/2addr v5, v1

    if-eqz v5, :cond_3

    .line 752
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    xor-int/2addr v5, v1

    iput v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    .line 753
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ":"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "9"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ";"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    .line 789
    :cond_0
    :goto_0
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    if-nez v5, :cond_2

    .line 790
    const-string v5, "MTKLogger/MTKLoggerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, " Notify other modules current state with broadcast. mCurrentAffectedLogType="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget v7, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ", currentRunningStatus="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget v7, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ", failReasonStr=["

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget-object v7, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "]"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 793
    new-instance v0, Landroid/content/Intent;

    const-string v5, "com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"

    invoke-direct {v0, v5}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 794
    .local v0, intent:Landroid/content/Intent;
    const-string v5, "affected_log_type"

    iget v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    invoke-virtual {v0, v5, v6}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 795
    const-string v5, "log_new_state"

    iget v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    invoke-virtual {v0, v5, v6}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 796
    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    invoke-static {v5}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v5

    if-nez v5, :cond_1

    .line 797
    const-string v5, "fail_reason"

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    invoke-virtual {v0, v5, v6}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 799
    :cond_1
    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->sendBroadcast(Landroid/content/Intent;)V

    .line 801
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateStartRecordingTime()V

    .line 803
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateLogFolderMonitor()V

    .line 805
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isAnyLogRunning()Z

    move-result v5

    if-eqz v5, :cond_9

    .line 806
    invoke-static {p0}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v5

    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRecordingLogPath:Ljava/lang/String;

    .line 812
    :goto_1
    iput v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    .line 813
    iput v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    .line 814
    const-string v5, ""

    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    .line 815
    iput v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mGlobalRunningStage:I

    .line 817
    .end local v0           #intent:Landroid/content/Intent;
    .end local v1           #logType:I
    :cond_2
    :goto_2
    return-void

    .line 755
    .restart local v1       #logType:I
    :cond_3
    const-string v5, "MTKLogger/MTKLoggerService"

    const-string v6, "Attention: timeout message should have been removed."

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 757
    .end local v1           #logType:I
    :cond_4
    const/4 v5, 0x1

    if-ne v4, v5, :cond_8

    .line 758
    iget v1, p2, Landroid/os/Message;->arg1:I

    .line 759
    .restart local v1       #logType:I
    iget v3, p2, Landroid/os/Message;->arg2:I

    .line 760
    .local v3, result:I
    iget-object v2, p2, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 761
    .local v2, reason:Ljava/lang/Object;
    const-string v5, "MTKLogger/MTKLoggerService"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, " MSG_LOG_STATE_CHANGED, logType="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ", result="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ", reason=["

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "]"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 762
    if-eqz v2, :cond_5

    const-string v6, ""

    move-object v5, v2

    check-cast v5, Ljava/lang/String;

    invoke-virtual {v6, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_5

    .line 763
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ":"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    check-cast v2, Ljava/lang/String;

    .end local v2           #reason:Ljava/lang/Object;
    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ";"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->failReasonStr:Ljava/lang/String;

    .line 765
    :cond_5
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    and-int/2addr v5, v1

    if-eqz v5, :cond_6

    .line 766
    const-string v5, "MTKLogger/MTKLoggerService"

    const-string v6, "Still waiting start/stop command response, mark it as finished."

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 767
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    xor-int/2addr v5, v1

    iput v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    .line 769
    add-int/lit16 v5, v1, 0x3e8

    invoke-virtual {p1, v5}, Landroid/os/Handler;->removeMessages(I)V

    .line 775
    :goto_3
    if-lez v3, :cond_7

    .line 776
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    or-int/2addr v5, v1

    iput v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    goto/16 :goto_0

    .line 771
    :cond_6
    const-string v5, "MTKLogger/MTKLoggerService"

    const-string v6, "No cached start/stop command for this log, treat it as self change"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 772
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    or-int/2addr v5, v1

    iput v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    goto :goto_3

    .line 778
    :cond_7
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    and-int/2addr v5, v1

    if-eqz v5, :cond_0

    .line 779
    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    xor-int/2addr v5, v1

    iput v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    goto/16 :goto_0

    .line 783
    .end local v1           #logType:I
    .end local v3           #result:I
    :cond_8
    const-string v5, "MTKLogger/MTKLoggerService"

    const-string v6, "Unknown message"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_2

    .line 808
    .restart local v0       #intent:Landroid/content/Intent;
    .restart local v1       #logType:I
    :cond_9
    const/4 v5, 0x0

    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRecordingLogPath:Ljava/lang/String;

    goto/16 :goto_1
.end method

.method private isAnyLogRunning()Z
    .locals 7

    .prologue
    .line 887
    const/4 v1, 0x0

    .line 888
    .local v1, isRunning:Z
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    .line 889
    .local v2, logType:Ljava/lang/Integer;
    const/4 v4, 0x1

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-virtual {v3, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    const/4 v6, 0x0

    invoke-interface {v5, v3, v6}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v3

    if-ne v4, v3, :cond_0

    .line 891
    const/4 v1, 0x1

    .line 895
    .end local v2           #logType:Ljava/lang/Integer;
    :cond_1
    const-string v3, "MTKLogger/MTKLoggerService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "<--isAnyLogRunning()? "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 896
    return v1
.end method

.method private isStorageReady()Z
    .locals 4

    .prologue
    const/4 v1, 0x1

    .line 546
    const-string v2, "/data"

    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentLogPathType:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 547
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "For phone internal storage, assume it\'s already ready"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 554
    :cond_0
    :goto_0
    return v1

    .line 550
    :cond_1
    invoke-static {p0}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentVolumeState(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v0

    .line 551
    .local v0, status:Ljava/lang/String;
    const-string v2, "mounted"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 554
    const/4 v1, 0x0

    goto :goto_0
.end method

.method private updateLogFolderMonitor()V
    .locals 5

    .prologue
    const/4 v4, 0x0

    .line 324
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isAnyLogRunning()Z

    move-result v0

    .line 326
    .local v0, isLogRunning:Z
    const-string v1, "MTKLogger/MTKLoggerService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "-->updateLogFolderMonitor(), isLogRunning="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ", mLogFolderMonitorThreadStopFlag="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 328
    if-eqz v0, :cond_1

    iget-boolean v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    if-eqz v1, :cond_1

    .line 329
    new-instance v1, Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    .line 330
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    invoke-virtual {v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;->start()V

    .line 331
    iput-boolean v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    .line 332
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "Log is running, so start monitor log folder"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 349
    :cond_0
    :goto_0
    return-void

    .line 333
    :cond_1
    if-nez v0, :cond_0

    iget-boolean v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    if-nez v1, :cond_0

    .line 334
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "Log is stopped, so need to stop log folder monitor if any exist."

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 335
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    .line 336
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    if-eqz v1, :cond_2

    .line 337
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    invoke-virtual {v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;->interrupt()V

    .line 338
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mMonitorLogFolderThread:Lcom/mediatek/mtklogger/framework/MTKLoggerService$LogFolderMonitor;

    .line 342
    :cond_2
    sget-object v1, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    if-nez v1, :cond_3

    .line 343
    const-string v1, "notification"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/app/NotificationManager;

    sput-object v1, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    .line 345
    :cond_3
    sget-object v1, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNM:Landroid/app/NotificationManager;

    const v2, 0x7f020006

    invoke-virtual {v1, v2}, Landroid/app/NotificationManager;->cancel(I)V

    .line 346
    iput v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mRemainingStorage:I

    goto :goto_0
.end method

.method private updateLogStatus()V
    .locals 15

    .prologue
    const/16 v14, 0x9

    const/4 v13, 0x0

    const/4 v12, 0x1

    .line 259
    const-string v9, "MTKLogger/MTKLoggerService"

    const-string v10, "-->updateLogStatus()"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 261
    new-instance v2, Lcom/mediatek/mtklogger/framework/LogInstance;

    invoke-direct {v2, p0}, Lcom/mediatek/mtklogger/framework/LogInstance;-><init>(Landroid/content/Context;)V

    .line 263
    .local v2, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v10, "md_assert_file_path"

    const-string v11, ""

    invoke-interface {v9, v10, v11}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 264
    .local v0, assertFileStr:Ljava/lang/String;
    const/4 v8, 0x0

    .line 265
    .local v8, needReconnectModemLog:Z
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-nez v9, :cond_0

    .line 266
    const-string v9, "MTKLogger/MTKLoggerService"

    const-string v10, " Modem assert file path is not null, need to re-show assert dialog"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 267
    const/4 v8, 0x1

    .line 269
    :cond_0
    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v9}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_1
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    if-eqz v9, :cond_5

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Integer;

    .line 270
    .local v6, logType:Ljava/lang/Integer;
    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP:Landroid/util/SparseArray;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v10

    invoke-virtual {v9, v10}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 271
    .local v4, key:Ljava/lang/String;
    const-string v9, "0"

    invoke-static {v4, v9}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 272
    .local v7, nativeStatus:Ljava/lang/String;
    const-string v9, "MTKLogger/MTKLoggerService"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, " Native log("

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, ") running status="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 273
    const-string v9, "1"

    invoke-virtual {v9, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_3

    .line 274
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v9}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v10

    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v11

    invoke-virtual {v9, v11}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/String;

    invoke-interface {v10, v9, v12}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v9

    invoke-interface {v9}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 275
    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v10

    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP:Landroid/util/SparseArray;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v11

    invoke-virtual {v9, v11}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/Integer;

    invoke-virtual {v9}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-virtual {v2, v10, v9, v12}, Lcom/mediatek/mtklogger/framework/LogInstance;->updateStatusBar(IIZ)V

    .line 277
    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-direct {p0, v9}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v5

    .line 278
    .local v5, logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    iget-object v9, v5, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v9, :cond_2

    .line 279
    iget-object v9, v5, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    invoke-virtual {v9, v14}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v9

    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_0

    .line 281
    :cond_2
    const-string v9, "MTKLogger/MTKLoggerService"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "When updateLogStatus(), fail to get log instance handler of log ["

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "]"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 285
    .end local v5           #logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    :cond_3
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v9}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v10

    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v11

    invoke-virtual {v9, v11}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/String;

    invoke-interface {v10, v9, v13}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v9

    invoke-interface {v9}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 286
    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v10

    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->KEY_LOG_TITLE_RES_IN_STSTUSBAR_MAP:Landroid/util/SparseArray;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v11

    invoke-virtual {v9, v11}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/Integer;

    invoke-virtual {v9}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-virtual {v2, v10, v9, v13}, Lcom/mediatek/mtklogger/framework/LogInstance;->updateStatusBar(IIZ)V

    .line 288
    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v9

    const/4 v10, 0x2

    if-ne v9, v10, :cond_1

    if-eqz v8, :cond_1

    .line 290
    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-direct {p0, v9}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v5

    .line 291
    .restart local v5       #logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    iget-object v9, v5, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v9, :cond_4

    .line 292
    iget-object v9, v5, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    invoke-virtual {v9, v14}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v9

    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_0

    .line 294
    :cond_4
    const-string v9, "MTKLogger/MTKLoggerService"

    const-string v10, "When try to reconnect to modem log, fail to get log instance handler"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 302
    .end local v4           #key:Ljava/lang/String;
    .end local v5           #logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    .end local v6           #logType:Ljava/lang/Integer;
    .end local v7           #nativeStatus:Ljava/lang/String;
    :cond_5
    sput-boolean v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->bootupFlag:Z

    .line 304
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isAnyLogRunning()Z

    move-result v9

    if-eqz v9, :cond_6

    .line 305
    invoke-static {p0}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v9

    iput-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRecordingLogPath:Ljava/lang/String;

    .line 310
    :goto_1
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateLogFolderMonitor()V

    .line 312
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateStartRecordingTime()V

    .line 315
    new-instance v3, Landroid/content/Intent;

    const-string v9, "com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"

    invoke-direct {v3, v9}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 316
    .local v3, intent:Landroid/content/Intent;
    invoke-virtual {p0, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->sendBroadcast(Landroid/content/Intent;)V

    .line 317
    return-void

    .line 307
    .end local v3           #intent:Landroid/content/Intent;
    :cond_6
    const/4 v9, 0x0

    iput-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRecordingLogPath:Ljava/lang/String;

    goto :goto_1
.end method

.method private updateStartRecordingTime()V
    .locals 8

    .prologue
    const-wide/16 v6, 0x0

    .line 861
    const-string v3, "MTKLogger/MTKLoggerService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "-->updateStartRecordingTime(), mCurrentRunningStatus="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 862
    iget v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    if-nez v3, :cond_1

    .line 863
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isAnyLogRunning()Z

    move-result v2

    .line 864
    .local v2, isLogRunning:Z
    if-nez v2, :cond_0

    .line 865
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "begin_recording_time"

    invoke-interface {v3, v4, v6, v7}, Landroid/content/SharedPreferences$Editor;->putLong(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 880
    .end local v2           #isLogRunning:Z
    :cond_0
    :goto_0
    return-void

    .line 869
    :cond_1
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v4, "begin_recording_time"

    invoke-interface {v3, v4, v6, v7}, Landroid/content/SharedPreferences;->getLong(Ljava/lang/String;J)J

    move-result-wide v0

    .line 871
    .local v0, currentRecordingTime:J
    cmp-long v3, v0, v6

    if-gtz v3, :cond_0

    .line 872
    const-string v3, "MTKLogger/MTKLoggerService"

    const-string v4, "Former log start time is 0, set to current system time."

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 875
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "begin_recording_time"

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v5

    invoke-interface {v3, v4, v5, v6}, Landroid/content/SharedPreferences$Editor;->putLong(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "system_time_reset"

    const/4 v5, 0x0

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto :goto_0
.end method


# virtual methods
.method public getLogInstanceRunningStatus(I)I
    .locals 5
    .parameter "logType"

    .prologue
    .line 847
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "-->getLogInstanceRunningStatus(), logType="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 848
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 849
    .local v0, logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    const/4 v1, -0x1

    .line 850
    .local v1, status:I
    if-eqz v0, :cond_0

    .line 851
    invoke-virtual {v0}, Lcom/mediatek/mtklogger/framework/LogInstance;->getLogRunningStatus()I

    move-result v1

    .line 853
    :cond_0
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "<--getLogInstanceRunningStatus(), status="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 854
    return v1
.end method

.method public onBind(Landroid/content/Intent;)Landroid/os/IBinder;
    .locals 2
    .parameter "intent"

    .prologue
    .line 353
    const-string v0, "MTKLogger/MTKLoggerService"

    const-string v1, "-->onBind()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 354
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mBind:Lcom/mediatek/mtklogger/IMTKLoggerManager$Stub;

    return-object v0
.end method

.method public onCreate()V
    .locals 4

    .prologue
    const/4 v3, 0x0

    .line 108
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "-->onCreate()"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 109
    invoke-super {p0}, Landroid/app/Service;->onCreate()V

    .line 110
    const-string v1, "log_settings"

    invoke-virtual {p0, v1, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 111
    invoke-static {p0}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 113
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    .line 114
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.MEDIA_BAD_REMOVAL"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 115
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.MEDIA_EJECT"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 116
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.MEDIA_REMOVED"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 117
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.MEDIA_UNMOUNTED"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 118
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.MEDIA_MOUNTED"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 119
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "file"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addDataScheme(Ljava/lang/String;)V

    .line 120
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSDStatusIntentFilter:Landroid/content/IntentFilter;

    invoke-virtual {p0, v1, v2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 123
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mPhoneStorageIntentFilter:Landroid/content/IntentFilter;

    .line 124
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mPhoneStorageIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.DEVICE_STORAGE_OK"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 125
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mPhoneStorageIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.DEVICE_STORAGE_LOW"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 126
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mPhoneStorageIntentFilter:Landroid/content/IntentFilter;

    invoke-virtual {p0, v1, v2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 129
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mIPOIntentFilter:Landroid/content/IntentFilter;

    .line 130
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mIPOIntentFilter:Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.ACTION_SHUTDOWN_IPO"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 131
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mIPOReceiver:Landroid/content/BroadcastReceiver;

    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mIPOIntentFilter:Landroid/content/IntentFilter;

    invoke-virtual {p0, v1, v2}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 133
    invoke-static {}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPathType()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentLogPathType:Ljava/lang/String;

    .line 135
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 136
    .local v0, userFilter:Landroid/content/IntentFilter;
    const-string v1, "android.intent.action.USER_SWITCHED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 137
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mUserSwitchReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v1, v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 140
    sput-boolean v3, Lcom/mediatek/mtklogger/framework/LogReceiver;->canKillSelf:Z

    .line 141
    return-void
.end method

.method public onDestroy()V
    .locals 3

    .prologue
    .line 239
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "-->onDestroy()"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 240
    const-string v1, "ro.monkey"

    const-string v2, "false"

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 242
    .local v0, isMonkeyRunning:Ljava/lang/String;
    const-string v1, "true"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 243
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "Monkey is running, MTKLoggerService destroy failed!"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 252
    :goto_0
    return-void

    .line 247
    :cond_0
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 248
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mIPOReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 249
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mUserSwitchReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 250
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mLogFolderMonitorThreadStopFlag:Z

    .line 251
    invoke-super {p0}, Landroid/app/Service;->onDestroy()V

    goto :goto_0
.end method

.method public onStartCommand(Landroid/content/Intent;II)I
    .locals 12
    .parameter "intent"
    .parameter "flags"
    .parameter "startId"

    .prologue
    const-wide/32 v10, 0x9c40

    const/4 v9, 0x2

    const/4 v8, 0x0

    const/4 v7, 0x1

    .line 145
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "-->onStartCommand(), isStarted="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    sget-boolean v6, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStarted:Z

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", old mServiceStartType="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 146
    if-eqz p1, :cond_6

    .line 147
    const-string v4, "startup_type"

    invoke-virtual {p1, v4}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    iput-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    .line 148
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, " new mServiceStartType="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 154
    :goto_0
    sget-boolean v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStarted:Z

    if-nez v4, :cond_4

    .line 155
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "MTKLoggerService.onStartCommand mServiceStartType="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", thread name = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 161
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateLogStatus()V

    .line 163
    const-string v4, "boot"

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_0

    const-string v4, "ipo"

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_8

    .line 166
    :cond_0
    sput-boolean v8, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isInIPOShutdown:Z

    .line 170
    const-string v4, "debug.mtk.aee.db"

    const-string v5, ""

    invoke-static {v4, v5}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 171
    .local v2, rebootFlag:Ljava/lang/String;
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_1

    const-string v4, "2:"

    invoke-virtual {v2, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_2

    .line 176
    :cond_1
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v5, "mobilelog_enable"

    invoke-interface {v4, v5, v8}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v4

    if-ne v7, v4, :cond_2

    .line 178
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v4

    const-string v5, "mobilelog_enable"

    invoke-interface {v4, v5, v8}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v4

    invoke-interface {v4}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 183
    :cond_2
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStorageReady()Z

    move-result v4

    if-eqz v4, :cond_7

    .line 184
    sput-boolean v8, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    .line 186
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-direct {p0, v7, v4}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->changeLogRunningStatus(ZLjava/lang/String;)V

    .line 212
    .end local v2           #rebootFlag:Ljava/lang/String;
    :cond_3
    :goto_1
    sput-boolean v7, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStarted:Z

    .line 215
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Service is first started, check whether need to resume TagLog process"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 216
    invoke-static {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->getInstance(Landroid/content/Context;)Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-result-object v4

    invoke-virtual {v4}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->resumeTag()V

    .line 220
    :cond_4
    sget-boolean v4, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    if-nez v4, :cond_a

    const-string v4, "adb"

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_a

    .line 221
    const-string v4, "cmd_target"

    invoke-virtual {p1, v4, v8}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 222
    .local v1, logType:I
    const-string v4, "cmd_name"

    invoke-virtual {p1, v4}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 223
    .local v0, command:Ljava/lang/String;
    invoke-direct {p0, v1, v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->dealWithAdbCommand(ILjava/lang/String;)V

    .line 234
    .end local v0           #command:Ljava/lang/String;
    .end local v1           #logType:I
    :cond_5
    :goto_2
    invoke-super {p0, p1, p2, p3}, Landroid/app/Service;->onStartCommand(Landroid/content/Intent;II)I

    move-result v4

    return v4

    .line 150
    :cond_6
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "intent == null, maybe this service is restarted by system."

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 151
    const/4 v4, 0x0

    iput-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    goto/16 :goto_0

    .line 188
    .restart local v2       #rebootFlag:Ljava/lang/String;
    :cond_7
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "At bootup/IPO time, SD is not ready yet, wait."

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 189
    sput-boolean v7, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    .line 190
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Storage is not ready yet, waiting for mounted signal."

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 191
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    invoke-virtual {v5, v9}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v5

    invoke-virtual {v4, v5, v10, v11}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 193
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v4

    const-string v5, "waiting_sd_ready_reason"

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-interface {v4, v5, v6}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v4

    invoke-interface {v4}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto :goto_1

    .line 197
    .end local v2           #rebootFlag:Ljava/lang/String;
    :cond_8
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v5, "waiting_sd_ready_reason"

    const-string v6, ""

    invoke-interface {v4, v5, v6}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 198
    .local v3, waitSDStatuStr:Ljava/lang/String;
    const-string v4, "MTKLogger/MTKLoggerService"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "At service first init time(maybe killed by system), waitSDStatuStr="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 199
    invoke-static {v3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v4

    if-nez v4, :cond_3

    .line 200
    sput-boolean v7, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    .line 201
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isStorageReady()Z

    move-result v4

    if-eqz v4, :cond_9

    .line 202
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "At service restarted time, SD is already ready, continue boot flow"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 203
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v4

    const-string v5, "waiting_sd_ready_reason"

    invoke-interface {v4, v5}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v4

    invoke-interface {v4}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 204
    invoke-direct {p0, v7, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->changeLogRunningStatus(ZLjava/lang/String;)V

    goto/16 :goto_1

    .line 206
    :cond_9
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "At service restarted time, SD is still not ready, keep waiting"

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 207
    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    invoke-virtual {v5, v9}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v5

    invoke-virtual {v4, v5, v10, v11}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto/16 :goto_1

    .line 224
    .end local v3           #waitSDStatuStr:Ljava/lang/String;
    :cond_a
    const-string v4, "update"

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_b

    .line 226
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Modem restart finished, update log running status now."

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 227
    iget v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    or-int/lit8 v4, v4, 0x2

    iput v4, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    .line 228
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->updateLogStatus()V

    goto/16 :goto_2

    .line 229
    :cond_b
    const-string v4, "exception_happen"

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mServiceStartType:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_5

    .line 230
    const-string v4, "MTKLogger/MTKLoggerService"

    const-string v5, "Got exception happens message, begin to tag log now."

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 231
    invoke-static {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->getInstance(Landroid/content/Context;)Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-result-object v4

    invoke-virtual {v4, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->beginTag(Landroid/content/Intent;)V

    goto/16 :goto_2
.end method

.method public startRecording(ILjava/lang/String;)Z
    .locals 12
    .parameter "logTypeCluster"
    .parameter "reason"

    .prologue
    .line 1047
    const-string v8, "MTKLogger/MTKLoggerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "-->startRecording(), logTypeCluster="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, ", reason="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1051
    const/4 v7, 0x0

    .line 1053
    .local v7, result:Z
    const-string v8, "MTKLogger/MTKLoggerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "MTKLoggerService.startRecording() thread name = "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 1055
    iget v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    if-eqz v8, :cond_0

    .line 1056
    const-string v8, "MTKLogger/MTKLoggerService"

    const-string v9, "Server is busy dealing former command, wait till it is free please"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1057
    const/4 v8, 0x0

    .line 1111
    :goto_0
    return v8

    .line 1059
    :cond_0
    sget-boolean v8, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isWaitingSDReady:Z

    if-eqz v8, :cond_1

    .line 1061
    const-string v8, "MTKLogger/MTKLoggerService"

    const-string v9, "Server is waiting for SD ready, wait till it is OK please"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1062
    const/4 v8, 0x0

    goto :goto_0

    .line 1065
    :cond_1
    iput p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    .line 1066
    iput p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    .line 1067
    const/4 v8, 0x0

    iput v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    .line 1069
    sget-object v8, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v8}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v4

    .local v4, i$:Ljava/util/Iterator;
    :cond_2
    :goto_1
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_6

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/Integer;

    invoke-virtual {v8}, Ljava/lang/Integer;->intValue()I

    move-result v6

    .line 1070
    .local v6, logType:I
    iget v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    and-int/2addr v8, v6

    if-eqz v8, :cond_2

    .line 1074
    invoke-direct {p0, v6}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v5

    .line 1075
    .local v5, logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-eqz v5, :cond_5

    .line 1076
    iget-object v3, v5, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    .line 1077
    .local v3, handler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
    if-eqz v3, :cond_4

    .line 1078
    iget-object v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    add-int/lit16 v10, v6, 0x3e8

    invoke-virtual {v9, v10}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v9

    const-wide/16 v10, 0x4e20

    invoke-virtual {v8, v9, v10, v11}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 1081
    int-to-double v8, v6

    invoke-static {v8, v9}, Ljava/lang/Math;->log(D)D

    move-result-wide v8

    const-wide/high16 v10, 0x4000

    invoke-static {v10, v11}, Ljava/lang/Math;->log(D)D

    move-result-wide v10

    div-double v0, v8, v10

    .line 1082
    .local v0, delayIndex:D
    const-string v8, "MTKLogger/MTKLoggerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "When start recording, for log ["

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "], delay index="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v0, v1}, Ljava/lang/StringBuilder;->append(D)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 1083
    const/4 v2, 0x0

    .line 1084
    .local v2, firstItemDelay:I
    const-string v8, "storage_recovery"

    invoke-virtual {v8, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_3

    .line 1088
    const-string v8, "MTKLogger/MTKLoggerService"

    const-string v9, "For storage recovery event, wait more time for its ready"

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1089
    const/16 v2, 0x64

    .line 1091
    :cond_3
    const/4 v8, 0x1

    invoke-virtual {v3, v8, p2}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v8

    double-to-int v9, v0

    mul-int/lit16 v9, v9, 0x12c

    add-int/2addr v9, v2

    int-to-long v9, v9

    invoke-virtual {v3, v8, v9, v10}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 1093
    const/4 v7, 0x1

    .line 1094
    goto :goto_1

    .line 1095
    .end local v0           #delayIndex:D
    .end local v2           #firstItemDelay:I
    :cond_4
    const-string v8, "MTKLogger/MTKLoggerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "When startRecording(), fail to get log instance handler  of log ["

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "]."

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1097
    iget-object v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    const/4 v9, 0x1

    const/4 v10, 0x0

    const-string v11, "4"

    invoke-virtual {v8, v9, v6, v10, v11}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v8

    invoke-virtual {v8}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_1

    .line 1101
    .end local v3           #handler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
    :cond_5
    const-string v8, "MTKLogger/MTKLoggerService"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Fail to get log instance of type: "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1102
    iget-object v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    const/4 v9, 0x1

    const/4 v10, 0x0

    const-string v11, "6"

    invoke-virtual {v8, v9, v6, v10, v11}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v8

    invoke-virtual {v8}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_1

    .line 1107
    .end local v5           #logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    .end local v6           #logType:I
    :cond_6
    if-eqz v7, :cond_7

    .line 1108
    const/4 v8, 0x1

    invoke-direct {p0, v8}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->handleGlobalRunningStageChange(I)V

    .line 1109
    const/4 v8, 0x1

    iput v8, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mGlobalRunningStage:I

    :cond_7
    move v8, v7

    .line 1111
    goto/16 :goto_0
.end method

.method public stopRecording(ILjava/lang/String;)Z
    .locals 13
    .parameter "logTypeCluster"
    .parameter "reason"

    .prologue
    const/4 v12, 0x2

    const/4 v11, 0x1

    const/4 v10, 0x0

    .line 1115
    const-string v6, "MTKLogger/MTKLoggerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "-->stopRecording(), logTypeCluster="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, ", reason="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1119
    const/4 v4, 0x0

    .line 1121
    .local v4, result:Z
    iget v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    if-eqz v6, :cond_0

    .line 1122
    const-string v6, "MTKLogger/MTKLoggerService"

    const-string v7, "Server is busy dealing former command, wait till it\'s free please"

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    move v5, v4

    .line 1162
    .end local v4           #result:Z
    .local v5, result:I
    :goto_0
    return v5

    .line 1125
    .end local v5           #result:I
    .restart local v4       #result:Z
    :cond_0
    iput p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentAffectedLogType:I

    .line 1126
    iput p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    .line 1127
    iput v10, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCurrentRunningStatus:I

    .line 1129
    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v6}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_1
    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_4

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Integer;

    invoke-virtual {v6}, Ljava/lang/Integer;->intValue()I

    move-result v3

    .line 1130
    .local v3, logType:I
    iget v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mCachedStartStopCmd:I

    and-int/2addr v6, v3

    if-eqz v6, :cond_1

    .line 1134
    invoke-direct {p0, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v2

    .line 1135
    .local v2, logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-eqz v2, :cond_3

    .line 1136
    iget-object v0, v2, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    .line 1137
    .local v0, handler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
    if-eqz v0, :cond_2

    .line 1138
    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    iget-object v7, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    add-int/lit16 v8, v3, 0x3e8

    invoke-virtual {v7, v8}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v7

    const-wide/16 v8, 0x4e20

    invoke-virtual {v6, v7, v8, v9}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 1140
    const/4 v6, 0x3

    invoke-virtual {v0, v6, p2}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v6

    invoke-virtual {v6}, Landroid/os/Message;->sendToTarget()V

    .line 1142
    const/4 v4, 0x1

    goto :goto_1

    .line 1144
    :cond_2
    const-string v6, "MTKLogger/MTKLoggerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "When stopRecording(), fail to get log instance handler  of log ["

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "]."

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1146
    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    const-string v7, "4"

    invoke-virtual {v6, v11, v3, v10, v7}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v6

    invoke-virtual {v6}, Landroid/os/Message;->sendToTarget()V

    goto :goto_1

    .line 1151
    .end local v0           #handler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
    :cond_3
    const-string v6, "MTKLogger/MTKLoggerService"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Fail to get log instance of logtype "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 1152
    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mNativeStateHandler:Landroid/os/Handler;

    const-string v7, "6"

    invoke-virtual {v6, v11, v3, v10, v7}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v6

    invoke-virtual {v6}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_1

    .line 1157
    .end local v2           #logInstance:Lcom/mediatek/mtklogger/framework/LogInstance;
    .end local v3           #logType:I
    :cond_4
    if-eqz v4, :cond_5

    .line 1158
    invoke-direct {p0, v12}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->handleGlobalRunningStageChange(I)V

    .line 1159
    iput v12, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->mGlobalRunningStage:I

    :cond_5
    move v5, v4

    .line 1162
    .restart local v5       #result:I
    goto/16 :goto_0
.end method
