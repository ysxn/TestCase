package netease.cheng.main;

import java.util.ArrayList;
import java.util.List;

import netease.cheng.adapters.HeadGalleryAdapter;
import netease.cheng.adapters.NewsAdapter;
import netease.cheng.beans.NewsInfo;
import netease.cheng.utils.ViewUtils;
import netease.cheng.widgets.HeadGallery;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
/**
 * ������ҳ��---��ʹ��ѡ�ʱ�������������棻��Щ�����¼���ʧЧ--�Զ���˵�����
 * ����ͨ����д��������---���߷����¼�ʵ��
 * @author Administrator
 *
 */
public class MainClass extends Activity {
    private ImageView sliding;
    private HorizontalScrollView hori;
    private ImageButton left,right;
    private LinearLayout lay;
    private ImageView image,indicator;
    private FrameLayout frame;
    private List<String> titles=new ArrayList<String>();
    List<String> descs=new ArrayList<String>();
    List<String> details=new ArrayList<String>();
    private ViewFlipper flipper;
    private LayoutInflater inflater;
    private View view,menuView,root,window,mainView;
    private ListView news_list;
    private PopupWindow popup;
    private TextView refresh_hint,refresh_time;
    int[] resDraw={R.drawable.bg1,
		R.drawable.bg2,R.drawable.bg3,R.drawable.duoyun};
    private List<NewsInfo> news;
    private int screenWidth,screenHeight,patch,slidLoc=0,index=0;
    private int flag=1; 
    //��UI���洴��ʱ�ص��˷���
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sliding=(ImageView)findViewById(R.id.main_frame_sliding);
        left=(ImageButton)findViewById(R.id.main_left);
        right=(ImageButton)findViewById(R.id.main_right);
        lay=(LinearLayout)findViewById(R.id.linearLayout1);
        hori=(HorizontalScrollView)findViewById(R.id.main_horizontal);
        image=(ImageView)findViewById(R.id.main_frame_sliding);
        frame=(FrameLayout)findViewById(R.id.frameLayout1);
        flipper=(ViewFlipper)findViewById(R.id.main_flipper);
        
        screenWidth=this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight=this.getWindowManager().getDefaultDisplay().getHeight();
        patch=(screenWidth-hori.getPaddingLeft()-hori.getPaddingRight())/6;
        System.out.println(patch);
        initData();
        left.setVisibility(View.INVISIBLE);
        if(lay.getChildCount()<=5){
        	right.setVisibility(View.INVISIBLE);
        }
        hori.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				//������Ҫ��������ֵ
//				System.out.println(v.getScrollX());
				//�Ȼ�ȡ�������ϵ�X����λ��
				if(lay.getChildCount()>5){
					int dis=v.getScrollX();//������������
					if(v.getScrollX()!=0){
						left.setVisibility(View.VISIBLE);
					}else{
						left.setVisibility(View.INVISIBLE);
					}
					int num=patch*(lay.getChildCount()-6);
					if(dis>=num-2){
						right.setVisibility(View.INVISIBLE);
					}else{
						right.setVisibility(View.VISIBLE);
					}
				}
				return v.onTouchEvent(event);//�˴����ܷ���ֱ��true������HorizontalScrollView����Ӧ
			}
		});
    }
    //��ʼ��ģ������
    private void initData(){
    	titles.add("ͷ��");
    	titles.add("�ֻ�");
    	titles.add("����");
    	titles.add("�Ƽ�");
    	titles.add("����");
//    	titles.add("����");
    	titles.add("�ƾ�");
    	titles.add("����");
    	for(int i=0;i<15;i++){
    		descs.add("ͷ���������ھ���"+(i+1));
    		details.add("�絶˪��������ᷢ�Ͽη��ش����յ��׷�"+i);
    	}
    	//��̬���TextView
    	for(int i=0;i<titles.size();i++){
    		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(patch,-2);
    		lay.addView(ViewUtils.createText(this,i,titles.get(i)), i, params);
    	}
    	for(int i=0;i<lay.getChildCount();i++){
    		lay.getChildAt(i).setOnClickListener(scrollListener);
    	}
    	for(int i=0;i<lay.getChildCount();i++){
    		flipper.addView(ViewUtils.createListView(this));
    	}
    	news=new ArrayList<NewsInfo>();
    	for(int i=0;i<resDraw.length;i++){
    		Bitmap b=BitmapFactory.decodeResource(getResources(), resDraw[i]);
    		NewsInfo info=new NewsInfo();
    		info.setBitmap(b);
    		if(i==1){
    			info.setTag("");
    		}else{
    			info.setTag("ͼ��"+i);
    		}
    		info.setTitle("�������ھ���-->"+i);
    		news.add(info);
    	}
    	requestData();
    	initMenu();
    }
    //����������
    OnClickListener scrollListener=new OnClickListener(){
		public void onClick(View v) {
			setAnim(v.getLeft());//���û�����Ķ���Ч��
			switch(v.getId()){
			case 0:break;
			case 1:break;
			case 2:break;
			case 3:break;
			case 4:break;
			case 5:break;
			case 6:break;
			case 7:break;
			}
			flipper.setDisplayedChild(v.getId());
			Toast.makeText(MainClass.this,titles.get(v.getId()),2000).show();
		}
    };
    //��������
    private void requestData(){
    	ListView list=(ListView)flipper.getChildAt(0);
		NewsAdapter adapter=new NewsAdapter(MainClass.this,descs,details);
		View v=LayoutInflater.from(MainClass.this).inflate(R.layout.head_advertisment,null);
		HeadGallery head=(HeadGallery)v.findViewById(R.id.head_advert_grally);
		HeadGalleryAdapter gAdapter=new HeadGalleryAdapter(this,news);
		head.setAdapter(gAdapter);
		list.addHeaderView(v);//����������������֮ǰ���ͷ
		list.addFooterView(LayoutInflater.from(this).inflate(R.layout.button,null));
		list.setAdapter(adapter);
		flipper.setDisplayedChild(0);
    }
    //��װ�����ķ���--���ڿ��ƻ��黬��
    private void setAnim(int loc){
    	TranslateAnimation tran=new TranslateAnimation(slidLoc,loc,
    			image.getTop()-frame.getPaddingTop(),
    			image.getTop()-frame.getPaddingTop());
    	//���ö���ʱ��
    	tran.setDuration(100);
    	//���ñ������������״̬
    	tran.setFillAfter(true);
    	image.startAnimation(tran);
    	slidLoc=loc;
    	tran=null;//���ڻ���
    }
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id=item.getItemId();
		switch(id){
		case 0:
			startActivity(new Intent(this,CommentNew.class));
			break;
		case 1:
			startActivity(new Intent(this,VotePage.class));
			break;
		case 2:
			startActivity(new Intent(this,ImagePage.class));
			break;
		case 3:
			exit();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		System.out.println("�򿪲˵���");
		popup.showAtLocation(mainView, Gravity.BOTTOM|
				Gravity.CENTER_HORIZONTAL, 0, 0);
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * ��ʼ���Զ���˵�
	 */
	private void initMenu(){
		window=LayoutInflater.from(this).inflate(R.layout.olympic_news_normal_menu,null);
		mainView=LayoutInflater.from(this).inflate(R.layout.kong,null);
		LinearLayout lay1=(LinearLayout)window.findViewById(R.id.menu_my_comment);
		LinearLayout lay2=(LinearLayout)window.findViewById(R.id.menu_my_collect);
		LinearLayout lay3=(LinearLayout)window.findViewById(R.id.menu_setting);
		lay1.setOnClickListener(menuListener);
		lay2.setOnClickListener(menuListener);
		lay3.setOnClickListener(menuListener);
		//��һ�������˵������ļ��� �ڶ��������˵���� �������������˵��߶ȡ�4�˵��Ƿ�ɻ�
		popup=new PopupWindow(window,-1,-2,true);
		popup.setAnimationStyle(R.style.menu_animation);
		popup.setOutsideTouchable(true);//����popup֮����Դ���
		//�������ò�����Ӧ��ͼ��
		window.setFocusableInTouchMode(true);		
//		popup.setBackgroundDrawable(new BitmapDrawable());
		//��������һ���ʱ����Ӧ
		window.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch(keyCode){
				case KeyEvent.KEYCODE_MENU:
				case KeyEvent.KEYCODE_BACK:
					System.out.println("��Ӧ��ͼ��");
					if(flag==1){
						flag=2;
					}else if(flag==2){
						popup.dismiss();						
					}
					break;
				}
				return true;
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
				System.out.println("��Ӧ�˵���");
				flag=1;
				popup.showAtLocation(mainView, Gravity.BOTTOM|
						Gravity.CENTER_HORIZONTAL, 0, 0);
				break;
			}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		System.out.println("��Ӧ���");
		popup.showAtLocation(mainView, Gravity.BOTTOM|
				Gravity.CENTER_HORIZONTAL, 0, 0);
		return false;
	}

	//�˵�����������
	View.OnClickListener menuListener=new View.OnClickListener(){
		public void onClick(View v) {
			int id=v.getId();
			switch(id){
			case R.id.menu_my_comment:
				startActivity(new Intent(MainClass.this,CommentNew.class));
				popup.dismiss();
				break;
			case R.id.menu_my_collect:
				startActivity(new Intent(MainClass.this,VoteSubmit.class));
				popup.dismiss();
				break;
			case R.id.menu_setting:
				startActivity(new Intent(MainClass.this,CommentPage.class));
				popup.dismiss();
				break;
			
			}
		}
	};
	private void exit(){
		finish();
	}
}