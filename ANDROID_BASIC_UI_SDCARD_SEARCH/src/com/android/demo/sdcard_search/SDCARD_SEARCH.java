package com.android.demo.sdcard_search;

import android.app.Activity;
import android.os.Bundle;
import java.io.File;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SDCARD_SEARCH extends Activity {
    /** Called when the activity is first created. */
	//�������Ҫʹ�õ������
	private File file;
	private String path;
	private String searchpath;
	private String info;
	private String theKey_formInput;
	private TextView show_Result;
	private EditText input_SearchKey_Edit;
	private Button toSearch_Button;
	
	private EditText input_Path_Edit;
	private Button toConfirmPath_Button;
	
	private Button toClearresult_Button;
	private TextView show_pathinfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //findViewByIdʵ��������
        show_Result = (TextView) findViewById(R.id.TextView_Result);
        input_SearchKey_Edit = (EditText) findViewById(R.id.input_KEY_EditText);
        toSearch_Button = (Button) findViewById(R.id.Button_Search );
        toClearresult_Button = (Button) findViewById(R.id.Button_Clearresult );
        
        input_Path_Edit = (EditText) findViewById(R.id.input_PATH_EditText );
        toConfirmPath_Button = (Button) findViewById(R.id.Button_SearchPath );
        
        show_pathinfo = (TextView) findViewById(R.id.current_pathinfo );
        //Ϊ������ť��ӵ���¼�������
        toSearch_Button.setOnClickListener(callback_toSearch_Button);
        
        //Ϊȷ��·����ť��ӵ���¼�������
        toConfirmPath_Button.setOnClickListener(callback_toConfirmPath_Button);
        
        //Ϊ������������ť��ӵ���¼�������
        toClearresult_Button.setOnClickListener(callback_toClearresult_Button);
        
        //��ʼ������·��
        searchpath = "/sdcard";
        
        //��ʼ��һ��File����ָ��·��λ/sdcard
        file = new File(searchpath);
        
        //��xmlȡ���ַ���
        info = getString(R.string.info);
        
        //��ʾ����·��
        show_pathinfo.setText(getString(R.string.pathinfo) + searchpath);
        
    }

    //������ť�¼�����
    private OnClickListener callback_toSearch_Button = new OnClickListener(){
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		//���
    		path = "";
    		show_Result.setText("");
    		//ȡ��������Ҫ��ѯ��Key
    		theKey_formInput = input_SearchKey_Edit.getText().toString();
    		//����ļ�
    		BrowserFile(file);
    	}
    };

  //Ϊȷ��·����ť��ӵ���¼�������
    private OnClickListener callback_toConfirmPath_Button = new OnClickListener(){
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		searchpath = "/" + input_Path_Edit.getText().toString();
    		show_pathinfo.setText(getString(R.string.pathinfo) + searchpath);
    		file = new File(searchpath);
    	}
    };
    
    //������������ť�¼�����
    private OnClickListener callback_toClearresult_Button = new OnClickListener(){
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		//���
    		show_Result.setText("");
    	}
    };
    
	//����ļ�����
	private void BrowserFile(File file) {
		// TODO Auto-generated method stub
		if (theKey_formInput.equals("")){
			Toast.makeText(this, getString(R.string.pleaseInput), 
					Toast.LENGTH_SHORT).show();
			
		}else{
			//��ʼ�����ļ�
			ToSearchFiles(file);
			//������ɺ�������Ϊ�գ���ʾû�ҵ�
			if (show_Result.getText().equals("")){
				Toast.makeText(this, getString(R.string.notFond), 
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	//��ʼ�����ļ�����
	private void ToSearchFiles(File file) {
		// TODO Auto-generated method stub
		//����һ��File�ļ����飬�������/sdcardĿ¼�µ��ļ����ļ���
		File[] the_Files = file.listFiles();
		//int index = sizeof(the_Files);
		//ͨ�����������ļ����ļ���
		for (File tempF : the_Files){
			Log.i("ZHUYAWEN", "infomation in sdcard search: File tempF = " + tempF.getName());
			if (tempF.isDirectory()){
				ToSearchFiles(tempF);
			}else{
				try {
					//���ļ�������бȽϣ�����ļ����ư�����������Key���򷵻ش���-1��ֵ
					if (tempF.getName().indexOf(theKey_formInput) > -1){
						//��ȡ�����������ļ�·���������ۼ�
						path += "\n" + tempF.getPath();
						//��ʾ���
						show_Result.setText(info + searchpath + "\n" + path);
					}
					
				}catch (Exception e){
					//���·���Ҳ�������ʾ����
					Toast.makeText(this, getString(R.string.pathError), 
							Toast.LENGTH_SHORT).show();
					
				}
			}
		}
	}
}