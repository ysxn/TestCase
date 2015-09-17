package com.example.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private LinearLayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

		// improve performance if you know that changes in content
		// do not change the size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(mLayoutManager);
		// 创建数据集
		String[] dataset = new String[100];
		for (int i = 0; i < dataset.length; i++){
			dataset[i] = "创建数据集如果想在ADT中关联源代码，可以在libs下新建文件右键项目close project,然后open project这样就可以关联源代码了item" + i;
		}
		// specify an adapter (see also next example)
		mAdapter = new MyAdapter(dataset);
		mRecyclerView.setAdapter(mAdapter);
	}

}