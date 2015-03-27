package com.mediatek.mtklogger.framework;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.LocalSocketAddress.Namespace;
import android.os.Handler;
import android.os.Message;
import com.mediatek.mtklogger.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LogConnection
{
  private static final String TAG = "MTKLogger/LogConnection";
  private final int BUFFER_SIZE = 100;
  LocalSocketAddress address;
  Handler mHandler = null;
  private InputStream mInputStream;
  private int mInstanceIndex = -1;
  private Message mMsg;
  private OutputStream mOutputStream;
  private boolean mShouldStop = false;
  private Thread mlistenThread = null;
  LocalSocket socket;
  
  public LogConnection(int paramInt, String paramString, LocalSocketAddress.Namespace paramNamespace, Handler paramHandler)
  {
    this(paramString, paramNamespace, paramHandler);
    this.mInstanceIndex = paramInt;
  }
  
  public LogConnection(String paramString, LocalSocketAddress.Namespace paramNamespace, Handler paramHandler)
  {
    this.mHandler = paramHandler;
    this.socket = new LocalSocket();
    this.address = new LocalSocketAddress(paramString, paramNamespace);
  }
  
  public LogConnection(String paramString, Handler paramHandler)
  {
    this(paramString, LocalSocketAddress.Namespace.ABSTRACT, paramHandler);
  }
  
  public boolean connect()
  {
    Utils.logd("MTKLogger/LogConnection", "-->connect(), socketName=" + this.address.getName());
    try
    {
      this.socket.connect(this.address);
      this.mOutputStream = this.socket.getOutputStream();
      this.mInputStream = this.socket.getInputStream();
      this.mlistenThread = new Thread()
      {
        public void run()
        {
          LogConnection.this.listen();
        }
      };
      this.mlistenThread.start();
      Utils.logd("MTKLogger/LogConnection", "Connect to native socket OK. And start local monitor thread now");
      return true;
    }
    catch (IOException localIOException)
    {
      Utils.logw("MTKLogger/LogConnection", "Communications error, Exception happens when connect to socket server");
    }
    return false;
  }
  
  public void dealWithResponse(byte[] paramArrayOfByte, Handler paramHandler) {}
  
  public boolean isConnected()
  {
    return (this.socket != null) && (this.socket.isConnected());
  }
  
  public void listen()
  {
    byte[] arrayOfByte1 = new byte[100];
    Utils.logd("MTKLogger/LogConnection", "Monitor thread running");
    for (;;)
    {
      if (!this.mShouldStop) {}
      try
      {
        i = this.mInputStream.read(arrayOfByte1, 0, 100);
        if (i >= 0) {
          break label125;
        }
        Utils.loge("MTKLogger/LogConnection", "Get a empty response from native layer, stop listen.");
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          int i;
          byte[] arrayOfByte2;
          Utils.loge("MTKLogger/LogConnection", "read failed", localIOException);
        }
      }
      if (!this.mShouldStop)
      {
        Utils.loge("MTKLogger/LogConnection", "listen break at address: " + this.address.getName());
        this.mMsg = this.mHandler.obtainMessage(22);
        if (this.mInstanceIndex > 0) {
          this.mMsg.arg1 = this.mInstanceIndex;
        }
        this.mHandler.sendMessage(this.mMsg);
      }
      return;
      label125:
      Utils.logv("MTKLogger/LogConnection", "Response from native byte size=" + i);
      arrayOfByte2 = new byte[i];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
      dealWithResponse(arrayOfByte2, this.mHandler);
    }
  }
  
  /* Error */
  public boolean sendCmd(String paramString)
  {
    // Byte code:
    //   0: ldc 8
    //   2: new 70	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   9: ldc 185
    //   11: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_1
    //   15: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 187
    //   20: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: aload_0
    //   24: getfield 57	com/mediatek/mtklogger/framework/LogConnection:address	Landroid/net/LocalSocketAddress;
    //   27: invokevirtual 81	android/net/LocalSocketAddress:getName	()Ljava/lang/String;
    //   30: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: ldc 189
    //   35: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   41: invokestatic 192	com/mediatek/mtklogger/utils/Utils:logi	(Ljava/lang/String;Ljava/lang/String;)V
    //   44: iconst_0
    //   45: istore_2
    //   46: aload_0
    //   47: monitorenter
    //   48: aload_0
    //   49: getfield 99	com/mediatek/mtklogger/framework/LogConnection:mOutputStream	Ljava/io/OutputStream;
    //   52: ifnonnull +18 -> 70
    //   55: ldc 8
    //   57: ldc 194
    //   59: invokestatic 141	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;)V
    //   62: aload_0
    //   63: invokevirtual 197	com/mediatek/mtklogger/framework/LogConnection:stop	()V
    //   66: aload_0
    //   67: monitorexit
    //   68: iload_2
    //   69: ireturn
    //   70: new 70	java/lang/StringBuilder
    //   73: dup
    //   74: aload_1
    //   75: invokespecial 200	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   78: astore 4
    //   80: aload 4
    //   82: iconst_0
    //   83: invokevirtual 203	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: ldc 8
    //   89: ldc 205
    //   91: invokestatic 90	com/mediatek/mtklogger/utils/Utils:logd	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: aload_0
    //   95: getfield 99	com/mediatek/mtklogger/framework/LogConnection:mOutputStream	Ljava/io/OutputStream;
    //   98: aload 4
    //   100: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: invokevirtual 211	java/lang/String:getBytes	()[B
    //   106: invokevirtual 217	java/io/OutputStream:write	([B)V
    //   109: aload_0
    //   110: getfield 99	com/mediatek/mtklogger/framework/LogConnection:mOutputStream	Ljava/io/OutputStream;
    //   113: invokevirtual 220	java/io/OutputStream:flush	()V
    //   116: iconst_1
    //   117: istore_2
    //   118: goto -52 -> 66
    //   121: astore 6
    //   123: ldc 8
    //   125: ldc 222
    //   127: aload 6
    //   129: invokestatic 181	com/mediatek/mtklogger/utils/Utils:loge	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   132: aload_0
    //   133: aconst_null
    //   134: putfield 99	com/mediatek/mtklogger/framework/LogConnection:mOutputStream	Ljava/io/OutputStream;
    //   137: iconst_0
    //   138: istore_2
    //   139: goto -73 -> 66
    //   142: astore_3
    //   143: aload_0
    //   144: monitorexit
    //   145: aload_3
    //   146: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	147	0	this	LogConnection
    //   0	147	1	paramString	String
    //   45	94	2	bool	boolean
    //   142	4	3	localObject	Object
    //   78	21	4	localStringBuilder	java.lang.StringBuilder
    //   121	7	6	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   94	116	121	java/io/IOException
    //   48	66	142	finally
    //   66	68	142	finally
    //   70	94	142	finally
    //   94	116	142	finally
    //   123	137	142	finally
    //   143	145	142	finally
  }
  
  public void stop()
  {
    Utils.logd("MTKLogger/LogConnection", "-->stop()");
    this.mShouldStop = true;
    if (this.socket == null) {
      return;
    }
    try
    {
      this.socket.shutdownInput();
      this.socket.close();
      this.mlistenThread = null;
      this.socket = null;
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        Utils.loge("MTKLogger/LogConnection", "Exception happended while closing socket: " + localIOException);
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.framework.LogConnection
 * JD-Core Version:    0.7.0.1
 */