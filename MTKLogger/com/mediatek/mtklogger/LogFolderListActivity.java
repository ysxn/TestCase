package com.mediatek.mtklogger;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LogFolderListActivity
  extends Activity
  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
  private static final int CANCEL_MENU_ID = 11;
  private static final int CLEAR_ALL_MENU_ID = 12;
  public static final String EXTRA_FILTER_DUALMODEM_KEY = "filterDualModemFile";
  public static final SparseArray<String> EXTRA_FILTER_FILES_KEY = new SparseArray();
  public static final String EXTRA_FILTER_FILE_PATH_KEY = "filterFilePath";
  public static final String EXTRA_FROM_WHERE_KEY = "fromWhere";
  public static final String EXTRA_ROOTPATH_KEY = "rootpath";
  public static final String EXTRA_TAGLOG_INPUT_NAME_KEY = "taglogInputName";
  private static final int FINISH_CLEAR_LOG = 1;
  public static final String FROM_TAGLOG = "fromTagLog";
  private static final String TAG = "MTKLogger/LogFolderListActivity";
  private LogFolderAdapter mAdapter;
  private Button mCancelButton;
  private Button mClearAllButton;
  private Dialog mClearLogConfirmDialog;
  private Handler mClearLogProgressHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1)
      {
        LogFolderListActivity.this.mListView.invalidateViews();
        LogFolderListActivity.this.finish();
      }
    }
  };
  private int mIsClearDone;
  private boolean mIsClearing = false;
  private boolean mIsLongClick = false;
  private ListView mListView;
  private List<LogFileItem> mLogFolderList = new ArrayList();
  private String mRootPath;
  
  static
  {
    EXTRA_FILTER_FILES_KEY.put(2, "filterModemFile");
    EXTRA_FILTER_FILES_KEY.put(1, "filterMobileFile");
    EXTRA_FILTER_FILES_KEY.put(4, "filterNetworkFile");
  }
  
  private void clearAllLogs()
  {
    this.mClearLogConfirmDialog = new AlertDialog.Builder(this).setTitle(2131165261).setMessage(2131165262).setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (LogFolderListActivity.this.mIsClearing) {
          return;
        }
        LogFolderListActivity.access$202(LogFolderListActivity.this, true);
        final ProgressDialog localProgressDialog = ProgressDialog.show(LogFolderListActivity.this, LogFolderListActivity.this.getString(2131165265), LogFolderListActivity.this.getString(2131165266), true, false);
        LogFolderListActivity.access$302(LogFolderListActivity.this, LogFolderListActivity.this.mLogFolderList.size());
        Iterator localIterator = LogFolderListActivity.this.mLogFolderList.iterator();
        while (localIterator.hasNext()) {
          new Thread()
          {
            public void run()
            {
              LogFolderListActivity.this.clearAllLogs(new File(LogFolderListActivity.this.mRootPath + File.separator + this.val$LogFileItem.fileName), this.val$LogFileItem.filterFilePath);
              LogFolderListActivity.access$310(LogFolderListActivity.this);
            }
          }.start();
        }
        new Thread()
        {
          public void run()
          {
            while (LogFolderListActivity.this.mIsClearDone > 0) {
              try
              {
                Thread.sleep(500L);
              }
              catch (InterruptedException localInterruptedException)
              {
                localInterruptedException.printStackTrace();
              }
            }
            LogFolderListActivity.access$202(LogFolderListActivity.this, false);
            if (localProgressDialog != null) {
              localProgressDialog.cancel();
            }
            LogFolderListActivity.this.mClearLogProgressHandler.sendEmptyMessage(1);
          }
        }.start();
      }
    }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    }).create();
    this.mClearLogConfirmDialog.show();
  }
  
  private void clearAllLogs(File paramFile, String paramString)
  {
    Utils.logd("MTKLogger/LogFolderListActivity", "clearAllLogs() : dir=" + paramFile.getAbsolutePath() + " filterFilePath=" + paramString);
    if ((!paramFile.exists()) || (!paramFile.isDirectory())) {
      return;
    }
    File localFile1 = new File(paramString);
    int i;
    label74:
    int k;
    label88:
    File localFile2;
    if ((localFile1 != null) && (localFile1.exists()))
    {
      i = 1;
      File[] arrayOfFile = paramFile.listFiles();
      int j = arrayOfFile.length;
      k = 0;
      if (k < j)
      {
        localFile2 = arrayOfFile[k];
        if ((i == 0) || (!localFile2.getName().equals(localFile1.getName()))) {
          break label134;
        }
      }
    }
    for (;;)
    {
      k++;
      break label88;
      break;
      i = 0;
      break label74;
      label134:
      clearLogs(localFile2);
    }
  }
  
  private void clearLogs(File paramFile)
  {
    Utils.logd("MTKLogger/LogFolderListActivity", "dir" + paramFile.getAbsolutePath());
    if (!paramFile.exists()) {
      return;
    }
    if (paramFile.isDirectory())
    {
      File[] arrayOfFile = paramFile.listFiles();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++) {
        clearLogs(arrayOfFile[j]);
      }
    }
    paramFile.delete();
  }
  
  private void findViews()
  {
    this.mListView = ((ListView)findViewById(2131296282));
    this.mClearAllButton = ((Button)findViewById(2131296284));
    this.mCancelButton = ((Button)findViewById(2131296280));
  }
  
  private void initLogItemList(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString))) {
      paramString = Utils.getCurrentLogPath(this) + "/mtklog/";
    }
    Utils.logd("MTKLogger/LogFolderListActivity", "initLogItemList() rootPath = " + paramString);
    if (!new File(paramString).exists()) {}
    File localFile2;
    do
    {
      return;
      this.mRootPath = paramString;
      Iterator localIterator = Utils.LOG_TYPE_SET.iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        File localFile3 = new File(paramString + (String)Utils.LOG_PATH_MAP.get(localInteger.intValue()));
        if ((localFile3.exists()) && (localFile3.listFiles().length > 0)) {
          this.mLogFolderList.add(new LogFileItem((String)Utils.LOG_PATH_MAP.get(localInteger.intValue()), getString(((Integer)Utils.LOG_NAME_MAP.get(localInteger.intValue())).intValue()), getIntent().getStringExtra((String)EXTRA_FILTER_FILES_KEY.get(localInteger.intValue()))));
        }
      }
      File localFile1 = new File(paramString + "dualmdlog");
      if ((localFile1.exists()) && (localFile1.listFiles().length > 0)) {
        this.mLogFolderList.add(new LogFileItem("dualmdlog", getString(2131165198), getIntent().getStringExtra("filterDualModemFile")));
      }
      localFile2 = new File(paramString + "taglog");
    } while ((!localFile2.exists()) || (localFile2.listFiles().length <= 0));
    this.mLogFolderList.add(new LogFileItem("taglog", getString(2131165199), ""));
  }
  
  private void initViews()
  {
    initLogItemList(getIntent().getStringExtra("rootpath"));
    this.mAdapter = new LogFolderAdapter(this);
    this.mListView.setAdapter(this.mAdapter);
  }
  
  private void removeManualButton()
  {
    ((LinearLayout)findViewById(2131296281)).removeView(findViewById(2131296283));
  }
  
  private void setListeners()
  {
    if (this.mListView != null)
    {
      this.mListView.setOnItemClickListener(this);
      this.mListView.setOnItemLongClickListener(this);
    }
    if (this.mClearAllButton != null) {
      this.mClearAllButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Utils.logd("MTKLogger/LogFolderListActivity", "Clear all button is clicked!");
          LogFolderListActivity.this.clearAllLogs();
        }
      });
    }
    if (this.mCancelButton != null) {
      this.mCancelButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Utils.logd("MTKLogger/LogFolderListActivity", "Cancel button is clicked!");
          LogFolderListActivity.this.finish();
        }
      });
    }
  }
  
  public void finish()
  {
    Utils.logd("MTKLogger/LogFolderListActivity", "finish()");
    String str = getIntent().getStringExtra("fromWhere");
    if ((str != null) && ("fromTagLog".equals(str)))
    {
      Intent localIntent = new Intent();
      localIntent.setAction("com.mediatek.log2server.EXCEPTION_HAPPEND");
      localIntent.putExtra("path", getIntent().getStringExtra("path"));
      localIntent.putExtra("taglogInputName", getIntent().getStringExtra("taglogInputName"));
      sendBroadcast(localIntent);
    }
    super.finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    Utils.logd("MTKLogger/LogFolderListActivity", "onCreate()");
    super.onCreate(paramBundle);
    setContentView(2130903047);
    if (Utils.ANDROID_VERSION_NUMBER > 3.999D) {
      removeManualButton();
    }
    findViews();
    initViews();
    setListeners();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    if (Utils.ANDROID_VERSION_NUMBER < 3.999D) {
      return true;
    }
    paramMenu.add(0, 11, 1, getString(2131165259)).setShowAsAction(2);
    paramMenu.add(0, 12, 2, getString(2131165260)).setShowAsAction(2);
    return true;
  }
  
  protected void onDestroy()
  {
    Utils.logd("MTKLogger/LogFolderListActivity", "onDestroy()");
    if (this.mClearLogConfirmDialog != null) {
      this.mClearLogConfirmDialog.dismiss();
    }
    super.onDestroy();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mIsLongClick)
    {
      this.mIsLongClick = false;
      return;
    }
    Intent localIntent = new Intent(this, LogFileListActivity.class);
    localIntent.putExtra("rootpath", this.mRootPath + ((LogFileItem)this.mLogFolderList.get(paramInt)).fileName);
    localIntent.putExtra("filterFilePath", ((LogFileItem)this.mLogFolderList.get(paramInt)).filterFilePath);
    startActivity(localIntent);
  }
  
  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mIsLongClick = true;
    return false;
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (Utils.ANDROID_VERSION_NUMBER < 3.999D) {
      return super.onMenuItemSelected(paramInt, paramMenuItem);
    }
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return super.onMenuItemSelected(paramInt, paramMenuItem);
      finish();
      continue;
      clearAllLogs();
    }
  }
  
  protected void onResume()
  {
    Utils.logd("MTKLogger/LogFolderListActivity", "onResume()");
    super.onResume();
  }
  
  static class LogFileItem
  {
    String fileName;
    String filterFilePath;
    String showName;
    
    public LogFileItem(String paramString1, String paramString2, String paramString3)
    {
      this.fileName = paramString1;
      this.showName = paramString2;
      if (paramString3 == null) {
        paramString3 = "";
      }
      this.filterFilePath = paramString3;
    }
  }
  
  class LogFolderAdapter
    extends BaseAdapter
  {
    private LayoutInflater mInflater;
    
    public LogFolderAdapter(Context paramContext)
    {
      this.mInflater = LayoutInflater.from(paramContext);
    }
    
    public int getCount()
    {
      return LogFolderListActivity.this.mLogFolderList.size();
    }
    
    public Object getItem(int paramInt)
    {
      return null;
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = paramView;
      if (localView == null) {
        localView = this.mInflater.inflate(2130968577, paramViewGroup, false);
      }
      ((TextView)localView.findViewById(2131296322)).setText(((LogFolderListActivity.LogFileItem)LogFolderListActivity.this.mLogFolderList.get(paramInt)).showName);
      return localView;
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.LogFolderListActivity
 * JD-Core Version:    0.7.0.1
 */