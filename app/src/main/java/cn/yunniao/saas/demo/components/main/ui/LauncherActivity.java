package cn.yunniao.saas.demo.components.main.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import cn.yunniao.saas.demo.R;
import cn.yunniao.saas.demo.common.utils.NetUtil;
import cn.yunniao.saas.demo.common.utils.PermissionUtil;
import cn.yunniao.saas.demo.common.view.activity.BaseActivity;
import cn.yunniao.saas.demo.net.RetrofitHelper;

public class LauncherActivity extends BaseActivity {

	private static final int RC_PERMISSION = 1;

	private Runnable mToMain = new Runnable() {
		@Override
		public void run() {
			startActivity(new Intent(LauncherActivity.this, MainActivity.class));
			finish();
		}
	};

	private Handler mHandler = new Handler();

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		for (int result : grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) {
				showPermissionDeniedDialog();
				return;
			}
		}
		allPermissionGranted();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

		List<String> permissions = PermissionUtil.getPermissions();
		List<String> denied = new ArrayList<>();
		for (String permission : permissions) {
			if (!PermissionUtil.isGranted(permission)) {
				denied.add(permission);
			}
		}

		if (denied.size() > 0) {
			String[] deniedPermissions = new String[denied.size()];
			denied.toArray(deniedPermissions);
			ActivityCompat.requestPermissions(this, deniedPermissions, RC_PERMISSION);
		} else {
			allPermissionGranted();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mToMain);
	}

	private void allPermissionGranted() {
		RetrofitHelper.getInstance().addHeaders(NetUtil.getHeaders());

		mHandler.postDelayed(mToMain, 2000);
	}

	private void showPermissionDeniedDialog() {
		new AlertDialog.Builder(this)
				.setMessage(R.string.permission_denied)
				.setCancelable(false)
				.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				})
				.create()
				.show();
	}

}
