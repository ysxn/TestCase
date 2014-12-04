package sutras.cheng.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ע���������Զ������Ե������ռ�����ǰ�װӦ�õİ����������޷��ҵ�������;͸�������Ϊ255
 * 
 * @author chengkai
 * 
 */
public class AlphaImageView extends ImageView {
	// ��Ҫ�ж��Ƿ�����̣߳�
	private final int YES = 1;
	// ����Ҫ�ж�
	private final int NO = -1;
	private int curState = YES;
	// ͼƬÿ��Ҫ�ı��͸���ȵȼ�
	private int alphaLevel = 10;
	// ��ǰ͸����
	private int curAlpha = 100;
	// ��С͸����
	private int minAlpha = 100;
	// ���͸����
	private int maxAlpha = 240;
	// ��ʱ���ٶ�0.5��ı�һ��͸����
	private int speed = 500;
	// ���ظ���͸���ȵı�־
	private boolean flag = true;
	// ��ʱ�ı�͸�����Ƿ�ֹͣ
	private boolean isStop = false;
	// ʱ��Ϊ5��
	private int duration = 5000;
	Handler handler = new Handler();

	public AlphaImageView(Context context) {
		this(context, null);
	}

	public AlphaImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ������������
		// TypedArray
		// typedArray=context.obtainStyledAttributes(attrs,R.styleable.AlphaImageView);
		// //��ȡduration ����--Ĭ��Ϊ1
		// int
		// duration=typedArray.getInt(R.styleable.AlphaImageView_duration,1);
		// �����ʼ͸����--ÿ�θı�Ĵ�С
		alphaLevel = maxAlpha * speed / duration;
	}

	public AlphaImageView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

	private Runnable run = new Runnable() {
		public void run() {
			if (flag) {
				curAlpha += alphaLevel;
			} else {
				curAlpha -= alphaLevel;
			}
			if (curAlpha >= maxAlpha) {
				flag = false;
			} else if (curAlpha <= minAlpha) {
				flag = true;
			}
			AlphaImageView.this.invalidate();// ��һ��һֱ��֪ͨ�ı�
			handler.postDelayed(this, speed);
		}
	};

	@Override
	protected void onDraw(Canvas canvas) {
		if (curState == YES) {
			if (!isStop) {
				// ���δִֹͣ���߳�
				handler.post(run);
				curState = NO;
			} else {
				handler.removeCallbacks(run);
			}
		}
		this.setAlpha(curAlpha);
		super.onDraw(canvas);
	}

	public int getMinAlpha() {
		return minAlpha;
	}

	public void setMinAlpha(int minAlpha) {
		this.minAlpha = minAlpha;
	}

	public int getMaxAlpha() {
		return maxAlpha;
	}

	public void setMaxAlpha(int maxAlpha) {
		this.maxAlpha = maxAlpha;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAlphaLevel() {
		return alphaLevel;
	}

	public void setAlphaLevel(int alphaLevel) {
		this.alphaLevel = alphaLevel;
		this.invalidate();
	}

	public int getCurAlpha() {
		return curAlpha;
	}

	public void setCurAlpha(int curAlpha) {
		this.curAlpha = curAlpha;
		this.invalidate();
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		this.invalidate();
	}

	public boolean isStop() {
		return isStop;
	}

	public void stop() {
		curState = YES;
		// ���ò�͸��
		this.curAlpha = maxAlpha;
		this.isStop = true;
		this.invalidate();
	}

	public void start() {
		curState = YES;
		isStop = false;
		this.invalidate();
	}

}
