package com.hdsy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.ls300.Internal;
import com.hdsy.ls300.R;
import com.hdsy.ls300.R.id;
import com.hdsy.ls300.R.layout;

public class DataAdapter_GridView extends BaseAdapter {

	private static final String ScanDir = "扫描数据";
	private LayoutInflater inflater;

	public DataAdapter_GridView(Context context) {
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		try {
			return Internal.data_mgr.Count();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.view_grid_box, null); //picture info

			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(Internal.data_mgr.Get(position).name);
		viewHolder.image.setImageBitmap(Internal.GetBitmap(
				Internal.data_mgr.Get(position).image_path, true));
		return convertView;
	}

	class ViewHolder {
		public TextView title;
		public ImageView image;
	}

}
