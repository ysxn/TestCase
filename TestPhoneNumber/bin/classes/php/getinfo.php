<?php
    header("Content-Type: text/html; charset=utf-8") ;
    #getinfo.php�ļ���Ҫ���û��ٴβ�ѯ��Ϣ��֤session�����������²���session���Լ�¼�û�״̬��ͨ����֤flag�Ƿ�Ϊempty�ж������Ƿ���ʾ������װ��json���͵��ͻ���
    #��ȡ����session�ķ�����
    #$sessionid=$_POST["sess_sessionid"];//��ȡandroid�ͻ��˵�sessionid
    #session_id($sessionid);//�в���
    #session_start();//����session

    include('conn.php');
    //��ȡ�ӿͻ���LoginSuccessActivity�ഫ�ݵĲ���
    $userid=$_POST["sess_userid"];
    $username=$_POST["sess_username"];
    //��ȡ�ͻ��˴��ݵ�session��ʶ
    $sessionid=$_POST["sess_sessionid"];
    session_id($sessionid);
    //�������session id���ԭ����session
    session_start();
    //��ȡ��������ԭ��session��¼��username,���Ҹ��ݿͻ��˴�������username�ȽϽ�����֤����
    $sess_username=$_SESSION['username'];
    if($username==$sess_username){
        mysql_query("set names utf8");
        //��ѯ�û�������Ϣ
        $check_query = mysql_query("select userinfo,level from info where userid='$userid'  limit 1");
        $arr=array();//�յ�����
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