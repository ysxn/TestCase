package com.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.LineNumberReader;

public class ServerShort {

	private static int mPort = 43211;

	public static class ServerThread extends Thread {
        private Socket socket;
        private BufferedWriter out;
        private BufferedReader in;
//      PrintWriter out;
        
        public ServerThread(Socket s) {
            socket = s;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), "UTF-8"));
//              out = new PrintWriter(socket.getOutputStream());
                out = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), "UTF-8"));
                while (!socket.isClosed()) {
                    String cmd;
                    cmd = in.readLine();
                    // ���ӻ�ÿͻ��˷���������
                    System.out.println("server print : " + cmd);
                    if (cmd == null || cmd.equals("end"))
                        break;
//                  out.println("server feedback origin = " + str);
//                  out.write("server feedback origin = " + str+"\n");
//                  out.flush();
                    out.write("server execute : "+cmd + "\n");
                    // Runtime.getRuntime()���ص�ǰӦ�ó����Runtime���󣬸ö����exec()
                    // ����ָʾJava���������һ���ӽ���ִ��ָ���Ŀ�ִ�г������������������ӽ��̶�Ӧ��Process����ʵ����
                    // ͨ��Process���Կ��Ƹ��ӽ��̵�ִ�л��ȡ���ӽ��̵���Ϣ

                    Process process = Runtime.getRuntime().exec(cmd);
                    InputStreamReader ir = new InputStreamReader(
                            process.getInputStream());
                    LineNumberReader input = new LineNumberReader(ir);
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                        out.write(line + "\n");
                    }
                    out.flush();
                    
                    socket.close();
                    in.close();
                    out.close();
                }
                System.out.println("-----------------------Thread finish");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("server print : Exception thread");
            } finally {
                System.out.println("server print : finally thread");
                if (in != null) {
                    try {
                        in.close();
                        System.out.println("server print : in.close");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
                if (out != null) {
                    try {
                        out.close();
                        System.out.println("server print : out.close");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                try {
                    if (!socket.isClosed()) {
                        socket.close();
                        System.out.println("server print : socket.close");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
	
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
					new ServerThread(socket).start();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("server print : Exception main");
				} finally {
				}
			}
		}
	}

	public static void main(final String args[]) throws IOException {
		SocketServer server = new SocketServer(mPort);
		server.beginListen();
	}

}