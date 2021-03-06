
package com.codezyw.common;

/**
 * 1.仿照AsyncTask可以设置线程名称. 但是默认只能执行一次。<br>
 * 结合ThreadPoolHelper做到线程池和主线程回调一一对应,这只能使用handler来保证回调运行在主线程。<br>
 * <p>
 * 2.在BaseActivity的onCreate里面把崩溃统计提交给线程池上传服务器
 * ，线程池里面只有一个线程，队列无限大(newSingleThreadExecutor
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。)
 * <p>
 * 3.前面崩溃统计在崩溃后先保存在sharepreference里面，多次崩溃怎么保存？文件分段保存再解析吗，不方便删除某条崩溃记录，
 * 所以不采用？还是数据库的表里面？ 如果数据库的表里面应该要有崩溃发生时间字段。
 * <p>
 * 4.json对应bean保存到文件缓存里面。
 * <p>
 * 5.下载管理器，对应显示下载状态的activity使用listview读取下载管理数据库DownloadHelper
 * <p>
 * 6.开启session
 * <p>
 * 7.开启ssl <br>
 * <a href="http://blog.csdn.net/raptor/article/details/18896375">说明1</a><br>
 * <a href="http://blog.csdn.net/raptor/article/details/18898937">说明2</a><br>
 * <p>
 * 8.在BaseActivity里面插入滑动返回
 * <p>
 * 9.php页面注册新用户前判断当前是管理员登录
 * <p>
 * 9_2.php页面插入新条目不用传参ID
 * <p>
 * 9_3.php页删除条目
 * <p>
 * 10.客户端注册新帐号-----暂不考虑
 * <p>
 * 11.夜间模式<a href=
 * "http://blog.zhaiyifan.cn/2015/09/10/Android%E6%8D%A2%E8%82%A4%E6%8A%80%E6%9C%AF%E6%80%BB%E7%BB%93/"
 * >夜间模式</a>
 * <p>
 * 12.性能优化1<a
 * href="http://www.trinea.cn/android/java-android-performance/">性能优化1</a><br>
 * 性能优化2<a href="http://android.jobbole.com/81274/">性能优化2</a> <br>
 * 性能优化3<a
 * href="http://www.trinea.cn/android/mobile-performance-optimization/">性能优化3
 * </a> <br>
 * 算法<a href="http://www.importnew.com/16266.html">算法</a>
 * <p>
 * 13.常用开源项目1<a href="http://p.codekk.com/">常用开源项目1</a> <br>
 * 常用开源项目2<a href="http://www.liaohuqiu.net/cn/index/page2/">常用开源项目2</a><br>
 * 常用开源项目3<a href="https://github.com/Trinea/android-open-project">常用开源项目3</a><br>
 * 常用开源项目4<a
 * href="http://www.trinea.cn/android/android-open-project-summary/">常用开源项目4</a>
 * <br>
 * <p>
 * 14.Canvas常用画图函数
 * <p>
 * 
 * <pre>
 * 15.Socket, NIO
 * 
 * http://blog.csdn.net/kongxx/article/category/1077912
 * 
 * http://blog.csdn.net/Anders_Zhuo/article/category/1299564
 * 
 * http://ifeve.com/overview/
 * 
 * File IO操作: https://github.com/awen83/TestCase/blob/master/Help/src/com/codezyw/common/FileHelper.java
 * read,write IO操作: https://github.com/awen83/TestCase/blob/master/SocketChatServer/nio.doc
 * 
 * 16.Android_View_View绘制流程
 * 
 * http://blog.csdn.net/zimo2013/article/details/44829583
 * 
 * 17.android Fragments (Android官方文档中文版)
 * 
 * http://blog.csdn.net/xyz_lmn/article/details/6927390
 * 
 * android Fragments (Android官方文档中文版)
 * 
 * http://blog.csdn.net/xyz_lmn/article/details/6930753
 * 
 * 18.深入理解Android之Java Security第一部分
 * 
 * http://blog.csdn.net/innost/article/details/44081147
 * 
 * 19.Android Design Support Library使用详解
 * http://blog.csdn.net/eclipsexys/article/details/46349721
 * 
 * 20.android6.0变化
 * http://developer.android.com/about/versions/marshmallow/android-6.0-changes.html
 * 
 * 21.proguard
 * http://proguard.sourceforge.net/index.html#manual/refcard.html
 * 
 * 22.Android Studio
 * http://segmentfault.com/a/1190000002401964
 * 
 * 23.HTML标签
 * http://www.w3school.com.cn/tags/tag_pre.asp
 * </pre>
 */
public class ReadMe {

}
