package com.hdsy.ls300;

import com.hdsy.ui.SettingView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivitySetting extends Activity {
	SettingView sv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		sv = (SettingView) this.findViewById(R.id.settingview);
		Button btn = (Button) this.findViewById(R.id.titleRightButton);
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sv.SaveSettingData();
			}

		});
	}
}
