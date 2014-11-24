package com.android.demo.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BMIActivity extends Activity {
    /** Called when the activity is first created. */
	private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylayout);
        //ȡ��Intetnt�е�Bundle����
        intent = this.getIntent();
        Bundle bunde = this.getIntent().getExtras();
        //ȡ��Bundle�����������
        String sex = bunde.getString("sex");
        double height = bunde.getDouble("height");
        //�ж��Ա�
        String sexText = "";
        if (sex.equals("M")){
        	sexText = "����";
        }else if (sex.equals("F")){
        	sexText = "Ů��";
        }else{
        	sexText = "δ֪";
        }
        //�����׼����
        String weight = this.getWeight(sex, height);
        //���ý���������
        TextView tv1 = (TextView)findViewById(R.id.text1);
        tv1.setText("����һλ" + sexText + "\n��������" + height + 
        			"����\n��ı�׼������" + weight + "����");
        //�������ݵ�ǰһ��activity
        Button b1 = (Button)findViewById(R.id.button_back);
        b1.setOnClickListener(callback_button);
        //
    }
    
    //�������ݵ�ǰһ��activity 
    private OnClickListener callback_button = new OnClickListener(){
    	public void onClick(View v){
    		BMIActivity.this.setResult(RESULT_OK, intent);
    		BMIActivity.this.finish();
    	}
    };
    
    
    //��������ķ���
    private String format(double num){
    	if (num < 0){
    		return "unknow";
    	}
    	NumberFormat formatter = new DecimalFormat("0.00");
    	String s = formatter.format(num);
    	return s;
    	
    }
    
    //�����׼����
    private String getWeight(String sex, double height){
    	String weight = "";
    	if (sex.equals("M")){
    		weight = format((height - 80) * 0.7);
    	}else if (sex.equals("F")){
    		weight = format((height - 70) * 0.6);
    	}else{
    		weight = format(0);
    	}
    	return weight;
    }
    //
}