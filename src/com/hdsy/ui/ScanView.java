package com.hdsy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.hdsy.adapter.TabPageAdapter.VisableStateListener;
import com.hdsy.ls300.ActivityNewConfig;
import com.hdsy.ls300.ActivityNewData;
import com.hdsy.ls300.R;


public class ScanView extends RelativeLayout implements VisableStateListener {
	Context app;
	public ConfigHorizontalGridView config_view;
	public DataVerticalGridView data_view;

	public ScanView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ScanView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ScanView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		app = context;
		LayoutInflater.from(context).inflate(R.layout.scan_tab_scan_layout, this);

		inflat_Views();
		init_Views();
	}

	private void inflat_Views() {
		config_view = (ConfigHorizontalGridView) findViewById(R.id.config_view);
		data_view = (DataVerticalGridView) findViewById(R.id.data_view);
	}

	private void init_Views() {
	}

	public boolean onPrepareOptionsMenu(Activity ac, Menu menu) {
		MenuInflater inflater = ac.getMenuInflater();
		inflater.inflate(R.menu.scan_tab_scan_menu_layout, menu); //这里面的内容在按下硬件menu键后会显示出来
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.newconfig: {
				// 到创建界面
				Intent mainIntent = new Intent(app, ActivityNewConfig.class);
				app.startActivity(mainIntent);
				break;
			}

			case R.id.newdata: {
				Intent mainIntent = new Intent(app, ActivityNewData.class);
				app.startActivity(mainIntent);
				break;
			}
		}
		return true;
	}

	@Override
	public void onVisableChange(int vis) {
		if(vis == View.VISIBLE){
			
		}
	}

}
