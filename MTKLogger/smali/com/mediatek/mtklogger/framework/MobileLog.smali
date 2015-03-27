.class public Lcom/mediatek/mtklogger/framework/MobileLog;
.super Lcom/mediatek/mtklogger/framework/LogInstance;
.source "MobileLog.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogConnection;,
        Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;,
        Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogThread;
    }
.end annotation


# static fields
.field private static final COMMAND_BOOT:Ljava/lang/String; = "copy"

.field private static final COMMAND_CHECK:Ljava/lang/String; = "check"

.field private static final COMMAND_DIE:Ljava/lang/String; = "die"

.field private static final COMMAND_IPO:Ljava/lang/String; = "IPO_BOOT"

.field private static final COMMAND_IPO_SHUTDOWN:Ljava/lang/String; = "IPO_SHUTDOWN"

.field private static final COMMAND_START:Ljava/lang/String; = "start"

.field private static final COMMAND_STOP:Ljava/lang/String; = "stop"

.field public static final KEY_SUB_LOG_TYPE_MAP:Landroid/util/SparseArray; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static final SOCKET_NAME:Ljava/lang/String; = "mobilelogd"

.field public static final SUB_LOG_TYPE_ANDROID:I = 0x1

.field public static final SUB_LOG_TYPE_BT:I = 0x3

.field public static final SUB_LOG_TYPE_KERNEL:I = 0x2

.field private static final TAG:Ljava/lang/String; = "MTKLogger/MobileLog"


# instance fields
.field private bConnected:Z

.field private mCmdResHandler:Landroid/os/Handler;

.field mobileLogLock:Ljava/lang/Object;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .prologue
    .line 31
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lcom/mediatek/mtklogger/framework/MobileLog;->KEY_SUB_LOG_TYPE_MAP:Landroid/util/SparseArray;

    .line 33
    sget-object v0, Lcom/mediatek/mtklogger/framework/MobileLog;->KEY_SUB_LOG_TYPE_MAP:Landroid/util/SparseArray;

    const/4 v1, 0x1

    const-string v2, "AndroidLog"

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 34
    sget-object v0, Lcom/mediatek/mtklogger/framework/MobileLog;->KEY_SUB_LOG_TYPE_MAP:Landroid/util/SparseArray;

    const/4 v1, 0x2

    const-string v2, "KernelLog"

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 35
    sget-object v0, Lcom/mediatek/mtklogger/framework/MobileLog;->KEY_SUB_LOG_TYPE_MAP:Landroid/util/SparseArray;

    const/4 v1, 0x3

    const-string v2, "BTLog"

    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 36
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/os/Handler;)V
    .locals 6
    .parameter "context"
    .parameter "handler"

    .prologue
    .line 51
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/LogInstance;-><init>(Landroid/content/Context;)V

    .line 38
    const/4 v2, 0x0

    iput-boolean v2, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z

    .line 42
    const/4 v2, 0x0

    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mCmdResHandler:Landroid/os/Handler;

    .line 49
    new-instance v2, Ljava/lang/Object;

    invoke-direct {v2}, Ljava/lang/Object;-><init>()V

    iput-object v2, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mobileLogLock:Ljava/lang/Object;

    .line 52
    new-instance v1, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogThread;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogThread;-><init>(Lcom/mediatek/mtklogger/framework/MobileLog;)V

    .line 53
    .local v1, mobileLogThread:Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogThread;
    invoke-virtual {v1}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogThread;->start()V

    .line 54
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mobileLogLock:Ljava/lang/Object;

    monitor-enter v3

    .line 56
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mobileLogLock:Ljava/lang/Object;

    const-wide/16 v4, 0x1f4

    invoke-virtual {v2, v4, v5}, Ljava/lang/Object;->wait(J)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 60
    :goto_0
    :try_start_1
    monitor-exit v3
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 61
    iput-object p2, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mCmdResHandler:Landroid/os/Handler;

    .line 63
    return-void

    .line 57
    :catch_0
    move-exception v0

    .line 58
    .local v0, e:Ljava/lang/InterruptedException;
    :try_start_2
    const-string v2, "MTKLogger/MobileLog"

    const-string v4, "Wait modem log sub thread initialization, but was interrupted"

    invoke-static {v2, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 60
    .end local v0           #e:Ljava/lang/InterruptedException;
    :catchall_0
    move-exception v2

    monitor-exit v3
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v2
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 13
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z

    return v0
.end method

.method static synthetic access$002(Lcom/mediatek/mtklogger/framework/MobileLog;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 13
    iput-boolean p1, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z

    return p1
.end method

.method static synthetic access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"
    .parameter "x2"

    .prologue
    .line 13
    invoke-direct {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V

    return-void
.end method

.method private notifyServiceStatus(ILjava/lang/String;)V
    .locals 6
    .parameter "status"
    .parameter "reason"

    .prologue
    const v5, 0x7f070002

    const/4 v4, 0x0

    const/4 v3, 0x1

    .line 278
    const-string v0, "MTKLogger/MobileLog"

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

    .line 280
    if-ne v3, p1, :cond_1

    .line 281
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v0, :cond_0

    .line 282
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "mobilelog_enable"

    invoke-interface {v0, v1, v3}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 286
    :cond_0
    invoke-virtual {p0, v3, v5, v3}, Lcom/mediatek/mtklogger/framework/MobileLog;->updateStatusBar(IIZ)V

    .line 297
    :goto_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mCmdResHandler:Landroid/os/Handler;

    invoke-virtual {v0, v3, v3, p1, p2}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v0

    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 298
    return-void

    .line 288
    :cond_1
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v0, :cond_2

    .line 289
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "mobilelog_enable"

    invoke-interface {v0, v1, v4}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 293
    :cond_2
    invoke-virtual {p0, v3, v5, v4}, Lcom/mediatek/mtklogger/framework/MobileLog;->updateStatusBar(IIZ)V

    goto :goto_0
.end method
