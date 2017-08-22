package com.glorystudent.golflife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.util.Constants;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 赛事详情，从已发布活动进入时
 * Created by Gavin.J on 2017/5/9.
 */

public class EventDetail2Activity extends BaseActivity {
    private final static String TAG = "EventDetail2Activity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.rl_bottom_edit)
    RelativeLayout bottomEdit;
    private String weburl;
    private int eventActivity_id;
    private String state;
    // 荣耀服务的url
    private static final String HONOR_SERVER_URL = "home/license";
    // 拨号url
    private static final String CALL_TEL_URL = "tel:";
    // 保存相册url
    private static final String SAVE_TO_PHOTO_ALBUMS_URL = "isAndroid:";

    @Override
    protected int getContentId() {
        return R.layout.activity_event_detail2;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        eventActivity_id = intent.getIntExtra("id", -1);
        state = intent.getStringExtra("state");
        if (state.equals("4")) {
            bottomEdit.setVisibility(View.GONE);
        } else {
            bottomEdit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadDatas() {
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
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(CALL_TEL_URL)) {
                    //电话
                    final String phoneStr = url.substring(4);
                    new AlertDialog(EventDetail2Activity.this)
                            .builder()
                            .setTitle(phoneStr)
                            .setCancelable(true)
                            .setPositiveButton("拨号", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    requestPermission(new String[]{android.Manifest.permission.CALL_PHONE}, true, new PermissionsResultListener() {
                                        @Override
                                        public void onPermissionGranted() {
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_CALL);
                                            intent.setData(Uri.parse("tel:" + phoneStr));
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onPermissionDenied() {
                                            Toast.makeText(EventDetail2Activity.this, "没有拨号权限", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", null).show();
                    return true;
                } else if (url.contains(HONOR_SERVER_URL)) {
                    //荣耀服务条款
                    startActivity(new Intent(EventDetail2Activity.this, ServiceTermsActivity.class));
                    return true;
                } else if (url.contains(SAVE_TO_PHOTO_ALBUMS_URL)) {
                    //保存到相册
                    Log.i(TAG, "shouldOverrideUrlLoading: " + url);
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
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

        if (eventActivity_id != -1) {
            weburl = String.format(Constants.EVENT_PREVIEW_URL, eventActivity_id);
            webView.loadUrl(weburl);
        }
    }

    @OnClick({R.id.back, R.id.rl_bottom_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_bottom_edit:
                //编辑
//                    Map<String, EventCompetityEntity.ListeventactivityBean> map = new HashMap<>();
//                    map.put("EventReleasedActivity", datas);
//                    EventBus.getDefault().postSticky(map);
                Intent intent = new Intent(this, EventReleasedActivity.class);
                intent.putExtra("id", eventActivity_id);
                startActivity(intent);
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

//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void getEvent(Map<String, EventCompetityEntity.ListeventactivityBean> map) {
//        if (map.containsKey("EventDetail2Activity")) {
//            EventCompetityEntity.ListeventactivityBean data = map.get("EventDetail2Activity");
//            this.data = data;
//        }
//    }
}
