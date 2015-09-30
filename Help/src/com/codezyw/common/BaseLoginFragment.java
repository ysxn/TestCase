
package com.codezyw.common;

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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseLoginFragment extends BaseFragment {
    private LinearLayout mRootView;
    private EditText mAccount;
    private EditText mPassword;
    private Button mLoginMenu;
    private Button mLogoutMenu;
    private Button mNewAccountMenu;

    // 主要是记录用户会话过程中的一些用户的基本信息
    private HashMap<String, String> session = new HashMap<String, String>();

    public static BaseLoginFragment newInstance(Bundle bundle) {
        BaseLoginFragment f = new BaseLoginFragment();
        f.setArguments(bundle);
        return f;
    }

    /**
     * 必须有无参构造函数，否则影响状态恢复
     */
    public BaseLoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        mRootView = new LinearLayout(getActivity());
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mRootView.setPadding(10, 0, 10, 0);
        mAccount = new EditText(getActivity());
        mAccount.setSingleLine(true);
        mPassword = new EditText(getActivity());
        mPassword.setSingleLine(true);
        mLoginMenu = new Button(getActivity());
        mLogoutMenu = new Button(getActivity());
        mNewAccountMenu = new Button(getActivity());
        TextView accountText = new TextView(getActivity());
        TextView passwordText = new TextView(getActivity());
        accountText.setText("账号:");
        passwordText.setText("密码:");
        mLoginMenu.setText("登录账号");
        mLogoutMenu.setText("退出账号");
        mNewAccountMenu.setText("创建新账号");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        mRootView.addView(accountText, lp);
        mRootView.addView(mAccount, lp);
        mRootView.addView(passwordText, lp);
        mRootView.addView(mPassword, lp);
        mRootView.addView(mLogoutMenu, lp);
        mRootView.addView(mLoginMenu, lp);
        mRootView.addView(mNewAccountMenu, lp);

        mLogoutMenu.setOnClickListener(logoutClickListener);
        mLoginMenu.setOnClickListener(loginClickListener);
        mNewAccountMenu.setOnClickListener(newAccountClickListener);
        mNewAccountMenu.setVisibility(View.GONE);

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        changeState();
        changePasswordVisible(false);
    }

    private void changeState() {
        String username = DbHelper.getInstance(getActivity()).getString(JsonHelper.ACCOUNT, "");
        String password = DbHelper.getInstance(getActivity()).getString(JsonHelper.PASSWORD, "");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mAccount.setText("");
            mPassword.setText("");
            mLoginMenu.setVisibility(View.VISIBLE);
            mLogoutMenu.setVisibility(View.GONE);
        } else {
            mAccount.setText(username);
            mPassword.setText(password);
            mLoginMenu.setVisibility(View.GONE);
            mLogoutMenu.setVisibility(View.VISIBLE);
        }
        mAccount.setSelection(mAccount.getText().length());
        mPassword.setSelection(mPassword.getText().length());
    }

    /**
     * 设置密码是否可见
     * 
     * @param visible
     */
    private void changePasswordVisible(boolean visible) {
        if (!visible) {
            // mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            // mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    OnClickListener loginClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final String username = mAccount.getText().toString().trim();
            final String password = mPassword.getText().toString().trim();
            if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                return;
            }
            DbHelper.getInstance(getActivity()).putString(JsonHelper.ACCOUNT, username);
            DbHelper.getInstance(getActivity()).putString(JsonHelper.PASSWORD, password);
            changeState();

            // if (checkUser()) {
            // Toast.makeText(v.getContext(), "用户登录成功！",
            // Toast.LENGTH_SHORT).show();
            // Context context = v.getContext();
            // Intent intent = new Intent(context, LoginSuccessActivity.class);
            // // 传递session参数,在用户登录成功后为session初始化赋值,即传递HashMap的值
            // Bundle map = new Bundle();
            // map.putSerializable("sessionid", session);
            // intent.putExtra("session", map);
            // context.startActivity(intent); // 跳转到成功页面
            // } else
            // Toast.makeText(v.getContext(), "用户验证失败！",
            // Toast.LENGTH_SHORT).show();
        }
    };

    OnClickListener logoutClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            DbHelper.getInstance(getActivity()).delete(JsonHelper.ACCOUNT);
            DbHelper.getInstance(getActivity()).delete(JsonHelper.PASSWORD);
            changeState();
        }
    };

    OnClickListener newAccountClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    private boolean checkUser() {
        String username = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        HttpPost mPost = new HttpPost("http://10.0.2.2/web/php/login.php");
        // 传递用户名和密码相当于
        // http://10.0.2.2/web/php/login.php?username=''&password=''
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("username", username));
        pairs.add(new BasicNameValuePair("password", password));
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
                    // 以下主要是对服务器端返回的数据进行解析
                    JSONObject jsonObject = null;
                    // flag为登录成功与否的标记,从服务器端返回的数据
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
                    // 根据服务器端返回的标记,判断服务端端验证是否成功
                    if (flag.equals("success")) {
                        // 为session传递相应的值,用于在session过程中记录相关用户信息
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
