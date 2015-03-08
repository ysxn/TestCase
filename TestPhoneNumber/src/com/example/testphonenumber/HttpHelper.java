
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
 * FILE,FILEINPUTSTREAM,FILEREADER,INPUTSTREAMREADER,BUFFEREDREADER的使用和区别
 * 2007年1月8日
 * <p>
 * File,FileInputStream,FileReader,InputStreamReader,BufferedReader 的使用和区别
 * <p>
 * 参考资料：
 * <p>
 * l 《 core java 》 12 章
 * <p>
 * l 使用 Java 操作文本文件的方法详解
 * <p>
 * http://java.ccidnet.com/art/3737/20041108/523627_1.html
 * <p>
 * l FileReader 是什么类？和 FileInputStream 有什么不同？？？
 * <p>
 * http://book.hackbase.com/ask2/ask107572.htm 自己的整理和领会： 引言：
 * <p>
 * C语言只需要一个File*就可以了，与C不同，java有一系列流类型，其数量超过60种。类库的设计者声称：“有
 * <p>
 * 足够的理由为用户提供丰富的流类型的选择：这样做可以减少程序的错误。”例如，在C语言种，许多人认为“
 * <p>
 * 将输出流写入一个只读模式的文件”是很常见的错误。（事实上，这并不常见。）
 * <p>
 * 我们认为在C＋＋语言中，流接口设计者避免程序出错的主要“工具”是小心谨慎的态度，在java语言中更是如
 * <p>
 * 此。流库的高度复杂性迫使程序设计人员谨小慎微。
 * <p>
 * 1. File类
 * <p>
 * 1 ） File 类介绍（《 core java 》 638 页）
 * <p>
 * File 类封装了对用户机器的文件系统进行操作的功能。例如，可以用 File 类获得文件上次修改的时间移动，
 * <p>
 * 或者对文件进行删除、重命名。换句话说，流类关注的是文件内容，而 File 类关注的是文件在磁盘上的存储
 * <p>
 * 。
 * <p>
 * File 类的主要方法有：
 * getName(),getCanonicalFile(),lastModified(),isDerector(),isFile(),getPath()
 * 等；
 * <p>
 * 2 ） File 类与 FileInputStream 类的区别：
 * <p>
 * 流类关注的是文件内容，而 File 类关注的是文件在磁盘上的存储。
 * <p>
 * File 不属于文件流 , 只能代表一个文件或是目录的路径名而已。
 * <p>
 * 提示：（《 core java 》 639 页）
 * <p>
 * 如果处理文件或者目录名，就应该使用 File 对象，而不是字符串。例如， File 类的 equals 方法知道一些
 * <p>
 * 文件系统对大小写是敏感的，目录尾的“ / ”字符无关紧要。
 * <p>
 * 自己的领会：
 * <p>
 * FileInputStream 类或者 FileReader 类的构造函数有多个，其中典型的两个分别为：一个使用 File 对象为
 * <p>
 * 参数；而另一个使用表示路径的 String 对象作为参数；自己以前一直觉得直接用了 String 指定路径就可以
 * <p>
 * 了，一直不明白为什么很多人都先构造一个 File 对象，现在终于明白了，“如果处理文件或者目录名，就应
 * <p>
 * 该使用 File 对象，而不是字符串。”！
 * <p>
 * 2. FileInputStream 类
 * <p>
 * 1 ） FileInputStream 类介绍：
 * <p>
 * 以字节为单位（非 unicode ）的流处理。字节序列即：二进制数据。与编码无关，不存在乱码问题。
 * <p>
 * FileInputStream 类的主要方法有：
 * <p>
 * Read （）， read （ byte[] b ）， read （ byte[],int off,int len ） ,available();
 * <p>
 * 2 ） FileInputStream 类与 FileReader 类的区别：
 * <p>
 * 两个类的构造函数的形式和参数都是相同的，参数为 File 对象或者表示路径的 String ，它们到底有何区别 呢？
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
 * l FileInputStream ：以字节流方式读取； FileReader ：把文件转换为字符流读入；
 * <p>
 * l InputStream提供的是字节流的读取，而非文本读取，这是和Reader类的根本区别。用Reader读取出
 * <p>
 * 来的是char数组或者String ，使用InputStream读取出来的是byte数组。
 * <p>
 * l Reader类及其子类提供的字符流的读取char（16位,unicode编码），inputStream及其子类提供字节
 * <p>
 * 流的读取byte（8位），所以FileReader类是将文件按字符流的方式读取，FileInputStream则按字节流的方式
 * <p>
 * 读取文件；InputStreamReader可以将读如stream转换成字符流方式，是reader和stream之间的桥梁
 * <p>
 * l 最初Java是不支持对文本文件的处理的，为了弥补这个缺憾而引入了Reader和Writer两个类。
 * <p>
 * l FileInputStream 类以二进制输入 / 输出， I/O 速度快且效率搞，但是它的 read （）方法读到
 * <p>
 * 的是一个字节（二进制数据），很不利于人们阅读。
 * <p>
 * l 而 FileReader 类弥补了这个缺陷，可以以文本格式输入 / 输出，非常方便；比如可以使用
 * <p>
 * while((ch = filereader.read())!=-1 ) 循环来读取文件；可以使用 BufferedReader 的 readLine()
 * 方法一
 * <p>
 * 行一行的读取文本。
 * <p>
 * l 当我们读写文本文件的时候，采用 Reader 是非常方便的，比如 FileReader ，
 * <p>
 * InputStreamReader 和 BufferedReader 。其中最重要的类是 InputStreamReader ，它是字节转换为字符的桥
 * <p>
 * 梁。 你可以在构造器重指定编码的方式，如果不指定的话将采用底层操作系统的默认编码方式，例如 GBK 等 。
 * <p>
 * l FileReader 与 InputStreamReader 涉及编码转换 ( 指定编码方式或者采用 os 默认编码 ) ，可
 * <p>
 * 能在不同的平台上出现乱码现象！而 FileInputStream 以二进制方式处理，不会出现乱码现象 .
 * <p>
 * 3 ）自己的领会：
 * <p>
 * l 如果处理纯文本文件，建议使用 FileReader ，因为更方便，也更适合阅读；但是要注意编码问题 ！
 * <p>
 * l 其他情况（处理非纯文本文件），FileInputStream是唯一的选择；FileInputStream是进Socket通讯时会
 * <p>
 * 用到很多，如将文件流是Stream的方式传向服务器！
 * <p>
 * 3. FileReader 类
 * <p>
 * 1） FileReader 类介绍：
 * <p>
 * InputStreamReader 类的子类，所有方法（ read （）等）都从父类 InputStreamReader 中继承而来；
 * <p>
 * 2） 与 InputStreamReader 类的区别：
 * <p>
 * l 自己的领会：
 * <p>
 * 该类与它的父类 InputStreamReader 的主要不同在于构造函数，主要区别也就在于构造函数！从
 * <p>
 * InputStreamReader 的构造函数中看到，参数为 InputStream 和编码方式，可以看出，当要指定编码方式时，
 * <p>
 * 必须使用 InputStreamReader 类；而 FileReader 构造函数的参数与 FileInputStream 同，为 File 对象或
 * <p>
 * 表示 path 的 String ，可以看出，当要根据 File 对象或者 String 读取一个文件时，用 FileReader ；我
 * <p>
 * 想 FileReader 子类的作用也就在于这个小分工吧。
 * <p>
 * 3） 一般用法：
 * <p>
 * FileReader fr = new FileReader("ming.txt");
 * <p>
 * 　　 char[] buffer = new char[1024];
 * <p>
 * 　　 int ch = 0;
 * <p>
 * 　　 while((ch = fr.read())!=-1 )
 * <p>
 * 　　 {
 * <p>
 * 　　　 System.out.print((char)ch);
 * <p>
 * 　　 }
 * <p>
 * 4. InputStreamReader 类
 * <p>
 * l 以文本格式输入 / 输出，可以指定编码格式；
 * <p>
 * l 主要方法：
 * <p>
 * getEncoding ()，read();
 * <p>
 * l 一般用法：
 * <p>
 * InputStreamReader isr = new InputStreamReader(new
 * FileInputStream("ming.txt"));
 * <p>
 * 　　 while((ch = isr.read())!=-1)
 * <p>
 * 　　 {
 * <p>
 * 　　　 System.out.print((char)ch);
 * <p>
 * 　　 }
 * <p>
 * 5. BufferedReader 类
 * <p>
 * l Jdk5 api ：
 * <p>
 * Read text from a character-input stream, buffering characters so as to
 * provide for the efficient
 * <p>
 * reading of characters, arrays, and lines.
 * <p>
 * l BufferedReader 由Reader类扩展而来，提供通用的缓冲方式文本读取，而且提供了很实用的readLine，
 * <p>
 * 读取分行文本很适合，BufferedReader是针对Reader的，不直接针对文件，也不是只针对文件读取。
 * <p>
 * l 一般用法：
 * <p>
 * BufferedReader br = new BufferedReader(new InputStreamReader(new
 * FileInputStream("ming.txt")));
 * <p>
 * 　　String data = null;
 * <p>
 * 　　while((data = br.readLine())!=null)
 * <p>
 * 　　{
 * <p>
 * 　　　System.out.println(data);
 * <p>
 * 　　}
 * <p>
 * 6. 总结以上内容，得出比较好的规范用法：
 * <p>
 * 1） File file = new File ("hello.txt");
 * <p>
 * FileInputStream in=new FileInputStream(file);
 * <p>
 * 2） File file = new File ("hello.txt");
 * <p>
 * FileInputStream in=new FileInputStream(file);
 * <p>
 * InputStreamReader inReader=new InputStreamReader(in);
 * <p>
 * BufferedReader bufReader=new BufferedReader(inReader);
 * <p>
 * 3） File file = new File ("hello.txt");
 * <p>
 * FileReader fileReader=new FileReader(file);
 * <p>
 * BufferedReader bufReader=new BufferedReader(fileReader);
 * <p>
 * 7.一些写法的区别：
 * <p>
 * 1）
 * <p>
 * File file = new File ("hello.txt");
 * <p>
 * FileInputStream in=new FileInputStream(file);
 * <p>
 * InputStreamReader inReader=new InputStreamReader(in);
 * <p>
 * BufferedReader bufReader=new BufferedReader(inReader);
 * <p>
 * 2）
 * <p>
 * FileInputStream in＝null;
 * <p>
 * File file = new File ("hello.txt");
 * <p>
 * in=new FileInputStream(file);
 * <p>
 * BufferedReader bufReader=new BufferedReader(new InputStreamReader(in));
 * <p>
 * 3）
 * <p>
 * File file = new File ("hello.txt");
 * <p>
 * BufferedReader bufReader=new BufferedReader(new InputStreamReader(new
 * FileInputStream(file)));
 * <p>
 * 上述两种写法的微小区别：
 * <p>
 * a）第二种方式中把“FileInputStream
 * in＝null;”定义单独放在开始处，说明下面应该还有要用到in对象变量的地方；（BufferedReader处用了）
 * <p>
 * b）第二种方式没有定义InputStreamReader的对象变量，直接在BufferedReader的构造函数中new一个，
 * 这种方式与第一种方式的主要区别：InputStreamReader对象只使用一次！
 * <p>
 * 这对于在这里只需要使用一次这个InputStreamReader对象的应用来说更好；无需定义InputStreamReader的对象变量，
 * 接收由new返回的该对象的引用
 * ，因为下面的程序中不需要这个InputStreamReader的对象变量，所以无需定义；所以这种情况下，第二种方式比第一种更好一些。
 * <p>
 * c）第三种方式中，典型的三层嵌套委派关系，清晰看出Reader的委派模式（《corejava》12章有图描述该委派关系），
 * FileInputStream和InputStreamReader都没有定义变量，new生成的对象都只是使用一次。
 * <p>
 * d）三种方式的区别也就在于FileInputStream和InputStreamReader对象是否都只使用一次，是否需要定义它们的对象变量，
 * 以及个人的编码习惯。
 * <p>
 * e)但是要注意异常处理，FileInputStream(file)会抛出NotFileFoundException，如果采用surround方式
 * <p>
 * （try&catch）处理，应该用第二种方式，这样可以用System.out.println提示文件未找到；
 * <p>
 * 当然在函数名后使用throws Exception，然后用第三种方式也行，但似乎这适合有用户界面的情况，把异常抛出在客户端在处理。
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
    public void methodHttpPost(String httpUrl, List<NameValuePair> params) {
        // http地址
        if (httpUrl == null || httpUrl.isEmpty()) {
            Message msg = mHandler.obtainMessage(MSG_SHOW_RESULT, "httpUrl is null");
            msg.sendToTarget();
            return;
        }
        String resultData = null;
        // HttpPost连接对象
        HttpPost httpRequest = new HttpPost(httpUrl);
        try {
            // 设置字符集
            HttpEntity httpentity = new UrlEncodedFormEntity(params, HTTP.UTF_8);//"gb2312"
            // 请求httpRequest
            httpRequest.setEntity(httpentity);
            // 取得默认的HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 取得HttpResponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            // HttpStatus.SC_OK表示连接成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 取得返回的字符串
                HttpEntity resultEntity = httpResponse.getEntity();
                resultData = EntityUtils.toString(resultEntity);
            } else {
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
        } catch (Exception e) {
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
