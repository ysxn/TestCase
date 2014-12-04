package netease.cheng.main;

import java.util.ArrayList;
import java.util.List;

import netease.cheng.adapters.TopicAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ������ҳ
 * @author Administrator
 *
 */
public class Topic extends Activity implements OnItemClickListener{
	private ListView list;
	private List<String> data=new ArrayList<String>();
	private TextView text;
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.topic);
		text=(TextView)findViewById(R.id.top_title);
		text.setText("����");
		list=(ListView)findViewById(R.id.topic_list);
		init();
		list.setOnItemClickListener(this);
	}
	//��ʼ������
	private void init(){
		for(int i=0;i<15;i++){
			data.add("�����Ļظ��ɼ���ʱ�����ٻ�--->"+i);
		}
		TopicAdapter adapter=new TopicAdapter(this,data);
		View view=LayoutInflater.from(this).inflate(R.layout.topic_list_head,null);
		list.addHeaderView(view);
		list.addFooterView(LayoutInflater.from(this).inflate(R.layout.button,null));
		list.setAdapter(adapter);
//		list.addFooterView(createButton("�鿴����ʮ��"));
	}
	//��д����ѡ��ķ���---������д
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//����ʹ��--������ʾ�û�
		Toast.makeText(Topic.this,"--->"+position,2000).show();
		//������ת--������ҳ
		startActivity(new Intent(Topic.this,DetailPage.class));
	}
}
