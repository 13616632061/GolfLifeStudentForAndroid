package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.QRUserEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.golflibrary.util.SharedUtil;
import com.glorystudent.util.Constants;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.QRCodeUtil;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.OnClick;

/**
 * 添加好友
 * Created by hyj on 2017/2/4.
 */
public class AddFriendsActivity extends BaseActivity {
    private final static String TAG = "AddFriendsActivity";
    private String userid;//用户加密id
    private PopupWindow window;

    @Override
    protected int getContentId() {
        return R.layout.activity_add_friends;
    }

    @OnClick({R.id.back, R.id.rl_number_add, R.id.rl_code_add, R.id.ll_look_code})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.rl_number_add:
                //手机号添加
                startActivity(new Intent(this, PhoneContactActivity.class));
                break;
            case R.id.ll_look_code:
                //查看二维码
                showPopupWindow();
                break;
            case R.id.rl_code_add:
                //扫描二维码添加
                requestPermission(new String[]{android.Manifest.permission.CAMERA}, true, new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        startCamera();
                    }

                    @Override
                    public void onPermissionDenied() {

                    }
                });
                break;
        }
    }

    /**
     * 打开相机
     */
    private void startCamera() {
        Intent intent = new Intent(Intents.Scan.ACTION);
        //设置模式:条形码或者二维码
        intent.putExtra(Intents.Scan.MODE, Intents.Scan.QR_CODE_MODE);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int w = displayMetrics.widthPixels;
        int h = displayMetrics.heightPixels;

        int size = h < w ? h : w;

        size = size >> 1;

        intent.putExtra(Intents.Scan.WIDTH, size);
        intent.putExtra(Intents.Scan.HEIGHT, size);

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0x003);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x003 && resultCode == RESULT_OK && data != null) {
            userid = data.getStringExtra(Intents.Scan.RESULT);
            getGolfFriends(userid);
        }
    }

    private void getGolfFriends(String userid) {
        showLoading();
        String json = "{" + "\"fromrode\":" + "\"0\"" + ",\"uid\":\"" + userid + "\"}";
        String requestJson = RequestUtil.getRequestJson(this, json);
        String url = "/api/APIUser/QueryUsersByUserID";
        Log.i(TAG, "getGolfFriends: " + requestJson);
        Log.i(TAG, "getGolfFriends: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                dismissLoading();
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        QRUserEntity qrUserEntity = new Gson().fromJson(jo.toString(), QRUserEntity.class);
                        List<QRUserEntity.ListUsersBean> listUsers = qrUserEntity.getListUsers();
                        String phoneNumber = listUsers.get(0).getPhonenumber();
                        Intent intent = new Intent(AddFriendsActivity.this, FriendProfileActivity.class);
                        intent.putExtra("phonenumber", phoneNumber);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(AddFriendsActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }

    /**
     * 显示popupWindow
     */
    private void showPopupWindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popu_look_code, null);
        TextView tv_username = (TextView) view.findViewById(R.id.tv_username);
        TextView tv_address = (TextView) view.findViewById(R.id.tv_address);
        ImageView qrcode = (ImageView) view.findViewById(R.id.qrcode);
        ImageView qrcode_headpic = (ImageView) view.findViewById(R.id.qrcode_headpic);

        String url = SharedUtil.getString(Constants.HEAD_PORTRAIT);
        if (url != null) {
            GlideUtil.loadCircleImageView(this, url, qrcode_headpic);
        } else {
            qrcode_headpic.setImageResource(R.drawable.pic_default_avatar);
        }
        tv_username.setText(SharedUtil.getString(Constants.NICKNAME));
        tv_address.setText(SharedUtil.getString(Constants.ADDRESS));
        //创建二维码
        QRCodeUtil.createCode(this, qrcode, SharedUtil.getString(Constants.USER_ID));

        window = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);
        // 在中间显示
        window.showAtLocation(findViewById(R.id.back), Gravity.CENTER, 0, 0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }
}
