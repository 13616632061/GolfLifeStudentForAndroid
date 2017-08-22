package com.glorystudent.golflife;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.PhoneFormatCheckUtils;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/3.
 */

public class EfficientCodeActivity extends BaseActivity {
    private static final String TAG = "EfficientCodeActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et_efficient_code)
    EditText efficientCode;
    @Bind(R.id.tv_sure)
    TextView sure;
    private int eventactivity_id;

    @Override
    protected int getContentId() {
        return R.layout.activity_efficient_code;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        eventactivity_id = intent.getIntExtra("id", -1);
    }

    @OnClick({R.id.back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_sure:
                String codeStr = efficientCode.getText().toString().trim();
                if (codeStr.isEmpty()) {
                    Toast.makeText(this, "有效码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (codeStr.length() == 11) {
                        //手机号
                        if (!PhoneFormatCheckUtils.isPhoneLegal(codeStr)) {
                            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                        } else {
                            signUpByPhone(codeStr);
                        }
                    } else {
                        //凭证号
                        signUpByVoucher(codeStr);
                    }
                }
                break;
        }
    }

    /**
     * 凭证号签到
     *
     * @param codeStr
     */
    private void signUpByVoucher(String codeStr) {
        showLoading();
        String json = "\"signup\":{" + "\"sign_activitiesid\":" + eventactivity_id + "," + "\"sign_voucher\":"
                + "\"" + codeStr + "\"" + "}";
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "signUpByVoucher: " + requestJson);
        String url = "/api/APISignUp/SweepCodeSignUp";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(EfficientCodeActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EfficientCodeActivity.this, statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
                finish();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EfficientCodeActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 手机号签到
     *
     * @param codeStr
     */
    private void signUpByPhone(String codeStr) {
        showLoading();
        String json = "\"signup\":{" + "\"sign_activitiesid\":" + eventactivity_id + "," + "\"sign_phone\":"
                + "\"" + codeStr + "\"" + "}";
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "signUpByPhone: " + requestJson);
        String url = "/api/APISignUp/SweepCodeSignUp";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        dismissLoading();
                        Toast.makeText(EfficientCodeActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(EfficientCodeActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }
}
