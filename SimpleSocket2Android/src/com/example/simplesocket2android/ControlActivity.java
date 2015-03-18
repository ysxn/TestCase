package com.example.simplesocket2android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class ControlActivity extends Activity {
	// 服务器ip地址
	public static final String DEFULT_PRES = "127.0.0.1";
	public static final String PREFS_NAME = "PreferencesFile";
	public static final int CONNENTED = 0;
	public static final int UPDATALOG = 1;

	private static final int PORT = 45332;

	private EditText command;
	private EditText ipEdit;
	private TextView log;
	private Button send;
	private Button clean;
	private String ip;
	private String logMsg;
	private Socket socket;
	private BufferedWriter writer;
	private InetSocketAddress isa = null;
	Context mContext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		findviews();
		setonclick();
		init();
	}

	public void findviews() {
		command = (EditText) this.findViewById(R.id.command);
		log = (TextView) this.findViewById(R.id.log);
		send = (Button) this.findViewById(R.id.send);
		ipEdit = (EditText) this.findViewById(R.id.ipEdit);
		clean = (Button) this.findViewById(R.id.clean);
	}

	private void init() {
		log.setMovementMethod(ScrollingMovementMethod.getInstance());
		logMsg = log.getText().toString();
		// 创建一个socket实例
		socket = new Socket();
		ip = onLoad();
		if (ip != null) {
			ipEdit.setText(ip);
		}
	}

	private void setonclick() {
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ip = ipEdit.getText().toString();
				// 当点击发送按钮时，开启一个tcpClient线程，向服务器发送消息
				tcpClient tcp = new tcpClient(command.getText().toString());
				tcp.start();
			}
		});

		clean.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logMsg = "";
				log.setText(logMsg);
			}
		});
	}

	// 在向服务器发送消息之前，必须先链接到服务器。
	public void connecttoserver() throws UnknownHostException, IOException {
		socket = RequestSocket(ip, PORT);
		// 判断是否链接成功
		if (socket.isConnected()) {
			Message msg = new Message();
			msg.what = CONNENTED;
			mHandler.sendMessage(msg);
		}
	}

	// 链接服务器
	private Socket RequestSocket(String host, int port)
			throws UnknownHostException, IOException {
		Socket ConSocket = new Socket();
		// 创建套接字地址，其中 IP 地址为通配符地址，端口号为指定值。
		// 有效端口值介于 0 和 65535 之间。端口号 zero 允许系统在 bind 操作中挑选暂时的端口。
		isa = new InetSocketAddress(host, port);
		// 建立一个远程链接
		ConSocket.connect(isa);

		return ConSocket;
	}

	// 向服务器发送信息
	private void SendMsg(Socket socket, String msg) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));

		Log.i("msg", msg.replace("\n", " ") + "\n");
		writer.write(msg.replace("\n", " ") + "\n");
		writer.flush();
	}

	// 接收服务器信息
	private String ReceiveMsg(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		String line;
		String txt = "";
		while ((line = reader.readLine()) != null) {
			txt += line + "\n";
		}
		reader.close();
		return txt;

	}

	class tcpClient extends Thread {
		String commandString;

		public tcpClient() {
			commandString = "ls";
		}

		public tcpClient(String command) {
			commandString = command;
		}

		public void run() {
			String recv;
			try {
				connecttoserver();
				// 向服务器发送命令
				SendMsg(socket, commandString);
				// 等待，接收来自服务器返回的消息
				recv = ReceiveMsg(socket);
				if (recv != null) {
					logMsg += recv;
					// close BufferedWriter and socket
					writer.close();
					socket.close();
					// 将服务器返回的消息显示出来
					Message msg = new Message();
					msg.what = UPDATALOG;
					mHandler.sendMessage(msg);
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String onLoad() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String mPreferences = settings.getString("preferences", DEFULT_PRES);
		return mPreferences;
	}

	private void onSave(String save) {
		if (TextUtils.isEmpty(save)) {
			setPreferences(DEFULT_PRES);
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
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							onSave(ipEdit.getText().toString());
							finish();
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "关机");
		menu.add(0, 2, 2, "重启");
		menu.add(0, 3, 3, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			tcpClient tcp = new tcpClient("sudo poweroff");
			tcp.start();
			return true;
		case 2:
			tcp = new tcpClient("sudo reboot");
			tcp.start();
			return true;
		case 3:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case CONNENTED:
				logMsg += "Server Connented\n";
				log.setText(logMsg);
				break;

			case UPDATALOG:
				log.setText(logMsg);
				log.setScrollContainer(true);
				break;
			}
		}
	};
}