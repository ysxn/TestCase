
package com.example.testgyroscope;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 陀螺仪传感器叫做Gyro-sensor，返回x、y、z三轴的角加速度数据。
 * <p>
 * 角加速度的单位是radians/second。
 * <p>
 * 根据Nexus S手机实测：
 * <p>
 * 水平逆时针旋转，Z轴为正。
 * <p>
 * 水平逆时针旋转，z轴为负。
 * <p>
 * 向左旋转，y轴为负。
 * <p>
 * 向右旋转，y轴为正。
 * <p>
 * 向上旋转，x轴为负。
 * <p>
 * 向下旋转，x轴为正。
 * <p>
 * ST的L3G系列的陀螺仪传感器比较流行，iphone4和google的nexus s中使用该种传感器。
 * 
 * @author zhuyw1
 */
public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGyroSensor;
    private Sensor mAccelerometerSensor;
    private TextView mTextViewGyroName;
    private TextView mTextViewGyroTips;
    private TextView mTextViewGyroData;
    private TextView mTextViewAccelerometerName;
    private TextView mTextViewAccelerometeTips;
    private TextView mTextViewAccelerometerData;
    private ImageView mGyroAnimation;
    private Button mExit;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;
    private final float EPSILON = 10f;

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        /**
         * Sensor.TYPE_GYROSCOPE:
         * <p>
         * All values are in radians/second and measure the rate of rotation
         * around the device's local X, Y and Z axis. The coordinate system is
         * the same as is used for the acceleration sensor. Rotation is positive
         * in the counter-clockwise direction. That is, an observer looking from
         * some positive location on the x, y or z axis at a device positioned
         * on the origin would report positive rotation if the device appeared
         * to be rotating counter clockwise. Note that this is the standard
         * mathematical definition of positive rotation and does not agree with
         * the definition of roll given earlier.
         * <p>
         * values[0]: Angular speed around the x-axis
         * <p>
         * values[1]: Angular speed around the y-axis
         * <p>
         * values[2]: Angular speed around the z-axis
         * <p>
         * Typically the output of the gyroscope is integrated over time to
         * calculate a rotation describing the change of angles over the
         * timestep, for example:
         * <p>
         * In practice, the gyroscope noise and offset will introduce some
         * errors which need to be compensated for. This is usually done using
         * the information from other sensors, but is beyond the scope of this
         * document.
         */
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            float x = Math.abs(event.values[0]) > 0.01f ? event.values[0] : 0f;
            float y = Math.abs(event.values[1]) > 0.01f ? event.values[1] : 0f;
            float z = Math.abs(event.values[2]) > 0.01f ? event.values[2] : 0f;
            mTextViewGyroData.setText("Angular speed around the x-axis:" + x + "\n"
                    + "Angular speed around the y-axis:" + y + "\n"
                    + "Angular speed around the z-axis:" + z + "\n");
            // This timestep's delta rotation to be multiplied by the current
            // rotation
            // after computing it from the gyro sample data.
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                // Axis of the rotation sample, not normalized yet.
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];

                // Calculate the angular speed of the sample
                float omegaMagnitude = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ
                        * axisZ);

                // Normalize the rotation vector if it's big enough to get the
                // axis
                if (omegaMagnitude > EPSILON) {
                    axisX /= omegaMagnitude;
                    axisY /= omegaMagnitude;
                    axisZ /= omegaMagnitude;
                }

                // Integrate around this axis with the angular speed by the
                // timestep
                // in order to get a delta rotation from this sample over the
                // timestep
                // We will convert this axis-angle representation of the delta
                // rotation
                // into a quaternion before turning it into the rotation matrix.
                float thetaOverTwo = omegaMagnitude * dT / 2.0f;
                float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
                float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
                deltaRotationVector[0] = sinThetaOverTwo * axisX;
                deltaRotationVector[1] = sinThetaOverTwo * axisY;
                deltaRotationVector[2] = sinThetaOverTwo * axisZ;
                deltaRotationVector[3] = cosThetaOverTwo;
            }
            timestamp = event.timestamp;
            float[] deltaRotationMatrix = new float[9];
            SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
            // User code should concatenate the delta rotation we computed with
            // the current rotation
            // in order to get the updated rotation.
            // rotationCurrent = rotationCurrent * deltaRotationMatrix;

        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = Math.abs(event.values[0]) > 0.09f ? event.values[0] : 0f;
            float y = Math.abs(event.values[1]) > 0.09f ? event.values[1] : 0f;
            float z = Math.abs(event.values[2]) > 0.09f ? event.values[2] : 0f;
            mTextViewAccelerometerData.setText("Acceleration minus Gx on the x-axis:" + x + "\n"
                    + "Acceleration minus Gy on the y-axis:" + y + "\n"
                    + "Acceleration minus Gz on the z-axis:" + z + "\n");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mGyroSensor == null) {
            Toast.makeText(this, "获取陀螺仪传感器为空！！！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        mTextViewGyroName = (TextView) findViewById(R.id.gyro_sensor_name);
        mTextViewGyroTips = (TextView) findViewById(R.id.gyro_sensor_tips);
        mTextViewGyroData = (TextView) findViewById(R.id.gyro_sensor_data);
        mTextViewGyroName.setText("陀螺仪型号:" + mGyroSensor.getName());
        mTextViewGyroTips.setText("陀螺仪传感器返回x、y、z三轴的角速度数据。单位是radians/second。水平逆时针旋转，Z轴为正。水平逆时针旋转，z轴为负。向左旋转，y轴为负。向右旋转，y轴为正。向上旋转，x轴为负。向下旋转，x轴为正。ST的L3G系列的陀螺仪传感器比较流行.");

        mTextViewAccelerometerName = (TextView) findViewById(R.id.accelerometer_sensor_name);
        mTextViewAccelerometeTips = (TextView) findViewById(R.id.accelerometer_sensor_tips);
        mTextViewAccelerometerData = (TextView) findViewById(R.id.accelerometer_sensor_data);

        mTextViewAccelerometeTips
                .setText("加速度传感器返回x、y、z三轴的加速度数值。该数值包含地心引力的影响，单位是m/s^2。将手机平放在桌面上，x轴默认为0，y轴默认0，z轴默认9.81。将手机屏幕朝下放在桌面上，z轴为-9.81。将手机向左倾斜，x轴为正值。将手机向右倾斜，x轴为负值。将手机向上倾斜，y轴为负值。将手机向下倾斜，y轴为正值。常用的加速度传感器有BOSCH（博世）的BMA系列，AMK的897X系列，ST的LIS3X系列");
        mTextViewAccelerometerName.setText("加速度传感器型号:" + mAccelerometerSensor.getName());

        mGyroAnimation = (ImageView) findViewById(R.id.animation_image);
        mGyroAnimation.setVisibility(View.GONE);
        mExit = (Button) findViewById(R.id.button_exit);
        if (mExit != null) {
            mExit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mSensorManager.unregisterListener(this, mGyroSensor);
        mSensorManager.unregisterListener(this, mAccelerometerSensor);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mSensorManager.registerListener(this, mGyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

}
