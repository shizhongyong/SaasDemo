package cn.yunniao.saas.demo.common.utils;

import android.view.View;

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
}
