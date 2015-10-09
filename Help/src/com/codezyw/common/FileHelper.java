/**
 * http://www.blogjava.net/flysky19/articles/92286.html
 * <p>
 * <pre>
File,FileInputStream,FileReader,InputStreamReader,BufferedReader 的使用和区别

参考资料：

l         《 core java 》 12 章

l         使用 Java 操作文本文件的方法详解

http://java.ccidnet.com/art/3737/20041108/523627_1.html

l  FileReader 是什么类？和 FileInputStream 有什么不同？？？      

 http://book.hackbase.com/ask2/ask107572.htm
自己的整理和领会：
引言：
    
C语言只需要一个File*就可以了，与C不同，java有一系列流类型，其数量超过60种。类库的设计者声称：“有

足够的理由为用户提供丰富的流类型的选择：这样做可以减少程序的错误。”例如，在C语言种，许多人认为“

将输出流写入一个只读模式的文件”是很常见的错误。（事实上，这并不常见。）

我们认为在C＋＋语言中，流接口设计者避免程序出错的主要“工具”是小心谨慎的态度，在java语言中更是如

此。流库的高度复杂性迫使程序设计人员谨小慎微。
    
1.  File类
  
1 ） File 类介绍（《 core java 》 638 页）

File 类封装了对用户机器的文件系统进行操作的功能。例如，可以用 File 类获得文件上次修改的时间移动，

或者对文件进行删除、重命名。换句话说，流类关注的是文件内容，而 File 类关注的是文件在磁盘上的存储

。

File 类的主要方法有： getName(),getCanonicalFile(),lastModified(),isDerector(),isFile(),getPath()

等；

2 ） File 类与 FileInputStream 类的区别：

流类关注的是文件内容，而 File 类关注的是文件在磁盘上的存储。

File 不属于文件流 , 只能代表一个文件或是目录的路径名而已。

提示：（《 core java 》 639 页）

如果处理文件或者目录名，就应该使用 File 对象，而不是字符串。例如， File 类的 equals 方法知道一些

文件系统对大小写是敏感的，目录尾的“ / ”字符无关紧要。

自己的领会：

FileInputStream 类或者 FileReader 类的构造函数有多个，其中典型的两个分别为：一个使用 File 对象为

参数；而另一个使用表示路径的 String 对象作为参数；自己以前一直觉得直接用了 String 指定路径就可以

了，一直不明白为什么很多人都先构造一个 File 对象，现在终于明白了，“如果处理文件或者目录名，就应

该使用 File 对象，而不是字符串。”！

2.       FileInputStream 类

1 ） FileInputStream 类介绍：

以字节为单位（非 unicode ）的流处理。字节序列即：二进制数据。与编码无关，不存在乱码问题。

FileInputStream 类的主要方法有：

Read （）， read （ byte[] b ）， read （ byte[],int off,int len ） ,available();

2 ） FileInputStream 类与 FileReader 类的区别：

两个类的构造函数的形式和参数都是相同的，参数为 File 对象或者表示路径的 String ，它们到底有何区别

呢？

l         Readers and Writers work only on line based character data, so plain text files.
For anything else, you MUST use Streams.

l         JDK5 API:

FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams

of characters, consider using FileReader.

FileReader is meant for reading streams of characters. For reading streams of raw bytes, consider

using a FileInputStream .

l         FileInputStream ：以字节流方式读取； FileReader ：把文件转换为字符流读入； 
l        InputStream提供的是字节流的读取，而非文本读取，这是和Reader类的根本区别。用Reader读取出

来的是char数组或者String ，使用InputStream读取出来的是byte数组。
l        Reader类及其子类提供的字符流的读取char（16位,unicode编码），inputStream及其子类提供字节

流的读取byte（8位），所以FileReader类是将文件按字符流的方式读取，FileInputStream则按字节流的方式

读取文件；InputStreamReader可以将读如stream转换成字符流方式，是reader和stream之间的桥梁
l  最初Java是不支持对文本文件的处理的，为了弥补这个缺憾而引入了Reader和Writer两个类。
  
l         FileInputStream 类以二进制输入 / 输出， I/O 速度快且效率搞，但是它的 read （）方法读到

的是一个字节（二进制数据），很不利于人们阅读。

l         而 FileReader 类弥补了这个缺陷，可以以文本格式输入 / 输出，非常方便；比如可以使用

while((ch = filereader.read())!=-1 ) 循环来读取文件；可以使用 BufferedReader 的 readLine() 方法一

行一行的读取文本。

l         当我们读写文本文件的时候，采用 Reader 是非常方便的，比如 FileReader ，

InputStreamReader 和 BufferedReader 。其中最重要的类是 InputStreamReader ，它是字节转换为字符的桥

梁。 你可以在构造器重指定编码的方式，如果不指定的话将采用底层操作系统的默认编码方式，例如 GBK 等

。

l         FileReader 与 InputStreamReader 涉及编码转换 ( 指定编码方式或者采用 os 默认编码 ) ，可

能在不同的平台上出现乱码现象！而 FileInputStream 以二进制方式处理，不会出现乱码现象 .

3 ）自己的领会：

l         如果处理纯文本文件，建议使用 FileReader ，因为更方便，也更适合阅读；但是要注意编码问题

！ 
l   其他情况（处理非纯文本文件），FileInputStream是唯一的选择；FileInputStream是进Socket通讯时会

用到很多，如将文件流是Stream的方式传向服务器！
  
3.       FileReader 类

1）    FileReader 类介绍：

InputStreamReader 类的子类，所有方法（ read （）等）都从父类 InputStreamReader 中继承而来；

2）    与 InputStreamReader 类的区别：

l         自己的领会：

该类与它的父类 InputStreamReader 的主要不同在于构造函数，主要区别也就在于构造函数！从

InputStreamReader 的构造函数中看到，参数为 InputStream 和编码方式，可以看出，当要指定编码方式时，

必须使用 InputStreamReader 类；而 FileReader 构造函数的参数与 FileInputStream 同，为 File 对象或

表示 path 的 String ，可以看出，当要根据 File 对象或者 String 读取一个文件时，用 FileReader ；我

想 FileReader 子类的作用也就在于这个小分工吧。

3）    一般用法：

FileReader fr = new FileReader("ming.txt");
　　 char[] buffer = new char[1024];
　　 int ch = 0;
　　 while((ch = fr.read())!=-1 )
　　 {
　　　 System.out.print((char)ch); 
　　 }

4.       InputStreamReader 类

l         以文本格式输入 / 输出，可以指定编码格式；

l         主要方法：

getEncoding ()，read();

l         一般用法：

InputStreamReader isr = new InputStreamReader(new FileInputStream("ming.txt"));
　　 while((ch = isr.read())!=-1)
　　 {
　　　 System.out.print((char)ch); 
　　 }

5.       BufferedReader 类

l         Jdk5 api ：

Read text from a character-input stream, buffering characters so as to provide for the efficient

reading of characters, arrays, and lines. 
l    BufferedReader 由Reader类扩展而来，提供通用的缓冲方式文本读取，而且提供了很实用的readLine，

读取分行文本很适合，BufferedReader是针对Reader的，不直接针对文件，也不是只针对文件读取。
l  一般用法：
    
BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ming.txt")));
　　String data = null;
　　while((data = br.readLine())!=null)
　　{
　　　System.out.println(data); 
　　}
    
  
6.       总结以上内容，得出比较好的规范用法：

1）    File file = new File ("hello.txt");

FileInputStream in=new FileInputStream(file);

2）    File file = new File ("hello.txt");

FileInputStream in=new FileInputStream(file);

InputStreamReader inReader=new InputStreamReader(in);

BufferedReader bufReader=new BufferedReader(inReader);

3）    File file = new File ("hello.txt");

FileReader fileReader=new FileReader(file);

BufferedReader bufReader=new BufferedReader(fileReader);

7.一些写法的区别：
1）
File file = new File ("hello.txt");
FileInputStream in=new FileInputStream(file);
InputStreamReader inReader=new InputStreamReader(in);
BufferedReader bufReader=new BufferedReader(inReader); 

2）
FileInputStream in＝null;
File file = new File ("hello.txt");
in=new FileInputStream(file);
BufferedReader bufReader=new BufferedReader(new InputStreamReader(in)); 

3）
File file = new File ("hello.txt");
BufferedReader bufReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));

上述两种写法的微小区别：
a）第二种方式中把“FileInputStream in＝null;”定义单独放在开始处，说明下面应该还有要用到in对象变量的地方；（BufferedReader处用了）

b）第二种方式没有定义InputStreamReader的对象变量，直接在BufferedReader的构造函数中new一个，
这种方式与第一种方式的主要区别：InputStreamReader对象只使用一次！

这对于在这里只需要使用一次这个InputStreamReader对象的应用来说更好；无需定义InputStreamReader的对象变量，接收由new返回的该对象的引用，因为下面的程序中不需要这个InputStreamReader的对象变量，所以无需定义；所以这种情况下，第二种方式比第一种更好一些。

c）第三种方式中，典型的三层嵌套委派关系，清晰看出Reader的委派模式（《corejava》12章有图描述该委派关系），FileInputStream和InputStreamReader都没有定义变量，new生成的对象都只是使用一次。

d）三种方式的区别也就在于FileInputStream和InputStreamReader对象是否都只使用一次，是否需要定义它们的对象变量，以及个人的编码习惯。

e)但是要注意异常处理，FileInputStream(file)会抛出NotFileFoundException，如果采用surround方式
（try&catch）处理，应该用第二种方式，这样可以用System.out.println提示文件未找到；
当然在函数名后使用throws Exception，然后用第三种方式也行，但似乎这适合有用户界面的情况，把异常抛出在客户端在处理。
 * </pre>
 * 
 * @author zhuyw1
 */

package com.codezyw.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <pre>
 * getCacheDir()方法用于获取/data/data/<application package>/cache目录
 * 
 * getFilesDir()方法用于获取/data/data/<application package>/files目录
 * 
 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 * 
 * 应用程序在运行的过程中如果需要向手机上保存数据，一般是把数据保存在SDcard中的。
 * 大部分应用是直接在SDCard的根目录下创建一个文件夹，然后把数据保存在该文件夹中。
 * 这样当该应用被卸载后，这些数据还保留在SDCard中，留下了垃圾数据。
 * 如果你想让你的应用被卸载后，与该应用相关的数据也清除掉，该怎么办呢？
 * 
 * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用包名/files/ 目录，一般放一些长时间保存的数据
 * 通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
 * 
 * 如果使用上面的方法，当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。
 * 而且上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项
 * 
 * 如果要保存下载的内容，就不要放在以上目录下
 * 
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 * 
 * 较优秀的程序都会专门写一个方法来获取缓存地址，如下所示：
 * 
 * 
 * public String getDiskCacheDir(Context context) {  
 *     String cachePath = null;  
 *     if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
 *             || !Environment.isExternalStorageRemovable()) {  
 *         cachePath = context.getExternalCacheDir().getPath();  
 *     } else {  
 *         cachePath = context.getCacheDir().getPath();  
 *     }  
 *     return cachePath;  
 * }  
 * 可以看到，当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，否则就调用getCacheDir()方法来获取缓存路径。前者获取到的就是 /sdcard/Android/data/<application package>/cache 这个路径，而后者获取到的是 /data/data/<application package>/cache 这个路径。
 * 
 * 
 * 
 * 
 * 参考：http://blog.csdn.net/beihai1212/article/details/7055899
 * 
 * http://blog.csdn.net/zhiyi2010/article/details/13017171
 * 
 * http://blog.csdn.net/guolin_blog/article/details/28863651
 * 
 * 
 * 
 * android程序扫描储存时，如果使用API：EnvironmentgetExternalStorageDirectory()getPath()获得的是默
 * 可以先判断下Environment.getExternalStorageDirectory().getParentFile()，如果返回null则没有父路径，取Environment.getExternalStorageDirectory().getPath()为当前父路径。
 *  
 * Android开发:filePath放在哪个文件夹
 * Environment.getDataDirectory() = /data
 * Environment.getDownloadCacheDirectory() = /cache
 * Environment.getExternalStorageDirectory() = /mnt/sdcard
 * Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
 * Environment.getRootDirectory() = /system
 * getPackageCodePath() = /data/app/com.my.app-1.apk
 * getPackageResourcePath() = /data/app/com.my.app-1.apk
 * getCacheDir() = /data/data/com.my.app/cache 
 * getFilesDir() = /data/data/com.my.app/files
 * getDatabasePath(“test”) = /data/data/com.my.app/databases/test
 * getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
 * getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
 * getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
 * getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
 * 
 * </pre>
 */
public class FileHelper {

    /**
     * 电子书后缀
     */
    public static final String TXTEND = ".txt";
    /**
     * pdf格式后缀
     */
    public static final String PDFEND = ".pdf";
    /**
     * doc格式后缀
     */
    public static final String DOCEND = ".doc";
    /**
     * xml格式后缀
     */
    public static final String XMLEND = ".xml";
    /**
     * 设置编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 超时时间
     */
    private static final int TIME_OUT = 10 * 1000;
    /**
     * 内容类型
     */
    private static final String CONTENT_TYPE = "multipart/form-data";
    private static final String BOUNDARY = UUID.randomUUID().toString();
    /**
     * 设置时间格式
     */
    private static final SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * {后缀名，MIME类型}
     */
    public static String[][] MIME_MapTable = {
            {
                    ".3gp", "video/3gpp"
            },
            {
                    ".apk", "application/vnd.Android.package-archive"
            },
            {
                    ".asf", "video/x-ms-asf"
            },
            {
                    ".avi", "video/x-msvideo"
            },
            {
                    ".bin", "application/octet-stream"
            },
            {
                    ".bmp", "image/bmp"
            },
            {
                    ".c", "text/plain"
            },
            {
                    ".class", "application/octet-stream"
            },
            {
                    ".conf", "text/plain"
            },
            {
                    ".cpp", "text/plain"
            },
            {
                    ".doc", "application/msword"
            },
            {
                    ".docx",
                    "application/vnd.openxmlformats-officedocument"
                            + ".wordprocessingml.document"
            },
            {
                    ".xls", "application/vnd.ms-excel"
            },
            {
                    ".xlsx",
                    "application/vnd.openxmlformats-officedocument"
                            + "spreadsheetml.sheet"
            },
            {
                    ".exe", "application/octet-stream"
            },
            {
                    ".gif", "image/gif"
            },
            {
                    ".gtar", "application/x-gtar"
            },
            {
                    ".gz", "application/x-gzip"
            },
            {
                    ".h", "text/plain"
            },
            {
                    ".htm", "text/html"
            },
            {
                    ".html", "text/html"
            },
            {
                    ".jar", "application/java-archive"
            },
            {
                    ".java", "text/plain"
            },
            {
                    ".jpeg", "image/jpeg"
            },
            {
                    ".jpg", "image/jpeg"
            },
            {
                    ".js", "application/x-javascript"
            },
            {
                    ".log", "text/plain"
            },
            {
                    ".m3u", "audio/x-mpegurl"
            },
            {
                    ".m4a", "audio/mp4a-latm"
            },
            {
                    ".m4b", "audio/mp4a-latm"
            },
            {
                    ".m4p", "audio/mp4a-latm"
            },
            {
                    ".m4u", "video/vnd.mpegurl"
            },
            {
                    ".m4v", "video/x-m4v"
            },
            {
                    ".mov", "video/quicktime"
            },
            {
                    ".mp2", "audio/x-mpeg"
            },
            {
                    ".mp3", "audio/x-mpeg"
            },
            {
                    ".mp4", "video/mp4"
            },
            {
                    ".mpc", "application/vnd.mpohun.certificate"
            },
            {
                    ".mpe", "video/mpeg"
            },
            {
                    ".mpeg", "video/mpeg"
            },
            {
                    ".mpg", "video/mpeg"
            },
            {
                    ".mpg4", "video/mp4"
            },
            {
                    ".mpga", "audio/mpeg"
            },
            {
                    ".msg", "application/vnd.ms-outlook"
            },
            {
                    ".ogg", "audio/ogg"
            },
            {
                    ".pdf", "application/pdf"
            },
            {
                    ".png", "image/png"
            },
            {
                    ".pps", "application/vnd.ms-powerpoint"
            },
            {
                    ".ppt", "application/vnd.ms-powerpoint"
            },
            {
                    ".pptx",
                    "application/vnd.openxmlformats-officedocument"
                            + ".presentationml.presentation"
            },
            {
                    ".prop", "text/plain"
            }, {
                    ".rc", "text/plain"
            },
            {
                    ".rmvb", "audio/x-pn-realaudio"
            }, {
                    ".rtf", "application/rtf"
            },
            {
                    ".sh", "text/plain"
            }, {
                    ".tar", "application/x-tar"
            },
            {
                    ".tgz", "application/x-compressed"
            }, {
                    ".txt", "text/plain"
            },
            {
                    ".wav", "audio/x-wav"
            }, {
                    ".wma", "audio/x-ms-wma"
            },
            {
                    ".wmv", "audio/x-ms-wmv"
            },
            {
                    ".wps", "application/vnd.ms-works"
            }, {
                    ".xml", "text/plain"
            },
            {
                    ".z", "application/x-compress"
            },
            {
                    ".zip", "application/x-zip-compressed"
            }, {
                    "", "*/*"
            }
    };

    public static final String[] fileEndingImage = {
            ".png",
            ".gif",
            ".jpg",
            ".jpeg",
            ".bmp",
    };

    public static final String[] fileEndingAudio = {
            ".mp3",
            ".wav",
            ".ogg",
            ".midi",
    };

    public static final String[] fileEndingVideo = {
            ".mp4",
            ".rmvb",
            ".avi",
            ".flv",
    };

    public static final String[] fileEndingPackage = {
            ".jar",
            ".zip",
            ".rar",
            ".gz",
            ".apk",
            ".img",
    };

    public static final String[] fileEndingWebText = {
            ".htm",
            ".html",
            ".php",
            ".jsp",
    };

    public static final String[] fileEndingText = {
            ".txt",
            ".java",
            ".c",
            ".cpp",
            ".py",
            ".xml",
            ".json",
            ".log",
    };

    public static final String[] fileEndingWord = {
            ".doc",
            ".docx",
    };

    public static final String[] fileEndingExcel = {
            ".xls",
            ".xlsx",
    };

    public static final String[] fileEndingPPT = {
            ".ppt",
            ".pptx",
    };

    public static final String[] fileEndingPdf = {
            ".pdf",
    };

    /**
     * 匹配文件后缀名
     * 
     * @param checkItsEnd
     * @param fileEndings
     * @return
     */
    public static boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    /**
     * 根据文件类型使用对应app应用打开
     * 
     * @param c
     * @param f
     */
    public static void tryOpenFile(Context c, File f) {
        if (f != null && f.isFile()) {
            String fileName = f.getName();
            Intent intent;
            if (checkEndsWithInStringArray(fileName, fileEndingImage)) {
                intent = getImageFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingWebText)) {
                intent = getHtmlFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingPackage)) {
                intent = getApkFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingAudio)) {
                intent = getAudioFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingVideo)) {
                intent = getVideoFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingText)) {
                intent = getTextFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingPdf)) {
                intent = getPdfFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingWord)) {
                intent = getWordFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingExcel)) {
                intent = getExcelFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingPPT)) {
                intent = getPPTFileIntent(f);
                c.startActivity(intent);
            } else {
                try {
                    intent = getTextFileIntent(f);
                    c.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打开html文件
     * 
     * @param file
     * @return
     */
    public static Intent getHtmlFileIntent(File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString())
                .build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * 打开图片文件
     * 
     * @param file
     * @return
     */
    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * 打开pdf文件
     * 
     * @param file
     * @return
     */
    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 打开文本文件
     * 
     * @param file
     * @return
     */
    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    /**
     * 打开音频文件
     * 
     * @param file
     * @return
     */
    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * 打开视频文件
     * 
     * @param file
     * @return
     */
    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * 打开chm文件
     * 
     * @param file
     * @return
     */
    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * 打开word文档
     * 
     * @param file
     * @return
     */
    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * 打开excel文档
     * 
     * @param file
     * @return
     */
    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * 打开ppt文档
     * 
     * @param file
     * @return
     */
    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * 打开apk文件
     * 
     * @param file
     * @return
     */
    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 开始搜索文件方法, 返回找到的第一个文件
     * 
     * @param file 搜索目录
     * @param key 搜索关键字
     * @return 返回找到的第一个文件, 找不到返回null
     */
    public static String toSearchFiles(File file, String key) {
        if (file == null || !file.isDirectory() || TextUtils.isEmpty(key)) {
            return null;
        }
        File[] allSubFiles = file.listFiles();
        // 通过遍历所有文件和文件夹
        for (File tempF : allSubFiles) {
            if (tempF.isDirectory()) {
                String path = toSearchFiles(tempF, key);
                if (!TextUtils.isEmpty(path)) {
                    return path;
                }
            } else {
                try {
                    // 是文件，则进行比较，如果文件名称包含输入搜索Key，则返回大于-1的值
                    if (tempF.getName().indexOf(key) > -1) {
                        // 获取符合条件的文件路径，进行累加
                        return tempF.getPath();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * 
     * @return 应用程序是/否获取Root权限
     */
    public static boolean getRootPermission() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su"); // 切换到root帐号
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * 
     * @return 应用程序是/否获取Root权限
     */
    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); // 切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    /**
     * 获取手机自身内存路径 : Environment.getDataDirectory() = /data
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * 获取sd卡路径 双sd卡时，根据”设置“里面的数据存储位置选择，获得的是内置sd卡或外置sd卡
     * 
     * @return
     */
    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取sd卡路径 双sd卡时，获得的是外置sd卡
     * 
     * @return
     */
    public static String getSDCardPath() {
        // 这个好像不行,红米1S内部sd卡是/storage/emulated/legacy，外部sd卡是/storage/sdcard1
        // /dev/fuse /storage/emulated/legacy fuse
        // rw,nosuid,nodev,relatime,user_id=1023,group_id=1023,default_permissions,allow_other
        // 0 0
        // /dev/block/vold/179:65 /storage/sdcard1 vfat
        // rw,dirsync,nosuid,nodev,noexec,relatime,uid=1000,gid=1015,fmask=0202,dmask=0202,allow_utime=0020,codepage=cp437,iocharset=iso8859-1,shortname=mixed,utf8,errors=remount-ro
        // 0 0

        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        BufferedInputStream in = null;
        BufferedReader inBr = null;
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            in = new BufferedInputStream(p.getInputStream());
            inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                Log.i("CommonUtil:getSDCardPath", lineStr);
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure", "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    Log.e("CommonUtil:getSDCardPath", "命令执行失败!");
                }
            }
        } catch (Exception e) {
            Log.e("CommonUtil:getSDCardPath", e.toString());
            // return Environment.getExternalStorageDirectory().getPath();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inBr != null) {
                    inBr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 查看所有的sd路径
     * 
     * @return
     */
    public String getSDCardPathEx() {
        String mount = new String();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        mount = mount.concat("*" + columns[1] + "\n");
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        mount = mount.concat(columns[1] + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mount;
    }

    /**
     * 获取当前路径，可用空间
     * 
     * @param path
     * @return
     */
    public static long getAvailableSize(String path) {
        return getAvailableSize(new File(path));
    }

    /**
     * 获取当前路径，可用空间<br>
     * Check how much usable space is available at a given path.
     * 
     * @param path The path to check
     * @return The space available in bytes by user, not by root, -1 means path
     *         is null, 0 means path is not exist.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getAvailableSize(File path) {
        if (path == null) {
            return -1;
        }
        if (Version.hasGingerbread()) {
            return path.getUsableSpace();
        } else {
            if (!path.exists()) {
                return 0;
            } else {
                final StatFs stats = new StatFs(path.getPath());
                return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
            }
        }
    }

    /**
     * 获取路径下已经使用的空间大小
     * 
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getUsedSpace(File path) {
        if (path == null) {
            return -1;
        }

        if (Version.hasGingerbread()) {
            return path.getTotalSpace() - path.getUsableSpace();
        } else {
            if (!path.exists()) {
                return -1;
            } else {
                final StatFs stats = new StatFs(path.getPath());
                return (long) stats.getBlockSize()
                        * (stats.getBlockCount() - stats.getAvailableBlocks());
            }
        }
    }

    /**
     * 获取目录下总空间大小
     * 
     * @param path
     * @return -1 means path is null, 0 means path is not exist.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getTotalSpace(File path) {
        if (path == null) {
            return -1;
        }
        if (Version.hasGingerbread()) {
            return path.getTotalSpace();
        } else {
            if (!path.exists()) {
                return 0;
            } else {
                final StatFs stats = new StatFs(path.getPath());
                return (long) stats.getBlockSize() * (long) stats.getBlockCount();
            }
        }
    }

    /**
     * 判断是否有Sdcard
     * 
     * @return
     */
    public static boolean hasSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (Environment.getExternalStorageDirectory() == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取应用在外部存储上的缓存目录, 应用卸载时候这些文件会被删除。<br>
     * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用包名/files/
     * 目录，一般放一些长时间保存的数据<br>
     * 通过Context.getExternalCacheDir()方法可以获取到
     * SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据<br>
     * <p>
     * these files will be deleted when the application is uninstalled,
     * 
     * @param context The context to use
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String getExternalCacheDir(Context context, String dir) {
        if (Version.hasFroyo()) {
            File path = context.getExternalCacheDir();
            // In some case, even the sd card is mounted, getExternalCacheDir
            // will return null, may be it is nearly full.
            if (path != null) {
                if (!TextUtils.isEmpty(dir)) {
                    path = new File(path.getAbsolutePath() + File.separator + dir + File.separator);
                }
                if (!path.exists()) {
                    path.mkdirs();
                }
                return path.getAbsolutePath();
            }
        }
        // Before Froyo or the path is null, we need to construct the external
        // cache folder ourselves
        StringBuilder sb = new StringBuilder();
        sb.append("/Android/data/").append(context.getPackageName()).append("/files/");
        if (!TextUtils.isEmpty(dir)) {
            sb.append(dir + File.separator);
        }
        final String cacheDir = sb.toString();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取应用在外部存储上的缓存目录, 应用卸载时候这些文件会被删除。<br>
     * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用包名/files/
     * 目录，一般放一些长时间保存的数据<br>
     * 通过Context.getExternalCacheDir()方法可以获取到
     * SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据<br>
     * <p>
     * these files will be deleted when the application is uninstalled,
     * 
     * @param context The context to use
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String getExternalFileDir(Context context, String dir) {
        if (TextUtils.isEmpty(dir)) {
            dir = null;
        }
        if (Version.hasFroyo()) {
            File path = context.getExternalFilesDir(dir);
            // In some case, even the sd card is mounted, getExternalCacheDir
            // will return null, may be it is nearly full.
            if (path != null) {
                if (!path.exists()) {
                    path.mkdirs();
                }
                return path.getAbsolutePath();
            }
        }
        // Before Froyo or the path is null, we need to construct the external
        // cache folder ourselves
        StringBuilder sb = new StringBuilder();
        sb.append("/Android/data/").append(context.getPackageName()).append("/files/");
        if (!TextUtils.isEmpty(dir)) {
            sb.append(dir + File.separator);
        }
        final String fileDir = sb.toString();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 请求一般首选外置存储位置<br>
     * external: "/storage/emulated/0/Android/data/in.srain.sample/files/dir"<br>
     * internal: "/data/data/in.srain.sample/files/dir"
     */
    public static String getSdcardDir(Context context, String dir) {
        if (TextUtils.isEmpty(dir)) {
            dir = "temp";
        }
        String path = null;
        File f = null;
        if (hasSDCard()
                && (f = new File(getExternalFileDir(context, dir))) != null) {
            path = f.getAbsolutePath();
        } else {
            path = context.getFilesDir().getAbsolutePath() + File.separator + dir;
        }
        f = new File(path);
        f.mkdirs();
        return path;
    }

    /**
     * 首选外置Sdcard根目录<br>
     * external: "/storage/emulated/0/dir"<br>
     * 否则返回：<br>
     * internal: "/data/data/in.srain.sample/files/dir"
     * 
     * @param context
     * @param dir
     * @return
     */
    public static String getSdcardRootDir(Context context, String dir) {
        if (TextUtils.isEmpty(dir)) {
            dir = "temp";
        }
        String path = null;
        File f = new File(Environment.getExternalStorageDirectory(), dir);
        if (f != null && !f.exists()) {
            f.mkdirs();
        }
        if (hasSDCard()
                && f != null && f.exists()) {
            path = f.getAbsolutePath();
        } else {
            path = context.getFilesDir().getAbsolutePath() + File.separator + dir + File.separator;
        }
        f = new File(path);
        f.mkdirs();
        return path;
    }

    /**
     * 序列化对象转换为byte数组
     * 
     * @param obj
     * @return
     */
    public static byte[] serialize(Serializable obj) {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        byte[] ary = null;
        try {
            out = new ObjectOutputStream(os);
            out.writeObject(obj);// 对象序列化
            out.close();
            ary = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ary;
    }

    /**
     * byte数组转换为序列化对象
     * 
     * @param data
     * @return
     */
    public static Object unserialize(byte[] data) {
        if (data == null || data.length <= 0) {
            return null;
        }
        Object o = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new ByteArrayInputStream(data));
            o = in.readObject();// 反序列化
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return o;
    }

    /**
     * 写入Serializable对象到磁盘
     */
    public static void writeSerialize(Context context, String key, Serializable object) {
        String path = getExternalFileDir(context, null);
        File file = new File(path, key);
        Gson gson = new Gson();
        String json = gson.toJson(object);
        FileOutputStream fs = null;
        if (TextUtils.isEmpty(json)) {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                fs = new FileOutputStream(file);
                fs.write(json.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fs != null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 读取Serializable对象到内存
     */
    public static Serializable readSerialize(Context context, String key) {
        Serializable object = null;
        String path = getExternalFileDir(context, null);
        File file = new File(path, key);
        if (file != null && file.exists()) {
            BufferedReader buffer = null;
            try {
                // FileInputStream fis = new FileInputStream(file);
                // byte[] tempbytes = new byte[100];
                // ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(0);
                // int byteread = 0;
                // while ((byteread = fis.read(tempbytes)) != -1) {
                // byteArrayBuffer.append(tempbytes, 0, byteread);
                // }
                // String resultData = new String(byteArrayBuffer.toByteArray(),
                // "UTF-8");
                // byteArrayBuffer.clear();

                buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                StringBuilder sb = new StringBuilder();
                String inputLine = null;
                while (((inputLine = buffer.readLine()) != null)) {
                    sb.append(inputLine).append("\n");
                }
                String resultData = sb.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<Serializable>() {
                }.getType();
                object = gson.fromJson(resultData, listType);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return object;
    }

    /**
     * 对象的深层赋值(克隆)
     * 
     * @param obj
     * @return 对象的副本
     */
    public static Object clone(Serializable obj) {
        return unserialize(serialize(obj));
    }

    /**
     * 工具文件后缀名，使用对应应用打开文件
     * 
     * @param context
     * @param file
     */
    public static void openFile(Context context, File file) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型
            String type = getMIMEType(file);
            // 设置intent的data和Type属性。
            intent.setDataAndType(/* uri */Uri.fromFile(file), type);
            // 跳转
            context.startActivity(intent);
            // Intent.createChooser(intent, "请选择对应的软件打开该附件！");
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "sorry附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 上传文件
     * 
     * @param file
     * @param serverUrl
     * @return
     */
    public static String uploadFile(File file, String serverUrl) {
        HttpURLConnection conn = getConnect(serverUrl);
        String result = null;
        // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";

        try {
            // conn.connect();//建立连接
            if (file != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                // 采用数据流包装文件上传
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                System.out.println("响应码--->" + res);
                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(input));
                    StringBuilder sb1 = new StringBuilder();
                    String s = "";
                    while ((s = reader.readLine()) != null) {
                        sb1.append(s);
                    }
                    result = sb1.toString();
                    System.out.println(result);
                } else {
                    System.out.println("上传文件失败！");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将指定路径的文件解析为字符串
     * 
     * @param path
     * @param charset
     * @return
     */
    public static String parseFileToString(String path, String charset) {
        String info = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                info += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return info;
    }

    /**
     * @param link 上传自己所在的地理位置
     * @return 返回响应的内容
     */
    public static String uploadLoc(String link) {
        String result = null;
        try {
            HttpURLConnection conn = getConnect(link);
            conn.connect();// 建立连接
            // 返回响应码
            int res = conn.getResponseCode();
            System.out.println("返回响应参数" + res);
            if (res == 200) {
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                String s = "";
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }
                result = sb.toString();
                System.out.println("响应结果-->" + result);
            } else {
                System.out.println("没有响应！");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据link返回HttpURLConnection连接实例
     * 
     * @param link
     * @return
     */
    public static HttpURLConnection getConnect(String link) {
        // 打开链接
        HttpURLConnection conn = null;
        try {
            // 创建URL
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            // 设置读取时间
            conn.setReadTimeout(TIME_OUT);
            // 设置连接超时时间
            conn.setConnectTimeout(TIME_OUT);
            // 允许输入流
            conn.setDoInput(true);
            // 允许输出流
            conn.setDoOutput(true);
            // 不允许使用缓存
            conn.setUseCaches(false);
            // 请求方式---post请求
            conn.setRequestMethod("POST");
            // 设置字符编码--相关参数设置
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取链接的输入流
     * 
     * @param str
     * @return
     */
    public static InputStream getInputStream(String url) {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return entity.getContent();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取目录的全部文件
     * 
     * @param dir
     * @return
     */
    public static List<File> listFile(File dir) {
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        return new ArrayList<File>(Arrays.asList(files));
    }

    /**
     * 获取目录的全部文件, 指定扩展名的文件
     * 
     * @param dir
     * @return
     */
    public static List<File> listFile(File dir, final String ext) {

        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(ext);
            }
        });
        return new ArrayList<File>(Arrays.asList(files));
    }

    /**
     * 递归获取目录的全部文件
     * 
     * @param dir
     * @return
     */
    public static List<File> listAll(File dir) {
        List<File> all = listFile(dir);
        File[] subs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File sub : subs) {
            all.addAll(listAll(sub));
        }
        return all;
    }

    /**
     * 递归获取目录的全部文件, 指定扩展名的文件
     * 
     * @param dir
     * @return
     */
    public static List<File> listAll(File dir, String ext) {
        List<File> all = listFile(dir, ext);
        File[] subs = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File sub : subs) {
            all.addAll(listAll(sub, ext));
        }
        return all;
    }

    /**
     * 复制文件
     */
    public static void cp(String from, String to) throws IOException {
        cp(new File(from), new File(to));
    }

    /**
     * 复制文件
     */
    public static void cp(File from, File to) throws IOException {
        FileInputStream in = new FileInputStream(from);
        OutputStream out = new FileOutputStream(to);
        cp(in, out);
        in.close();
        out.close();
    }

    /**
     * 复制文件
     */
    public static void cp(InputStream in, OutputStream out) throws IOException {
        // 1K byte 的缓冲区!
        byte[] buf = new byte[1024];
        int count;
        while ((count = in.read(buf)) != -1) {
            // System.out.println(count);
            out.write(buf, 0, count);
        }
        in.close();
        out.close();
    }

    public static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int b;
        while (true) {
            b = in.read();
            if (b == '\n' || b == '\r' || b == -1) {// 编码是否是回车换行
                break;
            }
            out.write(b);
        }
        return new String(out.toByteArray());
    }

    /**
     * 从流中读取一行文本, 读取到一行的结束为止
     * 
     * @param in
     * @return 一行文本
     */
    public static String readLine(InputStream in, String charset)
            throws IOException {
        byte[] buf = new byte[0];
        int b = 0;
        while (true) {
            b = in.read();
            if (b == '\n' || b == '\r' || b == -1) {// 编码是否是回车换行
                break;
            }
            buf = Arrays.copyOf(buf, buf.length + 1);
            buf[buf.length - 1] = (byte) b;
        }
        if (buf.length == 0 && b == -1) {
            return null;
        }
        return new String(buf, charset);
    }

    /**
     * 读取文件的全部内容到一个byte数组 可以缓存一个"小"文件到堆内存中
     */
    public static byte[] read(String filename) throws IOException {
        return read(new File(filename));
    }

    /**
     * 读取文件的全部内容到一个byte数组 可以缓存一个"小"文件到堆内存中
     */
    public static byte[] read(File file) throws IOException {
        // 用RAF打开文件
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        // 安装文件的长度开辟 缓冲区数组(byte数组)
        byte[] buf = new byte[(int) raf.length()];
        // 读取文件的缓冲区
        raf.read(buf);
        // 关闭文件(RAF)
        raf.close();
        // 返回缓冲区数组引用.
        return buf;
    }

    /**
     * 读取文件的全部内容到一个byte数组 可以缓存一个"小"文件到堆内存中 如: 文件内容: ABC中 读取为: {41, 42, 43, d6,
     * d0}
     */
    public static byte[] read(InputStream in) throws IOException {
        byte[] ary = new byte[in.available()];
        in.read(ary);
        in.close();
        return ary;
    }

    /**
     * 连接byte 数组的全部内容为字符串, 以hex(十六进制)形式连接 如: 数组{0x41, 0x42, 0x43, 0xd6, 0xd0}
     * 结果: "[41, 42, 43, d6, d0]"
     */
    public static String join(byte[] ary) {
        if (ary == null || ary.length == 0)
            return "[]";
        StringBuilder buf = new StringBuilder();
        buf.append("[").append(
                leftPad(Integer.toHexString(ary[0] & 0xff), '0', 2));
        for (int i = 1; i < ary.length; i++) {
            String hex = Integer.toHexString(ary[i] & 0xff);
            buf.append(",").append(leftPad(hex, '0', 2));
        }
        buf.append("]");
        return buf.toString();
    }

    public static String toBinString(byte[] ary) {
        if (ary == null || ary.length == 0)
            return "[]";
        StringBuilder buf = new StringBuilder();
        buf.append("[").append(
                leftPad(Integer.toBinaryString(ary[0] & 0xff), '0', 8));
        for (int i = 1; i < ary.length; i++) {
            String hex = Integer.toBinaryString(ary[i] & 0xff);
            buf.append(",").append(leftPad(hex, '0', 8));
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * 实现leftPad功能, 对字符串实现左填充
     * 
     * @param str 被填充字符串: 5
     * @param ch 填充字符: #
     * @param length 填充以后的长度: 8
     * @return "#######5"
     */
    public static String leftPad(String str, char ch, int length) {
        if (str.length() == length) {
            return str;
        }
        char[] chs = new char[length];
        Arrays.fill(chs, ch);
        System.arraycopy(str.toCharArray(), 0, chs, length - str.length(),
                str.length());
        return new String(chs);
    }

    /**
     * 将text追加到文件 filename的尾部 使用系统默认文本编码
     */
    public static void println(String filename, String text) throws IOException {
        println(new File(filename), text);
    }

    public static void println(File file, String text) throws IOException {
        OutputStream out = new FileOutputStream(file, true);
        println(out, text);
        out.close();
    }

    /**
     * 向流中输出一行字符串, 使用默认编码 不关闭流
     * 
     * @param out 目标流
     * @param text 文本
     * @throws IOException
     */
    public static void println(OutputStream out, String text)
            throws IOException {
        out.write(text.getBytes());
        out.write('\n');
        out.flush();
    }

    /**
     * 向流中输出一行字符串, 使用指定的编码 不关闭流
     * 
     * @param out 目标流
     * @param text 文本
     * @param charset 指定的编码
     * @throws IOException
     */
    public static void println(OutputStream out, String text, String charset)
            throws IOException {
        out.write(text.getBytes(charset));
        out.write('\n');
    }

    /**
     * 切分文件, 如: file.dat 切分为 file.dat.0, file.dat.1 ...
     * 
     * @param file
     * @param size 大小, 以KByte为单位
     */
    public static void split(String file, int size) throws IOException {
        if (size <= 0) {
            throw new IllegalArgumentException("搞啥呀!");
        }
        int idx = 0;// 文件的序号
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file
                + "." + idx++));
        int b;
        int count = 0;
        while ((b = in.read()) != -1) {
            out.write(b);
            count++;
            if (count % (size * 1024) == 0) {
                out.close();
                out = new BufferedOutputStream(new FileOutputStream(file + "."
                        + idx++));
            }
        }
        in.close();
        out.close();
    }

    /**
     * 将文件进行连接
     * 
     * @param filename 是第一个文件名,如:file.dat.0
     */
    public static void join(String file) throws IOException {
        String filename = file.substring(0, file.lastIndexOf("."));
        String num = file.substring(file.lastIndexOf(".") + 1);
        int idx = Integer.parseInt(num);
        OutputStream out = new FileOutputStream(filename);
        File f = new File(filename + "." + idx++);
        while (f.exists()) {
            InputStream in = new FileInputStream(f);
            cp(in, out);
            in.close();
            f = new File(filename + "." + idx++);
        }
        out.close();
    }

    /**
     * 读取流中到字符数组
     * 
     * @param in
     * @return
     */
    public static char[] readChar(Reader in) throws IOException {
        StringBuilder buf = new StringBuilder();
        int c;
        while ((c = in.read()) != -1) {
            buf.append((char) c);
        }
        in.close();
        return buf.toString().toCharArray();
    }

    public static char[] readChar(String filename, String encoding)
            throws IOException {
        return readChar(new File(filename), encoding);
    }

    public static char[] readChar(File file, String encoding)
            throws IOException {
        return readChar(new FileInputStream(file), encoding);
    }

    public static char[] readChar(InputStream in, String encoding)
            throws IOException {
        return readChar(new InputStreamReader(in, encoding));
    }

    /**
     * 工具后缀名获取MIME类型
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {

            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 读取assert目录下文件
     * 
     * @param context
     * @param filePath file path relative to assets, like
     *            request_init1/search_index.json
     * @return
     */
    public static String readAssert(Context context, String filePath) {
        try {
            if (filePath.startsWith(File.separator)) {
                filePath = filePath.substring(File.separator.length());
            }
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filePath);
            DataInputStream stream = new DataInputStream(inputStream);
            int length = stream.available();
            byte[] buffer = new byte[length];
            stream.readFully(buffer);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(buffer);
            stream.close();
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查2个文件长度是否相同，不检查MD5
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean checkFile(File a, File b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() == b.length()) {
            return true;
        }
        return false;
    }

    /**
     * 解析时间格式为毫秒
     * 
     * @param str
     * @return
     */
    public static Long convertDate(String str) {
        if (str == null || str.isEmpty()) {
            return Long.valueOf(System.currentTimeMillis());// 0L;
        }
        try {
            Date date = sFormat.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(System.currentTimeMillis());
    }

    /**
     * 毫秒转换为时间格式
     * 
     * @param modify
     * @return
     */
    public static String convetTime(long modify) {
        return sFormat.format(new Date(modify));
    }

    /**
     * 安装apk文件
     * 
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装apk文件
     * 
     * @param context
     * @param path
     */
    public static void installApk(Context context, String path) {
        File file = new File(path);
        installApk(context, file);
    }

    /**
     * 卸载apk应用
     * 
     * @param mContext
     * @param name 这个形式是 package:程序完整的路径 (包名+程序名) package:com.demo.CanavaCancel
     */
    public static void uninstallApk(Context mContext, String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        // 卸载：
        // Environment拥有一些可以获取环境变量的方法
        // package:com.demo.CanavaCancel 这个形式是 package:程序完整的路径 (包名+程序名).
        Uri packageURI = Uri.parse("package:com.demo.CanavaCancel" + name);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        try {
            mContext.startActivity(uninstallIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载apk应用
     * 
     * @param httpUrl
     * @return
     */
    public static File downLoadFile(Context context, String httpUrl) {
        final String fileName = "updata.apk";
        File tmpFile = new File(getSdcardRootDir(context, "update"));
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File(getSdcardRootDir(context, "update") + fileName);
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }

                        } else {
                            break;
                        }

                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取apk文件里面的信息<br>
     * 网传的方法,但获取不成功<br>
     * 其实可以的，见appInfo.publicSourceDir = "file.getAbsolutePath()";
     */
    public static Drawable getUninatllApkInfo(Context context, String archiveFilePath, File file) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_SIGNATURES);// PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            // 这是因为对于未安装应用ApplicationInfo未能获取到对应的source path。
            // 只要在getApplicationIcon()前传入source path即可：
            // add-absolute path of app
            appInfo.publicSourceDir = archiveFilePath;
            Drawable d = pm.getApplicationIcon(appInfo);
            return d;
        }
        return null;
    }

    /**
     * 获取apk文件里面的信息PackageInfo<br>
     */
    public static PackageInfo getPackageInfo(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_SIGNATURES);// PackageManager.GET_ACTIVITIES);
        return info;
    }

    /**
     * @param context
     * @param apkPath
     */
    public static void showUninstallAPKIcon(Context context, String apkPath) {
        String PATH_PackageParser = "android.content.pm.PackageParser";
        String PATH_AssetManager = "android.content.res.AssetManager";
        try {
            // apk包的文件路径
            // 这是一个Package 解释器, 是隐藏的
            // 构造函数的参数只有一个, apk文件的路径
            // PackageParser packageParser = new PackageParser(apkPath);
            Class pkgParserCls = Class.forName(PATH_PackageParser);
            Class[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            Object pkgParser = pkgParserCt.newInstance(valueArgs);
            Log.d("ANDROID_LAB", "pkgParser:" + pkgParser.toString());
            // 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            // PackageParser.Package mPkgInfo = packageParser.parsePackage(new
            // File(apkPath), apkPath,
            // metrics, 0);
            typeArgs = new Class[4];
            typeArgs[0] = File.class;
            typeArgs[1] = String.class;
            typeArgs[2] = DisplayMetrics.class;
            typeArgs[3] = Integer.TYPE;
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage", typeArgs);
            valueArgs = new Object[4];
            valueArgs[0] = new File(apkPath);
            valueArgs[1] = apkPath;
            valueArgs[2] = metrics;
            valueArgs[3] = 0;
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser, valueArgs);
            // 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开
            // ApplicationInfo info = mPkgInfo.applicationInfo;
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField("applicationInfo");
            ApplicationInfo info = (ApplicationInfo) appInfoFld.get(pkgParserPkg);
            // uid 输出为"-1"，原因是未安装，系统未分配其Uid。
            Log.d("ANDROID_LAB", "pkg:" + info.packageName + " uid=" + info.uid);
            // Resources pRes = getResources();
            // AssetManager assmgr = new AssetManager();
            // assmgr.addAssetPath(apkPath);
            // Resources res = new Resources(assmgr, pRes.getDisplayMetrics(),
            // pRes.getConfiguration());
            Class assetMagCls = Class.forName(PATH_AssetManager);
            Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
            Object assetMag = assetMagCt.newInstance((Object[]) null);
            typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath", typeArgs);
            valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
            Resources res = context.getResources();
            typeArgs = new Class[3];
            typeArgs[0] = assetMag.getClass();
            typeArgs[1] = res.getDisplayMetrics().getClass();
            typeArgs[2] = res.getConfiguration().getClass();
            Constructor resCt = Resources.class.getConstructor(typeArgs);
            valueArgs = new Object[3];
            valueArgs[0] = assetMag;
            valueArgs[1] = res.getDisplayMetrics();
            valueArgs[2] = res.getConfiguration();
            res = (Resources) resCt.newInstance(valueArgs);
            CharSequence label = null;
            if (info.labelRes != 0) {
                label = res.getText(info.labelRes);
            }
            // if (label == null) {
            // label = (info.nonLocalizedLabel != null) ? info.nonLocalizedLabel
            // : info.packageName;
            // }
            Log.d("ANDROID_LAB", "label=" + label);
            // 这里就是读取一个apk程序的图标
            if (info.icon != 0) {
                Drawable icon = res.getDrawable(info.icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
