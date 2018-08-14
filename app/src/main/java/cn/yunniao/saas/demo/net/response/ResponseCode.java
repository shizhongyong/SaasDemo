package cn.yunniao.saas.demo.net.response;

/**
 * Created by shizy on 2017/11/4.
 * 服务端返回的状态码
 */

public interface ResponseCode {

	/**
	 * 成功
	 */
	int SUCCESS = 0;
	/**
	 * 失败
	 */
	int FAILURE = 1;
	/**
	 * 需要登录
	 */
	int CODE_4 = 4;

	int CODE_5 = 5;
	int CODE_100 = 100;
	int CODE_101 = 101;
	int CODE_102 = 102;
	int CODE_103 = 103;

}
