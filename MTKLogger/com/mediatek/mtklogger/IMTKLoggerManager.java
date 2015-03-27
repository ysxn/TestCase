package com.mediatek.mtklogger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IMTKLoggerManager
  extends IInterface
{
  public abstract boolean clearLog()
    throws RemoteException;
  
  public abstract int getCurrentRunningStage()
    throws RemoteException;
  
  public abstract int getLogRunningStatus(int paramInt)
    throws RemoteException;
  
  public abstract boolean runCommand(String paramString)
    throws RemoteException;
  
  public abstract boolean setAutoStart(int paramInt, boolean paramBoolean)
    throws RemoteException;
  
  public abstract boolean setLogSize(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract boolean setSubLogEnableState(int paramInt1, int paramInt2, boolean paramBoolean)
    throws RemoteException;
  
  public abstract boolean startLog(int paramInt)
    throws RemoteException;
  
  public abstract boolean stopCommand()
    throws RemoteException;
  
  public abstract boolean stopLog(int paramInt)
    throws RemoteException;
  
  public abstract boolean tagLog()
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IMTKLoggerManager
  {
    private static final String DESCRIPTOR = "com.mediatek.mtklogger.IMTKLoggerManager";
    static final int TRANSACTION_clearLog = 8;
    static final int TRANSACTION_getCurrentRunningStage = 10;
    static final int TRANSACTION_getLogRunningStatus = 11;
    static final int TRANSACTION_runCommand = 3;
    static final int TRANSACTION_setAutoStart = 6;
    static final int TRANSACTION_setLogSize = 5;
    static final int TRANSACTION_setSubLogEnableState = 7;
    static final int TRANSACTION_startLog = 1;
    static final int TRANSACTION_stopCommand = 4;
    static final int TRANSACTION_stopLog = 2;
    static final int TRANSACTION_tagLog = 9;
    
    public Stub()
    {
      attachInterface(this, "com.mediatek.mtklogger.IMTKLoggerManager");
    }
    
    public static IMTKLoggerManager asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.mediatek.mtklogger.IMTKLoggerManager");
      if ((localIInterface != null) && ((localIInterface instanceof IMTKLoggerManager))) {
        return (IMTKLoggerManager)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.mediatek.mtklogger.IMTKLoggerManager");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool11 = startLog(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i9 = 0;
        if (bool11) {
          i9 = 1;
        }
        paramParcel2.writeInt(i9);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool10 = stopLog(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i8 = 0;
        if (bool10) {
          i8 = 1;
        }
        paramParcel2.writeInt(i8);
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool9 = runCommand(paramParcel1.readString());
        paramParcel2.writeNoException();
        int i7 = 0;
        if (bool9) {
          i7 = 1;
        }
        paramParcel2.writeInt(i7);
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool8 = stopCommand();
        paramParcel2.writeNoException();
        int i6 = 0;
        if (bool8) {
          i6 = 1;
        }
        paramParcel2.writeInt(i6);
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool7 = setLogSize(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i5 = 0;
        if (bool7) {
          i5 = 1;
        }
        paramParcel2.writeInt(i5);
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        int i3 = paramParcel1.readInt();
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool5 = true;; bool5 = false)
        {
          boolean bool6 = setAutoStart(i3, bool5);
          paramParcel2.writeNoException();
          int i4 = 0;
          if (bool6) {
            i4 = 1;
          }
          paramParcel2.writeInt(i4);
          return true;
        }
      case 7: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        int n = paramParcel1.readInt();
        int i1 = paramParcel1.readInt();
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool3 = true;; bool3 = false)
        {
          boolean bool4 = setSubLogEnableState(n, i1, bool3);
          paramParcel2.writeNoException();
          int i2 = 0;
          if (bool4) {
            i2 = 1;
          }
          paramParcel2.writeInt(i2);
          return true;
        }
      case 8: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool2 = clearLog();
        paramParcel2.writeNoException();
        int m = 0;
        if (bool2) {
          m = 1;
        }
        paramParcel2.writeInt(m);
        return true;
      case 9: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        boolean bool1 = tagLog();
        paramParcel2.writeNoException();
        int k = 0;
        if (bool1) {
          k = 1;
        }
        paramParcel2.writeInt(k);
        return true;
      case 10: 
        paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
        int j = getCurrentRunningStage();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      }
      paramParcel1.enforceInterface("com.mediatek.mtklogger.IMTKLoggerManager");
      int i = getLogRunningStatus(paramParcel1.readInt());
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }
    
    private static class Proxy
      implements IMTKLoggerManager
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public boolean clearLog()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getCurrentRunningStage()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "com.mediatek.mtklogger.IMTKLoggerManager";
      }
      
      public int getLogRunningStatus(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean runCommand(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public boolean setAutoStart(int paramInt, boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_3
        //   2: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore 4
        //   7: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   10: astore 5
        //   12: aload 4
        //   14: ldc 29
        //   16: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   19: aload 4
        //   21: iload_1
        //   22: invokevirtual 58	android/os/Parcel:writeInt	(I)V
        //   25: iload_2
        //   26: ifeq +59 -> 85
        //   29: iload_3
        //   30: istore 7
        //   32: aload 4
        //   34: iload 7
        //   36: invokevirtual 58	android/os/Parcel:writeInt	(I)V
        //   39: aload_0
        //   40: getfield 15	com/mediatek/mtklogger/IMTKLoggerManager$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   43: bipush 6
        //   45: aload 4
        //   47: aload 5
        //   49: iconst_0
        //   50: invokeinterface 39 5 0
        //   55: pop
        //   56: aload 5
        //   58: invokevirtual 42	android/os/Parcel:readException	()V
        //   61: aload 5
        //   63: invokevirtual 46	android/os/Parcel:readInt	()I
        //   66: istore 9
        //   68: iload 9
        //   70: ifeq +21 -> 91
        //   73: aload 5
        //   75: invokevirtual 49	android/os/Parcel:recycle	()V
        //   78: aload 4
        //   80: invokevirtual 49	android/os/Parcel:recycle	()V
        //   83: iload_3
        //   84: ireturn
        //   85: iconst_0
        //   86: istore 7
        //   88: goto -56 -> 32
        //   91: iconst_0
        //   92: istore_3
        //   93: goto -20 -> 73
        //   96: astore 6
        //   98: aload 5
        //   100: invokevirtual 49	android/os/Parcel:recycle	()V
        //   103: aload 4
        //   105: invokevirtual 49	android/os/Parcel:recycle	()V
        //   108: aload 6
        //   110: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	111	0	this	Proxy
        //   0	111	1	paramInt	int
        //   0	111	2	paramBoolean	boolean
        //   1	92	3	i	int
        //   5	99	4	localParcel1	Parcel
        //   10	89	5	localParcel2	Parcel
        //   96	13	6	localObject	Object
        //   30	5	7	j	int
        //   86	1	7	k	int
        //   66	3	9	m	int
        // Exception table:
        //   from	to	target	type
        //   12	25	96	finally
        //   32	68	96	finally
      }
      
      public boolean setLogSize(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public boolean setSubLogEnableState(int paramInt1, int paramInt2, boolean paramBoolean)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore 4
        //   3: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   6: astore 5
        //   8: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   11: astore 6
        //   13: aload 5
        //   15: ldc 29
        //   17: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   20: aload 5
        //   22: iload_1
        //   23: invokevirtual 58	android/os/Parcel:writeInt	(I)V
        //   26: aload 5
        //   28: iload_2
        //   29: invokevirtual 58	android/os/Parcel:writeInt	(I)V
        //   32: iload_3
        //   33: ifeq +61 -> 94
        //   36: iload 4
        //   38: istore 8
        //   40: aload 5
        //   42: iload 8
        //   44: invokevirtual 58	android/os/Parcel:writeInt	(I)V
        //   47: aload_0
        //   48: getfield 15	com/mediatek/mtklogger/IMTKLoggerManager$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   51: bipush 7
        //   53: aload 5
        //   55: aload 6
        //   57: iconst_0
        //   58: invokeinterface 39 5 0
        //   63: pop
        //   64: aload 6
        //   66: invokevirtual 42	android/os/Parcel:readException	()V
        //   69: aload 6
        //   71: invokevirtual 46	android/os/Parcel:readInt	()I
        //   74: istore 10
        //   76: iload 10
        //   78: ifeq +22 -> 100
        //   81: aload 6
        //   83: invokevirtual 49	android/os/Parcel:recycle	()V
        //   86: aload 5
        //   88: invokevirtual 49	android/os/Parcel:recycle	()V
        //   91: iload 4
        //   93: ireturn
        //   94: iconst_0
        //   95: istore 8
        //   97: goto -57 -> 40
        //   100: iconst_0
        //   101: istore 4
        //   103: goto -22 -> 81
        //   106: astore 7
        //   108: aload 6
        //   110: invokevirtual 49	android/os/Parcel:recycle	()V
        //   113: aload 5
        //   115: invokevirtual 49	android/os/Parcel:recycle	()V
        //   118: aload 7
        //   120: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	121	0	this	Proxy
        //   0	121	1	paramInt1	int
        //   0	121	2	paramInt2	int
        //   0	121	3	paramBoolean	boolean
        //   1	101	4	i	int
        //   6	108	5	localParcel1	Parcel
        //   11	98	6	localParcel2	Parcel
        //   106	13	7	localObject	Object
        //   38	5	8	j	int
        //   95	1	8	k	int
        //   74	3	10	m	int
        // Exception table:
        //   from	to	target	type
        //   13	32	106	finally
        //   40	76	106	finally
      }
      
      /* Error */
      public boolean startLog(int paramInt)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_2
        //   2: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore_3
        //   6: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   9: astore 4
        //   11: aload_3
        //   12: ldc 29
        //   14: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   17: aload_3
        //   18: iload_1
        //   19: invokevirtual 58	android/os/Parcel:writeInt	(I)V
        //   22: aload_0
        //   23: getfield 15	com/mediatek/mtklogger/IMTKLoggerManager$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   26: iconst_1
        //   27: aload_3
        //   28: aload 4
        //   30: iconst_0
        //   31: invokeinterface 39 5 0
        //   36: pop
        //   37: aload 4
        //   39: invokevirtual 42	android/os/Parcel:readException	()V
        //   42: aload 4
        //   44: invokevirtual 46	android/os/Parcel:readInt	()I
        //   47: istore 7
        //   49: iload 7
        //   51: ifeq +14 -> 65
        //   54: aload 4
        //   56: invokevirtual 49	android/os/Parcel:recycle	()V
        //   59: aload_3
        //   60: invokevirtual 49	android/os/Parcel:recycle	()V
        //   63: iload_2
        //   64: ireturn
        //   65: iconst_0
        //   66: istore_2
        //   67: goto -13 -> 54
        //   70: astore 5
        //   72: aload 4
        //   74: invokevirtual 49	android/os/Parcel:recycle	()V
        //   77: aload_3
        //   78: invokevirtual 49	android/os/Parcel:recycle	()V
        //   81: aload 5
        //   83: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	84	0	this	Proxy
        //   0	84	1	paramInt	int
        //   1	66	2	bool	boolean
        //   5	73	3	localParcel1	Parcel
        //   9	64	4	localParcel2	Parcel
        //   70	12	5	localObject	Object
        //   47	3	7	i	int
        // Exception table:
        //   from	to	target	type
        //   11	49	70	finally
      }
      
      public boolean stopCommand()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean stopLog(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean tagLog()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.mediatek.mtklogger.IMTKLoggerManager");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.IMTKLoggerManager
 * JD-Core Version:    0.7.0.1
 */