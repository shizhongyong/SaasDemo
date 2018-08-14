package cn.yunniao.saas.demo.common.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by shizy on 2017/8/3.
 */

public abstract class CommonAdapter<T> extends BaseCommonAdapter<T, ViewHolder> {

	public CommonAdapter(Context context, @LayoutRes int layoutResId) {
		super(context, layoutResId);
	}

	public CommonAdapter(Context context, @LayoutRes int layoutResId, List data) {
		super(context, layoutResId, data);
	}

	@Override
	protected ViewHolder getViewHolder(Context context, int position, View convertView, ViewGroup parent, @LayoutRes int layoutId) {
		return ViewHolder.getViewHolder(context, position, convertView, parent, layoutId);
	}

}
