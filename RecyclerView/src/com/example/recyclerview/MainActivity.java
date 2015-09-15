package com.example.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;

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
		mRecyclerView.setLayoutManager(mLayoutManager);
		// �������ݼ�
		String[] dataset = new String[100];
		for (int i = 0; i < dataset.length; i++){
			dataset[i] = "item" + i;
		}
		// specify an adapter (see also next example)
		mAdapter = new MyAdapter(dataset);
		mRecyclerView.setAdapter(mAdapter);
	}

}