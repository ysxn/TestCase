package com.example.test_webview_demo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.test_webview_demo.utils.FirstLoadingX5Service;
import com.example.test_webview_demo.utils.TraceTracker;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.VideoActivity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.QbSdk.PreInitCallback;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	public static boolean firstOpening=true;

	
	// TODO: See init() for initialization!
	// TODO: Title -- Add other items here in strings.xml
	private static String[] titles   = null;
	
	/**
	 *{@value TIPS_SHOW the handler msg to show tips}
	 */
	private static final int TIPS_SHOW=0;
	private static final int POP_WINDOW_SHOW=1;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//add constant here
	private static final int FILE_CHOOSER=0;
	private static final int FULL_SCREEN_VIDEO=1;
	private static final int TBS_COOKIE=2;
	private static final int JAVA_TO_JS=3;
	private static final int TBS_WEB=4;
	private static final int TBS_VIDEO=5;
	private static final int TBS_IMAGE=6;
	private static final int TBS_DB=7;
	private static final int TBS_FIRST_X5=8;
	private static final int TBS_NEW_WINDOW=9;
	private static final int SYS_WEB=10;
	private static final int TBS_FLASH=11;
	private static final int TBS_WEB_NOTICE=12;
	private static final int TBS_BUILDING=13;
	private static final int TBS_LONG_PRESS=14;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	//for view init
	private Context mContext = null;
	private SimpleAdapter gridAdapter;
	private GridView gridView;
	private ArrayList<HashMap<String, Object>> items ;


	
	private static boolean main_initialized = false;
	
	private volatile boolean isX5WebViewEnabled = false;
	
	Handler handler;

	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//Activity OnCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_advanced);
		mContext = this;				
		if (!main_initialized) {
			this.new_init();
		}


//		preinitX5WithService();//此方法必须在非主进程执行才会有效果
		
		
	}
	

	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//Activity OnResume
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		this.new_init();
		
		//this.gridView=(GridView) this.findViewById(R.id.item_grid);
		this.gridView.setAdapter(gridAdapter);
		super.onResume();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////
	//initiate new UI content
	private void new_init(){
		
		Button bt_setting=(Button) findViewById(R.id.bt_setting);
		bt_setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.openOptionsMenu();
			}
		});
	
		Button bt_help=(Button) findViewById(R.id.bt_help);
		bt_help.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		items = new ArrayList<HashMap<String, Object>>();
		this.gridView=(GridView) this.findViewById(R.id.item_grid);
		titles = getResources().getStringArray(R.array.index_titles);
		int[] iconResourse={
				R.drawable.filechooser,
				R.drawable.fullscreen,
				R.drawable.cookie,
				R.drawable.jsjava,
				R.drawable.tbsweb,
				R.drawable.tbsvideo,
				R.drawable.imageselect,
				R.drawable.tbs_db,
				R.drawable.firstx5,
				R.drawable.webviewtransport,
				R.drawable.sysweb,
				R.drawable.flash,
				R.drawable.webtips,
				
				R.drawable.securityjs,
				R.drawable.longclick,
		};

		HashMap<String, Object> item = null;
		//HashMap<String, ImageView> block = null;
		for (int i = 0; i < titles.length; i++) {
			item = new HashMap<String, Object>();

			item.put("title",titles[i]);
			item.put("icon", iconResourse[i]);
			

			items.add(item);
		}
		this.gridAdapter = new SimpleAdapter(this, items, R.layout.function_block,
				new String[] { "title" ,"icon"}, 
				new int[] { R.id.Item_text,R.id.Item_bt });
		if(null!=this.gridView){
			this.gridView.setAdapter(gridAdapter);
			this.gridAdapter.notifyDataSetChanged();
			this.gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> gridView, View view, int position, long id) {
					HashMap<String, String> item = (HashMap<String, String>) gridView.getItemAtPosition(position);
					
					String current_title = item.get("title");
					
					Intent intent = null;			
					switch (position) {
					case FILE_CHOOSER: {
						intent=new Intent(MainActivity.this,FilechooserActivity.class);
						MainActivity.this.startActivity(intent);
						
					}
					break;
					case FULL_SCREEN_VIDEO:
					{
						intent=new Intent(MainActivity.this,FullScreenActivity.class);
						MainActivity.this.startActivity(intent);
					}
					break;
					case JAVA_TO_JS:
					{
						intent=new Intent(MainActivity.this,JavaToJsActivity.class);
						MainActivity.this.startActivity(intent);
						
					}
					break;
					case TBS_COOKIE:
					{
//						intent=new Intent(MainActivity.this,CookieTestActivity.class);
//						MainActivity.this.startActivity(intent);
						Toast.makeText(mContext, "未开放功能", Toast.LENGTH_LONG).show();
					}
					break;
					case TBS_VIDEO:
					{
						
						MainActivity.this.invokeTbsVideoPlayer("http://125.64.133.74/data9/userfiles/video02/2014/12/11/2796948-280-068-1452.mp4");
					
					}
					break;
					case TBS_WEB:
					{
						intent=new Intent(MainActivity.this,BrowserActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
					case TBS_IMAGE:
					{
						intent=new Intent(MainActivity.this,ImageResultActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
					case TBS_FIRST_X5:
					{
//						Builder builder = new Builder(mContext);
//						builder.setTitle("首次加载X5内核");
//						builder.setPositiveButton("轮询检查法", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								Intent intent=new Intent(MainActivity.this,X5FirstTimeActivity.class);
//								MainActivity.this.startActivity(intent);
//							}
//						});
//						builder.setNegativeButton("延时构造法", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								Intent intent=new Intent(MainActivity.this,X5FirstTimeActivityForDelay.class);
//								MainActivity.this.startActivity(intent);
//							}
//						});
//						builder.show();
//						
						Toast.makeText(mContext, "未开放功能", Toast.LENGTH_LONG).show();
					
					}
					break;
					case SYS_WEB:
					{
						intent=new Intent(MainActivity.this,SystemWebActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
					case TBS_FLASH:
					{
						intent=new Intent(MainActivity.this,FlashPlayerActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
					
					case TBS_NEW_WINDOW:
					{
						intent=new Intent(MainActivity.this,WebViewTransportActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
					
					case TBS_LONG_PRESS:
					{
						intent=new Intent(MainActivity.this,MyLongPressActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
					
					case TBS_WEB_NOTICE:
					{
						intent=new Intent(MainActivity.this,WebDevelopmentTipsActivity.class);
						MainActivity.this.startActivity(intent);
					
					}
					break;
				}
				}});
			
			
		}
		main_initialized = true;
		
		
	}


	/////////////////////////////////////////////////////////////////////////////////////////////
	//Activity menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			this.tbsSuiteExit();
		}
		return super.onKeyDown(keyCode, event);
	}

	
	
	private void tbsSuiteExit() {
		// exit TbsSuite?
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle("X5功能演示");
		dialog.setPositiveButton("OK", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				android.os.Process.killProcess(Process.myPid());
			}
		});
		dialog.setMessage("quit now?");
		dialog.create().show();
	}
	
	
	
	/**
	 * 用于TBS 视频裸播
	 * @param videoUrl 视频源 url
	 */
	private void invokeTbsVideoPlayer(String videoUrl){
		if(TbsVideo.canUseTbsPlayer(mContext)){
			TbsVideo.openVideo(mContext, videoUrl);
		}else{
			Toast.makeText(this, "X5内核无法正常加载，无法启动视频裸播", Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//
	///////////////////////////////////////////////////////////////
	
	/**
	 * 调起简版QB
	 */
	private void invoke91MiniQb(String url){
		Log.i("yuanhaizhou", "isX5WebViewEnabled is:" +isX5WebViewEnabled);
		this.preinitX5WebCore();
		if(QbSdk.isTbsCoreInited()&&this.isX5WebViewEnabled){
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("StatKey", "BDTJ91");
			QbSdk.startMiniQBToLoadUrl(mContext, url, data);
		}else{
			Toast.makeText(mContext, "X5 disabled", Toast.LENGTH_SHORT).show();
		}
		

	}
	
	
	
	
	
	public static final int MSG_WEBVIEW_CONSTRUCTOR = 1;
	public static final int MSG_WEBVIEW_POLLING= 2;
	

	
	/**
	 * 开启额外进程 服务 预加载X5内核，
	 * 此操作必须在主进程调起X5内核前进行，否则将不会实现预加载
	 */
	private void preinitX5WithService(){
		Intent intent = new Intent(mContext, FirstLoadingX5Service.class);
		startService(intent);
	}
	
	/**
	 * X5内核在使用preinit接口之后，对于首次安装首次加载没有效果
	 * 实际上，X5webview的preinit接口只是降低了webview的冷启动时间；
	 * 因此，现阶段要想做到首次安装首次加载X5内核，必须要让X5内核提前获取到内核的加载条件
	 */
	private void preinitX5WebCore(){


		if(!QbSdk.isTbsCoreInited()){//preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
			QbSdk.preInit(MainActivity.this, myCallback);//设置X5初始化完成的回调接口  第三个参数为true：如果首次加载失败则继续尝试加载；
		}
	}
	
	
	
	private PreInitCallback myCallback=new QbSdk.PreInitCallback() {
		
		@Override
		public void onViewInitFinished() {//当X5webview 初始化结束后的回调
			new WebView(mContext);
			MainActivity.this.isX5WebViewEnabled = true;
		}
		
		@Override
		public void onCoreInitFinished() {
		}

	
	};
	
	


}
