package com.hdsy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hdsy.ui.MonitorView;
import com.hdsy.ui.RemoteView;
import com.hdsy.ui.ScanView;
import com.hdsy.ui.SettingView;

/**
 * ViewPager适配器
 */
public class TabPageAdapter extends PagerAdapter {
	public List<View> mListViews;

	private SettingView settingview;
	private RemoteView remoteview;
	private ScanView scanview;
	private MonitorView monitorview;
	private Activity app;

	public TabPageAdapter(Activity ctx) {
		app = ctx;
		mListViews = new ArrayList<View>();
		scanview = new ScanView(ctx);
		monitorview = new MonitorView(ctx);
		settingview = new SettingView(ctx);
		remoteview = new RemoteView(ctx);
		mListViews.add(scanview);
		mListViews.add(monitorview);
		mListViews.add(remoteview);
		mListViews.add(settingview);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(mListViews.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(mListViews.get(arg1), 0);
		return mListViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	public boolean onPrepareOptionsMenu(int page, Menu menu) {
		menu.clear();
		switch (page) {
		case 0:
			scanview.onPrepareOptionsMenu(app, menu);
			break;
		case 1:
			monitorview.onPrepareOptionsMenu(app, menu);
			break;
		case 2:
			remoteview.onPrepareOptionsMenu(app, menu);
			break;
		case 3:
			break;
		}

		return true;
	}

	public boolean onOptionsItemSelected(int page, MenuItem item) {
		switch (page) {
		case 0:
			scanview.onOptionsItemSelected(item);
			break;
		case 1:
			monitorview.onOptionsItemSelected(item);
			break;
		case 2:
			remoteview.onOptionsItemSelected(item);
			break;
		}
		return true;
	}

	int next(int idx) {
		return (idx + 1) % mListViews.size();
	}

	public void onViewShow(int idx) {
		try {
			VisableStateListener vsl = (VisableStateListener) mListViews
					.get(idx);
			vsl.onVisableChange(View.VISIBLE);
		} catch (Exception e) {

		}
		for (int idx1 = next(idx); idx1 != idx; idx1 = next(idx1)) {
			try {
				VisableStateListener vsl = (VisableStateListener) mListViews
						.get(idx1);
				vsl.onVisableChange(View.GONE);
			} catch (Exception e) {

			}
		}
	}

	public static interface VisableStateListener {
		public void onVisableChange(int vis);
	}
}
