
package com.codezyw.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {
    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    /**
     * 构造一个socket客户端
     * 
     * @param site
     * @param port The range for valid port numbers is between 0 and 65535 inclusive.
     */
    public SocketClient(String site, int port) {
        try {
            // 创建一个socket实例
            client = new Socket();
            // 将此选项设为非零的超时值时，在与此 Socket关联的 InputStream 上调用 read() 将只阻塞此时间长度。
            // 如果超过超时值，将引发 java.net.SocketTimeoutException，不过 Socket 仍旧有效。
            // setSoTimeout调用必须在进入阻塞操作前才能生效。
            client.setSoTimeout(5000);
            // 建立一个远程链接, 3秒内连接不上则超时
            client.connect(new InetSocketAddress(site, port), 3000);
            // 判断是否链接成功
            if (client.isConnected()) {
                log("Server Connented");
            } else {
                log("连接服务器失败！");
                return;
            }
            // client = new Socket(site, port);
            log("Client is created! site:" + site + " port:" + port);
            in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            out = new PrintWriter(client.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void close() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            out.close();
        }
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void log(String s) {
        System.out.println("Client Log {" + s + "}");
    }

    public String writeString(String msg) {
        if (in == null || out == null) {
            return null;
        }
        try {
            out.println(msg);
            out.flush();
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
