package cn.yunniao.saas.demo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.support.annotation.Nullable;

public class RebootService extends IntentService {

	private static final String EXTRA_PID = "pid";

	public RebootService() {
		super(RebootService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		if (intent == null) {
			return;
		}
		final int pid = intent.getIntExtra(EXTRA_PID, -1);
		Process.killProcess(pid);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		startActivity(getPackageManager().getLaunchIntentForPackage(getPackageName()));
	}

	public static void launch(Context context, int pid) {
		if (context == null) {
			return;
		}
		Intent intent = new Intent(context, RebootService.class);
		intent.putExtra(EXTRA_PID, pid);
		context.startService(intent);
	}

}
