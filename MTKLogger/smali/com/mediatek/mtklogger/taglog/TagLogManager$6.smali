.class Lcom/mediatek/mtklogger/taglog/TagLogManager$6;
.super Ljava/lang/Object;
.source "TagLogManager.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/taglog/TagLogManager;->createDialog(I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/taglog/TagLogManager;)V
    .locals 0
    .parameter

    .prologue
    .line 962
    iput-object p1, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager$6;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3
    .parameter "dialog"
    .parameter "which"

    .prologue
    const/4 v2, 0x1

    .line 965
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager$6;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mIsInputDialogClicked:Z
    invoke-static {v0, v2}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1302(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)Z

    .line 967
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 968
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager$6;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    const-string v1, "Cancel"

    #setter for: Lcom/mediatek/mtklogger/taglog/TagLogManager;->mTagLogResult:Ljava/lang/String;
    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$1402(Lcom/mediatek/mtklogger/taglog/TagLogManager;Ljava/lang/String;)Ljava/lang/String;

    .line 969
    iget-object v0, p0, Lcom/mediatek/mtklogger/taglog/TagLogManager$6;->this$0:Lcom/mediatek/mtklogger/taglog/TagLogManager;

    #calls: Lcom/mediatek/mtklogger/taglog/TagLogManager;->deInit(Z)V
    invoke-static {v0, v2}, Lcom/mediatek/mtklogger/taglog/TagLogManager;->access$300(Lcom/mediatek/mtklogger/taglog/TagLogManager;Z)V

    .line 970
    return-void
.end method
