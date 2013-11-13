package com.hdsy.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hd.base.Utils;
import com.hd.ls300.ConfigManager.Precisions;
import com.hd.ls300.ConfigManager.Precisions.precision;
import com.hd.ls300.ConfigManager.ScanConfig;
import com.hd.ls300.Internal;
import com.hdsy.ls300.ActivityDataList;
import com.hdsy.ls300.R;

public class NewConfigView extends ExpandableListView {
	private static final String TAG = "ConfigView";
	String[] groups = new String[] { "Info", "Precise", "Area" };
	int[] tags = new int[] { 0, 0, 0 };
	View[] setting_view = new View[3];
	Context app;

	public NewConfigView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public NewConfigView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NewConfigView(Context context) {
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
	}

	//控件与参量对应
	//0
	EditText et_name;

	String cf_name;
	//1
	Spinner sp_precise;
	EditText et_plus_delay, et_frequency, et_resolution;

	int plus_delay;
	float frequency, resolution;

	Button btn_plusdelay, btn_frequency, btn_resolution;
	//3
	ImageButton btn_select;
	EditText et_left, et_right, et_top, et_bottom;
	float area_left, area_right, area_top, area_bottom;

	boolean edit = false;

	public void Edit() {
		ScanConfig cfg = Internal.config_mgr.GetSelect();
		cf_name = cfg.name;
		plus_delay = cfg.preci.plus_delay;
		frequency = cfg.preci.resolution;
		resolution = cfg.preci.resolution;

		area_left = cfg.area.left;
		area_right = cfg.area.right;
		area_top = cfg.area.top;
		area_bottom = cfg.area.bottom;
		edit = true;
	}

	private void initChild(int idx, View v) {
		if (v == null) {
			Log.e(TAG, "initChild v==null.");
			return;
		}
		switch (idx) {
			case 0:
				et_name = (EditText) v.findViewById(R.id.et_set_config_name);
				break;
			case 1:
				sp_precise = (Spinner) v.findViewById(R.id.sp_set_precise);
				et_plus_delay = (EditText) v
						.findViewById(R.id.et_set_precise_plusdelay);
				et_frequency = (EditText) v
						.findViewById(R.id.et_set_precise_frequency);
				et_resolution = (EditText) v
						.findViewById(R.id.et_set_precise_resolution);

				btn_plusdelay = (Button) v
						.findViewById(R.id.set_btn_plus_delay);
				btn_frequency = (Button) v.findViewById(R.id.set_btn_frequency);
				btn_resolution = (Button) v
						.findViewById(R.id.set_btn_resolution);

				//将可选内容与ArrayAdapter连接起来  
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(app,
						android.R.layout.simple_spinner_item,
						Precisions.aviablePrecises);

				//设置下拉列表的风格  
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				//将adapter 添加到spinner中  
				sp_precise.setAdapter(adapter);
				sp_precise
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								precision p = Precisions.get(position);
								et_plus_delay.setText("" + p.plus_delay);
								et_frequency.setText("" + p.frequency);
								et_resolution.setText("" + p.resolution);
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
							}

						});
				//水平速度
				btn_plusdelay.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								app, android.R.layout.select_dialog_item,
								Precisions.aviablePlusDelay);

						new AlertDialog.Builder(app).setTitle(R.string.select)
								.setSingleChoiceItems(adapter, 0,//数据列表、第几个为选中
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												et_plus_delay
														.setText(Precisions.aviablePlusDelay[which]);
												dialog.dismiss();
											}
										}).show();
					}
				});

				//垂直精确度
				btn_resolution.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								app, android.R.layout.select_dialog_item,
								Precisions.aviableResolution);
						new AlertDialog.Builder(app).setTitle(R.string.select)
								.setSingleChoiceItems(adapter, 0,//数据列表、第几个为选中
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												et_resolution
														.setText(Precisions.aviableResolution[which]);
												dialog.dismiss();
											}
										}).show();
					}
				});

				//垂直速度
				btn_frequency.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								app, android.R.layout.select_dialog_item,
								Precisions.aviableFrequency);
						new AlertDialog.Builder(app).setTitle(R.string.select)
								.setSingleChoiceItems(adapter, 0,//数据列表、第几个为选中
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												et_frequency
														.setText(Precisions.aviableFrequency[which]);
												dialog.dismiss();
											}
										}).show();
					}
				});

				break;
			case 2:
				btn_select = (ImageButton) v.findViewById(R.id.btn_select_area);
				et_left = (EditText) v.findViewById(R.id.et_set_area_left);
				et_right = (EditText) v.findViewById(R.id.et_set_area_right);
				et_bottom = (EditText) v.findViewById(R.id.et_set_area_bottom);
				et_top = (EditText) v.findViewById(R.id.et_set_area_top);

				btn_select.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// Launching data Screen
						Intent i = new Intent(app, ActivityDataList.class);
						app.startActivity(i);
					}

				});
				break;
		}

		if (edit)
			loadValue(idx);
		else
			defaultValue(idx);

	}

	private void loadValue(int idx) {
		switch (idx) {
			case 0://info
				et_name.setText(cf_name);
				break;
			case 1://precise
				et_plus_delay.setText("" + plus_delay);
				et_frequency.setText("" + frequency);
				et_resolution.setText("" + resolution);

				break;
			case 2://area
				et_left.setText("" + area_left);
				et_right.setText("" + area_right);
				et_bottom.setText("" + area_bottom);
				et_top.setText("" + area_right);
				break;
		}
	}

	private void defaultValue(int idx) {
		switch (idx) {
			case 0://info
				et_name.setText(Utils.getDate());
				break;
			case 1://precise
				sp_precise.setSelection(0);
				break;
			case 2://area
				et_left.setText("0");
				et_right.setText("360");
				et_bottom.setText("-45");
				et_top.setText("90");
				break;
		}
	}

	private boolean retriveValue(int idx) {
		if (setting_view[idx] == null)
			return false;
		switch (idx) {
			case 0:
				cf_name = et_name.getText().toString();
				break;
			case 1:
				plus_delay = Integer.parseInt(et_plus_delay.getText()
						.toString());
				frequency = Float.parseFloat(et_frequency.getText().toString());
				resolution = Float.parseFloat(et_resolution.getText()
						.toString());
				break;
			case 2:
				area_left = Float.parseFloat(et_left.getText().toString());
				area_right = Float.parseFloat(et_right.getText().toString());
				area_bottom = Float.parseFloat(et_bottom.getText().toString());
				area_top = Float.parseFloat(et_top.getText().toString());
				break;
			default:
				return false;
		}
		return true;
	}

	//保存配置
	public void SaveSettingData() {
		if (retriveValue(0) && retriveValue(1) && retriveValue(2)) {
			ScanConfig sc = new ScanConfig(cf_name, area_bottom, area_top,
					area_left, area_right, plus_delay, frequency, resolution);
			Internal.config_mgr.Put(sc);
			Internal.config_mgr.Select(sc);
			Internal.config_mgr.Save(null);
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
							R.layout.list_child_item_config_info, null);
					break;
				case 1:
					arg3 = LayoutInflater.from(app).inflate(
							R.layout.list_child_item_config_precise, null);
					break;
				case 2:
					arg3 = LayoutInflater.from(app).inflate(
							R.layout.list_child_item_config_area, null);
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
			if (setting_view[arg0] == null) {
				setting_view[arg0] = getChild(arg0, arg1);
			}
			return setting_view[arg0];
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
