.class Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;
.super Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
.source "MobileLog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/framework/MobileLog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "MobileLogHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/framework/MobileLog;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/framework/MobileLog;)V
    .locals 0
    .parameter

    .prologue
    .line 82
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;-><init>(Lcom/mediatek/mtklogger/framework/LogInstance;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 14
    .parameter "msg"

    .prologue
    .line 85
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Handle receive message, what="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    iget v11, p1, Landroid/os/Message;->what:I

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 90
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    #getter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z

    move-result v9

    if-nez v9, :cond_1

    .line 91
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    if-nez v9, :cond_0

    .line 92
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    new-instance v10, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogConnection;

    iget-object v11, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const-string v12, "mobilelogd"

    iget-object v13, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v13, v13, Lcom/mediatek/mtklogger/framework/MobileLog;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    invoke-direct {v10, v11, v12, v13}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogConnection;-><init>(Lcom/mediatek/mtklogger/framework/MobileLog;Ljava/lang/String;Landroid/os/Handler;)V

    iput-object v10, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    .line 94
    :cond_0
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v10, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    invoke-virtual {v10}, Lcom/mediatek/mtklogger/framework/MobileLog;->initLogConnection()Z

    move-result v10

    #setter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$002(Lcom/mediatek/mtklogger/framework/MobileLog;Z)Z

    .line 98
    :cond_1
    iget-object v1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 99
    .local v1, cmdReasonObj:Ljava/lang/Object;
    const/4 v0, 0x0

    .line 100
    .local v0, cmdReason:Ljava/lang/String;
    if-eqz v1, :cond_2

    instance-of v9, v1, Ljava/lang/String;

    if-eqz v9, :cond_2

    move-object v0, v1

    .line 101
    check-cast v0, Ljava/lang/String;

    .line 103
    :cond_2
    iget v9, p1, Landroid/os/Message;->what:I

    sparse-switch v9, :sswitch_data_0

    .line 266
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Not supported message: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    iget v11, p1, Landroid/os/Message;->what:I

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 269
    :cond_3
    :goto_0
    return-void

    .line 105
    :sswitch_0
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    #getter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z

    move-result v9

    if-nez v9, :cond_5

    .line 106
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Fail to establish connection to native layer."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 107
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v9, :cond_4

    const/4 v9, 0x1

    iget-object v10, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v10, v10, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v11, "mobilelog_enable"

    const/4 v12, 0x1

    invoke-interface {v10, v11, v12}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v10

    if-ne v9, v10, :cond_4

    .line 109
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v9}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v9

    const-string v10, "mobilelog_enable"

    const/4 v11, 0x0

    invoke-interface {v9, v10, v11}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v9

    invoke-interface {v9}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 113
    :cond_4
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "1"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto :goto_0

    .line 116
    :cond_5
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    invoke-virtual {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->getLogStorageState()I

    move-result v5

    .line 117
    .local v5, logStorageStatus:I
    const/4 v9, 0x1

    if-eq v5, v9, :cond_8

    .line 118
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Log storage is not ready yet."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 119
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    if-eqz v9, :cond_6

    const/4 v9, 0x1

    iget-object v10, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v10, v10, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v11, "mobilelog_enable"

    const/4 v12, 0x1

    invoke-interface {v10, v11, v12}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v10

    if-ne v9, v10, :cond_6

    .line 121
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v9}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v9

    const-string v10, "mobilelog_enable"

    const/4 v11, 0x0

    invoke-interface {v9, v10, v11}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v9

    invoke-interface {v9}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 127
    :cond_6
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Going to start mobile log, but SD card not ready yet, status="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, ", just send out a stop command to native."

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 129
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "stop"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    .line 130
    const/4 v9, -0x1

    if-ne v5, v9, :cond_7

    .line 131
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "2"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 132
    :cond_7
    const/4 v9, -0x2

    if-ne v5, v9, :cond_3

    .line 133
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "3"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 136
    :cond_8
    const/4 v2, 0x0

    .line 137
    .local v2, cmdSuccess:Z
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-nez v9, :cond_b

    .line 138
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Mobile log initialization reason = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 139
    const-string v9, "boot"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_9

    .line 140
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "copy"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    .line 151
    :goto_1
    if-eqz v2, :cond_c

    .line 152
    const/4 v9, 0x4

    const-wide/16 v10, 0x2710

    invoke-virtual {p0, v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->sendEmptyMessageDelayed(IJ)Z

    .line 153
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x1

    const-string v11, ""

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 141
    :cond_9
    const-string v9, "ipo"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_a

    .line 142
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "IPO_BOOT"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    goto :goto_1

    .line 144
    :cond_a
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Unsupported initialization reason, ignore it."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 145
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "start"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    goto :goto_1

    .line 148
    :cond_b
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "No valid start up reason was found when init. Just start up."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 149
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "start"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    goto :goto_1

    .line 155
    :cond_c
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Send start command to native layer fail, maybe connection has already be lost."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 156
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "4"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 161
    .end local v2           #cmdSuccess:Z
    .end local v5           #logStorageStatus:I
    :sswitch_1
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    #getter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z

    move-result v9

    if-eqz v9, :cond_11

    .line 162
    const/4 v2, 0x0

    .line 163
    .restart local v2       #cmdSuccess:Z
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-nez v9, :cond_e

    .line 164
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Mobile log stop reason = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 165
    const-string v9, "ipo_shutdown"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_d

    .line 166
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "IPO_SHUTDOWN"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    .line 176
    :goto_2
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    invoke-virtual {v9}, Lcom/mediatek/mtklogger/framework/LogConnection;->stop()V

    .line 177
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    iput-object v10, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    .line 178
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    #setter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$002(Lcom/mediatek/mtklogger/framework/MobileLog;Z)Z

    .line 180
    if-nez v2, :cond_f

    .line 181
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Send stop command to native layer fail, maybe connection has already be lost."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 182
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "4"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    .line 191
    :goto_3
    const/4 v9, 0x4

    invoke-virtual {p0, v9}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->removeMessages(I)V

    goto/16 :goto_0

    .line 168
    :cond_d
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Unsupported stop reason, ignore it."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 169
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "stop"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    goto :goto_2

    .line 172
    :cond_e
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Normally stop mobile log with stop command"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 173
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    const-string v10, "stop"

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    move-result v2

    goto :goto_2

    .line 184
    :cond_f
    const-string v6, ""

    .line 185
    .local v6, stopReason:Ljava/lang/String;
    const-string v9, "sd_timeout"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_10

    .line 186
    const-string v6, "11"

    .line 188
    :cond_10
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v6}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto :goto_3

    .line 193
    .end local v2           #cmdSuccess:Z
    .end local v6           #stopReason:Ljava/lang/String;
    :cond_11
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Have not connected to native layer, just ignore this command"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 195
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "1"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 199
    :sswitch_2
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    #getter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z

    move-result v9

    if-eqz v9, :cond_12

    .line 200
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Receive a check command. Ignore it."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 204
    :cond_12
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "lost connection to native layer, stop check."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 205
    const/4 v9, 0x4

    invoke-virtual {p0, v9}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->removeMessages(I)V

    goto/16 :goto_0

    .line 209
    :sswitch_3
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    #getter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z

    move-result v9

    if-eqz v9, :cond_18

    .line 210
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v9

    if-nez v9, :cond_17

    .line 211
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Receive config command, parameter="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 212
    const-string v9, "logsize="

    invoke-virtual {v0, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-nez v9, :cond_13

    const-string v9, "autostart="

    invoke-virtual {v0, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_14

    .line 214
    :cond_13
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    invoke-virtual {v9, v0}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    goto/16 :goto_0

    .line 215
    :cond_14
    const-string v9, "sublog_"

    invoke-virtual {v0, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_16

    .line 216
    iget v8, p1, Landroid/os/Message;->arg1:I

    .line 217
    .local v8, subType:I
    iget v3, p1, Landroid/os/Message;->arg2:I

    .line 218
    .local v3, enableValue:I
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Try to set mobile sub log enable state, subType="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, ", enable?"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 220
    sget-object v9, Lcom/mediatek/mtklogger/framework/MobileLog;->KEY_SUB_LOG_TYPE_MAP:Landroid/util/SparseArray;

    invoke-virtual {v9, v8}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 221
    .local v7, subLogStr:Ljava/lang/String;
    if-nez v7, :cond_15

    .line 222
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Unsupported sub mobile log type"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 224
    :cond_15
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "sublog_"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/framework/LogConnection;->sendCmd(Ljava/lang/String;)Z

    goto/16 :goto_0

    .line 228
    .end local v3           #enableValue:I
    .end local v7           #subLogStr:Ljava/lang/String;
    .end local v8           #subType:I
    :cond_16
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Unsupported config command"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 232
    :cond_17
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Receive config command, but parameter is null"

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 235
    :cond_18
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Fail to config native parameter because of lost connection."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 239
    :sswitch_4
    const-string v9, "MTKLogger/MobileLog"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Receive adb command["

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "]"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 242
    :sswitch_5
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    #getter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$000(Lcom/mediatek/mtklogger/framework/MobileLog;)Z

    move-result v9

    if-nez v9, :cond_3

    .line 243
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Reconnect to native layer fail! Mark log status as stopped."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 244
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "1"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 248
    :sswitch_6
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    if-eqz v9, :cond_19

    .line 250
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    invoke-virtual {v9}, Lcom/mediatek/mtklogger/framework/LogConnection;->stop()V

    .line 251
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    iput-object v10, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mLogConnection:Lcom/mediatek/mtklogger/framework/LogConnection;

    .line 253
    :cond_19
    const/4 v9, 0x4

    invoke-virtual {p0, v9}, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->removeMessages(I)V

    .line 254
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    #setter for: Lcom/mediatek/mtklogger/framework/MobileLog;->bConnected:Z
    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$002(Lcom/mediatek/mtklogger/framework/MobileLog;Z)Z

    .line 255
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x0

    const-string v11, "5"

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 258
    :sswitch_7
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    iget-object v9, v9, Lcom/mediatek/mtklogger/framework/MobileLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v10, "mobilelog_enable"

    const/4 v11, 0x0

    invoke-interface {v9, v10, v11}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v4

    .line 259
    .local v4, formerStatus:I
    const/4 v9, 0x1

    if-eq v4, v9, :cond_1a

    .line 260
    iget-object v9, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    const/4 v10, 0x1

    const-string v11, ""

    #calls: Lcom/mediatek/mtklogger/framework/MobileLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v9, v10, v11}, Lcom/mediatek/mtklogger/framework/MobileLog;->access$100(Lcom/mediatek/mtklogger/framework/MobileLog;ILjava/lang/String;)V

    goto/16 :goto_0

    .line 262
    :cond_1a
    const-string v9, "MTKLogger/MobileLog"

    const-string v10, "Still running, ignore alive message."

    invoke-static {v9, v10}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0

    .line 103
    nop

    :sswitch_data_0
    .sparse-switch
        0x1 -> :sswitch_0
        0x3 -> :sswitch_1
        0x4 -> :sswitch_2
        0x7 -> :sswitch_3
        0x8 -> :sswitch_4
        0x9 -> :sswitch_5
        0x16 -> :sswitch_6
        0x17 -> :sswitch_7
    .end sparse-switch
.end method
