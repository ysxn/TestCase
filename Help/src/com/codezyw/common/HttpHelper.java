
package com.codezyw.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;
import cn.trinea.android.common.util.ImageUtils;

import com.codezyw.common.HttpPostAsyncTask.PostListener;

/**
 * HttpClient实际上是对Java提供方法的一些封装，在HttpURLConnection中的输入输出流操作，
 * 在这个接口中被统一封装成了HttpPost(HttpGet)和HttpResponse，这样，就减少了操作的繁琐性。
 * 另外，在使用POST方式进行传输时，需要进行字符编码。
 * <p>
 * <li>use bufferedReader to improve the reading speed</li>
 * <p>
 * <br>
 * 当完成一个响应实体，那么保证所有实体内容已经被完全消耗是很重要的，所以连接可以安全的放回到连接池中，而且可以通过连接管理器对后续的请求重用连接。
 * 处理这个操作的最方便的方法是调用HttpEntity
 * #consumeContent()方法来消耗流中的任意可用内容。HttpClient探测到内容流尾部已经到达后
 * ，会立即会自动释放低层连接，并放回到连接管理器。HttpEntity#consumeContent()方法调用多次也是安全的。
 * <p>
 * <a href=
 * "http://hc.apache.org/httpcomponents-client-ga/httpmime/apidocs/org/apache/http/entity/mime/MultipartEntity.html"
 * >apache的库API说明</a>
 * <p>
 * <a href="http://www.w3school.com.cn/tags/tag_br.asp">html标签说明</a>
 */
public class HttpHelper {

    /**
     * <p>
     * <a href="http://www.trinea.cn/android/android-network-sniffer/"
     * target="_blank">http://www.trinea.cn/android/android-network-sniffer/</a>
     * 
     * <pre>
     * Android利用Fiddler进行网络数据抓包
     * 主要介绍Android及IPhone手机上如何利用Fiddler进行网络数据抓包，比如我们想抓某个应用(微博、微信、墨迹天气)的网络通信请求就可以利用这个方法。
     *  
     * Mac 下请使用 Charles 代替 Fiddler，Charles 免费激活码为Registered name: a!nthony ortolani   License key: a!4036b2761c9583fda (需要将 name 和 key 中的 a! 都改为 a)
     *  
     * 相对于tcpdump配合wireshark抓包的优势在于：(1)无需root (2)对Android和Iphone同样适用 (3)操作更简单方便(第一次安装配置，第二次只需设置代理即可) (4)数据包的查看更清晰易懂,Fiddler的UI更简单明了 (5) 可以查看https请求。如果你坚持使用tcpdump也可见：利用tcpdump和wireshark抓取网络数据包。
     *  
     * PS：需要1台PC做辅助，且PC需要与手机在同一局域网内或有独立公网ip
     * 1、PC端安装Fiddler
     * 下载地址：Fiddler.exe，下面是Fiddler的简单介绍(不感兴趣的可以直接跳过)：
     * Fiddler是强大且好用的Web调试工具之一，它能记录客户端和服务器的http和https请求，允许你监视，设置断点，甚至修改输入输出数据，Fiddler包含了一个强大的基于事件脚本的子系统，并且能使用.net语言进行扩展，在web开发和调优中经常配合firebug使用。
     * Fiddler的运行机制其实就是本机上监听8888端口的HTTP代理。 对于PC端Fiddler启动的时候默认IE的代理设为了127.0.0.1:8888，而其他浏览器是需要手动设置的，所以如果需要监听PC端Chrome网络请求，将其代理改为127.0.0.1:8888就可以监听数据了，手机端按照下面的设置即可完成整个系统的http代理。
     *  
     * 2、 配置PC端Fiddler和手机
     * (1) 配置Fiddler允许监听https
     * 打开Fiddler菜单项Tools->Fiddler Options，选中decrypt https traffic和ignore server certificate errors两项，如下图：
     * fiddler https options
     * 第一次会提示是否信任fiddler证书及安全提醒，选择yes，之后也可以在系统的证书管理中进行管理。
     *  
     * (2) 配置Fiddler允许远程连接
     * 如上图的菜单中点击connections，选中allow remote computers to connect，默认监听端口为8888，若被占用也可以设置，配置好后需要重启Fiddler，如下图：
     * fiddler remote connect
     *  
     * (3) 配置手机端
     * Pc端命令行ipconfig查看Fiddler所在机器ip，本机ip为10.0.4.37，如下图
     * ipconfig
     * 打开手机连接到同一局域网的wifi，并修改该wifi网络详情(长按wifi选择->修改网络)->显示高级选项，选择手动代理设置，主机名填写Fiddler所在机器ip，端口填写Fiddler端口，默认8888，如下图：
     * android network proxy
     * 这时，手机上的网络访问在Fiddler就可以查看了，如下图微博和微信的网络请求：
     * 微信抓数据包
     * 可以双击上图某一行网络请求，右侧会显示具体请求内容(Request Header)和返回内容(Response Header and Content)，如下图：
     * 微博网络拦截
     * 可以发现Fiddler可以以各种格式查看网络请求返回的数据，包括Header, TextView(文字), ImageView(图片), HexView(十六进制)，WebView(网页形式), Auth(Proxy-Authenticate Header), Caching(Header cache), Cookies, Raw(原数据格式), JSON(json格式), XML(xml格式)很是方便。
     *  
     * 停止网络监控的话去掉wifi的代理设置即可，否则Fiddler退出后手机就上不网了哦。
     *  
     * 如果需要恢复手机无密码状态，Android端之后可以通过系统设置-安全-受信任的凭据-用户，点击证书进行删除或清除凭据删除所有用户证书，再设置密码为无。
     *  
     * 如果只需要监控一个软件，可结合系统流量监控，关闭其他应用网络访问的权限。
     * </pre>
     */

    private final static String TAG = "HttpHelper";

    public final static String DELETE_URL = "http://php.codezyw.com/delete_note_android.php";
    public final static String UPDATE_URL = "http://php.codezyw.com/update_note_android.php";
    public final static String FETCH_URL = "http://php.codezyw.com/fetch_note_android.php";

    /** 本身就是线程安全的 */
    private static HttpClient sHttpClient;

    static {
        /** http://www.codezyw.com/archives/1018 **/
        if (null == sHttpClient) {
            // httpClient = new DefaultHttpClient();
            // httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
            // HttpVersion.HTTP_1_1);
            // httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
            // 20000);
            // 以下代码处理了同一个HttpClient同时发出多个请求时可能发生的多线程问题
            HttpParams httpParams = new BasicHttpParams();

            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
            HttpProtocolParams.setUseExpectContinue(httpParams, true);

            // 设置最大连接数
            ConnManagerParams.setMaxTotalConnections(httpParams, 10);
            // 设置获取连接的最大等待时间
            ConnManagerParams.setTimeout(httpParams, 60000);
            // 设置每个路由最大连接数
            ConnPerRouteBean connPerRoute = new ConnPerRouteBean(8);
            ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
            // 设置连接超时时间
            HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
            // 设置读取超时时间
            HttpConnectionParams.setSoTimeout(httpParams, 30000);

            SchemeRegistry schreg = new SchemeRegistry();
            schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schreg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

            ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParams, schreg);

            sHttpClient = new DefaultHttpClient(connManager, httpParams);
        }
    }

    public static HttpClient getHttpClient() {
        return sHttpClient;
    }

    /**
     * 发送GET请求，并返回响应消息体的字符串内容
     * 
     * @param url 请求URL
     * @return 响应消息体的字符串内容
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        String result = null;
        HttpGet get = new HttpGet(url);
        HttpResponse response = sHttpClient.execute(get);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }
        if (result == null) {
            throw new IOException("result is null");
        }
        return result;
    }

    /**
     * 发送POST请求，并返回响应消息体的字符串内容
     * 
     * @param url 请求URL
     * @return 响应消息体的字符串内容
     * @throws IOException
     */
    public static String post(String url, HashMap<String, String> params) throws IOException {
        String result = null;
        HttpPost post = new HttpPost(url);
        if (null != params) {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>();
            for (Entry<String, String> paramPair : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(paramPair.getKey(), paramPair.getValue());
                pairList.add(pair);
            }
            HttpEntity entity = new UrlEncodedFormEntity(pairList, HTTP.UTF_8);
            post.setEntity(entity);
        }
        HttpResponse response = sHttpClient.execute(post);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }
        return result;
    }

    /**
     * 发送POST请求，消息体使用multipart/form-data编码，以支持多普通字段和多文件上同时上传
     * 
     * @param url 请求URL
     * @param params 普通字符串参数Map
     * @param fileMap 待上传的文件参数Map
     * @return 响应消息体的字符串内容
     * @throws IOException
     */
    public static String multipartPost(String url, HashMap<String, String> params, HashMap<String, File> fileMap) throws IOException {
        String result = null;
        HttpPost post = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        // 处理基本参数
        if (null != params) {
            for (Entry<String, String> paramPair : params.entrySet()) {
                entity.addPart(paramPair.getKey(), new StringBody(paramPair.getValue(), Charset.forName(HTTP.UTF_8)));
            }
        }
        // 处理文件参数
        if (null != fileMap) {
            for (Entry<String, File> paramPair : fileMap.entrySet()) {
                entity.addPart(paramPair.getKey(), new FileBody(paramPair.getValue()));
            }
        }
        post.setEntity(entity);
        HttpResponse response = sHttpClient.execute(post);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }
        return result;
    }

    /**
     * 文件下载
     * 
     * @param url 请求URL
     * @param dest 目标文件对象
     * @throws IOException
     */
    public static void download(String url, File dest) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = sHttpClient.execute(get);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream bis = null;
                BufferedOutputStream bos = null;
                byte[] b = new byte[4096];
                try {
                    bis = entity.getContent();
                    bos = new BufferedOutputStream(new FileOutputStream(dest));
                    for (int count = -1; (count = bis.read(b)) != -1;) {
                        bos.write(b, 0, count);
                    }
                    bos.flush();
                } finally {
                    if (bis != null) {
                        bis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    entity.consumeContent();
                }
            }
        }
    }

    /**
     * 加载远程图片成Drawable对象
     * 
     * @param url 图片路径
     * @return
     * @throws IOException
     */
    public static Drawable loadDrawable(String url) throws IOException {
        Drawable d = null;
        HttpGet get = new HttpGet(url);
        HttpResponse response = sHttpClient.execute(get);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = null;
                try {
                    is = entity.getContent();
                    d = Drawable.createFromStream(is, "src");
                } catch (IOException e) {
                    throw e;
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    entity.consumeContent();
                }
            }
        }
        return d;
    }

    /**
     * 加载远程图片成Bitmap对象
     * 
     * @param url 图片路径
     * @return
     * @throws IOException
     */
    public static Bitmap downloadBitmap(String url) throws IOException {
        Bitmap bmp = null;
        HttpGet get = new HttpGet(url);
        HttpResponse response = sHttpClient.execute(get);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = null;
                try {
                    is = entity.getContent();
                    bmp = BitmapFactory.decodeStream(new FlushedInputStream(is));
                } catch (IOException e) {
                    throw e;
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    entity.consumeContent();
                }
            }
        } else {
            return bmp;
        }
        return bmp;
    }

    /**
     * Many streams obtained over slow connection show <a
     * href="http://code.google.com/p/android/issues/detail?id=6066">this
     * problem</a>.
     * <p>
     * An InputStream that skips the exact number of bytes provided, unless it
     * reaches EOF.
     */
    private static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break; // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /**
     * 使用MultipartEntity模拟表单post提交
     * <p>
     * 比如上传文件
     */
    public static String httpPost(String httpUrl, MultipartEntity entity) {
        if (TextUtils.isEmpty(httpUrl) || entity == null) {
            return null;
        }
        String resultData = null;
        HttpPost httpRequest = new HttpPost(httpUrl);
        try {
            httpRequest.setEntity(entity);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resultEntity = httpResponse.getEntity();
                resultData = EntityUtils.toString(resultEntity, HTTP.UTF_8);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return resultData;
    }

    /**
     * HttpClient
     * 
     * @param httpUrl
     * @return
     */
    public static String httpGet(String httpUrl) {
        if (TextUtils.isEmpty(httpUrl)) {
            return null;
        }
        String resultData = null;
        HttpGet httpGet = new HttpGet(httpUrl);
        HttpClient httpClient = new DefaultHttpClient();
        InputStream inputStream = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                // 请求结果也可以这样返回
                // resultData = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                resultData = result.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return resultData;
    }

    /**
     * HttpClient
     * 
     * @param httpUrl
     * @param params
     * @return
     */
    public static String httpPost(String httpUrl, List<NameValuePair> params) {
        if (TextUtils.isEmpty(httpUrl)) {
            return null;
        }
        String resultData = null;
        HttpPost httpRequest = new HttpPost(httpUrl);
        try {
            HttpEntity httpentity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            httpRequest.setEntity(httpentity);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resultEntity = httpResponse.getEntity();
                resultData = EntityUtils.toString(resultEntity, HTTP.UTF_8);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return resultData;
    }

    /**
     * HttpURLConnection
     * 
     * @param httpUrl
     * @return
     */
    public static String httpGetConnection(String httpUrl) {
        if (TextUtils.isEmpty(httpUrl)) {
            return null;
        }
        String resultData = null;
        BufferedReader buffer = null;
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(httpUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String inputLine = null;
            while (((inputLine = buffer.readLine()) != null)) {
                sb.append(inputLine).append("\n");
            }
            resultData = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return resultData;
    }

    /**
     * HttpURLConnection
     * 
     * @param httpUrl
     * @param parasMap
     * @return
     */
    public static String httpPostConnection(String httpUrl, Map<String, String> parasMap) {
        if (TextUtils.isEmpty(httpUrl)) {
            return null;
        }
        String resultData = null;
        BufferedReader buffer = null;
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(httpUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            // Post 请求不能使用缓存
            urlConn.setUseCaches(false);
            urlConn.setInstanceFollowRedirects(true);
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 从openConnection()至此的配置必须要在connect之前完成
            // 要注意的是getOutputStream会隐含的进行connect
            urlConn.connect();
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
            String para = HttpUtils.joinParasWithEncodedValue(parasMap);
            out.writeBytes(para);
            out.flush();
            out.close();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String inputLine = null;
            while (((inputLine = buffer.readLine()) != null)) {
                sb.append(inputLine).append("\n");
            }
            resultData = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return resultData;
    }

    /**
     * HttpURLConnection
     * <p>
     * http://stackoverflow.com/questions/28559377/sending-multipartentity-using
     * -httpurlconnection
     * <p>
     * http://archive.apache.org/dist/httpcomponents/httpclient/source/
     */
    public static String httpPostMultipart(String httpUrl, MultipartEntity multipartEntity) {
        if (TextUtils.isEmpty(httpUrl)) {
            return null;
        }
        String resultData = null;
        BufferedReader buffer = null;
        HttpURLConnection urlConn = null;
        try {
            URL urlc = new URL(httpUrl);
            urlConn = (HttpURLConnection) urlc.openConnection();
            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Connection", "Keep-Alive");
            urlConn.addRequestProperty("Content-length", multipartEntity.getContentLength() + "");
            urlConn.addRequestProperty(multipartEntity.getContentType().getName(), multipartEntity.getContentType().getValue());

            urlConn.connect();
            OutputStream out = urlConn.getOutputStream();
            multipartEntity.writeTo(out);
            out.flush();
            out.close();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String inputLine = null;
            while (((inputLine = buffer.readLine()) != null)) {
                sb.append(inputLine).append("\n");
            }
            resultData = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return resultData;
    }

    /**
     * AsyncTask异步任务http post提交application/x-www-form-urlencoded
     */
    public static void asyncHttpPost(final String url, final Context context,
            final List<NameValuePair> paramsPair,
            final PostListener postListener) {
        if (context == null || TextUtils.isEmpty(url) || postListener == null) {
            return;
        }
        HttpPostNamedAsyncTask task = new HttpPostNamedAsyncTask(postListener, paramsPair);
        task.execute(url);
    }

    /**
     * AsyncTask异步任务http post提交multipart/form-data
     * <p>
     * <a href="http://bbs.51cto.com/thread-1114378-1-1.html">http://bbs.51cto.
     * com/thread-1114378-1-1.html</a>
     * 
     * <pre>
     * 在android AsyncTask里面有两种线程池供我们调用
     * 1．    THREAD_POOL_EXECUTOR, 异步线程池
     * 2．    SERIAL_EXECUTOR，同步线程池
     * 正如上面名称描述的那样，一个是异步线程池，多个任务在线程池中并发执行；还有一个是同步执行的。
     * 默认的话，直接调用execute的话，是使用SERIAL_EXECUTOR.
     * 
     * 但是系统默认同一个{@link AsyncTask} 对象只能执行一次，因为execute调用第二次会抛出异常@see {@link AsyncTask}。
     * </pre>
     */
    public static void asyncHttpPostMultipart(final String url, final Activity activity,
            ProgressMultipartEntity multipartEntity,
            final PostListener postListener) {
        if (activity == null || TextUtils.isEmpty(url) || postListener == null) {
            return;
        }
        if (multipartEntity == null) {
            Bitmap bitmap = BitmapHelper.getShot(activity);
            if (bitmap == null) {
                return;
            }
            multipartEntity = new ProgressMultipartEntity();
            byte[] data = ImageUtils.bitmapToByte(bitmap);
            multipartEntity.addPart("bitmap", new ByteArrayBody(data, "screenshot.png"));
            bitmap.recycle();
        }
        HttpPostAsyncTask task = new HttpPostAsyncTask(postListener, multipartEntity);
        task.execute(url);
    }

    /**
     * <a href="php.codezyw.com">php.codezyw.com</a>
     */
    public static void deleteServer(Context mContext, Bundle bundle) {
        int id = bundle.getInt(JsonHelper.NOTE_ID);
        final List<NameValuePair> phpParams = new ArrayList<NameValuePair>();
        phpParams.add(new BasicNameValuePair(JsonHelper.ACCOUNT, DbHelper.getInstance(
                mContext).getString(JsonHelper.ACCOUNT, "")));
        phpParams.add(new BasicNameValuePair(JsonHelper.PASSWORD, DbHelper.getInstance(
                mContext).getString(JsonHelper.PASSWORD, "")));
        phpParams.add(new BasicNameValuePair(JsonHelper.NOTE_ID, Integer.toString(id)));
        parseServerResult(mContext, HttpHelper.httpPost(HttpHelper.DELETE_URL, phpParams));
    }

    /**
     * <a href="php.codezyw.com">php.codezyw.com</a>
     */
    public static void updateServer(final Context mContext, Bundle bundle) {
        int id = bundle.getInt(JsonHelper.NOTE_ID);
        String title = bundle.getString(JsonHelper.TITLE);
        String content = bundle.getString(JsonHelper.CONTENT);
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(JsonHelper.ACCOUNT, DbHelper.getInstance(
                mContext).getString(JsonHelper.ACCOUNT, "")));
        postParams.add(new BasicNameValuePair(JsonHelper.PASSWORD, DbHelper.getInstance(
                mContext).getString(JsonHelper.PASSWORD, "")));
        postParams.add(new BasicNameValuePair(JsonHelper.TITLE, title));
        postParams.add(new BasicNameValuePair(JsonHelper.CONTENT, content));
        postParams.add(new BasicNameValuePair(JsonHelper.NOTE_ID, Integer.toString(id)));
        // parseServerResult(mContext,
        // HttpHelper.httpPost(HttpHelper.UPDATE_URL, postParams));
        PostListener postListener = new PostListener() {

            @Override
            public void onProgressUpdate(int progress) {
            }

            @Override
            public void onPreExecute() {
                UIHelper.showToast(mContext, "正在处理HttpPost请求网络请求......");
            }

            @Override
            public void onPostExecute(String result) {
                parseServerResult(mContext, result);
            }

            @Override
            public void onCancelled(String result) {
                parseServerResult(mContext, result);
            }
        };
        asyncHttpPost(HttpHelper.UPDATE_URL, mContext, postParams, postListener);
    }

    /**
     * <a href="php.codezyw.com">php.codezyw.com</a>
     */
    public static List<HttpDataContentBean> fetchServer(Context mContext) {
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(JsonHelper.ACCOUNT, DbHelper.getInstance(mContext)
                .getString(JsonHelper.ACCOUNT, "")));
        postParams.add(new BasicNameValuePair(JsonHelper.PASSWORD, DbHelper.getInstance(
                mContext).getString(JsonHelper.PASSWORD, "")));
        return parseServerResult(mContext, HttpHelper.httpPost(HttpHelper.FETCH_URL, postParams));
    }

    /**
     * <a href="php.codezyw.com">php.codezyw.com</a>
     */
    public static List<HttpDataContentBean> parseServerResult(Context mContext, String result_data) {
        Log.i(TAG, "after http post result_data = " + result_data);
        if (!TextUtils.isEmpty(result_data)) {
            HttpResultBean httpResultBean = JsonHelper.parseHttpResultBeanByJSONSingle(result_data);
            if (httpResultBean != null) {
                Log.i(TAG, ">>>>>>>>>>>>>dump httpResultBean start " + httpResultBean.dump()
                        + ">>>>>>>>>>>>>dump httpResultBean end ");
                if (httpResultBean.getLogin_result()) {
                    if (JsonHelper.FETCH_CMD_FETCH.equals(httpResultBean.getFetch_cmd())) {
                        if (httpResultBean.getFetch_result()) {
                            List<HttpDataContentBean> list = httpResultBean.getResult_data();
                            UIHelper.showToast(mContext, "登录成功,下载服务器数据成功!");
                            if (list != null) {
                                // 避免错误的无限同步循环
                                return list;
                            }
                        } else {
                            UIHelper.showToast(mContext, "登录成功,但是下载服务器数据失败!" + httpResultBean.getError_info());
                        }
                    } else if (JsonHelper.FETCH_CMD_INSERT.equals(httpResultBean.getFetch_cmd())) {
                        if (httpResultBean.getFetch_result()) {
                            UIHelper.showToast(mContext, "登录成功,插入服务器数据成功!");
                        } else {
                            UIHelper.showToast(mContext, "登录成功,但是插入服务器数据失败!" + httpResultBean.getError_info());
                        }
                    } else if (JsonHelper.FETCH_CMD_UPDATE.equals(httpResultBean.getFetch_cmd())) {
                        if (httpResultBean.getFetch_result()) {
                            UIHelper.showToast(mContext, "登录成功,更新服务器数据成功!");
                        } else {
                            UIHelper.showToast(mContext, "登录成功,但是更新服务器数据失败!" + httpResultBean.getError_info());
                        }
                    } else if (JsonHelper.FETCH_CMD_DELETE.equals(httpResultBean.getFetch_cmd())) {
                        if (httpResultBean.getFetch_result()) {
                            UIHelper.showToast(mContext, "登录成功,删除服务器数据成功!");
                        } else {
                            UIHelper.showToast(mContext, "登录成功,但是删除服务器数据失败!" + httpResultBean.getError_info());
                        }
                    }
                } else {
                    UIHelper.showToast(mContext, "登录失败!" + result_data);
                }
            } else {
                UIHelper.showToast(mContext, "JSON错误!" + result_data);
            }
        } else {
            UIHelper.showToast(mContext, "网络错误!" + result_data);
        }
        return null;
    }
}
