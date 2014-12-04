package sutras.cheng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * �˿ؼ��µ�ÿһ���Ӳ��ֶ�����ȫ�����Ļ
 * 
 * @author chengkai
 */
public class HorizontalScroller extends ViewGroup {
	// ѹ��������
	private Scroller mScroller;
	// ����׷����
	private VelocityTracker mVelocityTracker;
	// ��ǰ��Ļ
	private int mCurScreen;
	// Ĭ����Ļ
	private int mDefaultScreen = 0;
	// ��Ļ����
	private int maxScreen = 0;
	// ������ֹ״̬
	private static final int TOUCH_STATE_REST = 0;
	// ���ڹ���״̬
	private static final int TOUCH_STATE_SCROLLING = 1;
	// ˲ʱ����
	private static final int SNAP_VELOCITY = 600;
	// ��ʼ������״̬Ϊ��ֹ״̬
	private int mTouchState = TOUCH_STATE_REST;
	// �����¼���Ӧ����С����ֵ
	private int mTouchSlop;
	// �����������X����
	private float mLastMotionX;
	// �����������Y����
	private float mLastMotionY;
	private boolean isAllowOutside = true;
	private float outsideRate = 0.5f;
	private boolean hasMaxScreen = false;

	public HorizontalScroller(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// ��ʼ��ѹ��������
		mScroller = new Scroller(context);
		// ��ʼ����ǰ��ĻΪ0
		mCurScreen = mDefaultScreen;
		// ��ȡ�����Ĵ�������--����������--slop���
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	// ���ؼ�ʹ��xml�ļ�����ʱ���ô˹�����
	public HorizontalScroller(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// ��ʼ��ѹ��������
		mScroller = new Scroller(context);
		// ��ʼ����ǰ��ĻΪ0
		mCurScreen = mDefaultScreen;
		// ��ȡ�����Ĵ�������--����������--slop���
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	/**
	 *  ��onMeasure(�÷������޷���ȡ��ͼ�Ŀ��)����֮����ã�Ϊ����ͼ����λ��ʱ�ص��˷����÷���Ϊÿһ������ͼ����ȫ��
	 *  ---ֻ����һ�Ρ���ʱ�Ѿ�Ϊ���е���ͼ����ÿ��---��computerScroll֮ǰ����
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (!hasMaxScreen) {
			this.maxScreen = this.getChildCount();
		}
		// ���������ͼ�ı�--��ô��������λ��
		if (changed) {
			int childLeft = 0;
			final int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					// Ϊÿһ���ӿؼ�����λ��������ˮƽ����������ֻ���������
					childView.layout(childLeft, 0, childLeft + childWidth,
							childView.getMeasuredHeight());
					childLeft += childWidth;
				}
			}
		}
	}

	// �����������»�ͼʱ�ص��˷���
	@Override
	public void computeScroll() {
		// �������true��ʾ��������δ������λ�ý�Ӧ�����µ�λ�ñ��
		if (mScroller.computeScrollOffset()) {
			// �ص������ı��¼�
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// ��invalidate������ʱ֪ͨǰһ��֪ͨ��Ч�ı�
			postInvalidate();
		}
	}

	@Override
	/** �˷�������ߴ�---��Ҫ����measure����������ߴ�--ˮƽ�ռ�--��ֱ�ռ�
	 *  ����ͼ��߸ı�ʱ����ô˷���
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// ͨ��ָ����ģʽ��óߴ�
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		// ��ü���ߴ��ģʽ---����ģʽ--1��������С--2���������--3��ȷ
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// ����Ǿ�ȷ�ߴ�---�����ļ�����ȷָ���ĳߴ�--���׳��쳣
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"HorizontalScroll only canmCurScreen run at EXACTLY mode!");
		}
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		// ͬ����㷨һ��
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"HorizontalScroll only can run at EXACTLY mode!");
		}
		// ������ͬ�Ŀ�� ---��ȡ����ͼ����
		final int count = getChildCount();
		// Ϊÿһ����ͼ������
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		// ����ָ��λ��
		scrollTo(mCurScreen * width, 0);
	}

	// ������Ŀ��ҳ---���㵱ǰ���ֵ�����
	public void snapToDestination() {

		// ��Ļ���
		final int screenWidth = getWidth();
		/*
		 * ����Ŀ����Ļ//getScrollX()�������ص��ǵ�ǰ��ͼ���Ե��X����--�㷨��ʾ�Ƿ񳬹����� ���ؿ���0,1
		 */
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		// ָ����Ŀ����Ļ
		snapToScreen(destScreen);
	}

	// ָ����Ŀ����Ļ---����ͼ�±��0��ʼ��snap--����
	public void snapToScreen(int whichScreen) {
		int tmp = Math.min(whichScreen, getChildCount() - 1);
		whichScreen = Math.max(0, tmp);
		if (getScrollX() != (whichScreen * getWidth())) {
			final int scrollX = getScrollX();// ��ȡ��ǰ��ͼ���Եλ��
			// ����X�᷽���Ϲ�������ʵ��XĿ������
			final int delta = whichScreen * getWidth() - scrollX;
			mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			invalidate();// ֪ͨ�ػ�
		}
	}

	// ������ʾ�ĸ�����ͼ---�����ⲿ����
	public void setToScreen(int whichScreen) {
		// ���㷨����Ҫ��ʾ����ͼ--->�������û�е���ͼ
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		// ������ָ����������ص�һ���������Լ�����һ���㵽ָ����Ĺ�������
		scrollTo(whichScreen * getWidth(), 0);
	}

	// ���ص�ǰ��ʾ������ͼ�±�
	public int getCurScreen() {
		return mCurScreen;
	}

	// ��д�����¼�
	public boolean onTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null) {
			// ���׷����ʵ��--->obtain���
			mVelocityTracker = VelocityTracker.obtain();
		}
		// ���һ���û�������׷����---����׷���û�����
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();
		final float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// �����¼�ǿ��ֹͣ��������
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;
		// �ƶ��¼�
		case MotionEvent.ACTION_MOVE:
			// delta��һ����λ����������ʾX�ľ���
			int deltaX = (int) (mLastMotionX - x);
			mLastMotionX = x;
			if (isAllowOutside) {
				if (mCurScreen == 0 && deltaX <= 0) {
					scrollBy((int) (deltaX * outsideRate), 0);
				} else if (mCurScreen == (maxScreen - 1) && deltaX >= 0) {
					scrollBy((int) (deltaX * outsideRate), 0);
				} else {
					// ˮƽ�������������ô˷���ʱ��ص������������ı��ƶ�
					scrollBy(deltaX, 0);
				}
			} else {
				if (mCurScreen == 0 && deltaX <= 0) {
					break;
				} else if (mCurScreen == (maxScreen - 1) && deltaX >= 0) {
					break;
				} else {
					// ˮƽ�������������ô˷���ʱ��ص������������ı��ƶ�
					scrollBy(deltaX, 0);
				}
			}
			break;
		// �����¼�
		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker = mVelocityTracker;
			// ���������ٶȵļ���ֵ����Ϊ���ֵ
			velocityTracker.computeCurrentVelocity(1000);
			// ��ȡ֮ǰ������
			int velocityX = (int) velocityTracker.getXVelocity();
			// �ٶ���ʸ���з���---���ٶ�����������ֱ�ӽ�����һ��ͼ--������Ҫ����ֵ������
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				// ����ǣ�����Ǹ���ͼ����ٶȳ���600����û�е�������ͼ��ص�ǰһ����ͼ
				snapToScreen(mCurScreen - 1);
			} else if (velocityX < -SNAP_VELOCITY && mCurScreen < maxScreen - 1) {
				snapToScreen(mCurScreen + 1);
			} else {
				// ����ֵ��������ʾ����ͼ
				snapToDestination();
			}

			if (mVelocityTracker != null) {
				// ����׷����
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// ������ֹ״̬
			mTouchState = TOUCH_STATE_REST;
			break;
		// ȡ��ʱΪ��ֹ״̬
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return true;
	}

	// ���ش����¼�����д�÷��������Կ����¼������Ĺ��̣���������Ч�����������true����������ͼ��һ�ж����¼�
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		// �������¼�//��¼��ǰλ��--ֻ�����������
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;
		// �����ƶ��¼�
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				// �ı�Ϊ����״̬״̬
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		// �����ȡ���������ɿ��¼�
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// ����ֹͣ״̬
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	public boolean isAllowOutside() {
		return isAllowOutside;
	}

	public void setAllowOutside(boolean isAllowOutside) {
		this.isAllowOutside = isAllowOutside;
	}

	public float getOutsideRate() {
		return outsideRate;
	}

	public void setOutsideRate(float outsideRate) {
		if (outsideRate >= 1.0f) {
			this.outsideRate = 1.0f;
		} else if (outsideRate < 0.0f) {
			this.outsideRate = 0.0f;
		} else {
			this.outsideRate = outsideRate;
		}
	}

	public int getMaxScreen() {
		return maxScreen;
	}

	public void setMaxScreen(int maxScreen) {
		if (maxScreen > this.getChildCount()) {
			this.maxScreen = this.getChildCount();
		} else if (maxScreen < 1) {
			this.maxScreen = 1;
		} else {
			this.maxScreen = maxScreen;
		}
		hasMaxScreen = true;
	}

}
