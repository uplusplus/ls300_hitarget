package com.hdsy.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;

import com.hd.ls300.ConfigManager.ScanConfig;
import com.hd.ls300.Internal;
import com.hdsy.adapter.DataAdapter_GridView;
import com.hdsy.ls300.ActivityDataView;
import com.hdsy.ls300.ActivityMainTab;
import com.hdsy.ls300.ActivityNewData;
import com.hdsy.ls300.R;
import com.hdsy.ui.base.Views;
import com.hdsy.ui.base.Views.GridBoxView;

public class ConfigHorizontalGridView extends HorizontalScrollView {
	Context app;
	private GridView mGridView;
	private Adapter_GridView mAdapter;

	public ConfigHorizontalGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ConfigHorizontalGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ConfigHorizontalGridView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		app = context;
		LayoutInflater.from(context).inflate(R.layout.view_config_horizontal_list, this); //动态载入界面
		inflat_Views();
		init_Views();
	}

	private void inflat_Views() {
		mGridView = (GridView) findViewById(R.id.grid_view);

	}

	private void init_Views() {
		if (mGridView != null) {
			mAdapter = new Adapter_GridView(app);
			mGridView.setAdapter(mAdapter);
			mGridView.setNumColumns(mAdapter.getCount());

			mGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Internal.config_mgr.Select(position);
					Intent i = new Intent();
					i.setClass(app, ActivityNewData.class);
					app.startActivity(i);
				}
			});
		}
	}

	private static class Adapter_GridView extends BaseAdapter {
		public Adapter_GridView(Context context) {
			mContext = context;
		}

		public int getCount() {
			return Internal.config_mgr.Count();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			GridBoxView sv;
			ScanConfig cfg = Internal.config_mgr.Get(position);
			if (convertView == null) {
				sv = new Views.GridBoxView(mContext, cfg.Draw(100, 100),
						cfg.name);
			} else {
				sv = (Views.GridBoxView) convertView;
				sv.setTitle(cfg.name);
				sv.setImage(cfg.Draw(100, 100));
			}

			return sv;
		}

		private Context mContext;
	}
}
