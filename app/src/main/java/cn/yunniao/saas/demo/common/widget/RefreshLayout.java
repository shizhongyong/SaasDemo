package cn.yunniao.saas.demo.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class RefreshLayout extends PtrFrameLayout {

	private MaterialHeader mPtrHeader;
	private PtrClassicDefaultFooter mPtrClassicFooter;

	public RefreshLayout(Context context) {
		super(context);
		initView();
	}

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public RefreshLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		mPtrHeader = new MaterialHeader(getContext());
		mPtrHeader.setPadding(0, PtrLocalDisplay.dp2px(8), 0, PtrLocalDisplay.dp2px(8));
		setHeaderView(mPtrHeader);
		addPtrUIHandler(mPtrHeader);

		mPtrClassicFooter = new PtrClassicDefaultFooter(getContext());
		setFooterView(mPtrClassicFooter);
		addPtrUIHandler(mPtrClassicFooter);

		setDurationToBack(300);
		setDurationToClose(500);
		setKeepHeaderWhenRefresh(true);
		setPullToRefresh(false);
	}
}
