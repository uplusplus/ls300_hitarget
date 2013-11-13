package com.hdsy.ui;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hd.base.Utils;
import com.hd.ls300.DataManager.Data;
import com.hd.ls300.DataManager.MetaData;
import com.hd.ls300.Internal;
import com.hdsy.ls300.R;
import com.hdsy.ls300.R.drawable;
import com.hdsy.ls300.R.id;
import com.hdsy.ls300.R.layout;

public class NewDataView extends ExpandableListView {
	private static final String TAG = "NewDataView";
	String[] groups = new String[] { "Scan Config", "Meta Data" };
	int[] tags = new int[] { 0, 0 };
	View[] group_views = new View[2];
	Context app;

	public NewDataView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public NewDataView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NewDataView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		app = context;
		MyListAdapter adapter = new MyListAdapter(context);
		setAdapter(adapter);
		setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int arg0) {
				tags[arg0] = 1;
			}
		});
		setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int arg0) {
				tags[arg0] = 0;
			}
		});

		for(int i=0;i<adapter.getGroupCount();i++){
			expandGroup(i);
		}
		
	}

	//控件与参量对应
	//0
	Spinner sp_config;
	int idx_config;
	
	TextView tv_config;
	//1
	EditText et_name, et_stuff, et_date, et_locate;
	String data_name, data_stuff, data_date, data_locate;

	private void initChild(int idx, View v) {
		if (v == null) {
			Log.e(TAG, "initChild v==null.");
			return;
		}
		switch (idx) {
			case 0:
				sp_config = (Spinner) v.findViewById(R.id.sp_data_config);
				tv_config = (TextView)v.findViewById(R.id.tv_data_config);
				break;
			case 1:
				et_name = (EditText) v.findViewById(R.id.et_data_name);
				et_stuff = (EditText) v.findViewById(R.id.et_data_stuff);
				et_date = (EditText) v.findViewById(R.id.et_data_date);
				et_locate = (EditText) v.findViewById(R.id.et_data_locate);
				break;
		}

		loadValue(idx);
	}

	private void loadValue(int idx) {
		switch (idx) {
			case 0:
				//将可选内容与ArrayAdapter连接起来  
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(app,
						android.R.layout.simple_spinner_item,
						Internal.config_mgr.toStringArray());

				//设置下拉列表的风格  
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				//将adapter 添加到spinner中  
				sp_config.setAdapter(adapter);
				sp_config.setOnItemSelectedListener(new OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Internal.config_mgr.Select(position);
						tv_config.setText(Internal.config_mgr.GetSelect().toString());
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						tv_config.setText("Select a config.");
					}
					
				});

				//设置默认值  
				sp_config.setVisibility(View.VISIBLE);
				sp_config.setSelection(Internal.config_mgr.Select());
				break;
			case 1:
				et_name.setText(Utils.getDate());
				et_stuff.setText("Stuff1");
				et_date.setText(new Date().toLocaleString());
				et_locate.setText("Wuhan");
				break;
		}
	}

	private boolean retriveValue(int idx) {
		if (group_views[idx] == null)
			return false;
		switch (idx) {
			case 0:
				idx_config = sp_config.getSelectedItemPosition();
				break;
			case 1:
				data_name = et_name.getText().toString();
				data_stuff = et_stuff.getText().toString();
				data_date = et_date.getText().toString();
				data_locate = et_locate.getText().toString();
				break;
			default:
				return false;
		}
		return true;
	}

	public void SaveDataInfo() {
		if (retriveValue(0) && retriveValue(1)) {
			Internal.config_mgr.Select(idx_config);
			Data data = Data.Create(data_name, Internal.data_dir,
					Internal.image_dir, Internal.config_mgr.Get(idx_config),
					new MetaData(data_date, data_stuff, data_locate));
			Internal.data_mgr.Put(data);
			Internal.data_mgr.Select(data);
		}
	}

	class MyListAdapter extends BaseExpandableListAdapter {
		Context app;

		public MyListAdapter(Context ctx) {
			app = ctx;
		}

		@Override
		public View getChild(int arg0, int arg1) {
			View arg3 = null;
			switch (arg0) {
				case 0:
					arg3 = LayoutInflater.from(app).inflate(
							R.layout.list_child_item_data_config, null);
					break;
				case 1:
					arg3 = LayoutInflater.from(app).inflate(
							R.layout.list_child_item_data_meta, null);
					break;
			}

			if (arg0 == groups.length - 1) {
				arg3.setBackgroundResource(R.drawable.more_itembottom_press);
			} else {
				arg3.setBackgroundResource(R.drawable.more_itemmiddle_press);

			}

			initChild(arg0, arg3);

			return arg3;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			return arg0;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			if (group_views[arg0] == null) {
				group_views[arg0] = getChild(arg0, arg1);
			}
			return group_views[arg0];
		}

		@Override
		public int getChildrenCount(int arg0) {
			return 1;
		}

		@Override
		public Object getGroup(int arg0) {
			return groups[arg0];
		}

		@Override
		public int getGroupCount() {
			return groups.length;
		}

		@Override
		public long getGroupId(int arg0) {
			return arg0;
		}

		class GroupHolder {
			ImageView img;
			TextView title;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {
			GroupHolder groupHolder;
			if (arg2 == null) {
				arg2 = LayoutInflater.from(app).inflate(
						R.layout.list_group_item, null);
				groupHolder = new GroupHolder();
				groupHolder.img = (ImageView) arg2.findViewById(R.id.tag_img);
				groupHolder.title = (TextView) arg2
						.findViewById(R.id.title_view);
				arg2.setTag(groupHolder);
			} else {
				groupHolder = (GroupHolder) arg2.getTag();
			}
			if (tags[arg0] == 0) {
				groupHolder.img.setImageResource(R.drawable.ic_arrow);
			} else {
				groupHolder.img.setImageResource(R.drawable.ic_arrow_down);
			}
			groupHolder.title.setText(groups[arg0]);

			if (arg0 == 0) {
				arg2.setBackgroundResource(R.drawable.more_itemtop_press);
			} else if (arg0 == groups.length - 1 && !arg1) {
				arg2.setBackgroundResource(R.drawable.more_itembottom_press);
			} else {
				arg2.setBackgroundResource(R.drawable.more_itemmiddle_press);
			}

			return arg2;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}

	}
}
