package sutras.cheng.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import sutras.cheng.listener.OnRefreshListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ������ˢ�µ�ListView---���ݲ���ȫ��ʱ����ˢ��
 * 
 * @author chengkai
 * 
 */
public class PullClickRefreshListView extends ListView implements
		OnScrollListener {
	// ���״̬
	private static final int TAP_TO_REFRESH = 1;
	// ����״̬
	private static final int PULL_TO_REFRESH = 2;
	// �ɿ�״̬
	private static final int RELEASE_TO_REFRESH = 3;
	// ����ˢ��״̬
	private static final int REFRESHING = 4;
	// ͼƬ����С�߶�
	private static final int minHeight = 50;
	// ���ֽ�����
	private LayoutInflater mInflater;
	// ����ͼ
	private int selected = 1;
	// ���ڵ�������
	private Context context;
	// ��¼��ǰ������״̬
	private int mCurrentScrollState;
	// ��¼ˢ��״̬
	private int mRefreshState;
	// ��ת����
	private RotateAnimation mFlipAnimation;
	// �෴�ķ�ת����
	private RotateAnimation mReverseFlipAnimation;
	// ˢ����ͼ���ٽ��߶�
	private int mRefreshViewHeight;
	// ˢ��ԭʼ�����߾�
	private int mRefreshOriginalTopPadding;
	// ���������y����
	private int mLastMotionY;

	// �����ƶ������ر���
	private float paddingRatio = 3.0f;
	// ��ʾˢ�±��
	private String hintRefreshLabel = "�ɿ�����ˢ��";
	// ˢ�±��
	private String refreshingLabel = "����ˢ��...";
	// ��ֱ��������
	private LinearLayout mRefreshView;
	// ˢ����ʾ���ı��ؼ�
	private TextView mRefreshViewText;
	// ����ִ�ж���Ч����ͼƬ�ؼ�
	private ImageView mRefreshViewImage;
	// ��ʾˢ�½�����(����)
	private ProgressBar mRefreshViewProgress;
	// ��ʾ���θ������ϴθ��µ�ʱ���(������)
	private TextView mRefreshViewLastUpdated;
	// ����ͼƬ
	private Drawable drawable;
	// ʱ����ʽ
	private String pattern = "HH:mm";
	// ��ʽ������
	private SimpleDateFormat format;
	// ˢ�¼�����
	private OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
		public void onRefresh() {
		}
	};
	// ����������
	private OnScrollListener mOnScrollListener = new OnScrollListener() {
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}
	};

	public PullClickRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public PullClickRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullClickRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		// ��ʼ��ˢ��Ч������Ҫ�Ķ���
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		// �������Բ�ֵ��
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		// ���ö���ʱ��
		mFlipAnimation.setDuration(250);
		// ���ñ����������Ч��
		mFlipAnimation.setFillAfter(true);
		// ͬ��7
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);
		// ��ʼ�����ֽ�����
		mInflater = (LayoutInflater) LayoutInflater.from(context);
		format = new SimpleDateFormat(pattern);
	}

	/**
	 * ͨ��Id��Դ��������---�÷���һ��Ҫ������������֮ǰ����
	 * 
	 * @param layout�����ļ�
	 * @param imageViewͼƬ�ؼ�
	 * @param hintView��ʾ���ı��ؼ�
	 * @param progressBar���ν�����
	 * @param textLastUpdate����ʱ��
	 */
	public void initHeader(int layout, int imageView, int hintView,
			int progressBar, int textLastUpdate) {
		// ����������ͼ
		mRefreshView = (LinearLayout) mInflater.inflate(layout, null);
		mRefreshViewImage = (ImageView) mRefreshView.findViewById(imageView);
		mRefreshViewText = (TextView) mRefreshView.findViewById(hintView);
		mRefreshViewProgress = (ProgressBar) mRefreshView
				.findViewById(progressBar);
		mRefreshViewLastUpdated = (TextView) mRefreshView
				.findViewById(textLastUpdate);
		mRefreshViewText.setText(hintRefreshLabel);
		mRefreshViewLastUpdated.setText(updateTime());

		drawable = mRefreshViewImage.getDrawable();
		mRefreshViewImage.setMinimumHeight(minHeight);
		mRefreshView.setOnClickListener(new OnClickRefreshListener());
		mRefreshOriginalTopPadding = mRefreshView.getPaddingTop();
		mRefreshState = TAP_TO_REFRESH;
		// Ϊˢ����ͼ����ߴ�
		measureView(mRefreshView);
		// ��ˢ����ͼ��������߶�����Ϊˢ����ͼ���ٽ��
		mRefreshViewHeight = mRefreshView.getMeasuredHeight();
		// ��ˢ����ͼ���ΪListView��ͷ��
		addHeaderView(mRefreshView);
		// ע�����������
		super.setOnScrollListener(this);
	}

	public void setPaddingRatio(float paddingRatio) {
		this.paddingRatio = paddingRatio;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public void setDrawable(int resDraw) {
		this.drawable = context.getResources().getDrawable(resDraw);
	}

	// @Override
	// // �ú�����onDraw����֮ǰ����
	// protected void onAttachedToWindow() {
	// // ʹ�ù���������setSelection��ָ����ͼλ�õ�ԭ���Ƿ�ֹû������Ϊ�ն���ʾˢ����ͼ
	// //���õ�һ�ѡ
	// setSelection(selected);
	// }

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setSelection(selected);
	}

	@Override
	public void setOnScrollListener(AbsListView.OnScrollListener listener) {
		mOnScrollListener = listener;
	}

	/**
	 * ���ø÷�����ʾ����������ʾ��
	 * 
	 * @param hintLabel
	 *            Ĭ�� �ɿ�����ˢ��
	 * @param refreshingLabel
	 *            Ĭ�� ����ˢ��...
	 */
	public void initLabel(String hintLabel, String refreshingLabel) {
		this.hintRefreshLabel = hintLabel;
		this.refreshingLabel = refreshingLabel;
	}

	/**
	 * ���ø÷�����ʾ����������ʾ��
	 * 
	 * @param hintLabel
	 *            Ĭ�� �ɿ�����ˢ��
	 * @param refreshingLabel
	 *            Ĭ�� ����ˢ��...
	 */
	public void initLabel(int hintLabel, int refreshingLabel) {
		this.hintRefreshLabel = context.getResources().getString(hintLabel);
		this.refreshingLabel = context.getResources()
				.getString(refreshingLabel);
	}

	/**
	 * ע��ص������ӿ�
	 * 
	 * @param callback
	 *            onRefresh
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	/**
	 * ������������ʾ���ַ�
	 * 
	 * @param lastUpdated
	 *            time at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			mRefreshViewLastUpdated.setVisibility(View.VISIBLE);
			mRefreshViewLastUpdated.setText(lastUpdated);
		} else {
			mRefreshViewLastUpdated.setVisibility(View.GONE);
		}
	}

	/**
	 * ��д�����¼�
	 */
	public boolean onTouchEvent(MotionEvent event) {
		final int y = (int) event.getY();
		switch (event.getAction()) {
		// �����¼�
		case MotionEvent.ACTION_UP:
			// �����ֱ������δ����
			if (!isVerticalScrollBarEnabled()) {
				// ���ù���������---ȷ��ʼ�ճ��ֹ�����
				setVerticalScrollBarEnabled(true);
			}
			// ����һ���ɼ���Ϊ��һ�����Ҳ�����ˢ��״̬
			if (getFirstVisiblePosition() == 0 && mRefreshState != REFRESHING) {
				// ��ˢ����ͼ�ĵױ�Y�����������ˢ�µĸ߶Ȼ�ˢ����ͼ���ϱ�Y������ڵ���0ʱ���Ҵ����ͷ�״̬ʱ
				// System.out.println("up--" + mRefreshView.getBottom() + "--"
				// + mRefreshView.getTop());
				if ((mRefreshView.getBottom() > mRefreshViewHeight || mRefreshView
						.getTop() >= 0) && mRefreshState == RELEASE_TO_REFRESH) {
					// ����Ϊˢ��״̬
					mRefreshState = REFRESHING;
					Refreshing();
					onRefresh();
				} else if (mRefreshView.getBottom() < mRefreshViewHeight
						|| mRefreshView.getTop() < 0) {
					// ֹͣˢ�¡���������ʾ������ͼ
					resetHeader();
					setSelection(selected);
				}
			}
			break;
		// �����¼�
		case MotionEvent.ACTION_DOWN:
			// ��¼Y�����
			mLastMotionY = y;
			break;
		// �ƶ��¼�
		case MotionEvent.ACTION_MOVE:
			applyHeaderPadding(event);
			break;
		}
		return super.onTouchEvent(event);
	}

	// ����ͷ���
	private void applyHeaderPadding(MotionEvent event) {
		// ��ȡ�¼���ʷ�з����ĵ���
		final int historySize = event.getHistorySize();
		// System.out.println("historySize---"+historySize);
		int pointerCount = 1;
		try {
			// ��ȡ�¼�ָ������
			pointerCount = event.getPointerCount();
			// System.out.println("pointerCount-----------"+pointerCount);
		} catch (Exception e) {
			System.out.println("��ʹ�õ�android sdk�汾����1.5");
			e.printStackTrace();
		}
		for (int h = 0; h < historySize; h++) {
			for (int p = 0; p < pointerCount; p++) {
				if (mRefreshState == RELEASE_TO_REFRESH) {
					if (isVerticalFadingEdgeEnabled()) {
						setVerticalScrollBarEnabled(true);
					}
					int historicalY = 0;
					try {
						// android sdk2.0���֧�ֵķ���
						historicalY = (int) event.getHistoricalY(p, h);
					} catch (Exception e) {
						e.printStackTrace();
						try {
							historicalY = (int) event.getHistoricalY(h);
							System.out.println("historicalY---" + historicalY);
						} catch (Exception e1) {
							e1.printStackTrace();
							System.out.println("����android sdk�汾����2.0");
						}
					}
					// ���㶥����Ҫ���ľ���,�����ڼ����ǳ���ĳ����ֵ��ģ��һ������ĥ��(resistant)Ч��
					int topPadding = (int) (((historicalY - mLastMotionY) - mRefreshViewHeight) / paddingRatio);
					// ���ü���õĶ������
					mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
							topPadding, mRefreshView.getPaddingRight(),
							mRefreshView.getPaddingBottom());
				}
			}
		}
	}

	/**
	 * ����ͷ�����ص�ԭʼ�ߴ�
	 */
	private void resetHeaderPadding() {
		mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
				mRefreshOriginalTopPadding, mRefreshView.getPaddingRight(),
				mRefreshView.getPaddingBottom());
	}

	/**
	 * ����ͷ�����ָ���ԭʼ״̬
	 */
	private void resetHeader() {
		if (mRefreshState != TAP_TO_REFRESH) {
			mRefreshState = TAP_TO_REFRESH;
			resetHeaderPadding();
			// ����ˢ�±�ǩ
			mRefreshViewText.setText(this.hintRefreshLabel);
			// �滻ˢ��ͼƬ
			mRefreshViewImage.setImageDrawable(drawable);
			// �������ԭʼ����
			mRefreshViewImage.clearAnimation();
			// ����ˢ����ͼ�е�ͼƬ
			mRefreshViewImage.setVisibility(View.VISIBLE);
			// ���ؽ�����
			mRefreshViewProgress.setVisibility(View.GONE);
		}
	}

	// ������ͼ��Ϊ��ͼ����ߴ�
	private void measureView(View child) {
		// �õ�����ͼ�Ĳ��ֲ���
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			// �������0������Ϊ��ȷ����ģʽ
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			// �������С��0�����ɼ�״̬������Ϊδָ��ģʽ
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		// Ϊ����ͼ������
		child.measure(childWidthSpec, childHeightSpec);
	}

	// ʵ�ֹ�����������ִ�й���ʱ�ص��÷���
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// ��ˢ����ͼ�ɼ�ʱ�ı���ʾ��Ϊ����ʱhintRefreshLabel������ʱΪrefreshingLabelͬʱ�滻��ͷͼƬ
		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& mRefreshState != REFRESHING) {
			if (firstVisibleItem == 0) {
				mRefreshViewImage.setVisibility(View.VISIBLE);
				if ((mRefreshView.getBottom() >= mRefreshViewHeight || mRefreshView
						.getTop() >= 0) && mRefreshState != RELEASE_TO_REFRESH) {
					mRefreshViewText.setText(hintRefreshLabel);
					mRefreshViewImage.clearAnimation();
					mRefreshViewImage.startAnimation(mFlipAnimation);
					mRefreshState = RELEASE_TO_REFRESH;
				} else if (mRefreshView.getBottom() < mRefreshViewHeight
						&& mRefreshState != PULL_TO_REFRESH) {
					if (mRefreshState != TAP_TO_REFRESH) {
						mRefreshViewImage.clearAnimation();
						mRefreshViewImage.startAnimation(mReverseFlipAnimation);
					}
					mRefreshState = PULL_TO_REFRESH;
				}
			} else {
				setSelection(selected);
				resetHeader();
			}
		} else if (mCurrentScrollState == SCROLL_STATE_FLING
				&& firstVisibleItem == 0 && mRefreshState != REFRESHING) {
			setSelection(selected);
		}

		mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
				totalItemCount);
	}

	// ʵ�ֹ�������������״̬�ı�ʱִ��
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
		mOnScrollListener.onScrollStateChanged(view, scrollState);
	}

	/**
	 * ����ˢ��״̬ˢ��
	 */
	public void Refreshing() {
		mRefreshView.setPadding(mRefreshView.getPaddingLeft(),
				mRefreshOriginalTopPadding, mRefreshView.getPaddingRight(),
				mRefreshView.getPaddingBottom());
		mRefreshViewImage.setVisibility(View.GONE);
		// ��ͼƬ����Ϊnull,���򽫱���֮ǰ�Ķ���
		mRefreshViewImage.setImageDrawable(null);
		// ���ý������ɼ�
		mRefreshViewProgress.setVisibility(View.VISIBLE);
		// ��������ˢ��
		mRefreshViewText.setText(refreshingLabel);
		mRefreshState = REFRESHING;
	}

	/**
	 * ִ��ˢ��
	 */
	public void onRefresh() {
		mOnRefreshListener.onRefresh();
	}

	/**
	 * ˢ�����--ʹListView�ص�����״̬
	 * 
	 * @param lastUpdated
	 */
	public void onRefreshComplete(CharSequence lastUpdated) {
		// ʹ������ͼ�ؽ�
		invalidateViews();
		resetHeader();
		setLastUpdated(lastUpdated);
		setSelection(selected);
	}

	/**
	 * ˢ����ɺ�����ͷ��
	 */
	public void onRefreshComplete() {
		// ʹ������ͼ�ؽ�
		invalidateViews();
		resetHeader();
		setLastUpdated(updateTime());
		setSelection(selected);
	}

	private String updateTime() {
		return "������£�" + format.format(new Date());
	}

	/**
	 * ִ�е���ˢ�µļ������������¼��ص�
	 * 
	 * @author chengkai
	 */
	private class OnClickRefreshListener implements OnClickListener {
		public void onClick(View v) {
			if (mRefreshState != REFRESHING) {
				Refreshing();
				onRefresh();
			}
		}
	}

}
