.class public Lcom/mediatek/mtklogger/framework/MultiModemLog;
.super Lcom/mediatek/mtklogger/framework/LogInstance;
.source "MultiModemLog.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;,
        Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;,
        Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;,
        Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogThread;
    }
.end annotation


# static fields
.field private static final ADB_COMMAND_AUTO_TEST_START_MDLOGGER:Ljava/lang/String; = "auto_test_start_mdlogger"

.field private static final ADB_COMMAND_EXIT:Ljava/lang/String; = "exit"

.field public static final ADB_COMMAND_FORCE_MODEM_ASSERT:Ljava/lang/String; = "force_modem_assert"

.field private static final ADB_COMMAND_PAUSE:Ljava/lang/String; = "pause"

.field private static final ADB_COMMAND_RESUME:Ljava/lang/String; = "resume"

.field private static final COMMAND_ISPAUSED:Ljava/lang/String; = "ispaused"

.field private static final COMMAND_PAUSE:Ljava/lang/String; = "pause"

.field private static final COMMAND_POLLING:Ljava/lang/String; = "polling"

.field private static final COMMAND_RESET:Ljava/lang/String; = "resetmd"

.field private static final COMMAND_RESUME:Ljava/lang/String; = "resume"

.field private static final COMMAND_SETAUTO:Ljava/lang/String; = "setauto,"

.field private static final COMMAND_START:Ljava/lang/String; = "start,"

.field private static final COMMAND_STOP:Ljava/lang/String; = "stop"

.field public static final LOG_FOLDER_NAME_MD1:Ljava/lang/String; = "mdlog"

.field public static final LOG_FOLDER_NAME_MD2:Ljava/lang/String; = "dualmdlog"

.field private static final MSG_BEGIN_DUMP:I = 0x33

.field private static final MSG_BEGIN_RESET:I = 0x34

.field private static final MSG_DISMISS_RESET_DIALOG:I = 0x36

.field private static final MSG_MEMORY_DUMP_FINISH:I = 0x47

.field private static final MSG_MEMORY_DUMP_START:I = 0x46

.field private static final MSG_NO_LOGGING_FILE:I = 0x48

.field private static final MSG_QUERY_PAUSE_STATUS:I = 0x4c

.field private static final MSG_SDCARD_FULL:I = 0x4b

.field private static final MSG_SEND_FILTER_FAIL:I = 0x49

.field private static final MSG_SHOW_RESET_DIALOG:I = 0x35

.field private static final MSG_WRITE_FILE_FAIL:I = 0x4a

.field private static final RESPONSE_FAIL_SEND_FILTER:Ljava/lang/String; = "FAIL_SENDFILTER"

.field private static final RESPONSE_FAIL_WRITE_FILE:Ljava/lang/String; = "FAIL_WRITEFILE"

.field private static final RESPONSE_FINISH_MEMORY_DUMP:Ljava/lang/String; = "MEMORYDUMP_DONE"

.field private static final RESPONSE_LOGGING_FILE_NOTEXIST:Ljava/lang/String; = "LOGFILE_NOTEXIST"

.field private static final RESPONSE_SDCARD_FULL:Ljava/lang/String; = "SDCARD_FULL"

.field private static final RESPONSE_START_MEMORY_DUMP:Ljava/lang/String; = "MEMORYDUMP_START"

.field public static final SOCKET_NAME_MD1:Ljava/lang/String; = "com.mediatek.mdlogger.socket"

.field public static final SOCKET_NAME_MD2:Ljava/lang/String; = "com.mediatek.dualmdlogger.socket"

.field private static final TAG:Ljava/lang/String; = "MTKLogger/MultiModemLog"


# instance fields
.field private alertRingUri:Landroid/net/Uri;

.field private assertRingtone:Landroid/media/Ringtone;

.field private bConnected:Z

.field private isModemResetDialogShowing:Z

.field private mCmdResHandler:Landroid/os/Handler;

.field private mCurrentStage:I

.field private mCurrentStatus:I

.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field private mMessageHandler:Landroid/os/Handler;

.field private mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

.field private volatile mMonitorThreadRunning:Z

.field private volatile mMonitorThreadStop:Z

.field private mResetModemDialog:Landroid/app/ProgressDialog;

.field private mResetTimeout:I

.field private mTimer:Ljava/util/Timer;

.field modemLogLock:Ljava/lang/Object;

.field waitNextClearLogCheck:Ljava/lang/Object;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/os/Handler;)V
    .locals 6
    .parameter "context"
    .parameter "handler"

    .prologue
    const/4 v2, 0x0

    const/4 v3, 0x0

    .line 195
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/LogInstance;-><init>(Landroid/content/Context;)V

    .line 151
    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->assertRingtone:Landroid/media/Ringtone;

    .line 153
    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->alertRingUri:Landroid/net/Uri;

    .line 157
    iput-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadRunning:Z

    .line 159
    iput-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z

    .line 161
    iput-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z

    .line 167
    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCmdResHandler:Landroid/os/Handler;

    .line 169
    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    .line 171
    iput v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I

    .line 177
    const/4 v2, -0x1

    iput v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    .line 182
    iput-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->isModemResetDialogShowing:Z

    .line 186
    iput v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetTimeout:I

    .line 193
    new-instance v2, Ljava/lang/Object;

    invoke-direct {v2}, Ljava/lang/Object;-><init>()V

    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->modemLogLock:Ljava/lang/Object;

    .line 616
    new-instance v2, Ljava/lang/Object;

    invoke-direct {v2}, Ljava/lang/Object;-><init>()V

    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->waitNextClearLogCheck:Ljava/lang/Object;

    .line 742
    new-instance v2, Lcom/mediatek/mtklogger/framework/MultiModemLog$4;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v3

    invoke-direct {v2, p0, v3}, Lcom/mediatek/mtklogger/framework/MultiModemLog$4;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;Landroid/os/Looper;)V

    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMessageHandler:Landroid/os/Handler;

    .line 196
    new-instance v1, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogThread;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogThread;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V

    .line 197
    .local v1, modemLogThread:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogThread;
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogThread;->start()V

    .line 198
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->modemLogLock:Ljava/lang/Object;

    monitor-enter v3

    .line 200
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->modemLogLock:Ljava/lang/Object;

    const-wide/16 v4, 0x1f4

    invoke-virtual {v2, v4, v5}, Ljava/lang/Object;->wait(J)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 204
    :goto_0
    :try_start_1
    monitor-exit v3
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 205
    iput-object p2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCmdResHandler:Landroid/os/Handler;

    .line 206
    invoke-static {p1}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v2

    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 207
    return-void

    .line 201
    :catch_0
    move-exception v0

    .line 202
    .local v0, e:Ljava/lang/InterruptedException;
    :try_start_2
    const-string v2, "MTKLogger/MultiModemLog"

    const-string v4, "Wait modem log sub thread initialization, but was interrupted"

    invoke-static {v2, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 204
    .end local v0           #e:Ljava/lang/InterruptedException;
    :catchall_0
    move-exception v2

    monitor-exit v3
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v2
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    return-object v0
.end method

.method static synthetic access$002(Lcom/mediatek/mtklogger/framework/MultiModemLog;Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    return-object p1
.end method

.method static synthetic access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z

    return v0
.end method

.method static synthetic access$1000(Lcom/mediatek/mtklogger/framework/MultiModemLog;Ljava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->dealWithADBCommand(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$102(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z

    return p1
.end method

.method static synthetic access$1400(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->showResetModemDialog()V

    return-void
.end method

.method static synthetic access$1500(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I

    return v0
.end method

.method static synthetic access$1502(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I

    return p1
.end method

.method static synthetic access$1600(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCmdResHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$1700(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$1902(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadRunning:Z

    return p1
.end method

.method static synthetic access$2000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/media/Ringtone;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->assertRingtone:Landroid/media/Ringtone;

    return-object v0
.end method

.method static synthetic access$2102(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->isModemResetDialogShowing:Z

    return p1
.end method

.method static synthetic access$2200(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->dismissResetModemDialog()V

    return-void
.end method

.method static synthetic access$2300(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMessageHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$300(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    return v0
.end method

.method static synthetic access$302(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    return p1
.end method

.method static synthetic access$376(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I
    .locals 1
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iget v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    or-int/2addr v0, p1

    iput v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    return v0
.end method

.method static synthetic access$380(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I
    .locals 1
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iget v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    xor-int/2addr v0, p1

    iput v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    return v0
.end method

.method static synthetic access$400(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 43
    invoke-direct {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->showMemoryDumpDoneDialog(ILjava/lang/String;)V

    return-void
.end method

.method static synthetic access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 43
    invoke-direct {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V

    return-void
.end method

.method static synthetic access$600(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Ljava/lang/String;
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->getCurrentMode()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$800(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 43
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->runMonitoringLogSizeThread()V

    return-void
.end method

.method static synthetic access$900(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 43
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z

    return v0
.end method

.method static synthetic access$902(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 43
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z

    return p1
.end method

.method private dealWithADBCommand(Ljava/lang/String;)V
    .locals 4
    .parameter "cmd"

    .prologue
    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 563
    const-string v0, "exit"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 564
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z

    if-eqz v0, :cond_0

    .line 565
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    const-string v1, "stop"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 566
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "modemlog_enable"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 568
    const-string v0, ""

    invoke-direct {p0, v2, v0}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V

    .line 582
    :cond_0
    :goto_0
    return-void

    .line 571
    :cond_1
    const-string v0, "pause"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 572
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 573
    :cond_2
    const-string v0, "resume"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_3

    .line 574
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    invoke-virtual {v0, v3}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 575
    :cond_3
    const-string v0, "force_modem_assert"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 576
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/16 v1, 0x33

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 577
    :cond_4
    const-string v0, "auto_test_start_mdlogger"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 578
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->getCurrentMode()Ljava/lang/String;

    move-result-object v0

    const-string v1, "2"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 579
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    invoke-virtual {v0, v3}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->sendEmptyMessage(I)Z

    goto :goto_0
.end method

.method private dismissResetModemDialog()V
    .locals 6

    .prologue
    const/4 v0, 0x1

    const/4 v1, 0x0

    const/4 v5, 0x0

    .line 779
    iget v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetTimeout:I

    add-int/lit16 v2, v2, 0x3e8

    iput v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetTimeout:I

    .line 780
    iget v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetTimeout:I

    const/16 v3, 0x2710

    if-lt v2, v3, :cond_2

    .line 781
    const-string v2, "MTKLogger/MultiModemLog"

    const-string v3, "Reset modem timeout!"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 782
    iput v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetTimeout:I

    .line 783
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    if-eqz v1, :cond_0

    .line 784
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    invoke-virtual {v1}, Ljava/util/Timer;->cancel()V

    .line 785
    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    .line 787
    :cond_0
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    if-eqz v1, :cond_1

    .line 788
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->cancel()V

    .line 789
    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    .line 807
    :cond_1
    :goto_0
    return-void

    .line 793
    :cond_2
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v3, "modemlog_enable"

    invoke-interface {v2, v3, v0}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v2

    if-ne v0, v2, :cond_4

    .line 795
    .local v0, isResetDone:Z
    :goto_1
    const-string v2, "MTKLogger/MultiModemLog"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "dismissResetModemDialog()-> isResetDone ? "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 796
    if-eqz v0, :cond_1

    .line 797
    iput v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetTimeout:I

    .line 798
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    if-eqz v1, :cond_3

    .line 799
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    invoke-virtual {v1}, Ljava/util/Timer;->cancel()V

    .line 800
    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    .line 802
    :cond_3
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    if-eqz v1, :cond_1

    .line 803
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->cancel()V

    .line 804
    iput-object v5, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    goto :goto_0

    .end local v0           #isResetDone:Z
    :cond_4
    move v0, v1

    .line 793
    goto :goto_1
.end method

.method private getCurrentMode()Ljava/lang/String;
    .locals 4

    .prologue
    .line 237
    const-string v0, "2"

    .line 238
    .local v0, mode:Ljava/lang/String;
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v1, :cond_0

    .line 239
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v2, "log_mode"

    const-string v3, "2"

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 241
    :cond_0
    return-object v0
.end method

.method private notifyServiceStatus(ILjava/lang/String;)V
    .locals 7
    .parameter "status"
    .parameter "reason"

    .prologue
    const v6, 0x7f070003

    const/4 v5, 0x2

    const/4 v4, 0x0

    const/4 v3, 0x1

    .line 591
    const-string v0, "MTKLogger/MultiModemLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "-->notifyServiceStatus(), status="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ",  reason=["

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "]"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 595
    if-ne v3, p1, :cond_1

    .line 596
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v0, :cond_0

    .line 597
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "modemlog_enable"

    invoke-interface {v0, v1, v3}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 600
    :cond_0
    invoke-virtual {p0, v5, v6, v3}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->updateStatusBar(IIZ)V

    .line 611
    :goto_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCmdResHandler:Landroid/os/Handler;

    invoke-virtual {v0, v3, v5, p1, p2}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 613
    return-void

    .line 602
    :cond_1
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v0, :cond_2

    .line 603
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "modemlog_enable"

    invoke-interface {v0, v1, v4}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 605
    iput v4, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    .line 607
    :cond_2
    invoke-virtual {p0, v5, v6, v4}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->updateStatusBar(IIZ)V

    goto :goto_0
.end method

.method private runMonitoringLogSizeThread()V
    .locals 3

    .prologue
    const/4 v2, 0x0

    .line 619
    const-string v0, "MTKLogger/MultiModemLog"

    const-string v1, "-->runMonitoringLogSizeThread()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 620
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadRunning:Z

    if-eqz v0, :cond_0

    .line 621
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z

    .line 622
    const-string v0, "MTKLogger/MultiModemLog"

    const-string v1, "Already running, just return."

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 682
    :goto_0
    return-void

    .line 626
    :cond_0
    const-string v0, "MTKLogger/MultiModemLog"

    const-string v1, "Initialize running flag for clear log checking thread."

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 627
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z

    .line 628
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadRunning:Z

    .line 630
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lcom/mediatek/mtklogger/framework/MultiModemLog$1;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog$1;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    goto :goto_0
.end method

.method private showMemoryDumpDoneDialog(ILjava/lang/String;)V
    .locals 6
    .parameter "instanceIndex"
    .parameter "logFolderPath"

    .prologue
    .line 685
    const-string v3, "MTKLogger/MultiModemLog"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "-->showMemoryDumpDone(), logFolderPath="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, ", isModemResetDialogShowing="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-boolean v5, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->isModemResetDialogShowing:Z

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 687
    iget-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->isModemResetDialogShowing:Z

    if-eqz v3, :cond_0

    .line 688
    const-string v3, "MTKLogger/MultiModemLog"

    const-string v4, "Modem reset dialog is already showing, just return"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 740
    :goto_0
    return-void

    .line 692
    :cond_0
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->alertRingUri:Landroid/net/Uri;

    if-nez v3, :cond_1

    .line 693
    const/4 v3, 0x4

    invoke-static {v3}, Landroid/media/RingtoneManager;->getDefaultUri(I)Landroid/net/Uri;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->alertRingUri:Landroid/net/Uri;

    .line 695
    :cond_1
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->alertRingUri:Landroid/net/Uri;

    if-eqz v3, :cond_3

    .line 696
    const-string v3, "MTKLogger/MultiModemLog"

    const-string v4, "Play the ringtone"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 697
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->assertRingtone:Landroid/media/Ringtone;

    if-nez v3, :cond_2

    .line 698
    sget-object v3, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->alertRingUri:Landroid/net/Uri;

    invoke-static {v3, v4}, Landroid/media/RingtoneManager;->getRingtone(Landroid/content/Context;Landroid/net/Uri;)Landroid/media/Ringtone;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->assertRingtone:Landroid/media/Ringtone;

    .line 700
    :cond_2
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->assertRingtone:Landroid/media/Ringtone;

    if-eqz v3, :cond_3

    .line 701
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->assertRingtone:Landroid/media/Ringtone;

    invoke-virtual {v3}, Landroid/media/Ringtone;->play()V

    .line 704
    :cond_3
    const-string v3, "MTKLogger/MultiModemLog"

    const-string v4, "Show memory dump done dialog."

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 705
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v4, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    const v5, 0x7f070077

    invoke-virtual {v4, v5}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 706
    .local v2, message:Ljava/lang/String;
    new-instance v3, Landroid/app/AlertDialog$Builder;

    sget-object v4, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    invoke-direct {v3, v4}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    sget-object v4, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    const v5, 0x7f070078

    invoke-virtual {v4, v5}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    const v4, 0x1040013

    new-instance v5, Lcom/mediatek/mtklogger/framework/MultiModemLog$2;

    invoke-direct {v5, p0, p1}, Lcom/mediatek/mtklogger/framework/MultiModemLog$2;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)V

    invoke-virtual {v3, v4, v5}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 722
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    .line 723
    .local v1, dialog:Landroid/app/AlertDialog;
    invoke-virtual {v1}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v3

    const/16 v4, 0x7d3

    invoke-virtual {v3, v4}, Landroid/view/Window;->setType(I)V

    .line 724
    new-instance v3, Lcom/mediatek/mtklogger/framework/MultiModemLog$3;

    invoke-direct {v3, p0, p1}, Lcom/mediatek/mtklogger/framework/MultiModemLog$3;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)V

    invoke-virtual {v1, v3}, Landroid/app/AlertDialog;->setOnCancelListener(Landroid/content/DialogInterface$OnCancelListener;)V

    .line 737
    const/4 v3, 0x1

    iput-boolean v3, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->isModemResetDialogShowing:Z

    .line 738
    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_0
.end method

.method private showResetModemDialog()V
    .locals 7

    .prologue
    const-wide/16 v2, 0x3e8

    .line 757
    new-instance v0, Landroid/app/ProgressDialog;

    sget-object v1, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    .line 758
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    sget-object v1, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    const v4, 0x7f070079

    invoke-virtual {v1, v4}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setTitle(Ljava/lang/CharSequence;)V

    .line 759
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    sget-object v1, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mContext:Landroid/content/Context;

    const v4, 0x7f07007a

    invoke-virtual {v1, v4}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 760
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 761
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->getWindow()Landroid/view/Window;

    move-result-object v6

    .line 762
    .local v6, win:Landroid/view/Window;
    const/16 v0, 0x7d3

    invoke-virtual {v6, v0}, Landroid/view/Window;->setType(I)V

    .line 763
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mResetModemDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V

    .line 765
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    if-eqz v0, :cond_0

    .line 766
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    .line 767
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    .line 769
    :cond_0
    new-instance v0, Ljava/util/Timer;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Ljava/util/Timer;-><init>(Z)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    .line 770
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mTimer:Ljava/util/Timer;

    new-instance v1, Lcom/mediatek/mtklogger/framework/MultiModemLog$5;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/framework/MultiModemLog$5;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V

    move-wide v4, v2

    invoke-virtual/range {v0 .. v5}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    .line 776
    return-void
.end method


# virtual methods
.method public getGlobalRunningStage()I
    .locals 1

    .prologue
    .line 228
    iget v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I

    return v0
.end method

.method public getLogRunningStatus()I
    .locals 1

    .prologue
    .line 233
    iget v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I

    return v0
.end method
