.class Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;
.super Lcom/mediatek/mtklogger/framework/LogConnection;
.source "MultiModemLog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/mtklogger/framework/MultiModemLog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ModemLogConnection"
.end annotation


# instance fields
.field private mConnType:I

.field final synthetic this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;


# direct methods
.method public constructor <init>(Lcom/mediatek/mtklogger/framework/MultiModemLog;ILjava/lang/String;Landroid/os/Handler;)V
    .locals 1
    .parameter
    .parameter "connType"
    .parameter "socketName"
    .parameter "handler"

    .prologue
    .line 816
    iput-object p1, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    .line 817
    sget-object v0, Landroid/net/LocalSocketAddress$Namespace;->ABSTRACT:Landroid/net/LocalSocketAddress$Namespace;

    invoke-direct {p0, p2, p3, v0, p4}, Lcom/mediatek/mtklogger/framework/LogConnection;-><init>(ILjava/lang/String;Landroid/net/LocalSocketAddress$Namespace;Landroid/os/Handler;)V

    .line 814
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;->mConnType:I

    .line 818
    iput p2, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;->mConnType:I

    .line 819
    return-void
.end method


# virtual methods
.method public dealWithResponse([BLandroid/os/Handler;)V
    .locals 10
    .parameter "respBuffer"
    .parameter "handler"

    .prologue
    .line 823
    invoke-super {p0, p1, p2}, Lcom/mediatek/mtklogger/framework/LogConnection;->dealWithResponse([BLandroid/os/Handler;)V

    .line 824
    new-instance v4, Ljava/lang/String;

    invoke-direct {v4, p1}, Ljava/lang/String;-><init>([B)V

    .line 825
    .local v4, respStr:Ljava/lang/String;
    const-string v6, "MTKLogger/MultiModemLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "-->dealWithResponse(), resp="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    new-instance v8, Ljava/lang/String;

    invoke-direct {v8, p1}, Ljava/lang/String;-><init>([B)V

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logi(Ljava/lang/String;Ljava/lang/String;)V

    .line 826
    if-eqz p1, :cond_0

    array-length v6, p1

    if-nez v6, :cond_1

    .line 827
    :cond_0
    const-string v6, "MTKLogger/MultiModemLog"

    const-string v7, "Get an empty response from native, ignore it."

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logw(Ljava/lang/String;Ljava/lang/String;)V

    .line 878
    :goto_0
    return-void

    .line 831
    :cond_1
    const/4 v3, 0x0

    .line 832
    .local v3, msgType:I
    const/4 v1, 0x0

    .line 833
    .local v1, extraStr:Ljava/lang/String;
    const-string v6, "MEMORYDUMP_START"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_4

    .line 834
    const/16 v3, 0x46

    .line 872
    :cond_2
    :goto_1
    invoke-virtual {p2, v3}, Landroid/os/Handler;->obtainMessage(I)Landroid/os/Message;

    move-result-object v2

    .line 873
    .local v2, msg:Landroid/os/Message;
    iget v6, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;->mConnType:I

    iput v6, v2, Landroid/os/Message;->arg1:I

    .line 874
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v6

    if-nez v6, :cond_3

    .line 875
    iput-object v1, v2, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 877
    :cond_3
    invoke-virtual {v2}, Landroid/os/Message;->sendToTarget()V

    goto :goto_0

    .line 835
    .end local v2           #msg:Landroid/os/Message;
    :cond_4
    const-string v6, "MEMORYDUMP_DONE"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_6

    .line 836
    const/16 v3, 0x47

    .line 838
    const-string v6, "MEMORYDUMP_DONE"

    invoke-virtual {v6}, Ljava/lang/String;->length()I

    move-result v6

    add-int/lit8 v5, v6, 0x1

    .line 839
    .local v5, start:I
    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v6

    add-int/lit8 v0, v6, -0x1

    .line 840
    .local v0, end:I
    if-ge v5, v0, :cond_5

    .line 841
    invoke-virtual {v4, v5, v0}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    .line 848
    :goto_2
    const-string v6, "MTKLogger/MultiModemLog"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Try to save modem assert file name to file, file name="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    .line 849
    iget-object v6, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;->this$0:Lcom/mediatek/mtklogger/framework/MultiModemLog;

    iget-object v6, v6, Lcom/mediatek/mtklogger/framework/MultiModemLog;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v6}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    const-string v7, "md_assert_file_path"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    iget v9, p0, Lcom/mediatek/mtklogger/framework/MultiModemLog$ModemLogConnection;->mConnType:I

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, ";"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-interface {v6, v7, v8}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    invoke-interface {v6}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 851
    const-string v6, "MTKLogger/MultiModemLog"

    const-string v7, "Save modem assert file name to file done"

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->logd(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    .line 843
    :cond_5
    const-string v6, "MTKLogger/MultiModemLog"

    const-string v7, "Invalid dump done message from native."

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_2

    .line 852
    .end local v0           #end:I
    .end local v5           #start:I
    :cond_6
    const-string v6, "LOGFILE_NOTEXIST"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_7

    .line 853
    const/16 v3, 0x48

    goto/16 :goto_1

    .line 854
    :cond_7
    const-string v6, "FAIL_SENDFILTER"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_8

    .line 855
    const/16 v3, 0x49

    goto/16 :goto_1

    .line 856
    :cond_8
    const-string v6, "FAIL_WRITEFILE"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_9

    .line 857
    const/16 v3, 0x4a

    goto/16 :goto_1

    .line 858
    :cond_9
    const-string v6, "SDCARD_FULL"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_a

    .line 859
    const/16 v3, 0x4b

    goto/16 :goto_1

    .line 860
    :cond_a
    const-string v6, "ispaused"

    invoke-virtual {v4, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_2

    .line 861
    const/16 v3, 0x4c

    .line 863
    const-string v6, "ispaused"

    invoke-virtual {v6}, Ljava/lang/String;->length()I

    move-result v6

    add-int/lit8 v5, v6, 0x1

    .line 864
    .restart local v5       #start:I
    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v0

    .line 865
    .restart local v0       #end:I
    if-ge v5, v0, :cond_b

    .line 866
    add-int/lit8 v6, v5, 0x1

    invoke-virtual {v4, v5, v6}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    goto/16 :goto_1

    .line 868
    :cond_b
    const-string v6, "MTKLogger/MultiModemLog"

    const-string v7, "Invalid puase status response from native."

    invoke-static {v6, v7}, Lcom/mediatek/mtklogger/utils/Utils;->loge(Ljava/lang/String;Ljava/lang/String;)V

    goto/16 :goto_1
.end method
