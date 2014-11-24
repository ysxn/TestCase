package com.android.demo.notepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class notepad extends Activity implements Button.OnClickListener {
    /** Called when the activity is first created. */
	//要使用的对象，变量声明
	//保存部分
	private EditText inputArt;//编辑框，输入用户字符串
	private Button saveButton;//保存按钮
	private String Text_of_input;//用户输入的字符串
	private OutputStream os;//文件输出流，保存到文件
	
	//保存时输入文件名部分
	private EditText inputFilename;//编辑框，输入用户字符串
	private Button ConfirmFilenameButton;//确定按钮
	private Button BackToMain;//返回main按钮
	
	//打开时输入文件名部分
	private EditText inputFilename_open;//编辑框，输入用户字符串
	private Button ConfirmFilenameButton_open;//确定按钮
	private Button BackToMain_open;//返回main按钮
	
	//读取部分
	private TextView showmyText;//显示读取文件内容
	private Button openTxt, cleanTxt;//打开文件按钮，清除显示内容按钮
	private String Text_of_output;//从文件读取到字符串
	private InputStream is;//文件输入流，读取文件
	private byte[] b;//字节数组，应用读取文件内容
	
	//使用自定义路径里的文件
	private FileOutputStream my_os;
	private FileInputStream my_is;
	private File my_file;
	
	//配置文件
	private String setting_abs_path = "/sdcard/setting.txt";
	private File setting_file;
	
	//默认电子书目录
	private String dir_abs_path = "/sdcard/ebook/";
	//自定义电子书目录
	private String dir_abs_path_setting = " ";
	//自定义电子书名称
	private String giving_file_name = " ";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutShow(R.layout.main);//设置布局
        UIinit("main");//初始化UI元素方法
        Logic("main");//添加事件逻辑方法
        InitFileDir();//初始化配置，以及默认电子书目录
        
    }

    //初始化配置，以及默认电子书目录
    private void InitFileDir() {
		// TODO Auto-generated method stub
    	setting_file = new File(setting_abs_path);
    	if (setting_file.exists()){
    		Open_and_read_setting_file();//打开配置文件，读取默认电子书目录，如果是空：
        	if (dir_abs_path_setting.equals(" ")){
        		Open_and_write_setting_file();//打开配置文件，写入默认电子书目录
        	}
    	}else{
    		Open_and_write_setting_file();//打开配置文件，写入默认电子书目录
    	}
    				
	}

    //打开配置文件，写入默认电子书目录或者自定义目录
	private void Open_and_write_setting_file() {
		// TODO Auto-generated method stub
		try {
			//打开文件输出流，名称txtME，以不覆盖模式打开
			//os = this.openFileOutput("txtME", MODE_PRIVATE);
			my_os = new FileOutputStream(setting_file, false);
			//把字符串转化字节数组，写入文件里
			//os.write(Text_of_input.getBytes());
			if (dir_abs_path_setting.equals(" ")){
				my_os.write(dir_abs_path.getBytes());
				dir_abs_path_setting = dir_abs_path;
				NoteDebug("设置默认目录: " + dir_abs_path_setting);
			}else{
				my_os.write(dir_abs_path_setting.getBytes());
				NoteDebug("设置自定义目录: " + dir_abs_path_setting);
			}
							
		}catch (FileNotFoundException e){
			/*文件未找到，异常*/
			// TODO Auto-generated catch block
			NoteDebug("文件关闭失败" + e);
		}catch (IOException e){
			/*文件写入错误*/
			// TODO Auto-generated catch block
			NoteDebug("文件写入失败" + e);
		}finally {
			try {
				/*关闭文件输出流*/
				//os.close();
				my_os.close();
			}catch (IOException e){
				// TODO Auto-generated catch block
				NoteDebug("文件关闭失败" + e);
			}
		}
		
	}

	//打开配置文件，读取默认电子书目录
	private void Open_and_read_setting_file() {
		// TODO Auto-generated method stub
		try {
			/*打开文件输入流，名称txtME*/
			//is = this.openFileInput("txtME");
			my_is = new FileInputStream(setting_file);
			//初始化字节数组
			b = new byte[1024];
			//从文件输入流读取内容到字节数组里，返回内容长度
			//int length = is.read(b);
			int length = my_is.read(b);
			//把字节数组转化为字符串
			if (length > 0){
				dir_abs_path_setting = new String(b);
			}
			//设置标题，显示文件内容长度
			if (length > 0){
				NoteDebug("读取设置：" + length + " bytes,目录: " + dir_abs_path_setting);
			}
			//显示内容
			//showmyText.setText(Text_of_output);
		}catch (FileNotFoundException e) {
			/*文件未找到，异常*/
			// TODO Auto-generated catch block
			NoteDebug("文件打开失败" + e);
		} catch (IOException e) {
			/*文件读取错误，异常*/
			// TODO Auto-generated catch block
			NoteDebug("文件读取失败" + e);
		}finally {
			try {
				/*关闭文件输入流*/
				//is.close();
				my_is.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				NoteDebug( "文件关闭失败"+e);
			}
		}
		
	}
	
	//添加事件逻辑方法
    private void Logic(String string) {
		// TODO Auto-generated method stub
    	/*为按钮添加事件处理*/
    	if (string.equals("main")) {
    		saveButton.setOnClickListener(this);
    	} else if (string.equals("open")) {
    		openTxt.setOnClickListener(this);
    		cleanTxt.setOnClickListener(this);
    	} else if (string.equals("save")){
    		ConfirmFilenameButton.setOnClickListener(this);
    		BackToMain.setOnClickListener(this);
    	}
	}

	//初始化UI元素方法
	private void UIinit(String mainROopen) {
		// TODO Auto-generated method stub
		if (mainROopen.equals("main")){
			inputArt = (EditText) findViewById(R.id.EditText_Txt);
			saveButton = (Button) findViewById(R.id.Button_Save);
		}else if (mainROopen.equals("open")){
			showmyText = (TextView) findViewById(R.id.TextView_showTxt);
			openTxt = (Button) findViewById(R.id.Button_openTxt);
			cleanTxt =(Button)findViewById(R.id.Button_clean);
		}else if (mainROopen.equals("save")){
			inputFilename = (EditText) findViewById(R.id.EditText_Filename);
			ConfirmFilenameButton = (Button) findViewById(R.id.Button_ConfirmFilename);
			BackToMain = (Button) findViewById(R.id.Button_BackToMain);
		}
	}

	//设置布局
	private void setLayoutShow(int layoutID) {
		// TODO Auto-generated method stub
		setContentView(layoutID);
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*根据ID判断按钮事件*/
		switch (v.getId()) {
		case R.id.Button_ConfirmFilename:{
			// TODO Auto-generated method stub
			/*获得用户输入的字符串*/
			giving_file_name = inputFilename.getText().toString();
			my_file = new File(dir_abs_path_setting + giving_file_name + ".txt");
			try {
				//打开文件输出流，名称txtME，以不覆盖模式打开
				//os = this.openFileOutput("txtME", MODE_PRIVATE);
				my_os = new FileOutputStream(my_file, false);
				//把字符串转化字节数组，写入文件里
				//os.write(Text_of_input.getBytes());
				my_os.write(Text_of_input.getBytes());
			}catch (FileNotFoundException e){
				/*文件未找到，异常*/
				// TODO Auto-generated catch block
				NoteDebug("文件关闭失败" + e);
			}catch (IOException e){
				/*文件写入错误*/
				// TODO Auto-generated catch block
				NoteDebug("文件写入失败" + e);
			}finally {
				try {
					/*关闭文件输出流*/
					//os.close();
					my_os.close();
					/*提示*/
					NoteDebug("成功保存到:" + dir_abs_path_setting + giving_file_name + ".txt");
				}catch (IOException e){
					// TODO Auto-generated catch block
					NoteDebug("文件关闭失败" + e);
				}
			}
						
		}
		break;
		
		case R.id.Button_BackToMain:{
			/*显示main.xml为主屏布局*/
			setLayoutShow(R.layout.main);
			UIinit("main");
			Logic("main");
		}
		break;
		
		case R.id.Button_Save:{
			/*显示save.xml为主屏布局*/
			setLayoutShow(R.layout.save);
			UIinit("save");
			Logic("save");
		}
		break;
		
		case R.id.Button_openTxt: {
			NoteDebug("文件打开");
			try {
				/*打开文件输入流，名称txtME*/
				//is = this.openFileInput("txtME");
				my_is = new FileInputStream(my_file);
				//初始化字节数组
				b = new byte[1024];
				//从文件输入流读取内容到字节数组里，返回内容长度
				//int length = is.read(b);
				int length = my_is.read(b);
				//把字节数组转化为字符串
				Text_of_output = new String(b);
				//设置标题，显示文件内容长度
				setTitle("读取文件：" + length + " bytes" );
				//显示内容
				showmyText.setText(Text_of_output);
			}catch (FileNotFoundException e) {
				/*文件未找到，异常*/
				// TODO Auto-generated catch block
				NoteDebug("文件打开失败" + e);
			} catch (IOException e) {
				/*文件读取错误，异常*/
				// TODO Auto-generated catch block
				NoteDebug("文件读取失败" + e);
			}finally {
				try {
					/*关闭文件输入流*/
					//is.close();
					my_is.close();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					NoteDebug( "文件关闭失败"+e);
				}
			}
		}
			break;
		case R.id.Button_clean :{
			/*清空*/
			showmyText.setText("");
			NoteDebug("清空");
			}
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		/*添加三个菜单项目，并设置图片*/
		menu.add(0, 1, 1, "Edit").setIcon(R.drawable.ic_menu_edit);
		menu.add(0, 2, 2, "Open").setIcon(R.drawable.ic_menu_agenda);
		menu.add(0, 3, 3, "Exit").setIcon(R.drawable.ic_lock_power_off);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case 1:
				/*显示main.xml为主屏布局*/
				setLayoutShow(R.layout.main);
				UIinit("main");
				Logic("main");
				NoteDebug("编辑文件Layout");
				break;
			case 2:
				/*显示open.xml为主屏布局*/
				setLayoutShow(R.layout.open);
				UIinit("open");
				Logic("open");
				NoteDebug( "打开文件Layout");
				break;
			case 3:
				/*退出*/
				finish();
				NoteDebug( "Byebye");
				break;
			default:
				break;
		} 
		return super.onOptionsItemSelected(item);
	}

	private void NoteDebug(String string) {
		// TODO Auto-generated method stub
		/*显示Toast提示*/
		Toast.makeText(this,string, Toast.LENGTH_SHORT ).show();
	}
}