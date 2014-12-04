package sutras.cheng.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * ��㴥�ص�ImageView
 * 
 * @author chengkai
 * 
 */
public class MultiImageView extends ImageView {
	private Matrix matrix;
	private Matrix savePreMatrix;
	private PointF start;// ��һ��������
	private PointF mid;
	private float oldDist = 1.0f;
	// ͼƬ������״̬
	final int NONE = 0;// ��״̬
	final int DRAG = 1;// ��ҷ״̬
	final int ZOOM = 2;// ����״̬
	int MODE = NONE;

	public MultiImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MultiImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MultiImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		matrix = new Matrix();
		savePreMatrix = new Matrix();
		start = new PointF();
		mid = new PointF();
	}

	// ֻ����Ķ��
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
		// ���㰴��ʱ�������¼�
		case MotionEvent.ACTION_DOWN:
			savePreMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			MODE = DRAG;// ת��Ϊ��ҷ״̬
			break;
		case MotionEvent.ACTION_MOVE:
			if (MODE == DRAG) {
				matrix.set(savePreMatrix);
				// ˮƽ�ƶ�
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
			} else if (MODE == ZOOM) {
				float newDist = calDis(event);
				float scale = newDist / oldDist;
				matrix.set(savePreMatrix);
				matrix.postScale(scale, scale, mid.x, mid.y);
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = calDis(event);// ��������֮��ľ���
			savePreMatrix.set(matrix);
			mid = this.getMidPoint(mid, event);
			MODE = ZOOM;// ת��Ϊ����״̬
			break;
		// ͬʱ����������״̬
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			MODE = NONE;
			break;
		}
		this.setImageMatrix(matrix);
		return true;
	}

	// ��������֮��ľ���
	private float calDis(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// ������������ĵ�
	private PointF getMidPoint(PointF p, MotionEvent event) {
		float midX = (event.getX(0) + event.getX(1)) / 2;
		float midY = (event.getY(0) + event.getY(1)) / 2;
		p.set(midX, midY);
		return p;
	}
}
