package sutras.cheng.widget;

import java.util.ArrayList;
import java.util.List;

import sutras.cheng.listener.OnDragListViewListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * �������ı��ListView
 * 
 * @author chengkai
 * 
 */
public class DragableListView extends ListView {
	// android ���ڹ�����
	private WindowManager mWindowManager;
	// ���ڲ��ֲ���
	private WindowManager.LayoutParams mWindowParams;
	// �ֻ���Ļ�Ŀ��
	private int screenWidth;
	// ��ʾ��ҷʱ�����Ŀؼ�---�ÿؼ��ϵ������ڴ�����ҷ״̬ʱ���ȡ��һ������������
	private ImageView mDragView;
	// ��ʾ��ҷ����ʱ���Σ���ҷʱ��ImageView�ؼ��ϵ����ݽ�ȡ���þ�����
	private Rect mTempRect = new Rect();
	// ��ʾ��ҷʱ��λͼ
	private Bitmap mDragBitmap;
	// ����ͼƬ
	private Drawable mTrashcan;
	// ��ҷʱ�ı���ͼƬ
	private Drawable drawable;
	// ��ҷ��Ŀ��λ��
	private int mDesignDragPos;
	// ��ҷ֮ǰ��λ��
	private int mOriginalDragPos;
	// ��ҷ��x��������
	private int mDragPointX;
	// ��ҷ��y��������
	private int mDragPointY;
	// x����ƫ����
	private int mXOffset;
	// y����ƫ����
	private int mYOffset;
	// ListView���ϱ�
	private int mUpperBound;
	// ListView���±�
	private int mLowerBound;
	// ListView������߶�
	private int mHeight;
	// ������ҷʱ�ı���ɫ
	private int backgroundColor = Color.argb(55, 0xcc, 0xcc, 0xcc);
	// �Ϸ�ʱ��͸����
	private float alphaRate = 0.5f;
	// android���Ƽ����
	private GestureDetector mGestureDetector;
	// ���ƴ�������ģʽ
	private static final int FLING = 0;
	// ���ɻ���ģʽ
	private static final int SLIDE = 1;
	// ����ģʽ
	private static final int TRASH = 2;
	// ��ǰ��״̬ģʽ
	private int mStateMode = -1;
	// ������Ϊ���㴥���������������ص�λ---���豸���þ���
	private final int mTouchSlop;
	// ����ҷ�Ŀռ�����سߴ��С
	private int dragablePixel;
	// item�����߶�
	private int mItemHeightNormal = 0;
	// item ����߶�
	private int mItemHeightExpanded = mItemHeightNormal * 2;
	// item��һ��ĸ߶�
	private int mItemHeightHalf = mItemHeightNormal / 2;
	// �Ƿ�Ϊ����ģʽ---Ĭ�ϲ�����
	private boolean isExpansion = false;
	// ���÷�������
	private List<String> group = new ArrayList<String>();
	// ��ҷ������
	private OnDragListViewListener mDragListener = new OnDragListViewListener() {
		public void remove(int which) {
		}

		public void drop(int from, int to) {
		}

		public void drag(int from, int to) {
		}
	};

	public DragableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mWindowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		// ʹ����Ļ��ȵ�1/5��Ϊ��ִ���϶��Ŀ��
		dragablePixel = screenWidth / 5;
	}

	/**
	 * ���ش����¼��ܿ�����ҷЧ��---�ȴ��������¼��ڴ������¼�
	 */
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// �����ҷ�ļ�������Ϊnull�����������Ϊnull
		if (mGestureDetector == null) {
			// ���Ϊ����״̬
			if (mStateMode == FLING) {
				// �������������
				mGestureDetector = new GestureDetector(getContext(),
				// �����Ƽ�����ֻ��Ҫʵ�ֻ�����һ������
						new SimpleOnGestureListener() {
							// ���ƴ�������ģʽ
							public boolean onFling(MotionEvent e1,
									MotionEvent e2, float velocityX,
									float velocityY) {
								// ��ImageView�������ʱ�Ž�������
								if (mDragView != null) {
									// �������X���ϵ����ʴ���1000����ÿ��--������Ϊ��һ�ּ������
									if (velocityX > 1000) {
										// ������������
										Rect r = mTempRect;
										// ����һ������Ч����ImageView�ؼ��ϵ����ݽ�ȡ��һ������������
										mDragView.getDrawingRect(r);
										// ����ƶ��������ص��X������ھ����ұ�ԵX�����2/3
										if (e2.getX() > r.right * 2 / 3) {
											// ����Ļ�ұ�Ե���ٻ������ͷ�
											stopDragging();
											// ���ڻ���״̬ʱ---�Ƴ�ԭʼλ����
											mDragListener
													.remove(mOriginalDragPos);
											// �ָ�����item��
											resumeViews(true);
										}
									}
									// ���������ҷ��ʾû��ƫ����
									return true;
								}
								return false;
							}
						});
			}
		}
		switch (ev.getAction()) {
		// �����ڰ����¼�����Ҫ�Ǽ�¼��������ֵ
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			// ����x/y����������ǰ���ص���ListView�е���һ��item��
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION) {
				break;
			}
			if (group != null && group.contains(getAdapter().getItem(itemnum))) {
				break;
			}
			// ����ͼ��ListView�о���һ��item��
			ViewGroup item = (ViewGroup) getChildAt(itemnum
					- getFirstVisiblePosition());
			// ��ȡ����㼰ƫ����
			mDragPointX = x - item.getLeft();
			mDragPointY = y - item.getTop();
			mXOffset = ((int) ev.getRawX()) - x;
			mYOffset = ((int) ev.getRawY()) - y;
			// ָ��x���ط�Χ��������ҷ������
			if (x > screenWidth - dragablePixel) {
				// ���ÿɻ���---���Ա���ȡ
				item.setDrawingCacheEnabled(true);
				// �������ܿ����ͼ�����ڴ�ʱ������һ�ݿ�����ͼ����,��ֹ���ձ�����;��Ϊ��ͣ��ͼƬ
				Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
				// ����λͼ��ʾ��λ��
				startDragging(bitmap, x, y);
				// ��¼Ŀ��λ��
				mDesignDragPos = itemnum;
				mOriginalDragPos = mDesignDragPos;
				mHeight = getHeight();
				int touchSlop = mTouchSlop;
				mUpperBound = Math.min(y - touchSlop, mHeight / 3);
				mLowerBound = Math.max(y + touchSlop, mHeight * 2 / 3);
				return false;
			}
			// ֹͣ��ҷ
			stopDragging();
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			mGestureDetector.onTouchEvent(event);
		}
		if (mDragView != null) {
			int action = event.getAction();
			switch (action) {
			// �����¼�
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				Rect rect = mTempRect;
				// ����ȡ����ͼӳ�䵽Rect������
				mDragView.getDrawingRect(rect);
				stopDragging();
				// ����������ɻ���ģʽ
				if (mStateMode == SLIDE && event.getX() > rect.right * 3 / 4) {
					mDragListener.remove(mOriginalDragPos);
					resumeViews(true);
				} else {
					if (mDesignDragPos >= 0 && mDesignDragPos < getCount()) {
						mDragListener.drop(mOriginalDragPos, mDesignDragPos);
					}
					resumeViews(false);
				}
				break;
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				int x = (int) event.getX();
				int y = (int) event.getY();
				// ��ȡListView������Y��������
				dragView(x, y);
				if (!isExpansion) {
					// ��ȡListView��Ч�Ķ�������Y
					int tempY = this.getTop();
					if (tempY > y) {
						y = tempY;
					}
				}
				int itemnum = pointToPosition(0, y);
				if (itemnum == INVALID_POSITION) {
					break;
				}
				if (itemnum >= 0) {
					if (action == MotionEvent.ACTION_DOWN
							|| itemnum != mDesignDragPos) {
						mDragListener.drag(mDesignDragPos, itemnum);
						mDesignDragPos = itemnum;
						// ���Ϊ����ģʽ
						if (isExpansion) {
							doExpansion();
						}
					}
					int distance = 0;
					adjustScrollBounds(y);
					if (y > mLowerBound) {
						// ����һ���б�
						if (getLastVisiblePosition() < getCount() - 1) {
							distance = y > (mHeight + mLowerBound) / 2 ? 16 : 4;
						} else {
							distance = 1;
						}
					} else if (y < mUpperBound) {
						// �б����¹���һ��
						distance = y < mUpperBound / 2 ? -16 : -4;
						if (getFirstVisiblePosition() == 0
								&& getChildAt(0).getTop() >= getPaddingTop()) {
							// ����Ѿ��ڶ��������ٶ�Ϊ0��������
							distance = 0;
						}
					}
					if (distance != 0) {
						// ʹ��30�������ָ���ľ���
						smoothScrollBy(distance, 30);
					}
				}
				break;
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * ���������߽�
	 * 
	 * @param y
	 */
	private void adjustScrollBounds(int y) {
		if (y >= mHeight / 3) {
			mUpperBound = mHeight / 3;
		}
		if (y <= mHeight * 2 / 3) {
			mLowerBound = mHeight * 2 / 3;
		}
	}

	/**
	 * �ָ�����ListView item�ʹ�䲻��������״̬
	 */
	private void resumeViews(boolean deletion) {
		for (int i = 0;; i++) {
			View v = getChildAt(i);
			if (v == null) {
				if (deletion) {
					// ǿ�Ƹ���item ����
					int position = getFirstVisiblePosition();
					int y = getChildAt(0).getTop();
					setAdapter(getAdapter());
					setSelectionFromTop(position, y);
				}
				try {
					// ����Ҫʱitem�ǿ�ƴ���
					this.layoutChildren();
					v = getChildAt(i);
				} catch (IllegalStateException ex) {
					// ������ΪlayoutChildren�����׳�(�ڽ��̱�������������Ȼ���ڴ����¼�ʱ)
					ex.printStackTrace();
				}
				if (v == null) {
					return;
				}
			}
			ViewGroup.LayoutParams params = v.getLayoutParams();
			params.height = mItemHeightNormal;
			v.setLayoutParams(params);
			v.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * �����ɼ��Ժʹ�С(ʹitem������)
	 */
	private void doExpansion() {
		int childnum = mDesignDragPos - getFirstVisiblePosition();
		if (mDesignDragPos > mOriginalDragPos) {
			childnum++;
		}
		int numheaders = getHeaderViewsCount();
		View first = getChildAt(mOriginalDragPos - getFirstVisiblePosition());
		for (int i = 0;; i++) {
			View vv = getChildAt(i);
			if (vv == null) {
				break;
			}
			int height = mItemHeightNormal;
			int visibility = View.VISIBLE;
			if (mDesignDragPos < numheaders && i == numheaders) {
				// ������ҷ��item���ϵ�����ʱ�ŵ�ListView��һ��
				if (vv.equals(first)) {
					visibility = View.INVISIBLE;
				} else {
					height = mItemHeightExpanded;
				}
			} else if (vv.equals(first)) {
				// �������ڱ���ҷ��item��
				if (mDesignDragPos == mOriginalDragPos
						|| getPositionForView(vv) == getCount() - 1) {
					// ��ԭʼ��λ��������ͣ
					visibility = View.INVISIBLE;
				} else {
					// �����Ͽ���ͣ�����������,�Ѿ��ﵽ��Ԥ��Ч����item����ȫ��ʧ�����ǲ��������Ĵ�СΪ0��Ҳ�����ÿɼ���Ϊgone;
					height = 1;
				}
			} else if (i == childnum) {
				if (mDesignDragPos >= numheaders
						&& mDesignDragPos < getCount() - 1) {
					height = mItemHeightExpanded;
				}
			}
			ViewGroup.LayoutParams params = vv.getLayoutParams();
			params.height = height;
			vv.setLayoutParams(params);
			vv.setVisibility(visibility);
		}
	}

	/**
	 * ��ʼ��ҷ
	 * 
	 * @param bitmap
	 * @param x
	 * @param y
	 */
	private void startDragging(Bitmap bitmap, int x, int y) {
		stopDragging();
		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowParams.x = x - mDragPointX + mXOffset;
		mWindowParams.y = y - mDragPointY + mYOffset;
		mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
		mWindowParams.format = PixelFormat.TRANSLUCENT;
		mWindowParams.windowAnimations = 0;
		Context context = getContext();
		ImageView image = new ImageView(context);
		image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				mItemHeightNormal));
		image.setImageBitmap(bitmap);
		image.setAlpha((int) (255 * alphaRate));
		if (drawable != null) {
			image.setBackgroundDrawable(drawable);
		} else {
			image.setBackgroundColor(backgroundColor);
		}
		// ������������Ҫʱ������
		mDragBitmap = bitmap;
		mWindowManager.addView(image, mWindowParams);
		mDragView = image;
	}

	/**
	 * ��ҷ��ͼ
	 */
	private void dragView(int x, int y) {
		if (mStateMode == SLIDE) {
			float alpha = 1.0f;
			int width = mDragView.getWidth();
			if (x > width / 2) {
				alpha = ((float) (width - x)) / (width / 2);
			}
			mWindowParams.alpha = alpha;
		}
		if (mStateMode == FLING || mStateMode == TRASH) {
			mWindowParams.x = x - mDragPointX + mXOffset;
		} else {
			mWindowParams.x = 0;
		}
		mWindowParams.y = y - mDragPointY + mYOffset;
		mWindowManager.updateViewLayout(mDragView, mWindowParams);
		if (mTrashcan != null) {
			int width = mDragView.getWidth();
			if (y > getHeight() * 3 / 4) {
				mTrashcan.setLevel(2);
			} else if (width > 0 && x > width / 4) {
				mTrashcan.setLevel(1);
			} else {
				mTrashcan.setLevel(0);
			}
		}
	}

	/**
	 * ������ҷ
	 */
	private void stopDragging() {
		if (mDragView != null) {
			mDragView.setVisibility(GONE);
			WindowManager wm = (WindowManager) getContext().getSystemService(
					Context.WINDOW_SERVICE);
			wm.removeView(mDragView);
			mDragView.setImageDrawable(null);
			mDragView = null;
		}
		if (mDragBitmap != null) {
			mDragBitmap.recycle();
			mDragBitmap = null;
		}
		if (mTrashcan != null) {
			mTrashcan.setLevel(0);
		}
	}

	/**
	 * ��������ͼƬ
	 * 
	 * @param trash
	 */
	public void setTrashcan(Drawable trash) {
		mTrashcan = trash;
		mStateMode = TRASH;
	}

	/**
	 * �ֶ����ÿ���ҷ�Ŀռ��ߴ�x���ص�
	 * 
	 * @param dragablePixel
	 */
	public void setDragablePixel(int dragablePixel) {
		this.dragablePixel = dragablePixel;
	}

	/**
	 * ʹ����Դ������ҷʱ�ı���ͼƬ
	 * 
	 * @param resDrawable
	 */
	public void setResDrawable(int resDrawable) {
		this.drawable = this.getContext().getResources()
				.getDrawable(resDrawable);
	}

	/**
	 * ʹ��drawable����������ҷʱ�ı���
	 * 
	 * @param drawable
	 */
	public void setResDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	/**
	 * ����item�������߶�---�ڷ�װ���Զ���������ڸ߶Ȳ�ȷ�����������������Ҫ��Ϊ����������ͼ ����������Ϊ���������Ҳ��ܷ���
	 * 
	 * @param mItemHeightNormal
	 */
	public void setmItemHeightNormal(int mItemHeightNormal) {
		this.mItemHeightNormal = mItemHeightNormal;
	}

	/**
	 * �����Ϸ�ʱ�ı���ɫ
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * ������ҷʱ�ļ�������������ִ��ҵ���߼�(ʵ����ط���)
	 * 
	 * @param listener
	 */
	public void setDragListViewListener(OnDragListViewListener listener) {
		mDragListener = listener;
	}

	/**
	 * �����Ƿ�Ϊ����ģʽ
	 * 
	 * @param isExpansion
	 */
	public void setExpansion(boolean isExpansion) {
		this.isExpansion = isExpansion;
	}

	/**
	 * ���÷�������---�Ա�֤��ʾ�����item���ɵ��
	 * 
	 * @param group
	 */
	public void setGroup(List<String> group) {
		this.group = group;
		this.isExpansion = false;
	}

}
