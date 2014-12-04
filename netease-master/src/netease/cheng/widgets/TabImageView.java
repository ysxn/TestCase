package netease.cheng.widgets;

import netease.cheng.main.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * ѡ�ͼƬ��ʾ--����Ҫ����������ʹ���߼�������Ч��
 * @author Administrator
 *
 */
public class TabImageView extends ImageView{
	StateListDrawable state;
	Context context;
	Drawable selected,font;
	Bitmap src;
	
	public TabImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		
		state.addState(SELECTED_STATE_SET, selected);
	}
	public TabImageView(Context context,int back_bg,int cur_bg){
		super(context);
		this.context=context;
		Bitmap src=BitmapFactory.decodeResource(context.getResources(),back_bg);
		font=context.getResources().getDrawable(R.drawable.tab_front_bg);
		selected=context.getResources().getDrawable(cur_bg);
		state=new StateListDrawable();
		//���״̬ѡ����
		state.addState(SELECTED_STATE_SET, selected);
		
//		this.setBackgroundDrawable(state);
//		this.setImageBitmap(src);
		this.setImageDrawable(state);
		this.setBackgroundResource(back_bg);
	}
}
