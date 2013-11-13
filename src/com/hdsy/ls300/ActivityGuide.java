package com.hdsy.ls300;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityGuide extends Activity implements OnClickListener {
	HashMap<Integer, Drawable> map = new HashMap();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		findViewById(R.id.guid_config).setOnClickListener(this);
		findViewById(R.id.guid_manage).setOnClickListener(this);
		findViewById(R.id.guid_photo).setOnClickListener(this);
		findViewById(R.id.guid_scan).setOnClickListener(this);
		findViewById(R.id.guide_setup).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.guid_config: {
				Intent i = new Intent(getApplicationContext(),
						ActivityConfigList.class);
				startActivity(i);
				break;
			}
			case R.id.guid_manage: {
				Intent i = new Intent(getApplicationContext(),
						ActivityDataList.class);
				startActivity(i);
				break;
			}
			case R.id.guid_photo: {
				Intent i = new Intent(getApplicationContext(),
						ActivityConfigList.class);
				startActivity(i);
				break;
			}
			case R.id.guid_scan: {
				Intent i = new Intent(getApplicationContext(),
						ActivityConfigList.class);
				startActivity(i);
				break;
			}
			case R.id.guide_setup: {
				Intent i = new Intent(getApplicationContext(),
						ActivitySetting.class);
				startActivity(i);
				break;
			}
			default:
				return;
		}

		if (map.get(id) == null) {
			map.put(id, v.getBackground());
			v.setBackgroundResource(R.drawable.android_big_button_focus);
		} else {
			v.setBackgroundDrawable(map.remove(id));
		}
	}
}
