
package com.codezyw.common;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/
 * res_list&verify=1&id=1417751808&token=&lang=zh_CN <br>
 * 调用频率限制 <br>
 * ---接口名 ------ 频率限制--- <br>
 * 通过code换取access_token 1万/分钟 <br>
 * 刷新access_token 5万/分钟 <br>
 * 获取用户基本信息 5万/分钟
 * 
 * @author Administrator
 */
public class WXHelper {
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    /**
     * 请求微信授权,获取第一步的code
     * <p>
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=
     * resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
     * <p>
     * 
     * @param api
     */
    public static void sendAuthRequest(IWXAPI api) {
        if (api == null) {
            return;
        }
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    /**
     * 第二步：通过code获取access_token
     * <p>
     * 获取第一步的code后，请求以下链接获取access_token：
     * 
     * @param code
     * @param appid
     * @param appsecret
     * @return
     */
    public static String getAccessTokenUrl(String code, String appid, String appsecret) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(appid).append("&secret=").append(appsecret).append("&code=").append(code)
                .append("&grant_type=authorization_code");
        return sb.toString();
    }

    /**
     * 第二步：通过code获取access_token
     * <p>
     * 解析access_token
     * 
     * @param data
     * @return
     */
    public static AccessTokenResult parseAccessToken(String data) {
        if (!TextUtils.isEmpty(data)) {
            try {
                AccessTokenResult accessTokenResult = new AccessTokenResult();
                JSONObject jsonObject = new JSONObject(data);
                accessTokenResult.access_token = jsonObject.getString("access_token");
                accessTokenResult.expires_in = jsonObject.getInt("expires_in");
                accessTokenResult.refresh_token = jsonObject.getString("refresh_token");
                accessTokenResult.openid = jsonObject.getString("openid");
                accessTokenResult.scope = jsonObject.getString("scope");
                // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
                accessTokenResult.unionid = jsonObject.getString("unionid");
                return accessTokenResult;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 第三步：通过access_token获取UnionID
     * <p>
     * 获取第二步的access_token后获取用户个人信息（UnionID机制）
     * 
     */
    public static String getUnionIdUrl(AccessTokenResult accessTokenResult) {
        if (accessTokenResult == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/userinfo?access_token=").append(accessTokenResult.access_token).append("&openid=")
                .append(accessTokenResult.openid);
        return sb.toString();
    }

    /**
     * 第三步：通过access_token获取UnionID
     * <p>
     * 解析UnionID
     * 
     * @param data
     * @return
     */
    public static UnionIdResult parseUnionId(String data) {
        if (!TextUtils.isEmpty(data)) {
            try {
                UnionIdResult unionIdResult = new UnionIdResult();
                JSONObject jsonObject = new JSONObject(data);
                unionIdResult.openid = jsonObject.getString("openid");
                unionIdResult.nickname = jsonObject.getString("nickname");
                unionIdResult.sex = jsonObject.getInt("sex");
                unionIdResult.province = jsonObject.getString("province");
                unionIdResult.city = jsonObject.getString("city");
                unionIdResult.country = jsonObject.getString("country");
                unionIdResult.headimgurl = jsonObject.getString("headimgurl");
                unionIdResult.privilege = jsonObject.getString("privilege");
                unionIdResult.unionid = jsonObject.getString("unionid");
                return unionIdResult;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * <br>
     * 刷新access_token有效期 <br>
     * access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，当access_token超时后，
     * 可以使用refresh_token进行刷新，access_token刷新结果有两种： <br>
     * 1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间； <br>
     * 2. 若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，
     * 相当于续期access_token。 <br>
     * refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。 <br>
     * 请求方法 <br>
     * 获取第一步的code后，请求以下链接进行refresh_token：
     * 
     * @param code
     * @param appid
     * @param appsecret
     * @return
     */
    public static String refreshTokenUrl(String code, String appid, String refreshToken) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=").append(appid).append("&grant_type=refresh_token&refresh_token=")
                .append(refreshToken);
        return sb.toString();
    }

    public static int getWXAppSupportAPI(IWXAPI api) {
        if (api == null) {
            return 0;
        }
        return api.getWXAppSupportAPI();
    }

    public static boolean isSupportTimeLineAPI(IWXAPI api) {
        int wxSdkVersion = api.getWXAppSupportAPI();
        if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
            // timeline supported
            return true;
        } else {
            // timeline not supported
            return false;
        }
    }

    public static class AccessTokenResult {
        public String access_token = null;
        public int expires_in = 0;
        public String refresh_token = null;
        public String openid = null;
        public String scope = null;
        // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
        public String unionid = null;
    }
    
    public static class UnionIdResult {
        public String openid = null;
        public String nickname = null;
        public int sex = 0;
        public String province = null;
        public String city = null;
        public String country = null;
        public String headimgurl = null;
        public String privilege = null;
        public String unionid = null;
    }
}
