package cn.yunniao.saas.demo.common.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizy on 2017/8/3.
 */

public abstract class BaseCommonAdapter<T, VH extends ViewHolder> extends BaseAdapter {

	protected final Context mContext;

	protected final int mLayoutResId;

	protected final List<T> mData;

	protected boolean showFooterView = false;

	public BaseCommonAdapter(Context context, @LayoutRes int layoutResId) {
		this(context, layoutResId, null);
	}

	public BaseCommonAdapter(Context context, @LayoutRes int layoutResId, List<T> data) {
		mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
		mContext = context;
		mLayoutResId = layoutResId;
	}

	@Override
	public int getCount() {
		return mData.size() + (showFooterView ? 1 : 0);
	}

	@Override
	public T getItem(int position) {
		if (position < mData.size()) {
			return mData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position < mData.size()) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return position < mData.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 0) {
			final VH holder = getViewHolder(mContext, position, convertView, parent, mLayoutResId);
			T item = getItem(position);
			convert(holder, item);
			return holder.getView();

		}
		return getFooterView(convertView, parent);
	}

	private View getFooterView(View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = createFooterView(parent);
		}
		return convertView;
	}

	/**
	 * 创建底部View
	 *
	 * @param parent
	 * @return
	 */
	protected View createFooterView(ViewGroup parent) {
		FrameLayout container = new FrameLayout(mContext);
		container.setForegroundGravity(Gravity.CENTER);
		ProgressBar progress = new ProgressBar(mContext);
		container.addView(progress);
		return container;
	}

	public void setShowFooterView(boolean show) {
		if (showFooterView != show) {
			showFooterView = show;
			notifyDataSetChanged();
		}
	}

	public void add(T item) {
		mData.add(item);
		notifyDataSetChanged();
	}

	public void addAll(List<T> items) {
		mData.addAll(items);
		notifyDataSetChanged();
	}

	public void remove(T item) {
		mData.remove(item);
		notifyDataSetChanged();
	}

	public void clear() {
		mData.clear();
		notifyDataSetChanged();
	}

	protected abstract void convert(VH holder, T item);

	protected abstract VH getViewHolder(Context context, int position, View convertView, ViewGroup parent, @LayoutRes int layoutId);

}
