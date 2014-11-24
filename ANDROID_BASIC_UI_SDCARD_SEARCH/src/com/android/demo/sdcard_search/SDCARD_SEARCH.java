package com.android.demo.sdcard_search;

import android.app.Activity;
import android.os.Bundle;
import java.io.File;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SDCARD_SEARCH extends Activity {
    /** Called when the activity is first created. */
	//定义程序要使用的类对象
	private File file;
	private String path;
	private String searchpath;
	private String info;
	private String theKey_formInput;
	private TextView show_Result;
	private EditText input_SearchKey_Edit;
	private Button toSearch_Button;
	
	private EditText input_Path_Edit;
	private Button toConfirmPath_Button;
	
	private Button toClearresult_Button;
	private TextView show_pathinfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //findViewById实例化对象
        show_Result = (TextView) findViewById(R.id.TextView_Result);
        input_SearchKey_Edit = (EditText) findViewById(R.id.input_KEY_EditText);
        toSearch_Button = (Button) findViewById(R.id.Button_Search );
        toClearresult_Button = (Button) findViewById(R.id.Button_Clearresult );
        
        input_Path_Edit = (EditText) findViewById(R.id.input_PATH_EditText );
        toConfirmPath_Button = (Button) findViewById(R.id.Button_SearchPath );
        
        show_pathinfo = (TextView) findViewById(R.id.current_pathinfo );
        //为搜索按钮添加点击事件监听器
        toSearch_Button.setOnClickListener(callback_toSearch_Button);
        
        //为确认路径按钮添加点击事件监听器
        toConfirmPath_Button.setOnClickListener(callback_toConfirmPath_Button);
        
        //为清除搜索结果按钮添加点击事件监听器
        toClearresult_Button.setOnClickListener(callback_toClearresult_Button);
        
        //初始化搜索路径
        searchpath = "/sdcard";
        
        //初始化一个File对象，指定路径位/sdcard
        file = new File(searchpath);
        
        //从xml取得字符串
        info = getString(R.string.info);
        
        //显示搜索路径
        show_pathinfo.setText(getString(R.string.pathinfo) + searchpath);
        
    }

    //搜索按钮事件处理
    private OnClickListener callback_toSearch_Button = new OnClickListener(){
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		//清空
    		path = "";
    		show_Result.setText("");
    		//取得输入框的要查询的Key
    		theKey_formInput = input_SearchKey_Edit.getText().toString();
    		//浏览文件
    		BrowserFile(file);
    	}
    };

  //为确认路径按钮添加点击事件监听器
    private OnClickListener callback_toConfirmPath_Button = new OnClickListener(){
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		searchpath = "/" + input_Path_Edit.getText().toString();
    		show_pathinfo.setText(getString(R.string.pathinfo) + searchpath);
    		file = new File(searchpath);
    	}
    };
    
    //清除搜索结果按钮事件处理
    private OnClickListener callback_toClearresult_Button = new OnClickListener(){
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		//清空
    		show_Result.setText("");
    	}
    };
    
	//浏览文件方法
	private void BrowserFile(File file) {
		// TODO Auto-generated method stub
		if (theKey_formInput.equals("")){
			Toast.makeText(this, getString(R.string.pleaseInput), 
					Toast.LENGTH_SHORT).show();
			
		}else{
			//开始搜索文件
			ToSearchFiles(file);
			//搜索完成后，如果结果为空，提示没找到
			if (show_Result.getText().equals("")){
				Toast.makeText(this, getString(R.string.notFond), 
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	//开始搜索文件方法
	private void ToSearchFiles(File file) {
		// TODO Auto-generated method stub
		//定义一个File文件数组，用来存放/sdcard目录下的文件或文件夹
		File[] the_Files = file.listFiles();
		//int index = sizeof(the_Files);
		//通过遍历所有文件和文件夹
		for (File tempF : the_Files){
			Log.i("ZHUYAWEN", "infomation in sdcard search: File tempF = " + tempF.getName());
			if (tempF.isDirectory()){
				ToSearchFiles(tempF);
			}else{
				try {
					//是文件，则进行比较，如果文件名称包含输入搜索Key，则返回大于-1的值
					if (tempF.getName().indexOf(theKey_formInput) > -1){
						//获取符合条件的文件路径，进行累加
						path += "\n" + tempF.getPath();
						//显示结果
						show_Result.setText(info + searchpath + "\n" + path);
					}
					
				}catch (Exception e){
					//如果路径找不到，提示出错
					Toast.makeText(this, getString(R.string.pathError), 
							Toast.LENGTH_SHORT).show();
					
				}
			}
		}
	}
}