package cn.yunniao.saas.demo.components.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import cn.yunniao.saas.demo.R;
import cn.yunniao.saas.demo.common.utils.LogUtil;
import cn.yunniao.saas.demo.common.view.activity.BaseTitleActivity;
import cn.yunniao.saas.demo.net.RetrofitHelper;

public class WebViewActivity extends BaseTitleActivity {

	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	private static final String FILE = "file";

	private static final String EXTRA_TITLE = "title";
	private static final String EXTRA_URL = "url";

	@BindView(R.id.web_view)
	protected WebView mWebView;

	private CustomChromeClient mWebChromeClient;
	private ProgressDialog mProgressDialog = null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mWebChromeClient != null) {
			mWebChromeClient.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		initView();
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onClickTitleRight() {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWebView.onResume();
		mWebView.getSettings().setJavaScriptEnabled(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mWebView.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mWebView.getSettings().setJavaScriptEnabled(false);
	}

	@Override
	protected void onDestroy() {
		try {
			// 先清空，再删除
			mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			mWebView.clearHistory();

			ViewGroup parent = (ViewGroup) mWebView.getParent();
			parent.removeView(mWebView);
			mWebView.destroy();
			mWebView = null;
		} catch (Exception e) {
			LogUtil.e(e);
		}
		super.onDestroy();
	}

	private void initView() {
		Intent intent = getIntent();
		final String title = intent.getStringExtra(EXTRA_TITLE);
		final String url = intent.getStringExtra(EXTRA_URL);

		setTitle(title);
		setRightText(R.string.close);

		initWebView();
		loadUrl(url);
	}

	private void initWebView() {
		WebSettings settings = mWebView.getSettings();
		settings.setDatabaseEnabled(true);
		settings.setAppCacheEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		settings.setDomStorageEnabled(true);
		settings.setGeolocationEnabled(true);

		mWebChromeClient = new CustomChromeClient(this);
		mWebView.setWebChromeClient(mWebChromeClient);
		mWebView.setWebViewClient(new CustomWebViewClient(this));
	}

	private void loadUrl(String url) {
		mWebView.loadUrl(url, RetrofitHelper.getInstance().getHeader());
	}

	private void showProgressDialog() {
		if (isFinishing()) {
			return;
		}
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCanceledOnTouchOutside(false);//解决4.0版本触摸消失的问题
			mProgressDialog.setMessage(getString(R.string.loading));
		}
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	private void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	private void updateTitle(String newTitle) {
		if (TextUtils.isEmpty(newTitle)) {
			return;
		}
		if (mWebView == null) {
			return;
		}
		String url = mWebView.getUrl();
		if (TextUtils.isEmpty(url)) {
			return;
		}
		if (url.contains(newTitle)) {
			return;
		}

		setTitle(newTitle);
	}

	public boolean onInterceptUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return true;
		}

		Uri uri = Uri.parse(url);
		String scheme = uri.getScheme();
		if (TextUtils.isEmpty(scheme)) {
			return true;
		}

		Intent intent = null;
		scheme = scheme.toLowerCase();
		switch (scheme) {
			case HTTP:
			case HTTPS:
			case FILE:
				break;
			default:
				intent = new Intent(Intent.ACTION_VIEW, uri);
				break;
		}

		if (intent != null) {
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			}
			return true;
		}

		return false;
	}

	public static void launch(Activity activity, String title, String url) {
		if (activity == null) {
			return;
		}
		Intent intent = new Intent(activity, WebViewActivity.class);
		intent.putExtra(EXTRA_TITLE, title);
		intent.putExtra(EXTRA_URL, url);
		activity.startActivity(intent);
	}

	private static class CustomWebViewClient extends WebViewClient {

		private WeakReference<WebViewActivity> mWeakRef;

		CustomWebViewClient(WebViewActivity activity) {
			mWeakRef = new WeakReference<>(activity);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			WebViewActivity activity = mWeakRef.get();
			if (activity != null) {
				activity.showProgressDialog();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			WebViewActivity activity = mWeakRef.get();
			if (activity != null) {
				activity.hideProgressDialog();
				activity.updateTitle(view.getTitle());
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			WebViewActivity activity = mWeakRef.get();
			if (activity != null) {
				return activity.onInterceptUrl(url);
			}
			return false;
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}
	}

	private static class CustomChromeClient extends WebChromeClient {

		private static final int RC_CHOOSE_FILE = 0x1234;

		private ValueCallback<Uri> mUploadFile;
		private ValueCallback<Uri[]> mUploadMultiFiles;

		private WeakReference<WebViewActivity> mWeakRef;

		CustomChromeClient(WebViewActivity activity) {
			mWeakRef = new WeakReference<>(activity);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			WebViewActivity activity = mWeakRef.get();
			if (activity != null) {
				activity.updateTitle(title);
			}
		}

		@Override
		public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
			callback.invoke(origin, true, false);
			super.onGeolocationPermissionsShowPrompt(origin, callback);
		}

		// 3.0
		public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
			openFileChooser(uploadFile, acceptType, null);
		}

		// 4.1.2
		public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
			mUploadFile = uploadFile;
			openFileChooserActivity(acceptType);
		}

		// 5.0
		@Override
		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
			mUploadMultiFiles = filePathCallback;
			openFileChooserActivity(fileChooserParams.getAcceptTypes());
			return true;
		}

		private void openFileChooserActivity(String... acceptTypes) {
			WebViewActivity activity = mWeakRef.get();
			if (activity == null) {
				return;
			}

			String acceptType = "image/*";
			if (acceptTypes != null && acceptTypes.length > 0) {
				acceptType = acceptTypes[0];
			}

			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType(acceptType);
			activity.startActivityForResult(Intent.createChooser(intent, "Chooser"), RC_CHOOSE_FILE);
		}

		private void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == RC_CHOOSE_FILE) {
				if (mUploadMultiFiles != null) {
					Uri[] uris = null;
					if (data != null && resultCode == Activity.RESULT_OK) {
						String dataString = data.getDataString();
						if (Build.VERSION.SDK_INT >= 16) {
							ClipData clipData = data.getClipData();
							if (clipData != null) {
								uris = new Uri[clipData.getItemCount()];
								for (int i = 0; i < clipData.getItemCount(); i++) {
									uris[i] = clipData.getItemAt(i).getUri();
								}
							}
						}
						if (dataString != null) {
							uris = new Uri[]{Uri.parse(dataString)};
						}
					}
					mUploadMultiFiles.onReceiveValue(uris);
					mUploadMultiFiles = null;
				} else if (mUploadFile != null) {
					Uri uri = (data == null || resultCode != RESULT_OK) ? null : data.getData();
					mUploadFile.onReceiveValue(uri);
					mUploadFile = null;
				}
			}
		}
	}

}
