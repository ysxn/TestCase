package com.simple;

import java.io.*;
import java.net.*;

 public class Client {
	 private static int mPort = 43211;
	 private static String mSite = "107.170.224.94";
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
       
       SocketClient client = new SocketClient(mSite,mPort);
       System.out.println("client print : " +client.sendMsg("message 111"));

       System.out.println("client print : " +client.sendMsg("message 222"));

       System.out.println("client print : " +client.sendMsg("message 333"));

       System.out.println("client print : " +client.sendMsg("message 444"));

       client.closeSocket();
           
       }
   

}