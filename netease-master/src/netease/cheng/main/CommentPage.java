package netease.cheng.main;

import java.util.ArrayList;
import java.util.List;

import netease.cheng.adapters.CommentAdapter;
import netease.cheng.beans.CommentInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ����
 * @author Administrator
 *
 */
public class CommentPage extends Activity implements OnClickListener{
	private List<CommentInfo> data=new ArrayList<CommentInfo>();
	private TextView title;
	private ImageView slider;
	private TextView well,today,week;
	private ListView listView;
	private int animLoc;
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.comment_main);
		init();
	}
	private void init(){
		listView=(ListView)findViewById(R.id.comment_list);
		well=(TextView)findViewById(R.id.comment_top_comments);
		today=(TextView)findViewById(R.id.comment_everyday_comments_rank);
		week=(TextView)findViewById(R.id.comment_everyweek_comments_rank);
		slider=(ImageView)findViewById(R.id.comment_column_slide_bar);
		well.setOnClickListener(this);
		today.setOnClickListener(this);
		week.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(CommentPage.this,position+"-->����",2000).show();
				startActivity(new Intent(CommentPage.this,CommentNew.class));
			}
		});
		for(int i=0;i<15;i++){
			CommentInfo info=new CommentInfo();
			info.setContent("����Ƽ�������������ɨ�ؾ�����"+i);
			info.setSource("ԭ�ģ�����������������ߣ�"+i);
			info.setUsername("һ��÷��ɽ�����ѡ�"+i);
			data.add(info);
		}
		CommentAdapter adapter=new CommentAdapter(this,data);
		listView.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		int loc=0;
		switch(id){
		case R.id.comment_top_comments:
			loc=v.getLeft();
			break;
		case R.id.comment_everyday_comments_rank:
			loc=v.getLeft();
			break;
		case R.id.comment_everyweek_comments_rank:
			loc=v.getLeft();
			break;
		}
		setAnim(loc);
	}
	private void setAnim(int loc){
    	TranslateAnimation tran=new TranslateAnimation(animLoc,loc,
    			slider.getTop()-5,slider.getTop()-5);
    	tran.setDuration(100);
    	tran.setFillAfter(true);
    	slider.startAnimation(tran);
    	animLoc=loc;
    	tran=null;//���ڻ���
    }
}
