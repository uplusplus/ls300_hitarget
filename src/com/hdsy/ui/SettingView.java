package com.hdsy.ui;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hd.internal.WebServer;
import com.hd.ls300.Internal;
import com.hdsy.adapter.TabPageAdapter.VisableStateListener;
import com.hdsy.ls300.R;

public class SettingView extends LinearLayout implements VisableStateListener {
	private static final String TAG = "SettingView";
	String[] groups = new String[] { "Connect", "Scan", "Service", "Remote",
			"About" };
	int[] tags = new int[] { 0, 0, 0, 0, 0 };
	View[] setting_view = new View[5];
	ExpandableListView elv;

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SettingView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.scan_tab_setting_layout, this);

		elv = (ExpandableListView) findViewById(R.id.exlistview);

		MyListAdapter adapter = new MyListAdapter(getContext());
		elv.setAdapter(adapter);
		elv.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int arg0) {
				tags[arg0] = 1;
			}
		});
		elv.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int arg0) {
				tags[arg0] = 0;
			}
		});

		Button btn = (Button) this.findViewById(R.id.titleRightButton);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SaveSettingData();
			}

		});
	}

	// 控件与参量对应
	// 0
	EditText et_ip, et_port, et_device, et_baudrate, et_remote_ip,
			et_remote_port;

	String set_ip, set_port, set_device, set_baudrate, set_remote_ip,
			set_remote_port;
	// 1
	EditText et_root_path;
	String set_root_path;
	// 2
	ToggleButton tbWeb;
	Boolean set_web;
	TextView tv_web_ip;

	// 3

	private void initChild(int idx, View v) {
		if (v == null) {
			Log.e(TAG, "initChild v==null.");
			return;
		}
		switch (idx) {
		case 0:
			et_ip = (EditText) v.findViewById(R.id.et_set_ip);
			et_port = (EditText) v.findViewById(R.id.et_set_port);
			et_device = (EditText) v.findViewById(R.id.et_set_name);
			et_baudrate = (EditText) v.findViewById(R.id.et_set_stuff);
			break;
		case 1:
			et_root_path = (EditText) v.findViewById(R.id.et_set_root_dir);
			break;
		case 2:
			tbWeb = (ToggleButton) v.findViewById(R.id.tb_set_web);
			tv_web_ip = (TextView) v.findViewById(R.id.tv_web_ip);
			break;
		case 3:
			et_remote_ip = (EditText) v.findViewById(R.id.set_et_remote_ip);
			et_remote_port = (EditText) v.findViewById(R.id.set_et_remote_port);
			break;
		}

		loadValue(idx);
	}

	private void loadValue(int idx) {
		switch (idx) {
		case 0:
			et_ip.setText(Internal.setting.connections.sick.address);
			et_port.setText("" + Internal.setting.connections.sick.port);
			et_device.setText(Internal.setting.connections.control.dev);
			et_baudrate.setText(""
					+ Internal.setting.connections.control.bandrate);
			break;
		case 1:
			et_root_path.setText(Internal.setting.paths.data_dir);
			break;
		case 2:
			tbWeb.setChecked(Internal.setting.services.webservice);
			tv_web_ip.setText(getIpAddress());
			break;
		case 3:
			et_remote_ip.setText(Internal.setting.connections.web.address);
			et_remote_port.setText("" + Internal.setting.connections.web.port);
			break;
		}
	}

	private boolean retriveValue(int idx) {
		if (setting_view[idx] == null)
			return false;
		switch (idx) {
		case 0:
			set_ip = et_ip.getText().toString();
			set_port = et_port.getText().toString();
			set_device = et_device.getText().toString();
			set_baudrate = et_baudrate.getText().toString();
			break;
		case 1:
			set_root_path = et_root_path.getText().toString();
			break;
		case 2:
			set_web = tbWeb.isChecked();
			break;
		case 3:
			set_remote_ip = et_remote_ip.getText().toString();
			set_remote_port = et_remote_port.getText().toString();
			break;
		default:
			return false;
		}
		return true;
	}

	public void SaveSettingData() {
		if (retriveValue(0)) {
			Internal.setting.connections.sick.address = set_ip;
			Internal.setting.connections.sick.port = Integer.parseInt(set_port);
			Internal.setting.connections.control.dev = set_device;
			Internal.setting.connections.control.bandrate = Integer
					.parseInt(set_baudrate);
		}
		if (retriveValue(1)) {
			Internal.setting.paths.data_dir = set_root_path;
		}
		if (retriveValue(2)) {
			if (set_web) {
				WebServer.StartWebServer(getContext());
			} else {
				WebServer.StopWebServer();
			}
		}
		if (retriveValue(3)) {
			Internal.setting.connections.web.address = set_remote_ip;
			Internal.setting.connections.web.port = Integer
					.parseInt(set_remote_port);
		}

	}

	public String getIpAddress() {
		try {
			String ipv4;
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						if (!inetAddress.isLoopbackAddress()
								&& InetAddressUtils
										.isIPv4Address(ipv4 = inetAddress
												.getHostAddress()))
							return ipv4;
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("SettingView", ex.toString());
		}
		return "";
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
						R.layout.list_child_item_connect, null);
				break;
			case 1:
				arg3 = LayoutInflater.from(app).inflate(
						R.layout.list_child_item_scan, null);
				break;
			case 2:
				arg3 = LayoutInflater.from(app).inflate(
						R.layout.list_child_item_service, null);
				break;
			case 3:
				arg3 = LayoutInflater.from(app).inflate(
						R.layout.list_child_item_remote, null);
				break;
			case 4:
				arg3 = LayoutInflater.from(app).inflate(
						R.layout.list_child_item_about, null);
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

	public boolean onPrepareOptionsMenu(Menu menu) {
		return false;

	}

	@Override
	public void onVisableChange(int vis) {
		if (vis == View.VISIBLE) {

		}
	}
}
