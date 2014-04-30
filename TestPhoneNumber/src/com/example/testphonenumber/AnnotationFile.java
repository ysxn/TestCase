package com.example.testphonenumber;

public class AnnotationFile {
    /*
     * 
     * 
1. get是从服务器上获取数据，post是向服务器传送数据。
2. get是把参数数据队列加到提交表单的ACTION属性所指的URL中，值和表单内各个字段一一对应，在URL中可以看到。post是通过HTTP post机制，将表单内各个字段与其内容放置在HTML HEADER内一起传送到ACTION属性所指的URL地址。用户看不到这个过程。
3. 对于get方式，服务器端用Request.QueryString获取变量的值，对于post方式，服务器端用Request.Form获取提交的数据。
4. get传送的数据量较小，不能大于2KB。post传送的数据量较大，一般被默认为不受限制。但理论上，IIS4中最大量为80KB，IIS5中为100KB。
5. get安全性非常低，post安全性较高。但是执行效率却比Post方法好。 

建议：
1、get方式的安全性较Post方式要差些，包含机密信息的话，建议用Post数据提交方式；
2、在做数据查询时，建议用Get方式；而在做数据添加、修改或删除时，建议用Post方式；

get的方式是把数据在地址栏中明文的形式发送
post则不是，而且post可以传递的数据比get


Android中提供的HttpURLConnection和HttpClient接口可以用来开发HTTP程序。以下是本人在学习中的总结与归纳。
1. HttpURLConnection接口
      首先需要明确的是，Http通信中的POST和GET请求方式的不同。GET可以获得静态页面，也可以把参数放在URL字符串后面，传递给服务器。而POST方法的参数是放在Http请求中。因此，在编程之前，应当首先明确使用的请求方法，然后再根据所使用的方式选择相应的编程方式。
      HttpURLConnection是继承于URLConnection类，二者都是抽象类。其对象主要通过URL的openConnection方法获得。创建方法如下代码所示：
Java代码  收藏代码
URL url = new URL("http://www.51cto.com/index.jsp?par=123456");    
HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();  
    通过以下方法可以对请求的属性进行一些设置，如下所示
  
Java代码  收藏代码
//设置输入和输出流    
urlConn.setDoOutput(true);    
urlConn.setDoInput(true);    
//设置请求方式为POST    
urlConn.setRequestMethod("POST");    
//POST请求不能使用缓存    
urlConn.setUseCaches(false);   
//关闭连接    
urlConn.disConnection();   
Manifest文件中权限的设定：
Xml代码  收藏代码
<uses-permission android:name="android.permission.INTERNET" />   
  
HttpURLConnection默认使用GET方式，例如下面代码所示：
Java代码  收藏代码
//以Get方式上传参数  


  如果需要使用POST方式，则需要setRequestMethod设置。代码如下：
Java代码  收藏代码
//以post方式上传参数  


 2. HttpClient接口
 使用Apache提供的HttpClient接口同样可以进行HTTP操作。
 对于GET和POST请求方法的操作有所不同。GET方法的操作代码示例如下：
Java代码  收藏代码


  使用POST方法进行参数传递时，需要使用NameValuePair来保存要传递的参数，另外，还需要设置所使用的字符集。代码如下所示：
Java代码  收藏代码



 
     HttpClient实际上是对Java提供方法的一些封装，在HttpURLConnection中的输入输出流操作，在这个接口中被统一封装成了HttpPost(HttpGet)和HttpResponse，这样，就减少了操作的繁琐性。
    另外，在使用POST方式进行传输时，需要进行字符编码。
    
    Android中提供的HttpURLConnection和HttpClient接口可以用来开发HTTP程序。以下是本人在学习中的总结与归纳。
    1. HttpURLConnection接口
        首先需要明确的是，Http通信中的POST和GET请求方式的不同。GET可以获得静态页面，也可以把参数放在URL字符串后面，传递给服务器。而POST方法的参数是放在Http请求中。因此，在编程之前，应当首先明确使用的请求方法，然后再根据所使用的方式选择相应的编程方式。
        HttpURLConnection是继承于URLConnection类，二者都是抽象类。其对象主要通过URL的openConnection方法获得。创建方法如下代码所示：
     
     
    URL url = new URL("http://www.51cto.com/index.jsp?par=123456");  
    HttpURLConnection urlConn=(HttpURLConnection)url.openConnection(); 
       
        通过以下方法可以对请求的属性进行一些设置，如下所示：
     
     
    //设置输入和输出流  
    urlConn.setDoOutput(true);  
    urlConn.setDoInput(true);  
    //设置请求方式为POST  
    urlConn.setRequestMethod("POST");  
    //POST请求不能使用缓存  
    urlConn.setUseCaches(false);  
    //关闭连接  
    urlConn.disConnection();  
     


    HttpURLConnection默认使用GET方式，例如下面代码所示：

        如果需要使用POST方式，则需要setRequestMethod设置。代码如下：

    2. HttpClient接口
        使用Apache提供的HttpClient接口同样可以进行HTTP操作。
        对于GET和POST请求方法的操作有所不同。GET方法的操作代码示例如下：


        使用POST方法进行参数传递时，需要使用NameValuePair来保存要传递的参数。，另外，还需要设置所使用的字符集。代码如下所示：
     
   
        HttpClient实际上是对Java提供方法的一些封装，在HttpURLConnection中的输入输出流操作，在这个接口中被统一封装成了HttpPost(HttpGet)和HttpResponse，这样，就减少了操作的繁琐性。
        另外，在使用POST方式进行传输时，需要进行字符编码。    

     */
}