package netease.cheng.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
/**
 * ��ӭҳ��
 * @author Administrator
 *
 */
public class WelcomeAct extends Activity{
	//��Ϣ������
	Handler handler=new Handler();
	ImageView img;
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.root_welcome_main);
		img=(ImageView)findViewById(R.id.welcome_main_img);
		//�ӳ�����ִ��run1�߳�
		handler.postAtTime(run1,2000);
	}
	Runnable run1=new Runnable(){
		public void run(){
			//�����ӳ�����ִ���߳�run2
			handler.postDelayed(run2,2000);
			//����ͼƬ�ɼ�
			img.setVisibility(View.VISIBLE);
		}
	};
	Runnable run2=new Runnable(){
		public void run(){
			//��ת������ҳ
			startActivity(new Intent(WelcomeAct.this,TabTest.class));
			//���ٵ�ǰҳ��
			finish();
		}
	};
}
