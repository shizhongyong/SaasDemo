package cn.yunniao.saas.demo.net.response;

import cn.yunniao.saas.demo.common.utils.FileIOUtil;
import cn.yunniao.saas.demo.net.BaseObserver;
import cn.yunniao.saas.demo.net.ResponseException;
import okhttp3.ResponseBody;

public abstract class FileObserver extends BaseObserver<ResponseBody> {

	private String mLocalPath;

	public FileObserver(String path) {
		mLocalPath = path;
	}

	@Override
	public final void onNext(ResponseBody body) {
		boolean success = FileIOUtil.writeFileFromIS(mLocalPath, body.byteStream());
		if (success) {
			onSuccess();
		} else {
			onError(new ResponseException(-1, "保存文件出错！"));
		}
	}

	protected abstract void onSuccess();
}
