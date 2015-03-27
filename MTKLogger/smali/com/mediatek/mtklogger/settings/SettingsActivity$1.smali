.class Lcom/mediatek/mtklogger/settings/SettingsActivity$1;
.super Ljava/lang/Object;
.source "SettingsActivity.java"

# interfaces
.implements Landroid/preference/Preference$OnPreferenceClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/settings/SettingsActivity;->setListeners()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 198
    iput-object p1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 4
    .parameter "preference"

    .prologue
    const/4 v2, 0x0

    .line 201
    new-instance v0, Landroid/content/Intent;

    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    const-class v3, Lcom/mediatek/mtklogger/settings/MobileLogSettings;

    invoke-direct {v0, v1, v3}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 202
    .local v0, intent:Landroid/content/Intent;
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    #getter for: Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;
    invoke-static {v1}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->access$000(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/SwitchPreference;

    move-result-object v3

    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    #getter for: Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;
    invoke-static {v1}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->access$000(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/SwitchPreference;

    move-result-object v1

    invoke-virtual {v1}, Landroid/preference/SwitchPreference;->isChecked()Z

    move-result v1

    if-nez v1, :cond_0

    const/4 v1, 0x1

    :goto_0
    invoke-virtual {v3, v1}, Landroid/preference/SwitchPreference;->setChecked(Z)V

    .line 203
    const-string v1, "isSwitchChecked"

    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    #getter for: Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;
    invoke-static {v3}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->access$000(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/SwitchPreference;

    move-result-object v3

    invoke-virtual {v3}, Landroid/preference/SwitchPreference;->isChecked()Z

    move-result v3

    invoke-virtual {v0, v1, v3}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    .line 204
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    #calls: Lcom/mediatek/mtklogger/settings/SettingsActivity;->setSdcardSize()V
    invoke-static {v1}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->access$100(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V

    .line 205
    const-string v1, "sdcardSize"

    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    #getter for: Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSdcardSize:I
    invoke-static {v3}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->access$200(Lcom/mediatek/mtklogger/settings/SettingsActivity;)I

    move-result v3

    invoke-virtual {v0, v1, v3}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 206
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;->this$0:Lcom/mediatek/mtklogger/settings/SettingsActivity;

    invoke-virtual {v1, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->startActivity(Landroid/content/Intent;)V

    .line 207
    return v2

    :cond_0
    move v1, v2

    .line 202
    goto :goto_0
.end method
