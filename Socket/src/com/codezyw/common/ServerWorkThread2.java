
package com.codezyw.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Socket服务器新建线程处理客户端链接
 */
public class ServerWorkThread2 extends Thread {
    /**
     * 定义保存所有的Socket
     */
    public static List<Socket> sSocketList = new ArrayList<Socket>();
    private Socket socket;
    // private BufferedWriter out;
    private BufferedReader in;
    private PrintWriter out;

    public ServerWorkThread2(Socket s) {
        socket = s;
        if (s != null) {
            sSocketList.add(s);
        }
    }

    public void run() {
        if (socket == null) {
            return;
        }
        try {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(socket.getOutputStream());
            while (!socket.isClosed()) {
                String str;
                str = in.readLine();
                out.println("server feedback origin = " + str);
                // out.write("server feedback origin = " + str+"\n");
                out.flush();
                if (str == null || str.equals("end"))
                    break;
                log(str);
            }
            log("-----------------------ServerWorkThread finish");
        } catch (Exception e) {
            e.printStackTrace();
            log("ServerWorkThread : Exception");
        } finally {
            log("ServerWorkThread : finally");
            close();
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
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(String s) {
        System.out.println("Server Log {" + s + "}");
    }
}
