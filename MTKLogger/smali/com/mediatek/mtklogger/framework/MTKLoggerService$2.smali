.class Lcom/mediatek/mtklogger/framework/MTKLoggerService$2;
.super Landroid/content/BroadcastReceiver;
.source "MTKLoggerService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/framework/MTKLoggerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/framework/MTKLoggerService;)V
    .locals 0
    .parameter

    .prologue
    .line 436
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MTKLoggerService$2;->this$0:Lcom/mediatek/mtklogger/framework/MTKLoggerService;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 4
    .parameter "arg0"
    .parameter "arg1"

    .prologue
    .line 439
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 440
    .local v0, action:Ljava/lang/String;
    const-string v1, "MTKLogger/MTKLoggerService"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, " mIPOReceiver intent action: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 441
    const-string v1, "android.intent.action.ACTION_SHUTDOWN_IPO"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 442
    const-string v1, "MTKLogger/MTKLoggerService"

    const-string v2, "Get IPO_SHUTDOWN event!"

    invoke-static {v1, v2}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 445
    const/4 v1, 0x1

    sput-boolean v1, Lcom/mediatek/mtklogger/framework/MTKLoggerService;->isInIPOShutdown:Z

    .line 447
    :cond_0
    return-void
.end method
