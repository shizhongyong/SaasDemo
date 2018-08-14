package cn.yunniao.saas.demo.common.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.yunniao.saas.demo.common.utils.ImageLoader;

/**
 * Created by shizy on 2017/8/3.
 */

public class ViewHolder {

	private final Context mContext;
	private final SparseArray<View> mViews;
	private View mConvertView;
	private int mPosition;

	protected ViewHolder(Context context, int position, ViewGroup parent, @LayoutRes int layoutId) {
		mContext = context;
		mPosition = position;
		mViews = new SparseArray<>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}

	public static ViewHolder getViewHolder(Context context, int position, View convertView, ViewGroup parent, @LayoutRes int layoutId) {
		if (convertView == null) {
			return new ViewHolder(context, position, parent, layoutId);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.mPosition = position;
		return holder;
	}

	public View getView() {
		return mConvertView;
	}

	public int getPosition() {
		return mPosition;
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T retrieveView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public <T extends View> T getView(int viewId) {
		return retrieveView(viewId);
	}

	public ViewHolder setText(int viewId, @StringRes int stringResId) {
		TextView view = retrieveView(viewId);
		view.setText(stringResId);
		return this;
	}

	public ViewHolder setText(int viewId, CharSequence value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	public ViewHolder setImageResource(int viewId, @DrawableRes int imageResId) {
		ImageView view = retrieveView(viewId);
		view.setImageResource(imageResId);
		return this;
	}

	public ViewHolder setBackgroundColor(int viewId, @ColorInt int color) {
		View view = retrieveView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	public ViewHolder setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
		View view = retrieveView(viewId);
		view.setBackgroundResource(backgroundRes);
		return this;
	}

	public ViewHolder setTextColor(int viewId, @ColorInt int textColor) {
		TextView view = retrieveView(viewId);
		view.setTextColor(textColor);
		return this;
	}

	public ViewHolder setTextColorRes(int viewId, @ColorRes int textColorRes) {
		TextView view = retrieveView(viewId);
		view.setTextColor(mContext.getResources().getColor(textColorRes));
		return this;
	}

	public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
		ImageView view = retrieveView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}

	public ViewHolder setImageUrl(int viewId, String imageUrl) {
		ImageView view = retrieveView(viewId);
		ImageLoader.load(mContext, imageUrl, view);
		return this;
	}

	public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView view = retrieveView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	public ViewHolder setAlpha(int viewId, float value) {
		retrieveView(viewId).setAlpha(value);
		return this;
	}

	public ViewHolder setVisible(int viewId) {
		View view = retrieveView(viewId);
		if (view.getVisibility() != View.VISIBLE) {
			view.setVisibility(View.VISIBLE);
		}
		return this;
	}

	public ViewHolder setInvisible(int viewId) {
		View view = retrieveView(viewId);
		if (view.getVisibility() != View.INVISIBLE) {
			view.setVisibility(View.INVISIBLE);
		}
		return this;
	}

	public ViewHolder setGone(int viewId) {
		View view = retrieveView(viewId);
		if (view.getVisibility() != View.GONE) {
			view.setVisibility(View.GONE);
		}
		return this;
	}

	public ViewHolder linkify(int viewId) {
		TextView view = retrieveView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
		return this;
	}

	public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
		View view = retrieveView(viewId);
		view.setOnTouchListener(listener);
		return this;
	}

	public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnLongClickListener(listener);
		return this;
	}

	public ViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
		CompoundButton view = retrieveView(viewId);
		view.setOnCheckedChangeListener(listener);
		return this;
	}

	public ViewHolder setTag(int viewId, Object tag) {
		View view = retrieveView(viewId);
		view.setTag(tag);
		return this;
	}

	public ViewHolder setTag(int viewId, int key, Object tag) {
		View view = retrieveView(viewId);
		view.setTag(key, tag);
		return this;
	}

	public ViewHolder setChecked(int viewId, boolean checked) {
		View view = retrieveView(viewId);
		// View unable cast to Checkable
		if (view instanceof CompoundButton) {
			((CompoundButton) view).setChecked(checked);
		} else if (view instanceof CheckedTextView) {
			((CheckedTextView) view).setChecked(checked);
		}
		return this;
	}

}
