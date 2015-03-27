.class public Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;
.super Landroid/preference/PreferenceActivity;
.source "DebugToolboxActivity.java"

# interfaces
.implements Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;
.implements Landroid/preference/Preference$OnPreferenceClickListener;


# static fields
.field private static final TAG:Ljava/lang/String; = "MTKLogger/Debugutils"

.field private static final sAEE_BUILD_INFO:Ljava/lang/String; = "ro.aee.build.info"

.field private static final sAEE_MODE:Ljava/lang/String; = "persist.mtk.aee.mode"

.field private static final sAEE_MODE_STRING:[Ljava/lang/String; = null

.field private static final sDAL_OPTION_STRING:[Ljava/lang/String; = null

.field private static final sDAL_SETTING:Ljava/lang/String; = "persist.mtk.aee.dal"

.field private static final sRO_BUILD_TYPE:Ljava/lang/String; = "ro.build.type"


# instance fields
.field cleanData:Landroid/preference/PreferenceScreen;

.field clearDAL:Landroid/preference/PreferenceScreen;

.field private mBoundService:Lcom/mediatek/mtklogger/debugtool/AEEControlService;

.field private mConnection:Landroid/content/ServiceConnection;

.field private mIsBound:Z

.field pref_aeemode:Landroid/preference/ListPreference;

.field pref_daloption:Landroid/preference/ListPreference;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .prologue
    const/4 v5, 0x2

    const/4 v4, 0x1

    const/4 v3, 0x0

    .line 84
    const/4 v0, 0x5

    new-array v0, v0, [Ljava/lang/String;

    const-string v1, ""

    aput-object v1, v0, v3

    const-string v1, "MediatekEngineer"

    aput-object v1, v0, v4

    const-string v1, "MediatekUser"

    aput-object v1, v0, v5

    const/4 v1, 0x3

    const-string v2, "CustomerEngineer"

    aput-object v2, v0, v1

    const/4 v1, 0x4

    const-string v2, "CustomerUser"

    aput-object v2, v0, v1

    sput-object v0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->sAEE_MODE_STRING:[Ljava/lang/String;

    .line 90
    new-array v0, v5, [Ljava/lang/String;

    const-string v1, "EnableDAL"

    aput-object v1, v0, v3

    const-string v1, "DisableDAL"

    aput-object v1, v0, v4

    sput-object v0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->sDAL_OPTION_STRING:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .prologue
    .line 63
    invoke-direct {p0}, Landroid/preference/PreferenceActivity;-><init>()V

    .line 217
    new-instance v0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity$1;

    invoke-direct {v0, p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity$1;-><init>(Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;)V

    iput-object v0, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mConnection:Landroid/content/ServiceConnection;

    return-void
.end method

.method static synthetic access$002(Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;Lcom/mediatek/mtklogger/debugtool/AEEControlService;)Lcom/mediatek/mtklogger/debugtool/AEEControlService;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 63
    iput-object p1, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mBoundService:Lcom/mediatek/mtklogger/debugtool/AEEControlService;

    return-object p1
.end method

.method private static currentAEEMode()Ljava/lang/String;
    .locals 10

    .prologue
    const/4 v1, 0x4

    const/4 v6, 0x1

    .line 268
    const-string v7, "persist.mtk.aee.mode"

    invoke-static {v7}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 269
    .local v4, aeemodeIndexString:Ljava/lang/String;
    if-eqz v4, :cond_0

    invoke-virtual {v4}, Ljava/lang/String;->isEmpty()Z

    move-result v7

    if-nez v7, :cond_0

    .line 270
    invoke-static {v4}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v3

    .line 271
    .local v3, aeemodeIndex:I
    if-lt v3, v6, :cond_0

    if-gt v3, v1, :cond_0

    .line 272
    sget-object v7, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->sAEE_MODE_STRING:[Ljava/lang/String;

    aget-object v2, v7, v3

    .line 281
    .end local v3           #aeemodeIndex:I
    :goto_0
    return-object v2

    .line 276
    :cond_0
    const-string v7, "ro.aee.build.info"

    invoke-static {v7}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 277
    .local v0, aeebuildinfo:Ljava/lang/String;
    const-string v7, "ro.build.type"

    invoke-static {v7}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 278
    .local v5, robuildtype:Ljava/lang/String;
    const-string v7, "MTKLogger/Debugutils"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "[ro.build.type]"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, "[ro.aee.build.info]"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Lcom/mediatek/xlog/Xlog;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 279
    const-string v7, "mtk"

    invoke-virtual {v0, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_1

    const/4 v1, 0x2

    .line 280
    .local v1, aeebuildinfoInt:I
    :cond_1
    const-string v7, "eng"

    invoke-virtual {v5, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_2

    .line 281
    .local v6, robuildtypeInt:I
    :goto_1
    sget-object v7, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->sAEE_MODE_STRING:[Ljava/lang/String;

    sub-int v8, v1, v6

    aget-object v2, v7, v8

    goto :goto_0

    .line 280
    .end local v6           #robuildtypeInt:I
    :cond_2
    const/4 v6, 0x0

    goto :goto_1
.end method

.method private static currentDalOption()Ljava/lang/String;
    .locals 4

    .prologue
    .line 285
    const-string v2, "persist.mtk.aee.dal"

    invoke-static {v2}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 286
    .local v1, dalOptionIndexString:Ljava/lang/String;
    if-eqz v1, :cond_0

    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_0

    .line 287
    invoke-static {v1}, Ljava/lang/Integer;->valueOf(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v0

    .line 288
    .local v0, dalOptionIndex:I
    if-nez v0, :cond_0

    .line 289
    sget-object v2, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->sDAL_OPTION_STRING:[Ljava/lang/String;

    const/4 v3, 0x1

    aget-object v2, v2, v3

    .line 291
    .end local v0           #dalOptionIndex:I
    :goto_0
    return-object v2

    :cond_0
    sget-object v2, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->sDAL_OPTION_STRING:[Ljava/lang/String;

    const/4 v3, 0x0

    aget-object v2, v2, v3

    goto :goto_0
.end method

.method private static getProperty(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .parameter "prop"

    .prologue
    .line 295
    invoke-static {p0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method


# virtual methods
.method doBindService()V
    .locals 3

    .prologue
    const/4 v2, 0x1

    .line 248
    new-instance v0, Landroid/content/Intent;

    const-class v1, Lcom/mediatek/mtklogger/debugtool/AEEControlService;

    invoke-direct {v0, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    iget-object v1, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mConnection:Landroid/content/ServiceConnection;

    invoke-virtual {p0, v0, v1, v2}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    .line 250
    iput-boolean v2, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mIsBound:Z

    .line 251
    return-void
.end method

.method doUnbindService()V
    .locals 1

    .prologue
    .line 254
    iget-boolean v0, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mIsBound:Z

    if-eqz v0, :cond_0

    .line 256
    iget-object v0, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mConnection:Landroid/content/ServiceConnection;

    invoke-virtual {p0, v0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->unbindService(Landroid/content/ServiceConnection;)V

    .line 257
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mIsBound:Z

    .line 259
    :cond_0
    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 7
    .parameter "savedInstanceState"

    .prologue
    .line 96
    invoke-super {p0, p1}, Landroid/preference/PreferenceActivity;->onCreate(Landroid/os/Bundle;)V

    .line 99
    const v5, 0x7f040002

    invoke-virtual {p0, v5}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->addPreferencesFromResource(I)V

    .line 102
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v5

    const v6, 0x7f07007f

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/preference/PreferenceScreen;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v5

    check-cast v5, Landroid/preference/ListPreference;

    iput-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    .line 104
    const-string v5, "ro.aee.build.info"

    invoke-static {v5}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 105
    .local v0, aeebuildinfo:Ljava/lang/String;
    if-eqz v0, :cond_0

    const-string v5, "mtk"

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_1

    .line 106
    :cond_0
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    const v6, 0x7f060004

    invoke-virtual {v5, v6}, Landroid/preference/ListPreference;->setEntries(I)V

    .line 107
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    const v6, 0x7f060005

    invoke-virtual {v5, v6}, Landroid/preference/ListPreference;->setEntryValues(I)V

    .line 110
    :cond_1
    invoke-static {}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->currentAEEMode()Ljava/lang/String;

    move-result-object v1

    .line 111
    .local v1, deviceAEEmode:Ljava/lang/String;
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    invoke-virtual {v5, v1}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    .line 112
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    invoke-virtual {v5, v1}, Landroid/preference/ListPreference;->setSummary(Ljava/lang/CharSequence;)V

    .line 114
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v5

    const v6, 0x7f070083

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/preference/PreferenceScreen;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v5

    check-cast v5, Landroid/preference/ListPreference;

    iput-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_daloption:Landroid/preference/ListPreference;

    .line 116
    invoke-static {}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->currentDalOption()Ljava/lang/String;

    move-result-object v2

    .line 117
    .local v2, deviceDalOption:Ljava/lang/String;
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_daloption:Landroid/preference/ListPreference;

    invoke-virtual {v5, v2}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    .line 118
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_daloption:Landroid/preference/ListPreference;

    invoke-virtual {v5, v2}, Landroid/preference/ListPreference;->setSummary(Ljava/lang/CharSequence;)V

    .line 120
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v5

    const v6, 0x7f070087

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/preference/PreferenceScreen;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v5

    check-cast v5, Landroid/preference/PreferenceScreen;

    iput-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->clearDAL:Landroid/preference/PreferenceScreen;

    .line 122
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->clearDAL:Landroid/preference/PreferenceScreen;

    invoke-virtual {v5, p0}, Landroid/preference/PreferenceScreen;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 124
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v5

    const v6, 0x7f07008c

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/preference/PreferenceScreen;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v5

    check-cast v5, Landroid/preference/PreferenceScreen;

    iput-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->cleanData:Landroid/preference/PreferenceScreen;

    .line 126
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->cleanData:Landroid/preference/PreferenceScreen;

    invoke-virtual {v5, p0}, Landroid/preference/PreferenceScreen;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 127
    const-string v5, "ro.build.type"

    invoke-static {v5}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 128
    .local v4, robuildtype:Ljava/lang/String;
    if-eqz v4, :cond_2

    const-string v5, "eng"

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_3

    .line 130
    :cond_2
    iget-object v5, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->cleanData:Landroid/preference/PreferenceScreen;

    const/4 v6, 0x0

    invoke-virtual {v5, v6}, Landroid/preference/PreferenceScreen;->setEnabled(Z)V

    .line 133
    :cond_3
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v5

    const v6, 0x7f07008b

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/preference/PreferenceScreen;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v3

    check-cast v3, Landroid/preference/PreferenceCategory;

    .line 135
    .local v3, device_maintain:Landroid/preference/PreferenceCategory;
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v5

    invoke-virtual {v5, v3}, Landroid/preference/PreferenceScreen;->removePreference(Landroid/preference/Preference;)Z

    .line 137
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->doBindService()V

    .line 138
    return-void
.end method

.method protected onDestroy()V
    .locals 0

    .prologue
    .line 263
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onDestroy()V

    .line 264
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->doUnbindService()V

    .line 265
    return-void
.end method

.method protected onPause()V
    .locals 1

    .prologue
    .line 149
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onPause()V

    .line 151
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v0

    invoke-virtual {v0}, Landroid/preference/PreferenceScreen;->getSharedPreferences()Landroid/content/SharedPreferences;

    move-result-object v0

    invoke-interface {v0, p0}, Landroid/content/SharedPreferences;->unregisterOnSharedPreferenceChangeListener(Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;)V

    .line 153
    return-void
.end method

.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 3
    .parameter "preference"

    .prologue
    const/4 v1, 0x1

    .line 199
    iget-object v2, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->clearDAL:Landroid/preference/PreferenceScreen;

    invoke-virtual {p1, v2}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 201
    :try_start_0
    iget-object v2, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mBoundService:Lcom/mediatek/mtklogger/debugtool/AEEControlService;

    invoke-virtual {v2}, Lcom/mediatek/mtklogger/debugtool/AEEControlService;->clearDAL()V
    :try_end_0
    .catch Ljava/lang/NullPointerException; {:try_start_0 .. :try_end_0} :catch_0

    .line 214
    :goto_0
    return v1

    .line 203
    :catch_0
    move-exception v0

    .line 204
    .local v0, e:Ljava/lang/NullPointerException;
    const-string v1, "MTKLogger/Debugutils"

    const-string v2, "AEE Service is not started"

    invoke-static {v1, v2}, Lcom/mediatek/xlog/Xlog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 214
    .end local v0           #e:Ljava/lang/NullPointerException;
    :cond_0
    :goto_1
    const/4 v1, 0x0

    goto :goto_0

    .line 206
    :cond_1
    iget-object v2, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->cleanData:Landroid/preference/PreferenceScreen;

    invoke-virtual {p1, v2}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 208
    :try_start_1
    iget-object v2, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mBoundService:Lcom/mediatek/mtklogger/debugtool/AEEControlService;

    invoke-virtual {v2}, Lcom/mediatek/mtklogger/debugtool/AEEControlService;->cleanData()V
    :try_end_1
    .catch Ljava/lang/NullPointerException; {:try_start_1 .. :try_end_1} :catch_1

    goto :goto_0

    .line 210
    :catch_1
    move-exception v0

    .line 211
    .restart local v0       #e:Ljava/lang/NullPointerException;
    const-string v1, "MTKLogger/Debugutils"

    const-string v2, "AEE Service is not started"

    invoke-static {v1, v2}, Lcom/mediatek/xlog/Xlog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1
.end method

.method protected onResume()V
    .locals 1

    .prologue
    .line 142
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onResume()V

    .line 144
    invoke-virtual {p0}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getPreferenceScreen()Landroid/preference/PreferenceScreen;

    move-result-object v0

    invoke-virtual {v0}, Landroid/preference/PreferenceScreen;->getSharedPreferences()Landroid/content/SharedPreferences;

    move-result-object v0

    invoke-interface {v0, p0}, Landroid/content/SharedPreferences;->registerOnSharedPreferenceChangeListener(Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;)V

    .line 145
    return-void
.end method

.method public onSharedPreferenceChanged(Landroid/content/SharedPreferences;Ljava/lang/String;)V
    .locals 9
    .parameter "sharedPreferences"
    .parameter "key"

    .prologue
    const/4 v8, 0x0

    .line 156
    const v6, 0x7f07007f

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {p2, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_3

    .line 157
    invoke-interface {p1, p2, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 158
    .local v5, modevalue:Ljava/lang/String;
    if-nez v5, :cond_1

    .line 159
    const-string v6, "MTKLogger/Debugutils"

    const-string v7, "AEE working mode is set to NULL."

    invoke-static {v6, v7}, Lcom/mediatek/xlog/Xlog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 195
    .end local v5           #modevalue:Ljava/lang/String;
    :cond_0
    :goto_0
    return-void

    .line 163
    .restart local v5       #modevalue:Ljava/lang/String;
    :cond_1
    :try_start_0
    iget-object v6, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mBoundService:Lcom/mediatek/mtklogger/debugtool/AEEControlService;

    invoke-virtual {v6, v5}, Lcom/mediatek/mtklogger/debugtool/AEEControlService;->changeAEEMode(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/NullPointerException; {:try_start_0 .. :try_end_0} :catch_0

    .line 167
    :goto_1
    invoke-static {}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->currentAEEMode()Ljava/lang/String;

    move-result-object v1

    .line 168
    .local v1, deviceAEEmode:Ljava/lang/String;
    invoke-virtual {v1, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_2

    .line 169
    iget-object v6, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    invoke-virtual {v6, v1}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    .line 170
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Change debug level ["

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "-->"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "] too fequent, please retry later."

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 172
    .local v4, errorString:Ljava/lang/String;
    const-string v6, "MTKLogger/Debugutils"

    invoke-static {v6, v4}, Lcom/mediatek/xlog/Xlog;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 173
    const/4 v6, 0x0

    invoke-static {p0, v4, v6}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v6

    invoke-virtual {v6}, Landroid/widget/Toast;->show()V

    .line 175
    .end local v4           #errorString:Ljava/lang/String;
    :cond_2
    iget-object v6, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_aeemode:Landroid/preference/ListPreference;

    invoke-virtual {v6, v1}, Landroid/preference/ListPreference;->setSummary(Ljava/lang/CharSequence;)V

    .line 178
    .end local v1           #deviceAEEmode:Ljava/lang/String;
    .end local v5           #modevalue:Ljava/lang/String;
    :cond_3
    const v6, 0x7f070083

    invoke-virtual {p0, v6}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {p2, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_0

    .line 179
    invoke-interface {p1, p2, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 180
    .local v0, dal_option:Ljava/lang/String;
    if-nez v0, :cond_4

    .line 181
    const-string v6, "MTKLogger/Debugutils"

    const-string v7, "DAL setting mode is set to NULL."

    invoke-static {v6, v7}, Lcom/mediatek/xlog/Xlog;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 164
    .end local v0           #dal_option:Ljava/lang/String;
    .restart local v5       #modevalue:Ljava/lang/String;
    :catch_0
    move-exception v3

    .line 165
    .local v3, e:Ljava/lang/NullPointerException;
    const-string v6, "MTKLogger/Debugutils"

    const-string v7, "AEE Service is not started"

    invoke-static {v6, v7}, Lcom/mediatek/xlog/Xlog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 185
    .end local v3           #e:Ljava/lang/NullPointerException;
    .end local v5           #modevalue:Ljava/lang/String;
    .restart local v0       #dal_option:Ljava/lang/String;
    :cond_4
    :try_start_1
    iget-object v6, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->mBoundService:Lcom/mediatek/mtklogger/debugtool/AEEControlService;

    invoke-virtual {v6, v0}, Lcom/mediatek/mtklogger/debugtool/AEEControlService;->dalSetting(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/NullPointerException; {:try_start_1 .. :try_end_1} :catch_1

    .line 189
    :goto_2
    invoke-static {}, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->currentDalOption()Ljava/lang/String;

    move-result-object v2

    .line 190
    .local v2, deviceDalOption:Ljava/lang/String;
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-nez v6, :cond_5

    .line 191
    iget-object v6, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_daloption:Landroid/preference/ListPreference;

    invoke-virtual {v6, v2}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    .line 193
    :cond_5
    iget-object v6, p0, Lcom/mediatek/mtklogger/debugtool/DebugToolboxActivity;->pref_daloption:Landroid/preference/ListPreference;

    invoke-virtual {v6, v2}, Landroid/preference/ListPreference;->setSummary(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .line 186
    .end local v2           #deviceDalOption:Ljava/lang/String;
    :catch_1
    move-exception v3

    .line 187
    .restart local v3       #e:Ljava/lang/NullPointerException;
    const-string v6, "MTKLogger/Debugutils"

    const-string v7, "DAL setting error"

    invoke-static {v6, v7}, Lcom/mediatek/xlog/Xlog;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_2
.end method
