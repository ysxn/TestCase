
package com.example.testphonenumber;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public static final int MSG_SHOW_RESULT = HttpHelper.MSG_SHOW_RESULT;
        public static final String MSG_DATA_KEY = HttpHelper.MSG_DATA_KEY;
        private final static String TAG = "zhuyawen";
        private final static Object mHttpLock = new Object();
        private Button mCheckGetButton;
        private Button mCheckPostButton;
        private Button mCheckHttpPostButton;
        private EditText mEditText;
        private TextView mResult;
        private HttpHelper mHttpHelper;

        private Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_SHOW_RESULT: {
                        // String data = msg.getData().getString(MSG_DATA_KEY);
                        String data = (String) msg.obj;
                        Log.i(TAG, ">>>>>>>>>>>>>handler MSG_SHOW_RESULT :{" + data + "}");
                        if (mResult != null && data != null) {
                            mResult.setText(data);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        };

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            mHttpHelper = new HttpHelper();
            mHttpHelper.setMsgHandler(mHandler);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mCheckGetButton = (Button) rootView.findViewById(R.id.check_get_button);
            mCheckPostButton = (Button) rootView.findViewById(R.id.check_post_button);
            mCheckHttpPostButton = (Button) rootView.findViewById(R.id.check_httppost_button);
            mEditText = (EditText) rootView.findViewById(R.id.input_phone_number);
            mResult = (TextView) rootView.findViewById(R.id.check_result);

            if (mCheckGetButton != null) {
                mCheckGetButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String par = "http://www.dianhua.cn/search/yp?key=02150504500"
                                    + mEditText.getText().toString().trim();
                            final String par2 = "http://www.dianhua.cn/search/yp?key=02150504500";

                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        mHttpHelper.methodGet(par2);
                                    }
                                }
                            }.start();

                        }
                    }
                });
            }

            if (mCheckPostButton != null) {
                mCheckPostButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String par = "http://www.dianhua.cn/search/yp?key=02150504500"
                                    + mEditText.getText().toString().trim();
                            final String par2 = "http://www.dianhua.cn/search/yp";
                            final String key = "key";
                            final String value = "02150504500";

                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        mHttpHelper.methodPost(par2, key, value);
                                    }
                                }
                            }.start();

                        }
                    }
                });
            }

            if (mCheckHttpPostButton != null) {
                mCheckHttpPostButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String par = "http://www.dianhua.cn/search/yp?key=02150504500"
                                    + mEditText.getText().toString().trim();
                            final String par2 = "http://www.dianhua.cn/search/yp?key=02150504500";

                            // 使用NameValuePair来保存要传递的Post参数
                            final List<NameValuePair> params = new ArrayList<NameValuePair>();

                            // 添加要传递的参数
                            params.add(new BasicNameValuePair("key", "02150504500"));

                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        mHttpHelper.methodHttpPost(par2, params);
                                    }
                                }
                            }.start();

                        }
                    }
                });
            }
            return rootView;
        }
    }

}
