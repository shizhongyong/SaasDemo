package cn.yunniao.saas.demo;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import cn.yunniao.saas.demo.common.constant.ServerHost;
import cn.yunniao.saas.demo.common.utils.ActivityManager;
import cn.yunniao.saas.demo.common.utils.AppUtil;
import cn.yunniao.saas.demo.common.utils.NetUtil;
import cn.yunniao.saas.demo.net.RetrofitHelper;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initialize();
	}

	private void initialize() {
		AppUtil.init(this);
		RetrofitHelper.getInstance().init(this, ServerHost.getServerAddress());

		registerActivityLifecycleCallbacks(ActivityManager.getInstance());
	}

}
