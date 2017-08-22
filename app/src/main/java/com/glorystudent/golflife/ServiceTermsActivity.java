package com.glorystudent.golflife;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
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

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 服务条款
 * Created by hyj on 2016/12/22.
 */
public class ServiceTermsActivity extends BaseActivity {
    @Bind(R.id.webview)
    public WebView webView;
    @Bind(R.id.pb)
    public ProgressBar pb;

    @Override
    protected int getContentId() {
        return R.layout.activity_service_terms;
    }
    @Override
    protected void loadDatas() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("print", "网页加载结束:--->" + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("print", "开始加载连接:---->" + url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("print", "请求链接的url---->");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
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

        webView.loadUrl(Constants.SERVICE_TERMS);
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
