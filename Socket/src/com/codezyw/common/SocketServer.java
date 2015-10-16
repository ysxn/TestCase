
package com.codezyw.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建Socket服务器，监听客户端连接
 */
public class SocketServer {
    private ServerSocket mSever = null;
    private int mIndex = 0;

    public SocketServer(int port) {
        try {
            /**
             * <pre>
             * “输入连接指示（对连接的请求）的最大队列长度被设置为 backlog 参数。如果队列满时收到连接指示，则拒绝该连接。”
             * 对这句话的理解是backlog表示的是连接请求的队列长度，而不是请求连接的客户端的数目，所以虽然这里同时建立了几十个client，
             * 但是可能client一发出连接请求server马上就接受了，所以是否请求拒绝取决于server处理连接请求的速度。
             * 如果server来不及处理连接请求，才把请求放入队列，一旦请求队列满了，就拒绝。
             * </pre>
             */
            mSever = new ServerSocket(port, 50);
            log("create server ok.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void close() {
        if (mSever != null) {
            try {
                mSever.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void beginListen() {
        if (mSever == null) {
            return;
        }
        while (true) {
            try {
                /**
                 * <pre>
                 * ServerSocket：这个类是实现了一个服务器端的Socket，利用这个类可以监听来自网络的请求。
                 * 1、创建ServerSocket的方法： 　　
                 *  ServerSocket(int localPort)
                 *  ServerSocket(int localPort,int queueLimit)
                 *  ServerSocket(int localPort,int queueLimit,InetAddress localAddr)
                 * 创建一个ServerSocket必须指定一个端口，以便客户端能够向该端口号发送连接请求。 端口的有效范围是0-65535
                 * 2、ServerSocket操作 　　
                 *  Socket accept() 　　 
                 *  void close 　　
                 * accept()方法为下一个传入的连接请求创建Socket实例，并将已成功连接的Socket实例返回给服务器套接字 ，如果没有连接请求，accept()方法将阻塞等待； 　　 
                 * close方法用于关闭套接字
                 * </pre>
                 */
                final Socket socket = mSever.accept();
                mIndex++;
                log("client connected...sIndex=" + mIndex);
                new ServerWorkThread2(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
                log("Exception main");
            } finally {
            }
        }
    }

    private void log(String s) {
        System.out.println("Server Log {" + s + "}");
    }
}
