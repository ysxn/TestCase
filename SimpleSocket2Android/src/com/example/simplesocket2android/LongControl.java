
package com.example.simplesocket2android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LongControl extends Activity {
    // 服务器ip地址
    public static final String DEFULT_IP = "107.170.224.94";

    public static final String PREFS_NAME = "PreferencesFile";

    public static final int UPDATALOG = 1;

    private static final int PORT = 43211;

    private EditText mCommand;

    private EditText mIpEdit;

    private TextView mResult;

    private Button mSend;

    private Button mClean;

    private String mIp;

    Context mContext;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATALOG:
                    String s = (String) msg.obj;
                    mResult.setText(mResult.getText() + "\n" + s);
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mCommand = (EditText) this.findViewById(R.id.command);
        mResult = (TextView) this.findViewById(R.id.log);
        mSend = (Button) this.findViewById(R.id.send);
        mIpEdit = (EditText) this.findViewById(R.id.ipEdit);
        mClean = (Button) this.findViewById(R.id.clean);
        mSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mIp = mIpEdit.getText().toString();
                // 当点击发送按钮时，开启一个tcpClient线程，向服务器发送消息
                SocketClient tcp = new SocketClient(mCommand.getText().toString());
                tcp.start();
            }
        });

        mClean.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mResult.setText("");
            }
        });

        mIp = onLoad();
        if (mIp != null) {
            mIpEdit.setText(mIp);
        }
    }

    class SocketClient extends Thread {
        String cmd = "ls";

        private Socket socket;

        private BufferedWriter out;

        private BufferedReader in;

        public SocketClient(String c) {
            if (c != null && !c.isEmpty()) {
                cmd = c;
            }
        }

        public void run() {
            try {

                // 创建一个socket实例
                socket = new Socket();
                //将此选项设为非零的超时值时，在与此 Socket 关联的 InputStream 上调用 read() 将只阻塞此时间长度。  
                //如果超过超时值，将引发 java.net.SocketTimeoutException，虽然 Socket 仍旧有效。选项必须在进入阻塞操作前被启用才能生效。 
                socket.setSoTimeout(5000);
                // 建立一个远程链接
                //3秒内连接不上则超时
                socket.connect(new InetSocketAddress(mIp, PORT), 3000);
                // 判断是否链接成功
                if (socket.isConnected()) {
                    mHandler.obtainMessage(UPDATALOG, "Server Connented").sendToTarget();
                } else {
                    mHandler.obtainMessage(UPDATALOG, "连接服务器失败！").sendToTarget();
                    return;
                }
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.write(cmd.replace("\n", " ") + "\n");
                out.flush();
                // socket.setSoTimeout(10000);
                // Thread.sleep(5000);
                while (!socket.isClosed()) {
                    String result;
                    result = in.readLine();
                    System.out.println("client print : " + result);
                    if (result != null) {
                        mHandler.obtainMessage(UPDATALOG, result).sendToTarget();
                    } else {
                        socket.close();
                    }
                }
                mHandler.obtainMessage(UPDATALOG, "client thread finish").sendToTarget();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                mHandler.obtainMessage(UPDATALOG, "UnknownHostException").sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.obtainMessage(UPDATALOG, "IOException").sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
                mHandler.obtainMessage(UPDATALOG, "Exception").sendToTarget();
            } finally {
                System.out.println("client print : finally");
                if (in != null) {
                    try {
                        in.close();
                        System.out.println("client print : in.close");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                        System.out.println("client print : out.close");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                        System.out.println("client print : socket.close");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 有时一个域名会包含不止一个IP地址，比如微软的Web服务器，这是为了保持负载平衡。InetAddress提供了一种可以得到一个域名的所有IP地址的方法。让我们来考虑以下代码：
    public String getIP(String name) {
        InetAddress address = null;
        try {
            //address = InetAddress.getByName(name);
            InetAddress[] all = InetAddress.getAllByName(name);
            if (all != null) {
                address = all[0];
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("获取IP失败:" + name);
        }
        if (address == null) {
            return "";
        }
        return address.getHostAddress().toString();
    }

    private String onLoad() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String mPreferences = settings.getString("preferences", DEFULT_IP);
        return mPreferences;
    }

    private void onSave(String save) {
        if (TextUtils.isEmpty(save)) {
            setPreferences(DEFULT_IP);
        } else {
            setPreferences(save);
        }
    }

    private void setPreferences(String mPreferences) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("preferences", mPreferences);
        editor.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("PC Control");
            builder.setMessage("exit ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onSave(mIpEdit.getText().toString());
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

}
