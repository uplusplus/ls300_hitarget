package com.hdsy.ls300;

import android.app.Activity;
import android.os.Bundle;

import com.hd.ls300.Internal;
import com.hdsy.ui.base.TouchImageView;

public class ActivityDataImageView extends Activity {
	protected TouchImageView pic;
	private static final String TAG = "ActivityDataImageView";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image);
		pic = (TouchImageView) findViewById(R.id.pic);
		pic.SetImage(Internal.data_mgr.GetSelect().image_path);
		setTitle(Internal.data_mgr.GetSelect().name);
	}
}
