.class public Lcom/mediatek/mtklogger/framework/MTKLoggerManager;
.super Ljava/lang/Object;
.source "MTKLoggerManager.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "MTKLogger/MTKLoggerManager"


# instance fields
.field private mContext:Landroid/content/Context;

.field private mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

.field mServiceConnection:Landroid/content/ServiceConnection;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .parameter "context"

    .prologue
    const/4 v0, 0x0

    .line 31
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 15
    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    .line 16
    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    .line 18
    new-instance v0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager$1;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager$1;-><init>(Lcom/mediatek/mtklogger/framework/MTKLoggerManager;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mServiceConnection:Landroid/content/ServiceConnection;

    .line 32
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    .line 33
    invoke-direct {p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->initService()V

    .line 34
    return-void
.end method

.method static synthetic access$002(Lcom/mediatek/mtklogger/framework/MTKLoggerManager;Lcom/mediatek/mtklogger/IMTKLoggerManager;)Lcom/mediatek/mtklogger/IMTKLoggerManager;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 13
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    return-object p1
.end method

.method private initService()V
    .locals 4

    .prologue
    .line 41
    const-string v1, "MTKLogger/MTKLoggerManager"

    const-string v2, "-->initService()"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 42
    new-instance v0, Landroid/content/Intent;

    const-string v1, "com.mediatek.mtklogger.MTKLoggerService"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 45
    .local v0, intent:Landroid/content/Intent;
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 47
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mServiceConnection:Landroid/content/ServiceConnection;

    const/4 v3, 0x1

    invoke-virtual {v1, v0, v2, v3}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    move-result v1

    if-nez v1, :cond_0

    .line 48
    const-string v1, "MTKLogger/MTKLoggerManager"

    const-string v2, "Fail to bind to MTKLoggerService"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 50
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mServiceConnection:Landroid/content/ServiceConnection;

    invoke-virtual {v1, v2}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 51
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 53
    :cond_0
    return-void
.end method


# virtual methods
.method public clearLog()Z
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 184
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 185
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 192
    :goto_0
    return v1

    .line 189
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->clearLog()Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 190
    :catch_0
    move-exception v0

    .line 191
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public free()V
    .locals 2

    .prologue
    .line 37
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mServiceConnection:Landroid/content/ServiceConnection;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    .line 38
    return-void
.end method

.method public getCurrentRunningStage()I
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 219
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 220
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 227
    :goto_0
    return v1

    .line 224
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->getCurrentRunningStage()I
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 225
    :catch_0
    move-exception v0

    .line 226
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public getModemLogRunningDetailStatus()I
    .locals 3

    .prologue
    .line 237
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v1, :cond_0

    .line 238
    const-string v1, "MTKLogger/MTKLoggerManager"

    const-string v2, "Service has not been bind to yet."

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 239
    const/4 v1, -0x1

    .line 245
    :goto_0
    return v1

    .line 242
    :cond_0
    :try_start_0
    iget-object v1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    const/4 v2, 0x2

    invoke-interface {v1, v2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->getLogRunningStatus(I)I
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 243
    :catch_0
    move-exception v0

    .line 244
    .local v0, e:Landroid/os/RemoteException;
    const-string v1, "MTKLogger/MTKLoggerManager"

    const-string v2, "Fail to call service API."

    invoke-static {v1, v2, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 245
    const/4 v1, 0x0

    goto :goto_0
.end method

.method public runCommand(Ljava/lang/String;)Z
    .locals 4
    .parameter "cmd"

    .prologue
    const/4 v1, 0x0

    .line 97
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 98
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 105
    :goto_0
    return v1

    .line 102
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2, p1}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->runCommand(Ljava/lang/String;)Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 103
    :catch_0
    move-exception v0

    .line 104
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public setAutoStart(IZ)Z
    .locals 4
    .parameter "logType"
    .parameter "autoStart"

    .prologue
    const/4 v1, 0x0

    .line 154
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 155
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 162
    :goto_0
    return v1

    .line 159
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2, p1, p2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->setAutoStart(IZ)Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 160
    :catch_0
    move-exception v0

    .line 161
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public setLogSize(II)Z
    .locals 4
    .parameter "logType"
    .parameter "logSize"

    .prologue
    const/4 v1, 0x0

    .line 134
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 135
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 142
    :goto_0
    return v1

    .line 139
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2, p1, p2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->setLogSize(II)Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 140
    :catch_0
    move-exception v0

    .line 141
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public setMobileSubLogEnableState(IZ)Z
    .locals 4
    .parameter "subLogType"
    .parameter "enable"

    .prologue
    const/4 v1, 0x0

    .line 167
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 168
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 175
    :goto_0
    return v1

    .line 172
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    const/4 v3, 0x1

    invoke-interface {v2, v3, p1, p2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->setSubLogEnableState(IIZ)Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 173
    :catch_0
    move-exception v0

    .line 174
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public startLog(I)Z
    .locals 4
    .parameter "logType"

    .prologue
    const/4 v1, 0x0

    .line 61
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 62
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 69
    :goto_0
    return v1

    .line 66
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2, p1}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->startLog(I)Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 67
    :catch_0
    move-exception v0

    .line 68
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public stopCommand(Ljava/lang/String;)Z
    .locals 4
    .parameter "cmd"

    .prologue
    const/4 v1, 0x0

    .line 115
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 116
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 123
    :goto_0
    return v1

    .line 120
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->stopCommand()Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 121
    :catch_0
    move-exception v0

    .line 122
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public stopLog(I)Z
    .locals 4
    .parameter "logType"

    .prologue
    const/4 v1, 0x0

    .line 79
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 80
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 87
    :goto_0
    return v1

    .line 84
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2, p1}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->stopLog(I)Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 85
    :catch_0
    move-exception v0

    .line 86
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method

.method public tagLog()Z
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 201
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    if-nez v2, :cond_0

    .line 202
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Service has not been bind to yet."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 209
    :goto_0
    return v1

    .line 206
    :cond_0
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->mService:Lcom/mediatek/mtklogger/IMTKLoggerManager;

    invoke-interface {v2}, Lcom/mediatek/mtklogger/IMTKLoggerManager;->tagLog()Z
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    goto :goto_0

    .line 207
    :catch_0
    move-exception v0

    .line 208
    .local v0, e:Landroid/os/RemoteException;
    const-string v2, "MTKLogger/MTKLoggerManager"

    const-string v3, "Fail to call service API."

    invoke-static {v2, v3, v0}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    goto :goto_0
.end method
