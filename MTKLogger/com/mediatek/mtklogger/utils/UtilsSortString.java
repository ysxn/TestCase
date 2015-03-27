package com.mediatek.mtklogger.utils;

import java.util.Comparator;

public class UtilsSortString
  implements Comparator<String>
{
  public static final int DOWM = -1;
  public static final int UP = 1;
  private int state;
  
  public UtilsSortString() {}
  
  public UtilsSortString(int paramInt)
  {
    this.state = paramInt;
  }
  
  private int sortDown(String paramString1, String paramString2)
  {
    if (paramString1.compareTo(paramString2) > 0) {
      return -1;
    }
    if (paramString1.compareTo(paramString2) < 0) {
      return 1;
    }
    return 0;
  }
  
  private int sortUp(String paramString1, String paramString2)
  {
    if (paramString1.compareTo(paramString2) < 0) {
      return -1;
    }
    if (paramString1.compareTo(paramString2) > 0) {
      return 1;
    }
    return 0;
  }
  
  public int compare(String paramString1, String paramString2)
  {
    if (this.state == -1) {
      return sortDown(paramString1, paramString2);
    }
    return sortUp(paramString1, paramString2);
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.utils.UtilsSortString
 * JD-Core Version:    0.7.0.1
 */