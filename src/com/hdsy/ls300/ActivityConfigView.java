package com.hdsy.ls300;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.hd.ls300.Internal;

public class ActivityConfigView extends Activity implements ViewFactory,
		OnTouchListener, OnGestureListener {
	private static final String TAG = "ActivityDataView";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_introduct);
		// 解析控件
		imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		tv = (TextView) findViewById(R.id.detailView);

		// 为imageSwitcher设置Factory，用来为imageSwitcher制造ImageView
		imageSwitcher.setFactory(this);
		// 设置ImageSwitcher左右滑动事件
		imageSwitcher.setOnTouchListener(this);

		gestureScanner = new GestureDetector(this);
		gestureScanner
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					public boolean onDoubleTap(MotionEvent e) {
						// 双击时产生一次
						// 启动大图界面
						Intent mainIntent = new Intent(ActivityConfigView.this,
								ActivityDataImageView.class);
						ActivityConfigView.this.startActivity(mainIntent);
						return false;
					}

					public boolean onDoubleTapEvent(MotionEvent e) {
						// 双击时产生两次
						return false;
					}

					public boolean onSingleTapConfirmed(MotionEvent e) {
						// 短快的点击算一次单击
						return false;
					}
				});
	}

	protected ImageSwitcher imageSwitcher;
	protected TextView tv;
	private GestureDetector gestureScanner;
	// 左右滑动时手指按下的X坐标
	protected float touchDownX;
	// 左右滑动时手指松开的X坐标
	protected float touchUpX;

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(this);
		if (Internal.data_mgr.Count() > 0)
		{
			imageView.setImageBitmap(Internal.GetBitmap(Internal.data_mgr
					.GetSelect().image_path,true));
			tv.setText(Internal.data_mgr.GetSelect().point_cloud_path);
		}
		else
		{
			imageView.setImageResource(R.drawable.add);
			tv.setText("Nothing to show.");
		}
		return imageView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 取得左右滑动时手指按下的X坐标
			touchDownX = event.getX();
			gestureScanner.onTouchEvent(event);
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			// 取得左右滑动时手指松开的X坐标
			touchUpX = event.getX();
			// 从左往右，看前一张
			if (touchUpX - touchDownX > 100) {
				// 取得当前要看的图片的index
				Internal.data_mgr.SelectPriv();
				// 设置图片切换的动画
				imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
						android.R.anim.slide_in_left));
				imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
						this, android.R.anim.slide_out_right));
				// 设置当前要看的图片
				imageSwitcher.setImageDrawable(new BitmapDrawable(Internal
						.GetBitmap(Internal.data_mgr.GetSelect().image_path,true)));
				tv.setText(Internal.data_mgr.GetSelect().point_cloud_path);
				// 从右往左，看下一张
			} else if (touchDownX - touchUpX > 100) {
				// 取得当前要看的图片的index
				Internal.data_mgr.SelectNext();
				// 设置图片切换的动画
				// 由于Android没有提供slide_out_left和slide_in_right，所以仿照slide_in_left和slide_out_right编写了slide_out_left和slide_in_right
				imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_in_right));
				imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
						this, R.anim.slide_out_left));
				// 设置当前要看的图片
				imageSwitcher.setImageDrawable(new BitmapDrawable(Internal
						.GetBitmap(Internal.data_mgr.GetSelect().image_path,true)));
				tv.setText(Internal.data_mgr.GetSelect().point_cloud_path);
			}
			gestureScanner.onTouchEvent(event);
			return true;
		}
		return gestureScanner.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
