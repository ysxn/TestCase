package sleek.chat;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.naming.InitialContext;
import javax.swing.JOptionPane;

public class MyClient extends Frame {

    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    private boolean bconnected = false;
    TextField tf = new TextField();
    TextArea ta = new TextArea();
    private static String name;
    public static void main(String[] args) {
    	name = JOptionPane.showInputDialog("请输入姓名:");
        new MyClient().launchFrame("192.168.2.214",8888);
    }
  
    public void launchFrame(String ip,int port) {
        setBounds(400,300,300,300);
        add(tf,BorderLayout.SOUTH);
        add(ta,BorderLayout.NORTH);
        addWindowListener (new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disConnect();
                System.exit(0);
            }
        });
       tf.addActionListener(new TFText());
       setVisible(true);
       connect(ip,port);
       new Thread(new ReceiveThread()).start();
    }

   public void connect(String ip,int port) {
       try {
         s = new Socket(ip, port);
         dos = new DataOutputStream (s.getOutputStream());
         dis = new DataInputStream (s.getInputStream());
         dos.writeUTF(config.PROTOCOL_KEY+config.PROTOCOL_ONLINE+name);
         System.out.println("connect");
         bconnected = true;
       } catch (UnknownHostException e) {
             e.printStackTrace();
      }  catch (IOException e) {
             e.printStackTrace();
      }
   }
   
   public void chatconnect(String ip,int port) {
       try {
         s = new Socket(ip, port);
         dos = new DataOutputStream (s.getOutputStream());
         dis = new DataInputStream (s.getInputStream());
         dos.writeUTF(config.PROTOCOL_KEY+config.PROTOCOL_ONLINE+name);
         System.out.println("connect");
         bconnected = true;
       } catch (UnknownHostException e) {
             e.printStackTrace();
      }  catch (IOException e) {
             e.printStackTrace();
      }
   }
  
   public void disConnect() {
       try {
    	   dos.writeUTF(config.PROTOCOL_KEY+config.PROTOCOL_OFFLINE+name);
    	   s.close();
       } catch (IOException e) {
             e.printStackTrace();
       }
   }

  private class TFText implements ActionListener {
     public void actionPerformed(ActionEvent e) {
    	 
         String str = tf.getText().trim();
         tf.setText("");
         try {
        	 dos.writeUTF(config.PROTOCOL_KEY+config.PROTOCOL_FOR+"cnblog"+config.PROTOCOL_FOR_END+name+"end;"+str);
         } catch (IOException e1) {
               e1.printStackTrace();
         }
     }
  }

 private class ReceiveThread implements Runnable {
    public void run() {
        try {
        	
           while (bconnected) {
        	   String str = dis.readUTF();
               ta.setText(ta.getText()+str+'\n');
           }
        } catch (SocketException e) {
              System.out.println("ÍË³öÁË");
        } catch (IOException e) {
              e.printStackTrace();
        }
    }
 }
}