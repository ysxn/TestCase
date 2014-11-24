package com.android.demo.bgcolor;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class Bgcolor extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStatusBar();
        setContentView(R.layout.main);
        
        //实例化一个TextView
        TextView text = (TextView)findViewById(R.id.name);
        
        //由ID获得资源
        Resources myColor = getBaseContext().getResources();
        //getBaseContext()获得基础Context
        //getResources()获得资源
        
        //由资源myColor来获得Drawable R.color.lightgreen是颜色值的ID引用
        Drawable color_M = myColor.getDrawable(R.color.lightgreen);
        
        //设置背景
        text.setBackgroundDrawable(color_M);
        
        //example for 取得屏幕分辨率并显示
        get_Screenpixels_Display();
    }

    //example for 取得屏幕分辨率并显示
	private void get_Screenpixels_Display() {
		// TODO Auto-generated method stub
		//定义一个DisplayMetrics类对象
		//DisplayMetrics 一个描述普通显示信息的结构，例如显示大小，密度，字体尺寸
		DisplayMetrics displayMetrics = new DisplayMetrics();
		
		//getWindowManager()获取显示定制窗口的
		//getDefaultDisplay()获取默认显示Display对象
		//getMetrics()通过手机窗口的Display对象 来初始化DisplayMetrics
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		//CharSequence 数据类型与ResourceID应用
		CharSequence ch_screenpixels = getString(R.string.screenpixels);
		
		String str_screenpixels = getString(R.string.screenpixels);
		
		String showContent = str_screenpixels + displayMetrics.widthPixels + 
						" * " + 
						displayMetrics.heightPixels;
		TextView screenpixels = (TextView)findViewById(R.id.screenpixels);
		
		screenpixels.setText(showContent);
	}
	
	//标题栏隐藏
	private void HideTitle()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	//状态栏隐藏（全屏）
	private void HideStatusBar()
	{
		//标题栏隐藏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//定义全屏参数
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		//获得窗口对象
		Window myWindow = this.getWindow();
		//设置Flag标示
		myWindow.setFlags(flag, flag);
	}
}