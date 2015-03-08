
package com.example.android.notepad;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    /** Called when the activity is first created. */
    private EditText user;

    private EditText password;

    private Button loginBtn;

    private Button logoutBtn;
    
    private Button newCountBtn;

    // ��Ҫ�Ǽ�¼�û��Ự�����е�һЩ�û��Ļ�����Ϣ
    private HashMap<String, String> session = new HashMap<String, String>();

    private DataBase mDataBase;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = SharedPreferenceUtils.getPrefString(this, SharedPreferenceUtils.KEY_USERNAME, "");
        String pass = SharedPreferenceUtils.getPrefString(this, SharedPreferenceUtils.KEY_PASSWORD, "");

        setContentView(R.layout.login_main);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(loginClick);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(logoutClick);
        newCountBtn = (Button) findViewById(R.id.newCountBtn);
        newCountBtn.setOnClickListener(newCountClick);
        newCountBtn.setVisibility(View.GONE);
        mDataBase = new DataBase(this);
        username = mDataBase.getEmail(0);
        pass = mDataBase.getPassword(0);
        if (username == null || username.isEmpty() || pass == null || pass.isEmpty()) {
            logoutBtn.setVisibility(View.GONE);
        } else {
            user.setText(username);
            password.setText(pass);
            loginBtn.setVisibility(View.GONE);
        }
    }

    OnClickListener loginClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final String username = user.getText().toString().trim();
            final String pass = password.getText().toString().trim();
            Intent i = new Intent(LoginActivity.this, HttpConnectionService.class);
            i.putExtra(HttpConnectionService.CMD, HttpConnectionService.FETCH);
            i.putExtra(HttpConnectionService.EMAIL, username);
            i.putExtra(HttpConnectionService.PASSWORD, pass);
            LoginActivity.this.startService(i);
            LoginActivity.this.finish();
            return;
            
//            if (checkUser()) {
//                Toast.makeText(v.getContext(), "�û���¼�ɹ���", Toast.LENGTH_SHORT).show();
//                Context context = v.getContext();
//                Intent intent = new Intent(context, LoginSuccessActivity.class);
//                // ����session����,���û���¼�ɹ���Ϊsession��ʼ����ֵ,������HashMap��ֵ
//                Bundle map = new Bundle();
//                map.putSerializable("sessionid", session);
//                intent.putExtra("session", map);
//                context.startActivity(intent); // ��ת���ɹ�ҳ��
//            } else
//                Toast.makeText(v.getContext(), "�û���֤ʧ�ܣ�", Toast.LENGTH_SHORT).show();
        }
    };

    OnClickListener logoutClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mDataBase.remove(0);
            SharedPreferenceUtils.setPrefString(LoginActivity.this, SharedPreferenceUtils.KEY_USERNAME, "");
            SharedPreferenceUtils.setPrefString(LoginActivity.this, SharedPreferenceUtils.KEY_PASSWORD, "");
            Intent i = new Intent(LoginActivity.this, HttpConnectionService.class);
            startService(i);
            finish();
        }
    };
    
    OnClickListener newCountClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    private boolean checkUser() {
        String username = user.getText().toString();
        String pass = password.getText().toString();
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        HttpPost mPost = new HttpPost("http://10.0.2.2/web/php/login.php");
        // �����û����������൱��
        // http://10.0.2.2/web/php/login.php?username=''&password=''
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", pass));
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
                    String name = "";
                    String userid = "";
                    String sessionid = "";
                    try {
                        jsonObject = new JSONObject(info);
                        flag = jsonObject.getString("flag");
                        name = jsonObject.getString("name");
                        userid = jsonObject.getString("userid");
                        sessionid = jsonObject.getString("sessionid");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // ���ݷ������˷��صı��,�жϷ���˶���֤�Ƿ�ɹ�
                    if (flag.equals("success")) {
                        // Ϊsession������Ӧ��ֵ,������session�����м�¼����û���Ϣ
                        session.put("s_userid", userid);
                        session.put("s_username", name);
                        session.put("s_sessionid", sessionid);
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
