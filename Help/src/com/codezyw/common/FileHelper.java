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

package com.codezyw.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class FileHelper {
    private Context mContext;

    /**
     * 设置时间格式
     */
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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

    public static boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

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

    public static Intent getHtmlFileIntent(File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString())
                .build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    // 开始搜索文件方法
    public static String toSearchFiles(File file, String key) {
        // 定义一个File文件数组，用来存放/sdcard目录下的文件或文件夹
        File[] the_Files = file.listFiles();
        // int index = sizeof(the_Files);
        // 通过遍历所有文件和文件夹
        for (File tempF : the_Files) {
            Log.i("zyw", "infomation in sdcard search: File tempF = " + tempF.getName());
            if (tempF.isDirectory()) {
                String path = toSearchFiles(tempF, key);
                if (TextUtils.isEmpty(path)) {
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
                    // 如果路径找不到，提示出错
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
     * 获取手机自身内存路径
     */
    public static String getPhoneCardPath() {
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (inBr != null) {
                    inBr.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    // 查看所有的sd路径
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mount;
    }

    // 获取当前路径，可用空间
    public static long getAvailableSize(String path) {
        try {
            File base = new File(path);
            StatFs stat = new StatFs(base.getPath());
            long nAvailableCount = stat.getBlockSize() * ((long) stat.getAvailableBlocks());
            return nAvailableCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public FileHelper(Context c) {
        mContext = c;
    }

    public boolean checkFile(File a, File b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() == b.length()) {
            return true;
        }
        return false;
    }

    public Long convertDate(String str) {
        if (str == null || str.isEmpty()) {
            return Long.valueOf(System.currentTimeMillis());// 0L;
        }
        try {
            Date date = mFormat.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(System.currentTimeMillis());
    }

    public String convetTime(long modify) {
        return mFormat.format(new Date(modify));
    }

    public void installApk(File file) {
        // 安装
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void uninstallApk() {
        // 卸载：
        // Environment拥有一些可以获取环境变量的方法
        // package:com.demo.CanavaCancel 这个形式是 package:程序完整的路径 (包名+程序名).
        Uri packageURI = Uri.parse("package:com.demo.CanavaCancel");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        try {
            mContext.startActivity(uninstallIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 下载apk程序代码
    public File downLoadFile(String httpUrl) {
        final String fileName = "updata.apk";
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);

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
                    Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();
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
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        return file;
    }

    /** 网传的方法,但获取不成功 */
    // 其实可以的，见appInfo.publicSourceDir = "file.getAbsolutePath()";
    public Drawable getUninatllApkInfo(Context context, String archiveFilePath, File file) {
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

    public PackageInfo getPackageInfo(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_SIGNATURES);// PackageManager.GET_ACTIVITIES);

        return info;
    }

    //
    private void showUninstallAPKIcon(String apkPath) {
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
            Resources res = mContext.getResources();
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
