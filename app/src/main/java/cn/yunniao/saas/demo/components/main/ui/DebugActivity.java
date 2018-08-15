package cn.yunniao.saas.demo.components.main.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import cn.yunniao.saas.demo.R;
import cn.yunniao.saas.demo.common.constant.SPConstant;
import cn.yunniao.saas.demo.common.constant.ServerHost;
import cn.yunniao.saas.demo.common.utils.ActivityManager;
import cn.yunniao.saas.demo.common.utils.AppUtil;
import cn.yunniao.saas.demo.common.utils.SPUtil;
import cn.yunniao.saas.demo.common.utils.UIUtil;
import cn.yunniao.saas.demo.common.view.activity.BaseTitleActivity;
import cn.yunniao.saas.demo.service.RebootService;

public class DebugActivity extends BaseTitleActivity {

	@BindView(R.id.edit_text)
	protected EditText mEditText;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		initView();
	}

	private void initView() {
		setTitle(AppUtil.getVersionName());
		setRightText(R.string.ok);

		updateServer(ServerHost.getServerAddress());
	}

	@Override
	protected void onClickTitleLeft() {
		UIUtil.hideSoftInput(mEditText);
		super.onClickTitleLeft();
	}

	@Override
	protected void onClickTitleRight() {
		UIUtil.hideSoftInput(mEditText);
		new AlertDialog.Builder(this)
				.setMessage(R.string.modify_server_address)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String address = mEditText.getText().toString().trim();
						SPUtil.putString(SPConstant.SERVER_ADDRESS, address);
						SPUtil.commit();
						restartApp();
					}
				})
				.setNegativeButton(R.string.no, null)
				.create()
				.show();
	}

	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_f1:
				updateServer(ServerHost.SERVER_F1);
				break;
			case R.id.btn_m1:
				updateServer(ServerHost.SERVER_M1);
				break;
			case R.id.btn_102:
				updateServer(ServerHost.SERVER_102);
				break;
			case R.id.btn_pre:
				updateServer(ServerHost.SERVER_STAGING);
				break;
			case R.id.btn_release:
				updateServer(ServerHost.SERVER_PRODUCT);
				break;
			default:
				break;
		}
	}

	private void updateServer(String address) {
		mEditText.setText(address);
		mEditText.setSelection(mEditText.length());
	}

	private void restartApp() {
		RebootService.launch(this, Process.myPid());
		ActivityManager.getInstance().finishAllActivity();
	}

}
