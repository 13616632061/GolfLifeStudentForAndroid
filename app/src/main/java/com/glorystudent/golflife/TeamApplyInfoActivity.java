package com.glorystudent.golflife;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glorystudent.entity.ApplyCheckEntity;
import com.glorystudent.golflibrary.base.BaseActivity;
import com.glorystudent.golflibrary.util.GlideUtil;
import com.glorystudent.util.EventBusMapUtil;
import com.glorystudent.util.OkGoRequest;
import com.glorystudent.util.RequestUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 入队申请信息
 * Created by Gavin.J on 2017/5/18.
 */

public class TeamApplyInfoActivity extends BaseActivity {

    private static final String TAG = "TeamApplyInfoActivity";
    @Bind(R.id.iv_apply_info_head)
    ImageView ivHead;
    @Bind(R.id.iv_man)
    ImageView ivMan;
    @Bind(R.id.iv_lady)
    ImageView ivLady;
    @Bind(R.id.tv_apply_info_name)
    TextView tvName;
    @Bind(R.id.tv_apply_info_sex)
    TextView tvSex;
    @Bind(R.id.tv_apply_info_phone)
    TextView tvPhone;
    @Bind(R.id.tv_apply_info_handicap)
    TextView tvHandicap;
    @Bind(R.id.tv_apply_info_refuse)
    TextView tvRefuse;
    @Bind(R.id.tv_apply_info_agree)
    TextView tvAgree;
    private ApplyCheckEntity.ApplyTeamUserListBean datas;

    @Override
    protected int getContentId() {
        return R.layout.activity_apply_info;
    }

    @Override
    protected void init() {
        if (datas != null) {
            GlideUtil.loadCircleImageView(this, datas.getCustomerphoto(), ivHead, R.drawable.pic_default_avatar);
            if (datas.getSex().equals("M")) {
                ivMan.setVisibility(View.VISIBLE);
                ivLady.setVisibility(View.GONE);
                tvSex.setText("男");
            } else {
                ivMan.setVisibility(View.GONE);
                ivLady.setVisibility(View.VISIBLE);
                tvSex.setText("女");
            }
            tvName.setText(datas.getName());
            tvPhone.setText(datas.getTelphone());
            tvHandicap.setText(datas.getHandicap() + "");
        }

    }

    @OnClick({R.id.back, R.id.tv_apply_info_refuse, R.id.tv_apply_info_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_apply_info_refuse:
                requestRefuse();
                break;
            case R.id.tv_apply_info_agree:
                requestAgree();
                break;
        }
    }

    /**
     * 请求拒绝入队
     */
    private void requestRefuse() {
        requestServer(2);//状态2->拒绝
    }

    /**
     * 请求同意入队
     */
    private void requestAgree() {
        requestServer(1);//状态1->同意
    }

    private void requestServer(int status) {
        showLoading();
        String json = "\"applyTeamUser\":{" + "\"id\":" + datas.getId() + ",\"status\":" + status + "}";
        String requestJson = RequestUtil.getJson(this, json);
        Log.i(TAG, "requestAgree: " + requestJson);
        String url = "/api/APITeam/EditTeamUserApply";
        OkGoRequest.getOkGoRequest().setOnOkGoUtilListener(new OkGoRequest.OnOkGoUtilListener() {
            @Override
            public void parseDatas(String json) {
                try {
                    JSONObject jo = new JSONObject(json);
                    String statuscode = jo.getString("statuscode");
                    String statusmessage = jo.getString("statusmessage");
                    if (statuscode.equals("1")) {//正常
                        Toast.makeText(TeamApplyInfoActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(EventBusMapUtil.getStringMap("ApplicationCheckActivity", "refresh"));
                        finish();
                    } else if (statuscode.equals("2")) {//暂无数据
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    } else {
                        Log.i(TAG, "parseDatas: " + statusmessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void requestFailed() {
                dismissLoading();
                Toast.makeText(TeamApplyInfoActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        }).getEntityData(TeamApplyInfoActivity.this, url, requestJson);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(Map<String, ApplyCheckEntity.ApplyTeamUserListBean> map) {
        if (map.containsKey("TeamApplyInfoActivity")) {
            ApplyCheckEntity.ApplyTeamUserListBean datas = map.get("TeamApplyInfoActivity");
            this.datas = datas;
            datas = null;
        }
    }
}
