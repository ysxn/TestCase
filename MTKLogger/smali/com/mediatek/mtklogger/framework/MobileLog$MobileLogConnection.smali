.class Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogConnection;
.super Lcom/mediatek/mtklogger/framework/LogConnection;
.source "MobileLog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/framework/MobileLog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "MobileLogConnection"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/framework/MobileLog;


# direct methods
.method public constructor <init>(Lcom/mediatek/mtklogger/framework/MobileLog;Ljava/lang/String;Landroid/os/Handler;)V
    .locals 0
    .parameter
    .parameter "socketName"
    .parameter "handler"

    .prologue
    .line 301
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MobileLog$MobileLogConnection;->this$0:Lcom/mediatek/mtklogger/framework/MobileLog;

    .line 302
    invoke-direct {p0, p2, p3}, Lcom/mediatek/mtklogger/framework/LogConnection;-><init>(Ljava/lang/String;Landroid/os/Handler;)V

    .line 303
    return-void
.end method


# virtual methods
.method public dealWithResponse([BLandroid/os/Handler;)V
    .locals 4
    .parameter "respBuffer"
    .parameter "handler"

    .prologue
    .line 307
    invoke-super {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/LogConnection;->dealWithResponse([BLandroid/os/Handler;)V

    .line 308
    const-string v1, "MTKLogger/MobileLog"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "-->dealWithResponse(), resp="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    new-instance v3, Ljava/lang/String;

    invoke-direct {v3, p1}, Ljava/lang/String;-><init>([B)V

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 309
    if-eqz p1, :cond_0

    array-length v1, p1

    if-nez v1, :cond_1

    .line 310
    :cond_0
    const-string v1, "MTKLogger/MobileLog"

    const-string v2, "Get an empty response from native, ignore it."

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 337
    :goto_0
    return-void

    .line 314
    :cond_1
    const/4 v0, 0x0

    .line 315
    .local v0, msgType:I
    const/4 v1, 0x0

    aget-byte v1, p1, v1

    sparse-switch v1, :sswitch_data_0

    .line 332
    const/16 v0, 0x17

    .line 333
    const-string v1, "MTKLogger/MobileLog"

    const-string v2, "kmsgcat || logcat alive. recv unkown bytes"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 336
    :goto_1
    invoke-virtual {p2, v0}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    goto :goto_0

    .line 317
    :sswitch_0
    const/16 v0, 0x16

    .line 318
    const-string v1, "MTKLogger/MobileLog"

    const-string v2, "kmsgcat || logcat die"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    .line 321
    :sswitch_1
    const/16 v0, 0x17

    .line 322
    goto :goto_1

    .line 328
    :sswitch_2
    const/16 v0, 0x18

    .line 329
    const-string v1, "MTKLogger/MobileLog"

    const-string v2, "kmsgcat || sd card is almost full"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    .line 315
    :sswitch_data_0
    .sparse-switch
        0x61 -> :sswitch_1
        0x64 -> :sswitch_0
        0x73 -> :sswitch_2
    .end sparse-switch
.end method
