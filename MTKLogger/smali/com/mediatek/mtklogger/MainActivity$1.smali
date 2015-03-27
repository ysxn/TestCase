.class Lcom/mediatek/mtklogger/MainActivity$1;
.super Landroid/content/BroadcastReceiver;
.source "MainActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/MainActivity;
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
    .line 98
    iput-object p1, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 101
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 102
    .local v0, action:Ljava/lang/String;
    const-string v2, "com.mediatek.mtklogger.intent.action.LOG_STATE_CHANGED"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 103
    const-string v2, "MTKLogger/MainActivity"

    const-string v3, "ACTION_LOG_STATE_CHANGED"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 104
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->stopWaitingDialog()V
    invoke-static {v2}, Lcom/mediatek/mtklogger/MainActivity;->access$000(Lcom/mediatek/mtklogger/MainActivity;)V

    .line 105
    const-string v2, "fail_reason"

    invoke-virtual {p2, v2}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 106
    .local v1, failReason:Ljava/lang/String;
    if-eqz v1, :cond_0

    const-string v2, ""

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 107
    const-string v2, "MTKLogger/MainActivity"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "ACTION_LOG_STATE_CHANGED : failReason = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 108
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    iget-object v3, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->analyzeReason(Ljava/lang/String;)Ljava/lang/String;
    invoke-static {v3, v1}, Lcom/mediatek/mtklogger/MainActivity;->access$100(Lcom/mediatek/mtklogger/MainActivity;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    const/4 v4, 0x0

    invoke-static {v2, v3, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    .line 110
    :cond_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->updateUI()V
    invoke-static {v2}, Lcom/mediatek/mtklogger/MainActivity;->access$200(Lcom/mediatek/mtklogger/MainActivity;)V

    .line 118
    .end local v1           #failReason:Ljava/lang/String;
    :cond_1
    :goto_0
    return-void

    .line 111
    :cond_2
    const-string v2, "stage_event"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 112
    const-string v2, "MTKLogger/MainActivity"

    const-string v3, "EXTRA_RUNNING_STAGE_CHANGE_EVENT"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 113
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->changeWaitingDialog()V
    invoke-static {v2}, Lcom/mediatek/mtklogger/MainActivity;->access$300(Lcom/mediatek/mtklogger/MainActivity;)V

    goto :goto_0

    .line 114
    :cond_3
    const-string v2, "extra_key_from_taglog"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 115
    const-string v2, "MTKLogger/MainActivity"

    const-string v3, "EXTRA_KEY_FROM_TAGLOG"

    invoke-static {v2, v3}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 116
    iget-object v2, p0, Lcom/mediatek/mtklogger/MainActivity$1;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    invoke-virtual {v2}, Lcom/mediatek/mtklogger/MainActivity;->finish()V

    goto :goto_0
.end method
