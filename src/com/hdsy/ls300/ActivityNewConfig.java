package com.hdsy.ls300;

import com.hdsy.ui.NewConfigView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityNewConfig extends Activity {
	NewConfigView sv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_config);
		sv = (NewConfigView) this.findViewById(R.id.configview);
		
		if(getIntent().getBooleanExtra("edit", false)){
			sv.Edit();
		}

		Button btn = (Button) this.findViewById(R.id.titleRightButton);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sv.SaveSettingData();
			}

		});

	}
	
}
