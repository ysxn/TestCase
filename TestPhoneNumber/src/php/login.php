<?php
    header("Content-Type: text/html; charset=utf-8") ;
    #php����������Ҫ�������ļ�,conn.php,login.php��getinfo.php��
    #1. conn.php���������ݿ�������ļ���
    #2. login.php��Ҫ��������֤android�ͻ��˷��͵�����,����ɹ��򷵻�flag=��success����״̬��ʶ,���������¼�û�������Ϣ���洢�û����ݵ�session�����Ҽ�¼���β�����session id���û��������ݼ�����session������id����װ��json��ʽ(json_encode($arr)),����android�ͻ��ˡ���������session id�ķ���
    #$sessionid=session_id();//ע��û�в���
    //�������ݿ������ļ�
    include('conn.php');
    session_start();
    $username = htmlspecialchars($_POST["username"]);
    $password=$_POST["password"];
    mysql_query("set names utf8");
    //����û����������Ƿ���ȷ
    $check_query = mysql_query("select id ,name from user where name='$username' and password='$password' limit 1");
    $arr=array();//�յ�����,��������Ҫ�Ǹ�ʽ�����ݲ���װ��json��ʽ���͵��ͻ���
    if($result = mysql_fetch_array($check_query)){
        //��¼�ɹ�
        $_SESSION['username'] = $result['name'];
        $_SESSION['userid'] = $result['id'];
        //��ȡ��ǰsession id
        $sessionid=session_id();
        $_SESSION['$sessionid'] = $sessionid;
        $arr = array(
            'flag'=>'success',
            'name'=>$result['name'],
            'userid'=>$result['id'],
            'sessionid'=>$sessionid
        );
        //��װjson,���php�汾����5.2����֧��json_encode()������
        //���Բο����ļ�����php_json_encode.php��php_json_encode()��������json_encode();
        echo json_encode($arr);
    } else {
        $arr = array(
            'flag'=>'error',
            'name'=>'',
            'userid'=>'',
            'sessionid'=>''
        ); //��װjson,���php�汾����5.2����֧��json_encode()������
        //���Բο����ļ�����php_json_encode.php��php_json_encode()��������json_encode();
        echo json_encode($arr);
    }
?>