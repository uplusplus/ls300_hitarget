package com.hdsy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hd.ls300.DataManager.Data;
import com.hd.ls300.Internal;
import com.hdsy.ui.base.Views;

public class DataAdapter_ListView extends BaseAdapter {
	public DataAdapter_ListView(Context context) {
		mContext = context;
	}

	/**
	 * The number of items in the list is determined by the number of
	 * speeches in our array.
	 * 
	 * @see android.widget.ListAdapter#getCount()
	 */
	public int getCount() {
		return Internal.data_mgr.Count();
	}

	/**
	 * Since the data comes from an array, just returning the index is
	 * sufficent to get at the data. If we were using a more complex data
	 * structure, we would return whatever object represents one row in the
	 * list.
	 * 
	 * @see android.widget.ListAdapter#getItem(int)
	 */
	public Object getItem(int position) {
		return position;
	}

	/**
	 * Use the array index as a unique id.
	 * 
	 * @see android.widget.ListAdapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Make a SpeechView to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		Views.DetailView sv;
		Data data = Internal.data_mgr.Get(position);
		if (convertView == null) {
			//TODO:详细信息修改为meta data内容。
			sv = new Views.DetailView(mContext,
					Internal.GetBitmap(data.image_path,true), data.name,
					data.point_cloud_path);
		} else {
			sv = (Views.DetailView) convertView;
			sv.setTitle(data.name);
			sv.setImage(Internal.GetBitmap(data.image_path,true));
			sv.setDetail(data.point_cloud_path);
		}

		return sv;
	}

	/**
	 * Remember our context so we can use it when constructing views.
	 */
	private Context mContext;
}
