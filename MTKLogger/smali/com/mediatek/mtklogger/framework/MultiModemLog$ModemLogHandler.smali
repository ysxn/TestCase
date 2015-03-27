.class Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;
.super Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;
.source "MultiModemLog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/framework/MultiModemLog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ModemLogHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V
    .locals 0
    .parameter

    .prologue
    .line 244
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;-><init>(Lcom/mediatek/mtklogger/framework/LogInstance;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 24
    .parameter "msg"

    .prologue
    .line 247
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Handle receive message, what="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->what:I

    move/from16 v22, v0

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v21

    const-string v22, ", msg.arg1=["

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->arg1:I

    move/from16 v22, v0

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v21

    const-string v22, "]"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 255
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-nez v20, :cond_1

    .line 256
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    if-nez v20, :cond_0

    .line 257
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    new-instance v21, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v22, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mHandler:Lcom/mediatek/mtklogger/framework/LogInstance$LogHandler;

    move-object/from16 v23, v0

    invoke-direct/range {v21 .. v23}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;Landroid/os/Handler;)V

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$002(Lcom/mediatek/mtklogger/framework/MultiModemLog;Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    .line 259
    :cond_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v21, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v21 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v21

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->initLogConnection()Z
    invoke-static/range {v21 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$200(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;)Z

    move-result v21

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$102(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z

    .line 262
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, -0x1

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$302(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    .line 266
    :cond_1
    move-object/from16 v0, p1

    iget-object v6, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 267
    .local v6, cmdReasonObj:Ljava/lang/Object;
    const/4 v5, 0x0

    .line 268
    .local v5, cmdReason:Ljava/lang/String;
    if-eqz v6, :cond_2

    instance-of v0, v6, Ljava/lang/String;

    move/from16 v20, v0

    if-eqz v20, :cond_2

    move-object v5, v6

    .line 269
    check-cast v5, Ljava/lang/String;

    .line 273
    :cond_2
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_3

    .line 274
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    const-string v21, "md_assert_file_path"

    const-string v22, ""

    invoke-interface/range {v20 .. v22}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 276
    .local v3, assertFileStr:Ljava/lang/String;
    invoke-static {v3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v20

    if-nez v20, :cond_3

    .line 277
    const-string v20, ";"

    move-object/from16 v0, v20

    invoke-virtual {v3, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v19

    .line 278
    .local v19, values:[Ljava/lang/String;
    move-object/from16 v0, v19

    array-length v0, v0

    move/from16 v20, v0

    const/16 v21, 0x2

    move/from16 v0, v20

    move/from16 v1, v21

    if-ne v0, v1, :cond_3

    .line 279
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "The former modem reset dialog was killed abnormally, re-show it, assert file path="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 282
    :try_start_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    aget-object v21, v19, v21

    invoke-static/range {v21 .. v21}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v21

    const/16 v22, 0x1

    aget-object v22, v19, v22

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->showMemoryDumpDoneDialog(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$400(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 289
    .end local v3           #assertFileStr:Ljava/lang/String;
    .end local v19           #values:[Ljava/lang/String;
    :cond_3
    :goto_0
    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->what:I

    move/from16 v20, v0

    sparse-switch v20, :sswitch_data_0

    .line 556
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Not supported message: "

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->what:I

    move/from16 v22, v0

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 559
    :cond_4
    :goto_1
    return-void

    .line 283
    .restart local v3       #assertFileStr:Ljava/lang/String;
    .restart local v19       #values:[Ljava/lang/String;
    :catch_0
    move-exception v10

    .line 284
    .local v10, e:Ljava/lang/NumberFormatException;
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Cached modem assert file format invalid"

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 291
    .end local v3           #assertFileStr:Ljava/lang/String;
    .end local v10           #e:Ljava/lang/NumberFormatException;
    .end local v19           #values:[Ljava/lang/String;
    :sswitch_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-nez v20, :cond_6

    .line 292
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Fail to establish connection to native layer."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 293
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    if-eqz v20, :cond_5

    const/16 v20, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v21, v0

    const-string v22, "modemlog_enable"

    const/16 v23, 0x1

    invoke-interface/range {v21 .. v23}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v21

    move/from16 v0, v20

    move/from16 v1, v21

    if-ne v0, v1, :cond_5

    .line 296
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    const-string v21, "modemlog_enable"

    const/16 v22, 0x0

    invoke-interface/range {v20 .. v22}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 299
    :cond_5
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "1"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto :goto_1

    .line 302
    :cond_6
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->getLogStorageState()I

    move-result v12

    .line 303
    .local v12, logStorageStatus:I
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->getCurrentMode()Ljava/lang/String;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$600(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Ljava/lang/String;

    move-result-object v8

    .line 306
    .local v8, currentMode:Ljava/lang/String;
    const-string v20, "1"

    move-object/from16 v0, v20

    invoke-virtual {v0, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-nez v20, :cond_9

    const/16 v20, 0x1

    move/from16 v0, v20

    if-eq v12, v0, :cond_9

    .line 308
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Log storage is not ready yet."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 309
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    if-eqz v20, :cond_7

    const/16 v20, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v21, v0

    const-string v22, "modemlog_enable"

    const/16 v23, 0x1

    invoke-interface/range {v21 .. v23}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v21

    move/from16 v0, v20

    move/from16 v1, v21

    if-ne v0, v1, :cond_7

    .line 312
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    const-string v21, "modemlog_enable"

    const/16 v22, 0x0

    invoke-interface/range {v20 .. v22}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 315
    :cond_7
    const/16 v20, -0x1

    move/from16 v0, v20

    if-ne v12, v0, :cond_8

    .line 316
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "2"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 318
    :cond_8
    const/16 v20, -0x2

    move/from16 v0, v20

    if-ne v12, v0, :cond_4

    .line 319
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "3"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 324
    :cond_9
    const-wide/16 v20, 0xc8

    :try_start_1
    invoke-static/range {v20 .. v21}, Ljava/lang/Thread;->sleep(J)V

    .line 325
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "start,"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    .line 326
    const-wide/16 v20, 0x1f4

    invoke-static/range {v20 .. v21}, Ljava/lang/Thread;->sleep(J)V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_1

    .line 330
    :goto_2
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "resume"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 331
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x1

    const-string v22, ""

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    .line 332
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->runMonitoringLogSizeThread()V
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$800(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V

    goto/16 :goto_1

    .line 327
    :catch_1
    move-exception v10

    .line 328
    .local v10, e:Ljava/lang/InterruptedException;
    invoke-virtual {v10}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_2

    .line 337
    .end local v8           #currentMode:Ljava/lang/String;
    .end local v10           #e:Ljava/lang/InterruptedException;
    .end local v12           #logStorageStatus:I
    :sswitch_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_e

    .line 338
    const/4 v7, 0x0

    .line 339
    .local v7, cmdSuccess:Z
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Receive stop command, stop reason="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 340
    const-string v20, "ipo_shutdown"

    move-object/from16 v0, v20

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_b

    .line 341
    const-string v20, "sys.ipo.pwrdncap"

    const-string v21, "-1"

    invoke-static/range {v20 .. v21}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    .line 342
    .local v11, ipoFlag:Ljava/lang/String;
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "IPO flag for modem log is "

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logv(Ljava/lang/String;Ljava/lang/String;)V

    .line 343
    const-string v20, "1"

    move-object/from16 v0, v20

    invoke-virtual {v0, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-nez v20, :cond_a

    const-string v20, "3"

    move-object/from16 v0, v20

    invoke-virtual {v0, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_b

    .line 344
    :cond_a
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "According to IPO flag, do not need to stop modem log when IPO shutdown"

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 349
    .end local v11           #ipoFlag:Ljava/lang/String;
    :cond_b
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "pause"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    move-result v7

    .line 350
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x1

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$902(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z

    .line 351
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->waitNextClearLogCheck:Ljava/lang/Object;

    move-object/from16 v21, v0

    monitor-enter v21

    .line 352
    :try_start_2
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->waitNextClearLogCheck:Ljava/lang/Object;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Ljava/lang/Object;->notifyAll()V

    .line 353
    monitor-exit v21
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 354
    if-nez v7, :cond_c

    .line 355
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Send stop command to native layer fail, maybe connection has already be lost."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 357
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "4"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 353
    :catchall_0
    move-exception v20

    :try_start_3
    monitor-exit v21
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    throw v20

    .line 360
    :cond_c
    const-string v18, ""

    .line 361
    .local v18, stopReason:Ljava/lang/String;
    const-string v20, "sd_timeout"

    move-object/from16 v0, v20

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_d

    .line 362
    const-string v18, "11"

    .line 364
    :cond_d
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    move-object/from16 v0, v20

    move/from16 v1, v21

    move-object/from16 v2, v18

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static {v0, v1, v2}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 367
    .end local v7           #cmdSuccess:Z
    .end local v18           #stopReason:Ljava/lang/String;
    :cond_e
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Have not connected to native layer, just ignore this command"

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 372
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "1"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 376
    :sswitch_2
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_12

    .line 377
    invoke-static {v5}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v20

    if-nez v20, :cond_11

    .line 378
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Receive config command, parameter="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 379
    const-string v20, "autostart="

    move-object/from16 v0, v20

    invoke-virtual {v5, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v20

    if-eqz v20, :cond_10

    .line 380
    const-string v20, "1"

    move-object/from16 v0, v20

    invoke-virtual {v5, v0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v20

    if-eqz v20, :cond_f

    .line 383
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "setauto,"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v22, v0

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->getCurrentMode()Ljava/lang/String;
    invoke-static/range {v22 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$600(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Ljava/lang/String;

    move-result-object v22

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    goto/16 :goto_1

    .line 387
    :cond_f
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "setauto,0"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    goto/16 :goto_1

    .line 390
    :cond_10
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Unsupported config command"

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 393
    :cond_11
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Receive config command, but parameter is null"

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 396
    :cond_12
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Fail to config native parameter because of lost connection."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 401
    :sswitch_3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-nez v20, :cond_13

    .line 402
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Reconnect to native layer fail! Mark log status as stopped."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 404
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "1"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 407
    :cond_13
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->runMonitoringLogSizeThread()V
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$800(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V

    .line 409
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Send query pause status command to modem."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 410
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "ispaused"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    goto/16 :goto_1

    .line 414
    :sswitch_4
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Receive adb command["

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    const-string v22, "]"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 415
    invoke-static {v5}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v20

    if-nez v20, :cond_4

    .line 416
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->dealWithADBCommand(Ljava/lang/String;)V
    invoke-static {v0, v5}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1000(Lcom/mediatek/mtklogger/framework/MultiModemLog;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 420
    :sswitch_5
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$102(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z

    .line 421
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x1

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mMonitorThreadStop:Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$902(Lcom/mediatek/mtklogger/framework/MultiModemLog;Z)Z

    .line 422
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->waitNextClearLogCheck:Ljava/lang/Object;

    move-object/from16 v21, v0

    monitor-enter v21

    .line 423
    :try_start_4
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->waitNextClearLogCheck:Ljava/lang/Object;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Ljava/lang/Object;->notifyAll()V

    .line 424
    monitor-exit v21
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    .line 426
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Modemlog ["

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->arg1:I

    move/from16 v22, v0

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v21

    const-string v22, "] lost, do not stop other instance, just update status as stopped"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 427
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    if-eqz v20, :cond_14

    .line 429
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->stop()V
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$1100(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;)V

    .line 430
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$002(Lcom/mediatek/mtklogger/framework/MultiModemLog;Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    .line 432
    :cond_14
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "5"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 424
    :catchall_1
    move-exception v20

    :try_start_5
    monitor-exit v21
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    throw v20

    .line 435
    :sswitch_6
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_16

    .line 436
    const/16 v20, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v21, v0

    const-string v22, "modemlog_enable"

    const/16 v23, 0x1

    invoke-interface/range {v21 .. v23}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v21

    move/from16 v0, v20

    move/from16 v1, v21

    if-ne v0, v1, :cond_15

    .line 438
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "polling"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmdToOneInstance(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$1200(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    goto/16 :goto_1

    .line 440
    :cond_15
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Modem log is stopped, no dumping is allowed."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 443
    :cond_16
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Lost connection to native, so ignore polling command."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 447
    :sswitch_7
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_17

    .line 448
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->arg1:I

    move/from16 v21, v0

    const-string v22, "resetmd"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(ILjava/lang/String;)Z
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$1300(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;ILjava/lang/String;)Z

    .line 449
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->showResetModemDialog()V
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1400(Lcom/mediatek/mtklogger/framework/MultiModemLog;)V

    goto/16 :goto_1

    .line 451
    :cond_17
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Lost connection to native, reset command will have no effect."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1

    .line 456
    :sswitch_8
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x3

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1502(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    .line 457
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCmdResHandler:Landroid/os/Handler;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1600(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/os/Handler;

    move-result-object v20

    const/16 v21, 0x3

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v22, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I
    invoke-static/range {v22 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1500(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I

    move-result v22

    invoke-static/range {v22 .. v22}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v22

    invoke-virtual/range {v20 .. v22}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Landroid/os/Message;->sendToTarget()V

    goto/16 :goto_1

    .line 461
    :sswitch_9
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1502(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    .line 462
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCmdResHandler:Landroid/os/Handler;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1600(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Landroid/os/Handler;

    move-result-object v20

    const/16 v21, 0x3

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v22, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStage:I
    invoke-static/range {v22 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$1500(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I

    move-result v22

    invoke-static/range {v22 .. v22}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v22

    invoke-virtual/range {v20 .. v22}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Landroid/os/Message;->sendToTarget()V

    .line 464
    move-object/from16 v0, p1

    iget-object v15, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 465
    .local v15, pathObj:Ljava/lang/Object;
    const-string v16, ""

    .line 466
    .local v16, pathString:Ljava/lang/String;
    if-eqz v15, :cond_18

    instance-of v0, v15, Ljava/lang/String;

    move/from16 v20, v0

    if-eqz v20, :cond_18

    move-object/from16 v16, v15

    .line 467
    check-cast v16, Ljava/lang/String;

    .line 468
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    const-string v21, "modem_exception_path"

    move-object/from16 v0, v20

    move-object/from16 v1, v21

    move-object/from16 v2, v16

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 471
    :cond_18
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->arg1:I

    move/from16 v21, v0

    move-object/from16 v0, v20

    move/from16 v1, v21

    move-object/from16 v2, v16

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->showMemoryDumpDoneDialog(ILjava/lang/String;)V
    invoke-static {v0, v1, v2}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$400(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 474
    .end local v15           #pathObj:Ljava/lang/Object;
    .end local v16           #pathString:Ljava/lang/String;
    :sswitch_a
    sget-object v20, Lcom/mediatek/mtklogger/framework/LogInstance;->mContext:Landroid/content/Context;

    const-string v21, "Log file is lost"

    const/16 v22, 0x1

    invoke-static/range {v20 .. v22}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Landroid/widget/Toast;->show()V

    .line 475
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 476
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "pause"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 477
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    const-string v21, "modemlog_enable"

    const/16 v22, 0x0

    invoke-interface/range {v20 .. v22}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 479
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, ""

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 484
    :sswitch_b
    new-instance v4, Landroid/app/AlertDialog$Builder;

    sget-object v20, Lcom/mediatek/mtklogger/framework/LogInstance;->mContext:Landroid/content/Context;

    move-object/from16 v0, v20

    invoke-direct {v4, v0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 485
    .local v4, builder:Landroid/app/AlertDialog$Builder;
    const v20, 0x7f07007b

    move/from16 v0, v20

    invoke-virtual {v4, v0}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    move-result-object v20

    const v21, 0x7f07007c

    invoke-virtual/range {v20 .. v21}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    .line 487
    const v20, 0x1040013

    new-instance v21, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler$1;

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    invoke-direct {v0, v1}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler$1;-><init>(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;)V

    move/from16 v0, v20

    move-object/from16 v1, v21

    invoke-virtual {v4, v0, v1}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 492
    invoke-virtual {v4}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v9

    .line 493
    .local v9, dialog:Landroid/app/AlertDialog;
    invoke-virtual {v9}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v20

    const/16 v21, 0x7d3

    invoke-virtual/range {v20 .. v21}, Landroid/view/Window;->setType(I)V

    .line 494
    invoke-virtual {v9}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_1

    .line 504
    .end local v4           #builder:Landroid/app/AlertDialog$Builder;
    .end local v9           #dialog:Landroid/app/AlertDialog;
    :sswitch_c
    sget-object v20, Lcom/mediatek/mtklogger/framework/LogInstance;->mContext:Landroid/content/Context;

    const-string v21, "Failed to write log file"

    const/16 v22, 0x1

    invoke-static/range {v20 .. v22}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Landroid/widget/Toast;->show()V

    .line 505
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 506
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "pause"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 507
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    const-string v21, "modemlog_enable"

    const/16 v22, 0x0

    invoke-interface/range {v20 .. v22}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 509
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, ""

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 514
    :sswitch_d
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->bConnected:Z
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$100(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 515
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mModemManager:Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$000(Lcom/mediatek/mtklogger/framework/MultiModemLog;)Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;

    move-result-object v20

    const-string v21, "pause"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->sendCmd(Ljava/lang/String;)Z
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;->access$700(Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemManager;Ljava/lang/String;)Z

    move-result v20

    if-eqz v20, :cond_4

    .line 516
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    move-object/from16 v20, v0

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    const-string v21, "modemlog_enable"

    const/16 v22, 0x0

    invoke-interface/range {v20 .. v22}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 518
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, "3"

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 524
    :sswitch_e
    move-object/from16 v0, p1

    iget v13, v0, Landroid/os/Message;->arg1:I

    .line 525
    .local v13, modemIndex:I
    move-object/from16 v0, p1

    iget-object v0, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    move-object/from16 v17, v0

    .line 526
    .local v17, statusObj:Ljava/lang/Object;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$300(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I

    move-result v20

    const/16 v21, -0x1

    move/from16 v0, v20

    move/from16 v1, v21

    if-ne v0, v1, :cond_19

    .line 527
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    #setter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I
    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$302(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    .line 529
    :cond_19
    const-string v20, "0"

    move-object/from16 v0, v20

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_1a

    .line 530
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-static {v0, v13}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$376(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    .line 537
    :goto_3
    const-string v20, "MTKLogger/MultiModemLog"

    new-instance v21, Ljava/lang/StringBuilder;

    invoke-direct/range {v21 .. v21}, Ljava/lang/StringBuilder;-><init>()V

    const-string v22, "Query MD pause status return, modemIndex="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v0, v13}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v21

    const-string v22, ", new pause status="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v21

    const-string v22, ", mCurrentStatus="

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v22, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I
    invoke-static/range {v22 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$300(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I

    move-result v22

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 539
    sget-object v20, Lcom/mediatek/mtklogger/utils/Utils;->KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP:Landroid/util/SparseArray;

    const/16 v21, 0x2

    invoke-virtual/range {v20 .. v21}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v20

    check-cast v20, Ljava/lang/String;

    const-string v21, "0"

    invoke-static/range {v20 .. v21}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    .line 542
    .local v14, nativeStatus:Ljava/lang/String;
    const-string v20, "0"

    move-object/from16 v0, v20

    invoke-virtual {v0, v14}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_1c

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$300(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I

    move-result v20

    if-lez v20, :cond_1c

    .line 544
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Modem log runing status from system property is 0, but new query value is 1, update this to user."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 546
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x1

    const-string v22, ""

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 531
    .end local v14           #nativeStatus:Ljava/lang/String;
    :cond_1a
    const-string v20, "1"

    move-object/from16 v0, v20

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_1b

    .line 532
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-static {v0, v13}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$376(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    .line 533
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    invoke-static {v0, v13}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$380(Lcom/mediatek/mtklogger/framework/MultiModemLog;I)I

    goto/16 :goto_3

    .line 535
    :cond_1b
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Invalid pause status value."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_3

    .line 548
    .restart local v14       #nativeStatus:Ljava/lang/String;
    :cond_1c
    const-string v20, "1"

    move-object/from16 v0, v20

    invoke-virtual {v0, v14}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v20

    if-eqz v20, :cond_4

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    #getter for: Lcom/mediatek/mtklogger/framework/MultiModemLog;->mCurrentStatus:I
    invoke-static/range {v20 .. v20}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$300(Lcom/mediatek/mtklogger/framework/MultiModemLog;)I

    move-result v20

    if-nez v20, :cond_4

    .line 550
    const-string v20, "MTKLogger/MultiModemLog"

    const-string v21, "Modem log runing status from system property is 1, but new query value is 0, update this to user."

    invoke-static/range {v20 .. v21}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 552
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogHandler;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    const-string v22, ""

    #calls: Lcom/mediatek/mtklogger/framework/MultiModemLog;->notifyServiceStatus(ILjava/lang/String;)V
    invoke-static/range {v20 .. v22}, Lcom/mediatek/mtklogger/framework/MultiModemLog;->access$500(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;)V

    goto/16 :goto_1

    .line 289
    nop

    :sswitch_data_0
    .sparse-switch
        0x1 -> :sswitch_0
        0x3 -> :sswitch_1
        0x7 -> :sswitch_2
        0x8 -> :sswitch_4
        0x9 -> :sswitch_3
        0x16 -> :sswitch_5
        0x33 -> :sswitch_6
        0x34 -> :sswitch_7
        0x46 -> :sswitch_8
        0x47 -> :sswitch_9
        0x48 -> :sswitch_a
        0x49 -> :sswitch_b
        0x4a -> :sswitch_c
        0x4b -> :sswitch_d
        0x4c -> :sswitch_e
    .end sparse-switch
.end method
