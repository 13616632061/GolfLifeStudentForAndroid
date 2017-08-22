package com.glorystudent.golflife;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lucy on 2017/7/21.
 * 测评结果
 */
public class EvaluationResultActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.actionbar)
    LinearLayout actionbar;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.btn_add)
    Button btnAdd;
    @Bind(R.id.bt_back)
    Button btBack;
    private String url;
    private String json;
    private String type;


    @Override
    protected int getContentId() {
        return R.layout.activity_evaluationresult;
    }

    @Override
    protected void init() {
        type = getIntent().getStringExtra("type");

        //webView设置
        WebSettings settings = webview.getSettings();
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        //字体urf-8
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        //webView的加载监听
        webview.setWebViewClient(new WebViewClient() {
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
        webview.setWebChromeClient(new WebChromeClient() {
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
    }

    @OnClick({R.id.back, R.id.bt_back, R.id.btn_add})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.bt_back:
                //返回
                finish();
                break;
            case R.id.btn_add:
                //保存
                String request = RequestUtil.getRequestJson(this, json);
                Log.i("json", request);
                new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, "/api/APITests/AddTests", request);
                break;
        }
    }


    @Override
    protected void loadDatas() {
        switch (type) {
            case "TPI":
                url = getIntent().getStringExtra("url");
                json = getIntent().getStringExtra("json");
                //webView设置加载的url
                webview.loadUrl(Constants.BASE_URL + "/ActivityAPP/TestTPI?testJson=" + url);
                break;
            case "test":
                btnAdd.setVisibility(View.GONE);
                btBack.setVisibility(View.GONE);
                int test_id = getIntent().getIntExtra("test_id",0);
                webview.loadUrl(Constants.BASE_URL + "/ActivityAPP/TestTPI?id=" + test_id);
                Log.i("eeee",Constants.BASE_URL + "/ActivityAPP/TestTPI?id=" + test_id);
                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void parseDatas(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int statuscode = (int) jsonObject.opt("statuscode");
            Log.i("sdsssss", statuscode + "");
            //返回1 成功时跳转下个界面
            if (statuscode == 1) {
                finish();
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void requestFailed() {

    }
}
