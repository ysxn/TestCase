.class Lcom/mediatek/mtklogger/settings/NetworkLogSettings$1;
.super Landroid/preference/EditTextPreference;
.source "NetworkLogSettings.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->findViews()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/settings/NetworkLogSettings;Landroid/content/Context;)V
    .locals 0
    .parameter
    .parameter "x0"

    .prologue
    .line 83
    iput-object p1, p0, Lcom/mediatek/mtklogger/settings/NetworkLogSettings$1;->this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;

    invoke-direct {p0, p2}, Landroid/preference/EditTextPreference;-><init>(Landroid/content/Context;)V

    return-void
.end method


# virtual methods
.method protected onPrepareDialogBuilder(Landroid/app/AlertDialog$Builder;)V
    .locals 1
    .parameter "builder"

    .prologue
    .line 86
    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Landroid/app/AlertDialog$Builder;->setInverseBackgroundForced(Z)Landroid/app/AlertDialog$Builder;

    .line 87
    invoke-super {p0, p1}, Landroid/preference/EditTextPreference;->onPrepareDialogBuilder(Landroid/app/AlertDialog$Builder;)V

    .line 88
    return-void
.end method