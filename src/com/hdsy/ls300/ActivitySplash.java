package com.hdsy.ls300;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.hd.ls300.Internal;

public class ActivitySplash extends Activity {

	//	private final int SPLASH_DISPLAY_LENGHT = 2000; // delay 6s
	private ProgressBar psb;
	private final Timer timer = new Timer();
	private TimerTask task;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		i = 0;
		psb = (ProgressBar) findViewById(R.id.psb);
		psb.setMax(100);
		psb.setProgress(i);
		psb.setIndeterminate(false);

		task = new TimerTask() {
			@Override
			public void run() {
				i += 1;
				psb.setSecondaryProgress(i);
				if (100 == i) {
					Intent mainIntent = new Intent(ActivitySplash.this,
							ActivityDashboard.class);
					timer.cancel();
					ActivitySplash.this.startActivity(mainIntent);
					ActivitySplash.this.finish();
				}
			}
		};

		timer.schedule(task, 20, 20);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Internal.Init(getApplicationContext());
			}
		}).start();
	}
}
