
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

import cn.trinea.android.common.util.HttpUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

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
 */
public class HttpHelper {
    @SuppressWarnings("unused")
    private final static String TAG = "HttpHelper";

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
     * http://stackoverflow.com/questions/28559377/sending-multipartentity-using-httpurlconnection
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
            urlConn.addRequestProperty("Content-length", multipartEntity.getContentLength()+"");
            urlConn.addRequestProperty(multipartEntity.getContentType().getName(), multipartEntity.getContentType().getValue());

            urlConn.connect();
            OutputStream  out = urlConn.getOutputStream();
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
}
