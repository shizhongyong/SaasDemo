package cn.yunniao.saas.demo.components.main.ui;

import android.content.Context;

import cn.yunniao.saas.demo.common.view.adapter.CommonAdapter;
import cn.yunniao.saas.demo.common.view.adapter.ViewHolder;

public class MainAdapter extends CommonAdapter<String> {

	public MainAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}

	@Override
	protected void convert(ViewHolder holder, String item) {
		holder.setText(android.R.id.text1, item);
	}
}
