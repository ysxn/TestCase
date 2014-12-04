package netease.cheng.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
//����
public class MyGallery extends Gallery {
	// �Զ��������߳�
	private Runnable auto = new Runnable() {
		public void run() {
			scrollRight();// �������ұ�
			if (autoTime > 0) {
				postDelayed(this, autoTime);// ����������У����÷�ʹ��ѭ��
			}
		}
	};
	private int autoTime = 0;
	private Runnable plusRunable;

	public MyGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyGallery(Context context) {
		super(context);
		init();
	}

	private void init() {
		// �������е�ѡ��͸����һ��
		this.setUnselectedAlpha(1.0f);
		// ���ü��һ��
		this.setSpacing(0);
		// ��������Ӱ
		this.setFadingEdgeLength(0);
		// ����������Ч��
		this.setSoundEffectsEnabled(false);
	}

	// ������ð��������Ч��==�̳еķ���--�ƶ��������һ��ͼƬ
	private void scrollLeft() {
		onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
	}

	// ������ð����Ҽ���Ч��==�̳еķ���--�ƶ����ұ���һ��ͼƬ
	private void scrollRight() {
		onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
	}

	// ���ȵ�X����λ��
	private int getCenterOfGallery() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	// ָ��ͼƬ������λ��
	private static int getCenterOfView(View paramView) {
		return paramView.getLeft() + paramView.getWidth() / 2;
	}

	public int getAutoTime() {
		return autoTime;
	}

	public void setAutoTime(int autoTime) {
		this.autoTime = autoTime;
		if (autoTime <= 0) {
			startAutoScroll(false);
		} else {
			// ���ö�������ʱ��
			setAnimationDuration(1000);
			startAutoScroll(true);
		}
	}

	public void startAutoScroll(boolean paramBoolean) {
		removeCallbacks(this.auto);
		if ((!paramBoolean) || (this.autoTime <= 0)) {
			return;
		}
		// ���Ƴ��߳�����ӵ���Ϣ����
		postDelayed(this.auto, this.autoTime);
	}

	@Override
	// �������
	public void computeScroll() {
		super.computeScroll();
		Runnable tmp = this.plusRunable;
		if (tmp == null) {
			return;
		}
		if (getSelectedView() != null) {
			// ������ȵ�����λ�ò����ڱ�ѡͼƬ�ĺ����ĵ�
			if (getCenterOfGallery() != getCenterOfView(getSelectedView())) {
				return;
			}
			this.plusRunable = null;
			post(tmp);
		} else {
			this.plusRunable = null;
		}
	}

	@Override
	protected boolean getChildStaticTransformation(View paramView,
			Transformation paramTransformation) {
		return false;
	}

	@Override
	// ��д�ֿ�������ʾ
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		startAutoScroll(false);// ���ò��Զ�����--false��
	}

	@Override
	// ��д��ָ�����¼�
	public boolean onFling(MotionEvent e1, MotionEvent e2, float f1, float f2) {
		if (e2.getX() <= e1.getX()) {
			scrollRight();
		} else {
			scrollLeft();
		}
		return true;
	}

	@Override
	// ��������������ʱ�����Զ�����
	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		startAutoScroll(true);
		return super.onTouchEvent(paramMotionEvent);
	}

	@Override
	// �˷����Զ��ص�
	protected void onWindowVisibilityChanged(int paramInt) {
		super.onWindowVisibilityChanged(paramInt);
		// �����ڴ������ػ���goneʱ===isShown�����жϸ���������ڵĿɼ����������Ƿ����
		if ((paramInt != 0) || (!isShown())) {
			// ���ò��Զ�����
			startAutoScroll(false);
		} else {
			startAutoScroll(true);
		}
	}

	public Runnable getPlusRunable() {
		return plusRunable;
	}

	public void setPlusRunable(Runnable plusRunable) {
		if (plusRunable != null) {
			this.removeCallbacks(plusRunable);
		}
		this.plusRunable = plusRunable;
	}
}
