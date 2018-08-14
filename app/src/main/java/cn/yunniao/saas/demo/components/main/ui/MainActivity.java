package cn.yunniao.saas.demo.components.main.ui;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import cn.yunniao.saas.demo.common.utils.LogUtil;
import cn.yunniao.saas.demo.common.utils.RxJavaUtil;
import cn.yunniao.saas.demo.R;
import cn.yunniao.saas.demo.common.view.activity.BaseTitleActivity;
import cn.yunniao.saas.demo.components.main.api.IMainService;
import cn.yunniao.saas.demo.net.RetrofitHelper;
import cn.yunniao.saas.demo.net.progress.ProgressDialogObserver;
import cn.yunniao.saas.demo.net.response.ResponseData;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends BaseTitleActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	@BindView(R.id.layout_ptr)
	protected PtrFrameLayout mPtrLayout;
	@BindView(R.id.list_view)
	protected ListView mListView;

	private MainAdapter mAdapter;
	private int mPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPtrLayout.setMode(PtrFrameLayout.Mode.BOTH);
		mPtrLayout.setPtrHandler(new PtrDefaultHandler2() {
			@Override
			public void onLoadMoreBegin(PtrFrameLayout frame) {
				loadData(mPage + 1);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				loadData(1);
			}
		});

		mAdapter = new MainAdapter(this);
		mListView.setAdapter(mAdapter);
	}

	private void loadData(final int page) {
		IMainService service = RetrofitHelper.getInstance().createService(IMainService.class);

		service.listItem()
				.compose(RxJavaUtil.<ResponseData<List<String>>>applySchedulers())
				.as(this.<ResponseData<List<String>>>bindLifecycle())
				.subscribe(new ProgressDialogObserver<List<String>>(this) {
					@Override
					protected void onSuccess(ResponseData<List<String>> responseData) {
						LogUtil.d("onSuccess");
						mPage = page;
						if (page == 1) {
							mAdapter.clear();
						}
						mAdapter.addAll(responseData.getData());
					}

					@Override
					protected void onFinally() {
						super.onFinally();
						LogUtil.d("onFinally");
						mPtrLayout.refreshComplete();
					}
				});
	}

}