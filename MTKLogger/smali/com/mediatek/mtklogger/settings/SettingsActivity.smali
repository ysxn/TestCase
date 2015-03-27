.class public Lcom/mediatek/mtklogger/settings/SettingsActivity;
.super Landroid/preference/PreferenceActivity;
.source "SettingsActivity.java"

# interfaces
.implements Landroid/preference/Preference$OnPreferenceChangeListener;
.implements Landroid/preference/Preference$OnPreferenceClickListener;


# static fields
.field private static final ACTION_DUMP:Ljava/lang/String; = "com.mediatek.logdumper.DUMP_ACTION"

.field private static final EXTRA_TARGET_VALUE:Ljava/lang/String; = "target_value"

.field public static final KEY_ADVANCED_LOG_STORAGE_LOCATION:Ljava/lang/String; = "log_storage_location"

.field public static final KEY_ADVANCED_RUN_COMMAND:Ljava/lang/String; = "run_command"

.field public static final KEY_ADVANCED_RUN_EXCEPTION_HISTORY:Ljava/lang/String; = "run_exception_history"

.field public static final KEY_ADVANCED_SETTINGS_CATEGORY:Ljava/lang/String; = "advanced_settings_category"

.field public static final KEY_EXCEPTIONREPORTER_ENABLE:Ljava/lang/String; = "exceptionreporter_enable"

.field public static final KEY_LOG_AUTOSTART_MAP:Landroid/util/SparseArray; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static final KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static final KEY_MB_AUTOSTART:Ljava/lang/String; = "mobilelog_autostart"

.field public static final KEY_MB_SWITCH:Ljava/lang/String; = "mobilelog_switch"

.field public static final KEY_MD_AUTOSTART:Ljava/lang/String; = "modemlog_autostart"

.field public static final KEY_MD_SWITCH:Ljava/lang/String; = "modemlog_switch"

.field public static final KEY_NT_AUTOSTART:Ljava/lang/String; = "networklog_autostart"

.field public static final KEY_NT_SWITCH:Ljava/lang/String; = "networklog_switch"

.field public static final KEY_TAGLOG_ENABLE:Ljava/lang/String; = "taglog_enable"

.field public static final KEY_UI_DEBUG_MODE_ENABLE:Ljava/lang/String; = "ui_debug_mode_enable"


# instance fields
.field private final TAG:Ljava/lang/String;

.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field mExceptionHistory:Landroid/preference/PreferenceScreen;

.field private mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

.field private mLogStorageLocationList:Landroid/preference/ListPreference;

.field private mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

.field private mMbSwitchPre:Landroid/preference/SwitchPreference;

.field private mMdSwitchPre:Landroid/preference/SwitchPreference;

.field private mNtSwitchPre:Landroid/preference/SwitchPreference;

.field private mRunCmdEditText:Landroid/preference/EditTextPreference;

.field private mSdcardSize:I

.field private mSharedPreferences:Landroid/content/SharedPreferences;

.field private mTaglogEnable:Landroid/preference/CheckBoxPreference;

.field private mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;


# direct methods
.method static constructor <clinit>()V
    .locals 5

    .prologue
    const/4 v4, 0x4

    const/4 v3, 0x2

    const/4 v2, 0x1

    .line 44
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    .line 46
    sget-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    const-string v1, "mobilelog_switch"

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 47
    sget-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    const-string v1, "modemlog_switch"

    invoke-virtual {v0, v3, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 48
    sget-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_SWITCH_MAP:Landroid/util/SparseArray;

    const-string v1, "networklog_switch"

    invoke-virtual {v0, v4, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 54
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_AUTOSTART_MAP:Landroid/util/SparseArray;

    .line 56
    sget-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_AUTOSTART_MAP:Landroid/util/SparseArray;

    const-string v1, "mobilelog_autostart"

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 57
    sget-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_AUTOSTART_MAP:Landroid/util/SparseArray;

    const-string v1, "modemlog_autostart"

    invoke-virtual {v0, v3, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 58
    sget-object v0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->KEY_LOG_AUTOSTART_MAP:Landroid/util/SparseArray;

    const-string v1, "networklog_autostart"

    invoke-virtual {v0, v4, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 59
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .prologue
    .line 36
    invoke-direct {p0}, Landroid/preference/PreferenceActivity;-><init>()V

    .line 39
    const-string v0, "MTKLogger/SettingsActivity"

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->TAG:Ljava/lang/String;

    .line 85
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 87
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSdcardSize:I

    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/SwitchPreference;
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;

    return-object v0
.end method

.method static synthetic access$100(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 36
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setSdcardSize()V

    return-void
.end method

.method static synthetic access$200(Lcom/mediatek/mtklogger/settings/SettingsActivity;)I
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSdcardSize:I

    return v0
.end method

.method static synthetic access$300(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/SwitchPreference;
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMdSwitchPre:Landroid/preference/SwitchPreference;

    return-object v0
.end method

.method static synthetic access$400(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/SwitchPreference;
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mNtSwitchPre:Landroid/preference/SwitchPreference;

    return-object v0
.end method

.method static synthetic access$500(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/CheckBoxPreference;
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    return-object v0
.end method

.method static synthetic access$600(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$700(Lcom/mediatek/mtklogger/settings/SettingsActivity;)Landroid/preference/CheckBoxPreference;
    .locals 1
    .parameter "x0"

    .prologue
    .line 36
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    return-object v0
.end method

.method private findViews()V
    .locals 2

    .prologue
    .line 117
    const-string v0, "mobilelog_switch"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/SwitchPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;

    .line 118
    const-string v0, "modemlog_switch"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/SwitchPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMdSwitchPre:Landroid/preference/SwitchPreference;

    .line 119
    const-string v0, "networklog_switch"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/SwitchPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mNtSwitchPre:Landroid/preference/SwitchPreference;

    .line 121
    const-string v0, "taglog_enable"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/CheckBoxPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    .line 122
    const-string v0, "exceptionreporter_enable"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/CheckBoxPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    .line 123
    const-string v0, "ui_debug_mode_enable"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/CheckBoxPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    .line 124
    const-string v0, "log_storage_location"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/ListPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    .line 125
    const-string v0, "run_command"

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/EditTextPreference;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mRunCmdEditText:Landroid/preference/EditTextPreference;

    .line 126
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v0

    const-string v1, "run_exception_history"

    invoke-virtual {v0, v1}, Landroid/preference/PreferenceScreen;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/PreferenceScreen;

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    .line 129
    return-void
.end method

.method private initViews()V
    .locals 9

    .prologue
    const/4 v8, 0x0

    const/4 v4, 0x1

    const/4 v5, 0x0

    .line 133
    invoke-static {p0}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 134
    const-string v3, "log_settings"

    invoke-virtual {p0, v3, v5}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 135
    new-instance v3, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-direct {v3, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 138
    const-string v3, "MTKLogger/SettingsActivity"

    const-string v6, "Hide log2server_app"

    invoke-static {v3, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 140
    const-string v3, "advanced_settings_category"

    invoke-virtual {p0, v3}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/PreferenceCategory;

    .line 141
    .local v0, advancePreCategory:Landroid/preference/PreferenceCategory;
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, v3}, Landroid/preference/PreferenceCategory;->removePreference(Landroid/preference/Preference;)Z

    .line 142
    iput-object v8, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    .line 143
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    invoke-virtual {v0, v3}, Landroid/preference/PreferenceCategory;->removePreference(Landroid/preference/Preference;)Z

    .line 144
    iput-object v8, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    .line 148
    sget-object v1, Landroid/os/Build;->TYPE:Ljava/lang/String;

    .line 149
    .local v1, buildType:Ljava/lang/String;
    if-eqz v1, :cond_3

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    const-string v6, "eng"

    invoke-virtual {v3, v6}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 150
    const-string v3, "MTKLogger/SettingsActivity"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "initViews() BuildType : "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v3, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 152
    const-string v3, "advanced_settings_category"

    invoke-virtual {p0, v3}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .end local v0           #advancePreCategory:Landroid/preference/PreferenceCategory;
    check-cast v0, Landroid/preference/PreferenceCategory;

    .line 153
    .restart local v0       #advancePreCategory:Landroid/preference/PreferenceCategory;
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, v3}, Landroid/preference/PreferenceCategory;->removePreference(Landroid/preference/Preference;)Z

    .line 154
    iput-object v8, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    .line 157
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v6, "tagLogEnable"

    invoke-interface {v3, v6, v5}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    if-nez v3, :cond_1

    .line 158
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v3, v5}, Landroid/preference/CheckBoxPreference;->setChecked(Z)V

    .line 159
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    if-eqz v3, :cond_0

    .line 160
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v3, v5}, Landroid/preference/CheckBoxPreference;->setChecked(Z)V

    .line 162
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    const-string v6, "log2server_dialog_show"

    invoke-static {v3, v6, v5}, Landroid/provider/Settings$System;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 168
    :cond_1
    :goto_0
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setSdcardSize()V

    .line 169
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->updateUI()V

    .line 171
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setLogStorageEntries()V

    .line 173
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    if-eqz v3, :cond_5

    .line 174
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    iget-object v6, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v6}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v6

    invoke-virtual {v3, v6}, Landroid/preference/CheckBoxPreference;->setEnabled(Z)V

    .line 175
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v3}, Landroid/preference/CheckBoxPreference;->isEnabled()Z

    move-result v3

    if-nez v3, :cond_4

    .line 195
    :cond_2
    :goto_1
    return-void

    .line 165
    :cond_3
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    iget-object v6, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v7, "tagLogEnable"

    invoke-interface {v6, v7, v4}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v6

    invoke-virtual {v3, v6}, Landroid/preference/CheckBoxPreference;->setChecked(Z)V

    goto :goto_0

    .line 179
    :cond_4
    :try_start_0
    iget-object v6, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v3

    const-string v7, "log2server_dialog_show"

    invoke-static {v3, v7}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;)I

    move-result v3

    if-ne v3, v4, :cond_7

    move v3, v4

    :goto_2
    invoke-virtual {v6, v3}, Landroid/preference/CheckBoxPreference;->setChecked(Z)V
    :try_end_0
    .catch Landroid/provider/Settings$SettingNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    .line 185
    :cond_5
    :goto_3
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    if-eqz v3, :cond_6

    .line 186
    iget-object v6, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v3}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v3

    if-eqz v3, :cond_8

    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v3}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v3

    if-eqz v3, :cond_8

    move v3, v4

    :goto_4
    invoke-virtual {v6, v3}, Landroid/preference/PreferenceScreen;->setEnabled(Z)V

    .line 188
    :cond_6
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    const-string v6, "hasStartedDebugMode"

    invoke-interface {v3, v6, v5}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    if-nez v3, :cond_2

    .line 189
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    const-string v6, "hasStartedDebugMode"

    invoke-interface {v3, v6, v4}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 190
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    if-eqz v3, :cond_2

    .line 191
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v3, v5}, Landroid/preference/CheckBoxPreference;->setChecked(Z)V

    .line 192
    invoke-direct {p0, v5}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setUIDebugMode(Z)V

    goto :goto_1

    :cond_7
    move v3, v5

    .line 179
    goto :goto_2

    .line 181
    :catch_0
    move-exception v2

    .line 182
    .local v2, e:Landroid/provider/Settings$SettingNotFoundException;
    invoke-virtual {v2}, Landroid/provider/Settings$SettingNotFoundException;->printStackTrace()V

    goto :goto_3

    .end local v2           #e:Landroid/provider/Settings$SettingNotFoundException;
    :cond_8
    move v3, v5

    .line 186
    goto :goto_4
.end method

.method private setListeners()V
    .locals 2

    .prologue
    .line 198
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;

    new-instance v1, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity$1;-><init>(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V

    invoke-virtual {v0, v1}, Landroid/preference/SwitchPreference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 211
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMdSwitchPre:Landroid/preference/SwitchPreference;

    new-instance v1, Lcom/mediatek/mtklogger/settings/SettingsActivity$2;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity$2;-><init>(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V

    invoke-virtual {v0, v1}, Landroid/preference/SwitchPreference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 224
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mNtSwitchPre:Landroid/preference/SwitchPreference;

    new-instance v1, Lcom/mediatek/mtklogger/settings/SettingsActivity$3;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity$3;-><init>(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V

    invoke-virtual {v0, v1}, Landroid/preference/SwitchPreference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 237
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mTaglogEnable:Landroid/preference/CheckBoxPreference;

    new-instance v1, Lcom/mediatek/mtklogger/settings/SettingsActivity$4;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity$4;-><init>(Lcom/mediatek/mtklogger/settings/SettingsActivity;)V

    invoke-virtual {v0, v1}, Landroid/preference/CheckBoxPreference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 256
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    if-eqz v0, :cond_0

    .line 257
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p0}, Landroid/preference/CheckBoxPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 260
    :cond_0
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    if-eqz v0, :cond_1

    .line 261
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mUIDebugModeEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p0}, Landroid/preference/CheckBoxPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 263
    :cond_1
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    invoke-virtual {v0, p0}, Landroid/preference/ListPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 264
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mRunCmdEditText:Landroid/preference/EditTextPreference;

    invoke-virtual {v0, p0}, Landroid/preference/EditTextPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 265
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    if-eqz v0, :cond_2

    .line 266
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    invoke-virtual {v0, p0}, Landroid/preference/PreferenceScreen;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 268
    :cond_2
    return-void
.end method

.method private setLogStorageEntries()V
    .locals 8

    .prologue
    const v5, 0x7f07001b

    const v6, 0x7f07001a

    .line 330
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 331
    .local v0, entriesList:Ljava/util/List;,"Ljava/util/List<Ljava/lang/CharSequence;>;"
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 332
    .local v1, entryValuesList:Ljava/util/List;,"Ljava/util/List<Ljava/lang/CharSequence;>;"
    const-string v4, "/mnt/sdcard"

    invoke-static {p0, v4}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-static {p0, v4}, Lcom/mediatek/mtklogger/utils/Utils;->getVolumeState(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 334
    .local v3, status:Ljava/lang/String;
    const-string v4, "mounted"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_0

    .line 335
    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getString(I)Ljava/lang/String;

    move-result-object v4

    invoke-interface {v0, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 336
    const-string v4, "1"

    invoke-interface {v1, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 338
    :cond_0
    const-string v4, "/mnt/sdcard2"

    invoke-static {p0, v4}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPath(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-static {p0, v4}, Lcom/mediatek/mtklogger/utils/Utils;->getVolumeState(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 339
    const-string v4, "mounted"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 340
    invoke-virtual {p0, v5}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getString(I)Ljava/lang/String;

    move-result-object v4

    invoke-interface {v0, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 341
    const-string v4, "2"

    invoke-interface {v1, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 343
    :cond_1
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v4

    if-nez v4, :cond_2

    .line 344
    iget-object v4, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    const/4 v5, 0x0

    invoke-virtual {v4, v5}, Landroid/preference/ListPreference;->setEnabled(Z)V

    .line 354
    :goto_0
    return-void

    .line 347
    :cond_2
    iget-object v7, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v4

    new-array v4, v4, [Ljava/lang/CharSequence;

    invoke-interface {v0, v4}, Ljava/util/List;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Ljava/lang/CharSequence;

    invoke-virtual {v7, v4}, Landroid/preference/ListPreference;->setEntries([Ljava/lang/CharSequence;)V

    .line 348
    iget-object v7, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v4

    new-array v4, v4, [Ljava/lang/CharSequence;

    invoke-interface {v1, v4}, Ljava/util/List;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Ljava/lang/CharSequence;

    invoke-virtual {v7, v4}, Landroid/preference/ListPreference;->setEntryValues([Ljava/lang/CharSequence;)V

    .line 349
    const-string v4, "/mnt/sdcard2"

    invoke-static {}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPathType()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v4, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3

    const-string v2, "2"

    .line 351
    .local v2, logPathTypeKey:Ljava/lang/String;
    :goto_1
    iget-object v4, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    invoke-virtual {v4, v2}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    .line 352
    iget-object v7, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    const-string v4, "2"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    move v4, v5

    :goto_2
    invoke-virtual {v7, v4}, Landroid/preference/ListPreference;->setSummary(I)V

    goto :goto_0

    .line 349
    .end local v2           #logPathTypeKey:Ljava/lang/String;
    :cond_3
    const-string v2, "1"

    goto :goto_1

    .restart local v2       #logPathTypeKey:Ljava/lang/String;
    :cond_4
    move v4, v6

    .line 352
    goto :goto_2
.end method

.method private setSdcardSize()V
    .locals 5

    .prologue
    .line 310
    :try_start_0
    new-instance v2, Landroid/os/StatFs;

    invoke-static {p0}, Lcom/mediatek/mtklogger/utils/Utils;->getCurrentLogPath(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Landroid/os/StatFs;-><init>(Ljava/lang/String;)V

    .line 311
    .local v2, statFs:Landroid/os/StatFs;
    invoke-virtual {v2}, Landroid/os/StatFs;->getBlockSize()I

    move-result v3

    div-int/lit16 v0, v3, 0x400

    .line 312
    .local v0, blockSize:I
    invoke-virtual {v2}, Landroid/os/StatFs;->getBlockCount()I

    move-result v3

    mul-int/2addr v3, v0

    div-int/lit16 v3, v3, 0x400

    iput v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSdcardSize:I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 317
    .end local v0           #blockSize:I
    .end local v2           #statFs:Landroid/os/StatFs;
    :goto_0
    return-void

    .line 313
    :catch_0
    move-exception v1

    .line 314
    .local v1, e:Ljava/lang/Exception;
    const-string v3, "MTKLogger/SettingsActivity"

    const-string v4, "setSdcardSize() : StatFs error, maybe currentLogPath is invalid"

    invoke-static {v3, v4}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    .line 315
    const/4 v3, 0x0

    iput v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSdcardSize:I

    goto :goto_0
.end method

.method private setUIDebugMode(Z)V
    .locals 0
    .parameter "isDump"

    .prologue
    .line 326
    return-void
.end method

.method private updateUI()V
    .locals 6

    .prologue
    const/4 v4, 0x0

    const/4 v3, 0x1

    .line 357
    const-string v2, "MTKLogger/SettingsActivity"

    const-string v5, "updateUI()"

    invoke-static {v2, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 359
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-static {v2}, Lcom/mediatek/mtklogger/utils/Utils;->checkLogStarted(Landroid/content/SharedPreferences;)Z

    move-result v0

    .line 360
    .local v0, isRecording:Z
    iget-object v5, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;

    if-nez v0, :cond_1

    move v2, v3

    :goto_0
    invoke-virtual {v5, v2}, Landroid/preference/SwitchPreference;->setEnabled(Z)V

    .line 361
    iget-object v5, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMdSwitchPre:Landroid/preference/SwitchPreference;

    if-nez v0, :cond_2

    move v2, v3

    :goto_1
    invoke-virtual {v5, v2}, Landroid/preference/SwitchPreference;->setEnabled(Z)V

    .line 362
    iget-object v5, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mNtSwitchPre:Landroid/preference/SwitchPreference;

    if-nez v0, :cond_3

    move v2, v3

    :goto_2
    invoke-virtual {v5, v2}, Landroid/preference/SwitchPreference;->setEnabled(Z)V

    .line 363
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    invoke-virtual {v2}, Landroid/preference/ListPreference;->getEntries()[Ljava/lang/CharSequence;

    move-result-object v1

    .line 364
    .local v1, logStorageEntries:[Ljava/lang/CharSequence;
    if-eqz v1, :cond_0

    array-length v2, v1

    if-nez v2, :cond_4

    .line 365
    :cond_0
    const-string v2, "MTKLogger/SettingsActivity"

    const-string v5, "Log storage entry is null or empty, disable storage set item"

    invoke-static {v2, v5}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 366
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    invoke-virtual {v2, v4}, Landroid/preference/ListPreference;->setEnabled(Z)V

    .line 371
    :goto_3
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMbSwitchPre:Landroid/preference/SwitchPreference;

    iget-object v4, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v5, "mobilelog_switch"

    invoke-interface {v4, v5, v3}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v4

    invoke-virtual {v2, v4}, Landroid/preference/SwitchPreference;->setChecked(Z)V

    .line 372
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mMdSwitchPre:Landroid/preference/SwitchPreference;

    iget-object v4, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v5, "modemlog_switch"

    invoke-interface {v4, v5, v3}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v4

    invoke-virtual {v2, v4}, Landroid/preference/SwitchPreference;->setChecked(Z)V

    .line 373
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mNtSwitchPre:Landroid/preference/SwitchPreference;

    iget-object v4, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v5, "networklog_switch"

    invoke-interface {v4, v5, v3}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    invoke-virtual {v2, v3}, Landroid/preference/SwitchPreference;->setChecked(Z)V

    .line 375
    return-void

    .end local v1           #logStorageEntries:[Ljava/lang/CharSequence;
    :cond_1
    move v2, v4

    .line 360
    goto :goto_0

    :cond_2
    move v2, v4

    .line 361
    goto :goto_1

    :cond_3
    move v2, v4

    .line 362
    goto :goto_2

    .line 368
    .restart local v1       #logStorageEntries:[Ljava/lang/CharSequence;
    :cond_4
    iget-object v2, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mLogStorageLocationList:Landroid/preference/ListPreference;

    if-nez v0, :cond_5

    move v4, v3

    :cond_5
    invoke-virtual {v2, v4}, Landroid/preference/ListPreference;->setEnabled(Z)V

    goto :goto_3
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 92
    invoke-super {p0, p1}, Landroid/preference/PreferenceActivity;->onCreate(Landroid/os/Bundle;)V

    .line 93
    const v0, 0x7f040003

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->addPreferencesFromResource(I)V

    .line 95
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->findViews()V

    .line 96
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->initViews()V

    .line 97
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setListeners()V

    .line 98
    return-void
.end method

.method protected onDestroy()V
    .locals 1

    .prologue
    .line 109
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    if-eqz v0, :cond_0

    .line 110
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->free()V

    .line 111
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 113
    :cond_0
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onDestroy()V

    .line 114
    return-void
.end method

.method public onPreferenceChange(Landroid/preference/Preference;Ljava/lang/Object;)Z
    .locals 6
    .parameter "preference"
    .parameter "newValue"

    .prologue
    const/4 v3, 0x0

    const/4 v2, 0x1

    .line 272
    const-string v1, "MTKLogger/SettingsActivity"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Preference Change Key : "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " newValue : "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v1, v4}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 274
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v1

    const-string v4, "exceptionreporter_enable"

    invoke-virtual {v1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3

    .line 275
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    const-string v5, "log2server_dialog_show"

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_2

    move v1, v2

    :goto_0
    invoke-static {v4, v5, v1}, Landroid/provider/Settings$System;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 277
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    if-eqz v1, :cond_1

    .line 278
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    iget-object v4, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionReporterEnable:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v4}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v4

    if-nez v4, :cond_0

    move v3, v2

    :cond_0
    invoke-virtual {v1, v3}, Landroid/preference/PreferenceScreen;->setEnabled(Z)V

    .line 295
    :cond_1
    :goto_1
    return v2

    :cond_2
    move v1, v3

    .line 275
    goto :goto_0

    .line 279
    :cond_3
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v1

    const-string v3, "ui_debug_mode_enable"

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4

    .line 280
    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v1

    invoke-direct {p0, v1}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setUIDebugMode(Z)V

    goto :goto_1

    .line 281
    :cond_4
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v1

    const-string v3, "log_storage_location"

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8

    .line 282
    const-string v1, "/mnt/sdcard2"

    invoke-static {}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPathType()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_5

    const-string v0, "2"

    .line 284
    .local v0, oldValue:Ljava/lang/String;
    :goto_2
    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 287
    const-string v1, "2"

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_6

    const v1, 0x7f07001b

    :goto_3
    invoke-virtual {p1, v1}, Landroid/preference/Preference;->setSummary(I)V

    .line 289
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    const-string v1, "2"

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_7

    const-string v1, "Log2sd"

    :goto_4
    invoke-virtual {v3, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->runCommand(Ljava/lang/String;)Z

    .line 291
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->setSdcardSize()V

    goto :goto_1

    .line 282
    .end local v0           #oldValue:Ljava/lang/String;
    :cond_5
    const-string v0, "1"

    goto :goto_2

    .line 287
    .restart local v0       #oldValue:Ljava/lang/String;
    :cond_6
    const v1, 0x7f07001a

    goto :goto_3

    .line 289
    :cond_7
    const-string v1, "Log2emmc"

    goto :goto_4

    .line 292
    .end local v0           #oldValue:Ljava/lang/String;
    :cond_8
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v1

    const-string v3, "run_command"

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 293
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->runCommand(Ljava/lang/String;)Z

    goto/16 :goto_1
.end method

.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 2
    .parameter "preference"

    .prologue
    const/4 v0, 0x0

    .line 298
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    invoke-virtual {p1, v1}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 299
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/SettingsActivity;->mExceptionHistory:Landroid/preference/PreferenceScreen;

    invoke-virtual {v1}, Landroid/preference/PreferenceScreen;->isEnabled()Z

    move-result v1

    if-nez v1, :cond_1

    .line 305
    :cond_0
    :goto_0
    return v0

    .line 303
    :cond_1
    invoke-static {p0}, Lcom/mediatek/mtklogger/exceptionreporter/ExceptionReportManager;->runExceptionHistory(Landroid/content/Context;)Z

    move-result v0

    goto :goto_0
.end method

.method protected onResume()V
    .locals 2

    .prologue
    .line 102
    const-string v0, "MTKLogger/SettingsActivity"

    const-string v1, "onResume()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 103
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/SettingsActivity;->updateUI()V

    .line 104
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onResume()V

    .line 105
    return-void
.end method
