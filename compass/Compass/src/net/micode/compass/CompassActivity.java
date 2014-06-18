/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.micode.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class CompassActivity extends Activity {

    private String TAG = "zyw";
    private final float MAX_ROATE_DEGREE = 1.0f;
    private SensorManager mSensorManager;
    private Sensor mOrientationSensor;
    private LocationManager mLocationManager;
    private String mLocationProvider;
    private float mDirection;
    private float mTargetDirection;
    private AccelerateInterpolator mInterpolator;
    protected final Handler mHandler = new Handler();
    private boolean mStopDrawing;
    private boolean mChinease;
    private TextView mOrientationTipsText;
    private TextView mOrientationSensorData;

    View mCompassView;
    CompassView mPointer;
    TextView mLocationTextView;

    protected Runnable mCompassViewUpdater = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, ">>>>>>>>>>>update view mTargetDirection=" + mTargetDirection
                    + ", mDirection=" + mDirection + ", mStopDrawing=" + mStopDrawing);
            if (mPointer != null && !mStopDrawing) {
                if (mDirection != mTargetDirection) {

                    // calculate the short routine
                    float to = mTargetDirection;
                    if (to - mDirection > 180) {
                        to -= 360;
                    } else if (to - mDirection < -180) {
                        to += 360;
                    }

                    // limit the max speed to MAX_ROTATE_DEGREE
                    float distance = to - mDirection;
                    if (Math.abs(distance) > MAX_ROATE_DEGREE) {
                        distance = distance > 0 ? MAX_ROATE_DEGREE : (-1.0f * MAX_ROATE_DEGREE);
                    }

                    // need to slow down if the distance is short
                    mDirection = normalizeDegree(mDirection
                            + ((to - mDirection) * mInterpolator.getInterpolation(Math
                                    .abs(distance) > MAX_ROATE_DEGREE ? 0.4f : 0.3f)));
                    mDirection = mTargetDirection;
                    mPointer.updateDirection(mDirection);
                }

                updateDirection();

                mHandler.postDelayed(mCompassViewUpdater, 20);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initResources();
        initServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationProvider != null) {
            updateLocation(mLocationManager.getLastKnownLocation(mLocationProvider));
            mLocationManager.requestLocationUpdates(mLocationProvider, 2000, 10, mLocationListener);
        } else {
            mLocationTextView.setText(R.string.cannot_get_location);
        }
        if (mOrientationSensor != null) {
            mSensorManager.registerListener(mOrientationSensorEventListener, mOrientationSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
        mStopDrawing = false;
        Log.i(TAG, ">>>>>>>>>>onResume mStopDrawing = false");
        mHandler.postDelayed(mCompassViewUpdater, 20);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStopDrawing = true;
        Log.i(TAG, ">>>>>>>>>>onPause mStopDrawing = true");
        if (mOrientationSensor != null) {
            mSensorManager.unregisterListener(mOrientationSensorEventListener);
        }
        if (mLocationProvider != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    private void initResources() {
        mDirection = 0.0f;
        mTargetDirection = 0.0f;
        mInterpolator = new AccelerateInterpolator();
        mStopDrawing = true;
        Log.i(TAG, ">>>>>>>>>>initResources mStopDrawing = true");
        mChinease = TextUtils.equals(Locale.getDefault().getLanguage(), "zh");

        mCompassView = findViewById(R.id.view_compass);
        mPointer = (CompassView) findViewById(R.id.compass_pointer);
        mLocationTextView = (TextView) findViewById(R.id.textview_location);
        mOrientationTipsText = (TextView) findViewById(R.id.orientation_sensor_name);
        mOrientationSensorData = (TextView) findViewById(R.id.orientation_sensor_data);

        mPointer.setImageResource(mChinease ? R.drawable.compass_cn : R.drawable.compass);
    }

    private void initServices() {
        // sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        // location manager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        mLocationProvider = mLocationManager.getBestProvider(criteria, true);

    }

    private void updateDirection() {
        mOrientationSensorData.setText("当前方向角度:"+mTargetDirection);

    }

    private void updateLocation(Location location) {
        if (location == null) {
            mLocationTextView.setText(R.string.getting_location);
        } else {
            StringBuilder sb = new StringBuilder();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            if (latitude >= 0.0f) {
                sb.append(getString(R.string.location_north, getLocationString(latitude)));
            } else {
                sb.append(getString(R.string.location_south, getLocationString(-1.0 * latitude)));
            }

            sb.append("    ");

            if (longitude >= 0.0f) {
                sb.append(getString(R.string.location_east, getLocationString(longitude)));
            } else {
                sb.append(getString(R.string.location_west, getLocationString(-1.0 * longitude)));
            }

            mLocationTextView.setText(sb.toString());
        }
    }

    private String getLocationString(double input) {
        int du = (int) input;
        int fen = (((int) ((input - du) * 3600))) / 60;
        int miao = (((int) ((input - du) * 3600))) % 60;
        return String.valueOf(du) + "度" + String.valueOf(fen) + "'" + String.valueOf(miao) + "\"";
    }

    /**
     * Sensor.TYPE_ORIENTATION: All values are angles in degrees. values[0]:
     * Azimuth, angle between the magnetic north direction and the y-axis,
     * around the z-axis (0 to 359). 0=North, 90=East, 180=South, 270=West
     * values[1]: Pitch, rotation around x-axis (-180 to 180), with positive
     * values when the z-axis moves toward the y-axis. values[2]: Roll, rotation
     * around the x-axis (-90 to 90) increasing as the device moves clockwise.
     * Note: This definition is different from yaw, pitch and roll used in
     * aviation where the X axis is along the long side of the plane (tail to
     * nose). Note: This sensor type exists for legacy reasons, please use
     * getRotationMatrix() in conjunction with remapCoordinateSystem() and
     * getOrientation() to compute these values instead. Important note: For
     * historical reasons the roll angle is positive in the clockwise direction
     * (mathematically speaking, it should be positive in the counter-clockwise
     * direction).
     */
    private SensorEventListener mOrientationSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float direction = event.values[0];
            Log.i(TAG, ">>>>>>>>>>>>onSensorChanged before normalizeDegree direction = {"
                    + direction + "}");
            mTargetDirection = normalizeDegree(direction);
            Log.i(TAG, ">>>>>>>>>>>>onSensorChanged after  normalizeDegree mTargetDirection = {"
                    + mTargetDirection + "}");
            mOrientationTipsText
                    .setText("磁力传感器简称为M-sensor，返回x、y、z三轴的环境磁场数据.磁力数据由电子罗盘传感器提供（E-compass）。电子罗盘传感器同时提供下文的方向传感器数据。方向传感器简称为O-sensor，返回三轴的角度数据，方向数据的单位是角度。为了得到精确的角度数据，E-compass需要获取G-sensor的数据，经过计算生产O-sensor数据，否则只能获取水平方向的角度。方向传感器型号:"
                            + mOrientationSensor.getName()
                            + ";\n" + "azimuth 方位角：就是绕z轴转动的角度，0度=正北:"
                            + event.values[0]
                            + ",\n" + "pitch 仰俯：绕X轴转动的角度 (-180<=pitch<=180), 如果设备水平放置，前方向下俯就是正:"
                            + event.values[1]
                            + ",\n" + "roll 滚转：绕Y轴转动(-90<=roll<=90)，向左翻滚是正值:" + event.values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private float normalizeDegree(float degree) {
        return (degree + 720) % 360;
    }

    LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (status != LocationProvider.OUT_OF_SERVICE) {
                updateLocation(mLocationManager.getLastKnownLocation(mLocationProvider));
            } else {
                mLocationTextView.setText(R.string.cannot_get_location);
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

    };
}
