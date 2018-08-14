package cn.yunniao.saas.demo.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shizy on 2017/11/6.
 * 添加Header
 */

public class HeaderInterceptor implements Interceptor {

	private static final HashMap<String, String> HEADERS = new HashMap<>();

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Request.Builder builder = request.newBuilder();

		for (Map.Entry<String, String> entry : HEADERS.entrySet()) {
			builder.addHeader(entry.getKey(), entry.getValue());
		}

		return chain.proceed(builder.build());
	}

	static void addHeader(Map<String, String> headers) {
		HEADERS.putAll(headers);
	}

	static void addHeader(String name, String value) {
		HEADERS.put(name, value);
	}

	static String removeHeader(String name) {
		return HEADERS.remove(name);
	}

}
