package com.android.listactivity;



import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class listactivity extends ListActivity {
	private String[] name;
	private int[] to;
	private SimpleCursorAdapter sadapter;
	private Intent callIntent;
	private long PhoneID;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Java �����еĶ�ȡ��ϵ����Ϣ����
        try
        {
        	//Cursor ,�ýӿ��ṩ�����ݿ���ȡ��Ϣ���ؽ���������д����
        	//ContentResolver �ṩӦ�ó������Contentģ��
        	//ContentResolver.query()��ȡ������URI������һ���ڽ��֮�ϵ�Cursor
        	Cursor c = getContentResolver().query(People.CONTENT_URI, 
        			null, null, null, null);
        	
        	//�÷�������Activity����Activity������������Ϊ�㴦����������Cursor��ʲô����
        	startManagingCursor(c);
        	//�½��û������� ,People�������г���ϵ�ˣ�People.NAME�õ���ϵ������
        	name = new String[] {People.NAME};
        	//����һ��TextView�������飬�������û�ȡ�� People.NAME
        	to = new int[] {R.id.row_entry};
        	//����һ���򵥵���������һ��Cursorָ�뵽TextView����ImageView��mapר��������
        	//���췽��(Context,layout,Cursor,from,to),��һ�������豸�����ģ��ڶ��������ǲ�����
        	//����������������ָ����ϵ��URI��Cursorָ�룬form������Դ���ַ���(��ϵ����)��to����ϵ�����ŵ�
        	//�ĵط�(TextView)
        	sadapter = new SimpleCursorAdapter(this, R.layout.main, c, name, to);
        	
        }catch (Exception e)
        {
        	//Toast��ʾ��ʾ���������û���.show()������ʾToast
        	Toast.makeText(this, "��ϵ�˶�ȡ����", Toast.LENGTH_LONG).show();
        }
        //����������
        //setListAdapter(sadapter);
        getListView().setAdapter(sadapter);
    }
    //Java �����еĵ���¼�����(ѡ���绰��ϵ����ϵ��)
    //ѡ����¼���������Ҫ.setOnItemClickListener(); ֱ��ʵ�����·���
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	super.onListItemClick(l, v, position, id);
    	//����һ������Call������Intent
    	callIntent = new Intent(Intent.ACTION_CALL);
    	//ͨ��ѡ�е�Itemsλ������ȡ��Ӧ����ϵ��ָ��Cursor
    	Cursor c = (Cursor)sadapter.getItem(position);
    	//��ȡ��Ӧ��ϵ�˵绰����
    	//getLong()�����������ݵ�һ��������
    	//Ϊ�������У����ػ���0������ֵ
    	//People.PRIMARY_PHONE_ID ��ȡ�����绰����
    	PhoneID = c.getLong(c.getColumnIndex(People.PRIMARY_PHONE_ID));
    	//ΪIntent���ò���������
    	//ContentUris���������������ݵ�Uri��ʵ�÷��������Ǵ���"Content"����
    	//withAppendedId()�Ѹ�����ID��ӵ�path�����һ�������ǿ�ʼ�ģ������������ӵ�
    	callIntent.setData(ContentUris.withAppendedId(
    			android.provider.Contacts.Phones.CONTENT_URI, PhoneID));
    	//����Intent
    	startActivity(callIntent);
    }

}