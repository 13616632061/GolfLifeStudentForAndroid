package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.glorystudent.entity.ApplyFriendsEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 验证信息
 * Created by hyj on 2017/2/6.
 */
public class VerifyInformationActivity extends BaseActivity {
    private final static String TAG = "VerifyInfoActivity";
    @Bind(R.id.et_hello)
    public EditText etHello;

    private int userid;
    private String applyType;

    @Override
    protected int getContentId() {
        return R.layout.activity_validation_information;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", -1);
        applyType = intent.getStringExtra("applyType");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(etHello, 0);
                }
            }
        }, 200);
    }

    @OnClick({R.id.back, R.id.tv_send})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_send:
                //发送
                sendApplyFriend();
                break;
        }
    }

    public void sendApplyFriend() {
        showLoading();
        String text = etHello.getText().toString().trim();
        ApplyFriendsEntity applyFriendsEntity = new ApplyFriendsEntity();
        ApplyFriendsEntity.ApplyFriendsBean applyFriendsBean = new ApplyFriendsEntity.ApplyFriendsBean();
        applyFriendsBean.setApplyfriends_type(applyType);
//        applyFriendsBean.setApplystatus("0");
//        applyFriendsBean.setApplytype("1");
        applyFriendsBean.setAnsweruserid(userid);
        applyFriendsBean.setApplyremark(text);
        applyFriendsEntity.setApplyfriends(applyFriendsBean);
//        applyFriendsEntity.setUsername(phoneName);
//        applyFriendsEntity.setUsertel(phoneNumber);
        String json = new Gson().toJson(applyFriendsEntity);
        String requestJson = RequestUtil.getRequestJson(this, json);
        String url = "/api/APIApplyFriends/AddApplyFriends";
        Log.i(TAG, "sendApplyFriend: " + requestJson);
        Log.i(TAG, "sendApplyFriend: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(VerifyInformationActivity.this, "添加好友成功，请等待对方同意", Toast.LENGTH_SHORT).show();
                        //参数为要添加的好友的username和添加理由
//                        try {
//                            EMClient.getInstance().contactManager().addContact(pNumber, tvHello);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        finish();
                    } else {
                        Toast.makeText(VerifyInformationActivity.this, "添加失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(VerifyInformationActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

}
