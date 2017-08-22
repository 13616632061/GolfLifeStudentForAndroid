package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.UserEntity;
import com.glorystudent.entity.WxLoginRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.PhoneFormatCheckUtils;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;



import butterknife.Bind;
import butterknife.OnClick;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 绑定手机号
 * Created by hyj on 2017/3/6.
 */
public class BindPhoneNumberActivity extends BaseActivity implements TextWatcher {
    private final static String TAG = "LoginActivity";

    @Bind(R.id.et_phone_number)
    public EditText et_phone_number;

    @Bind(R.id.et_phone_code)
    public EditText et_phone_code;

    @Bind(R.id.tv_get_code)
    public TextView tv_get_code;



    private boolean isPhoneNumber = false;//false不是合格手机号码，true是合格手机号
    private UserEntity userEntity;//用户实体类
    private final static int COUNTDOWN = 0x001; //获取验证码倒计时
    private int time = 60;//验证码过期倒数60秒
    private boolean isCheckAgree = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNTDOWN:
                    if(time <= 0){
                        time = 60;
                        tv_get_code.setEnabled(true);
                        tv_get_code.setText(R.string.login_text3);
                        GradientDrawable myGrad = (GradientDrawable)tv_get_code.getBackground();
                        myGrad.setStroke(2,getResources().getColor(R.color.colorOrange));
                        tv_get_code.setTextColor(getResources().getColor(R.color.colorOrange));
                    }else{
                        tv_get_code.setEnabled(false);
                        tv_get_code.setText(time+"s重新获取");
                        GradientDrawable myGrad = (GradientDrawable)tv_get_code.getBackground();
                        myGrad.setStroke(2,getResources().getColor(R.color.colorGray11));
                        tv_get_code.setTextColor(getResources().getColor(R.color.colorGray11));
                        time--;
                        handler.sendEmptyMessageDelayed(COUNTDOWN, 1000);
                    }
                    break;
            }
        }
    };
    private String openid;


    /**
     * 点击EditText其他地方，隐藏软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO 绑定布局
     * 绑定布局
     * @return
     */
    @Override
    protected int getContentId() {
        return R.layout.activity_bind_phonenumber;
    }

    /**
     * TODO 初始化控件
     * 初始化控件
     */
    @Override
    protected void init() {
        Intent intent = getIntent();
        openid = intent.getStringExtra("openid");

        //已经登录，则跳转主页
        if(SharedUtil.getBoolean(Constants.LOGIN_STATE)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        //手机号码输入的监听事件
        et_phone_number.addTextChangedListener(this);

    }

    /**
     * TODO 监听输入手机号码
     * 监听输入手机号码
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isPhoneNumber = false;//重置状态
        String strPhone = s.toString();
        if(s.length() >= 8){
            if(PhoneFormatCheckUtils.isChinaPhoneLegal(strPhone)){
                isPhoneNumber = true;
            }
        }
    }

    /**
     * TODO 点击事件的监听
     * 点击事件的监听
     * @param v
     */
    @OnClick({R.id.back,R.id.tv_get_code, R.id.btn_login})
    public void onclick(View v){
        switch (v.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_get_code:
                //获取手机验证码
                if(!isPhoneNumber){
                    Toast.makeText(BindPhoneNumberActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }else{
                    //获取虚拟短信验证码接口
                    handler.sendEmptyMessage(COUNTDOWN);
                    String getCode = RequestUtil.getSMSCheck(this, et_phone_number.getText().toString());
                    OkGo.post(Constants.BASE_URL+"/api/APISMSCheck/GetSMSCheck")
                            .tag(this)
                            .params("request", getCode)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {

                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    Toast.makeText(BindPhoneNumberActivity.this, "请检查网络，无法连接服务器", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                break;
            case R.id.btn_login:
                //登陆
                if(et_phone_number.getText().toString().isEmpty()){
                    Toast.makeText(BindPhoneNumberActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if(et_phone_code.getText().toString().isEmpty()){
                    Toast.makeText(BindPhoneNumberActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    //进行注册登陆
                    WxLoginRequestEntity wxLoginRequestEntity = new WxLoginRequestEntity();
                    WxLoginRequestEntity.WxLoginBean wxLoginBean = new WxLoginRequestEntity.WxLoginBean();
                    wxLoginBean.setOpenid(openid);
                    wxLoginRequestEntity.setWxlogin(wxLoginBean);
                    wxLoginRequestEntity.setPhonenum(et_phone_number.getText().toString());
                    wxLoginRequestEntity.setPhonencode(et_phone_code.getText().toString());
                    String request = new Gson().toJson(wxLoginRequestEntity);
                    String requestJson = RequestUtil.getRequestJson(this, request);
                    String url = "/api/APIWXLogin/BindingWX";
                    OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                        @Override
                        public void parseDatas(String json) {
                            try {
                                JSONObject jo = new JSONObject(json);
                                userEntity = new Gson().fromJson(jo.toString(), UserEntity.class);
                                if(userEntity.getStatuscode() == 1){
                                    saveSharedPreferences(userEntity);//保存参数
                                    LoginHuanxin();
                                    startActivity(new Intent(BindPhoneNumberActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(BindPhoneNumberActivity.this, "绑定失败，错误码:" + userEntity.getStatusmessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void requestFailed() {

                        }
                    }).getEntityData(this, url, requestJson);
                }
                break;
        }
    }
    private void saveSharedPreferences(UserEntity userEntity){
        //保存参数
        SharedUtil.putBoolean(Constants.LOGIN_STATE, true);
        SharedUtil.putString(Constants.USER_ID, userEntity.getUserid());
        SharedUtil.putInt(Constants.NUMBER_ID, userEntity.getUserinfo().getUserid());
        SharedUtil.putString(Constants.GROUP_ID, userEntity.getGroupid());
        SharedUtil.putString(Constants.ACCESS_TOKEN, userEntity.getAccesstoken());
        SharedUtil.putString(Constants.PHONE_NUMBER, userEntity.getUserinfo().getPhonenumber());
        SharedUtil.putString(Constants.HEAD_PORTRAIT, (String) userEntity.getUserinfo().getCustomerphoto());
        SharedUtil.putString(Constants.NICKNAME, (String) userEntity.getUserinfo().getUsername());
        SharedUtil.putString(Constants.SEX, userEntity.getUserinfo().getGender());
        SharedUtil.putString(Constants.VETERAN, (int) userEntity.getUserinfo().getGolfage() + "");
        SharedUtil.putString(Constants.ADDRESS, (String) userEntity.getUserinfo().getChinacity_name());
        String usertype = userEntity.getUserinfo().getUsertype();
        SharedUtil.putString(Constants.USER_TYPE, usertype);
        if(usertype.equals("1")){
            SharedUtil.putString(Constants.ATTESTATION_STATE, "1");//教练认证状态
            SharedUtil.putString(Constants.SWITCH_VERSION, "教练端");
        }else if(usertype.equals("0")){
            SharedUtil.putString(Constants.ATTESTATION_STATE, "0");//教练认证状态
            SharedUtil.putString(Constants.SWITCH_VERSION, "客户端");
        } else if (usertype.equals("8")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "2");//教练认证状态
            SharedUtil.putString(Constants.SWITCH_VERSION, "客户端");
        }
    }

    private void LoginHuanxin(){
        String phonenumber = userEntity.getUserinfo().getPhonenumber();
        String password = "123456";
        EMClient.getInstance().login(phonenumber, password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }
}
