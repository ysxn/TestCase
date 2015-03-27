.class public Lcom/mediatek/mtklogger/MainActivity;
.super Landroid/app/Activity;
.source "MainActivity.java"


# static fields
.field private static final ALPHA_FULL:I = 0xff

.field private static final ALPHA_GRAY:I = 0x4b

.field private static final IS_LOG_START:I = 0x1

.field private static final IS_LOG_STOP:I = 0x0

.field private static final MESSAGE_DELAY_TIME:I = 0x190

.field private static final MSG_MONITOR_SDCARD_BAR:I = 0x3

.field private static final MSG_SET_AUTO_START:I = 0x4

.field private static final MSG_TIMER:I = 0x1

.field private static final MSG_WAITING_DIALOG_TIMER:I = 0x2

.field private static final SDCARD_RATIO_BAR_TIMER:I = 0x7530

.field private static final START_STOP_TIMER:I = 0xea60

.field private static final TAG:Ljava/lang/String; = "MTKLogger/MainActivity"

.field private static final TIMER_PERIOD:I = 0x3e8


# instance fields
.field private mClearLogImageButton:Landroid/widget/ImageButton;

.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field private mFreeStorageSize:F

.field private mFreeStorageText:Landroid/widget/TextView;

.field private mLogImageViews:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Landroid/widget/ImageView;",
            ">;"
        }
    .end annotation
.end field

.field private mLogTextViews:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Landroid/widget/TextView;",
            ">;"
        }
    .end annotation
.end field

.field private mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

.field private mMessageHandler:Landroid/os/Handler;

.field private mMobileLogImage:Landroid/widget/ImageView;

.field private mMobileLogText:Landroid/widget/TextView;

.field private mModemLogImage:Landroid/widget/ImageView;

.field private mModemLogText:Landroid/widget/TextView;

.field private mMonitorTimer:Ljava/util/Timer;

.field private mNetworkLogImage:Landroid/widget/ImageView;

.field private mNetworkLogText:Landroid/widget/TextView;

.field private mSDCardPathStr:Ljava/lang/String;

.field private mSavePathStr:Ljava/lang/String;

.field private mSavePathText:Landroid/widget/TextView;

.field private mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

.field private mServiceReceiver:Landroid/content/BroadcastReceiver;

.field private mSettingsImageButton:Landroid/widget/ImageButton;

.field private mSettingsMenuItem:Landroid/view/MenuItem;

.field private mSharedPreferences:Landroid/content/SharedPreferences;

.field private mStartStopToggleButton:Landroid/widget/ToggleButton;

.field private mStorageChartLabelText:Landroid/widget/TextView;

.field private mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

.field private mTagImageButton:Landroid/widget/ImageButton;

.field private mTimeMillisecond:J

.field private mTimeText:Landroid/widget/TextView;

.field private mTimer:Ljava/util/Timer;

.field private mUsedStorageSize:F

.field private mUsedStorageText:Landroid/widget/TextView;

.field private mWaitingDialog:Landroid/app/ProgressDialog;

.field private mWaitingDialogTimer:Ljava/util/Timer;


# direct methods
.method public constructor <init>()V
    .locals 2

    .prologue
    .line 43
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 61
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 75
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    .line 76
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    .line 93
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    .line 98
    new-instance v0, Lcom/mediatek/mtklogger/MainActivity$1;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/MainActivity$1;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mServiceReceiver:Landroid/content/BroadcastReceiver;

    .line 124
    new-instance v0, Lcom/mediatek/mtklogger/MainActivity$2;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/MainActivity$2;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    .line 212
    new-instance v0, Lcom/mediatek/mtklogger/MainActivity$4;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/MainActivity$4;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMessageHandler:Landroid/os/Handler;

    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->stopWaitingDialog()V

    return-void
.end method

.method static synthetic access$100(Lcom/mediatek/mtklogger/MainActivity;Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/MainActivity;->analyzeReason(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1000(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$1100(Lcom/mediatek/mtklogger/MainActivity;)Lcom/mediatek/mtklogger/framework/MTKLoggerManager;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    return-object v0
.end method

.method static synthetic access$1200(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$1300(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->clearLogs()V

    return-void
.end method

.method static synthetic access$1400(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->tagLogs()V

    return-void
.end method

.method static synthetic access$200(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->updateUI()V

    return-void
.end method

.method static synthetic access$300(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->changeWaitingDialog()V

    return-void
.end method

.method static synthetic access$400(Lcom/mediatek/mtklogger/MainActivity;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMessageHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$514(Lcom/mediatek/mtklogger/MainActivity;J)J
    .locals 2
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iget-wide v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    add-long/2addr v0, p1

    iput-wide v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    return-wide v0
.end method

.method static synthetic access$600(Lcom/mediatek/mtklogger/MainActivity;)Ljava/lang/String;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->calculateTimer()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$700(Lcom/mediatek/mtklogger/MainActivity;)Landroid/widget/TextView;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    return-object v0
.end method

.method static synthetic access$800(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->refreshSdcardBar()V

    return-void
.end method

.method static synthetic access$900(Lcom/mediatek/mtklogger/MainActivity;Z)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/MainActivity;->updateLogAutoStart(Z)V

    return-void
.end method

.method private alertLowStorageDialog()V
    .locals 5

    .prologue
    .line 385
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const v1, 0x7f07001d

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const v1, 0x7f07001e

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    const/16 v4, 0x1e

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    aput-object v4, v2, v3

    invoke-virtual {p0, v1, v2}, Lcom/mediatek/mtklogger/MainActivity;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const v1, 0x104000a

    new-instance v2, Lcom/mediatek/mtklogger/MainActivity$5;

    invoke-direct {v2, p0}, Lcom/mediatek/mtklogger/MainActivity$5;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 392
    return-void
.end method

.method private analyzeReason(Ljava/lang/String;)Ljava/lang/String;
    .locals 10
    .parameter "reason"

    .prologue
    const/4 v9, -0x1

    .line 134
    const-string v6, ""

    .line 135
    .local v6, rsReason:Ljava/lang/String;
    const/4 v1, 0x0

    .local v1, i:I
    :goto_0
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v7

    if-ge v1, v7, :cond_0

    .line 136
    const-string v7, ":"

    invoke-virtual {p1, v7, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;I)I

    move-result v2

    .line 137
    .local v2, index:I
    if-ne v2, v9, :cond_1

    .line 172
    .end local v2           #index:I
    :cond_0
    :goto_1
    return-object v6

    .line 140
    .restart local v2       #index:I
    :cond_1
    const/4 v3, -0x1

    .line 142
    .local v3, logType:I
    :try_start_0
    invoke-virtual {p1, v1, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v3

    .line 146
    :goto_2
    sget-object v7, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    invoke-interface {v7, v8}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_0

    .line 149
    add-int/lit8 v1, v2, 0x1

    .line 150
    const-string v7, ";"

    invoke-virtual {p1, v7, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;I)I

    move-result v2

    .line 151
    if-eq v2, v9, :cond_0

    .line 154
    invoke-virtual {p1, v1, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v5

    .line 157
    .local v5, reasonType:Ljava/lang/String;
    const/4 v4, 0x0

    .line 159
    .local v4, reasonKey:I
    :try_start_1
    sget-object v7, Lcom/mediatek/mtklogger/utils/Utils;->FAIL_REASON_DETAIL_MAP:Ljava/util/Map;

    invoke-interface {v7, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/Integer;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I
    :try_end_1
    .catch Ljava/lang/NullPointerException; {:try_start_1 .. :try_end_1} :catch_1

    move-result v4

    .line 163
    :goto_3
    if-ne v4, v9, :cond_2

    .line 164
    add-int/lit8 v1, v2, 0x1

    .line 165
    goto :goto_1

    .line 143
    .end local v4           #reasonKey:I
    .end local v5           #reasonType:Ljava/lang/String;
    :catch_0
    move-exception v0

    .line 144
    .local v0, e:Ljava/lang/NumberFormatException;
    const/4 v3, -0x1

    goto :goto_2

    .line 160
    .end local v0           #e:Ljava/lang/NumberFormatException;
    .restart local v4       #reasonKey:I
    .restart local v5       #reasonType:Ljava/lang/String;
    :catch_1
    move-exception v0

    .line 161
    .local v0, e:Ljava/lang/NullPointerException;
    const/4 v4, -0x1

    goto :goto_3

    .line 168
    .end local v0           #e:Ljava/lang/NullPointerException;
    :cond_2
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    sget-object v7, Lcom/mediatek/mtklogger/utils/Utils;->LOG_NAME_MAP:Landroid/util/SparseArray;

    invoke-virtual {v7, v3}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/Integer;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {p0, v7}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v8, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, " : "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {p0, v4}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "\n"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 170
    add-int/lit8 v1, v2, 0x1

    .line 171
    goto/16 :goto_0
.end method

.method private calculateTimer()Ljava/lang/String;
    .locals 10

    .prologue
    const/16 v9, 0xa

    .line 648
    iget-wide v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    long-to-int v5, v5

    div-int/lit16 v0, v5, 0xe10

    .line 649
    .local v0, hour:I
    const/16 v5, 0x270f

    if-le v0, v5, :cond_0

    .line 650
    const-string v5, "MTKLogger/MainActivity"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "There is something wrong with time record! The hour is "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 651
    const-wide/16 v5, 0x0

    iput-wide v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    .line 652
    const-string v5, "MTKLogger/MainActivity"

    const-string v6, "There is something wrong with time record!"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 655
    :cond_0
    if-lt v0, v9, :cond_1

    const/16 v5, 0x63

    if-gt v0, v5, :cond_1

    .line 656
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    const/high16 v6, 0x428c

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextSize(F)V

    .line 664
    :goto_0
    iget-wide v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    long-to-int v5, v5

    div-int/lit8 v5, v5, 0x3c

    rem-int/lit8 v1, v5, 0x3c

    .line 665
    .local v1, minute:I
    iget-wide v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    const-wide/16 v7, 0x3c

    rem-long v2, v5, v7

    .line 666
    .local v2, second:J
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, ""

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ":"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    if-ge v1, v9, :cond_4

    const-string v5, "0"

    :goto_1
    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ":"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-wide/16 v7, 0xa

    cmp-long v5, v2, v7

    if-gez v5, :cond_5

    const-string v5, "0"

    :goto_2
    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 670
    .local v4, timerStr:Ljava/lang/String;
    return-object v4

    .line 657
    .end local v1           #minute:I
    .end local v2           #second:J
    .end local v4           #timerStr:Ljava/lang/String;
    :cond_1
    const/16 v5, 0x64

    if-lt v0, v5, :cond_2

    const/16 v5, 0x3e7

    if-gt v0, v5, :cond_2

    .line 658
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    const/high16 v6, 0x4270

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextSize(F)V

    goto :goto_0

    .line 659
    :cond_2
    const/16 v5, 0x3e8

    if-lt v0, v5, :cond_3

    .line 660
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    const/high16 v6, 0x4248

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextSize(F)V

    goto :goto_0

    .line 662
    :cond_3
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    const/high16 v6, 0x42a0

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextSize(F)V

    goto :goto_0

    .line 666
    .restart local v1       #minute:I
    .restart local v2       #second:J
    :cond_4
    const-string v5, ""

    goto :goto_1

    :cond_5
    const-string v5, ""

    goto :goto_2
.end method

.method private changeWaitingDialog()V
    .locals 9

    .prologue
    const-wide/32 v2, 0xea60

    const/4 v5, 0x1

    .line 176
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->getCurrentRunningStage()I

    move-result v6

    .line 177
    .local v6, currentRunningStage:I
    const-string v0, "MTKLogger/MainActivity"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "changeWaitingDialog() -> currentRunningStage is "

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 178
    if-nez v6, :cond_1

    .line 179
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->stopWaitingDialog()V

    .line 210
    :cond_0
    :goto_0
    return-void

    .line 181
    :cond_1
    const-string v8, ""

    .line 182
    .local v8, title:Ljava/lang/String;
    const-string v7, ""

    .line 183
    .local v7, message:Ljava/lang/String;
    if-ne v6, v5, :cond_4

    .line 184
    const v0, 0x7f07001f

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v8

    .line 185
    const v0, 0x7f070020

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 194
    :cond_2
    :goto_1
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialog:Landroid/app/ProgressDialog;

    if-nez v0, :cond_0

    .line 195
    const/4 v0, 0x0

    invoke-static {p0, v8, v7, v5, v0}, Landroid/app/ProgressDialog;->show(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZZ)Landroid/app/ProgressDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialog:Landroid/app/ProgressDialog;

    .line 197
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    if-eqz v0, :cond_3

    .line 198
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    .line 199
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    .line 201
    :cond_3
    new-instance v0, Ljava/util/Timer;

    invoke-direct {v0, v5}, Ljava/util/Timer;-><init>(Z)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    .line 202
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$3;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$3;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    move-wide v4, v2

    invoke-virtual/range {v0 .. v5}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    goto :goto_0

    .line 186
    :cond_4
    const/4 v0, 0x2

    if-ne v6, v0, :cond_5

    .line 187
    const v0, 0x7f070021

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v8

    .line 188
    const v0, 0x7f070022

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    goto :goto_1

    .line 189
    :cond_5
    const/4 v0, 0x3

    if-ne v6, v0, :cond_2

    .line 190
    const v0, 0x7f070023

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v8

    .line 191
    const v0, 0x7f070024

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    goto :goto_1
.end method

.method private checkPath()Z
    .locals 7

    .prologue
    const/4 v2, 0x1

    const/4 v3, 0x0

    .line 789
    const/4 v0, 0x1

    .line 790
    .local v0, isExist:Z
    new-instance v4, Ljava/io/File;

    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    invoke-direct {v4, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4}, Ljava/io/File;->exists()Z

    move-result v4

    if-nez v4, :cond_0

    .line 791
    const v4, 0x7f07001c

    new-array v5, v2, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    aput-object v6, v5, v3

    invoke-virtual {p0, v4, v5}, Lcom/mediatek/mtklogger/MainActivity;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-static {p0, v4, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v4

    invoke-virtual {v4}, Landroid/widget/Toast;->show()V

    .line 793
    const/4 v0, 0x0

    .line 795
    :cond_0
    iget-object v4, p0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    invoke-static {p0, v4}, Lcom/mediatek/mtklogger/utils/Utils;->getVolumeState(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 796
    .local v1, mountStatus:Ljava/lang/String;
    const-string v4, "MTKLogger/MainActivity"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "-->checkPath(), path="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", exist?"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ", volumeState="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 798
    if-eqz v0, :cond_1

    const-string v4, "mounted"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1

    :goto_0
    return v2

    :cond_1
    move v2, v3

    goto :goto_0
.end method

.method private checkTaglogCompressing()V
    .locals 4

    .prologue
    .line 802
    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v2, "tag_log_compressing"

    const/4 v3, 0x0

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 804
    const-string v1, "MTKLogger/MainActivity"

    const-string v2, "checkTaglogCompressing() ? true"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 805
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 806
    .local v0, intent:Landroid/content/Intent;
    const-string v1, "com.mediatek.log2server.EXCEPTION_HAPPEND"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 807
    const-string v1, "extra_key_from_main_activity"

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    .line 808
    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->sendBroadcast(Landroid/content/Intent;)V

    .line 811
    .end local v0           #intent:Landroid/content/Intent;
    :cond_0
    return-void
.end method

.method private clearLogs()V
    .locals 15

    .prologue
    const/4 v14, 0x2

    const/4 v13, 0x3

    const/4 v9, 0x0

    const/4 v10, 0x1

    .line 728
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->checkPath()Z

    move-result v8

    if-nez v8, :cond_0

    .line 775
    :goto_0
    return-void

    .line 731
    :cond_0
    new-instance v2, Landroid/content/Intent;

    const-class v8, Lcom/mediatek/mtklogger/LogFolderListActivity;

    invoke-direct {v2, p0, v8}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 732
    .local v2, intent:Landroid/content/Intent;
    const-string v8, "rootpath"

    iget-object v11, p0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    invoke-virtual {v2, v8, v11}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 733
    iget-object v8, p0, Lcom/mediatek/mtklogger/MainActivity;->mStartStopToggleButton:Landroid/widget/ToggleButton;

    invoke-virtual {v8}, Landroid/widget/ToggleButton;->isChecked()Z

    move-result v8

    if-eqz v8, :cond_9

    .line 734
    sget-object v8, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v8}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v1

    .local v1, i$:Ljava/util/Iterator;
    :cond_1
    :goto_1
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v8

    if-eqz v8, :cond_9

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/Integer;

    .line 735
    .local v7, logType:Ljava/lang/Integer;
    iget-object v11, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v8, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v12

    invoke-virtual {v8, v12}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/String;

    invoke-interface {v11, v8, v9}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v8

    if-ne v10, v8, :cond_5

    move v3, v10

    .line 737
    .local v3, isLogStart:Z
    :goto_2
    if-eqz v3, :cond_1

    .line 738
    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v8

    if-ne v8, v14, :cond_8

    .line 740
    iget-object v8, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v8}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->getModemLogRunningDetailStatus()I

    move-result v0

    .line 741
    .local v0, currentRunningStage:I
    const-string v8, "MTKLogger/MainActivity"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "modemLogRunningDetailStatus : "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v8, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 742
    if-eq v0, v10, :cond_2

    if-ne v0, v13, :cond_6

    :cond_2
    move v4, v10

    .line 744
    .local v4, isModem1Running:Z
    :goto_3
    if-eqz v4, :cond_3

    .line 745
    const-string v8, "MTKLogger/MainActivity"

    const-string v11, "Modem one is running!"

    invoke-static {v8, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 746
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v11, p0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    invoke-virtual {v8, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    sget-object v8, Lcom/mediatek/mtklogger/utils/Utils;->LOG_PATH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v12

    invoke-virtual {v8, v12}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/String;

    invoke-virtual {v11, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;)Ljava/io/File;

    move-result-object v6

    .line 747
    .local v6, logFile:Ljava/io/File;
    if-eqz v6, :cond_3

    invoke-virtual {v6}, Ljava/io/File;->exists()Z

    move-result v8

    if-eqz v8, :cond_3

    .line 748
    sget-object v8, Lcom/mediatek/mtklogger/LogFolderListActivity;->EXTRA_FILTER_FILES_KEY:Landroid/util/SparseArray;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v11

    invoke-virtual {v8, v11}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/String;

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v2, v8, v11}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 752
    .end local v6           #logFile:Ljava/io/File;
    :cond_3
    if-eq v0, v14, :cond_4

    if-ne v0, v13, :cond_7

    :cond_4
    move v5, v10

    .line 754
    .local v5, isModem2Running:Z
    :goto_4
    if-eqz v5, :cond_1

    .line 755
    const-string v8, "MTKLogger/MainActivity"

    const-string v11, "DualModem is running!"

    invoke-static {v8, v11}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 756
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v11, p0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    invoke-virtual {v8, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v11, "dualmdlog"

    invoke-virtual {v8, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;)Ljava/io/File;

    move-result-object v6

    .line 757
    .restart local v6       #logFile:Ljava/io/File;
    if-eqz v6, :cond_1

    invoke-virtual {v6}, Ljava/io/File;->exists()Z

    move-result v8

    if-eqz v8, :cond_1

    .line 758
    const-string v8, "filterDualModemFile"

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v2, v8, v11}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    goto/16 :goto_1

    .end local v0           #currentRunningStage:I
    .end local v3           #isLogStart:Z
    .end local v4           #isModem1Running:Z
    .end local v5           #isModem2Running:Z
    .end local v6           #logFile:Ljava/io/File;
    :cond_5
    move v3, v9

    .line 735
    goto/16 :goto_2

    .restart local v0       #currentRunningStage:I
    .restart local v3       #isLogStart:Z
    :cond_6
    move v4, v9

    .line 742
    goto/16 :goto_3

    .restart local v4       #isModem1Running:Z
    :cond_7
    move v5, v9

    .line 752
    goto :goto_4

    .line 765
    .end local v0           #currentRunningStage:I
    .end local v4           #isModem1Running:Z
    :cond_8
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v11, p0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    invoke-virtual {v8, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    sget-object v8, Lcom/mediatek/mtklogger/utils/Utils;->LOG_PATH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v12

    invoke-virtual {v8, v12}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/String;

    invoke-virtual {v11, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;)Ljava/io/File;

    move-result-object v6

    .line 766
    .restart local v6       #logFile:Ljava/io/File;
    if-eqz v6, :cond_1

    invoke-virtual {v6}, Ljava/io/File;->exists()Z

    move-result v8

    if-eqz v8, :cond_1

    .line 767
    sget-object v8, Lcom/mediatek/mtklogger/LogFolderListActivity;->EXTRA_FILTER_FILES_KEY:Landroid/util/SparseArray;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v11

    invoke-virtual {v8, v11}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/String;

    invoke-virtual {v6}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v2, v8, v11}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    goto/16 :goto_1

    .line 773
    .end local v1           #i$:Ljava/util/Iterator;
    .end local v3           #isLogStart:Z
    .end local v6           #logFile:Ljava/io/File;
    .end local v7           #logType:Ljava/lang/Integer;
    :cond_9
    invoke-virtual {p0, v2}, Lcom/mediatek/mtklogger/MainActivity;->startActivity(Landroid/content/Intent;)V

    .line 774
    iget-object v8, p0, Lcom/mediatek/mtklogger/MainActivity;->mMessageHandler:Landroid/os/Handler;

    invoke-virtual {v8, v13}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    goto/16 :goto_0
.end method

.method private findViews()V
    .locals 5

    .prologue
    const/4 v4, 0x4

    const/4 v3, 0x2

    const/4 v2, 0x1

    .line 341
    const v0, 0x7f090021

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsImageButton:Landroid/widget/ImageButton;

    .line 343
    const v0, 0x7f090026

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mModemLogImage:Landroid/widget/ImageView;

    .line 344
    const v0, 0x7f090027

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mModemLogText:Landroid/widget/TextView;

    .line 345
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mModemLogImage:Landroid/widget/ImageView;

    invoke-virtual {v0, v3, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 346
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mModemLogText:Landroid/widget/TextView;

    invoke-virtual {v0, v3, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 347
    const v0, 0x7f090024

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMobileLogImage:Landroid/widget/ImageView;

    .line 348
    const v0, 0x7f090025

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMobileLogText:Landroid/widget/TextView;

    .line 349
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mMobileLogImage:Landroid/widget/ImageView;

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 350
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mMobileLogText:Landroid/widget/TextView;

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 351
    const v0, 0x7f090028

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mNetworkLogImage:Landroid/widget/ImageView;

    .line 352
    const v0, 0x7f090029

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mNetworkLogText:Landroid/widget/TextView;

    .line 353
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mNetworkLogImage:Landroid/widget/ImageView;

    invoke-virtual {v0, v4, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 354
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mNetworkLogText:Landroid/widget/TextView;

    invoke-virtual {v0, v4, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 356
    const v0, 0x7f090023

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    .line 357
    const v0, 0x7f09002a

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathText:Landroid/widget/TextView;

    .line 359
    const v0, 0x7f09002c

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/mediatek/mtklogger/LinearColorBar;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

    .line 360
    const v0, 0x7f09002e

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStorageChartLabelText:Landroid/widget/TextView;

    .line 361
    const v0, 0x7f09002d

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageText:Landroid/widget/TextView;

    .line 362
    const v0, 0x7f09002f

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageText:Landroid/widget/TextView;

    .line 363
    const v0, 0x7f090031

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    .line 364
    const v0, 0x7f090033

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ToggleButton;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStartStopToggleButton:Landroid/widget/ToggleButton;

    .line 365
    const v0, 0x7f090032

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    .line 366
    return-void
.end method

.method private initViews()V
    .locals 9

    .prologue
    const/4 v8, 0x0

    .line 369
    new-instance v3, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-direct {v3, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 370
    invoke-static {p0}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 371
    const-string v3, "log_settings"

    invoke-virtual {p0, v3, v8}, Lcom/mediatek/mtklogger/MainActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 373
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    .line 374
    .local v1, logType:Ljava/lang/Integer;
    iget-object v3, p0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/widget/TextView;

    const v5, 0x7f070017

    const/4 v4, 0x1

    new-array v6, v4, [Ljava/lang/Object;

    sget-object v4, Lcom/mediatek/mtklogger/utils/Utils;->LOG_NAME_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v4, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Integer;

    invoke-virtual {v4}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {p0, v4}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v6, v8

    invoke-virtual {p0, v5, v6}, Lcom/mediatek/mtklogger/MainActivity;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 377
    .end local v1           #logType:Ljava/lang/Integer;
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/MainActivity;->getIntent()Landroid/content/Intent;

    move-result-object v3

    const-string v4, "reason_start"

    invoke-virtual {v3, v4}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 378
    .local v2, startReason:Ljava/lang/String;
    if-eqz v2, :cond_1

    const-string v3, "low_storage"

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_1

    iget-object v3, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-static {v3}, Lcom/mediatek/mtklogger/utils/Utils;->checkLogStarted(Landroid/content/SharedPreferences;)Z

    move-result v3

    if-eqz v3, :cond_1

    .line 379
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->alertLowStorageDialog()V

    .line 382
    :cond_1
    return-void
.end method

.method private isAutoTest()Z
    .locals 4

    .prologue
    .line 594
    const-string v1, "ro.monkey"

    const-string v2, "false"

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 596
    .local v0, isMonkeyRunning:Ljava/lang/String;
    const-string v1, "MTKLogger/MainActivity"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "isAutoTest()-> Monkey running status is "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 598
    const-string v1, "true"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v1

    return v1
.end method

.method private monitorSdcardRatioBar()V
    .locals 6

    .prologue
    .line 674
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMonitorTimer:Ljava/util/Timer;

    if-eqz v0, :cond_0

    .line 684
    :goto_0
    return-void

    .line 677
    :cond_0
    new-instance v0, Ljava/util/Timer;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Ljava/util/Timer;-><init>(Z)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMonitorTimer:Ljava/util/Timer;

    .line 678
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMonitorTimer:Ljava/util/Timer;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$11;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$11;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    const-wide/16 v2, 0x0

    const-wide/16 v4, 0x7530

    invoke-virtual/range {v0 .. v5}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    goto :goto_0
.end method

.method private refreshSdcardBar()V
    .locals 15

    .prologue
    const-wide/16 v13, 0x400

    const v12, 0x7f070015

    const v11, 0x7f070014

    const/high16 v10, 0x4480

    const/4 v9, 0x0

    .line 687
    const-string v5, "MTKLogger/MainActivity"

    const-string v6, "-->refreshSdcardBar()"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 688
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->checkPath()Z

    move-result v5

    if-nez v5, :cond_0

    .line 689
    const-string v5, "MTKLogger/MainActivity"

    const-string v6, "Selected log path is unavailable, reset storage info"

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 690
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

    invoke-virtual {v5, v9, v9}, Lcom/mediatek/mtklogger/LinearColorBar;->setGradientPaint(FF)V

    .line 691
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

    invoke-virtual {v5, v9, v9, v9}, Lcom/mediatek/mtklogger/LinearColorBar;->setRatios(FFF)V

    .line 692
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageText:Landroid/widget/TextView;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "0M "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {p0, v11}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 693
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageText:Landroid/widget/TextView;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "0M "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {p0, v12}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 714
    :goto_0
    return-void

    .line 696
    :cond_0
    new-instance v4, Landroid/os/StatFs;

    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    invoke-direct {v4, v5}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    .line 697
    .local v4, statFs:Landroid/os/StatFs;
    invoke-virtual {v4}, Landroid/os/StatFs;->getBlockSize()I

    move-result v5

    div-int/lit16 v0, v5, 0x400

    .line 698
    .local v0, blockSize:I
    invoke-virtual {v4}, Landroid/os/StatFs;->getAvailableBlocks()I

    move-result v5

    mul-int/2addr v5, v0

    int-to-float v5, v5

    iput v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageSize:F

    .line 699
    invoke-virtual {v4}, Landroid/os/StatFs;->getBlockCount()I

    move-result v5

    mul-int/2addr v5, v0

    int-to-float v5, v5

    iget v6, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageSize:F

    sub-float/2addr v5, v6

    iput v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageSize:F

    .line 700
    const-string v5, "MTKLogger/MainActivity"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, " mSDCardPathStr="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget-object v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ", free size="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageSize:F

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ", used size="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    iget v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageSize:F

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 702
    iget v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageSize:F

    iget v6, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageSize:F

    iget v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageSize:F

    add-float/2addr v6, v7

    div-float v3, v5, v6

    .line 703
    .local v3, ratio:F
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

    const/4 v6, 0x0

    invoke-virtual {v5, v6}, Lcom/mediatek/mtklogger/LinearColorBar;->setShowingGreen(Z)V

    .line 704
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    invoke-static {v5}, Lcom/mediatek/mtklogger/utils/Utils;->getFileSize(Ljava/lang/String;)J

    move-result-wide v1

    .line 706
    .local v1, mtkLogSize:J
    cmp-long v5, v1, v13

    if-gtz v5, :cond_1

    .line 707
    const-wide/16 v1, 0x400

    .line 709
    :cond_1
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

    div-long v6, v1, v13

    long-to-float v6, v6

    iget v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageSize:F

    iget v8, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageSize:F

    add-float/2addr v7, v8

    div-float/2addr v6, v7

    sub-float v6, v3, v6

    invoke-virtual {v5, v6, v3}, Lcom/mediatek/mtklogger/LinearColorBar;->setGradientPaint(FF)V

    .line 711
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mSdcardRatioBar:Lcom/mediatek/mtklogger/LinearColorBar;

    const/high16 v6, 0x3f80

    sub-float/2addr v6, v3

    invoke-virtual {v5, v9, v3, v6}, Lcom/mediatek/mtklogger/LinearColorBar;->setRatios(FFF)V

    .line 712
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageText:Landroid/widget/TextView;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    iget v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mUsedStorageSize:F

    div-float/2addr v7, v10

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "M "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {p0, v11}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 713
    iget-object v5, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageText:Landroid/widget/TextView;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    iget v7, p0, Lcom/mediatek/mtklogger/MainActivity;->mFreeStorageSize:F

    div-float/2addr v7, v10

    float-to-int v7, v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "M "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {p0, v12}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0
.end method

.method private removeManualTitle()V
    .locals 2

    .prologue
    .line 336
    const v1, 0x7f09001d

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    .line 337
    .local v0, mainLinearLayout:Landroid/widget/LinearLayout;
    const v1, 0x7f09001e

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->removeView(Landroid/view/View;)V

    .line 338
    return-void
.end method

.method private setButtonStatus(Z)V
    .locals 1
    .parameter "enabled"

    .prologue
    .line 602
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    if-eqz v0, :cond_0

    .line 603
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    invoke-interface {v0, p1}, Landroid/view/MenuItem;->setEnabled(Z)Landroid/view/MenuItem;

    .line 605
    :cond_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    invoke-virtual {v0, p1}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 606
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStartStopToggleButton:Landroid/widget/ToggleButton;

    invoke-virtual {v0, p1}, Landroid/widget/ToggleButton;->setEnabled(Z)V

    .line 607
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    invoke-virtual {v0, p1}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 608
    return-void
.end method

.method private setListeners()V
    .locals 2

    .prologue
    .line 396
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsImageButton:Landroid/widget/ImageButton;

    if-eqz v0, :cond_0

    .line 398
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsImageButton:Landroid/widget/ImageButton;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$6;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$6;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 408
    :cond_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStartStopToggleButton:Landroid/widget/ToggleButton;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$7;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$7;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ToggleButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 457
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$8;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$8;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 465
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$9;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$9;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 472
    return-void
.end method

.method private startTimer()V
    .locals 11

    .prologue
    const/4 v10, 0x1

    const/4 v9, 0x0

    const-wide/16 v2, 0x3e8

    const-wide/16 v4, 0x0

    .line 611
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v1, "begin_recording_time"

    invoke-interface {v0, v1, v4, v5}, Landroid/content/SharedPreferences;->getLong(Ljava/lang/String;J)J

    move-result-wide v6

    .line 613
    .local v6, startTime:J
    cmp-long v0, v6, v4

    if-nez v0, :cond_1

    .line 614
    iput-wide v4, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    .line 619
    :goto_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v1, "system_time_reset"

    invoke-interface {v0, v1, v9}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v8

    .line 621
    .local v8, timeResetFlag:I
    if-eqz v8, :cond_0

    .line 622
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "system_time_reset"

    invoke-interface {v0, v1, v9}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 624
    const v0, 0x7f07008f

    invoke-static {p0, v0, v10}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 626
    :cond_0
    const-string v0, "MTKLogger/MainActivity"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "-->startTimer(), startTime="

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v6, v7}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v4, ", timeResetFlag="

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 628
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;

    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->calculateTimer()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 630
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->stopTimer()V

    .line 631
    new-instance v0, Ljava/util/Timer;

    invoke-direct {v0, v10}, Ljava/util/Timer;-><init>(Z)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimer:Ljava/util/Timer;

    .line 632
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimer:Ljava/util/Timer;

    new-instance v1, Lcom/mediatek/mtklogger/MainActivity$10;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/MainActivity$10;-><init>(Lcom/mediatek/mtklogger/MainActivity;)V

    move-wide v4, v2

    invoke-virtual/range {v0 .. v5}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    .line 638
    return-void

    .line 616
    .end local v8           #timeResetFlag:I
    :cond_1
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    sub-long/2addr v0, v6

    div-long/2addr v0, v2

    iput-wide v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimeMillisecond:J

    goto :goto_0
.end method

.method private stopTimer()V
    .locals 1

    .prologue
    .line 641
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimer:Ljava/util/Timer;

    if-eqz v0, :cond_0

    .line 642
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimer:Ljava/util/Timer;

    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    .line 643
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mTimer:Ljava/util/Timer;

    .line 645
    :cond_0
    return-void
.end method

.method private stopWaitingDialog()V
    .locals 2

    .prologue
    const/4 v1, 0x0

    .line 717
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_0

    .line 718
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->cancel()V

    .line 719
    iput-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialog:Landroid/app/ProgressDialog;

    .line 721
    :cond_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    if-eqz v0, :cond_1

    .line 722
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    .line 723
    iput-object v1, p0, Lcom/mediatek/mtklogger/MainActivity;->mWaitingDialogTimer:Ljava/util/Timer;

    .line 725
    :cond_1
    return-void
.end method

.method private tagLogs()V
    .locals 1

    .prologue
    .line 778
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStartStopToggleButton:Landroid/widget/ToggleButton;

    invoke-virtual {v0}, Landroid/widget/ToggleButton;->isChecked()Z

    move-result v0

    if-nez v0, :cond_0

    .line 786
    :goto_0
    return-void

    .line 785
    :cond_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->tagLog()Z

    goto :goto_0
.end method

.method private updateLogAutoStart(Z)V
    .locals 5
    .parameter "isStart"

    .prologue
    .line 475
    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v2}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    .line 476
    .local v1, logType:Ljava/lang/Integer;
    iget-object v3, p0, Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v2, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    const/4 v4, 0x1

    invoke-interface {v3, v2, v4}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 478
    const-string v2, "MTKLogger/MainActivity"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Log("

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ") is setAutoStart ? "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 479
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    if-nez v2, :cond_2

    .line 487
    .end local v1           #logType:Ljava/lang/Integer;
    :cond_1
    return-void

    .line 482
    .restart local v1       #logType:Ljava/lang/Integer;
    :cond_2
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v3

    invoke-virtual {v2, v3, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->setAutoStart(IZ)Z

    .line 483
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    sget-object v2, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_AUTOSTART_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v3, v2, p1}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto :goto_0
.end method

.method private updateUI()V
    .locals 21

    .prologue
    .line 490
    const-string v15, "MTKLogger/MainActivity"

    const-string v16, "-->updateUI(), Update UI Start"

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 493
    invoke-static/range {p0 .. p0}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v15

    move-object/from16 v0, p0

    iput-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    .line 494
    const-string v15, "MTKLogger/MainActivity"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, " mSDCardPathStr="

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    move-object/from16 v17, v0

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 495
    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSDCardPathStr:Ljava/lang/String;

    move-object/from16 v16, v0

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, "/mtklog/"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    move-object/from16 v0, p0

    iput-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    .line 496
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathText:Landroid/widget/TextView;

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const v17, 0x7f070013

    move-object/from16 v0, p0

    move/from16 v1, v17

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v17

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    const-string v17, ": "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    move-object/from16 v17, v0

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-virtual/range {v15 .. v16}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 497
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mStorageChartLabelText:Landroid/widget/TextView;

    move-object/from16 v16, v0

    const-string v15, "/mnt/sdcard2"

    invoke-static {}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPathType()Ljava/lang/String;

    move-result-object v17

    move-object/from16 v0, v17

    invoke-virtual {v15, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v15

    if-eqz v15, :cond_1

    const v15, 0x7f07001b

    :goto_0
    move-object/from16 v0, v16

    invoke-virtual {v0, v15}, Landroid/widget/TextView;->setText(I)V

    .line 501
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/MainActivity;->isAutoTest()Z

    move-result v15

    if-eqz v15, :cond_2

    .line 502
    const/4 v15, 0x0

    move-object/from16 v0, p0

    invoke-direct {v0, v15}, Lcom/mediatek/mtklogger/MainActivity;->setButtonStatus(Z)V

    .line 591
    :cond_0
    :goto_1
    return-void

    .line 497
    :cond_1
    const v15, 0x7f07001a

    goto :goto_0

    .line 505
    :cond_2
    const/4 v15, 0x1

    move-object/from16 v0, p0

    invoke-direct {v0, v15}, Lcom/mediatek/mtklogger/MainActivity;->setButtonStatus(Z)V

    .line 508
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/MainActivity;->changeWaitingDialog()V

    .line 510
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-static {v15}, Lcom/mediatek/mtklogger/utils/Utils;->checkLogStarted(Landroid/content/SharedPreferences;)Z

    move-result v9

    .line 511
    .local v9, isStart:Z
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mStartStopToggleButton:Landroid/widget/ToggleButton;

    invoke-virtual {v15, v9}, Landroid/widget/ToggleButton;->setChecked(Z)V

    .line 514
    if-eqz v9, :cond_3

    .line 515
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/MainActivity;->startTimer()V

    .line 521
    :goto_2
    sget-object v15, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v15}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v5

    .local v5, i$:Ljava/util/Iterator;
    :goto_3
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v15

    if-eqz v15, :cond_9

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/Integer;

    .line 522
    .local v12, logType:Ljava/lang/Integer;
    const/16 v16, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v17, v0

    sget-object v15, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v18

    move/from16 v0, v18

    invoke-virtual {v15, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/lang/String;

    const/16 v18, 0x0

    move-object/from16 v0, v17

    move/from16 v1, v18

    invoke-interface {v0, v15, v1}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v15

    move/from16 v0, v16

    if-ne v0, v15, :cond_4

    const/4 v8, 0x1

    .line 524
    .local v8, isLogStart:Z
    :goto_4
    if-eqz v9, :cond_5

    if-eqz v8, :cond_5

    .line 525
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/ImageView;

    const v16, 0x7f02000c

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 526
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/TextView;

    const v17, 0x7f070016

    const/16 v16, 0x1

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v18, v0

    const/16 v19, 0x0

    sget-object v16, Lcom/mediatek/mtklogger/utils/Utils;->LOG_NAME_MAP:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v20

    move-object/from16 v0, v16

    move/from16 v1, v20

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v16

    check-cast v16, Ljava/lang/Integer;

    invoke-virtual/range {v16 .. v16}, Ljava/lang/Integer;->intValue()I

    move-result v16

    move-object/from16 v0, p0

    move/from16 v1, v16

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v16

    aput-object v16, v18, v19

    move-object/from16 v0, p0

    move/from16 v1, v17

    move-object/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Lcom/mediatek/mtklogger/MainActivity;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    invoke-virtual/range {v15 .. v16}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 527
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/ImageView;

    const/16 v16, 0xff

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageView;->setAlpha(I)V

    .line 528
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/TextView;

    const/high16 v16, 0x437f

    invoke-virtual/range {v15 .. v16}, Landroid/widget/TextView;->setAlpha(F)V

    goto/16 :goto_3

    .line 517
    .end local v5           #i$:Ljava/util/Iterator;
    .end local v8           #isLogStart:Z
    .end local v12           #logType:Ljava/lang/Integer;
    :cond_3
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/MainActivity;->stopTimer()V

    goto/16 :goto_2

    .line 522
    .restart local v5       #i$:Ljava/util/Iterator;
    .restart local v12       #logType:Ljava/lang/Integer;
    :cond_4
    const/4 v8, 0x0

    goto/16 :goto_4

    .line 530
    .restart local v8       #isLogStart:Z
    :cond_5
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v16, v0

    sget-object v15, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v17

    move/from16 v0, v17

    invoke-virtual {v15, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/lang/String;

    const/16 v17, 0x1

    move-object/from16 v0, v16

    move/from16 v1, v17

    invoke-interface {v0, v15, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v7

    .line 532
    .local v7, isLogOn:Z
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/ImageView;

    const v16, 0x7f02000b

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 533
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/TextView;

    if-eqz v7, :cond_6

    const v16, 0x7f070017

    move/from16 v17, v16

    :goto_5
    const/16 v16, 0x1

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v18, v0

    const/16 v19, 0x0

    sget-object v16, Lcom/mediatek/mtklogger/utils/Utils;->LOG_NAME_MAP:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v20

    move-object/from16 v0, v16

    move/from16 v1, v20

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v16

    check-cast v16, Ljava/lang/Integer;

    invoke-virtual/range {v16 .. v16}, Ljava/lang/Integer;->intValue()I

    move-result v16

    move-object/from16 v0, p0

    move/from16 v1, v16

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v16

    aput-object v16, v18, v19

    move-object/from16 v0, p0

    move/from16 v1, v17

    move-object/from16 v2, v18

    invoke-virtual {v0, v1, v2}, Lcom/mediatek/mtklogger/MainActivity;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    invoke-virtual/range {v15 .. v16}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 535
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogImageViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/ImageView;

    if-eqz v7, :cond_7

    const/16 v16, 0xff

    :goto_6
    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageView;->setAlpha(I)V

    .line 536
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mLogTextViews:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    invoke-virtual/range {v15 .. v16}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Landroid/widget/TextView;

    if-eqz v7, :cond_8

    const/high16 v16, 0x437f

    :goto_7
    invoke-virtual/range {v15 .. v16}, Landroid/widget/TextView;->setAlpha(F)V

    goto/16 :goto_3

    .line 533
    :cond_6
    const v16, 0x7f070018

    move/from16 v17, v16

    goto :goto_5

    .line 535
    :cond_7
    const/16 v16, 0x4b

    goto :goto_6

    .line 536
    :cond_8
    const/high16 v16, 0x4296

    goto :goto_7

    .line 541
    .end local v7           #isLogOn:Z
    .end local v8           #isLogStart:Z
    .end local v12           #logType:Ljava/lang/Integer;
    :cond_9
    const/4 v10, 0x0

    .line 542
    .local v10, isTagLogEnabled:Z
    sget-object v3, Landroid/os/Build;->TYPE:Ljava/lang/String;

    .line 543
    .local v3, buildType:Ljava/lang/String;
    if-eqz v3, :cond_a

    .line 544
    const-string v15, "MTKLogger/MainActivity"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "Build type :"

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 545
    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v15

    const-string v16, "user"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v15

    if-eqz v15, :cond_f

    .line 546
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v16, "tagLogEnable"

    const/16 v17, 0x0

    invoke-interface/range {v15 .. v17}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v10

    .line 551
    :cond_a
    :goto_8
    if-eqz v9, :cond_10

    if-eqz v10, :cond_10

    .line 552
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0x1

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 553
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0xff

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setAlpha(I)V

    .line 560
    :goto_9
    const/4 v6, 0x0

    .line 561
    .local v6, isLogExists:Z
    sget-object v15, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v15}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v5

    :cond_b
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v15

    if-eqz v15, :cond_c

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/Integer;

    .line 562
    .restart local v12       #logType:Ljava/lang/Integer;
    new-instance v11, Ljava/io/File;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    move-object/from16 v16, v0

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    sget-object v15, Lcom/mediatek/mtklogger/utils/Utils;->LOG_PATH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v17

    move/from16 v0, v17

    invoke-virtual {v15, v0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/lang/String;

    move-object/from16 v0, v16

    invoke-virtual {v0, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-direct {v11, v15}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 563
    .local v11, logRootPath:Ljava/io/File;
    invoke-virtual {v11}, Ljava/io/File;->exists()Z

    move-result v15

    if-eqz v15, :cond_b

    invoke-virtual {v11}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v15

    array-length v15, v15

    if-lez v15, :cond_b

    .line 564
    const/4 v6, 0x1

    .line 569
    .end local v11           #logRootPath:Ljava/io/File;
    .end local v12           #logType:Ljava/lang/Integer;
    :cond_c
    new-instance v4, Ljava/io/File;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    move-object/from16 v16, v0

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, "dualmdlog"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-direct {v4, v15}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 570
    .local v4, dualMdLogRootPath:Ljava/io/File;
    invoke-virtual {v4}, Ljava/io/File;->exists()Z

    move-result v15

    if-eqz v15, :cond_d

    invoke-virtual {v4}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v15

    array-length v15, v15

    if-lez v15, :cond_d

    .line 571
    const/4 v6, 0x1

    .line 573
    :cond_d
    new-instance v13, Ljava/io/File;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/MainActivity;->mSavePathStr:Ljava/lang/String;

    move-object/from16 v16, v0

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, "taglog"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-direct {v13, v15}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 574
    .local v13, taglogTootPath:Ljava/io/File;
    invoke-virtual {v13}, Ljava/io/File;->exists()Z

    move-result v15

    if-eqz v15, :cond_e

    invoke-virtual {v13}, Ljava/io/File;->listFiles()[Ljava/io/File;

    move-result-object v15

    array-length v15, v15

    if-lez v15, :cond_e

    .line 575
    const/4 v6, 0x1

    .line 577
    :cond_e
    if-eqz v6, :cond_11

    .line 578
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0x1

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 579
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0xff

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setAlpha(I)V

    .line 586
    :goto_a
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v16, "waiting_sd_ready_reason"

    const-string v17, ""

    invoke-interface/range {v15 .. v17}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    .line 587
    .local v14, waitSDStatusStr:Ljava/lang/String;
    invoke-static {v14}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v15

    if-nez v15, :cond_0

    .line 588
    const-string v15, "MTKLogger/MainActivity"

    const-string v16, "Still waiting SD ready"

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 589
    const v15, 0x7f070066

    const/16 v16, 0x1

    move-object/from16 v0, p0

    move/from16 v1, v16

    invoke-static {v0, v15, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v15

    invoke-virtual {v15}, Landroid/widget/Toast;->show()V

    goto/16 :goto_1

    .line 548
    .end local v4           #dualMdLogRootPath:Ljava/io/File;
    .end local v6           #isLogExists:Z
    .end local v13           #taglogTootPath:Ljava/io/File;
    .end local v14           #waitSDStatusStr:Ljava/lang/String;
    :cond_f
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v16, "tagLogEnable"

    const/16 v17, 0x1

    invoke-interface/range {v15 .. v17}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v10

    goto/16 :goto_8

    .line 555
    :cond_10
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0x0

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 556
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mTagImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0x4b

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setAlpha(I)V

    goto/16 :goto_9

    .line 581
    .restart local v4       #dualMdLogRootPath:Ljava/io/File;
    .restart local v6       #isLogExists:Z
    .restart local v13       #taglogTootPath:Ljava/io/File;
    :cond_11
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0x0

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setEnabled(Z)V

    .line 582
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/MainActivity;->mClearLogImageButton:Landroid/widget/ImageButton;

    const/16 v16, 0x4b

    invoke-virtual/range {v15 .. v16}, Landroid/widget/ImageButton;->setAlpha(I)V

    goto :goto_a
.end method


# virtual methods
.method public onCreate(Landroid/os/Bundle;)V
    .locals 4
    .parameter "savedInstanceState"

    .prologue
    .line 232
    const-string v0, "MTKLogger/MainActivity"

    const-string v1, "onCreate"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 233
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 234
    const v0, 0x7f030008

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->setContentView(I)V

    .line 236
    sget v0, Lcom/mediatek/mtklogger/utils/Utils;->ANDROID_VERSION_NUMBER:F

    float-to-double v0, v0

    const-wide v2, 0x400ffdf3b645a1cbL

    cmpl-double v0, v0, v2

    if-lez v0, :cond_0

    .line 237
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->removeManualTitle()V

    .line 240
    :cond_0
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->findViews()V

    .line 241
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->initViews()V

    .line 242
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->setListeners()V

    .line 243
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->updateUI()V

    .line 244
    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 5
    .parameter "menu"

    .prologue
    const/4 v4, 0x1

    .line 302
    sget v0, Lcom/mediatek/mtklogger/utils/Utils;->ANDROID_VERSION_NUMBER:F

    float-to-double v0, v0

    const-wide v2, 0x400ffdf3b645a1cbL

    cmpg-double v0, v0, v2

    if-gez v0, :cond_0

    .line 313
    :goto_0
    return v4

    .line 305
    :cond_0
    const v0, 0x7f070010

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-interface {p1, v0}, Landroid/view/Menu;->add(Ljava/lang/CharSequence;)Landroid/view/MenuItem;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    .line 306
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    const/4 v1, 0x2

    invoke-interface {v0, v1}, Landroid/view/MenuItem;->setShowAsAction(I)V

    .line 307
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    const v1, 0x7f020008

    invoke-interface {v0, v1}, Landroid/view/MenuItem;->setIcon(I)Landroid/view/MenuItem;

    .line 308
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->isAutoTest()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 309
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Landroid/view/MenuItem;->setEnabled(Z)Landroid/view/MenuItem;

    goto :goto_0

    .line 311
    :cond_1
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mSettingsMenuItem:Landroid/view/MenuItem;

    invoke-interface {v0, v4}, Landroid/view/MenuItem;->setEnabled(Z)Landroid/view/MenuItem;

    goto :goto_0
.end method

.method protected onDestroy()V
    .locals 2

    .prologue
    .line 291
    const-string v0, "MTKLogger/MainActivity"

    const-string v1, "onDestroy"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 292
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    if-eqz v0, :cond_0

    .line 293
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->free()V

    .line 294
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 296
    :cond_0
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->stopWaitingDialog()V

    .line 297
    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    .line 298
    return-void
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 6
    .parameter "item"

    .prologue
    const/4 v5, 0x1

    .line 318
    sget v1, Lcom/mediatek/mtklogger/utils/Utils;->ANDROID_VERSION_NUMBER:F

    float-to-double v1, v1

    const-wide v3, 0x400ffdf3b645a1cbL

    cmpg-double v1, v1, v3

    if-gez v1, :cond_0

    .line 330
    :goto_0
    return v5

    .line 322
    :cond_0
    sget v1, Lcom/mediatek/mtklogger/utils/Utils;->USER_ID:I

    const/4 v2, -0x1

    if-eq v1, v2, :cond_1

    sget v1, Lcom/mediatek/mtklogger/utils/Utils;->USER_ID:I

    if-eqz v1, :cond_1

    .line 323
    const-string v1, "MTKLogger/MainActivity"

    const-string v2, "In multi user case, only device owner can change log configuration"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 324
    const v1, 0x7f070032

    const/4 v2, 0x0

    invoke-static {p0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 327
    :cond_1
    const-string v1, "MTKLogger/MainActivity"

    const-string v2, "SettingsActivity open!"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 328
    new-instance v0, Landroid/content/Intent;

    const-class v1, Lcom/mediatek/mtklogger/settings/SettingsActivity;

    invoke-direct {v0, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 329
    .local v0, intent:Landroid/content/Intent;
    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->startActivity(Landroid/content/Intent;)V

    goto :goto_0
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 272
    const-string v0, "MTKLogger/MainActivity"

    const-string v1, "onPause"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 273
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 274
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMonitorTimer:Ljava/util/Timer;

    if-eqz v0, :cond_0

    .line 275
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMonitorTimer:Ljava/util/Timer;

    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    .line 276
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mMonitorTimer:Ljava/util/Timer;

    .line 278
    :cond_0
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->stopTimer()V

    .line 279
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mServiceReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 280
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/MainActivity;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 281
    return-void
.end method

.method protected onResume()V
    .locals 4

    .prologue
    .line 248
    const-string v2, "MTKLogger/MainActivity"

    const-string v3, "onResume"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 249
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    .line 250
    .local v1, serviceIntent:Landroid/content/IntentFilter;
    const-string v2, "com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 251
    const-string v2, "stage_event"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 252
    const-string v2, "extra_key_from_taglog"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 253
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity;->mServiceReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v2, v1}, Lcom/mediatek/mtklogger/MainActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 255
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 256
    .local v0, SDStatusIntentFilter:Landroid/content/IntentFilter;
    const-string v2, "android.intent.action.MEDIA_BAD_REMOVAL"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 257
    const-string v2, "android.intent.action.MEDIA_EJECT"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 258
    const-string v2, "android.intent.action.MEDIA_REMOVED"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 259
    const-string v2, "android.intent.action.MEDIA_UNMOUNTED"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 260
    const-string v2, "android.intent.action.MEDIA_MOUNTED"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 261
    const-string v2, "file"

    invoke-virtual {v0, v2}, Landroid/content/IntentFilter;->addDataScheme(Ljava/lang/String;)V

    .line 262
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity;->mStorageStatusReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v2, v0}, Lcom/mediatek/mtklogger/MainActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 264
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->checkTaglogCompressing()V

    .line 265
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->updateUI()V

    .line 266
    invoke-direct {p0}, Lcom/mediatek/mtklogger/MainActivity;->monitorSdcardRatioBar()V

    .line 267
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 268
    return-void
.end method

.method protected onStop()V
    .locals 2

    .prologue
    .line 285
    const-string v0, "MTKLogger/MainActivity"

    const-string v1, "onStop"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 286
    invoke-super {p0}, Landroid/app/Activity;->onStop()V

    .line 287
    return-void
.end method
