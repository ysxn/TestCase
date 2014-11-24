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
        //取得Intetnt中的Bundle对象
        intent = this.getIntent();
        Bundle bunde = this.getIntent().getExtras();
        //取得Bundle对象里的数据
        String sex = bunde.getString("sex");
        double height = bunde.getDouble("height");
        //判断性别
        String sexText = "";
        if (sex.equals("M")){
        	sexText = "男性";
        }else if (sex.equals("F")){
        	sexText = "女性";
        }else{
        	sexText = "未知";
        }
        //计算标准体重
        String weight = this.getWeight(sex, height);
        //设置结果输出文字
        TextView tv1 = (TextView)findViewById(R.id.text1);
        tv1.setText("你是一位" + sexText + "\n你的身高是" + height + 
        			"厘米\n你的标准体重是" + weight + "公斤");
        //返回数据到前一个activity
        Button b1 = (Button)findViewById(R.id.button_back);
        b1.setOnClickListener(callback_button);
        //
    }
    
    //返回数据到前一个activity 
    private OnClickListener callback_button = new OnClickListener(){
    	public void onClick(View v){
    		BMIActivity.this.setResult(RESULT_OK, intent);
    		BMIActivity.this.finish();
    	}
    };
    
    
    //四舍五入的方法
    private String format(double num){
    	if (num < 0){
    		return "unknow";
    	}
    	NumberFormat formatter = new DecimalFormat("0.00");
    	String s = formatter.format(num);
    	return s;
    	
    }
    
    //计算标准体重
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