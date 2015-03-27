.class Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
.super Landroid/os/Handler;
.source "TagLogManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/taglog/TagLogManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "UIHandler"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;


# direct methods
.method private constructor <init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V
    .locals 0
    .parameter

    .prologue
    .line 307
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;Lcom/mediatek/mtklogger/taglog/TagLogManager$1;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 307
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 19
    .parameter "msg"

    .prologue
    .line 310
    invoke-super/range {p0 .. p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 311
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, " UIHandler handleMessage, what="

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->what:I

    move/from16 v17, v0

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v16

    const-string v17, ", thread="

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v17

    invoke-virtual/range {v17 .. v17}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v17

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 313
    move-object/from16 v0, p1

    iget v15, v0, Landroid/os/Message;->what:I

    sparse-switch v15, :sswitch_data_0

    .line 445
    :cond_0
    :goto_0
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, " MyHandler handleMessage --> end "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, p1

    iget v0, v0, Landroid/os/Message;->what:I

    move/from16 v17, v0

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 446
    :goto_1
    return-void

    .line 316
    :sswitch_0
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/os/Bundle;

    move-result-object v15

    const-string v16, "path"

    invoke-virtual/range {v15 .. v16}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 317
    .local v7, expPath:Ljava/lang/String;
    if-nez v7, :cond_1

    .line 318
    const-string v15, "MTKLogger/TagLogManager"

    const-string v16, "params are not valid! exp_path is null"

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 319
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x0

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    goto :goto_1

    .line 323
    :cond_1
    const-string v15, "SaveLogManually"

    invoke-virtual {v15, v7}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v15

    if-eqz v15, :cond_4

    .line 324
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x1

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z

    .line 376
    :cond_2
    :goto_2
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v15

    if-eqz v15, :cond_3

    .line 378
    const-wide/16 v15, 0x1388

    :try_start_0
    invoke-static/range {v15 .. v16}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_1

    .line 383
    :cond_3
    :goto_3
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->initToolStatus()V
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    .line 384
    const/4 v1, 0x0

    .line 385
    .local v1, allLogType:I
    sget-object v15, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v15}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v10

    .local v10, i$:Ljava/util/Iterator;
    :goto_4
    invoke-interface {v10}, Ljava/util/Iterator;->hasNext()Z

    move-result v15

    if-eqz v15, :cond_c

    invoke-interface {v10}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/Integer;

    .line 386
    .local v12, logType:Ljava/lang/Integer;
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogToolStatus:Landroid/util/SparseArray;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$900(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/util/SparseArray;

    move-result-object v15

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v16

    const/16 v17, 0x0

    invoke-static/range {v17 .. v17}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v17

    invoke-virtual/range {v15 .. v17}, Landroid/util/SparseArray;->get(ILjava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    check-cast v15, Ljava/lang/Boolean;

    invoke-virtual {v15}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v15

    if-eqz v15, :cond_b

    invoke-virtual {v12}, Ljava/lang/Integer;->intValue()I

    move-result v15

    :goto_5
    or-int/2addr v1, v15

    goto :goto_4

    .line 326
    .end local v1           #allLogType:I
    .end local v10           #i$:Ljava/util/Iterator;
    .end local v12           #logType:Ljava/lang/Integer;
    :cond_4
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/os/Bundle;

    move-result-object v15

    const-string v16, "from_reboot"

    invoke-virtual/range {v15 .. v16}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    .line 327
    .local v11, isFromReboot:Ljava/lang/String;
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "isFromReboot ? "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 330
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x0

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z

    .line 331
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/os/Bundle;

    move-result-object v15

    const-string v16, "db_filename"

    invoke-virtual/range {v15 .. v16}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 332
    .local v4, expFileName:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDataFromExtras:Landroid/os/Bundle;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/os/Bundle;

    move-result-object v15

    const-string v16, "zz_filename"

    invoke-virtual/range {v15 .. v16}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v13

    .line 333
    .local v13, zzFileName:Ljava/lang/String;
    if-eqz v4, :cond_5

    if-nez v13, :cond_6

    .line 334
    :cond_5
    const-string v15, "MTKLogger/TagLogManager"

    const-string v16, "params are not valid! exp_file name is null"

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 335
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x0

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    goto/16 :goto_1

    .line 338
    :cond_6
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mDbPathFromAee:Ljava/lang/String;
    invoke-static {v15, v7}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$502(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;

    .line 339
    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v15, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    .line 340
    .local v14, zzFilePath:Ljava/lang/String;
    new-instance v6, Lcom/mediatek/mtklogger/utils/ExceptionInfo;

    invoke-direct {v6}, Lcom/mediatek/mtklogger/utils/ExceptionInfo;-><init>()V

    .line 341
    .local v6, expInfo:Lcom/mediatek/mtklogger/utils/ExceptionInfo;
    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v15, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 342
    .local v5, expFilePath:Ljava/lang/String;
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "exp_path: "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 343
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "exp_file: "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 344
    invoke-virtual {v6, v5}, Lcom/mediatek/mtklogger/utils/ExceptionInfo;->setmPath(Ljava/lang/String;)V

    .line 346
    :try_start_1
    invoke-virtual {v6, v14}, Lcom/mediatek/mtklogger/utils/ExceptionInfo;->initFieldsFromZZ(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    .line 353
    invoke-virtual {v6}, Lcom/mediatek/mtklogger/utils/ExceptionInfo;->getmType()Ljava/lang/String;

    move-result-object v9

    .line 354
    .local v9, expType:Ljava/lang/String;
    invoke-virtual {v6}, Lcom/mediatek/mtklogger/utils/ExceptionInfo;->getmDiscription()Ljava/lang/String;

    move-result-object v3

    .line 355
    .local v3, expDiscription:Ljava/lang/String;
    invoke-virtual {v6}, Lcom/mediatek/mtklogger/utils/ExceptionInfo;->getmProcess()Ljava/lang/String;

    move-result-object v8

    .line 356
    .local v8, expProcess:Ljava/lang/String;
    if-eqz v9, :cond_7

    .line 357
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "exp_info.getmType(): "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 359
    :cond_7
    if-eqz v3, :cond_8

    .line 360
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "exp_info.getmDiscription(): "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 362
    :cond_8
    if-eqz v8, :cond_9

    .line 363
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "exp_info.getmProcess(): "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 366
    :cond_9
    if-eqz v9, :cond_a

    const-string v15, "Externel (EE)"

    invoke-virtual {v9, v15}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v15

    if-eqz v15, :cond_a

    .line 367
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "expType == External (EE) "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 368
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x1

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$602(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z

    .line 370
    :cond_a
    if-eqz v9, :cond_2

    const-string v15, "Kernel (KE)"

    invoke-virtual {v9, v15}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v15

    if-eqz v15, :cond_2

    .line 371
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "expType == Kernel (KE) "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 372
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x1

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$702(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z

    goto/16 :goto_2

    .line 347
    .end local v3           #expDiscription:Ljava/lang/String;
    .end local v8           #expProcess:Ljava/lang/String;
    .end local v9           #expType:Ljava/lang/String;
    :catch_0
    move-exception v2

    .line 348
    .local v2, e:Ljava/io/IOException;
    const-string v15, "MTKLogger/TagLogManager"

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string v17, "fail to init exception info:"

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual {v2}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v17

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 349
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x0

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    goto/16 :goto_1

    .line 379
    .end local v2           #e:Ljava/io/IOException;
    .end local v4           #expFileName:Ljava/lang/String;
    .end local v5           #expFilePath:Ljava/lang/String;
    .end local v6           #expInfo:Lcom/mediatek/mtklogger/utils/ExceptionInfo;
    .end local v11           #isFromReboot:Ljava/lang/String;
    .end local v13           #zzFileName:Ljava/lang/String;
    .end local v14           #zzFilePath:Ljava/lang/String;
    :catch_1
    move-exception v2

    .line 380
    .local v2, e:Ljava/lang/InterruptedException;
    invoke-virtual {v2}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto/16 :goto_3

    .line 386
    .end local v2           #e:Ljava/lang/InterruptedException;
    .restart local v1       #allLogType:I
    .restart local v10       #i$:Ljava/util/Iterator;
    .restart local v12       #logType:Ljava/lang/Integer;
    :cond_b
    const/4 v15, 0x0

    goto/16 :goto_5

    .line 390
    .end local v12           #logType:Ljava/lang/Integer;
    :cond_c
    if-nez v1, :cond_d

    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v15

    if-nez v15, :cond_d

    .line 391
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x12e

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    goto/16 :goto_1

    .line 395
    :cond_d
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mManualSaveLog:Z
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$400(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v15

    if-nez v15, :cond_e

    .line 396
    invoke-static {}, Lcom/mediatek/mtklogger/taglog/ZipManager;->initZippedFilesCount()V

    .line 397
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    move-result-object v15

    const/16 v16, 0x132

    invoke-virtual/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    .line 399
    new-instance v15, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v16, v0

    const/16 v17, 0x0

    invoke-direct/range {v15 .. v17}, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;Lcom/mediatek/mtklogger/taglog/TagLogManager$1;)V

    invoke-virtual {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->start()V

    goto/16 :goto_0

    .line 401
    :cond_e
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x12d

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    .line 403
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    move-result-object v15

    const/16 v16, 0xd1

    const-wide/32 v17, 0x1d4c0

    invoke-virtual/range {v15 .. v18}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessageDelayed(IJ)Z

    goto/16 :goto_0

    .line 409
    .end local v1           #allLogType:I
    .end local v7           #expPath:Ljava/lang/String;
    .end local v10           #i$:Ljava/util/Iterator;
    :sswitch_1
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsInputDialogClicked:Z
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1300(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v15

    if-nez v15, :cond_0

    .line 410
    const-string v15, "MTKLogger/TagLogManager"

    const-string v16, "time out"

    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 411
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const-string v16, "Cancel"

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;

    .line 412
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x1

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    goto/16 :goto_0

    .line 416
    :sswitch_2
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x12f

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    goto/16 :goto_0

    .line 419
    :sswitch_3
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1500(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    .line 420
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x193

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    goto/16 :goto_0

    .line 423
    :sswitch_4
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1500(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    .line 424
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x194

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    goto/16 :goto_0

    .line 427
    :sswitch_5
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x12e

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    goto/16 :goto_0

    .line 430
    :sswitch_6
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1500(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    .line 431
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const-string v16, "Successful"

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;

    .line 432
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/Context;

    move-result-object v15

    const v16, 0x7f070074

    const/16 v17, 0x0

    invoke-static/range {v15 .. v17}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v15

    invoke-virtual {v15}, Landroid/widget/Toast;->show()V

    .line 433
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x1

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    goto/16 :goto_0

    .line 436
    :sswitch_7
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->dismissProgressDialog()V
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1500(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    .line 437
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const/16 v16, 0x131

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
    invoke-static/range {v15 .. v16}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1000(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)V

    goto/16 :goto_0

    .line 440
    :sswitch_8
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->createProgressDialog()V
    invoke-static {v15}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    goto/16 :goto_0

    .line 313
    nop

    :sswitch_data_0
    .sparse-switch
        0xcb -> :sswitch_0
        0xcd -> :sswitch_5
        0xce -> :sswitch_6
        0xcf -> :sswitch_7
        0xd1 -> :sswitch_1
        0x132 -> :sswitch_8
        0x192 -> :sswitch_2
        0x193 -> :sswitch_3
        0x194 -> :sswitch_4
    .end sparse-switch
.end method
