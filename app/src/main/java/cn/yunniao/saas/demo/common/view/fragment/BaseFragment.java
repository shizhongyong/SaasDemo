package cn.yunniao.saas.demo.common.view.fragment;

import android.support.v4.app.Fragment;

import com.uber.autodispose.AutoDisposeConverter;

import cn.yunniao.saas.demo.common.utils.RxJavaUtil;

/**
 * Created by shizy on 2018/8/3.
 */

public class BaseFragment extends Fragment {

	protected <T> AutoDisposeConverter<T> bindLifecycle() {
		return RxJavaUtil.bindLifecycle(this);
	}

}
