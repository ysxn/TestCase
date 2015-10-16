
package sleek.chat;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Server {

    ServerSocket ss = null;
    boolean started = false;
    public static List<Client> clients = new ArrayList<Client>();
    static Map<String, Socket> socketMap = new HashMap<String, Socket>();
    static Map<String, Socket> onlineMap = new HashMap<String, Socket>();
    List<Info> infos = new ArrayList<Info>();

    public static void main(String[] args) {
        String inputport = JOptionPane.showInputDialog("請輸入該服務器使用的端口:");
        int port = Integer.parseInt(inputport);
        new Server().start(port);
    }

    public void start(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("服務器啟動");
            started = true;
        } catch (BindException e) {
            System.out.println(" 端口已经被占用");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (started) {
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("a client is connected");
                new Thread(c).start();
                clients.add(c);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Info {
        private String info_name = null;

        public Info() {

        }

        public void setName(String name) {
            info_name = name;
        }

        public String getName() {
            return info_name;
        }
    }
}
