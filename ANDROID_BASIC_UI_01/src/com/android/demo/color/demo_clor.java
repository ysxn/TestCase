package com.android.demo.color;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;


public class demo_clor extends Activity {
    /** Called when the activity is first created. */
	//����ʹ�õĶ���
	private LinearLayout myLayout;
	private LinearLayout.LayoutParams layoutP;
	private int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
	private TextView black_TV, blue_TV, cyan_TV, dkgray_TV, 
					 gray_TV, green_TV, ltgray_TV, magenta_TV, red_TV,
					 transparent_TV, white_TV, yellow_TV;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ʵ����һ�����ֶ���
        myLayout = new LinearLayout(this);
        //ʵ��������Ϊ��ֱ����
        myLayout.setOrientation(LinearLayout.VERTICAL);
        //���ò��ֱ���ͼƬ
        myLayout.setBackgroundResource(R.drawable.background);
        //������������
        setContentView(myLayout);
        //ʵ�������ֲ������������View
        layoutP = new LinearLayout.LayoutParams(WC, WC);
        //����ʵ����TextView����
        constructTextView();
        //��TextView��ӵ�������
        addTextView();
        //����TextView�ı���ɫ
        setTextColor();
        //����TextView�ı�����
        setTextViewText();
        
    }

    //����TextView�ı�����
	private void setTextViewText() {
		// TODO Auto-generated method stub
		black_TV.setText("��ɫ");
		blue_TV.setText("��ɫ"); 
		cyan_TV.setText("����ɫ");
		dkgray_TV.setText("�Һ�ɫ");
		gray_TV.setText("��ɫ"); 
		green_TV.setText("��ɫ"); 
		ltgray_TV.setText("ǳ��ɫ"); 
		magenta_TV.setText("����ɫ"); 
		red_TV.setText("��ɫ");
		transparent_TV.setText("͸��"); 
		white_TV.setText("��ɫ"); 
		yellow_TV.setText("��ɫ");
		
	}

	//����TextView�ı���ɫ
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

    //��TextView��ӵ�������
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

	//����ʵ����TextView����
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