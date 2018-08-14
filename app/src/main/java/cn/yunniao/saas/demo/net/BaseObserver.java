package cn.yunniao.saas.demo.net;

import cn.yunniao.saas.demo.BuildConfig;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by shizy on 2017/11/4.
 * Response结果订阅
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {

	@Override
	public final void onComplete() {
		onFinally();
		if (!isDisposed()) {
			dispose();
		}
	}

	@Override
	public final void onError(Throwable e) {
		if (BuildConfig.DEBUG) {
			e.printStackTrace();
		}
		if (!(e instanceof ResponseException)) {
			e = new ResponseException();
		}
		onFailure((ResponseException) e);
		onFinally();
		if (!isDisposed()) {
			dispose();
		}
	}

	protected void onFailure(ResponseException e) {

	}

	protected void onFinally() {

	}

}
