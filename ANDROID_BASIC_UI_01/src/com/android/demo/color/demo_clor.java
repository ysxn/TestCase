package com.android.demo.color;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;


public class demo_clor extends Activity {
    /** Called when the activity is first created. */
	//定义使用的对象
	private LinearLayout myLayout;
	private LinearLayout.LayoutParams layoutP;
	private int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
	private TextView black_TV, blue_TV, cyan_TV, dkgray_TV, 
					 gray_TV, green_TV, ltgray_TV, magenta_TV, red_TV,
					 transparent_TV, white_TV, yellow_TV;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化一个布局对象
        myLayout = new LinearLayout(this);
        //实例化布局为垂直布局
        myLayout.setOrientation(LinearLayout.VERTICAL);
        //设置布局背景图片
        myLayout.setBackgroundResource(R.drawable.background);
        //加载主屏布局
        setContentView(myLayout);
        //实例化布局参数，用于添加View
        layoutP = new LinearLayout.LayoutParams(WC, WC);
        //构造实例化TextView对象
        constructTextView();
        //把TextView添加到布局里
        addTextView();
        //设置TextView文本颜色
        setTextColor();
        //设置TextView文本内容
        setTextViewText();
        
    }

    //设置TextView文本内容
	private void setTextViewText() {
		// TODO Auto-generated method stub
		black_TV.setText("黑色");
		blue_TV.setText("蓝色"); 
		cyan_TV.setText("青绿色");
		dkgray_TV.setText("灰黑色");
		gray_TV.setText("灰色"); 
		green_TV.setText("绿色"); 
		ltgray_TV.setText("浅灰色"); 
		magenta_TV.setText("红紫色"); 
		red_TV.setText("红色");
		transparent_TV.setText("透明"); 
		white_TV.setText("白色"); 
		yellow_TV.setText("黄色");
		
	}

	//设置TextView文本颜色
	private void setTextColor() {
		// TODO Auto-generated method stub
		black_TV.setTextColor(Color.BLACK);
		blue_TV.setTextColor(Color.BLUE);
		cyan_TV.setTextColor(Color.CYAN);
		dkgray_TV.setTextColor(Color.DKGRAY);
		gray_TV.setTextColor(Color.GRAY);
		green_TV.setTextColor(Color.GREEN);
		ltgray_TV.setTextColor(Color.LTGRAY);
		magenta_TV.setTextColor(Color.MAGENTA);
		red_TV.setTextColor(Color.RED);
		transparent_TV.setTextColor(Color.TRANSPARENT);
		white_TV.setTextColor(Color.WHITE);
		yellow_TV.setTextColor(Color.YELLOW);
	}

    //把TextView添加到布局里
	private void addTextView() {
		// TODO Auto-generated method stub
		myLayout.addView(black_TV, layoutP);
		myLayout.addView(blue_TV, layoutP);
		myLayout.addView(cyan_TV, layoutP);
		myLayout.addView(dkgray_TV, layoutP);
		myLayout.addView(gray_TV, layoutP);
		myLayout.addView(green_TV, layoutP);
		myLayout.addView(ltgray_TV, layoutP);
		myLayout.addView(magenta_TV, layoutP);
		myLayout.addView(red_TV, layoutP);
		myLayout.addView(transparent_TV, layoutP);
		myLayout.addView(white_TV, layoutP);
		myLayout.addView(yellow_TV, layoutP);

	}

	//构造实例化TextView对象
	private void constructTextView() {
		// TODO Auto-generated method stub
		black_TV = new TextView(this);
		blue_TV = new TextView(this);
		cyan_TV = new TextView(this);
		dkgray_TV = new TextView(this);
		gray_TV = new TextView(this);
		green_TV = new TextView(this);
		ltgray_TV = new TextView(this);
		magenta_TV = new TextView(this);
		red_TV = new TextView(this);
		transparent_TV = new TextView(this);
		white_TV = new TextView(this);
		yellow_TV = new TextView(this);
	}
}