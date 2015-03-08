
package com.example.testphonenumber;

import java.util.HashMap;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * GetUserInfoActivity.java���Ǹ����û���¼�����Ψһsession ��ʶ���в�����ȡ�û���ϸ��Ϣ���ࡣ
 * @author zhuyawen
 *
 */
public class GetUserInfoActivity extends Activity {
    private HashMap<String, String> session;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info);
        // ��ȡ�ӵ�¼�ɹ��������ٴδ��ݵĲ���
        session = (HashMap<String, String>) this.getIntent().getBundleExtra("session")
                .getSerializable("sessionid");
        // ��ȡsession�Ļ�����Ϣ������ʾ��Ӧ�Ŀؼ�
        String session_info = session.get("info_userinfo");
        String session_level = session.get("info_level");
        String session_id = session.get("info_sessionid");
        // ��ʾ��Ӧ�����ݵ��ؼ�
        System.out.println("session_info--------" + session_info);
        TextView get_info = (TextView) findViewById(R.id.get_info);
        get_info.setText(session_info);
        TextView get_level = (TextView) findViewById(R.id.get_level);
        get_level.setText(session_level);
        TextView get_sessionid = (TextView) findViewById(R.id.get_sessionid);
        get_sessionid.setText(session_id);
    }
}
