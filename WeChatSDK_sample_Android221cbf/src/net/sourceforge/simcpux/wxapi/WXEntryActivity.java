package net.sourceforge.simcpux.wxapi;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import net.sourceforge.simcpux.Constants;
import net.sourceforge.simcpux.GetFromWXActivity;
import net.sourceforge.simcpux.HttpHelper;
import net.sourceforge.simcpux.SendToWXActivity;
import net.sourceforge.simcpux.ShowFromWXActivity;
import net.sourceforge.simcpux.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	
	private Button gotoBtn, regBtn, launchBtn, checkBtn;
	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");//设置格式 
    private HttpHelper mHttpHelper;
    private HandlerThread mWorkThread;
    private final static Object mHttpLock = new Object();
    
    public static final int GET_ACCESS_TOKEN = 0x1000;
    public static final int GET_UNION_ID = 0x1001;
    public static final int REFRESH_ACCESS_TOKEN = 0x1002;
    public static final int CHECK_ACCESS_TOKEN = 0x1003;
    
    private Handler mMainHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_ACCESS_TOKEN: {
                	String data = (String) msg.obj;
                	Log.i("zzz", "GET_ACCESS_TOKEN data="+data);
                	if (data != null && !data.isEmpty()) {
                		try {
							JSONObject jsonObject = new JSONObject(data);
							String access_token = jsonObject.getString("access_token");
				            int expires_in = jsonObject.getInt("expires_in");
				            String refresh_token = jsonObject.getString("refresh_token");
				            String openid = jsonObject.getString("openid");
				            String scope = jsonObject.getString("scope");
				            String unionid = jsonObject.getString("unionid");
				            final String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
				            Message message = mWorkHandler.obtainMessage(GET_UNION_ID, url);
				            message.sendToTarget();
                		} catch (JSONException e) {
							e.printStackTrace();
						}
                	}
                }
                	break;
                case GET_UNION_ID: {
                	String data = (String) msg.obj;
                	Log.i("zzz", "GET_UNION_ID data="+data);
                	if (data != null && !data.isEmpty()) {
                		try {
							JSONObject jsonObject = new JSONObject(data);
							String openid = jsonObject.getString("openid");
				            String nickname = jsonObject.getString("nickname");
				            int sex = jsonObject.getInt("sex");
				            String province = jsonObject.getString("province");
				            String city = jsonObject.getString("city");
				            String country = jsonObject.getString("country");
				            String headimgurl = jsonObject.getString("headimgurl");
				            String privilege = jsonObject.getString("privilege");
				            String unionid = jsonObject.getString("unionid");
				            Intent intent = new Intent();        
				            intent.setAction("android.intent.action.VIEW");    
				            Uri content_url = Uri.parse("http://www.baidu.com");   
				            intent.setData(content_url);
				            startActivity(intent);
                		} catch (JSONException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
                	}
                }
                	break;
                default:
                    break;
            }
        }
    };
    private Handler mWorkHandler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);

    	// 将该app注册到微信
	    api.registerApp(Constants.APP_ID); 
	    
	    mHttpHelper = new HttpHelper();
	    mHttpHelper.setMsgHandler(mMainHandler);

        mWorkThread = new HandlerThread("worker_thread");
        mWorkThread.start();
        mWorkHandler = new Handler(mWorkThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_ACCESS_TOKEN: {
                    	String url = (String) msg.obj;
                        synchronized (mHttpLock) {
                        	mHttpHelper.methodHttpGet(url, GET_ACCESS_TOKEN);
                        }
                        break;
                    }
                    case GET_UNION_ID: {
                    	String url = (String) msg.obj;
                        synchronized (mHttpLock) {
                        	mHttpHelper.methodHttpGet(url, GET_UNION_ID);
                        }
                        break;
                    }
                }
            }
        };

    	regBtn = (Button) findViewById(R.id.reg_btn);
    	regBtn.setVisibility(View.GONE);
    	regBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 将该app注册到微信
			    api.registerApp(Constants.APP_ID);    	
			}
		});
    	
        gotoBtn = (Button) findViewById(R.id.goto_send_btn);
        gotoBtn.setVisibility(View.GONE);
        gotoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        startActivity(new Intent(WXEntryActivity.this, SendToWXActivity.class));
		        finish();
			}
		});
        
        launchBtn = (Button) findViewById(R.id.launch_wx_btn);
        launchBtn.setText("请求微信登陆授权");
        launchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// send oauth request 
				final SendAuth.Req req = new SendAuth.Req();
				req.scope = "snsapi_userinfo";
				req.state = "wechat_sdk_demo_test";
				api.sendReq(req);
				//Toast.makeText(WXEntryActivity.this, "launch result = " + api.openWXApp(), Toast.LENGTH_LONG).show();
			}
		});
        
        checkBtn = (Button) findViewById(R.id.check_timeline_supported_btn);
        checkBtn.setVisibility(View.GONE);
        checkBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int wxSdkVersion = api.getWXAppSupportAPI();
				if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
					Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline supported", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline not supported", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mWorkThread != null) {
			mWorkThread.quit();
		}
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			goToGetMsg();		
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		default:
			break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			String code = ((SendAuth.Resp) resp).code; 
			Log.i("zzz", "openId="+resp.openId+";code="+code);
			final String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constants.APP_ID+"&secret="+Constants.APP_SECRET+"&code="+code+"&grant_type=authorization_code";
			Message msg = mWorkHandler.obtainMessage(GET_ACCESS_TOKEN, url);
            msg.sendToTarget();
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
		
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
	
	private void goToGetMsg() {
		Intent intent = new Intent(this, GetFromWXActivity.class);
		intent.putExtras(getIntent());
		startActivity(intent);
		finish();
	}
	
	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		WXMediaMessage wxMsg = showReq.message;		
		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
		
		StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
		msg.append("description: ");
		msg.append(wxMsg.description);
		msg.append("\n");
		msg.append("extInfo: ");
		msg.append(obj.extInfo);
		msg.append("\n");
		msg.append("filePath: ");
		msg.append(obj.filePath);
		
		Intent intent = new Intent(this, ShowFromWXActivity.class);
		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
		startActivity(intent);
		finish();
	}
}