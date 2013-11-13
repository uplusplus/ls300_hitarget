package com.hdsy.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.base.TextOutput;
import com.hd.egl.EGLSurfaceView;
import com.hd.internal.ScanServiceManager;
import com.hdsy.adapter.TabPageAdapter.VisableStateListener;
import com.hdsy.ls300.R;

public class MonitorView extends RelativeLayout implements TextOutput,
		VisableStateListener {
	private MachineStatusView msview;
	private StartScanView ssview;
	private EGLSurfaceView eglview;

	public MonitorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MonitorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MonitorView(Context context) {
		super(context);
		init();
	}

	private void init() {

		LayoutInflater.from(getContext()).inflate(
				R.layout.scan_tab_monitor_layout, this);

		msview = (MachineStatusView) findViewById(R.id.msv);
		ssview = (StartScanView) findViewById(R.id.ssv);
		eglview = (EGLSurfaceView)findViewById(R.id.egl_view);

		et = (TextView) findViewById(R.id.txt_view);
		if (et != null) {
			et.setBackgroundDrawable(null);
			et.setTextColor(Color.WHITE);
			et.setGravity(Gravity.TOP);
			et.setText("LS300 V2.0\n");
			et.setEnabled(false);
			ScanServiceManager.Output(this);
		}

	}

	private TextView et;

	public void print(String str) {
		if (et != null) {
			et.append(str);
			et.append("\n");
		}
	}

	public boolean onPrepareOptionsMenu(Activity ac, Menu menu) {
		MenuInflater inflater = ac.getMenuInflater();
		inflater.inflate(R.menu.scan_tab_monitor_menu_layout, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.connect:
			ScanServiceManager.Connect();
			break;
		case R.id.monitor:
			ScanServiceManager.requestMonitor(msview);
			break;
		case R.id.cloud:
			ScanServiceManager.ScanCloud();
			break;
		case R.id.photo:
			ScanServiceManager.ScanPhoto();
			break;
		case R.id.cancel:
			ScanServiceManager.Cancel();
			break;
		case R.id.back:
			break;
		}
		return true;
	}

	@Override
	public void onVisableChange(int vis) {
		if (vis == View.VISIBLE) {
			eglview.onResume();
		}else{
			eglview.onPause();
		}

		ssview.onVisableChange(vis);
	}
}