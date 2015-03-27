.class Lcom/mediatek/mtklogger/MainActivity$4;
.super Landroid/os/Handler;
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
    .line 212
    iput-object p1, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 3
    .parameter "msg"

    .prologue
    const/4 v0, 0x1

    .line 214
    iget v1, p1, Landroid/os/Message;->what:I

    if-ne v1, v0, :cond_1

    .line 215
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    const-wide/16 v1, 0x1

    invoke-static {v0, v1, v2}, Lcom/mediatek/mtklogger/MainActivity;->access$514(Lcom/mediatek/mtklogger/MainActivity;J)J

    .line 216
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #getter for: Lcom/mediatek/mtklogger/MainActivity;->mTimeText:Landroid/widget/TextView;
    invoke-static {v0}, Lcom/mediatek/mtklogger/MainActivity;->access$700(Lcom/mediatek/mtklogger/MainActivity;)Landroid/widget/TextView;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->calculateTimer()Ljava/lang/String;
    invoke-static {v1}, Lcom/mediatek/mtklogger/MainActivity;->access$600(Lcom/mediatek/mtklogger/MainActivity;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 226
    :cond_0
    :goto_0
    return-void

    .line 217
    :cond_1
    iget v1, p1, Landroid/os/Message;->what:I

    const/4 v2, 0x2

    if-ne v1, v2, :cond_2

    .line 218
    const-string v0, "MTKLogger/MainActivity"

    const-string v1, "mMessageHandler->handleMessage() msg.what == MSG_WAITING_DIALOG_TIMER"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 219
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->stopWaitingDialog()V
    invoke-static {v0}, Lcom/mediatek/mtklogger/MainActivity;->access$000(Lcom/mediatek/mtklogger/MainActivity;)V

    goto :goto_0

    .line 220
    :cond_2
    iget v1, p1, Landroid/os/Message;->what:I

    const/4 v2, 0x3

    if-ne v1, v2, :cond_3

    .line 221
    const-string v0, "MTKLogger/MainActivity"

    const-string v1, "mMessageHandler->handleMessage() msg.what == MSG_MONITOR_SDCARD_BAR"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 222
    iget-object v0, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    #calls: Lcom/mediatek/mtklogger/MainActivity;->refreshSdcardBar()V
    invoke-static {v0}, Lcom/mediatek/mtklogger/MainActivity;->access$800(Lcom/mediatek/mtklogger/MainActivity;)V

    goto :goto_0

    .line 223
    :cond_3
    iget v1, p1, Landroid/os/Message;->what:I

    const/4 v2, 0x4

    if-ne v1, v2, :cond_0

    .line 224
    iget-object v1, p0, Lcom/mediatek/mtklogger/MainActivity$4;->this$0:Lcom/mediatek/mtklogger/MainActivity;

    iget v2, p1, Landroid/os/Message;->arg1:I

    if-ne v2, v0, :cond_4

    :goto_1
    #calls: Lcom/mediatek/mtklogger/MainActivity;->updateLogAutoStart(Z)V
    invoke-static {v1, v0}, Lcom/mediatek/mtklogger/MainActivity;->access$900(Lcom/mediatek/mtklogger/MainActivity;Z)V

    goto :goto_0

    :cond_4
    const/4 v0, 0x0

    goto :goto_1
.end method
