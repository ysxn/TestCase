
package com.codezyw.common;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.apache.commons.codec.binary.Base64;

public class DeviceHelper {
    private static DeviceHelper sDeviceHelper;
    private Context mContext;
    private boolean mFakeOpenWifi = false;
    private TelephonyManager mTelephonyMgr;
    private WifiManager mWifiManager;

    private NetworkConnectChangedReceiver receiver = new NetworkConnectChangedReceiver();

    public DeviceHelper() {
    }

    public static DeviceHelper getInstance() {
        if (sDeviceHelper == null) {
            synchronized (DeviceHelper.class) {
                if (sDeviceHelper == null) {
                    sDeviceHelper = new DeviceHelper();
                }
            }
        }
        return sDeviceHelper;
    }

    public void setContext(Context context) {
        mContext = context;
        mTelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取IMSI
     * 
     * @return
     */
    public String getIMSI() {
        String imsi = mTelephonyMgr.getSubscriberId();
        return imsi;
    }

    /**
     * 获取IMEI
     * 
     * @return
     */
    public String getIMEI() {
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    /**
     * 获取手机串号
     * 
     * @return
     */
    public static String getSerialNumberBuild() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取Google账号对应的Android Id
     * 
     * @return
     */
    public String getAndroidId() {
        return android.provider.Settings.Secure.getString(mContext.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    /**
     * 为了获取MAC尝试打开WiFi
     * 
     * @return
     */
    public boolean tryOpenMAC() {
        boolean softOpenWifi = false;
        int state = mWifiManager.getWifiState();
        if (state != WifiManager.WIFI_STATE_ENABLED && state != WifiManager.WIFI_STATE_ENABLING) {
            mWifiManager.setWifiEnabled(true);
            softOpenWifi = true;
        }
        return softOpenWifi;
    }

    /**
     * 如果前面为了获取MAC打开了WiFi，现在关闭WiFi
     * 
     * @return
     */
    public void tryCloseMAC() {
        mWifiManager.setWifiEnabled(false);
    }

    /**
     * 尝试读取MAC地址
     * 
     * @return
     */
    public String tryGetMAC() {
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * 尝试读取MAC地址,如果读不到，需要打开WiFi
     * 
     * @return
     */
    public String getMacFromDevice() {
        String mac = null;
        mac = tryGetMAC();
        if (!isNull(mac)) {
            return mac;
        }
        // 获取失败，尝试打开wifi获取
        mFakeOpenWifi = tryOpenMAC();
        return mac;
    }

    public boolean isNull(String s) {
        if (s == null || s.isEmpty() || "NULL".equalsIgnoreCase(s)) {
            return true;
        }
        return false;
    }

    /**
     * 尝试读取MAC地址,如果读不到，需要打开WiFi 这个监听wifi的打开与关闭，与wifi的连接无关
     */
    public class NetworkConnectChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        String mac = tryGetMAC();
                        // mMacAddress.setText("本机MAC地址" + " " + mac);
                        // 尝试关闭wifi
                        if (mFakeOpenWifi) {
                            tryCloseMAC();
                            mFakeOpenWifi = false;
                        }
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        break;
                }
            }
        }
    }

    private static byte[] key = new byte[] {
            'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd', '.', '.', '.', '.', '.', '.'
    };

    public static byte[] encrypt(String data) {
        byte[] encrypted = null;
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(key);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, zeroIv);
            encrypted = cipher.doFinal(data.getBytes("utf-8"));
            encrypted = Base64.encodeBase64(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
        SecureRandom sr = null;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        kgen.init(128, sr); // 256 bits or 128 bits,192bits
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
}
