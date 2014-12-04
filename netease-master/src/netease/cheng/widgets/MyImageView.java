package netease.cheng.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * ͼƬ�ؼ�
 * @author Administrator
 *
 */
public class MyImageView extends ImageView{

	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyImageView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		onDrawExtra(canvas);
	}
	//�ڻ������ٻ�һ���͸����ǰ��
	protected void onDrawExtra(Canvas canvas) {
		int w=getWidth();
		int h=getHeight();
		Rect r=new Rect(0,h/10,w,h);
		Paint paint=new Paint();
		int color=Color.argb(125, 0,0,0);
		paint.setColor(color);
		canvas.drawRect(r, paint);
//		canvas.drawARGB(125, 0, 0, 0);
	}
}
