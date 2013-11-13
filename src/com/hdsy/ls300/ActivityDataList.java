package com.hdsy.ls300;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hd.ls300.Internal;
import com.hdsy.adapter.DataAdapter_ListView;

public class ActivityDataList extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_list);
		setUpContent();
	}

	private static final String TAG = "ActivityDataList";

	private void setUpContent() {
		DataAdapter_ListView mAdapter = new DataAdapter_ListView(this);
		setListAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Internal.data_mgr.Select(position);
				// 到数据视图界面
				Intent mainIntent = new Intent(ActivityDataList.this,
						ActivityDataView.class);
				ActivityDataList.this.startActivity(mainIntent);
			}
		});

		findViewById(R.id.titleRightButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 到创建数据界面
						Intent mainIntent = new Intent(ActivityDataList.this,
								ActivityNewData.class);
						ActivityDataList.this.startActivity(mainIntent);
					}

				});
	}

}
