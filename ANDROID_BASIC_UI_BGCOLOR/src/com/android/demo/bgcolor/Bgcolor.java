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
        
        //ʵ����һ��TextView
        TextView text = (TextView)findViewById(R.id.name);
        
        //��ID�����Դ
        Resources myColor = getBaseContext().getResources();
        //getBaseContext()��û���Context
        //getResources()�����Դ
        
        //����ԴmyColor�����Drawable R.color.lightgreen����ɫֵ��ID����
        Drawable color_M = myColor.getDrawable(R.color.lightgreen);
        
        //���ñ���
        text.setBackgroundDrawable(color_M);
        
        //example for ȡ����Ļ�ֱ��ʲ���ʾ
        get_Screenpixels_Display();
    }

    //example for ȡ����Ļ�ֱ��ʲ���ʾ
	private void get_Screenpixels_Display() {
		// TODO Auto-generated method stub
		//����һ��DisplayMetrics�����
		//DisplayMetrics һ��������ͨ��ʾ��Ϣ�Ľṹ��������ʾ��С���ܶȣ�����ߴ�
		DisplayMetrics displayMetrics = new DisplayMetrics();
		
		//getWindowManager()��ȡ��ʾ���ƴ��ڵ�
		//getDefaultDisplay()��ȡĬ����ʾDisplay����
		//getMetrics()ͨ���ֻ����ڵ�Display���� ����ʼ��DisplayMetrics
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		//CharSequence ����������ResourceIDӦ��
		CharSequence ch_screenpixels = getString(R.string.screenpixels);
		
		String str_screenpixels = getString(R.string.screenpixels);
		
		String showContent = str_screenpixels + displayMetrics.widthPixels + 
						" * " + 
						displayMetrics.heightPixels;
		TextView screenpixels = (TextView)findViewById(R.id.screenpixels);
		
		screenpixels.setText(showContent);
	}
	
	//����������
	private void HideTitle()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	//״̬�����أ�ȫ����
	private void HideStatusBar()
	{
		//����������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����ȫ������
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		//��ô��ڶ���
		Window myWindow = this.getWindow();
		//����Flag��ʾ
		myWindow.setFlags(flag, flag);
	}
}