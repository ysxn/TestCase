
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * http://www.blogjava.net/flysky19/articles/92286.html
 * <p>
 * FILE,FILEINPUTSTREAM,FILEREADER,INPUTSTREAMREADER,BUFFEREDREADER��ʹ�ú�����
 * 2007��1��8��
 * <p>
 * File,FileInputStream,FileReader,InputStreamReader,BufferedReader ��ʹ�ú�����
 * <p>
 * �ο����ϣ�
 * <p>
 * l �� core java �� 12 ��
 * <p>
 * l ʹ�� Java �����ı��ļ��ķ������
 * <p>
 * http://java.ccidnet.com/art/3737/20041108/523627_1.html
 * <p>
 * l FileReader ��ʲô�ࣿ�� FileInputStream ��ʲô��ͬ������
 * <p>
 * http://book.hackbase.com/ask2/ask107572.htm �Լ����������᣺ ���ԣ�
 * <p>
 * C����ֻ��Ҫһ��File*�Ϳ����ˣ���C��ͬ��java��һϵ�������ͣ�����������60�֡�������������ƣ�����
 * <p>
 * �㹻������Ϊ�û��ṩ�ḻ�������͵�ѡ�����������Լ��ٳ���Ĵ��󡣡����磬��C�����֣��������Ϊ��
 * <p>
 * �������д��һ��ֻ��ģʽ���ļ����Ǻܳ����Ĵ��󡣣���ʵ�ϣ��Ⲣ����������
 * <p>
 * ������Ϊ��C���������У����ӿ�����߱������������Ҫ�����ߡ���С�Ľ�����̬�ȣ���java�����и�����
 * <p>
 * �ˡ�����ĸ߶ȸ�������ʹ���������Ա��С��΢��
 * <p>
 * 1. File��
 * <p>
 * 1 �� File ����ܣ��� core java �� 638 ҳ��
 * <p>
 * File ���װ�˶��û��������ļ�ϵͳ���в����Ĺ��ܡ����磬������ File �����ļ��ϴ��޸ĵ�ʱ���ƶ���
 * <p>
 * ���߶��ļ�����ɾ���������������仰˵�������ע�����ļ����ݣ��� File ���ע�����ļ��ڴ����ϵĴ洢
 * <p>
 * ��
 * <p>
 * File �����Ҫ�����У�
 * getName(),getCanonicalFile(),lastModified(),isDerector(),isFile(),getPath()
 * �ȣ�
 * <p>
 * 2 �� File ���� FileInputStream �������
 * <p>
 * �����ע�����ļ����ݣ��� File ���ע�����ļ��ڴ����ϵĴ洢��
 * <p>
 * File �������ļ��� , ֻ�ܴ���һ���ļ�����Ŀ¼��·�������ѡ�
 * <p>
 * ��ʾ������ core java �� 639 ҳ��
 * <p>
 * ��������ļ�����Ŀ¼������Ӧ��ʹ�� File ���󣬶������ַ��������磬 File ��� equals ����֪��һЩ
 * <p>
 * �ļ�ϵͳ�Դ�Сд�����еģ�Ŀ¼β�ġ� / ���ַ��޹ؽ�Ҫ��
 * <p>
 * �Լ�����᣺
 * <p>
 * FileInputStream ����� FileReader ��Ĺ��캯���ж�������е��͵������ֱ�Ϊ��һ��ʹ�� File ����Ϊ
 * <p>
 * ����������һ��ʹ�ñ�ʾ·���� String ������Ϊ�������Լ���ǰһֱ����ֱ������ String ָ��·���Ϳ���
 * <p>
 * �ˣ�һֱ������Ϊʲô�ܶ��˶��ȹ���һ�� File �����������������ˣ�����������ļ�����Ŀ¼������Ӧ
 * <p>
 * ��ʹ�� File ���󣬶������ַ���������
 * <p>
 * 2. FileInputStream ��
 * <p>
 * 1 �� FileInputStream ����ܣ�
 * <p>
 * ���ֽ�Ϊ��λ���� unicode �����������ֽ����м������������ݡ�������޹أ��������������⡣
 * <p>
 * FileInputStream �����Ҫ�����У�
 * <p>
 * Read ������ read �� byte[] b ���� read �� byte[],int off,int len �� ,available();
 * <p>
 * 2 �� FileInputStream ���� FileReader �������
 * <p>
 * ������Ĺ��캯������ʽ�Ͳ���������ͬ�ģ�����Ϊ File ������߱�ʾ·���� String �����ǵ����к����� �أ�
 * <p>
 * l Readers and Writers work only on line based character data, so plain text
 * files. For anything else, you MUST use Streams.
 * <p>
 * l JDK5 API:
 * <p>
 * FileInputStream is meant for reading streams of raw bytes such as image data.
 * For reading streams
 * <p>
 * of characters, consider using FileReader.
 * <p>
 * FileReader is meant for reading streams of characters. For reading streams of
 * raw bytes, consider
 * <p>
 * using a FileInputStream .
 * <p>
 * l FileInputStream �����ֽ�����ʽ��ȡ�� FileReader �����ļ�ת��Ϊ�ַ������룻
 * <p>
 * l InputStream�ṩ�����ֽ����Ķ�ȡ�������ı���ȡ�����Ǻ�Reader��ĸ���������Reader��ȡ��
 * <p>
 * ������char�������String ��ʹ��InputStream��ȡ��������byte���顣
 * <p>
 * l Reader�༰�������ṩ���ַ����Ķ�ȡchar��16λ,unicode���룩��inputStream���������ṩ�ֽ�
 * <p>
 * ���Ķ�ȡbyte��8λ��������FileReader���ǽ��ļ����ַ����ķ�ʽ��ȡ��FileInputStream���ֽ����ķ�ʽ
 * <p>
 * ��ȡ�ļ���InputStreamReader���Խ�����streamת�����ַ�����ʽ����reader��stream֮�������
 * <p>
 * l ���Java�ǲ�֧�ֶ��ı��ļ��Ĵ���ģ�Ϊ���ֲ����ȱ����������Reader��Writer�����ࡣ
 * <p>
 * l FileInputStream ���Զ��������� / ����� I/O �ٶȿ���Ч�ʸ㣬�������� read ������������
 * <p>
 * ����һ���ֽڣ����������ݣ����ܲ����������Ķ���
 * <p>
 * l �� FileReader ���ֲ������ȱ�ݣ��������ı���ʽ���� / ������ǳ����㣻�������ʹ��
 * <p>
 * while((ch = filereader.read())!=-1 ) ѭ������ȡ�ļ�������ʹ�� BufferedReader �� readLine()
 * ����һ
 * <p>
 * ��һ�еĶ�ȡ�ı���
 * <p>
 * l �����Ƕ�д�ı��ļ���ʱ�򣬲��� Reader �Ƿǳ�����ģ����� FileReader ��
 * <p>
 * InputStreamReader �� BufferedReader ����������Ҫ������ InputStreamReader �������ֽ�ת��Ϊ�ַ�����
 * <p>
 * ���� ������ڹ�������ָ������ķ�ʽ�������ָ���Ļ������õײ����ϵͳ��Ĭ�ϱ��뷽ʽ������ GBK �� ��
 * <p>
 * l FileReader �� InputStreamReader �漰����ת�� ( ָ�����뷽ʽ���߲��� os Ĭ�ϱ��� ) ����
 * <p>
 * ���ڲ�ͬ��ƽ̨�ϳ����������󣡶� FileInputStream �Զ����Ʒ�ʽ������������������� .
 * <p>
 * 3 ���Լ�����᣺
 * <p>
 * l ��������ı��ļ�������ʹ�� FileReader ����Ϊ�����㣬Ҳ���ʺ��Ķ�������Ҫע��������� ��
 * <p>
 * l �������������Ǵ��ı��ļ�����FileInputStream��Ψһ��ѡ��FileInputStream�ǽ�SocketͨѶʱ��
 * <p>
 * �õ��ܶ࣬�罫�ļ�����Stream�ķ�ʽ�����������
 * <p>
 * 3. FileReader ��
 * <p>
 * 1�� FileReader ����ܣ�
 * <p>
 * InputStreamReader ������࣬���з����� read �����ȣ����Ӹ��� InputStreamReader �м̳ж�����
 * <p>
 * 2�� �� InputStreamReader �������
 * <p>
 * l �Լ�����᣺
 * <p>
 * ���������ĸ��� InputStreamReader ����Ҫ��ͬ���ڹ��캯������Ҫ����Ҳ�����ڹ��캯������
 * <p>
 * InputStreamReader �Ĺ��캯���п���������Ϊ InputStream �ͱ��뷽ʽ�����Կ�������Ҫָ�����뷽ʽʱ��
 * <p>
 * ����ʹ�� InputStreamReader �ࣻ�� FileReader ���캯���Ĳ����� FileInputStream ͬ��Ϊ File �����
 * <p>
 * ��ʾ path �� String �����Կ�������Ҫ���� File ������� String ��ȡһ���ļ�ʱ���� FileReader ����
 * <p>
 * �� FileReader ���������Ҳ���������С�ֹ��ɡ�
 * <p>
 * 3�� һ���÷���
 * <p>
 * FileReader fr = new FileReader("ming.txt");
 * <p>
 * ���� char[] buffer = new char[1024];
 * <p>
 * ���� int ch = 0;
 * <p>
 * ���� while((ch = fr.read())!=-1 )
 * <p>
 * ���� {
 * <p>
 * ������ System.out.print((char)ch);
 * <p>
 * ���� }
 * <p>
 * 4. InputStreamReader ��
 * <p>
 * l ���ı���ʽ���� / ���������ָ�������ʽ��
 * <p>
 * l ��Ҫ������
 * <p>
 * getEncoding ()��read();
 * <p>
 * l һ���÷���
 * <p>
 * InputStreamReader isr = new InputStreamReader(new
 * FileInputStream("ming.txt"));
 * <p>
 * ���� while((ch = isr.read())!=-1)
 * <p>
 * ���� {
 * <p>
 * ������ System.out.print((char)ch);
 * <p>
 * ���� }
 * <p>
 * 5. BufferedReader ��
 * <p>
 * l Jdk5 api ��
 * <p>
 * Read text from a character-input stream, buffering characters so as to
 * provide for the efficient
 * <p>
 * reading of characters, arrays, and lines.
 * <p>
 * l BufferedReader ��Reader����չ�������ṩͨ�õĻ��巽ʽ�ı���ȡ�������ṩ�˺�ʵ�õ�readLine��
 * <p>
 * ��ȡ�����ı����ʺϣ�BufferedReader�����Reader�ģ���ֱ������ļ���Ҳ����ֻ����ļ���ȡ��
 * <p>
 * l һ���÷���
 * <p>
 * BufferedReader br = new BufferedReader(new InputStreamReader(new
 * FileInputStream("ming.txt")));
 * <p>
 * ����String data = null;
 * <p>
 * ����while((data = br.readLine())!=null)
 * <p>
 * ����{
 * <p>
 * ������System.out.println(data);
 * <p>
 * ����}
 * <p>
 * 6. �ܽ��������ݣ��ó��ȽϺõĹ淶�÷���
 * <p>
 * 1�� File file = new File ("hello.txt");
 * <p>
 * FileInputStream in=new FileInputStream(file);
 * <p>
 * 2�� File file = new File ("hello.txt");
 * <p>
 * FileInputStream in=new FileInputStream(file);
 * <p>
 * InputStreamReader inReader=new InputStreamReader(in);
 * <p>
 * BufferedReader bufReader=new BufferedReader(inReader);
 * <p>
 * 3�� File file = new File ("hello.txt");
 * <p>
 * FileReader fileReader=new FileReader(file);
 * <p>
 * BufferedReader bufReader=new BufferedReader(fileReader);
 * <p>
 * 7.һЩд��������
 * <p>
 * 1��
 * <p>
 * File file = new File ("hello.txt");
 * <p>
 * FileInputStream in=new FileInputStream(file);
 * <p>
 * InputStreamReader inReader=new InputStreamReader(in);
 * <p>
 * BufferedReader bufReader=new BufferedReader(inReader);
 * <p>
 * 2��
 * <p>
 * FileInputStream in��null;
 * <p>
 * File file = new File ("hello.txt");
 * <p>
 * in=new FileInputStream(file);
 * <p>
 * BufferedReader bufReader=new BufferedReader(new InputStreamReader(in));
 * <p>
 * 3��
 * <p>
 * File file = new File ("hello.txt");
 * <p>
 * BufferedReader bufReader=new BufferedReader(new InputStreamReader(new
 * FileInputStream(file)));
 * <p>
 * ��������д����΢С����
 * <p>
 * a���ڶ��ַ�ʽ�аѡ�FileInputStream
 * in��null;�����嵥�����ڿ�ʼ����˵������Ӧ�û���Ҫ�õ�in��������ĵط�����BufferedReader�����ˣ�
 * <p>
 * b���ڶ��ַ�ʽû�ж���InputStreamReader�Ķ��������ֱ����BufferedReader�Ĺ��캯����newһ����
 * ���ַ�ʽ���һ�ַ�ʽ����Ҫ����InputStreamReader����ֻʹ��һ�Σ�
 * <p>
 * �����������ֻ��Ҫʹ��һ�����InputStreamReader�����Ӧ����˵���ã����趨��InputStreamReader�Ķ��������
 * ������new���صĸö��������
 * ����Ϊ����ĳ����в���Ҫ���InputStreamReader�Ķ���������������趨�壻������������£��ڶ��ַ�ʽ�ȵ�һ�ָ���һЩ��
 * <p>
 * c�������ַ�ʽ�У����͵�����Ƕ��ί�ɹ�ϵ����������Reader��ί��ģʽ����corejava��12����ͼ������ί�ɹ�ϵ����
 * FileInputStream��InputStreamReader��û�ж��������new���ɵĶ���ֻ��ʹ��һ�Ρ�
 * <p>
 * d�����ַ�ʽ������Ҳ������FileInputStream��InputStreamReader�����Ƿ�ֻʹ��һ�Σ��Ƿ���Ҫ�������ǵĶ��������
 * �Լ����˵ı���ϰ�ߡ�
 * <p>
 * e)����Ҫע���쳣����FileInputStream(file)���׳�NotFileFoundException���������surround��ʽ
 * <p>
 * ��try&catch������Ӧ���õڶ��ַ�ʽ������������System.out.println��ʾ�ļ�δ�ҵ���
 * <p>
 * ��Ȼ�ں�������ʹ��throws Exception��Ȼ���õ����ַ�ʽҲ�У����ƺ����ʺ����û��������������쳣�׳��ڿͻ����ڴ���
 * 
 * @author zhuyw1
 */
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
    public void methodHttpPost(String httpUrl, List<NameValuePair> params) {
        // http��ַ
        if (httpUrl == null || httpUrl.isEmpty()) {
            Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, "httpUrl is null");
            msg.sendToTarget();
            return;
        }
        String resultData = null;
        // HttpPost���Ӷ���
        HttpPost httpRequest = new HttpPost(httpUrl);
        try {
            // �����ַ���
            HttpEntity httpentity = new UrlEncodedFormEntity(params, HTTP.UTF_8);//"gb2312"
            // ����httpRequest
            httpRequest.setEntity(httpentity);
            // ȡ��Ĭ�ϵ�HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // ȡ��HttpResponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            // HttpStatus.SC_OK��ʾ���ӳɹ�
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // ȡ�÷��ص��ַ���
                HttpEntity resultEntity = httpResponse.getEntity();
                resultData = EntityUtils.toString(resultEntity);
            } else {
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
        } catch (Exception e) {
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
