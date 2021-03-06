package cn.yunniao.saas.demo.common.utils;

import android.arch.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shizy on 2017/11/3.
 */

public class RxJavaUtil {

	public static <T> ObservableTransformer<T, T> mainSchedulers() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> upstream) {
				return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
			}
		};
	}

	public static <T> ObservableTransformer<T, T> ioSchedulers() {
		return new ObservableTransformer<T, T>() {
			@Override
			public ObservableSource<T> apply(Observable<T> upstream) {
				return upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
			}
		};
	}

	public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
		return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner));
	}

}
