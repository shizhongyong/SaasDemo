package cn.yunniao.saas.demo.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shizy on 2017/11/3.
 * 服务端返回的统一JSON结构
 */

public class ResponseData<T> {

	private int code;
	private String msg;
	@SerializedName("info")
	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
