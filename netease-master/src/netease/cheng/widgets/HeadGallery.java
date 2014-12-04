package netease.cheng.widgets;

import netease.cheng.adapters.HeadGalleryAdapter;
import netease.cheng.beans.NewsInfo;
import netease.cheng.main.DetailPage;
import netease.cheng.main.R;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.TextView;
/**
 * δ���ͼƬָʾ��--�Զ���֡������
 * @author Administrator
 *
 */
public class HeadGallery extends FrameLayout implements 
				OnItemSelectedListener,OnItemClickListener{
	private MyGallery mGallery;
	private TextView tag;
	private TextView title;
	private HeadGalleryAdapter mAdapter;
	public HeadGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public HeadGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public HeadGallery(Context context) {
		super(context);
	}
	//��д��������
	public void setOnItemClickListener(
			AdapterView.OnItemClickListener listener) {
		this.mGallery.setOnItemClickListener(listener);
	}
	//�����Ƿ��Զ�����
	public void startAutoScroll(boolean bool) {
		if (this.mGallery !=null){
			this.mGallery.startAutoScroll(bool);
		}
	}
	@Override//��������xml�ļ�ʱ����
	protected void onFinishInflate() {
		//�˷�����Ҫһ����Դ�ļ�
		mGallery=(MyGallery)findViewById(R.id.head_mygallery);//��Ҫһ����Դ�ļ�
		mGallery.setAutoTime(0);
		mGallery.setOnItemSelectedListener(this);
		mGallery.setOnItemClickListener(this);
		tag=(TextView)findViewById(R.id.head_text_tag);
		title=(TextView)findViewById(R.id.head_text_title);
		super.onFinishInflate();
	}
	//���з���
	public MyGallery getmGallery() {
		return mGallery;
	}
	public void setmGallery(MyGallery mGallery) {
		this.mGallery = mGallery;
	}
	//�������������ݸ��Զ��廭��
	public void setAdapter(HeadGalleryAdapter adapter) {
		if (adapter != null){
			this.mGallery.setAdapter(adapter);
			this.mGallery.setSelection(adapter.getInitLoc(0));
			this.mAdapter = adapter;
		}
	}
	@Override//�ص����ര�ڿɼ��Ըı�ʱ�Ĵ���
	protected void onWindowVisibilityChanged(int loc) {
		super.onWindowVisibilityChanged(loc);
		if (((loc == 0) && (((isShown()) || (this.mGallery == null) 
				|| (this.mAdapter == null))))|| (this.mAdapter.getCount() <= 0)){
			return;
		}
		this.mGallery.setSelection(this.mAdapter.getInitLoc(this.mGallery
				.getSelectedItemPosition()));
	}
	
	@Override//�ص����봰��
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if ((this.mGallery == null) || (this.mAdapter == null)
				|| (this.mAdapter.getCount() <= 0)){
			return;
		}
		this.mGallery.setSelection(this.mAdapter.getInitLoc(this.mGallery
				.getSelectedItemPosition()));
	}
	@Override//��д��ѡ�ķ���
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if(mAdapter!=null){
			NewsInfo info=mAdapter.getItem(position);
			if(!info.getTag().equals("")){
				this.tag.setText(info.getTag());
				this.tag.setBackgroundResource(R.drawable.topcomment_column);
			}
			this.title.setText(info.getTitle());
			System.out.println(info.getTag()+"---"+info.getTitle());
		}
		System.out.println("�ȴ�ʵ��====��---->"+position);
	}
	//������
	public void onNothingSelected(AdapterView<?> parent) {}
	//����ָʾ��
	public void setIndicator(){
		System.out.println("�ȴ�ʵ��ָʾ��");
//		this.mIndicator.setIndicatorBg(paramContext, paramThemeSettingsHelper);
//		paramThemeSettingsHelper.setViewBackgroud(paramContext, this.mTag,
//				2130837732);
//		paramThemeSettingsHelper.setTextViewColor(paramContext, this.mTag,
//				2131230765);
	}
	@Override//��дѡ�����ʱ�ص��˷���
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("ItemClickListener--->"+position);
		this.getContext().startActivity(new Intent(getContext(),DetailPage.class));
	}
}
