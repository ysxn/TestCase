.class Lcom/mediatek/mtklogger/LogFolderListActivity$5;
.super Ljava/lang/Object;
.source "LogFolderListActivity.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/LogFolderListActivity;->clearAllLogs()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/LogFolderListActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 252
    iput-object p1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 8
    .parameter "dialog"
    .parameter "whichButton"

    .prologue
    const/4 v7, 0x1

    .line 255
    iget-object v3, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #getter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mIsClearing:Z
    invoke-static {v3}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$200(Lcom/mediatek/mtklogger/LogFolderListActivity;)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 294
    :goto_0
    return-void

    .line 258
    :cond_0
    iget-object v3, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #setter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mIsClearing:Z
    invoke-static {v3, v7}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$202(Lcom/mediatek/mtklogger/LogFolderListActivity;Z)Z

    .line 259
    iget-object v3, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    iget-object v4, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    const v5, 0x7f070051

    invoke-virtual {v4, v5}, Lcom/mediatek/mtklogger/LogFolderListActivity;->getString(I)Ljava/lang/String;

    move-result-object v4

    iget-object v5, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    const v6, 0x7f070052

    invoke-virtual {v5, v6}, Lcom/mediatek/mtklogger/LogFolderListActivity;->getString(I)Ljava/lang/String;

    move-result-object v5

    const/4 v6, 0x0

    invoke-static {v3, v4, v5, v7, v6}, Landroid/app/ProgressDialog;->show(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZZ)Landroid/app/ProgressDialog;

    move-result-object v1

    .line 265
    .local v1, clearLogWaitingStopDialog:Landroid/app/ProgressDialog;
    iget-object v3, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    iget-object v4, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #getter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mLogFolderList:Ljava/util/List;
    invoke-static {v4}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$400(Lcom/mediatek/mtklogger/LogFolderListActivity;)Ljava/util/List;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    #setter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mIsClearDone:I
    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$302(Lcom/mediatek/mtklogger/LogFolderListActivity;I)I

    .line 266
    iget-object v3, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #getter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mLogFolderList:Ljava/util/List;
    invoke-static {v3}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$400(Lcom/mediatek/mtklogger/LogFolderListActivity;)Ljava/util/List;

    move-result-object v3

    invoke-interface {v3}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .local v2, i$:Ljava/util/Iterator;
    :goto_1
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mediatek/mtklogger/LogFolderListActivity$LogFileItem;

    .line 267
    .local v0, LogFileItem:Lcom/mediatek/mtklogger/LogFolderListActivity$LogFileItem;
    new-instance v3, Lcom/mediatek/mtklogger/LogFolderListActivity$5$1;

    invoke-direct {v3, p0, v0}, Lcom/mediatek/mtklogger/LogFolderListActivity$5$1;-><init>(Lcom/mediatek/mtklogger/LogFolderListActivity$5;Lcom/mediatek/mtklogger/LogFolderListActivity$LogFileItem;)V

    invoke-virtual {v3}, Lcom/mediatek/mtklogger/LogFolderListActivity$5$1;->start()V

    goto :goto_1

    .line 277
    .end local v0           #LogFileItem:Lcom/mediatek/mtklogger/LogFolderListActivity$LogFileItem;
    :cond_1
    new-instance v3, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;

    invoke-direct {v3, p0, v1}, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;-><init>(Lcom/mediatek/mtklogger/LogFolderListActivity$5;Landroid/app/ProgressDialog;)V

    invoke-virtual {v3}, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->start()V

    goto :goto_0
.end method
