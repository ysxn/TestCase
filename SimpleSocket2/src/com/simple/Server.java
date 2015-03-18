package com.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.LineNumberReader;

public class Server extends Thread {

	private ServerSocket server = null;
	private static final int PORT = 45332;
	private BufferedWriter writer;
	private BufferedReader reader;

	private Server() throws IOException {
		// ����Socket������
		CreateSocket();
	}

	public void run() {
		Socket client;
		String txt;
		try {
			// �߳�����ѭ����ʵʱ����socket�˿�
			while (true) {
				client = ResponseSocket();

				// ��Ӧ�ͻ����������󡣡�
				while (true) {
					txt = ReceiveMsg(client);
					// ���ӻ�ÿͻ��˷���������
					System.out.println(txt);
					if (txt != null) {
						// ִ������
						txt = exceCommand(txt);
					}
					// ��ͻ��˷�����Ϣ
					SendMsg(client, txt);
					// �жϣ��رմ˴�����
					break;
				}
				CloseSocket(client);
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	// ����һ��ServerSocketʵ�����󶨵�һ���ض��Ķ˿ڡ�һ���������˿�������֧��50�����ӣ���������ܾ����ӡ�
	// ���ԣ����ֻ����50���ͻ���ͬʱʹ�����ָ���Ķ˿��������������Ϣ
	private void CreateSocket() throws IOException {
		server = new ServerSocket(PORT);
		System.out.println("Server starting..");
	}

	/*
	 * ServerSocket���������ʵ����һ���������˵�Socket�������������Լ���������������� ����
	 * 1������ServerSocket�ķ����� ���� ServerSocket(Int localPort) ���� ServerSocket(int
	 * localport,int queueLimit) ���� ServerSocket(int localport,int
	 * queueLimit,InetAddress localAddr) ����
	 * ����һ��ServerSocket����ָ��һ���˿ڣ��Ա�ͻ����ܹ���ö˿ںŷ����������󡣶˿ڵ���Ч��Χ��0-65535
	 * ����2��ServerSocket���� ���� Socket accept() ���� void close ����
	 * accept()����Ϊ��һ��������������󴴽�Socketʵ���������ѳɹ����ӵ�Socketʵ�����ظ��������׽��֣����û����������
	 * accept()�����������ȴ��� ���� close�������ڹر��׽���
	 */
	private Socket ResponseSocket() throws IOException {
		Socket client = server.accept();
		System.out.println("client connected..");
		return client;
	}

	// �ر�socket
	private void CloseSocket(Socket socket) throws IOException {
		reader.close();
		writer.close();
		socket.close();
		System.out.println("client closed..");
	}

	// ��ͻ��˷�����Ϣ
	private void SendMsg(Socket socket, String Msg) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		writer.write(Msg + "\n");
		writer.flush();
	}

	// �������Կͻ��˵���Ϣ��������ͨ��server.accept();�������Կͻ��˵��׽��֣�����I/O��ʽ
	// ���׽��ֵ���Ϣȡ����
	private String ReceiveMsg(Socket socket) throws IOException {
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		System.out.println("server get input from client socket..");
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			return line;
		}
		return line;
	}

	// ִ�пͻ��˷�������������������ڱ���ִ�пͻ��˷��͹�������Ϣ�������
	// ������ִ������󣬷��������صĽ����Ʃ�磬��linux��ִ�С�ls��������ص��ǵ�ǰĿ¼
	// ����������ļ�������
	private String exceCommand(String command) {
		String msg = "";
		try {
			// Runtime.getRuntime()���ص�ǰӦ�ó����Runtime���󣬸ö����exec()
			// ����ָʾJava���������һ���ӽ���ִ��ָ���Ŀ�ִ�г������������������ӽ��̶�Ӧ��Process����ʵ����
			// ͨ��Process���Կ��Ƹ��ӽ��̵�ִ�л��ȡ���ӽ��̵���Ϣ

			Process process = Runtime.getRuntime().exec(command);
			InputStreamReader ir = new InputStreamReader(
					process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
				msg += line + "\n";
			}

		} catch (java.io.IOException e) {
			System.err.println("IOException " + e.getMessage());
		}

		return msg;
	}

	public static void main(final String args[]) throws IOException {

		Server commandServer = new Server();
		if (commandServer != null) {
			// commandServer.stop();
			commandServer.start();
		}
	}

}