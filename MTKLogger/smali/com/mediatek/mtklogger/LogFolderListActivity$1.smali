.class Lcom/mediatek/mtklogger/LogFolderListActivity$1;
.super Landroid/os/Handler;
.source "LogFolderListActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/LogFolderListActivity;
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
    .line 91
    iput-object p1, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$1;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .parameter "msg"

    .prologue
    .line 94
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 95
    iget-object v0, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$1;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    #getter for: Lcom/mediatek/mtklogger/LogFolderListActivity;->mListView:Landroid/widget/ListView;
    invoke-static {v0}, Lcom/mediatek/mtklogger/LogFolderListActivity;->access$000(Lcom/mediatek/mtklogger/LogFolderListActivity;)Landroid/widget/ListView;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/ListView;->invalidateViews()V

    .line 96
    iget-object v0, p0, Lcom/mediatek/mtklogger/LogFolderListActivity$1;->this$0:Lcom/mediatek/mtklogger/LogFolderListActivity;

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/LogFolderListActivity;->finish()V

    .line 98
    :cond_0
    return-void
.end method
