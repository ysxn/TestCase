package sutras.cheng.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import sutras.cheng.listener.OnRefreshListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PullRefreshListView extends ListView implements OnScrollListener {
	private final static int RELEASE_To_REFRESH = 0; // �ͷ�
	private final static int PULL_To_REFRESH = 1;// ����ˢ��
	private final static int REFRESHING = 2; // ����ˢ��
	private final static int DONE = 3; // ����
	private final static int LOADING = 4;
	// ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı���
	private final static int RATIO = 3;
	private LayoutInflater inflater;
	private LinearLayout headView;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;
	private int headContentHeight;
	private int startY;
	private int firstItemIndex;
	private int state;
	private boolean isBack;
	private OnRefreshListener refreshListener;
	private boolean isRefreshable;
	private SimpleDateFormat format;
	private String pattern = "HH:mm";
	private String hintRefresh = "�ɿ�����ˢ��";
	private String refreshing = "����ˢ��...";
	private int duration = 250;

	public PullRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public PullRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void initHeader(int layout, int imageView, int hintView,
			int progressBar, int textLastUpdate) {
		headView = (LinearLayout) inflater.inflate(layout, null);
		// ������ͷ
		arrowImageView = (ImageView) headView.findViewById(imageView);
		// ������
		this.progressBar = (ProgressBar) headView.findViewById(progressBar);
		// ������ʾˢ��
		tipsTextview = (TextView) headView.findViewById(hintView);
		// ����һ��ˢ��ʱ��
		lastUpdatedTextView = (TextView) headView.findViewById(textLastUpdate);
		// ����head�ĸ߿�
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		// ��ʼ״̬�� ���ص�head ����
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
		// list���ͷ�ļ�
		addHeaderView(headView, null, false);
		setOnScrollListener(this);
		// �����Լ��ָ�����
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(duration);
		animation.setFillAfter(true);
		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(duration);
		reverseAnimation.setFillAfter(true);
		state = DONE;
		isRefreshable = false;
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3) {
		firstItemIndex = firstVisiableItem;
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			// ��downʱ���¼��ǰY��λ��
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
						// ʲô������
					}
					// ������ˢ��״̬����done״̬
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
					}

					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}
				isRecored = false;
				isBack = false;
				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();
				if (!isRecored && firstItemIndex == 0) {
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING) {
					// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�����Ļ�Ļ����������Ƶ�ʱ���б��ͬʱ���й���
					// ��������ȥˢ����
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);
						// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
						// һ�����Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
						// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
						else {
							// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
						}
					}
					// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
					if (state == PULL_To_REFRESH) {
						setSelection(0);
						// ���������Խ���RELEASE_TO_REFRESH��״̬
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						}
						// ���Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
					}
					// done״̬��
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}
					// ����headView��size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);
					}
					// ����headView��paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}
				}
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	private void changeHeaderViewByState() {
		switch (state) {
		// �ɿ�ˢ��״̬
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);
			tipsTextview.setText(hintRefresh);
			break;
		// ����ˢ��
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// ����RELEASE_To_REFRESH״̬ת������
			// ��ͷ��ת����
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
				tipsTextview.setText(hintRefresh);
			} else {
				tipsTextview.setText(hintRefresh);
			}
			break;
		// ˢ���� ״̬
		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(refreshing);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		// ˢ�����
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			tipsTextview.setText(hintRefresh);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText(updateTime());
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// �˷���ֱ���հ��������ϵ�һ������ˢ�µ�demo���˴��ǡ����ơ�headView��width�Լ�height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		lastUpdatedTextView.setText(updateTime());
		super.setAdapter(adapter);
	}

	private String updateTime() {
		format = new SimpleDateFormat(pattern);
		String date = format.format(new Date());
		return "������£�" + date;
	}
}
