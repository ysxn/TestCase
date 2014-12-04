package netease.cheng.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.Scroller;
//��ֱ������ʾ��---��δʹ��--��Ϊ����ʹ��
public class VerticalScroll extends ViewGroup{
	//���ھ�ֹ״̬
	private static final int TOUCH_STATE_REST=0;
	//���ڹ���״̬
	private static final int TOUCH_STATE_SCROLLING=1;
	public int maxHeight=60;
	//��ǰ����״̬
	private int touchState;
	//������
	private Scroller mScroller;
	//��ʾĬ���ӿؼ�
	private int defaultChild=1;
	//ָ����ʾ����Ļ�����Ŀؼ�
	private int topChild;
	//�����µĵ�һ���ؼ�
	private View firstChild;
	//��������X����
	private float lastMotionX;
	//��������Y����
	private float lastMotionY;
	//��Ӧ��������С����ֵ
	private int touchSlop;
	public VerticalScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public VerticalScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public VerticalScroll(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context){
		mScroller=new Scroller(context);
		//Ĭ����ʾ�ڶ����ӿؼ�
		topChild=defaultChild;
		//��ȡ�����Ĵ�������--����������--slop���
		touchSlop=ViewConfiguration.get(getContext()).getScaledTouchSlop();
		touchState=TOUCH_STATE_REST;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(changed){
			int childTop=0;
			final int num=getChildCount();
			for(int i=0;i<num;i++){
				final View child=this.getChildAt(i);
				if(child.getVisibility()!=View.GONE){
					final int childWidth=child.getMeasuredWidth();
					final int childHeight=child.getMeasuredHeight();
					//Ϊÿһ���ӿؼ�����λ�������Ǵ�ֱ��������ֻ����������
					child.layout(0, childTop,childWidth ,childTop+=childHeight);
				}
			}
		}
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();//����δʵ�ֵķ���
		if(mScroller.computeScrollOffset()){
			//������ͼ�ı��λ��
			this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			//����֪ͨ��ͼ
			this.postInvalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//����ߴ�
		int measuredHeight = measureHeight(heightMeasureSpec);
		int measuredWidth = measureWidth(widthMeasureSpec);
		System.out.println(measuredHeight+"---onMeasure---"+measuredWidth);
		this.setMeasuredDimension(measuredHeight, measuredWidth);
	    //������ͬ�Ŀ��   ---��ȡ����ͼ����
	    final int count = getChildCount();   
	    //Ϊÿһ����ͼ������
	    for (int i = 0; i < count; i++) {   
	        getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);   
	    }   
	    //���򵽵�һ������ͼ����
	    scrollTo(0,getChildAt(0).getHeight());
	}
	private int measureHeight(int measureHeight){
		//ԭ�������������һ��--��������һ��--�ݲ�����
		int specMode=MeasureSpec.getMode(measureHeight);
		int specSize=MeasureSpec.getSize(measureHeight);
		int result=480;
		if(specMode==MeasureSpec.AT_MOST){
			result=specSize;
		}else if(specMode==MeasureSpec.EXACTLY){
			result=specSize;
		}
		return result;
	}
	private int measureWidth(int measureWidth){
		//ԭ�������������һ��--��������һ��--�ݲ�����
		int specMode=MeasureSpec.getMode(measureWidth);
		int specSize=MeasureSpec.getSize(measureWidth);
		int result=320;
		if(specMode==MeasureSpec.AT_MOST){
			result=specSize;
		}else if(specMode==MeasureSpec.EXACTLY){
			result=specSize;
		}
		return result;
	}
	//���ע�������Ը÷�����Ҫ�����߳�����д--�ڴ˴�дֻ���Ǿ�̬��ʾ������
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action=event.getAction();
		final float x=event.getX();
		final float y=event.getY();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			if(mScroller.isFinished()){
				//ǿ�ƽ�����������
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			int distanceY=(int)(lastMotionY-y);
			lastMotionY=y;
			//�˷������ص���ط���getScrollY()��ȡY����ı�ľ���
			scrollBy(0,distanceY);
			break;
		case MotionEvent.ACTION_UP:
			//��ȡ��ǰ��ͼ���������Եλ��
			final int scrollY=getScrollY();
			//���ֺ�ʼ������ʵ�ʵ�Ŀ��Y����
			final int deltaY=getHeight()-scrollY;
			//���������ʱ��
			final int time=Math.abs(deltaY)*2;
			if(deltaY>maxHeight){
				//��������ֹͣ��λ��
				mScroller.startScroll(0,scrollY, 0,deltaY-maxHeight,time);
			}else{
				mScroller.startScroll(0,scrollY, 0,deltaY,time);
			}
			invalidate();//֪ͨ�ػ����
			//ִ�����ֵΪ��ֹ״̬
			touchState=TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			touchState=TOUCH_STATE_REST;
			break;
		}
		return true;//���뷵��true���������̲߳���Ӧ
	}
	public void setHeadHeight(int height){
		maxHeight=height;
	}
	//���ش����¼�����ʾ��������
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		final int action=event.getAction();
		if((action==MotionEvent.ACTION_MOVE)&&touchState!=TOUCH_STATE_REST){
			return true;
		}
		final float x=event.getX();
		final float y=event.getY();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			lastMotionX=x;
			lastMotionY=y;
			touchState=mScroller.isFinished()?TOUCH_STATE_REST:TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_MOVE:
			final int yDistance=(int)Math.abs(lastMotionY-y);
			if(yDistance>touchSlop){
				touchState=TOUCH_STATE_SCROLLING;
			}
			break;
			//ͬʱ���������¼���ȡ���¼�
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			touchState=TOUCH_STATE_REST;
			break;
		}
		return true;//�����⴫�������¼�
	}
	
}
