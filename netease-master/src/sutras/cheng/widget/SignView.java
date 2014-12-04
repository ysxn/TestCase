package sutras.cheng.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ǩ���ؼ�--ע��һ���ܵ��û�����ȡ��ߵķ���---�����������
 * 
 * @author chengkai
 * 
 */
public class SignView extends View {
	// Ĭ��͸��ֽ��
	private int paperColor = Color.argb(0, 0, 0, 0);
	private float preX;
	private float preY;
	private Path path;
	private Paint cachePaint;
	public Bitmap cacheBitmap;
	public Canvas cacheCanvas;
	private boolean isInit = true;

	public SignView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SignView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SignView(Context context) {
		super(context);
	}

	/**
	 * ����ǩ����
	 * 
	 * @param context
	 */
	public void initParam() {
		// ��ʼ������
		cacheCanvas = new Canvas();
		// 256ɫλͼ---������λͼ
		cacheBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
				Config.ARGB_8888);
		path = new Path();
		// ���û������Ƶ��ڴ��cacheBitmap��
		cacheCanvas.setBitmap(cacheBitmap);
		// ���û�����ɫ
		cacheCanvas.drawColor(paperColor);
		//
		cachePaint = new Paint(Paint.DITHER_FLAG);
		// ������ɫ
		cachePaint.setColor(Color.RED);
		// ���û��ʿ��
		cachePaint.setStrokeWidth(1.0f);
		// ���ʷ��--����
		cachePaint.setStyle(Paint.Style.STROKE);
		// ���ÿ����
		cachePaint.setAntiAlias(true);
		cachePaint.setDither(true);
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float pointX = event.getX();
		float pointY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(pointX, pointY);
			preX = pointX;
			preY = pointY;
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(preX, preY, pointX, pointY);
			preX = pointX;
			preY = pointY;
			break;
		case MotionEvent.ACTION_UP:
			// ���滭����·��
			cacheCanvas.drawPath(path, cachePaint);
			// ����·��
			path.reset();
			break;
		}
		this.invalidate();// ֪ͨ�����ͼ
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isInit) {
			// ����������
			Rect rect = new Rect();
			Rect rect2 = new Rect();
			// ���Լ��Ŀ�߸�ֵ������
			this.getDrawingRect(rect2);
			// ����Ļ��߷ֲ���ֵ��������
			this.getWindowVisibleDisplayFrame(rect);
			// int tmpH = rect.height();// Ӧ�ó����߶�
			// int tmpT = rect.top;// ״̬���߶�
			// ֻ�е�onDraw�������ص���߶ȺͿ�Ȳű�����
			// int tmpH2 = rect2.height();// �Լ���������ĸ߶�
			System.out.println(this.getWidth() + "<<<>>>" + this.getHeight());
			initParam();
			isInit = false;
		}
		// ������ʱ��������ɫ
		// canvas.drawColor(paperColor);
		// canvas.drawBitmap(defaultBitmap, 0, 0,null);
		Paint tmpPaint = new Paint();// ����Ĭ�ϻ���
		// ������λͼ���Ƶ�View�ؼ���
		canvas.drawBitmap(cacheBitmap, 0, 0, tmpPaint);
		// ����·����
		canvas.drawPath(path, cachePaint);

	}

	/**
	 * ʹ����ɫΪǩ������
	 * 
	 * @param color
	 */
	public void setPaperColor(int color) {
		this.paperColor = color;
		Canvas canvas = new Canvas();
		// �������Ȼ�λͼ�����û�����ɫ����ʼ��������û�д�С�ģ�����λͼ����д�С
		cacheCanvas = canvas;// �����滭�����¸�ֵ�������ǰ��ͼ��
		cacheCanvas.setBitmap(cacheBitmap);// ִ����һ����Ϊ��Ϊ����ָ������
		cacheCanvas.drawColor(paperColor);// Ϊ���滭��������ɫ
		cacheCanvas.setBitmap(cacheBitmap);// �ٱ���
		cacheCanvas.save(Canvas.ALL_SAVE_FLAG);// ��������ͼ��
	}

	/**
	 * ʹ��λͼΪǩ������
	 * 
	 * @param bitmap
	 */
	public void setPaperBg(Bitmap bitmap) {
		Canvas canvas = new Canvas();
		cacheCanvas = canvas;
		canvas.drawBitmap(bitmap, 0, 0, null);
		cacheCanvas.setBitmap(cacheBitmap);
		cacheCanvas.save(Canvas.ALL_SAVE_FLAG);
	}

	/**
	 * ���ػ����λͼ����
	 * 
	 * @return
	 */
	public Bitmap getBitmap() {
		return cacheBitmap;
	}

	/**
	 * ���û��滭��
	 * 
	 * @return
	 */
	public Paint getCachePaint() {
		return cachePaint;
	}

	/**
	 * ��ȡ���滭��
	 * 
	 * @param cachePaint
	 */
	public void setCachePaint(Paint cachePaint) {
		this.cachePaint = cachePaint;
	}

}
