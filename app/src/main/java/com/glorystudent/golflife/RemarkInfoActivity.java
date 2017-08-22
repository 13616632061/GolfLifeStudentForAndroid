package com.glorystudent.golflife;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.glorystudent.entity.StudentRequestEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.util.Constants;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改备注页面
 * Created by hyj on 2017/2/21.
 */
public class RemarkInfoActivity extends BaseActivity {
    private static final String TAG = "RemarkInfoActivity";
    @Bind(R.id.et_remark)
    public EditText et_remark;
    private int friend_userid;
    private String oldRemark;//原始备注

    @Override
    protected int getContentId() {
        return R.layout.activity_remark_info;
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        friend = (FriendsProfileEntity.ListfriendsBean) bundle.getSerializable("friendbean");
//        if (friend != null) {
//            et_remark.setText(friend.getRemark());
//            et_remark.setSelection(et_remark.getText().length());
//        }
        friend_userid = intent.getIntExtra("friend_userid", -1);
        oldRemark = intent.getStringExtra("remarkName");
        if (oldRemark != null && !oldRemark.isEmpty()) {
            et_remark.setText(oldRemark);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(et_remark, 0);
                }
            }
        }, 200);
    }

    @OnClick({R.id.back, R.id.tv_save})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //返回
                finish();
                break;
            case R.id.tv_save:
                String remark = et_remark.getText().toString();
                if (remark != null && !remark.isEmpty()) {
                    if (!remark.equals(oldRemark)) {
                        saveUsername(remark);
                    }
                } else {
                    Toast.makeText(RemarkInfoActivity.this, "备注名不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void saveUsername(final String remark) {
        showLoading();
        StudentRequestEntity studentRequestEntity = new StudentRequestEntity();
        StudentRequestEntity.FriendsBean friendsBean = new StudentRequestEntity.FriendsBean();
//        friendsBean.setFriendid(friend.getFriendid());
//        friendsBean.setUserid(friend.getUserid());
//        friendsBean.setRelationshiptime(friend.getRelationshiptime());
        friendsBean.setFriend_userID(friend_userid);
        friendsBean.setRemark(remark);
        studentRequestEntity.setFriends(friendsBean);
        String request = new Gson().toJson(studentRequestEntity);
        String requestJson = RequestUtil.getRequestJson(this, request);
        String url = "/api/APIFriends/EditFriends";
        Log.i(TAG, "saveUsername: " + requestJson);
        Log.i(TAG, "saveUsername: " + Constants.BASE_URL + url);
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                dismissLoading();
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {
                        Toast.makeText(RemarkInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("FriendProfileActivity", "refresh"));
                        EventBus.getDefault().post(EventBusMapUtil.getIntMap(2, 1));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(RemarkInfoActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(this, url, requestJson);
    }
}
