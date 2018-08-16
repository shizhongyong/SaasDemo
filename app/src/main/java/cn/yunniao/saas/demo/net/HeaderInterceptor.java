package cn.yunniao.saas.demo.net;

import java.io.IOException;
import java.util.Collections;
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

	private final HashMap<String, String> mHeaders = new HashMap<>();

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Request.Builder builder = request.newBuilder();

		for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
			builder.addHeader(entry.getKey(), entry.getValue());
		}

		return chain.proceed(builder.build());
	}

	public void addHeaders(Map<String, String> headers) {
		mHeaders.putAll(headers);
	}

	public void addHeader(String name, String value) {
		mHeaders.put(name, value);
	}

	public String removeHeader(String name) {
		return mHeaders.remove(name);
	}

	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(mHeaders);
	}

}
