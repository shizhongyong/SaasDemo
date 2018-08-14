package cn.yunniao.saas.demo.common.view.activity;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.yunniao.saas.demo.R;

/**
 * Created by shizy on 2018/8/3.
 */

public abstract class BaseTitleActivity extends BaseActivity {

	@BindView(R.id.tv_title)
	protected TextView mTitleTv;
	@BindView(R.id.tv_title_left)
	protected TextView mTitleLeftTv;
	@BindView(R.id.iv_title_left)
	protected ImageView mTitleLeftIv;
	@BindView(R.id.tv_title_right)
	protected TextView mTitleRightTv;

	@OnClick({R.id.tv_title_left, R.id.iv_title_left})
	protected void onClickTitleLeft() {
	}

	@OnClick(R.id.tv_title_right)
	protected void onClickTitleRight() {
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		if (mTitleTv != null) {
			mTitleTv.setText(title);
		}
	}

	@Override
	public void setTitle(@StringRes int titleId) {
		super.setTitle(titleId);
		if (mTitleTv != null) {
			mTitleTv.setText(titleId);
		}
	}

	public void setLeftImage(@DrawableRes int resId) {
		if (mTitleLeftIv != null) {
			mTitleLeftIv.setImageResource(resId);
			mTitleLeftIv.setVisibility(View.VISIBLE);
			mTitleLeftTv.setVisibility(View.GONE);
		}
	}

	public void setLeftText(CharSequence text) {
		if (mTitleLeftTv != null) {
			mTitleLeftTv.setText(text);
			mTitleLeftTv.setVisibility(View.VISIBLE);
			mTitleLeftIv.setVisibility(View.GONE);
		}
	}

	public void setLeftText(@StringRes int resId) {
		setLeftText(getString(resId));
	}

	public void setRightText(CharSequence text) {
		if (mTitleRightTv != null) {
			mTitleRightTv.setText(text);
			mTitleRightTv.setVisibility(View.VISIBLE);
		}
	}

	public void setRightText(@StringRes int resId) {
		setRightText(getString(resId));
	}

}
