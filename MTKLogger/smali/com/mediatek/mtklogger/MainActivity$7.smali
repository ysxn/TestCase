.class Lcom/mediatek/mtklogger/MainActivity$7;
.super Ljava/lang/Object;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/MainActivity;->setListeners()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/MainActivity;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/MainActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 408
    iput-object p1, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 11
    .parameter "v"

    .prologue
    const/4 v7, 0x0

    const/4 v8, 0x1

    .line 411
    const-string v6, "MTKLogger/MainActivity"

    const-string v9, "StartStopToggleButton button is clicked!"

    invoke-static {v6, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 412
    instance-of v6, p1, Landroid/widget/ToggleButton;

    if-eqz v6, :cond_6

    move-object v1, p1

    .line 413
    check-cast v1, Landroid/widget/ToggleButton;

    .line 414
    .local v1, button:Landroid/widget/ToggleButton;
    invoke-virtual {v1}, Landroid/widget/ToggleButton;->isChecked()Z

    move-result v3

    .line 415
    .local v3, isChecked:Z
    const-string v6, "MTKLogger/MainActivity"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "StartStopToggleButton button is checked ? "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v6, v9}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 416
    const/4 v0, 0x0

    .line 417
    .local v0, allLogType:I
    if-eqz v3, :cond_c

    .line 418
    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v6}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    .line 419
    .local v5, logType:Ljava/lang/Integer;
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1000(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;

    move-result-object v9

    sget-object v6, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v10

    invoke-virtual {v6, v10}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    invoke-interface {v9, v6, v8}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v6

    if-eqz v6, :cond_0

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v6

    :goto_1
    or-int/2addr v0, v6

    goto :goto_0

    :cond_0
    move v6, v7

    goto :goto_1

    .line 423
    .end local v5           #logType:Ljava/lang/Integer;
    :cond_1
    and-int/lit8 v6, v0, 0x1

    if-eqz v6, :cond_5

    .line 424
    const/4 v4, 0x0

    .line 425
    .local v4, isMobileLogsOn:Z
    if-nez v4, :cond_2

    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1000(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;

    move-result-object v6

    const-string v9, "mobilelog_androidlog"

    invoke-interface {v6, v9, v8}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v6

    if-eqz v6, :cond_7

    :cond_2
    move v4, v8

    .line 427
    :goto_2
    if-nez v4, :cond_3

    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1000(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;

    move-result-object v6

    const-string v9, "mobilelog_kernellog"

    invoke-interface {v6, v9, v8}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v6

    if-eqz v6, :cond_8

    :cond_3
    move v4, v8

    .line 429
    :goto_3
    if-nez v4, :cond_4

    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1000(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;

    move-result-object v6

    const-string v9, "mobilelog_btlog"

    invoke-interface {v6, v9, v8}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v6

    if-eqz v6, :cond_9

    :cond_4
    move v4, v8

    .line 431
    :goto_4
    if-nez v4, :cond_5

    .line 432
    add-int/lit8 v0, v0, -0x1

    .line 433
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    iget-object v9, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    const v10, 0x7f070011

    invoke-virtual {v9, v10}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v9

    invoke-static {v6, v9, v8}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v6

    invoke-virtual {v6}, Landroid/widget/Toast;->show()V

    .line 437
    .end local v4           #isMobileLogsOn:Z
    :cond_5
    if-nez v0, :cond_b

    .line 438
    if-nez v3, :cond_a

    move v6, v8

    :goto_5
    invoke-virtual {v1, v6}, Landroid/widget/ToggleButton;->setChecked(Z)V

    .line 439
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    iget-object v8, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    const v9, 0x7f070012

    invoke-virtual {v8, v9}, Lcom/mediatek/mtklogger/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v8

    invoke-static {v6, v8, v7}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v6

    invoke-virtual {v6}, Landroid/widget/Toast;->show()V

    .line 454
    .end local v0           #allLogType:I
    .end local v1           #button:Landroid/widget/ToggleButton;
    .end local v2           #i$:Ljava/util/Iterator;
    .end local v3           #isChecked:Z
    :cond_6
    :goto_6
    return-void

    .restart local v0       #allLogType:I
    .restart local v1       #button:Landroid/widget/ToggleButton;
    .restart local v2       #i$:Ljava/util/Iterator;
    .restart local v3       #isChecked:Z
    .restart local v4       #isMobileLogsOn:Z
    :cond_7
    move v4, v7

    .line 425
    goto :goto_2

    :cond_8
    move v4, v7

    .line 427
    goto :goto_3

    :cond_9
    move v4, v7

    .line 429
    goto :goto_4

    .end local v4           #isMobileLogsOn:Z
    :cond_a
    move v6, v7

    .line 438
    goto :goto_5

    .line 442
    :cond_b
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1100(Lcom/mediatek/mtklogger/MainActivity;)Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    move-result-object v6

    invoke-virtual {v6, v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->startLog(I)Z

    .line 450
    :goto_7
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->changeWaitingDialog()V
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$300(Lcom/mediatek/mtklogger/MainActivity;)V

    .line 451
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mMessageHandler:Landroid/os/Handler;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$400(Lcom/mediatek/mtklogger/MainActivity;)Landroid/os/Handler;

    move-result-object v6

    iget-object v9, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mMessageHandler:Landroid/os/Handler;
    invoke-static {v9}, Lcom/mediatek/mtklogger/MainActivity;->access$400(Lcom/mediatek/mtklogger/MainActivity;)Landroid/os/Handler;

    move-result-object v9

    const/4 v10, 0x4

    if-eqz v3, :cond_f

    :goto_8
    invoke-virtual {v9, v10, v8, v7}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v7

    const-wide/16 v8, 0x190

    invoke-virtual {v6, v7, v8, v9}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    goto :goto_6

    .line 444
    .end local v2           #i$:Ljava/util/Iterator;
    :cond_c
    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->LOG_TYPE_SET:Ljava/util/Set;

    invoke-interface {v6}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .restart local v2       #i$:Ljava/util/Iterator;
    :goto_9
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_e

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    .line 445
    .restart local v5       #logType:Ljava/lang/Integer;
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1200(Lcom/mediatek/mtklogger/MainActivity;)Landroid/content/SharedPreferences;

    move-result-object v9

    sget-object v6, Lcom/mediatek/mtklogger/utils/Utils;->KEY_STATUS_MAP:Landroid/util/SparseArray;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v10

    invoke-virtual {v6, v10}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/String;

    invoke-interface {v9, v6, v8}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v6

    if-ne v8, v6, :cond_d

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v6

    :goto_a
    or-int/2addr v0, v6

    goto :goto_9

    :cond_d
    move v6, v7

    goto :goto_a

    .line 448
    .end local v5           #logType:Ljava/lang/Integer;
    :cond_e
    iget-object v6, p0, Lcom/mediatek/mtklogger/MainActivity$7;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;
    invoke-static {v6}, Lcom/mediatek/mtklogger/MainActivity;->access$1100(Lcom/mediatek/mtklogger/MainActivity;)Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    move-result-object v6

    invoke-virtual {v6, v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->stopLog(I)Z

    goto :goto_7

    :cond_f
    move v8, v7

    .line 451
    goto :goto_8
.end method
