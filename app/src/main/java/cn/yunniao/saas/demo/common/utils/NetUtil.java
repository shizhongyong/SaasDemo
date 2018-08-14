package cn.yunniao.saas.demo.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.yunniao.saas.demo.common.constant.NetConstant;

/**
 * 网络工具类
 */
public class NetUtil {

	private static final Map<String, String> HEADERS = new HashMap<>();

	static {
		HEADERS.put(NetConstant.OSNAME, NetConstant.ANDROID);
		HEADERS.put(NetConstant.OSVERSION, DeviceUtil.getSystemVersion());

		HEADERS.put(NetConstant.PRODUCT, DeviceUtil.getProduct());
		HEADERS.put(NetConstant.MANUFACTURER, DeviceUtil.getManufacturer());
		HEADERS.put(NetConstant.BRAND, DeviceUtil.getBrand());
		HEADERS.put(NetConstant.MODEL, DeviceUtil.getModel());
		HEADERS.put(NetConstant.ABIS, Arrays.toString(DeviceUtil.getABIs()));

		if (PermissionUtil.isGranted(android.Manifest.permission.READ_PHONE_STATE)) {
			HEADERS.put(NetConstant.IMEI, PhoneUtil.getDeviceId());
			HEADERS.put(NetConstant.UDID, PhoneUtil.getUuid().toString());
		}

		HEADERS.put(NetConstant.VERSION, String.valueOf(AppUtil.getVersionCode()));
	}

	public static Map<String, String> getHeaders() {
		return HEADERS;
	}

}
