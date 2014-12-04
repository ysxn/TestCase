package sutras.cheng.widget;

import sutras.cheng.listener.SectionListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * ����ѡ���������ڿ��ٶ�λ
 * 
 * @author chengkai
 * 
 */
public class SectionBar extends View {
	// �ַ�������Դ--���ڿ��ٶ�λ
	private char[] crr;
	// ������ڵ�������
	private Context context;
	// ÿһ����ĸ���ڵ�����ֵ
	private int barHeight;
	// ��ʾ�������ĸ����
	private View container;
	// ������ʾ�������ĸ
	private TextView hint;
	// �Ƿ���ʾ�����
	private boolean isHint = false;
	// �ؼ��ı�����ɫ
	private int backgroundColor = 0x00000000;
	// ������ɫ--������ĸ��ʾ��ɫ
	private int paintColor = 0xff000000;
	// ���û������ַ���С
	private int textSize = 12;
	// �ײ���϶
	private int space = 1;
	// ��Ҫ�����߳���ʵ�ֵļ�����
	private SectionListener mListener = new SectionListener() {
		public void doPosition(String str) {
		}
	};

	public SectionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public SectionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SectionBar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		crr = new char[] { '#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z' };
		setBackgroundColor(backgroundColor);
	}

	public void setHint(boolean isHint) {
		this.isHint = isHint;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setPaintColor(int paintColor) {
		this.paintColor = paintColor;
	}

	// ��ʼ����ʾ�������ʾЧ��
	public void initHintContainer(View container, TextView hint) {
		this.container = container;
		this.hint = hint;
		isHint = true;
	}

	// ��ʼ����ʾ�������ʾЧ��
	public void initHintContainer(int resLay, int resText) {
		this.container = ((Activity) context).findViewById(resLay);
		this.hint = (TextView) ((Activity) context).findViewById(resText);
		isHint = true;
	}

	public void setSectionListener(SectionListener listener) {
		this.mListener = listener;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		barHeight = (getHeight() - space) / crr.length;
		super.onLayout(changed, left, top, right, bottom);
	}

	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		int i = (int) event.getY();
		int idx = i / barHeight;
		if (idx >= crr.length) {
			idx = crr.length - 1;
		} else if (idx < 0) {
			idx = 0;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			if (isHint) {
				container.setVisibility(View.VISIBLE);
				hint.setText(crr[idx] + "");
			}
			// ִ�лص�����
			mListener.doPosition(crr[idx] + "");
			break;
		case MotionEvent.ACTION_UP:
			if (isHint) {
				container.setVisibility(View.GONE);
			}
			break;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(paintColor);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);
		float widthCenter = getMeasuredWidth() / 2;
		for (int i = 0; i < crr.length; i++) {
			canvas.drawText(String.valueOf(crr[i]), widthCenter, barHeight
					+ (i * barHeight), paint);
		}
		super.onDraw(canvas);
	}

}
