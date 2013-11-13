package com.hdsy.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.hd.ls300.Internal;
import com.hdsy.adapter.DataAdapter_GridView;
import com.hdsy.ls300.ActivityDataView;
import com.hdsy.ls300.R;

public class DataVerticalGridView extends RelativeLayout {
	private GridView gridView;

	public DataVerticalGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DataVerticalGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DataVerticalGridView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_data_vertiacl_grid, this);

		inflat_Views();
		init_Views();
	}

	private void inflat_Views() {
		gridView = (GridView) findViewById(R.id.gridview);
		if (gridView != null) {
			DataAdapter_GridView adapter = new DataAdapter_GridView(getContext());
			gridView.setAdapter(adapter);
		}
	}

	private void init_Views() {
		if (gridView != null) {
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					Internal.data_mgr.Select(position);
					Intent i = new Intent();
					i.setClass(getContext(), ActivityDataView.class);
					getContext().startActivity(i);
				}
			});
		}
	}

}
