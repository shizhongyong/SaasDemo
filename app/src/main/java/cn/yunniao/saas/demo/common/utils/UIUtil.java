package cn.yunniao.saas.demo.common.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UIUtil {

	private UIUtil() {
	}

	public static void setVisible(View... views) {
		setVisibility(View.VISIBLE, views);
	}

	public static void setGone(View... views) {
		setVisibility(View.GONE, views);
	}

	public static void setInvisible(View... views) {
		setVisibility(View.INVISIBLE, views);
	}

	private static void setVisibility(int visibility, View... views) {
		for (View view : views) {
			if (view != null && view.getVisibility() != visibility) {
				view.setVisibility(visibility);
			}
		}
	}

	/**
	 * 隐藏键盘
	 *
	 * @param view
	 */
	public static void hideSoftInput(View view) {
		if (view == null) {
			return;
		}
		InputMethodManager imm = (InputMethodManager) AppUtil.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

}
