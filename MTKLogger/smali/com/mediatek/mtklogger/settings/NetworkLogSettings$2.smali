.class Lcom/mediatek/mtklogger/settings/NetworkLogSettings$2;
.super Lcom/mediatek/mtklogger/settings/LogSwitchListener;
.source "NetworkLogSettings.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->setListeners()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;


# direct methods
.method constructor <init>(Lcom/mediatek/mtklogger/settings/NetworkLogSettings;)V
    .locals 0
    .parameter

    .prologue
    .line 127
    iput-object p1, p0, Lcom/mediatek/mtklogger/settings/NetworkLogSettings$2;->this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;

    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/LogSwitchListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .locals 2
    .parameter "buttonView"
    .parameter "isChecked"

    .prologue
    .line 131
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/NetworkLogSettings$2;->this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;

    #getter for: Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v0}, Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->access$000(Lcom/mediatek/mtklogger/settings/NetworkLogSettings;)Landroid/content/SharedPreferences;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "networklog_switch"

    invoke-interface {v0, v1, p2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 132
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/NetworkLogSettings$2;->this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;

    #calls: Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->setAllPreferencesEnable(Z)V
    invoke-static {v0, p2}, Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->access$100(Lcom/mediatek/mtklogger/settings/NetworkLogSettings;Z)V

    .line 133
    return-void
.end method

.method public onPreferenceChange(Landroid/preference/Preference;Ljava/lang/Object;)Z
    .locals 4
    .parameter "preference"
    .parameter "newValue"

    .prologue
    .line 137
    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v0

    .line 138
    .local v0, isChecked:Z
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/NetworkLogSettings$2;->this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;

    #getter for: Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;
    invoke-static {v1}, Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->access$000(Lcom/mediatek/mtklogger/settings/NetworkLogSettings;)Landroid/content/SharedPreferences;

    move-result-object v1

    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    const-string v2, "networklog_switch"

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v3

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    invoke-interface {v1}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 140
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/NetworkLogSettings$2;->this$0:Lcom/mediatek/mtklogger/settings/NetworkLogSettings;

    #calls: Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->setAllPreferencesEnable(Z)V
    invoke-static {v1, v0}, Lcom/mediatek/mtklogger/settings/NetworkLogSettings;->access$100(Lcom/mediatek/mtklogger/settings/NetworkLogSettings;Z)V

    .line 141
    const/4 v1, 0x1

    return v1
.end method
