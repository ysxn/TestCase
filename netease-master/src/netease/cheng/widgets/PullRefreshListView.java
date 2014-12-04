package netease.cheng.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Scroller;
/**
 * �ο���δʹ��
 * @author Administrator
 *
 */
public class PullRefreshListView extends FrameLayout {
	//����״̬
	public static final int REFRESH_STATE_IDLE = 0;
	//����׼��ˢ��״̬
	public static final int REFRESH_STATE_PREPARE_REFRESHING = 2;
	//����ˢ��״̬
	public static final int REFRESH_STATE_REFRESHING = 3;
	//��ʾ״̬
	public static final int REFRESH_STATE_SHOWING = 1;
	//��ʼˢ���߳�
	private Runnable mBeginRefreshRunnable;
	//�����߳�
	private Runnable mFinishRefreshRunnable;
	//״̬�ı���߳���
	private Runnable mStateChangedRunnable;
	//��ֹ����
	private boolean mForbidPull = false;
	//����������
	private boolean mInterpreterTouch;
	//��������Y����
	private int mLastMotionY;
	//��������Y����
	private int mLastScrollY;
	//ˢ�»ص��ӿ�
	private PullRefreshCallback mListView;
	//ˢ�¼���
	private OnRefreshListener mOnRefreshListener;
	//ˢ��״̬
	private int mRefreshState = 0;
	//ˢ�µ���ͼ
	private View mRefreshView;
	//֡����������Ϊ������
	private RefreshViewContainer mRefreshViewContainer;
	//ѹ�������Ĺ�����
	private Scroller mScroller;
	//Ŀ���ַ�
	private String mTag;

	public PullRefreshListView(Context paramContext) {
		super(paramContext);
		init();
	}

	public PullRefreshListView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	private boolean callListViewTouchEvent(MotionEvent paramMotionEvent) {
		View localView = (View) this.mListView;
		paramMotionEvent.setLocation(
				paramMotionEvent.getX() - localView.getLeft(),
				paramMotionEvent.getY() - localView.getTop() + getScrollY());
		return this.mListView.onTouch(paramMotionEvent);
	}

	private void callRefreshTask() {
		if (this.mOnRefreshListener == null)
			refreshDone();
		else
			this.mOnRefreshListener.doRefresh(this.mTag);
	}
	//���ListView�Ĵ����¼�
	private void clearListViewTouchEvent(MotionEvent event) {
		View localView = (View) this.mListView;
		float f1 = event.getX() - localView.getLeft();
		float f2 = event.getY() - localView.getTop() + getScrollY();
		MotionEvent localMotionEvent = MotionEvent.obtain(event);
		//����ȡ������
		localMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
		
		localMotionEvent.setLocation(f1, f2);
		//��������¼����ǵ��ø���Ĵ����¼�---�¼�����Ϊȡ�������ڵ��ø��෽��
		this.mListView.clearTouch(localMotionEvent);
	}

	private int getCurrentTop() {
		return getMinTop() + getMoveDistance();
	}

	private int getMaxTop() {
		return 2 * getRefreshViewHeight();
	}

	private int getMinTop() {
		return -getRefreshViewHeight();
	}

	private int getRefreshHeight() {
		return (int) (3 * getRefreshViewHeight() / 2.5F);
	}

	private int getRefreshState() {
		int j=0;
		if (!isRefreshing()) {
			int i = getMoveDistance();
			j = 0;
			if (i <= getRefreshHeight())
				if (i > 0)
					j = 1;
				else
					j = 2;
		} else {
			j = 3;
		}
		return j;
	}
	//��ò��ּ���ĸ߶�
	private int getRefreshViewHeight() {
		return this.mRefreshViewContainer.getMeasuredHeight();
	}
	//����ƶ����ܾ���
	private int getTotalMoveDistance() {
		return getMaxTop() - getMinTop();
	}

	private boolean hasInterpreterTouchEvent() {
		if (getMoveDistance() == 0){
			return false;
		}
		return true;
	}
	//����xml�ļ��򴴽�ʵ���ǳ�ʼ��ѹ��������
	private void init() {
		this.mScroller = new Scroller(getContext());
	}
	//�Ƿ��ڹ���״̬
	private boolean isRefreshing() {
		if (this.mBeginRefreshRunnable == null)
			return false;
		return true;
	}

	private void move(int paramInt) {
		if (paramInt == 0)
			return;
		int i = getRefreshState();
		int j = getRefreshState();
		if ((paramInt > 0) && (!isRefreshing())) {
			j = getTotalMoveDistance();
			paramInt = (int) (Math.pow(1.0F - getMoveDistance() / j, 1.5D) * paramInt);
		}
		offsetView(paramInt);
		if (i == j)
			return;
		updateRereshState(j, true);
	}

	private void offsetView(int paramInt) {
		if (paramInt == 0)
			return;
		int i = getCurrentTop();
		i = Math.min(getMaxTop(), Math.max(getMinTop(), i + paramInt)) - i;
		if (i == 0)
			return;
		scrollBy(0, -i);
		if (!isRefreshing())
			return;
		i = getMoveDistance();
		if ((i != getRefreshViewHeight())
				|| (this.mBeginRefreshRunnable == null)) {
			if (i != 0)
				return;
			resetInternal();
		} else {
			this.mScroller.forceFinished(true);
			post(this.mBeginRefreshRunnable);
		}
	}
	//�������¼�
	private void onDown(MotionEvent event) {
		//�������������ƶ��ľ���==0
		if ((this.mScroller.isFinished()) && (!hasInterpreterTouchEvent()))
			//�����������---���ʹ�������Ϊfalse---û�з��������¼���---�ͽ���
			this.mInterpreterTouch = false;
		else
			this.mInterpreterTouch = true;
		if (!isRefreshing()) {
			this.mScroller.forceFinished(true);
			updateRereshState(getRefreshState(), true);
		}
		//���յ�Y����---�¼�������Y�����
		this.mLastMotionY = (int) event.getY();
	}

	private void onMove(MotionEvent paramMotionEvent) {
		int j = (int) paramMotionEvent.getY();
		int i = j - this.mLastMotionY;
		boolean bool = this.mInterpreterTouch;
		if (!bool) {
			bool = willInterpreterTouchEvent(i);
			if (bool) {
				clearListViewTouchEvent(paramMotionEvent);
				this.mInterpreterTouch = true;
			}
		}
		if ((bool) && (!isRefreshing()))
			move(i);
		this.mLastMotionY = j;
	}

	private void onUp(MotionEvent paramMotionEvent) {
		if ((!this.mInterpreterTouch) || (isRefreshing()))
			return;
		int i = this.mRefreshState;
		if (i != 2) {
			if (i != 1)
				return;
			updateRereshState(0, true);
		} else {
			updateRereshState(3, true);
		}
	}
	//�����ڲ�����---���еĻص�δ��ʼ��״̬
	private void resetInternal() {
		this.mBeginRefreshRunnable = null;
		this.mFinishRefreshRunnable = null;
		this.mStateChangedRunnable = null;
		//ǿ����ɹ���
		this.mScroller.forceFinished(true);
		//�ƶ���0,0λ��
		scrollTo(0, 0);
		if (!this.mListView.hasRefreshView())
			return;
		//����ˢ�µ��б���
		this.mListView.clearRefreshViewForList();
		//�������������ˢ�µ�view�ؼ�
		this.mRefreshViewContainer.addView(this.mRefreshView);
	}
	//ƽ������
	private void smoothScroll(int paramInt1, int paramInt2) {
		if (!this.mScroller.isFinished())
			//ǿ�����
			this.mScroller.forceFinished(true);
		if (paramInt2 == 0)return;
		int i;
		if (paramInt2 <= 0)
			i = Math.max(paramInt2, -getMoveDistance());
		else
			i = Math.min(paramInt2, getTotalMoveDistance() - getMoveDistance());
		this.mLastScrollY = paramInt1;
		this.mScroller.startScroll(0, paramInt1, 0, i, 400);
		invalidate();
	}
	//����ˢ��״̬
	private void updateRereshState(int paramInt, boolean paramBoolean) {
		if (this.mRefreshState == paramInt){
			return;
		}
		if ((paramInt == 3)&& (((this.mOnRefreshListener == null) 
			|| (!this.mOnRefreshListener.onPrepareRefresh(this.mTag))))){
			paramInt = 0;
		}
		if (this.mRefreshState == paramInt){
			return;
		}
		int j = this.mRefreshState;
		this.mRefreshState = paramInt;
		switch (paramInt) {
		case 0:
			if (this.mStateChangedRunnable == null) {
				if (isRefreshing()) {
					if ((!paramBoolean) || (this.mForbidPull)
							|| (!this.mListView.hasRefreshView())
							|| (getListView().getFirstVisiblePosition() != 0)
							|| (getListView().getChildCount() <= 0))
						resetInternal();
					else
						scrollTo(0, -getListView().getChildAt(0).getBottom());
					if (this.mListView.hasRefreshView()) {
						this.mListView.clearRefreshViewForList();
						this.mRefreshViewContainer.addView(this.mRefreshView);
					}
				}
				int i = -getMoveDistance();
				if (i != 0);
//					post(new Runnable(getCurrentTop(), i) {
//						public void run() {
//							smoothScroll(this.val$start, this.val$distance);
//						}
//					});
			} else {
				this.mStateChangedRunnable = null;
			}
			break;
		case 3:
			if ((!this.mForbidPull) && (paramBoolean)) {
				this.mStateChangedRunnable = new Runnable() {
					public void run() {
						mStateChangedRunnable = null;
//						smoothScroll(
//								PullRefreshListView
//										.access$2(PullRefreshListView.this),
//								PullRefreshListView
//										.access$3(PullRefreshListView.this)
//										- PullRefreshListView.this
//												.getMoveDistance());
						mBeginRefreshRunnable = new Runnable() {
							public void run() {
								scrollTo(0, 0);
								mRefreshViewContainer
										.removeAllViews();
								mListView
										.setRefreshViewForList(mRefreshView);
								callRefreshTask();
							}
						};
					}
				};
				if (getHeight() != 0)
					this.mStateChangedRunnable.run();
			} else {
				callRefreshTask();
			}
		case 1:
		case 2:
		}
		if ((this.mOnRefreshListener == null) || (this.mRefreshState == j))
			return;
		this.mOnRefreshListener.updateRefreshView(this.mTag, this.mRefreshView,
				j, paramInt);
	}
	
	private boolean willInterpreterTouchEvent(int paramInt) {
		//FrameLayout��ʵ����
		ListView list = this.mListView.getListView();
		if ((!isRefreshing()) && (paramInt > 0)
				&& (list.getFirstVisiblePosition() == 0)
				&& (list.getChildAt(0) != null)
				&& (list.getChildAt(0).getTop() == 0))
			return true;
		return false;
	}
	//�������
	public void computeScroll() {
		super.computeScroll();
		//�������true��������û��ִ����ɣ������λ��
		if (!this.mScroller.computeScrollOffset())
			return;
		move(this.mScroller.getCurrY() - this.mLastScrollY);
		this.mLastScrollY = this.mScroller.getCurrY();
		invalidate();
	}
	//�����Ƿ���������
	public void forbidPull(boolean paramBoolean) {
		this.mForbidPull = paramBoolean;
		if (!paramBoolean)
			return;
		resetInternal();
	}
	//���ؽӿڵ�ʵ����
	public ListView getListView() {
		return this.mListView.getListView();
	}
	//��ȡY�����ƶ��ľ���
	public int getMoveDistance() {
		return -getScrollY();
	}
	//����һ�������¼�����---���������
	public MotionEvent getProperMotionEvent(MotionEvent event) {
		View view = (View) this.mListView;
		//���ö����¼���λ��
		event.setLocation(
				event.getX() + view.getLeft(),
				event.getY() + view.getTop() - getScrollY());
		return event;
	}
	//���ز��ְ�����
	public View getRefreshViewContainer() {
		return this.mRefreshViewContainer;
	}
	//�Ƿ���ܴ���
	public boolean isInterpreterTouch() {
		return this.mInterpreterTouch;
	}
	/**
	 * ��д����ķ���---��������ڲ����ļ��������ʱ����--�������ִ�н��������е�����ͼ������ӽ�������
	 * �����������󱻵���
	 */
	protected void onFinishInflate() {
		super.onFinishInflate();
		this.mListView = ((PullRefreshCallback) findViewById(2131493145));
		this.mRefreshViewContainer = new RefreshViewContainer(getContext());
		this.mListView.setPullRefreshListView(this);
		this.mRefreshViewContainer.setPadding(0, 0, 0, getListView()
				.getDividerHeight());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				-1, -2);
		params.gravity = Gravity.CENTER|Gravity.TOP;//48
		addView(this.mRefreshViewContainer, params);
	}
	//���ݶ����¼�
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){onDown(event);}
		return super.onInterceptTouchEvent(event);
	}

	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
			int paramInt3, int paramInt4) {
		((FrameLayout.LayoutParams) this.mRefreshViewContainer
				.getLayoutParams()).topMargin = (-getRefreshViewHeight());
		super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
		if (this.mStateChangedRunnable == null)
			return;
		post(this.mStateChangedRunnable);
	}

	public boolean onTouch(MotionEvent paramMotionEvent) {
		if (this.mForbidPull)
			this.mInterpreterTouch = false;
		else
			switch (paramMotionEvent.getAction()) {
			case 1:
			case 3:
				onUp(paramMotionEvent);
				break;
			case 2:
				onMove(paramMotionEvent);
			case 0:
			}
		return callListViewTouchEvent(paramMotionEvent);
	}

	public void refresh() {
		updateRereshState(3, true);
	}

	public void refreshDone() {
		updateRereshState(0, true);
	}

	public void refreshDoneNoAnimate() {
		updateRereshState(0, false);
		resetInternal();
	}

	public void refreshNoAnimate() {
		updateRereshState(3, false);
		resetInternal();
	}
	//����--����ˢ��״̬
	public void reset() {
		
		updateRereshState(0, false);
		resetInternal();
	}
	//����ˢ�¼���--���ü�����ʶ��
	public void setOnRefreshListener(String paramString,
			OnRefreshListener paramOnRefreshListener) {
		this.mTag = paramString;
		this.mOnRefreshListener = paramOnRefreshListener;
	}
	//����ˢ�µ�View��ʾ����
	public void setRefreshView(int resId) {
		this.mRefreshView = inflate(getContext(), resId, null);
		this.mRefreshViewContainer.addView(this.mRefreshView);
	}
	//ˢ�¼�����---����״̬
	public static abstract interface OnRefreshListener {
		public abstract void doRefresh(String paramString);

		public abstract boolean onPrepareRefresh(String paramString);

		public abstract void updateRefreshView(String paramString,
				View paramView, int paramInt1, int paramInt2);
	}
	//�Զ����ListViewʵ�ֵĽӿ�ʵ����ط���
	public static abstract interface PullRefreshCallback {
		public abstract void clearRefreshViewForList();

		public abstract boolean clearTouch(MotionEvent paramMotionEvent);

		public abstract ListView getListView();

		public abstract boolean hasRefreshView();

		public abstract boolean onTouch(MotionEvent paramMotionEvent);

		public abstract void setPullRefreshListView(
				PullRefreshListView paramPullRefreshListView);

		public abstract void setRefreshViewForList(View paramView);
	}
	//ˢ�µ�֡������
	private class RefreshViewContainer extends FrameLayout {
		public RefreshViewContainer(Context localContext) {
			super(localContext);
		}
		//���Ȼ�����ͼ
		protected void dispatchDraw(Canvas canvas) {
			super.dispatchDraw(canvas);
			if (getMoveDistance() <= 0)return;
			Drawable divider = getListView().getDivider();
			if (divider == null)
				return;
			Rect rect = divider.copyBounds();
			divider.setBounds(0,
					getHeight() - divider.getIntrinsicHeight(),
					getWidth(), getHeight());
			//�Ȼ��ָ����ڻ�����
			divider.draw(canvas);
			//�ָ������������ñ߽�---��������
			divider.setBounds(rect);
		}
	}
}