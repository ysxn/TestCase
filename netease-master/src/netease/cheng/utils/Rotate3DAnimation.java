package netease.cheng.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;
/**
 * 3d��������תԭ��---ʹ��һ���������Χ��Ŀ�����㣻
 * Ȼ�󽫹��̼�¼��Matrix�����У�֮��ֱ�Ӳ������Matrix������ʵ��3d��Ч
 * @author Administrator
 *
 */
public class Rotate3DAnimation extends Animation {
	//�������
	private Camera mCamera;
	//X��������
	private final float mCenterX;
	//Y��������
	private final float mCenterY;
	//����ת�����
	private final float mDepthZ;
	//��ʼ�Ƕ�
	private final float mFromDegrees;
	//�����Ƕ�
	private final float mToDegrees;
	//�Ƿ�������ʾ����
	private final boolean mReverse;

	public Rotate3DAnimation(float fromDegrees, float toDegrees,
			float centerX, float centerY, float depthz,boolean bool) {
		this.mFromDegrees = fromDegrees;
		this.mToDegrees = toDegrees;
		this.mCenterX = centerX;
		this.mCenterY = centerY;
		this.mDepthZ = depthz;
		this.mReverse = bool;
	}
	//�ڳ�ʼ���������ú󱻵���
	protected void applyTransformation(float time,
			Transformation tran) {
		//time��һ��ϵͳ����ʱ�䣻ʱ������С�����ϵͳĬ�ϵ���תʱ�䶼�ܶ�
		Matrix matrix = tran.getMatrix();
		//�µ�ת��
		float newDegrees = mFromDegrees + time * (mToDegrees - mFromDegrees);
		mCamera.save();//��תǰ���浱ǰ����
		if (!this.mReverse){
			mCamera.translate(0.0F, 0.0F, this.mDepthZ
					* (1.0F - time));
		}
		else{
			mCamera.translate(0.0F, 0.0F,time * this.mDepthZ);
		}
		//����Y����ת�¼���ĽǶ�
		mCamera.rotateY(newDegrees);
		//���ݾ�����󵽵ײ㣻��õײ�matrix��ʵ��
		mCamera.getMatrix(matrix);
		//������ָ�ԭ״
		mCamera.restore();
		//����Matrix����׼��ƽ��
		matrix.preTranslate(-mCenterX, -mCenterY);
		//����ƽ��
		matrix.postTranslate(mCenterX, mCenterY);
	}
	//��������applyTransformation���������
	public boolean getTransformation(long currentTime,
			Transformation outTransformation) {
		return super.getTransformation(currentTime, outTransformation);
	}
	//3d����ǰ�ĳ�ʼ������
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		//�������
		super.initialize(width, height, parentWidth, parentHeight);
		//��ʼ������ͷ
		mCamera=new Camera();
	}
}