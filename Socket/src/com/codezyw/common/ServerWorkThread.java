
package com.codezyw.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Socket服务器新建线程处理客户端链接
 */
public class ServerWorkThread extends Thread {
    /**
     * 定义保存所有的Socket
     */
    public static List<Socket> sSocketList = new ArrayList<Socket>();
    private Socket socket;
    // PrintWriter out;
    private BufferedWriter out;
    private BufferedReader in;

    public ServerWorkThread(Socket s) {
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
            // out = new PrintWriter(socket.getOutputStream());
            out = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(), "UTF-8"));
            while (!socket.isClosed()) {
                String cmd;
                cmd = in.readLine();
                // 链接获得客户端发来的命令
                System.out.println("server print : " + cmd);
                if (cmd == null || cmd.equals("end"))
                    break;
                // out.println("server feedback origin = " + str);
                // out.write("server feedback origin = " + str+"\n");
                // out.flush();
                out.write("server execute : " + cmd + "\n");
                // Runtime.getRuntime()返回当前应用程序的Runtime对象，该对象的exec()
                // 方法指示Java虚拟机创建一个子进程执行指定的可执行程序（命令），并返回与该子进程对应的Process对象实例。
                // 通过Process可以控制该子进程的执行或获取该子进程的信息
                Process process = Runtime.getRuntime().exec(cmd);
                InputStreamReader ir = new InputStreamReader(
                        process.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String line;
                while ((line = input.readLine()) != null) {
                    log(line);
                    out.write(line + "\n");
                }
                out.flush();
            }
            log("-----------------------ServerWorkThread finish");
        } catch (Exception e) {
            e.printStackTrace();
            log("ServerWorkThread : Exception thread");
        } finally {
            log("ServerWorkThread : finally thread");
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
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
