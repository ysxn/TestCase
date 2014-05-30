
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
 * frameworks\base\core\res\res\values\config.xml����config_autoBrightnessLevels���
 * ,���ж�Ӧ���autoBrightnessLcdBacklightValues
 * ���������frameworks\base\services\java\com
 * \android\server\power\DisplayPowerController.java
 * ��ϵ�����config_autoBrightnessLevels ��Χ��0~10240����λ��������
 * autoBrightnessLcdBacklightValues����Ǹ�LCD���⣬��Χ0~255�� �����Ǳߵ�.als_value
 * ���ú�config_autoBrightnessLevels ��ͬһ����λ��
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
                             * �����ʾbuffer�����룬�����"\n"���з���
                             */
                            file = new FileReader(LCD_PATH);
                            int len = file.read(buffer, 0, 1024);
                            b =Integer.valueOf((new String(buffer, 0, len)).trim());
                        } catch (FileNotFoundException e) {
                            Log.e(TAG,
                                    "FileNotFoundException�� This kernel does not have hall sensor support");
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

                        mTextViewLightMode.setText("����->��ʾ->����: ��ǰ�Ƿ��Զ����� = "
                                + isAutoBrightness + ",����Զ����������±���ֵ��Ч�����"
                                +"\n"
                                +"����ֶ���������ģʽ��,��ǰ����Ϊ(0~255)= "+ brightness
                                +"\n"
                                +"Ӧ�ò����ñ����������������Ϊ30, ������ø������ȣ�����������"
                                +"\n"
                                +"��һ��ִ�� adb shell �����ֻ�ϵͳ" 
                                +"\n"
                                +"�ڶ���ִ�� echo 10 > /sys/devices/platform/leds-mt65xx/leds/lcd-backlight/brightness"
                                +"\n"
                                +"��������� 10 ���Ը�Ϊ0~255֮������ֵ��"
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
            mTextViewLightSensorName.setText("û�й⴫����!");
        } else {
            mTextViewLightSensorName.setText("�⴫�����ͺ�:" + mLightSensor.getName());
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
                    Toast.makeText(MainActivity.this, "����Ϊ�ջ��߷Ƿ����룡����", Toast.LENGTH_LONG).show();
                }
                if (i >= 30 && i <= 255) {
                    Toast.makeText(MainActivity.this, "������Ч����Ϊ  i=" + i, Toast.LENGTH_LONG).show();
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
                                        "ִ��echoʧ�ܣ�����Ȩ�ޣ�return error = " + r, Toast.LENGTH_LONG)
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
                                    "InterruptedException ִ��echoʧ�ܣ�����Ȩ�ޣ�", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "IOException ִ��echoʧ�ܣ�����Ȩ�ޣ�",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Ӧ�ò����ñ����������������Ϊ30����������Ϊ  i=" + i + ", ����30~255��Χ, ��Ч����.",
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
         * �տ�˹��Lux��ͨ����дΪlx����һ����ʶ�նȵĹ��ʵ�λ�Ƶ�λ��1����ÿƽ�������������1�տ�˹�� �䵥λ������ 1�տ�˹ = 1
         * ����/ƽ���� = 1 �������������/ƽ���ף�1 lx = 1 lm/m2= 1 cd��sr��m�C2����
         */
        if (paramSensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            mAmbientLightLevel = paramSensorEvent.values[0];
            /**
             * Sensor.TYPE_LIGHT: values[0]: Ambient light level in SI lux units
             */
            mTextViewLightSensorData.setText("�⴫�������� Ambient Light Level : "
                    + mAmbientLightLevel + "�տ�˹(��λSI lux units)"
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
     * Android permission ����Ȩ�޴�ȫ
     * <p>
     * ����ִ����Ҫ��ȡ����ȫ�����������androidmanifest.xml���������Ȩ������, �����б�����:
     * <p>
     * android.permission.ACCESS_CHECKIN_PROPERTIES
     * <p>
     * �����д���ʡ�properties������ checkin���ݿ��У���ֵ�����޸��ϴ�( Allows read/write access to
     * the ��properties�� table in the checkin database, to change values that get
     * uploaded)
     * <p>
     * android.permission.ACCESS_COARSE_LOCATION
     * <p>
     * ����һ���������CellID��WiFi�ȵ�����ȡ���Ե�λ��(Allows an application to access coarse
     * (e.g., Cell-ID, WiFi) location)
     * <p>
     * android.permission.ACCESS_FINE_LOCATION
     * <p>
     * ����һ��������ʾ���λ��(��GPS) (Allows an application to access fine (e.g., GPS)
     * location)
     * <p>
     * android.permission.ACCESS_LOCATION_EXTRA_COMMANDS
     * <p>
     * ����Ӧ�ó�����ʶ����λ���ṩ����(Allows an application to access extra location provider
     * commands)
     * <p>
     * android.permission.ACCESS_MOCK_LOCATION
     * <p>
     * ������򴴽�ģ��λ���ṩ���ڲ���(Allows an application to create mock location providers
     * for testing)
     * <p>
     * android.permission.ACCESS_NETWORK_STATE
     * <p>
     * �����������й�GSM������Ϣ(Allows applications to access information about networks)
     * <p>
     * android.permission.ACCESS_SURFACE_FLINGER
     * <p>
     * �������ʹ��SurfaceFlinger�ײ����� (Allows an application to use SurfaceFlinger��s
     * low level features)
     * <p>
     * android.permission.ACCESS_WIFI_STATE
     * <p>
     * ����������Wi-Fi����״̬��Ϣ(Allows applications to access information about Wi-Fi
     * networks)
     * <p>
     * android.permission.ADD_SYSTEM_SERVICE
     * <p>
     * ������򷢲�ϵͳ������(Allows an application to publish system-level services).
     * <p>
     * android.permission.BATTERY_STATS
     * <p>
     * �����������ֻ����ͳ����Ϣ(Allows an application to update the collected battery
     * statistics)
     * <p>
     * android.permission.BLUETOOTH
     * <p>
     * ����������ӵ�����Ե������豸(Allows applications to connect to paired bluetooth
     * devices)
     * <p>
     * android.permission.BLUETOOTH_ADMIN
     * <p>
     * ��������ֺ���������豸(Allows applications to discover and pair bluetooth devices)
     * <p>
     * android.permission.BRICK
     * <p>
     * �����ܹ������豸(�ǳ�Σ��)(Required to be able to disable the device (very *erous!).)
     * <p>
     * android.permission.BROADCAST_PACKAGE_REMOVED
     * <p>
     * �������㲥һ����ʾ��Ϣ��һ��Ӧ�ó�����Ѿ��Ƴ���(Allows an application to broadcast a
     * notification that an application package has been removed)
     * <p>
     * android.permission.BROADCAST_STICKY
     * <p>
     * ����һ������㲥����intents(Allows an application to broadcast sticky intents)
     * <p>
     * android.permission.CALL_PHONE
     * <p>
     * ����һ�������ʼ��һ���绰���Ų���ͨ�������û�������Ҫ�û�ȷ�� (Allows an application to initiate a
     * phone call without going through the Dialer user interface for the user
     * to confirm the call being placed.)
     * <p>
     * android.permission.CALL_PRIVILEGED
     * <p>
     * ����һ�����򲦴��κκ��룬����������������ͨ�������û�������Ҫ�û�ȷ�� (Allows an application to call any
     * phone number, including emergency numbers, without going through the
     * Dialer user interface for the user to confirm the call being placed)
     * <p>
     * android.permission.CAMERA
     * <p>
     * �������ʹ�������豸(Required to be able to access the camera device. )
     * <p>
     * android.permission.CHANGE_COMPONENT_ENABLED_STATE
     * <p>
     * ����һ�������Ƿ�ı�һ����������������û����(Allows an application to change whether an
     * application component (other than its own) is enabled or not. )
     * <p>
     * android.permission.CHANGE_CONFIGURATION
     * <p>
     * ����һ�������޸ĵ�ǰ���ã��籾�ػ�(Allows an application to modify the current
     * configuration, such as locale. )
     * <p>
     * android.permission.CHANGE_NETWORK_STATE
     * <p>
     * �������ı���������״̬(Allows applications to change network connectivity state)
     * <p>
     * android.permission.CHANGE_WIFI_STATE
     * <p>
     * �������ı�Wi-Fi����״̬(Allows applications to change Wi-Fi connectivity state)
     * <p>
     * android.permission.CLEAR_APP_CACHE
     * <p>
     * ����һ�����������������а�װ�ĳ������豸��(Allows an application to clear the caches of all
     * installed applications on the device. )
     * <p>
     * android.permission.CLEAR_APP_USER_DATA
     * <p>
     * ����һ����������û�����(Allows an application to clear user data)
     * <p>
     * android.permission.CONTROL_LOCATION_UPDATES
     * <p>
     * �������ý�ֹλ�ø�����ʾ������ģ�� (Allows enabling/disabling location update
     * notifications from the radio. )
     * <p>
     * android.permission.DELETE_CACHE_FILES
     * <p>
     * �������ɾ�������ļ�(Allows an application to delete cache files)
     * <p>
     * android.permission.DELETE_PACKAGES
     * <p>
     * ����һ������ɾ����(Allows an application to delete packages)
     * <p>
     * android.permission.DEVICE_POWER
     * <p>
     * ������ʵײ��Դ����(Allows low-level access to power management)
     * <p>
     * android.permission.DIAGNOSTIC
     * <p>
     * �������RW�����Դ(Allows applications to RW to diagnostic resources. )
     * <p>
     * android.permission.DISABLE_KEYGUARD
     * <p>
     * ���������ü�����(Allows applications to disable the keyguard )
     * <p>
     * android.permission.DUMP
     * <p>
     * ������򷵻�״̬ץȡ��Ϣ��ϵͳ����(Allows an application to retrieve state dump
     * information from system services.)
     * <p>
     * android.permission.EXPAND_STATUS_BAR
     * <p>
     * ����һ��������չ������״̬��,android��������ʾӦ����һ������Windows Mobile�е����̳���(Allows an
     * application to expand or collapse the status bar. )
     * <p>
     * android.permission.FACTORY_TEST
     * <p>
     * ��Ϊһ���������Գ���������root�û�(Run as a manufacturer test application, running as
     * the root user. )
     * <p>
     * android.permission.FLASHLIGHT
     * <p>
     * ���������,android��������ʾHTC Dream�����������(Allows access to the flashlight )
     * <p>
     * android.permission.FORCE_BACK
     * <p>
     * �������ǿ��һ�����˲����Ƿ��ڶ���activities(Allows an application to force a BACK
     * operation on whatever is the top activity. )
     * <p>
     * android.permission.FOTA_UPDATE
     * <p>
     * ��ʱ���˽�������ʲôʹ�õģ�android����������������һ��Ԥ��Ȩ��.
     * <p>
     * android.permission.GET_ACCOUNTS
     * <p>
     * ����һ���ʻ��б���Accounts Service��(Allows access to the list of accounts in the
     * Accounts Service)
     * <p>
     * android.permission.GET_PACKAGE_SIZE
     * <p>
     * ����һ�������ȡ�κ�packageռ�ÿռ�����(Allows an application to find out the space used
     * by any package. )
     * <p>
     * android.permission.GET_TASKS
     * <p>
     * ����һ�������ȡ��Ϣ�йص�ǰ��������е�����һ�����Ե�����״̬���Ƿ��ȵ�(Allows an application to get
     * information about the currently or recently running tasks: a thumbnail
     * representation of the tasks, what activities are running in it, etc.)
     * <p>
     * android.permission.HARDWARE_TEST
     * <p>
     * �������Ӳ��(Allows access to hardware peripherals. )
     * <p>
     * android.permission.INJECT_EVENTS
     * <p>
     * ����һ������ػ��û��¼��簴�����������켣��ȵȵ�һ��ʱ������android ��������������hook������(Allows an
     * application to inject user events (keys, touch, trackball) into the event
     * stream and deliver them to ANY window.)
     * <p>
     * android.permission.INSTALL_PACKAGES
     * <p>
     * ����һ������װpackages(Allows an application to install packages. )
     * <p>
     * android.permission.INTERNAL_SYSTEM_WINDOW
     * <p>
     * ����򿪴���ʹ��ϵͳ�û�����(Allows an application to open windows that are for use by
     * parts of the system user interface. )
     * <p>
     * android.permission.INTERNET
     * <p>
     * �������������׽���(Allows applications to open network sockets)
     * <p>
     * android.permission.MANAGE_APP_TOKENS
     * <p>
     * ����������(�������ߺ� z- orderĬ����z������)���������ڴ��ڹ�������(Allows an application to manage
     * (create, destroy, Z-order) application tokens in the window manager. )
     * <p>
     * android.permission.MASTER_CLEARĿǰ��û����ȷ�Ľ��ͣ�android�������������������һ�����ݣ�����Ӳ���
     * <p>
     * android.permission.MODIFY_AUDIO_SETTINGS
     * <p>
     * ��������޸�ȫ����Ƶ����(Allows an application to modify global audio settings)
     * <p>
     * android.permission.MODIFY_PHONE_STATE
     * <p>
     * �����޸Ļ���״̬�����Դ���˻��ӿڵ�(Allows modification of the telephony state ? power on,
     * mmi, etc. )
     * <p>
     * android.permission.MOUNT_UNMOUNT_FILESYSTEMS
     * <p>
     * ������غͷ������ļ�ϵͳ���ƶ��洢 (Allows mounting and unmounting file systems for
     * removable storage. )
     * <p>
     * android.permission.PERSISTENT_ACTIVITY
     * <p>
     * ����һ��������������activities��ʾ (Allow an application to make its activities
     * persistent. )
     * <p>
     * android.permission.PROCESS_OUTGOING_CALLS
     * <p>
     * ���������ӡ��޸��йز����绰(Allows an application to monitor, modify, or abort
     * outgoing calls)
     * <p>
     * android.permission.READ_CALENDAR
     * <p>
     * ��������ȡ�û���������(Allows an application to read the user��s calendar data.)
     * <p>
     * android.permission.READ_CONTACTS
     * <p>
     * ��������ȡ�û���ϵ������(Allows an application to read the user��s contacts data.)
     * <p>
     * android.permission.READ_FRAME_BUFFER
     * <p>
     * ���������Ļ����͸��ೣ��ķ���֡��������(Allows an application to take screen shots and more
     * generally get access to the frame buffer data)
     * <p>
     * android.permission.READ_INPUT_STATE
     * <p>
     * ������򷵻ص�ǰ����״̬(Allows an application to retrieve the current state of keys
     * and switches. )
     * <p>
     * android.permission.READ_LOGS
     * <p>
     * ��������ȡ�ײ�ϵͳ��־�ļ�(Allows an application to read the low-level system log
     * files. )
     * <p>
     * android.permission.READ_OWNER_DATA
     * <p>
     * ��������ȡ����������(Allows an application to read the owner��s data)
     * <p>
     * android.permission.READ_SMS
     * <p>
     * ��������ȡ����Ϣ(Allows an application to read SMS messages.)
     * <p>
     * android.permission.READ_SYNC_SETTINGS
     * <p>
     * ��������ȡͬ������(Allows applications to read the sync settings)
     * <p>
     * android.permission.READ_SYNC_STATS
     * <p>
     * ��������ȡͬ��״̬(Allows applications to read the sync stats)
     * <p>
     * android.permission.REBOOT
     * <p>
     * �����ܹ����������豸(Required to be able to reboot the device. )
     * <p>
     * android.permission.RECEIVE_BOOT_COMPLETED
     * <p>
     * ����һ��������յ� ACTION_BOOT_COMPLETED�㲥��ϵͳ�������(Allows an application to receive
     * the ACTION_BOOT_COMPLETED that is broadcast after the system finishes
     * booting. )
     * <p>
     * android.permission.RECEIVE_MMS
     * <p>
     * ����һ�������ؽ��յ�MMS����,��¼����(Allows an application to monitor incoming MMS
     * messages, to record or perform processing on them. )
     * <p>
     * android.permission.RECEIVE_SMS
     * <p>
     * ���������һ�����յ�����Ϣ����¼����(Allows an application to monitor incoming SMS
     * messages, to record or perform processing on them.)
     * <p>
     * android.permission.RECEIVE_WAP_PUSH
     * <p>
     * ��������ؽ��յ�WAP PUSH��Ϣ(Allows an application to monitor incoming WAP push
     * messages. )
     * <p>
     * android.permission.RECORD_AUDIO
     * <p>
     * �������¼����Ƶ(Allows an application to record audio)
     * <p>
     * android.permission.REORDER_TASKS
     * <p>
     * �������ı�Z����������(Allows an application to change the Z-order of tasks)
     * <p>
     * android.permission.RESTART_PACKAGES
     * <p>
     * �����������������������(Allows an application to restart other applications)
     * <p>
     * android.permission.SEND_SMS
     * <p>
     * ���������SMS����(Allows an application to send SMS messages)
     * <p>
     * android.permission.SET_ACTIVITY_WATCHER
     * <p>
     * ��������ػ����activities�Ѿ�����ȫ��ϵͳ��Allows an application to watch and control
     * how activities are started globally in the system.
     * <p>
     * android.permission.SET_ALWAYS_FINISH
     * <p>
     * �����������Ƿ��������ڴ��ں�̨ʱAllows an application to control whether activities
     * are immediately finished when put in the background.
     * <p>
     * android.permission.SET_ANIMATION_SCALE
     * <p>
     * �޸�ȫ����Ϣ����(Modify the global animation scaling factor.)
     * <p>
     * android.permission.SET_DEBUG_APP
     * <p>
     * ����һ���������ڵ���(Configure an application for debugging.)
     * <p>
     * android.permission.SET_ORIENTATION
     * <p>
     * ����ײ����������Ļ�����ʵ����ת(Allows low-level access to setting the orientation
     * <p>
     * (actually rotation) of the screen.)
     * <p>
     * android.permission.SET_PREFERRED_APPLICATIONS
     * <p>
     * ����һ�������޸��б���� PackageManager.addPackageToPreferred()
     * ��PackageManager.removePackageFromPreferred()����(Allows an application to
     * modify the list of preferred applications with the
     * PackageManager.addPackageToPreferred() and
     * PackageManager.removePackageFromPreferred() methods.)
     * <p>
     * android.permission.SET_PROCESS_FOREGROUND
     * <p>
     * �������ǰ���г���ǿ�е�ǰ̨(Allows an application to force any currently running
     * process to be in the foreground.)
     * <p>
     * android.permission.SET_PROCESS_LIMIT
     * <p>
     * ���������������н�������(Allows an application to set the maximum number of (not
     * needed) application processes that can be running. )
     * <p>
     * android.permission.SET_TIME_ZONE
     * <p>
     * �����������ʱ������(Allows applications to set the system time zone)
     * <p>
     * android.permission.SET_WALLPAPER
     * <p>
     * ����������ñ�ֽ(Allows applications to set the wallpaper )
     * <p>
     * android.permission.SET_WALLPAPER_HINTS
     * <p>
     * ����������ñ�ֽhits(Allows applications to set the wallpaper hints)
     * <p>
     * android.permission.SIGNAL_PERSISTENT_PROCESSES
     * <p>
     * ��������������źŵ�������ʾ�Ľ����� (Allow an application to request that a signal be
     * sent to all persistent processes)
     * <p>
     * android.permission.STATUS_BAR
     * <p>
     * �������򿪡��رջ����״̬����ͼ��Allows an application to open, close, or disable the
     * status bar and its icons.
     * <p>
     * android.permission.SUBSCRIBED_FEEDS_READ
     * <p>
     * ����һ��������ʶ���RSS Feed�����ṩ(Allows an application to allow access the
     * subscribed feeds ContentProvider. )
     * <p>
     * android.permission.SUBSCRIBED_FEEDS_WRITE
     * <p>
     * ϵͳ��ʱ����������,android��������Ϊδ���汾�����ù��ܡ�
     * <p>
     * android.permission.SYSTEM_ALERT_WINDOW
     * <p>
     * ����һ������򿪴���ʹ�� TYPE_SYSTEM_ALERT����ʾ���������г���Ķ���(Allows an application to open
     * <p>
     * windows using the type TYPE_SYSTEM_ALERT, shown on top of all other
     * applications. )
     * <p>
     * android.permission.VIBRATE
     * <p>
     * ����������豸(Allows access to the vibrator)
     * <p>
     * android.permission.WAKE_LOCK
     * <p>
     * ����ʹ��PowerManager�� WakeLocks���ֽ���������ʱ����Ļ��ʧ( Allows using PowerManager
     * WakeLocks to keep processor from sleeping or screen from dimming)
     * <p>
     * android.permission.WRITE_APN_SETTINGS
     * <p>
     * �������д��API����(Allows applications to write the apn settings)
     * <p>
     * android.permission.WRITE_CALENDAR
     * <p>
     * ����һ������д�뵫����ȡ�û���������(Allows an application to write (but not read) the
     * user��s calendar data. )
     * <p>
     * android.permission.WRITE_CONTACTS
     * <p>
     * �������д�뵫����ȡ�û���ϵ������(Allows an application to write (but not read) the
     * user��s contacts data. )
     * <p>
     * android.permission.WRITE_GSERVICES
     * <p>
     * ��������޸�Google�����ͼ(Allows an application to modify the Google service map.
     * )
     * <p>
     * android.permission.WRITE_OWNER_DATA
     * <p>
     * ����һ������д�뵫����ȡ����������(Allows an application to write (but not read) the
     * owner��s data.)
     * <p>
     * android.permission.WRITE_SETTINGS
     * <p>
     * ��������ȡ��д��ϵͳ����(Allows an application to read or write the system settings.
     * )
     * <p>
     * android.permission.WRITE_SMS
     * <p>
     * �������д����(Allows an application to write SMS messages)
     * <p>
     * android.permission.WRITE_SYNC_SETTINGS
     * <p>
     * �������д��ͬ������(Allows applications to write the sync settings)
     * <p>
     * androidƽ̨�ϵ�Ȩ����ɷֵú�ϸ���������޷�����ִ��ʱ�����ǲ���ȱ����ص�permission�������������ǻ���Ҫʹ�� android
     * sign toolsǩ�����ɵ�apk�ļ���
     */
}
