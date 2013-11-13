package com.hdsy.ls300;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hdsy.ui.NewDataView;

public class ActivityNewData extends Activity {
	NewDataView sv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_data);

		sv = (NewDataView) this.findViewById(R.id.newdataview);
		Button btn = (Button) this.findViewById(R.id.titleRightButton);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sv.SaveDataInfo();
				ActivityNewData.this.finish();
			}
		});
	}
}
