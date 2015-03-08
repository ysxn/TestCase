<?php
    header("Content-Type: text/html; charset=utf-8") ;
    #php服务器端主要有三个文件,conn.php,login.php和getinfo.php。
    #1. conn.php是连接数据库的配置文件。
    #2. login.php主要是用来验证android客户端发送的请求,请求成功则返回flag=’success’的状态标识,采用数组记录用户基本信息，存储用户数据到session，并且记录本次产生的session id。用户基本数据及本次session产生的id均封装成json格式(json_encode($arr)),发送android客户端。产生本次session id的方法
    #$sessionid=session_id();//注意没有参数
    //包含数据库连接文件
    include('conn.php');
    session_start();
    $username = htmlspecialchars($_POST["username"]);
    $password=$_POST["password"];
    mysql_query("set names utf8");
    //检测用户名及密码是否正确
    $check_query = mysql_query("select id ,name from user where name='$username' and password='$password' limit 1");
    $arr=array();//空的数组,该数组主要是格式化数据并封装成json格式发送到客户端
    if($result = mysql_fetch_array($check_query)){
        //登录成功
        $_SESSION['username'] = $result['name'];
        $_SESSION['userid'] = $result['id'];
        //获取当前session id
        $sessionid=session_id();
        $_SESSION['$sessionid'] = $sessionid;
        $arr = array(
            'flag'=>'success',
            'name'=>$result['name'],
            'userid'=>$result['id'],
            'sessionid'=>$sessionid
        );
        //封装json,如果php版本低于5.2，则不支持json_encode()方法，
        //可以参考本文件夹中php_json_encode.php中php_json_encode()方法代替json_encode();
        echo json_encode($arr);
    } else {
        $arr = array(
            'flag'=>'error',
            'name'=>'',
            'userid'=>'',
            'sessionid'=>''
        ); //封装json,如果php版本低于5.2，则不支持json_encode()方法，
        //可以参考本文件夹中php_json_encode.php中php_json_encode()方法代替json_encode();
        echo json_encode($arr);
    }
?>