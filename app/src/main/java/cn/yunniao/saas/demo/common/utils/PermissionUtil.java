package cn.yunniao.saas.demo.common.utils;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermissionUtil {

	/**
	 * Return the permissions used in application.
	 *
	 * @return the permissions used in application
	 */
	public static List<String> getPermissions() {
		return getPermissions(AppUtil.getContext().getPackageName());
	}

	/**
	 * Return the permissions used in application.
	 *
	 * @param packageName The name of the package.
	 * @return the permissions used in application
	 */
	public static List<String> getPermissions(final String packageName) {
		PackageManager pm = AppUtil.getContext().getPackageManager();
		try {
			return Arrays.asList(
					pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
							.requestedPermissions
			);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Return whether <em>you</em> have granted the permissions.
	 *
	 * @param permissions The permissions.
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isGranted(final String... permissions) {
		for (String permission : permissions) {
			if (!isGranted(permission)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isGranted(final String permission) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
				|| PackageManager.PERMISSION_GRANTED
				== ContextCompat.checkSelfPermission(AppUtil.getContext(), permission);
	}


}
