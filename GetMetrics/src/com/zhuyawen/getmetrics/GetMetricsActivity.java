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
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class GetMetricsActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		Log.i("zhuyawen", dm.toString());
		Log.i("zhuyawen", ">>>>>>>>>>>>>>>>>" + ", metrics"
				+ this.getResources().getDisplayMetrics().toString()
				+ " , screenDensity = "
				+ this.getResources().getDisplayMetrics().densityDpi);
		Toast.makeText(
				this,
				">>>>>>>>>>>>>>>>>" + ", metrics"
						+ this.getResources().getDisplayMetrics().toString()
						+ " , screenDensity = "
						+ this.getResources().getDisplayMetrics().densityDpi,
				Toast.LENGTH_LONG).show();
		String s = "xxx";
		Log.i("zhuyawen", "before test s=" + s);
		Log.i("zhuyawen", "after test s=" + s);
		EditText v = new EditText(this);
		v.setText(dm.toString());
//		new AlertDialog.Builder(this).setTitle("DisplayMetrics").setView(v)
//				.create().show();
		
		
		
		
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