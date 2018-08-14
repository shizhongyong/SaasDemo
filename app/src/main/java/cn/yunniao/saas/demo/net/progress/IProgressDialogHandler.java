package cn.yunniao.saas.demo.net.progress;

import android.content.DialogInterface;

/**
 * Created by shizy on 2017/11/4.
 */

public interface IProgressDialogHandler {

	void showProgressDialog(DialogInterface.OnCancelListener listener);

	void dismissProgressDialog();

}
