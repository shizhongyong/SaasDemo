package cn.yunniao.saas.demo.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class ImageLoader extends AppGlideModule {

	public static void load(Context context, String url, ImageView view) {
		load(context, url, view, null);
	}

	public static void load(Context context, String url, ImageView view, Option option) {
		if (context == null) {
			return;
		}
		if (TextUtils.isEmpty(url)) {
			if (option != null && option.getPlaceholder() > 0) {
				view.setImageResource(option.getPlaceholder());
			}
			return;
		}

		GlideRequest<Drawable> request = GlideApp.with(context).load(url);
		if (option != null) {
			if (option.getPlaceholder() > 0) {
				request = request.placeholder(option.getPlaceholder());
			}
			if (option.getError() > 0) {
				request = request.error(option.getError());
			}
			if (option.isCenterCrop()) {
				request = request.centerCrop();
			}
			if (option.isCircleCrop()) {
				request = request.circleCrop();
			}
		}
		request.into(view);
	}

	public static void clear(Context context, ImageView view) {
		if (context == null || view == null) {
			return;
		}
		GlideApp.with(context).clear(view);
	}

	public static final class Option {

		private int placeholder = -1;
		private int error = -1;
		private boolean centerCrop = false;
		private boolean circleCrop = false;

		private Option() {

		}

		public int getPlaceholder() {
			return placeholder;
		}

		public void setPlaceholder(int placeholder) {
			this.placeholder = placeholder;
		}

		public int getError() {
			return error;
		}

		public void setError(int error) {
			this.error = error;
		}

		public boolean isCenterCrop() {
			return centerCrop;
		}

		public void setCenterCrop(boolean centerCrop) {
			this.centerCrop = centerCrop;
		}

		public boolean isCircleCrop() {
			return circleCrop;
		}

		public void setCircleCrop(boolean circleCrop) {
			this.circleCrop = circleCrop;
		}

		public static Option create() {
			return new Option();
		}
	}

}
