package com.android.trafficmeter;

/**
 *	The class that contains the constants.
 * @author archermind
 *
 */
public class TrafficConst {

	public static final boolean DEBUG =true;
	public static final String TAG ="TrafficMeter";
	public static final String WIFI ="WIFI";
	public static final String MOBILE ="MOBILE";


	public static final String DAY ="DAY";
	public static final String WEEK ="WEEK";
	public static final String MONTH ="MONTH";
	public static final String TOTAL ="TOTAL";
	public static final String YESTERDAY ="YESTERDAY";
	public static final String LAST_WEEK ="LAST WEEK";
	public static final String LAST_MONTH ="LAST MONTH";

	public static final String STARTDAY="start_day";
	public static final String CURRENTDAY="current_day";

	public static final int MENU_GROUP=0;

	public static final int MENU_HELP=1;
	public static final int MENU_ABOUT=2;
	public static final int MENU_REFRESH=3;
	public static final int MENU_HELP_ORDER=1;
	public static final int MENU_ABOUT_ORDER=2;
	public static final int MENU_REFRESH_ORDER=3;

	public static final int DIALOG_MENU_HELP=1;
	public static final int DIALOG_MENU_ABOUT=2;
	public static final int DIALOG_DAY=3;
	public static final int DIALOG_DETAIL=4;
	public static final int DIALOG_COUNTERTYPE=5;

	public static final int CONTEXTMENU_GROUP=0;
	public static final int CONTEXTMENU_COUNTER_DAY=1;
	public static final int CONTEXTMENU_GRAPH=2;
	public static final int CONTEXTMENU_COUNTER_ADD=3;
	public static final int CONTEXTMENU_COUNTER_REMOVE=4;
	public static final int CONTEXTMENU_COUNTER_DAY_ORDER=1;
	public static final int CONTEXTMENU_GRAPH_ORDER=2;
	public static final int CONTEXTMENU_COUNTER_ADD_ORDER=3;
	public static final int CONTEXTMENU_COUNTER_REMOVE_ORDER=4;

	public static final String MOBILEACTIVITY="activity.MobileActivity";
	public static final String WIFIACTIVITY="activity.WifiActivity";


	public static final String INTENT_EXTRA_INTERFACE="interface";
	public static final String INTENT_EXTRA_DATE_TYPE="date_type";
}
