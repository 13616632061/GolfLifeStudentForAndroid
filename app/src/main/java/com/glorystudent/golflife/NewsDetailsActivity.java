package com.glorystudent.golflife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.glorystudent.dialog.SharePopupWindow;
import com.glorystudent.entity.NewsEntity;
import com.glorystudent.entity.ShareModel;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.widget.PullToRefreshLayout;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * H5
 * Created by hyj on 2016/12/22.
 */
public class NewsDetailsActivity extends BaseActivity implements PlatformActionListener {
    private final static String TAG = "NewsDetailsActivity";
    @Bind(R.id.webview)
    public WebView webView;

    @Bind(R.id.pb)
    public ProgressBar pb;
    private SharePopupWindow share;
    private ShareModel model;
    private String url;
    private NewsEntity.ListnewsBean newsBean;

    @Bind(R.id.refresh_view)
    public PullToRefreshLayout refresh_view;
    private boolean isRefresh;//true 是下拉刷新， false 是上拉加载

    private int id;

    @Override
    protected int getContentId() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void init() {
        model = new ShareModel();
    }

    @Override
    protected void loadDatas() {
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //下拉刷新回调
                isRefresh = true;
                pb.setProgress(0);
                pb.setVisibility(View.VISIBLE);
                if (id != -1) {
                    url = String.format(Constants.NEWS_URL, id);
                    webView.loadUrl(url);
                }
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //上拉加载回调
                isRefresh = false;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        //设置缓存，否则后台重定向返回后导致缓存丢失
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //android5.0以上不显示图片的解决办法
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("print", "网页加载结束:--->" + url);
                super.onPageFinished(view, url);
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.SUCCEED);
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
                refresh_view.setRefreshState(isRefresh, PullToRefreshLayout.FAIL);
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
        Intent intent = getIntent();
        id = intent.getIntExtra("newsid", -1);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            newsBean = (NewsEntity.ListnewsBean) extras.getSerializable("newsBean");
            model.setImageUrl(newsBean.getNews_picture());
            model.setText(newsBean.getNews_subtitle());
            model.setTitle(newsBean.getNews_title());
            model.setUrl(String.format(Constants.NEWS_URL, id));
        }
        if (id != -1) {
            url = String.format(Constants.NEWS_URL, id);
            webView.loadUrl(url);
        }
    }

    @OnClick({R.id.back, R.id.iv_share})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.iv_share:
                if (newsBean != null && url != null) {
                    share = new SharePopupWindow(this);
                    share.setPlatformActionListener(this);
                    share.initShareParams(model);
                    share.showShareWindow();
                    // 显示窗口 (设置layout在PopupWindow中显示的位置)
                    share.showAtLocation(this.findViewById(R.id.back),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    // 设置背景颜色变暗
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 0.7f;
                    getWindow().setAttributes(lp);
                    share.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 1f;
                            getWindow().setAttributes(lp);
                        }
                    });
                }
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

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
