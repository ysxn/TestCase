.class Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;
.super Ljava/lang/Thread;
.source "TagLogManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/taglog/TagLogManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "TagLogThread"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;


# direct methods
.method private constructor <init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V
    .locals 0
    .parameter

    .prologue
    .line 452
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;Lcom/mediatek/mtklogger/taglog/TagLogManager$1;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 452
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;-><init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 37

    .prologue
    .line 456
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "-->begin tag log compressing thread"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 458
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "tag_log_compressing"

    const/16 v33, 0x1

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 460
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/Context;

    move-result-object v31

    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v24

    .line 461
    .local v24, sDCardPath:Ljava/lang/String;
    invoke-static/range {v24 .. v24}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->checkSdCardSpace(Ljava/lang/String;)I

    move-result v25

    .line 462
    .local v25, sDCardSpaceStatus:I
    const/16 v31, 0x191

    move/from16 v0, v25

    move/from16 v1, v31

    if-eq v0, v1, :cond_0

    .line 463
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    move-result-object v31

    move-object/from16 v0, v31

    move/from16 v1, v25

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    .line 464
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "tag_log_compressing"

    const/16 v33, 0x0

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 649
    :goto_0
    return-void

    .line 468
    :cond_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->getLogPath()Landroid/util/SparseArray;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1900(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/util/SparseArray;

    move-result-object v21

    .line 469
    .local v21, logToolPath:Landroid/util/SparseArray;,"Landroid/util/SparseArray<Ljava/lang/String;>;"
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    new-instance v32, Ljava/util/ArrayList;

    invoke-direct/range {v32 .. v32}, Ljava/util/ArrayList;-><init>()V

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2002(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/util/List;)Ljava/util/List;

    .line 471
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-eqz v31, :cond_5

    .line 472
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    const-string v32, "modem_exception_path"

    const-string v33, ""

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    .line 473
    .local v10, eeModemLogPath:Ljava/lang/String;
    if-eqz v10, :cond_2

    const-string v31, ""

    move-object/from16 v0, v31

    invoke-virtual {v0, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v31

    if-nez v31, :cond_2

    const/16 v16, 0x1

    .line 474
    .local v16, isModemLogReady:Z
    :goto_1
    const/4 v11, 0x0

    .line 475
    .local v11, i:I
    :goto_2
    if-nez v16, :cond_4

    .line 477
    const-wide/16 v31, 0x3e8

    :try_start_0
    invoke-static/range {v31 .. v32}, Ljava/lang/Thread;->sleep(J)V

    .line 478
    add-int/lit8 v11, v11, 0x1

    .line 479
    const/16 v31, 0x5

    move/from16 v0, v31

    if-ne v11, v0, :cond_1

    .line 480
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Modem Log is not Ready , wait for 5s"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 481
    const/4 v11, 0x0

    .line 483
    :cond_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    const-string v32, "modem_exception_path"

    const-string v33, ""

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v10

    .line 484
    if-eqz v10, :cond_3

    const-string v31, ""

    move-object/from16 v0, v31

    invoke-virtual {v0, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v31

    if-nez v31, :cond_3

    const/16 v16, 0x1

    :goto_3
    goto :goto_2

    .line 473
    .end local v11           #i:I
    .end local v16           #isModemLogReady:Z
    :cond_2
    const/16 v16, 0x0

    goto :goto_1

    .line 484
    .restart local v11       #i:I
    .restart local v16       #isModemLogReady:Z
    :cond_3
    const/16 v16, 0x0

    goto :goto_3

    .line 485
    :catch_0
    move-exception v9

    .line 486
    .local v9, e:Ljava/lang/InterruptedException;
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Catch InterruptedException"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 489
    .end local v9           #e:Ljava/lang/InterruptedException;
    :cond_4
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "modem_exception_path"

    const-string v33, ""

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 490
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "MODEM_EXCEPTION_PATH : "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    invoke-virtual {v0, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 491
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_5

    .line 492
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v31

    new-instance v32, Lcom/mediatek/mtklogger/taglog/LogInformation;

    const/16 v33, 0x2

    new-instance v34, Ljava/io/File;

    move-object/from16 v0, v34

    invoke-direct {v0, v10}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-direct/range {v32 .. v34}, Lcom/mediatek/mtklogger/taglog/LogInformation;-><init>(ILjava/io/File;)V

    invoke-interface/range {v31 .. v32}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 495
    .end local v10           #eeModemLogPath:Ljava/lang/String;
    .end local v11           #i:I
    .end local v16           #isModemLogReady:Z
    :cond_5
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->initToolStatus()V
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V

    .line 496
    sget-object v31, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface/range {v31 .. v31}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v12

    .local v12, i$:Ljava/util/Iterator;
    :cond_6
    :goto_4
    invoke-interface {v12}, Ljava/util/Iterator;->hasNext()Z

    move-result v31

    if-eqz v31, :cond_f

    invoke-interface {v12}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v22

    check-cast v22, Ljava/lang/Integer;

    .line 509
    .local v22, logType:Ljava/lang/Integer;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogToolStatus:Landroid/util/SparseArray;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$900(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/util/SparseArray;

    move-result-object v31

    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v32

    invoke-virtual/range {v31 .. v32}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v31

    check-cast v31, Ljava/lang/Boolean;

    invoke-virtual/range {v31 .. v31}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v31

    if-eqz v31, :cond_6

    .line 510
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "Type of "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v32

    const-string v33, " log is running!"

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 511
    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v31

    const/16 v32, 0x2

    move/from16 v0, v31

    move/from16 v1, v32

    if-ne v0, v1, :cond_e

    .line 512
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_6

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_6

    .line 515
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Modem log is running!"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 516
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    move-result-object v31

    const/16 v32, 0x2

    invoke-virtual/range {v31 .. v32}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstanceRunningStatus(I)I

    move-result v7

    .line 517
    .local v7, currentRunningStage:I
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "modemLogRunningDetailStatus : "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 518
    const/4 v11, 0x0

    .line 519
    .restart local v11       #i:I
    :cond_7
    :goto_5
    const/16 v31, -0x1

    move/from16 v0, v31

    if-ne v7, v0, :cond_8

    .line 521
    const-wide/16 v31, 0x1f4

    :try_start_1
    invoke-static/range {v31 .. v32}, Ljava/lang/Thread;->sleep(J)V

    .line 522
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mLogService:Lcom/mediatek/mtklogger/framework/MTKLoggerService;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2200(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    move-result-object v31

    const/16 v32, 0x2

    invoke-virtual/range {v31 .. v32}, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->getLogInstanceRunningStatus(I)I
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_1

    move-result v7

    .line 523
    add-int/lit8 v11, v11, 0x1

    .line 524
    const/16 v31, 0xa

    move/from16 v0, v31

    if-ne v11, v0, :cond_7

    .line 531
    :cond_8
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "After wait for "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    int-to-double v0, v11

    move-wide/from16 v33, v0

    const-wide/high16 v35, 0x3fe0

    mul-double v33, v33, v35

    invoke-virtual/range {v32 .. v34}, Ljava/lang/StringBuilder;->append(D)Ljava/lang/StringBuilder;

    move-result-object v32

    const-string v33, "s, modemLogRunningDetailStatus : "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 533
    const/16 v31, 0x1

    move/from16 v0, v31

    if-eq v7, v0, :cond_9

    const/16 v31, 0x3

    move/from16 v0, v31

    if-ne v7, v0, :cond_c

    :cond_9
    const/4 v14, 0x1

    .line 535
    .local v14, isModem1Running:Z
    :goto_6
    if-eqz v14, :cond_a

    .line 536
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Modem one is running!"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 537
    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v31

    move-object/from16 v0, v21

    move/from16 v1, v31

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v31

    check-cast v31, Ljava/lang/String;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v32, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;Z)Ljava/io/File;

    move-result-object v18

    .line 538
    .local v18, logFile:Ljava/io/File;
    if-eqz v18, :cond_a

    invoke-virtual/range {v18 .. v18}, Ljava/io/File;->exists()Z

    move-result v31

    if-eqz v31, :cond_a

    .line 539
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v31

    new-instance v32, Lcom/mediatek/mtklogger/taglog/LogInformation;

    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v33

    move-object/from16 v0, v32

    move/from16 v1, v33

    move-object/from16 v2, v18

    invoke-direct {v0, v1, v2}, Lcom/mediatek/mtklogger/taglog/LogInformation;-><init>(ILjava/io/File;)V

    invoke-interface/range {v31 .. v32}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 542
    .end local v18           #logFile:Ljava/io/File;
    :cond_a
    const/16 v31, 0x2

    move/from16 v0, v31

    if-eq v7, v0, :cond_b

    const/16 v31, 0x3

    move/from16 v0, v31

    if-ne v7, v0, :cond_d

    :cond_b
    const/4 v15, 0x1

    .line 544
    .local v15, isModem2Running:Z
    :goto_7
    if-eqz v15, :cond_6

    .line 545
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Modem two is running!"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 546
    new-instance v31, Ljava/lang/StringBuilder;

    invoke-direct/range {v31 .. v31}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v32, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mContext:Landroid/content/Context;
    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/Context;

    move-result-object v32

    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v32

    invoke-virtual/range {v31 .. v32}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v31

    const-string v32, "/mtklog/"

    invoke-virtual/range {v31 .. v32}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v31

    const-string v32, "dualmdlog"

    invoke-virtual/range {v31 .. v32}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v31

    invoke-virtual/range {v31 .. v31}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 548
    .local v8, dualModemPath:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    move/from16 v0, v31

    invoke-static {v8, v0}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;Z)Ljava/io/File;

    move-result-object v18

    .line 549
    .restart local v18       #logFile:Ljava/io/File;
    if-eqz v18, :cond_6

    invoke-virtual/range {v18 .. v18}, Ljava/io/File;->exists()Z

    move-result v31

    if-eqz v31, :cond_6

    .line 550
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v31

    new-instance v32, Lcom/mediatek/mtklogger/taglog/LogInformation;

    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v33

    move-object/from16 v0, v32

    move/from16 v1, v33

    move-object/from16 v2, v18

    invoke-direct {v0, v1, v2}, Lcom/mediatek/mtklogger/taglog/LogInformation;-><init>(ILjava/io/File;)V

    invoke-interface/range {v31 .. v32}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto/16 :goto_4

    .line 527
    .end local v8           #dualModemPath:Ljava/lang/String;
    .end local v14           #isModem1Running:Z
    .end local v15           #isModem2Running:Z
    .end local v18           #logFile:Ljava/io/File;
    :catch_1
    move-exception v9

    .line 528
    .restart local v9       #e:Ljava/lang/InterruptedException;
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Catch InterruptedException"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_5

    .line 533
    .end local v9           #e:Ljava/lang/InterruptedException;
    :cond_c
    const/4 v14, 0x0

    goto/16 :goto_6

    .line 542
    .restart local v14       #isModem1Running:Z
    :cond_d
    const/4 v15, 0x0

    goto :goto_7

    .line 555
    .end local v7           #currentRunningStage:I
    .end local v11           #i:I
    .end local v14           #isModem1Running:Z
    :cond_e
    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v31

    move-object/from16 v0, v21

    move/from16 v1, v31

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v31

    check-cast v31, Ljava/lang/String;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v32, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getCurrentLogFolderFromPath(Ljava/lang/String;Z)Ljava/io/File;

    move-result-object v18

    .line 556
    .restart local v18       #logFile:Ljava/io/File;
    if-eqz v18, :cond_6

    invoke-virtual/range {v18 .. v18}, Ljava/io/File;->exists()Z

    move-result v31

    if-eqz v31, :cond_6

    .line 557
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v31

    new-instance v32, Lcom/mediatek/mtklogger/taglog/LogInformation;

    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v33

    move-object/from16 v0, v32

    move/from16 v1, v33

    move-object/from16 v2, v18

    invoke-direct {v0, v1, v2}, Lcom/mediatek/mtklogger/taglog/LogInformation;-><init>(ILjava/io/File;)V

    invoke-interface/range {v31 .. v32}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto/16 :goto_4

    .line 562
    .end local v18           #logFile:Ljava/io/File;
    .end local v22           #logType:Ljava/lang/Integer;
    :cond_f
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    const/16 v32, 0x0

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I
    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2302(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)I

    .line 563
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v12

    :goto_8
    invoke-interface {v12}, Ljava/util/Iterator;->hasNext()Z

    move-result v31

    if-eqz v31, :cond_10

    invoke-interface {v12}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v20

    check-cast v20, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 564
    .local v20, logInformation:Lcom/mediatek/mtklogger/taglog/LogInformation;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    invoke-virtual/range {v20 .. v20}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogFilesCount()I

    move-result v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2312(Lcom/mediatek/mtklogger/taglog/TagLogManager;I)I

    goto :goto_8

    .line 566
    .end local v20           #logInformation:Lcom/mediatek/mtklogger/taglog/LogInformation;
    :cond_10
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "Total taglog files count is : "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v33, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I
    invoke-static/range {v33 .. v33}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2300(Lcom/mediatek/mtklogger/taglog/TagLogManager;)I

    move-result v33

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 567
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2400(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/app/ProgressDialog;

    move-result-object v31

    if-eqz v31, :cond_11

    .line 568
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mProgressDialog:Landroid/app/ProgressDialog;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2400(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/app/ProgressDialog;

    move-result-object v31

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v32, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTotalFilesCount:I
    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2300(Lcom/mediatek/mtklogger/taglog/TagLogManager;)I

    move-result v32

    invoke-virtual/range {v31 .. v32}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 571
    :cond_11
    new-instance v28, Landroid/os/StatFs;

    move-object/from16 v0, v28

    move-object/from16 v1, v24

    invoke-direct {v0, v1}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    .line 572
    .local v28, stat:Landroid/os/StatFs;
    invoke-virtual/range {v28 .. v28}, Landroid/os/StatFs;->getAvailableBlocks()I

    move-result v3

    .line 573
    .local v3, availableBlocks:I
    invoke-virtual/range {v28 .. v28}, Landroid/os/StatFs;->getBlockSize()I

    move-result v4

    .line 574
    .local v4, blockSize:I
    int-to-long v0, v3

    move-wide/from16 v31, v0

    int-to-long v0, v4

    move-wide/from16 v33, v0

    mul-long v26, v31, v33

    .line 575
    .local v26, sdAvailableSpace:J
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v32, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v32

    invoke-static/range {v32 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->getTagLogNeededSize(Ljava/util/List;)J

    move-result-wide v32

    sub-long v32, v32, v26

    move-wide/from16 v0, v32

    move-object/from16 v2, v31

    iput-wide v0, v2, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mNeedMoreSpace:J

    .line 576
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    move-object/from16 v0, v31

    iget-wide v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager;->mNeedMoreSpace:J

    move-wide/from16 v31, v0

    const-wide/16 v33, 0x0

    cmp-long v31, v31, v33

    if-lez v31, :cond_12

    .line 577
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mUIHandler:Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;

    move-result-object v31

    const/16 v32, 0x192

    invoke-virtual/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager$UIHandler;->sendEmptyMessage(I)Z

    .line 578
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "tag_log_compressing"

    const/16 v33, 0x0

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto/16 :goto_0

    .line 582
    :cond_12
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_13

    .line 583
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    const/16 v32, 0x0

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->startOrStopAllLogTool(Z)V
    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2500(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    .line 587
    :cond_13
    const-wide/16 v5, 0x0

    .line 588
    .local v5, currentLogSize:J
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mCurrentLogFolderList:Ljava/util/List;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/util/List;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v12

    :goto_9
    invoke-interface {v12}, Ljava/util/Iterator;->hasNext()Z

    move-result v31

    if-eqz v31, :cond_14

    invoke-interface {v12}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v19

    check-cast v19, Lcom/mediatek/mtklogger/taglog/LogInformation;

    .line 589
    .local v19, logInfo:Lcom/mediatek/mtklogger/taglog/LogInformation;
    invoke-virtual/range {v19 .. v19}, Lcom/mediatek/mtklogger/taglog/LogInformation;->getLogSize()J

    move-result-wide v31

    add-long v5, v5, v31

    goto :goto_9

    .line 591
    .end local v19           #logInfo:Lcom/mediatek/mtklogger/taglog/LogInformation;
    :cond_14
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "tag_log_ongoing"

    const-wide/16 v33, 0x400

    div-long v33, v5, v33

    const-wide/16 v35, 0x400

    div-long v33, v33, v35

    const-wide/16 v35, 0x1

    div-long v33, v33, v35

    move-wide/from16 v0, v33

    long-to-int v0, v0

    move/from16 v33, v0

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 594
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsFromReboot:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$700(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_1c

    .line 595
    const/4 v13, 0x0

    .line 596
    .local v13, isAllStop:Z
    const/16 v30, 0x0

    .line 597
    .local v30, timer:I
    :cond_15
    :goto_a
    if-nez v13, :cond_1b

    .line 598
    move/from16 v0, v30

    add-int/lit16 v0, v0, 0x1f4

    move/from16 v30, v0

    .line 599
    const/16 v31, 0x3e80

    move/from16 v0, v30

    move/from16 v1, v31

    if-lt v0, v1, :cond_16

    .line 600
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "Waiting log stopped timeout"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 601
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "tag_log_compressing"

    const/16 v33, 0x0

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto/16 :goto_0

    .line 605
    :cond_16
    const-wide/16 v31, 0x1f4

    :try_start_2
    invoke-static/range {v31 .. v32}, Ljava/lang/Thread;->sleep(J)V
    :try_end_2
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_2} :catch_2

    .line 610
    :goto_b
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsWaitingLogStateChange:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$000(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_15

    .line 611
    sget-object v31, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface/range {v31 .. v31}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v12

    :cond_17
    :goto_c
    invoke-interface {v12}, Ljava/util/Iterator;->hasNext()Z

    move-result v31

    if-eqz v31, :cond_15

    invoke-interface {v12}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v22

    check-cast v22, Ljava/lang/Integer;

    .line 612
    .restart local v22       #logType:Ljava/lang/Integer;
    const/16 v32, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v33

    sget-object v31, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v34

    move-object/from16 v0, v31

    move/from16 v1, v34

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v31

    check-cast v31, Ljava/lang/String;

    const/16 v34, 0x0

    move-object/from16 v0, v33

    move-object/from16 v1, v31

    move/from16 v2, v34

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v31

    move/from16 v0, v32

    move/from16 v1, v31

    if-ne v0, v1, :cond_19

    .line 614
    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v31

    const/16 v32, 0x2

    move/from16 v0, v31

    move/from16 v1, v32

    if-ne v0, v1, :cond_18

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsModemExp:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_17

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIgnoreMdLog:Z
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2100(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Z

    move-result v31

    if-nez v31, :cond_17

    .line 617
    :cond_18
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "The type "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v32

    const-string v33, " log is not stopped!"

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 619
    const/4 v13, 0x0

    .line 620
    goto/16 :goto_a

    .line 606
    .end local v22           #logType:Ljava/lang/Integer;
    :catch_2
    move-exception v9

    .line 607
    .restart local v9       #e:Ljava/lang/InterruptedException;
    invoke-virtual {v9}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto/16 :goto_b

    .line 622
    .end local v9           #e:Ljava/lang/InterruptedException;
    .restart local v22       #logType:Ljava/lang/Integer;
    :cond_19
    sget-object v31, Lcom/mediatek/mtklogger/utils/Utils;->KEY_LOG_RUNNING_STATUS_IN_SYSPROP_MAP:Landroid/util/SparseArray;

    invoke-virtual/range {v22 .. v22}, Ljava/lang/Integer;->intValue()I

    move-result v32

    invoke-virtual/range {v31 .. v32}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v17

    check-cast v17, Ljava/lang/String;

    .line 623
    .local v17, key:Ljava/lang/String;
    const-string v31, "0"

    move-object/from16 v0, v17

    move-object/from16 v1, v31

    invoke-static {v0, v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v23

    .line 624
    .local v23, nativeStatus:Ljava/lang/String;
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, " Native log("

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v32

    const-string v33, ") running status="

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 625
    const-string v31, "1"

    move-object/from16 v0, v31

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v31

    if-eqz v31, :cond_1a

    .line 626
    const/4 v13, 0x0

    .line 627
    goto/16 :goto_a

    .line 629
    :cond_1a
    const-string v31, "MTKLogger/TagLogManager"

    new-instance v32, Ljava/lang/StringBuilder;

    invoke-direct/range {v32 .. v32}, Ljava/lang/StringBuilder;-><init>()V

    const-string v33, "The type "

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    move-object/from16 v0, v32

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v32

    const-string v33, " log is stopped!"

    invoke-virtual/range {v32 .. v33}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v32

    invoke-virtual/range {v32 .. v32}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v32

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 631
    const/4 v13, 0x1

    .line 632
    goto/16 :goto_c

    .line 635
    .end local v17           #key:Ljava/lang/String;
    .end local v22           #logType:Ljava/lang/Integer;
    .end local v23           #nativeStatus:Ljava/lang/String;
    :cond_1b
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    const/16 v32, 0x1

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->startOrStopAllLogTool(Z)V
    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2500(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    .line 638
    .end local v13           #isAllStop:Z
    .end local v30           #timer:I
    :cond_1c
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTag:Ljava/lang/String;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2600(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Ljava/lang/String;

    move-result-object v31

    move-object/from16 v0, v24

    move-object/from16 v1, v31

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogUtils;->createTagLogFolder(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v29

    .line 642
    .local v29, tagLogFolderName:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    move-object/from16 v0, v31

    move-object/from16 v1, v29

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->rememberCurrentTaggingLogFolder(Ljava/lang/String;)V
    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2700(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)V

    .line 644
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    move-object/from16 v0, v31

    move-object/from16 v1, v29

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->tagSelectedLogFolder(Ljava/lang/String;)V
    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$2800(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)V

    .line 646
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/mediatek/mtklogger/taglog/TagLogManager$TagLogThread;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    move-object/from16 v31, v0

    #getter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static/range {v31 .. v31}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1800(Lcom/mediatek/mtklogger/taglog/TagLogManager;)Landroid/content/SharedPreferences;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    const-string v32, "tag_log_compressing"

    const/16 v33, 0x0

    invoke-interface/range {v31 .. v33}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v31

    invoke-interface/range {v31 .. v31}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 648
    const-string v31, "MTKLogger/TagLogManager"

    const-string v32, "<--tag log compressing thread end"

    invoke-static/range {v31 .. v32}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_0
.end method
