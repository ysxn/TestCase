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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mediatek.mtklogger.settings.OptionalActionBarSwitch;
import com.mediatek.mtklogger.utils.Utils;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class LogFileListActivity
  extends Activity
  implements AdapterView.OnItemClickListener
{
  private static final int CALCULATE_FILE_SIZE_DONE = 2;
  private static final int FINISH_CLEAR_LOG = 1;
  private static final String TAG = "MTKLogger/LogFileListActivity";
  private OptionalActionBarSwitch mActionBar;
  private LogFileAdapter mAdapter;
  private Button mCancelButton;
  private Button mClearButton;
  private Dialog mClearLogConfirmDialog;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1)
      {
        LogFileListActivity.this.mListView.invalidateViews();
        LogFileListActivity.access$102(LogFileListActivity.this, 0);
        LogFileListActivity.this.updateTitle(LogFileListActivity.this.mNumSelected);
      }
      if (paramAnonymousMessage.what == 2) {
        LogFileListActivity.this.mListView.invalidateViews();
      }
    }
  };
  private ListView mListView;
  private List<LogFileItem> mLogItemList = new ArrayList();
  private int mNumSelected;
  private String mRootPath;
  private CheckBox mSelectAllButton;
  private TextView mSelectAllTextView;
  
  private void clearFileSelected()
  {
    if (this.mNumSelected == 0)
    {
      Toast.makeText(this, getString(2131165271), 0).show();
      return;
    }
    this.mClearLogConfirmDialog = new AlertDialog.Builder(this).setTitle(2131165272).setMessage(2131165273).setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        new Thread()
        {
          public void run()
          {
            for (int i = -1 + LogFileListActivity.this.mLogItemList.size(); i >= 0; i--)
            {
              LogFileListActivity.LogFileItem localLogFileItem = (LogFileListActivity.LogFileItem)LogFileListActivity.this.mLogItemList.get(i);
              Utils.logd("MTKLogger/LogFileListActivity", "Log File Item name : " + localLogFileItem.getFileName());
              if (localLogFileItem.isChecked())
              {
                LogFileListActivity.this.clearLogs(new File(LogFileListActivity.this.mRootPath + File.separator + localLogFileItem.getFileName()));
                LogFileListActivity.this.mLogItemList.remove(i);
              }
            }
            if (this.val$clearLogWaitingStopDialog != null) {
              this.val$clearLogWaitingStopDialog.cancel();
            }
            LogFileListActivity.this.mHandler.sendEmptyMessage(1);
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
  
  private void clearLogs(File paramFile)
  {
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
    this.mListView = ((ListView)findViewById(2131296277));
    this.mSelectAllTextView = ((TextView)findViewById(2131296275));
    this.mSelectAllButton = ((CheckBox)findViewById(2131296276));
    this.mClearButton = ((Button)findViewById(2131296279));
    this.mCancelButton = ((Button)findViewById(2131296280));
  }
  
  private void initLogItemList(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || ("".equals(paramString1))) {
      paramString1 = Utils.getCurrentLogPath(this) + "/mtklog/";
    }
    Utils.logd("MTKLogger/LogFileListActivity", "initLogItemList() rootPath = " + paramString1 + "; filterPath = " + paramString2);
    if (!new File(paramString1).exists()) {}
    String[] arrayOfString;
    do
    {
      return;
      this.mRootPath = paramString1;
      arrayOfString = new File(paramString1).list();
    } while (arrayOfString.length == 0);
    File localFile = new File(paramString2);
    int i;
    int k;
    label139:
    String str;
    if ((localFile != null) && (localFile.exists()))
    {
      i = 1;
      int j = arrayOfString.length;
      k = 0;
      if (k >= j) {
        break label216;
      }
      str = arrayOfString[k];
      if ((i == 0) || (!str.equals(localFile.getName()))) {
        break label182;
      }
    }
    for (;;)
    {
      k++;
      break label139;
      i = 0;
      break;
      label182:
      if (!str.equals("file_tree.txt")) {
        this.mLogItemList.add(new LogFileItem(str, 0L));
      }
    }
    label216:
    Collections.sort(this.mLogItemList, new Comparator()
    {
      public int compare(LogFileListActivity.LogFileItem paramAnonymousLogFileItem1, LogFileListActivity.LogFileItem paramAnonymousLogFileItem2)
      {
        return paramAnonymousLogFileItem1.getFileName().compareTo(paramAnonymousLogFileItem2.getFileName());
      }
    });
    new Thread()
    {
      public void run()
      {
        Utils.logd("MTKLogger/LogFileListActivity", "Calculate log file's size");
        Iterator localIterator = LogFileListActivity.this.mLogItemList.iterator();
        while (localIterator.hasNext())
        {
          LogFileListActivity.LogFileItem localLogFileItem = (LogFileListActivity.LogFileItem)localIterator.next();
          localLogFileItem.setFileSize(Utils.getFileSize(LogFileListActivity.this.mRootPath + File.separator + localLogFileItem.getFileName()));
        }
        LogFileListActivity.this.mHandler.sendEmptyMessage(2);
      }
    }.start();
  }
  
  private void initViews()
  {
    initLogItemList(getIntent().getStringExtra("rootpath"), getIntent().getStringExtra("filterFilePath"));
    this.mAdapter = new LogFileAdapter(this);
    this.mListView.setAdapter(this.mAdapter);
    this.mActionBar = new OptionalActionBarSwitch(this, this.mNumSelected);
  }
  
  private void removeManualButton()
  {
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296273);
    localLinearLayout.removeView(findViewById(2131296274));
    localLinearLayout.removeView(findViewById(2131296278));
  }
  
  private void setAllFileSelected(boolean paramBoolean)
  {
    if (this.mListView != null)
    {
      Iterator localIterator = this.mLogItemList.iterator();
      while (localIterator.hasNext()) {
        ((LogFileItem)localIterator.next()).setChecked(paramBoolean);
      }
      if (!paramBoolean) {
        break label77;
      }
    }
    label77:
    for (int i = this.mLogItemList.size();; i = 0)
    {
      this.mNumSelected = i;
      this.mListView.invalidateViews();
      updateTitle(this.mNumSelected);
      return;
    }
  }
  
  private void setListeners()
  {
    if (this.mListView != null) {
      this.mListView.setOnItemClickListener(this);
    }
    if (this.mSelectAllButton != null) {
      this.mSelectAllButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Utils.logd("MTKLogger/LogFileListActivity", "Select All button is clicked!");
          if (LogFileListActivity.this.mSelectAllButton.isChecked()) {
            LogFileListActivity.this.mSelectAllTextView.setText(2131165269);
          }
          for (;;)
          {
            LogFileListActivity.this.setAllFileSelected(LogFileListActivity.this.mSelectAllButton.isChecked());
            return;
            LogFileListActivity.this.mSelectAllTextView.setText(2131165268);
          }
        }
      });
    }
    if (this.mClearButton != null) {
      this.mClearButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Utils.logd("MTKLogger/LogFileListActivity", "Clear file button is clicked!");
          LogFileListActivity.this.clearFileSelected();
        }
      });
    }
    if (this.mCancelButton != null) {
      this.mCancelButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Utils.logd("MTKLogger/LogFileListActivity", "Cancel button is clicked!");
          LogFileListActivity.this.finish();
        }
      });
    }
  }
  
  private void updateTitle(int paramInt)
  {
    this.mActionBar.updateTitle(paramInt);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903046);
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
    getMenuInflater().inflate(2131230722, paramMenu);
    return true;
  }
  
  protected void onDestroy()
  {
    if (this.mClearLogConfirmDialog != null) {
      this.mClearLogConfirmDialog.dismiss();
    }
    super.onDestroy();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    CheckBox localCheckBox = (CheckBox)paramView.findViewById(2131296319);
    boolean bool;
    if (localCheckBox != null)
    {
      if (localCheckBox.isChecked()) {
        break label79;
      }
      bool = true;
      localCheckBox.setChecked(bool);
      if (!localCheckBox.isChecked()) {
        break label85;
      }
      this.mNumSelected = (1 + this.mNumSelected);
      ((LogFileItem)this.mLogItemList.get(paramInt)).setChecked(true);
    }
    for (;;)
    {
      updateTitle(this.mNumSelected);
      return;
      label79:
      bool = false;
      break;
      label85:
      this.mNumSelected = (-1 + this.mNumSelected);
      ((LogFileItem)this.mLogItemList.get(paramInt)).setChecked(false);
    }
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
      setAllFileSelected(true);
      continue;
      setAllFileSelected(false);
      continue;
      clearFileSelected();
    }
  }
  
  class LogFileAdapter
    extends BaseAdapter
  {
    private LayoutInflater mInflater;
    
    public LogFileAdapter(Context paramContext)
    {
      this.mInflater = LayoutInflater.from(paramContext);
    }
    
    public int getCount()
    {
      return LogFileListActivity.this.mLogItemList.size();
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
        localView = this.mInflater.inflate(2130968576, paramViewGroup, false);
      }
      TextView localTextView1 = (TextView)localView.findViewById(2131296320);
      TextView localTextView2 = (TextView)localView.findViewById(2131296321);
      CheckBox localCheckBox = (CheckBox)localView.findViewById(2131296319);
      LogFileListActivity.LogFileItem localLogFileItem = (LogFileListActivity.LogFileItem)LogFileListActivity.this.mLogItemList.get(paramInt);
      localTextView1.setText(localLogFileItem.getFileName());
      double d = localLogFileItem.getFileSize();
      if (d < 1024.0D) {
        localTextView2.setText("Size " + localLogFileItem.getFileSize() + " B");
      }
      for (;;)
      {
        localCheckBox.setChecked(localLogFileItem.isChecked());
        return localView;
        if (d / 1024.0D < 1024.0D) {
          localTextView2.setText("Size " + new DecimalFormat(".00").format(d / 1024.0D) + " KB");
        } else {
          localTextView2.setText("Size " + new DecimalFormat(".00").format(d / 1024.0D / 1024.0D) + " MB");
        }
      }
    }
  }
  
  class LogFileItem
  {
    private String mFileName;
    private long mFileSize;
    private boolean mIsChecked;
    
    public LogFileItem(String paramString, long paramLong)
    {
      this.mFileName = paramString;
      this.mFileSize = paramLong;
    }
    
    public String getFileName()
    {
      return this.mFileName;
    }
    
    public long getFileSize()
    {
      return this.mFileSize;
    }
    
    public boolean isChecked()
    {
      return this.mIsChecked;
    }
    
    public void setChecked(boolean paramBoolean)
    {
      this.mIsChecked = paramBoolean;
    }
    
    public void setFileSize(long paramLong)
    {
      this.mFileSize = paramLong;
    }
  }
}


/* Location:           D:\apktool\MTKLogger\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.mtklogger.LogFileListActivity
 * JD-Core Version:    0.7.0.1
 */