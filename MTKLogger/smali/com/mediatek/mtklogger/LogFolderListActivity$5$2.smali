.class Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;
.super Ljava/lang/Thread;
.source "LogFolderListActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/LogFolderListActivity$5;->onClick(Landroid/content/DialogInterface;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/mediatek/mtklogger/LogFolderListActivity$5;

.field final synthetic val$clearLogWaitingStopDialog:Landroid/app/ProgressDialog;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/LogFolderListActivity$5;Landroid/app/ProgressDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 277
    iput-object p1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->this$1:Lcom/mediatek/mtklogger/LogFolderListActivity$5;

    iput-object p2, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->val$clearLogWaitingStopDialog:Landroid/app/ProgressDialog;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 280
    :goto_0
    iget-object v1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->this$1:Lcom/mediatek/mtklogger/LogFolderListActivity$5;

    iget-object v1, v1, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #getter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mIsClearDone:I
    invoke-static {v1}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$300(Lcom/mediatek/mtklogger/LogFolderListActivity;)I

    move-result v1

    if-lez v1, :cond_0

    .line 282
    const-wide/16 v1, 0x1f4

    :try_start_0
    invoke-static {v1, v2}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 283
    :catch_0
    move-exception v0

    .line 284
    .local v0, e:Ljava/lang/InterruptedException;
    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_0

    .line 287
    .end local v0           #e:Ljava/lang/InterruptedException;
    :cond_0
    iget-object v1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->this$1:Lcom/mediatek/mtklogger/LogFolderListActivity$5;

    iget-object v1, v1, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    const/4 v2, 0x0

    #setter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mIsClearing:Z
    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$202(Lcom/mediatek/mtklogger/LogFolderListActivity;Z)Z

    .line 288
    iget-object v1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->val$clearLogWaitingStopDialog:Landroid/app/ProgressDialog;

    if-eqz v1, :cond_1

    .line 289
    iget-object v1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->val$clearLogWaitingStopDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->cancel()V

    .line 291
    :cond_1
    iget-object v1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$5$2;->this$1:Lcom/mediatek/mtklogger/LogFolderListActivity$5;

    iget-object v1, v1, Lcom/mediatek/mtklogger/LogFolderListActivity$5;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #getter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mClearLogProgressHandler:Landroid/os/Handler;
    invoke-static {v1}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$700(Lcom/mediatek/mtklogger/LogFolderListActivity;)Landroid/os/Handler;

    move-result-object v1

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 292
    return-void
.end method
