package com.hdsy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hd.ls300.ConfigManager.ScanConfig;
import com.hd.ls300.Internal;
import com.hdsy.ui.base.Views;

public class ConfigAdapter_ListView extends BaseAdapter {
	public ConfigAdapter_ListView(Context context) {
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
		Views.DetailView sv;
		ScanConfig cfg = Internal.config_mgr.Get(position);
		if (convertView == null) {
			sv = new Views.DetailView(mContext, cfg.Draw(250, 250), cfg.name,
					cfg.toString());
		} else {
			sv = (Views.DetailView) convertView;
			sv.setTitle(cfg.name);
			sv.setImage(cfg.Draw(250, 250));
			sv.setDetail(cfg.toString());
		}

		return sv;
	}

	private Context mContext;
}
