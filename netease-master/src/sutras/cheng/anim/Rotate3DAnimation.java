package sutras.cheng.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

/**
 * 3d��������תԭ��---ʹ��һ���������Χ��Ŀ�����㣻 Ȼ�󽫹��̼�¼��Matrix�����У� ֮��ֱ�Ӳ������Matrix������ʵ��3d��Ч
 * 
 * @author chengkai
 * 
 */
public class Rotate3DAnimation extends Animation {
	// Ĭ����ʼ��ת�Ƕ�---from
	public static final float PRE_FROMDEGREE = 0.0f;
	// Ĭ����ʼ��ת�Ƕ�---to
	public static final float PRE_TODEGREE = 90.0f;
	// Ĭ�Ͻ��淭ת�Ƕ�---from
	public static final float NEXT_FROMDEGREE = -90.0f;
	// Ĭ�Ͻ��淭ת�Ƕ�---to
	public static final float NEXT_TODEGREE = 0.0f;
	// Ĭ�Ϸ�תʱ��
	public static final long DURATION = 250L;
	// Ĭ�ϱ��ַ�ת���״̬
	public static final boolean FILLAFTER = true;
	// Ĭ�ϼ��ٲ�ֵ��
	public static final Interpolator INTERPOLATOR = new AccelerateInterpolator();
	// �������
	private Camera mCamera;
	// X��������
	private final float mCenterX;
	// Y��������
	private final float mCenterY;
	// ����ת�����
	private final float mDepthZ;
	// ��ʼ�Ƕ�
	private final float mFromDegrees;
	// �����Ƕ�
	private final float mToDegrees;
	// �Ƿ�������ʾ����
	private final boolean mReverse;

	public Rotate3DAnimation(float fromDegrees, float toDegrees, float centerX,
			float centerY, float depthz, boolean bool) {
		this.mFromDegrees = fromDegrees;
		this.mToDegrees = toDegrees;
		this.mCenterX = centerX;
		this.mCenterY = centerY;
		this.mDepthZ = depthz;
		this.mReverse = bool;
	}

	// �ڳ�ʼ���������ú󱻵���
	protected void applyTransformation(float time, Transformation tran) {
		// time��һ��ϵͳ����ʱ�䣻ʱ������С�����ϵͳĬ�ϵ���תʱ�䶼�ܶ�
		Matrix matrix = tran.getMatrix();
		// �µ�ת��
		float newDegrees = mFromDegrees + time * (mToDegrees - mFromDegrees);
		mCamera.save();// ��תǰ���浱ǰ����
		if (!this.mReverse) {
			mCamera.translate(0.0F, 0.0F, this.mDepthZ * (1.0F - time));
		} else {
			mCamera.translate(0.0F, 0.0F, time * this.mDepthZ);
		}
		// ����Y����ת�¼���ĽǶ�
		mCamera.rotateY(newDegrees);
		// ���ݾ�����󵽵ײ㣻��õײ�matrix��ʵ��
		mCamera.getMatrix(matrix);
		// ������ָ�ԭ״
		mCamera.restore();
		// ����Matrix����׼��ƽ��
		matrix.preTranslate(-mCenterX, -mCenterY);
		// ����ƽ��
		matrix.postTranslate(mCenterX, mCenterY);
	}

	/**
	 * ��ȡĬ�ϵļ��ٲ�ֵ��
	 * 
	 * @return
	 */
	public static Interpolator getDefaultInterpolator() {
		return INTERPOLATOR;
	}

	/**
	 * ���Ĭ�ϵĶ���ʵ��
	 * 
	 * @param container
	 * @return
	 */
	public static Rotate3DAnimation getDefaultInstance(ViewGroup container) {
		Rotate3DAnimation d3 = new Rotate3DAnimation(PRE_FROMDEGREE,
				PRE_TODEGREE, container.getWidth() / 2.0f,
				container.getHeight() / 2.0f, 0.0f, true);
		d3.setDuration(DURATION);
		d3.setFillAfter(FILLAFTER);
		d3.setInterpolator(INTERPOLATOR);
		return d3;
	}

	/**
	 * ����Ĭ�ϵ���ʼ��ת�Ķ���
	 * 
	 * @param container
	 * @return
	 */
	public static Rotate3DAnimation getDefaultPreAnim(ViewGroup container) {
		Rotate3DAnimation d3 = new Rotate3DAnimation(PRE_FROMDEGREE,
				PRE_TODEGREE, container.getWidth() / 2.0f,
				container.getHeight() / 2.0f, 0.0f, true);
		d3.setDuration(DURATION);
		d3.setFillAfter(FILLAFTER);
		d3.setInterpolator(INTERPOLATOR);
		return d3;
	}

	/**
	 * ����Ĭ�ϵĽ�����ת����
	 * 
	 * @param container
	 * @return
	 */
	public static Rotate3DAnimation getDefaultNextAnim(ViewGroup container) {
		Rotate3DAnimation d3 = new Rotate3DAnimation(NEXT_FROMDEGREE,
				NEXT_TODEGREE, container.getWidth() / 2.0f,
				container.getHeight() / 2.0f, 0.0f, true);
		d3.setDuration(DURATION);
		d3.setFillAfter(FILLAFTER);
		d3.setInterpolator(INTERPOLATOR);
		return d3;
	}

	/**
	 * ��̬��������
	 * 
	 * @param container
	 * @param fromDegrees
	 * @param toDegrees
	 * @param duration
	 * @param fillAfter
	 * @param interpolator
	 * @return
	 */
	public static Rotate3DAnimation get3DAnim(ViewGroup container,
			float fromDegrees, float toDegrees, long duration,
			boolean fillAfter, Interpolator interpolator) {
		Rotate3DAnimation d3 = new Rotate3DAnimation(fromDegrees, toDegrees,
				container.getWidth() / 2.0f, container.getHeight() / 2.0f,
				0.0f, true);
		d3.setDuration(duration);
		d3.setFillAfter(fillAfter);
		d3.setInterpolator(interpolator);
		return d3;
	}

	// ��������applyTransformation���������
	@Override
	public boolean getTransformation(long currentTime,
			Transformation outTransformation) {
		return super.getTransformation(currentTime, outTransformation);
	}

	// 3d����ǰ�ĳ�ʼ������
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// �������
		super.initialize(width, height, parentWidth, parentHeight);
		// ��ʼ������ͷ
		mCamera = new Camera();
	}
}