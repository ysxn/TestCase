package com.simple;

import java.io.*;
import java.net.*;

 public class Client {
     public static class SocketClient {
         static Socket client;
         
         public SocketClient(String site, int port){
             try{
                 client = new Socket(site,port);
                 System.out.println("Client is created! site:"+site+" port:"+port);
             }catch (UnknownHostException e){
                 e.printStackTrace();
             }catch (IOException e){
                 e.printStackTrace();
             }
         }
         
         public String sendMsg(String msg){
             try{
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream());
                 out.println(msg);
                 out.flush();
                 return in.readLine();
             }catch(IOException e){
                 e.printStackTrace();
             }
             return "";
         }
         public void closeSocket(){
             try{
                 client.close();
             }catch(IOException e){
                 e.printStackTrace();
             }
         }

     }
    
   public static void main(String[] args){
       
       SocketClient client = new SocketClient("127.0.0.1",12345);
       System.out.println(client.sendMsg("nimei1"));
       client.closeSocket();
       
       SocketClient client1 = new SocketClient("127.0.0.1",12345);
       System.out.println(client1.sendMsg("nimei1111"));
       client1.closeSocket();
       
       SocketClient client11 = new SocketClient("127.0.0.1",12345);
       System.out.println(client11.sendMsg("nimei11111111"));
       client11.closeSocket();
       
       SocketClient client111 = new SocketClient("127.0.0.1",12345);
       System.out.println(client111.sendMsg("nimei11111111111111111"));
           client111.closeSocket();
           
       }
   

}