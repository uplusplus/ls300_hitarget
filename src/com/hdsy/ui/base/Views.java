package com.hdsy.ui.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdsy.ls300.R;

/*
 * ��Ƕ����ͼ����
 */
public class Views {

	/**
	 * We will use a SpeechView to display each speech. It's just a LinearLayout
	 * with two text fields.
	 * 
	 */
	public static class DetailView extends LinearLayout {
		public DetailView(Context context, int img_rs_id, String title,
				String words) {
			super(context);

			this.setOrientation(VERTICAL);

			// Here we build the child views in code. They could also have
			// been specified in an XML file.
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View detailView = inflater.inflate(R.layout.view_list_view, null);

			mTitle = (TextView) detailView.findViewById(R.id.listDetailTitle);
			mTitle.setText(title);

			mImageView = (ImageView) detailView
					.findViewById(R.id.listDetailImage);
			mImageView.setImageResource(img_rs_id);

			mDetail = (TextView) detailView.findViewById(R.id.listDetailText);
			mDetail.setText(words);

			addView(detailView);
		}

		public DetailView(Context context, int img_rs_id, int title, int words) {
			super(context);

			this.setOrientation(VERTICAL);

			// Here we build the child views in code. They could also have
			// been specified in an XML file.
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View detailView = inflater.inflate(R.layout.view_list_view, null);

			mTitle = (TextView) detailView.findViewById(R.id.listDetailTitle);
			mTitle.setText(title);

			mImageView = (ImageView) detailView
					.findViewById(R.id.listDetailImage);
			mImageView.setImageResource(img_rs_id);

			mDetail = (TextView) detailView.findViewById(R.id.listDetailText);
			mDetail.setText(words);

			addView(detailView);
		}

		public DetailView(Context context, Bitmap image, String title,
				String words) {
			super(context);

			this.setOrientation(VERTICAL);
			// Here we build the child views in code. They could also have
			// been specified in an XML file.
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View detailView = inflater.inflate(R.layout.view_list_view, null);

			mTitle = (TextView) detailView.findViewById(R.id.listDetailTitle);
			mTitle.setText(title);

			mImageView = (ImageView) detailView
					.findViewById(R.id.listDetailImage);
			mImageView.setImageBitmap(image);

			mDetail = (TextView) detailView.findViewById(R.id.listDetailText);
			mDetail.setText(words);

			setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT,
					200));
			addView(detailView);
		}

		/**
		 * Convenience method to set the title of a SpeechView
		 */
		public void setTitle(String title) {
			mTitle.setText(title);
		}

		public void setTitle(int title) {
			mTitle.setText(title);
		}

		/**
		 * Convenience method to set the dialogue of a SpeechView
		 */
		public void setDetail(String words) {
			mDetail.setText(words);
		}

		public void setDetail(int words) {
			mDetail.setText(words);
		}

		public void setImage(int img_rs_id) {
			mImageView.setImageResource(img_rs_id);
		}

		public void setImage(Bitmap image) {
			mImageView.setImageBitmap(image);
		}

		private TextView mTitle;
		private TextView mDetail;
		private ImageView mImageView;
	}

	public static class GridBoxView extends FrameLayout {
		public GridBoxView(Context context, int img_rs_id, String title) {
			super(context);

			View gridBox;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridBox = inflater
					.inflate(R.layout.view_horizontal_grid_item, null);

			mTitle = (TextView) gridBox.findViewById(R.id.griditem_name);
			mTitle.setText(title);
			mImageView = (ImageView) gridBox.findViewById(R.id.griditem_image);
			mImageView.setImageResource(img_rs_id);

			addView(gridBox);
		}

		public GridBoxView(Context context, int img_rs_id, int title) {
			super(context);

			View gridBox;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridBox = inflater
					.inflate(R.layout.view_horizontal_grid_item, null);

			mTitle = (TextView) gridBox.findViewById(R.id.griditem_name);
			mTitle.setText(title);
			mImageView = (ImageView) gridBox.findViewById(R.id.griditem_image);
			mImageView.setImageResource(img_rs_id);

			addView(gridBox);
		}

		public GridBoxView(Context context, Bitmap image, String title) {
			super(context);

			View gridBox;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridBox = inflater
					.inflate(R.layout.view_horizontal_grid_item, null);

			mTitle = (TextView) gridBox.findViewById(R.id.griditem_name);
			mTitle.setText(title);
			mImageView = (ImageView) gridBox.findViewById(R.id.griditem_image);
			mImageView.setImageBitmap(image);

			setLayoutParams(new GridView.LayoutParams(100, 100));
			addView(gridBox);
		}

		/**
		 * Convenience method to set the title of a SpeechView
		 */
		public void setTitle(String title) {
			mTitle.setText(title);
		}

		public void setTitle(int title) {
			mTitle.setText(title);
		}

		public void setImage(int img_rs_id) {
			mImageView.setImageResource(img_rs_id);
		}

		public void setImage(Bitmap image) {
			mImageView.setImageBitmap(image);
		}

		private TextView mTitle;
		private ImageView mImageView;
	}
}
