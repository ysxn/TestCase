
package com.codezyw.common;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 继承自 {@link LinearLayout}<br>
 * 用于主界面底部菜单UI<br>
 * 同时包含了管理fragment的切换
 */
public class BottomMenuLayout extends LinearLayout {
    @SuppressWarnings("unused")
    private static boolean DEBUG = true;
    @SuppressWarnings("unused")
    private static final String TAG = "zyw";
    private Context mContext;
    @SuppressWarnings("unused")
    private LayoutInflater mInflater;
    @SuppressWarnings("unused")
    private Scroller mScroller;
    private ArrayList<View> mAllMenu = new ArrayList<View>();
    private ArrayList<BaseFragment> mAllBaseFragment = new ArrayList<BaseFragment>();
    private BaseFragment mCurrent;
    private int mFramelayoutId;

    public BottomMenuLayout(Context context) {
        this(context, null);
    }

    public BottomMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mScroller = new Scroller(context, new LinearInterpolator());
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mAllMenu.clear();
        mAllBaseFragment.clear();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 切换fragment使用的Framelayout的Id
     */
    public void setFrameId(int id) {
        mFramelayoutId = id;
    }

    /**
     * 添加底部菜单，图片可以为空
     * 
     * @param imageResId <=0 为空
     * @param s
     */
    public void addMenu(int imageResId, String s) {
        View v = createMenuView(imageResId, s);
        addView(v);
        mAllMenu.add(v);
    }

    /**
     * 添加底部菜单，图片可以为空
     * 
     * @param imageResId <=0 为空
     * @param s
     */
    public void addMenu(int imageResId, String s, int index) {
        View v = createMenuView(imageResId, s);
        addView(v, index);
        mAllMenu.add(index, v);
    }

    /**
     * 添加底部菜单，图片可以为空<br>
     * 同时设置对应的fragment和菜单点击切换listener<br>
     * 
     * @param imageResId <=0 为空
     * @param s
     * @param index
     * @param fragment
     */
    public void addMenu(int imageResId, String s, final int index, BaseFragment fragment) {
        View v = createMenuView(imageResId, s);
        addView(v, index);
        mAllMenu.add(index, v);
        mAllBaseFragment.add(fragment);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchFragment(index);
            }
        });
        if (index == 0) {
            switchFragment(0);
        }
    }

    private View createMenuView(int imageResId, String s) {
        LinearLayout parent = new LinearLayout(mContext);
        ImageView image = new ImageView(mContext);
        TextView text = new TextView(mContext);

        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setGravity(Gravity.CENTER);
        image.setScaleType(ScaleType.CENTER_INSIDE);
        if (imageResId > 0) {
            image.setImageResource(imageResId);
        } else {
            image.setVisibility(View.GONE);
        }
        text.setText(s);
        text.setSingleLine(true);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(ColorHelper.WHITE);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lparent = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lparent.weight = 1.0f;

        parent.addView(image, lp);
        parent.addView(text, lp);
        parent.setLayoutParams(lparent);
        return parent;
    }

    private void switchFragment(int i) {
        BaseFragment old = mCurrent;
        FragmentTransaction ft = ((BaseFragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        if (old != null) {
            ft.hide(old);
        }
        mCurrent = mAllBaseFragment.get(i);
        if (!mCurrent.isAdded()) {
            ft.add(mFramelayoutId, mCurrent);
        } else {
            ft.show(mCurrent);
        }
        ft.commitAllowingStateLoss();
        for (int index = 0; index < mAllMenu.size(); index++) {
            View v = mAllMenu.get(index);
            TextView text = (TextView) ((LinearLayout) v).getChildAt(1);
            if (index == i) {
                text.setTextColor(ColorHelper.BLUE);
            } else {
                text.setTextColor(ColorHelper.WHITE);
            }
        }
    }

    /**
     * @return 菜单数量
     */
    public int getMenuCount() {
        return getChildCount();
    }

    /**
     * @param i
     * @return 菜单i对应的View
     */
    public View getMenuAt(int i) {
        return getChildAt(i);
    }

    /**
     * 设置菜单点击事件
     */
    public void setMenuOnClickListener(int i, View.OnClickListener listener) {
        View v = getChildAt(i);
        if (v != null) {
            v.setOnClickListener(listener);
        }
    }

    /**
     * Fragment拦截处理按键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mCurrent.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
