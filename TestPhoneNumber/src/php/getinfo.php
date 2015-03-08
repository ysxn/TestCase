<?php
    header("Content-Type: text/html; charset=utf-8") ;
    #getinfo.php文件主要是用户再次查询信息验证session，而不是重新产生session，以记录用户状态。通过验证flag是否为empty判断数据是否显示。最后封装成json发送到客户端
    #获取本次session的方法：
    #$sessionid=$_POST["sess_sessionid"];//获取android客户端的sessionid
    #session_id($sessionid);//有参数
    #session_start();//启动session

    include('conn.php');
    //获取从客户端LoginSuccessActivity类传递的参数
    $userid=$_POST["sess_userid"];
    $username=$_POST["sess_username"];
    //获取客户端传递的session标识
    $sessionid=$_POST["sess_sessionid"];
    session_id($sessionid);
    //将会根据session id获得原来的session
    session_start();
    //获取服务器端原来session记录的username,并且根据客户端传过来的username比较进行验证操作
    $sess_username=$_SESSION['username'];
    if($username==$sess_username){
        mysql_query("set names utf8");
        //查询用户基本信息
        $check_query = mysql_query("select userinfo,level from info where userid='$userid'  limit 1");
        $arr=array();//空的数组
        if($result = mysql_fetch_array($check_query)){
            $arr = array(
                'flag'=>'notempty',
                'info'=>$result['userinfo'],
                'level'=>$result['level'],
                'sessionid'=>$sessionid
            );
            echo json_encode($arr);
        }
    } else {
        $arr = array(
            'flag'=>'empty',
            'name'=>'',
            'userid'=>'',
            'sessionid'=>$sessionid
        );
        echo json_encode($arr);
    }
?>