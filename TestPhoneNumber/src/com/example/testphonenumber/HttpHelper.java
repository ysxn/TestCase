
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
     * HttpClientʵ�����Ƕ�Java�ṩ������һЩ��װ����HttpURLConnection�е����������������
     * ������ӿ��б�ͳһ��װ����HttpPost(HttpGet)��HttpResponse���������ͼ����˲����ķ����ԡ�
     * ���⣬��ʹ��POST��ʽ���д���ʱ����Ҫ�����ַ����롣
     */
    public void methodHttpGet(String passUrl) {
        String httpUrl = "http://www.baidu.com";
        if (passUrl != null) {
            httpUrl = passUrl;
        }
        String resultData = null;
        // ����һ���������
        HttpGet httpGet = new HttpGet(httpUrl);
        // ����һ��Http�ͻ��˶���
        HttpClient httpClient = new DefaultHttpClient();
        // ʹ��http�ͻ��˷����������
        InputStream inputStream = null;

        try {
            // httpResponse���Ǵ�����Ӧ����
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Log.i(DEBUG_TAG, ">>>>HttpStatus :" + httpResponse.getStatusLine().getStatusCode());
            // ����ɹ�
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // httpEntity�����ľ��Ƿ��ص���Ϣ����
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
                resultData = "�������!";
            }
            if (resultData == null || resultData.isEmpty()) {
                resultData = "��ȡ������ΪNULL";
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
     * HttpClientʵ�����Ƕ�Java�ṩ������һЩ��װ����HttpURLConnection�е����������������
     * ������ӿ��б�ͳһ��װ����HttpPost(HttpGet)��HttpResponse���������ͼ����˲����ķ����ԡ�
     * ���⣬��ʹ��POST��ʽ���д���ʱ����Ҫ�����ַ����롣
     */
    public void methodHttpGetAlias(String passUrl) {
        // http��ַ
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp?par=HttpClient_android_Get";
        if (passUrl != null) {
            httpUrl = passUrl;
        }
        String resultData = null;
        // HttpGet���Ӷ���
        HttpGet httpRequest = new HttpGet(httpUrl);
        try
        {
            // ȡ��HttpClient����
            HttpClient httpclient = new DefaultHttpClient();
            // ����HttpClient��ȡ��HttpResponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            Log.i(DEBUG_TAG, ">>>>HttpStatus :" + httpResponse.getStatusLine().getStatusCode());
            // ����ɹ�
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // ȡ�÷��ص��ַ���
                resultData = EntityUtils.toString(httpResponse.getEntity());
            }
            else
            {
                resultData = "�������!";
            }
            if (resultData == null || resultData.isEmpty()) {
                resultData = "��ȡ������ΪNULL";
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
     * HttpClientʵ�����Ƕ�Java�ṩ������һЩ��װ����HttpURLConnection�е����������������
     * ������ӿ��б�ͳһ��װ����HttpPost(HttpGet)��HttpResponse���������ͼ����˲����ķ����ԡ�
     * ���⣬��ʹ��POST��ʽ���д���ʱ����Ҫ�����ַ����롣
     */
    public void methodHttpPost(String par, List<NameValuePair> params) {
        // http��ַ
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp";
        if (par != null) {
            httpUrl = par;
        }
        String resultData = null;
        // HttpPost���Ӷ���
        HttpPost httpRequest = new HttpPost(httpUrl);
        // ʹ��NameValuePair������Ҫ���ݵ�Post����
        // List<NameValuePair> params = new ArrayList<NameValuePair>();

        // ���Ҫ���ݵĲ���
        // params.add(new BasicNameValuePair("par", "HttpClient_android_Post"));
        try
        {
            // �����ַ���
            HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
            // ����httpRequest
            httpRequest.setEntity(httpentity);
            // ȡ��Ĭ�ϵ�HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // ȡ��HttpResponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            // HttpStatus.SC_OK��ʾ���ӳɹ�
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // ȡ�÷��ص��ַ���
                resultData = EntityUtils.toString(httpResponse.getEntity());
            }
            else
            {
                resultData = "�������!";
            }
            if (resultData == null || resultData.isEmpty()) {
                resultData = "��ȡ������ΪNULL";
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
     * methodGet ��Get��ʽ�ϴ�����
     */
    public void methodGet(String par) {
        // http��ַ"?par=abcdefg"�������ϴ��Ĳ���
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp?par=abcdefg";
        if (par != null) {
            httpUrl = par;
        }

        // ��õ�����
        String resultData = "";
        URL url = null;
        try
        {
            // ����һ��URL����
            url = new URL(httpUrl);
        } catch (MalformedURLException e)
        {
            Log.e(DEBUG_TAG, "MalformedURLException");
        }
        if (url != null)
        {
            try
            {
                // ʹ��HttpURLConnection������
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // �õ���ȡ������(��)
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                // Ϊ�������BufferedReader
                BufferedReader buffer = new BufferedReader(in);
                String inputLine = null;
                // ʹ��ѭ������ȡ��õ�����
                Log.i(DEBUG_TAG,
                        "*****************************start*******************************");
                while (((inputLine = buffer.readLine()) != null))
                {
                    // ������ÿһ�к������һ��"\n"������
                    resultData += inputLine + "\n";
                    Log.i(DEBUG_TAG, inputLine);
                }
                Log.i(DEBUG_TAG, "*****************************end*******************************");
                // �ر�InputStreamReader
                in.close();
                // �ر�http����
                urlConn.disconnect();
                // ������ʾȡ�õ�����
                if (resultData == null || resultData.isEmpty()) {
                    resultData = "��ȡ������ΪNULL";
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
     * methodPost ��post��ʽ�ϴ�����
     * 
     * @param textView
     */
    public void methodPost(String passUrl, String key, String value) {
        // http��ַ"?par=abcdefg"�������ϴ��Ĳ���
        String httpUrl = "http://192.168.1.110:8080/httpget.jsp";
        if (passUrl != null) {
            httpUrl = passUrl;
        }
        // ��õ�����
        String resultData = "";
        URL url = null;
        try
        {
            // ����һ��URL����
            url = new URL(httpUrl);
        } catch (MalformedURLException e)
        {
            Log.e(DEBUG_TAG, "MalformedURLException");
        }
        if (url != null)
        {
            try
            {
                // ʹ��HttpURLConnection������
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // ��Ϊ�����post����,������Ҫ����Ϊtrue
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                // ������POST��ʽ
                urlConn.setRequestMethod("POST");
                // Post ������ʹ�û���
                urlConn.setUseCaches(false);
                urlConn.setInstanceFollowRedirects(true);
                // ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
                // Ҫע�����connection.getOutputStream�������Ľ���connect��
                urlConn.connect();
                // DataOutputStream��
                DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
                // Ҫ�ϴ��Ĳ���
                // String content = "par=" + URLEncoder.encode("ABCDEFG",
                // "gb2312");
                String content = key + "=" + URLEncoder.encode(value, "gb2312");
                // ��Ҫ�ϴ�������д������
                out.writeBytes(content);
                // ˢ�¡��ر�
                out.flush();
                out.close();
                // ��ȡ����
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        urlConn.getInputStream()));
                String inputLine = null;
                // ʹ��ѭ������ȡ��õ�����
                Log.i(DEBUG_TAG,
                        "*****************************start*******************************");
                while (((inputLine = reader.readLine()) != null))
                {
                    // ������ÿһ�к������һ��"\n"������
                    resultData += inputLine + "\n";
                    Log.i(DEBUG_TAG, inputLine);
                }
                Log.i(DEBUG_TAG, "*****************************end*******************************");
                // �ر�InputStreamReader
                reader.close();
                // �ر�http����
                urlConn.disconnect();
                // ������ʾȡ�õ�����
                if (resultData == null || resultData.isEmpty()) {
                    resultData = "��ȡ������ΪNULL";
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
