package com.mediatek.mtklogger.framework;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.mediatek.mtklogger.IMTKLoggerManager;
import com.mediatek.mtklogger.IMTKLoggerManager.Stub;
import com.mediatek.mtklogger.utils.Utils;

public class MTKLoggerManager
{
  private static final String TAG = "MTKLogger/MTKLoggerManager";
  private Context mContext = null;
  private IMTKLoggerManager mService = null;
  ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Utils.logi("MTKLogger/MTKLoggerManager", "Bind to service successfully");
      MTKLoggerManager.access$002(MTKLoggerManager.this, IMTKLoggerManager.Stub.asInterface(paramAnonymousIBinder));
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      Utils.logi("MTKLogger/MTKLoggerManager", "-->onServiceDisconnected");
      MTKLoggerManager.access$002(MTKLoggerManager.this, null);
    }
  };
  
  public MTKLoggerManager(Context paramContext)
  {
    this.mContext = paramContext;
    initService();
  }
  
  private void initService()
  {
    Utils.logd("MTKLogger/MTKLoggerManager", "-->initService()");
    Intent localIntent = new Intent("com.mediatek.mtklogger.MTKLoggerService");
    this.mContext.startService(localIntent);
    if (!this.mContext.bindService(localIntent, this.mServiceConnection, 1))
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to bind to MTKLoggerService");
      this.mContext.unbindService(this.mServiceConnection);
      this.mContext.startService(localIntent);
    }
  }
  
  public boolean clearLog()
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.clearLog();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public void free()
  {
    this.mContext.unbindService(this.mServiceConnection);
  }
  
  public int getCurrentRunningStage()
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return 0;
    }
    try
    {
      int i = this.mService.getCurrentRunningStage();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return 0;
  }
  
  public int getModemLogRunningDetailStatus()
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return -1;
    }
    try
    {
      int i = this.mService.getLogRunningStatus(2);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return 0;
  }
  
  public boolean runCommand(String paramString)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.runCommand(paramString);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean setAutoStart(int paramInt, boolean paramBoolean)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.setAutoStart(paramInt, paramBoolean);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean setLogSize(int paramInt1, int paramInt2)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.setLogSize(paramInt1, paramInt2);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean setMobileSubLogEnableState(int paramInt, boolean paramBoolean)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.setSubLogEnableState(1, paramInt, paramBoolean);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean startLog(int paramInt)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.startLog(paramInt);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean stopCommand(String paramString)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.stopCommand();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean stopLog(int paramInt)
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.stopLog(paramInt);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
  
  public boolean tagLog()
  {
    if (this.mService == null)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Service has not been bind to yet.");
      return false;
    }
    try
    {
      boolean bool = this.mService.tagLog();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Utils.loge("MTKLogger/MTKLoggerManager", "Fail to call service API.", localRemoteException);
    }
    return false;
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.MTKLoggerManager
 * JD-Core Version:    0.7.0.1
 */