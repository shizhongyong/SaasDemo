package cn.yunniao.saas.demo.components.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.yunniao.saas.demo.R;
import cn.yunniao.saas.demo.common.utils.LogUtil;
import cn.yunniao.saas.demo.common.utils.RxJavaUtil;
import cn.yunniao.saas.demo.common.utils.ToastUtil;
import cn.yunniao.saas.demo.common.view.activity.BaseTitleActivity;
import cn.yunniao.saas.demo.components.main.api.IDownloadService;
import cn.yunniao.saas.demo.components.main.api.IMainService;
import cn.yunniao.saas.demo.net.RetrofitHelper;
import cn.yunniao.saas.demo.net.progress.ProgressDialogObserver;
import cn.yunniao.saas.demo.net.response.FileObserver;
import cn.yunniao.saas.demo.net.response.ResponseData;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

public class MainActivity extends BaseTitleActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private PtrDefaultHandler2 mPtrHandler = new PtrDefaultHandler2() {
		@Override
		public void onLoadMoreBegin(PtrFrameLayout frame) {
			loadData(mPage + 1);
		}

		@Override
		public void onRefreshBegin(PtrFrameLayout frame) {
			loadData(1);
		}
	};

	@BindView(R.id.layout_ptr)
	protected PtrFrameLayout mPtrLayout;
	@BindView(R.id.list_view)
	protected ListView mListView;

	private MainAdapter mAdapter;
	private int mPage;
	private boolean downloading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setTitle(R.string.app_name);
		setLeftText(R.string.download);
		setRightText("DEBUG");

		mPtrLayout.setMode(PtrFrameLayout.Mode.BOTH);
		mPtrLayout.setPtrHandler(mPtrHandler);

		mAdapter = new MainAdapter(this);
		mListView.setAdapter(mAdapter);

		loadData(1);
	}

	@OnItemClick(R.id.list_view)
	protected void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		startActivity(new Intent(this, DebugActivity.class));
	}

	@Override
	protected void onClickTitleLeft() {
		downloadFile();
	}

	@Override
	protected void onClickTitleRight() {
		startActivity(new Intent(this, DebugActivity.class));
	}

	private void loadData(final int page) {
		IMainService service = RetrofitHelper.getInstance().createService(IMainService.class);

		service.listItem()
				.compose(RxJavaUtil.<ResponseData<List<String>>>mainSchedulers())
				.as(this.<ResponseData<List<String>>>bindLifecycle())
				.subscribe(new ProgressDialogObserver<List<String>>(this) {
					@Override
					protected void onSuccess(ResponseData<List<String>> responseData) {
						ToastUtil.showShort(R.string.load_finished);
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

	private void downloadFile() {
		if (downloading) {
			return;
		}
		downloading = true;

		final String localPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SaasDemo.zip").getAbsolutePath();
		final String fileUrl = "http://192.168.200.143:8080/jiagu/captain_v2.1.0_2018-08-15.zip";
		IDownloadService service = RetrofitHelper.getInstance().createService(IDownloadService.class);
		service.downloadFile(fileUrl)
				.compose(RxJavaUtil.<ResponseBody>ioSchedulers())
				.as(this.<ResponseBody>bindLifecycle())
				.subscribe(new FileObserver(localPath) {
					@Override
					protected void onSuccess() {
						ToastUtil.showShort("下载完成！");
					}

					@Override
					protected void onFinally() {
						downloading = false;
					}
				});
	}

}
