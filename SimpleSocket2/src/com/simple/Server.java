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
				// 创建Socket服务器
				// “输入连接指示（对连接的请求）的最大队列长度被设置为 backlog 参数。如果队列满时收到连接指示，则拒绝该连接。”
				// 对这句话的理解是backlog表示的是连接请求的队列长度，而不是请求连接的客户端的数目，所以虽然这里同时建立了几十个client，
				// 但是可能client一发出连接请求server马上就接受了，所以是否请求拒绝取决于server处理连接请求的速度。
				// 如果server来不及处理连接请求，才把请求放入队列，一旦请求队列满了，就拒绝。
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
					 * ServerSocket：这个类是实现了一个服务器端的Socket，利用这个类可以监听来自网络的请求。 　　
					 * 1、创建ServerSocket的方法： 　　 ServerSocket(Int localPort) 　　
					 * ServerSocket(int localport,int queueLimit) 　　
					 * ServerSocket(int localport,int queueLimit,InetAddress
					 * localAddr) 　　
					 * 创建一个ServerSocket必须指定一个端口，以便客户端能够向该端口号发送连接请求。
					 * 端口的有效范围是0-65535 　　2、ServerSocket操作 　　 Socket accept() 　　
					 * void close 　　
					 * accept()方法为下一个传入的连接请求创建Socket实例，并将已成功连接的Socket实例返回给服务器套接字
					 * ，如果没有连接请求， accept()方法将阻塞等待； 　　 close方法用于关闭套接字
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
									// 链接获得客户端发来的命令
									System.out.println("server print : " + str);
									if (str == null || str.equals("end"))
										break;
//									out.println("server feedback origin = " + str);
//									out.write("server feedback origin = " + str+"\n");
//									out.flush();
									writer.write(str + "\n");
									writer.flush();
									// 执行命令
									str = exceCommand(str);
									// 向客户端返回消息
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

	// 执行客户端发送来的命令。服务器将在本机执行客户端发送过来的消息（命令）。
	// 并返回执行命令后，服务器返回的结果。譬如，在linux下执行“ls”命令，返回的是当前目录
	// 下面的所有文件的名称
	private static String exceCommand(String command) {
		String msg = "";
		try {
			// Runtime.getRuntime()返回当前应用程序的Runtime对象，该对象的exec()
			// 方法指示Java虚拟机创建一个子进程执行指定的可执行程序（命令），并返回与该子进程对应的Process对象实例。
			// 通过Process可以控制该子进程的执行或获取该子进程的信息

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