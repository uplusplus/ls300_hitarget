package com.hdsy.ls300;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityDashboard extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		/**
		 * Creating all buttons instances
		 * */
		// Dashboard scan button
		Button btn_scan = (Button) findViewById(R.id.btn_scan);

		// Dashboard guide button
		Button btn_guide = (Button) findViewById(R.id.btn_guide);

		// Dashboard data button
		Button btn_data = (Button) findViewById(R.id.btn_data);

		// Dashboard config button
		Button btn_config = (Button) findViewById(R.id.btn_config);

		// Dashboard setting button
		Button btn_setting = (Button) findViewById(R.id.btn_setting);

		// Dashboard leave button
		Button btn_leave = (Button) findViewById(R.id.btn_leave);

		/**
		 * Handling all button click events
		 * */

		// Listening to scan button click
		btn_scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching scan Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityMainTab.class);
				startActivity(i);
			}
		});

		// Listening guide button click
		btn_guide.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching guide Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityGuide.class);
				startActivity(i);
			}
		});

		// Listening data button click
		btn_data.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching data Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityDataList.class);
				startActivity(i);
			}
		});

		// Listening to config button click
		btn_config.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching config Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityConfigList.class);
				startActivity(i);
			}
		});

		// Listening to setting button click
		btn_setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching setting Screen
				Intent i = new Intent(getApplicationContext(),
						ActivitySetting.class);
				startActivity(i);
			}
		});

		// Listening to leave button click
		btn_leave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching leave Screen

				new AlertDialog.Builder(ActivityDashboard.this)
						.setTitle("确认退出吗？")
						.setPositiveButton(R.string.ok, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ActivityDashboard.this.finish();
							}

						}).create().show();

			}
		});
	}
}
