
package com.codezyw.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SensorHelper {
    /**
     * frameworks\base\core\res\res\values\config.xml里面这个属性值config_autoBrightnessLevels
     * 对应这个autoBrightnessLcdBacklightValues
     * 代码是这个frameworks\base\services\java\com
     * \android\server\power\DisplayPowerController.java
     * 关系是这个config_autoBrightnessLevels 范围是0~10240，单位是流明。
     * autoBrightnessLcdBacklightValues这个是给LCD背光，范围0~255。 驱动那边的.als_value
     * 正好和config_autoBrightnessLevels 是同一个单位。
     * 
     */
    private static final String TAG = "SensorHelper";
    public final static String HALL_STATE_PATH = "/sys/class/switch/hall/state";

    /**
     * 获取霍尔开关状态，用于手机皮套
     * 
     * @return
     */
    public static boolean getHallState() {
        int mSensorData = 0;
        FileReader file = null;
        try {
            char[] buffer = new char[1024];
            file = new FileReader(HALL_STATE_PATH);
            int len = file.read(buffer, 0, 1024);

            /**
             * FileInputStream.jva
             * <p>
             * BufferedInputStream.java
             * <p>
             * BufferedReader.java
             * <p>
             * InputStreamReader.java 这个显示buffer是乱码，最后有"\n"换行符。
             */
            // Log.i(TAG,
            // ">>>>>read state len=="+len+"  buffer={"+buffer.toString()+"}");
            Log.i(TAG, ">>>>>read state len==" + len + "  buffer={"
                    + new String(buffer, 0, len) + "}");
            mSensorData = Integer.valueOf((new String(buffer, 0, len)).trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "FileNotFoundException！ This kernel does not have hall sensor support");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mSensorData > 0 ? true : false;
    }

    /**
     * 获取sensor属性
     * 
     * @param sensor
     */
    public static void dumpSensorStruct(Sensor sensor) {
        StringBuilder sb = new StringBuilder();
        sb.append("传感器最大测量范围=").append(sensor.getMaximumRange())
                .append("; Resolution=").append(sensor.getResolution())
                .append("; 传感器运行时电流单位mA=").append(sensor.getPower());
    }

    public static SensorEventListener sSensorEventListener = new SensorEventListener() {
        private static final float NS2S = 1.0f / 1000000000.0f;
        private final float[] deltaRotationVector = new float[4];
        private float timestamp;
        private final float EPSILON = 10f;

        @Override
        public void onSensorChanged(SensorEvent event) {
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
             */
            /**
             * Sensor.TYPE_GYROSCOPE:
             * <p>
             * All values are in radians/second and measure the rate of rotation
             * around the device's local X, Y and Z axis. The coordinate system
             * is the same as is used for the acceleration sensor. Rotation is
             * positive in the counter-clockwise direction. That is, an observer
             * looking from some positive location on the x, y or z axis at a
             * device positioned on the origin would report positive rotation if
             * the device appeared to be rotating counter clockwise. Note that
             * this is the standard mathematical definition of positive rotation
             * and does not agree with the definition of roll given earlier.
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
             * errors which need to be compensated for. This is usually done
             * using the information from other sensors, but is beyond the scope
             * of this document.
             */
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float x = Math.abs(event.values[0]) > 0.01f ? event.values[0] : 0f;
                float y = Math.abs(event.values[1]) > 0.01f ? event.values[1] : 0f;
                float z = Math.abs(event.values[2]) > 0.01f ? event.values[2] : 0f;
                StringBuilder sb = new StringBuilder();
                sb.append("Angular speed around the x-axis:").append(x).append("\n")
                        .append("Angular speed around the y-axis:").append(y).append("\n")
                        .append("Angular speed around the z-axis:").append(z).append("\n");
                /**
                 * This timestep's delta rotation to be multiplied by the
                 * current rotation after computing it from the gyro sample
                 * data.
                 */
                if (timestamp != 0) {
                    final float dT = (event.timestamp - timestamp) * NS2S;
                    // Axis of the rotation sample, not normalized yet.
                    float axisX = event.values[0];
                    float axisY = event.values[1];
                    float axisZ = event.values[2];

                    // Calculate the angular speed of the sample
                    float omegaMagnitude = (float) Math.sqrt(axisX * axisX + axisY * axisY + axisZ
                            * axisZ);

                    /**
                     * Normalize the rotation vector if it's big enough to get
                     * the axis
                     */
                    if (omegaMagnitude > EPSILON) {
                        axisX /= omegaMagnitude;
                        axisY /= omegaMagnitude;
                        axisZ /= omegaMagnitude;
                    }

                    /**
                     * Integrate around this axis with the angular speed by the
                     * timestep in order to get a delta rotation from this
                     * sample over the timestep We will convert this axis-angle
                     * representation of the delta rotation into a quaternion
                     * before turning it into the rotation matrix.
                     */
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
                /**
                 * User code should concatenate the delta rotation we computed
                 * with the current rotation in order to get the updated
                 * rotation. rotationCurrent = rotationCurrent *
                 * deltaRotationMatrix;
                 */
            } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                /**
                 * "加速度传感器返回x、y、z三轴的加速度数值。<br>
                 * 该数值包含地心引力的影响，单位是m/s^2。<br>
                 * 将手机平放在桌面上，x轴默认为0，y轴默认0，z轴默认9.81。 <br>
                 * 将手机屏幕朝下放在桌面上 ，z轴为-9.81。<br>
                 * 将手机向左倾斜，x轴为正值。<br>
                 * 将手机向右倾斜，x轴为负值。<br>
                 * 将手机向上倾斜，y轴为负值 。<br>
                 * 将手机向下倾斜，y轴为正值 。<br>
                 * 常用的加速度传感器有BOSCH（博世）的BMA系列，AMK的897X系列，ST的LIS3X系列"
                 */
                float x = Math.abs(event.values[0]) > 0.09f ? event.values[0] : 0f;
                float y = Math.abs(event.values[1]) > 0.09f ? event.values[1] : 0f;
                float z = Math.abs(event.values[2]) > 0.09f ? event.values[2] : 0f;
                StringBuilder sb = new StringBuilder();
                sb.append("Acceleration minus Gx on the x-axis:").append(x).append("\n")
                        .append("Acceleration minus Gy on the y-axis:").append(y).append("\n")
                        .append("Acceleration minus Gz on the z-axis:").append(z).append("\n");
            } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                /**
                 * Sensor.TYPE_LIGHT: values[0]: Ambient light level in SI lux
                 * units
                 * <p>
                 * 光传感器读数 ： 勒克斯（Lux，通常简写为lx）是一个标识照度的国际单位制单位，1流明每平方米面积，就是1勒克斯。
                 * 其单位换算是 1勒克斯 = 1 流明/平方米 = 1 坎德拉·球面度/平方米（1 lx = 1 lm/m2= 1
                 * cd·sr·m–2）。
                 */
                int mAmbientLightLevel = (int) event.values[0];
            } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                /**
                 * Sensor.TYPE_PROXIMITY: values[0]: Proximity sensor distance
                 * measured in centimeters Note: Some proximity sensors only
                 * support a binary near or far measurement. In this case, the
                 * sensor should report its maximum range value in the far state
                 * and a lesser value in the near state.<br>
                 * 接近传感器读数 : 单位一般是厘米
                 */
                int mProximitySensorData = (int) event.values[0];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 注册传感器监听
     * 
     * @param mSensorManager
     * @param listener
     * @param sensor
     */
    public static void registerListener(SensorManager mSensorManager, SensorEventListener listener, Sensor sensor) {
        mSensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 注册传感器监听
     * 
     * @param mSensorManager
     * @param listener
     * @param sensor
     * @param rate
     */
    public static void registerListener(SensorManager mSensorManager, SensorEventListener listener, Sensor sensor, int rate) {
        mSensorManager.registerListener(listener, sensor, rate);
    }

    /**
     * 解除传感器监听
     * 
     * @param mSensorManager
     * @param listener
     * @param sensor
     */
    public static void unregisterListener(SensorManager mSensorManager, SensorEventListener listener, Sensor sensor) {
        mSensorManager.unregisterListener(listener, sensor);
    }

    // Register for updates from each continuous mode sensor, wait for 25
    // events, call flush and
    // wait for flushCompleteEvent before unregistering for the sensor.
    public static void testBatchAndFlush(Context context) throws Exception {
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        final ArrayList<Sensor> mContinuousSensorList = new ArrayList<Sensor>();
        boolean mFlagTest = true;
        for (int i = Sensor.TYPE_ACCELEROMETER; i <= Sensor.TYPE_AMBIENT_TEMPERATURE; ++i) {
            Sensor sensor = mSensorManager.getDefaultSensor(i);
            mContinuousSensorList.add(sensor);
        }
        while (mFlagTest) {
            for (Sensor sensor : mContinuousSensorList) {
                if (!mFlagTest) {
                    return;
                }
                final CountDownLatch eventReceived = new CountDownLatch(25);
                SensorEventListener listener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        Log.i("cts", ">>>>>>>>onSensorChanged");
                        eventReceived.countDown();
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
                };

                boolean result = mSensorManager.registerListener(listener, sensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                Log.e("cts", ">>>>>>>>start 稳定性测试 for sensor=" + sensor.toString());
                if (result) {
                    Log.i("cts", "current registerListener pass");
                } else {
                    Log.i("cts", "current registerListener fail");
                }
                // Wait for 25 events.
                if (!mFlagTest) {
                    mSensorManager.unregisterListener(listener);
                    return;
                }
                eventReceived.await();
                mSensorManager.unregisterListener(listener);
            }
        }
    }

    /**
     * 检查系统是否存在特定硬件功能：sensor，nfc等等
     * 
     * @param context
     * @return
     */
    public static boolean has(Context context, String feature) {
        if (TextUtils.isEmpty(feature)) {
            feature = PackageManager.FEATURE_SENSOR_ACCELEROMETER;
        }
        return context.getPackageManager().hasSystemFeature(feature);
    }

    /**
     * 当前是否自动背光
     * 
     * @param context
     */
    public static boolean checktSystemBrightness(Context context) {
        boolean isAutoBrightness = Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        return isAutoBrightness;
    }

    /**
     * 当前背光亮度 如果是自动背光模式下本数值恒定, 背光亮度(0~255)
     * 
     * @param context
     */
    public static int getBrightness(Context context) {
        int brightness = Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, -1);
        return brightness;
    }
}
