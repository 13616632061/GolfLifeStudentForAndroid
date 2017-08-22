package com.glorystudent.golflife;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.RequestUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 更改昵称
 * Created by hyj on 2016/12/23.
 */
public class EditNicknameActivity extends BaseActivity {
    private final static String TAG = "EditNicknameActivity";
    @Bind(R.id.et_nickname)
    public EditText et_nickname;
    private String nicknameStr;

    @Override
    protected int getContentId() {
        return R.layout.activity_edit_nickname;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");
        et_nickname.setText(nickname);
    }

    @OnClick({R.id.back, R.id.tv_save})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                Intent intent = new Intent();
                intent.putExtra("nickname", SharedUtil.getString(Constants.NICKNAME));
                setResult(0x002, intent);
                finish();
                break;
            case R.id.tv_save:
                //保存
                nicknameStr = et_nickname.getText().toString();
                if(nicknameStr.isEmpty()){
                    Toast.makeText(EditNicknameActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    showWaiting();
                    String nicknameJson = "\"username\":" + "\"" + nicknameStr +"\"";
                    String editUserInfo = RequestUtil.getEditUserInfo(this, nicknameJson);
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
                                            SharedUtil.putString(Constants.NICKNAME, nicknameStr);
                                            EventBus.getDefault().post("nickname");
                                            Toast.makeText(EditNicknameActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(EditNicknameActivity.this, "保存失败，错误码:" + statusmessage, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    dismissWaiting();
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    dismissWaiting();
                                    Toast.makeText(EditNicknameActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }
}
