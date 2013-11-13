package com.hdsy.ls300;

import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hd.ls300.Internal;
import com.hdsy.adapter.ConfigAdapter_ListView;

public class ActivityConfigList extends ListActivity {
	private static final String TAG = "ActivityDataList";
	ConfigAdapter_ListView mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_list);
		setUpContent();
	}

	private void setUpContent() {
		prepareQuickActionBar();
		mAdapter = new ConfigAdapter_ListView(this);
		setListAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Internal.config_mgr.Select(position);
				mBar.show(view);
			}
		});

		findViewById(R.id.titleRightButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 到创建界面
						Intent mainIntent = new Intent(ActivityConfigList.this,
								ActivityNewConfig.class);
						ActivityConfigList.this.startActivity(mainIntent);
					}

				});

	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}

	private QuickActionWidget mBar;

	private void prepareQuickActionBar() {
		mBar = new QuickActionBar(this);
		mBar.addQuickAction(new MyQuickAction(this,
				R.drawable.gd_action_bar_trashcan, R.string.gd_trashcan));
		mBar.addQuickAction(new MyQuickAction(this,
				R.drawable.gd_action_bar_edit, R.string.gd_edit));

		mBar.setOnQuickActionClickListener(mActionListener);
	}

	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			switch (position) {
				case 0:
					Internal.config_mgr.Remove(Internal.config_mgr.Select());
					mAdapter.notifyDataSetChanged();
					break;
				case 1:
					// 到编辑界面
					Intent mainIntent = new Intent(ActivityConfigList.this,
							ActivityNewConfig.class);
					mainIntent.putExtra("edit", true);
					ActivityConfigList.this.startActivity(mainIntent);
					break;
			}
		}
	};

	private static class MyQuickAction extends QuickAction {

		private static final ColorFilter BLACK_CF = new LightingColorFilter(
				Color.BLACK, Color.BLACK);

		public MyQuickAction(Context ctx, int drawableId, int titleId) {
			super(ctx, buildDrawable(ctx, drawableId), titleId);
		}

		private static Drawable buildDrawable(Context ctx, int drawableId) {
			Drawable d = ctx.getResources().getDrawable(drawableId);
			d.setColorFilter(BLACK_CF);
			return d;
		}

	}
}
