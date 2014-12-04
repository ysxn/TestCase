package netease.cheng.utils;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
/**
 * �ο���δʹ��
 * @author Administrator
 *
 */
public class DotIndicator extends Indicator {
	private static final String TAG = "SlideDotIndicator";
	//�Ƿ���ʾ����
	private boolean mIsShowNum = false;

	public DotIndicator(Context paramContext) {
		super(paramContext);
		init(paramContext);
	}

	public DotIndicator(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext);
	}

	private void init(Context paramContext) {
	}
	//���õ�ǰѡ��
	public void onSetCurrentItem() {
		//�����ӽڵ������
		int i = getChildCount();
		for (int k = 0; k < i; k++) {
			DotIndicatorItem item = (DotIndicatorItem) getChildAt(k);
			//�������ʾ������ô��������
			if (this.mIsShowNum){
				item.resetNum();
			}
			//����--->��ȡ�ؼ��ϵ�ͼƬ//���ñ�ʾֻ��ʾ��һ��ͼƬ
			((TransitionDrawable) item.getDrawable()).resetTransition();
		}
		//��ȡ��ǰѡ��
		int j = getCurrentItem();
		if ((j < 0) || (j >= i)){
			return;
		}
		DotIndicatorItem item1 = (DotIndicatorItem) getChildAt(j);
		if (this.mIsShowNum)
			item1.setNum(j);
		((TransitionDrawable) item1.getDrawable()).startTransition(50);
	}

	public void onSetTotalItems() {
		//�Ӹ����з������е���ͼ
		detachAllViewsFromParent();
		int i = getTotalItems();
		LayoutInflater localLayoutInflater=null;
		if (i > 0){
			localLayoutInflater = LayoutInflater.from(getContext());
		}
		for (int j = 0;j<i; ++j) {
			DotIndicatorItem item = (DotIndicatorItem)
			localLayoutInflater.inflate(2130903072, null);
			((TransitionDrawable) item.getDrawable())
			.setCrossFadeEnabled(true);
			addView(item,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}
}