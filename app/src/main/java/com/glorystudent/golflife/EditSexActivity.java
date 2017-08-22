package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 更改性别
 * Created by hyj on 2016/12/23.
 */
public class EditSexActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.rg_sex)
    public RadioGroup rg_sex;
    private String sexcode;

    @Override
    protected int getContentId() {
        return R.layout.activity_edit_sex;
    }

    @Override
    protected void init() {
        rg_sex.setOnCheckedChangeListener(this);
        String sex = SharedUtil.getString(Constants.SEX);//1代表男  2代表女
        if(sex.equals("1")){
            rg_sex.getChildAt(0).performClick();
        }else{
            rg_sex.getChildAt(1).performClick();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = (RadioButton) findViewById(checkedId);
        String sex = rb.getText().toString();
        //1代表男  2代表女
        sexcode = null;

        switch (sex) {
            case "男":
                sexcode = "1";
                break;
            case "女":
                sexcode = "2";
                break;
        }
        //当选择的性别与共享文件参数的值不一样才发送信息给服务器
        if(!(sexcode != null && sexcode.equals(SharedUtil.getString(Constants.SEX)))){
            showWaiting();
            String sexJson = "\"gender\":" + "\"" + sexcode +"\"";
            String editUserInfo = RequestUtil.getEditUserInfo(this, sexJson);
            OkGo.post(Constants.BASE_URL+"/api/APIUser/EditUser")
                    .tag(this)//
                    .params("request", editUserInfo)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String statuscode = jo.getString("statuscode");
                                String statusmessage = jo.getString("statusmessage");
                                if(statuscode.equals("1")){
                                    SharedUtil.putString(Constants.SEX, sexcode);
                                    Toast.makeText(EditSexActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(EditSexActivity.this, "保存失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dismissWaiting();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            dismissWaiting();
                            Toast.makeText(EditSexActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @OnClick(R.id.back)
    public void onclick(View view) {
        Intent intent = new Intent();
        intent.putExtra("sex", SharedUtil.getString(Constants.SEX));
        setResult(0x004, intent);
        finish();
    }

}
