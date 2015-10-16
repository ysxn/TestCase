
package com.simple;

import com.codezyw.common.SocketClient;

public class Client {
    private static int sPort = 43211;
    private static String sSite = "127.0.0.1";// "107.170.224.94";

    public static void main(String[] args) {
        SocketClient client = new SocketClient(sSite, sPort);
        log(client.writeString("message 111"));
        log(client.writeString("message 222"));
        log(client.writeString("message 333"));
        log(client.writeString("message 444"));
        client.close();
    }

    private static void log(String s) {
        System.out.println("Client Log {" + s + "}");
    }
}
