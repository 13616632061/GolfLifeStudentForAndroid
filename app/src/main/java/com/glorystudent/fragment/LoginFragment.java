package com.glorystudent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.UserEntity;
import com.glorystudent.entity.UserInformationEntity;
import com.glorystudent.entity.UserRequestEntity;
import com.glorystudent.entity.response.ResponseSMSCodeEntity;
import com.glorystudent.golflibrary.base.BaseFragment;
import com.glorystudent.golflibrary.util.PhoneFormatCheckUtils;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.golflife.BindPhoneNumberActivity;
import com.glorystudent.golflife.MainActivity;
import com.glorystudent.golflife.R;
import com.glorystudent.golflife.ServiceTermsActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hyj on 2017/3/27.
 */
public class LoginFragment extends BaseFragment implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    public static LoginFragment getInstance(int position) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("version", position);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Bind(R.id.et_phone_number)
    public EditText et_phone_number;

    @Bind(R.id.et_phone_code)
    public EditText et_phone_code;

    @Bind(R.id.tv_get_code)
    public TextView tv_get_code;

    @Bind(R.id.cb_agree)
    public CheckBox cb_agree;
    private OnekeyShare oks;

    private boolean isPhoneNumber = false;//false不是合格手机号码，true是合格手机号
    private UserEntity userEntity;//用户实体类
    private final static int COUNTDOWN = 0x001; //获取验证码倒计时
    private final static int LOGIN_SUCCEED = 0x002;//微信登陆成功
    private final static int LOGIN_FAILURE = 0x003;
    private int time = 60;//验证码过期倒数60秒
    private boolean countDownFlag = true;
    private boolean isCheckAgree = false;
    private String userid;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNTDOWN:
                    //定时器
                    if (time <= 0) {
                        time = 60;
                        tv_get_code.setEnabled(true);
                        tv_get_code.setText(R.string.login_text3);
                        tv_get_code.setTextColor(getResources().getColor(R.color.colorOrange));
                    } else {
                        if (countDownFlag) {
                            tv_get_code.setEnabled(false);
                            tv_get_code.setText(time + "s重新获取");
                            tv_get_code.setTextColor(getResources().getColor(R.color.colorGray11));
                            time--;
                            handler.sendEmptyMessageDelayed(COUNTDOWN, 1000);
                        }
                    }
                    break;
                case LOGIN_SUCCEED:
                    //登陆成功
                    Log.d("print", "getEventBus: 点击了微信登陆1");
                    openid = (String) map.get("openid");
                    UserRequestEntity userRequestEntity = new UserRequestEntity();
                    UserRequestEntity.UserBean userBean = new UserRequestEntity.UserBean();
                    userBean.setOpenid(openid);
                    userRequestEntity.setUser(userBean);
                    String request = new Gson().toJson(userRequestEntity);
                    String requestJson = RequestUtil.getRequestJson(getActivity(), request);
                    String url = "/Public/APIPublicUser/QueryUser";
                    OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
                        @Override
                        public void parseDatas(String json) {
                            try {
                                Log.d("print", "getEventBus: 点击了微信登陆"+json);
                                JSONObject jo = new JSONObject(json);
                                String statuscode = jo.getString("statuscode");
                                String statusmessage = jo.getString("statusmessage");
                                if (statuscode.equals("1")) {
                                    UserInformationEntity userInformationEntity = new Gson().fromJson(jo.toString(), UserInformationEntity.class);
                                    if (userInformationEntity != null) {
                                        List<UserInformationEntity.ListUsersBean> listUsers = userInformationEntity.getListUsers();
                                        if (listUsers != null) {
                                            saveUserListSharedPreferences(userInformationEntity);//保存参数
                                            loginUserListHuanxin(userInformationEntity);
                                            startActivity(new Intent(getActivity(), MainActivity.class));
                                            getActivity().finish();
                                        } else {
                                            Intent intent = new Intent(getActivity(), BindPhoneNumberActivity.class);
                                            intent.putExtra("openid", openid);
                                            startActivity(intent);
                                        }
                                    }
                                } else if (statuscode.equals("2")) {
                                    Intent intent = new Intent(getActivity(), BindPhoneNumberActivity.class);
                                    intent.putExtra("openid", openid);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void requestFailed() {

                        }
                    }).getEntityData(getActivity(), url, requestJson);
                    break;
                case LOGIN_FAILURE:
                    //登陆失败
                    Toast.makeText(getActivity(), "微信登陆失败，请重新登陆", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private HashMap<String, Object> map;
    private String openid;


    @Override
    protected int getContentId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    /**
     * TODO 初始化控件
     * 初始化控件
     */
    @Override
    protected void init(View view) {
        //已经登录，则跳转主页
        EventBus.getDefault().register(this);
        if (SharedUtil.getBoolean(Constants.LOGIN_STATE)) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        //手机号码输入的监听事件
        et_phone_number.addTextChangedListener(this);
        cb_agree.setOnCheckedChangeListener(this);
        cb_agree.setChecked(true);
    }


    /**
     * TODO 监听输入手机号码
     * 监听输入手机号码
     *
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
        if (s.length() >= 8) {
            if (PhoneFormatCheckUtils.isChinaPhoneLegal(strPhone)) {
                isPhoneNumber = true;
            }
        }
    }

    /**
     * TODO 点击事件的监听
     * 点击事件的监听
     *
     * @param v
     */
    @OnClick({R.id.tv_get_code, R.id.btn_login, R.id.service_terms})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_code:
                //获取手机验证码
                if (!isPhoneNumber) {
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    //获取虚拟短信验证码接口
                    handler.sendEmptyMessage(COUNTDOWN);
                    String getCode = RequestUtil.getSMSCheck(getActivity(), et_phone_number.getText().toString());
                    OkGo.post(Constants.BASE_URL + "/api/APISMSCheck/GetSMSCheck")
                            .tag(this)
                            .params("request", getCode)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    //获取虚拟验证码
                                    // TODO: 17-8-2 废弃接口
//                                    OkGo.post(Constants.BASE_URL + "/api/APISMSCheck/GetVituralSMSCheckCode")
//                                            .tag(this)//
//                                            .params("PhoneNumber", et_phone_number.getText().toString())
//                                            .execute(new StringCallback() {
//                                                @Override
//                                                public void onSuccess(String s, Call call, Response response) {
//                                                    if (s != null && s.length() == 6) {
//                                                        et_phone_code.setText(s);
//                                                    }
//                                                }
//                                            });
                                    System.out.println("短信验证码:"+s);
                                    if(!TextUtils.isEmpty(s)){
                                        ResponseSMSCodeEntity responseSMSCodeEntity=new Gson().fromJson(s,ResponseSMSCodeEntity.class);
                                        if(responseSMSCodeEntity.getStatuscode()==1){
//                                            et_phone_code.setText(responseSMSCodeEntity.getStatusmessage());
                                        }else {
                                            Toast.makeText(getActivity(), responseSMSCodeEntity.getStatusmessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    Toast.makeText(getActivity(), "请检查网络，无法连接服务器", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                break;
            case R.id.btn_login:
                //登陆
                if (et_phone_number.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (et_phone_code.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!isCheckAgree) {
                    Toast.makeText(getActivity(), "请同意服务条款", Toast.LENGTH_SHORT).show();
                } else {
                    //进行注册登陆
                    userEntity = new UserEntity();
                    String json = RequestUtil.getLogin(getActivity(), et_phone_number.getText().toString(), et_phone_code.getText().toString());
                    OkGo.post(Constants.BASE_URL + "/api/APISMSLogin/SMSLogin")
                            .tag(this)//
                            .params("request", json)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jo = new JSONObject(s);
                                        userEntity = new Gson().fromJson(jo.toString(), UserEntity.class);
                                        if (userEntity.getStatuscode() == 1) {
                                            saveSharedPreferences(userEntity);//保存参数
                                            loginHuanxin();
                                            startActivity(new Intent(getActivity(), MainActivity.class));
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getActivity(), "错误码:" + userEntity.getStatusmessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                }
                break;
            case R.id.service_terms:
                //服务条款
                startActivity(new Intent(getActivity(), ServiceTermsActivity.class));
                break;
        }
    }

    private void saveSharedPreferences(UserEntity userEntity) {
        //保存参数
        SharedUtil.putBoolean(Constants.LOGIN_STATE, true);
        SharedUtil.putString(Constants.USER_ID, userEntity.getUserid());
        SharedUtil.putInt(Constants.NUMBER_ID, userEntity.getUserinfo().getUserid());
        SharedUtil.putString(Constants.GROUP_ID, userEntity.getGroupid());
        Log.d("login", "saveSharedPreferences: 1234--->" + userEntity.getGroupid());
        SharedUtil.putString(Constants.ACCESS_TOKEN, userEntity.getAccesstoken());
        SharedUtil.putString(Constants.PHONE_NUMBER, userEntity.getUserinfo().getPhonenumber());
        SharedUtil.putString(Constants.HEAD_PORTRAIT, (String) userEntity.getUserinfo().getCustomerphoto());
        SharedUtil.putString(Constants.NICKNAME, (String) userEntity.getUserinfo().getUsername());
        SharedUtil.putString(Constants.SEX, userEntity.getUserinfo().getGender());
        SharedUtil.putString(Constants.VETERAN, (int) userEntity.getUserinfo().getGolfage() + "");
        SharedUtil.putString(Constants.ADDRESS, (String) userEntity.getUserinfo().getChinacity_name());
        String usertype = userEntity.getUserinfo().getUsertype();
        SharedUtil.putString(Constants.USER_TYPE, usertype);
        if (usertype.equals("1")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "1");//教练认证状态
        } else if (usertype.equals("0")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "0");//教练认证状态
        } else if (usertype.equals("8")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "2");//教练认证状态
        }

    }

    private void saveUserListSharedPreferences(UserInformationEntity userEntity) {
        //保存参数
        SharedUtil.putBoolean(Constants.LOGIN_STATE, true);
        SharedUtil.putString(Constants.USER_ID, userEntity.getUserid() + "");
        SharedUtil.putInt(Constants.NUMBER_ID, userEntity.getListUsers().get(0).getUserid());
        SharedUtil.putString(Constants.GROUP_ID, userEntity.getGroupid());
        SharedUtil.putString(Constants.ACCESS_TOKEN, userEntity.getAccesstoken());
        SharedUtil.putString(Constants.PHONE_NUMBER, userEntity.getListUsers().get(0).getPhonenumber());
        SharedUtil.putString(Constants.HEAD_PORTRAIT, userEntity.getListUsers().get(0).getCustomerphoto());
        SharedUtil.putString(Constants.NICKNAME, userEntity.getListUsers().get(0).getUsername());
        SharedUtil.putString(Constants.SEX, userEntity.getListUsers().get(0).getGender());
        SharedUtil.putString(Constants.VETERAN, userEntity.getListUsers().get(0).getGolfage() + "");
        SharedUtil.putString(Constants.ADDRESS, userEntity.getListUsers().get(0).getChinacity_name());

        String usertype = userEntity.getListUsers().get(0).getUsertype();
        SharedUtil.putString(Constants.USER_TYPE, usertype);
        if (usertype.equals("1")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "1");//教练认证状态
        } else if (usertype.equals("0")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "0");//教练认证状态
        } else if (usertype.equals("8")) {
            SharedUtil.putString(Constants.ATTESTATION_STATE, "2");//教练认证状态
        }
    }

    private void loginUserListHuanxin(UserInformationEntity userEntity) {
        String phonenumber = userEntity.getListUsers().get(0).getPhonenumber();
        String password = "123456";
        EMClient.getInstance().login(phonenumber, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }


    private void loginHuanxin() {
        String phonenumber = userEntity.getUserinfo().getPhonenumber();
        String password = "123456";
        EMClient.getInstance().login(phonenumber, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isCheckAgree = isChecked;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(Map<String, String> eventMap) {
        if (eventMap.containsKey("login")) {
            if (eventMap.get("login").equals("wechat")) {
                Log.d("print", "getEventBus: 点击了微信登陆");
                //微信登陆
                if (!isCheckAgree) {
                    Toast.makeText(getActivity(), "请同意服务条款", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("print", "初始化");
                Platform wechat = ShareSDK.getPlatform(getActivity(), Wechat.NAME);
                Log.d("print", "初始化成功");
                wechat.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        userid = platform.getDb().getUserId();
                        map = hashMap;
                        handler.sendEmptyMessage(LOGIN_SUCCEED);
                        Log.d("print", "userid"+userid);
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        handler.sendEmptyMessage(LOGIN_FAILURE);
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                if (wechat.isAuthValid()) {
                    wechat.removeAccount(true);
                } else {
                    //authorize与showUser单独调用一个即可
                    wechat.authorize();//单独授权，OnComplete返回的hashmap是空的
//                    wechat.SSOSetting(true);//此处设置为false，则在优先采用客户端授权的方法，设置true会采用网页方式
                    wechat.showUser(null);//授权并获取用户信息
                    //isValid和removeAccount不开启线程，会直接返回。
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        countDownFlag = false;
    }
}
