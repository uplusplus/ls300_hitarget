package com.hdsy.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hd.internal.ScanWork.WorkState;
import com.hdsy.ls300.R;
import com.hdsy.ui.base.TextProgressBar;

public class MachineStatusView extends LinearLayout implements
		com.hd.internal.ScanServiceManager.Monitor {
	Context app;
	private TextProgressBar tpb_temperature, tpb_battery, tpb_titl_x,
			tpb_titl_y, tpb_angle_h;
	private TextView tv_status;

	public MachineStatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MachineStatusView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		app = context;
		LayoutInflater.from(context)
				.inflate(R.layout.machine_status_view, this);
		inflat_Views();
		init_Views();
	}

	private void inflat_Views() {
		tpb_temperature = (TextProgressBar) findViewById(R.id.pb_temperature);
		tpb_battery = (TextProgressBar) findViewById(R.id.pb_battery);
		tpb_titl_x = (TextProgressBar) findViewById(R.id.pb_titl_x);
		tpb_titl_y = (TextProgressBar) findViewById(R.id.pb_titl_y);
		tpb_angle_h = (TextProgressBar) findViewById(R.id.pb_angle);
		tv_status = (TextView) findViewById(R.id.tv_status);
	}

	private void init_Views() {
		tpb_temperature.setValueRange(-10f, 100f, "°C");
		tpb_battery.setValueRange(0f, 20f, "V");
		tpb_titl_x.setValueRange(-90f, 90f, "°");
		tpb_titl_y.setValueRange(-90f, 90f, "°");
		tpb_angle_h.setValueRange(-180f, 180f, "°");
	}

	@Override
	public boolean on_state(WorkState s) {
		tv_status.setText(s.toString());
		return true;
	}

	@Override
	public boolean on_angle(double a) {
		tpb_angle_h.setProgress(a);
		return true;
	}

	@Override
	public boolean on_temperature(double t) {
		tpb_temperature.setProgress(t);
		return true;
	}

	@Override
	public boolean on_tilt(double[] angle) {
		tpb_titl_x.setProgress(angle[0]);
		tpb_titl_y.setProgress(angle[1]);
		return true;
	}

	@Override
	public boolean on_battery(double b) {
		tpb_battery.setProgress(b);
		return false;
	}
}
