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
        //ΪButton���õ���ص�����
        Button ok = (Button)findViewById(R.id.button_OK);
        ok.setOnClickListener(callback_button);
        //Ϊ����ѡ��������״̬�仯�Ļص�����
        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radioGroup);
        radiogroup.setOnCheckedChangeListener(callback_radionutton);
        //
        
        //
        
        //
        
        //
    }
    
    //Ϊ����ѡ��������״̬�仯�Ļص�����
    private OnCheckedChangeListener callback_radionutton = new OnCheckedChangeListener(){

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			IsRadioButtonChecked = true;
		}
    	
    };
    //ΪButton���õ���ص�����
    private OnClickListener callback_button = new OnClickListener(){

		public void onClick(View v) {
			try
			{
				// TODO Auto-generated method stub
				//����ѡ��������״̬�仯�ı�־λ��������ʾ�Ƿ�ѡ���ˡ�
				if (!IsRadioButtonChecked){
					//return;
				}
				//ȡ���������
				EditText et = (EditText)findViewById(R.id.height_Edit);
				double height = Double.parseDouble(et.getText().toString());
				
				//ȡ���Ա�
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
				
				//new һ��Intent���󣬲�ָ��class
				Intent intent = new Intent();
				intent.setClass(mainActivity.this, BMIActivity.class);
				//new һ��Bundle���󣬲�ָ��Ҫ���������
				Bundle bundle = new Bundle();
				bundle.putDouble("height", height);
				bundle.putString("sex", sex);
				
				//��Bundle����assign��Intent
				intent.putExtras(bundle);
				
				//����BMIActivity��һ����startActivity�������صģ�
				//��һ��startActivityForResult�Ǵ����صġ�
				//startActivity(intent);
				startActivityForResult(intent, my_requestcode);
				//
			}catch (Exception e){
				Toast.makeText(mainActivity.this, R.string.errorString, Toast.LENGTH_LONG).show();
			}
		}
    	
    };
    
    @Override
    //�����ص���Ϣ
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
            //�ж��Ա�
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