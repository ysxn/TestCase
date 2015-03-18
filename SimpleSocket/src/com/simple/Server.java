
package com.simple;

import java.io.*;
import java.net.*;

public class Server {
	private static int mPort = 43211;

    public static class SocketServer {

        ServerSocket sever;

        public SocketServer(int port) {
            try {
                sever = new ServerSocket(port);
                System.out.println("create server ok.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void beginListen() {
            while (true) {
                try {
                    final Socket socket = sever.accept();

                    new Thread(new Runnable() {
                        public void run() {
                            BufferedReader in;
                            try {
                                in = new BufferedReader(new InputStreamReader(
                                        socket.getInputStream(), "UTF-8"));
                                PrintWriter out = new PrintWriter(socket.getOutputStream());
                                while (!socket.isClosed()) {
                                    String str;
                                    str = in.readLine();
                                    out.println("server feedback origin = " + str);
                                    //out.write("server feedback origin = " + str+"\n");
                                    out.flush();
                                    if (str == null || str.equals("end"))
                                        break;
                                    System.out.println("server print : "+str);
                                }
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] argvs) {
        SocketServer server = new SocketServer(mPort);
        server.beginListen();
    }
}
