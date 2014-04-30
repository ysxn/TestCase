
package com.example.testphonenumber;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class HttpHelper {
    private final static String DEBUG_TAG = "zhuyawen";
    public static final int MSG_SHOW_RESULT = 0x123;
    public static final String MSG_DATA_KEY = "result";
    private Handler mHandler;

    public void setMsgHandler(Handler handler) {
        mHandler = handler;
    }

    /**
     * HttpClient实际上是对Java提供方法的一些封装，在HttpURLConnection中的输入输出流操作，
     * 在这个接口中被统一封装成了HttpPost(HttpGet)和HttpResponse，这样，就减少了操作的繁琐性。
     * 另外，在使用POST方式进行传输时，需要进行字符编码。
     */
    public void methodHttpGet(String passUrl) {
        String httpUrl = "http://www.baidu.com";
        if (passUrl != null) {
            httpUrl = passUrl;
        }
        String resultData = null;
        // 生成一个请求对象
        HttpGet httpGet = new HttpGet(httpUrl);
        // 生成一个Http客户端对象
        HttpClient httpClient = new DefaultHttpClient();
        // 使用http客户端发送请求对象
        InputStream inputStream = null;

        try {
            // httpResponse就是代表响应对象
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Log.i(DEBUG_TAG, ">>>>HttpStatus :" + httpResponse.getStatusLine().getStatusCode());
            // 请求成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // httpEntity包含的就是返回的消息内容
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder("");
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                resultData = result.toString();
            } else
            {
                resultData = "请求错误!";
            }
            if (resultData == null || resultData.isEmpty()) {
                resultData = "读取的内容为NULL";
            }
            Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, resultData);
            msg.sendToTarget();
        } catch (ClientProtocolException e) {
            Log.e(DEBUG_TAG, "ClientProtocolException");
            e.printStackTrace();

        } catch (IOException e) {
            Log.e(DEBUG_TAG, "IOException");
            e.printStackTrace();

        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                Log.e(DEBUG_TAG, "Exception");
                e2.printStackTrace();
            }
        }
    }

    /**
     * HttpClient实际上是对Java提供方法的一些封装，在HttpURLConnection中的输入输出流操作，
     * 在这个接口中被统一封装成了HttpPost(HttpGet)和HttpResponse，这样，就减少了操作的繁琐性。
     * 另外，在使用POST方式进行传输时，需要进行字符编码。
     */
    public void methodHttpGetAlias(String passUrl) {
        // http地址
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp?par=HttpClient_android_Get";
        if (passUrl != null) {
            httpUrl = passUrl;
        }
        String resultData = null;
        // HttpGet连接对象
        HttpGet httpRequest = new HttpGet(httpUrl);
        try
        {
            // 取得HttpClient对象
            HttpClient httpclient = new DefaultHttpClient();
            // 请求HttpClient，取得HttpResponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            Log.i(DEBUG_TAG, ">>>>HttpStatus :" + httpResponse.getStatusLine().getStatusCode());
            // 请求成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 取得返回的字符串
                resultData = EntityUtils.toString(httpResponse.getEntity());
            }
            else
            {
                resultData = "请求错误!";
            }
            if (resultData == null || resultData.isEmpty()) {
                resultData = "读取的内容为NULL";
            }
            Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, resultData);
            msg.sendToTarget();
        } catch (ClientProtocolException e)
        {
            Log.e(DEBUG_TAG, "ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.e(DEBUG_TAG, "IOException");
            e.printStackTrace();
        } catch (Exception e)
        {
            Log.e(DEBUG_TAG, "Exception");
            e.printStackTrace();
        }

    }

    /**
     * HttpClient实际上是对Java提供方法的一些封装，在HttpURLConnection中的输入输出流操作，
     * 在这个接口中被统一封装成了HttpPost(HttpGet)和HttpResponse，这样，就减少了操作的繁琐性。
     * 另外，在使用POST方式进行传输时，需要进行字符编码。
     */
    public void methodHttpPost(String par, List<NameValuePair> params) {
        // http地址
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp";
        if (par != null) {
            httpUrl = par;
        }
        String resultData = null;
        // HttpPost连接对象
        HttpPost httpRequest = new HttpPost(httpUrl);
        // 使用NameValuePair来保存要传递的Post参数
        // List<NameValuePair> params = new ArrayList<NameValuePair>();

        // 添加要传递的参数
        // params.add(new BasicNameValuePair("par", "HttpClient_android_Post"));
        try
        {
            // 设置字符集
            HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
            // 请求httpRequest
            httpRequest.setEntity(httpentity);
            // 取得默认的HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 取得HttpResponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            // HttpStatus.SC_OK表示连接成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 取得返回的字符串
                resultData = EntityUtils.toString(httpResponse.getEntity());
            }
            else
            {
                resultData = "请求错误!";
            }
            if (resultData == null || resultData.isEmpty()) {
                resultData = "读取的内容为NULL";
            }
            Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, resultData);
            msg.sendToTarget();
        } catch (ClientProtocolException e)
        {
            Log.e(DEBUG_TAG, "ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.e(DEBUG_TAG, "IOException");
            e.printStackTrace();
        } catch (Exception e)
        {
            Log.e(DEBUG_TAG, "Exception");
            e.printStackTrace();
        }
    }

    /**
     * methodGet 以Get方式上传参数
     */
    public void methodGet(String par) {
        // http地址"?par=abcdefg"是我们上传的参数
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp?par=abcdefg";
        if (par != null) {
            httpUrl = par;
        }

        // 获得的数据
        String resultData = "";
        URL url = null;
        try
        {
            // 构造一个URL对象
            url = new URL(httpUrl);
        } catch (MalformedURLException e)
        {
            Log.e(DEBUG_TAG, "MalformedURLException");
        }
        if (url != null)
        {
            try
            {
                // 使用HttpURLConnection打开连接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // 得到读取的内容(流)
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                // 为输出创建BufferedReader
                BufferedReader buffer = new BufferedReader(in);
                String inputLine = null;
                // 使用循环来读取获得的数据
                Log.i(DEBUG_TAG,
                        "*****************************start*******************************");
                while (((inputLine = buffer.readLine()) != null))
                {
                    // 我们在每一行后面加上一个"\n"来换行
                    resultData += inputLine + "\n";
                    Log.i(DEBUG_TAG, inputLine);
                }
                Log.i(DEBUG_TAG, "*****************************end*******************************");
                // 关闭InputStreamReader
                in.close();
                // 关闭http连接
                urlConn.disconnect();
                // 设置显示取得的内容
                if (resultData == null || resultData.isEmpty()) {
                    resultData = "读取的内容为NULL";
                }
                Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, resultData);
                msg.sendToTarget();

            } catch (IOException e)
            {
                Log.e(DEBUG_TAG, "IOException");
                e.printStackTrace();
            }
        }
        else
        {
            Log.e(DEBUG_TAG, "Url NULL");
        }
    }

    /**
     * methodPost 以post方式上传参数
     * 
     * @param textView
     */
    public void methodPost(String passUrl, String key, String value) {
        // http地址"?par=abcdefg"是我们上传的参数
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp";
        if (passUrl != null) {
            httpUrl = passUrl;
        }
        // 获得的数据
        String resultData = "";
        URL url = null;
        try
        {
            // 构造一个URL对象
            url = new URL(httpUrl);
        } catch (MalformedURLException e)
        {
            Log.e(DEBUG_TAG, "MalformedURLException");
        }
        if (url != null)
        {
            try
            {
                // 使用HttpURLConnection打开连接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // 因为这个是post请求,设立需要设置为true
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                // 设置以POST方式
                urlConn.setRequestMethod("POST");
                // Post 请求不能使用缓存
                urlConn.setUseCaches(false);
                urlConn.setInstanceFollowRedirects(true);
                // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                // 要注意的是connection.getOutputStream会隐含的进行connect。
                urlConn.connect();
                // DataOutputStream流
                DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
                // 要上传的参数
                // String content = "par=" + URLEncoder.encode("ABCDEFG",
                // "gb2312");
                String content = key + "=" + URLEncoder.encode(value, "gb2312");
                // 将要上传的内容写入流中
                out.writeBytes(content);
                // 刷新、关闭
                out.flush();
                out.close();
                // 获取数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        urlConn.getInputStream()));
                String inputLine = null;
                // 使用循环来读取获得的数据
                Log.i(DEBUG_TAG,
                        "*****************************start*******************************");
                while (((inputLine = reader.readLine()) != null))
                {
                    // 我们在每一行后面加上一个"\n"来换行
                    resultData += inputLine + "\n";
                    Log.i(DEBUG_TAG, inputLine);
                }
                Log.i(DEBUG_TAG, "*****************************end*******************************");
                // 关闭InputStreamReader
                reader.close();
                // 关闭http连接
                urlConn.disconnect();
                // 设置显示取得的内容
                if (resultData == null || resultData.isEmpty()) {
                    resultData = "读取的内容为NULL";
                }
                Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, resultData);
                msg.sendToTarget();
            } catch (IOException e)
            {
                Log.e(DEBUG_TAG, "IOException");
                e.printStackTrace();
            }
        }
        else
        {
            Log.e(DEBUG_TAG, "Url NULL");
        }
    }
}
