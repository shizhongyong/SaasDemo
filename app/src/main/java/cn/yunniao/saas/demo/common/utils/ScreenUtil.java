package cn.yunniao.saas.demo.common.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

public class ScreenUtil {

	private static int screenWidth;
	private static int screenHeight;
	private static float density;

	private ScreenUtil() {
	}

	public static void init(Context context) {
		if (context == null) {
			return;
		}
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		density = dm.density;
	}

	public static void showToast(CharSequence text) {
		Toast.makeText(AppUtil.getContext(), text, Toast.LENGTH_SHORT);
	}

	public static void showToast(@StringRes int resId) {
		Toast.makeText(AppUtil.getContext(), resId, Toast.LENGTH_SHORT);
	}

	public static int screenWidth() {
		return screenWidth;
	}

	public static int screenHeight() {
		return screenHeight;
	}

	public static int dp2px(float dp) {
		return (int) (dp * density + 0.5f);
	}

}
