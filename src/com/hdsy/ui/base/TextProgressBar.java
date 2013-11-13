package com.hdsy.ui.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {
	String text;
	Paint mPaint;

	public TextProgressBar(Context context) {
		super(context);
		initText();
	}

	public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initText();
	}

	public TextProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initText();
	}

	double range, min;
	String unit;

	public void setValueRange(double min, double max, String unit) {
		range = max - min;
		this.min = min;
		this.unit = unit;
		this.setMax((int) range);
	}

	public synchronized void setProgress(double progress) {
		double p = progress - min;
		setText(p);
		super.setProgress((int) p);
	}

	@Override
	public synchronized void setProgress(int progress) {
		int p = (int) (progress - min);
		setText(p);
		super.setProgress(p);
	}

	Rect rect = new Rect();

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) + 5;//- rect.centerY();
		canvas.drawText(this.text, x, y, this.mPaint);
	}

	//初始化，画笔
	private void initText() {
		this.mPaint = new Paint();
		this.mPaint.setColor(Color.WHITE);
		this.text = "N/A";
	}

	//设置文字内容
	private void setText(double progress) {
		this.text = String.format("%.1f", progress) + unit;
	}

	//设置文字内容
	private void setText(int progress) {
		this.text = String.format("%d", progress) +  unit;
	}
}
