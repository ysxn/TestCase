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
		// 创建Socket服务器
		CreateSocket();
	}

	public void run() {
		Socket client;
		String txt;
		try {
			// 线程无限循环，实时监听socket端口
			while (true) {
				client = ResponseSocket();

				// 响应客户端链接请求。。
				while (true) {
					txt = ReceiveMsg(client);
					// 链接获得客户端发来的命令
					System.out.println(txt);
					if (txt != null) {
						// 执行命令
						txt = exceCommand(txt);
					}
					// 向客户端返回消息
					SendMsg(client, txt);
					// 中断，关闭此次连接
					break;
				}
				CloseSocket(client);
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	// 创建一个ServerSocket实例，绑定到一个特定的端口。一个服务器端口最多可以支持50个链接，超出了则拒绝链接。
	// 所以，最多只能有50个客户端同时使用这个指定的端口向服务器发送消息
	private void CreateSocket() throws IOException {
		server = new ServerSocket(PORT);
		System.out.println("Server starting..");
	}

	/*
	 * ServerSocket：这个类是实现了一个服务器端的Socket，利用这个类可以监听来自网络的请求。 　　
	 * 1、创建ServerSocket的方法： 　　 ServerSocket(Int localPort) 　　 ServerSocket(int
	 * localport,int queueLimit) 　　 ServerSocket(int localport,int
	 * queueLimit,InetAddress localAddr) 　　
	 * 创建一个ServerSocket必须指定一个端口，以便客户端能够向该端口号发送连接请求。端口的有效范围是0-65535
	 * 　　2、ServerSocket操作 　　 Socket accept() 　　 void close 　　
	 * accept()方法为下一个传入的连接请求创建Socket实例，并将已成功连接的Socket实例返回给服务器套接字，如果没有连接请求，
	 * accept()方法将阻塞等待； 　　 close方法用于关闭套接字
	 */
	private Socket ResponseSocket() throws IOException {
		Socket client = server.accept();
		System.out.println("client connected..");
		return client;
	}

	// 关闭socket
	private void CloseSocket(Socket socket) throws IOException {
		reader.close();
		writer.close();
		socket.close();
		System.out.println("client closed..");
	}

	// 向客户端发送消息
	private void SendMsg(Socket socket, String Msg) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		writer.write(Msg + "\n");
		writer.flush();
	}

	// 接收来自客户端的消息。服务器通过server.accept();接收来自客户端的套接字，采用I/O方式
	// 将套接字的消息取出来
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

	// 执行客户端发送来的命令。服务器将在本机执行客户端发送过来的消息（命令）。
	// 并返回执行命令后，服务器返回的结果。譬如，在linux下执行“ls”命令，返回的是当前目录
	// 下面的所有文件的名称
	private String exceCommand(String command) {
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