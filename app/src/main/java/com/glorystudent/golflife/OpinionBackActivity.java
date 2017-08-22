package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 意见反馈
 * Created by hyj on 2016/12/22.
 */
public class OpinionBackActivity extends BaseActivity implements OkGoRequest.OnOkGoUtilListener {
    private final static String TAG = "OpinionBackActivity";
    @Bind(R.id.et_opinion)
    public EditText et_opinion;
    @Bind(R.id.tv_nickname)
    public TextView tv_nickname;
    @Override
    protected int getContentId() {
        return R.layout.activity_opinion_back;
    }

    @Override
    protected void init() {
        tv_nickname.setText(SharedUtil.getString(Constants.NICKNAME));
    }

    @OnClick({R.id.back, R.id.btn_submit})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.btn_submit:
                //提交

                String opinionStr = et_opinion.getText().toString();
                if (opinionStr != null) {
                    String url = "/api/APIFeedBack/AddFeedBack";
                    String request = RequestUtil.getOpinion(this, opinionStr);
                    showWaiting();
                    new OkGoRequest().setOnOkGoUtilListener(this).getEntityData(this, url, request);
                }else{
                    Toast.makeText(OpinionBackActivity.this, "请填写您的意见", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void parseDatas(String json) {
        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            String statuscode = jo.getString("statuscode");
            String statusmessage = jo.getString("statusmessage");
            if (statuscode.equals("-114")) {
                Toast.makeText(OpinionBackActivity.this, statusmessage + "，请重新登陆", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OpinionBackActivity.this, LoginActivity.class));
                finish();
            } else if(statuscode.equals("1")){
                et_opinion.setText("");
                Toast.makeText(OpinionBackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OpinionBackActivity.this, "未知原因，提交失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dismissWaiting();
    }

    @Override
    public void requestFailed() {
        dismissWaiting();
        Toast.makeText(OpinionBackActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
    }
}
