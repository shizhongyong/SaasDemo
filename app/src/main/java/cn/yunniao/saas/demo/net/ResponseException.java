package cn.yunniao.saas.demo.net;

/**
 * Created by shizy on 2017/11/4.
 */

public class ResponseException extends Exception {

	private int code;
	private String msg;

	public ResponseException() {
		this(-1, "网络错误！");
	}

	public ResponseException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

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
}
