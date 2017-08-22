package com.glorystudent.golflife.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String orderId = SharedUtil.getString(Constants.ORDER_ID);
            String json = "\"wxpay\":{" + "\"order_id\":" + orderId + "}";
            String requestJson = RequestUtil.getJson(this, json);
            String url = "/api/APIWXPay/QueryWXPay";
            Log.i(TAG, "onResp: " + requestJson);
            Log.i(TAG, "onResp: " + Constants.BASE_URL + url);
            OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                @Override
                public void parseDatas(String json) {
                    try {
                        JSONObject jo = new JSONObject(json);
                        String statuscode = jo.getString("statuscode");
                        String statusmessage = jo.getString("statusmessage");
                        if (statuscode.equals("1")) {
                            if (statusmessage.equals("支付成功")) {
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetailActivity", "success"));
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetail3Activity", "success"));
                            } else if (statusmessage.equals("订单待支付")) {
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetailActivity", "failure"));
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetail3Activity", "failure"));
                            } else if (statusmessage.equals("订单信息不存在")) {
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetailActivity", "failure"));
                                EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetail3Activity", "failure"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void requestFailed() {
                    Toast.makeText(WXPayEntryActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetailActivity", "failure"));
                    EventBus.getDefault().post(EventBusMapUtil.getStringMap("EventDetail3Activity", "failure"));
                }
            }).getEntityData(this, url, requestJson);

            //不能使用客户端返回值判断是否支付成功，应该从服务器获取支付状态
//            if (resp.errCode == 0) {
//                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
//            }
            finish();
        }
    }
}