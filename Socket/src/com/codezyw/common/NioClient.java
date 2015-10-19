
package com.codezyw.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * TCP/IP的NIO非阻塞方式 客户端
 */
public class NioClient {
    /**
     * 服务器地址
     */
    private static String mHostName = "localhost";
    /**
     * 创建缓冲区
     */
    private ByteBuffer buffer = ByteBuffer.allocate(512);

    /**
     * 访问服务器
     * 
     * @param host
     * @param port
     */
    public void connect(String host, int port) {
        SocketChannel socket = null;
        byte[] bytes = new byte[512];
        while (true) {
            try {
                InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
                System.in.read(bytes);
                socket = SocketChannel.open();
                socket.connect(address);
                buffer.clear();
                buffer.put(bytes);
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new NioClient().connect(mHostName, 8099);
    }
}
