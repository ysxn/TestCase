
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
 * autoBrightnessLcdBacklightValues这个是给LCD背光，范围0~255。 驱动那边的.als_value
 * 正好和config_autoBrightnessLevels 是同一个单位。
 * 
 * @author zhuyw1
 */
public class MainActivity extends Activity implements SensorEventListener {

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
    private final static String LCD_PATH = "/sdcard/123.txt";//"/sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness";

    //private final IPowerManager mPower;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_UPDATE_DATA: {
                    mHandler.removeMessages(REQUEST_UPDATE_DATA);

                    if (mTextViewLightMode != null) {
                        boolean isAutoBrightness = Settings.System.getInt(
                                getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS_MODE,
                                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

                        int brightness = Settings.System.getInt(
                                getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS, -1);
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
                             * InputStreamReader.java
                             * 这个显示buffer是乱码，最后有"\n"换行符。
                             */
                            file = new FileReader(LCD_PATH);
                            int len = file.read(buffer, 0, 1024);
                            b =Integer.valueOf((new String(buffer, 0, len)).trim());
                        } catch (FileNotFoundException e) {
                            Log.e(TAG,
                                    "FileNotFoundException！ This kernel does not have hall sensor support");
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
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

                        mTextViewLightMode.setText("设置->显示->亮度: 当前是否自动背光 = "
                                + isAutoBrightness + ",如果自动调整亮度下本数值无效请忽略"
                                +"\n"
                                +"如果手动调整亮度模式下,当前亮度为(0~255)= "+ brightness
                                +"\n"
                                +"应用层设置背光亮度最低有限制为30, 如果设置更低亮度，请这样做："
                                +"\n"
                                +"第一步执行 adb shell 进入手机系统" 
                                +"\n"
                                +"第二步执行 echo 10 > /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness"
                                +"\n"
                                +"上面的数字 10 可以改为0~255之间任意值。"
                                +"\n"
                                +"lcd-backlight/brightness="+b);

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
        
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        
        if (mLightSensor == null)
        {
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
                if (i >= 30 && i <= 255) {
                    Toast.makeText(MainActivity.this, "输入有效数字为  i=" + i, Toast.LENGTH_LONG).show();
                    String cmd1 = "echo " + i
                            + " > /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness";
                    String cmd = "ls -la";
                    String suffix = "\n";
                    System.out.println(">>>>>>>>>>dfssssss");
                    //Settings.System.putInt(MainActivity.this.getContentResolver(),
                    //        Settings.System.SCREEN_BRIGHTNESS, i);
                    FileWriter file = null;
                    try {
                        file = new FileWriter(LCD_PATH);
                        file.write(String.valueOf(i));
                        file.flush();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        System.out.println(">>>>>>>>>>write fail!");
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
//                        dataOutputStream.writeBytes("ls -la"+suffix);
//                        dataOutputStream.flush();
                        dataOutputStream.writeBytes("cd /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/"+suffix);
                        dataOutputStream.flush();
//                        dataOutputStream.writeBytes("pwd"+suffix);
//                        dataOutputStream.flush();
                        dataOutputStream.writeBytes("cat brightness"+suffix);
                        dataOutputStream.flush();
                        dataOutputStream.writeBytes("exit"+suffix);
                        dataOutputStream.flush();
                        dataOutputStream.close();
                        try {
                            int r = -1;
                            if ((r = process.waitFor()) != 0) {
                                Toast.makeText(MainActivity.this,
                                        "执行echo失败，请检查权限！return error = " + r, Toast.LENGTH_LONG)
                                        .show();
                            }

                            BufferedReader in = new BufferedReader(new InputStreamReader(
                                    process.getInputStream()));
                            StringBuffer stringBuffer = new StringBuffer();
                            String line = null;
                            while ((line = in.readLine()) != null) {
                                stringBuffer.append(line + "\n");
                            }
                            System.out.println(stringBuffer.toString());
                            in.close();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,
                                    "InterruptedException 执行echo失败，请检查权限！", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "IOException 执行echo失败，请检查权限！",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "应用层设置背光亮度最低有限制为30，输入数字为  i=" + i + ", 超过30~255范围, 无效数字.",
                            Toast.LENGTH_LONG).show();
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
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mSensorManager.unregisterListener(this, mLightSensor);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent paramSensorEvent) {
        // TODO Auto-generated method stub
        /**
         * 勒克斯（Lux，通常简写为lx）是一个标识照度的国际单位制单位，1流明每平方米面积，就是1勒克斯。 其单位换算是 1勒克斯 = 1
         * 流明/平方米 = 1 坎德拉·球面度/平方米（1 lx = 1 lm/m2= 1 cd·sr·m–2）。
         */
        if (paramSensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            mAmbientLightLevel = paramSensorEvent.values[0];
            /**
             * Sensor.TYPE_LIGHT: values[0]: Ambient light level in SI lux units
             */
            mTextViewLightSensorData.setText("光传感器读数 Ambient Light Level : "
                    + mAmbientLightLevel + "勒克斯(单位SI lux units)"
                    + "; MaximumRange=" + mLightSensor.getMaximumRange() + "; Resolution="
                    + mLightSensor.getResolution() + "; the power in mA used by ="
                    + mLightSensor.getPower());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    /**
     * Android permission 访问权限大全
     * <p>
     * 程序执行需要读取到安全敏感项必需在androidmanifest.xml中声明相关权限请求, 完整列表如下:
     * <p>
     * android.permission.ACCESS_CHECKIN_PROPERTIES
     * <p>
     * 允许读写访问”properties”表在 checkin数据库中，改值可以修改上传( Allows read/write access to
     * the “properties” table in the checkin database, to change values that get
     * uploaded)
     * <p>
     * android.permission.ACCESS_COARSE_LOCATION
     * <p>
     * 允许一个程序访问CellID或WiFi热点来获取粗略的位置(Allows an application to access coarse
     * (e.g., Cell-ID, WiFi) location)
     * <p>
     * android.permission.ACCESS_FINE_LOCATION
     * <p>
     * 允许一个程序访问精良位置(如GPS) (Allows an application to access fine (e.g., GPS)
     * location)
     * <p>
     * android.permission.ACCESS_LOCATION_EXTRA_COMMANDS
     * <p>
     * 允许应用程序访问额外的位置提供命令(Allows an application to access extra location provider
     * commands)
     * <p>
     * android.permission.ACCESS_MOCK_LOCATION
     * <p>
     * 允许程序创建模拟位置提供用于测试(Allows an application to create mock location providers
     * for testing)
     * <p>
     * android.permission.ACCESS_NETWORK_STATE
     * <p>
     * 允许程序访问有关GSM网络信息(Allows applications to access information about networks)
     * <p>
     * android.permission.ACCESS_SURFACE_FLINGER
     * <p>
     * 允许程序使用SurfaceFlinger底层特性 (Allows an application to use SurfaceFlinger’s
     * low level features)
     * <p>
     * android.permission.ACCESS_WIFI_STATE
     * <p>
     * 允许程序访问Wi-Fi网络状态信息(Allows applications to access information about Wi-Fi
     * networks)
     * <p>
     * android.permission.ADD_SYSTEM_SERVICE
     * <p>
     * 允许程序发布系统级服务(Allows an application to publish system-level services).
     * <p>
     * android.permission.BATTERY_STATS
     * <p>
     * 允许程序更新手机电池统计信息(Allows an application to update the collected battery
     * statistics)
     * <p>
     * android.permission.BLUETOOTH
     * <p>
     * 允许程序连接到已配对的蓝牙设备(Allows applications to connect to paired bluetooth
     * devices)
     * <p>
     * android.permission.BLUETOOTH_ADMIN
     * <p>
     * 允许程序发现和配对蓝牙设备(Allows applications to discover and pair bluetooth devices)
     * <p>
     * android.permission.BRICK
     * <p>
     * 请求能够禁用设备(非常危险)(Required to be able to disable the device (very *erous!).)
     * <p>
     * android.permission.BROADCAST_PACKAGE_REMOVED
     * <p>
     * 允许程序广播一个提示消息在一个应用程序包已经移除后(Allows an application to broadcast a
     * notification that an application package has been removed)
     * <p>
     * android.permission.BROADCAST_STICKY
     * <p>
     * 允许一个程序广播常用intents(Allows an application to broadcast sticky intents)
     * <p>
     * android.permission.CALL_PHONE
     * <p>
     * 允许一个程序初始化一个电话拨号不需通过拨号用户界面需要用户确认 (Allows an application to initiate a
     * phone call without going through the Dialer user interface for the user
     * to confirm the call being placed.)
     * <p>
     * android.permission.CALL_PRIVILEGED
     * <p>
     * 允许一个程序拨打任何号码，包含紧急号码无需通过拨号用户界面需要用户确认 (Allows an application to call any
     * phone number, including emergency numbers, without going through the
     * Dialer user interface for the user to confirm the call being placed)
     * <p>
     * android.permission.CAMERA
     * <p>
     * 请求访问使用照相设备(Required to be able to access the camera device. )
     * <p>
     * android.permission.CHANGE_COMPONENT_ENABLED_STATE
     * <p>
     * 允许一个程序是否改变一个组件或其他的启用或禁用(Allows an application to change whether an
     * application component (other than its own) is enabled or not. )
     * <p>
     * android.permission.CHANGE_CONFIGURATION
     * <p>
     * 允许一个程序修改当前设置，如本地化(Allows an application to modify the current
     * configuration, such as locale. )
     * <p>
     * android.permission.CHANGE_NETWORK_STATE
     * <p>
     * 允许程序改变网络连接状态(Allows applications to change network connectivity state)
     * <p>
     * android.permission.CHANGE_WIFI_STATE
     * <p>
     * 允许程序改变Wi-Fi连接状态(Allows applications to change Wi-Fi connectivity state)
     * <p>
     * android.permission.CLEAR_APP_CACHE
     * <p>
     * 允许一个程序清楚缓存从所有安装的程序在设备中(Allows an application to clear the caches of all
     * installed applications on the device. )
     * <p>
     * android.permission.CLEAR_APP_USER_DATA
     * <p>
     * 允许一个程序清除用户设置(Allows an application to clear user data)
     * <p>
     * android.permission.CONTROL_LOCATION_UPDATES
     * <p>
     * 允许启用禁止位置更新提示从无线模块 (Allows enabling/disabling location update
     * notifications from the radio. )
     * <p>
     * android.permission.DELETE_CACHE_FILES
     * <p>
     * 允许程序删除缓存文件(Allows an application to delete cache files)
     * <p>
     * android.permission.DELETE_PACKAGES
     * <p>
     * 允许一个程序删除包(Allows an application to delete packages)
     * <p>
     * android.permission.DEVICE_POWER
     * <p>
     * 允许访问底层电源管理(Allows low-level access to power management)
     * <p>
     * android.permission.DIAGNOSTIC
     * <p>
     * 允许程序RW诊断资源(Allows applications to RW to diagnostic resources. )
     * <p>
     * android.permission.DISABLE_KEYGUARD
     * <p>
     * 允许程序禁用键盘锁(Allows applications to disable the keyguard )
     * <p>
     * android.permission.DUMP
     * <p>
     * 允许程序返回状态抓取信息从系统服务(Allows an application to retrieve state dump
     * information from system services.)
     * <p>
     * android.permission.EXPAND_STATUS_BAR
     * <p>
     * 允许一个程序扩展收缩在状态栏,android开发网提示应该是一个类似Windows Mobile中的托盘程序(Allows an
     * application to expand or collapse the status bar. )
     * <p>
     * android.permission.FACTORY_TEST
     * <p>
     * 作为一个工厂测试程序，运行在root用户(Run as a manufacturer test application, running as
     * the root user. )
     * <p>
     * android.permission.FLASHLIGHT
     * <p>
     * 访问闪光灯,android开发网提示HTC Dream不包含闪光灯(Allows access to the flashlight )
     * <p>
     * android.permission.FORCE_BACK
     * <p>
     * 允许程序强行一个后退操作是否在顶层activities(Allows an application to force a BACK
     * operation on whatever is the top activity. )
     * <p>
     * android.permission.FOTA_UPDATE
     * <p>
     * 暂时不了解这是做什么使用的，android开发网分析可能是一个预留权限.
     * <p>
     * android.permission.GET_ACCOUNTS
     * <p>
     * 访问一个帐户列表在Accounts Service中(Allows access to the list of accounts in the
     * Accounts Service)
     * <p>
     * android.permission.GET_PACKAGE_SIZE
     * <p>
     * 允许一个程序获取任何package占用空间容量(Allows an application to find out the space used
     * by any package. )
     * <p>
     * android.permission.GET_TASKS
     * <p>
     * 允许一个程序获取信息有关当前或最近运行的任务，一个缩略的任务状态，是否活动等等(Allows an application to get
     * information about the currently or recently running tasks: a thumbnail
     * representation of the tasks, what activities are running in it, etc.)
     * <p>
     * android.permission.HARDWARE_TEST
     * <p>
     * 允许访问硬件(Allows access to hardware peripherals. )
     * <p>
     * android.permission.INJECT_EVENTS
     * <p>
     * 允许一个程序截获用户事件如按键、触摸、轨迹球等等到一个时间流，android 开发网提醒算是hook技术吧(Allows an
     * application to inject user events (keys, touch, trackball) into the event
     * stream and deliver them to ANY window.)
     * <p>
     * android.permission.INSTALL_PACKAGES
     * <p>
     * 允许一个程序安装packages(Allows an application to install packages. )
     * <p>
     * android.permission.INTERNAL_SYSTEM_WINDOW
     * <p>
     * 允许打开窗口使用系统用户界面(Allows an application to open windows that are for use by
     * parts of the system user interface. )
     * <p>
     * android.permission.INTERNET
     * <p>
     * 允许程序打开网络套接字(Allows applications to open network sockets)
     * <p>
     * android.permission.MANAGE_APP_TOKENS
     * <p>
     * 允许程序管理(创建、催后、 z- order默认向z轴推移)程序引用在窗口管理器中(Allows an application to manage
     * (create, destroy, Z-order) application tokens in the window manager. )
     * <p>
     * android.permission.MASTER_CLEAR目前还没有明确的解释，android开发网分析可能是清除一切数据，类似硬格机
     * <p>
     * android.permission.MODIFY_AUDIO_SETTINGS
     * <p>
     * 允许程序修改全局音频设置(Allows an application to modify global audio settings)
     * <p>
     * android.permission.MODIFY_PHONE_STATE
     * <p>
     * 允许修改话机状态，如电源，人机接口等(Allows modification of the telephony state ? power on,
     * mmi, etc. )
     * <p>
     * android.permission.MOUNT_UNMOUNT_FILESYSTEMS
     * <p>
     * 允许挂载和反挂载文件系统可移动存储 (Allows mounting and unmounting file systems for
     * removable storage. )
     * <p>
     * android.permission.PERSISTENT_ACTIVITY
     * <p>
     * 允许一个程序设置他的activities显示 (Allow an application to make its activities
     * persistent. )
     * <p>
     * android.permission.PROCESS_OUTGOING_CALLS
     * <p>
     * 允许程序监视、修改有关播出电话(Allows an application to monitor, modify, or abort
     * outgoing calls)
     * <p>
     * android.permission.READ_CALENDAR
     * <p>
     * 允许程序读取用户日历数据(Allows an application to read the user’s calendar data.)
     * <p>
     * android.permission.READ_CONTACTS
     * <p>
     * 允许程序读取用户联系人数据(Allows an application to read the user’s contacts data.)
     * <p>
     * android.permission.READ_FRAME_BUFFER
     * <p>
     * 允许程序屏幕波或和更多常规的访问帧缓冲数据(Allows an application to take screen shots and more
     * generally get access to the frame buffer data)
     * <p>
     * android.permission.READ_INPUT_STATE
     * <p>
     * 允许程序返回当前按键状态(Allows an application to retrieve the current state of keys
     * and switches. )
     * <p>
     * android.permission.READ_LOGS
     * <p>
     * 允许程序读取底层系统日志文件(Allows an application to read the low-level system log
     * files. )
     * <p>
     * android.permission.READ_OWNER_DATA
     * <p>
     * 允许程序读取所有者数据(Allows an application to read the owner’s data)
     * <p>
     * android.permission.READ_SMS
     * <p>
     * 允许程序读取短信息(Allows an application to read SMS messages.)
     * <p>
     * android.permission.READ_SYNC_SETTINGS
     * <p>
     * 允许程序读取同步设置(Allows applications to read the sync settings)
     * <p>
     * android.permission.READ_SYNC_STATS
     * <p>
     * 允许程序读取同步状态(Allows applications to read the sync stats)
     * <p>
     * android.permission.REBOOT
     * <p>
     * 请求能够重新启动设备(Required to be able to reboot the device. )
     * <p>
     * android.permission.RECEIVE_BOOT_COMPLETED
     * <p>
     * 允许一个程序接收到 ACTION_BOOT_COMPLETED广播在系统完成启动(Allows an application to receive
     * the ACTION_BOOT_COMPLETED that is broadcast after the system finishes
     * booting. )
     * <p>
     * android.permission.RECEIVE_MMS
     * <p>
     * 允许一个程序监控将收到MMS彩信,记录或处理(Allows an application to monitor incoming MMS
     * messages, to record or perform processing on them. )
     * <p>
     * android.permission.RECEIVE_SMS
     * <p>
     * 允许程序监控一个将收到短信息，记录或处理(Allows an application to monitor incoming SMS
     * messages, to record or perform processing on them.)
     * <p>
     * android.permission.RECEIVE_WAP_PUSH
     * <p>
     * 允许程序监控将收到WAP PUSH信息(Allows an application to monitor incoming WAP push
     * messages. )
     * <p>
     * android.permission.RECORD_AUDIO
     * <p>
     * 允许程序录制音频(Allows an application to record audio)
     * <p>
     * android.permission.REORDER_TASKS
     * <p>
     * 允许程序改变Z轴排列任务(Allows an application to change the Z-order of tasks)
     * <p>
     * android.permission.RESTART_PACKAGES
     * <p>
     * 允许程序重新启动其他程序(Allows an application to restart other applications)
     * <p>
     * android.permission.SEND_SMS
     * <p>
     * 允许程序发送SMS短信(Allows an application to send SMS messages)
     * <p>
     * android.permission.SET_ACTIVITY_WATCHER
     * <p>
     * 允许程序监控或控制activities已经启动全局系统中Allows an application to watch and control
     * how activities are started globally in the system.
     * <p>
     * android.permission.SET_ALWAYS_FINISH
     * <p>
     * 允许程序控制是否活动间接完成在处于后台时Allows an application to control whether activities
     * are immediately finished when put in the background.
     * <p>
     * android.permission.SET_ANIMATION_SCALE
     * <p>
     * 修改全局信息比例(Modify the global animation scaling factor.)
     * <p>
     * android.permission.SET_DEBUG_APP
     * <p>
     * 配置一个程序用于调试(Configure an application for debugging.)
     * <p>
     * android.permission.SET_ORIENTATION
     * <p>
     * 允许底层访问设置屏幕方向和实际旋转(Allows low-level access to setting the orientation
     * <p>
     * (actually rotation) of the screen.)
     * <p>
     * android.permission.SET_PREFERRED_APPLICATIONS
     * <p>
     * 允许一个程序修改列表参数 PackageManager.addPackageToPreferred()
     * 和PackageManager.removePackageFromPreferred()方法(Allows an application to
     * modify the list of preferred applications with the
     * PackageManager.addPackageToPreferred() and
     * PackageManager.removePackageFromPreferred() methods.)
     * <p>
     * android.permission.SET_PROCESS_FOREGROUND
     * <p>
     * 允许程序当前运行程序强行到前台(Allows an application to force any currently running
     * process to be in the foreground.)
     * <p>
     * android.permission.SET_PROCESS_LIMIT
     * <p>
     * 允许设置最大的运行进程数量(Allows an application to set the maximum number of (not
     * needed) application processes that can be running. )
     * <p>
     * android.permission.SET_TIME_ZONE
     * <p>
     * 允许程序设置时间区域(Allows applications to set the system time zone)
     * <p>
     * android.permission.SET_WALLPAPER
     * <p>
     * 允许程序设置壁纸(Allows applications to set the wallpaper )
     * <p>
     * android.permission.SET_WALLPAPER_HINTS
     * <p>
     * 允许程序设置壁纸hits(Allows applications to set the wallpaper hints)
     * <p>
     * android.permission.SIGNAL_PERSISTENT_PROCESSES
     * <p>
     * 允许程序请求发送信号到所有显示的进程中 (Allow an application to request that a signal be
     * sent to all persistent processes)
     * <p>
     * android.permission.STATUS_BAR
     * <p>
     * 允许程序打开、关闭或禁用状态栏及图标Allows an application to open, close, or disable the
     * status bar and its icons.
     * <p>
     * android.permission.SUBSCRIBED_FEEDS_READ
     * <p>
     * 允许一个程序访问订阅RSS Feed内容提供(Allows an application to allow access the
     * subscribed feeds ContentProvider. )
     * <p>
     * android.permission.SUBSCRIBED_FEEDS_WRITE
     * <p>
     * 系统暂时保留改设置,android开发网认为未来版本会加入该功能。
     * <p>
     * android.permission.SYSTEM_ALERT_WINDOW
     * <p>
     * 允许一个程序打开窗口使用 TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层(Allows an application to open
     * <p>
     * windows using the type TYPE_SYSTEM_ALERT, shown on top of all other
     * applications. )
     * <p>
     * android.permission.VIBRATE
     * <p>
     * 允许访问振动设备(Allows access to the vibrator)
     * <p>
     * android.permission.WAKE_LOCK
     * <p>
     * 允许使用PowerManager的 WakeLocks保持进程在休眠时从屏幕消失( Allows using PowerManager
     * WakeLocks to keep processor from sleeping or screen from dimming)
     * <p>
     * android.permission.WRITE_APN_SETTINGS
     * <p>
     * 允许程序写入API设置(Allows applications to write the apn settings)
     * <p>
     * android.permission.WRITE_CALENDAR
     * <p>
     * 允许一个程序写入但不读取用户日历数据(Allows an application to write (but not read) the
     * user’s calendar data. )
     * <p>
     * android.permission.WRITE_CONTACTS
     * <p>
     * 允许程序写入但不读取用户联系人数据(Allows an application to write (but not read) the
     * user’s contacts data. )
     * <p>
     * android.permission.WRITE_GSERVICES
     * <p>
     * 允许程序修改Google服务地图(Allows an application to modify the Google service map.
     * )
     * <p>
     * android.permission.WRITE_OWNER_DATA
     * <p>
     * 允许一个程序写入但不读取所有者数据(Allows an application to write (but not read) the
     * owner’s data.)
     * <p>
     * android.permission.WRITE_SETTINGS
     * <p>
     * 允许程序读取或写入系统设置(Allows an application to read or write the system settings.
     * )
     * <p>
     * android.permission.WRITE_SMS
     * <p>
     * 允许程序写短信(Allows an application to write SMS messages)
     * <p>
     * android.permission.WRITE_SYNC_SETTINGS
     * <p>
     * 允许程序写入同步设置(Allows applications to write the sync settings)
     * <p>
     * android平台上的权限许可分得很细，如果软件无法正常执行时看看是不是缺少相关的permission声明，最终我们还需要使用 android
     * sign tools签名生成的apk文件。
     */
}
