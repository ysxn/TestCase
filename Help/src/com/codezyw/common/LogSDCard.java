package com.codezyw.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LogSDCard {
    static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");// 设置格式
	private static Process process = null; 

	public static void startLog() {
    //      if (process != null) {
    //      return;
    //  }
        String path = android.os.Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/autolog";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    //  List<String> commandList = new ArrayList<String>();  
    //  commandList.add("logcat");  
    //  commandList.add("-f");  
    //  commandList.add(path+File.separator+"logcat_"+mFormat.format(new Date())+".xt");  
    //  commandList.add("-v");  
    //  commandList.add("time");  
    //
    //  try {
            path = path+File.separator+"logcat_"+mFormat.format(new Date())+".ttxt";
    //      Log.i(TAG, cmd);
    //      process = Runtime.getRuntime().exec("logcat -f  "+cmd);
    ////                commandList.toArray(new String[commandList.size()]));  
    //      process.waitFor();
    //      Log.i(TAG, "finish");
    //  } catch (Exception e) {
    //      e.printStackTrace();
    //      Log.e(TAG,e.getMessage(), e);  
    //  }
        
        final StringBuilder log = new StringBuilder();
        try {        
            ArrayList<String> commandLine = new ArrayList<String>();
            commandLine.add("logcat");
            commandLine.add("-d");
    //      ArrayList<String> arguments = ((params != null) && (params.length > 0)) ? params[0] : null;
    //      if (null != arguments){
    //          commandLine.addAll(arguments);
    //      }
    
            Process process = Runtime.getRuntime().exec(commandLine.toArray(new String[commandLine.size()]));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    
            String line;
            while ((line = bufferedReader.readLine()) != null){ 
                log.append(line);
                log.append("\n"); 
            }
        } 
        catch (IOException e){
                //
        } 
    
    //  return log;
        File outFile = new File(path);
        try {
            outFile.createNewFile();
            FileOutputStream out = new FileOutputStream(outFile);
            out.write(log.toString().getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    //  
    //  FileOutputStream out = null;
    //  BufferedReader in = null;
    //  Process logcatProc = null;
    //  try {
    //      outFile.createNewFile();
    //      out = new FileOutputStream(outFile);
    //      logcatProc = Runtime.getRuntime()
    //              .exec("logcat -v time");
    //      in = new BufferedReader(new InputStreamReader(
    //              logcatProc.getInputStream()), 1024);
    //      String line = null;
    //      mLogRunning = true;
    //      while (mLogRunning
    //              && (line = in.readLine()) != null) {
    //          if (!mLogRunning) {
    //              break;
    //          }
    //          if (line.length() == 0) {
    //              continue;
    //          }
    //          if (out != null /* && line.contains(mPID) */) {
    //              out.write((line + "\n").getBytes());
    //          }
    //      }
    
    //  } catch (IOException e) {
    //      e.printStackTrace();
    //  } finally {
    //      Log.i(TAG, "finish logcat.");
    //      if (logcatProc != null) {
    //          logcatProc.destroy();
    //          logcatProc = null;
    //      }
    //      if (in != null) {
    //          try {
    //              in.close();
    //              in = null;
    //          } catch (IOException e) {
    //              e.printStackTrace();
    //          }
    //      }
    //      if (out != null) {
    //          try {
    //              out.close();
    //          } catch (IOException e) {
    //              e.printStackTrace();
    //          }
    //          out = null;
    //      }
    
    //  }
    }

    public static void stopLog() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
