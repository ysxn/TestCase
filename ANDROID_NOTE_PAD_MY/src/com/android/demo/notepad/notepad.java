package com.android.demo.notepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class notepad extends Activity implements Button.OnClickListener {
    /** Called when the activity is first created. */
	//Ҫʹ�õĶ��󣬱�������
	//���沿��
	private EditText inputArt;//�༭�������û��ַ���
	private Button saveButton;//���水ť
	private String Text_of_input;//�û�������ַ���
	private OutputStream os;//�ļ�����������浽�ļ�
	
	//����ʱ�����ļ�������
	private EditText inputFilename;//�༭�������û��ַ���
	private Button ConfirmFilenameButton;//ȷ����ť
	private Button BackToMain;//����main��ť
	
	//��ʱ�����ļ�������
	private EditText inputFilename_open;//�༭�������û��ַ���
	private Button ConfirmFilenameButton_open;//ȷ����ť
	private Button BackToMain_open;//����main��ť
	
	//��ȡ����
	private TextView showmyText;//��ʾ��ȡ�ļ�����
	private Button openTxt, cleanTxt;//���ļ���ť�������ʾ���ݰ�ť
	private String Text_of_output;//���ļ���ȡ���ַ���
	private InputStream is;//�ļ�����������ȡ�ļ�
	private byte[] b;//�ֽ����飬Ӧ�ö�ȡ�ļ�����
	
	//ʹ���Զ���·������ļ�
	private FileOutputStream my_os;
	private FileInputStream my_is;
	private File my_file;
	
	//�����ļ�
	private String setting_abs_path = "/sdcard/setting.txt";
	private File setting_file;
	
	//Ĭ�ϵ�����Ŀ¼
	private String dir_abs_path = "/sdcard/ebook/";
	//�Զ��������Ŀ¼
	private String dir_abs_path_setting = " ";
	//�Զ������������
	private String giving_file_name = " ";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutShow(R.layout.main);//���ò���
        UIinit("main");//��ʼ��UIԪ�ط���
        Logic("main");//����¼��߼�����
        InitFileDir();//��ʼ�����ã��Լ�Ĭ�ϵ�����Ŀ¼
        
    }

    //��ʼ�����ã��Լ�Ĭ�ϵ�����Ŀ¼
    private void InitFileDir() {
		// TODO Auto-generated method stub
    	setting_file = new File(setting_abs_path);
    	if (setting_file.exists()){
    		Open_and_read_setting_file();//�������ļ�����ȡĬ�ϵ�����Ŀ¼������ǿգ�
        	if (dir_abs_path_setting.equals(" ")){
        		Open_and_write_setting_file();//�������ļ���д��Ĭ�ϵ�����Ŀ¼
        	}
    	}else{
    		Open_and_write_setting_file();//�������ļ���д��Ĭ�ϵ�����Ŀ¼
    	}
    				
	}

    //�������ļ���д��Ĭ�ϵ�����Ŀ¼�����Զ���Ŀ¼
	private void Open_and_write_setting_file() {
		// TODO Auto-generated method stub
		try {
			//���ļ������������txtME���Բ�����ģʽ��
			//os = this.openFileOutput("txtME", MODE_PRIVATE);
			my_os = new FileOutputStream(setting_file, false);
			//���ַ���ת���ֽ����飬д���ļ���
			//os.write(Text_of_input.getBytes());
			if (dir_abs_path_setting.equals(" ")){
				my_os.write(dir_abs_path.getBytes());
				dir_abs_path_setting = dir_abs_path;
				NoteDebug("����Ĭ��Ŀ¼: " + dir_abs_path_setting);
			}else{
				my_os.write(dir_abs_path_setting.getBytes());
				NoteDebug("�����Զ���Ŀ¼: " + dir_abs_path_setting);
			}
							
		}catch (FileNotFoundException e){
			/*�ļ�δ�ҵ����쳣*/
			// TODO Auto-generated catch block
			NoteDebug("�ļ��ر�ʧ��" + e);
		}catch (IOException e){
			/*�ļ�д�����*/
			// TODO Auto-generated catch block
			NoteDebug("�ļ�д��ʧ��" + e);
		}finally {
			try {
				/*�ر��ļ������*/
				//os.close();
				my_os.close();
			}catch (IOException e){
				// TODO Auto-generated catch block
				NoteDebug("�ļ��ر�ʧ��" + e);
			}
		}
		
	}

	//�������ļ�����ȡĬ�ϵ�����Ŀ¼
	private void Open_and_read_setting_file() {
		// TODO Auto-generated method stub
		try {
			/*���ļ�������������txtME*/
			//is = this.openFileInput("txtME");
			my_is = new FileInputStream(setting_file);
			//��ʼ���ֽ�����
			b = new byte[1024];
			//���ļ���������ȡ���ݵ��ֽ�������������ݳ���
			//int length = is.read(b);
			int length = my_is.read(b);
			//���ֽ�����ת��Ϊ�ַ���
			if (length > 0){
				dir_abs_path_setting = new String(b);
			}
			//���ñ��⣬��ʾ�ļ����ݳ���
			if (length > 0){
				NoteDebug("��ȡ���ã�" + length + " bytes,Ŀ¼: " + dir_abs_path_setting);
			}
			//��ʾ����
			//showmyText.setText(Text_of_output);
		}catch (FileNotFoundException e) {
			/*�ļ�δ�ҵ����쳣*/
			// TODO Auto-generated catch block
			NoteDebug("�ļ���ʧ��" + e);
		} catch (IOException e) {
			/*�ļ���ȡ�����쳣*/
			// TODO Auto-generated catch block
			NoteDebug("�ļ���ȡʧ��" + e);
		}finally {
			try {
				/*�ر��ļ�������*/
				//is.close();
				my_is.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				NoteDebug( "�ļ��ر�ʧ��"+e);
			}
		}
		
	}
	
	//����¼��߼�����
    private void Logic(String string) {
		// TODO Auto-generated method stub
    	/*Ϊ��ť����¼�����*/
    	if (string.equals("main")) {
    		saveButton.setOnClickListener(this);
    	} else if (string.equals("open")) {
    		openTxt.setOnClickListener(this);
    		cleanTxt.setOnClickListener(this);
    	} else if (string.equals("save")){
    		ConfirmFilenameButton.setOnClickListener(this);
    		BackToMain.setOnClickListener(this);
    	}
	}

	//��ʼ��UIԪ�ط���
	private void UIinit(String mainROopen) {
		// TODO Auto-generated method stub
		if (mainROopen.equals("main")){
			inputArt = (EditText) findViewById(R.id.EditText_Txt);
			saveButton = (Button) findViewById(R.id.Button_Save);
		}else if (mainROopen.equals("open")){
			showmyText = (TextView) findViewById(R.id.TextView_showTxt);
			openTxt = (Button) findViewById(R.id.Button_openTxt);
			cleanTxt =(Button)findViewById(R.id.Button_clean);
		}else if (mainROopen.equals("save")){
			inputFilename = (EditText) findViewById(R.id.EditText_Filename);
			ConfirmFilenameButton = (Button) findViewById(R.id.Button_ConfirmFilename);
			BackToMain = (Button) findViewById(R.id.Button_BackToMain);
		}
	}

	//���ò���
	private void setLayoutShow(int layoutID) {
		// TODO Auto-generated method stub
		setContentView(layoutID);
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*����ID�жϰ�ť�¼�*/
		switch (v.getId()) {
		case R.id.Button_ConfirmFilename:{
			// TODO Auto-generated method stub
			/*����û�������ַ���*/
			giving_file_name = inputFilename.getText().toString();
			my_file = new File(dir_abs_path_setting + giving_file_name + ".txt");
			try {
				//���ļ������������txtME���Բ�����ģʽ��
				//os = this.openFileOutput("txtME", MODE_PRIVATE);
				my_os = new FileOutputStream(my_file, false);
				//���ַ���ת���ֽ����飬д���ļ���
				//os.write(Text_of_input.getBytes());
				my_os.write(Text_of_input.getBytes());
			}catch (FileNotFoundException e){
				/*�ļ�δ�ҵ����쳣*/
				// TODO Auto-generated catch block
				NoteDebug("�ļ��ر�ʧ��" + e);
			}catch (IOException e){
				/*�ļ�д�����*/
				// TODO Auto-generated catch block
				NoteDebug("�ļ�д��ʧ��" + e);
			}finally {
				try {
					/*�ر��ļ������*/
					//os.close();
					my_os.close();
					/*��ʾ*/
					NoteDebug("�ɹ����浽:" + dir_abs_path_setting + giving_file_name + ".txt");
				}catch (IOException e){
					// TODO Auto-generated catch block
					NoteDebug("�ļ��ر�ʧ��" + e);
				}
			}
						
		}
		break;
		
		case R.id.Button_BackToMain:{
			/*��ʾmain.xmlΪ��������*/
			setLayoutShow(R.layout.main);
			UIinit("main");
			Logic("main");
		}
		break;
		
		case R.id.Button_Save:{
			/*��ʾsave.xmlΪ��������*/
			setLayoutShow(R.layout.save);
			UIinit("save");
			Logic("save");
		}
		break;
		
		case R.id.Button_openTxt: {
			NoteDebug("�ļ���");
			try {
				/*���ļ�������������txtME*/
				//is = this.openFileInput("txtME");
				my_is = new FileInputStream(my_file);
				//��ʼ���ֽ�����
				b = new byte[1024];
				//���ļ���������ȡ���ݵ��ֽ�������������ݳ���
				//int length = is.read(b);
				int length = my_is.read(b);
				//���ֽ�����ת��Ϊ�ַ���
				Text_of_output = new String(b);
				//���ñ��⣬��ʾ�ļ����ݳ���
				setTitle("��ȡ�ļ���" + length + " bytes" );
				//��ʾ����
				showmyText.setText(Text_of_output);
			}catch (FileNotFoundException e) {
				/*�ļ�δ�ҵ����쳣*/
				// TODO Auto-generated catch block
				NoteDebug("�ļ���ʧ��" + e);
			} catch (IOException e) {
				/*�ļ���ȡ�����쳣*/
				// TODO Auto-generated catch block
				NoteDebug("�ļ���ȡʧ��" + e);
			}finally {
				try {
					/*�ر��ļ�������*/
					//is.close();
					my_is.close();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					NoteDebug( "�ļ��ر�ʧ��"+e);
				}
			}
		}
			break;
		case R.id.Button_clean :{
			/*���*/
			showmyText.setText("");
			NoteDebug("���");
			}
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		/*��������˵���Ŀ��������ͼƬ*/
		menu.add(0, 1, 1, "Edit").setIcon(R.drawable.ic_menu_edit);
		menu.add(0, 2, 2, "Open").setIcon(R.drawable.ic_menu_agenda);
		menu.add(0, 3, 3, "Exit").setIcon(R.drawable.ic_lock_power_off);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case 1:
				/*��ʾmain.xmlΪ��������*/
				setLayoutShow(R.layout.main);
				UIinit("main");
				Logic("main");
				NoteDebug("�༭�ļ�Layout");
				break;
			case 2:
				/*��ʾopen.xmlΪ��������*/
				setLayoutShow(R.layout.open);
				UIinit("open");
				Logic("open");
				NoteDebug( "���ļ�Layout");
				break;
			case 3:
				/*�˳�*/
				finish();
				NoteDebug( "Byebye");
				break;
			default:
				break;
		} 
		return super.onOptionsItemSelected(item);
	}

	private void NoteDebug(String string) {
		// TODO Auto-generated method stub
		/*��ʾToast��ʾ*/
		Toast.makeText(this,string, Toast.LENGTH_SHORT ).show();
	}
}