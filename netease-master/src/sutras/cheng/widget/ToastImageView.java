package sutras.cheng.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ѡ�ͼƬ��ʾ--����Ҫ����������ʹ���߼�������Ч��
 * 
 * @author Administrator
 * 
 */
public class ToastImageView extends ImageView {
	private StateListDrawable state;
	private Drawable selected;

	public ToastImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ToastImageView(Context context, int unselected, int selected) {
		super(context);
		this.selected = context.getResources().getDrawable(selected);
		state = new StateListDrawable();
		// ���״̬ѡ����
		state.addState(ImageView.SELECTED_STATE_SET, this.selected);
		this.setImageDrawable(state);
		this.setBackgroundResource(unselected);
	}
}
