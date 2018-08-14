package cn.yunniao.saas.demo.common.constant;

import cn.yunniao.saas.demo.BuildConfig;
import cn.yunniao.saas.demo.common.utils.SPUtil;

public class ServerHost {

	// testing
	public static final String SERVER_F1 = "http://192.168.204.14:8080/";
	public static final String SERVER_M1 = "http://192.168.204.14:8080/";
	public static final String SERVER_102 = "http://192.168.204.14:8080/";

	// staging
	public static final String SERVER_STAGING = "http://192.168.204.14:8080/";

	// product
	public static final String SERVER_PRODUCT = "http://192.168.204.14:8080/";

	private static String sServerAddress = SPUtil.getString(SPConstant.SERVER_ADDRESS, BuildConfig.DEBUG ? SERVER_M1 : SERVER_PRODUCT);

	public static String getServerAddress() {
		return sServerAddress;
	}

	private ServerHost() {
		throw new RuntimeException("Can not invoke constructor!");
	}

}
