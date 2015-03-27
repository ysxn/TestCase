.class public Lcom/mediatek/mtklogger/framework/LogReceiver;
.super Landroid/content/BroadcastReceiver;
.source "LogReceiver.java"


# static fields
.field private static final ACTION_CALIBRATION_DATA:Ljava/lang/String; = "android.intent.action.DOWNLOAD_CALIBRATION_DATA"

.field private static final DELAY_KILL_SELF:I = 0x2710

.field private static final MSG_KILL_SELF:I = 0x1

.field private static final TAG:Ljava/lang/String; = "MTKLogger/LogReceiver"

.field public static bootupFlag:Z

.field public static canKillSelf:Z

.field private static mKillProcessCmdHandler:Landroid/os/Handler;


# instance fields
.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field private mSharedPreferences:Landroid/content/SharedPreferences;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 35
    const/4 v0, 0x0

    sput-boolean v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->bootupFlag:Z

    .line 41
    const/4 v0, 0x1

    sput-boolean v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->canKillSelf:Z

    .line 193
    new-instance v0, Lcom/mediatek/mtklogger/framework/LogReceiver$1;

    invoke-direct {v0}, Lcom/mediatek/mtklogger/framework/LogReceiver$1;-><init>()V

    sput-object v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .prologue
    const/4 v0, 0x0

    .line 25
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    .line 31
    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 32
    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    return-void
.end method

.method private checkProcess()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    .line 356
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->needStartLogAtBootTime()Z

    move-result v0

    .line 357
    .local v0, needAutoStart:Z
    const-string v1, "MTKLogger/LogReceiver"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "-->checkProcess(), needAutoStart="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ", canKillSelf="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-boolean v3, Lcom/mediatek/mtklogger/framework/LogReceiver;->canKillSelf:Z

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 359
    if-nez v0, :cond_0

    sget-boolean v1, Lcom/mediatek/mtklogger/framework/LogReceiver;->canKillSelf:Z

    if-eqz v1, :cond_0

    .line 360
    const-string v1, "MTKLogger/LogReceiver"

    const-string v2, "MTKLogger process should not startup, send a kill message to kill it."

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 361
    sget-object v1, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    invoke-virtual {v1, v4}, Landroid/os/Handler;->removeMessages(I)V

    .line 362
    sget-object v1, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    sget-object v2, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    invoke-virtual {v2, v4}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v2

    const-wide/16 v3, 0x2710

    invoke-virtual {v1, v2, v3, v4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    .line 365
    :cond_0
    return-void
.end method

.method private checkRebootIssue(Landroid/content/Context;)V
    .locals 3
    .parameter "context"

    .prologue
    .line 273
    const-string v1, "MTKLogger/LogReceiver"

    const-string v2, "-->checkRebootIssue()"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 274
    const-string v1, "debug.mtk.aee.db"

    const-string v2, ""

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 275
    .local v0, dbFlag:Ljava/lang/String;
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 276
    const-string v1, "MTKLogger/LogReceiver"

    const-string v2, "After reboot completed, detect an exception, notify Exception Reporter"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 277
    sget-object v1, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/os/Handler;->removeMessages(I)V

    .line 278
    invoke-direct {p0, p1, v0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->reportDb2ExceptionReporter(Landroid/content/Context;Ljava/lang/String;)V

    .line 282
    :goto_0
    return-void

    .line 280
    :cond_0
    const-string v1, "MTKLogger/LogReceiver"

    const-string v2, "Normal boot completed event."

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method private handleSystemTimeChanged()V
    .locals 6

    .prologue
    .line 320
    const-string v3, "MTKLogger/LogReceiver"

    const-string v4, "-->handleSystemTimeChanged()"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 321
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->isAnyLogRunning()Z

    move-result v2

    .line 322
    .local v2, isLogRunning:Z
    if-eqz v2, :cond_0

    .line 323
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    .line 324
    .local v0, currentTime:J
    const-string v3, "MTKLogger/LogReceiver"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Log is running, need to notify user this log start time change event.currentTime="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 326
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "system_time_reset"

    const/4 v5, 0x1

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v4, "begin_recording_time"

    invoke-interface {v3, v4, v0, v1}, Landroid/content/SharedPreferences$Editor;->putLong(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 330
    const-string v3, "MTKLogger/LogReceiver"

    const-string v4, "Reset log starting time and store to shanred preference finished."

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 332
    .end local v0           #currentTime:J
    :cond_0
    return-void
.end method

.method private initLogStatus()V
    .locals 7

    .prologue
    const/4 v6, 0x0

    .line 209
    const-string v2, "MTKLogger/LogReceiver"

    const-string v3, "-->initLogStatus()"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 210
    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v2}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    .line 212
    .local v1, logType:Ljava/lang/Integer;
    const/4 v3, 0x1

    iget-object v4, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v2, v5}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v4, v2, v6}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v2

    if-ne v3, v2, :cond_1

    .line 213
    const-string v3, "MTKLogger/LogReceiver"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Boot up, set "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v5

    invoke-virtual {v2, v5}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v4, " to stopped"

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v3, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 214
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v3, v2, v6}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 220
    :cond_1
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->KEY_NEED_RECOVER_RUNNING_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v3, v2, v6}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 222
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    sget-object v2, Lcom/mediatek/mtklogger/utils/Utils;->KEY_NEED_RECOVER_RUNNING_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    invoke-interface {v3, v2, v6}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto/16 :goto_0

    .line 229
    .end local v1           #logType:Ljava/lang/Integer;
    :cond_2
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    const-string v3, "begin_recording_time"

    const-wide/16 v4, 0x0

    invoke-interface {v2, v3, v4, v5}, Landroid/content/SharedPreferences$Editor;->putLong(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 234
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    const-string v3, "hasStartedDebugMode"

    invoke-interface {v2, v3, v6}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 237
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    const-string v3, "system_time_reset"

    invoke-interface {v2, v3, v6}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 242
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    const-string v3, "md_assert_file_path"

    invoke-interface {v2, v3}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    const-string v3, "waiting_sd_ready_reason"

    invoke-interface {v2, v3}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 245
    return-void
.end method

.method private isAnyLogRunning()Z
    .locals 7

    .prologue
    .line 339
    const/4 v1, 0x0

    .line 340
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

    .line 341
    .local v2, logType:Ljava/lang/Integer;
    const/4 v4, 0x1

    iget-object v5, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

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

    .line 343
    const/4 v1, 0x1

    .line 347
    .end local v2           #logType:Ljava/lang/Integer;
    :cond_1
    const-string v3, "MTKLogger/LogReceiver"

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

    .line 348
    return v1
.end method

.method private needStartLogAtBootTime()Z
    .locals 8

    .prologue
    .line 254
    const/4 v2, 0x0

    .line 255
    .local v2, needStart:Z
    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v3}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .local v0, i$:Ljava/util/Iterator;
    :cond_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    .line 256
    .local v1, logType:Ljava/lang/Integer;
    const/4 v5, 0x1

    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    sget-object v3, Lcom/mediatek/mtklogger/utils/Utils;->KEY_START_AUTOMATIC_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    sget-object v4, Lcom/mediatek/mtklogger/utils/Utils;->DEFAULT_CONFIG_LOG_AUTO_START_MAP:Landroid/util/SparseArray;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v7

    invoke-virtual {v4, v7}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Boolean;

    invoke-virtual {v4}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v4

    invoke-interface {v6, v3, v4}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    if-ne v5, v3, :cond_0

    .line 260
    const/4 v2, 0x1

    .line 264
    .end local v1           #logType:Ljava/lang/Integer;
    :cond_1
    const-string v3, "MTKLogger/LogReceiver"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "-->needStartLogAtBootTime(), needStart="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 265
    return v2
.end method

.method private reportDb2ExceptionReporter(Landroid/content/Context;Ljava/lang/String;)V
    .locals 9
    .parameter "context"
    .parameter "dbPathStr"

    .prologue
    .line 293
    const-string v6, "MTKLogger/LogReceiver"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "-->reportDb2ExceptionReporter(), dbPathStr="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 294
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Detect a reboot exception:["

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "]"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    const/4 v7, 0x0

    invoke-static {p1, v6, v7}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v6

    invoke-virtual {v6}, Landroid/widget/Toast;->show()V

    .line 296
    const-string v6, "db."

    invoke-virtual {p2, v6}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v3

    .line 297
    .local v3, dbNamePos:I
    invoke-virtual {p2, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v2

    .line 298
    .local v2, dbName:Ljava/lang/String;
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, ":"

    invoke-virtual {p2, v7}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v7

    add-int/lit8 v7, v7, 0x1

    invoke-virtual {p2, v7}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    sget-object v7, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 299
    .local v1, dbFolderPath:Ljava/lang/String;
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ".dbg"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 300
    .local v0, dbFileName:Ljava/lang/String;
    const-string v5, "ZZ_INTERNAL"

    .line 302
    .local v5, zzFileName:Ljava/lang/String;
    new-instance v4, Landroid/content/Intent;

    const-string v6, "com.mediatek.mtklogger.MTKLoggerService"

    invoke-direct {v4, v6}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 303
    .local v4, serviceIntent:Landroid/content/Intent;
    const-string v6, "startup_type"

    const-string v7, "exception_happen"

    invoke-virtual {v4, v6, v7}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 305
    const-string v6, "from_reboot"

    const-string v7, "FROM_REBOOT"

    invoke-virtual {v4, v6, v7}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 306
    const-string v6, "path"

    invoke-virtual {v4, v6, v1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 307
    const-string v6, "db_filename"

    invoke-virtual {v4, v6, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 308
    const-string v6, "zz_filename"

    invoke-virtual {v4, v6, v5}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 310
    const-string v6, "MTKLogger/LogReceiver"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Report reboot exception to Exception Reporter module, path="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, ", dbName="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, ", zzName="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 312
    invoke-virtual {p1, v4}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 313
    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 16
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 45
    invoke-virtual/range {p2 .. p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v1

    .line 46
    .local v1, action:Ljava/lang/String;
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, " -->onReceive(), action="

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 47
    const-string v12, "log_settings"

    const/4 v13, 0x0

    move-object/from16 v0, p1

    invoke-virtual {v0, v12, v13}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v12

    move-object/from16 v0, p0

    iput-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 48
    invoke-static/range {p1 .. p1}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v12

    move-object/from16 v0, p0

    iput-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 49
    const-string v12, "android.intent.action.BOOT_COMPLETED"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-nez v12, :cond_0

    const-string v12, "android.intent.action.ACTION_BOOT_IPO"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_6

    .line 51
    :cond_0
    new-instance v10, Lcom/mediatek/mtklogger/framework/LogConfig;

    move-object/from16 v0, p1

    invoke-direct {v10, v0}, Lcom/mediatek/mtklogger/framework/LogConfig;-><init>(Landroid/content/Context;)V

    .line 52
    .local v10, logConfig:Lcom/mediatek/mtklogger/framework/LogConfig;
    invoke-virtual {v10}, Lcom/mediatek/mtklogger/framework/LogConfig;->checkConfig()V

    .line 55
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->needStartLogAtBootTime()Z

    move-result v12

    if-eqz v12, :cond_5

    .line 56
    sget-boolean v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->bootupFlag:Z

    if-eqz v12, :cond_1

    .line 57
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "Service already be started by boot event, ignore this event."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 191
    .end local v10           #logConfig:Lcom/mediatek/mtklogger/framework/LogConfig;
    :goto_0
    return-void

    .line 60
    .restart local v10       #logConfig:Lcom/mediatek/mtklogger/framework/LogConfig;
    :cond_1
    const/4 v12, 0x1

    sput-boolean v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->bootupFlag:Z

    .line 61
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->initLogStatus()V

    .line 62
    new-instance v11, Landroid/content/Intent;

    const-string v12, "com.mediatek.mtklogger.MTKLoggerService"

    invoke-direct {v11, v12}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 63
    .local v11, serviceIntent:Landroid/content/Intent;
    const-string v12, "android.intent.action.BOOT_COMPLETED"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_4

    .line 64
    const-string v12, "startup_type"

    const-string v13, "boot"

    invoke-virtual {v11, v12, v13}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 68
    :cond_2
    :goto_1
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v13, 0x1

    invoke-virtual {v12, v13}, Landroid/os/Handler;->removeMessages(I)V

    .line 69
    move-object/from16 v0, p1

    invoke-virtual {v0, v11}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 81
    .end local v11           #serviceIntent:Landroid/content/Intent;
    :goto_2
    const-string v12, "android.intent.action.BOOT_COMPLETED"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_3

    .line 82
    invoke-direct/range {p0 .. p1}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkRebootIssue(Landroid/content/Context;)V

    .line 190
    .end local v10           #logConfig:Lcom/mediatek/mtklogger/framework/LogConfig;
    :cond_3
    :goto_3
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, " OnReceive function exit."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 65
    .restart local v10       #logConfig:Lcom/mediatek/mtklogger/framework/LogConfig;
    .restart local v11       #serviceIntent:Landroid/content/Intent;
    :cond_4
    const-string v12, "android.intent.action.ACTION_BOOT_IPO"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_2

    .line 66
    const-string v12, "startup_type"

    const-string v13, "ipo"

    invoke-virtual {v11, v12, v13}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    goto :goto_1

    .line 74
    .end local v11           #serviceIntent:Landroid/content/Intent;
    :cond_5
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->initLogStatus()V

    .line 75
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "Do not need to start any log at boot time, send out self kill message."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 76
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v13, 0x1

    invoke-virtual {v12, v13}, Landroid/os/Handler;->removeMessages(I)V

    .line 77
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    sget-object v13, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v14, 0x1

    invoke-virtual {v13, v14}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v13

    const-wide/16 v14, 0x2710

    invoke-virtual {v12, v13, v14, v15}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto :goto_2

    .line 84
    .end local v10           #logConfig:Lcom/mediatek/mtklogger/framework/LogConfig;
    :cond_6
    const-string v12, "android.intent.action.ACTION_SHUTDOWN_IPO"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_7

    .line 86
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "Receive IPO shutdown event in LogReceiver, notify to service for safety, since service may already been killed by system."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 88
    const/4 v12, 0x1

    sput-boolean v12, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isInIPOShutdown:Z

    goto :goto_3

    .line 90
    :cond_7
    const-string v12, "com.mediatek.mtklogger.ADB_CMD"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_c

    .line 91
    new-instance v11, Landroid/content/Intent;

    const-string v12, "com.mediatek.mtklogger.MTKLoggerService"

    invoke-direct {v11, v12}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 92
    .restart local v11       #serviceIntent:Landroid/content/Intent;
    const-string v12, "cmd_target"

    const/4 v13, 0x0

    move-object/from16 v0, p2

    invoke-virtual {v0, v12, v13}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v9

    .line 93
    .local v9, logCluster:I
    const-string v12, "cmd_name"

    move-object/from16 v0, p2

    invoke-virtual {v0, v12}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 94
    .local v4, command:Ljava/lang/String;
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Receive adb command, logCluster="

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v13

    const-string v14, ", command="

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 95
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v12, :cond_8

    const-string v12, "1"

    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v14, "log_mode"

    const-string v15, "2"

    invoke-interface {v13, v14, v15}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v13

    invoke-virtual {v12, v13}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_8

    const-string v12, "force_modem_assert"

    invoke-virtual {v12, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_8

    .line 99
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "In USB mode, force modem assert command is not supported"

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 100
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkProcess()V

    goto/16 :goto_0

    .line 103
    :cond_8
    const-string v12, "switch_taglog"

    invoke-virtual {v12, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_b

    .line 104
    const-string v12, "cmd_target"

    const/4 v13, -0x1

    move-object/from16 v0, p2

    invoke-virtual {v0, v12, v13}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v7

    .line 105
    .local v7, iTagLogEnabled:I
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Receive a Taglog configuration broadcast, target value="

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 106
    const/4 v12, 0x1

    if-ne v12, v7, :cond_9

    .line 107
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v12}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v12

    const-string v13, "tagLogEnable"

    const/4 v14, 0x1

    invoke-interface {v12, v13, v14}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v12

    invoke-interface {v12}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 113
    :goto_4
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkProcess()V

    goto/16 :goto_0

    .line 108
    :cond_9
    if-nez v7, :cond_a

    .line 109
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v12}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v12

    const-string v13, "tagLogEnable"

    const/4 v14, 0x0

    invoke-interface {v12, v13, v14}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v12

    invoke-interface {v12}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto :goto_4

    .line 111
    :cond_a
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Configure taglog value invalid: "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_4

    .line 116
    .end local v7           #iTagLogEnabled:I
    :cond_b
    const-string v12, "startup_type"

    const-string v13, "adb"

    invoke-virtual {v11, v12, v13}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 117
    const-string v12, "cmd_target"

    invoke-virtual {v11, v12, v9}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 118
    const-string v12, "cmd_name"

    invoke-virtual {v11, v12, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 119
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v13, 0x1

    invoke-virtual {v12, v13}, Landroid/os/Handler;->removeMessages(I)V

    .line 120
    move-object/from16 v0, p1

    invoke-virtual {v0, v11}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    goto/16 :goto_3

    .line 121
    .end local v4           #command:Ljava/lang/String;
    .end local v9           #logCluster:I
    .end local v11           #serviceIntent:Landroid/content/Intent;
    :cond_c
    const-string v12, "com.mediatek.mdlogger.AUTOSTART_COMPLETE"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_e

    .line 122
    sget-boolean v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->bootupFlag:Z

    if-eqz v12, :cond_d

    .line 123
    new-instance v11, Landroid/content/Intent;

    const-string v12, "com.mediatek.mtklogger.MTKLoggerService"

    invoke-direct {v11, v12}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 124
    .restart local v11       #serviceIntent:Landroid/content/Intent;
    const-string v12, "startup_type"

    const-string v13, "update"

    invoke-virtual {v11, v12, v13}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 125
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v13, 0x1

    invoke-virtual {v12, v13}, Landroid/os/Handler;->removeMessages(I)V

    .line 126
    move-object/from16 v0, p1

    invoke-virtual {v0, v11}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    goto/16 :goto_3

    .line 128
    .end local v11           #serviceIntent:Landroid/content/Intent;
    :cond_d
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "Modem reset come before boot completed(IPO), just ignore this event, and send out self kill message."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 130
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v13, 0x1

    invoke-virtual {v12, v13}, Landroid/os/Handler;->removeMessages(I)V

    .line 131
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    sget-object v13, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v14, 0x1

    invoke-virtual {v13, v14}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v13

    const-wide/16 v14, 0x2710

    invoke-virtual {v12, v13, v14, v15}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto/16 :goto_3

    .line 134
    :cond_e
    const-string v12, "android.intent.action.DOWNLOAD_CALIBRATION_DATA"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_f

    .line 135
    const-string v12, "calibrationData"

    const/4 v13, 0x0

    move-object/from16 v0, p2

    invoke-virtual {v0, v12, v13}, Landroid/content/Intent;->getBooleanExtra(Ljava/lang/String;Z)Z

    move-result v3

    .line 136
    .local v3, calibrated:Z
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Get calibration info from modem. Calibrated?"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 137
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v12}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v5

    .line 138
    .local v5, edit:Landroid/content/SharedPreferences$Editor;
    const-string v12, "calibrationData"

    invoke-interface {v5, v12, v3}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v12

    invoke-interface {v12}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 140
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->needStartLogAtBootTime()Z

    move-result v12

    if-nez v12, :cond_3

    .line 141
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "Do not need to start any log when get calibration data, send out self kill message."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 143
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v13, 0x1

    invoke-virtual {v12, v13}, Landroid/os/Handler;->removeMessages(I)V

    .line 144
    sget-object v12, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    sget-object v13, Lcom/mediatek/mtklogger/framework/LogReceiver;->mKillProcessCmdHandler:Landroid/os/Handler;

    const/4 v14, 0x1

    invoke-virtual {v13, v14}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v13

    const-wide/16 v14, 0x2710

    invoke-virtual {v12, v13, v14, v15}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto/16 :goto_3

    .line 147
    .end local v3           #calibrated:Z
    .end local v5           #edit:Landroid/content/SharedPreferences$Editor;
    :cond_f
    const-string v12, "com.mediatek.log2server.EXCEPTION_HAPPEND"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_14

    .line 148
    const/4 v8, 0x0

    .line 149
    .local v8, isTagLogEnabled:Z
    sget-object v2, Landroid/os/Build;->TYPE:Ljava/lang/String;

    .line 150
    .local v2, buildType:Ljava/lang/String;
    if-eqz v2, :cond_10

    .line 151
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Build type :"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 152
    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v12

    const-string v13, "user"

    invoke-virtual {v12, v13}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v12

    if-eqz v12, :cond_11

    .line 153
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v13, "tagLogEnable"

    const/4 v14, 0x0

    invoke-interface {v12, v13, v14}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v8

    .line 158
    :cond_10
    :goto_5
    const-string v12, "MTKLogger/LogReceiver"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Receive exception happens broadcast, isTagLogEnabled?"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v8}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 159
    if-eqz v8, :cond_13

    .line 160
    new-instance v11, Landroid/content/Intent;

    const-string v12, "com.mediatek.mtklogger.MTKLoggerService"

    invoke-direct {v11, v12}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 161
    .restart local v11       #serviceIntent:Landroid/content/Intent;
    const-string v12, "startup_type"

    const-string v13, "exception_happen"

    invoke-virtual {v11, v12, v13}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 163
    invoke-virtual/range {p2 .. p2}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v6

    .line 164
    .local v6, extras:Landroid/os/Bundle;
    if-nez v6, :cond_12

    .line 165
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "--> intent.getExtras() == null"

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 166
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkProcess()V

    goto/16 :goto_0

    .line 155
    .end local v6           #extras:Landroid/os/Bundle;
    .end local v11           #serviceIntent:Landroid/content/Intent;
    :cond_11
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/mediatek/mtklogger/framework/LogReceiver;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v13, "tagLogEnable"

    const/4 v14, 0x1

    invoke-interface {v12, v13, v14}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v8

    goto :goto_5

    .line 169
    .restart local v6       #extras:Landroid/os/Bundle;
    .restart local v11       #serviceIntent:Landroid/content/Intent;
    :cond_12
    invoke-virtual {v11, v6}, Landroid/content/Intent;->putExtras(Landroid/os/Bundle;)Landroid/content/Intent;

    .line 170
    move-object/from16 v0, p1

    invoke-virtual {v0, v11}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    goto/16 :goto_3

    .line 173
    .end local v6           #extras:Landroid/os/Bundle;
    .end local v11           #serviceIntent:Landroid/content/Intent;
    :cond_13
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkProcess()V

    goto/16 :goto_3

    .line 175
    .end local v2           #buildType:Ljava/lang/String;
    .end local v8           #isTagLogEnabled:Z
    :cond_14
    const-string v12, "android.net.conn.CONNECTIVITY_CHANGE"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_16

    .line 178
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "start ExceptionReportManager"

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 179
    invoke-static/range {p1 .. p1}, Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;->getInstance(Landroid/content/Context;)Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;

    move-result-object v12

    move-object/from16 v0, p2

    invoke-virtual {v12, v0}, Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;->beginException(Landroid/content/Intent;)Z

    move-result v12

    if-nez v12, :cond_15

    .line 180
    const-string v12, "MTKLogger/LogReceiver"

    const-string v13, "Connectivity status changed, but log2server have nothing to report."

    invoke-static {v12, v13}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_3

    .line 182
    :cond_15
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkProcess()V

    goto/16 :goto_3

    .line 184
    :cond_16
    const-string v12, "android.intent.action.TIME_SET"

    invoke-virtual {v12, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_3

    .line 187
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->handleSystemTimeChanged()V

    .line 188
    invoke-direct/range {p0 .. p0}, Lcom/mediatek/mtklogger/framework/LogReceiver;->checkProcess()V

    goto/16 :goto_3
.end method
