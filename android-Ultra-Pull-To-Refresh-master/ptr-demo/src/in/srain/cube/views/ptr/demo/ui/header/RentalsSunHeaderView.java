
package in.srain.cube.views.ptr.demo.ui.header;

import in.srain.cube.views.ptr.PullViewManager;
import in.srain.cube.views.ptr.PullViewTensionManager;
import in.srain.cube.views.ptr.PullWidget;
import in.srain.cube.views.ptr.PullWidget.OnPullUIListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class RentalsSunHeaderView extends View implements OnPullUIListener {

    private RentalsSunDrawable mDrawable;
    private PullWidget mPtrFrameLayout;
    private PullViewTensionManager mPtrTensionIndicator;

    public RentalsSunHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RentalsSunHeaderView(Context context) {
        super(context);
        init();
    }

    public RentalsSunHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setUp(PullWidget ptrFrameLayout) {
        mPtrFrameLayout = ptrFrameLayout;
        mPtrTensionIndicator = new PullViewTensionManager();
        mPtrFrameLayout.setPtrIndicator(mPtrTensionIndicator);
    }

    private void init() {
        mDrawable = new RentalsSunDrawable(getContext(), this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mDrawable.getTotalDragDistance() * 5 / 4;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int pl = getPaddingLeft();
        int pt = getPaddingTop();
        mDrawable.setBounds(pl, pt, pl + right - left, pt + bottom - top);
    }

    @Override
    public void onUIReset(PullWidget frame) {
        mDrawable.resetOriginals();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
        float percent = mPtrTensionIndicator.getOverDragPercent();
    }

    @Override
    public void onUIRefreshPrepare(PullWidget frame) {

    }

    @Override
    public void onUIRefreshBegin(PullWidget frame) {
        mDrawable.start();
        float percent = mPtrTensionIndicator.getOverDragPercent();
        mDrawable.offsetTopAndBottom(mPtrTensionIndicator.getCurrentPosY());
        mDrawable.setPercent(percent);
        invalidate();
    }

    @Override
    public void onUIRefreshComplete(PullWidget frame) {
        float percent = mPtrTensionIndicator.getOverDragPercent();
        mDrawable.stop();
        mDrawable.offsetTopAndBottom(mPtrTensionIndicator.getCurrentPosY());
        mDrawable.setPercent(percent);
        invalidate();
    }

    @Override
    public void onUIPositionChange(PullWidget frame, boolean isUnderTouch, byte status, PullViewManager ptrIndicator) {
        float percent = mPtrTensionIndicator.getOverDragPercent();
        mDrawable.offsetTopAndBottom(mPtrTensionIndicator.getCurrentPosY());
        mDrawable.setPercent(percent);
        invalidate();
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (dr == mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }
}
