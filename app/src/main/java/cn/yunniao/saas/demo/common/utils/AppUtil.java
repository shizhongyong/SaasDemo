package cn.yunniao.saas.demo.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;

public class AppUtil {

	private static Context mContext;

	private AppUtil() {
	}

	public static void init(Context context) {
		if (context == null) {
			throw new NullPointerException("Context is null");
		}
		mContext = context.getApplicationContext();
	}

	public static Context getContext() {
		return mContext;
	}

	public static Resources getResources() {
		return mContext.getResources();
	}

	public static String getPackageName() {
		return mContext.getPackageName();
	}

	/**
	 * 获取版本名
	 */
	public static String getVersionName() {
		return getVersionName(getPackageName());
	}

	/**
	 * 获取版本名
	 */
	public static String getVersionName(final String packageName) {
		if (!TextUtils.isEmpty(packageName)) {
			try {
				PackageManager pm = mContext.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(packageName, 0);
				return pi == null ? null : pi.versionName;
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 获取版本号
	 */
	public static int getVersionCode() {
		return getVersionCode(getPackageName());
	}

	/**
	 * 获取版本号
	 */
	public static int getVersionCode(final String packageName) {
		if (!TextUtils.isEmpty(packageName)) {
			try {
				PackageManager pm = mContext.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(packageName, 0);
				return pi == null ? -1 : pi.versionCode;
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}
