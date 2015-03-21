package com.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.LineNumberReader;

public class Server {

	private static int mPort = 43211;

	public static class SocketServer {

		private static ServerSocket sSever = null;
		private static int sIndex = 0;

		public SocketServer(int port) {
			try {
				// ����Socket������
				// ����������ָʾ�������ӵ����󣩵������г��ȱ�����Ϊ backlog ���������������ʱ�յ�����ָʾ����ܾ������ӡ���
				// ����仰�������backlog��ʾ������������Ķ��г��ȣ��������������ӵĿͻ��˵���Ŀ��������Ȼ����ͬʱ�����˼�ʮ��client��
				// ���ǿ���clientһ������������server���Ͼͽ����ˣ������Ƿ�����ܾ�ȡ����server��������������ٶȡ�
				// ���server�����������������󣬲Ű����������У�һ������������ˣ��;ܾ���
				sSever = new ServerSocket(port, 50);
				System.out.println("create server ok.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void beginListen() {
			while (true) {
				try {
					/*
					 * ServerSocket���������ʵ����һ���������˵�Socket�������������Լ���������������� ����
					 * 1������ServerSocket�ķ����� ���� ServerSocket(Int localPort) ����
					 * ServerSocket(int localport,int queueLimit) ����
					 * ServerSocket(int localport,int queueLimit,InetAddress
					 * localAddr) ����
					 * ����һ��ServerSocket����ָ��һ���˿ڣ��Ա�ͻ����ܹ���ö˿ںŷ�����������
					 * �˿ڵ���Ч��Χ��0-65535 ����2��ServerSocket���� ���� Socket accept() ����
					 * void close ����
					 * accept()����Ϊ��һ��������������󴴽�Socketʵ���������ѳɹ����ӵ�Socketʵ�����ظ��������׽���
					 * �����û���������� accept()�����������ȴ��� ���� close�������ڹر��׽���
					 */
					final Socket socket = sSever.accept();
					sIndex++;
					System.out.println("client connected...sIndex=" + sIndex);
					new Thread(new Runnable() {
						public void run() {
							BufferedReader in;
//							PrintWriter out;
							BufferedWriter writer;
							try {
								in = new BufferedReader(new InputStreamReader(
										socket.getInputStream(), "UTF-8"));
//								out = new PrintWriter(socket.getOutputStream());
								writer = new BufferedWriter(new OutputStreamWriter(
										socket.getOutputStream(), "UTF-8"));
								while (!socket.isClosed()) {
									String str;
									str = in.readLine();
									// ���ӻ�ÿͻ��˷���������
									System.out.println("server print : " + str);
									if (str == null || str.equals("end"))
										break;
//									out.println("server feedback origin = " + str);
//									out.write("server feedback origin = " + str+"\n");
//									out.flush();
									writer.write(str + "\n");
									writer.flush();
									// ִ������
									str = exceCommand(str);
									// ��ͻ��˷�����Ϣ
									writer.write(str + "\n");
									writer.flush();
								}
								in.close();
//								out.close();
								writer.close();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								try {
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}).start();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					
				}
			}
		}
	}

	// ִ�пͻ��˷�������������������ڱ���ִ�пͻ��˷��͹�������Ϣ�������
	// ������ִ������󣬷��������صĽ����Ʃ�磬��linux��ִ�С�ls��������ص��ǵ�ǰĿ¼
	// ����������ļ�������
	private static String exceCommand(String command) {
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
			msg = "error";
		}

		return msg;
	}

	public static void main(final String args[]) throws IOException {
		SocketServer server = new SocketServer(mPort);
		server.beginListen();
	}

}