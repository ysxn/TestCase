package com.android.demo.font;


import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class demo_font extends Activity {
    /** Called when the activity is first created. */
	/*
	 * android.graphics.Typeface java.lang.Object
	 * Typeface类指定一个字体的字体和固有风格
	 * 该类用于绘制，与可绘制设置一起使用
	 * 如textSize， textSkewX, textScaleX 当绘制（测量）时来指定如何显示文本。
	 * 
	 */
	//定义实例化一个布局大小，用来添加TextView
	final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
	//定义TextView对象
	private TextView bold_TV, 
					bold_italic_TV, 
					italic_TV,
					normal_TV,
					default_TV,
					default_bold_TV,
					monospace_TV,
					sans_serif_TV,
					serif_TV;
	//定义LinearLayout布局对象
	private LinearLayout linearLayout;
	//定义LinearLayout布局参数对象
	private LinearLayout.LayoutParams linearLayouttParams;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化一个布局对象
        linearLayout = new LinearLayout(this);
        //实例化布局为垂直布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //设置布局背景图片
        linearLayout.setBackgroundResource(R.drawable.background);
        //加载主屏布局
        setContentView(linearLayout);
        //实例化布局参数，用于添加View
        linearLayouttParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        //构造实例化TextView对象
        constructTextView();
        //设置字体大小,该值必须大于0
        setTextSizeOf();
        //设置TextView文本内容
        setTextViewText();
        //设置字体风格
        setStyleOfFont();
        //设置TextView文本颜色
        setTextColor();
        //把TextView添加到布局里
        addTextView();


        
    }

    //设置字体风格
    private void setStyleOfFont() {
		// TODO Auto-generated method stub
		bold_TV.setTypeface(null, Typeface.BOLD);
		bold_italic_TV.setTypeface(null, Typeface.BOLD_ITALIC);
		italic_TV.setTypeface(null, Typeface.ITALIC);
		normal_TV.setTypeface(null, Typeface.NORMAL);
		default_TV.setTypeface(Typeface.DEFAULT);
		default_bold_TV.setTypeface(Typeface.DEFAULT_BOLD);
		monospace_TV.setTypeface(Typeface.MONOSPACE);
		sans_serif_TV.setTypeface(Typeface.SANS_SERIF);
		serif_TV.setTypeface(Typeface.SERIF);
	}

	//设置字体大小,该值必须大于0
    private void setTextSizeOf() {
		// TODO Auto-generated method stub
		bold_TV.setTextSize(24.0f);
		bold_italic_TV.setTextSize(24.0f);
		italic_TV.setTextSize(24.0f);
		normal_TV.setTextSize(24.0f);
		default_TV.setTextSize(24.0f);
		default_bold_TV.setTextSize(24.0f);
		monospace_TV.setTextSize(24.0f);
		sans_serif_TV.setTextSize(24.0f);
		serif_TV.setTextSize(24.0f);
	}

	//设置TextView文本内容
	private void setTextViewText() {
		// TODO Auto-generated method stub
		bold_TV.setText("BOLD");
		bold_italic_TV.setText("BOLD_ITALIC");
		italic_TV.setText("ITALIC");
		normal_TV.setText("NORMAL");
		default_TV.setText("DEFAULT");
		default_bold_TV.setText("DEFAULT_BOLD");
		monospace_TV.setText("MONOSPACE");
		sans_serif_TV.setText("SANS_SERIF");
		serif_TV.setText("SERIF");
		
	}

	//设置TextView文本颜色
	private void setTextColor() {
		// TODO Auto-generated method stub
		bold_TV.setTextColor(Color.BLACK);
		bold_italic_TV.setTextColor(Color.CYAN);
		sans_serif_TV.setTextColor(Color.GRAY);
		default_TV.setTextColor(Color.GREEN);
		serif_TV.setTextColor(Color.LTGRAY);
		default_bold_TV.setTextColor(Color.MAGENTA);
		italic_TV.setTextColor(Color.RED);
		monospace_TV.setTextColor(Color.WHITE);
		normal_TV.setTextColor(Color.YELLOW);
	}

    //把TextView添加到布局里
	private void addTextView() {
		// TODO Auto-generated method stub
		linearLayout.addView(bold_TV, linearLayouttParams);
		linearLayout.addView(bold_italic_TV, linearLayouttParams);
		linearLayout.addView(italic_TV, linearLayouttParams);
		linearLayout.addView(normal_TV, linearLayouttParams);
		linearLayout.addView(default_TV, linearLayouttParams);
		linearLayout.addView(default_bold_TV, linearLayouttParams);
		linearLayout.addView(monospace_TV, linearLayouttParams);
		linearLayout.addView(sans_serif_TV, linearLayouttParams);
		linearLayout.addView(serif_TV, linearLayouttParams);
	}

	//构造实例化TextView对象
	private void constructTextView() {
		// TODO Auto-generated method stub
		bold_TV = new TextView(this);
		bold_italic_TV = new TextView(this);
		italic_TV = new TextView(this);
		normal_TV = new TextView(this);
		default_TV = new TextView(this);
		default_bold_TV = new TextView(this);
		monospace_TV = new TextView(this);
		sans_serif_TV = new TextView(this);
		serif_TV = new TextView(this);
	}
}