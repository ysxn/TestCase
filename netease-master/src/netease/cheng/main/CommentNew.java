package netease.cheng.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import netease.cheng.adapters.CommentDetailAdapter;
import netease.cheng.beans.CommentResult;
import netease.cheng.utils.Rotate3DAnimation;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

public class CommentNew extends Activity{
	private static final int SOURCE=0;//��ʾԭ�ĵ���ͼ
	private static final int BACK=1;//��ʾ��������ͼ
	String newsHtml = "file:///android_asset/night_shownews.html";
    String blankHtml = "file:///android_asset/night_blank.html";
	private int page=0;
	private FrameLayout container, root1,root2;
	private WebView web1,web2;
	private ListView  list;
	private Button switcher1,switcher2;
	private int[] location=new int[2];
	private InputMethodManager inputMethod;
	private ClipboardManager clip;
	private LayoutInflater inflater;
	private View toolbar;
	private PopupWindow hint;
	private int itemLoc=0;
	private ImageButton imgbtn;
	private EditText input;
	private LinearLayout inputlay;
	private Button replybtn;
	private LinearLayout copy,replyroot;
	private boolean isShowInput=false;
	private List<CommentResult> data=new ArrayList<CommentResult>();
	
	public void onCreate(Bundle save){
		super.onCreate(save);
		setContentView(R.layout.new_comment);
		inputMethod=(InputMethodManager)getSystemService(
				Context.INPUT_METHOD_SERVICE);
		clip=(ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
		inflater=LayoutInflater.from(this);
		toolbar=inflater.inflate(R.layout.mycomment_toolbar,null);
		hint=new PopupWindow(toolbar,-2,-2);
		hint.setOutsideTouchable(true);//����δ����Ҳ�����Լ�
		hint.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				//�����ڱ�����ʱ�ص��˷���
				System.out.println("���ڱ�����");
			}
		});
		init();
	}
	OnClickListener inputListener=new OnClickListener(){
		public void onClick(View v) {
			int id=v.getId();
			switch(id){
			case R.id.reply_img_button:
				inputlay.setVisibility(View.VISIBLE);
				inputMethod.showSoftInput(input,0);
				isShowInput=true;
				break;
			case R.id.toolbar_copy_text:
				clip.setText("��Ҫ����������---�ʳɿ�");
				hint.dismiss();
				Toast.makeText(CommentNew.this,"�Ѹ��Ƶ�������",Toast.LENGTH_SHORT).show();
				break;
			case R.id.reply_button:
				inputlay.setVisibility(View.GONE);
				break;
			}
		}
	};
	private void init(){
		Random random=new Random();
		View view=inflater.inflate(R.layout.mycomment_toolbar,null);
		replyroot=(LinearLayout)findViewById(R.id.reply_layout_frame);
		copy=(LinearLayout)view.findViewById(R.id.toolbar_copy_text);
		container=(FrameLayout)findViewById(R.id.new_comment_container);
		root1=(FrameLayout)findViewById(R.id.new_comment_root1);
		root2=(FrameLayout)findViewById(R.id.new_comment_root2);
		web1=(WebView)findViewById(R.id.new_comment_web1);
		web2=(WebView)findViewById(R.id.new_comment_web2);
		list=(ListView)findViewById(R.id.new_comment_listview);
		switcher1=(Button)findViewById(R.id.new_comment_btn1);
		switcher2=(Button)findViewById(R.id.new_comment_btn2);
		imgbtn=(ImageButton)findViewById(R.id.reply_img_button);
		input=(EditText)findViewById(R.id.reply_edittext);
		inputlay=(LinearLayout)findViewById(R.id.reply_edittext_layout);
		replybtn=(Button)findViewById(R.id.reply_button);
		replybtn.setOnClickListener(inputListener);
		copy.setOnClickListener(inputListener);
		imgbtn.setOnClickListener(inputListener);
		switcher1.setOnClickListener(switcherListener);
		switcher2.setOnClickListener(switcherListener);
		for(int i=0;i<15;i++){
			CommentResult result=new CommentResult();
			result.setContent("�񶦷ɵ�ɰ��Ʒ�����ɽ����"+i);
			result.setCount(random.nextInt(1000));
			result.setTime("13Сʱǰ��"+i);
			result.setUser("������"+i);
			data.add(result);
		}
		CommentDetailAdapter adapter=new CommentDetailAdapter(this,data);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//��ȡ��ǰ�����item�ڴ��ڵ�λ�ø�ֵ��ָ������
				//��ʾ������Ҫ��ʾһ��֮��Ż�������Ŀ��
				view.getLocationInWindow(location);
				int tmpHeight=location[1]-50;//�������Ϸ�50���صĵط���ʾ
				int tmpWidth=toolbar.getWidth();
				int i=Gravity.TOP|Gravity.RIGHT;
				if(hint.isShowing()){
					if(position==itemLoc){
						hint.dismiss();
					}else{
						hint.dismiss();
						hint.showAtLocation(view, i, 0, tmpHeight);
						itemLoc=position;
					}
				}else{
					hint.showAtLocation(view,i, 0, tmpHeight);
					itemLoc=position;
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(inputMethod.isActive()){
				inputMethod.hideSoftInputFromInputMethod(input.getWindowToken(),0);
				inputlay.setVisibility(View.GONE);
			}else if(!inputMethod.isActive()&&page==BACK){
				startAnim(SOURCE,0.0F,90.0F);
				page=SOURCE;
			}else if(!inputMethod.isActive()&&page==SOURCE){
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	OnClickListener switcherListener=new OnClickListener(){
		public void onClick(View v) {
			int id=v.getId();
			switch(id){
			case R.id.new_comment_btn1:
				if(inputlay.getVisibility()==View.VISIBLE){
					inputMethod.hideSoftInputFromWindow(input.getWindowToken(),0);
					inputlay.setVisibility(View.GONE);
				}
				startAnim(BACK,0.0f,90.0f);
				
				break;
			case R.id.new_comment_btn2:
				if(inputlay.getVisibility()==View.VISIBLE){
					inputMethod.hideSoftInputFromWindow(input.getWindowToken(),0);
					inputlay.setVisibility(View.GONE);
				}
				if(hint.isShowing()){
					hint.dismiss();
				}
				startAnim(SOURCE,0.0f,90.0f);
				
				break;
			}
		}
	};
	
	
	//��д�����¼�
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		return super.dispatchKeyEvent(event);
	}

	private class NextView implements Animation.AnimationListener {
		private int loc;

		public NextView(int loc) {
			this.loc = loc;
		}

		public void onAnimationEnd(Animation animation) {
			container.post(new SwapView(loc));
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private class SwapView implements Runnable {
		private int loc=0;
		public SwapView(int loc) {
			this.loc = loc;
		}
		/*
		 * �������Ĳ�����SOURCE--->��ʾԭ��
		 * �������Ĳ�����BACK---->��ʾ����
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			//��ʾ����ҳ��
			if (this.loc ==BACK) {
				//��ʾ����ҳ��
				root2.setVisibility(View.VISIBLE);
				root1.setVisibility(View.GONE);
				//��ʾ������ť
				switcher2.setVisibility(View.VISIBLE);
				switcher1.setVisibility(View.GONE);
				//��ʾ�ײ��ؼ�
				replyroot.setVisibility(View.VISIBLE);
//				root2.requestFocus();
				page=BACK;
			} 
			//��ʾԭ��ҳ��
			else if(this.loc==SOURCE){
				//��ʾԭ�İ�ť
				switcher2.setVisibility(View.GONE);
				switcher1.setVisibility(View.VISIBLE);
				//��ʾwebҳ��
				root2.setVisibility(View.GONE);
				root1.setVisibility(View.VISIBLE);
				//ȥ���ײ��ؼ�
				replyroot.setVisibility(View.GONE);
				//webҳ�������
//				root1.requestFocus();
				page=SOURCE;
			}
			startAnim(-90.0f, 0.0f);
		}
	}

	private void startAnim(int loc, float fromDegrees, float toDegrees) {
		// Ĭ��loc��ȡֵ����Ϊ0��-1��from--->0.0f;to--->90.0f;
		Rotate3DAnimation d3 = new Rotate3DAnimation(fromDegrees, toDegrees,
				container.getWidth() / 2.0F, container.getHeight() / 2.0F,
				0.0F, true);
		d3.setDuration(250L);
		d3.setFillAfter(true);
		d3.setInterpolator(new AccelerateInterpolator());
		d3.setAnimationListener(new NextView(loc));
		this.container.startAnimation(d3);
	}

	private void startAnim(float fromDegrees, float toDegrees) {
		// Ĭ��loc��ȡֵ����Ϊ0��-1��from--->0.0f;to--->90.0f;
		Rotate3DAnimation d3 = new Rotate3DAnimation(fromDegrees, toDegrees,
				container.getWidth() / 2.0F, container.getHeight() / 2.0F,
				0.0F, true);
		d3.setDuration(250L);
		d3.setFillAfter(true);
		d3.setInterpolator(new AccelerateInterpolator());
		this.container.startAnimation(d3);
	}
	//�ȴ�ʵ�ֵļ���
	private void test(){
		Spanned span=Html.fromHtml("��������һ��html��ʽ���ַ�����");
		
	}
}
