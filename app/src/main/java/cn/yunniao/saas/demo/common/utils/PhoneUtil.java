package cn.yunniao.saas.demo.common.utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about phone
 * </pre>
 */
public final class PhoneUtil {

	private static final String PREFS_FILE = "device_id.xml";
	private static final String PREFS_DEVICE_ID = "device_id";

	private static volatile UUID uuid;

	private PhoneUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * Return whether the device is phone.
	 *
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isPhone() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
	}

	/**
	 * Return the unique device id.
	 * <p>Must hold
	 * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
	 *
	 * @return the unique device id
	 */
	@SuppressLint("HardwareIds")
	@RequiresPermission(READ_PHONE_STATE)
	public static String getDeviceId() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			if (tm == null) return "";
			String imei = tm.getImei();
			if (!TextUtils.isEmpty(imei)) return imei;
			String meid = tm.getMeid();
			return TextUtils.isEmpty(meid) ? "" : meid;

		}
		return tm != null ? tm.getDeviceId() : "";
	}

	/**
	 * Return the serial of device.
	 *
	 * @return the serial of device
	 */
	@SuppressLint("HardwareIds")
	@RequiresPermission(READ_PHONE_STATE)
	public static String getSerial() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? Build.getSerial() : Build.SERIAL;
	}

	/**
	 * Return the IMEI.
	 * <p>Must hold
	 * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
	 *
	 * @return the IMEI
	 */
	@SuppressLint("HardwareIds")
	@RequiresPermission(READ_PHONE_STATE)
	public static String getIMEI() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			return tm != null ? tm.getImei() : "";
		}
		return tm != null ? tm.getDeviceId() : "";
	}

	/**
	 * Return the MEID.
	 * <p>Must hold
	 * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
	 *
	 * @return the MEID
	 */
	@SuppressLint("HardwareIds")
	@RequiresPermission(READ_PHONE_STATE)
	public static String getMEID() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			return tm != null ? tm.getMeid() : "";
		} else {
			return tm != null ? tm.getDeviceId() : "";
		}
	}

	/**
	 * Return the IMSI.
	 * <p>Must hold
	 * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
	 *
	 * @return the IMSI
	 */
	@SuppressLint("HardwareIds")
	@RequiresPermission(READ_PHONE_STATE)
	public static String getIMSI() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return tm != null ? tm.getSubscriberId() : "";
	}

	/**
	 * Returns the current phone type.
	 *
	 * @return the current phone type
	 * <ul>
	 * <li>{@link TelephonyManager#PHONE_TYPE_NONE}</li>
	 * <li>{@link TelephonyManager#PHONE_TYPE_GSM }</li>
	 * <li>{@link TelephonyManager#PHONE_TYPE_CDMA}</li>
	 * <li>{@link TelephonyManager#PHONE_TYPE_SIP }</li>
	 * </ul>
	 */
	public static int getPhoneType() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return tm != null ? tm.getPhoneType() : -1;
	}

	/**
	 * Return whether sim card state is ready.
	 *
	 * @return {@code true}: yes<br>{@code false}: no
	 */
	public static boolean isSimCardReady() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
	}

	/**
	 * Return the sim operator name.
	 *
	 * @return the sim operator name
	 */
	public static String getSimOperatorName() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		return tm != null ? tm.getSimOperatorName() : "";
	}

	/**
	 * Return the sim operator using mnc.
	 *
	 * @return the sim operator
	 */
	public static String getSimOperatorByMnc() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		String operator = tm != null ? tm.getSimOperator() : null;
		if (operator == null) return null;
		switch (operator) {
			case "46000":
			case "46002":
			case "46007":
				return "中国移动";
			case "46001":
				return "中国联通";
			case "46003":
				return "中国电信";
			default:
				return operator;
		}
	}

	/**
	 * Return the phone status.
	 * <p>Must hold
	 * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
	 *
	 * @return DeviceId = 99000311726612<br>
	 * DeviceSoftwareVersion = 00<br>
	 * Line1Number =<br>
	 * NetworkCountryIso = cn<br>
	 * NetworkOperator = 46003<br>
	 * NetworkOperatorName = 中国电信<br>
	 * NetworkType = 6<br>
	 * PhoneType = 2<br>
	 * SimCountryIso = cn<br>
	 * SimOperator = 46003<br>
	 * SimOperatorName = 中国电信<br>
	 * SimSerialNumber = 89860315045710604022<br>
	 * SimState = 5<br>
	 * SubscriberId(IMSI) = 460030419724900<br>
	 * VoiceMailNumber = *86<br>
	 */
	@SuppressLint("HardwareIds")
	@RequiresPermission(READ_PHONE_STATE)
	public static String getPhoneStatus() {
		TelephonyManager tm =
				(TelephonyManager) AppUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) return "";
		String str = "";
		str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
		str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
		str += "Line1Number = " + tm.getLine1Number() + "\n";
		str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
		str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
		str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
		str += "NetworkType = " + tm.getNetworkType() + "\n";
		str += "PhoneType = " + tm.getPhoneType() + "\n";
		str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
		str += "SimOperator = " + tm.getSimOperator() + "\n";
		str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
		str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
		str += "SimState = " + tm.getSimState() + "\n";
		str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
		str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
		return str;
	}

	/**
	 * Skip to dial.
	 *
	 * @param phoneNumber The phone number.
	 */
	public static void dial(final String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
		AppUtil.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}

	/**
	 * Make a phone call.
	 * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
	 *
	 * @param phoneNumber The phone number.
	 */
	@RequiresPermission(CALL_PHONE)
	public static void call(final String phoneNumber) {
		Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
		AppUtil.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}

	/**
	 * Send sms.
	 *
	 * @param phoneNumber The phone number.
	 * @param content     The content.
	 */
	public static void sendSms(final String phoneNumber, final String content) {
		Uri uri = Uri.parse("smsto:" + phoneNumber);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		AppUtil.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}

	/**
	 * Send sms silently.
	 * <p>Must hold {@code <uses-permission android:name="android.permission.SEND_SMS" />}</p>
	 *
	 * @param phoneNumber The phone number.
	 * @param content     The content.
	 */
	@RequiresPermission(SEND_SMS)
	public static void sendSmsSilent(final String phoneNumber, final String content) {
		if (TextUtils.isEmpty(content)) return;
		PendingIntent sentIntent = PendingIntent.getBroadcast(AppUtil.getContext(), 0, new Intent("send"), 0);
		SmsManager smsManager = SmsManager.getDefault();
		if (content.length() >= 70) {
			List<String> ms = smsManager.divideMessage(content);
			for (String str : ms) {
				smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
			}
		} else {
			smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
		}
	}

	public static UUID getUuid() {
		Context context = AppUtil.getContext();
		if (uuid == null) {
			synchronized (PhoneUtil.class) {
				if (uuid == null) {
					final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
					final String id = prefs.getString(PREFS_DEVICE_ID, null);
					if (id != null) {
						uuid = UUID.fromString(id);
					} else {
						final String deviceId = ((TelephonyManager) context
								.getSystemService(Context.TELEPHONY_SERVICE))
								.getDeviceId();
						try {
							if (deviceId != null) {
								uuid = UUID.nameUUIDFromBytes(deviceId
										.getBytes("utf8"));
							} else {
								final String androidId = Settings.Secure.getString(
										context.getContentResolver(),
										Settings.Secure.ANDROID_ID);
								if (!"9774d56d682e549c".equals(androidId)) {
									uuid = UUID.nameUUIDFromBytes(androidId
											.getBytes("utf8"));
								} else {
									uuid = UUID.randomUUID();
								}
							}
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
						prefs.edit()
								.putString(PREFS_DEVICE_ID, uuid.toString())
								.apply();
					}
				}
			}
		}
		return uuid;
	}
}
