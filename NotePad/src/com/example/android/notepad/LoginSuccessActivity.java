
package com.example.android.notepad;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LoginSuccessActivity.java��Ҫ��ȡphp��sessionΨһ�ı�ʶid�Լ��û���һЩ������Ϣ��session
 * id����Ϊ�����û���¼״̬�ڷ�������Ψһ��ʶ
 * ����ȷ���û���Ψһ״̬������ز�����LoginSuccessActivity.java��ķ�����GetWebSession
 * .java���ơ�����Ҫ�����ǻ�ȡsession id���ٴη���session id��������������֤�����ݷ�װ��session������֤�û�����Ȩ�޵ȡ�
 * 
 * @author zhuyawen
 */
public class LoginSuccessActivity extends Activity {
    private HashMap<String, String> session;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);
        // ��ȡ�ӵ�¼�ɹ������Ĵ��ݵĲ���
        session = (HashMap<String, String>) this.getIntent().getBundleExtra("session")
                .getSerializable("sessionid");
        // ��ȡsession�Ļ�����Ϣ������ʾ��Ӧ�Ŀؼ�
        String userid_info = session.get("s_userid");
        String username_info = session.get("s_username");
        String session_id = session.get("s_sessionid");
        // ��ʾ��Ӧ�����ݵ��ؼ�
        TextView userid_show = (TextView) findViewById(R.id.userid_show);
        userid_show.setText(userid_info);
        TextView username_show = (TextView) findViewById(R.id.username_show);
        username_show.setText(username_info);
        TextView sessionid_show = (TextView) findViewById(R.id.sessionid_show);
        sessionid_show.setText(session_id);
        // ���ݱ���session�ٴλ�ȡ�û���Ϣ
        Button getInfo = (Button) findViewById(R.id.getinfo);
        getInfo.setOnClickListener(getInfoClick);
    }

    OnClickListener getInfoClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (getUserInfo()) {
                Context context = v.getContext();
                Intent intent = new Intent(context, GetUserInfoActivity.class);
                // ����session����,���û���¼�ɹ���Ϊsession��ʼ����ֵ,������HashMap��ֵ
                Bundle map = new Bundle();
                map.putSerializable("sessionid", session);
                intent.putExtra("session", map);
                context.startActivity(intent); // ��ת���ɹ�ҳ��
            } else {
                Toast.makeText(v.getContext(), "����Ϊ�գ�", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean getUserInfo() {
        String sess_username = session.get("s_username");
        String sess_userid = session.get("s_userid");
        String sess_id = session.get("s_sessionid");
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        HttpPost mPost = new HttpPost("http://10.0.2.2/web/php/getinfo.php");
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("sess_userid", sess_userid));
        pairs.add(new BasicNameValuePair("sess_username", sess_username));
        pairs.add(new BasicNameValuePair("sess_sessionid", sess_id));
        try {
            mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            HttpResponse response = mHttpClient.execute(mPost);
            int res = response.getStatusLine().getStatusCode();
            if (res == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String info = EntityUtils.toString(entity);
                    System.out.println("info-----------" + info);
                    // ������Ҫ�ǶԷ������˷��ص����ݽ��н���
                    JSONObject jsonObject = null;
                    // flagΪ��¼�ɹ����ı��,�ӷ������˷��ص�����
                    String flag = "";
                    String userinfo = "";
                    String level = "";
                    String sessionid = "";
                    try {
                        jsonObject = new JSONObject(info);
                        flag = jsonObject.getString("flag");
                        userinfo = jsonObject.getString("info");
                        level = jsonObject.getString("level");
                        sessionid = jsonObject.getString("sessionid");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // ���ݷ������˷��صı��,�жϷ���˶���֤�Ƿ�ɹ�
                    if (flag.equals("notempty")) {
                        // Ϊsession������Ӧ��ֵ,������session�����м�¼����û���Ϣ
                        session.put("info_userinfo", userinfo);
                        session.put("info_level", level);
                        session.put("info_sessionid", sessionid);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
