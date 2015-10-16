
package com.simple;

import com.codezyw.common.SocketServer;

public class Server {
    private static int sPort = 43211;

    public static void main(String[] argvs) {
        SocketServer server = new SocketServer(sPort);
        server.beginListen();
    }
}
