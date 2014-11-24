package com.android.demo.bundle;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class mainActivity extends Activity {
    /** Called when the activity is first created. */
	private boolean IsRadioButtonChecked = false;
	protected int my_requestcode = 1550;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //为Button设置点击回调函数
        Button ok = (Button)findViewById(R.id.button_OK);
        ok.setOnClickListener(callback_button);
        //为单项选择组设置状态变化的回调函数
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radioGroup);
        radiogroup.setOnCheckedChangeListener(callback_radionutton);
        //
        
        //
        
        //
        
        //
    }
    
    //为单项选择组设置状态变化的回调函数
    private OnCheckedChangeListener callback_radionutton = new OnCheckedChangeListener(){

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			IsRadioButtonChecked = true;
		}
    	
    };
    //为Button设置点击回调函数
    private OnClickListener callback_button = new OnClickListener(){

		public void onClick(View v) {
			try
			{
				// TODO Auto-generated method stub
				//单项选择组设置状态变化的标志位，可以提示是否选择了。
				if (!IsRadioButtonChecked){
					//return;
				}
				//取得身高数据
				EditText et = (EditText)findViewById(R.id.height_Edit);
				double height = Double.parseDouble(et.getText().toString());
				
				//取得性别
				String sex = "";
				RadioButton rb1 = (RadioButton)findViewById(R.id.Sex_Man);
				RadioButton rb2 = (RadioButton)findViewById(R.id.Sex_Woman);
				if (rb1.isChecked()){
					sex = "M";
				}else if (rb2.isChecked()){
					sex = "F";
				}else{
					return;
				}
				
				//new 一个Intent对象，并指定class
				Intent intent = new Intent();
				intent.setClass(mainActivity.this, BMIActivity.class);
				//new 一个Bundle对象，并指定要传入的数据
				Bundle bundle = new Bundle();
				bundle.putDouble("height", height);
				bundle.putString("sex", sex);
				
				//将Bundle对象assign给Intent
				intent.putExtras(bundle);
				
				//调用BMIActivity，一种是startActivity不带返回的，
				//另一种startActivityForResult是带返回的。
				//startActivity(intent);
				startActivityForResult(intent, my_requestcode);
				//
			}catch (Exception e){
				Toast.makeText(mainActivity.this, R.string.errorString, Toast.LENGTH_LONG).show();
			}
		}
    	
    };
    
    @Override
    //处理返回的信息
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	switch (resultCode){
    	case RESULT_OK:
			EditText et = (EditText)findViewById(R.id.height_Edit);
			RadioButton rb1 = (RadioButton)findViewById(R.id.Sex_Man);
			RadioButton rb2 = (RadioButton)findViewById(R.id.Sex_Woman);
			//
    		Bundle bunde = data.getExtras();
    		String sex = bunde.getString("sex");
            double height = bunde.getDouble("height");
            //
            et.setText("back: " + height);
            //判断性别
            if (sex.equals("M")){
            	rb1.setChecked(true);
            }else if (sex.equals("F")){
            	rb2.setChecked(true);
            }else{
            	//
            }
            break;
        default:
        	break;
    	}
    }
}