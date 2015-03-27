package com.mediatek.mtklogger.debugtool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.mediatek.xlog.Xlog;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AEEControlService
  extends Service
{
  protected static final String COMMAND_CLEAN_DATA = "rm -r /data/core/ /data/anr";
  protected static final String COMMAND_CLEAR_DAL = "aee -c dal";
  private static final String TAG = "MTKLogger/Debugutils";
  protected Map<String, String> commandMap;
  private final IBinder mBinder = new LocalBinder();
  
  private static Map<String, String> buildCommandList(String... paramVarArgs)
  {
    HashMap localHashMap = new HashMap();
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      String[] arrayOfString = paramVarArgs[j].split("#");
      localHashMap.put(arrayOfString[0], arrayOfString[1]);
    }
    return localHashMap;
  }
  
  private static StringBuffer systemexec(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Process localProcess;
    BufferedReader localBufferedReader;
    try
    {
      localProcess = Runtime.getRuntime().exec(paramString);
      localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      new String();
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        localStringBuffer.append(str + "\n");
      }
      Xlog.d("MTKLogger/Debugutils", localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Xlog.e("MTKLogger/Debugutils", "Operation failed.");
      return localStringBuffer;
    }
    localProcess.waitFor();
    localBufferedReader.close();
    return localStringBuffer;
  }
  
  public void changeAEEMode(String paramString)
  {
    String str = (String)this.commandMap.get(paramString);
    if (str != null) {
      systemexec(str);
    }
  }
  
  public void cleanData()
  {
    String str = (String)this.commandMap.get("AEECleanData");
    if (str != null) {
      systemexec(str);
    }
    Xlog.i("MTKLogger/Debugutils", "Device /data partition cleaned up.");
    Toast.makeText(this, "Device /data partition cleaned up.", 0).show();
  }
  
  public void clearDAL()
  {
    String str = (String)this.commandMap.get("AEEClearDAL");
    if (str != null) {
      systemexec(str);
    }
    Xlog.d("MTKLogger/Debugutils", "Device AEE red screen cleared.");
  }
  
  public void dalSetting(String paramString)
  {
    String str = (String)this.commandMap.get(paramString);
    if (str != null) {
      systemexec(str);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public void onCreate()
  {
    this.commandMap = buildCommandList(new String[] { "AEECleanData#rm -r /data/core /data/anr /data/tombstones", "AEEClearDAL#aee -c dal", "MediatekEngineer#aee -m 1", "MediatekUser#aee -m 2", "CustomerEngineer#aee -m 3", "CustomerUser#aee -m 4", "EnableDAL#aee -s on", "DisableDAL#aee -s off" });
  }
  
  public void onDestroy() {}
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Xlog.i("AEEControlService", "Received start id " + paramInt2 + ": " + paramIntent);
    return 1;
  }
  
  public class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
    
    AEEControlService getService()
    {
      return AEEControlService.this;
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.debugtool.AEEControlService
 * JD-Core Version:    0.7.0.1
 */