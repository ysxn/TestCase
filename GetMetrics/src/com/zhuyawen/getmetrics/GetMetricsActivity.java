package com.zhuyawen.getmetrics;

import java.io.UnsupportedEncodingException;
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

import org.apache.commons.codec.digest.DigestUtils;

import com.apache.commons.codec.binary.Base64;

import junit.framework.Test;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.app.AlertDialog;
import android.net.wifi.WifiManager;
import android.os.*;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GetMetricsActivity extends Activity {
	private TelephonyManager mTelephonyMgr;
	private WifiManager mWifiManager;
	private TextView mIMSI;
	private TextView mIMEI;
	private TextView mMacAddress;
	private TextView mSerialno;
	private TextView mGoogle;
	private TextView mGetMetrics;
	private NetworkConnectChangedReceiver receiver = new NetworkConnectChangedReceiver();
	private boolean mFakeOpenWifi = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mIMSI = (TextView)findViewById(R.id.get_imsi);
		mIMEI = (TextView)findViewById(R.id.get_imei);
		mMacAddress = (TextView)findViewById(R.id.get_mac);
		mSerialno = (TextView)findViewById(R.id.get_serialno);
		mGoogle = (TextView)findViewById(R.id.get_google);
		mGetMetrics = (TextView)findViewById(R.id.get_metrics);
		
		mIMSI.setText(mIMSI.getText()+" "+getIMSI());
		mIMEI.setText(mIMEI.getText()+" "+getIMEI());
		mMacAddress.setText(mMacAddress.getText()+" "+getMacFromDevice());
		mSerialno.setText(mSerialno.getText()+" "+getSerialNumberBuild());
		mGoogle.setText(mGoogle.getText()+" "+getAndroidId());
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		this.registerReceiver(receiver, filter);

		
		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		Log.i("GUH", dm.toString());
		mGetMetrics.setText(dm.toString());

		
		
		
		String data = "hello world!";
		byte[] r = encrypt(data);
		String result = "--";
		result = new String(r);
		Log.i("zzz", "result=" + result);
		result = result.replace('+', '*');
		result = result.replace('/', '-');
		result = result.replace('=', '.');
		Log.i("zzz", "result2=" + result);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	/**
	 * ��ȡIMSI
	 * @return
	 */
	private String getIMSI() {
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		return imsi;
	}
	
	/**
	 * ��ȡIMEI
	 * @return
	 */
	private String getIMEI() {
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		return imei;
	}
	
	/**
	 * ��ȡ�ֻ�����
	 * @return
	 */
	public static String getSerialNumberBuild() {
	    return android.os.Build.SERIAL;
	}

	/**
	 * ��ȡGoogle�˺Ŷ�Ӧ��Android Id
	 * @return
	 */
	public String getAndroidId() {
	    return android.provider.Settings.Secure.getString(getContentResolver(),
	    		android.provider.Settings.Secure.ANDROID_ID);
	}

	/**
	 * Ϊ�˻�ȡMAC���Դ�WiFi
	 * @return
	 */
	private boolean tryOpenMAC() {
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		boolean softOpenWifi = false;
		int state = mWifiManager.getWifiState();
		if (state != WifiManager.WIFI_STATE_ENABLED && state != WifiManager.WIFI_STATE_ENABLING) {
			mWifiManager.setWifiEnabled(true);
			softOpenWifi = true;
		}
		return softOpenWifi;
	}

	/**
	 * ���ǰ��Ϊ�˻�ȡMAC����WiFi�����ڹر�WiFi
	 * @return
	 */
	private void tryCloseMAC() {
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		mWifiManager.setWifiEnabled(false);
	}

	/**
	 * ���Զ�ȡMAC��ַ
	 * @return
	 */
	private String tryGetMAC() {
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}

	/**
	 * ���Զ�ȡMAC��ַ,�������������Ҫ��WiFi
	 * @return
	 */
	private String getMacFromDevice() {
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		String mac = null;
		mac = tryGetMAC();
		if (!isNull(mac)) {
			return mac;
		}
		// ��ȡʧ�ܣ����Դ�wifi��ȡ
		mFakeOpenWifi = tryOpenMAC();
		return mac;
	}
	
	private boolean isNull(String s) {
		if (s == null || s.isEmpty() || "NULL".equalsIgnoreCase(s)) {
			return true;
		}
		return false;
	}

	/**
	 * ���Զ�ȡMAC��ַ,�������������Ҫ��WiFi
	 * �������wifi�Ĵ���رգ���wifi�������޹�
	 *
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
					mMacAddress.setText("����MAC��ַ"+" "+mac);
					// ���Թر�wifi
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
	
	private static byte[] key = new byte[] {'h','e','l','l','o','w','o','r','l','d','.','.','.','.','.','.'};

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
        int len = hexString.length()/2;      
        byte[] result = new byte[len];      
        for (int i = 0; i < len; i++)      
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();      
        return result;      
    }      
     
    public static String toHex(byte[] buf) {      
        if (buf == null)      
            return "";      
        StringBuffer result = new StringBuffer(2*buf.length);      
        for (int i = 0; i < buf.length; i++) {      
            appendHex(result, buf[i]);      
        }      
        return result.toString();      
    }      
    private final static String HEX = "0123456789ABCDEF";      
    private static void appendHex(StringBuffer sb, byte b) {      
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));      
    }
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// SHA1PRNG ǿ��������㷨, Ҫ����4.2���ϰ汾�ĵ��÷���
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