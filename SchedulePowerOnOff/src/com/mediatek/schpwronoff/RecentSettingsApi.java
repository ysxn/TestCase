package com.mediatek.schpwronoff;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * 近期设置对外接口
 * @author huangzb1
 *
 */
public class RecentSettingsApi {
	private static final String TAG = "RecentSettingsApi";
	
	private static final String	AUTHORITY	= "com.lenovo.settings.provider.RecentSettingSyncProvider";
	private static final Uri		CONTENT_URI_CITY			= Uri.parse("content://" + AUTHORITY + "/sync");
	/**
	 * 统计的类型--统计点击次数
	 */
	private static final int ITEM_TYPE_CLICK = 0;
	/**
	 * 统计的类型--统计修改设置次数
	 */
	private static final int ITEM_TYPE_SET = 1;
	/**
	 * 设置项统计类型，
	 */
	private static final String ITEM_TYPE = "itemType";
	/*设置项title的字符串id*/
	private static final String TITLE_STRING_ID = "titleStringId";
	/*设置项title字符串id所在包名*/
	private static final String TITLE_STRING_PACKAGE_NAME = "titleStringPackageName";
	/*设置项所在screen title字符串id*/
	private static final String SCREEN_TITLE_STRING_ID = "screenTitleStringId";
	/*设置项fragment的类名*/
	private static final String FRAGMENT_CLASS_NAME = "fragmentClassName";
	/*启动设置项的action*/
	private static final String INTENT_ACTION = "intentAction";
	/*设置项所在的包名*/
	private static final String INTENT_TARGET_PACKAGE = "intentTargetPackage";
	/*设置项所在的 Activity 名*/
	private static final String INTENT_TARGET_CLASS = "intentTargetClass";
	
	/**
	 * 修改设置项值时调用
	 * @param context
	 * @param titleStringId 设置项title的字符串id
	 * @param titleStringPackageName 设置项title字符串id所在包名
	 * @param screenTitleStringId 设置项所在activity title字符串id
	 * @param fragmentClassName 设置项fragment的类名
	 * @param intentAction 启动设置项的action
	 * @param intentTargetPackage 设置项所在的包名
	 * @param intentTargetClass 设置项所在的 Activity 名
	 */
	public static void addChangValueAction(Context context, int titleStringId,
			String titleStringPackageName, 
			int screenTitleStringId,
			String fragmentClassName,
			String intentAction, String intentTargetPackage,
			String intentTargetClass) {
		long time = System.currentTimeMillis();
		try {
			ContentValues values = new ContentValues();
			values.put(ITEM_TYPE, ITEM_TYPE_SET);
			values.put(TITLE_STRING_ID, titleStringId);
			values.put(TITLE_STRING_PACKAGE_NAME, titleStringPackageName);
			values.put(SCREEN_TITLE_STRING_ID, screenTitleStringId);
			values.put(FRAGMENT_CLASS_NAME, fragmentClassName);
			values.put(INTENT_ACTION, intentAction);
			values.put(INTENT_TARGET_PACKAGE, intentTargetPackage);
			values.put(INTENT_TARGET_CLASS, intentTargetClass);
			context.getContentResolver().update(CONTENT_URI_CITY, values, null,
					null);
		}catch(Exception e){
			Log.d(TAG, "addChangValueAction e "+e.getMessage());
		}
        time = System.currentTimeMillis() - time;
        Log.d(TAG, "addChangValueAction time = "+time);
	}

	/**
	 * 浏览设置项时调用
	 * @param context
	 * @param titleStringId 设置项title的字符串id
	 * @param titleStringPackageName 设置项title字符串id所在包名
	 * @param screenTitleStringId 设置项所在activity title字符串id
	 * @param fragmentClassName 设置项fragment的类名
	 * @param intentAction 启动设置项的action
	 * @param intentTargetPackage 设置项所在的包名
	 * @param intentTargetClass 设置项所在的 Activity 名
	 */
	public static void addShowInfoAction(Context context, int titleStringId,
			String titleStringPackageName, 
			int screenTitleStringId,
			String fragmentClassName,
			String intentAction, String intentTargetPackage,
			String intentTargetClass) {
		long time = System.currentTimeMillis();
		try {
			ContentValues values = new ContentValues();
			values.put(ITEM_TYPE, ITEM_TYPE_CLICK);
			values.put(TITLE_STRING_ID, titleStringId);
			values.put(TITLE_STRING_PACKAGE_NAME, titleStringPackageName);
			values.put(SCREEN_TITLE_STRING_ID, screenTitleStringId);
			values.put(FRAGMENT_CLASS_NAME, fragmentClassName);
			values.put(INTENT_ACTION, intentAction);
			values.put(INTENT_TARGET_PACKAGE, intentTargetPackage);
			values.put(INTENT_TARGET_CLASS, intentTargetClass);
			context.getContentResolver().update(CONTENT_URI_CITY, values, null,
					null);
		}catch(Exception e){
			Log.d(TAG, "addShowInfoAction e "+e.getMessage());
		}
        time = System.currentTimeMillis() - time;
        Log.d(TAG, "addShowInfoAction time = "+time);
	}
}
