package com.example.testphonenumber;

public class AnnotationFile {
    /*
     * 
     * 
1. get�Ǵӷ������ϻ�ȡ���ݣ�post����������������ݡ�
2. get�ǰѲ������ݶ��мӵ��ύ����ACTION������ָ��URL�У�ֵ�ͱ��ڸ����ֶ�һһ��Ӧ����URL�п��Կ�����post��ͨ��HTTP post���ƣ������ڸ����ֶ��������ݷ�����HTML HEADER��һ���͵�ACTION������ָ��URL��ַ���û�������������̡�
3. ����get��ʽ������������Request.QueryString��ȡ������ֵ������post��ʽ������������Request.Form��ȡ�ύ�����ݡ�
4. get���͵���������С�����ܴ���2KB��post���͵��������ϴ�һ�㱻Ĭ��Ϊ�������ơ��������ϣ�IIS4�������Ϊ80KB��IIS5��Ϊ100KB��
5. get��ȫ�Էǳ��ͣ�post��ȫ�Խϸߡ�����ִ��Ч��ȴ��Post�����á� 

���飺
1��get��ʽ�İ�ȫ�Խ�Post��ʽҪ��Щ������������Ϣ�Ļ���������Post�����ύ��ʽ��
2���������ݲ�ѯʱ��������Get��ʽ��������������ӡ��޸Ļ�ɾ��ʱ��������Post��ʽ��

get�ķ�ʽ�ǰ������ڵ�ַ�������ĵ���ʽ����
post���ǣ�����post���Դ��ݵ����ݱ�get


Android���ṩ��HttpURLConnection��HttpClient�ӿڿ�����������HTTP���������Ǳ�����ѧϰ�е��ܽ�����ɡ�
1. HttpURLConnection�ӿ�
      ������Ҫ��ȷ���ǣ�Httpͨ���е�POST��GET����ʽ�Ĳ�ͬ��GET���Ի�þ�̬ҳ�棬Ҳ���԰Ѳ�������URL�ַ������棬���ݸ�����������POST�����Ĳ����Ƿ���Http�����С���ˣ��ڱ��֮ǰ��Ӧ��������ȷʹ�õ����󷽷���Ȼ���ٸ�����ʹ�õķ�ʽѡ����Ӧ�ı�̷�ʽ��
      HttpURLConnection�Ǽ̳���URLConnection�࣬���߶��ǳ����ࡣ�������Ҫͨ��URL��openConnection������á������������´�����ʾ��
Java����  �ղش���
URL url = new URL("http://www.51cto.com/index.jsp?par=123456");    
HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();  
    ͨ�����·������Զ���������Խ���һЩ���ã�������ʾ
  
Java����  �ղش���
//��������������    
urlConn.setDoOutput(true);    
urlConn.setDoInput(true);    
//��������ʽΪPOST    
urlConn.setRequestMethod("POST");    
//POST������ʹ�û���    
urlConn.setUseCaches(false);   
//�ر�����    
urlConn.disConnection();   
Manifest�ļ���Ȩ�޵��趨��
Xml����  �ղش���
<uses-permission android:name="android.permission.INTERNET" />   
  
HttpURLConnectionĬ��ʹ��GET��ʽ���������������ʾ��
Java����  �ղش���
//��Get��ʽ�ϴ�����  


  �����Ҫʹ��POST��ʽ������ҪsetRequestMethod���á��������£�
Java����  �ղش���
//��post��ʽ�ϴ�����  


 2. HttpClient�ӿ�
 ʹ��Apache�ṩ��HttpClient�ӿ�ͬ�����Խ���HTTP������
 ����GET��POST���󷽷��Ĳ���������ͬ��GET�����Ĳ�������ʾ�����£�
Java����  �ղش���


  ʹ��POST�������в�������ʱ����Ҫʹ��NameValuePair������Ҫ���ݵĲ��������⣬����Ҫ������ʹ�õ��ַ���������������ʾ��
Java����  �ղش���



 
     HttpClientʵ�����Ƕ�Java�ṩ������һЩ��װ����HttpURLConnection�е����������������������ӿ��б�ͳһ��װ����HttpPost(HttpGet)��HttpResponse���������ͼ����˲����ķ����ԡ�
    ���⣬��ʹ��POST��ʽ���д���ʱ����Ҫ�����ַ����롣
    
    Android���ṩ��HttpURLConnection��HttpClient�ӿڿ�����������HTTP���������Ǳ�����ѧϰ�е��ܽ�����ɡ�
    1. HttpURLConnection�ӿ�
        ������Ҫ��ȷ���ǣ�Httpͨ���е�POST��GET����ʽ�Ĳ�ͬ��GET���Ի�þ�̬ҳ�棬Ҳ���԰Ѳ�������URL�ַ������棬���ݸ�����������POST�����Ĳ����Ƿ���Http�����С���ˣ��ڱ��֮ǰ��Ӧ��������ȷʹ�õ����󷽷���Ȼ���ٸ�����ʹ�õķ�ʽѡ����Ӧ�ı�̷�ʽ��
        HttpURLConnection�Ǽ̳���URLConnection�࣬���߶��ǳ����ࡣ�������Ҫͨ��URL��openConnection������á������������´�����ʾ��
     
     
    URL url = new URL("http://www.51cto.com/index.jsp?par=123456");  
    HttpURLConnection urlConn=(HttpURLConnection)url.openConnection(); 
       
        ͨ�����·������Զ���������Խ���һЩ���ã�������ʾ��
     
     
    //��������������  
    urlConn.setDoOutput(true);  
    urlConn.setDoInput(true);  
    //��������ʽΪPOST  
    urlConn.setRequestMethod("POST");  
    //POST������ʹ�û���  
    urlConn.setUseCaches(false);  
    //�ر�����  
    urlConn.disConnection();  
     


    HttpURLConnectionĬ��ʹ��GET��ʽ���������������ʾ��

        �����Ҫʹ��POST��ʽ������ҪsetRequestMethod���á��������£�

    2. HttpClient�ӿ�
        ʹ��Apache�ṩ��HttpClient�ӿ�ͬ�����Խ���HTTP������
        ����GET��POST���󷽷��Ĳ���������ͬ��GET�����Ĳ�������ʾ�����£�


        ʹ��POST�������в�������ʱ����Ҫʹ��NameValuePair������Ҫ���ݵĲ����������⣬����Ҫ������ʹ�õ��ַ���������������ʾ��
     
   
        HttpClientʵ�����Ƕ�Java�ṩ������һЩ��װ����HttpURLConnection�е����������������������ӿ��б�ͳһ��װ����HttpPost(HttpGet)��HttpResponse���������ͼ����˲����ķ����ԡ�
        ���⣬��ʹ��POST��ʽ���д���ʱ����Ҫ�����ַ����롣    

     */
}