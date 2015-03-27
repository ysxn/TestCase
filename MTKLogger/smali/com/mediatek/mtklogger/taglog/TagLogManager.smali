.class public Lcom/mediatek/mtklogger/taglog/TagLogManager;
.super Ljava/lang/Object;
.source "TagLogManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/mtklogger/taglog/TagLogManager$ResumeTagThread;,
        Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;,
        Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
    }
.end annotation


# static fields
.field private static final COMPRESS_SPEED:I = 0x1

.field private static final DIALOG_ALL_LOGTOOL_STOPED:I = 0x12e

.field private static final DIALOG_INPUT:I = 0x12d

.field private static final DIALOG_LACK_OF_SDSPACE:I = 0x12f

.field private static final DIALOG_START_PROGRESS:I = 0x132

.field private static final DIALOG_ZIP_LOG_FAIL:I = 0x131

.field private static final EVENT_ALL_LOGTOOL_STOPED:I = 0xcd

.field private static final EVENT_CHECK_INPUTDIALOG_TIMEOUT:I = 0xd1

.field private static final EVENT_CREATE_INPUTDIALOG:I = 0xcb

.field private static final EVENT_ZIP_LOG_FAIL:I = 0xcf

.field private static final EVENT_ZIP_LOG_SUCCESS:I = 0xce

.field private static final INPUT_TIMEOUT:I = 0x1d4c0

.field private static final LOGPATHKEY:Landroid/util/SparseArray; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final MONITOR_TIMER:I = 0xc8

.field private static final STOPPED_TIMEOUT:I = 0x3e80

.field private static final TAG:Ljava/lang/String; = "MTKLogger/TagLogManager"

.field private static final WAIT_MODEM_INTENT:I = 0x3e8

.field private static instance:Lcom/mediatek/mtklogger/taglog/TagLogManager;

.field private static isTagingLog:Z


# instance fields
.field private mContext:Landroid/content/Context;

.field private mCurrentLogFolderList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lcom/mediatek/mtklogger/taglog/LogInformation;",
            ">;"
        }
    .end annotation
.end field

.field private mDataFromExtras:Landroid/os/Bundle;

.field private mDbPathFromAee:Ljava/lang/String;

.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field private mDialog:Landroid/app/AlertDialog;

.field private mIgnoreMdLog:Z

.field private mIsFromMainActivity:Z

.field private mIsFromReboot:Z

.field private mIsInputDialogClicked:Z

.field private mIsModemExp:Z

.field private mIsTagInputNull:Z

.field private mIsTaglogClicked:Z

.field private mIsWaitingLogStateChange:Z

.field private mLogPathInTagLog:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

.field private mLogStateReceiver:Landroid/content/BroadcastReceiver;

.field private mLogToolStatus:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation
.end field

.field private mManualSaveLog:Z

.field private mMonitorTimer:Ljava/util/Timer;

.field mNeedMoreSpace:J

.field private mProgressDialog:Landroid/app/ProgressDialog;

.field private mSharedPreferences:Landroid/content/SharedPreferences;

.field private mTag:Ljava/lang/String;

.field private mTagLogResult:Ljava/lang/String;

.field private mTotalFilesCount:I

.field private mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .prologue
    .line 76
    const/4 v0, 0x0

    sput-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->instance:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    .line 85
    const/4 v0, 0x0

    sput-boolean v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->isTagingLog:Z

    .line 124
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->LOGPATHKEY:Landroid/util/SparseArray;

    .line 126
    sget-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->LOGPATHKEY:Landroid/util/SparseArray;

    const/4 v1, 0x2

    const-string v2, "ModemLogPath"

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 127
    sget-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->LOGPATHKEY:Landroid/util/SparseArray;

    const/4 v1, 0x1

    const-string v2, "MobileLogPath"

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 128
    sget-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->LOGPATHKEY:Landroid/util/SparseArray;

    const/4 v1, 0x4

    const-string v2, "NetLogPath"

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 129
    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .locals 3
    .parameter "context"

    .prologue
    const/4 v0, 0x0

    const/4 v2, 0x0

    .line 134
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 77
    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    .line 87
    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    .line 94
    const-string v0, ""

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTag:Ljava/lang/String;

    .line 96
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mNeedMoreSpace:J

    .line 98
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    .line 101
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogToolStatus:Landroid/util/SparseArray;

    .line 105
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromMainActivity:Z

    .line 106
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    .line 107
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsTagInputNull:Z

    .line 108
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsTaglogClicked:Z

    .line 109
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z

    .line 113
    iput v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    .line 132
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z

    .line 147
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsWaitingLogStateChange:Z

    .line 148
    new-instance v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$1;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$1;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogStateReceiver:Landroid/content/BroadcastReceiver;

    .line 135
    const-string v0, "MTKLogger/TagLogManager"

    const-string v1, "<init>"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 136
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    .line 137
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->init()V

    .line 138
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsWaitingLogStateChange:Z

    return v0
.end method

.method static synthetic access$002(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsWaitingLogStateChange:Z

    return p1
.end method

.method static synthetic access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V

    return-void
.end method

.method static synthetic access$1100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    return-object v0
.end method

.method static synthetic access$1300(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsInputDialogClicked:Z

    return v0
.end method

.method static synthetic access$1302(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsInputDialogClicked:Z

    return p1
.end method

.method static synthetic access$1402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$1500(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 52
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V

    return-void
.end method

.method static synthetic access$1600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/Context;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 52
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->createProgressDialog()V

    return-void
.end method

.method static synthetic access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$1900(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/util/SparseArray;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->getLogPath()Landroid/util/SparseArray;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/os/Bundle;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;

    return-object v0
.end method

.method static synthetic access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    return-object v0
.end method

.method static synthetic access$2002(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/util/List;)Ljava/util/List;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    return-object p1
.end method

.method static synthetic access$2100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z

    return v0
.end method

.method static synthetic access$2102(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z

    return p1
.end method

.method static synthetic access$2200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/framework/MTKLoggerService;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    return-object v0
.end method

.method static synthetic access$2300(Lcom/mediatek/mtklogger/taglog/TagLogManager;)I
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    return v0
.end method

.method static synthetic access$2302(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)I
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    return p1
.end method

.method static synthetic access$2312(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)I
    .locals 1
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iget v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    add-int/2addr v0, p1

    iput v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    return v0
.end method

.method static synthetic access$2400(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/app/ProgressDialog;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    return-object v0
.end method

.method static synthetic access$2500(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->startOrStopAllLogTool(Z)V

    return-void
.end method

.method static synthetic access$2600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/lang/String;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTag:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$2602(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTag:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$2700(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->rememberCurrentTaggingLogFolder(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$2800(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->tagSelectedLogFolder(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$2900(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/Timer;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mMonitorTimer:Ljava/util/Timer;

    return-object v0
.end method

.method static synthetic access$2902(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/util/Timer;)Ljava/util/Timer;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mMonitorTimer:Ljava/util/Timer;

    return-object p1
.end method

.method static synthetic access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V

    return-void
.end method

.method static synthetic access$3000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsTaglogClicked:Z

    return v0
.end method

.method static synthetic access$3002(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsTaglogClicked:Z

    return p1
.end method

.method static synthetic access$3100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsTagInputNull:Z

    return v0
.end method

.method static synthetic access$3102(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsTagInputNull:Z

    return p1
.end method

.method static synthetic access$400(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    return v0
.end method

.method static synthetic access$402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    return p1
.end method

.method static synthetic access$502(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDbPathFromAee:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z

    return v0
.end method

.method static synthetic access$602(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z

    return p1
.end method

.method static synthetic access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z

    return v0
.end method

.method static synthetic access$702(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 52
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z

    return p1
.end method

.method static synthetic access$800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 52
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->initToolStatus()V

    return-void
.end method

.method static synthetic access$900(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/util/SparseArray;
    .locals 1
    .parameter "x0"

    .prologue
    .line 52
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogToolStatus:Landroid/util/SparseArray;

    return-object v0
.end method

.method private createDialog(I)V
    .locals 12
    .parameter "id"

    .prologue
    .line 888
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-direct {v0, v4}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 889
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    sparse-switch p1, :sswitch_data_0

    .line 1061
    :goto_0
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v4

    iput-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    .line 1062
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    const/4 v5, 0x0

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog;->setCancelable(Z)V

    .line 1063
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v4}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v3

    .line 1064
    .local v3, win:Landroid/view/Window;
    const/16 v4, 0x7d3

    invoke-virtual {v3, v4}, Landroid/view/Window;->setType(I)V

    .line 1065
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v4}, Landroid/app/AlertDialog;->show()V

    .line 1066
    return-void

    .line 891
    .end local v3           #win:Landroid/view/Window;
    :sswitch_0
    const/4 v4, 0x0

    iput-boolean v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsInputDialogClicked:Z

    .line 892
    new-instance v2, Landroid/widget/EditText;

    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-direct {v2, v4}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    .line 893
    .local v2, inputText:Landroid/widget/EditText;
    new-instance v4, Lcom/mediatek/mtklogger/taglog/TagLogManager$4;

    invoke-direct {v4, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$4;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v2, v4}, Landroid/widget/EditText;->setCustomSelectionActionModeCallback(Landroid/view/ActionMode$Callback;)V

    .line 910
    const/4 v4, 0x0

    invoke-virtual {v2, v4}, Landroid/widget/EditText;->setLongClickable(Z)V

    .line 911
    const/4 v4, 0x0

    invoke-virtual {v2, v4}, Landroid/widget/EditText;->setTextIsSelectable(Z)V

    .line 913
    new-instance v4, Lcom/mediatek/mtklogger/taglog/TagLogManager$5;

    invoke-direct {v4, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$5;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v2, v4}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 929
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;

    const-string v5, "taglogInputName"

    invoke-virtual {v4, v5}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 930
    .local v1, fromPreInputName:Ljava/lang/String;
    if-nez v1, :cond_0

    const-string v1, ""

    .end local v1           #fromPreInputName:Ljava/lang/String;
    :cond_0
    invoke-virtual {v2, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 931
    const v4, 0x7f070068

    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x7f07006c

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    invoke-virtual {v4, v2}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x104000a

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$7;

    invoke-direct {v6, p0, v2}, Lcom/mediatek/mtklogger/taglog/TagLogManager$7;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;Landroid/widget/EditText;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const/high16 v5, 0x104

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$6;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$6;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    goto :goto_0

    .line 974
    .end local v2           #inputText:Landroid/widget/EditText;
    :sswitch_1
    const v4, 0x7f070068

    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x7f07006d

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x104000a

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$9;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$9;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const/high16 v5, 0x104

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$8;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$8;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    goto/16 :goto_0

    .line 995
    :sswitch_2
    const v4, 0x7f07006a

    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    iget-object v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    const v6, 0x7f07006b

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    new-instance v9, Ljava/text/DecimalFormat;

    const-string v10, ".00"

    invoke-direct {v9, v10}, Ljava/text/DecimalFormat;-><init>(Ljava/lang/String;)V

    iget-wide v10, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mNeedMoreSpace:J

    long-to-float v10, v10

    const/high16 v11, 0x4480

    div-float/2addr v10, v11

    const/high16 v11, 0x4480

    div-float/2addr v10, v11

    float-to-double v10, v10

    invoke-virtual {v9, v10, v11}, Ljava/text/DecimalFormat;->format(D)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-virtual {v5, v6, v7}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x104000a

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$11;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$11;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const/high16 v5, 0x104

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$10;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$10;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    goto/16 :goto_0

    .line 1030
    :sswitch_3
    const v4, 0x7f070069

    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x7f07006e

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x104000a

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$12;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$12;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    goto/16 :goto_0

    .line 1041
    :sswitch_4
    const v4, 0x7f070069

    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x7f07006f

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x104000a

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$13;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$13;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    goto/16 :goto_0

    .line 1050
    :sswitch_5
    const v4, 0x7f070069

    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x7f070070

    invoke-virtual {v4, v5}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    move-result-object v4

    const v5, 0x104000a

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$14;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$14;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v4, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    goto/16 :goto_0

    .line 889
    nop

    :sswitch_data_0
    .sparse-switch
        0x12d -> :sswitch_0
        0x12e -> :sswitch_1
        0x12f -> :sswitch_2
        0x131 -> :sswitch_3
        0x193 -> :sswitch_4
        0x194 -> :sswitch_5
    .end sparse-switch
.end method

.method private createProgressDialog()V
    .locals 7

    .prologue
    const/4 v6, 0x0

    .line 809
    const/4 v1, 0x0

    .line 810
    .local v1, timer:I
    :goto_0
    iget v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    if-nez v3, :cond_2

    .line 812
    add-int/lit8 v1, v1, 0x32

    .line 813
    const/16 v3, 0x2710

    if-lt v1, v3, :cond_1

    .line 814
    :try_start_0
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "Create progress dialog failed! The total files count calculated error!"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 860
    :cond_0
    :goto_1
    return-void

    .line 817
    :cond_1
    const-wide/16 v3, 0x32

    invoke-static {v3, v4}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 818
    :catch_0
    move-exception v0

    .line 819
    .local v0, e:Ljava/lang/InterruptedException;
    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_0

    .line 822
    .end local v0           #e:Ljava/lang/InterruptedException;
    :cond_2
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    if-nez v3, :cond_5

    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    if-eqz v3, :cond_5

    .line 823
    new-instance v3, Landroid/app/ProgressDialog;

    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-direct {v3, v4}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    .line 824
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    if-eqz v3, :cond_6

    .line 825
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v3, v6}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 826
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    const v4, 0x7f070068

    invoke-virtual {v3, v4}, Landroid/app/ProgressDialog;->setTitle(I)V

    .line 827
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-virtual {v4}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f070071

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 828
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v3, v6}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 829
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    const/4 v4, 0x1

    invoke-virtual {v3, v4}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 830
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    const/4 v4, -0x1

    iget-object v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-virtual {v5}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v5

    const v6, 0x7f070072

    invoke-virtual {v5, v6}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object v5

    new-instance v6, Lcom/mediatek/mtklogger/taglog/TagLogManager$2;

    invoke-direct {v6, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$2;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    invoke-virtual {v3, v4, v5, v6}, Landroid/app/ProgressDialog;->setButton(ILjava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)V

    .line 843
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v3}, Landroid/app/ProgressDialog;->getWindow()Landroid/view/Window;

    move-result-object v2

    .line 844
    .local v2, win:Landroid/view/Window;
    const/16 v3, 0x7d3

    invoke-virtual {v2, v3}, Landroid/view/Window;->setType(I)V

    .line 845
    iget-boolean v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    if-nez v3, :cond_3

    iget-boolean v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromMainActivity:Z

    if-eqz v3, :cond_4

    .line 846
    :cond_3
    const-string v3, "MTKLogger/TagLogManager"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "mManualSaveLog?"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-boolean v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "; mIsFromMainActivity?"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-boolean v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromMainActivity:Z

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 848
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v3}, Landroid/app/ProgressDialog;->show()V

    .line 850
    :cond_4
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    iget v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    invoke-virtual {v3, v4}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 851
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-static {}, Lcom/mediatek/mtklogger/taglog/ZipManager;->getZippedFilesCount()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/app/ProgressDialog;->setProgress(I)V

    .line 852
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->monitorProgressDialogBar()V

    .line 857
    .end local v2           #win:Landroid/view/Window;
    :cond_5
    :goto_2
    iget-boolean v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    if-nez v3, :cond_0

    iget-boolean v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromMainActivity:Z

    if-nez v3, :cond_0

    .line 858
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V

    goto/16 :goto_1

    .line 854
    :cond_6
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "new mProgressDialog failed"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2
.end method

.method private deInit(Z)V
    .locals 3
    .parameter "needNotifyLog2Server"

    .prologue
    const/4 v2, 0x0

    .line 191
    const-string v0, "MTKLogger/TagLogManager"

    const-string v1, "-->deInit()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 192
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogStateReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 194
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 195
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V

    .line 196
    iput-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDialog:Landroid/app/AlertDialog;

    .line 199
    :cond_0
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V

    .line 200
    sput-object v2, Lcom/mediatek/mtklogger/taglog/TagLogManager;->instance:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    .line 201
    iput-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    .line 203
    if-eqz p1, :cond_1

    iget-boolean v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    if-nez v0, :cond_1

    .line 204
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->notifyToLog2Server()V

    .line 208
    :goto_0
    return-void

    .line 206
    :cond_1
    const-string v0, "MTKLogger/TagLogManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "<-- Not need to notify Log2Server, mManualSaveLog="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method private dismissProgressDialog()V
    .locals 1

    .prologue
    .line 863
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_0

    .line 864
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 865
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    .line 867
    :cond_0
    return-void
.end method

.method private forgetCachedTaggingLogFolder()V
    .locals 5

    .prologue
    .line 798
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "-->forgetCachedTaggingLogFolder()"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 799
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 800
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v2

    .line 801
    .local v2, logType:I
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->KEY_TAGGING_LOG_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3, v2}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-interface {v0, v3}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 803
    .end local v2           #logType:I
    :cond_0
    const-string v3, "tagging_dest"

    invoke-interface {v0, v3}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "tagging_db"

    invoke-interface {v3, v4}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 806
    return-void
.end method

.method public static getInstance(Landroid/content/Context;)Lcom/mediatek/mtklogger/taglog/TagLogManager;
    .locals 1
    .parameter "context"

    .prologue
    .line 141
    sget-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->instance:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    if-nez v0, :cond_0

    .line 142
    new-instance v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;-><init>(Landroid/content/Context;)V

    sput-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->instance:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    .line 144
    :cond_0
    sget-object v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->instance:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    return-object v0
.end method

.method private getLogPath()Landroid/util/SparseArray;
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .prologue
    .line 730
    new-instance v2, Landroid/util/SparseArray;

    invoke-direct {v2}, Landroid/util/SparseArray;-><init>()V

    .line 731
    .local v2, logToolPath:Landroid/util/SparseArray;,"Landroid/util/SparseArray<Ljava/lang/String;>;"
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-static {v6}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "/mtklog/"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 732
    .local v4, mtkLogPath:Ljava/lang/String;
    new-instance v5, Ljava/io/File;

    invoke-direct {v5, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v5}, Ljava/io/File;->exists()Z

    move-result v5

    if-nez v5, :cond_1

    .line 740
    :cond_0
    return-object v2

    .line 735
    :cond_1
    const-string v5, "MTKLogger/TagLogManager"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "getMTKLogPath :"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 736
    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v5}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Integer;

    .line 737
    .local v3, logType:Ljava/lang/Integer;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    sget-object v5, Lcom/mediatek/mtklogger/utils/Utils;->LOG_PATH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v5, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 738
    .local v0, filePath:Ljava/lang/String;
    invoke-virtual {v3}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v2, v5, v0}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    goto :goto_0
.end method

.method private init()V
    .locals 7

    .prologue
    const/4 v4, 0x0

    .line 159
    const-string v2, "MTKLogger/TagLogManager"

    const-string v3, "-->init()"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 160
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    const v3, 0x103006e

    invoke-virtual {v2, v3}, Landroid/content/Context;->setTheme(I)V

    .line 161
    sput-boolean v4, Lcom/mediatek/mtklogger/taglog/TagLogManager;->isTagingLog:Z

    .line 162
    new-instance v2, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    const/4 v3, 0x0

    invoke-direct {v2, p0, v3}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;Lcom/mediatek/mtklogger/taglog/TagLogManager$1;)V

    iput-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    .line 163
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    const-string v3, "log_settings"

    invoke-virtual {v2, v3, v4}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v2

    iput-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 165
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-static {v2}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v2

    iput-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 167
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x7f060007

    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object v1

    .line 168
    .local v1, modes:[Ljava/lang/String;
    if-eqz v1, :cond_0

    array-length v2, v1

    if-lez v2, :cond_0

    .line 169
    aget-object v2, v1, v4

    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v4, "log_mode"

    iget-object v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    const v6, 0x7f070076

    invoke-virtual {v5, v6}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v5

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z

    .line 173
    :cond_0
    const-string v2, "MTKLogger/TagLogManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "mIgnoreMdLog?"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-boolean v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 175
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 176
    .local v0, logStateFilter:Landroid/content/IntentFilter;
    const-string v2, "com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 177
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogStateReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v2, v3, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 179
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    instance-of v2, v2, Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    if-eqz v2, :cond_1

    .line 180
    iget-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    check-cast v2, Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    iput-object v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    .line 184
    :goto_0
    return-void

    .line 182
    :cond_1
    const-string v2, "MTKLogger/TagLogManager"

    const-string v3, "Wrong parameter to contruct TagLogManager"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method private initToolStatus()V
    .locals 9

    .prologue
    const/4 v3, 0x1

    const/4 v4, 0x0

    .line 719
    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v2}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    .line 720
    .local v1, logType:Ljava/lang/Integer;
    iget-object v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogToolStatus:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v6

    iget-object v7, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v8

    invoke-virtual {v2, v8}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v7, v2, v4}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v2

    if-ne v3, v2, :cond_0

    move v2, v3

    :goto_1
    invoke-static {v2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v2

    invoke-virtual {v5, v6, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    goto :goto_0

    :cond_0
    move v2, v4

    goto :goto_1

    .line 723
    .end local v1           #logType:Ljava/lang/Integer;
    :cond_1
    return-void
.end method

.method private monitorProgressDialogBar()V
    .locals 6

    .prologue
    .line 870
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mMonitorTimer:Ljava/util/Timer;

    if-eqz v0, :cond_0

    .line 885
    :goto_0
    return-void

    .line 873
    :cond_0
    new-instance v0, Ljava/util/Timer;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Ljava/util/Timer;-><init>(Z)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mMonitorTimer:Ljava/util/Timer;

    .line 874
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mMonitorTimer:Ljava/util/Timer;

    new-instance v1, Lcom/mediatek/mtklogger/taglog/TagLogManager$3;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager$3;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    const-wide/16 v2, 0x0

    const-wide/16 v4, 0xc8

    invoke-virtual/range {v0 .. v5}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    goto :goto_0
.end method

.method private notifyToLog2Server()V
    .locals 7

    .prologue
    .line 1142
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "-->notifyToLog2Server()"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1143
    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    .line 1144
    .local v1, intent:Landroid/content/Intent;
    const-string v3, "com.mediatek.syslogger.taglog"

    invoke-virtual {v1, v3}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 1145
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;

    if-eqz v3, :cond_0

    .line 1146
    const-string v3, "TaglogResult"

    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;

    invoke-virtual {v1, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 1147
    const-string v3, "MTKLogger/TagLogManager"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "TaglogResult = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1153
    :goto_0
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    if-eqz v3, :cond_2

    .line 1155
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :goto_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_3

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    .line 1156
    .local v2, logType:Ljava/lang/Integer;
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    if-eqz v3, :cond_1

    .line 1157
    sget-object v3, Lcom/mediatek/mtklogger/taglog/TagLogManager;->LOGPATHKEY:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    invoke-virtual {v1, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 1158
    const-string v4, "MTKLogger/TagLogManager"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v3, Lcom/mediatek/mtklogger/taglog/TagLogManager;->LOGPATHKEY:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-virtual {v3, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v5, " = "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-virtual {v3, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v4, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    .line 1150
    .end local v0           #i$:Ljava/util/Iterator;
    .end local v2           #logType:Ljava/lang/Integer;
    :cond_0
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "mTagLogResult is null!"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 1160
    .restart local v0       #i$:Ljava/util/Iterator;
    .restart local v2       #logType:Ljava/lang/Integer;
    :cond_1
    const-string v3, "MTKLogger/TagLogManager"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "mLogPathInTagLog["

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "]"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "= null!"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 1164
    .end local v0           #i$:Ljava/util/Iterator;
    .end local v2           #logType:Ljava/lang/Integer;
    :cond_2
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "mLogPathInTagLog is null"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1166
    :cond_3
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;

    if-eqz v3, :cond_4

    .line 1167
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;

    invoke-virtual {v1, v3}, Landroid/content/Intent;->putExtras(Landroid/os/Bundle;)Landroid/content/Intent;

    .line 1172
    :goto_2
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-static {v3}, Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;->getInstance(Landroid/content/Context;)Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;

    move-result-object v3

    invoke-virtual {v3, v1}, Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;->beginException(Landroid/content/Intent;)Z

    .line 1173
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "send intent to Log2Server"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1174
    return-void

    .line 1169
    :cond_4
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "Data From Aee is null"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2
.end method

.method private rememberCurrentTaggingLogFolder(Ljava/lang/String;)V
    .locals 6
    .parameter "targetFolderName"

    .prologue
    .line 783
    const-string v3, "MTKLogger/TagLogManager"

    const-string v4, "-->rememberCurrentTaggingLogFolder()"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 784
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 785
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    iget-object v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 786
    .local v2, info:Lcom/mediatek/mtklogger/taglog/LogInformation;
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->KEY_TAGGING_LOG_MAP:Landroid/util/SparseArray;

    invoke-virtual {v2}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogType()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v2}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogFile()Ljava/io/File;

    move-result-object v4

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    invoke-interface {v0, v3, v4}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 789
    .end local v2           #info:Lcom/mediatek/mtklogger/taglog/LogInformation;
    :cond_0
    const-string v3, "tagging_dest"

    invoke-interface {v0, v3, p1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "tagging_db"

    iget-object v5, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDbPathFromAee:Ljava/lang/String;

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 792
    return-void
.end method

.method private startOrStopAllLogTool(Z)V
    .locals 9
    .parameter "isStart"

    .prologue
    const/4 v8, 0x0

    const/4 v5, 0x0

    .line 750
    const-string v4, "MTKLogger/TagLogManager"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "startOrStopAllLogTool() -> isStart?"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 751
    const/4 v0, 0x0

    .line 752
    .local v0, allLogType:I
    sget-object v4, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_0
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_3

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    .line 753
    .local v2, logType:Ljava/lang/Integer;
    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v4

    const/4 v6, 0x2

    if-ne v4, v6, :cond_1

    iget-boolean v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z

    if-nez v4, :cond_0

    iget-boolean v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z

    if-nez v4, :cond_0

    .line 756
    :cond_1
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogToolStatus:Landroid/util/SparseArray;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v6

    invoke-static {v5}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v7

    invoke-virtual {v4, v6, v7}, Landroid/util/SparseArray;->get(ILjava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Boolean;

    invoke-virtual {v4}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v4

    if-eqz v4, :cond_2

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v4

    :goto_1
    or-int/2addr v0, v4

    goto :goto_0

    :cond_2
    move v4, v5

    goto :goto_1

    .line 758
    .end local v2           #logType:Ljava/lang/Integer;
    :cond_3
    if-nez v0, :cond_4

    .line 775
    :goto_2
    return-void

    .line 762
    :cond_4
    const/4 v3, 0x0

    .line 763
    .local v3, result:Z
    if-eqz p1, :cond_5

    .line 764
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    invoke-virtual {v4, v0, v8}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->startRecording(ILjava/lang/String;)Z

    move-result v3

    .line 769
    :goto_3
    const-string v4, "MTKLogger/TagLogManager"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "startOrStopAllLogTool() -> result?"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 770
    if-eqz v3, :cond_6

    .line 771
    const/4 v4, 0x1

    iput-boolean v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsWaitingLogStateChange:Z

    goto :goto_2

    .line 766
    :cond_5
    iget-object v4, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    invoke-virtual {v4, v0, v8}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->stopRecording(ILjava/lang/String;)Z

    move-result v3

    goto :goto_3

    .line 773
    :cond_6
    const-string v4, "MTKLogger/TagLogManager"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "-->startOrStopAllLogTool(), isStart="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", operation fail!"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2
.end method

.method private tagSelectedLogFolder(Ljava/lang/String;)V
    .locals 10
    .parameter "targetFolderName"

    .prologue
    .line 672
    const-string v6, "MTKLogger/TagLogManager"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "-->tagSelectedLogFolder(), targetFolderName="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 674
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v6}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    const-string v7, "tag_log_compressing"

    const/4 v8, 0x1

    invoke-interface {v6, v7, v8}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    invoke-interface {v6}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 675
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    invoke-direct {p0, v6, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->zipAllLogAndDelete(Ljava/util/List;Ljava/lang/String;)Landroid/util/SparseArray;

    move-result-object v6

    iput-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    .line 677
    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v6}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    .line 678
    .local v5, logType:Ljava/lang/Integer;
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v6, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_0

    .line 679
    const-string v6, "MTKLogger/TagLogManager"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mLogPathInTagLog["

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "]"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "= null"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 681
    :cond_0
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mLogPathInTagLog["

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v8, "]"

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v8, "="

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v9

    invoke-virtual {v6, v9}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v7, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 685
    .end local v5           #logType:Ljava/lang/Integer;
    :cond_1
    iget-boolean v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    if-nez v6, :cond_2

    .line 686
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDbPathFromAee:Ljava/lang/String;

    invoke-static {v6, p1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->writeFolderToTagFolder(Ljava/lang/String;Ljava/lang/String;)V

    .line 689
    :cond_2
    new-instance v0, Ljava/io/File;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    sget-object v7, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "checksop.txt"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v0, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 690
    .local v0, checkSopFile:Ljava/io/File;
    if-eqz v0, :cond_4

    .line 691
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v6

    if-eqz v6, :cond_3

    .line 692
    invoke-virtual {v0}, Ljava/io/File;->delete()Z

    .line 695
    :cond_3
    :try_start_0
    invoke-virtual {v0}, Ljava/io/File;->createNewFile()Z
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 699
    :goto_1
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    invoke-static {v6, v0}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->writeCheckSOPToFile(Landroid/content/Context;Ljava/io/File;)Z

    .line 702
    :cond_4
    const/4 v3, 0x1

    .line 703
    .local v3, isZipSuccess:Z
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    invoke-interface {v6}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_5
    :goto_2
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_6

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 704
    .local v4, logInformation:Lcom/mediatek/mtklogger/taglog/LogInformation;
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogPathInTagLog:Landroid/util/SparseArray;

    invoke-virtual {v4}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogType()I

    move-result v7

    invoke-virtual {v6, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    if-nez v6, :cond_5

    .line 705
    const-string v6, "MTKLogger/TagLogManager"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "mLogPathInTagLog["

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v4}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogType()I

    move-result v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "]"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "= null"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 706
    const/4 v3, 0x0

    goto :goto_2

    .line 696
    .end local v3           #isZipSuccess:Z
    .end local v4           #logInformation:Lcom/mediatek/mtklogger/taglog/LogInformation;
    :catch_0
    move-exception v1

    .line 697
    .local v1, e:Ljava/io/IOException;
    invoke-virtual {v1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1

    .line 709
    .end local v1           #e:Ljava/io/IOException;
    .restart local v3       #isZipSuccess:Z
    :cond_6
    if-eqz v3, :cond_7

    .line 710
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    const/16 v7, 0xce

    invoke-virtual {v6, v7}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    .line 714
    :goto_3
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v6}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    const-string v7, "tag_log_compressing"

    const/4 v8, 0x0

    invoke-interface {v6, v7, v8}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    invoke-interface {v6}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 715
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->forgetCachedTaggingLogFolder()V

    .line 716
    return-void

    .line 712
    :cond_7
    iget-object v6, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    const/16 v7, 0xcf

    invoke-virtual {v6, v7}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    goto :goto_3
.end method

.method private zipAllLogAndDelete(Ljava/util/List;Ljava/lang/String;)Landroid/util/SparseArray;
    .locals 7
    .parameter
    .parameter "tagLogFolderName"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List",
            "<",
            "Lcom/mediatek/mtklogger/taglog/LogInformation;",
            ">;",
            "Ljava/lang/String;",
            ")",
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .prologue
    .line 1077
    .local p1, currentLogFolderList:Ljava/util/List;,"Ljava/util/List<Lcom/mediatek/mtklogger/taglog/LogInformation;>;"
    new-instance v2, Landroid/util/SparseArray;

    invoke-direct {v2}, Landroid/util/SparseArray;-><init>()V

    .line 1079
    .local v2, currentTaglogPaths:Landroid/util/SparseArray;,"Landroid/util/SparseArray<Ljava/lang/String;>;"
    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v3

    .local v3, i$:Ljava/util/Iterator;
    :cond_0
    :goto_0
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 1080
    .local v0, currentLogFolder:Lcom/mediatek/mtklogger/taglog/LogInformation;
    invoke-direct {p0, v0, p2}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->zipLogAndDelete(Lcom/mediatek/mtklogger/taglog/LogInformation;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 1081
    .local v1, currentTaglogPath:Ljava/lang/String;
    if-eqz v1, :cond_0

    .line 1082
    invoke-virtual {v0}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogType()I

    move-result v4

    invoke-virtual {v2, v4, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 1083
    const-string v4, "MTKLogger/TagLogManager"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "currentTaglogPaths["

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogType()I

    move-result v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "]"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 1087
    .end local v0           #currentLogFolder:Lcom/mediatek/mtklogger/taglog/LogInformation;
    .end local v1           #currentTaglogPath:Ljava/lang/String;
    :cond_1
    return-object v2
.end method

.method private zipLogAndDelete(Lcom/mediatek/mtklogger/taglog/LogInformation;Ljava/lang/String;)Ljava/lang/String;
    .locals 11
    .parameter "currentLogFolder"
    .parameter "tagLogFolderName"

    .prologue
    const/4 v8, 0x0

    .line 1096
    invoke-virtual {p1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogFile()Ljava/io/File;

    move-result-object v1

    .line 1097
    .local v1, neededLogFolder:Ljava/io/File;
    if-nez v1, :cond_0

    .line 1098
    const-string v7, "MTKLogger/TagLogManager"

    const-string v9, "Needed log folder path is null!!"

    invoke-static {v7, v9}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    move-object v6, v8

    .line 1138
    :goto_0
    return-object v6

    .line 1101
    :cond_0
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "-->zipLog() Folder : "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v7, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1102
    const/4 v6, 0x0

    .line 1104
    .local v6, zipResultPath:Ljava/lang/String;
    if-eqz v1, :cond_6

    .line 1106
    const-string v7, "dualmdlog"

    invoke-virtual {v1}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v9

    invoke-virtual {v9}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    .line 1108
    .local v0, isDualModemLog:Z
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    sget-object v9, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    if-eqz v0, :cond_3

    const-string v7, "Dual"

    :goto_1
    invoke-virtual {v9, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v9, ".zip"

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 1111
    .local v3, targetLogFileName:Ljava/lang/String;
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "targetLogFileName :"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v7, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 1112
    new-instance v4, Ljava/io/File;

    invoke-direct {v4, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1113
    .local v4, targetTagFile:Ljava/io/File;
    if-eqz v4, :cond_1

    invoke-virtual {v4}, Ljava/io/File;->exists()Z

    move-result v7

    if-eqz v7, :cond_1

    invoke-virtual {v4}, Ljava/io/File;->isFile()Z

    move-result v7

    if-eqz v7, :cond_1

    .line 1114
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "target taglog file ["

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "] already exist, delete it first"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v7, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 1115
    invoke-virtual {v4}, Ljava/io/File;->delete()Z

    .line 1118
    :cond_1
    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7, v3}, Lcom/mediatek/mtklogger/taglog/ZipManager;->zipFileOrFolder(Ljava/lang/String;Ljava/lang/String;)Z

    move-result v5

    .line 1119
    .local v5, zipResult:Z
    if-nez v5, :cond_4

    .line 1120
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Fail to zip log folder: "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v7, v9}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1121
    new-instance v2, Ljava/io/File;

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1122
    .local v2, targetLogFile:Ljava/io/File;
    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v7

    if-eqz v7, :cond_2

    .line 1123
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    :cond_2
    move-object v6, v8

    .line 1125
    goto/16 :goto_0

    .line 1108
    .end local v2           #targetLogFile:Ljava/io/File;
    .end local v3           #targetLogFileName:Ljava/lang/String;
    .end local v4           #targetTagFile:Ljava/io/File;
    .end local v5           #zipResult:Z
    :cond_3
    const-string v7, ""

    goto/16 :goto_1

    .line 1127
    .restart local v3       #targetLogFileName:Ljava/lang/String;
    .restart local v4       #targetTagFile:Ljava/io/File;
    .restart local v5       #zipResult:Z
    :cond_4
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Zip log success, target log file="

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 1128
    move-object v6, v3

    .line 1130
    iget-boolean v7, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z

    if-eqz v7, :cond_5

    invoke-virtual {p1}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogType()I

    move-result v7

    const/4 v8, 0x2

    if-ne v7, v8, :cond_5

    .line 1131
    const-string v7, "MTKLogger/TagLogManager"

    const-string v8, "It is a ModemExp and not delete primary log folder"

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 1134
    :cond_5
    invoke-static {v1}, Lcom/mediatek/mtklogger/utils/Utils;->deleteFolder(Ljava/io/File;)V

    .line 1137
    .end local v0           #isDualModemLog:Z
    .end local v3           #targetLogFileName:Ljava/lang/String;
    .end local v4           #targetTagFile:Ljava/io/File;
    .end local v5           #zipResult:Z
    :cond_6
    const-string v7, "MTKLogger/TagLogManager"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "<--zipLog(), zipResultPath="

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0
.end method


# virtual methods
.method public beginTag(Landroid/content/Intent;)V
    .locals 4
    .parameter "intent"

    .prologue
    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 215
    const-string v0, "MTKLogger/TagLogManager"

    const-string v1, "-->beginTag()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 217
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromMainActivity:Z

    .line 218
    const-string v0, "extra_key_from_main_activity"

    invoke-virtual {p1, v0, v2}, Landroid/content/Intent;->getBooleanExtra(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 219
    sget-boolean v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->isTagingLog:Z

    if-eqz v0, :cond_1

    .line 220
    const-string v0, "MTKLogger/TagLogManager"

    const-string v1, "EXTRA_KEY_FROM_MAIN_ACTIVITY"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 221
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    if-eqz v0, :cond_0

    .line 222
    iput-boolean v3, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromMainActivity:Z

    .line 223
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    const/16 v1, 0x132

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    .line 240
    :cond_0
    :goto_0
    return-void

    .line 226
    :cond_1
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "tag_log_compressing"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto :goto_0

    .line 231
    :cond_2
    sget-boolean v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->isTagingLog:Z

    if-eqz v0, :cond_3

    .line 232
    const-string v0, "MTKLogger/TagLogManager"

    const-string v1, "TagLog is already on process, reject new request!"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 233
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;

    const v1, 0x7f070073

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 236
    :cond_3
    sput-boolean v3, Lcom/mediatek/mtklogger/taglog/TagLogManager;->isTagingLog:Z

    .line 238
    invoke-virtual {p1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;

    .line 239
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    const/16 v1, 0xcb

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    goto :goto_0
.end method

.method public resumeTag()V
    .locals 14

    .prologue
    const/4 v13, 0x1

    const/4 v12, 0x0

    .line 247
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v10, "tag_log_compressing"

    invoke-interface {v9, v10, v12}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v2

    .line 248
    .local v2, isLastTaggingNotFinished:Z
    const-string v9, "MTKLogger/TagLogManager"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "-->resumeTag(), isLastTaggingNotFinished="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 249
    if-eqz v2, :cond_a

    .line 250
    sput-boolean v13, Lcom/mediatek/mtklogger/taglog/TagLogManager;->isTagingLog:Z

    .line 251
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v10, "tagging_dest"

    const-string v11, ""

    invoke-interface {v9, v10, v11}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 252
    .local v8, targetFolderName:Ljava/lang/String;
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v10, "tagging_db"

    const-string v11, ""

    invoke-interface {v9, v10, v11}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 253
    .local v0, dbFolder:Ljava/lang/String;
    invoke-static {v8}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-eqz v9, :cond_0

    .line 254
    const-string v9, "MTKLogger/TagLogManager"

    const-string v10, "Last tagging flag is not null, but can not find target folder, ignore"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 304
    .end local v0           #dbFolder:Ljava/lang/String;
    .end local v8           #targetFolderName:Ljava/lang/String;
    :goto_0
    return-void

    .line 257
    .restart local v0       #dbFolder:Ljava/lang/String;
    .restart local v8       #targetFolderName:Ljava/lang/String;
    :cond_0
    new-instance v7, Ljava/io/File;

    invoke-direct {v7, v8}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 258
    .local v7, targetFolder:Ljava/io/File;
    if-eqz v7, :cond_1

    invoke-virtual {v7}, Ljava/io/File;->exists()Z

    move-result v9

    if-nez v9, :cond_2

    .line 259
    :cond_1
    const-string v9, "MTKLogger/TagLogManager"

    const-string v10, "Last tagging target folder does not exist, ignore"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 262
    :cond_2
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-eqz v9, :cond_3

    .line 263
    const-string v9, "MTKLogger/TagLogManager"

    const-string v10, "At resume time, find no db file, treat this as user trigger event"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 264
    iput-boolean v13, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    .line 269
    :goto_1
    new-instance v9, Ljava/util/ArrayList;

    invoke-direct {v9}, Ljava/util/ArrayList;-><init>()V

    iput-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    .line 270
    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v9}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :goto_2
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    if-eqz v9, :cond_6

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/Integer;

    invoke-virtual {v9}, Ljava/lang/Integer;->intValue()I

    move-result v6

    .line 271
    .local v6, logType:I
    iget-object v10, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v9, Lcom/mediatek/mtklogger/utils/Utils;->KEY_TAGGING_LOG_MAP:Landroid/util/SparseArray;

    invoke-virtual {v9, v6}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/String;

    const-string v11, ""

    invoke-interface {v10, v9, v11}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 272
    .local v4, logFolderStr:Ljava/lang/String;
    invoke-static {v4}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-nez v9, :cond_5

    .line 273
    new-instance v3, Ljava/io/File;

    invoke-direct {v3, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 274
    .local v3, logFolder:Ljava/io/File;
    if-eqz v3, :cond_4

    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v9

    if-eqz v9, :cond_4

    .line 275
    const-string v9, "MTKLogger/TagLogManager"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Add not finished log folder: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 276
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    new-instance v10, Lcom/mediatek/mtklogger/taglog/LogInformation;

    invoke-direct {v10, v6, v3}, Lcom/mediatek/mtklogger/taglog/LogInformation;-><init>(ILjava/io/File;)V

    invoke-interface {v9, v10}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_2

    .line 266
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v3           #logFolder:Ljava/io/File;
    .end local v4           #logFolderStr:Ljava/lang/String;
    .end local v6           #logType:I
    :cond_3
    iput-boolean v12, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z

    .line 267
    iput-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDbPathFromAee:Ljava/lang/String;

    goto :goto_1

    .line 278
    .restart local v1       #i$:Ljava/util/Iterator;
    .restart local v3       #logFolder:Ljava/io/File;
    .restart local v4       #logFolderStr:Ljava/lang/String;
    .restart local v6       #logType:I
    :cond_4
    const-string v9, "MTKLogger/TagLogManager"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Log folder ["

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "] not exist, maybe already tag finish."

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 281
    .end local v3           #logFolder:Ljava/io/File;
    :cond_5
    const-string v9, "MTKLogger/TagLogManager"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Log folder for type ["

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "] is empty or null."

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_2

    .line 285
    .end local v4           #logFolderStr:Ljava/lang/String;
    .end local v6           #logType:I
    :cond_6
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    invoke-interface {v9}, Ljava/util/List;->size()I

    move-result v9

    if-lez v9, :cond_9

    .line 286
    iput v12, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    .line 287
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;

    invoke-interface {v9}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_3
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    if-eqz v9, :cond_7

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 288
    .local v5, logInformation:Lcom/mediatek/mtklogger/taglog/LogInformation;
    iget v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    invoke-virtual {v5}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogFilesCount()I

    move-result v10

    add-int/2addr v9, v10

    iput v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    goto :goto_3

    .line 290
    .end local v5           #logInformation:Lcom/mediatek/mtklogger/taglog/LogInformation;
    :cond_7
    const-string v9, "MTKLogger/TagLogManager"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Total taglog files count after resumeTag is : "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    iget v11, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 291
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    if-eqz v9, :cond_8

    .line 292
    iget-object v9, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;

    iget v10, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I

    invoke-virtual {v9, v10}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 294
    :cond_8
    new-instance v9, Lcom/mediatek/mtklogger/taglog/TagLogManager$ResumeTagThread;

    invoke-direct {v9, p0, v8}, Lcom/mediatek/mtklogger/taglog/TagLogManager$ResumeTagThread;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)V

    invoke-virtual {v9}, Lcom/mediatek/mtklogger/taglog/TagLogManager$ResumeTagThread;->start()V

    goto/16 :goto_0

    .line 296
    :cond_9
    const-string v9, "MTKLogger/TagLogManager"

    const-string v10, "From taglog flag, need to resume tagging, but find no log folder that need tag, ignore"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 298
    invoke-direct {p0}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->forgetCachedTaggingLogFolder()V

    goto/16 :goto_0

    .line 302
    .end local v0           #dbFolder:Ljava/lang/String;
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v7           #targetFolder:Ljava/io/File;
    .end local v8           #targetFolderName:Ljava/lang/String;
    :cond_a
    invoke-direct {p0, v12}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V

    goto/16 :goto_0
.end method
