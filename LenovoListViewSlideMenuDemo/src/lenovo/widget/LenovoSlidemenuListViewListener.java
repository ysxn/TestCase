package lenovo.widget;

import lenovo.view.LenovoSlideMenuGroupView;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class LenovoSlidemenuListViewListener implements View.OnTouchListener {
	private static final String TAG = "LenovoSlidemenuListViewListener";
	private ListView mListView;
	private OnSlideMenuClickListener mOnSlideMenuClickListener;
	private LenovoSlideMenuGroupView mDownView;
	private LenovoSlideMenuGroupView mSelectView;
	private VelocityTracker mVelocityTracker;
	private View mMenuItemView;
	
	private int mScrollState;
	private int mTouchSlop;
	private int mMinFlingVelocity;
	private int mMaxFlingVelocity;
	
	private float mDownX;
	private float mDownY;
	private float mTouchX;
	private float mTouchY;
	
	private boolean bSliding;
	private boolean bIntercepTouch;
	private boolean bMenuTouch;
	
	public LenovoSlidemenuListViewListener(ListView listView, OnSlideMenuClickListener listener) {
		mListView = listView;
		mOnSlideMenuClickListener = listener;
		ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
		mTouchSlop = vc.getScaledTouchSlop();
		mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
		mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
		mScrollState = LenovoSlideMenuGroupView.SLIDE_STATE_IDLE;
	}
	
	public interface OnSlideMenuClickListener {
		void onClick(ListView parent, View view, int position);
	}
	
	private void registerOnScrollListener() {
		if (mDownView == null) {
			return;
		}

		mDownView.registerSlideSectionListener(new LenovoSlideMenuGroupView.OnSlideSectionListener() {

			@Override
			public void onSlideStateChanged(LenovoSlideMenuGroupView view, int state) {
				//Log.e(TAG, "####state:"+state);
				// TODO Auto-generated method stub
				mScrollState = state;
				if (mScrollState == LenovoSlideMenuGroupView.SLIDE_STATE_SETTING) {
					mSelectView = view;
				} else {
					mSelectView = null;
				}
			}
		});
	}
	
	public void abortSlide() {
		//bForceAbort = true;
		if (mScrollState == LenovoSlideMenuGroupView.SLIDE_STATE_SETTING) {
			if (mMenuItemView != null
				&& mMenuItemView.isSelected()) {
				mMenuItemView.setSelected(false);
			}
			mSelectView.resetState();
		}
		
		mDownView = null;
		bSliding = false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		mTouchX = event.getRawX();
		mTouchY = event.getRawY();
		
		//Log.e(TAG, "####onTouch:"+event.getAction()+", mScrollState:"+mScrollState);
		
		switch (event.getAction())
		{ 
		case MotionEvent.ACTION_DOWN:
			if (!bIntercepTouch
				&& (mScrollState == LenovoSlideMenuGroupView.SLIDE_STATE_SCROLLING)) {
				bIntercepTouch = true;
			}
			
			if (!bIntercepTouch
				&& (mScrollState == LenovoSlideMenuGroupView.SLIDE_STATE_SETTING)) {
				bIntercepTouch = true;
				mMenuItemView = mSelectView.getTouchMenu((int)mTouchX, (int)mTouchY);
				if (mMenuItemView == null){
					mSelectView.cancelScroll();
				} else {
					bMenuTouch = true;
					mMenuItemView.setSelected(true);
				}
			}
			
			if (bIntercepTouch) {
				return true;
			}
			
			mDownView = (LenovoSlideMenuGroupView)getFocusChild((int)mTouchX, (int)mTouchY);
			if (mDownView != null) {
				//mDownPosition = mListView.getPositionForView(mDownView);
				//Log.e(TAG, "#####mDownView:" + mDownView);
				mDownX = mTouchX;
				mDownY = mTouchY;
				if (mVelocityTracker == null) {
					mVelocityTracker = VelocityTracker.obtain();
				}
				mVelocityTracker.addMovement(event);
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			float deltaX_move = mTouchX - mDownX;
			float deltaY_move = mTouchY - mDownY;
			
			if (bIntercepTouch) {
				if (bMenuTouch
					&& mScrollState == LenovoSlideMenuGroupView.SLIDE_STATE_SETTING) {
					if (mMenuItemView != null
						&& (mMenuItemView != mSelectView.getTouchMenu((int)mTouchX, (int)mTouchY))) {
						mMenuItemView.setSelected(false);
					}
				}
				return true;
			}
			
			if (mDownView == null
				|| mVelocityTracker == null) {
				break;
			}

			mVelocityTracker.addMovement(event);
			
			if (!bSliding
				&& Math.abs(deltaY_move) > mTouchSlop) {
				mDownView = null;
				break;
			}
			
			if (!bSliding
				&& Math.abs(deltaX_move) > mTouchSlop) {
				bSliding = true;
			}
			
			if (bSliding) {
				//Log.e(TAG, "####touchScroll");
				mListView.requestDisallowInterceptTouchEvent(true);

				// Cancel ListView's touch (un-highlighting the item)
				MotionEvent cancelEvent = MotionEvent.obtain(event);
				cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
						(event.getActionIndex()
								<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
				mListView.onTouchEvent(cancelEvent);
				cancelEvent.recycle();
				
				mDownView.touchScroll((int)deltaX_move);
				return true;
			}
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			boolean bIntercepInternel = false;
			if (bIntercepTouch || bSliding) {
				if (bIntercepTouch
					&& bMenuTouch
					&& (mScrollState == LenovoSlideMenuGroupView.SLIDE_STATE_SETTING)) {
					if (mMenuItemView != null
						&& (mMenuItemView.isSelected())) {
						mMenuItemView.setSelected(false);
						//Log.e(TAG, "#####mMenuItemView click:");
						mSelectView.resetState();
						if (mOnSlideMenuClickListener != null) {
							int position = ((Integer)mMenuItemView.getTag()).intValue();
							mOnSlideMenuClickListener.onClick(mListView, mSelectView, position);
						}
					}
				}
				bIntercepInternel = true;
			}
			
			if (!bIntercepTouch
				&& bSliding
				&& mDownView != null
				&& mVelocityTracker != null) {
				mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
				int v_x = (int)mVelocityTracker.getXVelocity();
				int v_y = (int)mVelocityTracker.getYVelocity();
				registerOnScrollListener();
				mDownView.touchCancelScroll(v_x, v_y);
				bIntercepInternel = true;
			}
			
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			bIntercepTouch = false;
			bMenuTouch = false;
			mDownView = null;
			bSliding = false;
			
			if (bIntercepInternel) {
				return true;
			}
			break;
			
		default:
			break;
		}
		
		return false;
	}
	
	private View getFocusChild(int x, int y) {
		View child = null;
		Rect rect = new Rect();
		int[] listViewCoords = new int[2];
		mListView.getLocationOnScreen(listViewCoords);
		int view_x = (int) x - listViewCoords[0];
		int view_y = (int) y - listViewCoords[1];
		int childCount = mListView.getChildCount();
		//Log.e(TAG, "####childCount:"+childCount);
		
		for (int i = 0; i < childCount; i++) {
			child = mListView.getChildAt(i);
			child.getHitRect(rect);
			if (rect.contains(view_x, view_y)) {
				//Log.e(TAG, "####Focus: "+i);
				return child;
			}
		}
		
		return null;
	}
}
