package com.example.testlcdbrightness;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * frameworks\base\core\res\res\values\config.xml里面config_autoBrightnessLevels这个
 * ,还有对应这个autoBrightnessLcdBacklightValues
 * 代码是这个frameworks\base\services\java\com
 * \android\server\power\DisplayPowerController.java
 * 关系是这个config_autoBrightnessLevels 范围是0~10240，单位是流明。
 * autoBrightnessLcdBacklightValues这个是给LCD背光，范围0~255。<br>
 * 驱动那边的.als_value 正好和config_autoBrightnessLevels 是同一个单位。
 * 
 * @author zhuyw1
 */
public class MainActivity extends Activity {

	public static final long GB_IN_BYTES = 1073741824L;
	public static final long KB_IN_BYTES = 1024L;
	public static final long MB_IN_BYTES = 1048576L;
	private static final int REQUEST_UPDATE_DATA = 299;
	private float mAmbientLightLevel = 0.0F;
	private final static String TAG = "zyw";

	private long mCurrentTime = SystemClock.elapsedRealtime();
	private Sensor mGSensor;
	private TextView mTextViewGSensorName;

	private Sensor mLightSensor;

	private long mRxAll = TrafficStats.getTotalRxBytes();
	private SensorManager mSensorManager;
	private TextView mTextViewLightSensorName;
	private TextView mTextViewProximitySensorName;
	private TextView mTextViewProximityData;
	private TextView mTextViewLightSensorData;
	private TextView mTextViewLightMode;
	private EditText mEditText;
	private final static String LCD_PATH = "/sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness";

	// private final IPowerManager mPower;

	private SensorEventListener mLightSensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent paramSensorEvent) {
			/**
			 * 勒克斯（Lux，通常简写为lx）是一个标识照度的国际单位制单位，1流明每平方米面积，就是1勒克斯。 <br>
			 * 其单位换算是 1勒克斯 = 1 流明/平方米 = 1 坎德拉·球面度/平方米（1 lx = 1 lm/m2= 1
			 * cd·sr·m–2）。
			 */
			if (paramSensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
				/**
				 * Sensor.TYPE_LIGHT: values[0]: Ambient light level in SI lux
				 * units
				 */
				mAmbientLightLevel = paramSensorEvent.values[0];
				StringBuilder sb = new StringBuilder();
				mTextViewLightSensorData.setText("光传感器读数 Ambient Light Level : " + mAmbientLightLevel + "勒克斯(单位SI lux units)" + "; MaximumRange="
						+ mLightSensor.getMaximumRange() + "; Resolution=" + mLightSensor.getResolution() + "; the power in mA used by ="
						+ mLightSensor.getPower());
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_UPDATE_DATA: {
				mHandler.removeMessages(REQUEST_UPDATE_DATA);

				if (mTextViewLightMode != null) {
					boolean isAutoBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
							Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

					int brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);
					FileReader file = null;
					char[] buffer = new char[1024];
					int b = -1;
					try {
						/**
						 * FileInputStream.jva
						 * <p>
						 * BufferedInputStream.java
						 * <p>
						 * BufferedReader.java
						 * <p>
						 * InputStreamReader.java 这个显示buffer是乱码，最后有"\n"换行符。
						 */
						file = new FileReader(LCD_PATH);
						int len = file.read(buffer, 0, 1024);
						b = Integer.valueOf((new String(buffer, 0, len)).trim());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						Log.e(TAG, "FileNotFoundException！LCD_PATH= " + LCD_PATH);
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

					mTextViewLightMode.setText("设置->显示->亮度: 当前是否自动背光 = " + isAutoBrightness + ",如果自动调整亮度下本数值无效请忽略" + "\n" + "如果手动调整亮度模式下,当前亮度为(0~255)= "
							+ brightness + "\n" + "应用层设置背光亮度最低有限制为30, 如果设置更低亮度，请这样做：" + "\n" + "第一步执行 adb shell 进入手机系统" + "\n"
							+ "第二步执行 echo 10 > /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness" + "\n" + "上面的数字 10 可以改为0~255之间任意值。" + "\n"
							+ "lcd-backlight/brightness=" + b);

				}
				mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 300L);
				break;
			}
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		mTextViewLightSensorName = ((TextView) findViewById(R.id.light_sensor_name));
		mTextViewLightSensorData = ((TextView) findViewById(R.id.light_data));
		mTextViewLightMode = ((TextView) findViewById(R.id.light_mode));
		mEditText = (EditText) findViewById(R.id.edittext_echo);

		getSystemService(Context.POWER_SERVICE);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		if (mLightSensor == null) {
			mTextViewLightSensorName.setText("没有光传感器!");
		} else {
			mTextViewLightSensorName.setText("光传感器型号:" + mLightSensor.getName());
		}

		Button echo = (Button) findViewById(R.id.button_echo);
		echo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = mEditText.getText().toString();
				int i = -1;
				try {
					i = Integer.valueOf(s);
				} catch (NumberFormatException e) {
					Toast.makeText(MainActivity.this, "数字为空或者非法输入！！！", Toast.LENGTH_LONG).show();
				}
				if (i >= 0 && i <= 255) {
					Toast.makeText(MainActivity.this, "输入有效数字为  i=" + i, Toast.LENGTH_LONG).show();
					String cmd1 = "echo " + i + " > /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness";
					String cmd = "ls -la";
					String suffix = "\n";
					Settings.System.putInt(MainActivity.this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, i);
					FileWriter file = null;
					try {
						file = new FileWriter(LCD_PATH);
						file.write(String.valueOf(i));
						file.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Log.i(TAG, ">>>>>>>>>>write fail!");
					} finally {
						if (file != null) {
							try {
								file.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					try {
						Process process = Runtime.getRuntime().exec("/system/bin/sh");
						OutputStream outputStream = process.getOutputStream();
						DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
						// dataOutputStream.writeBytes("ls -la"+suffix);
						// dataOutputStream.flush();
						dataOutputStream.writeBytes("cd /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/" + suffix);
						dataOutputStream.flush();
						// dataOutputStream.writeBytes("pwd"+suffix);
						// dataOutputStream.flush();
						dataOutputStream.writeBytes("cat brightness" + suffix);
						dataOutputStream.flush();
						dataOutputStream.writeBytes("ls -la" + suffix);
						dataOutputStream.flush();
						dataOutputStream.writeBytes("exit" + suffix);
						dataOutputStream.flush();
						dataOutputStream.close();
						try {
							int r = -1;
							if ((r = process.waitFor()) != 0) {
								Toast.makeText(MainActivity.this, "执行echo失败，请检查权限！return error = " + r, Toast.LENGTH_LONG).show();
							}

							BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
							StringBuffer stringBuffer = new StringBuffer();
							String line = null;
							while ((line = in.readLine()) != null) {
								stringBuffer.append(line + "\n");
							}
							Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>exec result start :" + "\n");
							Log.i(TAG, stringBuffer.toString());
							Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>exec result end :" + "\n");
							in.close();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(MainActivity.this, "InterruptedException 执行echo失败，请检查权限！", Toast.LENGTH_LONG).show();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(MainActivity.this, "IOException 执行echo失败，请检查权限！", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(MainActivity.this, "应用层设置背光亮度最低有限制为30，输入数字为  i=" + i + ", 超过30~255范围, 无效数字.", Toast.LENGTH_LONG).show();
				}

			}
		});

		Button exit = (Button) findViewById(R.id.button_exit);
		if (exit != null) {
			exit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		mHandler.sendEmptyMessageDelayed(REQUEST_UPDATE_DATA, 100L);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(mLightSensorEventListener, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(mLightSensorEventListener, mLightSensor);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
