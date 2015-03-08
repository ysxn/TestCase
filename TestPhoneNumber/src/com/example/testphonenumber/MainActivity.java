
package com.example.testphonenumber;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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
        private Button mFetchNote;
        private Button mUpdateNote;
        private Button mInsertNote;
        private Button mDeleteNote;
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
                            FetchBean fetchBean = ParseJSONHelper.parseFetchBeanByJSONSingle(data);
                            if (fetchBean != null) {
                                mResult.setText(mResult.getText() + "\n=" +fetchBean.dump());
                            }
                            JSONObject json;
                            String result_data = "";
                            boolean login_result = false;
                            boolean fetch_result = false;
                            try {
                                json = new JSONObject(data);
                                result_data = json.getString("result_data");
                                login_result = json.getBoolean("login_result");
                                fetch_result = json.getBoolean("fetch_result");
                                //mResult.setText(mResult.getText() + "\n\nlogin_result=" +login_result);
                                //mResult.setText(mResult.getText() + "\n\nfetch_result=" +fetch_result);
                            } catch (JSONException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            List<NoteBean> notesBean = ParseJSONHelper.parseNoteBeanByJSON(result_data);
                            if (notesBean != null) {
                                for (NoteBean n : notesBean) {
                                    //mResult.setText(mResult.getText() + "" +n.dump());
                                    Log.i(TAG, ">>>>>>after note="+n.dump());
                                }
                            }
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
            mFetchNote = (Button) rootView.findViewById(R.id.fetch_button);
            mUpdateNote = (Button) rootView.findViewById(R.id.update_button);
            mInsertNote = (Button) rootView.findViewById(R.id.insert_button);
            mDeleteNote = (Button) rootView.findViewById(R.id.delete_button);
            mEditText = (EditText) rootView.findViewById(R.id.input);
            mResult = (TextView) rootView.findViewById(R.id.check_result);

            if (mFetchNote != null) {
                mFetchNote.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String par = "http://www.dianhua.cn/search/yp?key="
                                    + mEditText.getText().toString().trim();
                            final String phpString = "http://php.codezyw.com/view_users_android.php";
                            final String httpUrl = "http://php.codezyw.com/fetch_note_android.php";
                            final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        //mHttpHelper.methodGet(phpString);
                                        mHttpHelper.methodHttpPost(httpUrl, postParams);
                                    }
                                }
                            }.start();

                        }
                    }
                });
            }

            if (mUpdateNote != null) {
                mUpdateNote.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String httpUrl = "http://php.codezyw.com/update_note_android.php";
                            final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                            postParams.add(new BasicNameValuePair("note_title", mEditText.getText().toString().trim()));
                            postParams.add(new BasicNameValuePair("note_content", mEditText.getText().toString().trim()));
                            postParams.add(new BasicNameValuePair("note_id", "1"));
                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        mHttpHelper.methodHttpPost(httpUrl, postParams);
                                    }
                                }
                            }.start();

                        }
                    }
                });
            }

            if (mInsertNote != null) {
                mInsertNote.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String phpString = "http://php.codezyw.com/insert_note_android.php";
                            final List<NameValuePair> phpParams = new ArrayList<NameValuePair>();
                            phpParams.add(new BasicNameValuePair("note_title", mEditText.getText().toString().trim()));
                            phpParams.add(new BasicNameValuePair("note_content", mEditText.getText().toString().trim()));
                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        mHttpHelper.methodHttpPost(phpString, phpParams);
                                    }
                                }
                            }.start();

                        }
                    }
                });
            }
            if (mDeleteNote != null) {
                mDeleteNote.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mResult != null && mEditText != null) {
                            mResult.setText("");
                            final String phpString = "http://php.codezyw.com/delete_note_android.php";
                            final List<NameValuePair> phpParams = new ArrayList<NameValuePair>();
                            phpParams.add(new BasicNameValuePair("note_id", mEditText.getText().toString().trim()));
                            new Thread() {
                                @Override
                                public void run() {
                                    synchronized (mHttpLock) {
                                        mHttpHelper.methodHttpPost(phpString, phpParams);
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
