package sleek.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sleek.chat.database.SqlBean;

public class Client implements Runnable {
    private Socket s = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private boolean bConnected = false;
    private String sendmsg=null;
    private String getnameString=null;
    public List<Map<Object, Object>> getList;
    public Client (Socket s) {
       this.s = s;
       try {
         dis = new DataInputStream (s.getInputStream());
         dos = new DataOutputStream (s.getOutputStream());
         bConnected = true;
       } catch(IOException e) {
             e.printStackTrace();
          }
    }
    
    public void send (String str) {
   	 
        try {
       	 //System.out.println(s);
       	 dos.writeUTF(str+"");
       	 dos.flush();
        } catch(IOException e) {
       	 Server.clients.remove(this);
       	 System.out.println("对方已经退出了");
        }
    }
    public void run() {
        try {
           while (bConnected) {
               String str = dis.readUTF();
               DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
               String date = "  ["+df.format(new Date())+"]";
               String date2 = df.format(new Date());
               //online handle
               if(str.startsWith(config.PROTOCOL_KEY+config.PROTOCOL_ONLINE)){
            	   
            	   //save client's name when online
            	   getnameString = str.substring(config.PROTOCOL_KEY.length()+config.PROTOCOL_ONLINE.length());
            	   Server.onlineMap.put(getnameString, s);
            	   
	  
            	   //all online clients , them made to onlineString and send 
            	   String onlineString="";
            	   for (Iterator iterator=Server.onlineMap.entrySet().iterator(); iterator.hasNext();) {
	               		Map.Entry elementEntry = (Map.Entry) iterator.next();
	               		String strKeyObject = (String) elementEntry.getKey();
	               		onlineString+=(strKeyObject+config.PROTOCOL_FRIENDS_SEPARATE);
	               }
            	   
            	   for (Iterator iterator=Server.onlineMap.entrySet().iterator(); iterator.hasNext();) {
	               		Map.Entry elementEntry = (Map.Entry) iterator.next();
	               		String clientKey = (String) elementEntry.getKey();
	               		
	               		//update online friends
	            		DataOutputStream onlineDOS = new DataOutputStream (Server.onlineMap.get(clientKey).getOutputStream());
	            		onlineDOS.writeUTF(config.PROTOCOL_FRIENDS_START+onlineString+config.PROTOCOL_FRIENDS_END);
	            		onlineDOS.flush();
	               }
//	               	System.out.println(onlineString);
//	                for (int i=0; i<Server.clients.size(); i++) {
//	                  Client c = Server.clients.get(i);
//	                  //c.send(getnameString+" on line."+date);
//	                  c.send(config.PROTOCOL_FRIENDS_START+onlineString+config.PROTOCOL_FRIENDS_END);
//	                }
	                System.out.println(getnameString+" on line."+date);
               }
               //offline handle
               else if(str.startsWith(config.PROTOCOL_KEY+config.PROTOCOL_OFFLINE)){
	               	getnameString = str.substring(config.PROTOCOL_KEY.length()+config.PROTOCOL_OFFLINE.length());
	               	Server.clients.remove(this);
	               	Server.onlineMap.remove(getnameString);
	               	Server.socketMap.remove(getnameString);
	               	String onlineString="";
	               	for (Iterator iterator=Server.onlineMap.entrySet().iterator(); iterator.hasNext();) {
	               		Map.Entry elementEntry = (Map.Entry) iterator.next();
	               		String strKeyObject = (String) elementEntry.getKey();
	               		onlineString+=(strKeyObject+config.PROTOCOL_FRIENDS_SEPARATE);
	               	}
	               	
	               	for (Iterator iterator=Server.onlineMap.entrySet().iterator(); iterator.hasNext();) {
	               		Map.Entry elementEntry = (Map.Entry) iterator.next();
	               		String clientoffKey = (String) elementEntry.getKey();
	               		//update offline friends
	            		DataOutputStream onlineDOS = new DataOutputStream (Server.onlineMap.get(clientoffKey).getOutputStream());
	            		onlineDOS.writeUTF(config.PROTOCOL_FRIENDS_START+onlineString+config.PROTOCOL_FRIENDS_END);
	            		onlineDOS.flush();
	               	}
//	               	System.out.println(onlineString);
//	               	for (int i=0; i<Server.clients.size(); i++) {
//	               		Client c = Server.clients.get(i);
//		                //c.send(getnameString+" off line."+date);
//			            c.send(config.PROTOCOL_FRIENDS_START+onlineString+config.PROTOCOL_FRIENDS_END);
//			        }
		            System.out.println(getnameString+" off line."+date);
               }
               else if(str.startsWith(config.PROTOCOL_KEY+config.PROTOCOL_WAIT)){
            	   
            	   //save client's name when online to wait
            	   getnameString = str.substring(config.PROTOCOL_KEY.length()+config.PROTOCOL_WAIT.length());
            	   Server.socketMap.put(getnameString, s);
            	   System.out.println(getnameString+" is waiting on "+s);
            	   
            	   //send the offline time chat
            	   getList = SqlBean.executeQuery("select cmID,cmContent,cmFrom,cmPostdate" +
            	   		" from chat_message where cmTo='"+getnameString+"' and cmRead=0 and cmDelete=0");
            	   for(int i=0;i<getList.size();i++){
            		   String cmContent=null,cmFrom=null,cmPostdate=null;
            		   Long cmID=null;
            		   Set<Map.Entry<Object, Object>> set = getList.get(i).entrySet();
            		   for (Iterator<Map.Entry<Object, Object>> it = set.iterator(); it.hasNext();) {
            			   Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            			   if(entry.getKey().equals("cmContent")) cmContent=(String) entry.getValue();
            			   else if(entry.getKey().equals("cmFrom")) cmFrom=(String) entry.getValue();
            			   else if(entry.getKey().equals("cmPostdate")) {
            				   cmPostdate = "  ["+df.format(entry.getValue())+"]";
            			   }
            			   else if(entry.getKey().equals("cmID")) cmID= (Long)entry.getValue();
            		   }
            		   if(cmFrom!=null && cmFrom!=""){
            			   try {
            				   DataOutputStream onlineDOS = new DataOutputStream (s.getOutputStream());
    	            		   onlineDOS.writeUTF(cmFrom+cmPostdate+"\n"+cmContent);
    	            		   onlineDOS.flush();
    	            		   System.out.println("get offline message");
    	            		   System.out.println(cmFrom+cmPostdate+"\n"+cmContent);
    	            		   SqlBean.executeUpdate("UPDATE chat_message SET cmRead=1,cmLastupdate='"+date2+"'" +
    	            		   		"WHERE cmID='"+cmID+"'");
            			   } catch (Exception e) {
							// TODO: handle exception
            				   
            			   }
            		   }
          		   }
               }
               
               //chat for handle
               else if(str.startsWith(config.PROTOCOL_KEY+config.PROTOCOL_FOR)){
            	   int forend = str.indexOf(config.PROTOCOL_FOR_END);
            	   //System.out.println(str);
            	   if(forend>0){
            		   int from = str.indexOf("end;");
            		   int issend=0;
            		   String fromname = str.substring(str.indexOf(config.PROTOCOL_FOR_END)+1, from);
            		   String forname = str.substring(config.PROTOCOL_KEY.length()+config.PROTOCOL_FOR.length(),forend);
            		   String forchat = str.substring(from+4);
            		   for (Iterator iterator=Server.socketMap.entrySet().iterator(); iterator.hasNext();) {
                     		Map.Entry elementEntry = (Map.Entry) iterator.next();
                     		String strKeyObject = (String) elementEntry.getKey();
                     		if(strKeyObject.equals(forname)){
                     			issend++;
                     		}
                     	}
            		   if(issend>0){
            		   System.out.println("forname:"+forname+"  fromname:"+fromname+"  :"+ forchat  +Server.socketMap.get(forname));
            		   
            		   //send to friend
            		   DataOutputStream ndos = new DataOutputStream (Server.socketMap.get(forname).getOutputStream());
            		  // System.out.println(Server.socketMap.get(forname));
            		   ndos.writeUTF(fromname+date+"\n"+forchat);
            		   ndos.flush();
            		   
            		   //send to me
            		   //DataOutputStream ndos2 = new DataOutputStream (Server.socketMap.get(fromname).getOutputStream());
            		   //String forchat2 = str.substring(from+4);
            		   //ndos2.writeUTF(fromname+date+"\n"+forchat2);
            		   //ndos2.flush();
            		   }else{
            			   System.out.println(forname+" is offline , save the message");
//            			   forchat=new String(forchat.getBytes("gbk"), "utf-8");
//            			   fromname=new String(fromname.getBytes("gbk"), "utf-8");
//            			   forname=new String(forname.getBytes("gbk"), "utf-8");
            			   SqlBean.executeUpdate("INSERT INTO chat_message(" +
            			   		"cmContent," +
            			   		"cmFrom," +
            			   		"cmTo," +
            			   		"cmPostdate," +
            			   		"cmLastupdate," +
            			   		"cmType" +
            			   		")VALUE(" +
            			   		"'"+forchat+"'," +
            			   		"'"+fromname+"'," +
            			   		"'"+forname+"'," +
            			   		"'"+date2+"'," +
            			   		"'"+date2+"'," +
            			   		"'text'" +
            			   		")");
            		   }
            	   }else{
            		   String forname = str.substring(config.PROTOCOL_KEY.length()+config.PROTOCOL_FOR.length());
            		   System.out.println(str);
            		   int isonline=0;
            		   for (Iterator iterator=Server.socketMap.entrySet().iterator(); iterator.hasNext();) {
                    		Map.Entry elementEntry = (Map.Entry) iterator.next();
                    		String strKeyObject = (String) elementEntry.getKey();
                    		if(strKeyObject.equals(forname)){
                    			isonline++;
                    		}
                    	}
	           		   if(isonline>0){
	            		   DataOutputStream ndos = new DataOutputStream (Server.socketMap.get(forname).getOutputStream());
	            		   ndos.writeUTF("a client try chat to you");
	            		   ndos.flush();
	           		   }else{
	           			   System.out.println(forname+" is offline");
	           		   }
            	   }
               }
//               System.out.println("onlineMap:");
//              	for (Iterator iterator=Server.onlineMap.entrySet().iterator(); iterator.hasNext();) {
//               		Map.Entry elementEntry = (Map.Entry) iterator.next();
//               		String strKeyObject = (String) elementEntry.getKey();
//               		Socket strObject = (Socket) elementEntry.getValue();
//               		System.out.println("key:"+strKeyObject+"  value:"+strObject);
//               	}
//              	System.out.println("------------------------------------------------------------------");
//              	System.out.println("socketMap:");
//              	for (Iterator iterator=Server.socketMap.entrySet().iterator(); iterator.hasNext();) {
//               		Map.Entry elementEntry = (Map.Entry) iterator.next();
//               		String strKeyObject = (String) elementEntry.getKey();
//               		Socket strObject = (Socket) elementEntry.getValue();
//               		System.out.println("key:"+strKeyObject+"  value:"+strObject);
//               	}
//              	System.out.println("------------------------------------------------------------------");
//              	System.out.println("clients:");
//              	for (int i=0; i<Server.clients.size(); i++) {
//              		System.out.println(Server.clients.get(i).s);
//		        }
//              	System.out.println(" ");
               //message handle
//               else{
//            	   int charend = str.indexOf("end;");
//            	   String chatString = str.substring(charend+4);
//            	   String chatName = str.substring(25, charend);
//               	
//            	   sendmsg=chatName+date+"\n"+chatString; 
//            	   for (int i=0; i<Server.clients.size(); i++) {
//            		   Client c = Server.clients.get(i);
//            		   System.out.println(c);
//            		   c.send(sendmsg);
//            		   
//            	   }	
//            	   System.out.println(sendmsg);
//               }
            }
        } catch (SocketException e) {
            System.out.println("client is closed!");
            Server.clients.remove(this);
        } catch (EOFException e) {
              System.out.println("client is closed!");
              Server.clients.remove(this);
           }
           catch (IOException e) {
              e.printStackTrace();
           }
          finally {
            try {
              if (dis != null) dis.close();
              if (dos != null) dos.close();
              if (s != null) s.close();
            } catch (IOException e) {
                  e.printStackTrace();
              }
          }
    }
 }