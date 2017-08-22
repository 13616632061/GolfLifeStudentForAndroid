package com.glorystudent.golflife;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 关于我们
 * Created by hyj on 2016/12/22.
 */
public class AboutUsActivity extends BaseActivity {
    @Bind(R.id.webview)
    public WebView webView;
    @Bind(R.id.pb)
    public ProgressBar pb;
    private String version;

    @Override
    protected int getContentId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init() {
        version = RequestUtil.getVersion(this);
    }

    @Override
    protected void loadDatas() {
        //webView设置
        WebSettings settings = webView.getSettings();
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        //字体urf-8
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        //webView的加载监听
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    pb.setVisibility(View.GONE);
                } else {
                    // 加载中
                    pb.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });


        //webView设置加载的url
        webView.loadUrl(String.format(Constants.ABOUT_US_URL, version));
    }

    @OnClick({R.id.back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
