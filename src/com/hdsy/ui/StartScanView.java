package com.hdsy.ui;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hd.internal.ScanServiceManager;
import com.hd.ls300.DataManager.Data;
import com.hd.ls300.Internal;
import com.hdsy.adapter.TabPageAdapter.VisableStateListener;
import com.hdsy.ls300.R;

public class StartScanView extends RelativeLayout implements
		VisableStateListener {
	Context app;
	private Animation buttonAnimation = null;

	public StartScanView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public StartScanView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public StartScanView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		app = context;
		LayoutInflater.from(getContext()).inflate(R.layout.view_start_scan,
				this);

		init_Views();
	}

	private void init_Views() {

		final Button b = (Button) findViewById(R.id.btn_start_scan);
		buttonAnimation = AnimationUtils.loadAnimation(app, R.anim.animation);
		buttonAnimation.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				b.setEnabled(true);
				StartScanView.this.setVisibility(View.INVISIBLE);
				//ScanServiceManager.Connect();
				//ScanServiceManager.ScanCloud();
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {

			}
		});
		b.startAnimation(buttonAnimation);

		b.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				b.clearAnimation();
			}
		});
	}

	@Override
	public void onVisableChange(int vis) {
		if (vis == View.VISIBLE) {
			final TextView tv = (TextView) findViewById(R.id.scan_detail_info);
			final Button b = (Button) findViewById(R.id.btn_start_scan);
			Data da = Internal.data_mgr.GetSelect();
			if (da != null) {
				b.setEnabled(true);
				Spanned html = Html.fromHtml("<table color=\"White\">"
						+ "<p>Name: "
						+ da.name
						+ "</p>"
						+ "<p>Area : "
						+ da.config.area.toString()
						+ "</p>"
						+ "<p>Precise :"
						+ da.config.preci.toString()
						+ "</p>"
						+ "<p>Meta :"
						+ da.meta.toString()
								.replace("\n", "</p><p>")
						+ "</p></table>");
				tv.setText(html);
			} else {
				b.setEnabled(false);
				Spanned html = Html
						.fromHtml("<b><font color='red'>Not Config yet,Config first.</font></b>");
				tv.setText(html);
				b.setVisibility(View.GONE);
			}
		}
	}
}
