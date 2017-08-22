package com.glorystudent.golflife;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.dialog.SharePopupWindow;
import com.glorystudent.entity.ShareModel;
import com.glorystudent.entity.WxPayOrderEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.dialog.iosdialog.AlertDialog;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.PhoneIpUtil;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 从赛事活动也进入时活动详情
 * Created by hyj on 2016/12/21.
 */
public class EventDetailActivity extends BaseActivity implements PlatformActionListener {
    private final static String TAG = "EventDetailActivity";
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_share)
    TextView tvShare;
    private int id;
    private String weburl;
    private ShareModel model;
    private SharePopupWindow share;
    // 支付的url
    private static final String ORDER_URL = "http://www.pgagolf.cn/?OrderID";
    // 荣耀服务的url
    private static final String HONOR_SERVER_URL = "home/license";
    // 我的活动url
    private static final String MY_COMPETITION_URL = "Activityapp/MyActivityList";
    // 拨号url
    private static final String CALL_TEL_URL = "tel:";
    // 保存相册url
    private static final String SAVE_TO_PHOTO_ALBUMS_URL = "isAndroid:";
    // 跳到我的报名页
    private static final String JUMP_TO_SIGN_UP_URL = "Activity/MyActivityList";

    private LinkedList<String> loadHistoryUrls;
    private String orderId;

    @Override
    protected int getContentId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String detail = intent.getStringExtra("detail");
        String imageUrl = intent.getStringExtra("imageUrl");

        loadHistoryUrls = new LinkedList<>();

        model = new ShareModel();
        model.setImageUrl(imageUrl);
        model.setText(detail);
        model.setTitle(name);
        model.setUrl(String.format(Constants.EVENT_SHARE_URL, id));
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
                Log.i(TAG, "onPageFinished: " + url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadHistoryUrls.add(url);
                Log.i(TAG, "onPageStarted: --->" + url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //接收所有证书
                handler.proceed();
//                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading:  旧--->" + url);
                if (url.contains(MY_COMPETITION_URL)) {
                    //我的活动
                    startActivity(new Intent(EventDetailActivity.this, MyEventActivity.class));
                    return true;
                } else if (url.contains(CALL_TEL_URL)) {
                    //电话
                    final String phoneStr = url.substring(4);
                    callPhone(phoneStr);
                    return true;
                } else if (url.contains(HONOR_SERVER_URL)) {
                    //荣耀服务条款
                    startActivity(new Intent(EventDetailActivity.this, ServiceTermsActivity.class));
                    return true;
                } else if (url.contains(SAVE_TO_PHOTO_ALBUMS_URL)) {
                    //保存到相册
                    Log.i(TAG, "shouldOverrideUrlLoading: " + url);
                    return true;
                } else if (url.contains(JUMP_TO_SIGN_UP_URL)) {
                    //返回我的参加的活动
                    startActivity(new Intent(EventDetailActivity.this, MyEventActivity.class));
                    finish();
                    return true;
                } else if (url.contains(ORDER_URL)) {
                    //支付
                    orderId = url.substring(url.indexOf("=") + 1);
                    Log.i(TAG, "shouldOverrideUrlLoading: " + orderId);
                    accessOrder();
                    return true;
                } else {

                }
                return super.shouldOverrideUrlLoading(view, url);
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
                    pb.setVisibility(View.VISIBLE);
                    pb.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        if (id != -1) {
            weburl = String.format(Constants.EVENT_DETAIL_URL, id, SharedUtil.getString(Constants.USER_ID));
            webView.loadUrl(weburl);
        }
    }

    /**
     * 获取微信统一下单
     */
    private void accessOrder() {
        showLoading();
        String hostIP = PhoneIpUtil.getHostIP();
        String json = "\"wxpay\":{" + "\"order_id\":" + orderId + ",\"clientip\":\"" + hostIP + "\"}";
        String requestJson = RequestUtil.getJson(this, json);
        String url = "/Activity/WXPayAPP";
        Log.i(TAG, "accessOrder: " + requestJson);
        Log.i(TAG, "accessOrder: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                dismissLoading();
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        WxPayOrderEntity wxPayOrderEntity = new Gson().fromJson(jo.toString(), WxPayOrderEntity.class);
                        if (wxPayOrderEntity != null) {
                            final IWXAPI msgApi = WXAPIFactory.createWXAPI(EventDetailActivity.this, Constants.WX_APPID);
                            // 将该app注册到微信
                            msgApi.registerApp(Constants.WX_APPID);
                            PayReq request = new PayReq();
                            request.appId = wxPayOrderEntity.getAppId();
                            request.partnerId = wxPayOrderEntity.getPartnerid();
                            request.prepayId = wxPayOrderEntity.getPrepayid();
                            request.packageValue = wxPayOrderEntity.getPackageX();
                            request.nonceStr = wxPayOrderEntity.getNonceStr();
                            request.timeStamp = wxPayOrderEntity.getTimeStamp();
                            request.sign = wxPayOrderEntity.getPaySign();
                            msgApi.sendReq(request);
                            SharedUtil.putString(Constants.ORDER_ID, orderId);
                        }
                    } else if (statuscode.equals("2")) {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EventDetailActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 拨打电话
     *
     * @param phoneStr
     */
    private void callPhone(final String phoneStr) {
        new AlertDialog(EventDetailActivity.this)
                .builder()
                .setTitle(phoneStr)
                .setCancelable(true)
                .setPositiveButton("拨号", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermission(new String[]{Manifest.permission.CALL_PHONE}, true, new PermissionsResultListener() {
                            @Override
                            public void onPermissionGranted() {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + phoneStr));
                                startActivity(intent);
                            }

                            @Override
                            public void onPermissionDenied() {
                                Toast.makeText(EventDetailActivity.this, "没有拨号权限", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @OnClick({R.id.back, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                close();
                break;
            case R.id.tv_share:
                if (weburl != null) {
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

    private void close() {
        Log.i(TAG, "close: " + webView.canGoBack());
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventActivity", "refresh"));
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (webView.canGoBack()) {
//                webView.goBack();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Map<String, String> map) {
        if (map.containsKey("EventDetailActivity")) {
            String orderId = SharedUtil.getString(Constants.ORDER_ID);
            String url = Constants.BASE_URL + "/ActivityAPP/ActivitiesCredence?OrderId=" + orderId;
            webView.loadUrl(url);
            if (map.get("EventDetailActivity").equals("success")) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            } else if (map.get("EventDetailActivity").equals("failure")) {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
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
