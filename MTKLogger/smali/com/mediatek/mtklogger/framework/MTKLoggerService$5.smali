.class Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;
.super Lcom/mediatek/mtklogger/IMTKLoggerManager$Stub;
.source "MTKLoggerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/framework/MTKLoggerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V
    .locals 0
    .parameter

    .prologue
    .line 912
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    invoke-direct {p0}, Lcom/mediatek/mtklogger/IMTKLoggerManager$Stub;-><init>()V

    return-void
.end method


# virtual methods
.method public clearLog()Z
    .locals 2

    .prologue
    .line 1018
    const-string v0, "MTKLogger/MTKLoggerService"

    const-string v1, "-->clearLog()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1019
    const/4 v0, 0x1

    return v0
.end method

.method public getCurrentRunningStage()I
    .locals 1

    .prologue
    .line 1031
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getGlobalRunningStage()I
    invoke-static {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1500(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)I

    move-result v0

    return v0
.end method

.method public getLogRunningStatus(I)I
    .locals 1
    .parameter "logType"

    .prologue
    .line 1036
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    invoke-virtual {v0, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstanceRunningStatus(I)I

    move-result v0

    return v0
.end method

.method public runCommand(Ljava/lang/String;)Z
    .locals 5
    .parameter "cmd"

    .prologue
    const/4 v1, 0x0

    .line 925
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "-->runCommand(), command="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 926
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    const/4 v3, 0x4

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;
    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 927
    .local v0, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-nez v0, :cond_0

    .line 928
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "Fail to get shell command handler."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 937
    :goto_0
    return v1

    .line 931
    :cond_0
    iget-object v2, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v2, :cond_1

    .line 932
    iget-object v1, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/4 v2, 0x5

    invoke-virtual {v1, v2, p1}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    .line 933
    const/4 v1, 0x1

    goto :goto_0

    .line 935
    :cond_1
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "When runCommand(), fail to get log instance handler of network log."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method public setAutoStart(IZ)Z
    .locals 5
    .parameter "logType"
    .parameter "autoStart"

    .prologue
    const/4 v1, 0x0

    .line 980
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "-->setAutoStart(), logType="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ", autoStart?"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 981
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;
    invoke-static {v2, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 982
    .local v0, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-nez v0, :cond_0

    .line 983
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "Fail to get log instance for config auto start value."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 993
    :goto_0
    return v1

    .line 986
    :cond_0
    iget-object v2, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v2, :cond_2

    .line 987
    iget-object v2, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/4 v3, 0x7

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "autostart="

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    if-eqz p2, :cond_1

    const-string v1, "1"

    :goto_1
    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v2, v3, v1}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    .line 989
    const/4 v1, 0x1

    goto :goto_0

    .line 987
    :cond_1
    const-string v1, "0"

    goto :goto_1

    .line 991
    :cond_2
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "When setAutoStart(), fail to get log instance handler  of log ["

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "]."

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method public setLogSize(II)Z
    .locals 5
    .parameter "logType"
    .parameter "size"

    .prologue
    const/4 v1, 0x0

    .line 961
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "-->setLogSize(), logType="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ", size="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 962
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;
    invoke-static {v2, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 963
    .local v0, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-nez v0, :cond_0

    .line 964
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "Fail to get log instance for config log size."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 974
    :goto_0
    return v1

    .line 967
    :cond_0
    iget-object v2, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v2, :cond_1

    .line 968
    iget-object v1, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/4 v2, 0x7

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "logsize="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    .line 970
    const/4 v1, 0x1

    goto :goto_0

    .line 972
    :cond_1
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "When setLogSize(), fail to get log instance handler  of log ["

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "]."

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method public setSubLogEnableState(IIZ)Z
    .locals 6
    .parameter "logType"
    .parameter "subType"
    .parameter "enable"

    .prologue
    const/4 v2, 0x1

    const/4 v1, 0x0

    .line 999
    const-string v3, "MTKLogger/MTKLoggerService"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "-->setSubLogEnableState(), logType="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, ", subType="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, ", enable?"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1000
    iget-object v3, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;
    invoke-static {v3, p1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 1001
    .local v0, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-nez v0, :cond_0

    .line 1002
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "Fail to get log instance for config sub log enable state."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 1012
    :goto_0
    return v1

    .line 1005
    :cond_0
    iget-object v3, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v3, :cond_2

    .line 1006
    iget-object v3, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/4 v4, 0x7

    if-eqz p3, :cond_1

    move v1, v2

    :cond_1
    const-string v5, "sublog_"

    invoke-virtual {v3, v4, p2, v1, v5}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    move v1, v2

    .line 1008
    goto :goto_0

    .line 1010
    :cond_2
    const-string v2, "MTKLogger/MTKLoggerService"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "When setSubLogEnableState(), fail to get log instance handler  of log ["

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "]."

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method public startLog(I)Z
    .locals 2
    .parameter "logTypeCluster"

    .prologue
    .line 915
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    const/4 v1, 0x0

    invoke-virtual {v0, p1, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->startRecording(ILjava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public stopCommand()Z
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 943
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "-->stopCommand()"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 944
    iget-object v2, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    const/4 v3, 0x4

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstance(I)Lcom/mediatek/mtklogger/framework/LogInstance;
    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1100(Lcom/mediatek/mtklogger/framework/MTKLoggerService;I)Lcom/mediatek/mtklogger/framework/LogInstance;

    move-result-object v0

    .line 945
    .local v0, instance:Lcom/mediatek/mtklogger/framework/LogInstance;
    if-nez v0, :cond_0

    .line 946
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "Fail to get shell command handler."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 955
    :goto_0
    return v1

    .line 949
    :cond_0
    iget-object v2, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    if-eqz v2, :cond_1

    .line 950
    iget-object v1, v0, Lcom/mediatek/mtklogger/framework/LogInstance;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    const/4 v2, 0x6

    invoke-virtual {v1, v2}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v1

    invoke-virtual {v1}, Landroid/os/Message;->sendToTarget()V

    .line 951
    const/4 v1, 0x1

    goto :goto_0

    .line 953
    :cond_1
    const-string v2, "MTKLogger/MTKLoggerService"

    const-string v3, "When stopCommand(), fail to get log instance handler of network log."

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0
.end method

.method public stopLog(I)Z
    .locals 2
    .parameter "logTypeCluster"

    .prologue
    .line 920
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    const/4 v1, 0x0

    invoke-virtual {v0, p1, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->stopRecording(ILjava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public tagLog()Z
    .locals 2

    .prologue
    .line 1024
    const-string v0, "MTKLogger/MTKLoggerService"

    const-string v1, "-->tagLog()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 1025
    iget-object v0, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$5;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    #calls: Lcom/mediatek/mtklogger/framework/MTKLoggerService;->beginTagLog()Z
    invoke-static {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->access$1400(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)Z

    .line 1026
    const/4 v0, 0x1

    return v0
.end method
