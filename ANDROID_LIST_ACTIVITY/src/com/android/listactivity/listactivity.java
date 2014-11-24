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
        //Java 代码中的读取联系人信息处理
        try
        {
        	//Cursor ,该接口提供从数据库提取信息返回结果中随机读写访问
        	//ContentResolver 提供应用程序访问Content模型
        	//ContentResolver.query()抽取给定的URI，返回一个在结果之上的Cursor
        	Cursor c = getContentResolver().query(People.CONTENT_URI, 
        			null, null, null, null);
        	
        	//该方法允许Activity基于Activity的生命周期来为你处理管理给定的Cursor的什么周期
        	startManagingCursor(c);
        	//新建用户名数组 ,People类用于列出联系人，People.NAME得到联系人姓名
        	name = new String[] {People.NAME};
        	//创建一个TextView引用数组，用来放置获取的 People.NAME
        	to = new int[] {R.id.row_entry};
        	//创建一个简单的适配器从一个Cursor指针到TextView或是ImageView的map专栏适配器
        	//构造方法(Context,layout,Cursor,from,to),第一参数是设备上下文，第二个参数是布局文
        	//件，第三个参数是指向联系人URI的Cursor指针，form代表来源的字符串(联系人名)，to把联系人名放到
        	//的地方(TextView)
        	sadapter = new SimpleCursorAdapter(this, R.layout.main, c, name, to);
        	
        }catch (Exception e)
        {
        	//Toast显示提示，不打扰用户。.show()用来显示Toast
        	Toast.makeText(this, "联系人读取错误", Toast.LENGTH_LONG).show();
        }
        //设置适配器
        //setListAdapter(sadapter);
        getListView().setAdapter(sadapter);
    }
    //Java 代码中的点击事件处理(选择后电话联系该联系人)
    //选项按键事件处理，不需要.setOnItemClickListener(); 直接实现以下方法
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	super.onListItemClick(l, v, position, id);
    	//创建一个带有Call动作的Intent
    	callIntent = new Intent(Intent.ACTION_CALL);
    	//通过选中的Items位置来获取相应的联系人指针Cursor
    	Cursor c = (Cursor)sadapter.getItem(position);
    	//获取相应联系人电话号码
    	//getLong()返回请求数据的一个长整型
    	//为给定的列，返回基于0的索引值
    	//People.PRIMARY_PHONE_ID 获取主键电话号码
    	PhoneID = c.getLong(c.getColumnIndex(People.PRIMARY_PHONE_ID));
    	//为Intent设置操作的数据
    	//ContentUris操作带有数据内容的Uri的实用方法、它们带有"Content"体制
    	//withAppendedId()把给定的ID添加到path后面第一个参数是开始的，后面参数是添加的
    	callIntent.setData(ContentUris.withAppendedId(
    			android.provider.Contacts.Phones.CONTENT_URI, PhoneID));
    	//开启Intent
    	startActivity(callIntent);
    }

}