package com.hdsy.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.hd.ls300.Internal;
import com.hdsy.adapter.TabPageAdapter.VisableStateListener;
import com.hdsy.ls300.R;

public class RemoteView extends RelativeLayout implements VisableStateListener {
	Context app;
	public WebView web_view;

	public RemoteView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public RemoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RemoteView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		app = context;
		LayoutInflater.from(context).inflate(R.layout.scan_tab_remote_layout,
				this);

		inflat_Views();
		init_Views();
	}

	private void inflat_Views() {
		web_view = (WebView) findViewById(R.id.remote_web_view);
	}

	private void init_Views() {
		web_view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url); // 在当前的webview中跳转到新的url

				return true;
			}
		});
		web_view.getSettings().setJavaScriptEnabled(true);
		web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

	}

	public boolean onPrepareOptionsMenu(Activity ac, Menu menu) {
		MenuInflater inflater = ac.getMenuInflater();
		inflater.inflate(R.menu.scan_tab_web_menu_layout, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.connect: {
			web_view.loadUrl("http://"
					+ Internal.setting.connections.web.address + ":"
					+ Internal.setting.connections.web.port);
			break;
		}
		case R.id.reload: {
			web_view.reload();
			break;
		}
		case R.id.test_html5: {
			web_view.loadUrl("http://html5test.com");
			break;
		}
		}
		return true;
	}

	@Override
	public void onVisableChange(int vis) {
		if (vis == View.VISIBLE) {

		}
	}

}
