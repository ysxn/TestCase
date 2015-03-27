.class public Lcom/mediatek/mtklogger/settings/MobileLogSettings;
.super Landroid/preference/PreferenceActivity;
.source "MobileLogSettings.java"

# interfaces
.implements Landroid/preference/Preference$OnPreferenceChangeListener;


# static fields
.field public static final KEY_MB_ANDROID_LOG:Ljava/lang/String; = "mobilelog_androidlog"

.field public static final KEY_MB_BT_LOG:Ljava/lang/String; = "mobilelog_btlog"

.field public static final KEY_MB_CATEGORY:Ljava/lang/String; = "mobilelog_category"

.field public static final KEY_MB_KERNEL_LOG:Ljava/lang/String; = "mobilelog_kernellog"

.field public static final KEY_MB_LOGSIZE:Ljava/lang/String; = "mobilelog_logsize"

.field public static final KEY_MB_PREFERENCE_SCREEN:Ljava/lang/String; = "mobilelog_preference_screen"

.field private static final LIMIT_LOG_SIZE:I = 0x64


# instance fields
.field private final TAG:Ljava/lang/String;

.field private mBarSwitch:Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

.field private mDefaultSharedPreferences:Landroid/content/SharedPreferences;

.field private mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

.field private mMbAndroidLog:Landroid/preference/CheckBoxPreference;

.field private mMbAutoStartPre:Landroid/preference/CheckBoxPreference;

.field private mMbBluetoothLog:Landroid/preference/CheckBoxPreference;

.field private mMbKernelLog:Landroid/preference/CheckBoxPreference;

.field private mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

.field private mSdcardSize:I

.field private mSharedPreferences:Landroid/content/SharedPreferences;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 26
    invoke-direct {p0}, Landroid/preference/PreferenceActivity;-><init>()V

    .line 28
    const-string v0, "MTKLogger/MobileLogSettings"

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->TAG:Ljava/lang/String;

    .line 46
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/mtklogger/settings/MobileLogSettings;)Landroid/content/SharedPreferences;
    .locals 1
    .parameter "x0"

    .prologue
    .line 26
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    return-object v0
.end method

.method static synthetic access$100(Lcom/mediatek/mtklogger/settings/MobileLogSettings;Z)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 26
    invoke-direct {p0, p1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->setAllPreferencesEnable(Z)V

    return-void
.end method

.method static synthetic access$200(Lcom/mediatek/mtklogger/settings/MobileLogSettings;)Landroid/preference/EditTextPreference;
    .locals 1
    .parameter "x0"

    .prologue
    .line 26
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    return-object v0
.end method

.method static synthetic access$300(Lcom/mediatek/mtklogger/settings/MobileLogSettings;)I
    .locals 1
    .parameter "x0"

    .prologue
    .line 26
    iget v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mSdcardSize:I

    return v0
.end method

.method private findViews()V
    .locals 6

    .prologue
    const v5, 0x7f07003b

    .line 84
    const-string v1, "mobilelog_androidlog"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    check-cast v1, Landroid/preference/CheckBoxPreference;

    iput-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbAndroidLog:Landroid/preference/CheckBoxPreference;

    .line 85
    const-string v1, "mobilelog_kernellog"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    check-cast v1, Landroid/preference/CheckBoxPreference;

    iput-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbKernelLog:Landroid/preference/CheckBoxPreference;

    .line 86
    const-string v1, "mobilelog_btlog"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    check-cast v1, Landroid/preference/CheckBoxPreference;

    iput-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbBluetoothLog:Landroid/preference/CheckBoxPreference;

    .line 87
    const-string v1, "mobilelog_autostart"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    check-cast v1, Landroid/preference/CheckBoxPreference;

    iput-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbAutoStartPre:Landroid/preference/CheckBoxPreference;

    .line 89
    sget v1, Lcom/mediatek/mtklogger/utils/Utils;->ANDROID_VERSION_NUMBER:F

    float-to-double v1, v1

    const-wide v3, 0x400ffdf3b645a1cbL

    cmpl-double v1, v1, v3

    if-lez v1, :cond_0

    .line 90
    const-string v1, "mobilelog_logsize"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    check-cast v1, Landroid/preference/EditTextPreference;

    iput-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    .line 108
    :goto_0
    return-void

    .line 92
    :cond_0
    const-string v1, "mobilelog_preference_screen"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/PreferenceScreen;

    .line 93
    .local v0, preferenceScreen:Landroid/preference/PreferenceScreen;
    const-string v1, "mobilelog_logsize"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/preference/PreferenceScreen;->removePreference(Landroid/preference/Preference;)Z

    .line 94
    new-instance v1, Lcom/mediatek/mtklogger/settings/MobileLogSettings$1;

    invoke-direct {v1, p0, p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings$1;-><init>(Lcom/mediatek/mtklogger/settings/MobileLogSettings;Landroid/content/Context;)V

    iput-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    .line 101
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    const-string v2, "mobilelog_logsize"

    invoke-virtual {v1, v2}, Landroid/preference/EditTextPreference;->setKey(Ljava/lang/String;)V

    .line 102
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v1, v5}, Landroid/preference/EditTextPreference;->setTitle(I)V

    .line 103
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v1, v5}, Landroid/preference/EditTextPreference;->setDialogTitle(I)V

    .line 104
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    const v2, 0x7f07003c

    invoke-virtual {v1, v2}, Landroid/preference/EditTextPreference;->setSummary(I)V

    .line 105
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    const-string v2, "300"

    invoke-virtual {v1, v2}, Landroid/preference/EditTextPreference;->setDefaultValue(Ljava/lang/Object;)V

    .line 106
    iget-object v1, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v0, v1}, Landroid/preference/PreferenceScreen;->addPreference(Landroid/preference/Preference;)Z

    goto :goto_0
.end method

.method private getIntByObj(Ljava/lang/Object;)I
    .locals 2
    .parameter "obj"

    .prologue
    .line 220
    :try_start_0
    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    move-result v1

    .line 222
    :goto_0
    return v1

    .line 221
    :catch_0
    move-exception v0

    .line 222
    .local v0, e:Ljava/lang/NumberFormatException;
    const/4 v1, 0x0

    goto :goto_0
.end method

.method private initViews()V
    .locals 9

    .prologue
    const/16 v8, 0x64

    const/4 v7, 0x2

    const/4 v4, 0x1

    const/4 v5, 0x0

    .line 111
    const-string v3, "MTKLogger/MobileLogSettings"

    const-string v6, "initViews()"

    invoke-static {v3, v6}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 112
    invoke-static {p0}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    .line 113
    const-string v3, "log_settings"

    invoke-virtual {p0, v3, v5}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    iput-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 114
    new-instance v3, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-direct {v3, p0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;-><init>(Landroid/content/Context;)V

    iput-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 117
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mDefaultSharedPreferences:Landroid/content/SharedPreferences;

    const-string v6, "mobilelog_switch"

    invoke-interface {v3, v6, v5}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    .line 118
    .local v1, isSwitchChecked:Z
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-static {v3}, Lcom/mediatek/mtklogger/utils/Utils;->checkLogStarted(Landroid/content/SharedPreferences;)Z

    move-result v0

    .line 119
    .local v0, isRecording:Z
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mBarSwitch:Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    invoke-virtual {v3, v1}, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;->setChecked(Z)V

    .line 120
    iget-object v6, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mBarSwitch:Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    if-nez v0, :cond_0

    move v3, v4

    :goto_0
    invoke-virtual {v6, v3}, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;->setEnabled(Z)V

    .line 121
    if-eqz v1, :cond_1

    if-nez v0, :cond_1

    move v3, v4

    :goto_1
    invoke-direct {p0, v3}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->setAllPreferencesEnable(Z)V

    .line 123
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v3}, Landroid/preference/EditTextPreference;->getEditText()Landroid/widget/EditText;

    move-result-object v3

    invoke-virtual {v3, v7}, Landroid/widget/EditText;->setInputType(I)V

    .line 124
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->getIntent()Landroid/content/Intent;

    move-result-object v3

    const-string v6, "sdcardSize"

    invoke-virtual {v3, v6, v8}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v3

    iput v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mSdcardSize:I

    .line 126
    const/4 v3, 0x4

    new-array v2, v3, [Ljava/lang/Object;

    const v3, 0x7f07000c

    invoke-virtual {p0, v3}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->getString(I)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v2, v5

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v2, v4

    iget v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mSdcardSize:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v2, v7

    const/4 v4, 0x3

    const-string v3, "/mnt/sdcard2"

    invoke-static {}, Lcom/mediatek/mtklogger/utils/Utils;->getLogPathType()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v3, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_2

    const v3, 0x7f07001b

    :goto_2
    invoke-virtual {p0, v3}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->getString(I)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v2, v4

    .line 129
    .local v2, objs:[Ljava/lang/Object;
    iget-object v3, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    const v4, 0x7f07003d

    invoke-virtual {p0, v4, v2}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/preference/EditTextPreference;->setDialogMessage(Ljava/lang/CharSequence;)V

    .line 130
    return-void

    .end local v2           #objs:[Ljava/lang/Object;
    :cond_0
    move v3, v5

    .line 120
    goto :goto_0

    :cond_1
    move v3, v5

    .line 121
    goto :goto_1

    .line 126
    :cond_2
    const v3, 0x7f07001a

    goto :goto_2
.end method

.method private removeManualTitle()V
    .locals 2

    .prologue
    .line 79
    const-string v1, "mobilelog_preference_screen"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    check-cast v0, Landroid/preference/PreferenceScreen;

    .line 80
    .local v0, preferenceScreen:Landroid/preference/PreferenceScreen;
    const-string v1, "mobilelog_category"

    invoke-virtual {p0, v1}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/preference/PreferenceScreen;->removePreference(Landroid/preference/Preference;)Z

    .line 81
    return-void
.end method

.method private setAllPreferencesEnable(Z)V
    .locals 1
    .parameter "isEnable"

    .prologue
    .line 212
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbAndroidLog:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p1}, Landroid/preference/CheckBoxPreference;->setEnabled(Z)V

    .line 213
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbKernelLog:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p1}, Landroid/preference/CheckBoxPreference;->setEnabled(Z)V

    .line 214
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbBluetoothLog:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p1}, Landroid/preference/CheckBoxPreference;->setEnabled(Z)V

    .line 215
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v0, p1}, Landroid/preference/EditTextPreference;->setEnabled(Z)V

    .line 216
    return-void
.end method

.method private setListeners()V
    .locals 2

    .prologue
    .line 133
    const-string v0, "MTKLogger/MobileLogSettings"

    const-string v1, "setListeners()"

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 134
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbAndroidLog:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p0}, Landroid/preference/CheckBoxPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 135
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbKernelLog:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p0}, Landroid/preference/CheckBoxPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 136
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbBluetoothLog:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p0}, Landroid/preference/CheckBoxPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 137
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v0, p0}, Landroid/preference/EditTextPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 138
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbAutoStartPre:Landroid/preference/CheckBoxPreference;

    invoke-virtual {v0, p0}, Landroid/preference/CheckBoxPreference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 140
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mBarSwitch:Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    new-instance v1, Lcom/mediatek/mtklogger/settings/MobileLogSettings$2;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings$2;-><init>(Lcom/mediatek/mtklogger/settings/MobileLogSettings;)V

    invoke-virtual {v0, v1}, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;->setOnCheckedChangeListener(Lcom/mediatek/mtklogger/settings/LogSwitchListener;)V

    .line 160
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mMbLogSizeLimitPre:Landroid/preference/EditTextPreference;

    invoke-virtual {v0}, Landroid/preference/EditTextPreference;->getEditText()Landroid/widget/EditText;

    move-result-object v0

    new-instance v1, Lcom/mediatek/mtklogger/settings/MobileLogSettings$3;

    invoke-direct {v1, p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings$3;-><init>(Lcom/mediatek/mtklogger/settings/MobileLogSettings;)V

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->addTextChangedListener(Landroid/text/TextWatcher;)V

    .line 191
    return-void
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 4
    .parameter "savedInstanceState"

    .prologue
    .line 54
    invoke-super {p0, p1}, Landroid/preference/PreferenceActivity;->onCreate(Landroid/os/Bundle;)V

    .line 55
    const v0, 0x7f030009

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->addPreferencesFromResource(I)V

    .line 57
    sget v0, Lcom/mediatek/mtklogger/utils/Utils;->ANDROID_VERSION_NUMBER:F

    float-to-double v0, v0

    const-wide v2, 0x400ffdf3b645a1cbL

    cmpl-double v0, v0, v2

    if-lez v0, :cond_0

    .line 58
    new-instance v0, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;-><init>(Landroid/preference/PreferenceActivity;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mBarSwitch:Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    .line 59
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->removeManualTitle()V

    .line 64
    :goto_0
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->findViews()V

    .line 65
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->initViews()V

    .line 66
    invoke-direct {p0}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->setListeners()V

    .line 67
    return-void

    .line 61
    :cond_0
    new-instance v0, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    const/4 v1, 0x1

    invoke-direct {v0, p0, v1}, Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;-><init>(Landroid/app/Activity;I)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mBarSwitch:Lcom/mediatek/mtklogger/settings/OptionalActionBarSwitch;

    goto :goto_0
.end method

.method protected onDestroy()V
    .locals 1

    .prologue
    .line 71
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    if-eqz v0, :cond_0

    .line 72
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {v0}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->free()V

    .line 73
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    .line 75
    :cond_0
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onDestroy()V

    .line 76
    return-void
.end method

.method public onPreferenceChange(Landroid/preference/Preference;Ljava/lang/Object;)Z
    .locals 4
    .parameter "preference"
    .parameter "newValue"

    .prologue
    const/4 v3, 0x1

    .line 195
    const-string v0, "MTKLogger/MobileLogSettings"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Preference Change Key : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " newValue : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 197
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v0

    const-string v1, "mobilelog_androidlog"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 198
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v1

    invoke-virtual {v0, v3, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->setMobileSubLogEnableState(IZ)Z

    .line 208
    :cond_0
    :goto_0
    return v3

    .line 199
    :cond_1
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v0

    const-string v1, "mobilelog_kernellog"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 200
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    const/4 v1, 0x2

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->setMobileSubLogEnableState(IZ)Z

    goto :goto_0

    .line 201
    :cond_2
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v0

    const-string v1, "mobilelog_btlog"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    .line 202
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    const/4 v1, 0x3

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->setMobileSubLogEnableState(IZ)Z

    goto :goto_0

    .line 203
    :cond_3
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v0

    const-string v1, "mobilelog_logsize"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 204
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-direct {p0, p2}, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->getIntByObj(Ljava/lang/Object;)I

    move-result v1

    invoke-virtual {v0, v3, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->setLogSize(II)Z

    goto :goto_0

    .line 205
    :cond_4
    invoke-virtual {p1}, Landroid/preference/Preference;->getKey()Ljava/lang/String;

    move-result-object v0

    const-string v1, "mobilelog_autostart"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 206
    iget-object v0, p0, Lcom/mediatek/mtklogger/settings/MobileLogSettings;->mManager:Lcom/mediatek/mtklogger/framework/MTKLoggerManager;

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    move-result v1

    invoke-virtual {v0, v3, v1}, Lcom/mediatek/mtklogger/framework/MTKLoggerManager;->setAutoStart(IZ)Z

    goto :goto_0
.end method
